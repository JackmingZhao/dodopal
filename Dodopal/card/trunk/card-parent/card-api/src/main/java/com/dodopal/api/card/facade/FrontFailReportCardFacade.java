package com.dodopal.api.card.facade;
import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.common.model.DodopalResponse;

public interface FrontFailReportCardFacade {
    /**
     * @Title: frontFailReportFun 
     * @Description: 卡服务订单校验方法
     * @param crdDTO 
     * @return DodopalResponse<CrdSysOrderDTO>
     */
    public DodopalResponse<CrdSysOrderDTO> frontFailReportFun(CrdSysOrderDTO crdDTO);
    
}
