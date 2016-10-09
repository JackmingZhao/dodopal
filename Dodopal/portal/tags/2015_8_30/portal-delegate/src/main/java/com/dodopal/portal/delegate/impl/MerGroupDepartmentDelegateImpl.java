package com.dodopal.portal.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerGroupDepartmentDTO;
import com.dodopal.api.users.dto.query.MerGroupDepartmentQueryDTO;
import com.dodopal.api.users.facade.MerGroupDepartmentFacade;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.MerGroupDepartmentDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;

@Service("merGroupDepartmentDelegate")
public class MerGroupDepartmentDelegateImpl extends BaseDelegate implements MerGroupDepartmentDelegate {

	@Override
	public DodopalResponse<List<MerGroupDepartmentDTO>> findMerGroupDepartmentDTOList(MerGroupDepartmentDTO departmentDTO, String fromFlag) {
		MerGroupDepartmentFacade facade = getFacade(MerGroupDepartmentFacade.class, DelegateConstant.FACADE_USERS_URL);
		return facade.findMerGroupDepartmentDTOList(departmentDTO, fromFlag);
	}

	@Override
	public DodopalResponse<DodopalDataPage<MerGroupDepartmentDTO>> findMerGroupDepartmentDTOListByPage(MerGroupDepartmentQueryDTO departmentDTO, String fromFlag) {
		MerGroupDepartmentFacade facade = getFacade(MerGroupDepartmentFacade.class, DelegateConstant.FACADE_USERS_URL);
		return facade.findMerGroupDepartmentDTOListByPage(departmentDTO, fromFlag);
	}

	@Override
	public DodopalResponse<MerGroupDepartmentDTO> findMerGroupDepartmentDTOById(String id) {
		MerGroupDepartmentFacade facade = getFacade(MerGroupDepartmentFacade.class, DelegateConstant.FACADE_USERS_URL);
		return facade.findMerGroupDepartmentDTOById(id);
	}

	@Override
	public DodopalResponse<Boolean> deleteMerGroupDepartmentDTO(List<String> ids) {
		MerGroupDepartmentFacade facade = getFacade(MerGroupDepartmentFacade.class, DelegateConstant.FACADE_USERS_URL);
		return facade.deleteMerGroupDepartmentDTO(ids);
	}

	@Override
	public DodopalResponse<Boolean> updateMerGroupDepartmentDTO(MerGroupDepartmentDTO departmentDTO) {
		MerGroupDepartmentFacade facade = getFacade(MerGroupDepartmentFacade.class, DelegateConstant.FACADE_USERS_URL);
		return facade.updateMerGroupDepartmentDTO(departmentDTO);
	}

	@Override
	public DodopalResponse<Boolean> startOrStopMerGroupDepartmentDTO(ActivateEnum startOrStop, List<String> idList) {
		MerGroupDepartmentFacade facade = getFacade(MerGroupDepartmentFacade.class, DelegateConstant.FACADE_USERS_URL);
		return facade.startOrStopMerGroupDepartmentDTO(startOrStop, idList);
	}

	@Override
	public DodopalResponse<MerGroupDepartmentDTO> saveMerGroupDepartmentDTO(MerGroupDepartmentDTO departmentDTO) {
		MerGroupDepartmentFacade facade = getFacade(MerGroupDepartmentFacade.class, DelegateConstant.FACADE_USERS_URL);
		return facade.saveMerGroupDepartmentDTO(departmentDTO);
	}

	@Override
	public DodopalResponse<Boolean> checkMerGroupDepartmentDTO(String mercode, String deptName,String id) {
		MerGroupDepartmentFacade facade = getFacade(MerGroupDepartmentFacade.class, DelegateConstant.FACADE_USERS_URL);
		return facade.checkMerGroupDepartmentDTO(mercode, deptName,id);
	}

}
