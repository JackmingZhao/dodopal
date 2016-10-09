package com.dodopal.portal.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerFunctionInfoBean;
import com.dodopal.portal.business.bean.MerRoleBean;
import com.dodopal.portal.business.bean.MerRoleQueryBean;

public interface MerRoleService {

	/**
	 * 查询商户角色列表【分页】
	 * @param merRoleQueryDTO merCode 商户号必填
	 * @return
	 */
	public DodopalResponse<DodopalDataPage<MerRoleBean>> findMerRoleByPage(MerRoleQueryBean merRoleQueryDTO);

	/**
	 * 查看商户角色信息
	 * @param merCode 商户号必填
	 * @param merRoleCode 角色号必填
	 * @return
	 */
	public DodopalResponse<MerRoleBean> findMerRoleByMerRoleCode(String merCode, String merRoleCode);

	/**
	 * 添加角色
	 * @param merRoleDTO merCode 商户号必填 merRoleName 角色名称必填
	 * @return
	 */
	public DodopalResponse<String> addMerRole(MerRoleBean merRoleDTO);

	/**
	 * 修改角色
	 * @param merRoleDTO merCode 商户号必填 merRoleCode 角色号必填 merRoleName 角色名称必填
	 * @return
	 */
	public DodopalResponse<String> updateMerRole(MerRoleBean merRoleDTO);

	/**
	 * 批量删除商户角色信息
	 * @param merCode
	 * @param merRoleCodes
	 * @return
	 */
	public DodopalResponse<String> batchDelMerRoleByCodes(String merCode, List<String> merRoleCodes);

	/**
	 * 根据用户编号获取权限列表
	 * @param userCode
	 * @return
	 */
	public DodopalResponse<List<MerFunctionInfoBean>> findMerFuncInfoByUserCode(String userCode);
	
	public DodopalResponse<Boolean> checkMerRoleNameExist(MerRoleBean bean);
	
}
