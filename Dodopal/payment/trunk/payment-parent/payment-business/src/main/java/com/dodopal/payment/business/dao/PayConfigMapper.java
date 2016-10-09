package com.dodopal.payment.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dodopal.payment.business.model.PayConfig;
import com.dodopal.payment.business.model.query.PayConfigQuery;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月28日 下午1:39:54
 */
public interface PayConfigMapper {
  public void savePayConfig(PayConfig payConfig);
  
  public  List<PayConfig> findPayConfigByPage(PayConfigQuery query);
  
  public PayConfig findPayConfigById(String id);
  
  public Integer findPayConfigByAnother(@Param("anotherAccountCode")String anotherCode,@Param("payType")String payType);
  
  public int startOrStopPayConfig (Map<String ,Object> map);
  
  public int updatePayConfig(PayConfig payConfig);
  
  public int updatePayConfigBankGateway(PayConfig payConfig);
  
  public  List<PayConfig> findPayConfigPayType(String payType);

  public int findPayConfigByPayWayName(@Param("payWayName")String payWayName,@Param("id")String id);
  //根据tranCode 查询支付配置信息
  public PayConfig queryPayInfo(@Param("tranCode") String tranCode);
  //根据payWayId(支付方式id)查询支付配置信息
  public PayConfig queryPayInfoByPayWayId(@Param("payWayId") String payWayId);
}
