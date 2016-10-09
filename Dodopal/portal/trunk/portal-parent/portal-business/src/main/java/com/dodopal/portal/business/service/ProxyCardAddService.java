package com.dodopal.portal.business.service;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.ProxyCardAddBean;
import com.dodopal.portal.business.bean.ProxyCountAllByIDBean;
import com.dodopal.portal.business.bean.ProxyOffLineOrderTbBean;
import com.dodopal.portal.business.bean.query.ProxyCardAddQuery;
import com.dodopal.portal.business.bean.query.ProxyOffLineOrderTbQuery;

public interface ProxyCardAddService {

    /**
     * 城市一卡通充值记录查询 （供门户调用）
     * @param yktPsamQueryDTO
     * @return
     */
    public DodopalResponse<DodopalDataPage<ProxyCardAddBean>> cardTradeList(ProxyCardAddQuery proxyCardAddQuery);
    
    /**
     * 城市一卡通充值记录 统计 （供门户调用）
     * @param proxyCardAddQueryDTO
     * @return
     */
    public DodopalResponse<ProxyCountAllByIDBean> findCardTradeListCount(ProxyCardAddQuery proxyCardAddQuery);
    
    
    /**
     * 城市一卡通消费记录查询 （供门户调用）
     * @param proxyCardAddQueryDTO
     * @return
     */
    public DodopalResponse<DodopalDataPage<ProxyOffLineOrderTbBean>> offLineTradeList(ProxyOffLineOrderTbQuery proxyOffLineOrderTbQuery);

    /**
     * 城市一卡通消费记录 统计（供门户调用）
     * @param proxyCardAddQueryDTO
     * @return
     */
    public DodopalResponse<ProxyCountAllByIDBean> findoffLineTradeListCount(ProxyOffLineOrderTbQuery proxyOffLineOrderTbQuery);
    
}
