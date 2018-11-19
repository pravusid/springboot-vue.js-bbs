package kr.pravusid.dto;

import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PaginationTest {

    @Test
    public void 게시물_7개_페이지당게시물_1개_현재페이지_6_페이지계산_결과() {
        List<String> data = Arrays.asList("고길동", "둘리", "도우너", "희동이", "피카츄", "라이츄", "망나뇽");
        Page<String> page = new PageImpl<>(data, new PageRequest(5, 1), data.size());
        Pagination pagination = new Pagination();
        pagination.calcPage(page, 5);

        assertThat(pagination.getCurrPage()).isEqualTo(5);
        assertThat(pagination.getTotalPages()).isEqualTo(6);
        assertThat(pagination.getFirstBlock()).isEqualTo(5);
        assertThat(pagination.getLastBlock()).isEqualTo(6);
        assertThat(pagination.getPrev()).isEqualTo(4);
        assertThat(pagination.getNext()).isEqualTo(6);
    }

    @Test
    public void 게시물_7개_페이지당게시물_1개_현재페이지_3_페이지계산_결과() {
        List<String> data = Arrays.asList("고길동", "둘리", "도우너", "희동이", "피카츄", "라이츄", "망나뇽");
        Page<String> page = new PageImpl<>(data, new PageRequest(2, 1), data.size());
        Pagination pagination = new Pagination();
        pagination.calcPage(page, 5);

        assertThat(pagination.getCurrPage()).isEqualTo(2);
        assertThat(pagination.getTotalPages()).isEqualTo(6);
        assertThat(pagination.getFirstBlock()).isEqualTo(0);
        assertThat(pagination.getLastBlock()).isEqualTo(4);
        assertThat(pagination.getPrev()).isEqualTo(0);
        assertThat(pagination.getNext()).isEqualTo(5);
    }

    @Test
    public void 게시물_5개_페이지당게시물_1개_현재페이지_5_페이지계산_결과() {
        List<String> data = Arrays.asList("고길동", "둘리", "도우너", "희동이", "피카츄");
        Page<String> page = new PageImpl<>(data, new PageRequest(4, 1), data.size());
        Pagination pagination = new Pagination();
        pagination.calcPage(page, 5);

        assertThat(pagination.getCurrPage()).isEqualTo(4);
        assertThat(pagination.getTotalPages()).isEqualTo(4);
        assertThat(pagination.getFirstBlock()).isEqualTo(0);
        assertThat(pagination.getLastBlock()).isEqualTo(4);
        assertThat(pagination.getPrev()).isEqualTo(0);
        assertThat(pagination.getNext()).isEqualTo(4);
    }

}
