package com.dodopal.product.business.dao;

import java.util.List;

import com.dodopal.product.business.model.ChildRechargeOrderSum;
import com.dodopal.product.business.model.query.ChildRechargeOrderSumQuery;

public interface ChildRechargeOrderSumMapper {
    
    
    /**
     * 业务订单汇总  （一卡通充值）查询
     * @param query
     * @return List<RechargeStatisticsYktOrder>
     */
    public List<ChildRechargeOrderSum> queryChildRechargeOrderByPage(ChildRechargeOrderSumQuery query);

}
