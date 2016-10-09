package com.dodopal.card.business.log;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.card.business.dao.LogMapper;
import com.dodopal.card.business.model.CrdSysLog;
import com.dodopal.card.business.util.DateUtil;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.util.DodopalAppVarPropsUtil;

@Service
public class LogHelper {
    @Autowired
    private LogMapper mapper;

    private static Logger logger = LoggerFactory.getLogger(LogHelper.class);

    /**
     * 写日志方法
     * @param inparas 入参
     * @param tradestart 开始时间
     * @param respcode 交易代码
     * @param respexplain 响应码
     * @param description 响应码描述
     * @param crdordernum 卡服务订单号
     * @param prdordernum 产品库订单号
     * @param classname 类名称
     * @param methodname 方法名称
     * @param outparas 出参
     */
    public void recordLogFun(String inparas, Long tradestart, String respcode, String respexplain, String description, String crdordernum, String prdordernum, String classname, String methodname, String outparas,int txnType) {
        CrdSysLog crdSysLog = new CrdSysLog();
        crdSysLog.setTradeStart(tradestart);
        crdSysLog.setRespExplain(respexplain);
        crdSysLog.setInParas(inparas);
        crdSysLog.setRespCode(respcode);
        crdSysLog.setDescription(description);
        crdSysLog.setCrdOrderNum(crdordernum);
        crdSysLog.setProOrderNum(prdordernum);
        crdSysLog.setClassName(classname);
        crdSysLog.setMethodName(methodname);
        crdSysLog.setOutParas(outparas);
        crdSysLog.setTxnType(txnType);
        recordLogFun(crdSysLog);
    }

    private void recordLogFun(CrdSysLog crdSysLog) {

        try {
            crdSysLog.setServerName(DodopalAppVarPropsUtil.getStringProp("card.log.servername"));
            //当前时间
            Date now = new Date();
            String tradeend = DateUtil.dateToStrLongs(now);
            crdSysLog.setTradeEnd(Long.parseLong(tradeend));

            //结束日期
            String endDate = DateUtil.getCurrentDateByFormat("yyyyMMdd");
            crdSysLog.setEndDate(endDate);

            //开始时间
            Long tradestart = crdSysLog.getTradeStart();

            //操作时长
            if (tradestart != null && !"".equals(tradestart) && !"null".equals(tradestart) && tradestart != 0) {
                Date start = DateUtil.strToDateLongs(tradestart);
                long tradetrack = now.getTime() - start.getTime();
                crdSysLog.setTradeTrack(tradetrack);
                crdSysLog.setTradeStart(Long.parseLong(DateUtil.dateToStrLongs(start)));
            }

            //入参长度
            int inparalength = crdSysLog.getInParas().length();
            crdSysLog.setInParaLength(inparalength);

            //交易状态(tradetype): 当respcode为null或空时tradetype=0(成功)，其他tradetype=1(失败);
            String respcode = crdSysLog.getRespCode();
            if (respcode != null && !"".equals(respcode) && !"null".equals(respcode) && !ResponseCode.SUCCESS.equals(respcode)) {
                crdSysLog.setTradeType(1);
            } else {
                //当respcode传入为null或空时respcode=000000 默认值；
                crdSysLog.setRespCode(ResponseCode.SUCCESS);
                crdSysLog.setTradeType(0);
            }

            //当respexplain传入为null或空时 respexplain=success默认值
            if ((crdSysLog.getRespExplain() == null || "".equals(crdSysLog.getRespExplain()) || "null".equals(crdSysLog.getRespExplain())) && ResponseCode.SUCCESS.equals(respcode)) {
                crdSysLog.setRespExplain("success");
            }
            //日志记录方式 0:记录文件 1:记录数据库 2:先写文件，再写数据库
            String recordtype = DodopalAppVarPropsUtil.getStringProp("card.log.recordtype");
            if ("0".equals(recordtype)) {
                writeFile(crdSysLog);
            } else if ("1".equals(recordtype)) {
                writeDB(crdSysLog);
            } else {
                writeFile(crdSysLog);
                writeDB(crdSysLog);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

    private static void writeFile(CrdSysLog crdSysLog) {
        StringBuffer log = new StringBuffer();
        log.append(JSONObject.toJSONString(crdSysLog));
        logger.info(log.toString());
    }

    private void writeDB(CrdSysLog crdSysLog) {
        //当输入参数的长度超出了数据表中定义的最大长度时按数据表的最大长度进行截取
        if (crdSysLog.getInParaLength() > 4000) {
            String inpra = crdSysLog.getInParas().substring(0, 4000);
            crdSysLog.setInParas(inpra);
        }
        if (crdSysLog.getRespExplain().length() > 500) {
            crdSysLog.setRespExplain(crdSysLog.getRespExplain().substring(0, 500));
        }
        mapper.insertLog(crdSysLog);
    }
}
