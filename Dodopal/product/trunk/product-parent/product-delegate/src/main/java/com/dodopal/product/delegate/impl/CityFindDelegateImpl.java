package com.dodopal.product.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.facade.MerchantFacade;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.delegate.BaseDelegate;
import com.dodopal.product.delegate.CityFindDelegate;
import com.dodopal.product.delegate.constant.DelegateConstant;

@Service("cityFindDelegate")
public class CityFindDelegateImpl extends  BaseDelegate implements CityFindDelegate{
    @Override
    public DodopalResponse<List<Area>> findYktCitys(MerUserTypeEnum custType, String custCode){
   
        MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<List<Area>>  response = facade.findYktCitys(custType, custCode);
        return response;
    }

}
