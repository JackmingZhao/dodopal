package com.dodopal.oss.business.bean.query;

import com.dodopal.common.model.QueryBase;

/**
 * 通卡公司基础信息查询条件
 * @author 
 * @version 
 */
public class ProductYKTBDFindBean extends QueryBase{

    private static final long serialVersionUID = -3330346763699042960L;

    private String yktName;

    private String activate;
    
    public String getYktName() {
        return yktName;
    }

    public void setYktName(String yktName) {
        this.yktName = yktName;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }
}
