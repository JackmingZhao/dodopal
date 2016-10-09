package com.dodopal.product.business.service;

import java.util.List;

import com.dodopal.product.business.model.QueryProductOrderResult;
import com.dodopal.product.business.model.QueryProductOrderStatesResult;
import com.dodopal.product.business.model.query.QueryProductOrderRequest;
import com.dodopal.product.business.model.query.QueryProductOrderStatesRequest;

/**
 * 产品库公交卡充值订单(移动端、VC端接口)Service
 * 
 * @author dodopal
 */
public interface ProductOrderForMobileVCService {
    
    /**
     * 3.6  公交卡充值订单查询 (移动端、VC端接口)
     * @param requestDto
     * @return
     */
    public List<QueryProductOrderResult> queryProductOrder(QueryProductOrderRequest query);
    
    /**
     * 3.12  订单状态查询(移动端、VC端接口)
     * @param requestDto
     * @return
     */
    public QueryProductOrderStatesResult queryProductOrderStates(QueryProductOrderStatesRequest query);
 
}
