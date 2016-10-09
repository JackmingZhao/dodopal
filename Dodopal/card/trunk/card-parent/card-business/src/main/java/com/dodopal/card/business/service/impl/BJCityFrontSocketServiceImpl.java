package com.dodopal.card.business.service.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.dodopal.api.card.dto.BJAccountConsumeDTO;
import com.dodopal.api.card.dto.BJAccountConsumeSpecialDTO;
import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.api.card.dto.BJDiscountDTO;
import com.dodopal.api.card.dto.BJIntegralConsumeDTO;
import com.dodopal.api.card.dto.BJIntegralConsumeSpecialDTO;
import com.dodopal.api.card.dto.BJNfcSpecdata;
import com.dodopal.api.card.dto.SignParameter;
import com.dodopal.card.business.log.LogHelper;
import com.dodopal.card.business.log.SysLogHelper;
import com.dodopal.card.business.model.dto.BJCityFrontDTO;
import com.dodopal.card.business.model.dto.BJNfcCityFrontDTO;
import com.dodopal.card.business.service.BJCityFrontSocketService;
import com.dodopal.card.business.socket.SocketOperator;
import com.dodopal.card.business.util.BJStringUtil;
import com.dodopal.card.business.util.DateUtil;
import com.dodopal.card.business.util.HttpUtil;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.CardTradeEndFlagEnum;

@Service
public class BJCityFrontSocketServiceImpl implements BJCityFrontSocketService {
    private final static Logger log = LoggerFactory.getLogger(BJCityFrontSocketServiceImpl.class);

    @Autowired
    private LogHelper logHelper;

    @Autowired
    private SysLogHelper sysLogHelper;

