/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.merdevice.web.controller;

import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.api.card.dto.BJSpecdata;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.md5.V71MD5;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.merdevice.business.constant.MerdeviceConstants;
import com.dodopal.merdevice.business.util.DataTranUtil;
import com.dodopal.merdevice.delegate.TcpServerDelegate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * Created by lenovo on 2016/1/20.
 * 交易-充值
 */
@Controller
public class MessageHandler3011 {
    private static Logger log = Logger.getLogger(MessageHandler3011.class);
    @Autowired
    TcpServerDelegate tcpServerDelegate;

    public String message3011(String msg) {
        long startTime = System.currentTimeMillis();
        log.info("==============message3011开始时间=====================" + startTime);
        //首先解析msg 对msg进行透析
        BJCrdSysOrderDTO bjCrdSysOrderDTO = new BJCrdSysOrderDTO();
        String returnMessage = "";
        String newStirng = "";
        String reVer = "";
        String reDate = "";
        String reCityCode = "";
        try {
            //消息类型
            bjCrdSysOrderDTO.setMessagetype(msg.substring(0, 4));
            //版本号
            reVer = msg.substring(4, 6);
            bjCrdSysOrderDTO.setVer(reVer);
            //发送时间
            reDate = msg.substring(6, 20);
            bjCrdSysOrderDTO.setSysdatetime(reDate);
            //特殊域启用该标志
            String isTSused = msg.substring(20, 22);
            //特殊域长度
            int cmdLen = Integer.parseInt(msg.substring(22, 26));
            //应答码
            bjCrdSysOrderDTO.setRespcode(msg.substring(26, 32));
            //城市代码
            reCityCode = msg.substring(32, 36);
            bjCrdSysOrderDTO.setCitycode(reCityCode);
            //商户类型
            String mertype = msg.substring(36, 38);
            //商户号38开始，要后15位
            bjCrdSysOrderDTO.setMercode(msg.substring(43, 58));
            //用户编号
            String usercode = msg.substring(58, 68);
            bjCrdSysOrderDTO.setUsercode(usercode);
            //设备类型
            bjCrdSysOrderDTO.setPostype(msg.substring(68, 70));
            //设备编号
            bjCrdSysOrderDTO.setPosid(msg.substring(70, 82));
            //设备流水号
            bjCrdSysOrderDTO.setPosseq(msg.substring(82, 91));
            //操作员号
            String operId = msg.substring(91, 107);
            //psam 卡号
            bjCrdSysOrderDTO.setSamno(operId.substring(0, 12));
            bjCrdSysOrderDTO.setUserid(operId);
            bjCrdSysOrderDTO.setUsercode(operId);
            //卡物理类型
            bjCrdSysOrderDTO.setCardtype(msg.substring(107, 109));
            //系统订单号
            String sysOrder = msg.substring(109, 129);
            bjCrdSysOrderDTO.setPrdordernum(sysOrder);
            //支付订单号
            String payOrder = msg.substring(129, 193);
            bjCrdSysOrderDTO.setMerordercode(payOrder);
            //卡号
            bjCrdSysOrderDTO.setCardinnerno(msg.substring(193, 213).trim());
            //卡面号
            bjCrdSysOrderDTO.setCardfaceno(msg.substring(213, 233).trim());
            bjCrdSysOrderDTO.setTradecard(msg.substring(213, 233).trim());
            //交易日期
            bjCrdSysOrderDTO.setTxndate(msg.substring(233, 241));
            //交易时间
            bjCrdSysOrderDTO.setTxntime(msg.substring(241, 247));
            //交易金额
            bjCrdSysOrderDTO.setTxnamt(msg.substring(247, 255));
            //实收金额
            bjCrdSysOrderDTO.setReceivedAmt(msg.substring(247, 255));
            //卡余额
            String balance = DataTranUtil.dropFirst0(msg.substring(255, 263));
            bjCrdSysOrderDTO.setBefbal(balance==null || "".equals(balance)?"0":balance);
            //特殊域
            int crmdEnd = 263 + cmdLen;
            String crmd = msg.substring(263, crmdEnd);
            BJSpecdata bjSpecdata = new BJSpecdata();
            //动作集数量
            int count = Integer.parseInt(crmd.substring(10, 12));
            int course = 12;
            for (int i = 0; i < count; i++) {
                //动作集长度字段内容
                String cipher = crmd.substring(course + 4, course + 8);
                //动作集数据长度
                int cipherdataLen = Integer.parseInt(DataTranUtil.format16to10(cipher)) * 2;
                //动作集结束位置
                int cipherdataEnd = course + cipherdataLen + 8;
//            plainaction = plainaction+crmd.substring(course+8,cipherdataEnd);
                course = cipherdataEnd;
            }
            //动作集明文信息
            bjSpecdata.setPlainaction(crmd.substring(0, course));
            //特殊域
            bjCrdSysOrderDTO.setSpecdata(bjSpecdata);
            //交易记录长度字段内容
            String tranN = crmd.substring(course, course + 4);
            //交易记录长度
            int tranLen = Integer.parseInt(DataTranUtil.noTRansferFormat16to10(tranN)) * 2;
            //交易记录条数
            int tranCount = tranLen / 50;
            //交易记录结束位置
            int tranEnd = course + 4 + tranLen;
            //截取交易记录
            String tranDetail = crmd.substring(course + 4, tranEnd);
            String[] dtoTran = new String[tranCount];
            for (int i = 0; i < tranCount; i++) {
                dtoTran[i] = tranDetail.substring(i * 50, (i + 1) * 50);
            }
            bjCrdSysOrderDTO.setTxnrecode(dtoTran);
            bjCrdSysOrderDTO.setSpecdata(bjSpecdata);
            //保留字段
            String rev = msg.substring(crmdEnd, msg.length());
            bjCrdSysOrderDTO.setReserved(rev);
            if (MerdeviceConstants.QUERY_RECHARGE.equals(rev)) {
                bjCrdSysOrderDTO.setTxntype("0");
            } else {
                bjCrdSysOrderDTO.setTxntype("2");
            }
            bjCrdSysOrderDTO.setReserved(rev);
            //钱包消费
            bjCrdSysOrderDTO.setChargetype(MerdeviceConstants.CHARGE_TYPE);
            //数据来源
            bjCrdSysOrderDTO.setSource(SourceEnum.MERMACHINE.getCode());
            //通讯流水号
            bjCrdSysOrderDTO.setPostransseq("".equals(DataTranUtil.dropFirst0(rev))?"0":DataTranUtil.dropFirst0(rev));

            //调用产品库签到接口，传输对象BJCrdSysOrderDTO
            DodopalResponse<BJCrdSysOrderDTO> returnDto = tcpServerDelegate.V71Message3011(bjCrdSysOrderDTO);
            log.info("=======3011接口调用外围系统返回===========" + returnDto.getCode());
            if (!MerdeviceConstants.SUCCESS.equals(returnDto.getCode())) {
                returnMessage = MerdeviceConstants.CONSUM_CHECK_CARD +
                        //版本号
                        reVer +
                        //发送时间
                        reDate +
                        //特殊域启用该标志
                        "00" +
                        //特殊域长度
                        "0000" +
                        //应答码
                        returnDto.getCode() +
                        //城市代码
                        reCityCode;
                newStirng = String.format("%0" + 4 + "d", returnMessage.length() + 4);
                return newStirng + returnMessage;
            }
            BJCrdSysOrderDTO returnBjCrdSysOrderDto = returnDto.getResponseEntity();
            BJSpecdata returncrdm = returnBjCrdSysOrderDto.getSpecdata();
            String crdmIsused = "00";
            String crdmLen = "0000";
//        if (returncrdm != null || "".equals(returncrdm)) {
//            crdmIsused = "01";
//            //计算特殊域长度
//            crdmLen = String.format("%0" + 4 + "d", returncrdm.toString().length());
//        } else {
//            crdmIsused = "00";
//            crdmLen = "0000";
//        }
            //对商户号进行前边补0操作
            String returnMercode = DataTranUtil.leftAdd0(returnBjCrdSysOrderDto.getMercode(), 20);
            //对版本号进行前边补0
            String returnVer = DataTranUtil.leftAdd0(returnBjCrdSysOrderDto.getVer(), 2);
            returnMessage = MerdeviceConstants.CONSUM_CHECK_CARD +
                    //版本号
                    returnVer +
                    //发送时间
                    returnBjCrdSysOrderDto.getSysdatetime() +
                    //特殊域启用该标志
                    crdmIsused +
                    //特殊域长度
                    crdmLen +
                    //应答码
                    returnBjCrdSysOrderDto.getRespcode() +
                    //城市代码
                    returnBjCrdSysOrderDto.getCitycode() +
                    //商户类型(pos发送直接返回)
                    mertype +
                    //商户号
                    returnMercode +
                    //用户编号
                    usercode +
                    //设备类型
                    DataTranUtil.leftAdd0(returnBjCrdSysOrderDto.getPostype(), 2) +
                    //设备编号
                    DataTranUtil.leftAdd0(returnBjCrdSysOrderDto.getPosid(), 12) +
                    //设备流水号
                    DataTranUtil.leftAdd0(returnBjCrdSysOrderDto.getPosseq(), 9) +
                    //操作员号(pos发送直接返回)
                    operId +
                    //卡物理类型
                    DataTranUtil.leftAdd0(returnBjCrdSysOrderDto.getCardtype(), 2) +
                    //系统订单号
                    DataTranUtil.leftAdd0(returnBjCrdSysOrderDto.getPrdordernum(), 20) +
                    //支付订单号(pos发送直接返回)
                    payOrder +
                    //卡号
                    String.format("%-20s", returnBjCrdSysOrderDto.getCardinnerno()) +
                    //卡面号
                    String.format("%-20s", returnBjCrdSysOrderDto.getCardfaceno()) +
                    //交易日期
                    returnBjCrdSysOrderDto.getTxndate() +
                    //交易时间
                    returnBjCrdSysOrderDto.getTxntime() +
                    //交易金额
                    DataTranUtil.leftAdd0(returnBjCrdSysOrderDto.getTxnamt(), 8) +
                    //卡余额
                    DataTranUtil.leftAdd0(returnBjCrdSysOrderDto.getBefbal(), 8) +
//        //特殊域
//        returnBjCrdSysOrderDto.getSpecdata()+
//        //交易记录
//        returnBjCrdSysOrderDto.getTxnrecode()+
                    //保留字段
                    DataTranUtil.leftAdd0(returnBjCrdSysOrderDto.getReserved(), 10);
            newStirng = String.format("%0" + 4 + "d", returnMessage.length() + 4);
        } catch (DDPException ex) {
            log.error("3011DDPException===============" + ex.getCode());
            ex.printStackTrace();
            returnMessage = MerdeviceConstants.CONSUM_CHECK_CARD +
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
            log.error("3011Exception===============" + ex.getMessage());
            ex.printStackTrace();
            returnMessage = MerdeviceConstants.CONSUM_CHECK_CARD +
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
        log.info("==============message3011耗费时间=====================" + (System.currentTimeMillis() - startTime));
        return newStirng + returnMessage;
    }
}
