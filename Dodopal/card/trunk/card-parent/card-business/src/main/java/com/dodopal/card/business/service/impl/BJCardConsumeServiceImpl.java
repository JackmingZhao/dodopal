package com.dodopal.card.business.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.card.business.dao.CrdSysConsOrderMapper;
import com.dodopal.card.business.dao.CrdSysOrderMapper;
import com.dodopal.card.business.model.CrdSysConsOrder;
import com.dodopal.card.business.model.CrdSysSupplement;
import com.dodopal.card.business.service.BJCardConsumeService;
import com.dodopal.card.business.util.BJStringUtil;
import com.dodopal.card.business.util.DateUtil;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.CardOrderStatesEnum;
import com.dodopal.common.enums.CardTxnTypeEnum;
import com.dodopal.common.util.DateUtils;

@Service
public class BJCardConsumeServiceImpl implements BJCardConsumeService {

    @Autowired
    private CrdSysOrderMapper crdSysOrderMapper;

    @Autowired
    private CrdSysConsOrderMapper crdSysConsOrderMapper;

    @Transactional
    @Override
    public String createCrdSysConsOrder(BJCrdSysOrderDTO crdDTO) {
        //N + 20150428222211 +五位数据库cycle sequence（循环使用）
        StringBuffer crdOrderNum = new StringBuffer();
        crdOrderNum.append("N");
        crdOrderNum.append(DateUtil.getCurrTime());
        crdOrderNum.append(crdSysConsOrderMapper.queryCrdSysConsOrdercodeSeq());
        crdDTO.setCrdordernum(crdOrderNum.toString());

        //插入订单表
        CrdSysConsOrder order = getCreateOrder(crdDTO);
        crdSysConsOrderMapper.insertBjCrdSysConsOrder(order);

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
    private CrdSysConsOrder getCreateOrder(BJCrdSysOrderDTO crdDTO) {

        CrdSysConsOrder order = new CrdSysConsOrder();

        /*
         * 不需要传入的参数
         */
        order.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_APPLY_READCARD_KEY_SUCCESS.getCode());//申请读卡密钥成功03
        order.setCrdBeforeorderStates(CardOrderStatesEnum.CARD_ORDER_CREATE_SUCCESS.getCode());//创建成功01
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
        //        order.setMerOrderCode(crdDTO.getMerordercode());
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

        if (StringUtils.isNotBlank(crdDTO.getCardlogictype())) {
            order.setCardLogicType(crdDTO.getCardlogictype());
        }

        order.setPosTransSeq(BJStringUtil.format10to16(crdDTO.getPostransseq()));

        if (StringUtils.isNotBlank(crdDTO.getKeyindex())) {
            order.setKeyIndex(crdDTO.getKeyindex());
        }
        if (StringUtils.isNotBlank(crdDTO.getAppid())) {
            order.setAppId(crdDTO.getAppid());
        }
        if (StringUtils.isNotBlank(crdDTO.getOfflineflag())) {
            order.setOfflineFlag(crdDTO.getOfflineflag());
        }
        if (StringUtils.isNotBlank(crdDTO.getSamno())) {
            order.setSamNo(crdDTO.getSamno());
        }

        return order;
    }

