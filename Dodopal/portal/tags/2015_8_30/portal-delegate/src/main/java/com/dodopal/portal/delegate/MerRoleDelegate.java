package com.dodopal.portal.delegate;

import java.util.List;

import com.dodopal.api.users.dto.MerFunctionInfoDTO;
import com.dodopal.api.users.dto.MerRoleDTO;
import com.dodopal.api.users.dto.query.MerRoleQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

/**
 * 类说明 ：
 * @author lifeng
 */

public interface MerRoleDelegate {
	/**
	 * 查询商户角色列表【分页】
	 * @param merRoleQueryDTO merCode 商户号必填
	 * @return
	 */
	public DodopalResponse<DodopalDataPage<MerRoleDTO>> findMerRoleByPage(MerRoleQueryDTO merRoleQueryDTO);

	/**
	 * 查看商户角色信息
	 * @param merCode 商户号必填
	 * @param merRoleCode 角色号必填
	 * @return
	 */
	public DodopalResponse<MerRoleDTO> findMerRoleByMerRoleCode(String merCode, String merRoleCode);

	/**
	 * 添加角色
	 * @param merRoleDTO merCode 商户号必填 merRoleName 角色名称必填
	 * @return
	 */
	public DodopalResponse<Integer> addMerRole(MerRoleDTO merRoleDTO);

	/**
	 * 修改角色
	 * @param merRoleDTO merCode 商户号必填 merRoleCode 角色号必填 merRoleName 角色名称必填
	 * @return
	 */
	public DodopalResponse<Integer> updateMerRole(MerRoleDTO merRoleDTO);

	/**
	 * 批量删除商户角色信息
	 * @param merCode
	 * @param merRoleCodes
	 * @return
	 */
	public DodopalResponse<Integer> batchDelMerRoleByCodes(String merCode, List<String> merRoleCodes);

	/**
	 * 根据用户编号获取权限列表
	 * @param userCode
	 * @return
	 */
	public DodopalResponse<List<MerFunctionInfoDTO>> findMerFuncInfoByUserCode(String userCode);
	
	public DodopalResponse<Boolean> checkMerRoleNameExist(String merCode, String merRoleName, String id);
}
