package com.dodopal.common.util;

import java.io.Serializable;

import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;

/**
 * 分页包装通用工具类
 */
public class DodopalDataPageUtil implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 4909801673431174885L;

    public static PageParameter convertPageInfo(DodopalDataPage<?> entity) {
        if (entity != null) {
            PageParameter page = new PageParameter();
            page.setPageNo(entity.getPageNo());
            page.setPageSize(entity.getPageSize());
            page.setRows(entity.getRows());
            page.setTotalPages(entity.getTotalPages());
            return page;
        }
        return null;
    }
}
