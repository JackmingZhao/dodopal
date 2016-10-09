package com.dodopal.common.interceptor;

import java.io.Serializable;

/**
 * 分页参数类
 */
public class PageParameter implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8928001281813972010L;

    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 当前页
     */
    public Integer pageNo;

    /**
     * 每页数量
     */
    public Integer pageSize;

    /**
     * 总行数
     */
    public Integer rows;

    /**
     * 总页数
     */
    public Integer totalPages;

    /**
     * 前一页，保留字段，当前不使用
     */
    private int prePage;

    /**
     * 下一页， 保留字段，当前不使用
     */
    private int nextPage;

    public PageParameter() {
        this.pageNo = 1;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    /**
     * @param currentPage
     * @param pageSize
     */
    public PageParameter(int currentPage, int pageSize) {
        this.pageNo = currentPage;
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

}
