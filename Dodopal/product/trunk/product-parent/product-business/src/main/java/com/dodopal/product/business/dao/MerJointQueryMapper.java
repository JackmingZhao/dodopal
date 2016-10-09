package com.dodopal.product.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.product.business.model.ChargeOrder;
import com.dodopal.product.business.model.ConsumptionOrder;
import com.dodopal.product.business.model.ConsumptionOrderCount;
import com.dodopal.product.business.model.query.ChargeOrderQuery;
import com.dodopal.product.business.model.query.ConsumptionOrderQuery;

/** 
  * @author  Dingkuiyuan@dodopal.com 
  * @date 创建时间：2016年3月30日 下午4:52:54 
  * 联合查询商户
  */
public interface MerJointQueryMapper {
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年3月30日 下午4:54:06 
      * @version 1.0 
      * @parameter  ChargeOrderQuery
      * @描述 充值订单查询
      * @return  List<ChargeOrder>
      */
    public List<ChargeOrder> findChargeOrderByPage(ChargeOrderQuery query);
   
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年3月31日 上午11:36:17 
      * @version 1.0 
      * @parameter  ChargeOrderQuery
      * @描述 消费订单查询
      * @return  List<ConsumptionOrder> 
      */
    public List<ConsumptionOrder> findConsumptionOrderByPage(ConsumptionOrderQuery query);
    
    
    /** 
     * @author  Dingkuiyuan@dodopal.com 
     * @date 创建时间：2016年3月31日 上午11:36:17 
     * @version 1.0 
     * @parameter  ChargeOrderQuery
     * @描述 消费订单统计
     * @return  List<ConsumptionOrderCount> 
     */
   public List<ConsumptionOrderCount> findConsumptionOrderCountByPage(ChargeOrderQuery query);
   
   
   /** 
    * @author  Dingkuiyuan@dodopal.com 
    * @date 创建时间：2016年3月31日 上午11:55:48 
    * @version 1.0 
    * @parameter  ChargeOrderQuery
    * @描述 获取消费订单的统计数据
    * @return  ConsumptionOrderCount
    */
   public ConsumptionOrderCount findConsumptionOrderCount(ChargeOrderQuery query);

   
   /** 
    * @author  Dingkuiyuan@dodopal.com 
    * @date 创建时间：2016年3月30日 下午4:54:06 
    * @version 1.0 
    * @parameter  ChargeOrderQuery
    * @描述 充值订单查询导出
    * @return  List<ChargeOrder>
    */
   public List<ChargeOrder> exportChargeOrder(ChargeOrderQuery query);
 
  
   /** 
    * @author  Dingkuiyuan@dodopal.com 
    * @date 创建时间：2016年3月31日 上午11:36:17 
    * @version 1.0 
    * @parameter  ChargeOrderQuery
    * @描述 消费订单查询导出
    * @return  List<ConsumptionOrder> 
    */
   public List<ConsumptionOrder> exportConsumptionOrder(ConsumptionOrderQuery query);
  
  
   /** 
    * @author  Dingkuiyuan@dodopal.com 
    * @date 创建时间：2016年3月31日 上午11:36:17 
    * @version 1.0 
    * @parameter  ChargeOrderQuery
    * @描述 消费订单统计导出
    * @return  List<ConsumptionOrderCount> 
    */
   public List<ConsumptionOrderCount> exportConsumptionOrderCount(ChargeOrderQuery query);
 
 
   /** 
     * @author  Dingkuiyuan@dodopal.com 
     * @date 创建时间：2016年4月8日 上午11:03:15 
     * @version 1.0 
     * @描述 根据mercode查找老平台的merid
     * @return  
     */
   public String findMchnitidByMerCode(@Param("merCode")String merCode);
   
}
