package com.dodopal.oss.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.bean.PayAwayCommonBean;
import com.dodopal.oss.business.model.dto.PayAwayCommonQuery;

/**
 * 用户常用支付方式
 * @author xiongzhijing@dodopal.com
 * @version 2015年8月11日 下午16:30
 *
 */
public interface PayAwayCommonService {
   /**
    * 查询用户常用支付方式（分页）
    * @param payQuery 查询条件封装的实体bean
    * @return DodopalResponse<DodopalDataPage< PayAwayCommonBean>>
    */
   public DodopalResponse<DodopalDataPage< PayAwayCommonBean>> findPayAwayCommonList (PayAwayCommonQuery  payQuery);
  
   /**
   * 批量删除 用户常用支付方式
   * @param ids 用户常用支付方式的ID
   * @return DodopalResponse<Integer>
   */
   public DodopalResponse<Integer> deletePayAwayCommon(List<String> ids);


}
