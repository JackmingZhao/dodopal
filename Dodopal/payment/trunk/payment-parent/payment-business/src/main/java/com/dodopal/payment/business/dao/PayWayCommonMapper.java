package com.dodopal.payment.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.payment.business.model.PayWayCommon;
import com.dodopal.payment.business.model.query.PayCommonQuery;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月28日 上午11:25:03
 */
public interface PayWayCommonMapper {

    /**
     * 查询
     * @param payWayQuery
     * @return
     */
    public List<PayWayCommon> findPayWayCommonByPage(PayCommonQuery payCommonQuery);
    
    /**
     * 根据Id删除
     * @param ids
     * @return
     */
    public int deletePayWayCommon(@Param("ids") List<String> ids);
    
    /**
     * 添加
     * @param common
     * @return int
     */
    public int insertPayWayCommon(PayWayCommon common);
    
    /**
     * 根据userCode删除
     * @param userCode
     * @return int
     */
    public int deletePaywayCommon(String userCode);
    /**
     * @description 查询通用表数据信息
     * @return List<PayWayCommon>
     */
    public List<PayWayCommon> queryPayCommonList(@Param("userId")String userId);
    //更改常用支付表信息
    public int updateCommonInfo(@Param("payCommonId")String payCommonId);
    
    /**
     * 根据payWayId查询用户常用支付方式id (用户常用支付方式关联的是外接支付方式)
     * @param payWayIds
     * @return
     */
    public List<String> findPaywayCommonByPayWayId(@Param("payWayId")String payWayId);
}
