package com.dodopal.product.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.product.business.model.ChargeOrder;
import com.dodopal.product.business.model.ConsumptionOrderCount;
import com.dodopal.product.business.model.ConsumptionOrder;
import com.dodopal.product.business.model.query.ChargeOrderQuery;
import com.dodopal.product.business.model.query.ConsumptionOrderQuery;

public interface MerJointQueryService {
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年3月31日 下午4:26:05 
      * @version 1.0 
      * @parameter  ChargeOrderQuery
      * @描述  充值订单查询
      * @return  DodopalDataPage<ChargeOrder>
      */
    public DodopalDataPage<ChargeOrder> findChargeOrderByPage(ChargeOrderQuery query);

    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年3月31日 下午4:31:32 
      * @version 1.0 
      * @parameter  ChargeOrderQuery
      * @描述 消费订单查询
      * @return  DodopalDataPage<ConsumptionOrder>
      */
    public DodopalDataPage<ConsumptionOrder> findConsumptionOrderByPage(ConsumptionOrderQuery query);

    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年3月31日 下午4:31:55 
      * @version 1.0 
      * @parameter  ChargeOrderQuery
      * @描述  消费订单统计
      * @return  DodopalDataPage<ConsumptionOrderCount>
      */
    public DodopalDataPage<ConsumptionOrderCount> findConsumptionOrderCountByPage(ChargeOrderQuery query);
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年4月8日 下午5:07:22 
      * @version 1.0 
      * @parameter  
      * @描述 充值订单查询导出
      * @return  
      */
    public List<ChargeOrder> exportChargeOrder(ChargeOrderQuery query);
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年4月8日 下午5:07:35 
      * @version 1.0 
      * @parameter  
      * @描述 消费订单查询导出
      * @return  
      */
    public List<ConsumptionOrder> exportConsumptionOrder(ConsumptionOrderQuery query);

    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年4月8日 下午5:07:51 
      * @version 1.0 
      * @parameter  
      * @描述 消费订单统计导出
      * @return  
      */
    public List<ConsumptionOrderCount> exportConsumptionOrderCount(ChargeOrderQuery query);
    

    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年3月31日 下午4:32:27 
      * @version 1.0 
      * @parameter ChargeOrderQuery 
      * @描述 获取消费订单的统计数据
      * @return  ConsumptionOrderCount
      */
    public ConsumptionOrderCount findConsumptionOrderCount(ChargeOrderQuery query);
    
}
