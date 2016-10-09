package com.dodopal.api.product.facade;

import java.util.List;

import com.dodopal.api.product.dto.LoadOrderDTO;
import com.dodopal.api.product.dto.LoadOrderForPaySysDTO;
import com.dodopal.api.product.dto.query.LoadOrderQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

/**
 * 圈存订单对外的api接口
 */

public interface LoadOrderFacade {
    
    /**
     * 6.4  查询公交卡充值圈存订单
     * @param queryDto
     * @return
     */
    public DodopalResponse<DodopalDataPage<LoadOrderDTO>> findLoadOrders(LoadOrderQueryDTO queryDto);
    
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年1月26日 下午4:06:05 
      * @version 1.0 
      * @parameter  根据订单号查询圈存的详细信息
      * @since  
      * @return  
      */
    public DodopalResponse<LoadOrderDTO> findLoadOrderByOrderNum(String orderNum);
    
    
    public DodopalResponse<List<LoadOrderDTO>> findLoadOrdersExport(LoadOrderQueryDTO queryDto);
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年4月12日 下午5:25:13 
      * @parameter  
      * @描述 生成圈存订单 该方法是在已支付完成的时候,不必扣商户额度,且订单状态为’已支付’.
      * @return  
      */
    public DodopalResponse<LoadOrderDTO> bookLoadOrderForPaySys(LoadOrderForPaySysDTO orderDto);
}
