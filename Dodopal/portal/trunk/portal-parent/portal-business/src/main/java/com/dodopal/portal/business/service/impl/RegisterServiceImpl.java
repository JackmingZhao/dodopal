package com.dodopal.portal.business.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.portal.business.bean.MerchantUserBean;
import com.dodopal.portal.business.bean.MerchantUserRegisterBean;
import com.dodopal.portal.business.service.RegisterService;
import com.dodopal.portal.delegate.RegisterDelegate;
import com.dodopal.portal.delegate.SendDelegate;

@Service("registerService")
public class RegisterServiceImpl implements RegisterService {

    private Logger logger = Logger.getLogger(RegisterServiceImpl.class);
    
    @Autowired
    private RegisterDelegate registerDelegate;
    
    @Autowired
    private SendDelegate sendDelegate;

    @Override
    public DodopalResponse<Boolean> checkMobileExist(String mobile) {
        if (DDPStringUtil.isPopulated(mobile)) {
            return registerDelegate.checkMobileExist(mobile);
        } else {
            DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(false);
            return response;
        }
    }

    @Override
    public DodopalResponse<Boolean> checkUserNameExist(String userName) {
    	DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        if (DDPStringUtil.isPopulated(userName)) {
        	response = registerDelegate.checkUserNameExist(userName);
        	if(ResponseCode.USERS_MER_USER_NAME_EXIST.equals(response.getCode())){
        		response.setCode(ResponseCode.USERS_MER_USER_NAME_REG);
        	}
        } else {
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(false);
            
        }
        return response;
    }

    @Override
    public DodopalResponse<Boolean> checkMerchantNameExist(String merName) {
    	 DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        if (DDPStringUtil.isPopulated(merName)) {
        	response= registerDelegate.checkMerchantNameExist(merName);
        	if(ResponseCode.USERS_MER_NAME_EXIST.equals(response.getCode())){
        		response.setCode(ResponseCode.USERS_MER_NAME_REG);
        	}
        } else {
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(false);           
        }        
        return response;
    }

    @Override
    public DodopalResponse<MerchantUserBean> registerUser(MerchantUserBean merUserBean, String dypwd, String serialNumber) {
        List<String> msg = validateUser(merUserBean);
        if(CollectionUtils.isNotEmpty(msg)) {
            DodopalResponse<MerchantUserBean> response = new DodopalResponse<MerchantUserBean>();
            response.setCode(ResponseCode.PORTAL_REGISTER_USER_ERR);
            response.setMessage(DDPStringUtil.concatenate(msg, "!\n"));
            return response;
        }
        MerchantUserDTO merUserDTO = new MerchantUserDTO();
        BeanUtils.copyProperties(merUserBean, merUserDTO);
        DodopalResponse<MerchantUserDTO> result = registerDelegate.registerUser(merUserDTO, dypwd, serialNumber);

        MerchantUserDTO resultMerUserDTO = result.getResponseEntity();
        MerchantUserBean resultMerUserBean = new MerchantUserBean();
        if(resultMerUserDTO!=null) {
            BeanUtils.copyProperties(resultMerUserDTO, resultMerUserBean);
        }

        DodopalResponse<MerchantUserBean> response = new DodopalResponse<MerchantUserBean>();
        response.setCode(result.getCode());
        response.setMessage(result.getMessage());
        response.setResponseEntity(resultMerUserBean);
        return response;
    }

    @Override
    public DodopalResponse<String> registerMerchant(MerchantUserRegisterBean merchantBean) {
        List<String> msg = validateMerchant(merchantBean);
        if (CollectionUtils.isNotEmpty(msg)) {
            DodopalResponse<String> response = new DodopalResponse<String>();
            response.setCode(ResponseCode.PORTAL_REGISTER_MER_ERR);
            response.setMessage(DDPStringUtil.concatenate(msg, "!\n"));
            return response;
        }
        return registerDelegate.registerMerchant(convertToMerchantDTO(merchantBean), merchantBean.getDypwd(), merchantBean.getSerialNumber());
    }
    
    private List<String> validateUser(MerchantUserBean merUserBean) {
        List<String> msg = new ArrayList<String>();
        if (!DDPStringUtil.isMobile(merUserBean.getMerUserMobile())) {
            msg.add("手机号格式不正确.");
        }

        if (!DDPStringUtil.existingWithLengthRange(merUserBean.getMerUserName(), 4, 20)) {
            msg.add("用户名不能为空, 且长度必须在4到20位字符之间.");
        }

        // TODO 验证用户名输入的合法性
        
        
        DodopalResponse<Boolean> response =registerDelegate.checkUserNameExist(merUserBean.getMerUserName());
        if((response != null && ResponseCode.SUCCESS.equals(response.getCode()) && response.getResponseEntity())) {
            msg.add("该用户名已被注册，请您重新输入.");
        }
        
        // 由于前台传过来的密码是已经加密过的密码, 这里对密码的复杂性交由前台校验, 这里不做校验, 
       
        return msg;
    }
    
