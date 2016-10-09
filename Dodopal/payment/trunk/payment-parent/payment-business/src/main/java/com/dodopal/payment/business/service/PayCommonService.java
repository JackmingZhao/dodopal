package com.dodopal.payment.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.payment.business.model.PayWayCommon;
import com.dodopal.payment.business.model.query.PayCommonQuery;


/**
 * @author hxc
 * @version 创建时间：2015年8月11日
 */
public interface PayCommonService {

    /**
     * 查询
     * @param query
     * @return DodopalResponse<DodopalDataPage<PayWayCommon>>
     */
    public DodopalDataPage<PayWayCommon> findPayCommonByPage(PayCommonQuery query);
    /**
     * 删除
     * @param ids
     * @return int
     */
    public int deletePayCommonByIds(List<String> ids);
    
    /**
     * 新增
     * @param commons
     * @return int
     */
    public List<Integer> insertPayWayCommon(List<PayWayCommon> commons);
    
    /**
     * 删除
     * @return int
     */
    public int deletePaywayCommon(String userCode);
    
    /**
     * 根据payWayId 查询用户常用支付方式id(关联的是外接支付方式)
     * @param payWayIds
     * @return
     */
    public List<String> findPaywayCommonByPayWayId(String payWayIds);
}
