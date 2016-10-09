package com.dodopal.product.business.service.impl;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.users.dto.MerKeyTypeDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.MerKeyTypeMD5PwdEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.model.MerKeyType;
import com.dodopal.product.business.service.MerKeyTypeService;
import com.dodopal.product.delegate.MerKeyTypeDelegate;
@Service
public class MerKeyTypeServiceImpl implements MerKeyTypeService{
    private final static Logger log = LoggerFactory.getLogger(MerKeyTypeServiceImpl.class);
    
    @Autowired
    MerKeyTypeDelegate merKeyTypeDelegate;
    
    public DodopalResponse<MerKeyType> findMerMD5PayPWDOrBackPayPWD(String merCode, MerKeyTypeMD5PwdEnum keyTypeMD5PwdEnum){
        DodopalResponse<MerKeyType> dodopalResponse = new DodopalResponse<MerKeyType>();
        MerKeyTypeDTO query = new MerKeyTypeDTO();
        try {
            query.setMerCode(merCode);
            query.setActivate(ActivateEnum.ENABLE.getCode());
            DodopalResponse<MerKeyTypeDTO> merKeyTypeResult = merKeyTypeDelegate.findMerMD5PayPWDOrBackPayPWD(query, keyTypeMD5PwdEnum);
            if(merKeyTypeResult.isSuccessCode()){
                if(null!=merKeyTypeResult.getResponseEntity()){
                    MerKeyType bean = new MerKeyType();
                    PropertyUtils.copyProperties(bean, merKeyTypeResult.getResponseEntity());
                    dodopalResponse.setResponseEntity(bean);
                }
            }
            dodopalResponse.setCode(merKeyTypeResult.getCode());
        }catch (HessianRuntimeException e){
            log.error("MerKeyTypeServiceImpl findMerMD5PayPWDOrBackPayPWD call an error",e);
            dodopalResponse.setCode(ResponseCode.PRODUCT_CALL_USERS_ERROR);
        }
        catch (Exception e) {
            log.error("MerKeyTypeServiceImpl findMerMD5PayPWDOrBackPayPWD call an error",e);
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }
}
