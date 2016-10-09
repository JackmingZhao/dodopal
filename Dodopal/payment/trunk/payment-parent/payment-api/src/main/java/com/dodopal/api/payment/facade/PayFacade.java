package com.dodopal.api.payment.facade;

import java.util.List;

import com.dodopal.api.payment.dto.CreateTranDTO;
import com.dodopal.api.payment.dto.PayTraTransactionDTO;
import com.dodopal.api.payment.dto.PayTranDTO;
import com.dodopal.api.payment.dto.PayWayDTO;
import com.dodopal.common.model.DodopalResponse;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年8月13日 下午1:04:38
 */
public interface PayFacade {
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findCommonPayWay 
     * @Description: 支付方式查询     
     *                用户购买产品，确认下单选择支付渠道。产品库取得支付方式。
     * @param ext  = true 的时候    merCode不能为空 [ True ：外接商户 False：非外接商户]
     * @param merCode（第一参数：商户号；第二参数：支付类型编码）   
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
     * @author dingkuiyuan@dodopal.com
     * @Title: toFreezeCapital 
     * @Description: 资金冻结，生成交易支付流水
     * @param transactionDTO
     * @return    设定文件 
     * DodopalResponse<Boolean>    返回类型 
     * @throws 
     */
    DodopalResponse<Boolean> freezeAccountAmt(PayTranDTO transactionDTO);
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: unfreezeAccountAmt 
     * @Description: 资金解冻
     * @param transactionDTO
     * @return    设定文件 
     * DodopalResponse<Boolean>    返回类型 
     * @throws 
     */
    DodopalResponse<Boolean> unfreezeAccountAmt(PayTranDTO transactionDTO);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: deductAccountAmt 
     * @Description: 根据圈存金额以及圈存费率，从账户中扣除相应的额度。
     * @param transactionDTO
     * @return    设定文件 
     * DodopalResponse<Boolean>    返回类型 
     * @throws 
     */
    DodopalResponse<Boolean> deductAccountAmt(PayTranDTO transactionDTO);
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2015年12月29日 下午12:21:13 
      * @version 1.0 
      * @parameter  
      * @since  
      * @return  
      */
    DodopalResponse<Boolean>  loadOrderDeductAccountAmt(PayTranDTO transactionDTO);
    
    /**
     *  手机端支付
     * @param transactionDTO
     * @return
     */
    DodopalResponse<String> mobilepay(PayTranDTO transactionDTO);
    
    
    /**
     * 自动转账：只适用于连锁商户下的直营网点进行此操作。
     * @param merCode:自动转账的商户编号
     * @return
     */
    DodopalResponse<Boolean> autoTransfer(String merCode);
    

}
