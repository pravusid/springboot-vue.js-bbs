package kr.pravusid.dto;

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

    public Pagination calcPage(Page page, int blockSize) {
        this.currPage = page.getNumber();
        this.totalPages = (page.getTotalPages() == 0) ? 0 : page.getTotalPages() - 1;

        firstBlock = currPage - (currPage % blockSize);
        lastBlock = (firstBlock + (blockSize - 1) > totalPages) ? totalPages : firstBlock + (blockSize - 1);
        prev = (firstBlock == 0) ? 0 : firstBlock - 1;
        next = (lastBlock == totalPages) ? totalPages : lastBlock + 1;

        return this;
    }

    public int getCurrPage() {
        return currPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getFirstBlock() {
        return firstBlock;
    }

    public int getLastBlock() {
        return lastBlock;
    }

    public int getPrev() {
        return prev;
    }

    public int getNext() {
        return next;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
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

    public enum FilterType {
        TITLE, CONTENT, USER, COMMENTS, ALL;
    }

}
