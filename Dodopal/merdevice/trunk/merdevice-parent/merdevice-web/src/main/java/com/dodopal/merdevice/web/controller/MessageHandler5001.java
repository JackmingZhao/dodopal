/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.merdevice.web.controller;

import com.dodopal.api.card.dto.SignParameter;
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
public class MessageHandler5001 {
    private static Logger log = Logger.getLogger(MessageHandler5001.class);
    @Autowired
    TcpServerDelegate tcpServerDelegate;

    public String message5001(String msg) {
        long startTime = System.currentTimeMillis();
        log.info("==============message5001开始时间=====================" + startTime);
        //首先解析msg 对msg进行透析
        SignParameter signParameter = new SignParameter();
        String returnMessage = "";
        String newStirng = "";
        String reVer = "";
        String reDate = "";
        String reCityCode = "";
        try {
            //消息类型
            signParameter.setMessagetype(msg.substring(0, 4));
            //版本号
            reVer = msg.substring(4, 6);
            signParameter.setVer(reVer);
            //发送时间
            reDate = msg.substring(6, 20);
            signParameter.setSysdatetime(reDate);
            //特殊域启用标志
            signParameter.setIstsused(msg.substring(20, 22));
            //特殊域长度
            String cmdLen = msg.substring(22, 26);
            //应答码
            signParameter.setRespcode(msg.substring(26, 32));
            //城市代码
            reCityCode = msg.substring(32, 36);
            signParameter.setCitycode(reCityCode);
            //商户类型
            signParameter.setMertype(msg.substring(36, 38));
            //商户号38开始，要后15位
            signParameter.setMercode(msg.substring(43, 58));
            //设备类型
            signParameter.setPostype(msg.substring(58, 60));
            //设备编号
            signParameter.setPosid(msg.substring(60, 72));
            //操作员号
            signParameter.setOperid(msg.substring(72, 88));
            //PSAM号
            //signParameter.setSamno(msg.substring(98, 110));
            //结算日期
            signParameter.setSettdate(msg.substring(88, 96));
            //通讯流水号
            signParameter.setComseq(msg.substring(96, 106));
            //终端IC交易流水号
            signParameter.setIcseq(msg.substring(106, 116));
            //终端账户交易流水号
            signParameter.setAccseq(msg.substring(116, 126));
            //批次号
            signParameter.setBatchid(msg.substring(126, 136));
            //授权截止至时间
            signParameter.setAuthendtime(msg.substring(136, 150));
            //系统时间
            signParameter.setDatetime(msg.substring(150, 164));
            //服务器公钥
            signParameter.setCert(msg.substring(164, 420));
            //工作密钥
            signParameter.setKeyset(msg.substring(420, 452));
            //计算特殊域的长度
            int len = Integer.parseInt(cmdLen);
            //计算特殊域的结束位置
            int end = 452 + len;
            if (len != 0) {
                //特殊域
                signParameter.setCrdm(msg.substring(452, end));
            }
            //保留字段;
            String reserved = msg.substring(end, msg.length());
            signParameter.setReserved(reserved);
            //调用产品库签到接口，传输对象SignParameter
            DodopalResponse<SignParameter> retSignParameter = tcpServerDelegate.signin(signParameter);
            log.info("=======5001接口调用外围系统返回===========" + retSignParameter.getCode());
            if (!MerdeviceConstants.SUCCESS.equals(retSignParameter.getCode())) {
                //1对返回对象进行处理，拆解成报文直接返回客户端
                String returnMsg = MerdeviceConstants.SIGN_ON +
                        reVer +
                        reDate +
                        "00" + "0000" +
                        retSignParameter.getCode() +
                        reCityCode;
                //2报文长度获取，以便封装返回时使用：
                //3对报文进行
                newStirng = String.format("%0" + 4 + "d", returnMsg.length() + 4);
                return newStirng + returnMsg;
            }
            SignParameter returnSignParameter = retSignParameter.getResponseEntity();
            String crdm = returnSignParameter.getCrdm();
            String crdmIsused = "";
            String crdmLen = "";
            if (crdm != null && !"".equals(crdm)) {
                crdmIsused = "10";
                //计算特殊域长度
                crdmLen = String.format("%0" + 4 + "d", crdm.length());
            } else {
                crdmIsused = "00";
                crdmLen = "0000";
            }
            //对商户号进行前边补0操作
            String returnMercode = DataTranUtil.leftAdd0(returnSignParameter.getMercode(), 20);
            //对版本号进行前边补0
            String returnVer = DataTranUtil.leftAdd0(returnSignParameter.getVer(), 2);
            String returnReserved = returnSignParameter.getReserved();
            if("05".equals(reVer)){
                if(log.isInfoEnabled()){
                    log.info("签到报文版本号==========="+reVer);
                }
                returnReserved = DataTranUtil.rightAdd0(returnSignParameter.getReserved(), 100);
            }
            //对pos类型进行前边补0
            String returnPostype = DataTranUtil.leftAdd0(returnSignParameter.getPostype(), 2);
            //对商户类型进行前边补0
            String returnMertype = DataTranUtil.leftAdd0(returnSignParameter.getMertype(), 2);
            String returnCert = DataTranUtil.leftAdd0(returnSignParameter.getCert(), 256);
            String keySet = "";
            if (returnSignParameter.getKeyset() == null || "".equals(returnSignParameter.getKeyset())) {
                keySet = "00000000000000000000000000000000";
            } else {
                keySet = returnSignParameter.getKeyset();
            }
            //1对返回对象进行处理，拆解成报文直接返回客户端
            returnMessage = MerdeviceConstants.SIGN_ON +
                    returnVer +
                    returnSignParameter.getSysdatetime() +
                    crdmIsused + crdmLen +
                    returnSignParameter.getRespcode() +
                    returnSignParameter.getCitycode() +
                    returnMertype +
                    returnMercode +
                    returnPostype +
                    returnSignParameter.getPosid() +
                    returnSignParameter.getOperid() +
                    returnSignParameter.getSettdate() +
                    returnSignParameter.getComseq() +
                    returnSignParameter.getIcseq() +
                    returnSignParameter.getAccseq() +
                    returnSignParameter.getBatchid() +
                    returnSignParameter.getAuthendtime() +
                    returnSignParameter.getDatetime() +
                    returnCert +
                    keySet +
                    returnSignParameter.getCrdm() +
                    returnReserved;
            newStirng = String.format("%0" + 4 + "d", returnMessage.length() + 4);
        } catch (DDPException ex) {
            log.error("5001DDPException==============="+ex.getCode());
            ex.printStackTrace();
            returnMessage = MerdeviceConstants.SIGN_ON +
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
            log.error("5001Exception==============="+ex.getMessage());
            ex.printStackTrace();
            returnMessage = MerdeviceConstants.SIGN_ON +
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
        log.info("==============message5001耗费时间=====================" + (System.currentTimeMillis() - startTime));
        return newStirng + returnMessage;
    }
}
