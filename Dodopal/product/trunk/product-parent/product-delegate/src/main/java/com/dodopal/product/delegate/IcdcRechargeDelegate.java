package com.dodopal.product.delegate;

import java.util.Map;

import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.api.payment.dto.PayAwayDTO;
import com.dodopal.api.payment.dto.PayServiceRateDTO;
import com.dodopal.api.payment.dto.PayTranDTO;
import com.dodopal.common.model.DodopalResponse;

public interface IcdcRechargeDelegate {

    /****************************************** 卡系统相关接口 start ********************************************/
    /**
     * 卡服务：充值验卡接口
     * @param crdDTO
     * @return
     */
    public DodopalResponse<CrdSysOrderDTO > queryCheckCardFun(CrdSysOrderDTO crdDTO);
    
    /**
     * 卡服务：生单接口
     * @param crdDTO
     * @return
     */
    public DodopalResponse<CrdSysOrderDTO> createCrdOrderCardFun(CrdSysOrderDTO crdDTO);
    
    /**
     * 卡服务：充值申请接口
     * @param crdDTO
     * @return
     */
    public DodopalResponse<CrdSysOrderDTO> getLoadOrderFun(CrdSysOrderDTO crdDTO);

    /**
     * 卡服务：充值结果接口
     * @param crdDTO
     * @return
     */
    public DodopalResponse<CrdSysOrderDTO> uploadResultFun(CrdSysOrderDTO crdDTO);
    
    /**
     * 卡服务：充值校验更新接口
     * @param crdDTO
     * @return
     */
    public DodopalResponse<CrdSysOrderDTO> uplodResultChkUdpOrderFun(CrdSysOrderDTO crdDTO);
   
    /**
     * 卡服务：验证订单接口
     * @param crdDTO
     * @return
     */
    public DodopalResponse<CrdSysOrderDTO> frontFailReportFun(CrdSysOrderDTO crdDTO);

    /**
     * 卡服务：获取APDU信息接口
     * @param crdDTO
     * @return
     */
    public DodopalResponse<CrdSysOrderDTO> getLoadInitFun(CrdSysOrderDTO crdDTO);
    
    /****************************************** 卡系统相关接口 end ********************************************/
    
    /****************************************** 用户系统相关接口 start ********************************************/
    /**
     * 用户系统：检验商户合法性接口
     * @param merchantNum
     * @param userId
     * @param posId
     * @param providerCode
     * @param source
     * @return
     */
    public DodopalResponse<Map<String, String>> validateMerchantForIcdcRecharge(String merchantNum, String userId, String posId, String providerCode, String source);
    
    /****************************************** 用户系统相关接口 end ********************************************/
    
    /****************************************** 支付交易系统相关接口 start ********************************************/
    
    /**
     * 支付交易：获取支付服务费和费率类型（通用支付方式）
     * @param payWayId (通用)支付方式id
     * @param busType 业务类型
     * @param amout 金额 单位（元）
     * @return  DodopalResponse<PayServiceRateDTO>
     */
    public DodopalResponse<PayServiceRateDTO> findPayServiceRate(String payWayId, String busType, long amout);
    
    /**
     * 支付交易：获取支付服务费和费率类型（外接支付方式）
     * @param payWayId (外接)支付方式id
     * @return  PayAwayDTO
     */
    public DodopalResponse<PayAwayDTO> findPayExternalById(String payWayId);
   
    /**
     * 支付交易：资金冻结接口
     * @param transactionDTO
     * @return
     */
    public DodopalResponse<Boolean> freezeAccountAmt(PayTranDTO transactionDTO);
    
    /**
     * 支付交易：资金解冻接口
     * @param transactionDTO
     * @return
     */
    public DodopalResponse<Boolean> unfreezeAccountAmt(PayTranDTO transactionDTO);

    /**
     * 支付交易：资金扣款接口
     * @param transactionDTO
     * @return
     */
    public DodopalResponse<Boolean> deductAccountAmt(PayTranDTO transactionDTO);
    
    /**
     * 支付交易：（来源：移动端）生单接口调用支付交易生成交易流水
     * @param PayTranDTO
     * @return  DodopalResponse<String>
     */
    public DodopalResponse<String> mobilepay(PayTranDTO payTranDTO);
    
    /****************************************** 支付交易系统相关接口 end ********************************************/
    
}
