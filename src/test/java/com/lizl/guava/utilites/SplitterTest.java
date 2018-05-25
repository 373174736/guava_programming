package com.lizl.guava.utilites;

import com.google.common.base.Splitter;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author lizl
 * @date 2018/5/25 11:46
 */
public class SplitterTest {

    @Test
    public void testSplitOnSplit(){
        List<String> result = Splitter.on("|").splitToList("hello|world");
        assertThat(result, notNullValue());
        assertThat(result.size(),equalTo(2));
        assertThat(result.get(0),equalTo("hello"));
        assertThat(result.get(1),equalTo("world"));
    }

    @Test
    public void testSplit_On_OmitEmpty(){
        List<String> result = Splitter.on("|").splitToList("hello|world|||");
        assertThat(result, notNullValue());
        assertThat(result.size(),equalTo(5));
        result = Splitter.on("|").omitEmptyStrings().splitToList("hello|world|||");
        assertThat(result.size(),equalTo(2));
    }

    @Test
    public void testSplit_On_OmitEmpty_TrimResult(){
        List<String> result = Splitter.on("|").omitEmptyStrings().splitToList("hello | world|||");
        assertThat(result, notNullValue());
        assertThat(result.size(),equalTo(2));
        assertThat(result.get(0),equalTo("hello "));
        assertThat(result.get(1),equalTo(" world"));
        result = Splitter.on("|").trimResults().omitEmptyStrings().splitToList("hello | world|||");
        assertThat(result, notNullValue());
        assertThat(result.size(),equalTo(2));
        assertThat(result.get(0),equalTo("hello"));
        assertThat(result.get(1),equalTo("world"));
    }

    @Test
    public void textSplitFixLength(){
        List<String> result = Splitter.fixedLength(4).splitToList("aaaabbbbccccdddd");
        assertThat(result, notNullValue());
        assertThat(result.size(),equalTo(4));
        assertThat(result.get(0),equalTo("aaaa"));
        assertThat(result.get(3),equalTo("dddd"));
    }

    @Test
    public void textSplitOnSplitLimit(){
        List<String> result = Splitter.on("#").limit(3).splitToList("hello#world#java#google#scala");
        assertThat(result, notNullValue());
        assertThat(result.size(),equalTo(3));
        assertThat(result.get(0), equalTo("hello"));
        assertThat(result.get(1), equalTo("world"));
        assertThat(result.get(2), equalTo("java#google#scala"));
    }

    @Test
    public void textSplitOnPatternString(){
        List<String> result = Splitter.onPattern("\\|").trimResults().omitEmptyStrings().splitToList("hello | world|||");
        assertThat(result, notNullValue());
        assertThat(result.size(),equalTo(2));
        assertThat(result.get(0), equalTo("hello"));
        assertThat(result.get(1), equalTo("world"));
    }

    @Test
    public void textSplitOnPattern(){
        List<String> result = Splitter.on(Pattern.compile("\\|")).trimResults().omitEmptyStrings().splitToList("hello | world|||");
        assertThat(result, notNullValue());
        assertThat(result.size(),equalTo(2));
        assertThat(result.get(0), equalTo("hello"));
        assertThat(result.get(1), equalTo("world"));
    }

    @Test
    public void textSplitOnSplitToMap(){
        Map<String, String> result = Splitter.on(Pattern.compile("\\|")).trimResults()
                .omitEmptyStrings().withKeyValueSeparator("=").split("hello=HELLO | world=WORLD|||");
        assertThat(result, notNullValue());
        System.out.println(result);
        assertThat(result.size(),equalTo(2));
        assertThat(result.get("hello"), equalTo("HELLO"));
        assertThat(result.get("world"), equalTo("WORLD"));

    }
}
