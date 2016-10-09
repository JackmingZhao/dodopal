/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.merdevice.web.controller;

import com.dodopal.api.card.dto.BJIntegralConsumeDTO;
import com.dodopal.api.card.dto.BJIntegralConsumeSpecialDTO;
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
public class MessageHandler3501 {
    private static Logger log = Logger.getLogger(MessageHandler3501.class);
    @Autowired
    TcpServerDelegate tcpServerDelegate;

    public String message3501(String msg) {
        long startTime = System.currentTimeMillis();
        log.info("==============message3501开始时间=====================" + startTime);
        //首先解析msg 对msg进行透析
        BJIntegralConsumeDTO bjIntegralConsumeDTO = new BJIntegralConsumeDTO();
        String returnMessage = "";
        String newStirng = "";
        String reVer = "";
        String reDate = "";
        String reCityCode = "";
        try {
            //消息类型
            bjIntegralConsumeDTO.setMessagetype(msg.substring(0, 4));
            //版本号
            reVer = msg.substring(4, 6);
            bjIntegralConsumeDTO.setVer(reVer);
            //发送时间
            reDate = msg.substring(6, 20);
            bjIntegralConsumeDTO.setSysdatetime(reDate);
            //特殊域启用标志
            bjIntegralConsumeDTO.setIstsused(msg.substring(20, 22));
            //特殊域长度
            String cmdLen = msg.substring(22, 26);
            //应答码
            bjIntegralConsumeDTO.setRespcode(msg.substring(26, 32));
            //城市代码
            reCityCode = msg.substring(32, 36);
            bjIntegralConsumeDTO.setCitycode(reCityCode);
            //商户类型
            bjIntegralConsumeDTO.setMertype(msg.substring(36, 38));
            //商户号38开始，要后15位
            bjIntegralConsumeDTO.setMercode(msg.substring(43, 58));
            //设备类型
            bjIntegralConsumeDTO.setPostype(msg.substring(58, 60));
            //设备编号
            bjIntegralConsumeDTO.setPoscode(msg.substring(60, 72));
            //操作员号
            bjIntegralConsumeDTO.setOperid(msg.substring(72, 88));
            //结算日期
            bjIntegralConsumeDTO.setSettdate(msg.substring(88, 96));
            //通讯流水号
            bjIntegralConsumeDTO.setComseq(msg.substring(96, 106));
            //终端IC交易流水号
            bjIntegralConsumeDTO.setIcseq(msg.substring(106, 116));
            //终端账户交易流水号
            bjIntegralConsumeDTO.setAccseq(msg.substring(116, 126));
            //批次号
            bjIntegralConsumeDTO.setBatchid(msg.substring(126, 136));
            //系统时间
            bjIntegralConsumeDTO.setDatetime(msg.substring(136, 150));
            //系统订单号
            bjIntegralConsumeDTO.setProordernum(msg.substring(150, 170));
            //卡号
            bjIntegralConsumeDTO.setCardno(msg.substring(170, 190).trim());
            //积分金额
            bjIntegralConsumeDTO.setPreautheamt(msg.substring(190, 198));
            int len = Integer.parseInt(cmdLen);
            //计算特殊域的结束位置
            int end = 198 + len;
            if (len != 0) {
                //特殊域
                String crmdStr = msg.substring(198, end);
                BJIntegralConsumeSpecialDTO bjIntegralConsumeSpecialDTO = new BJIntegralConsumeSpecialDTO();
                //交易类型
                bjIntegralConsumeSpecialDTO.setTxntype(crmdStr.substring(0, 4));
                //pos通讯流水号
                bjIntegralConsumeSpecialDTO.setPostranseq(crmdStr.substring(4, 12));
                //卡号
                bjIntegralConsumeSpecialDTO.setCardno(crmdStr.substring(12, 28));
                //卡片验证信息
                bjIntegralConsumeSpecialDTO.setCardmsg(crmdStr.substring(28, 86));
                //卡内余额
                bjIntegralConsumeSpecialDTO.setCardbal(crmdStr.substring(86, 94));
                //积分余额
                bjIntegralConsumeSpecialDTO.setPreauthamt(crmdStr.substring(94, 102));
                //账户个人校验码
                bjIntegralConsumeSpecialDTO.setEncpasswd(crmdStr.substring(102, 118));
                //特殊域放到外层bean中
                bjIntegralConsumeDTO.setCrdm(bjIntegralConsumeSpecialDTO);
            }
            //保留字段;
            String reserved = msg.substring(end, msg.length());
            bjIntegralConsumeDTO.setReserved(reserved);
            //调用产品库签到接口，传输对象SignParameter
            DodopalResponse<BJIntegralConsumeDTO> returnbjIntegralConsume = tcpServerDelegate.integralConsume(bjIntegralConsumeDTO);
            log.info("=======message3501接口调用外围系统返回===========" + returnbjIntegralConsume.getCode());
            if (!MerdeviceConstants.SUCCESS.equals(returnbjIntegralConsume.getCode())) {
                //1对返回对象进行处理，拆解成报文直接返回客户端
                String returnMsg = MerdeviceConstants.INTEGRAL_CONSUME +
                        reVer +
                        reDate +
                        "00" + "0000" +
                        returnbjIntegralConsume.getCode() +
                        reCityCode;
                //2报文长度获取，以便封装返回时使用：
                //3对报文进行
                newStirng = String.format("%0" + 4 + "d", returnMsg.length() + 4);
                return newStirng + returnMsg;
            }
            BJIntegralConsumeDTO returnbjIntegralConsumeDTO = returnbjIntegralConsume.getResponseEntity();
            BJIntegralConsumeSpecialDTO crdm = returnbjIntegralConsumeDTO.getCrdm();
            String returnCrmdStr =
                    //交易类型
                    crdm.getTxntype() +
                            //pos通讯流水号
                            crdm.getPostranseq() +
                            //交易日期
                            crdm.getTxndate() +
                            //交易时间
                            crdm.getTxntime() +
                            //卡号
                            crdm.getCardno() +
                            //积分金额
                            crdm.getPreauthamt() +
                            //主机交易流水号
                            crdm.getTxnseqno();
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
            String returnMercode = DataTranUtil.leftAdd0(returnbjIntegralConsumeDTO.getMercode(), 20);
            //对版本号进行前边补0
            String returnVer = DataTranUtil.leftAdd0(returnbjIntegralConsumeDTO.getVer(), 2);
            String returnReserved = returnbjIntegralConsumeDTO.getReserved();
            //对pos类型进行前边补0
            String returnPostype = DataTranUtil.leftAdd0(returnbjIntegralConsumeDTO.getPostype(), 2);
            //对商户类型进行前边补0
            String returnMertype = DataTranUtil.leftAdd0(returnbjIntegralConsumeDTO.getMertype(), 2);
            //1对返回对象进行处理，拆解成报文直接返回客户端
            returnMessage = MerdeviceConstants.INTEGRAL_CONSUME +
                    //版本号
                    returnVer +
                    //发送时间
                    returnbjIntegralConsumeDTO.getSysdatetime() +
                    //特殊域启用标志
                    crdmIsused +
                    //特殊域长度
                    crdmLen +
                    //应答码
                    returnbjIntegralConsumeDTO.getRespcode() +
                    //城市代码
                    returnbjIntegralConsumeDTO.getCitycode() +
                    //商户类型
                    returnMertype +
                    //商户号
                    returnMercode +
                    //设备类型
                    returnPostype +
                    //设备编号
                    returnbjIntegralConsumeDTO.getPoscode() +
                    //操作员号
                    returnbjIntegralConsumeDTO.getOperid() +
                    //结算日期
                    returnbjIntegralConsumeDTO.getSettdate() +
                    //通讯流水号
                    returnbjIntegralConsumeDTO.getComseq() +
                    //终端IC交易流水号
                    returnbjIntegralConsumeDTO.getIcseq() +
                    //终端账户交易流水号
                    returnbjIntegralConsumeDTO.getAccseq() +
                    //批次号
                    returnbjIntegralConsumeDTO.getBatchid() +
                    //系统时间
                    returnbjIntegralConsumeDTO.getDatetime() +
                    //系统订单号
                    returnbjIntegralConsumeDTO.getProordernum() +
                    //卡号
                    String.format("%-20s", returnbjIntegralConsumeDTO.getCardno()) +
                    //积分金额
                    returnbjIntegralConsumeDTO.getPreautheamt() +
                    //特殊域
                    returnCrmdStr +
                    //保留字段
                    returnReserved;
            newStirng = String.format("%0" + 4 + "d", returnMessage.length() + 4);
        } catch (DDPException ex) {
            log.error("message3501DDPException===============" + ex.getCode());
            ex.printStackTrace();
            returnMessage = MerdeviceConstants.INTEGRAL_CONSUME +
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
            log.error("message3501Exception===============" + ex.getMessage());
            ex.printStackTrace();
            returnMessage = MerdeviceConstants.INTEGRAL_CONSUME +
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
        log.info("==============message3501耗费时间=====================" + (System.currentTimeMillis() - startTime));
        return newStirng + returnMessage;
    }
}
