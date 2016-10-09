package com.dodopal.product.business.service;

import java.util.List;

import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.bean.MerchantUserBean;

public interface MerchantUserService {

    /**
     * 根据用户名或手机号查找用户信息
     * @param userNameOrMobile
     * @return
     */
    public DodopalResponse<MerchantUserBean> findUserInfoByMobileOrUserName(String userNameOrMobile);

    /**
     * 根据用户code查找用户信息
     * @param userNameOrMobile
     * @return
     */
    public DodopalResponse<MerchantUserBean> findUserInfoByUserCode(String userCode);

    /**
     * 修改支付确认信息标志
     * @param userbean
     * @return
     */
    public DodopalResponse<Boolean> modifyPayInfoFlg(MerchantUserBean userbean);

    /**
     * @param cityCode
     * @param userId
     * @return 修改默认城市
     */
    public DodopalResponse<Boolean> updateMerUserBusCity(String cityCode, String userId);

    public DodopalResponse<Boolean> resetPWDByMobile(String mobile, String pwd);

    /**
     * 根据用户id查询用户姓名
     * @param id 用户id
     * @return
     */
    public DodopalResponse<String> findNickNameById(String id);
    
    
    /**
     * 根据id查询联系人信息
     * @param id
     * @return DodopalResponse<MerchantUserBean>
     */
    DodopalResponse<MerchantUserBean> findUserInfoById(String id);

    /**
     * 根据老系统userid和usertype修改密码
     * @param userid
     * @param usertype
     * @param userpwd
     * @return
     */
    public DodopalResponse<Boolean> updateUserPwdByOldUserId(String userid, String usertype, String userpwd);

	/**
	 * 根据商户号查询商户下所有用户
	 * 
	 * @param merCode
	 * @return
	 */
	public DodopalResponse<List<MerchantUserDTO>> findMerchantUserList(MerchantUserDTO merUserDTO);
}
