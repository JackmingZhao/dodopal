package com.dodopal.api.product.facade;

import com.dodopal.api.product.dto.ChildRechargeOrderSumDTO;
import com.dodopal.api.product.dto.query.ChildRechargeOrderSumQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
/**
 * 子商户 订单记录汇总（一卡通充值）
 * @author lenovo
 *
 */
public interface ChildRechargeOrderSumFacade {
    
    
    /**
     * 子商户 一 卡通充值订单汇总查询
     * @param query
     * @return
     */
    public DodopalResponse<DodopalDataPage<ChildRechargeOrderSumDTO>> queryChildRechargeOrder(ChildRechargeOrderSumQueryDTO query);

}
