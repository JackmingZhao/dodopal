package com.dodopal.product.delegate;

import java.util.Map;

import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.api.payment.dto.PayAwayDTO;
import com.dodopal.api.payment.dto.PayServiceRateDTO;
import com.dodopal.api.payment.dto.PayTranDTO;
import com.dodopal.common.model.DodopalResponse;

/**
 * 北京城市一卡通充值业务delegate接口
 * @author dodopal
 *
 */
public interface BJIcdcRechargeDelegate {

    /****************************************** 卡系统相关接口 start ********************************************/

    /**
     * 卡服务: 验卡查询接口（V61充值接口）
     * 
     */
    public DodopalResponse<BJCrdSysOrderDTO> queryCheckCardByV61(BJCrdSysOrderDTO crdDTO);

    /**
     * 卡服务: 订单创建接口（V61充值接口）
     * 
     */
    public DodopalResponse<BJCrdSysOrderDTO> createCrdOrderCardByV61(BJCrdSysOrderDTO crdDTO);

    /**
     * 卡服务：获取APDU信息接口（V61充值接口）
     * 
     */
    public DodopalResponse<BJCrdSysOrderDTO> getApduByV61(BJCrdSysOrderDTO crdDTO);
    
    /**
     * 卡服务: 充值申请获得圈存指令接口（V61充值接口）
     * 
     */
    public DodopalResponse<BJCrdSysOrderDTO> getLoadOrderByV61(BJCrdSysOrderDTO crdDTO);

    /**
     * 卡服务：结果上传校验更新订单接口（V61充值接口）
     * 
     */
    public DodopalResponse<BJCrdSysOrderDTO> uploadResultChkUdpOrderByV61(BJCrdSysOrderDTO crdDTO);

    /**
     * 卡服务：充值结果上传接口（V61充值接口）
     *  
     */
    public DodopalResponse<BJCrdSysOrderDTO> uploadResultByV61(BJCrdSysOrderDTO crdDTO);
    
    /**
     * 卡服务: 验卡查询接口（V71充值接口）
     * 
     */
    public DodopalResponse<BJCrdSysOrderDTO> queryCheckCardByV71(BJCrdSysOrderDTO crdDTO);
    
    /**
     * 卡服务: 充值申请（V71充值接口）
     * 
     */
    public DodopalResponse<BJCrdSysOrderDTO> getLoadOrderByV71(BJCrdSysOrderDTO crdDTO);
    
    /**
     * 卡服务：结果上传校验更新订单接口（V71充值接口）
     * 
     */
    public DodopalResponse<BJCrdSysOrderDTO> uploadResultChkUdpOrderByV71(BJCrdSysOrderDTO crdDTO);
    
    /**
     * 卡服务: 充值结果上传（V71充值接口）
     * 
     */
    public DodopalResponse<BJCrdSysOrderDTO> uploadResultByV71(BJCrdSysOrderDTO crdDTO);
    
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
     * 通用支付方式：获取支付服务费和费率类型
     * @param payWayId (通用)支付方式id
     * @param busType 业务类型
     * @param amout 金额 单位（元）
     * @return  DodopalResponse<PayServiceRateDTO>
     */
    public DodopalResponse<PayServiceRateDTO> findPayServiceRate(String payWayId, String busType, long amout);
    
    /**
     * 外接支付方式：获取支付服务费和费率类型
     * @param payWayId (外接)支付方式id
     * @return  PayAwayDTO
     */
    public DodopalResponse<PayAwayDTO> findPayExternalById(String payWayId);
    
    /**
     * 支付交易：（来源：移动端）生单接口调用支付交易生成交易流水
     * @param PayTranDTO
     * @return  DodopalResponse<String>
     */
    public DodopalResponse<String> mobilepay(PayTranDTO payTranDTO);
    
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
    
    /****************************************** 支付交易系统相关接口 end ********************************************/
    
}
