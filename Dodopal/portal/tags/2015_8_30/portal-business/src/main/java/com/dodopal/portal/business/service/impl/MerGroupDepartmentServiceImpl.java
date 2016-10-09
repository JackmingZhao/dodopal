package com.dodopal.portal.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerGroupDepartmentDTO;
import com.dodopal.api.users.dto.query.MerGroupDepartmentQueryDTO;
import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerGroupDepartmentBean;
import com.dodopal.portal.business.bean.MerGroupDepartmentQueryBean;
import com.dodopal.portal.business.constant.PortalConstants;
import com.dodopal.portal.business.service.MerGroupDepartmentService;
import com.dodopal.portal.delegate.MerGroupDepartmentDelegate;

@Service("merGroupDepartmentService")
public class MerGroupDepartmentServiceImpl implements MerGroupDepartmentService {

	@Autowired
	private MerGroupDepartmentDelegate delegate;

	@Override
	public DodopalResponse<List<MerGroupDepartmentBean>> findMerGroupDepartmentDTOList(MerGroupDepartmentBean bean) {
		MerGroupDepartmentDTO dto = convertBean2DTO(bean);

		DodopalResponse<List<MerGroupDepartmentDTO>> rep = delegate.findMerGroupDepartmentDTOList(dto, PortalConstants.FIND_WEB);
		DodopalResponse<List<MerGroupDepartmentBean>> response = new DodopalResponse<List<MerGroupDepartmentBean>>();

		if (ResponseCode.SUCCESS.equals(rep.getCode()) && rep.getResponseEntity() != null) {
			List<MerGroupDepartmentBean> beans = new ArrayList<MerGroupDepartmentBean>();

			for (MerGroupDepartmentDTO d : rep.getResponseEntity()) {
				MerGroupDepartmentBean b = convertDTO2Bean(d);
				beans.add(b);
			}
			response.setResponseEntity(beans);
			response.setCode(rep.getCode());
			return response;
		} else {
			response.setCode(rep.getCode());
			response.setMessage(rep.getMessage());
			return response;
		}

	}

	@Override
	public DodopalResponse<DodopalDataPage<MerGroupDepartmentBean>> findMerGroupDepartmentDTOListByPage(MerGroupDepartmentQueryBean queryBean, String fromFlag) {

		MerGroupDepartmentQueryDTO dto = new MerGroupDepartmentQueryDTO();
		dto.setActivate(queryBean.getActivate());
		dto.setDepName(queryBean.getDepName());
		dto.setMerCode(queryBean.getMerCode());
		dto.setPage(queryBean.getPage());
		dto.setRemark(queryBean.getRemark());

		DodopalResponse<DodopalDataPage<MerGroupDepartmentDTO>> rep = delegate.findMerGroupDepartmentDTOListByPage(dto, PortalConstants.FIND_WEB);

		DodopalResponse<DodopalDataPage<MerGroupDepartmentBean>> response = new DodopalResponse<DodopalDataPage<MerGroupDepartmentBean>>();
		List<MerGroupDepartmentBean> beans = new ArrayList<MerGroupDepartmentBean>();

		if (ResponseCode.SUCCESS.equals(rep.getCode())) {
			for (MerGroupDepartmentDTO d : rep.getResponseEntity().getRecords()) {
				MerGroupDepartmentBean b = convertDTO2Bean(d);
				beans.add(b);
			}

			PageParameter pp = new PageParameter();
			pp.setPageNo(rep.getResponseEntity().getPageNo());
			pp.setRows(rep.getResponseEntity().getRows());
			pp.setTotalPages(rep.getResponseEntity().getTotalPages());
			pp.setPageSize(rep.getResponseEntity().getPageSize());

			DodopalDataPage<MerGroupDepartmentBean> dataPage = new DodopalDataPage<MerGroupDepartmentBean>(pp, beans);
			dataPage.setRecords(beans);
			response.setCode(rep.getCode());
			response.setResponseEntity(dataPage);
		} else {
			response.setCode(rep.getCode());
			response.setMessage(rep.getMessage());
			return response;
		}

		return response;
	}

