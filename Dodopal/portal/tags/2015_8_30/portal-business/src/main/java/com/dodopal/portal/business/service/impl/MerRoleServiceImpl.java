package com.dodopal.portal.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerFunctionInfoDTO;
import com.dodopal.api.users.dto.MerRoleDTO;
import com.dodopal.api.users.dto.query.MerRoleQueryDTO;
import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerFunctionInfoBean;
import com.dodopal.portal.business.bean.MerRoleBean;
import com.dodopal.portal.business.bean.MerRoleQueryBean;
import com.dodopal.portal.business.service.MerRoleService;
import com.dodopal.portal.delegate.MerRoleDelegate;

@Service("merRoleServiceImpl")
public class MerRoleServiceImpl implements MerRoleService {

	@Autowired
	private MerRoleDelegate delegate;

	@Override
	public DodopalResponse<DodopalDataPage<MerRoleBean>> findMerRoleByPage(MerRoleQueryBean bean) {
		MerRoleQueryDTO qto = new MerRoleQueryDTO();
		qto.setMerCode(bean.getMerCode());
		qto.setMerRoleName(bean.getMerRoleName());
		qto.setLoginUserId(bean.getLoginUserId());
		qto.setPage(bean.getPage());

		DodopalResponse<DodopalDataPage<MerRoleDTO>> rep = delegate.findMerRoleByPage(qto);
		DodopalResponse<DodopalDataPage<MerRoleBean>> response = new DodopalResponse<DodopalDataPage<MerRoleBean>>();

		if (ResponseCode.SUCCESS.equals(rep.getCode())) {
			List<MerRoleBean> beans = new ArrayList<MerRoleBean>();

			if (rep.getResponseEntity() != null) {
				PageParameter page = new PageParameter(rep.getResponseEntity().getPageNo(), rep.getResponseEntity().getPageSize());

				for (MerRoleDTO dto : rep.getResponseEntity().getRecords()) {
					MerRoleBean b = new MerRoleBean();
					try {
						BeanUtils.copyProperties(b, dto);
					}
					catch (Exception e) {
						e.printStackTrace();
						response.setMessage(e.getMessage());
						response.setCode(ResponseCode.SYSTEM_ERROR);
						return response;
					}

					beans.add(b);
					DodopalDataPage<MerRoleBean> dataPage = new DodopalDataPage<MerRoleBean>(page, beans);
					dataPage.setRows(rep.getResponseEntity().getRows());
					dataPage.setTotalPages(rep.getResponseEntity().getTotalPages());
					response.setResponseEntity(dataPage);
				}
			}

			response.setCode(rep.getCode());
		} else {
			response.setCode(rep.getCode());
			response.setMessage(rep.getMessage());
		}

		return response;
	}

	@Override
	public DodopalResponse<MerRoleBean> findMerRoleByMerRoleCode(String merCode, String merRoleCode) {
		DodopalResponse<MerRoleDTO> rep = delegate.findMerRoleByMerRoleCode(merCode, merRoleCode);
		DodopalResponse<MerRoleBean> response = new DodopalResponse<MerRoleBean>();

		if (ResponseCode.SUCCESS.equals(rep.getCode())) {
			response.setCode(rep.getCode());
			MerRoleBean bean = new MerRoleBean();
			try {
				if (rep.getResponseEntity() != null) {
					BeanUtils.copyProperties(bean, rep.getResponseEntity());
					List<MerFunctionInfoBean> funBeans = new ArrayList<MerFunctionInfoBean>();
					List<MerFunctionInfoDTO> funs = rep.getResponseEntity().getMerRoleFunDTOList();
					if (CollectionUtils.isNotEmpty(funs)) {
						for (MerFunctionInfoDTO dto : funs) {
							MerFunctionInfoBean b = new MerFunctionInfoBean();
							BeanUtils.copyProperties(b, dto);
							funBeans.add(b);
						}
					}
					bean.setMerRoleFunDTOList(funBeans);
				}

				response.setResponseEntity(bean);
			}
			catch (Exception e) {
				e.printStackTrace();
				response.setMessage(e.getMessage());
				response.setCode(ResponseCode.SYSTEM_ERROR);
				return response;
			}

		} else {
			response.setCode(rep.getCode());
			response.setMessage(rep.getMessage());
		}

		return response;
	}

