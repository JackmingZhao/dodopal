package com.dodopal.product.business.service;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.product.business.model.ChildRechargeOrderSum;
import com.dodopal.product.business.model.query.ChildRechargeOrderSumQuery;

public interface ChildRechargeOrderSumService {
    /**
     * 子商户的   业务订单汇总（一卡通充值）
     * @param query
     * @return
     */
    public DodopalDataPage<ChildRechargeOrderSum> queryChildRechargeOrder(ChildRechargeOrderSumQuery query);

}