	private MerGroupDepartmentDTO convertBean2DTO(MerGroupDepartmentBean bean) {
		MerGroupDepartmentDTO dto = new MerGroupDepartmentDTO();
		dto.setActivate(bean.getActivate());
		dto.setCreateDate(bean.getCreateDate());
		dto.setCreateUser(bean.getCreateUser());
		dto.setDepName(bean.getDepName());
		dto.setId(bean.getId());
		dto.setRemark(bean.getRemark());
		dto.setMerCode(bean.getMerCode());
		dto.setUpdateUser(bean.getUpdateUser());
		dto.setUpdateDate(bean.getUpdateDate());
		return dto;
	}

	private MerGroupDepartmentBean convertDTO2Bean(MerGroupDepartmentDTO d) {
		MerGroupDepartmentBean b = new MerGroupDepartmentBean();
		b.setActivate(d.getActivate());
		b.setCreateDate(d.getCreateDate());
		b.setCreateUser(d.getCreateUser());
		b.setDepName(d.getDepName());
		b.setId(d.getId());
		b.setRemark(d.getRemark());
		b.setMerCode(d.getMerCode());
		b.setUpdateUser(d.getUpdateUser());
		b.setUpdateDate(d.getUpdateDate());
		return b;
	}

	@Override
	public DodopalResponse<MerGroupDepartmentBean> findMerGroupDepartmentById(String id) {
		DodopalResponse<MerGroupDepartmentDTO> rep = delegate.findMerGroupDepartmentDTOById(id);
		MerGroupDepartmentDTO dto = rep.getResponseEntity();
		MerGroupDepartmentBean bean = convertDTO2Bean(dto);

		DodopalResponse<MerGroupDepartmentBean> resp = new DodopalResponse<MerGroupDepartmentBean>();
		if (ResponseCode.SUCCESS.equals(rep.getCode())) {
			resp.setCode(rep.getCode());
			resp.setResponseEntity(bean);
		} else {
			resp.setCode(rep.getCode());
			resp.setMessage(rep.getMessage());
		}

		return resp;
	}

	@Override
	public DodopalResponse<String> deleteMerGroupDepartment(List<String> ids) {
		DodopalResponse<Boolean> rep = delegate.deleteMerGroupDepartmentDTO(ids);
		DodopalResponse<String> response = new DodopalResponse<String>();

		if (ResponseCode.SUCCESS.equals(rep.getCode())) {
			response.setResponseEntity(CommonConstants.SUCCESS);
			response.setCode(ResponseCode.SUCCESS);
		} else {
			response.setResponseEntity(CommonConstants.FAILURE);
			response.setCode(rep.getCode());
			response.setMessage(rep.getMessage());
		}
		return response;
	}

	@Override
	public DodopalResponse<String> updateMerGroupDepartment(MerGroupDepartmentBean bean) {
		DodopalResponse<Boolean> rep = delegate.updateMerGroupDepartmentDTO(convertBean2DTO(bean));
		DodopalResponse<String> response = new DodopalResponse<String>();

		if (ResponseCode.SUCCESS.equals(rep.getCode())) {
			response.setResponseEntity(CommonConstants.SUCCESS);
			response.setCode(ResponseCode.SUCCESS);
		} else {
			response.setResponseEntity(CommonConstants.FAILURE);
			response.setCode(rep.getCode());
			response.setMessage(rep.getMessage());
		}

		return response;
	}

	@Override
	public DodopalResponse<String> saveMerGroupDepartmentDTOList(MerGroupDepartmentBean bean) {
		DodopalResponse<MerGroupDepartmentDTO> rep = delegate.saveMerGroupDepartmentDTO(convertBean2DTO(bean));
		DodopalResponse<String> response = new DodopalResponse<String>();

		if (ResponseCode.SUCCESS.equals(rep.getCode())) {
			response.setResponseEntity(CommonConstants.SUCCESS);
			response.setCode(ResponseCode.SUCCESS);
		} else {
			response.setResponseEntity(CommonConstants.FAILURE);
			response.setCode(rep.getCode());
			response.setMessage(rep.getMessage());
		}
		return response;
	}

	@Override
	public DodopalResponse<Boolean> checkMerGroupDepartmentDTO(MerGroupDepartmentBean bean) {
		return delegate.checkMerGroupDepartmentDTO(bean.getMerCode(), bean.getDepName(),bean.getId());
	}

}
