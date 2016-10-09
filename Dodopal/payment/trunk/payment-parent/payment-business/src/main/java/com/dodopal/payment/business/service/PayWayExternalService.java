package com.dodopal.payment.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.payment.business.model.PayConfig;
import com.dodopal.payment.business.model.PayWayExternal;
import com.dodopal.payment.business.model.query.PayWayQuery;

/**
 * @author xiongzhijing@dodopal.com
 * @version 创建时间：2015年8月6日
 */
public interface PayWayExternalService {

    /**
     * 查询外接支付方式（分页）
     * @param payExterDTO 查询条件封装的实体
     * @return DodopalDataPage<PayWayExternal>
     */
    public DodopalDataPage<PayWayExternal> findPayWayExternalByPage(PayWayQuery payExterDTO);
 
    /**
     * 保存外接支付方式
     * @param payExternal 外接支付方式对应的实体
     * @return  int
     */
    public int savePayWayExternal(PayWayExternal payExternal);
 
    /**
     * 修改外接支付方式
     * @param payExternal 外接支付方式对应的实体
     * @return int
     */
    public int updatePayWay(PayWayExternal payExternal);

 
    /**
     * 批量删除外接支付方式
     * @param ids  外接支付方式的ID
     * @return int
     */
    public int batchDelPayExternal(List<String> ids);

    /**
     * 批量修改启用停用标示
     * @param activate 启用停用标识
     * @param updateUser 修改人
     * @param ids 外接支付方式id
     * @return int
     */
    public int batchActivateOperator(String activate, String updateUser, List<String> ids);
 
    /**
     * 根据id查询外接支付方式
     * @param id 外接支付方式id
     * @return PayWayExternal  外接支付方式实体
     */
    public PayWayExternal findPayExternalById(String id);

  
    /**
     * 查询所有支付方式名称根据支付类型
     * @param payType 支付类型
     * @return List<PayConfig> 支付方式实体的LIST
     */
    public List<PayConfig> findPayWayNameByPayType(String payType);
  
    /**
     * 根据商户编码和支付方式配置表id 查询外接支付表中是否已存在该支付方式
     * @param merCode 商户编号
     * @param payConfigId 支付方式配置表id
     * @return int 
     */
    public int countBymerCodeAndPayConfigId(String merCode,String payConfigId);
    /**
     * 根据支付方式id或者支付配置信息id
     * @param payWayId
     * @return
     */
    public PayWayExternal queryPayConfigId(String payWayId);

}
