package com.dodopal.card.business.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dodopal.card.business.model.CrdSysOrder;
import com.dodopal.card.business.model.CrdSysSupplement;

public interface IcdcRechargeMapper {
    public Integer queryCrdSysOrderCountByPrdOrderId(@Param("PRO_ORDER_NUM") String prdOrderNum);

    public CrdSysOrder queryCardSysOrderByPrdOrderId(@Param("PRO_ORDER_NUM") String prdOrderNum);

    public CrdSysOrder queryBJCardSysOrderByPrdOrderId(@Param("PRO_ORDER_NUM") String prdOrderNum);

    public CrdSysSupplement queryCrdSysSupplementByCrdOrderId(@Param("CRD_ORDER_NUM") String crdOrderNum);

    public int updateCardSysOrderByCrdOrderId(Map<String, Object> value);

    public int updateCardSysSupplementByCrdOrderId(Map<String, Object> value);

    public int updateCardSysOrderBackAmtByCrdOrderId(@Param("CRD_ORDER_NUM") String crdOrderId);

    public int updateResetCardSysOrderBackAmtByCrdOrderId(@Param("CRD_ORDER_NUM") String crdOrderId);

    public int updateClearCardSysOrderBackAmtByCrdOrderId(@Param("CRD_ORDER_NUM") String crdOrderId);

    public String queryCardSysOrderStatusByPrdId(@Param("PRO_ORDER_NUM") String prdOrderNum);

    public CrdSysOrder queryCardSysOrderByCrdOrderId(@Param("CRD_ORDER_NUM") String crdOrderNum);
}
