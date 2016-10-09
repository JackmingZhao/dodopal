package com.dodopal.card.business.service;

import java.util.Map;

import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.card.business.model.CrdSysOrder;

public interface CardTopupService {

    /**
     * @Title: queryCheckCardSend
     * @Description: 调用此方法向城市前置发送查询验卡阶段报文
     * @param
     * @return
     */
    public CrdSysOrderDTO queryCheckCardSendFun(String ip, int port, CrdSysOrderDTO crdDTO);

    /**
     * @Title: applyRechargeKey
     * @Description: 调用此方法向城市前置发送充值密钥申请阶段报文
     * @param
     * @return
     */
    public CrdSysOrderDTO rechargeKeySendFun(String ip, int port, CrdSysOrderDTO crdDTO);

    /**
     * @Title checkCrdOrderByPrdId
     * @Description 校验产品库订单号查询卡服务订单是否存在
     * @param prdordernum
     * @return
     */
    public int checkCrdOrderByPrdId(String prdordernum);

    /**
     * 根据yktcode获得访问城市前置具体的ip地址和端口号
     * @param map
     * @return
     */
    public Map<String, Object> queryYktIpAndPort(String yktCode);
    
    /**
     * 根据cityCode获得访问城市前置具体的ip地址和端口号
     * @param yktCode
     * @return
     */
    public Map<String, Object> queryCityIpAndPort(String cityCode);

    /**
     * 创建卡服务订单
     * @param crdDTO
     * @return
     */
    public String createCrdSysOrder(CrdSysOrderDTO crdDTO);

    /**
     * 通过传入的产品库订单号，找到对应的卡服务订单记录，没找到进行异常处理
     * @param prdordernum
     * @return
     */
    public String checkPrdnumExistByid(String prdordernum);

    /**
     * 根据产品库订单号查询卡服务订单
     * @param prdordernum
     * @return
     */
    public CrdSysOrder findCrdSysOrderByPrdnum(String prdordernum);

    /**
     * 根据卡服务订单号更改卡服务订单
     * @param crdSysOrder
     * @return
     */
    public void updateCrdSysOrderByCrdnum(CrdSysOrderDTO crdDTO);

    /**
     * 申请圈存后修改订单
     * @param crdDTO
     */
    public void updateCrdSysOrderAfterByCrdnum(CrdSysOrderDTO crdDTO);

    /**
     * 重新获取充值密钥
     * @param crdnum
     */
    public String reSendOrderByCrdnum(String crdnum);

    /**
     * 更新重发次数
     * @param crdnum
     */
    public void updateKeyCountByCrdnum(String crdnum);
    
    /*
     * 更新卡服务pos通讯流水号
     */
    public void updatePosTranSeqByCrdnum(String crdnum,String posTranSeq);
    /*
     * 更新卡服务pos通讯流水号(消费)
     */
    public void updatePosTranSeqConsByCrdnum(String crdnum,String posTranSeq);
}
