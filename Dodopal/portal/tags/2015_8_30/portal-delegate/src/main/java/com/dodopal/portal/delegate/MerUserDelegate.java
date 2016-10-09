package com.dodopal.portal.delegate;

import java.util.List;
import java.util.Map;

import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.api.users.dto.query.MerchantUserQueryDTO;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

/**
 * @author vn
 */
public interface MerUserDelegate {

	/**
	 * 商户/个人用户 用户信息查询
	 * @param userDTO
	 * @return
	 */
	public DodopalResponse<List<MerchantUserDTO>> findMerUserList(MerchantUserDTO userDTO);

	/**
	 * 查看个人用户信息
	 * @param userID 用户ID
	 * @return
	 */
	public DodopalResponse<MerchantUserDTO> findMerUser(String userID);

	/**
	 * @Title: resetPwdByMobile
	 * @Description: 根据手机号重置用户密码
	 * @param moblie
	 * @param pwd DodopalResponse<Map<String,String>> 返回类型
	 */
	public DodopalResponse<Boolean> resetPwdByMobile(String mobile, String pwd);

	
	/**
	 * @Title: updateUser
	 * @Description: 更新用户
	 * @param userDTO DodopalResponse<Boolean> 返回类型
	 * @throws
	 */
	public DodopalResponse<Boolean> updateUser(MerchantUserDTO userDTO);

	/**
	 * @Title: startOrStop
	 * @Description: 启用或停用
	 * @param userDTO
	 * @return 设定文件 DodopalResponse<String> 返回类型
	 * @throws
	 */
	public DodopalResponse<Map<String, String>> startOrStop(List<String> idList, ActivateEnum activate,String updateUser);

	public DodopalResponse<DodopalDataPage<MerchantUserDTO>> findMerUsersByPage(MerchantUserQueryDTO dto);

	public DodopalResponse<String> findMerUserNameByMobile(String mobile);
	public DodopalResponse<MerchantUserDTO> findUserInfoByUserName(String userNameOrMobile);

}
