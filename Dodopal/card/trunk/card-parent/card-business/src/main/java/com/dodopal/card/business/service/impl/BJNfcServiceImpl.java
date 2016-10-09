package com.dodopal.card.business.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.api.card.dto.BJNfcSpecdata;
import com.dodopal.card.business.constant.CardConstants;
import com.dodopal.card.business.dao.CrdSysOrderMapper;
import com.dodopal.card.business.dao.IcdcRechargeMapper;
import com.dodopal.card.business.log.LogHelper;
import com.dodopal.card.business.model.CrdSys100000Mobile;
import com.dodopal.card.business.model.CrdSysOrder;
import com.dodopal.card.business.model.CrdSysSupplement;
import com.dodopal.card.business.service.BJCityFrontSocketService;
import com.dodopal.card.business.service.BJNfcService;
import com.dodopal.card.business.service.CardTopupService;
import com.dodopal.card.business.service.IcdcRechargeService;
import com.dodopal.card.business.util.BJStringUtil;
import com.dodopal.card.business.util.DateUtil;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.CardOrderStatesEnum;
import com.dodopal.common.enums.CardTradeEndFlagEnum;
import com.dodopal.common.enums.CardTxnStatEnum;
import com.dodopal.common.enums.CardTxnTypeEnum;
import com.dodopal.common.util.DateUtils;

@Service
public class BJNfcServiceImpl implements BJNfcService {

    @Autowired
    private CrdSysOrderMapper crdSysOrderMapper;

    @Autowired
    private BJCityFrontSocketService bjCityFrontSocketService;

    @Autowired
    private CardTopupService cardTopupService;

    @Autowired
    private IcdcRechargeMapper icdcRechargeMapper;

    @Autowired
    private IcdcRechargeService icdcRechargeService;

    @Autowired
    private LogHelper logHelper;

    /*
     * 终端登记
     */
    @Override
    public BJCrdSysOrderDTO userTeminalRegister(BJCrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());//日志开始时间
        StringBuffer logRespexplain = new StringBuffer();//日志响应吗描述
        String logRespcode = ResponseCode.SUCCESS;//日志响应吗
        String inPras = JSONObject.toJSONString(crdDTO);//入参

        BJCrdSysOrderDTO retDTO = new BJCrdSysOrderDTO();

        try {
            /******************** 向城市前置发送请求 ********************/
            String cityFrontUrl = CardConstants.BJ_NFC_CITY_FRONT_URL + CardConstants.BJ_NFC_USER_TEMINAL_REGISTER;
            logRespexplain.append("[url=" + cityFrontUrl + "]");
            retDTO = bjCityFrontSocketService.sendBJCityFrontHttp(cityFrontUrl, crdDTO, CardConstants.CARD_LOG_TXNTYPE_OTHER);

            if (!ResponseCode.SUCCESS.equals(retDTO.getRespcode())) {
                logRespexplain.append("[与城市前置交互失败]");
                logRespcode = retDTO.getRespcode();
            }
        }
        catch (Exception e) {
            logRespcode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append(e.getMessage());
            crdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            return crdDTO;
        }
        finally {
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[BJ][NFC]终端登记", "", "", this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(retDTO), CardConstants.CARD_LOG_TXNTYPE_OTHER);
        }

