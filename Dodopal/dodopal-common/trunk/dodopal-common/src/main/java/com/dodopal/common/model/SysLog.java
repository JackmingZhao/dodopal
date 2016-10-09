package com.dodopal.common.model;

import java.util.Date;

public class SysLog extends BaseEntity {

    private static final long serialVersionUID = -1036053125981731420L;

    //自增主键
    private String id;
    //服务名称
    private String serverName;
    //订单号
    private String orderNum;
    //交易流水号
    private String tranNum;
    //交易状态(0:成功;1:失败)
    private long tradeType;
    //类名称
    private String className;
    //方法名称
    private String methodName;
    //开始时间:格式(YYYYMMDDhhmmss)
    private long tradeStart;
    //结束时间:格式(YYYYMMDDhhmmss)
    private long tradeEnd;
    //操作时长(毫秒)
    private long tradeRrack;
    //入参
    private String inParas;
    //出参
    private String outParas;
    //响应码
    private String respCode;
    //响应码描述
    private String respExplain;
    //方法描述
    private String description;
    //来源
    private String source;

    //堆栈信息
    private String statckTrace;

    //IP地址
    private String ipAddress;

    //异常信息
    private String message;

    //日志入队列时间
    private Date logDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getTranNum() {
        return tranNum;
    }

    public void setTranNum(String tranNum) {
        this.tranNum = tranNum;
    }

    public long getTradeType() {
        return tradeType;
    }

    public void setTradeType(long tradeType) {
        this.tradeType = tradeType;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public long getTradeStart() {
        return tradeStart;
    }

    public void setTradeStart(long tradeStart) {
        this.tradeStart = tradeStart;
    }

    public long getTradeEnd() {
        return tradeEnd;
    }

    public void setTradeEnd(long tradeEnd) {
        this.tradeEnd = tradeEnd;
    }

    public long getTradeRrack() {
        return tradeRrack;
    }

    public void setTradeRrack(long tradeRrack) {
        this.tradeRrack = tradeRrack;
    }

    public String getInParas() {
        return inParas;
    }

    public void setInParas(String inParas) {
        this.inParas = inParas;
    }

    public String getOutParas() {
        return outParas;
    }

    public void setOutParas(String outParas) {
        this.outParas = outParas;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespExplain() {
        return respExplain;
    }

    public void setRespExplain(String respExplain) {
        this.respExplain = respExplain;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStatckTrace() {
        return statckTrace;
    }

    public void setStatckTrace(String statckTrace) {
        this.statckTrace = statckTrace;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

}
