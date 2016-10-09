package com.dodopal.card.business.dao;

import java.util.List;

import com.dodopal.card.business.model.BJAccountIntegralOrder;

/** 
  * @author  Dingkuiyuan@dodopal.com 
  * @date 创建时间：2016年5月23日 下午1:44:35 
  * @version 1.0 
  * @parameter 北京账户积分消费订单   
  */
public interface BJAccountIntegralOrderMapper {
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年5月23日 下午4:45:03 
      * @version 1.0 
      * @parameter  
      * @描述 保存北京账户积分消费订单
      * @return  
      */
    public void  saveBJAccountIntegralOrder(BJAccountIntegralOrder order);
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年5月24日 上午9:26:51 
      * @version 1.0 
      * @parameter  
      * @描述 查询北京账户积分消费订单
      * @return  
      */
    public List<BJAccountIntegralOrder> findBJAccountIntegralOrder(BJAccountIntegralOrder order);
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年5月24日 上午9:17:45 
      * @version 1.0 
      * @parameter  
      * @描述 根据产品库订单号，更新卡服务账户积分消费的订单表
      * @return  
      */
    public void  updateBJAccountIntegralOrderByProOrderNum(BJAccountIntegralOrder order);
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年5月24日 上午9:40:28 
      * @version 1.0 
      * @parameter  
      * @描述 获取5位序列
      * @return  
      */
    public String getBJAccIntConsOrderCodeSeq();
}