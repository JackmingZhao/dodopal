package com.dodopal.product.delegate;

import java.util.List;

import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.common.model.DodopalResponse;

/**
 * @author vn
 */
public interface MerUserDelegate {

    public DodopalResponse<MerchantUserDTO> findUserInfoByUserName(String userNameOrMobile);

    public DodopalResponse<MerchantUserDTO> findUserInfoByUserCode(String userCode);

    public DodopalResponse<Boolean> modifyPayInfoFlg(MerchantUserDTO userDTO);

    /**
     * @param cityCode
     * @param userId
     * @return 更新用户设置的默认城市
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
     * 根据id查询
     * @param id
     * @return DodopalResponse<MerchantUserDTO>
     */
    public DodopalResponse<MerchantUserDTO> findUserInfoById(String id);

    /**
     * 根据老系统userid和usertype修改密码
     * @param userid
     * @param usertype
     * @param userpwd
     * @return
     */
    public DodopalResponse<Boolean> updateUserPwdByOldUserId(String userid, String usertype, String userpwd);

	/**
	 * 查询商户用户列表
	 * 
	 * @param merCode
	 * @return
	 */
	public DodopalResponse<List<MerchantUserDTO>> findMerchantUserList(MerchantUserDTO merUserDTO);
}
