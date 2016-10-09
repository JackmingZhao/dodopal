package com.dodopal.portal.business.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dodopal.api.product.dto.query.ChargeOrderQueryDTO;
import com.dodopal.api.product.dto.query.ConsumptionOrderQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.ChargeOrder;
import com.dodopal.portal.business.bean.ConsumptionOrder;
import com.dodopal.portal.business.bean.ConsumptionOrderCount;

public interface MerJointQueryService {
    /** 
     * @author  Dingkuiyuan@dodopal.com 
     * @date 创建时间：2016年3月31日 下午4:26:05 
     * @version 1.0 
     * @parameter  ChargeOrderQuery
     * @描述  充值订单查询
     * @return  DodopalDataPage<ChargeOrder>
     */
   public DodopalResponse<DodopalDataPage<ChargeOrder>> findChargeOrderByPage(ChargeOrderQueryDTO queryDTO);

   /** 
     * @author  Dingkuiyuan@dodopal.com 
     * @date 创建时间：2016年3月31日 下午4:31:32 
     * @version 1.0 
     * @parameter  ChargeOrderQuery
     * @描述 消费订单查询
     * @return  DodopalDataPage<ConsumptionOrder>
     */
   public DodopalResponse<DodopalDataPage<ConsumptionOrder>> findConsumptionOrderByPage(ConsumptionOrderQueryDTO queryDTO);

   /** 
     * @author  Dingkuiyuan@dodopal.com 
     * @date 创建时间：2016年3月31日 下午4:31:55 
     * @version 1.0 
     * @parameter  ChargeOrderQuery
     * @描述  消费订单统计
     * @return  DodopalDataPage<ConsumptionOrderCount>
     */
   public DodopalResponse<DodopalDataPage<ConsumptionOrderCount>> findConsumptionOrderCountByPage(ChargeOrderQueryDTO queryDTO);
  
   /** 
     * @author  Dingkuiyuan@dodopal.com 
     * @date 创建时间：2016年3月31日 下午4:32:27 
     * @version 1.0 
     * @parameter ChargeOrderQuery 
     * @描述 获取消费订单的统计数据
     * @return  ConsumptionOrderCount
     */
   public DodopalResponse<ConsumptionOrderCount> findConsumptionOrderCount(ChargeOrderQueryDTO queryDTO);
   
   
   public DodopalResponse<String> exportChargeOrder(HttpServletRequest request,HttpServletResponse response,ChargeOrderQueryDTO queryDTO);

   /** 
     * @author  Dingkuiyuan@dodopal.com 
     * @date 创建时间：2016年3月31日 下午4:31:32 
     * @version 1.0 
     * @parameter  ChargeOrderQuery
     * @描述 消费订单查询导出
     * @return  DodopalDataPage<ConsumptionOrder>
     */
   public DodopalResponse<String> exportConsumptionOrder(HttpServletRequest request,HttpServletResponse response,ConsumptionOrderQueryDTO queryDTO);

   /** 
     * @author  Dingkuiyuan@dodopal.com 
     * @date 创建时间：2016年3月31日 下午4:31:55 
     * @version 1.0 
     * @parameter  ChargeOrderQuery
     * @描述  消费订单统计导出
     * @return  DodopalDataPage<ConsumptionOrderCount>
     */
   public DodopalResponse<String> exportConsumptionOrderCount(HttpServletRequest request,HttpServletResponse response,ChargeOrderQueryDTO queryDTO);


}
