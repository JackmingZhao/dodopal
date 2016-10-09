package com.dodopal.product.business.service;

import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.common.model.DodopalResponse;

/**
 * 北京城市一卡通充值业务流程接口
 * @author dodopal
 *
 */
public interface BJIcdcRechargeService {

    /******************************  北京（WEB端（POS:V61）：（OCX请求后台）） start   ******************************/
    
    /**
     * 验卡接口（OCX请求后台）
     * @param crdDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> checkCardQueryByV61(BJCrdSysOrderDTO crdDTO);
    
    /**
     * 生单接口（OCX请求后台）
     * @param crdDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> createRechargeOrderByV61(BJCrdSysOrderDTO crdDTO);
    
    /**
     * 获取圈存初始化指令接口（OCX请求后台）
     * @param crdDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> retrieveApduByV61(BJCrdSysOrderDTO crdDTO);
    
    /**
     * 充值申请接口（OCX请求后台）
     * @param crdDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> applyRechargeByV61(BJCrdSysOrderDTO crdDTO);
    
    /**
     * 上传结果接口（OCX请求后台）
     * @param crdDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> uploadRechargeResultByV61(BJCrdSysOrderDTO crdDTO);
    
    
    /******************************  北京（WEB端（POS:V61）：（OCX请求后台）） end   ******************************/
    
    
    /******************************  北京(V71请求) start   ******************************/
    
    /**
     * 充值生单验卡(V71请求)
     * @param crdDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> createOrderCheckCardByV71(BJCrdSysOrderDTO crdDTO);
    
    /**
     * 充值申请(V71请求)
     * @param crdDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> applyRechargeByV71(BJCrdSysOrderDTO crdDTO);
    
    /**
     * 充值结果上传(V71请求)
     * @param crdDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> uploadRechargeResultByV71(BJCrdSysOrderDTO crdDTO);
    
    
    /******************************  北京(V71请求) end   ******************************/
    
}
