package com.dodopal.card.business.dao;

import org.apache.ibatis.annotations.Param;

import com.dodopal.card.business.model.CrdSysSupplement;

public interface CrdSysSupplementMapper {

    /**
     * @Title findCrdSysOrderByCode
     * @Description 根据卡服务订单号查询卡服务补充信息详情
     * @param crdOrderNum
     * @return
     */
    public CrdSysSupplement findCrdSysSupplementByCode(@Param("crdOrderNum") String crdOrderNum);
}
