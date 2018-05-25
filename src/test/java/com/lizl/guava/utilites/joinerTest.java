package com.lizl.guava.utilites;

import com.google.common.base.Joiner;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.ImmutableMap.of;
import static java.util.stream.Collectors.joining;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author lizl
 * @date 2018/5/24 16:56
 */
public class joinerTest {

    private final List<String> stringList = Arrays.asList(
         "Google", "Guava", "Java", "Scala", "Kafka"
    );
    private final List<String> stringListWithNullValue = Arrays.asList(
        "Google", "Guava", "Java", "Scala", null
    );

    private final String targetFileName = "D:\\guava-joiner.txt";

    private final Map<String,String> stringMap = of("Hello", "Guava", "Java", "Scala");

    @Test
    public void testJoinerOn(){
        String result = Joiner.on("#").join(stringList);
        assertThat(result, equalTo("Google#Guava#Java#Scala#Kafka"));
    }

    @Test
    public void testJoinerOnWithNullValue(){
        String result = Joiner.on("#").skipNulls().join(stringListWithNullValue);
        assertThat(result, equalTo("Google#Guava#Java#Scala"));
    }

    @Test
    public void testJoiner_On_WithNullValue_UseDefaultValue(){
        String result = Joiner.on("#").useForNull("DEFAULT").join(stringListWithNullValue);
        assertThat(result, equalTo("Google#Guava#Java#Scala#DEFAULT"));
    }

    @Test
    public void testJoin_on_Append_To_StringBuilder(){
        final StringBuilder builder = new StringBuilder();
        StringBuilder resultBuilder = Joiner.on("#").useForNull("DEFAULT").appendTo(builder, stringListWithNullValue);
        assertThat(resultBuilder, sameInstance(builder));
        assertThat(resultBuilder.toString(), equalTo("Google#Guava#Java#Scala#DEFAULT"));
        assertThat(builder.toString(), equalTo("Google#Guava#Java#Scala#DEFAULT"));
    }

    @Test
    public void testJoin_on_Append_To_Writer(){
        try (FileWriter fileWriter = new FileWriter(new File(targetFileName))){
            Joiner.on("#").useForNull("DEFAULT").appendTo(fileWriter, stringListWithNullValue);
            assertThat(Files.isFile().test(new File(targetFileName)), equalTo(true));
        } catch (IOException e) {
            fail("append to the writer occur fetal error");
        }
    }

    @Test
    public void testJoiningByStreamSkipNullValues(){
        String result = stringListWithNullValue.stream().filter(item->item!=null&&!item.isEmpty()).collect(joining("#"));
        assertThat(result, equalTo("Google#Guava#Java#Scala"));
    }

    @Test
    public void testJoiningByStreamWithDefaultValue(){
        String result = stringListWithNullValue.stream().map(this::defaultValue).collect(joining("#"));
        assertThat(result, equalTo("Google#Guava#Java#Scala#DEFAULT"));
    }

    private String defaultValue(final String item){
        return item==null||item.isEmpty() ? "DEFAULT":item;
    }

    @Test
    public void testJoinOnWithMap(){
        String result = Joiner.on("#").withKeyValueSeparator("=").join(stringMap);
        assertThat(result, equalTo("Hello=Guava#Java=Scala"));
    }

    @Test
    public void testJoinOnWithMapToAppendable(){
        try (FileWriter fileWriter = new FileWriter(new File(targetFileName))){
            Joiner.on("#").withKeyValueSeparator("=").appendTo(fileWriter, stringMap);
            assertThat(Files.isFile().test(new File(targetFileName)), equalTo(true));
        } catch (IOException e) {
            fail("append to the writer occur fetal error");
        }
    }
}
