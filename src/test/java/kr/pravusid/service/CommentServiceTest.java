package kr.pravusid.service;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class CommentServiceTest {

    @Test
    public void findReplyOrderTest() {
        // given
        List<Long> list = Arrays.asList(0L, 1L, 2L, 3L);
        // then
        Assert.assertEquals(Long.valueOf(0), list.stream().filter(i -> i >= 0).findFirst().get());
    }

}
