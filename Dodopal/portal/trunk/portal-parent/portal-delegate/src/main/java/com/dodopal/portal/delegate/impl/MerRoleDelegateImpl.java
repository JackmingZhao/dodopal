package com.dodopal.portal.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerFunctionInfoDTO;
import com.dodopal.api.users.dto.MerRoleDTO;
import com.dodopal.api.users.dto.query.MerRoleQueryDTO;
import com.dodopal.api.users.facade.MerRoleFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.MerRoleDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;

/**
 * 类说明 ：
 * @author lifeng
 */
@Service("merRoleDelegate")
public class MerRoleDelegateImpl extends BaseDelegate implements MerRoleDelegate {

	@Override
	public DodopalResponse<DodopalDataPage<MerRoleDTO>> findMerRoleByPage(MerRoleQueryDTO merRoleQueryDTO) {
		MerRoleFacade merRoleFacade = getFacade(MerRoleFacade.class, DelegateConstant.FACADE_USERS_URL);
		return merRoleFacade.findMerRoleByPage(merRoleQueryDTO);
	}

	@Override
	public DodopalResponse<MerRoleDTO> findMerRoleByMerRoleCode(String merCode, String merRoleCode) {
		MerRoleFacade merRoleFacade = getFacade(MerRoleFacade.class, DelegateConstant.FACADE_USERS_URL);
		return merRoleFacade.findMerRoleByMerRoleCode(merCode, merRoleCode);
	}

	@Override
	public DodopalResponse<Integer> addMerRole(MerRoleDTO merRoleDTO) {
		MerRoleFacade merRoleFacade = getFacade(MerRoleFacade.class, DelegateConstant.FACADE_USERS_URL);
		return merRoleFacade.addMerRole(merRoleDTO);
	}

	@Override
	public DodopalResponse<Integer> updateMerRole(MerRoleDTO merRoleDTO) {
		MerRoleFacade merRoleFacade = getFacade(MerRoleFacade.class, DelegateConstant.FACADE_USERS_URL);
		return merRoleFacade.updateMerRole(merRoleDTO);
	}

	@Override
	public DodopalResponse<Integer> batchDelMerRoleByCodes(String merCode, List<String> merRoleCodes) {
		MerRoleFacade merRoleFacade = getFacade(MerRoleFacade.class, DelegateConstant.FACADE_USERS_URL);
		return merRoleFacade.batchDelMerRoleByCodes(merCode, merRoleCodes);
	}

	@Override
	public DodopalResponse<List<MerFunctionInfoDTO>> findMerFuncInfoByUserCode(String userCode) {
		MerRoleFacade merRoleFacade = getFacade(MerRoleFacade.class, DelegateConstant.FACADE_USERS_URL);
		return merRoleFacade.findMerFuncInfoByUserCode(userCode);
	}

	@Override
	public DodopalResponse<Boolean> checkMerRoleNameExist(String merCode, String merRoleName, String id) {
		MerRoleFacade merRoleFacade = getFacade(MerRoleFacade.class, DelegateConstant.FACADE_USERS_URL);
		return merRoleFacade.checkMerRoleNameExist(merCode, merRoleName, id);
	}

}
