/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.merdevice.web.controller;

import com.dodopal.api.card.dto.BJAccountConsumeDTO;
import com.dodopal.api.card.dto.BJAccountConsumeSpecialDTO;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.merdevice.business.constant.MerdeviceConstants;
import com.dodopal.merdevice.business.util.DataTranUtil;
import com.dodopal.merdevice.delegate.TcpServerDelegate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by lenovo on 2016/1/20.
 */
@Controller
public class MessageHandler3401 {
    private static Logger log = Logger.getLogger(MessageHandler3401.class);
    @Autowired
    TcpServerDelegate tcpServerDelegate;

    public String message3401(String msg) {
        long startTime = System.currentTimeMillis();
        log.info("==============message3401开始时间=====================" + startTime);
        //首先解析msg 对msg进行透析
        BJAccountConsumeDTO bjAccountConsumeDTO = new BJAccountConsumeDTO();
        String returnMessage = "";
        String newStirng = "";
        String reVer = "";
        String reDate = "";
        String reCityCode = "";
        try {
            //消息类型
            bjAccountConsumeDTO.setMessagetype(msg.substring(0, 4));
            //版本号
            reVer = msg.substring(4, 6);
            bjAccountConsumeDTO.setVer(reVer);
            //发送时间
            reDate = msg.substring(6, 20);
            bjAccountConsumeDTO.setSysdatetime(reDate);
            //特殊域启用标志
            bjAccountConsumeDTO.setIstsused(msg.substring(20, 22));
            //特殊域长度
            String cmdLen = msg.substring(22, 26);
            //应答码
            bjAccountConsumeDTO.setRespcode(msg.substring(26, 32));
            //城市代码
            reCityCode = msg.substring(32, 36);
            bjAccountConsumeDTO.setCitycode(reCityCode);
            //商户类型
            bjAccountConsumeDTO.setMertype(msg.substring(36, 38));
            //商户号38开始，要后15位
            bjAccountConsumeDTO.setMercode(msg.substring(43, 58));
            //设备类型
            bjAccountConsumeDTO.setPostype(DataTranUtil.dropFirst0(msg.substring(58, 60)));
            //设备编号
            bjAccountConsumeDTO.setPoscode(msg.substring(60, 72));
            //操作员号
            bjAccountConsumeDTO.setOperid(msg.substring(72, 88));
            //结算日期
            bjAccountConsumeDTO.setSettdate(msg.substring(88, 96));
            //通讯流水号
            bjAccountConsumeDTO.setComseq(msg.substring(96, 106));
            //终端IC交易流水号
            bjAccountConsumeDTO.setIcseq(msg.substring(106, 116));
            //终端账户交易流水号
            bjAccountConsumeDTO.setAccseq(msg.substring(116, 126));
            //批次号
            bjAccountConsumeDTO.setBatchid(msg.substring(126, 136));
            //系统时间
            bjAccountConsumeDTO.setDatetime(msg.substring(136, 150));
            //系统订单号 + 产品库订单号
            bjAccountConsumeDTO.setProordernum(msg.substring(150, 170));
            bjAccountConsumeDTO.setProordernum(msg.substring(150, 170));
            //卡号
            bjAccountConsumeDTO.setCardno(msg.substring(170, 190).trim());
            //账户号
            bjAccountConsumeDTO.setAccountno(msg.substring(190, 210));
            //交易金额
            bjAccountConsumeDTO.setTxnamt(msg.substring(210, 218));
            //个性化信息
            bjAccountConsumeDTO.setPrivimsg(msg.substring(218, 418));
            int len = Integer.parseInt(cmdLen);
            //计算特殊域的结束位置
            int end = 418 + len;
            if (len != 0) {
                //特殊域
                String crmdStr = msg.substring(418, end);
                BJAccountConsumeSpecialDTO bjAccountConsumeSpecialDTO = new BJAccountConsumeSpecialDTO();
                //交易类型
                bjAccountConsumeSpecialDTO.setTxntype(crmdStr.substring(0, 4));
                //pos通讯流水号
                bjAccountConsumeSpecialDTO.setPostranseq(crmdStr.substring(4, 12));
//                //交易日期
//                bjAccountConsumeSpecialDTO.setTxndate(crmdStr.substring(12, 20));
//                //交易时间
//                bjAccountConsumeSpecialDTO.setTxntime(crmdStr.substring(20, 26));
                //卡号
                bjAccountConsumeSpecialDTO.setCardno(crmdStr.substring(12, 28));
                //卡片验证信息
                bjAccountConsumeSpecialDTO.setCardmsg(crmdStr.substring(28, 86));
                //卡内余额
                bjAccountConsumeSpecialDTO.setCardbal(crmdStr.substring(86, 94));
                //账户号
                bjAccountConsumeSpecialDTO.setAccountid(crmdStr.substring(94, 114));
                //交易金额
                bjAccountConsumeSpecialDTO.setPreauthamt(crmdStr.substring(114, 122));
//                //主机交易流水号
//                bjAccountConsumeSpecialDTO.setTxnseqno(crmdStr.substring(136, 144));
                //账户个人校验码pin
                bjAccountConsumeSpecialDTO.setEncpasswd(crmdStr.substring(122, 138));
//                //自动圈存个圈存交易状态
//                bjAccountConsumeSpecialDTO.setAutotxnstate(crmdStr.substring(164, 168));
//                //自动圈存金额
//                bjAccountConsumeSpecialDTO.setAutoloadamt(crmdStr.substring(168, 176));
//                //自动圈存下步动作集合
//                bjAccountConsumeSpecialDTO.setNtxtstep(crmdStr.substring(176, 216));
                bjAccountConsumeSpecialDTO.setEncryptinfo(crmdStr.substring(138,crmdStr.length()));
                bjAccountConsumeDTO.setCrdm(bjAccountConsumeSpecialDTO);
            }
            //保留字段;
            String reserved = msg.substring(end, msg.length());
            bjAccountConsumeDTO.setReserved(reserved);
            //调用产品库签到接口，传输对象SignParameter
            DodopalResponse<BJAccountConsumeDTO> returnbjAccountConsume = tcpServerDelegate.accountConsume(bjAccountConsumeDTO);
            log.info("=======message3401接口调用外围系统返回===========" + returnbjAccountConsume.getCode());
            if (!MerdeviceConstants.SUCCESS.equals(returnbjAccountConsume.getCode())) {
                //1对返回对象进行处理，拆解成报文直接返回客户端
                String returnMsg = MerdeviceConstants.ACCOUNT_CONSUME +
                        reVer +
                        reDate +
                        "00" + "0000" +
                        returnbjAccountConsume.getCode() +
                        reCityCode;
                //2报文长度获取，以便封装返回时使用：
                //3对报文进行
                newStirng = String.format("%0" + 4 + "d", returnMsg.length() + 4);
                return newStirng + returnMsg;
            }
            BJAccountConsumeDTO returnbjAccountConsumeDTO = returnbjAccountConsume.getResponseEntity();
            BJAccountConsumeSpecialDTO crdm = returnbjAccountConsumeDTO.getCrdm();
            String returnCrmdStr = crdm.getEncryptinfo();
            if(returnCrmdStr==null){
                returnCrmdStr="";
            }
//                    //交易类型
//                    crdm.getTxntype() +
//                    //pos通讯流水号
//                    crdm.getPostranseq() +
//                    //交易日期
//                    crdm.getTxndate() +
//                    //交易时间
//                    crdm.getTxntime() +
//                    //卡号
//                    crdm.getCardno() +
////                    //卡片验证信息
////                    crdm.getCardmsg() +
////                    //卡内余额
////                    crdm.getCardbal() +
//                    //账户号
//                    crdm.getAccountid() +
//                    //交易金额
//                    crdm.getPreauthamt() +
//                    //主机交易流水号
//                    crdm.getTxnseqno() +
////                    //账户个人校验码pin
////                    crdm.getEncpasswd() +
//                    //自动圈存交易类型
//                    crdm.getAutotxntype() +
//                    //自动圈存个圈存交易状态
//                    crdm.getAutotxnstate() +
//                    //自动圈存金额
//                    crdm.getAutoloadamt() +
//                    //自动圈存下步动作集合
//                    crdm.getNtxtstep();
            String crdmIsused = "";
            String crdmLen = "";
            if (returnCrmdStr != null && !"".equals(returnCrmdStr)) {
                crdmIsused = "10";
                //计算特殊域长度
                crdmLen = String.format("%0" + 4 + "d", returnCrmdStr.length());
            } else {
                crdmIsused = "00";
                crdmLen = "0000";
            }
            //对商户号进行前边补0操作
            String returnMercode = DataTranUtil.leftAdd0(returnbjAccountConsumeDTO.getMercode(), 20);
            //对版本号进行前边补0
            String returnVer = DataTranUtil.leftAdd0(returnbjAccountConsumeDTO.getVer(), 2);
            String returnReserved = returnbjAccountConsumeDTO.getReserved();
            //对pos类型进行前边补0
            String returnPostype = DataTranUtil.leftAdd0(returnbjAccountConsumeDTO.getPostype(), 2);
            //对商户类型进行前边补0
            String returnMertype = DataTranUtil.leftAdd0(returnbjAccountConsumeDTO.getMertype(), 2);
            String returnAccNum = returnbjAccountConsumeDTO.getAccnum();
            String returnAccInfo = "";
            if(!"0".equals(returnAccNum)){
                returnAccInfo = returnbjAccountConsumeDTO.getAccinfo();
            }
            //1对返回对象进行处理，拆解成报文直接返回客户端
            returnMessage = MerdeviceConstants.ACCOUNT_CONSUME +
                    //版本号
                    returnVer +
                    //发送时间
                    returnbjAccountConsumeDTO.getSysdatetime() +
                    //特殊域启用标志
                    crdmIsused +
                    //特殊域长度
                    crdmLen +
                    //应答码
                    returnbjAccountConsumeDTO.getRespcode() +
                    //城市代码
                    returnbjAccountConsumeDTO.getCitycode() +
                    //商户类型
                    returnMertype +
                    //商户号
                    returnMercode +
                    //设备类型
                    returnPostype +
                    //设备编号
                    returnbjAccountConsumeDTO.getPoscode()+
                    //操作员号
                    returnbjAccountConsumeDTO.getOperid()+
                    //结算日期
                    returnbjAccountConsumeDTO.getSettdate()+
                    //通讯流水号
                    returnbjAccountConsumeDTO.getComseq()+
                    //终端IC交易流水号
                    returnbjAccountConsumeDTO.getIcseq()+
                    //终端账户交易流水号
                    returnbjAccountConsumeDTO.getAccseq()+
                    //批次号
                    returnbjAccountConsumeDTO.getBatchid()+
                    //系统时间
                    returnbjAccountConsumeDTO.getDatetime()+
                    //系统订单号
                    returnbjAccountConsumeDTO.getProordernum()+
                    //卡号
                    String.format("%-20s",returnbjAccountConsumeDTO.getCardno())+
                    //账户号
                    returnbjAccountConsumeDTO.getAccountno()+
                    //交易金额
                    returnbjAccountConsumeDTO.getTxnamt()+
//                    //个性化信息
//                    returnbjAccountConsumeDTO.getPrivimsg()+
                    //账户个数
                    returnAccNum +
                    //账户信息
                    returnAccInfo +
                    //特殊域
                    returnCrmdStr +
                    //保留字段
                    returnReserved;
            newStirng = String.format("%0" + 4 + "d", returnMessage.length() + 4);
        } catch (DDPException ex) {
            log.error("message3401DDPException===============" + ex.getCode());
            ex.printStackTrace();
            returnMessage = MerdeviceConstants.ACCOUNT_CONSUME +
                    //版本号
                    reVer +
                    //发送时间
                    reDate +
                    //特殊域启用该标志
                    "00" +
                    //特殊域长度
                    "0000" +
                    //应答码
                    ex.getCode() +
                    //城市代码
                    reCityCode;
            newStirng = String.format("%0" + 4 + "d", returnMessage.length() + 4);
            return newStirng + returnMessage;
        } catch (Exception ex) {
            log.error("message3401Exception===============" + ex.getMessage());
            ex.printStackTrace();
            returnMessage = MerdeviceConstants.ACCOUNT_CONSUME +
                    //版本号
                    reVer +
                    //发送时间
                    reDate +
                    //特殊域启用该标志
                    "00" +
                    //特殊域长度
                    "0000" +
                    //应答码
                    MerdeviceConstants.MESSAGE_ANALYZE_FAILURE +
                    //城市代码
                    reCityCode;
            newStirng = String.format("%0" + 4 + "d", returnMessage.length() + 4);
            return newStirng + returnMessage;
        }
        log.info("==============message3401耗费时间=====================" + (System.currentTimeMillis() - startTime));
        return newStirng + returnMessage;
    }
}
