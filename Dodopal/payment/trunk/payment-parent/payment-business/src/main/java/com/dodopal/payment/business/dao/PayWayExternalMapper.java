package com.dodopal.payment.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dodopal.payment.business.model.PayWayExternal;
import com.dodopal.payment.business.model.query.PayWayQuery;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月28日 上午11:34:45
 */
public interface PayWayExternalMapper {
 
    /**
     * 查询外接支付方式
     * @param query 外接支付方式查询条件封装的实体
     * @return List<PayWayExternal> 外接支付方式实体的list
     */
     public List<PayWayExternal> findPayWayExternalByPage(PayWayQuery query);

     /**
      * 根据外接支付方式id查询外接支付方式的详细信息
      * @param id 外接支付方式id
      * @return PayWayExternal 外接支付方式 实体
      */
     public PayWayExternal findPayExternalById(@Param("id") String id);
     
     /**
      * 添加外接支付方式
      * @param payExternal 外接支付方式实体
      * @return int 
      */
     public int savePayWayExternal(PayWayExternal payExternal);
     
     /**
      * 根据商户编码和支付方式配置信息表id查询表中是否已经存在该支付方式
      * @param merCode 商户编码
      * @param payConfigId  支付方式配置信息表id
      * @return int
      */
     public int countBymerCodeAndPayConfigId(@Param("merCode") String merCode,@Param("payConfigId") String payConfigId);
     
     /**
      * 批量删除 外接支付方式
      * @param ids 外接支付方式id
      * @return int
      */
     public int batchDelPayWayExternalByIds(@Param("ids") List<String> ids);
     
     /**
      * 修改外接支付方式信息
      * @param payExternal 外接支付方式实体
      * @return int
      */
     public int updatePayWayExternal(PayWayExternal payExternal);

     /**
      * 批量启用停用外接支付方式
      * @param activate 启用停用标识
      * @param updateUser 修改人
      * @param ids 外接支付方式id
      * @return int
      */
     public int batchActivateOperator(@Param("activate") String activate, @Param("updateUser") String updateUser, @Param("ids") List<String> ids);
     
     /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: disableByPayConfigId 
     * @Description: 根据支付配置信息id停用
     * @param map
     * @return    设定文件 
     * int    返回类型 
     * @throws 
     */ 
    public int disableByPayConfigId(Map<String ,Object> map);

    /**
     * 根据支付方式id或者支付配置信息id
     * @param payWayId
     * @return
     */
    public PayWayExternal queryPayConfigId(@Param("payWayId") String payWayId);
}
