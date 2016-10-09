package com.dodopal.users.business.model.query;

import com.dodopal.api.users.dto.MerchantQueryDTO;

/**
 * 类说明 ：
 * @author lifeng
 */

public class MerchantQuery extends MerchantQueryDTO {
    private static final long serialVersionUID = 9186707949400066078L;
    /*排序*/
    private String orderBy;

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

}
