package com.dodopal.card.business.dao;

import java.util.List;

import com.dodopal.card.business.model.CrdSys100000;
import com.dodopal.card.business.model.CrdSysConsOrder;
import com.dodopal.card.business.model.CrdSysSupplement;
import com.dodopal.card.business.model.dto.CrdSysOrderQuery;

public interface CrdSysConsOrderMapper {

    /**
     * 根据产品库订单号查询卡服务订单条数
     * @param prdordernum
     * @return
     */
    public int checkCrdOrderByPrdId(String prdordernum);

    /**
     * 查询卡服务循环序列
     * @return
     */
    public String queryCrdSysConsOrdercodeSeq();

    /**
     * 插入卡服务消费订单表
     * @param crdSysOrder
     */
    public void insertCrdSysConsOrder(CrdSysConsOrder crdSysConsOrder);

    /**
     * 插入卡服务消费订单表/北京脱机
     * @param crdSysConsOrder
     */
    public void insertCrdSysConsOrderOfflineForV71(CrdSysConsOrder crdSysConsOrder);

    /**
     * 插入卡服务补充表/北京脱机
     * @param crdSysConsOrder
     */
    public void insertSupplementOfflineForV71(CrdSysSupplement supplement);
    
    /**
     * 插入卡服务北京补充表
     * @param sys100000
     */
    public void insertCrdSys100000(CrdSys100000 sys100000);

    public void insertBjCrdSysConsOrder(CrdSysConsOrder crdSysConsOrder);

    /**
     * 根据产品库订单号查询卡服务订单
     * @param prdordernum
     * @return
     */
    public CrdSysConsOrder findCrdSysConsOrderByPrdnum(String prdordernum);

    /**
     * 根据卡服务订单号更改卡服务订单
     * @param order
     * @return
     */
    public void updateCrdSysConsOrderByCrdnum(CrdSysConsOrder order);

    /**
     * 上传前校验更新订单
     * @param crdDTO
     */
    public int updateConsOrderChkUpd(CrdSysConsOrder order);
    
    /**
     * v61脱机消费更新订单
     * @param order
     * @return
     */
    public int updateConsOrderForV61Offline(CrdSysConsOrder order);

    /*
     * 北京V61脱机消费上传更新订单
     */
    public int uploadOfflineUpdOrder(CrdSysConsOrder order);

    /**
     * 上传前校验更新补充表
     * @param crdDTO
     */
    public void updateSupplementChkUpd(CrdSysSupplement supplement);

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
     * 前端失败上报更新订单
     * @param order
     */
    public void updateConsOrderFailReport(CrdSysConsOrder order);

    /**
     * 查询卡服务消费订单（分页）
     * @param crdSysOrderQuery
     */
    public List<CrdSysConsOrder> findCrdSysConsOrderByPage(CrdSysOrderQuery crdSysOrderQuery);

    /**
     * 查询卡服务消费订单
     * @param crdSysOrderQuery
     */
    public List<CrdSysConsOrder> findCrdSysConsOrder(CrdSysOrderQuery crdSysOrderQuery);
}
