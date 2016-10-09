package com.dodopal.portal.business.bean;

import java.util.List;

import com.dodopal.api.product.dto.PrdProductYktDTO;
import com.dodopal.common.model.BaseEntity;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年8月20日 下午3:51:49
 */
public class CardRechargeBean extends BaseEntity{

    private static final long serialVersionUID = -2705047540969283525L;

    /**
     * 城市名称
     */
    private String cityName;
    
    /**
     * 城市Code
     */
    private String cityCode;
    
    
    /**
     * 产品库一卡通
     */
    private List<PrdProductYktBean> prdProductYktList;
    
    /**
     * 面额列表
     */
    private List<Integer> proPriceList;
    
    
    public List<Integer> getProPriceList() {
        return proPriceList;
    }

    public void setProPriceList(List<Integer> proPriceList) {
        this.proPriceList = proPriceList;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public List<PrdProductYktBean> getPrdProductYktList() {
        return prdProductYktList;
    }

    public void setPrdProductYktList(List<PrdProductYktBean> prdProductYktList) {
        this.prdProductYktList = prdProductYktList;
    }


}
