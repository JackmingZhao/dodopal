package com.dodopal.card.business.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.card.business.constant.CardConstants;
import com.dodopal.card.business.dao.CrdSysOrderMapper;
import com.dodopal.card.business.dao.CrdSysSupplementMapper;
import com.dodopal.card.business.model.CrdSysOrder;
import com.dodopal.card.business.model.CrdSysSupplement;
import com.dodopal.card.business.service.CardTopupService;
import com.dodopal.card.business.service.CityFrontSocketService;
import com.dodopal.card.business.util.BJStringUtil;
import com.dodopal.card.business.util.DateUtil;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.CardOrderStatesEnum;
import com.dodopal.common.enums.CardTradeEndFlagEnum;
import com.dodopal.common.enums.CardTxnTypeEnum;
import com.dodopal.common.util.DateUtils;

@Service
public class CardTopupServiceImpl implements CardTopupService {
    @Autowired
    private CrdSysOrderMapper crdSysOrderMapper;

    @Autowired
    private CrdSysSupplementMapper crdSysSupplementMapper;

    @Autowired
    private CityFrontSocketService cityFrontSocketService;

    /**
     * 创建卡服务订单
     * @param crdDTO
     * @return
     */
    @Transactional
    @Override
    public String createCrdSysOrder(CrdSysOrderDTO crdDTO) {

        //获取卡服务订单号   P + 20150428222211 +五位数据库cycle sequence（循环使用）
        StringBuffer crdOrderNum = new StringBuffer();
        crdOrderNum.append("P");
        crdOrderNum.append(DateUtil.getCurrTime());
        crdOrderNum.append(crdSysOrderMapper.queryCrdSysOrdercodeSeq());
        crdDTO.setCrdordernum(crdOrderNum.toString());

        //插入订单表
        CrdSysOrder order = getCreateOrder(crdDTO);
        crdSysOrderMapper.insertCrdSysOrder(order);
        crdDTO.setMessagetype("2012");

        //插入补充表
        CrdSysSupplement supplement = getCreateSupplement(crdDTO);
        crdSysOrderMapper.insertCrdSysSupplement(supplement);

        return crdOrderNum.toString();
    }

    /*
     * 校验产品库订单号查询卡服务订单是否存在
     * @Description: 校验产品库订单号查询卡服务订单是否存在
     * @see com.dodopal.card.business.service.CardRechargeService#checkCrdOrderByPrdId(java.lang.String)
     */
    @Override
    public int checkCrdOrderByPrdId(String proOrderNum) {
        int count = crdSysOrderMapper.checkCrdOrderByPrdId(proOrderNum);
        return count;
    }