    private List<String> validateMerchant(MerchantUserRegisterBean merUserBean) {

        List<String> msg = new ArrayList<String>();
        if (!DDPStringUtil.lessThan(merUserBean.getMerName(), 200)) {
            msg.add("请输入商户店面名称，可由中文、字母、数字组成, 且长度必须小于等于200.");
        }
        // TODO 验证用户名输入的合法性 -- 请输入商户店面名称，可由中文、字母、数字组成
        DodopalResponse<Boolean> response = registerDelegate.checkMerchantNameExist(merUserBean.getMerName());
        if ((response != null && ResponseCode.SUCCESS.equals(response.getCode()) && response.getResponseEntity())) {
            msg.add("该商户名称已注册，请您重新输入.");
        }

        //TODO 联系人 -- 英文、中文 
        if (!DDPStringUtil.existingWithLengthRange(merUserBean.getMerLinkUser(), 2, 20)) {
            msg.add("联系人不能为空,且长度必须在2到20个字符之间.");
        }

        if (!DDPStringUtil.isMobile(merUserBean.getMerLinkUserMobile())) {
            msg.add("手机号格式不正确.");
        }

        if (DDPStringUtil.isNotPopulated(merUserBean.getDypwd())) {
            msg.add("手机验证码不能为空.");
        }

        if (DDPStringUtil.isNotPopulated(merUserBean.getAddress())) {
            msg.add("请填写您店面的详细地址，如：XX市XX区XX街XX号XX室.");
        }

        if (!DDPStringUtil.existingWithLengthRange(merUserBean.getMerUserName(), 4, 20)) {
            msg.add("用户名不能为空, 且长度必须在4到20位字符之间.");
        }

        // TODO 验证用户名输入的合法性

        DodopalResponse<Boolean> response2 = registerDelegate.checkUserNameExist(merUserBean.getMerUserName());
        if ((response2 != null && ResponseCode.SUCCESS.equals(response2.getCode()) && response2.getResponseEntity())) {
            msg.add("该用户名已被注册，请您重新输入.");
        }
        // 由于前台传过来的密码是已经加密过的密码, 这里对密码的复杂性交由前台校验, 这里不做校验, 

        return msg;
    }
    
    /**
     * merchantBean转换 DTO
     * @param merchantBean
     * @return
     */
    private MerchantDTO convertToMerchantDTO(MerchantUserRegisterBean merchantBean) {
        //商户用户登录密码
        MerchantDTO merchantDTO = new MerchantDTO();
        merchantDTO.setMerName(merchantBean.getMerName());
        merchantDTO.setMerLinkUser(merchantBean.getMerLinkUser());
        merchantDTO.setMerLinkUserMobile(merchantBean.getMerLinkUserMobile());
        merchantDTO.setMerPro(merchantBean.getProvinceCode());
        merchantDTO.setMerCity(merchantBean.getCityCode());
        merchantDTO.setMerAdds(merchantBean.getAddress());
        // TODO address 详细地址
        MerchantUserDTO merUserDTO = new MerchantUserDTO();
        merUserDTO.setMerUserName(merchantBean.getMerUserName());
        merUserDTO.setMerUserPWD(merchantBean.getMerUserPWD());

        merchantDTO.setMerchantUserDTO(merUserDTO);
        return merchantDTO;
    }
    
    /**
     * 发送手机号码，获取验证码
     */
    public DodopalResponse<Map<String, String>> sendNoCheck(String mobile) {
        DodopalResponse<Map<String, String>> response = null;
        try {
            //电话号验证
            if (!DDPStringUtil.isMobile(mobile)) {
                //手机号格式不正确
                response = new DodopalResponse<Map<String, String>>();
                response.setCode(ResponseCode.PORTAL_MOB_ERROR);
            } else {
                response = sendDelegate.sendNoCheck(mobile);
            }
        }
        catch (Exception e) {
            response = new DodopalResponse<Map<String, String>>();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            logger.error(e.getMessage(), e);
        }
        return response;
    }

    @Override
    public DodopalResponse<String> moblieCodeCheck(String moblieNum, String dypwd, String serialNumber) {
        DodopalResponse<String> response = null;
        try {
            //电话号验证
            if (!DDPStringUtil.isMobile(moblieNum)) {
                //手机号格式不正确
                response = new DodopalResponse<String>();
                response.setCode(ResponseCode.PORTAL_MOB_ERROR);
            } else {
                response = sendDelegate.moblieCodeCheck(moblieNum, dypwd, serialNumber);
            }
        }
        catch (Exception e) {
            response = new DodopalResponse<String>();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            logger.error(e.getMessage(), e);
        }
        return response;
    }

}
