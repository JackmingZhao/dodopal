package com.dodopal.common.model;

import java.io.Serializable;
import java.util.List;

import com.dodopal.common.interceptor.PageParameter;

/**
 * 分页包装类
 */
public class DodopalDataPage<T> implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 4909801673431174885L;
    
    public DodopalDataPage(PageParameter page, List<T> records) {
        if (page != null) {
            this.setPageNo(page.getPageNo());
            this.setPageSize(page.getPageSize());
            this.setRows(page.getRows());
            this.setTotalPages(page.getTotalPages());
        }
        this.setRecords(records);
    }

    /**
     * 分页信息-当前第几页
     */
    public Integer pageNo;

    /**
     * 分页信息-每页数量
     */
    public Integer pageSize;

    /**
     * 分页信息-当页记录
     */
    public List<T> records;

    /**
     * 分页信息-总共记录数
     */
    public Integer rows;

    /**
     * 分页信息-总页数
     */
    public Integer totalPages;

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

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
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
    
    public void buildPages(PageParameter page, List<T> records) {
        if(page != null) {
            this.setPageNo(page.getPageNo());
            this.setPageSize(page.getPageSize());
            this.setRows(page.getRows());
            this.setTotalPages(page.getTotalPages());
        }
        this.setRecords(records);
    }

}
