package com.dodopal.api.payment.facade;

import com.dodopal.api.payment.dto.CreateTranDTO;
import com.dodopal.common.model.DodopalResponse;

/**
 * 退款接口
 * @author 
 *
 */
public interface PayRetundFacade {

	//
	/**
	 * 退款接口
	 * @param paymentTranNo 原退款流水号
	 * @param source    来源    
	 * @param operId    操作员ID
	 * @return
	 */
	DodopalResponse<String> sendRefund(String paymentTranNo,String source,String operId);	
	
    /** 
     * @author  Dingkuiyuan@dodopal.com 
     * @date 创建时间：2016年4月12日 下午5:40:52 
     * @version 1.0 
     * @parameter  
     * @描述 生成交易流水
     * @return  交易流水号
     */
   DodopalResponse<String> createPayTraTransaction(CreateTranDTO dto);
}
