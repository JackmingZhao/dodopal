package com.dodopal.api.card.facade;

import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.common.model.DodopalResponse;

public interface IcdcConsumeCardFacade {
    /**
     * 消费验卡查询获得扣款初始化指令方法(外部调用)
     * @param crdDTO
     * @return
     */
    public DodopalResponse<CrdSysOrderDTO> queryCheckCardConsFun(CrdSysOrderDTO crdDTO);

    /**
     * 消费申请获得扣款指令方法(外部调用)
     * @param crdDTO
     * @return
     */
    public DodopalResponse<CrdSysOrderDTO> getDeductOrderConsFun(CrdSysOrderDTO crdDTO);

    /**
     * 消费结果上传方法(外部调用)
     * @param crdDTO
     * @return
     */
    public DodopalResponse<CrdSysOrderDTO> uploadResultConsFun(CrdSysOrderDTO crdDTO);
    
    /**
     * 前端失败校验方法(外部调用)
     * @param crdDTO
     * @return
     */
    public DodopalResponse<CrdSysOrderDTO> frontFailReportConsFun(CrdSysOrderDTO crdDTO);

}
