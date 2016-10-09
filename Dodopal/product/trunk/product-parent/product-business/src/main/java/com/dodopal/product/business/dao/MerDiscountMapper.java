package com.dodopal.product.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/** 
  * @author  Dingkuiyuan@dodopal.com 
  * @date 创建时间：2016年4月16日 下午3:30:21 
  * @version 1.0 
  * 商户折扣
  * @parameter    
  */
public interface MerDiscountMapper {
   /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年4月16日 下午3:30:39 
      * @version 1.0 
      * @parameter  
      * @描述 商户折扣  今天此时所有适用的折扣
      * @return  
      */
    List<Map<String,String>> findMerDiscountToday(@Param("merCode")String merCode);
}
