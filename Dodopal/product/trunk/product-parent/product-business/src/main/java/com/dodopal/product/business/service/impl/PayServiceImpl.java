package com.dodopal.product.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.dodopal.api.payment.dto.PayTranDTO;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.payment.dto.PayWayDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.bean.PayWayBean;
import com.dodopal.product.business.service.PayService;
import com.dodopal.product.delegate.PayDelegate;

@Service
public class PayServiceImpl implements PayService{
	 private final static Logger log = LoggerFactory.getLogger(PayServiceImpl.class);
	
	@Autowired
	PayDelegate payDelegate;

	@Override
	public DodopalResponse<List<PayWayBean>> findPayWay(boolean ext,String merCode) {
		DodopalResponse<List<PayWayBean>> dodopalResponse = new DodopalResponse<List<PayWayBean>>();
		try{
			DodopalResponse<List<PayWayDTO>> dtoResp = payDelegate.findPayWay(ext, merCode, "");
			if(ResponseCode.SUCCESS.equals(dtoResp.getCode())){
				if(CollectionUtils.isNotEmpty(dtoResp.getResponseEntity())){
					List<PayWayBean> beanList = new ArrayList<PayWayBean>();
					for(PayWayDTO tempDTO:dtoResp.getResponseEntity()){
						PayWayBean bean = new PayWayBean();
						PropertyUtils.copyProperties(bean,tempDTO);
						beanList.add(bean);
					}
					dodopalResponse.setResponseEntity(beanList);
				}
			}
			dodopalResponse.setCode(dtoResp.getCode());
		}catch (HessianRuntimeException e){
			log.error("PayServiceImpl's findPayWay call an error",e);
			dodopalResponse.setCode(ResponseCode.PRODUCT_CALL_PAYMENT_ERROR);
		}catch (Exception e){
			log.error("PayServiceImpl's findPayWay call an error",e);
			dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return dodopalResponse;
	}

	@Override
	public DodopalResponse<List<PayWayBean>> findCommonPayWay(boolean ext,String userCode) {
		DodopalResponse<List<PayWayBean>> dodopalResponse = new DodopalResponse<List<PayWayBean>>();
		try{
			DodopalResponse<List<PayWayDTO>> dtoResp = payDelegate.findCommonPayWay(ext, userCode);
			if(ResponseCode.SUCCESS.equals(dtoResp.getCode())){
				if(CollectionUtils.isNotEmpty(dtoResp.getResponseEntity())){
					List<PayWayBean> beanList = new ArrayList<PayWayBean>();
					for(PayWayDTO tempDTO:dtoResp.getResponseEntity()){
						PayWayBean bean = new PayWayBean();
						PropertyUtils.copyProperties(bean,tempDTO);
						beanList.add(bean);
					}
					dodopalResponse.setResponseEntity(beanList);
				}
			}
			dodopalResponse.setCode(dtoResp.getCode());
		}catch (HessianRuntimeException e){
			log.error("PayServiceImpl's findPayWay call an error",e);
			dodopalResponse.setCode(ResponseCode.PRODUCT_CALL_PAYMENT_ERROR);
		}catch (Exception e){
			log.error("PayServiceImpl's findPayWay call an error",e);
			dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return dodopalResponse;
	}
	/**
	 * @description 手机支付账户充值功能
	 * @param payTranDTO
	 * @return
	 */
	@Override
	public DodopalResponse<String> mobilePay(PayTranDTO payTranDTO) {
		return payDelegate.mobilePay(payTranDTO);
	}
}
