package com.dodopal.product.business.dao;

import java.util.List;

import com.dodopal.product.business.model.ProxyCardAdd;
import com.dodopal.product.business.model.ProxyCountAllByID;
import com.dodopal.product.business.model.ProxyOffLineOrderTb;
import com.dodopal.product.business.model.query.ProxyCardAddQuery;
import com.dodopal.product.business.model.query.ProxyOffLineOrderTbQuery;

public interface ProxyCardAddQueryMapper {

    /**
     * 城市一卡通充值记录查询  
     * @param proxyCardAddQuery
     * @return
     */
    public List<ProxyCardAdd> queryCardTradeList(ProxyCardAddQuery proxyCardAddQuery);

    /**
     * 城市一卡通充值记录查询  （供门户调用）
     * @param proxyCardAddQuery
     * @return
     */
    public List<ProxyCardAdd> cardTradeListByPage(ProxyCardAddQuery proxyCardAddQuery);

    /**
     * 查询 城市一卡通充值记录 统计（供门户调用）
     * @param proxyCardAddQuery
     * @return
     */
    public ProxyCountAllByID findCardTradeListCount(ProxyCardAddQuery proxyCardAddQuery);

    /**
     * 城市一卡通消费记录查询  （供门户调用）
     * @param proxyOffLineOrderTbQuery
     * @return
     */
    public List<ProxyOffLineOrderTb> offLineTradeListByPage(ProxyOffLineOrderTbQuery proxyOffLineOrderTbQuery);
    
    
    
    /**
     * 查询 城市一卡通消费记录 统计（供门户调用）
     * @param proxyCardAddQuery
     * @return
     */
    public ProxyCountAllByID findoffLineTradeListCount(ProxyOffLineOrderTbQuery proxyOffLineOrderTbQuery);


}
