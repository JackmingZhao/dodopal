package com.dodopal.card.business.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.api.card.dto.ReslutData;
import com.dodopal.api.card.dto.ReslutDataParameter;
import com.dodopal.card.business.dao.CrdSysConsOrderMapper;
import com.dodopal.card.business.dao.CrdSysOrderMapper;
import com.dodopal.card.business.dao.IcdcRechargeMapper;
import com.dodopal.card.business.dao.ParameterMapper;
import com.dodopal.card.business.model.Bjtlfhupfiletb;
import com.dodopal.card.business.model.CrdSys100000;
import com.dodopal.card.business.model.CrdSysConsOrder;
import com.dodopal.card.business.model.CrdSysOrder;
import com.dodopal.card.business.model.CrdSysSupplement;
import com.dodopal.card.business.model.ProductPurchaseOrder;
import com.dodopal.card.business.model.ProductPurchaseOrderRecord;
import com.dodopal.card.business.model.SamSignInOffTb;
import com.dodopal.card.business.model.TlpPosMenuTb;
import com.dodopal.card.business.model.query.ParameterQuery;
import com.dodopal.card.business.service.BJCardTopupService;
import com.dodopal.card.business.util.BJStringUtil;
import com.dodopal.card.business.util.DateUtil;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.CardOrderStatesEnum;
import com.dodopal.common.enums.CardTradeEndFlagEnum;
import com.dodopal.common.enums.CardTxnStatEnum;
import com.dodopal.common.enums.CardTxnTypeEnum;
import com.dodopal.common.enums.ClearingMarkEnum;
import com.dodopal.common.enums.FundProcessResultEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.enums.PurchaseOrderRedordStatesEnum;
import com.dodopal.common.enums.PurchaseOrderStatesEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.RateTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.SuspiciousStatesEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.MoneyAlgorithmUtils;

@Service
public class BJCardTopupServiceImpl implements BJCardTopupService {
    @Autowired
    private CrdSysOrderMapper crdSysOrderMapper;

    @Autowired
    private IcdcRechargeMapper icdcRechargeMapper;

    @Autowired
    private CrdSysConsOrderMapper crdSysConsOrderMapper;

    @Autowired
    private ParameterMapper parameterMapper;

    /**
     * 创建卡服务订单
     * @param crdDTO
     * @return
     */
    @Transactional
    @Override
    public String createCrdSysOrder(BJCrdSysOrderDTO crdDTO) {

        //获取卡服务订单号   P + 20150428222211 +五位数据库cycle sequence（循环使用）
        StringBuffer crdOrderNum = new StringBuffer();
        crdOrderNum.append("P");
        crdOrderNum.append(DateUtil.getCurrTime());
        crdOrderNum.append(crdSysOrderMapper.queryCrdSysOrdercodeSeq());
        crdDTO.setCrdordernum(crdOrderNum.toString());

        //组装订单数据
        CrdSysOrder order = getCreateOrder(crdDTO);
        //插入订单表
        crdSysOrderMapper.insertCrdSysOrderForBJ(order);
        crdDTO.setMessagetype("2012");

        //插入补充表
        CrdSysSupplement supplement = getCreateSupplement(crdDTO);
        crdSysOrderMapper.insertCrdSysSupplement(supplement);

        return crdOrderNum.toString();
    }

    @Transactional
    @Override
    public String createCrdSysOrderForV71(BJCrdSysOrderDTO crdDTO) {

        //获取卡服务订单号   P + 20150428222211 +五位数据库cycle sequence（循环使用）
        StringBuffer crdOrderNum = new StringBuffer();
        crdOrderNum.append("P");
        crdOrderNum.append(DateUtil.getCurrTime());
        crdOrderNum.append(crdSysOrderMapper.queryCrdSysOrdercodeSeq());
        crdDTO.setCrdordernum(crdOrderNum.toString());

        //组装订单数据
        CrdSysOrder order = getCreateOrderForV71(crdDTO);
        //插入订单表
        crdSysOrderMapper.insertCrdSysOrderForBJ(order);
        crdDTO.setMessagetype("2012");

        //插入补充表
        CrdSysSupplement supplement = getCreateSupplement(crdDTO);
        crdSysOrderMapper.insertCrdSysSupplement(supplement);

        return crdOrderNum.toString();
    }

    /**
     * 处理补充表数据
     * @param crdDTO
     * @return
     */
    private CrdSysSupplement getCreateSupplement(BJCrdSysOrderDTO crdDTO) {

        CrdSysSupplement supplement = new CrdSysSupplement();

        supplement.setCrdOrderNum(crdDTO.getCrdordernum());//卡服务订单号
        supplement.setTxnType(Integer.valueOf(CardTxnTypeEnum.TXN_TYPE_RECHARGE.getCode()));//交易类型 1-充值,0-查询
        if (crdDTO.getSpecdata() != null) {
            supplement.setCheckYktmw(JSONObject.toJSONString(crdDTO.getSpecdata()));//特属域
        }
        //验卡返回
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
        String lastReadKeyTime = DateUtils.dateToStrLongs(new Date());
        supplement.setLastReadKeyTime(lastReadKeyTime);

        supplement.setRequestReadKeyCount(1);
        supplement.setRequestChargeKeyCount(0);
        supplement.setSendResultCount(0);

        supplement.setCreateUser(crdDTO.getUserid());
        supplement.setUpdateUser(crdDTO.getUserid());
        return supplement;
    }

    /**
     * 处理订单表数据
     * @param crdDTO
     * @return
     */
    private CrdSysOrder getCreateOrder(BJCrdSysOrderDTO crdDTO) {

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
        order.setTxnDate(DateUtil.getDateForLong());
        order.setTxnTime(DateUtil.getTimeShortHHmmss());
        order.setCheckCardNo(crdDTO.getTradecard());
        order.setCheckCardPosCode(crdDTO.getPosid());
        order.setChargeType(Integer.valueOf(crdDTO.getChargetype()));
        order.setCreateUser(crdDTO.getUserid());
        order.setUpdateUser(crdDTO.getUserid());

        /*
         * 北京新增字段 start
         */
        if (StringUtils.isNotBlank(crdDTO.getCardlogictype())) {
            order.setCardLogicType(crdDTO.getCardlogictype());
        }
        if (StringUtils.isNotBlank(crdDTO.getPostransseq())) {
            order.setPosTransSeq(BJStringUtil.format10to16(crdDTO.getPostransseq()));
        }
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

        /*
         * 北京新增字段 end
         */

        return order;
    }

