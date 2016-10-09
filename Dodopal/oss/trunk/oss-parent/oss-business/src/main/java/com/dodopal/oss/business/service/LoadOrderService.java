package com.dodopal.oss.business.service;

import java.util.List;

import com.dodopal.api.product.dto.LoadOrderRequestDTO;
import com.dodopal.api.product.dto.query.LoadOrderQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.bean.LoadOrderBean;

public interface LoadOrderService {

    /**
     * 6.4 查询公交卡充值圈存订单
     */
    public DodopalResponse<DodopalDataPage<LoadOrderBean>> findLoadOrders(LoadOrderQueryDTO queryDto);
    
    public DodopalResponse<LoadOrderBean> findLoadOrderByOrderNum(String orderNum);
    
    
    public DodopalResponse<List<LoadOrderBean>> findLoadOrdersExport(LoadOrderQueryDTO queryDto);

    
    /////////////////////////////////////////////// 以下是测试代码///////////////////////////////////
    public DodopalResponse<String> bookLoadOrder(LoadOrderRequestDTO loadOrderRequestDTO);

    public DodopalResponse<String> findLoadOrder();

    //6.3 根据外接商户的订单号查询圈存订单状态
    public DodopalResponse<String> findLoadOrderStatus(LoadOrderRequestDTO loadOrderRequestDTO);

  
}
