package com.dodopal.portal.business.service;

import java.util.Map;

import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerchantUserBean;

public interface MerchantUserService {
    
    /**
     * 重置密码
     * @param userId 用户ID
     * @return 
     */
    public  DodopalResponse<Map<String,String>> resetPwd(String userId);
    
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: sendAuthCode 
     * @Description: 发送短信验证码
     * @param mobile
     * @return    设定文件 
     * DodopalResponse<Map<String,String>>    返回类型 
     * @throws 
     */
    public  DodopalResponse<Map<String,String>> sendAuthCode(String mobile);
    
    /**
     * @Title: checkAuthCode 
     * @Description: 检查验证码
     * @return    设定文件 
     * DodopalResponse<Map<String,String>>    返回类型 
     * @throws
     */
    public  DodopalResponse<String> checkAuthCode(String mobile,String code,String seq);
    
    /** 
     * @Title: modifyPWD 
     * @Description: 手机重置密码
     * @param mobile
     * @param pwd
     * @return    设定文件 
     * DodopalResponse<Map<String,String>>    返回类型 
     * @throws 
     */
    public DodopalResponse<Boolean> modifyPWD(String mobile,String pwd);

    /** 
     * @Title: findMerUserNameByMobile 
     * @Description: 手机号查找账号
     * @param mobile
     * @return    设定文件 
     * DodopalResponse<String>    返回类型 
     * @throws 
     */
    public DodopalResponse<String> findMerUserNameByMobile(String mobile);

    
    /**
     * 查看用户信息
     * @param userId 用户ID
     * @return 
     */
    public  DodopalResponse<MerchantUserBean> findMerUserInfo(MerchantUserBean bean);

    /******************************************用户信息******************************************结束*/
    
    
    public  DodopalResponse<MerchantUserBean> findUserInfoByMobileOrUserName(String userNameOrMobile);
}