    /**
     * 处理补充表数据
     * @param crdDTO
     * @return
     */
    private CrdSysSupplement getCreateSupplement(BJCrdSysOrderDTO crdDTO) {

        CrdSysSupplement supplement = new CrdSysSupplement();

        supplement.setCrdOrderNum(crdDTO.getCrdordernum());//卡服务订单号
        supplement.setTxnType(Integer.valueOf(CardTxnTypeEnum.TXN_TYPE_CONSUME.getCode()));//交易类型 2=消费
        if (crdDTO.getSpecdata() != null) {
            supplement.setCheckYktmw(JSONObject.toJSONString(crdDTO.getSpecdata()));//特属域
        }
        if (StringUtils.isNotBlank(crdDTO.getSpecdata().getCipheraction())) {
            supplement.setCheckYktkey(crdDTO.getSpecdata().getCipheraction());
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
        supplement.setRequestReadKeyCount(1);

        String lastReadKeyTime = DateUtils.dateToStrLongs(new Date());
        supplement.setLastReadKeyTime(lastReadKeyTime);

        supplement.setCreateUser(crdDTO.getUserid());
        return supplement;
    }

    /**
     * 消费前更改订单
     */
    @Transactional
    @Override
    public void updateCrdSysConsOrderByCrdnum(BJCrdSysOrderDTO crdDTO) {
        CrdSysConsOrder order = new CrdSysConsOrder();
        order.setCrdOrderNum(crdDTO.getCrdordernum());
        order.setProOrderNum(crdDTO.getPrdordernum());
        order.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY.getCode());//更新订单状态为申请充值密钥(1000000005)
        order.setConsumeCardNo(crdDTO.getTradecard());//消费卡号(chargecardno)
        order.setConsumeCardPosCode(crdDTO.getPosid());//消费pos号
        order.setTradeStep(crdDTO.getMessagetype());//交易步骤(tradestep)
        order.setTradeStepVer(BJStringUtil.removeVerZero(crdDTO.getVer()));//交易步骤版本(tradestepver)
        order.setCardCounter(crdDTO.getCardcounter());//卡计数器
        if (StringUtils.isNotBlank(crdDTO.getSecmac()) && !BJStringUtil.isZeroStr(crdDTO.getSecmac())) {
            order.setSecmac(crdDTO.getSecmac());//安全认证码
        }

        order.setPosTransSeq(BJStringUtil.format10to16(crdDTO.getPostransseq()));

        CrdSysSupplement crdSysSupplement = new CrdSysSupplement();
        crdSysSupplement.setCrdOrderNum(crdDTO.getCrdordernum());
        String specdate = JSONObject.toJSONString(crdDTO.getSpecdata());//特属域 
        crdSysSupplement.setChargeYktmw(specdate);
        String lastChargeKeyTime = DateUtils.dateToStrLongs(new Date());
        crdSysSupplement.setLastChargeKeyTime(lastChargeKeyTime);
        crdSysSupplement.setUpdateUser(crdDTO.getUserid());

        //更新order表
        crdSysConsOrderMapper.updateCrdSysConsOrderByCrdnum(order);
        //更新supplement表
        crdSysOrderMapper.updateCrdSysSupplementByCrdnumBef(crdSysSupplement);
    }

    /**
     * 申请秘钥后更改订单
     */
    @Transactional
    @Override
    public void updateCrdSysConsOrderAfterByCrdnum(BJCrdSysOrderDTO crdDTO) {
        CrdSysConsOrder order = new CrdSysConsOrder();
        order.setCrdOrderNum(crdDTO.getCrdordernum());
        order.setProOrderNum(crdDTO.getPrdordernum());
        CrdSysConsOrder dbOrder = crdSysConsOrderMapper.findCrdSysConsOrderByPrdnum(crdDTO.getPrdordernum());
        //如果订单状态为09,11,19,17不变更卡服务订单状态
        if (CardOrderStatesEnum.CARD_ORDER_RECHARGE_ERROR.getCode().equals(dbOrder.getCrdOrderStates()) || CardOrderStatesEnum.CARD_ORDER_APPLY_REVERSE.getCode().equals(dbOrder.getCrdOrderStates()) || CardOrderStatesEnum.CARD_ORDER_APPLY_REVERSE_SUCCESS.getCode().equals(dbOrder.getCrdOrderStates()) || CardOrderStatesEnum.CARD_ORDER_APPLY_REVERSE_ERROR.getCode().equals(dbOrder.getCrdOrderStates())) {
            order.setCrdOrderStates("");
        } else {
            if (!ResponseCode.SUCCESS.equals(crdDTO.getRespcode())) {
                order.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY_ERROR.getCode());//申请消费密钥失败(1000000006)
            } else {
                order.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY_SUCCESS.getCode());//申请消费密钥成功(1000000007)
            }
        }
        order.setRespCode(crdDTO.getRespcode());//交易应答码(respcode)
        order.setTradeStep(crdDTO.getMessagetype());//交易步骤(tradestep)
        order.setTradeStepVer(BJStringUtil.removeVerZero(crdDTO.getVer()));//交易步骤版本(tradestepver)
        if (StringUtils.isNotBlank(crdDTO.getTradeendflag())) {
            order.setTradeEndFlag(Integer.valueOf(crdDTO.getTradeendflag()));//交易结束标志(tradeendflag)
        }
        //        order.setCardCounter(crdDTO.getCardcounter());//cardcounter(卡片计数器)

        CrdSysSupplement crdSysSupplement = new CrdSysSupplement();
        crdSysSupplement.setCrdOrderNum(crdDTO.getCrdordernum());
        String apdu = "";
        if (ResponseCode.SUCCESS.equals(crdDTO.getRespcode())) {
            apdu = crdDTO.getSpecdata().getCipheraction();
            crdSysSupplement.setChargeKey(apdu);//充值密钥(chargekey)
        }

        //更新order表
        crdSysConsOrderMapper.updateCrdSysConsOrderByCrdnum(order);
        //更新supplement表
        crdSysOrderMapper.updateCrdSysSupplementByCrdnumAfr(crdSysSupplement);
    }
}
