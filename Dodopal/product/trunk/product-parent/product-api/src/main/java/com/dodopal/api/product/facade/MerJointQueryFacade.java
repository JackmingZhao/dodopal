package com.dodopal.api.product.facade;

import java.util.List;

import com.dodopal.api.product.dto.ChargeOrderDTO;
import com.dodopal.api.product.dto.ConsumptionOrderCountDTO;
import com.dodopal.api.product.dto.ConsumptionOrderDTO;
import com.dodopal.api.product.dto.query.ChargeOrderQueryDTO;
import com.dodopal.api.product.dto.query.ConsumptionOrderQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface MerJointQueryFacade {
    /** 
     * @author  Dingkuiyuan@dodopal.com 
     * @date 创建时间：2016年3月31日 下午4:26:05 
     * @version 1.0 
     * @parameter  ChargeOrderQuery
     * @描述  充值订单查询
     * @return  DodopalDataPage<ChargeOrder>
     */
   public DodopalResponse<DodopalDataPage<ChargeOrderDTO>> findChargeOrderByPage(ChargeOrderQueryDTO queryDTO);

   /** 
     * @author  Dingkuiyuan@dodopal.com 
     * @date 创建时间：2016年3月31日 下午4:31:32 
     * @version 1.0 
     * @parameter  ChargeOrderQuery
     * @描述 消费订单查询
     * @return  DodopalDataPage<ConsumptionOrder>
     */
   public DodopalResponse<DodopalDataPage<ConsumptionOrderDTO>> findConsumptionOrderByPage(ConsumptionOrderQueryDTO queryDTO);

   /** 
     * @author  Dingkuiyuan@dodopal.com 
     * @date 创建时间：2016年3月31日 下午4:31:55 
     * @version 1.0 
     * @parameter  ChargeOrderQuery
     * @描述  消费订单统计
     * @return  DodopalDataPage<ConsumptionOrderCount>
     */
   public DodopalResponse<DodopalDataPage<ConsumptionOrderCountDTO>> findConsumptionOrderCountByPage(ChargeOrderQueryDTO queryDTO);
  
   /** 
     * @author  Dingkuiyuan@dodopal.com 
     * @date 创建时间：2016年3月31日 下午4:32:27 
     * @version 1.0 
     * @parameter ChargeOrderQuery 
     * @描述 获取消费订单的统计数据
     * @return  ConsumptionOrderCount
     */
   public DodopalResponse<ConsumptionOrderCountDTO> findConsumptionOrderCount(ChargeOrderQueryDTO queryDTO);
   
   /** 
    * @author  Dingkuiyuan@dodopal.com 
    * @date 创建时间：2016年4月8日 下午5:23:22 
    * @version 1.0 
    * @parameter  
    * @描述 导出充值订单
    * @return  
    */
   public DodopalResponse<List<ChargeOrderDTO>> exportChargeOrder(ChargeOrderQueryDTO queryDTO);

   /** 
     * @author  Dingkuiyuan@dodopal.com 
     * @date 创建时间：2016年3月31日 下午4:31:32 
     * @version 1.0 
     * @parameter  ChargeOrderQuery
     * @描述 消费订单查询导出
     * @return  DodopalDataPage<ConsumptionOrder>
     */
   public DodopalResponse<List<ConsumptionOrderDTO>> exportConsumptionOrder(ConsumptionOrderQueryDTO queryDTO);

   /** 
     * @author  Dingkuiyuan@dodopal.com 
     * @date 创建时间：2016年3月31日 下午4:31:55 
     * @version 1.0 
     * @parameter  ChargeOrderQuery
     * @描述  消费订单统计导出
     * @return  DodopalDataPage<ConsumptionOrderCount>
     */
   public DodopalResponse<List<ConsumptionOrderCountDTO>> exportConsumptionOrderCount(ChargeOrderQueryDTO queryDTO);
  
   
   

}
