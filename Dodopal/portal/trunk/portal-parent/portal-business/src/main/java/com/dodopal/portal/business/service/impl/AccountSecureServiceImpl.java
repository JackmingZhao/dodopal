package com.dodopal.portal.business.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerKeyTypeDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MerKeyTypeMD5PwdEnum;
import com.dodopal.common.enums.MoblieCodeTypeEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerchantUserBean;
import com.dodopal.portal.business.bean.MerchantUserUpBean;
import com.dodopal.portal.business.bean.MobileBean;
import com.dodopal.portal.business.service.AccountSecureService;
import com.dodopal.portal.delegate.AccountSecureDelegate;
@Service
public class AccountSecureServiceImpl implements AccountSecureService {
    @Autowired
    AccountSecureDelegate accountSecureDelegate;

    @Override
    public DodopalResponse<Map<String, String>> send(String moblieNum) {
        return accountSecureDelegate.send(moblieNum, MoblieCodeTypeEnum.USER_DE_UP);
    }

    @Override
    public DodopalResponse<Map<String, String>> sendNoCheck(String moblieNum) {
        return accountSecureDelegate.sendNoCheck(moblieNum);
    }

    @Override
    public DodopalResponse<String> moblieCodeCheck(MobileBean mobileBean) {
        return accountSecureDelegate.moblieCodeCheck(mobileBean.getMoblieNum(), mobileBean.getDypwd(), mobileBean.getSerialNum());
    }

    @Override
    public DodopalResponse<String> saveAccountMoblie(MobileBean mobileBean,MerchantUserBean merchUserBaen) {
        DodopalResponse<String> responseMoblieCodeCheck = new DodopalResponse<String>();
        responseMoblieCodeCheck = accountSecureDelegate.moblieCodeCheck(mobileBean.getMoblieNum(), mobileBean.getDypwd(), mobileBean.getSerialNum());
        DodopalResponse<String> response = new DodopalResponse<String>();
        if(ResponseCode.SUCCESS.equals(responseMoblieCodeCheck.getCode())){
            MerchantUserDTO  merchantUserDTO  = new MerchantUserDTO();
            merchantUserDTO.setMerUserMobile(merchUserBaen.getMerUserMobile());
            merchantUserDTO.setId(merchUserBaen.getId());
            DodopalResponse<Boolean> responseMer =accountSecureDelegate.saveMerUserMoblie(merchantUserDTO); 
            response.setCode(responseMer.getCode());
            response.setResponseEntity(responseMer.getResponseEntity().toString());
        }else{
            response.setCode(responseMoblieCodeCheck.getCode());
            response.setResponseEntity(responseMoblieCodeCheck.getResponseEntity());
        }
        return response;
    }

