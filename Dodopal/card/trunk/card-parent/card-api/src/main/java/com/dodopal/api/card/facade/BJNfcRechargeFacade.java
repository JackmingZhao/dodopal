package com.dodopal.api.card.facade;

import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.common.model.DodopalResponse;

/*
 * 北京nfc卡服务
 */
public interface BJNfcRechargeFacade {
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
    
    /**
     * 补充值申请起始
     * @param crdDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> reChargeApplyStart(BJCrdSysOrderDTO crdDTO);
    
    /**
     * 补充值申请后续
     * @param crdDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> reChargeApplyFollow(BJCrdSysOrderDTO crdDTO);

}
