package com.dodopal.api.users.dto.query;

import com.dodopal.common.model.QueryBase;
/**
 * 折扣查询
 * @author lenovo
 *
 */
public class MerDiscountQueryDTO extends QueryBase {
    private static final long serialVersionUID = -8755208127660838509L;
    //商户号
    private String merCode;
    //启用标示
    private String activate;



    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }



}
