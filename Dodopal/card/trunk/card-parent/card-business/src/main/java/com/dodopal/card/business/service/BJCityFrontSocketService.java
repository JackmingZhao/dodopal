package com.dodopal.card.business.service;

import com.dodopal.api.card.dto.BJAccountConsumeDTO;
import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.api.card.dto.BJDiscountDTO;
import com.dodopal.api.card.dto.BJIntegralConsumeDTO;
import com.dodopal.api.card.dto.SignParameter;

public interface BJCityFrontSocketService {
    /**
     * 通过socket完成向城市前置json格式的报文发送(北京)
     * @param ip
     * @param port
     * @param crdDTO
     * @param txnType
     * @return
     */
    public BJCrdSysOrderDTO sendBJCityFrontSocket(String ip, int port, BJCrdSysOrderDTO crdDTO, int txnType);

    /**
     * 向北京城市前置发送签到、签退报文
     * @param ip
     * @param port
     * @param crdDTO
     * @param txnType
     * @return
     */
    public SignParameter sendSignInJsonSocket(String ip, int port, SignParameter parameter, int txnType);

    /**
     * 向北京城市前置发送查询优惠信息报文
     * @param ip
     * @param port
     * @param discountDTO
     * @param txnType
     * @return
     */
    public BJDiscountDTO sendDiscountSocket(String ip, int port, BJDiscountDTO discountDTO, int txnType);
    
    
    /**
     * 通过socket完成向城市前置json格式的报文发送(北京)账户消费申请
     * @param ip
     * @param port
     * @param crdDTO
     * @param txnType
     * @return
     */
    public BJAccountConsumeDTO sendBJCityFrontSocketForAccCons(String ip, int port, BJAccountConsumeDTO bjAccountDTO, int txnType);
   
    /**
     * 通过socket完成向城市前置json格式的报文发送(北京)账户消费申请
     * @param ip
     * @param port
     * @param crdDTO
     * @param txnType
     * @return
     */
    public BJIntegralConsumeDTO sendBJCityFrontSocketForIntegral(String ip, int port, BJIntegralConsumeDTO dto, int txnType);
   

    /**
     * nfc北京前置
     * @param url
     * @param bjCrdSysOrderDTO
     * @param txnType
     * @return
     */
    public BJCrdSysOrderDTO sendBJCityFrontHttp(String url, BJCrdSysOrderDTO bjCrdSysOrderDTO, int txnType);
}