	@Override
	public DodopalResponse<String> addMerRole(MerRoleBean merRoleDTO) {
		MerRoleDTO dto = new MerRoleDTO();
		DodopalResponse<String> response = new DodopalResponse<String>();
		try {
			BeanUtils.copyProperties(dto, merRoleDTO);
			List<MerFunctionInfoBean> funBeans = merRoleDTO.getMerRoleFunDTOList();
			List<MerFunctionInfoDTO> funs = new ArrayList<MerFunctionInfoDTO>();
			if (CollectionUtils.isNotEmpty(funBeans)) {
				for (MerFunctionInfoBean b : funBeans) {
					MerFunctionInfoDTO fDto = new MerFunctionInfoDTO();
					BeanUtils.copyProperties(fDto, b);
					funs.add(fDto);
				}
			}
			dto.setMerRoleFunDTOList(funs);
		}
		catch (Exception e) {
			e.printStackTrace();
			response.setCode(ResponseCode.SYSTEM_ERROR);
			response.setResponseEntity(CommonConstants.FAILURE);
			return response;
		}

		DodopalResponse<Integer> rep = delegate.addMerRole(dto);
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
	public DodopalResponse<String> updateMerRole(MerRoleBean merRoleDTO) {
		MerRoleDTO dto = new MerRoleDTO();
		DodopalResponse<String> response = new DodopalResponse<String>();

		try {
			BeanUtils.copyProperties(dto, merRoleDTO);

			List<MerFunctionInfoBean> funBeans = merRoleDTO.getMerRoleFunDTOList();
			List<MerFunctionInfoDTO> funs = new ArrayList<MerFunctionInfoDTO>();
			if (CollectionUtils.isNotEmpty(funBeans)) {
				for (MerFunctionInfoBean b : funBeans) {
					MerFunctionInfoDTO fDto = new MerFunctionInfoDTO();
					BeanUtils.copyProperties(fDto, b);
					funs.add(fDto);
				}
			}
			dto.setMerRoleFunDTOList(funs);

		}
		catch (Exception e) {
			e.printStackTrace();
			response.setMessage(e.getMessage());
			response.setCode(ResponseCode.SYSTEM_ERROR);
			return response;
		}

		DodopalResponse<Integer> rep = delegate.updateMerRole(dto);

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
	public DodopalResponse<String> batchDelMerRoleByCodes(String merCode, List<String> merRoleCodes) {
		DodopalResponse<Integer> rep = delegate.batchDelMerRoleByCodes(merCode, merRoleCodes);
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
	public DodopalResponse<List<MerFunctionInfoBean>> findMerFuncInfoByUserCode(String userCode) {

		DodopalResponse<List<MerFunctionInfoDTO>> rep = delegate.findMerFuncInfoByUserCode(userCode);
		DodopalResponse<List<MerFunctionInfoBean>> response = new DodopalResponse<List<MerFunctionInfoBean>>();
		if (ResponseCode.SUCCESS.equals(rep.getCode())) {
			List<MerFunctionInfoBean> funs = new ArrayList<MerFunctionInfoBean>();
			if (CollectionUtils.isNotEmpty(rep.getResponseEntity())) {
				for (MerFunctionInfoDTO dto : rep.getResponseEntity()) {
					MerFunctionInfoBean f = new MerFunctionInfoBean();
					try {
						BeanUtils.copyProperties(f, dto);
					}
					catch (Exception e) {
						e.printStackTrace();
						response.setMessage(e.getMessage());
						response.setCode(ResponseCode.SYSTEM_ERROR);
						return response;
					}

					funs.add(f);
				}
			}
			response.setCode(rep.getCode());
			response.setResponseEntity(funs);
		} else {
			response.setCode(rep.getCode());
			response.setMessage(rep.getMessage());
		}

		return response;
	}

	@Override
	public DodopalResponse<Boolean> checkMerRoleNameExist(MerRoleBean bean) {
		return delegate.checkMerRoleNameExist(bean.getMerCode(), bean.getMerRoleName(), bean.getId());
	}

}
