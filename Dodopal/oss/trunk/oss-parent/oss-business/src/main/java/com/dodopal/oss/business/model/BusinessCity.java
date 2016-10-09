package com.dodopal.oss.business.model;

import java.util.ArrayList;
import java.util.List;

import com.dodopal.common.model.BaseEntity;

public class BusinessCity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     *  城市排序(拼音字母)
     */
    private String sort;
    
    /**
     *  省份code
     */
    private String parentCode;
    
    /**
     *  城市code
     */
    private String cityCode;
    
    /**
     *  城市名称
     */
    private String cityName;
    
    /**
     *  城市状态(0:未被其他通卡公司占用;1:已被其他通卡公司占用)
     */ 
    private String states;
    
    /**
     *  一卡通代码
     */ 
    private String yktCode;
    
    /**
     * 记录通卡公司业务城市
     */
    private String BusinesscityIds;
    
    private List<BusinessCity> list = new ArrayList<BusinessCity>();
 

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public List<BusinessCity> getList() {
        return list;
    }

    public void setList(List<BusinessCity> list) {
        this.list = list;
    }

    public String getYktCode() {
        return yktCode;
    }

    public void setYktCode(String yktCode) {
        this.yktCode = yktCode;
    }

    public String getBusinesscityIds() {
        return BusinesscityIds;
    }

    public void setBusinesscityIds(String businesscityIds) {
        BusinesscityIds = businesscityIds;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

}
