package com.dodopal.product.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.bean.ProxyCardAddRequestBean;
import com.dodopal.product.business.bean.ProxyCardAddResultBean;
import com.dodopal.product.business.model.ProxyCardAdd;
import com.dodopal.product.business.model.ProxyCountAllByID;
import com.dodopal.product.business.model.ProxyOffLineOrderTb;
import com.dodopal.product.business.model.query.ProxyCardAddQuery;
import com.dodopal.product.business.model.query.ProxyOffLineOrderTbQuery;

public interface ProxyCardAddQueryService {

    /**
     * 查询城市一卡通的充值记录
     * @param requestDto
     * @return
     */
    public DodopalResponse<List<ProxyCardAddResultBean>> queryCardTradeList(ProxyCardAddRequestBean requestDto);

    /**
     * 查询城市一卡通的充值记录(供门户调用)
     * @param proxyCardAddQuery
     * @return
     */
    public DodopalDataPage<ProxyCardAdd> cardTradeList(ProxyCardAddQuery proxyCardAddQuery);

    /**
     * 查询 城市一卡通的充值记录的统计(供门户调用)
     * @param proxyCardAddQuery
     * @return
     */
    public ProxyCountAllByID findCardTradeListCount(ProxyCardAddQuery proxyCardAddQuery);

    /**
     * 查询城市一卡通的消费记录(供门户调用)
     * @param proxyOffLineOrderTbQuery
     * @return
     */
    public DodopalDataPage<ProxyOffLineOrderTb> offLineTradeList(ProxyOffLineOrderTbQuery proxyOffLineOrderTbQuery);

    /**
     * 城市一卡通的消费记录   统计(供门户调用)
     * @param proxyOffLineOrderTbQuery
     * @return
     */
    public ProxyCountAllByID findoffLineTradeListCount(ProxyOffLineOrderTbQuery proxyOffLineOrderTbQuery);
    
    
    
    

}
