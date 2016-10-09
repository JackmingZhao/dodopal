package com.dodopal.users.business.facadeImpl;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerKeyTypeDTO;
import com.dodopal.api.users.facade.MerKeyTypeFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MerKeyTypeMD5PwdEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.users.business.model.MerKeyType;
import com.dodopal.users.business.service.MerKeyTypeService;
@Service("merKeyTypeFacade")
public class MerKeyTypeFacadeImpl implements MerKeyTypeFacade {
    
    
    private final static Logger log = LoggerFactory.getLogger(MerKeyTypeFacadeImpl.class);
    @Autowired
    private MerKeyTypeService merKeyTypeService;
   
    @Override
    public DodopalResponse<MerKeyTypeDTO> findMerMD5PayPWDOrBackPayPWD(MerKeyTypeDTO merKeyTypeDTO,MerKeyTypeMD5PwdEnum enum1) {
        DodopalResponse<MerKeyTypeDTO> response = new DodopalResponse<MerKeyTypeDTO>();
        MerKeyType keyType = new MerKeyType();
        try {
            PropertyUtils.copyProperties(keyType, merKeyTypeDTO);
            if(enum1.equals(MerKeyTypeMD5PwdEnum.MerMD5PayPwd)){
                keyType = merKeyTypeService.findMerMD5PayPwd(keyType);
            }else if(enum1.equals(MerKeyTypeMD5PwdEnum.MerMD5BackPayPWD)){
                keyType = merKeyTypeService.findMerMD5BackPayPWD(keyType);
            }
            if(null!=keyType){
                PropertyUtils.copyProperties(merKeyTypeDTO, keyType);
                response.setCode(ResponseCode.SUCCESS);
            }else{
                merKeyTypeDTO = null;
                response.setCode(ResponseCode.KEY_TYPE_NOT_FIND);
            }
            response.setResponseEntity(merKeyTypeDTO);
        }catch (Exception e) {
            log.debug("MerKeyTypeFacadeImpl call error", e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return response;
    }
    @Override
    public DodopalResponse<Boolean> updateMerMD5PayPwdOrBackPayPWD(MerKeyTypeDTO merKeyTypeDTO,String oldPWD,MerKeyTypeMD5PwdEnum enum1) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        MerKeyType keyType = new MerKeyType();
        try {
            int rows = 0;
            PropertyUtils.copyProperties(keyType, merKeyTypeDTO);
            if(enum1.equals(MerKeyTypeMD5PwdEnum.MerMD5PayPwd)){
                if(StringUtils.isBlank(merKeyTypeDTO.getMerMD5PayPwd())){
                    response.setCode(ResponseCode.UP_MER_MD5_PAY_PWD_ERR);
                }else{
                    rows = merKeyTypeService.updateMerMD5PayPwd(keyType, oldPWD);
                    response.setCode(ResponseCode.SUCCESS);
                }
            }else if(enum1.equals(MerKeyTypeMD5PwdEnum.MerMD5BackPayPWD)){
                if(StringUtils.isBlank(merKeyTypeDTO.getMerMD5BackPayPWD())){
                    response.setCode(ResponseCode.UP_MER_MD5_BACK_PAY_PWD_ERR);
                }else{
                    rows =  merKeyTypeService.updateMerMD5BackPayPWD(keyType, oldPWD);
                    response.setCode(ResponseCode.SUCCESS);
                }
            }
            response.setResponseEntity(rows>0?true:false);
        }catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.debug("MerKeyTypeFacadeImpl call error", e);
            throw new RuntimeException(e);
        }
        response.setMessage("");
       
        return response;
    }
}
