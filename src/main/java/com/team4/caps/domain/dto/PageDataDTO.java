package com.team4.caps.domain.dto;

public class PageDataDTO<T> {

    private Integer totalPage;

    private T pageData;

    public T getPageData() {
        return pageData;
    }

    public void setPageData(T pageData) {
        this.pageData = pageData;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalCount, Integer pageSize) {
        if (totalCount <= 0) {
            totalPage = 1;
            return;
        }
        this.totalPage = (int) Math.ceil(totalCount / (double)pageSize);
    }
}
