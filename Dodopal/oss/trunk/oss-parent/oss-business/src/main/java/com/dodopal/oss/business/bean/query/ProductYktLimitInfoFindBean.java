package com.dodopal.oss.business.bean.query;

import com.dodopal.common.model.QueryBase;

/**
 * 额度信息查询条件
 * @author 
 * @version 
 */
public class ProductYktLimitInfoFindBean extends QueryBase{

    private static final long serialVersionUID = -3330346763699042960L;

    private String yktName;
    
    public String getYktName() {
        return yktName;
    }

    public void setYktName(String yktName) {
        this.yktName = yktName;
    }
}
