package com.dodopal.product.business.service;

import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.common.model.DodopalResponse;

public interface IcdcRechargeService {

    /**
     * 验卡接口
     * @param crdDTO
     * @return
     */
    public DodopalResponse<CrdSysOrderDTO> preCheckCardQueryInPrd(CrdSysOrderDTO crdDTO);
    
    /**
     * 生单接口
     * @param crdDTO
     * @return
     */
    public DodopalResponse<CrdSysOrderDTO> createIcdcRechargeOrder(CrdSysOrderDTO crdDTO);
    
    /**
     * 获取圈存初始化指令接口
     * @param crdDTO
     * @return
     */
    public DodopalResponse<CrdSysOrderDTO> retrieveApdu(CrdSysOrderDTO crdDTO);
    
    /**
     * 充值申请接口
     * @param crdDTO
     * @return
     */
    public DodopalResponse<CrdSysOrderDTO> icdcRechargeCard(CrdSysOrderDTO crdDTO);
    
    /**
     * 上传结果接口
     * @param crdDTO
     * @return
     */
    public DodopalResponse<CrdSysOrderDTO> uploadIcdcRechargeResult(CrdSysOrderDTO crdDTO);
    
    /**
     * 前端充值失败接口
     * @param crdDTO
     * @return
     */
    public DodopalResponse<CrdSysOrderDTO> frontFailReportFun(CrdSysOrderDTO crdDTO);
    
}
