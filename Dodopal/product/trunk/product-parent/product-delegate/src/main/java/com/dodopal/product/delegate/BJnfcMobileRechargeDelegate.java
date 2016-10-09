package com.dodopal.product.delegate;

import java.util.Map;

import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.api.payment.dto.PayTranDTO;
import com.dodopal.common.model.DodopalResponse;


/**
 * 北京NFC手机端一卡通充值业务delegate接口
 * @author dodopal
 *
 */
public interface BJnfcMobileRechargeDelegate {
    
    /**
     * 用户系统：检验个人用户相关合法性接口
     * @param userId
     * @return
     */
    public DodopalResponse<Map<String, String>> validatePersonalUserForNfcRecharge(String userId);
    
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
     * 用户终端信息登记
     * @param crdDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> userTeminalRegister(BJCrdSysOrderDTO crdDTO);

    
    /**
     * 充值验卡接口
     * @param crdDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> chargeValidateCard(BJCrdSysOrderDTO crdDTO);
    
    
    /**
     * 充值申请起始接口
     * @param crdDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> chargeStart(BJCrdSysOrderDTO crdDTO);

    /**
     * 充值申请后续接口
     * @param crdDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> chargeFollow(BJCrdSysOrderDTO crdDTO);

    /**
     * 充值结果上传
     * @param crdDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> chargeResultUp(BJCrdSysOrderDTO crdDTO);
    
}
