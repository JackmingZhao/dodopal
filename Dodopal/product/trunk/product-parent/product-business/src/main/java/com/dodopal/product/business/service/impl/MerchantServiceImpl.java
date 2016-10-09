package com.dodopal.product.business.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.users.dto.MerBusRateDTO;
import com.dodopal.api.users.dto.MerRateSupplementDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.bean.MerBusRateBean;
import com.dodopal.product.business.bean.MerRateSupplementBean;
import com.dodopal.product.business.bean.MerchantBean;
import com.dodopal.product.business.service.MerchantService;
import com.dodopal.product.delegate.MerchantDelegate;

@Service
public class MerchantServiceImpl implements MerchantService {
	private final static Logger log = LoggerFactory
			.getLogger(MerchantServiceImpl.class);
	@Autowired
	MerchantDelegate merchantDelegate;

	@Override
	public DodopalResponse<List<Area>> findMerCitys(String merCode) {
		DodopalResponse<List<Area>> dodopal = new DodopalResponse<List<Area>>();
		try {
			dodopal = merchantDelegate.findMerCitys(merCode);
		} catch (HessianRuntimeException e) {
			log.error("MerchantServiceImpl's findMerCitys call an error", e);
			dodopal.setCode(ResponseCode.PRODUCT_CALL_USERS_ERROR);
		}
		return dodopal;
	}
	@Override
	public DodopalResponse<List<MerBusRateBean>> findMerBusRateByMerCode(
			String merCode) {
		DodopalResponse<List<MerBusRateBean>> dodopalResponse = new DodopalResponse<List<MerBusRateBean>>();
		try {
			DodopalResponse<List<MerBusRateDTO>> dtoResponse = merchantDelegate
					.findMerBusRateByMerCode(merCode);
			List<MerBusRateBean> rateBeanList = new ArrayList<MerBusRateBean>();
			if (CollectionUtils.isNotEmpty(dtoResponse.getResponseEntity())) {
				for (MerBusRateDTO tempDTO : dtoResponse.getResponseEntity()) {
					MerBusRateBean bean = new MerBusRateBean();
					PropertyUtils.copyProperties(bean, tempDTO);
					rateBeanList.add(bean);
				}
			}
			dodopalResponse.setCode(dtoResponse.getCode());
			dodopalResponse.setResponseEntity(rateBeanList);
		} catch (HessianRuntimeException e) {
			log.error("MerchantServiceImpl's findMerCitys call an error", e);
			dodopalResponse.setCode(ResponseCode.PRODUCT_CALL_USERS_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
		}

		return dodopalResponse;
	}
	@Override
	public DodopalResponse<MerchantBean> findMerchantByCode(String merCode) {
		DodopalResponse<MerchantBean> response = new DodopalResponse<MerchantBean>();
		try{
			MerchantBean merBean = new MerchantBean();
			DodopalResponse<MerchantDTO> dtoResponse = merchantDelegate.findMerchantByCode(merCode);
			if(null!=dtoResponse.getResponseEntity()&&ResponseCode.SUCCESS.equals(dtoResponse.getCode())){
				PropertyUtils.copyProperties(merBean, dtoResponse.getResponseEntity());
			}
			response.setCode(dtoResponse.getCode());
			response.setResponseEntity(merBean);
		} catch (HessianRuntimeException e) {
			log.error("MerchantServiceImpl's findMerchantByCode call an error", e);
			response.setCode(ResponseCode.PRODUCT_CALL_USERS_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}
	@Override
	public DodopalResponse<List<Area>> findMerCitysPayment(String merCode) {
		DodopalResponse<List<Area>> dodopal = new DodopalResponse<List<Area>>();
		try {
			dodopal = merchantDelegate.findMerCitysPayment(merCode);
		} catch (HessianRuntimeException e) {
			log.error("MerchantServiceImpl's findMerCitys call an error", e);
			dodopal.setCode(ResponseCode.PRODUCT_CALL_USERS_ERROR);
		}
		return dodopal;
	}
	@Override
	public DodopalResponse<MerRateSupplementBean> findMerRateSupplementByMerCode(
			String merCode, String bussinessType) {
		DodopalResponse<MerRateSupplementBean> dodopal = new DodopalResponse<MerRateSupplementBean>();
		DodopalResponse<List<MerRateSupplementDTO>> dodopalDTORes = merchantDelegate.findMerRateSupplementsByMerCode(merCode);
		try {
			if(ResponseCode.SUCCESS.equals(dodopalDTORes.getCode())){
				if(CollectionUtils.isNotEmpty(dodopalDTORes.getResponseEntity())){
					for(MerRateSupplementDTO dto:dodopalDTORes.getResponseEntity()){
						if(bussinessType.equals(dto.getRateCode())){
							MerRateSupplementBean bean = new MerRateSupplementBean();
								PropertyUtils.copyProperties(bean, dto);
								dodopal.setResponseEntity(bean);
								break;
						}
					}
				}
			}
			dodopal.setCode(dodopalDTORes.getCode());
		} catch (IllegalAccessException
				| InvocationTargetException
				| NoSuchMethodException e) {
			log.error("MerchantServiceImpl's findMerRateSupplementByMerCode call an error", e);
			dodopal.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return dodopal;
	}
	@Override
	public DodopalResponse<Map<String, String>> validateMerchantForIcdcRecharge(
			String merchantNum, String userId, String posId,
			String providerCode, String source) {
		return merchantDelegate.validateMerchantForIcdcRecharge(merchantNum, userId, posId, providerCode, source);
	}

	@Override
	public DodopalResponse<MerchantDTO> findMerchantInfoByMerCode(String merCode) {
		DodopalResponse<MerchantDTO> response = null;
		try {
			response = merchantDelegate.findMerchantByCode(merCode);
		} catch (HessianRuntimeException e) {
			log.error("MerchantServiceImpl's findMerCitys call an error", e);
			response = new DodopalResponse<MerchantDTO>();
			response.setCode(ResponseCode.PRODUCT_CALL_USERS_ERROR);
		}
		return response;
	}

}
