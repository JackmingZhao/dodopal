package com.dodopal.card.business.service;

import java.util.List;

import com.dodopal.card.business.model.BJAccountIntegralOrder;

/** 
  * @author  Dingkuiyuan@dodopal.com 
  * @date 创建时间：2016年5月24日 上午10:07:13 
  * @version 1.0 
  * @parameter    北京一卡通消费优惠    账户积分消费卡服务订单表
  */
public interface BJAccountIntegralOrderService {
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年5月24日 上午9:30:04 
      * @version 1.0 
      * @parameter  
      * @描述 创建北京一卡通账户积分消费订单
      * @return  
      */
    public BJAccountIntegralOrder createBJAccountIntegralOrder(BJAccountIntegralOrder order);
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年5月24日 上午9:56:16 
      * @version 1.0 
      * @parameter  
      * @描述 更新北京一卡通账户积分消费订单
      * @return  
      */
    public void updateBJAccountIntegralOrder(BJAccountIntegralOrder order);
    
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年5月24日 上午10:06:46 
      * @version 1.0 
      * @parameter  
      * @描述 查询北京一卡通账户积分消费订单
      * @return  
      */
    public List<BJAccountIntegralOrder> findBJAccountIntegralOrder(BJAccountIntegralOrder order);
    

}
