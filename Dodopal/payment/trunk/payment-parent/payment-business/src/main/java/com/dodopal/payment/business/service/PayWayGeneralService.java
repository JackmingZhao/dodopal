package com.dodopal.payment.business.service;

import java.util.List;

import com.dodopal.common.enums.PayAwayEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.payment.business.model.PayConfig;
import com.dodopal.payment.business.model.PayWayExternal;
import com.dodopal.payment.business.model.PayWayGeneral;
import com.dodopal.payment.business.model.query.PayWayQuery;

/**
 * 
 * @author hxc
 *
 */
public interface PayWayGeneralService {

    /**
     * 启用停用
     * @param flag 启用标识
     * @param ids
     * @param updateUser
     * @return
     */
    public int batchStartOrStop(String flag,List<String> ids,String updateUser);
    
    /**
     * 新增
     * @param general
     */
    public int savePayWayGeneral(PayWayGeneral general);  
    
    /**
     * 删除
     * @param ids
     * @return
     */
    public int deletePayWayGeneral(List<String> ids);
    
    /**
     * 查询
     * @param payWayQuery
     * @return
     */
    public DodopalDataPage<PayWayGeneral> findPayWayGeneralByPage(PayWayQuery payWayQuery);
    
    /**
     * 修改
     * @param general
     * @return
     */
    public int updatePayWayGeneral(PayWayGeneral general);
    /**
     * @Description: 根据id查询
     * @param id
     * @return PayWayGeneral
     */
    public PayWayGeneral findPayGeneralById(String id);
    
    /**
     * @Title: findPayWayNameByPayType
     * @Description: 查询所有支付方式名称根据支付类型
     * @param String payType
     * @return List<PayConfigDTO>
     */
    public List<PayConfig> findPayWayNameByPayType(String payType);
    /**
     * 查询记录数
     * @param payConfigId
     * @return 记录数
     */
    public int countByPayConfigId(String payConfigId);
    /**
     * 根据支付方式id或者支付配置信息id
     * @param payWayId
     * @return
     */
    public PayWayGeneral queryPayConfigId(String payWayId);
    
    /**
     * 根据支付方式id或者支付配置信息id
     * @param payWayId 支付方式id（通用）
     * @return
     */
    public PayWayGeneral  queryPayConfigIdByPayWayId(String payWayId);
}
