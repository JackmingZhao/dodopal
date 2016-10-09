package com.dodopal.merdevice.business.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.api.card.dto.Specdata;
import com.dodopal.api.merdevice.crdConstants.CardServiceConstants;


public class CrdSysOrderDTOInfoAdapter {

    
    private Logger log = LoggerFactory.getLogger(CrdSysOrderDTOInfoAdapter.class);
    
    public  CrdSysOrderDTO crdSysOrderDTO;
    
    public CrdSysOrderDTOInfoAdapter(CrdSysOrderDTO crdSysOrderDTO) {
        this.crdSysOrderDTO = crdSysOrderDTO;
    }
    
    private void log(String info) {
        if(log.isDebugEnabled()) {
            log.debug(info);
        }
    }
    //消息类型
    public String getMessagetype(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(crdSysOrderDTO.getMessagetype(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log.info("getMessagetype: [" + crdSysOrderDTO.getMessagetype() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }
    public void setMessagetype(String value) {
        crdSysOrderDTO.setMessagetype(value);
        System.out.println("消息类型[messagetype]: [" + crdSysOrderDTO.getMessagetype() + "]");
        log.info("消息类型[messagetype]: [" + crdSysOrderDTO.getMessagetype() + "]");
    }
    //版本号
    public String getVer(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(crdSysOrderDTO.getVer(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getVer: [" + crdSysOrderDTO.getVer() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }
    
    public void setVer(String value) {
        crdSysOrderDTO.setVer(value);
        System.out.println("版本号[ver]: [" + crdSysOrderDTO.getVer() + "]");
        log.info("版本号[ver]: [" + crdSysOrderDTO.getVer() + "]");
    }
    
    //前端发送时间格式如下 YYYYMMDDhhmmss
    public String getSysdatetime(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(crdSysOrderDTO.getSysdatetime(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getSysdatetime: [" + crdSysOrderDTO.getSysdatetime() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public void setSysdatetime(String sysdatetime) {
        crdSysOrderDTO.setSysdatetime(sysdatetime);
        System.out.println("前端发送时间格式[sysdatetime]: [" + crdSysOrderDTO.getSysdatetime() + "]");
        log.info("前端发送时间格式[sysdatetime]: [" + crdSysOrderDTO.getSysdatetime() + "]");
    }

    //产品编号
    public String getProductcode(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(crdSysOrderDTO.getProductcode(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getProductcode: [" + crdSysOrderDTO.getProductcode() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public void setProductcode(String productcode) {
        crdSysOrderDTO.setProductcode(productcode);
        System.out.println("产品编号[productcode]: [" + crdSysOrderDTO.getProductcode() + "]");
        log.info("产品编号[productcode]: [" + crdSysOrderDTO.getProductcode() + "]");
    }

    //商户编号
    public String getMercode(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(crdSysOrderDTO.getMercode(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMercode: [" + crdSysOrderDTO.getMercode() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public void setMercode(String mercode) {
        crdSysOrderDTO.setMercode(mercode);
        System.out.println("商户编号[mercode]: [" + crdSysOrderDTO.getMercode() + "]");
        log.info("商户编号[mercode]: [" + crdSysOrderDTO.getMercode() + "]");
    }

    //商户订单编号/圈存订单编号
    public String getMerordercode(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(crdSysOrderDTO.getMerordercode(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMerordercode: [" + crdSysOrderDTO.getMerordercode() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public void setMerordercode(String merordercode) {
        crdSysOrderDTO.setMerordercode(merordercode);
        System.out.println("商户订单编号/圈存订单编号[merordercode]: [" + crdSysOrderDTO.getMerordercode() + "]");
        log.info("商户订单编号/圈存订单编号[merordercode]: [" + crdSysOrderDTO.getMerordercode() + "]");
    }

    //交易应答码
    public String getRespcode(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(crdSysOrderDTO.getRespcode(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMerordercode: [" + crdSysOrderDTO.getRespcode() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public void setRespcode(String respcode) {
        crdSysOrderDTO.setRespcode(respcode);
        System.out.println("交易应答码[respcode]: [" + crdSysOrderDTO.getRespcode() + "]");
        log.info("交易应答码[respcode]: [" + crdSysOrderDTO.getRespcode() + "]");
    }

    //一卡通代码
    public String getYktcode(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(crdSysOrderDTO.getYktcode(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMerordercode: [" + crdSysOrderDTO.getYktcode() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public void setYktcode(String yktcode) {
        crdSysOrderDTO.setYktcode(yktcode);
        System.out.println("一卡通代码[yktcode]: [" + crdSysOrderDTO.getYktcode() + "]");
        log.info("一卡通代码[yktcode]: [" + crdSysOrderDTO.getYktcode() + "]");
    }

    //城市代码
    public String getCitycode(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(crdSysOrderDTO.getCitycode(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getCitycode: [" + crdSysOrderDTO.getCitycode() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public void setCitycode(String citycode) {
        crdSysOrderDTO.setCitycode(citycode);
        System.out.println("城市代码[citycode]: [" + crdSysOrderDTO.getCitycode() + "]");
        log.info("城市代码[citycode]: [" + crdSysOrderDTO.getCitycode() + "]");
    }

    //卡物理类型
    public String getCardtype(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(crdSysOrderDTO.getCitycode(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getCardtype: [" + crdSysOrderDTO.getCardtype() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public void setCardtype(String cardtype) {
        crdSysOrderDTO.setCardtype(cardtype);
        System.out.println("卡物理类型[cardtype]: [" + crdSysOrderDTO.getCardtype() + "]");
        log.info("卡物理类型[cardtype]: [" + crdSysOrderDTO.getCardtype() + "]");
    }

    //卡号(前端读出)
    public String getTradecard(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(crdSysOrderDTO.getTradecard(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getTradecard: [" + crdSysOrderDTO.getTradecard() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public void setTradecard(String tradecard) {
        crdSysOrderDTO.setTradecard(tradecard);
        System.out.println("卡号(前端读出)[tradecard]: [" + crdSysOrderDTO.getTradecard() + "]");
        log.info("卡号(前端读出)[tradecard]: [" + crdSysOrderDTO.getTradecard() + "]");
    }

    //卡号(验卡返回 充值和结果回传)
    private String cardinnerno;
    
    public String getCardinnerno(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(crdSysOrderDTO.getTradecard(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getCardinnerno: [" + crdSysOrderDTO.getCardinnerno() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public void setCardinnerno(String cardinnerno) {
        crdSysOrderDTO.setCardinnerno(cardinnerno);
        System.out.println("卡号(验卡返回 充值和结果回传)[cardinnerno]: [" + crdSysOrderDTO.getCardinnerno() + "]");
        log.info("卡号(验卡返回 充值和结果回传)[cardinnerno]: [" + crdSysOrderDTO.getCardinnerno() + "]");
    }

    //卡面号(验卡返回 充值和结果回传)
    public String getCardfaceno(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(crdSysOrderDTO.getCardfaceno(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getCardfaceno: [" + crdSysOrderDTO.getCardfaceno() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public void setCardfaceno(String cardfaceno) {
        crdSysOrderDTO.setCardfaceno(cardfaceno);
        System.out.println("卡面号(验卡返回 充值和结果回传)[cardfaceno]: [" + crdSysOrderDTO.getCardfaceno() + "]");
        log.info("卡面号(验卡返回 充值和结果回传)[cardfaceno]: [" + crdSysOrderDTO.getCardfaceno() + "]");
    }

    //0-查询
    private String txntype;

    public String getTxntype(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(crdSysOrderDTO.getTxntype(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getTxntype: [" + crdSysOrderDTO.getTxntype() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public void setTxntype(String txntype) {
        crdSysOrderDTO.setTxntype(txntype);
        System.out.println("0-查询[txntype]: [" + crdSysOrderDTO.getTxntype() + "]");
        log.info("0-查询[txntype]: [" + crdSysOrderDTO.getTxntype() + "]");
    }

    //0-交易透传 1-交易结束
    public String getTradeendflag(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(crdSysOrderDTO.getTradeendflag(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getTradeendflag: [" + crdSysOrderDTO.getTradeendflag() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public void setTradeendflag(String tradeendflag) {
        crdSysOrderDTO.setTradeendflag(tradeendflag);
        System.out.println("交易结束[tradeendflag]: [" + crdSysOrderDTO.getTradeendflag() + "]");
        log.info("交易结束[tradeendflag]: [" + crdSysOrderDTO.getTradeendflag() + "]");
    }

    //0-交易透传 1-交易开始
    public String getTradestartflag(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(crdSysOrderDTO.getTradestartflag(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getTradestartflag: [" + crdSysOrderDTO.getTradestartflag() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public void setTradestartflag(String tradestartflag) {
        crdSysOrderDTO.setTradestartflag(tradestartflag);
        System.out.println("交易开始[tradestartflag]: [" + crdSysOrderDTO.getTradestartflag() + "]");
        log.info("交易开始[tradestartflag]: [" + crdSysOrderDTO.getTradestartflag() + "]");
    }

    //数据来源
    private String source;

    
    public String getSource(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(crdSysOrderDTO.getSource(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getSource: [" + crdSysOrderDTO.getSource() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public void setSource(String source) {
        crdSysOrderDTO.setSource(source);
        System.out.println("数据来源[tradestartflag]: [" + crdSysOrderDTO.getSource() + "]");
        log.info("数据来源[tradestartflag]: [" + crdSysOrderDTO.getSource() + "]");
    }

    //设备类型 0：NFC手机；1：V61 V60；2：V70
    public String getPostype(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(crdSysOrderDTO.getPostype(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getPostype: [" + crdSysOrderDTO.getPostype() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public void setPostype(String postype) {
        crdSysOrderDTO.setPostype(postype);
        System.out.println("设备类型[postype]: [" + crdSysOrderDTO.getPostype() + "]");
        log.info("设备类型[postype]: [" + crdSysOrderDTO.getPostype() + "]");
    }

    //充值类型 0:钱包;1:月票
    private String chargetype;
    
    public String getChargetype() {
        return chargetype;
    }

    public void setChargetype(String chargetype) {
        this.chargetype = chargetype;
    }

    //设备编号
    private String posid;
    
    public String getPosid() {
        return posid;
    }

    public void setPosid(String posid) {
        this.posid = posid;
    }

    //设备流水号
    private String posseq;
    
    public String getPosseq() {
        return posseq;
    }

    public void setPosseq(String posseq) {
        this.posseq = posseq;
    }

    //交易流水号(充值和结果回传上来)
    private String txnseq;
    
    //用户编号
    private String usercode;

    //产品库主订单号
    private String prdordernum;

    //卡服务订单号
    private String crdordernum;

    //交易日期(后台返回一笔交易此字段保持一致)
    private String txndate;

    //交易时间(后台返回一笔交易此字段保持一致)
    private String txntime;

    //交易金额(查询时不需要传)
    private String txnamt;

    //交易前卡余额(查询时不需要传)
    private String befbal;

    //交易后卡余额
    private String blackamt;

    //卡内允许最大金额
    private String cardlimit;

    //安全认证码
    private String secmac;

    //卡计数器由前置圈存指令请求返回
    private String cardcounter;

    //结果上传交易状态0：成功 1：失败 2：未知
    private String txnstat;

    //特殊域
    private Specdata specdata;

    //交易记录域JSON数组
    private String[] txnrecode;

    //金融IC卡文件数据JSON数组
    private String[] bankfile;

    //APDU指令域JSON数组
    private String[] apdu;

    //上传后台原路返回(返存前端)
    private String retdata;

    //保留字段
    private String reserved;
    
    //支付类型
    private String paytype;
    
    //支付方式
    private String payway;
    
    //圈存订单号
    private String loadordernum;
    
    //用户id
    private String userid;
    
    //商户端签名值(关键字段签名值)
    private String m_sign;
    
    //动态库签名值
    private String d_sign;
    
    //产品库签名值
    private String p_sign;
}
