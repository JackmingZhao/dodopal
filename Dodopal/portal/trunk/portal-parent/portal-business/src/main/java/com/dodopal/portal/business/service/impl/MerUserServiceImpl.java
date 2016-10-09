package com.dodopal.portal.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerRoleDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.api.users.dto.query.MerchantUserQueryDTO;
import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.UserClassifyEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.SysLog;
import com.dodopal.common.util.DDPOracleLog;
import com.dodopal.portal.business.bean.MerchantUserBean;
import com.dodopal.portal.business.bean.MerchantUserQueryBean;
import com.dodopal.portal.business.service.MerUserService;
import com.dodopal.portal.delegate.MerOperatorDelegate;

@Service("merUserServiceImpl")
public class MerUserServiceImpl implements MerUserService {

	@Autowired
	private MerOperatorDelegate delegate;

	private DDPOracleLog<MerUserServiceImpl> logger = new DDPOracleLog<>(MerUserServiceImpl.class);
	
	@Override
	public DodopalResponse<DodopalDataPage<MerchantUserBean>> findMerOperatorByPage(MerchantUserQueryBean bean) {
		MerchantUserQueryDTO dto = new MerchantUserQueryDTO();
		dto.setMerCode(bean.getMerCode());
		dto.setMerUserNickName(bean.getMerUserNickName());
		dto.setMerUserName(bean.getMerUserName());
		dto.setMerUserMobile(bean.getMerUserMobile());
		dto.setActivate(bean.getActivate());
		dto.setClassify(UserClassifyEnum.MERCHANT);
		dto.setLoginUserId(bean.getLoginUserId());
		dto.setPage(bean.getPage());

		DodopalResponse<DodopalDataPage<MerchantUserDTO>> rep = delegate.findMerOperatorByPage(dto);

		DodopalResponse<DodopalDataPage<MerchantUserBean>> response = new DodopalResponse<DodopalDataPage<MerchantUserBean>>();
		List<MerchantUserBean> beans = new ArrayList<MerchantUserBean>();

		if (ResponseCode.SUCCESS.equals(rep.getCode()) && rep.getResponseEntity() != null) {
			for (MerchantUserDTO d : rep.getResponseEntity().getRecords()) {
				MerchantUserBean b = new MerchantUserBean();

				try {
					PropertyUtils.copyProperties(b, d);
				}
				catch (Exception e) {
					e.printStackTrace();
					response.setMessage(e.getMessage());
					response.setCode(ResponseCode.SYSTEM_ERROR);
					return response;
				}
				beans.add(b);
			}

			PageParameter pp = new PageParameter();
			pp.setPageNo(rep.getResponseEntity().getPageNo());
			pp.setRows(rep.getResponseEntity().getRows());
			pp.setTotalPages(rep.getResponseEntity().getTotalPages());
			pp.setPageSize(rep.getResponseEntity().getPageSize());

			DodopalDataPage<MerchantUserBean> dataPage = new DodopalDataPage<MerchantUserBean>(pp, beans);
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

	@Override
	public DodopalResponse<MerchantUserBean> findMerOperatorByUserCode(String merCode, String userCode) {
		DodopalResponse<MerchantUserDTO> rep = delegate.findMerOperatorByUserCode(merCode, userCode);

		//#####################测试日志代码#############################
		SysLog log = new SysLog();
		log.setInParas("merCode:" + merCode + ",userCode:" + userCode);
		log.setOutParas("");
		log.setMethodName("findMerOperatorByUserCode");
		log.setRespCode("000000");
		log.setRespExplain("Rep Message");
		log.setServerName("Portal");
		log.setTranNum("TranNum");
		log.setOrderNum("OrderNum");
		logger.info(log);
		
		try{
			throw new RuntimeException("Error is occured!");
		}catch(Throwable e){
			logger.error(log,e);
		}
		//#####################测试日志代码#############################
		
		
		DodopalResponse<MerchantUserBean> response = new DodopalResponse<MerchantUserBean>();
		if (ResponseCode.SUCCESS.equals(rep.getCode())) {
			MerchantUserBean bean = new MerchantUserBean();
			try {
				PropertyUtils.copyProperties(bean, rep.getResponseEntity());
				
				List<MerRoleDTO> roles = rep.getResponseEntity().getMerUserRoleList();
				List<String> roleList = new ArrayList<String>();
				if(CollectionUtils.isNotEmpty(roles)){
    				for(MerRoleDTO d : roles){
    					roleList.add(d.getMerRoleCode());
    				}
				}
				bean.setMerUserRoleList(roleList);
			}
			catch (Exception e) {
				e.printStackTrace();
				response.setMessage(e.getMessage());
				response.setCode(ResponseCode.SYSTEM_ERROR);
				return response;
			}
			response.setResponseEntity(bean);
			response.setCode(ResponseCode.SUCCESS);
		} else {
			response.setCode(rep.getCode());
			response.setMessage(rep.getMessage());
		}
		
		return response;
	}

	@Override
	public DodopalResponse<String> addMerOperator(MerchantUserBean merchantUserDTO) {
		MerchantUserDTO dto = new MerchantUserDTO();
		DodopalResponse<String> response = new DodopalResponse<String>();
		try {
			PropertyUtils.copyProperties(dto, merchantUserDTO);

			ArrayList<MerRoleDTO> roleList = new ArrayList<MerRoleDTO>();
			List<String> roles = merchantUserDTO.getMerUserRoleList();
			if(CollectionUtils.isNotEmpty(roles)){
				for(String d : roles){
					MerRoleDTO role = new MerRoleDTO();
					role.setMerRoleCode(d);
					roleList.add(role);
				}
				dto.setMerUserRoleList(roleList);
			}
			
		}	catch (Exception e) {
			e.printStackTrace();
			response.setCode(ResponseCode.SYSTEM_ERROR);
			response.setResponseEntity(CommonConstants.FAILURE);
			return response;
		}

		DodopalResponse<Integer> rep = delegate.addMerOperator(dto);
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
	public DodopalResponse<String> updateMerOperator(MerchantUserBean merchantUserDTO) {
		MerchantUserDTO dto = new MerchantUserDTO();
		DodopalResponse<String> response = new DodopalResponse<String>();
		try {
//			PropertyUtils.copyProperties(dto, merchantUserDTO);
			PropertyUtils.copyProperties(dto, merchantUserDTO);
			ArrayList<MerRoleDTO> roleList = new ArrayList<MerRoleDTO>();
			List<String> roles = merchantUserDTO.getMerUserRoleList();
			if(CollectionUtils.isNotEmpty(roles)){
    			for(String d : roles){
    				MerRoleDTO role = new MerRoleDTO();
    				role.setMerRoleCode(d);
    				roleList.add(role);
    			}
    			dto.setMerUserRoleList(roleList);
			}
		}	catch (Exception e) {
			e.printStackTrace();
			response.setCode(ResponseCode.SYSTEM_ERROR);
			response.setResponseEntity(CommonConstants.FAILURE);
			return response;
		}

		DodopalResponse<Integer> rep = delegate.updateMerOperator(dto);
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
	public DodopalResponse<String> batchActivateMerOperator(String merCode, String updateUser, ActivateEnum activate, List<String> userCodes) {
		DodopalResponse<Integer> rep = delegate.batchActivateMerOperator(merCode, updateUser, activate, userCodes);
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
	public DodopalResponse<String> configMerOperatorRole(MerchantUserBean merchantUserDTO) {
		MerchantUserDTO dto = new MerchantUserDTO();
		DodopalResponse<String> response = new DodopalResponse<String>();
		try {
			PropertyUtils.copyProperties(dto, merchantUserDTO);

			ArrayList<MerRoleDTO> roleList = new ArrayList<MerRoleDTO>();
			List<String> roles = merchantUserDTO.getMerUserRoleList();
			for(String d : roles){
				MerRoleDTO role = new MerRoleDTO();
				role.setMerRoleCode(d);
				roleList.add(role);
			}
			dto.setMerUserRoleList(roleList);
		}	catch (Exception e) {
			e.printStackTrace();
			response.setCode(ResponseCode.SYSTEM_ERROR);
			response.setResponseEntity(CommonConstants.FAILURE);
			return response;
		}

		DodopalResponse<Integer> rep = delegate.configMerOperatorRole(dto);
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
	public DodopalResponse<String> resetOperatorPwd(String merCode, String id, String password, String updateUser) {
		DodopalResponse<Boolean> rep = delegate.resetOperatorPwd(merCode, id, password, updateUser);
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

}
