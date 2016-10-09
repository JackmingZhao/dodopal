package com.dodopal.oss.delegate;

import java.util.List;

import com.dodopal.api.product.dto.LoadOrderDTO;
import com.dodopal.api.product.dto.LoadOrderRequestDTO;
import com.dodopal.api.product.dto.query.LoadOrderQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface LoadOrderDelegate {
    
    DodopalResponse<DodopalDataPage<LoadOrderDTO>> findLoadOrders(LoadOrderQueryDTO queryDto);
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年1月26日 下午4:29:03 
      * @version 1.0 
      * @parameter   根据圈存订单号查找圈存详细信息
      * @since  
      * @return  
      */
    DodopalResponse<LoadOrderDTO> findLoadOrderByOrderNum(String orderNum);
    
    DodopalResponse<List<LoadOrderDTO>> findLoadOrdersExport(LoadOrderQueryDTO queryDto);
    
    ///////////////////以下是测试代码/////////////////////////////////
    
    //保存
    public DodopalResponse<String> bookLoadOrder(LoadOrderRequestDTO loadOrderRequestDTO);
    //查看
    public DodopalResponse<String> findLoadOrder();
    //6.3   根据外接商户的订单号查询圈存订单状态
    public DodopalResponse<String> findLoadOrderStatus(LoadOrderRequestDTO loadOrderRequestDTO);
}
