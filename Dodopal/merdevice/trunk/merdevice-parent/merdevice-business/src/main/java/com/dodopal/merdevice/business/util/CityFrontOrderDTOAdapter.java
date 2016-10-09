package com.dodopal.merdevice.business.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dodopal.api.merdevice.crdConstants.CardServiceConstants;
import com.dodopal.api.merdevice.dto.CityFrontOrderDTO;


public class CityFrontOrderDTOAdapter {

    
    private Logger log = LoggerFactory.getLogger(CityFrontOrderDTOAdapter.class);
    
    private CityFrontOrderDTO cityFrontOrderDTO;
    
    public CityFrontOrderDTOAdapter(CityFrontOrderDTO cityFrontOrderDTO) {
        this.cityFrontOrderDTO = cityFrontOrderDTO;
    }
    

   

    public String getMessagetype(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getMessagetype(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        System.out.println(cityFrontOrderDTO.getMessagetype());
        log("getMessLen: [" + cityFrontOrderDTO.getMessagetype() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public String getVer(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getVer(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMessLen: [" + cityFrontOrderDTO.getVer() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public String getSysdatetime(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getSysdatetime(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMessLen: [" + cityFrontOrderDTO.getSysdatetime() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        System.out.println(rst);
        return rst;
    }

    public String getRespcode(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getRespcode(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMessLen: [" + cityFrontOrderDTO.getRespcode() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public String getYktcode(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getYktcode(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMessLen: [" + cityFrontOrderDTO.getYktcode() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public String getCitycode(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getCitycode(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMessLen: [" + cityFrontOrderDTO.getCitycode() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public String getCardtype(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getCardtype(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMessLen: [" + cityFrontOrderDTO.getCardtype() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public String getCardinnerno(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getCardinnerno(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMessLen: [" + cityFrontOrderDTO.getCardinnerno() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public String getCardfaceno(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getCardfaceno(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMessLen: [" + cityFrontOrderDTO.getCardfaceno() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public String getTxntype(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getTxntype(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMessLen: [" + cityFrontOrderDTO.getTxntype() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public String getTradeendflag(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getTradeendflag(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMessLen: [" + cityFrontOrderDTO.getTradeendflag() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public String getTradestartflag(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getTradestartflag(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMessLen: [" + cityFrontOrderDTO.getTradestartflag() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public String getPostype(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getPostype(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMessLen: [" + cityFrontOrderDTO.getPostype() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public String getChargetype(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getChargetype(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMessLen: [" + cityFrontOrderDTO.getChargetype() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public String getPosid(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getPosid(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMessLen: [" + cityFrontOrderDTO.getPosid() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public String getPosseq(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getPosseq(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMessLen: [" + cityFrontOrderDTO.getPosseq() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public String getTxnseq(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getTxnseq(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMessLen: [" + cityFrontOrderDTO.getTxnseq() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public String getUsercode(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getUsercode(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMessLen: [" + cityFrontOrderDTO.getUsercode() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public String getCrdordernum(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getCrdordernum(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMessLen: [" + cityFrontOrderDTO.getCrdordernum() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public String getTxndate(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getTxndate(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMessLen: [" + cityFrontOrderDTO.getTxndate() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public String getTxntime(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getTxntime(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMessLen: [" + cityFrontOrderDTO.getTxntime() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public String getTxnamt(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getTxnamt(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMessLen: [" + cityFrontOrderDTO.getTxnamt() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public String getBefbal(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getBefbal(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMessLen: [" + cityFrontOrderDTO.getBefbal() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public String getBlackamt(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getBlackamt(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMessLen: [" + cityFrontOrderDTO.getBlackamt() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public String getCardlimit(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getCardlimit(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMessLen: [" + cityFrontOrderDTO.getCardlimit() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public String getSecmac(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getSecmac(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMessLen: [" + cityFrontOrderDTO.getSecmac() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public String getCardcounter(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getCardcounter(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMessLen: [" + cityFrontOrderDTO.getCardcounter() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public String getTxnstat(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getTxnstat(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMessLen: [" + cityFrontOrderDTO.getTxnstat() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

//    public Specdata getSpecdata() {
//        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getSysdatetime(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
//        log("getMessLen: [" + cityFrontOrderDTO.getSysdatetime() + "], default: [" + defaultVal + "], result: [" + rst + "].");
//        return rst;
//    }

//    public String[] getTxnrecode() {
//        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getSysdatetime(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
//        log("getMessLen: [" + cityFrontOrderDTO.getSysdatetime() + "], default: [" + defaultVal + "], result: [" + rst + "].");
//        return rst;
//    }

//    public String[] getBankfile() {
//        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getSysdatetime(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
//        log("getMessLen: [" + cityFrontOrderDTO.getSysdatetime() + "], default: [" + defaultVal + "], result: [" + rst + "].");
//        return rst;
//    }

//    public String[] getApdu() {
//        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getSysdatetime(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
//        log("getMessLen: [" + cityFrontOrderDTO.getSysdatetime() + "], default: [" + defaultVal + "], result: [" + rst + "].");
//        return rst;
//    }

    public String getRetdata(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getRetdata(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMessLen: [" + cityFrontOrderDTO.getRetdata() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }

    public String getReserved(String defaultVal) {
        String rst = MessageStringUtil.getValueOrDefault(cityFrontOrderDTO.getReserved(), defaultVal, CardServiceConstants.LEFT_PAD_ZERO);
        log("getMessLen: [" + cityFrontOrderDTO.getReserved() + "], default: [" + defaultVal + "], result: [" + rst + "].");
        return rst;
    }
    
    

    public void setMessagetype(String value) {
        cityFrontOrderDTO.setMessagetype(value);
        System.out.println("cityFrontOrderDTO.getMessagetype():"+cityFrontOrderDTO.getMessagetype());
        log("setMessagetype: [" + value + "]");
    }

    public void setVer(String value) {
        cityFrontOrderDTO.setMessagetype(value);
        log("setVer: [" + value + "]");
    }

    public void setSysdatetime(String value) {
        cityFrontOrderDTO.setSysdatetime(value);
        log("setSysdatetime: [" + value + "]");
    }

    public void setRespcode(String value) {
        cityFrontOrderDTO.setRespcode(value);
        log("setRespcode: [" + value + "]");
    }

    public void setYktcode(String value) {
        cityFrontOrderDTO.setYktcode(value);
        log("setYktcode: [" + value + "]");
    }

    public void setCitycode(String value) {
        cityFrontOrderDTO.setCitycode(value);
        log("setCitycode: [" + value + "]");
    }

    public void setCardtype(String value) {
        cityFrontOrderDTO.setCardtype(value);
        log("setCardtype: [" + value + "]");
    }

    public void setCardinnerno(String value) {
        cityFrontOrderDTO.setCardinnerno(value);
        log("setCardinnerno: [" + value + "]");
    }

    public void setCardfaceno(String value) {
        cityFrontOrderDTO.setCardfaceno(value);
        log("setCardfaceno: [" + value + "]");
    }

    public void setTxntype(String value) {
        cityFrontOrderDTO.setTxntype(value);
        log("setTxntype: [" + value + "]");
    }

    public void setTradeendflag(String value) {
        cityFrontOrderDTO.setTradeendflag(value);
        log("setTradeendflag: [" + value + "]");
    }

    public void setTradestartflag(String value) {
        cityFrontOrderDTO.setTradestartflag(value);
        log("setTradestartflag: [" + value + "]");
    }

    public void setPostype(String value) {
        cityFrontOrderDTO.setPostype(value);
        log("setPostype: [" + value + "]");
    }

    public void setChargetype(String value) {
        cityFrontOrderDTO.setChargetype(value);
        log("setChargetype: [" + value + "]");
    }

    public void setPosid(String value) {
        cityFrontOrderDTO.setPosid(value);
        log("setPosid: [" + value + "]");
    }

    public void setPosseq(String value) {
        cityFrontOrderDTO.setPosseq(value);
        log("setPosseq: [" + value + "]");
    }

    public void setTxnseq(String value) {
        cityFrontOrderDTO.setTxnseq(value);
        log("setTxnseq: [" + value + "]");
    }

    public void setUsercode(String value) {
        cityFrontOrderDTO.setUsercode(value);
        log("setMessLen: [" + value + "]");
    }

    public void setCrdordernum(String value) {
        cityFrontOrderDTO.setCrdordernum(value);
        log("setCrdordernum: [" + value + "]");
    }

    public void setTxndate(String value) {
        cityFrontOrderDTO.setTxndate(value);
        log("setTxndate: [" + value + "]");
    }

    public void setTxntime(String value) {
        cityFrontOrderDTO.setTxntime(value);
        log("setTxntime: [" + value + "]");
    }

    public void setTxnamt(String value) {
        cityFrontOrderDTO.setTxnamt(value);
        log("setTxnamt: [" + value + "]");
    }

    public void setBefbal(String value) {
        cityFrontOrderDTO.setBefbal(value);
        log("setBefbal: [" + value + "]");
    }

    public void setBlackamt(String value) {
        cityFrontOrderDTO.setBlackamt(value);
        log("setBlackamt: [" + value + "]");
    }

    public void setCardlimit(String value) {
        cityFrontOrderDTO.setCardlimit(value);
        log("setCardlimit: [" + value + "]");
    }

    public void setSecmac(String value) {
        cityFrontOrderDTO.setSecmac(value);
        log("setSecmac: [" + value + "]");
    }

    public void setCardcounter(String value) {
        cityFrontOrderDTO.setCardcounter(value);
        log("setCardcounter: [" + value + "]");
    }

    public void setTxnstat(String value) {
        cityFrontOrderDTO.setTxnstat(value);
        log("setTxnstat: [" + value + "]");
    }

   /* public void setSpecdata(String value) {
        this.specdata = specdata;
    }

    public void setTxnrecode(String[] txnrecode) {
        this.txnrecode = txnrecode;
    }

    public void setBankfile(String[] bankfile) {
        this.bankfile = bankfile;
    }*/

//    public void setApdu(String[] apdu) {
//        this.apdu = apdu;
//    }

    public void setRetdata(String value) {
        cityFrontOrderDTO.setRetdata(value);
        log("setRetdata: [" + value + "]");
    }

    public void setReserved(String value) {
        cityFrontOrderDTO.setReserved(value);
        log("setReserved: [" + value + "]");
    }
    
    private void log(String info) {
        if(log.isDebugEnabled()) {
            log.debug(info);
        }
    }
}
