package com.dodopal.card.business.service;

import com.dodopal.api.card.dto.BJCrdSysOrderDTO;

public interface BJNfcService {

    /*
     * 终端登记
     */
    public BJCrdSysOrderDTO userTeminalRegister(BJCrdSysOrderDTO crdDTO);

    /*
     * 充值验卡
     */
    public BJCrdSysOrderDTO chargeValidateCard(BJCrdSysOrderDTO crdDTO);

    /*
     * 充值起始
     */
    public BJCrdSysOrderDTO chargeStart(BJCrdSysOrderDTO crdDTO);

    /*
     * 充值后续
     */
    public BJCrdSysOrderDTO chargeFollow(BJCrdSysOrderDTO crdDTO);

    /*
     * 结果上传
     */
    public BJCrdSysOrderDTO chargeResultUp(BJCrdSysOrderDTO crdDTO);

    /*
     * 补充值申请起始
     */
    public BJCrdSysOrderDTO reChargeApplyStart(BJCrdSysOrderDTO crdDTO);

    /*
     * 补充值申请后续
     */
    public BJCrdSysOrderDTO reChargeApplyFollow(BJCrdSysOrderDTO crdDTO);

}
