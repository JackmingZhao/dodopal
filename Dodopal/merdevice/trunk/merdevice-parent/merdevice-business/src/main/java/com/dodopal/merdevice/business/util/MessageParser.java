package com.dodopal.merdevice.business.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.api.merdevice.crdConstants.CardServiceConstants;
import com.dodopal.api.merdevice.dto.CityFrontOrderDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.merdevice.business.message.MessageCodes;

@Component("messageParser")
public class MessageParser {
    private static Logger log = LoggerFactory.getLogger(MessageParser.class);
    
    //2011报文解析体
    public static CrdSysOrderDTO parsePreProcessChargeResp(CrdSysOrderDTO cfDto, String resp) {
        String msgHeaderAndData = null; 
        String[] msgHeaderAndDataArray = {};
        CrdSysOrderDTOInfoAdapter cityCardInfo = new CrdSysOrderDTOInfoAdapter(cfDto);
        if(StringUtils.isNotEmpty(resp) && resp.length() > CardServiceConstants.MSG_DESC_LEN) {
            msgHeaderAndData = resp.substring(CardServiceConstants.MSG_DESC_LEN);
            //获取报文体模板
            MessageCodes msgCodes = new MessageCodes();
            msgHeaderAndDataArray = MessageStringUtil.StringTostringA(msgHeaderAndData, msgCodes.getPreProcessCharge());
            if(log.isDebugEnabled()) {
                log.debug("Response code from CardSystem: " + msgHeaderAndDataArray[6]);
            }
            if(!CardServiceConstants.VALID_RESPONSE_CODE.equals(msgHeaderAndDataArray[6])) {
               //cityCardInfo.setRespcode(msgHeaderAndDataArray[6]);
            } else {
                msgHeaderAndDataArray = MessageStringUtil.StringTostringA(msgHeaderAndData, msgCodes.getPreProcessCharge());
                //消息类型
                cityCardInfo.setMessagetype(msgHeaderAndDataArray[1]);
                //版本号
                cityCardInfo.setVer(msgHeaderAndDataArray[2]);
//                //前端发送时间格式如下 YYYYMMDDhhmmss
//                cityCardInfo.setSysdatetime(msgHeaderAndDataArray[3]);
                //交易应答码
                cityCardInfo.setRespcode(msgHeaderAndDataArray[6]);
//                //一卡通代码
//                cityCardInfo.setYktcode(null);
//                //城市代码
                cityCardInfo.setCitycode(msgHeaderAndDataArray[7]);
                //卡物理类型
                cityCardInfo.setCardtype(msgHeaderAndDataArray[23]);
                //卡号(验卡返回 充值和结果回传)
                cityCardInfo.setCardinnerno(msgHeaderAndDataArray[24]);
                //卡面号(验卡返回 充值和结果回传)
                cityCardInfo.setCardfaceno(msgHeaderAndDataArray[25]);
//                //0-查询
//                cityCardInfo.setTxntype(null);
//                //0-交易透传 1-交易结束
//                cityCardInfo.setTradeendflag(null);
//                //0-交易透传 1-交易开始
//                cityCardInfo.setTradestartflag(null);
//                //设备类型 0：NFC手机；1：V61 V60；2：V70
//                cityCardInfo.setPostype(msgHeaderAndDataArray[17]);
//                //充值类型 0:钱包;1:月票
//                cityCardInfo.setChargetype(null);
//                //设备编号
//                cityCardInfo.setPosid(msgHeaderAndDataArray[18]);
//                //设备流水号
//                cityCardInfo.setPosseq(msgHeaderAndDataArray[19]);
//                //交易流水号(充值和结果回传上来)
//                cityCardInfo.setTxnseq(null);
//                //用户编号
//                cityCardInfo.setUsercode(msgHeaderAndDataArray[16]);
//                //卡服务订单号
//                cityCardInfo.setCrdordernum(null);
//                //交易日期(后台返回一笔交易此字段保持一致)
//                cityCardInfo.setTxndate(msgHeaderAndDataArray[26]);
//                //交易时间(后台返回一笔交易此字段保持一致)
//                cityCardInfo.setTxntime(msgHeaderAndDataArray[27]);
//                //交易金额(查询时不需要传)
//                cityCardInfo.setTxnamt(msgHeaderAndDataArray[28]);
//                //交易前卡余额(查询时不需要传)
//                cityCardInfo.setBefbal(msgHeaderAndDataArray[29]);
//                //交易后卡余额
//                cityCardInfo.setBlackamt(null);
//                //卡内允许最大金额
//                cityCardInfo.setCardlimit(null);
//                //安全认证码
//                cityCardInfo.setSecmac(null);
//                //卡计数器由前置圈存指令请求返回
//                cityCardInfo.setCardcounter(null);
//                //结果上传交易状态0：成功 1：失败 2：未知
//                cityCardInfo.setTxnstat(null);
//                //上传后台原路返回(返存前端)
//                cityCardInfo.setRetdata(null);
//                //保留字段
//                cityCardInfo.setReserved(msgHeaderAndDataArray[31]);
//                
               
            }
        } else {
            cfDto.setRespcode(ResponseCode.CARD_PARSE_MSG_FAILED);
        }
        
        log.info("Exit MessageParser.parsePreProcessChargeResp");
        String json = JSONObject.toJSONString(cfDto, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
        System.out.println("cfDto"+json);
        log.info("cfDto"+json);
        return cfDto;
    }
}
