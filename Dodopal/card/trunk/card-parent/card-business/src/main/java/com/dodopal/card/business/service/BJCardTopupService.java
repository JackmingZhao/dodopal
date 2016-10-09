package com.dodopal.card.business.service;

import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.api.card.dto.ReslutData;
import com.dodopal.api.card.dto.ReslutDataParameter;
import com.dodopal.card.business.model.Bjtlfhupfiletb;
import com.dodopal.card.business.model.SamSignInOffTb;
import com.dodopal.card.business.model.TlpPosMenuTb;

public interface BJCardTopupService {

    /**
     * 创建卡服务订单
     */
    public String createCrdSysOrder(BJCrdSysOrderDTO crdDTO);

    /**
     * 申请圈存前修改订单
     */
    public void updateCrdSysOrderByCrdnum(BJCrdSysOrderDTO crdDTO);

    /**
     * 申请圈存后修改订单
     */
    public void updateCrdSysOrderAfterByCrdnum(BJCrdSysOrderDTO crdDTO);

    /*
     * 上传前更新订单状态
     */
    public String uploadResultChkUdpOrderFun(BJCrdSysOrderDTO crdDTO);

    /**
     * 查询签到签退表数据
     * @param posId
     * @return
     */
    public SamSignInOffTb querySamInOffTbByPosId(String posId);

    /**
     * 查询菜单表数据
     * @param posId
     * @return
     */
    public TlpPosMenuTb queryTlpPosMenuTb(String samNo);

    /**
     * 查询菜单数据(消费)
     * @param samNo
     * @return
     */
    public TlpPosMenuTb queryTlpPosMenuTbConsBySamNo(String samNo);

    /**
     * 查询商户折扣信息
     * @param posId
     * @return
     */
    public String queryMerDiscountByMerCode(String merCode);

    /**
     * 批量结果上传
     * @param parameter
     * @return
     */
    public String batchUploadResult(ReslutDataParameter parameter);

    /*
     * 圈存初始化
     */
    public BJCrdSysOrderDTO getLoadInitFun(BJCrdSysOrderDTO crdDTO);

    /*
     * 查询商户号
     */
    public String queryMerCodeByPosNo(String posNo);

    /*
     * 查询脱机信息
     */
    public String queryOfflineBySamNo(String samNo, String cardPhyType, String cardType);

    /*
     * 校验脱机金额
     */
    public String checkOfflineAmt(String offlineType, String samNo, String txnAmt);

    /*
     * 更新通讯流水号
     */
    public void updateSamsigninfo(String posId, String posIcSeq, String posTransSeq);

    /*
     * 拼装秘钥数据
     */
    public String queryKeyset(String merCode);

    /*
     * 拼装通卡订单
     */
    public Bjtlfhupfiletb fixTkOrder(ReslutData reslutData);

    /*
     * 生成V71订单
     */
    public String createCrdSysOrderForV71(BJCrdSysOrderDTO crdDTO);

}
