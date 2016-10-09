package com.dodopal.card.business.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.card.business.dao.CrdSysConsOrderMapper;
import com.dodopal.card.business.dao.CrdSysOrderMapper;
import com.dodopal.card.business.model.Bjtlfhupfiletb;
import com.dodopal.card.business.model.CrdSysConsOrder;
import com.dodopal.card.business.model.CrdSysSupplement;
import com.dodopal.card.business.model.dto.CrdSysOrderQuery;
import com.dodopal.card.business.service.CardConsumeService;
import com.dodopal.card.business.util.BJStringUtil;
import com.dodopal.card.business.util.DateUtil;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.CardOrderStatesEnum;
import com.dodopal.common.enums.CardTxnTypeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalDataPage;

@Service
public class CardConsumeServiceImpl implements CardConsumeService {

    @Autowired
    private CrdSysOrderMapper crdSysOrderMapper;

    @Autowired
    private CrdSysConsOrderMapper crdSysConsOrderMapper;

    @Override
    public int checkCrdConsOrderByPrdId(String prdOrderNum) {
        int count = crdSysConsOrderMapper.checkCrdOrderByPrdId(prdOrderNum);
        return count;
    }

    @Transactional
    @Override
    public String createCrdSysConsOrder(CrdSysOrderDTO crdDTO) {
        //N + 20150428222211 +五位数据库cycle sequence（循环使用）
        StringBuffer crdOrderNum = new StringBuffer();
        crdOrderNum.append("N");
        crdOrderNum.append(DateUtil.getCurrTime());
        crdOrderNum.append(crdSysConsOrderMapper.queryCrdSysConsOrdercodeSeq());
        crdDTO.setCrdordernum(crdOrderNum.toString());

        //插入订单表
        CrdSysConsOrder order = getCreateOrder(crdDTO);
        crdSysConsOrderMapper.insertCrdSysConsOrder(order);

        //插入补充表
        CrdSysSupplement supplement = getCreateSupplement(crdDTO);
        crdSysOrderMapper.insertCrdSysSupplement(supplement);

        return crdOrderNum.toString();
    }

    /**
     * 处理订单表数据
     * @param crdDTO
     * @return
     */
    private CrdSysConsOrder getCreateOrder(CrdSysOrderDTO crdDTO) {

        CrdSysConsOrder order = new CrdSysConsOrder();

        /*
         * 不需要传入的参数
         */
        order.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_APPLY_READCARD_KEY_SUCCESS.getCode());//申请读卡密钥成功
        order.setCrdBeforeorderStates(CardOrderStatesEnum.CARD_ORDER_CREATE_SUCCESS.getCode());//创建成功
        order.setRespCode(ResponseCode.SUCCESS);
        order.setTxnType(Integer.valueOf(CardTxnTypeEnum.TXN_TYPE_CONSUME.getCode()));//消费(2)
        /*
         * 需要传入的参数(已校验过)
         */
        order.setTradeStep(crdDTO.getMessagetype());
        order.setTradeStepVer(BJStringUtil.removeVerZero(crdDTO.getVer()));
        order.setTradeEndFlag(Integer.valueOf(crdDTO.getTradeendflag()));
        order.setCrdOrderNum(crdDTO.getCrdordernum());
        order.setProOrderNum(crdDTO.getPrdordernum());
        order.setProCode(crdDTO.getProductcode());
        order.setMerCode(crdDTO.getMercode());
        order.setMerOrderCode(crdDTO.getMerordercode());
        order.setUserCode(crdDTO.getUsercode());
        order.setUserId(crdDTO.getUserid());
        order.setCityCode(crdDTO.getCitycode());
        order.setYktCode(crdDTO.getYktcode());
        order.setCardInnerNo(crdDTO.getCardinnerno());
        order.setCardFaceNo(crdDTO.getCardfaceno());
        //前台显示的卡面号: ordercardno = (cardinnerno、cardfaceno、checkcardno)其中之一由卡服务决定
        order.setOrderCardNo(crdDTO.getTradecard());
        //验卡卡号(前端读出)对应tradecard
        order.setCardType(Integer.valueOf(crdDTO.getCardtype()));
        order.setPosType(Integer.valueOf(crdDTO.getPostype()));
        order.setPosCode(crdDTO.getPosid());
        if (StringUtils.isNotBlank(crdDTO.getPosseq())) {
            order.setPosSeq(Integer.valueOf(crdDTO.getPosseq()));
        }
        order.setBefbal(Integer.valueOf(crdDTO.getBefbal()));
        order.setTxnAmt(Integer.valueOf(crdDTO.getTxnamt()));
        order.setSource(Integer.valueOf(crdDTO.getSource()));
        order.setTxnSeq(Integer.valueOf(crdDTO.getTxnseq()));
        if (crdDTO.getTxndate().length() > 8) {
            order.setTxnDate(crdDTO.getTxndate().substring(0, 8));
        } else {
            order.setTxnDate(crdDTO.getTxndate());
        }
        if (crdDTO.getTxntime().length() > 6) {
            order.setTxnTime(crdDTO.getTxntime().substring(0, 6));
        } else {
            order.setTxnTime(crdDTO.getTxntime());
        }
        order.setCheckCardNo(crdDTO.getTradecard());
        order.setCheckCardPosCode(crdDTO.getPosid());
        order.setChargeType(Integer.valueOf(crdDTO.getChargetype()));
        if (StringUtils.isNotBlank(crdDTO.getPosseq())) {
            order.setPosSeq(Integer.valueOf(crdDTO.getPosseq()));
        }
        order.setCreateUser(crdDTO.getUserid());
        order.setUpdateUser(crdDTO.getUserid());

