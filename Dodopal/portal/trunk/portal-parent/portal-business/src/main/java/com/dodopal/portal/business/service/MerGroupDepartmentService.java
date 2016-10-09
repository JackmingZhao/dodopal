package com.dodopal.portal.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerGroupDepartmentBean;
import com.dodopal.portal.business.bean.MerGroupDepartmentQueryBean;

public interface MerGroupDepartmentService {

	DodopalResponse<List<MerGroupDepartmentBean>> findMerGroupDepartmentDTOList(MerGroupDepartmentBean bean);
	
	DodopalResponse<DodopalDataPage<MerGroupDepartmentBean>> findMerGroupDepartmentDTOListByPage(MerGroupDepartmentQueryBean departmentBean,String fromFlag);
	
	DodopalResponse<String> saveMerGroupDepartmentDTOList(MerGroupDepartmentBean merDep);
	
	DodopalResponse<MerGroupDepartmentBean> findMerGroupDepartmentById(String Id);
	
	DodopalResponse<String> deleteMerGroupDepartment(List<String> id);
	
	DodopalResponse<String> updateMerGroupDepartment(MerGroupDepartmentBean bean);
	
	DodopalResponse<Boolean> checkMerGroupDepartmentDTO(MerGroupDepartmentBean bean);
	
}