    @Override
    public DodopalResponse<String> validateMerUserPWD(MerchantUserUpBean merchantUserUpBean) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        DodopalResponse<Boolean> responsePwd =accountSecureDelegate.validateMerUserPWD(merchantUserUpBean.getId(), merchantUserUpBean.getMerUserPWD());
        if(ResponseCode.SUCCESS.equals(responsePwd.getCode())){
            response.setCode(responsePwd.getCode());
        }else{
            response.setCode(responsePwd.getCode());
        }
        return response;
    }

    @Override
    public DodopalResponse<String> updateMerUserPWDById(MerchantUserUpBean merchantUserUpBean) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        DodopalResponse<Boolean> responsePwd =accountSecureDelegate.updateMerUserPWDById(merchantUserUpBean.getId(), merchantUserUpBean.getMerUserPWD(), merchantUserUpBean.getMerUserUpPWD());
        if(ResponseCode.SUCCESS.equals(responsePwd.getCode())){
            response.setCode(responsePwd.getCode());
            response.setResponseEntity(responsePwd.getResponseEntity().toString());
        }else{
            response.setCode(responsePwd.getCode());
            response.setResponseEntity(responsePwd.getResponseEntity().toString());
        }
        return response;
    }

    @Override
    public DodopalResponse<MerchantUserUpBean> findMerMD5PayPWD(MerchantUserUpBean merchantUserUpBean) {
        DodopalResponse<MerchantUserUpBean> response = new DodopalResponse<MerchantUserUpBean>();
        MerKeyTypeDTO merKeyTypeDTO = new MerKeyTypeDTO();
        merKeyTypeDTO.setMerCode(merchantUserUpBean.getMerCode());
        merKeyTypeDTO.setActivate("0");
        DodopalResponse<MerKeyTypeDTO> responseDto=accountSecureDelegate.findMerMD5PayPWDOrBackPayPWD(merKeyTypeDTO, MerKeyTypeMD5PwdEnum.MerMD5PayPwd);
        response.setCode(responseDto.getCode());
        MerKeyTypeDTO merKey =new MerKeyTypeDTO();
        merKey=responseDto.getResponseEntity();
        try {
            if(merKey!=null){
                MerchantUserUpBean merBean = new MerchantUserUpBean();
                merBean.setMerCode(merKey.getMerCode());
                merBean.setMerMD5PayPwd(merKey.getMerMD5PayPwd());
                response.setResponseEntity(merBean); 
            }
          
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(responseDto.getCode());
        }
       
        return response;
    }

    @Override
    public DodopalResponse<MerchantUserUpBean> findMerMD5BackPayPWD(MerchantUserUpBean merchantUserUpBean) {
        DodopalResponse<MerchantUserUpBean> response = new DodopalResponse<MerchantUserUpBean>();
        MerKeyTypeDTO merKeyTypeDTO = new MerKeyTypeDTO();
        merKeyTypeDTO.setMerCode(merchantUserUpBean.getMerCode());
        merKeyTypeDTO.setActivate("0");
        DodopalResponse<MerKeyTypeDTO> responseDto=accountSecureDelegate.findMerMD5PayPWDOrBackPayPWD(merKeyTypeDTO, MerKeyTypeMD5PwdEnum.MerMD5BackPayPWD);
       try {
           response.setCode(responseDto.getCode());
           MerKeyTypeDTO merKey =new MerKeyTypeDTO();
           merKey=responseDto.getResponseEntity();
           if(merKey!=null){
               MerchantUserUpBean merBean = new MerchantUserUpBean();
               merBean.setMerCode(merKey.getMerCode());
               merBean.setMerMD5BackPayPWD(merKey.getMerMD5BackPayPWD());
               response.setResponseEntity(merBean);
           }
      
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(responseDto.getCode());
        }
        return response;
    }

    @Override
    public DodopalResponse<String> updateMerMD5PayPWD(MerchantUserUpBean merchantUserUpBean) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        MerKeyTypeDTO merKeyTypeDTO = new MerKeyTypeDTO();
        merKeyTypeDTO.setMerCode(merchantUserUpBean.getMerCode());
        merKeyTypeDTO.setActivate("0");
        merKeyTypeDTO.setMerMD5PayPwd(merchantUserUpBean.getUpmerMD5PayPwd());
        DodopalResponse<Boolean> responseup=accountSecureDelegate.updateMerMD5PayPwdOrBackPayPWD(merKeyTypeDTO, merchantUserUpBean.getMerMD5PayPwd(), MerKeyTypeMD5PwdEnum.MerMD5PayPwd);
        response.setCode(responseup.getCode());
        response.setResponseEntity(responseup.getResponseEntity().toString());
        return response;
    }

    @Override
    public DodopalResponse<String> updateMerMD5BackPayPWD(MerchantUserUpBean merchantUserUpBean) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        MerKeyTypeDTO merKeyTypeDTO = new MerKeyTypeDTO();
        merKeyTypeDTO.setMerCode(merchantUserUpBean.getMerCode());
        merKeyTypeDTO.setActivate("0");
        merKeyTypeDTO.setMerMD5BackPayPWD(merchantUserUpBean.getUpmerMD5BackPayPWD());
        DodopalResponse<Boolean> responseup=accountSecureDelegate.updateMerMD5PayPwdOrBackPayPWD(merKeyTypeDTO, merchantUserUpBean.getMerMD5BackPayPWD(), MerKeyTypeMD5PwdEnum.MerMD5BackPayPWD);
        response.setCode(responseup.getCode());
        response.setResponseEntity(responseup.getResponseEntity().toString());
        return response;
    }

    @Override
    public DodopalResponse<String> upModifyPayInfoFlg(MerchantUserBean merchUserBaen) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        MerchantUserDTO merchantUserDTO = new MerchantUserDTO();
        merchantUserDTO.setId(merchUserBaen.getId());
        merchantUserDTO.setPayInfoFlg(merchUserBaen.getPayInfoFlg());
        DodopalResponse<Boolean> responseup=accountSecureDelegate.upModifyPayInfoFlg(merchantUserDTO);
        response.setCode(responseup.getCode());
        response.setResponseEntity(responseup.getResponseEntity().toString());
        return response;
    }

    @Override
    public DodopalResponse<MerchantUserBean> findModifyPayInfoFlg(String id) {
        DodopalResponse<MerchantUserBean> response = new DodopalResponse<MerchantUserBean>();
        try {
            MerchantUserBean merchantUserBean = new MerchantUserBean();
            DodopalResponse<MerchantUserDTO> reMerchantUserDTO=accountSecureDelegate.findModifyPayInfoFlg(id);
            MerchantUserDTO merchantUserDTO = reMerchantUserDTO.getResponseEntity();
            if(merchantUserDTO!=null){
                merchantUserBean.setPayInfoFlg(merchantUserDTO.getPayInfoFlg());;
                response.setCode(reMerchantUserDTO.getCode());
                response.setResponseEntity(merchantUserBean);
            }
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      
        return response;
    }

}
