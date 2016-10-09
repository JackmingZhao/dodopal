package com.dodopal.card.business.service;

import java.util.List;

import com.dodopal.card.business.model.CrdSysOrder;
import com.dodopal.card.business.model.CrdSysSupplement;
import com.dodopal.card.business.model.dto.CrdSysOrderQuery;
import com.dodopal.common.model.DodopalDataPage;

public interface QueryCrdSysOrderService {
    /**
     * @Title findCrdSysOrderByPage
     * @Description OSS系统查询卡服务订单
     * @param crdDTO
     * @author 乔
     * @return
     */
    public DodopalDataPage<CrdSysOrder> findCrdSysOrderByPage(CrdSysOrderQuery crdSysOrderQuery);
    /**
     * @Title findCrdSysOrderByCode
     * @Description 根据卡服务订单号查询详情
     * @param crdOrderNum
     * @return
     */
    public CrdSysOrder findCrdSysOrderByCode(String crdOrderNum);
    
    /**
     * @Title findCrdSysOrderByCode
     * @Description 根据卡服务订单号查询详情
     * @param crdOrderNum
     * @return
     */
    public CrdSysSupplement findCrdSysSupplementByCode(String crdOrderNum);
    /**
     * @Title findCrdSysOrderAll
     * @Description 导出卡服务订单内容
     * @return
     */
    public List<CrdSysOrder> findCrdSysOrderAll(CrdSysOrderQuery crdSysOrderQuery);
}
