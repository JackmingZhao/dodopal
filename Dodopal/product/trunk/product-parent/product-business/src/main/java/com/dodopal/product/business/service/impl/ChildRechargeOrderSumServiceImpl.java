package com.dodopal.product.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.product.business.dao.ChildRechargeOrderSumMapper;
import com.dodopal.product.business.model.ChildRechargeOrderSum;
import com.dodopal.product.business.model.query.ChildRechargeOrderSumQuery;
import com.dodopal.product.business.service.ChildRechargeOrderSumService;

/**
 * 业务订单汇总（一卡通充值）
 * @author lenovo
 *
 */
@Service
public class ChildRechargeOrderSumServiceImpl implements ChildRechargeOrderSumService {

    @Autowired
    ChildRechargeOrderSumMapper  childRechargeOrderSumMapper;
    
    @Transactional(readOnly=true)
    public DodopalDataPage<ChildRechargeOrderSum> queryChildRechargeOrder(ChildRechargeOrderSumQuery query) {
        List<ChildRechargeOrderSum>  rechargeOrderList = childRechargeOrderSumMapper.queryChildRechargeOrderByPage(query);
        DodopalDataPage<ChildRechargeOrderSum> result = new DodopalDataPage<ChildRechargeOrderSum>(query.getPage(), rechargeOrderList);
        return result;
    }


}
