package com.talsist.dto;

import org.springframework.data.domain.Page;

public class Pagination {

    private int currPage;
    private int totalPages;

    private int firstBlock;
    private int lastBlock;

    private int prev;
    private int next;

    /* 검색 관련 parameter */
    private String filter;
    private String keyword;

    public enum FilterType {
        TITLE, CONTENT, USER, COMMENT, ALL;
    }

    public Pagination calcPage(Page<? extends Object> page, int blockSize) {
        this.currPage = page.getNumber() + 1;
        this.totalPages = (page.getTotalPages() == 0) ? 1 : page.getTotalPages();

        firstBlock = currPage - ((currPage - 1) % blockSize);
        lastBlock = ((firstBlock + blockSize - 1) > totalPages) ? totalPages : firstBlock + blockSize - 1;
        prev = (firstBlock == 1) ? 1 : firstBlock - 1;
        next = (lastBlock == totalPages) ? totalPages : lastBlock + 1;

        return this;
    }

    public int getCurrPage() {
        return currPage - 1;
    }

    public int getTotalPages() {
        return totalPages - 1;
    }

    public int getFirstBlock() {
        return firstBlock - 1;
    }

    public int getLastBlock() {
        return lastBlock - 1;
    }

    public int getPrev() {
        return prev - 1;
    }

    public int getNext() {
        return next - 1;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        if (FilterType.valueOf(filter.toUpperCase()) != null) {
            this.filter = filter;
        }
    }

    public boolean filterMatcher(FilterType type) {
        return Pagination.FilterType.valueOf(this.filter.toUpperCase()).equals(type);
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSearchQuery() {
        return (filter == null || keyword == null) ? "" : "&filter=" + filter + "&keyword=" + keyword;
    }

}