    /**
     * @Description: 通过socket完成json格式的查询验卡阶段报文发送
     */
    @Override
    public CrdSysOrderDTO queryCheckCardSendFun(String ip, int port, CrdSysOrderDTO crdDTO) {
        CrdSysOrderDTO retCrdDTO = cityFrontSocketService.sendCityFrontSocket(ip, port, crdDTO, CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        return retCrdDTO;
    }

    /**
     * @Description: 通过socket完成json格式的充值密钥申请阶段报文发送
     */
    @Override
    public CrdSysOrderDTO rechargeKeySendFun(String ip, int port, CrdSysOrderDTO crdDTO) {
        CrdSysOrderDTO retCrdDTO = cityFrontSocketService.sendCityFrontSocket(ip, port, crdDTO, CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        return retCrdDTO;
    }

    /**
     * 根据yktcode获得访问城市前置具体的ip地址和端口号
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> queryYktIpAndPort(String yktCode) {
        Map<String, Object> retMap = crdSysOrderMapper.queryYktIpAndPort(yktCode);
        return retMap;
    }

    /**
     * 根据cityCode获得访问城市前置具体的ip地址和端口号
     */
    @Override
    public Map<String, Object> queryCityIpAndPort(String cityCode) {
        Map<String, Object> retMap = crdSysOrderMapper.queryCityIpAndPort(cityCode);
        return retMap;
    }

    /**
     * 处理补充表数据
     * @param crdDTO
     * @return
     */
    private CrdSysSupplement getCreateSupplement(CrdSysOrderDTO crdDTO) {

        CrdSysSupplement supplement = new CrdSysSupplement();

        supplement.setCrdOrderNum(crdDTO.getCrdordernum());//卡服务订单号
        supplement.setTxnType(Integer.valueOf(CardTxnTypeEnum.TXN_TYPE_RECHARGE.getCode()));//交易类型 1-充值,0-查询
        if (crdDTO.getSpecdata() != null) {
            supplement.setCheckYktmw(JSONObject.toJSONString(crdDTO.getSpecdata()));//特属域
        }
        if (crdDTO.getApdu() != null && crdDTO.getApdu().length > 0) {
            //处理apdu数据
            supplement.setCheckYktkey(getYktKey(crdDTO.getApdu(), crdDTO.getTxnamt()));
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

    private String getYktKey(String[] apdu, String txnamt) {
        String str = "";
        for (int i = 0; i < apdu.length; i++) {
            //查找apdu数组定位开头为8050的数组元素
            if (apdu[i].startsWith("8050")) {
                //圈存初始化指令中交易金额位置为12为开始截取后8位,上传交易金额进行16进制的转化
                String reAmt = Integer.toHexString(Integer.valueOf(txnamt));
                //补齐8位
                while (reAmt.length() < 8) {
                    reAmt = "0" + reAmt;
                }
                //替换到圈存初始化指令中的交易金额的位置
                StringBuffer strBuffer = new StringBuffer();
                strBuffer.append(apdu[i].substring(0, 12));
                strBuffer.append(reAmt);
                strBuffer.append(apdu[i].substring(20, apdu[i].length()));
                apdu[i] = strBuffer.toString();
            }
        }
        str = JSONObject.toJSONString(apdu);
        return str;
    }

    /**
     * 处理订单表数据
     * @param crdDTO
     * @return
     */
    private CrdSysOrder getCreateOrder(CrdSysOrderDTO crdDTO) {

        CrdSysOrder order = new CrdSysOrder();

        /*
         * 不需要传入的参数
         */
        order.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_CREATE_SUCCESS.getCode());//创建成功
        order.setCrdBeforeorderStates(CardOrderStatesEnum.CARD_ORDER_CREATE_SUCCESS.getCode());//创建成功
        order.setRespCode(ResponseCode.SUCCESS);
        order.setTxnType(Integer.valueOf(CardTxnTypeEnum.TXN_TYPE_RECHARGE.getCode()));//充值(1)
        order.setTradeStep("2011");
        order.setTradeStepVer("1");
        order.setTradeEndFlag(Integer.valueOf(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode()));//交易结束(1)
        /*
         * 需要传入的参数(已校验过)
         */
        order.setCrdOrderNum(crdDTO.getCrdordernum());
        order.setProOrderNum(crdDTO.getPrdordernum());
        order.setProCode(crdDTO.getProductcode());
        order.setMerCode(crdDTO.getMercode());
        /*        //loadflag为0时,MER_ORDER_CODE存圈存订单号
                if (LoadFlagEnum.LOAD_YES.getCode().equals(crdDTO.getLoadflag())) {
                    order.setMerOrderCode(crdDTO.getLoadordernum());
                } else {//loadflag为1时,MER_ORDER_CODE存商户订单号
                    order.setMerOrderCode(crdDTO.getMerordercode());
                }*/
        //有圈存订单号时,MER_ORDER_CODE存圈存订单号
        if (StringUtils.isNotBlank(crdDTO.getLoadordernum())) {
            order.setMerOrderCode(crdDTO.getLoadordernum());
        } else {
            order.setMerOrderCode(crdDTO.getMerordercode());
        }
        order.setUserCode(crdDTO.getUsercode());
        order.setUserId(crdDTO.getUserid());
        order.setCityCode(crdDTO.getCitycode());
        order.setYktCode(crdDTO.getYktcode());
        order.setCardInnerNo(crdDTO.getCardinnerno());
        order.setCardFaceNo(crdDTO.getCardfaceno());
        //前台显示的卡面号: ordercardno = (cardinnerno、cardfaceno、checkcardno)其中之一由卡服务决定
        order.setOrderCardNo(crdDTO.getTradecard());
        //验卡卡号(前端读出)对应tradecard
        order.setCardType(Long.valueOf(crdDTO.getCardtype()));
        order.setPosType(Long.valueOf(crdDTO.getPostype()));
        order.setPosCode(crdDTO.getPosid());
        if (StringUtils.isNotBlank(crdDTO.getPosseq())) {
            order.setPosSeq(Integer.valueOf(crdDTO.getPosseq()));
        }
        order.setBefbal(Long.valueOf(crdDTO.getBefbal()));
        order.setTxnAmt(Long.valueOf(crdDTO.getTxnamt()));
        order.setCardLimit(Long.valueOf(crdDTO.getCardlimit()));
        order.setSource(Long.valueOf(crdDTO.getSource()));
        order.setTxnSeq(Long.valueOf(crdDTO.getTxnseq()));
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
     * 通过传入的产品库订单号，找到对应的卡服务订单记录
     * @param prdordernum
     * @return
     */
    @Override
    public String checkPrdnumExistByid(String prdordernum) {
        String prdExist = "";
        int count = crdSysOrderMapper.checkCrdOrderByPrdId(prdordernum);
        if (count == 1) {
            prdExist = ResponseCode.SUCCESS;
        } else if (count == 0) {
            prdExist = ResponseCode.CARD_PRDORDERNUM_NOT_EXIST;
        } else {
            prdExist = ResponseCode.SYSTEM_ERROR;
        }
        return prdExist;
    }

    /**
     * 根据产品库订单号查询卡服务订单
     * @param prdordernum
     * @return
     */
    @Override
    public CrdSysOrder findCrdSysOrderByPrdnum(String prdordernum) {
        CrdSysOrder order = crdSysOrderMapper.findCrdSysOrderByPrdnum(prdordernum);
        return order;
    }

    /**
     * 根据卡服务订单号更改卡服务订单
     * @Description: 申请充值密钥前后更新订单状态
     * 1.当开始标志为交易开始时:卡服务校验订单成功后调用此方法，根据卡服务订单号更新订单状态为申请充值密钥
     * (1000000005),前卡服务订单状态:申请读卡密钥成功(1000000003),充值卡号(chargecardno),特属域 ;否则透传数据
     * 2.当结束标志为结束时，卡服务调用城市前置充值申请接口后
     * ，需要更新卡服务订单状态：申请充值密钥成功(1000000007)或申请充值密钥失败(1000000006),
     * 前卡服务订单状态：申请充值密钥(1000000005), 交易应答码(respcode), 交易步骤(tradestep),
     * 交易步骤版本(tradestepver), 交易结束标志(tradeendflag), 充值密钥(chargekey),
     * cardcounter(卡片计数器);否则透传数据
     * @param crdSysOrder
     * @return
     */
    @Transactional
    @Override
    public void updateCrdSysOrderByCrdnum(CrdSysOrderDTO crdDTO) {
        CrdSysOrder crdSysOrder = new CrdSysOrder();
        crdSysOrder.setCrdOrderNum(crdDTO.getCrdordernum());
        crdSysOrder.setProOrderNum(crdDTO.getPrdordernum());
        crdSysOrder.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY.getCode());//更新订单状态为申请充值密钥(1000000005)
        crdSysOrder.setChargeCardNo(crdDTO.getTradecard());//充值卡号(chargecardno)
        crdSysOrder.setChargeCardPosCode(crdDTO.getPosid());//充值pos号
        crdSysOrder.setTradeStep(crdDTO.getMessagetype());//交易步骤(tradestep)
        crdSysOrder.setTradeStepVer(BJStringUtil.removeVerZero(crdDTO.getVer()));//交易步骤版本(tradestepver)

        CrdSysSupplement crdSysSupplement = new CrdSysSupplement();
        crdSysSupplement.setCrdOrderNum(crdDTO.getCrdordernum());
        String specdate = JSONObject.toJSONString(crdDTO.getSpecdata());//特属域 
        crdSysSupplement.setChargeYktmw(specdate);

        //更新order表
        crdSysOrderMapper.updateCrdSysOrderByCrdnum(crdSysOrder);
        //更新supplement表
        crdSysOrderMapper.updateCrdSysSupplementByCrdnumBef(crdSysSupplement);
    }

    @Transactional
    @Override
    public void updateCrdSysOrderAfterByCrdnum(CrdSysOrderDTO crdDTO) {
        CrdSysOrder crdSysOrder = new CrdSysOrder();
        crdSysOrder.setCrdOrderNum(crdDTO.getCrdordernum());
        crdSysOrder.setProOrderNum(crdDTO.getPrdordernum());
        if (!ResponseCode.SUCCESS.equals(crdDTO.getRespcode())) {
            crdSysOrder.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY_ERROR.getCode());//申请充值密钥失败(1000000006)
        } else {
            crdSysOrder.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY_SUCCESS.getCode());//申请充值密钥成功(1000000007)
        }
        crdSysOrder.setRespCode(crdDTO.getRespcode());//交易应答码(respcode)
        crdSysOrder.setTradeStep(crdDTO.getMessagetype());//交易步骤(tradestep)
        crdSysOrder.setTradeStepVer(BJStringUtil.removeVerZero(crdDTO.getVer()));//交易步骤版本(tradestepver)
        crdSysOrder.setTradeEndFlag(Integer.valueOf(crdDTO.getTradeendflag()));//交易结束标志(tradeendflag)
        crdSysOrder.setCardCounter(crdDTO.getCardcounter());//cardcounter(卡片计数器)

        CrdSysSupplement crdSysSupplement = new CrdSysSupplement();
        crdSysSupplement.setCrdOrderNum(crdDTO.getCrdordernum());
        String apdu = "";
        if (ResponseCode.SUCCESS.equals(crdDTO.getRespcode())) {
            apdu = JSONObject.toJSONString(crdDTO.getApdu());
        }
        crdSysSupplement.setChargeKey(apdu);//充值密钥(chargekey)

        //更新order表
        crdSysOrderMapper.updateCrdSysOrderByCrdnum(crdSysOrder);
        //更新supplement表
        crdSysOrderMapper.updateCrdSysSupplementByCrdnumAfr(crdSysSupplement);
    }

    /**
     * 重新获取充值秘钥
     */
    @Transactional
    @Override
    public String reSendOrderByCrdnum(String crdnum) {
        //按照卡服务充值订单号查询对应的卡服务补充信息表中的充值密钥(CHARGE_KEY)返回产品库
        CrdSysSupplement supplement = crdSysSupplementMapper.findCrdSysSupplementByCode(crdnum);
        String apdu = supplement.getChargeKey();
        //同时申请充消密钥次数(REQUEST_CHARGE_KEY_COUNT)进行加1处理并更新最近一次申请充消密钥时间(LAST_CHARGE_KEY_TIME)
        String lastChargeKeyTime = DateUtils.dateToStrLongs(new Date());
        crdSysOrderMapper.updateCrdSysSupplementChargeKeyByCrdnum(crdnum, lastChargeKeyTime);
        return apdu;
    }

    @Override
    public void updateKeyCountByCrdnum(String crdnum) {
        String lastChargeKeyTime = DateUtils.dateToStrLongs(new Date());
        crdSysOrderMapper.updateCrdSysSupplementChargeKeyByCrdnum(crdnum, lastChargeKeyTime);
    }

    @Override
    public void updatePosTranSeqByCrdnum(String crdnum, String posTranSeq) {
        crdSysOrderMapper.updatePosTranSeqByCrdnum(crdnum, posTranSeq);
    }

    @Override
    public void updatePosTranSeqConsByCrdnum(String crdnum, String posTranSeq) {
        crdSysOrderMapper.updatePosTranSeqConsByCrdnum(crdnum, posTranSeq);
    }

}