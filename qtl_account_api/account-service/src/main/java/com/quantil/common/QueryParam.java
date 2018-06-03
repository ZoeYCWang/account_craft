package com.quantil.common;

/**
 * QueryParam
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/8/1
 */
public class QueryParam {
    private int page;
    private int size;
    private String sortBy;
    private String order;

    public static QueryParam getDefault() {
        QueryParam queryParam = new QueryParam();
        queryParam.setPage(1);
        queryParam.setSize(10);
        return queryParam;
    }

    public QueryParam() {
    }

    public QueryParam(Integer page, Integer size, String sortBy, String order) {
        if (page == null)
            page = 1;
        if (size == null)
            size = 10;
        if (page == -1)
            size = -1;
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
        this.order = order;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
