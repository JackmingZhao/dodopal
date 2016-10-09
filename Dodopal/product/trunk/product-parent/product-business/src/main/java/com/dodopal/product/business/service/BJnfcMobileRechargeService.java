package com.dodopal.product.business.service;

import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.common.model.DodopalResponse;

/**
 * 北京NFC手机端一卡通充值业务流程接口
 * @author dodopal
 *
 */
public interface BJnfcMobileRechargeService {
    
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
