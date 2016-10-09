package com.dodopal.product.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.product.business.dao.ProductOrderForMobileVCMapper;
import com.dodopal.product.business.model.QueryProductOrderResult;
import com.dodopal.product.business.model.QueryProductOrderStatesResult;
import com.dodopal.product.business.model.query.QueryProductOrderRequest;
import com.dodopal.product.business.model.query.QueryProductOrderStatesRequest;
import com.dodopal.product.business.service.ProductOrderForMobileVCService;

/**
 * 产品库公交卡充值订单(移动端、VC端接口)ServiceImpl
 * @author dodopal
 */
@Service
public class ProductOrderForMobileVCServiceImpl implements ProductOrderForMobileVCService {

    @Autowired
    private ProductOrderForMobileVCMapper productOrderForMobileVCMapper;

    /**
     * 3.6 公交卡充值订单查询(移动端、VC端接口)
     */
    @Override
    @Transactional(readOnly = true)
    public List<QueryProductOrderResult> queryProductOrder(QueryProductOrderRequest query) {
        List<QueryProductOrderResult> resultDTOs = productOrderForMobileVCMapper.queryProductOrder(query);
        return resultDTOs;
    }

    /**
     * 3.12 订单状态查询(移动端、VC端接口)
     */
    @Override
    @Transactional(readOnly = true)
    public QueryProductOrderStatesResult queryProductOrderStates(QueryProductOrderStatesRequest query) {
        QueryProductOrderStatesResult resultDTO = productOrderForMobileVCMapper.queryProductOrderStates(query);
        return resultDTO;
    }
}
