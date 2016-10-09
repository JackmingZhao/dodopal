package com.dodopal.portal.business.service;

import java.util.Map;

import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerchantUserBean;
import com.dodopal.portal.business.bean.MerchantUserRegisterBean;

public interface RegisterService {

    /**
     * 检查手机号是否已注册
     * @param mobile
     * @return true:已注册
     */
    public DodopalResponse<Boolean> checkMobileExist(String mobile);

    /**
     * 检查用户名是否已注册
     * @param userName
     * @return true:已注册
     */
    public DodopalResponse<Boolean> checkUserNameExist(String userName);

    /**
     * 检查商户名称是否已注册
     * @param merName
     * @return
     */
    public DodopalResponse<Boolean> checkMerchantNameExist(String merName);

    /**
     * 个人用户注册
     * @param merUserBean
     * @return
     */
    public DodopalResponse<MerchantUserBean> registerUser(MerchantUserBean merUserBean, String mobilemobileCheckCode, String serialNumber);

    /**
     * 商户注册
     * @param merUserBean merUserBean.merUserName：用户名 merUserBean.merUserPWD：密码
     * merUserBean.merUserMobile：用户手机号
     * @return 商户号
     */
    public DodopalResponse<String> registerMerchant(MerchantUserRegisterBean merchantBean);
    
    /**
     * 发送手机号码，获取验证码
     */
    public DodopalResponse<Map<String,String>> sendNoCheck(String mobile);
    
    /**
     * 手机验证码验证
     * @param moblieNum    手机号
     * @param dypwd        验证码
     * @param serialNumber 序号
     * @return
     */
    public DodopalResponse<String> moblieCodeCheck(String moblieNum,String dypwd,String serialNumber);
    
}