    /*
     * 向北京前置发送报文
     */
    @Override
    public BJCrdSysOrderDTO sendBJCityFrontSocket(String ip, int port, BJCrdSysOrderDTO crdDTO, int txnType) {
        //日志方法开始时间
        Long tradestart = Long.valueOf(DateUtil.getCurrSdTime());
        //日志响应码描述
        StringBuffer respexplain = new StringBuffer();
        //日志响应码
        String logRespcode = ResponseCode.SUCCESS;

        //城市前置传输对象
        BJCityFrontDTO cityFrontOrderDTO = new BJCityFrontDTO();
        BeanUtils.copyProperties(crdDTO, cityFrontOrderDTO);

        //发送到城市前置json
        String jsonStr = "";
        //城市前置返回json
        String retJsonStr = "";
        BJCityFrontDTO retCityDTO = new BJCityFrontDTO();
        BJCrdSysOrderDTO retDTO = new BJCrdSysOrderDTO();
        try {
            //封装
            jsonStr = JSONObject.toJSONString(cityFrontOrderDTO);
            //发送socket
            Long socketStart = Long.valueOf(DateUtil.getCurrSdTime());
            //防止超时无返回的情况，在发送socke之前记录一条日志
            logHelper.recordLogFun(jsonStr, tradestart, logRespcode, respexplain.toString(), "[" + crdDTO.getMessagetype() + "," + crdDTO.getVer() + "]向城市前置发送socket(前)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), retJsonStr, txnType);
            retJsonStr = new SocketOperator().AsyncClient(jsonStr, ip, port);
            Long socketEnd = Long.valueOf(DateUtil.getCurrSdTime());
            //解析
            retCityDTO = JSONObject.parseObject(retJsonStr, BJCityFrontDTO.class);
            if (!ResponseCode.SUCCESS.equals(retCityDTO.getRespcode())) {
                logRespcode = retCityDTO.getRespcode();
                respexplain.append("[与城市前置交互成功,返回交易应答码异常],");
            }
            //把产品库传输的对象数据回填到城市前置返回的对象中
            retDTO = backfillData(retCityDTO, crdDTO);
            respexplain.append("[socket交互时长:" + (socketEnd - socketStart) + "ms]");
        }
        catch (Exception e) {
            //调用城市前置失败
            logRespcode = ResponseCode.CARD_SOCKET_CONNECT_ERROR;
            BeanUtils.copyProperties(crdDTO, retDTO);
            retDTO.setRespcode(ResponseCode.CARD_SOCKET_CONNECT_ERROR);
            respexplain.append(e.getMessage());
        }
        finally {
            //记录日志
            logHelper.recordLogFun(jsonStr, tradestart, logRespcode, respexplain.toString(), "[" + crdDTO.getMessagetype() + "," + crdDTO.getVer() + "]向城市前置发送socket(后)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), retJsonStr, txnType);
        }
        return retDTO;
    }

    private BJCrdSysOrderDTO backfillData(BJCityFrontDTO retCityDTO, BJCrdSysOrderDTO crdDTO) {
        //消息类型
        if (StringUtils.isNotBlank(retCityDTO.getMessagetype())) {
            crdDTO.setMessagetype(retCityDTO.getMessagetype());
        }
        //版本号
        if (StringUtils.isNotBlank(retCityDTO.getVer())) {
            crdDTO.setVer(retCityDTO.getVer());
        }

        //前端发送时间格式如下 YYYYMMDDhhmmss
        if (StringUtils.isNotBlank(retCityDTO.getSysdatetime())) {
            crdDTO.setSysdatetime(retCityDTO.getSysdatetime());
        }

        //交易应答码
        if (StringUtils.isNotBlank(retCityDTO.getRespcode())) {
            crdDTO.setRespcode(retCityDTO.getRespcode());
        }

        //一卡通代码
        if (StringUtils.isNotBlank(retCityDTO.getYktcode())) {
            crdDTO.setYktcode(retCityDTO.getYktcode());
        }

        //城市代码
        if (StringUtils.isNotBlank(retCityDTO.getCitycode())) {
            crdDTO.setCitycode(retCityDTO.getCitycode());
        }

        //卡物理类型
        if (StringUtils.isNotBlank(retCityDTO.getCardtype())) {
            crdDTO.setCardtype(retCityDTO.getCardtype());
        }

        //卡号(验卡返回 充值和结果回传)
        if (StringUtils.isNotBlank(retCityDTO.getCardinnerno())) {
            crdDTO.setCardinnerno(retCityDTO.getCardinnerno());
        }

        //卡面号(验卡返回 充值和结果回传)
        if (StringUtils.isNotBlank(retCityDTO.getCardfaceno())) {
            crdDTO.setCardfaceno(retCityDTO.getCardfaceno());
        }

        //0-查询
        if (StringUtils.isNotBlank(retCityDTO.getTxntype())) {
            crdDTO.setTxntype(retCityDTO.getTxntype());
        }

        //0-交易透传 1-交易结束
        if (StringUtils.isNotBlank(retCityDTO.getTradeendflag())) {
            crdDTO.setTradeendflag(retCityDTO.getTradeendflag());
        }

        //0-交易透传 1-交易开始
        if (StringUtils.isNotBlank(retCityDTO.getTradestartflag())) {
            crdDTO.setTradestartflag(retCityDTO.getTradestartflag());
        }

        //设备类型 0：NFC手机；1：V61 V60；2：V70
        if (StringUtils.isNotBlank(retCityDTO.getPostype())) {
            crdDTO.setPostype(retCityDTO.getPostype());
        }

        //充值类型 0:钱包;1:月票
        if (StringUtils.isNotBlank(retCityDTO.getChargetype())) {
            crdDTO.setChargetype(retCityDTO.getChargetype());
        }

        //设备编号
        if (StringUtils.isNotBlank(retCityDTO.getPosid())) {
            crdDTO.setPosid(retCityDTO.getPosid());
        }

        //设备流水号
        if (StringUtils.isNotBlank(retCityDTO.getPosseq())) {
            crdDTO.setPosseq(retCityDTO.getPosseq());
        }

        //交易流水号(充值和结果回传上来)
        if (StringUtils.isNotBlank(retCityDTO.getTxnseq())) {
            crdDTO.setTxnseq(retCityDTO.getTxnseq());
        }

        //用户编号
        if (StringUtils.isNotBlank(retCityDTO.getUsercode())) {
            crdDTO.setUsercode(retCityDTO.getUsercode());
        }

        //卡服务订单号
        if (StringUtils.isNotBlank(retCityDTO.getCrdordernum())) {
            crdDTO.setCrdordernum(retCityDTO.getCrdordernum());
        }

        //交易日期(后台返回一笔交易此字段保持一致)
        if (StringUtils.isNotBlank(retCityDTO.getTxndate())) {
            crdDTO.setTxndate(retCityDTO.getTxndate());
        }

        //交易时间(后台返回一笔交易此字段保持一致)
        if (StringUtils.isNotBlank(retCityDTO.getTxntime())) {
            crdDTO.setTxntime(retCityDTO.getTxntime());
        }

        //交易金额(查询时不需要传)
        if (StringUtils.isNotBlank(retCityDTO.getTxnamt())) {
            crdDTO.setTxnamt(retCityDTO.getTxnamt());
        }

        //交易前卡余额(查询时不需要传)
        if (StringUtils.isNotBlank(retCityDTO.getBefbal())) {
            crdDTO.setBefbal(retCityDTO.getBefbal());
        }

        //交易后卡余额
        if (StringUtils.isNotBlank(retCityDTO.getBlackamt())) {
            crdDTO.setBlackamt(retCityDTO.getBlackamt());
        }

        //卡内允许最大金额
        if (StringUtils.isNotBlank(retCityDTO.getCardlimit())) {
            crdDTO.setCardlimit(retCityDTO.getCardlimit());
        }

        //安全认证码
        if (StringUtils.isNotBlank(retCityDTO.getSecmac())) {
            crdDTO.setSecmac(retCityDTO.getSecmac());
        }

        //卡计数器由前置圈存指令请求返回
        if (StringUtils.isNotBlank(retCityDTO.getCardcounter())) {
            crdDTO.setCardcounter(retCityDTO.getCardcounter());
        }

        //结果上传交易状态0：成功 1：失败 2：未知
        if (StringUtils.isNotBlank(retCityDTO.getTxnstat())) {
            crdDTO.setTxnstat(retCityDTO.getTxnstat());
        }

        //特殊域
        if (retCityDTO.getSpecdata() != null) {
            crdDTO.setSpecdata(retCityDTO.getSpecdata());
        }

        //交易记录域JSON数组
        if (retCityDTO.getTxnrecode() != null) {
            crdDTO.setTxnrecode(retCityDTO.getTxnrecode());
        }

        //金融IC卡文件数据JSON数组
        if (retCityDTO.getBankfile() != null) {
            crdDTO.setBankfile(retCityDTO.getBankfile());
        }

        //APDU指令域JSON数组
        if (retCityDTO.getBankfile() != null) {
            crdDTO.setApdu(retCityDTO.getBankfile());
        }

        //上传后台原路返回(返存前端)
        if (StringUtils.isNotBlank(retCityDTO.getRetdata())) {
            crdDTO.setRetdata(retCityDTO.getRetdata());
        }

        //保留字段
        if (StringUtils.isNotBlank(retCityDTO.getReserved())) {
            crdDTO.setReserved(retCityDTO.getReserved());
        }

        if (StringUtils.isNotBlank(retCityDTO.getMaccounter())) {
            crdDTO.setMaccounter(retCityDTO.getMaccounter());
        }

        if (StringUtils.isNotBlank(retCityDTO.getCardlogictype())) {
            crdDTO.setCardlogictype(retCityDTO.getCardlogictype());
        }
        if (StringUtils.isNotBlank(retCityDTO.getPostransseq())) {
            crdDTO.setPostransseq(retCityDTO.getPostransseq());
        }
        if (StringUtils.isNotBlank(retCityDTO.getKeyindex())) {
            crdDTO.setKeyindex(retCityDTO.getKeyindex());
        }
        if (StringUtils.isNotBlank(retCityDTO.getAppid())) {
            crdDTO.setAppid(retCityDTO.getAppid());
        }

        if (StringUtils.isNotBlank(retCityDTO.getSamno())) {
            crdDTO.setSamno(retCityDTO.getSamno());
        }

        return crdDTO;
    }

    /**
     * 发送签到、签退报文
     */
    @Override
    public SignParameter sendSignInJsonSocket(String ip, int port, SignParameter signParameter, int txnType) {
        //日志方法开始时间
        Long tradestart = Long.valueOf(DateUtil.getCurrSdTime());
        //日志响应码描述
        StringBuffer respexplain = new StringBuffer();
        //日志响应码
        String logRespcode = ResponseCode.SUCCESS;

        //发送到城市前置json
        String jsonStr = "";
        //城市前置返回json
        String retJsonStr = "";
        SignParameter retsSignParameter = new SignParameter();
        try {
            //封装
            jsonStr = JSONObject.toJSONString(signParameter);
            //发送socket
            Long socketStart = Long.valueOf(DateUtil.getCurrSdTime());
            //防止超时无返回的情况，在发送socke之前记录一条日志
            logHelper.recordLogFun(jsonStr, tradestart, logRespcode, respexplain.toString(), "[" + signParameter.getMessagetype() + signParameter.getVer() + "]向城市前置发送socket(前)", "", "", this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), retJsonStr, txnType);
            retJsonStr = new SocketOperator().AsyncClient(jsonStr, ip, port);
            Long socketEnd = Long.valueOf(DateUtil.getCurrSdTime());
            //解析
            retsSignParameter = JSONObject.parseObject(retJsonStr, SignParameter.class);
            if (!ResponseCode.SUCCESS.equals(retsSignParameter.getRespcode())) {
                logRespcode = retsSignParameter.getRespcode();
                respexplain.append("[与城市前置交互成功,返回交易应答码异常],");
            }
            //回填数据
            backfillSignData(signParameter, retsSignParameter);
            respexplain.append("[socket交互时长:" + (socketEnd - socketStart) + "ms]");
        }
        catch (Exception e) {
            logRespcode = ResponseCode.CARD_SOCKET_CONNECT_ERROR;
            retsSignParameter.setRespcode(logRespcode);
            respexplain.append(e.getMessage());
        }
        finally {
            //记录日志
            logHelper.recordLogFun(jsonStr, tradestart, logRespcode, respexplain.toString(), "[" + retsSignParameter.getMessagetype() + retsSignParameter.getVer() + "]向城市前置发送socket(后)", "", "", this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), retJsonStr, txnType);
        }
        return retsSignParameter;
    }

