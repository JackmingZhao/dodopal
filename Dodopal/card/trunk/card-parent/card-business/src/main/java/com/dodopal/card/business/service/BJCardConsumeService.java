package com.dodopal.card.business.service;

import com.dodopal.api.card.dto.BJCrdSysOrderDTO;


public interface BJCardConsumeService {
    
    /*
     * 创建卡服务订单
     */
    public String createCrdSysConsOrder(BJCrdSysOrderDTO crdDTO);
    
    
    /**
     * 根据卡服务订单号更改卡服务订单
     * @param crdSysOrder
     * @return
     */
    public void updateCrdSysConsOrderByCrdnum(BJCrdSysOrderDTO crdDTO);
    
    /**
     * 根据卡服务订单号更改卡服务订单
     * @param crdSysOrder
     * @return
     */
    public void updateCrdSysConsOrderAfterByCrdnum(BJCrdSysOrderDTO crdDTO);
    
}
