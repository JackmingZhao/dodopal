package com.dodopal.card.business.service;

import com.dodopal.api.card.dto.CrdSysOrderDTO;

public interface CityFrontSocketService {
    /**
     * 通过socket完成向城市前置json格式的报文发送
     * @param ip
     * @param port
     * @param crdDTO
     * @return
     */
    public CrdSysOrderDTO sendCityFrontSocket(String ip, int port, CrdSysOrderDTO crdDTO, int txnType);

}
