package com.dodopal.product.delegate;

import java.util.List;

import com.dodopal.api.payment.dto.PayTranDTO;
import com.dodopal.api.payment.dto.PayWayDTO;
import com.dodopal.common.model.DodopalResponse;

public interface PayDelegate {
	  /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findCommonPayWay 
     * @Description: 支付方式查询     
     *                用户购买产品，确认下单选择支付渠道。产品库取得支付方式。
     * @param ext  = true 的时候    merCode不能为空 [ True ：外接商户 False：非外接商户]
     * @param merCode   （第一参数：商户号；第二参数：支付类型编码）  
     * @return    设定文件 
     * DodopalResponse<List<PayWayDTO>>    返回类型 
     * @throws 
     */
    DodopalResponse<List<PayWayDTO>> findPayWay(boolean ext,String ...merCode);

    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findCommonPayWay 
     * @Description: 用户常用支付方式。
     *                用户购买产品，确认下单选择支付渠道。产品库取得支付方式。
     * @param ext  = true 的时候    userCode不能为空 [ True ：外接商户 False：非外接商户]
     * @param userCode
     * @return    设定文件 
     * DodopalResponse<List<PayWayDTO>>    返回类型 
     * @throws 
     */
    DodopalResponse<List<PayWayDTO>> findCommonPayWay(boolean ext,String userCode);
    
    /**
     * @description 手机支付账户充值功能
     * @param payTranDTO
     * @return
     */
    DodopalResponse<String> mobilePay(PayTranDTO payTranDTO);
  
    /** 
     * @author  Dingkuiyuan@dodopal.com 
     * @date 创建时间：2015年12月29日 下午12:21:13 
     * @version 1.0 
     * @parameter  
     * @since  
     * @return  
     */
    DodopalResponse<Boolean> loadOrderDeductAccountAmt(PayTranDTO payTranDTO);
    
}
