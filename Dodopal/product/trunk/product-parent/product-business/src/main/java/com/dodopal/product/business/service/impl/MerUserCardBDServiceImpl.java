package com.dodopal.product.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.users.dto.MerUserCardBDDTO;
import com.dodopal.api.users.dto.MerUserCardBDFindDTO;
import com.dodopal.api.users.dto.UserCardRecordDTO;
import com.dodopal.api.users.facade.UserCardRecordQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.bean.MerUserCardBDBean;
import com.dodopal.product.business.bean.MerUserCardBDFindBean;
import com.dodopal.product.business.service.MerUserCardBDService;
import com.dodopal.product.delegate.MerUserCardBDDelegate;
@Service
public class MerUserCardBDServiceImpl implements MerUserCardBDService{
	@Autowired
	MerUserCardBDDelegate bdDelegate;
	private final static Logger log = LoggerFactory.getLogger(MerUserCardBDServiceImpl.class);
   
	@Override
	public DodopalResponse<List<MerUserCardBDBean>> findMerUserCardBD(
			MerUserCardBDFindBean merUserCardBDBean) {
		DodopalResponse<List<MerUserCardBDBean>> response = new DodopalResponse<List<MerUserCardBDBean>>();
		MerUserCardBDFindDTO bdFindDTO = new MerUserCardBDFindDTO();
		try {
			PropertyUtils.copyProperties(bdFindDTO, merUserCardBDBean);
			List<MerUserCardBDBean> bdBeans = new ArrayList<MerUserCardBDBean>();
			DodopalResponse<List<MerUserCardBDDTO>> bdList = bdDelegate.findMerUserCardBD(bdFindDTO);
			if(ResponseCode.SUCCESS.equals(bdList.getCode())){
				for(MerUserCardBDDTO tempBean:bdList.getResponseEntity()){
					MerUserCardBDBean bdBean = new MerUserCardBDBean();
					PropertyUtils.copyProperties(bdBean, tempBean);
					bdBeans.add(bdBean);
				}
				response.setResponseEntity(bdBeans);
			}
			response.setCode(bdList.getCode());
		} catch(HessianRuntimeException e){
			  response.setCode(ResponseCode.PRODUCT_CALL_USERS_ERROR);
	          log.error("MerUserCardBDServiceImpl findMerUserCardBD call an error:  ",e);
		}catch (Exception e) {
			response.setCode(ResponseCode.SYSTEM_ERROR);
            log.error("MerUserCardBDServiceImpl findMerUserCardBD call an error:  ",e);
		}
		return response;
	}

	@Override
	public DodopalResponse<MerUserCardBDBean> saveMerUserCardBD(
			MerUserCardBDBean cardBDBean) {
		DodopalResponse<MerUserCardBDBean> response = new DodopalResponse<MerUserCardBDBean>();
		MerUserCardBDDTO bdDTO = new MerUserCardBDDTO();
		try {
			PropertyUtils.copyProperties(bdDTO, cardBDBean);
			DodopalResponse<MerUserCardBDDTO> dtoRes = bdDelegate.saveMerUserCardBD(bdDTO);
//			if(ResponseCode.SUCCESS.equals(dtoRes.getCode())){
//				MerUserCardBDBean bdBean = new MerUserCardBDBean();
//				PropertyUtils.copyProperties(bdBean, dtoRes.getResponseEntity());
//				response.setResponseEntity(bdBean);
//			}
			response.setCode(dtoRes.getCode());
		}catch(HessianRuntimeException e){
			  response.setCode(ResponseCode.PRODUCT_CALL_USERS_ERROR);
	          log.error("MerUserCardBDServiceImpl findMerUserCardBD call an error:  ",e);
		}catch (Exception e) {
			response.setCode(ResponseCode.SYSTEM_ERROR);
            log.error("MerUserCardBDServiceImpl findMerUserCardBD call an error:  ",e);
		}
		return response;
	}

	@Override
	public DodopalResponse<DodopalDataPage<UserCardRecordDTO>> findUserCardRecordByPage(UserCardRecordQueryDTO query) {
		// TODO 查询个人卡片充值和消费信息(手机端)
		return null;
	}

}
