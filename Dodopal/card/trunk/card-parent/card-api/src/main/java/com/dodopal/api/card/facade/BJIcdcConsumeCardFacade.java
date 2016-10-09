package com.dodopal.api.card.facade;

import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.common.model.DodopalResponse;

/**
 * 北京V61消费接口
 */
public interface BJIcdcConsumeCardFacade {

    /**
     * @Title: queryCheckCardCons
     * @Description: 消费验卡查询获得扣款初始化指令方法
     */
    public DodopalResponse<BJCrdSysOrderDTO> queryCheckCardCons(BJCrdSysOrderDTO crdDTO);

    /**
     * @Title: getDeductOrderCons
     * @Description: 消费申请获得扣款指令方法
     */
    public DodopalResponse<BJCrdSysOrderDTO> getDeductOrderCons(BJCrdSysOrderDTO crdDTO);

    /**
     * @Title: getDeductOrderCons
     * @Description: 消费结果上传方法
     */
    public DodopalResponse<BJCrdSysOrderDTO> uploadResultCons(BJCrdSysOrderDTO crdDTO);

    /**
     * @Title: frontFailReportCons
     * @Description: 前端失败校验方法
     */
    public DodopalResponse<BJCrdSysOrderDTO> frontFailReportCons(BJCrdSysOrderDTO crdDTO);

    /**
     * @Title: uploadResultCons
     * @Description: 消费脱机结果上传
     */
    public DodopalResponse<BJCrdSysOrderDTO> uploadResultConsOffLine(BJCrdSysOrderDTO crdDTO);
}