    private CrdSysOrder getCreateOrderForV71(BJCrdSysOrderDTO crdDTO) {

        CrdSysOrder order = new CrdSysOrder();

        /*
         * 不需要传入的参数
         */
        order.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_APPLY_READCARD_KEY_SUCCESS.getCode());//申请读卡密钥成功
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
        order.setTxnDate(DateUtil.getDateForLong());
        order.setTxnTime(DateUtil.getTimeShortHHmmss());
        order.setCheckCardNo(crdDTO.getTradecard());
        order.setCheckCardPosCode(crdDTO.getPosid());
        order.setChargeType(Integer.valueOf(crdDTO.getChargetype()));
        order.setCreateUser(crdDTO.getUserid());
        order.setUpdateUser(crdDTO.getUserid());

        /*
         * 北京新增字段 start
         */
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

        /*
         * 北京新增字段 end
         */

        return order;
    }

    @Override
    public void updateCrdSysOrderByCrdnum(BJCrdSysOrderDTO crdDTO) {
        CrdSysOrder crdSysOrder = new CrdSysOrder();
        crdSysOrder.setCrdOrderNum(crdDTO.getCrdordernum());
        crdSysOrder.setProOrderNum(crdDTO.getPrdordernum());
        crdSysOrder.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY.getCode());//更新订单状态为申请充值密钥(1000000005)
        crdSysOrder.setChargeCardNo(crdDTO.getTradecard());//充值卡号(chargecardno)
        crdSysOrder.setChargeCardPosCode(crdDTO.getPosid());//充值pos号
        crdSysOrder.setTradeStep(crdDTO.getMessagetype());//交易步骤(tradestep)
        crdSysOrder.setTradeStepVer(BJStringUtil.removeVerZero(crdDTO.getVer()));//交易步骤版本(tradestepver)
        crdSysOrder.setPosTransSeq(BJStringUtil.format10to16(crdDTO.getPostransseq()));
        crdSysOrder.setCardCounter(crdDTO.getCardcounter());

        CrdSysSupplement crdSysSupplement = new CrdSysSupplement();
        crdSysSupplement.setCrdOrderNum(crdDTO.getCrdordernum());
        String specdate = JSONObject.toJSONString(crdDTO.getSpecdata());//特属域 
        crdSysSupplement.setChargeYktmw(specdate);

        String lastChargeKeyTime = DateUtils.dateToStrLongs(new Date());
        crdSysSupplement.setLastChargeKeyTime(lastChargeKeyTime);
        //更新order表
        crdSysOrderMapper.updateCrdSysOrderByCrdnum(crdSysOrder);
        //更新supplement表
        crdSysOrderMapper.updateCrdSysSupplementByCrdnumBef(crdSysSupplement);
    }

    @Transactional
    @Override
    public void updateCrdSysOrderAfterByCrdnum(BJCrdSysOrderDTO crdDTO) {
        CrdSysOrder crdSysOrder = new CrdSysOrder();
        crdSysOrder.setCrdOrderNum(crdDTO.getCrdordernum());
        crdSysOrder.setProOrderNum(crdDTO.getPrdordernum());

        CrdSysOrder dbOrder = crdSysOrderMapper.findCrdSysOrderByPrdnum(crdDTO.getPrdordernum());
        //如果订单状态为09,11,19,17不变更卡服务订单状态
        if (CardOrderStatesEnum.CARD_ORDER_RECHARGE_ERROR.getCode().equals(dbOrder.getCrdOrderStates()) || CardOrderStatesEnum.CARD_ORDER_APPLY_REVERSE.getCode().equals(dbOrder.getCrdOrderStates()) || CardOrderStatesEnum.CARD_ORDER_APPLY_REVERSE_SUCCESS.getCode().equals(dbOrder.getCrdOrderStates()) || CardOrderStatesEnum.CARD_ORDER_APPLY_REVERSE_ERROR.getCode().equals(dbOrder.getCrdOrderStates())) {
            crdSysOrder.setCrdOrderStates("");
        } else {
            if (!ResponseCode.SUCCESS.equals(crdDTO.getRespcode())) {
                crdSysOrder.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY_ERROR.getCode());//申请充值密钥失败(1000000006)
            } else {
                crdSysOrder.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY_SUCCESS.getCode());//申请充值密钥成功(1000000007)
            }
        }
        crdSysOrder.setRespCode(crdDTO.getRespcode());//交易应答码(respcode)
        crdSysOrder.setTradeStep(crdDTO.getMessagetype());//交易步骤(tradestep)
        crdSysOrder.setTradeStepVer(BJStringUtil.removeVerZero(crdDTO.getVer()));//交易步骤版本(tradestepver)
        crdSysOrder.setTradeEndFlag(Integer.valueOf(crdDTO.getTradeendflag()));//交易结束标志(tradeendflag)
        //        crdSysOrder.setCardCounter(crdDTO.getCardcounter());//cardcounter(卡片计数器)

        CrdSysSupplement crdSysSupplement = new CrdSysSupplement();
        crdSysSupplement.setCrdOrderNum(crdDTO.getCrdordernum());
        if (ResponseCode.SUCCESS.equals(crdDTO.getRespcode())) {
            crdSysSupplement.setChargeKey(crdDTO.getSpecdata().getCipheraction());//充值密钥(chargekey)
        }

        //更新order表
        crdSysOrderMapper.updateCrdSysOrderByCrdnum(crdSysOrder);
        //更新supplement表
        crdSysOrderMapper.updateCrdSysSupplementByCrdnumAfr(crdSysSupplement);
    }

    @Transactional
    @Override
    public String uploadResultChkUdpOrderFun(BJCrdSysOrderDTO crdDTO) {
        String txnstat = crdDTO.getTxnstat();
        String apdu = crdDTO.getSpecdata().getApdudata();
        String resultYktMw = JSONObject.toJSONString(crdDTO.getSpecdata());
        if (resultYktMw.length() > 4000) {
            resultYktMw = resultYktMw.substring(0, 4000);
        }
        int count = 0;
        Map<String, Object> crdOrder = new HashMap<String, Object>(1);
        crdOrder.put("CARD_TAC", apdu);
        crdOrder.put("CRD_ORDER_NUM", crdDTO.getCrdordernum());
        crdOrder.put("UPDATE_USER", crdDTO.getUserid());
        if (StringUtils.isNotBlank(crdDTO.getBlackamt())) {
            crdOrder.put("BLACK_AMT", Integer.valueOf(crdDTO.getBlackamt()));
        }
        Map<String, Object> crdSupplement = new HashMap<String, Object>(1);
        crdSupplement.put("RESULT_YKTMW", resultYktMw);
        crdSupplement.put("CRD_ORDER_NUM", crdDTO.getCrdordernum());
        crdSupplement.put("UPDATE_USER", crdDTO.getUserid());

        String lastResultTime = DateUtils.dateToStrLongs(new Date());
        crdSupplement.put("LAST_RESULT_TIME", lastResultTime);

        crdSupplement.put("SEND_COUNT", "1");

        //判断订单状态
        if (CardTxnStatEnum.TXN_STAT_SUCCESS.getCode().equals(txnstat)) {// 当txnstat=0(成功)
            crdOrder.put("CRD_ORDER_STATES", CardOrderStatesEnum.CARD_ORDER_RECHARGE_SUCCESS.getCode());//1000000008
        } else if (CardTxnStatEnum.TXN_STAT_FAIL.getCode().equals(txnstat)) {// 当txnstat=1(失败)
            crdOrder.put("CRD_ORDER_STATES", CardOrderStatesEnum.CARD_ORDER_RECHARGE_ERROR.getCode());//1000000009
        } else if (CardTxnStatEnum.TXN_STAT_UNKNOW.getCode().equals(txnstat)) {// 当txnstat=2(未知)
            crdOrder.put("CRD_ORDER_STATES", CardOrderStatesEnum.CARD_ORDER_RECHARGE_UNKNOW.getCode());//1000000010
        } else {
            return ResponseCode.CARD_APDU_ERROR;
        }
        //更新卡服务订单状态
        count = icdcRechargeMapper.updateCardSysOrderByCrdOrderId(crdOrder);
        if (count != 1) {//更新失败
            throw new DDPException(ResponseCode.SYSTEM_ERROR, "更新卡服务订单交易状态失败");
        }
        //更新卡服务订单子表特殊域信息和TAC信息
        count = icdcRechargeMapper.updateCardSysSupplementByCrdOrderId(crdSupplement);
        if (count != 1) {//更新失败
            throw new DDPException(ResponseCode.SYSTEM_ERROR, "更新卡服务订单子表信息失败");
        }

        return ResponseCode.SUCCESS;
    }

    @Override
    public SamSignInOffTb querySamInOffTbByPosId(String posId) {
        SamSignInOffTb inOffTb = crdSysOrderMapper.querySamInOffTbByPosId(posId);
        return inOffTb;
    }

    @Override
    public String queryMerDiscountByMerCode(String merCode) {
        ParameterQuery query = new ParameterQuery();
        query.setToday(new Date());
        Calendar cal = Calendar.getInstance();
        cal.setTime(query.getToday());
        int w = cal.get(Calendar.DAY_OF_WEEK);
        query.setTodayWeek(String.valueOf(w - 1));

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(query.getToday());
        calendar.add(Calendar.DATE, 1);
        query.setTomorrow(calendar.getTime());
        cal.setTime(query.getToday());
        w = cal.get(Calendar.DAY_OF_WEEK);
        query.setTomorrowWeek(String.valueOf(w - 1));

        query.setMerCode(merCode);
        String count = parameterMapper.findSubPeriodDiscountParameterCount(query);
        return count;
    }

    private String getCardOrderNum() {
        //获取卡服务订单号   N + 20150428222211 +五位数据库cycle sequence（循环使用）
        StringBuffer crdOrderNum = new StringBuffer();
        crdOrderNum.append("N");
        crdOrderNum.append(DateUtil.getCurrTime());
        crdOrderNum.append(crdSysOrderMapper.queryCrdSysOrdercodeSeq());
        return crdOrderNum + "";
    }

    private String getProdOrderNum() {
        String prdOrderNum = "C";
        String timeStr = DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR);
        prdOrderNum += timeStr;
        prdOrderNum += crdSysOrderMapper.getPrdPurchaseOrderNumSeq();
        return prdOrderNum;
    }

    private CrdSysConsOrder fixCrdOrder(ReslutDataParameter parameter, ReslutData reslutData) {
        CrdSysConsOrder crdOrder = new CrdSysConsOrder();
        ////00:交易成功;01:交易失败;02/0F:交易未知
        if ("00".equals(reslutData.getTxnstat())) {
            crdOrder.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_UPLOAD_RESULT_SUCCESS.getCode());//一卡通确认充值成功
        } else if ("01".equals(reslutData.getTxnstat())) {
            crdOrder.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_APPLY_REVERSE_SUCCESS.getCode());//一卡通确认冲正成功
        } else if ("02".equals(reslutData.getTxnstat()) || "0F".equals(reslutData.getTxnstat())) {
            crdOrder.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_RECHARGE_UNKNOW.getCode());//卡片充值未知
        }
        crdOrder.setCrdBeforeorderStates(CardOrderStatesEnum.CARD_ORDER_CREATE_SUCCESS.getCode());//创建成功
        crdOrder.setRespCode(parameter.getRespcode());
        crdOrder.setTxnType(Integer.valueOf(CardTxnTypeEnum.TXN_TYPE_CONSUME.getCode()));//消费2
        crdOrder.setTradeStep(parameter.getMessagetype());
        crdOrder.setTradeStepVer(BJStringUtil.removeVerZero(parameter.getVer()));
        crdOrder.setTradeEndFlag(Integer.valueOf(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode()));//交易结束(1)
        crdOrder.setCityCode(parameter.getCitycode());
        crdOrder.setCardFaceNo(reslutData.getCardno());
        crdOrder.setOrderCardNo(reslutData.getCardno());
        crdOrder.setCardType(1);//1:CPU;2:M1
        crdOrder.setPosType(Integer.valueOf(parameter.getPostype()));
        crdOrder.setPosCode(parameter.getPosid());
        if (StringUtils.isNotBlank(parameter.getPosseq())) {
            crdOrder.setPosSeq(Integer.valueOf(parameter.getPosseq()));
        }
        crdOrder.setBefbal(Integer.valueOf(BJStringUtil.format16to10(reslutData.getBefbal())));
        crdOrder.setTxnAmt(Integer.valueOf(BJStringUtil.format16to10(reslutData.getTxnamt())));
        //        crdOrder.setTxnSeq(Integer.valueOf(BJStringUtil.format16to10(reslutData.getTxnseq())));
        crdOrder.setBlackAmt(Integer.valueOf(BJStringUtil.format16to10(reslutData.getAftbal())));
        crdOrder.setTxnDate(reslutData.getTxndate());
        crdOrder.setTxnTime(reslutData.getTxntime());

        crdOrder.setCheckCardNo(reslutData.getCardno());
        crdOrder.setCheckCardPosCode(parameter.getPosid());
        crdOrder.setSamNo(reslutData.getSam());
        crdOrder.setMerCode(parameter.getMercode());
        crdOrder.setRespCode(ResponseCode.SUCCESS);
        crdOrder.setCardInnerNo(reslutData.getCardno());
        crdOrder.setConsumeCardNo(reslutData.getCardno());
        crdOrder.setConsumeCardPosCode(parameter.getPosid());
        crdOrder.setSource(Integer.valueOf(SourceEnum.MERMACHINE.getCode()));
        crdOrder.setChargeType(0);
        crdOrder.setCardTac(reslutData.getTac());
        crdOrder.setCardCounter(reslutData.getCardcnt());
        //        crdOrder.setCardLogicType(reslutData.getCardtype());
        crdOrder.setOfflineFlag("1");//脱机
        crdOrder.setUserCode(parameter.getUsercode());
        crdOrder.setUserId(parameter.getUserid());
        crdOrder.setYktCode(parameter.getYktcode());
        crdOrder.setCreateUser(parameter.getUserid());
        //        order.setProCode(crdDTO.getProductcode());
        //        order.setCardLimit(Long.valueOf(crdDTO.getCardlimit()));
        //        order.setPosTransSeq(BJStringUtil.format10to16(crdDTO.getPostransseq()));
        return crdOrder;
    }

    private CrdSysSupplement fixSupment(ReslutDataParameter parameter, ReslutData reslutData) {

        CrdSysSupplement supplement = new CrdSysSupplement();

        supplement.setTxnType(Integer.valueOf(CardTxnTypeEnum.TXN_TYPE_CONSUME.getCode()));//1:充值；2：消费
        supplement.setResultYktmw(reslutData.getTac());
        if (reslutData.getTxndate().length() > 8) {
            supplement.setTxnDate(reslutData.getTxndate().substring(0, 8));
        } else {
            supplement.setTxnDate(reslutData.getTxndate());
        }
        if (reslutData.getTxntime().length() > 6) {
            supplement.setTxnTime(reslutData.getTxntime().substring(0, 6));
        } else {
            supplement.setTxnTime(reslutData.getTxntime());
        }

        String lastResultTime = DateUtils.dateToStrLongs(new Date());
        supplement.setLastResultTime(lastResultTime);

        supplement.setRequestReadKeyCount(0);
        supplement.setRequestChargeKeyCount(0);
        supplement.setSendResultCount(0);

        supplement.setCreateUser(parameter.getUserid());
        return supplement;
    }

    private CrdSys100000 fixCrdSys1000000(ReslutData reslutData) {
        CrdSys100000 sys100000 = new CrdSys100000();
        //个性化信息
        if (StringUtils.isNotBlank(reslutData.getPlivatemsg())) {
            sys100000.setPlivatemsg(reslutData.getPlivatemsg());
        }
        //卡号
        if (StringUtils.isNotBlank(reslutData.getCardno())) {
            sys100000.setCardNo(reslutData.getCardno());
        }
        //应收金额
        if (StringUtils.isNotBlank(reslutData.getAmtneedrecv())) {
            sys100000.setAmtNeedRecv(reslutData.getAmtneedrecv());
        }
        //实收金额
        if (StringUtils.isNotBlank(reslutData.getAmtrecv())) {
            sys100000.setAmtRecv(reslutData.getAmtrecv());
        }
        //优惠类型
        if (StringUtils.isNotBlank(reslutData.getDistypeone())) {
            sys100000.setDisTypeOne(reslutData.getDistypeone());
        }
        //优惠金额
        if (StringUtils.isNotBlank(reslutData.getDistypeoneamt())) {
            sys100000.setDisTypeOneAmt(reslutData.getDistypeoneamt());
        }
        //优惠类型
        if (StringUtils.isNotBlank(reslutData.getDistypetwo())) {
            sys100000.setDisTypeTwo(reslutData.getDistypetwo());
        }
        //优惠金额
        if (StringUtils.isNotBlank(reslutData.getDistypetwoamt())) {
            sys100000.setDisTypeTwoAmt(reslutData.getDistypetwoamt());
        }
        //优惠类型
        if (StringUtils.isNotBlank(reslutData.getDistypethree())) {
            sys100000.setDisTypeThree(reslutData.getDistypethree());
        }
        //优惠金额
        if (StringUtils.isNotBlank(reslutData.getDistypethreeamt())) {
            sys100000.setDisTypeThreeAmt(reslutData.getDistypethreeamt());
        }
        //商户代码
        if (StringUtils.isNotBlank(reslutData.getUnitid())) {
            sys100000.setUnitId(reslutData.getUnitid());
        }
        //账户号
        if (StringUtils.isNotBlank(reslutData.getAccountid())) {
            sys100000.setAccountId(reslutData.getAccountid());
        }
        //交易批次号
        if (StringUtils.isNotBlank(reslutData.getBatchid())) {
            sys100000.setBatchId(reslutData.getBatchid());
        }
        //主机交易流水号
        if (StringUtils.isNotBlank(reslutData.getHostseq())) {
            sys100000.setHostSeq(reslutData.getHostseq());
        }
        //设备代码，没有时填0
        if (StringUtils.isNotBlank(reslutData.getDevid())) {
            sys100000.setDevId(reslutData.getDevid());
        }
        //预留
        if (StringUtils.isNotBlank(reslutData.getResv())) {
            sys100000.setResv(reslutData.getResv());
        }
        //应收金额
        if (StringUtils.isNotBlank(reslutData.getAmtreceivable())) {
            sys100000.setAmtReceivable(reslutData.getAmtreceivable());
        }
        //用户折扣
        if (StringUtils.isNotBlank(reslutData.getUserdiscount())) {
            sys100000.setUserDiscount(reslutData.getUserdiscount());
        }
        //结算折扣
        if (StringUtils.isNotBlank(reslutData.getSettldiscount())) {
            sys100000.setSettlDiscount(reslutData.getSettldiscount());
        }

        sys100000.setTxndate(reslutData.getTxndate());
        sys100000.setTxntime(reslutData.getTxntime());
        return sys100000;
    }

    private ProductPurchaseOrderRecord fixPcsOrder(ReslutDataParameter parameter, ReslutData reslutData) {
        ProductPurchaseOrderRecord orderRecord = new ProductPurchaseOrderRecord();
        orderRecord.setMerDiscount(10.0);// 北京商户折扣默认值：10无折扣）----------------------------------------------------------------无折扣
        orderRecord.setCityCode(parameter.getCitycode());// 城市CODE
        orderRecord.setBeforInnerStates("");// 前内部状态----------------------------------------------------------------------------初始值：空
        orderRecord.setCardNum(reslutData.getCardno());// 卡号
        orderRecord.setCardFaceno(reslutData.getCardno());
        orderRecord.setPosCode(parameter.getPosid());// POS号
        orderRecord.setBefbal(Long.valueOf(BJStringUtil.format16to10(reslutData.getBefbal())));// 支付前卡内金额
        orderRecord.setBlackAmt(Long.valueOf(BJStringUtil.format16to10(reslutData.getAftbal())));// 支付后卡内金额
        if ("00".equals(reslutData.getTxnstat())) {
            orderRecord.setInnerStates(PurchaseOrderRedordStatesEnum.DEDUCT_MONEY_SUCCESS.getCode());// 内部状态 扣款成功
            orderRecord.setSuspiciousStates(SuspiciousStatesEnum.SUSPICIOUS_NO.getCode());// 可疑状态---------------------------------初始值：0:不可疑
        } else if ("01".equals(reslutData.getTxnstat())) {
            orderRecord.setInnerStates(PurchaseOrderRedordStatesEnum.DEDUCT_MONEY_FAILURE.getCode());// 内部状态 扣款失败
            orderRecord.setSuspiciousStates(SuspiciousStatesEnum.SUSPICIOUS_NO.getCode());// 可疑状态---------------------------------初始值：0:不可疑
        } else if ("02".equals(reslutData.getTxnstat()) || "0F".equals(reslutData.getTxnstat())) {
            orderRecord.setInnerStates(PurchaseOrderRedordStatesEnum.DEDUCT_MONEY_SUSPICIOUS.getCode());// 内部状态  未知
            orderRecord.setSuspiciousStates(SuspiciousStatesEnum.SUSPICIOUS_UNHANDLE.getCode());// 可疑状态---------------------------------初始值：1:可疑
        }
        String orderDate = reslutData.getTxndate() + reslutData.getTxntime();
        Date date = DateUtils.stringtoDate(orderDate, DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR);
        orderRecord.setOrderDate(date);
        orderRecord.setOrderDay(reslutData.getTxndate());
        orderRecord.setCityName(parameter.getCityname());
        orderRecord.setYtkCode(parameter.getYktcode());
        orderRecord.setYtkName(parameter.getYktname());
        orderRecord.setYktPayRate(Double.valueOf(parameter.getYktpayrate()));
        orderRecord.setCreateUser(parameter.getUserid());

        //通卡优惠标志
        if ("06".equals(parameter.getVer())) {
            orderRecord.setYktDisCountSign("1");
        } else {
            orderRecord.setYktDisCountSign("0");
        }

        return orderRecord;
    }

    private ProductPurchaseOrder fixPrdOrder(ReslutDataParameter parameter, ReslutData reslutData) {
        ProductPurchaseOrder purchaseOrder = new ProductPurchaseOrder();

        String amtreceivable = reslutData.getAmtreceivable();
        String userdiscount = reslutData.getUserdiscount();
        String settldiscount = reslutData.getSettldiscount();
        // 应收金额
        long originalPrice = Long.parseLong(amtreceivable);
        // 商户费率(单位：千分比)
        Double merRate = 1000 - Double.parseDouble(userdiscount);
        // 服务费率(单位：千分比)
        Double serviceRate = 1000 - Double.parseDouble(settldiscount);
        // 商户利润(单位：分)
        long merGain = 0;
        if (Double.parseDouble(settldiscount) > Double.parseDouble(userdiscount)) {
            merGain = MoneyAlgorithmUtils.multiplyToIntValue(amtreceivable, String.valueOf((Double.parseDouble(settldiscount) - Double.parseDouble(userdiscount)) / 1000));
        } else {
            merGain = MoneyAlgorithmUtils.multiplyToIntValueAddOne(amtreceivable, String.valueOf((Double.parseDouble(settldiscount) - Double.parseDouble(userdiscount)) / 1000));
        }

        purchaseOrder.setOriginalPrice(originalPrice);//应收金额
        purchaseOrder.setReceivedPrice(Long.valueOf(BJStringUtil.format16to10(reslutData.getTxnamt())));// 实收金额 
        purchaseOrder.setMerGain(merGain);// 商户利润
        purchaseOrder.setServiceRate(serviceRate); // 服务费率(单位：千分比)
        purchaseOrder.setMerRate(merRate);// 商户费率(单位：千分比)
        purchaseOrder.setServiceRateType(RateTypeEnum.PERMILLAGE.getCode());// 服务费率类型-------------------------------北京V71默认：千分比
        purchaseOrder.setMerRateType(RateTypeEnum.PERMILLAGE.getCode());// 商户费率类型-----------------------------------北京V71默认：千分比

        purchaseOrder.setCustomerType(parameter.getMertype());// 客户类型
        purchaseOrder.setCustomerCode(parameter.getMercode());// 客户编号
        purchaseOrder.setBusinessType(RateCodeEnum.YKT_PAYMENT.getCode());// 业务类型---------------------------------------固定值：03:一卡通消费
        purchaseOrder.setPayType(PayTypeEnum.PAY_CARD.getCode());// 支付类型-------------------------------------------------固定值：1：一卡通支付
        purchaseOrder.setPayWay(parameter.getCitycode());// 支付方式-----------------------------------------------------------固定值：城市code
        if ("00".equals(reslutData.getTxnstat())) {
            purchaseOrder.setStates(PurchaseOrderStatesEnum.PAID_SUCCESS.getCode());// 状态---------------------------------------------2:支付成功
        } else if ("01".equals(reslutData.getTxnstat())) {
            purchaseOrder.setStates(PurchaseOrderStatesEnum.PAID_FAILURE.getCode());// 状态---------------------------------------------1:支付失败
        } else if ("02".equals(reslutData.getTxnstat()) || "0F".equals(reslutData.getTxnstat())) {
            purchaseOrder.setStates(PurchaseOrderStatesEnum.PAID_SUSPICIOUS.getCode());// 状态--------------------------------------------3
        }
        purchaseOrder.setClearingMark(ClearingMarkEnum.CLEARING_NO.getCode());// 清算标识-----------------------------------------初始值0：未清算
        purchaseOrder.setFundProcessResult(FundProcessResultEnum.UNDONE.getCode());// 资金处理结果--------------------------------初始值0：未处理
        purchaseOrder.setSource(SourceEnum.MERMACHINE.getCode());//商用机 4
        String orderDate = reslutData.getTxndate() + reslutData.getTxntime();
        Date date = DateUtils.stringtoDate(orderDate, DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR);
        purchaseOrder.setOrderDate(date);
        purchaseOrder.setOrderDay(reslutData.getTxndate());
        purchaseOrder.setPayGateway(parameter.getYktcode());
        purchaseOrder.setUserId(parameter.getUserid());
        purchaseOrder.setCreateUser(parameter.getUserid());
        purchaseOrder.setCustomerName(parameter.getMername());
        purchaseOrder.setComments("V71操作员ID：" + parameter.getOperid());

        return purchaseOrder;
    }

    @Override
    public Bjtlfhupfiletb fixTkOrder(ReslutData reslutData) {
        Bjtlfhupfiletb tkOrder = new Bjtlfhupfiletb();
        //交易后卡余额
        tkOrder.setBalance(reslutData.getAftbal());
        //交易前卡余额
        tkOrder.setBefBal(reslutData.getBefbal());
        //卡交易计数器
        tkOrder.setCardCnt(reslutData.getCardcnt());
        //卡序列号
        tkOrder.setCardCsn(reslutData.getCardseq());
        tkOrder.setCardNo(reslutData.getCardno());
        tkOrder.setCardphyType(reslutData.getCardphytype());
        tkOrder.setCardType(reslutData.getCardtype());
        //密钥及算法标示
        tkOrder.setKeyalg(reslutData.getAlgorithm());
        tkOrder.setPlivateMsg(reslutData.getPlivatemsg());
        //终端交易序号
        tkOrder.setPosTxnSn(reslutData.getTermtxnseq());
        //包中记录序号
        tkOrder.setRecordsn(reslutData.getRecno());
        //00:交易成功;01:交易失败;02/0F:交易未知
        if ("00".equals(reslutData.getTxnstat())) {
            tkOrder.setResult("0");
        }
        if ("01".equals(reslutData.getTxnstat())) {
            tkOrder.setResult("1");
        }
        if ("02".equals(reslutData.getTxnstat()) || "0F".equals(reslutData.getTxnstat())) {
            tkOrder.setResult("2");
        }
        tkOrder.setSamNo(reslutData.getSam());
        tkOrder.setTac(reslutData.getTac());
        tkOrder.setTxnAmt(reslutData.getTxnamt());
        tkOrder.setTxnDate(reslutData.getTxndate());
        //交易顺序号
        tkOrder.setTxnSn(reslutData.getTxnseq());
        tkOrder.setTxnState(reslutData.getTxnstat());
        tkOrder.setTxnTime(reslutData.getTxntime());
        tkOrder.setTxnType(reslutData.getTxntype());
        //钱包交易序号
        tkOrder.setWalletTxnSn(reslutData.getBaltxnseq());

        return tkOrder;
    }

    /*
     * 批量结果上传
     */
    @Override
    public String batchUploadResult(ReslutDataParameter parameter) {
        StringBuffer retStr = new StringBuffer();
        List<ReslutData> filerecords = parameter.getCrdm().getFilerecords();
        for (int i = 0; i < filerecords.size(); i++) {
            ReslutData reslutData = filerecords.get(i);
            String txnType = reslutData.getTxntype();
            txnType.toUpperCase();
            //判断是否锁卡,直接插入通卡订单表
            if ("0A".equals(txnType)) {
                retStr.append(",[第" + (i + 1) + "条记录" + "卡号(" + reslutData.getCardno() + "),交易时间(" + reslutData.getTxndate() + reslutData.getTxntime() + "),交易金额(" + reslutData.getTxnamt() + "),为锁卡记录]");
                //拼装通卡数据
                Bjtlfhupfiletb tkOrder = fixTkOrder(reslutData);
                //校验通卡订单重复
                int tkCount = crdSysOrderMapper.checkTkOrderCount(tkOrder);
                //校验重复订单
                if (tkCount != 0) {
                    retStr.append(",[发现重复订单]");
                } else {
                    //插入通卡订单表
                    crdSysOrderMapper.insertTkOrder(tkOrder);
                }
            } else {
                String addStr = insertOrders(parameter, reslutData);
                if (StringUtils.isNotBlank(addStr)) {//失败
                    retStr.append(",[第" + (i + 1) + "条记录" + addStr + "]");
                    continue;
                }
            }
        }
        return retStr + "";
    }

    @Transactional
    private String insertOrders(ReslutDataParameter parameter, ReslutData reslutData) {
        String retStr = "";
        String errStr = "卡号(" + reslutData.getCardno() + "),交易时间(" + reslutData.getTxndate() + reslutData.getTxntime() + "),交易金额(" + reslutData.getTxnamt() + "),";
        try {
            String crdOrderNum = getCardOrderNum();
            String prdOrderNum = getProdOrderNum();

            //拼装卡服务主订单数据
            CrdSysConsOrder crdOrder = fixCrdOrder(parameter, reslutData);
            crdOrder.setCrdOrderNum(crdOrderNum);
            crdOrder.setProOrderNum(prdOrderNum);
            //拼装卡服务补充数据
            CrdSysSupplement supplement = fixSupment(parameter, reslutData);
            supplement.setCrdOrderNum(crdOrderNum);

            //拼装产品库消费主订单数据
            ProductPurchaseOrder prdOrder = fixPrdOrder(parameter, reslutData);
            prdOrder.setOrderNum(prdOrderNum);
            //拼装收单记录数据
            ProductPurchaseOrderRecord pcsOrder = fixPcsOrder(parameter, reslutData);
            pcsOrder.setOrderNum(prdOrderNum);

            //拼装通卡数据
            Bjtlfhupfiletb tkOrder = fixTkOrder(reslutData);

            //校验通卡订单重复
            int tkCount = crdSysOrderMapper.checkTkOrderCount(tkOrder);
            //校验卡服务订单重复
            int crdCount = crdSysOrderMapper.checkCrdOrderCount(crdOrder);
            //校验重复订单
            if (tkCount != 0 || crdCount != 0) {
                retStr = errStr + "发现重复订单";
                return retStr;
            }

            //插入卡服务消费订单表
            crdSysConsOrderMapper.insertCrdSysConsOrderOfflineForV71(crdOrder);
            //插入卡服务补充表
            crdSysConsOrderMapper.insertSupplementOfflineForV71(supplement);

            //如果是06版本,添加数据到优惠信息表
            if ("06".equals(parameter.getVer())) {
                //拼装卡服务北京补充数据
                CrdSys100000 crdSys100000 = fixCrdSys1000000(reslutData);
                crdSys100000.setCrdOrderNum(crdOrderNum);
                crdSys100000.setPrdOrderNum(prdOrderNum);
                crdSys100000.setCreateUser(parameter.getUserid());
                //插入卡服务北京补充表
                crdSysConsOrderMapper.insertCrdSys100000(crdSys100000);
            }

            //创建消费主订单
            crdSysOrderMapper.addProductPurchaseOrder(prdOrder);
            //创建收单记录
            crdSysOrderMapper.addProductPurchaseOrderRecord(pcsOrder);

            //插入通卡订单表
            crdSysOrderMapper.insertTkOrder(tkOrder);
        }
        catch (Exception e) {
            retStr = errStr + "发生异常:" + e.getCause() + e.getMessage();
        }
        return retStr;
    }

    @Transactional
    @Override
    public BJCrdSysOrderDTO getLoadInitFun(BJCrdSysOrderDTO crdDTO) {
        crdDTO.setTradeendflag(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode());//结束标志
        // 1.检查卡服务订单是否存在
        int count = icdcRechargeMapper.queryCrdSysOrderCountByPrdOrderId(crdDTO.getPrdordernum());
        if (count != 1) {
            throw new DDPException(ResponseCode.SYSTEM_ERROR, "检查卡服务订单是否存在失败");
        }
        // 2.查询卡服务订单
        CrdSysOrder crdSysOrder = icdcRechargeMapper.queryBJCardSysOrderByPrdOrderId(crdDTO.getPrdordernum());
        String crdOrderNum = crdSysOrder.getCrdOrderNum();
        crdDTO.setCrdordernum(crdOrderNum);
        // 3.校验前端读卡卡号和充值金额
        if (!crdSysOrder.getOrderCardNo().equals(crdDTO.getTradecard())) {
            crdDTO.setRespcode(ResponseCode.CARD_TRADECARD_ERROR);
            return crdDTO;
        }
        if (!(crdSysOrder.getTxnAmt() == Long.valueOf(crdDTO.getTxnamt()))) {
            crdDTO.setRespcode(ResponseCode.CARD_TXNAMT_ERROR);
            return crdDTO;
        }
        // 4.校验订单状态
        String orderStatus = crdSysOrder.getCrdOrderStates();
        if (!CardOrderStatesEnum.CARD_ORDER_CREATE_SUCCESS.getCode().equals(orderStatus)) {//1000000001
            //订单状态不正确
            crdDTO.setRespcode(ResponseCode.CARD_ORDER_STATES_ERROR);
            return crdDTO;
        } else {
            // 5.往DTO填充数据 卡号(cardinnerno)、卡面号(cardfaceno)、交易流水号(txnseq)、交易日期(txndate)、交易时间(交易时间)、设备号编号(posid)
            crdDTO.setCardinnerno(crdSysOrder.getCardInnerNo());
            crdDTO.setCardfaceno(crdSysOrder.getCardFaceNo());
            crdDTO.setTxnseq(crdSysOrder.getTxnSeq() + "");
            crdDTO.setTxndate(crdSysOrder.getTxnDate());
            crdDTO.setTxntime(crdSysOrder.getTxnTime());
            crdDTO.setPosid(crdSysOrder.getPosCode());
            crdDTO.setTradeendflag(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode());//结束标志
            crdDTO.setCardlimit(crdSysOrder.getCardLimit() + "");
            crdDTO.setUsercode(crdSysOrder.getUserCode());
            crdDTO.setChargetype(crdSysOrder.getChargeType() + "");
            //beijing start
            crdDTO.setAppid(crdSysOrder.getAppId());
            crdDTO.setCardlogictype(crdSysOrder.getCardLogicType());
            crdDTO.setPostransseq(BJStringUtil.format16to10(crdSysOrder.getPosTransSeq()));
            crdDTO.setKeyindex(crdSysOrder.getKeyIndex());
            crdDTO.setOfflineflag(crdSysOrder.getOfflineFlag());
            crdDTO.setSamno(crdSysOrder.getSamNo());
            crdDTO.setPosseq(crdSysOrder.getPosSeq() + "");
            crdDTO.setMessagetype("2012");
            //beijing end
            // 7.更新卡服务订单状态
            Map<String, Object> value = new HashMap<String, Object>(1);
            value.put("RESP_CODE", ResponseCode.SUCCESS);
            value.put("CRD_ORDER_STATES", CardOrderStatesEnum.CARD_ORDER_APPLY_READCARD_KEY_SUCCESS.getCode());//03
            value.put("MER_ORDER_CODE", crdDTO.getMerordercode());
            value.put("USER_CODE", crdDTO.getUsercode());
            value.put("CRD_ORDER_NUM", crdOrderNum);
            value.put("UPDATE_USER", crdDTO.getUserid());
            count = icdcRechargeMapper.updateCardSysOrderByCrdOrderId(value);
            if (count != 1) {
                throw new DDPException(ResponseCode.SYSTEM_ERROR, "更新卡服务订单状态失败");
            }
            crdDTO.setRespcode(ResponseCode.SUCCESS);
            return crdDTO;
        }
    }

    /**
     * 查询菜单数据
     */
    @Override
    public TlpPosMenuTb queryTlpPosMenuTb(String samNo) {
        //TODO 暂时写死3000
        TlpPosMenuTb posMenuTb = crdSysOrderMapper.queryTlpPosMenuTbBySamNo(samNo);
        return posMenuTb;
    }

    @Override
    public TlpPosMenuTb queryTlpPosMenuTbConsBySamNo(String samNo) {
        //TODO 根据vc客户端根据中文菜单名取数据
        TlpPosMenuTb posMenuTb = crdSysOrderMapper.queryTlpPosMenuTbConsBySamNo(samNo);
        return posMenuTb;
    }

    /**
     * 查询商户号
     */
    @Override
    public String queryMerCodeByPosNo(String posNo) {
        String merCode = crdSysOrderMapper.queryMerCodeByPosNo(posNo);
        return merCode;
    }

    /*
     * 查询脱机数据
     */
    @Override
    public String queryOfflineBySamNo(String samNo, String cardPhyType, String cardType) {
        String offlineType = crdSysOrderMapper.queryOfflineBySamNo(samNo, cardPhyType, cardType);
        return offlineType;
    }

    /*
     * 校验脱机金额
     */
    @Override
    public String checkOfflineAmt(String offlineType, String samNo, String txnAmt) {
        String offlineFlag = "0";//0：联机 1：脱机
        if ("01".equals(offlineType)) {//全脱机
            offlineFlag = "1";
        } else if ("03".equals(offlineType)) {//全联机
            offlineFlag = "0";
        } else if ("02".equals(offlineType)) {//受消费金额限制
            //限制金额，16进制转10进制
            String fhtxncritical = crdSysOrderMapper.queryOfflineAmt(samNo);
            if (StringUtils.isBlank(fhtxncritical)) {
                throw new DDPException(ResponseCode.CARD_CONSUME_CARD_OFFLINE_ERROR, "查询脱机金额异常");
            }
            int offlineAmt = Integer.parseInt(fhtxncritical, 16);
            //如果交易金额小于阈值需要脱机
            if (Double.parseDouble(txnAmt) <= offlineAmt) {
                offlineFlag = "1";
            }
        }
        return offlineFlag;
    }

    /*
     * 更新通讯流水号
     */
    @Override
    public void updateSamsigninfo(String posId, String posIcSeq, String posTransSeq) {
        posIcSeq = BJStringUtil.format10to16(posIcSeq);
        posTransSeq = BJStringUtil.format10to16(posTransSeq);
        crdSysOrderMapper.updateSamsigninfo(posId, posIcSeq, posTransSeq);
    }

    /*
     * 查询商户秘钥
     */
    @Override
    public String queryKeyset(String merCode) {
        Map<String, Object> map = crdSysOrderMapper.queryKeyset(merCode);
        String paypwd = map.get("PAYPWD") + "";
        String backpaypwd = map.get("BACKPAYPWD") + "";
        StringBuffer keSet = new StringBuffer();
        //keyset=商户支付密钥paypwd长度（2）+商户支付密钥paypwd实际值+退货密码backpaypwd长度（2）+退货密码实际值   keyset不足32后补0
        keSet.append(BJStringUtil.startFix(paypwd.length() + "", "0", 2));
        keSet.append(paypwd);
        keSet.append(BJStringUtil.startFix(backpaypwd.length() + "", "0", 2));
        keSet.append(backpaypwd);
        while (keSet.length() < 32) {
            keSet.append("0");
        }
        return keSet.toString();
    }
}
