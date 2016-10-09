package com.dodopal.product.business.dao;

import java.util.List;

import com.dodopal.product.business.model.QueryProductOrderResult;
import com.dodopal.product.business.model.QueryProductOrderStatesResult;
import com.dodopal.product.business.model.query.QueryProductOrderRequest;
import com.dodopal.product.business.model.query.QueryProductOrderStatesRequest;

public interface ProductOrderForMobileVCMapper {
    
    /**
     * 3.6  公交卡充值订单查询(移动端、VC端接口)
     * 
     */
    List<QueryProductOrderResult> queryProductOrder(QueryProductOrderRequest query);
    
    /**
     * 3.12  订单状态查询(移动端、VC端接口)
     * 
     */
    QueryProductOrderStatesResult queryProductOrderStates(QueryProductOrderStatesRequest query);
    
}
