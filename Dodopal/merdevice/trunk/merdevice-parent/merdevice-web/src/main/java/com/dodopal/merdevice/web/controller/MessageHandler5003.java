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
public class MessageHandler5003 {
    private static Logger log = Logger.getLogger(MessageHandler5003.class);
    @Autowired
    TcpServerDelegate tcpServerDelegate;//= new TcpServerDelegateImpl();

    public String message5003(String msg) {
        long startTime = System.currentTimeMillis();
        log.info("==============message5003开始时间=====================" + startTime);
        //首先解析msg 对msg进行透析
        SignParameter signParameter = new SignParameter();
        String returnMsg = "";
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
            String isTsused = msg.substring(20, 22);
            signParameter.setIstsused(isTsused);
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
            //脱机消费成功总笔数
            signParameter.setTotalrecnum(msg.substring(96, 104));
            //通讯流水号
            signParameter.setComseq(msg.substring(104, 114));
            //终端IC交易流水号
            signParameter.setIcseq(msg.substring(114, 124));
            //终端账户交易流水号
            signParameter.setAccseq(msg.substring(124, 134));
            //批次号
            signParameter.setBatchid(msg.substring(134, 144));
            //授权截止至时间
            signParameter.setAuthendtime(msg.substring(144, 158));
            //系统时间
            signParameter.setDatetime(msg.substring(158, 172));
            //服务器公钥
            //signParameter.setCert(msg.substring(172, 420));
            //工作密钥
            // signParameter.setKeyset(msg.substring(420, 452));
            //计算特殊域的长度
            int len = Integer.parseInt(cmdLen);
            //计算特殊域的结束位置
            int end = 172 + len;
            if (len != 0) {
                //特殊域
                signParameter.setCrdm(msg.substring(452, end));
            }
            //保留字段;
            signParameter.setReserved(msg.substring(end, end + 10));
            //调用产品库签到接口，传输对象SignParameter
            DodopalResponse<SignParameter> retSignParameter = tcpServerDelegate.signout(signParameter);
            log.info("=======5003接口调用外围系统返回===========" + retSignParameter.getCode());
            if (!MerdeviceConstants.SUCCESS.equals(retSignParameter.getCode())) {
                returnMsg = MerdeviceConstants.SIGN_OUT +
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
            //对pos类型进行前边补0
            String returnPostype = DataTranUtil.leftAdd0(returnSignParameter.getPostype(), 2);
            //对商户类型进行前边补0
            String returnMertype = DataTranUtil.leftAdd0(returnSignParameter.getMertype(), 2);
            //1对返回对象进行处理，拆解成报文直接返回客户端
            returnMsg = MerdeviceConstants.SIGN_OUT +
                    returnVer +
                    returnSignParameter.getSysdatetime() +
                    crdmIsused + crdmLen +
                    returnSignParameter.getRespcode() +
                    returnSignParameter.getCitycode() +
                    returnSignParameter.getMertype() +
                    returnMercode +
                    returnPostype +
                    returnSignParameter.getPosid() +
                    returnSignParameter.getOperid() +
                    returnSignParameter.getSettdate() +
                    returnSignParameter.getTotalrecnum() +
                    returnSignParameter.getComseq() +
                    returnSignParameter.getIcseq() +
                    returnSignParameter.getAccseq() +
                    returnSignParameter.getBatchid() +
                    returnSignParameter.getAuthendtime() +
                    returnSignParameter.getDatetime() +
                    returnSignParameter.getReserved();
            //2报文长度获取，以便封装返回时使用：
            //3对报文进行
            newStirng = String.format("%0" + 4 + "d", returnMsg.length() + 4);
        } catch (DDPException ex) {
            log.error("5003DDPException===============" + ex.getCode());
            ex.printStackTrace();
            returnMsg = MerdeviceConstants.SIGN_OUT +
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
            newStirng = String.format("%0" + 4 + "d", returnMsg.length() + 4);
            return newStirng + returnMsg;
        } catch (Exception ex) {
            log.error("5003Exception===============" + ex.getMessage());
            ex.printStackTrace();
            returnMsg = MerdeviceConstants.SIGN_OUT +
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
            newStirng = String.format("%0" + 4 + "d", returnMsg.length() + 4);
            return newStirng + returnMsg;
        }
        log.info("==============message5003耗费时间=====================" + (System.currentTimeMillis() - startTime));
        return newStirng + returnMsg;
    }
}
