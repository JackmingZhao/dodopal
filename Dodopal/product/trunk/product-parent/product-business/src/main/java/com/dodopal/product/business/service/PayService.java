package com.dodopal.product.business.service;

import java.util.List;

import com.dodopal.api.payment.dto.PayTranDTO;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.bean.PayWayBean;

public interface PayService {
	/**
	 * 查询支付方式
	 * ext  = true 的时候    merCode不能为空 [ True ：外接商户 False：非外接商户]
	 */
	public DodopalResponse<List<PayWayBean>> findPayWay(boolean ext,String merCode); 
	/**
	 * 查询常用支付方式
	 * ext  = true 的时候    merCode不能为空 [ True ：外接商户 False：非外接商户]
	 */
	public DodopalResponse<List<PayWayBean>> findCommonPayWay(boolean ext,String userCode);
	/**
	 * @description 手机支付账户充值功能
	 * @param payTranDTO
	 * @return
	 */
	public DodopalResponse<String> mobilePay(PayTranDTO payTranDTO);
}
