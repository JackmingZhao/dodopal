package com.dodopal.product.business.service;


import java.util.List;

import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalResponse;
public interface  CityFindService {
    /**
     * 查询城市列表
     * @param custType
     * @param custCode
     * @return
     */
    public DodopalResponse<List<Area>>findYktCitys(MerUserTypeEnum custType, String custCode);
    
    /**
     * 查找所有开通的业务城市
     * add by D
     * @return
     */
    public List<Area> getAllBusinessCity();

}
