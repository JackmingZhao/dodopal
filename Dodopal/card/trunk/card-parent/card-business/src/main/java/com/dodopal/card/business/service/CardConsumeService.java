package com.dodopal.card.business.service;

import java.util.List;

import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.card.business.model.Bjtlfhupfiletb;
import com.dodopal.card.business.model.CrdSysConsOrder;
import com.dodopal.card.business.model.CrdSysSupplement;
import com.dodopal.card.business.model.dto.CrdSysOrderQuery;
import com.dodopal.common.model.DodopalDataPage;

public interface CardConsumeService {

    /*
     * 根据产品库订单号查询卡服务订单数量
     */
    public int checkCrdConsOrderByPrdId(String prdOrderNum);

    /*
     * 创建卡服务订单
     */
    public String createCrdSysConsOrder(CrdSysOrderDTO crdDTO);

    /**
     * 根据产品库订单号查询卡服务订单
     * @param prdordernum
     * @return
     */
    public CrdSysConsOrder findCrdSysConsOrderByPrdnum(String prdordernum);

    /**
     * 根据卡服务订单号更改卡服务订单
     * @param crdSysOrder
     * @return
     */
    public void updateCrdSysConsOrderByCrdnum(CrdSysOrderDTO crdDTO);

    /**
     * 根据卡服务订单号更改卡服务订单
     * @param crdSysOrder
     * @return
     */
    public void updateCrdSysConsOrderAfterByCrdnum(CrdSysOrderDTO crdDTO);

    /**
     * 上传校验更新订单
     * @param crdDTO
     */
    public void updateConsOrderChkUpd(CrdSysConsOrder order, CrdSysSupplement supplement);

    /*
     * V61脱机上传更新订单
     */
    public void updateBjConsOrder(CrdSysConsOrder order, CrdSysSupplement supplement, Bjtlfhupfiletb tkOrder);
    
    /**
     * 插入通卡订单表
     * @param tkOrder
     */
    public void inserTkOrder(Bjtlfhupfiletb tkOrder);

    /**
     * 上传前更新订单
     * @param order
     */
    public void updateConsOrderBefore(CrdSysConsOrder order);

    /**
     * 上传后更新订单
     * @param order
     */
    public void updateConsOrderAfter(CrdSysConsOrder order);

    /**
     * 失败上报更新订单
     * @param order
     */
    public void updateConsOrderFailReport(CrdSysConsOrder order);

    /**
     * 查询卡服务消费订单（分页）
     */
    public DodopalDataPage<CrdSysConsOrder> findCrdSysConsOrderByPage(CrdSysOrderQuery crdSysOrderQuery);

    /**
     * 查询卡服务消费订单
     */
    public List<CrdSysConsOrder> findCrdSysConsOrder(CrdSysOrderQuery crdSysOrderQuery);
}