        return retDTO;
    }

    /*
     * 充值验卡
     * 1.判断绑定卡片的数量是否大于等于6是返回绑卡数量超限超限错误;
     * 2.判断此卡片是否绑定在同一用户上，不是返回错误信息(卡片已绑定在其他用户下);
     * 3.发现此卡片已经注册，无需城市前置用户信息注册,(充值验卡需要生成订单)
     * 4.当是新的卡片,调用城市前置用户信息注册接口进行验证。(充值验卡城市前置返回成功需要生成订单)
     */
    @Override
    public BJCrdSysOrderDTO chargeValidateCard(BJCrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());//日志开始时间
        StringBuffer logRespexplain = new StringBuffer();//日志响应吗描述
        String logRespcode = ResponseCode.SUCCESS;//日志响应吗
        String inPras = JSONObject.toJSONString(crdDTO);//入参

        BJCrdSysOrderDTO retDTO = new BJCrdSysOrderDTO();

        try {
            /******************** 校验空值 ********************/
            String emptyCode = checkQueryEmpty(crdDTO);
            if (!ResponseCode.SUCCESS.equals(emptyCode)) {
                logRespcode = emptyCode;
                logRespexplain.append("[校验参数失败]");
                crdDTO.setRespcode(emptyCode);
                return crdDTO;
            }

            /******************** 根据产品库订单号校验卡服务订单是否存在 ********************/
            int count = crdSysOrderMapper.checkCrdOrderByPrdId(crdDTO.getPrdordernum());
            if (count != 0) {
                logRespcode = ResponseCode.CARD_PRDORDERNUM_EXIST;
                logRespexplain.append("[卡服务订单已存在]");
                crdDTO.setRespcode(ResponseCode.CARD_PRDORDERNUM_EXIST);
                return crdDTO;
            }

            /******************** 校验卡内限额 ********************/
            //查询卡内限额
            Map<String, Object> retMap = cardTopupService.queryYktIpAndPort(crdDTO.getYktcode());
            if (StringUtils.isBlank(retMap.get("CARDLIMIT") + "")) {
                logRespcode = ResponseCode.CARD_LIMIT_NULL;
                logRespexplain.append("[卡内限额为空]");
                crdDTO.setRespcode(ResponseCode.CARD_LIMIT_NULL);
                return crdDTO;
            }
            crdDTO.setCardlimit(retMap.get("CARDLIMIT") + "");
            if (!ResponseCode.SUCCESS.equals(checkLimit(crdDTO))) {
                logRespcode = ResponseCode.CARD_LIMIT_ERROR;
                logRespexplain.append("[超过卡内限额]");
                crdDTO.setRespcode(ResponseCode.CARD_LIMIT_ERROR);
                return crdDTO;
            }

            /******************** 调用前置 ********************/
            String cityFrontUrl = CardConstants.BJ_NFC_CITY_FRONT_URL + CardConstants.BJ_NFC_CHARGE_VALIDATE_CARD;
            logRespexplain.append("[url=" + cityFrontUrl + "]");
            retDTO = bjCityFrontSocketService.sendBJCityFrontHttp(cityFrontUrl, crdDTO, CardConstants.CARD_LOG_TXNTYPE_CHARGE);

            //城市前置返回成功
            if (ResponseCode.SUCCESS.equals(retDTO.getRespcode())) {
                /******************** 创建卡服务订单 ********************/
                createCrdOrder(retDTO);
                logRespexplain.append("[创建订单成功]");
            } else {
                logRespexplain.append("[与城市前置交互失败]");
                logRespcode = retDTO.getRespcode();
            }
        }
        catch (Exception e) {
            logRespcode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append(e.getMessage());
            crdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            return crdDTO;
        }
        finally {
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[BJ][NFC]充值验卡", retDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(retDTO), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        }
        return retDTO;
    }

    @Transactional
    public void createCrdOrder(BJCrdSysOrderDTO crdDTO) {
        //获取卡服务订单号   P + 20150428222211 +五位数据库cycle sequence（循环使用）
        StringBuffer crdOrderNum = new StringBuffer();
        crdOrderNum.append("P");
        crdOrderNum.append(DateUtil.getCurrTime());
        crdOrderNum.append(crdSysOrderMapper.queryCrdSysOrdercodeSeq());
        crdDTO.setCrdordernum(crdOrderNum.toString());

        //组装订单数据
        CrdSysOrder order = new CrdSysOrder();

        order.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_CREATE_SUCCESS.getCode());//创建成功
        order.setCrdBeforeorderStates(CardOrderStatesEnum.CARD_ORDER_CREATE_SUCCESS.getCode());//创建成功
        order.setRespCode(ResponseCode.SUCCESS);
        order.setTxnType(Integer.valueOf(CardTxnTypeEnum.TXN_TYPE_RECHARGE.getCode()));//充值(1)
        order.setTradeEndFlag(Integer.valueOf(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode()));//交易结束(1)

        order.setTradeStepVer(crdDTO.getVer());
        order.setCrdOrderNum(crdDTO.getCrdordernum());
        order.setProOrderNum(crdDTO.getPrdordernum());
        order.setProCode(crdDTO.getProductcode());
        order.setMerCode(crdDTO.getMercode());
        order.setUserCode(crdDTO.getUsercode());
        order.setUserId(crdDTO.getUserid());
        order.setCityCode(crdDTO.getCitycode());
        order.setYktCode(crdDTO.getYktcode());
        order.setCardInnerNo(crdDTO.getCardinnerno());
        order.setCardFaceNo(crdDTO.getCardfaceno());
        order.setOrderCardNo(crdDTO.getCardfaceno());
        order.setCheckCardNo(crdDTO.getCardfaceno());
        order.setCheckCardPosCode(crdDTO.getPosid());
        order.setCreateUser(crdDTO.getUserid());
        order.setUpdateUser(crdDTO.getUserid());
        order.setPosCode(crdDTO.getPosid());
        order.setTxnDate(crdDTO.getTxndate());
        order.setTxnTime(crdDTO.getTxntime());
        order.setMobileNo(crdDTO.getMobileno());

        order.setCardType(Long.valueOf(crdDTO.getCardtype()));
        order.setPosType(Long.valueOf(crdDTO.getPostype()));
        order.setBefbal(Long.valueOf(crdDTO.getBefbal()));
        order.setTxnAmt(Long.valueOf(crdDTO.getTxnamt()));
        order.setCardLimit(Long.valueOf(crdDTO.getCardlimit()));
        order.setSource(Long.valueOf(crdDTO.getSource()));
        order.setChargeType(Integer.valueOf(crdDTO.getChargetype()));

        //组装补充信息数据
        CrdSysSupplement supplement = new CrdSysSupplement();

        BJNfcSpecdata nfcSpecdata = crdDTO.getNfcspecdata();
        if (nfcSpecdata != null) {
            supplement.setCheckYktkey(JSONObject.toJSONString(nfcSpecdata));
            supplement.setCheckYktmw(JSONObject.toJSONString(nfcSpecdata));
        }
        supplement.setCrdOrderNum(crdDTO.getCrdordernum());//卡服务订单号
        supplement.setTxnType(Integer.valueOf(CardTxnTypeEnum.TXN_TYPE_RECHARGE.getCode()));//交易类型 1-充值,0-查询

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

        //组装手机补充表数据
        CrdSys100000Mobile mobile = new CrdSys100000Mobile();

        mobile.setCrdOrderNum(crdDTO.getCrdordernum());
        mobile.setPrdOrderNum(crdDTO.getPrdordernum());
        mobile.setRequestno(crdDTO.getRequestno());
        mobile.setMobileno(crdDTO.getMobileno());
        mobile.setMobiletype(crdDTO.getMobiletype());
        mobile.setMobileimei(crdDTO.getMobileimei());
        mobile.setMobilesysver(crdDTO.getMobilesysver());
        mobile.setCardoverdraft(crdDTO.getCardoverdraft());
        mobile.setCardstartdate(crdDTO.getCardstartdate());
        mobile.setCardenddate(crdDTO.getCardenddate());
        mobile.setCardstartflag(crdDTO.getCardstartflag());
        mobile.setImsi(crdDTO.getImsi());
        mobile.setDealtype(crdDTO.getDealtype());
        mobile.setPaysource(crdDTO.getPaysource());
        mobile.setResponseno(crdDTO.getResponseno());
        mobile.setCreateUser(crdDTO.getUserid());
        mobile.setUpdateUser(crdDTO.getUserid());
        mobile.setCardNo(crdDTO.getCardfaceno());

        //插入订单表
        crdSysOrderMapper.insertCrdSysOrderForBJNfc(order);
        //插入补充表
        crdSysOrderMapper.insertCrdSysSupplement(supplement);
        //插入手机补充表
        crdSysOrderMapper.insertCrdSys100000Mobile(mobile);

    }

    private String checkQueryEmpty(BJCrdSysOrderDTO crdDTO) {
        if (StringUtils.isBlank(crdDTO.getPrdordernum())) {
            return ResponseCode.CARD_PRDORDERNUM_NULL;
        }
        return ResponseCode.SUCCESS;
    }

    /*
     * 充值申请起始
     */
    @Override
    public BJCrdSysOrderDTO chargeStart(BJCrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());//日志开始时间
        StringBuffer logRespexplain = new StringBuffer();//日志响应吗描述
        String logRespcode = ResponseCode.SUCCESS;//日志响应吗
        String inPras = JSONObject.toJSONString(crdDTO);//入参

        BJCrdSysOrderDTO retDTO = new BJCrdSysOrderDTO();
        try {
            /******************** 校验空值 ********************/
            String emptyCode = checkQueryEmpty(crdDTO);
            if (!ResponseCode.SUCCESS.equals(emptyCode)) {
                logRespcode = emptyCode;
                logRespexplain.append("[校验参数失败]");
                crdDTO.setRespcode(emptyCode);
                return crdDTO;
            }

            /******************** 根据产品库订单号校验卡服务订单是否存在 ********************/
            int count = crdSysOrderMapper.checkCrdOrderByPrdId(crdDTO.getPrdordernum());
            if (count != 1) {
                logRespcode = ResponseCode.CARD_PRDORDERNUM_NOT_EXIST;
                logRespexplain.append("[卡服务无此订单信息]");
                crdDTO.setRespcode(ResponseCode.CARD_PRDORDERNUM_NOT_EXIST);
                return crdDTO;
            }

            /******************** 校验卡服务订单状态与订单数据 ********************/
            CrdSysOrder crdSysOrder = icdcRechargeMapper.queryBJCardSysOrderByPrdOrderId(crdDTO.getPrdordernum());
            if (crdSysOrder == null) {
                logRespcode = ResponseCode.CARD_PRDORDERNUM_NOT_EXIST;
                logRespexplain.append("[卡服务无此订单信息]");
                crdDTO.setRespcode(ResponseCode.CARD_PRDORDERNUM_NOT_EXIST);
                return crdDTO;
            }
            crdDTO.setCrdordernum(crdSysOrder.getCrdOrderNum());

            //校验数据是否匹配
            String compareCode = compareOrder(crdSysOrder, crdDTO);
            if (!ResponseCode.SUCCESS.equals(compareCode)) {
                logRespcode = compareCode;
                logRespexplain.append("[参数与订单数据不匹配]");
                crdDTO.setRespcode(compareCode);
                return crdDTO;
            }

            //校验卡内限额
            if (!ResponseCode.SUCCESS.equals(checkLimit(crdDTO))) {
                logRespcode = ResponseCode.CARD_LIMIT_ERROR;
                logRespexplain.append("[超过卡内限额]");
                crdDTO.setRespcode(ResponseCode.CARD_LIMIT_ERROR);
                return crdDTO;
            }

            //校验订单状态
            if (!CardOrderStatesEnum.CARD_ORDER_CREATE_SUCCESS.getCode().equals(crdSysOrder.getCrdOrderStates())) {//1000000001
                logRespcode = ResponseCode.CARD_ORDER_STATES_ERROR;
                logRespexplain.append("[订单状态不正确]");
                crdDTO.setRespcode(ResponseCode.CARD_ORDER_STATES_ERROR);
                return crdDTO;
            }

            /******************** 与城市前置交互 ********************/
            String cityFrontUrl = CardConstants.BJ_NFC_CITY_FRONT_URL + CardConstants.BJ_NFC_CHARGE_START;
            logRespexplain.append("[url=" + cityFrontUrl + "]");
            retDTO = bjCityFrontSocketService.sendBJCityFrontHttp(cityFrontUrl, crdDTO, CardConstants.CARD_LOG_TXNTYPE_CHARGE);

            /******************** 变更卡服务订单状态 ********************/
            Map<String, Object> value = new HashMap<String, Object>(1);
            value.put("RESP_CODE", ResponseCode.SUCCESS);
            value.put("MER_ORDER_CODE", crdDTO.getMerordercode());
            value.put("USER_CODE", crdDTO.getUsercode());
            value.put("CRD_ORDER_NUM", crdDTO.getCrdordernum());
            value.put("UPDATE_USER", crdDTO.getUserid());
            //城市前置返回成功
            if (ResponseCode.SUCCESS.equals(retDTO.getRespcode())) {
                value.put("CRD_ORDER_STATES", CardOrderStatesEnum.CARD_ORDER_APPLY_READCARD_KEY_SUCCESS.getCode());//1000000003
                logRespexplain.append("[与前置交互成功03]");
            } else {//失败
                value.put("CRD_ORDER_STATES", CardOrderStatesEnum.CARD_ORDER_APPLY_READCARD_KEY_ERROR.getCode());//1000000004
                logRespexplain.append("[与前置交互成功04]");
            }
            //更新订单
            count = icdcRechargeMapper.updateCardSysOrderByCrdOrderId(value);
            if (count != 1) {
                logRespcode = ResponseCode.SYSTEM_ERROR;
                logRespexplain.append("[更新订单失败]");
                retDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            }
        }
        catch (Exception e) {
            logRespcode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append(e.getMessage());
            crdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            return crdDTO;
        }
        finally {
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[BJ][NFC]充值申请起始", retDTO.getCrdordernum(), retDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(retDTO), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        }
        return retDTO;
    }

    /*
     * 充值后续
     */
    @Override
    public BJCrdSysOrderDTO chargeFollow(BJCrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());//日志开始时间
        StringBuffer logRespexplain = new StringBuffer();//日志响应吗描述
        String logRespcode = ResponseCode.SUCCESS;//日志响应吗
        String inPras = JSONObject.toJSONString(crdDTO);//入参

        BJCrdSysOrderDTO retDTO = new BJCrdSysOrderDTO();

        try {
            /******************** 校验空值 ********************/
            String emptyCode = checkQueryEmpty(crdDTO);
            if (!ResponseCode.SUCCESS.equals(emptyCode)) {
                logRespcode = emptyCode;
                logRespexplain.append("[校验参数失败]");
                crdDTO.setRespcode(emptyCode);
                return crdDTO;
            }
            /******************** 根据产品库订单号校验卡服务订单是否存在 ********************/
            int count = crdSysOrderMapper.checkCrdOrderByPrdId(crdDTO.getPrdordernum());
            if (count != 1) {
                logRespcode = ResponseCode.CARD_PRDORDERNUM_NOT_EXIST;
                logRespexplain.append("[卡服务无此订单信息]");
                crdDTO.setRespcode(ResponseCode.CARD_PRDORDERNUM_NOT_EXIST);
                return crdDTO;
            }
            /******************** 校验订单数据 ********************/
            //获取卡服务订单
            CrdSysOrder crdSysOrder = icdcRechargeMapper.queryBJCardSysOrderByPrdOrderId(crdDTO.getPrdordernum());
            if (crdSysOrder == null) {
                logRespcode = ResponseCode.CARD_PRDORDERNUM_NOT_EXIST;
                logRespexplain.append("[卡服务无此订单信息]");
                crdDTO.setRespcode(ResponseCode.CARD_PRDORDERNUM_NOT_EXIST);
                return crdDTO;
            }
            crdDTO.setCrdordernum(crdSysOrder.getCrdOrderNum());
            String CrdOrderStates = crdSysOrder.getCrdOrderStates();
            logRespexplain.append("[订单状态=" + CrdOrderStates + "]");

            //校验数据是否匹配
            String compareCode = compareOrder(crdSysOrder, crdDTO);
            if (!ResponseCode.SUCCESS.equals(compareCode)) {
                logRespcode = compareCode;
                logRespexplain.append("[参数与订单数据不匹配]");
                crdDTO.setRespcode(compareCode);
                return crdDTO;
            }

            //校验卡内限额
            if (!ResponseCode.SUCCESS.equals(checkLimit(crdDTO))) {
                logRespcode = ResponseCode.CARD_LIMIT_ERROR;
                logRespexplain.append("[超过卡内限额]");
                crdDTO.setRespcode(ResponseCode.CARD_LIMIT_ERROR);
                return crdDTO;
            }

            /******************** 向城市前置发送请求 ********************/
            //判断订单状态为03,05,07
            if (CardOrderStatesEnum.CARD_ORDER_APPLY_READCARD_KEY_SUCCESS.getCode().equals(CrdOrderStates) || CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY.getCode().equals(CrdOrderStates) || CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY.getCode().equals(CrdOrderStates)) {
                //订单状态为03
                if (CardOrderStatesEnum.CARD_ORDER_APPLY_READCARD_KEY_SUCCESS.getCode().equals(CrdOrderStates)) {
                    //申请前更新卡服务订单状态 05
                    updateOrderChargeBefByCrdnum(crdSysOrder, crdDTO);
                    logRespexplain.append("[申请前变更订单状态05]");
                }

                //向城市前置发送请求
                String cityFrontUrl = CardConstants.BJ_NFC_CITY_FRONT_URL + CardConstants.BJ_NFC_CHARGE_FOLLOW;
                retDTO = bjCityFrontSocketService.sendBJCityFrontHttp(cityFrontUrl, crdDTO, CardConstants.CARD_LOG_TXNTYPE_CHARGE);

                if (!ResponseCode.SUCCESS.equals(retDTO.getRespcode())) {//向城市前置申请充值失败
                    logRespexplain.append("[与前置交互失败]");
                    if (StringUtils.isBlank(retDTO.getTradeendflag())) {
                        retDTO.setTradeendflag(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode());//设置交易结束标志为1
                    }
                    //011003和000047错误码需要锁卡
                    if ("000047".equals(retDTO.getRespcode())) {
                        logRespexplain.append("[黑名单需要锁卡]");
                        retDTO.setBlackflag("1");
                    }
                    //申请后更新订单状态 06
                    logRespexplain.append("[更新订单06]");
                    updateOrderChargeAfterByCrdnum(crdSysOrder, retDTO, CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY_ERROR.getCode());
                } else {//向城市前置申请充值成功
                    logRespexplain.append("[与前置交互成功]");
                    if (checkEndFlag(retDTO.getNfcspecdata().getApduseq())) {//交易结束标志为1,且验证8052
                        logRespexplain.append("[8052]");
                        //申请后更新订单状态 07
                        logRespexplain.append("[更新订单07]");
                        updateOrderChargeAfterByCrdnum(crdSysOrder, retDTO, CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY_SUCCESS.getCode());
                    }
                }
            } else {
                logRespcode = ResponseCode.CARD_ORDER_STATES_ERROR;
                logRespexplain.append("[订单状态不正确]");
                crdDTO.setRespcode(ResponseCode.CARD_ORDER_STATES_ERROR);//订单状态不正确
                return crdDTO;
            }
        }
        catch (Exception e) {
            logRespcode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append(e.getMessage());
            crdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            return crdDTO;
        }
        finally {
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[BJ][NFC]充值申请后续", retDTO.getCrdordernum(), retDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(retDTO), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        }
        return retDTO;
    }

    private boolean checkEndFlag(String apduseq) {
        int apduseqlen = 0;
        while (apduseqlen + 6 < apduseq.length()) {
            apduseqlen += 4;
            int apdulen = Integer.parseInt(BJStringUtil.getHexToDec(apduseq.substring(apduseqlen, apduseqlen += 2))) * 2;
            if (apdulen < 4) {
                continue;
            }
            String apdu = apduseq.substring(apduseqlen, apduseqlen += apdulen);
            if (apdu.subSequence(0, 4).equals("8052")) {
                //申请充值密钥成功
                return true;
            }
        }
        return false;
    }

    /*
     * 充值申请前更新订单
     */
    @Transactional
    private void updateOrderChargeBefByCrdnum(CrdSysOrder crdSysOrder, BJCrdSysOrderDTO crdDTO) {
        crdSysOrder.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY.getCode());//更新订单状态为申请充值密钥(1000000005)
        crdSysOrder.setChargeCardNo(crdDTO.getTradecard());//充值卡号(chargecardno)
        crdSysOrder.setChargeCardPosCode(crdDTO.getPosid());//充值pos号
        crdSysOrder.setTradeStepVer(BJStringUtil.removeVerZero(crdDTO.getVer()));//交易步骤版本(tradestepver)

        CrdSysSupplement crdSysSupplement = new CrdSysSupplement();
        crdSysSupplement.setCrdOrderNum(crdDTO.getCrdordernum());
        String specdate = JSONObject.toJSONString(crdDTO.getNfcspecdata());//特属域 
        crdSysSupplement.setChargeYktmw(specdate);
        String lastChargeKeyTime = DateUtils.dateToStrLongs(new Date());
        crdSysSupplement.setLastChargeKeyTime(lastChargeKeyTime);

        //更新order表
        crdSysOrderMapper.updateCrdSysOrderByCrdnum(crdSysOrder);
        //更新supplement表
        crdSysOrderMapper.updateCrdSysSupplementByCrdnumBef(crdSysSupplement);
    }

    /*
     * 充值申请后更新订单
     */
    @Transactional
    private void updateOrderChargeAfterByCrdnum(CrdSysOrder crdSysOrder, BJCrdSysOrderDTO crdDTO, String state) {
        crdSysOrder.setCrdOrderStates(state);//申请充值密钥成功(1000000007);申请充值密钥失败(1000000006)

        crdSysOrder.setRespCode(crdDTO.getRespcode());//交易应答码(respcode)
        crdSysOrder.setTradeStepVer(BJStringUtil.removeVerZero(crdDTO.getVer()));//交易步骤版本(tradestepver)
        crdSysOrder.setTradeEndFlag(Integer.valueOf(crdDTO.getTradeendflag()));//交易结束标志(tradeendflag)

        CrdSysSupplement crdSysSupplement = new CrdSysSupplement();
        crdSysSupplement.setCrdOrderNum(crdDTO.getCrdordernum());
        if (ResponseCode.SUCCESS.equals(crdDTO.getRespcode())) {
            crdSysSupplement.setChargeKey(JSONObject.toJSONString(crdDTO.getNfcspecdata()));//充值密钥(chargekey)
        }

        //更新order表
        crdSysOrderMapper.updateCrdSysOrderByCrdnum(crdSysOrder);
        //更新supplement表
        crdSysOrderMapper.updateCrdSysSupplementByCrdnumAfr(crdSysSupplement);
    }

    private String checkLimit(BJCrdSysOrderDTO crdDTO) {
        long limit = Long.valueOf(crdDTO.getCardlimit());
        long txnamt = Long.valueOf(crdDTO.getTxnamt());
        long befamt = Long.valueOf(crdDTO.getBefbal());
        if ((befamt + txnamt) > limit) {
            return ResponseCode.CARD_LIMIT_ERROR;
        }
        return ResponseCode.SUCCESS;
    }

    private String compareOrder(CrdSysOrder crdSysOrder, BJCrdSysOrderDTO crdDTO) {
        if (!crdSysOrder.getOrderCardNo().equals(crdDTO.getTradecard())) {//卡号
            return ResponseCode.CARD_TRADECARD_ERROR;
        }
        if (!(crdSysOrder.getTxnAmt() == Long.valueOf(crdDTO.getTxnamt()))) {//交易金额
            return ResponseCode.CARD_TXNAMT_ERROR;
        }
        if (!crdDTO.getYktcode().equals(crdSysOrder.getYktCode())) {//一卡通编号 (yktcode)
            return ResponseCode.CARD_YKTCODE_ERROR;
        }
        if (!crdDTO.getCitycode().equals(crdSysOrder.getCityCode())) {//城市代码 (citycode)
            return ResponseCode.CARD_CITYCODE_ERROR;
        }
        if (!crdDTO.getPosid().equals(crdSysOrder.getPosCode())) {//设备编号 (posid)
            return ResponseCode.CARD_POSID_ERROR;
        }
        if (Long.valueOf(crdDTO.getBefbal()) != crdSysOrder.getBefbal()) {//交易前卡余额 (befbal)
            return ResponseCode.CARD_BEFBAL_ERROR;
        }
        return ResponseCode.SUCCESS;
    }

    /*
     * 结果上传
     */
    @Override
    public BJCrdSysOrderDTO chargeResultUp(BJCrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());//日志开始时间
        StringBuffer logRespexplain = new StringBuffer();//日志响应吗描述
        String logRespcode = ResponseCode.SUCCESS;//日志响应吗
        String inPras = JSONObject.toJSONString(crdDTO);//入参

        BJCrdSysOrderDTO retDTO = new BJCrdSysOrderDTO();

        try {
            /******************** 校验空值 ********************/
            String emptyCode = checkQueryEmpty(crdDTO);
            if (!ResponseCode.SUCCESS.equals(emptyCode)) {
                logRespcode = emptyCode;
                logRespexplain.append("[校验参数失败]");
                crdDTO.setRespcode(emptyCode);
                return crdDTO;
            }
            /******************** 根据产品库订单号校验卡服务订单是否存在 ********************/
            int count = crdSysOrderMapper.checkCrdOrderByPrdId(crdDTO.getPrdordernum());
            if (count != 1) {
                logRespcode = ResponseCode.CARD_PRDORDERNUM_NOT_EXIST;
                logRespexplain.append("[卡服务无此订单信息]");
                crdDTO.setRespcode(ResponseCode.CARD_PRDORDERNUM_NOT_EXIST);
                return crdDTO;
            }
            /******************** 校验订单数据 ********************/
            //获取卡服务订单
            CrdSysOrder crdSysOrder = icdcRechargeMapper.queryBJCardSysOrderByPrdOrderId(crdDTO.getPrdordernum());
            if (crdSysOrder == null) {
                logRespcode = ResponseCode.CARD_PRDORDERNUM_NOT_EXIST;
                logRespexplain.append("[卡服务无此订单信息]");
                crdDTO.setRespcode(ResponseCode.CARD_PRDORDERNUM_NOT_EXIST);
                return crdDTO;
            }
            crdDTO.setCrdordernum(crdSysOrder.getCrdOrderNum());
            String CrdOrderStates = crdSysOrder.getCrdOrderStates();
            logRespexplain.append("[订单状态=" + CrdOrderStates + "]");

            //校验数据是否匹配
            String compareCode = compareOrder(crdSysOrder, crdDTO);
            if (!ResponseCode.SUCCESS.equals(compareCode)) {
                logRespcode = compareCode;
                logRespexplain.append("[参数与订单数据不匹配]");
                crdDTO.setRespcode(compareCode);
                return crdDTO;
            }

            //校验卡内限额
            if (!ResponseCode.SUCCESS.equals(checkLimit(crdDTO))) {
                logRespcode = ResponseCode.CARD_LIMIT_ERROR;
                logRespexplain.append("[超过卡内限额]");
                crdDTO.setRespcode(ResponseCode.CARD_LIMIT_ERROR);
                return crdDTO;
            }

            /******************** 向城市前置发送请求 ********************/
            String txnstate = crdDTO.getTxnstat();
            logRespexplain.append("[txnstat=" + txnstate + "]");
            //txnstat!=0 充值不成功
            if (!CardTxnStatEnum.TXN_STAT_SUCCESS.getCode().equals(txnstate)) {
                //变更订单状态：10 未知
                String resultCode = uploadResultCheck(crdDTO, CardOrderStatesEnum.CARD_ORDER_RECHARGE_UNKNOW.getCode());
                if (!ResponseCode.SUCCESS.equals(resultCode)) {
                    logRespcode = resultCode;
                    logRespexplain.append("[上传校验更新订单失败]");
                    crdDTO.setRespcode(resultCode);
                    return crdDTO;
                }
            } else {//txnstat=0 充值成功
                //校验订单是否为07
                if (!CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY_SUCCESS.getCode().equals(CrdOrderStates)) {
                    logRespcode = ResponseCode.CARD_ORDER_STATES_ERROR;
                    logRespexplain.append("[订单状态不正确]");
                    crdDTO.setRespcode(ResponseCode.CARD_ORDER_STATES_ERROR);//订单状态不正确
                    return crdDTO;
                } else {
                    //TODO 为了订单状态一致性,nfc仍然分两步变更订单
                    //变更订单状态 08 
                    String resultCode = uploadResultCheck(crdDTO, CardOrderStatesEnum.CARD_ORDER_RECHARGE_SUCCESS.getCode());
                    if (!ResponseCode.SUCCESS.equals(resultCode)) {
                        logRespcode = resultCode;
                        logRespexplain.append("[上传校验更新订单失败]");
                        crdDTO.setRespcode(resultCode);
                        return crdDTO;
                    }
                    //变更订单状态 12
                    resultCode = uploadResultBef(crdDTO);
                    if (!ResponseCode.SUCCESS.equals(resultCode)) {
                        logRespcode = resultCode;
                        logRespexplain.append("[上传前更新订单失败]");
                        crdDTO.setRespcode(resultCode);
                        return crdDTO;
                    }

                    //向前置发送请求
                    String cityFrontUrl = CardConstants.BJ_NFC_CITY_FRONT_URL + CardConstants.BJ_NFC_CHARGE_RESULTUP;
                    retDTO = bjCityFrontSocketService.sendBJCityFrontHttp(cityFrontUrl, crdDTO, CardConstants.CARD_LOG_TXNTYPE_CHARGE);

                    if (!ResponseCode.SUCCESS.equals(retDTO.getRespcode())) {//失败
                        logRespexplain.append("[与前置交互失败]");
                        if (StringUtils.isBlank(retDTO.getTradeendflag())) {
                            retDTO.setTradeendflag(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode());//设置交易结束标志为1
                        }
                        //更新订单状态 10
                        logRespexplain.append("[更新订单10]");
                        resultCode = uploadResultAfter(crdDTO, CardOrderStatesEnum.CARD_ORDER_RECHARGE_UNKNOW.getCode());
                        if (!ResponseCode.SUCCESS.equals(resultCode)) {
                            logRespcode = resultCode;
                            logRespexplain.append("[上传后更新订单失败]");
                            retDTO.setRespcode(resultCode);
                            return retDTO;
                        }
                    } else {//成功
                        logRespexplain.append("[与前置交互成功]");
                        logRespexplain.append("[更新订单18]");
                        resultCode = uploadResultAfter(crdDTO, CardOrderStatesEnum.CARD_ORDER_UPLOAD_RESULT_SUCCESS.getCode());
                        if (!ResponseCode.SUCCESS.equals(resultCode)) {
                            logRespcode = resultCode;
                            logRespexplain.append("[上传后更新订单失败]");
                            retDTO.setRespcode(resultCode);
                            return retDTO;
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            logRespcode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append(e.getMessage());
            crdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            return crdDTO;
        }
        finally {
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[BJ][NFC]结果上传", retDTO.getCrdordernum(), retDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(retDTO), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        }
        return retDTO;
    }

    @Transactional
    private String uploadResultCheck(BJCrdSysOrderDTO crdDTO, String state) {
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

        crdOrder.put("CRD_ORDER_STATES", state);
        //更新卡服务订单状态
        count = icdcRechargeMapper.updateCardSysOrderByCrdOrderId(crdOrder);
        if (count != 1) {//更新失败
            return ResponseCode.SYSTEM_ERROR;
        }
        //更新卡服务订单子表特殊域信息和TAC信息
        count = icdcRechargeMapper.updateCardSysSupplementByCrdOrderId(crdSupplement);
        if (count != 1) {//更新失败
            return ResponseCode.SYSTEM_ERROR;
        }
        return ResponseCode.SUCCESS;
    }

    private String uploadResultBef(BJCrdSysOrderDTO crdDTO) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String now = simpleDateFormat.format(new Date());
        Map<String, Object> value = new HashMap<String, Object>(1);
        value.put("RESULT_SEND_CARD_DATE", now);
        value.put("TRADE_STEP", crdDTO.getMessagetype());
        value.put("TRADE_STEP_VER", crdDTO.getVer());
        value.put("CRD_ORDER_NUM", crdDTO.getCrdordernum());
        value.put("UPDATE_USER", crdDTO.getUserid());
        value.put("CRD_ORDER_STATES", CardOrderStatesEnum.CARD_ORDER_UPLOAD_RESULT.getCode());//1000000012
        value.put("POS_TRANS_SEQ", BJStringUtil.format10to16(crdDTO.getPostransseq()));
        //结果上传后更新订单
        int updateCount = icdcRechargeService.updateCardSysOrderByCrdOrderId(value);
        if (updateCount != 1) {
            return ResponseCode.SYSTEM_ERROR;
        }
        return ResponseCode.SUCCESS;
    }

    private String uploadResultAfter(BJCrdSysOrderDTO crdDTO, String state) {
        int updateCount = 0;//更新记录条数
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String now = simpleDateFormat.format(new Date());
        Map<String, Object> value = new HashMap<String, Object>(1);
        value.put("CRD_ORDER_STATES", state);
        value.put("RESULT_RES_CARD_DATE", now);
        value.put("TRADE_STEP", crdDTO.getMessagetype());
        value.put("TRADE_STEP_VER", crdDTO.getVer());
        value.put("RESP_CODE", crdDTO.getRespcode());
        value.put("TRADE_STEP", crdDTO.getMessagetype());
        value.put("TRADE_STEP_VER", crdDTO.getVer());
        value.put("TRADE_END_FLAG", crdDTO.getTradeendflag());
        value.put("CRD_ORDER_NUM", crdDTO.getCrdordernum());
        value.put("UPDATE_USER", crdDTO.getUserid());
        updateCount = icdcRechargeService.updateCardSysOrderByCrdOrderId(value);
        if (updateCount != 1) {
            return ResponseCode.SYSTEM_ERROR;
        }
        return ResponseCode.SUCCESS;
    }

    @Override
    public BJCrdSysOrderDTO reChargeApplyStart(BJCrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());//日志开始时间
        StringBuffer logRespexplain = new StringBuffer();//日志响应吗描述
        String logRespcode = ResponseCode.SUCCESS;//日志响应吗
        String inPras = JSONObject.toJSONString(crdDTO);//入参

        BJCrdSysOrderDTO retDTO = new BJCrdSysOrderDTO();

        try {
            /******************** 校验空值 ********************/
            String emptyCode = checkQueryEmpty(crdDTO);
            if (!ResponseCode.SUCCESS.equals(emptyCode)) {
                logRespcode = emptyCode;
                logRespexplain.append("[校验参数失败]");
                crdDTO.setRespcode(emptyCode);
                return crdDTO;
            }
            /******************** 根据产品库订单号校验卡服务订单是否存在 ********************/
            int count = crdSysOrderMapper.checkCrdOrderByPrdId(crdDTO.getPrdordernum());
            if (count != 1) {
                logRespcode = ResponseCode.CARD_PRDORDERNUM_NOT_EXIST;
                logRespexplain.append("[卡服务无此订单信息]");
                crdDTO.setRespcode(ResponseCode.CARD_PRDORDERNUM_NOT_EXIST);
                return crdDTO;
            }
            /******************** 校验订单数据 ********************/
            //获取卡服务订单
            CrdSysOrder crdSysOrder = icdcRechargeMapper.queryBJCardSysOrderByPrdOrderId(crdDTO.getPrdordernum());
            if (crdSysOrder == null) {
                logRespcode = ResponseCode.CARD_PRDORDERNUM_NOT_EXIST;
                logRespexplain.append("[卡服务无此订单信息]");
                crdDTO.setRespcode(ResponseCode.CARD_PRDORDERNUM_NOT_EXIST);
                return crdDTO;
            }
            crdDTO.setCrdordernum(crdSysOrder.getCrdOrderNum());
            String CrdOrderStates = crdSysOrder.getCrdOrderStates();
            logRespexplain.append("[订单状态=" + CrdOrderStates + "]");

            //校验数据是否匹配
            String compareCode = compareOrder(crdSysOrder, crdDTO);
            if (!ResponseCode.SUCCESS.equals(compareCode)) {
                logRespcode = compareCode;
                logRespexplain.append("[参数与订单数据不匹配]");
                crdDTO.setRespcode(compareCode);
                return crdDTO;
            }

            //校验卡内限额
            if (!ResponseCode.SUCCESS.equals(checkLimit(crdDTO))) {
                logRespcode = ResponseCode.CARD_LIMIT_ERROR;
                logRespexplain.append("[超过卡内限额]");
                crdDTO.setRespcode(ResponseCode.CARD_LIMIT_ERROR);
                return crdDTO;
            }

            /******************** 向城市前置发送请求 ********************/
            String txnstate = crdDTO.getTxnstat();
            logRespexplain.append("[txnstat=" + txnstate + "]");

            //txnstat!=2 交易状态不为未知 返回状态异常
            if (!CardTxnStatEnum.TXN_STAT_UNKNOW.getCode().equals(txnstate)) {
                logRespcode = ResponseCode.CARD_TXNSTAT_ERROR;
                logRespexplain.append("[txnstat不为2:" + txnstate + "]");
                crdDTO.setRespcode(ResponseCode.CARD_TXNSTAT_ERROR);
                return crdDTO;
            }

            //txnstat=2 交易状态未知
            if (CardOrderStatesEnum.CARD_ORDER_APPLY_READCARD_KEY_SUCCESS.getCode().equals(CrdOrderStates)) {//03

                //当订单状态为:1000000003(申请读卡密钥成功),更新订单状态1000000009(卡片充值失败),并告知产品库更新失败并解冻;
                logRespexplain.append("[更新订单09]");
                String resultCode = uploadResultCheck(crdDTO, CardOrderStatesEnum.CARD_ORDER_RECHARGE_ERROR.getCode());//09
                if (!ResponseCode.SUCCESS.equals(resultCode)) {
                    logRespcode = ResponseCode.SYSTEM_ERROR;
                    logRespexplain.append("[更新09失败]");
                    crdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
                    return crdDTO;
                }
                logRespcode = ResponseCode.CARD_RECHARGEAPPLY_END;
                logRespexplain.append("[充值失败,返回产品库解冻]");
                crdDTO.setRespcode(ResponseCode.CARD_RECHARGEAPPLY_END);//190021
                return crdDTO;

            } else if (CardOrderStatesEnum.CARD_ORDER_RECHARGE_UNKNOW.getCode().equals(CrdOrderStates) || CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY_SUCCESS.getCode().equals(CrdOrderStates)) {//10,07

                //判断订单状态为:1000000010(卡片充值未知)或1000000007(申请充值密钥成功)，调用前置补充值申请接口;
                //向前置发送请求
                String cityFrontUrl = CardConstants.BJ_NFC_CITY_FRONT_URL + CardConstants.BJ_NFC_RECHARGE_APPLY_START;
                retDTO = bjCityFrontSocketService.sendBJCityFrontHttp(cityFrontUrl, crdDTO, CardConstants.CARD_LOG_TXNTYPE_CHARGE);
                String retCode = retDTO.getRespcode();

                if (!ResponseCode.SUCCESS.equals(retCode)) {//非全0
                    logRespexplain.append("[前置返回非全0:" + retCode + "]");
                    if (StringUtils.isBlank(retDTO.getTradeendflag())) {
                        retDTO.setTradeendflag(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode());//设置交易结束标志为1
                    }

                    //190021 明确充值失败,更新订单状态为1000000019
                    if (ResponseCode.CARD_RECHARGEAPPLY_END.equals(retCode)) {
                        logRespexplain.append("[更新订单19]");
                        String resultCode = uploadResultAfter(retDTO, CardOrderStatesEnum.CARD_ORDER_APPLY_REVERSE_SUCCESS.getCode());//19
                        if (!ResponseCode.SUCCESS.equals(resultCode)) {
                            logRespcode = ResponseCode.SYSTEM_ERROR;
                            logRespexplain.append("[更新19失败]");
                            retDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
                        }
                        return retDTO;
                    }

                    //其他非全0应答码,更新订单状态为：1000000010(卡片充值未知)
                    logRespexplain.append("[更新订单10]");
                    String resultCode = uploadResultAfter(retDTO, CardOrderStatesEnum.CARD_ORDER_RECHARGE_UNKNOW.getCode());//10
                    if (!ResponseCode.SUCCESS.equals(resultCode)) {
                        logRespcode = ResponseCode.SYSTEM_ERROR;
                        logRespexplain.append("[更新10失败]");
                        retDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
                    }
                    return retDTO;
                }

            } else {//卡服务状态不正确
                logRespcode = ResponseCode.CARD_ORDER_STATES_ERROR;
                logRespexplain.append("[订单状态不正确]");
                crdDTO.setRespcode(ResponseCode.CARD_ORDER_STATES_ERROR);
                return crdDTO;
            }
        }
        catch (Exception e) {
            logRespcode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append(e.getMessage());
            crdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            return crdDTO;
        }
        finally {
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[BJ][NFC]补充值申请起始", retDTO.getCrdordernum(), retDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(retDTO), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        }
        return retDTO;
    }

    @Override
    public BJCrdSysOrderDTO reChargeApplyFollow(BJCrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());//日志开始时间
        StringBuffer logRespexplain = new StringBuffer();//日志响应吗描述
        String logRespcode = ResponseCode.SUCCESS;//日志响应吗
        String inPras = JSONObject.toJSONString(crdDTO);//入参

        BJCrdSysOrderDTO retDTO = new BJCrdSysOrderDTO();

        try {
            /******************** 校验空值 ********************/
            String emptyCode = checkQueryEmpty(crdDTO);
            if (!ResponseCode.SUCCESS.equals(emptyCode)) {
                logRespcode = emptyCode;
                logRespexplain.append("[校验参数失败]");
                crdDTO.setRespcode(emptyCode);
                return crdDTO;
            }
            /******************** 根据产品库订单号校验卡服务订单是否存在 ********************/
            int count = crdSysOrderMapper.checkCrdOrderByPrdId(crdDTO.getPrdordernum());
            if (count != 1) {
                logRespcode = ResponseCode.CARD_PRDORDERNUM_NOT_EXIST;
                logRespexplain.append("[卡服务无此订单信息]");
                crdDTO.setRespcode(ResponseCode.CARD_PRDORDERNUM_NOT_EXIST);
                return crdDTO;
            }
            /******************** 校验订单数据 ********************/
            //获取卡服务订单
            CrdSysOrder crdSysOrder = icdcRechargeMapper.queryBJCardSysOrderByPrdOrderId(crdDTO.getPrdordernum());
            if (crdSysOrder == null) {
                logRespcode = ResponseCode.CARD_PRDORDERNUM_NOT_EXIST;
                logRespexplain.append("[卡服务无此订单信息]");
                crdDTO.setRespcode(ResponseCode.CARD_PRDORDERNUM_NOT_EXIST);
                return crdDTO;
            }
            crdDTO.setCrdordernum(crdSysOrder.getCrdOrderNum());
            String CrdOrderStates = crdSysOrder.getCrdOrderStates();
            logRespexplain.append("[订单状态=" + CrdOrderStates + "]");

            //校验数据是否匹配
            String compareCode = compareOrder(crdSysOrder, crdDTO);
            if (!ResponseCode.SUCCESS.equals(compareCode)) {
                logRespcode = compareCode;
                logRespexplain.append("[参数与订单数据不匹配]");
                crdDTO.setRespcode(compareCode);
                return crdDTO;
            }

            //校验卡内限额
            if (!ResponseCode.SUCCESS.equals(checkLimit(crdDTO))) {
                logRespcode = ResponseCode.CARD_LIMIT_ERROR;
                logRespexplain.append("[超过卡内限额]");
                crdDTO.setRespcode(ResponseCode.CARD_LIMIT_ERROR);
                return crdDTO;
            }

            /******************** 向城市前置发送请求 ********************/
            String txnstate = crdDTO.getTxnstat();
            logRespexplain.append("[txnstat=" + txnstate + "]");

            //txnstat!=2 交易状态不为未知 返回状态异常
            if (!CardTxnStatEnum.TXN_STAT_UNKNOW.getCode().equals(txnstate)) {
                logRespcode = ResponseCode.CARD_TXNSTAT_ERROR;
                logRespexplain.append("[txnstat不为2:" + txnstate + "]");
                crdDTO.setRespcode(ResponseCode.CARD_TXNSTAT_ERROR);
                return crdDTO;
            }

            //订单状态  !=10
            if (!CardOrderStatesEnum.CARD_ORDER_RECHARGE_UNKNOW.getCode().equals(CrdOrderStates)) {//10
                logRespcode = ResponseCode.CARD_ORDER_STATES_ERROR;
                logRespexplain.append("[卡服务订单状态不为10]");
                crdDTO.setRespcode(ResponseCode.CARD_ORDER_STATES_ERROR);
                return crdDTO;

            }

            //向前置发送请求
            String cityFrontUrl = CardConstants.BJ_NFC_CITY_FRONT_URL + CardConstants.BJ_NFC_RECHARGE_APPLY_START;
            retDTO = bjCityFrontSocketService.sendBJCityFrontHttp(cityFrontUrl, crdDTO, CardConstants.CARD_LOG_TXNTYPE_CHARGE);
            String retCode = retDTO.getRespcode();
            logRespcode = retCode;

            if (!ResponseCode.SUCCESS.equals(retCode)) {//非全0
                logRespexplain.append("[前置返回非全0:" + retCode + "]");
                if (StringUtils.isBlank(retDTO.getTradeendflag())) {
                    retDTO.setTradeendflag(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode());//设置交易结束标志为1
                }

                //193013 一卡通错误码为:0156,不更新订单状态,通知前端需要即时发起补充值
                if ("193013".equals(retCode)) {
                    logRespexplain.append("[一卡通错误码为:0156,不更新订单状态,通知前端需要即时发起补充值]");
                    return retDTO;
                }

                //193014 一卡通错误码为:014F,不更新订单状态,通知前端需要延时发起补充值
                if ("193014".equals(retCode)) {
                    logRespexplain.append("[一卡通错误码为:014F,不更新订单状态,通知前端需要延时发起补充值]");
                    return retDTO;
                }

                //193012 一卡通错误码为:0150,更新订单状态1000000008(卡片充值成功) 和交易后卡余额=交易金额+交易前卡余额
                //通知产品库进行扣款操作并更新订单状态，通知前端审核不成功不能做补充值。
                if ("193012".equals(retCode)) {
                    logRespexplain.append("[一卡通错误码为:0150,更新订单状态08]");
                    retDTO.setBlackamt((Integer.valueOf(crdDTO.getBefbal() + Integer.valueOf(crdDTO.getTxnamt()))) + "");
                    String resultCode = uploadResultCheck(retDTO, CardOrderStatesEnum.CARD_ORDER_RECHARGE_SUCCESS.getCode());
                    if (!ResponseCode.SUCCESS.equals(resultCode)) {
                        logRespcode = resultCode;
                        logRespexplain.append("[更新08失败]");
                        retDTO.setRespcode(resultCode);
                    }
                    return retDTO;
                }

                //190021 一卡通错误码为:0180，更新订单状态为1000000019(一卡通确认冲正成功),通知产品库进行解冻操作并更新订单状态，通知前端充值失败
                if ("190021".equals(retCode)) {
                    logRespexplain.append("[一卡通错误码为:0180,更新订单19]");
                    String resultCode = uploadResultAfter(retDTO, CardOrderStatesEnum.CARD_ORDER_APPLY_REVERSE_SUCCESS.getCode());//19
                    if (!ResponseCode.SUCCESS.equals(resultCode)) {
                        logRespcode = ResponseCode.SYSTEM_ERROR;
                        logRespexplain.append("[更新19失败]");
                        retDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
                    }
                    return retDTO;
                }

                //其他非全0应答码,一卡通错误码非全零:不更新订单状态 ,通知前端未知。
                return retDTO;
            }
            //一卡通错误码为全零:不更新订单状态,下发对应的指令信息。
        }
        catch (Exception e) {
            logRespcode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append(e.getMessage());
            crdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            return crdDTO;
        }
        finally {
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[BJ][NFC]补充值申请后续", retDTO.getCrdordernum(), retDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(retDTO), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        }
        return retDTO;
    }
}