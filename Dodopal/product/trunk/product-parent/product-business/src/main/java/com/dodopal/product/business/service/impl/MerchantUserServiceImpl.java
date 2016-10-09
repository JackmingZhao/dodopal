package com.dodopal.product.business.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.PayWarnFlagEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.bean.MerchantUserBean;
import com.dodopal.product.business.service.MerchantUserService;
import com.dodopal.product.delegate.MerUserDelegate;

@Service
public class MerchantUserServiceImpl implements MerchantUserService {

	@Autowired
	MerUserDelegate merUserDelegate;
	
	private final static Logger log = LoggerFactory.getLogger(MerchantUserServiceImpl.class);
    
    public DodopalResponse<MerchantUserBean> findUserInfoByMobileOrUserName(String userNameOrMobile){
        DodopalResponse<MerchantUserBean> response = new DodopalResponse<MerchantUserBean>();
    	try {
	        DodopalResponse<MerchantUserDTO> getResponse = merUserDelegate.findUserInfoByUserName(userNameOrMobile);
	        MerchantUserBean bean = new MerchantUserBean();
	        if(null!=getResponse.getResponseEntity()){
	                PropertyUtils.copyProperties(bean, getResponse.getResponseEntity());
	        }
	        response.setCode(getResponse.getCode());
	        response.setResponseEntity(bean);
    	}catch (HessianRuntimeException e) {
            response.setCode(ResponseCode.PRODUCT_CALL_USERS_ERROR);
            log.error("MerchantUserServiceImpl's findUserInfoByMobileOrUserName call an error:  ",e);
    	}catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            log.error("MerchantUserServiceImpl's findUserInfoByMobileOrUserName call an error:  ",e);
        }
        return response;
    }
    
    
    public DodopalResponse<Boolean> modifyPayInfoFlg(MerchantUserBean userbean){
    	MerchantUserDTO userDTO = new MerchantUserDTO();
    	DodopalResponse<Boolean> dodopalResponse;
    	try {
    		userDTO.setPayInfoFlg(PayWarnFlagEnum.OFF_WARN.getCode());
			PropertyUtils.copyProperties(userDTO, userbean);
			dodopalResponse = merUserDelegate.modifyPayInfoFlg(userDTO);
    	} catch (HessianRuntimeException e) {
    		dodopalResponse = new DodopalResponse<Boolean>();
			dodopalResponse.setCode(ResponseCode.PRODUCT_CALL_USERS_ERROR);
			log.error("MerchantUserServiceImpl's modifyPayInfoFlg call an error:  ",e);
		} catch (Exception e) {
			dodopalResponse = new DodopalResponse<Boolean>();
			dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
			log.error("MerchantUserServiceImpl's modifyPayInfoFlg call an error:  ",e);
		}
		return dodopalResponse;
    }


	@Override
	public DodopalResponse<MerchantUserBean> findUserInfoByUserCode(
			String userCode) {
		 DodopalResponse<MerchantUserBean> response = new DodopalResponse<MerchantUserBean>();
	    	try {
		        DodopalResponse<MerchantUserDTO> getResponse = merUserDelegate.findUserInfoByUserCode(userCode);
		        MerchantUserBean bean = new MerchantUserBean();
		        if(null!=getResponse.getResponseEntity()){
		                PropertyUtils.copyProperties(bean, getResponse.getResponseEntity());
		        }
		        response.setCode(getResponse.getCode());
		        response.setResponseEntity(bean);
	    	}catch (HessianRuntimeException e) {
	            response.setCode(ResponseCode.PRODUCT_CALL_USERS_ERROR);
	            log.error("MerchantUserServiceImpl's findUserInfoByMobileOrUserName call an error:  ",e);
	    	}catch (Exception e) {
	            response.setCode(ResponseCode.SYSTEM_ERROR);
	            log.error("MerchantUserServiceImpl's findUserInfoByMobileOrUserName call an error:  ",e);
	        }
	        return response;
	}
	
	
	@Override
	public DodopalResponse<Boolean> updateMerUserBusCity(String cityCode,String userId) {
        DodopalResponse<Boolean> response =  merUserDelegate.updateMerUserBusCity(cityCode, userId);
        return response;
	}


	@Override
	public DodopalResponse<Boolean> resetPWDByMobile(String mobile, String pwd) {
		return merUserDelegate.resetPWDByMobile(mobile, pwd);
	}



    @Override
    public DodopalResponse<String> findNickNameById(String id) {
        // TODO Auto-generated method stub
        return merUserDelegate.findNickNameById(id);
    }


    /**
     * 根据id查询
     */
    public DodopalResponse<MerchantUserBean> findUserInfoById(String id) {
        log.info("query this id:"+id);
        DodopalResponse<MerchantUserBean> response = new DodopalResponse<MerchantUserBean>();
        //根据id获取用户信息
        DodopalResponse<MerchantUserDTO> merchantUserDTO = merUserDelegate.findUserInfoById(id);
        try {
            if(merchantUserDTO.getCode().equals(ResponseCode.SUCCESS)){
                MerchantUserBean merchantUserBean = new MerchantUserBean();
                PropertyUtils.copyProperties(merchantUserBean, merchantUserDTO.getResponseEntity());
                response.setResponseEntity(merchantUserBean);
                response.setCode(ResponseCode.SUCCESS);
            }else{
                response.setCode(merchantUserDTO.getCode());
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
              e.printStackTrace();
              log.error("MerchantUserServiceImpl findUserInfoById throws:" ,e);
              response.setCode(merchantUserDTO.getCode());
        }
        return response;
    }

	@Override
	public DodopalResponse<Boolean> updateUserPwdByOldUserId(String userid, String usertype, String userpwd) {
		return merUserDelegate.updateUserPwdByOldUserId(userid, usertype, userpwd);
	}


	@Override
	public DodopalResponse<List<MerchantUserDTO>> findMerchantUserList(MerchantUserDTO merUserDTO) {
		return merUserDelegate.findMerchantUserList(merUserDTO);
	}


}
