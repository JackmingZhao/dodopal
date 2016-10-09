package com.dodopal.portal.business.service;

import java.util.List;

import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerchantUserBean;
import com.dodopal.portal.business.bean.MerchantUserQueryBean;

public interface MerUserService {

	/**
	 * 查询用户（分页）
	 * @param userQueryDTO
	 * @return
	 */
	public DodopalResponse<DodopalDataPage<MerchantUserBean>> findMerOperatorByPage(MerchantUserQueryBean userQueryDTO);

	/**
	 * 根据用户编号查询用户信息
	 * @param merCode
	 * @param userCode
	 * @return
	 */
	public DodopalResponse<MerchantUserBean> findMerOperatorByUserCode(String merCode, String userCode);

	/**
	 * 创建用户
	 * @param merchantUserDTO
	 * @return
	 */
	public DodopalResponse<String> addMerOperator(MerchantUserBean merchantUserDTO);

	/**
	 * 修改用户
	 * @param merchantUserDTO
	 * @return
	 */
	public DodopalResponse<String> updateMerOperator(MerchantUserBean merchantUserDTO);

	/**
	 * 批量停启用用户
	 * @param merCode
	 * @param updateUser
	 * @param activate
	 * @param userCodes
	 * @return
	 */
	public DodopalResponse<String> batchActivateMerOperator(String merCode, String updateUser, ActivateEnum activate, List<String> userCodes);

	/**
	 * 分配角色
	 * @param merchantUserDTO
	 * @return
	 */
	public DodopalResponse<String> configMerOperatorRole(MerchantUserBean merchantUserDTO);

	/**
	 * 重置密码
	 * @param merCode
	 * @param id
	 * @param password
	 * @param updateUser
	 * @return
	 */
	public DodopalResponse<String> resetOperatorPwd(String merCode, String id, String password, String updateUser);

}
