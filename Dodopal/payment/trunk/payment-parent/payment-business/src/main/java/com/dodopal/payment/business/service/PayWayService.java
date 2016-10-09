package com.dodopal.payment.business.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.payment.business.model.PayTraTransaction;
import com.dodopal.payment.business.model.PayWay;
import com.dodopal.payment.business.model.Payment;

/**
 * 支付方式查询
 * @author xiongzhijing@dodopal.com
 * @version 2015年8月14日
 */
public interface PayWayService {
   /**
    * 查询外接商户的外接支付方式
    * @param merCode 商户编号
    * @return List<PayWay>
    */
     public  List<PayWay> findPayWayExternal(String merCode);
     
     /**
      * 查询非外接商户的通用支付方式
      * @param payType
      * @return List<PayWay>
      */
     public  List<PayWay> findPayWayGeneral(String payType);
     
     /**
      * 常用支付方式（外接）
      * @param merCode
      * @return List<PayWay>
      */
     public List<PayWay> findCommonExternal(String merCode);
     /**
      * 常用支付方式（非外接）
      * @return List<PayWay>
      */
     public List<PayWay> findCommonGeneral(String userCode);
     
     /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findPayWayByPayType 
     * @Description: merCode为空，查询通用表，不为空查询外接
     * @param merCode
     * @param payType
     * @return    设定文件 
     * List<PayWay>    返回类型 
     * @throws 
     */
    public List<PayWay> findPayWayByPayType(String merCode,String payType);
    
    
 }
