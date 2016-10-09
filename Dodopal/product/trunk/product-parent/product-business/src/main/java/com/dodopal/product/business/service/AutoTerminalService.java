package com.dodopal.product.business.service;

import com.dodopal.api.card.dto.TerminalParameter;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.bean.LoadAndTradeOrder;
import com.dodopal.product.business.model.AutoTerminalParameterResult;
import com.dodopal.product.business.model.query.AutoTerminalParameterQuery;



/**
 * 自助终端用Service接口
 * @author dodopal
 *
 */
public interface AutoTerminalService {

    /**
     * 终端参数下载
     * 
     * @param queryDto
     * @return
     */
    DodopalResponse<AutoTerminalParameterResult> parameterDownload(AutoTerminalParameterQuery queryDto);
    
   /**
    * 退款接口
    * @param tranCode 交易流水号
    * @param source   来源
    * @param userid   用户ID
    * @return
    */
    DodopalResponse<String> sendRefund(String tranCode,String source,String userid);
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年4月12日 上午11:15:02 
      * @version 1.0 
      * @parameter  tradeNum 交易流水号
      * @描述 取交易流水信息和圈存订单 根据交易流水号取圈存订单信息
      * @return  String 圈存订单编号
      */
    DodopalResponse<LoadAndTradeOrder> getLoadOrderAndTradeOrder(String tradeNum);
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年4月12日 上午11:19:49 
      * @version 1.0 
      * @parameter  orderNumber 圈存订单编号
      * @描述 取圈存订单
      * @return String 圈存订单编号 
      */
    LoadAndTradeOrder getLoadOrderNumByLoadNum(String orderNumber);
    
    /**
     * 获取机具参数
     * @param psamno
     * @return
     */
    TerminalParameter findTerminalParameter(String psamno);
    
    
    
}
