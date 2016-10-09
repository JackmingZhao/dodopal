package com.dodopal.api.card.facade;

import com.dodopal.api.card.dto.BJAccountConsumeDTO;
import com.dodopal.api.card.dto.BJIntegralConsumeDTO;
import com.dodopal.common.model.DodopalResponse;

public interface BJAccIntConsumeDiscountFacade {
    /** 
     * @author  Dingkuiyuan@dodopal.com 
     * @date 创建时间：2016年5月19日 下午5:29:33 
     * @version 1.0 
     * @parameter  
     * @描述 消费优惠  ——账户消费申请
     * @return  
     */
   public DodopalResponse<BJAccountConsumeDTO> applyAccountConsume(BJAccountConsumeDTO crdDTO);

   /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年5月23日 下午2:43:48 
      * @version 1.0 
      * @parameter  
      * @描述 账户消费撤销
      * @return  
      */
   public DodopalResponse<BJAccountConsumeDTO> revokeAccountConsume(BJAccountConsumeDTO crdDTO);
   
   /** 
    * @author  Dingkuiyuan@dodopal.com 
    * @date 创建时间：2016年5月23日 下午2:43:48 
    * @version 1.0 
    * @parameter  
    * @描述 账户消费结果上送
    * @return  
    */
   public DodopalResponse<BJAccountConsumeDTO> uploadAccountConsume(BJAccountConsumeDTO crdDTO);
   
   /** 
    * @author  Dingkuiyuan@dodopal.com 
    * @date 创建时间：2016年5月23日 下午2:43:48 
    * @version 1.0 
    * @parameter  
    * @描述 积分消费申请
    * @return  
    */
   public DodopalResponse<BJIntegralConsumeDTO> applyIntegralConsume(BJIntegralConsumeDTO crdDTO);
   
   
   /** 
    * @author  Dingkuiyuan@dodopal.com 
    * @date 创建时间：2016年5月23日 下午2:43:48 
    * @version 1.0 
    * @parameter  
    * @描述 积分消费撤销
    * @return  
    */
   public DodopalResponse<BJIntegralConsumeDTO> revokeIntegralConsume(BJIntegralConsumeDTO crdDTO);
   
   /** 
    * @author  Dingkuiyuan@dodopal.com 
    * @date 创建时间：2016年5月23日 下午2:43:48 
    * @version 1.0 
    * @parameter  
    * @描述 积分消费结果上送
    * @return  
    */
   public DodopalResponse<BJIntegralConsumeDTO> uploadIntegralConsume(BJIntegralConsumeDTO crdDTO);
   
   
   
}