    //回填数据
    private void backfillSignData(SignParameter signParameter, SignParameter retSignParameter) {
        retSignParameter.setMertype(signParameter.getMertype());
        retSignParameter.setOperid(signParameter.getOperid());
    }

    /*
     * 发送优惠信息报文
     */
    @Override
    public BJDiscountDTO sendDiscountSocket(String ip, int port, BJDiscountDTO discountDTO, int txnType) {
        Long tradestart = Long.valueOf(DateUtil.getCurrSdTime());//日志方法开始时间
        StringBuffer respexplain = new StringBuffer();//日志响应码描述
        String logRespcode = ResponseCode.SUCCESS;//日志响应码

        String jsonStr = "";//发送到城市前置json
        String retJsonStr = "";//城市前置返回json

        BJDiscountDTO retDiscountDTO = new BJDiscountDTO();
        try {
            //封装
            jsonStr = JSONObject.toJSONString(discountDTO);
            //发送socket
            Long socketStart = Long.valueOf(DateUtil.getCurrSdTime());
            //防止超时无返回的情况，在发送socke之前记录一条日志
            logHelper.recordLogFun(jsonStr, tradestart, logRespcode, respexplain.toString(), "[" + discountDTO.getMessagetype() + "," + discountDTO.getVer() + "]向城市前置发送socket(前)", "", "", this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), retJsonStr, txnType);
            retJsonStr = new SocketOperator().AsyncClient(jsonStr, ip, port);
            Long socketEnd = Long.valueOf(DateUtil.getCurrSdTime());
            //解析
            retDiscountDTO = JSONObject.parseObject(retJsonStr, BJDiscountDTO.class);
            //空值回填
            retDiscountDTO = fillBack(discountDTO, retDiscountDTO);
            if (!ResponseCode.SUCCESS.equals(retDiscountDTO.getRespcode())) {
                logRespcode = retDiscountDTO.getRespcode();
                respexplain.append("[与城市前置交互成功,返回交易应答码异常],");
            }
            respexplain.append("[socket交互时长:" + (socketEnd - socketStart) + "ms]");
        }
        catch (Exception e) {
            logRespcode = ResponseCode.CARD_SOCKET_CONNECT_ERROR;
            retDiscountDTO.setRespcode(logRespcode);
            respexplain.append(e.getMessage());
        }
        finally {
            //记录日志
            logHelper.recordLogFun(jsonStr, tradestart, logRespcode, respexplain.toString(), "[" + retDiscountDTO.getMessagetype() + "," + retDiscountDTO.getVer() + "]向城市前置发送socket(后)", "", "", this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), retJsonStr, txnType);
        }
        return retDiscountDTO;
    }

    /*
     * 向北京前置发送报文
     */
    @Override
    public BJAccountConsumeDTO sendBJCityFrontSocketForAccCons(String ip, int port, BJAccountConsumeDTO bjAccountDTO, int txnType) {
        //日志方法开始时间
        Long tradestart = Long.valueOf(DateUtil.getCurrSdTime());
        //日志响应码描述
        StringBuffer respexplain = new StringBuffer();
        //日志响应码
        String logRespcode = ResponseCode.SUCCESS;

        //发送到城市前置json
        String jsonStr = "";
        //城市前置返回json
        String retJsonStr = "";
        try {
            //封装
            jsonStr = JSONObject.toJSONString(bjAccountDTO);
            
            //发送socket
            Long socketStart = Long.valueOf(DateUtil.getCurrSdTime());
            //防止超时无返回的情况，在发送socke之前记录一条日志
            logHelper.recordLogFun(jsonStr, tradestart, logRespcode, respexplain.toString(), "[" + bjAccountDTO.getMessagetype() + "," + bjAccountDTO.getVer() + "]向城市前置发送socket(前)", bjAccountDTO.getCrdordernum(), bjAccountDTO.getProordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), retJsonStr, txnType);
            retJsonStr = new SocketOperator().AsyncClient(jsonStr, ip, port);
            Long socketEnd = Long.valueOf(DateUtil.getCurrSdTime());
            log.info("城市前置返回的json:"+retJsonStr);
            try{
                Map<String,String> jsonMap  = (Map<String,String>)JSONObject.parse(retJsonStr);
                String crdm = jsonMap.get("crdm");
                jsonMap.remove("crdm");
                String cardGetJson =JSONObject.toJSONString(jsonMap);
                //解析
                bjAccountDTO = JSONObject.parseObject(cardGetJson, BJAccountConsumeDTO.class);
                BJAccountConsumeSpecialDTO crdmDto = new BJAccountConsumeSpecialDTO();
                crdmDto.setEncryptinfo(crdm);
                bjAccountDTO.setCrdm(crdmDto);
            }catch(java.lang.ClassCastException caste){
                caste.printStackTrace();
                log.info("前端返回的特殊域是OBJECT");
                //解析
                bjAccountDTO = JSONObject.parseObject(retJsonStr, BJAccountConsumeDTO.class);
            }
           
            if (!ResponseCode.SUCCESS.equals(bjAccountDTO.getRespcode())) {
                logRespcode = bjAccountDTO.getRespcode();
                respexplain.append("[与城市前置交互成功,返回交易应答码异常],");
            }
            //把产品库传输的对象数据回填到城市前置返回的对象中
            //            retDTO = backfillData(retCityDTO, crdDTO);
            respexplain.append("[socket交互时长:" + (socketEnd - socketStart) + "ms]");
        }
        catch (Exception e) {
            //调用城市前置失败
            logRespcode = ResponseCode.CARD_SOCKET_CONNECT_ERROR;
            //            BeanUtils.copyProperties(crdDTO, retDTO);
            e.printStackTrace();
            bjAccountDTO.setRespcode(ResponseCode.CARD_SOCKET_CONNECT_ERROR);
            bjAccountDTO.setTradeendflag(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode());
            respexplain.append(e.getMessage());
        }
        finally {
            //记录日志
            logHelper.recordLogFun(jsonStr, tradestart, logRespcode, respexplain.toString(), "[" + bjAccountDTO.getMessagetype() + "," + bjAccountDTO.getVer() + "]向城市前置发送socket(后)", bjAccountDTO.getCrdordernum(), bjAccountDTO.getProordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), retJsonStr, txnType);
        }
        if(StringUtils.isBlank(bjAccountDTO.getTradeendflag())){
            bjAccountDTO.setTradeendflag(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode());
        }
        return bjAccountDTO;
    }
     
    
    @Override
    public BJIntegralConsumeDTO sendBJCityFrontSocketForIntegral(String ip, int port, BJIntegralConsumeDTO dto, int txnType) {
        //日志方法开始时间
        Long tradestart = Long.valueOf(DateUtil.getCurrSdTime());
        //日志响应码描述
        StringBuffer respexplain = new StringBuffer();
        //日志响应码
        String logRespcode = ResponseCode.SUCCESS;

        //发送到城市前置json
        String jsonStr = "";
        //城市前置返回json
        String retJsonStr = "";
        try {
            //封装
            jsonStr = JSONObject.toJSONString(dto);
            //发送socket
            Long socketStart = Long.valueOf(DateUtil.getCurrSdTime());
            //防止超时无返回的情况，在发送socke之前记录一条日志
            logHelper.recordLogFun(jsonStr, tradestart, logRespcode, respexplain.toString(), "[" + dto.getMessagetype() + "," + dto.getVer() + "]向城市前置发送socket(前)", dto.getCrdordernum(), dto.getProordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), retJsonStr, txnType);
            retJsonStr = new SocketOperator().AsyncClient(jsonStr, ip, port);
            log.info("城市前置返回的json" + retJsonStr);
            Long socketEnd = Long.valueOf(DateUtil.getCurrSdTime());
            try{
                Map<String,String> jsonMap  = (Map<String,String>)JSONObject.parse(retJsonStr);
                String crdm = jsonMap.get("crdm");
                jsonMap.remove("crdm");
                String cardGetJson =JSONObject.toJSONString(jsonMap);
                //解析
                dto = JSONObject.parseObject(cardGetJson, BJIntegralConsumeDTO.class);
                BJIntegralConsumeSpecialDTO crdmDto = new BJIntegralConsumeSpecialDTO();
                crdmDto.setEncryptinfo(crdm);
                dto.setCrdm(crdmDto);
            }catch(java.lang.ClassCastException caste){
                log.info("前端返回的特殊域是OBJECT");
                caste.printStackTrace();
                //解析
                dto = JSONObject.parseObject(retJsonStr, BJIntegralConsumeDTO.class);
            }
            if (!ResponseCode.SUCCESS.equals(dto.getRespcode())) {
                logRespcode = dto.getRespcode();
                respexplain.append("[与城市前置交互成功,返回交易应答码异常],");
            }
            //把产品库传输的对象数据回填到城市前置返回的对象中
            //            retDTO = backfillData(retCityDTO, crdDTO);
            respexplain.append("[socket交互时长:" + (socketEnd - socketStart) + "ms]");
        }
        catch (Exception e) {
            //调用城市前置失败
            logRespcode = ResponseCode.CARD_SOCKET_CONNECT_ERROR;
            e.printStackTrace();
            dto.setTradeendflag(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode());
            dto.setRespcode(ResponseCode.CARD_SOCKET_CONNECT_ERROR);
            respexplain.append(e.getMessage());
        }
        finally {
            //记录日志
            logHelper.recordLogFun(jsonStr, tradestart, logRespcode, respexplain.toString(), "[" + dto.getMessagetype() + "," + dto.getVer() + "]向城市前置发送socket(后)", dto.getCrdordernum(), dto.getProordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), retJsonStr, txnType);
        }
        if(StringUtils.isBlank(dto.getTradeendflag())){
            dto.setTradeendflag(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode());

        }
        return dto;
    }

    private BJDiscountDTO fillBack(BJDiscountDTO discountDTO, BJDiscountDTO retDiscountDTO) {
        if (StringUtils.isBlank(retDiscountDTO.getMercode())) {
            retDiscountDTO.setMercode(discountDTO.getMercode());
        }
        if (StringUtils.isBlank(retDiscountDTO.getCardno())) {
            retDiscountDTO.setCardno(discountDTO.getCardno());
        }
        if (StringUtils.isBlank(retDiscountDTO.getBefbal())) {
            retDiscountDTO.setBefbal(discountDTO.getBefbal());
        }
        if (StringUtils.isBlank(retDiscountDTO.getAmout())) {
            retDiscountDTO.setAmout(discountDTO.getAmout());
        }
        if (StringUtils.isBlank(retDiscountDTO.getAmout())) {
            retDiscountDTO.setAmout(discountDTO.getAmout());
        }
        return retDiscountDTO;
    }

    /*
     * nfc向城市前置发送报文
     */
    @Override
    public BJCrdSysOrderDTO sendBJCityFrontHttp(String url, BJCrdSysOrderDTO crdDTO, int txnType) {
        Long tradestart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer respexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;

        //映射城市前置对象
        BJNfcCityFrontDTO cityFrontOrderDTO = mappingNfcCity(crdDTO);

        String msg = "";//发送到城市前置msg
        String retmsg = "";//城市前置返回msg
        BJNfcCityFrontDTO retCityDTO = new BJNfcCityFrontDTO();
        BJCrdSysOrderDTO retDTO = new BJCrdSysOrderDTO();
        try {
            //拼装数据
            msg = BJStringUtil.pinEntity(cityFrontOrderDTO, BJNfcCityFrontDTO.class);
            respexplain.append("[url=" + url + "]");
            Long startTime = Long.valueOf(DateUtil.getCurrSdTime());
            //防止超时无返回的情况，在发送socke之前记录一条日志
            logHelper.recordLogFun(msg, tradestart, logRespcode, respexplain.toString(), "[" + crdDTO.getVer() + "]向城市前置发送socket(前)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), retmsg, txnType);
            retmsg = HttpUtil.callHttp(url, msg);
            Long endTime = Long.valueOf(DateUtil.getCurrSdTime());
            //解析
            retCityDTO = JSONObject.parseObject(retmsg, BJNfcCityFrontDTO.class);
            if (!ResponseCode.SUCCESS.equals(retCityDTO.getStatus())) {
                logRespcode = retCityDTO.getStatus();
                respexplain.append(",[与城市前置交互成功,返回交易应答码异常:" + retCityDTO.getErrmsg() + "]");
            }
            //把产品库传输的对象数据回填到城市前置返回的对象中
            retDTO = backfillData(retCityDTO, crdDTO);
            respexplain.append(",[与前置交互时长:" + (endTime - startTime) + "ms]");
        }
        catch (Exception e) {
            //调用城市前置失败
            logRespcode = ResponseCode.CARD_SOCKET_CONNECT_ERROR;
            BeanUtils.copyProperties(crdDTO, retDTO);
            retDTO.setRespcode(ResponseCode.CARD_SOCKET_CONNECT_ERROR);
            respexplain.append(e.getMessage());
        }
        finally {
            //记录日志
            logHelper.recordLogFun(msg, tradestart, logRespcode, respexplain.toString(), "[" + crdDTO.getVer() + "]向城市前置发送socket(后)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), retmsg, txnType);
        }
        return retDTO;
    }

    private BJNfcCityFrontDTO mappingNfcCity(BJCrdSysOrderDTO crdDTO) {
        BJNfcCityFrontDTO cityDTO = new BJNfcCityFrontDTO();
        //请求时间
        if (StringUtils.isNotBlank(crdDTO.getSysdatetime())) {
            cityDTO.setRequesttime(crdDTO.getSysdatetime());
        }

        //系统订单号
        if (StringUtils.isNotBlank(crdDTO.getCrdordernum())) {
            cityDTO.setOrderid(crdDTO.getCrdordernum());
        }
        //交易日期
        if (StringUtils.isNotBlank(crdDTO.getTxndate())) {
            cityDTO.setTradedate(crdDTO.getTxndate());
        }
        //交易类型
        if (StringUtils.isNotBlank(crdDTO.getTxntype())) {
            cityDTO.setTradetype(crdDTO.getTxntype());
        }
        //用户id
        if (StringUtils.isNotBlank(crdDTO.getUserid())) {
            cityDTO.setUserid(crdDTO.getUserid());
        }
        //卡内号
        if (StringUtils.isNotBlank(crdDTO.getCardinnerno())) {
            cityDTO.setCardinnerno(crdDTO.getCardinnerno());
        }
        //pos号
        if (StringUtils.isNotBlank(crdDTO.getPosid())) {
            cityDTO.setPosid(crdDTO.getPosid());
        }
        //交易金额
        if (StringUtils.isNotBlank(crdDTO.getTxnamt())) {
            cityDTO.setTxnamt(crdDTO.getTxnamt());
        }
        //卡面号
        if (StringUtils.isNotBlank(crdDTO.getCardfaceno())) {
            cityDTO.setCardno(crdDTO.getCardfaceno());
        }
        //卡片类型
        if (StringUtils.isNotBlank(crdDTO.getCardtype())) {
            cityDTO.setCardtype(crdDTO.getCardtype());
        }
        //卡片余额
        if (StringUtils.isNotBlank(crdDTO.getBefbal())) {
            cityDTO.setCardbalance(crdDTO.getBefbal());
        }
        //黑名单标志
        if (StringUtils.isNotBlank(crdDTO.getBlackflag())) {
            cityDTO.setCardblackflag(crdDTO.getBlackflag());
        }
        if (StringUtils.isNotBlank(crdDTO.getYktcode())) {
            cityDTO.setYktid(crdDTO.getYktcode());
        }
        if (StringUtils.isNotBlank(crdDTO.getCitycode())) {
            cityDTO.setCityid(crdDTO.getCitycode());
        }
        //请求序号
        if (StringUtils.isNotBlank(crdDTO.getRequestno())) {
            cityDTO.setRequestno(crdDTO.getRequestno());
        }
        //手机号
        if (StringUtils.isNotBlank(crdDTO.getMobileno())) {
            cityDTO.setMobileno(crdDTO.getMobileno());
        }
        //手机类型
        if (StringUtils.isNotBlank(crdDTO.getMobiletype())) {
            cityDTO.setMobiletype(crdDTO.getMobiletype());
        }
        //手机IMEI号
        if (StringUtils.isNotBlank(crdDTO.getMobileimei())) {
            cityDTO.setMobileimei(crdDTO.getMobileimei());
        }
        //手机系统版本
        if (StringUtils.isNotBlank(crdDTO.getMobilesysver())) {
            cityDTO.setMobilesysver(crdDTO.getMobilesysver());
        }

        //卡片透支金额
        if (StringUtils.isNotBlank(crdDTO.getCardoverdraft())) {
            cityDTO.setCardoverdraft(crdDTO.getCardoverdraft());
        }
        //卡片启用日期
        if (StringUtils.isNotBlank(crdDTO.getCardstartdate())) {
            cityDTO.setCardstartdate(crdDTO.getCardstartdate());
        }
        //卡片失效日期
        if (StringUtils.isNotBlank(crdDTO.getCardenddate())) {
            cityDTO.setCardenddate(crdDTO.getCardenddate());
        }
        //卡启用标志
        if (StringUtils.isNotBlank(crdDTO.getCardstartflag())) {
            cityDTO.setCardstartflag(crdDTO.getCardstartflag());
        }
        if (StringUtils.isNotBlank(crdDTO.getImsi())) {
            cityDTO.setImsi(crdDTO.getImsi());
        }
        //卡片处理方式
        if (StringUtils.isNotBlank(crdDTO.getDealtype())) {
            cityDTO.setDealtype(crdDTO.getDealtype());
        }
        //资金来源
        if (StringUtils.isNotBlank(crdDTO.getPaysource())) {
            cityDTO.setPaytype(crdDTO.getPaysource());
        }
        //产品库订单号
        if (StringUtils.isNotBlank(crdDTO.getPrdordernum())) {
            cityDTO.setPrdordernum(crdDTO.getPrdordernum());
        }
        BJNfcSpecdata nfcSpecdata = crdDTO.getNfcspecdata();
        if (nfcSpecdata != null) {
            //卡片CSN
            if (StringUtils.isNotBlank(nfcSpecdata.getCardcsn())) {
                cityDTO.setCardcsn(nfcSpecdata.getCardcsn());
            }
            //APDU 指令包序号
            if (StringUtils.isNotBlank(nfcSpecdata.getApdupacno())) {
                cityDTO.setApdupacno(nfcSpecdata.getApdupacno());
            }
            //APDU 指令/应答个数
            if (StringUtils.isNotBlank(nfcSpecdata.getApduordernum())) {
                cityDTO.setApduordernum(nfcSpecdata.getApduordernum());
            }
            //APDU 指令/应答包长度
            if (StringUtils.isNotBlank(nfcSpecdata.getApdupaclen())) {
                cityDTO.setApdupaclen(nfcSpecdata.getApdupaclen());
            }
            //APDU 指令/应答序列
            if (StringUtils.isNotBlank(nfcSpecdata.getApduseq())) {
                cityDTO.setApduseq(nfcSpecdata.getApduseq());
            }
        }

        return cityDTO;
    }

    private BJCrdSysOrderDTO backfillData(BJNfcCityFrontDTO retCityDTO, BJCrdSysOrderDTO crdDTO) {
        BJNfcSpecdata nfcSpecdata = crdDTO.getNfcspecdata();
        if (nfcSpecdata == null) {
            nfcSpecdata = new BJNfcSpecdata();
        }
        if (StringUtils.isNotBlank(retCityDTO.getTotalpacnum())) {
            nfcSpecdata.setTotalpacnum(retCityDTO.getTotalpacnum());
        }
        if (StringUtils.isNotBlank(retCityDTO.getApduordernum())) {
            nfcSpecdata.setApduordernum(retCityDTO.getApduordernum());
        }
        if (StringUtils.isNotBlank(retCityDTO.getApdupacno())) {
            nfcSpecdata.setApdupacno(retCityDTO.getApdupacno());
        }
        if (StringUtils.isNotBlank(retCityDTO.getApdupaclen())) {
            nfcSpecdata.setApdupaclen(retCityDTO.getApdupaclen());
        }
        if (StringUtils.isNotBlank(retCityDTO.getApduseq())) {
            nfcSpecdata.setApduseq(retCityDTO.getApduseq());
        }
        crdDTO.setNfcspecdata(nfcSpecdata);
        //应答序号
        if (StringUtils.isNotBlank(retCityDTO.getResponseno())) {
            crdDTO.setResponseno(retCityDTO.getResponseno());
        }
        if (StringUtils.isNotBlank(retCityDTO.getOrderid())) {
            crdDTO.setCrdordernum(retCityDTO.getOrderid());
        }
        //接口版本
        if (StringUtils.isNotBlank(retCityDTO.getServiceversion())) {
            crdDTO.setVer(retCityDTO.getServiceversion());
        }
        //应答码
        if (StringUtils.isNotBlank(retCityDTO.getStatus())) {
            crdDTO.setRespcode(retCityDTO.getStatus());
        }
        if (StringUtils.isNotBlank(retCityDTO.getTradedate())) {
            crdDTO.setTxndate(retCityDTO.getTradedate());
        }
        if (StringUtils.isNotBlank(retCityDTO.getTradetime())) {
            crdDTO.setTxntime(retCityDTO.getTradetime());
        }
        if (StringUtils.isNotBlank(retCityDTO.getTradeendflag())) {
            crdDTO.setTradeendflag(retCityDTO.getTradeendflag());
        }
        return crdDTO;
    }
}
