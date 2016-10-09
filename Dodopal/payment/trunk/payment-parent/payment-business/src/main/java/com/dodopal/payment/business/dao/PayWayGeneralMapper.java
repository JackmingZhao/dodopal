package com.dodopal.payment.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dodopal.payment.business.model.PayWayExternal;
import com.dodopal.payment.business.model.PayWayGeneral;
import com.dodopal.payment.business.model.query.PayWayQuery;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月28日 上午11:39:49
 */
public interface PayWayGeneralMapper {
    /**
     * 保存
     * @param general
     */
    public int savePayWayGeneral(PayWayGeneral general); 
    
    /**
     * 查询
     * @param payWayQuery
     * @return
     */
    public List<PayWayGeneral> findPayWayGeneralByPage(PayWayQuery payWayQuery);
    
    
    /**
     * 删除
     */
    public int deletePayWayGeneral(@Param("ids") List<String> ids);
    
    /**
     * 修改
     */
    public int updatePayWayGeneral(PayWayGeneral general);
    
    
    /**
     * 启用或停用
     */
    
    public int startOrStopUser(@Param("activate") String activate, @Param("updateUser") String updateUser, @Param("ids") List<String> ids); 
    
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
     * 返回修改页面信息
     * @param id
     * @return
     */
    public PayWayGeneral findPayWayGeneralById(@Param("id") String id);
    
    public int countByPayConfigId(@Param("payConfigId") String payConfigId);
    /**
     * 根据支付方式id或者支付配置信息id
     * @param payWayId
     * @return
     */
    public PayWayGeneral queryPayConfigId(@Param("payWayId") String payWayId);
    
    /**
     * 根据支付方式id或者支付配置信息id
     * @param payWayId 支付方式id
     * @return
     */
    public PayWayGeneral queryPayConfigIdByPayWayId(@Param("payWayId") String payWayId);

}
