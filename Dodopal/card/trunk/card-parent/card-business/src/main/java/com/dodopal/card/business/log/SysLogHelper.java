package com.dodopal.card.business.log;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.dodopal.card.business.constant.CardConstants;
import com.dodopal.card.business.util.DateUtil;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.log.ActivemqLogPublisher;
import com.dodopal.common.model.SysLog;
import com.dodopal.common.util.DodopalAppVarPropsUtil;

@Service
public class SysLogHelper {

    public void recordLogFun(String inparas, Long tradestart, String respcode, String respexplain, String description, String tranNum, String orderNum, String classname, String methodname, String outparas, String source, String statckTrace) {
        SysLog sysLog = new SysLog();
        sysLog.setTradeStart(tradestart);
        sysLog.setRespExplain(respexplain);
        sysLog.setInParas(inparas);
        sysLog.setRespCode(respcode);
        sysLog.setDescription(description);
        sysLog.setTranNum(tranNum);
        sysLog.setOrderNum(orderNum);
        sysLog.setClassName(classname);
        sysLog.setMethodName(methodname);
        sysLog.setOutParas(outparas);
        sysLog.setSource(source);
        sysLog.setStatckTrace(statckTrace);
        recordLogFun(sysLog);
    }

    private void recordLogFun(SysLog sysLog) {
        String logServerName = DodopalAppVarPropsUtil.getStringProp("server.log.name");
        String logServerUrl = DodopalAppVarPropsUtil.getStringProp("server.log.url");
        //服务名称
        sysLog.setServerName(CardConstants.SYSTEM_NAME_CARD);
        //当前时间
        Date now = new Date();
        String tradeend = DateUtil.dateToStrLongs(now);
        sysLog.setTradeEnd(Long.parseLong(tradeend));

        //开始时间
        Long tradestart = sysLog.getTradeStart();

        //操作时长
        if (tradestart != null && !"".equals(tradestart) && !"null".equals(tradestart) && tradestart != 0) {
            Date start = DateUtil.strToDateLongs(tradestart);
            long tradetrack = now.getTime() - start.getTime();
            sysLog.setTradeRrack(tradetrack);
            sysLog.setTradeStart(Long.parseLong(DateUtil.dateToStrLongs(start)));
        }

        //交易状态(tradetype): 当respcode为null或空时tradetype=0(成功)，其他tradetype=1(失败);
        String respcode = sysLog.getRespCode();
        if (respcode != null && !"".equals(respcode) && !"null".equals(respcode) && !ResponseCode.SUCCESS.equals(respcode)) {
            sysLog.setTradeType(1);
        } else {
            //当respcode传入为null或空时respcode=000000 默认值；
            sysLog.setRespCode(ResponseCode.SUCCESS);
            sysLog.setTradeType(0);
        }

        //当respexplain传入为null或空时 respexplain=success默认值
        if ((sysLog.getRespExplain() == null || "".equals(sysLog.getRespExplain()) || "null".equals(sysLog.getRespExplain())) && ResponseCode.SUCCESS.equals(respcode)) {
            sysLog.setRespExplain("success");
        }

        ActivemqLogPublisher.publishLog2Queue(sysLog, logServerName, logServerUrl);
    }
}