        return order;
    }

    /**
     * 处理补充表数据
     * @param crdDTO
     * @return
     */
    private CrdSysSupplement getCreateSupplement(CrdSysOrderDTO crdDTO) {

        CrdSysSupplement supplement = new CrdSysSupplement();

        supplement.setCrdOrderNum(crdDTO.getCrdordernum());//卡服务订单号
        supplement.setTxnType(Integer.valueOf(CardTxnTypeEnum.TXN_TYPE_CONSUME.getCode()));//交易类型 2=消费
        if (crdDTO.getSpecdata() != null) {
            supplement.setCheckYktmw(JSONObject.toJSONString(crdDTO.getSpecdata()));//特属域
        }
        if (crdDTO.getApdu() != null && crdDTO.getApdu().length > 0) {
            //处理apdu数据
            //            supplement.setCheckYktkey(getYktKey(crdDTO.getApdu(), crdDTO.getTxnamt()));
            supplement.setCheckYktkey(JSONObject.toJSONString(crdDTO.getApdu()));
        }
        if (crdDTO.getTxndate().length() > 8) {
            supplement.setTxnDate(crdDTO.getTxndate().substring(0, 8));
        } else {
            supplement.setTxnDate(crdDTO.getTxndate());
        }
        if (crdDTO.getTxntime().length() > 6) {
            supplement.setTxnTime(crdDTO.getTxntime().substring(0, 6));
        } else {
            supplement.setTxnTime(crdDTO.getTxntime());
        }
        supplement.setCreateUser(crdDTO.getUserid());
        supplement.setUpdateUser(crdDTO.getUserid());
        return supplement;
    }

    /**
     * 根据产品库订单号查询卡服务消费订单
     */
    @Override
    public CrdSysConsOrder findCrdSysConsOrderByPrdnum(String prdordernum) {
        CrdSysConsOrder order = crdSysConsOrderMapper.findCrdSysConsOrderByPrdnum(prdordernum);
        return order;
    }

    /**
     * 消费更改订单
     */
    @Transactional
    @Override
    public void updateCrdSysConsOrderByCrdnum(CrdSysOrderDTO crdDTO) {
        CrdSysConsOrder order = new CrdSysConsOrder();
        order.setCrdOrderNum(crdDTO.getCrdordernum());
        order.setProOrderNum(crdDTO.getPrdordernum());
        order.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY.getCode());//更新订单状态为申请充值密钥(1000000005)
        order.setConsumeCardNo(crdDTO.getTradecard());//消费卡号(chargecardno)
        order.setConsumeCardPosCode(crdDTO.getPosid());//消费pos号
        order.setTradeStep(crdDTO.getMessagetype());//交易步骤(tradestep)
        order.setTradeStepVer(BJStringUtil.removeVerZero(crdDTO.getVer()));//交易步骤版本(tradestepver)
        if (StringUtils.isNotBlank(crdDTO.getSecmac())) {
            order.setSecmac(crdDTO.getSecmac());//安全认证码
        }

        CrdSysSupplement crdSysSupplement = new CrdSysSupplement();
        crdSysSupplement.setCrdOrderNum(crdDTO.getCrdordernum());
        String specdate = JSONObject.toJSONString(crdDTO.getSpecdata());//特属域 
        crdSysSupplement.setChargeYktmw(specdate);

        //更新order表
        crdSysConsOrderMapper.updateCrdSysConsOrderByCrdnum(order);
        //更新supplement表
        crdSysOrderMapper.updateCrdSysSupplementByCrdnumBef(crdSysSupplement);
    }

    /**
     * 消费更改订单
     */
    @Transactional
    @Override
    public void updateCrdSysConsOrderAfterByCrdnum(CrdSysOrderDTO crdDTO) {
        CrdSysConsOrder order = new CrdSysConsOrder();
        order.setCrdOrderNum(crdDTO.getCrdordernum());
        order.setProOrderNum(crdDTO.getPrdordernum());
        if (!ResponseCode.SUCCESS.equals(crdDTO.getRespcode())) {
            order.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY_ERROR.getCode());//申请消费密钥失败(1000000006)
        } else {
            order.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY_SUCCESS.getCode());//申请消费密钥成功(1000000007)
        }
        order.setRespCode(crdDTO.getRespcode());//交易应答码(respcode)
        order.setTradeStep(crdDTO.getMessagetype());//交易步骤(tradestep)
        order.setTradeStepVer(BJStringUtil.removeVerZero(crdDTO.getVer()));//交易步骤版本(tradestepver)
        order.setTradeEndFlag(Integer.valueOf(crdDTO.getTradeendflag()));//交易结束标志(tradeendflag)
        order.setCardCounter(crdDTO.getCardcounter());//cardcounter(卡片计数器)

        CrdSysSupplement crdSysSupplement = new CrdSysSupplement();
        crdSysSupplement.setCrdOrderNum(crdDTO.getCrdordernum());
        String apdu = "";
        if (ResponseCode.SUCCESS.equals(crdDTO.getRespcode()) && crdDTO.getApdu().length > 0) {
            apdu = JSONObject.toJSONString(crdDTO.getApdu());
        }
        crdSysSupplement.setChargeKey(apdu);//充值密钥(chargekey)

        //更新order表
        crdSysConsOrderMapper.updateCrdSysConsOrderByCrdnum(order);
        //更新supplement表
        crdSysOrderMapper.updateCrdSysSupplementByCrdnumAfr(crdSysSupplement);
    }

    /**
     * 上传前校验更新订单
     */
    @Transactional
    @Override
    public void updateConsOrderChkUpd(CrdSysConsOrder order, CrdSysSupplement supplement) {
        crdSysConsOrderMapper.updateConsOrderChkUpd(order);
        crdSysConsOrderMapper.updateSupplementChkUpd(supplement);
    }

    /**
     * 上传前更新订单
     * @param order
     */
    @Override
    public void updateConsOrderBefore(CrdSysConsOrder order) {
        crdSysConsOrderMapper.updateConsOrderBefore(order);
    }

    /**
     * 上传后更新订单
     * @param order
     */
    @Override
    public void updateConsOrderAfter(CrdSysConsOrder order) {
        crdSysConsOrderMapper.updateConsOrderAfter(order);
    }

    /**
     * 失败上报更新订单
     */
    @Override
    public void updateConsOrderFailReport(CrdSysConsOrder order) {
        crdSysConsOrderMapper.updateConsOrderFailReport(order);
    }

    /**
     * 查询卡服务消费订单（分页）
     * @param crdSysOrderQuery
     */
    @Transactional
    public DodopalDataPage<CrdSysConsOrder> findCrdSysConsOrderByPage(CrdSysOrderQuery crdSysOrderQuery) {
        List<CrdSysConsOrder> orderByPage = crdSysConsOrderMapper.findCrdSysConsOrderByPage(crdSysOrderQuery);
        DodopalDataPage<CrdSysConsOrder> pages = new DodopalDataPage<CrdSysConsOrder>(crdSysOrderQuery.getPage(), orderByPage);
        return pages;
    }

    /**
     * 查询卡服务消费订单
     * @param crdSysOrderQuery
     */
    @Transactional
    public List<CrdSysConsOrder> findCrdSysConsOrder(CrdSysOrderQuery crdSysOrderQuery) {
        List<CrdSysConsOrder> consOrder = crdSysConsOrderMapper.findCrdSysConsOrder(crdSysOrderQuery);
        return consOrder;
    }

    @Transactional
    @Override
    public void updateBjConsOrder(CrdSysConsOrder order, CrdSysSupplement supplement, Bjtlfhupfiletb tkOrder) {
        int tkCount = crdSysOrderMapper.checkTkOrderCount(tkOrder);
        if (tkCount != 0) {
            throw new DDPException(ResponseCode.CARD_OFFLINE_RECORDS_ERROR, "发现重复通卡订单");
        }
        crdSysConsOrderMapper.updateConsOrderForV61Offline(order);
        crdSysConsOrderMapper.updateSupplementChkUpd(supplement);
        crdSysOrderMapper.insertTkOrder(tkOrder);
    }

    @Override
    public void inserTkOrder(Bjtlfhupfiletb tkOrder) {
        crdSysOrderMapper.insertTkOrder(tkOrder);
    }

}
