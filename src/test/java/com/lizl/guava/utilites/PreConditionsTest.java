package com.lizl.guava.utilites;

import com.google.common.base.Preconditions;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author lizl
 * @date 2018/5/25 16:53
 */
public class PreConditionsTest {

    private void checkNotNull(final List<String> list){
        Preconditions.checkNotNull(list);
    }

    private void checkNotNullWithMessage(final List<String> list){
        Preconditions.checkNotNull(list, "The list should be not null");
    }

    private void checkNotNullWithFormatMessage(final List<String> list){
        Preconditions.checkNotNull(list, "The list should be not null and the size must be %s", 2);
    }

    @Test
    public void testCheckNotNullWithMessage(){
        try {
            checkNotNullWithMessage(null);
        }catch (Exception e){
//            assertThat(e, is(NullPointerException.class));
            assertThat(e.getMessage(), equalTo("The list should be not null"));
        }
    }

    @Test
    public void testCheckNotNullWithFormatMessage(){
        try {
            checkNotNullWithFormatMessage(null);
        }catch (Exception e){
//            assertThat(e, is(NullPointerException.class));
            assertThat(e.getMessage(), equalTo("The list should be not null and the size must be 2"));
        }
    }

    @Test
    public void testCheckArguments(){
        final String type = "A";
        Preconditions.checkArgument(type.equals("a"),"111 %s", 3);
    }

    @Test
    public void testCheckState(){
        final String type = "A";
        Preconditions.checkState(type.equals("a"),"111 %s", 3);
    }

    @Test
    public void testCheckIndex(){
        
    }
}
