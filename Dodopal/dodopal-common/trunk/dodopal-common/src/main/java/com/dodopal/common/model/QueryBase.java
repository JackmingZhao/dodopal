package com.dodopal.common.model;

import java.io.Serializable;

import com.dodopal.common.interceptor.PageParameter;

public class QueryBase implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2633028000208180824L;

    private PageParameter page;

    public PageParameter getPage() {
        return page;
    }

    public void setPage(PageParameter page) {
        this.page = page;
    }
}
