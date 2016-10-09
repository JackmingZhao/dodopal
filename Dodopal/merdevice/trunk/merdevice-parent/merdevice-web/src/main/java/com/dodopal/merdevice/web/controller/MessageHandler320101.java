/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.merdevice.web.controller;

import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.api.card.dto.BJSpecdata;
import com.dodopal.api.card.dto.ReslutData;
import com.dodopal.api.card.dto.ReslutDataSpecial;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.merdevice.business.constant.MerdeviceConstants;
import com.dodopal.merdevice.business.util.DataTranUtil;
import com.dodopal.merdevice.delegate.TcpServerDelegate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/1/20.
 * 交易-充值
 */
@Controller
public class MessageHandler320101 {
    private static Logger log = Logger.getLogger(MessageHandler320101.class);
    @Autowired
    TcpServerDelegate tcpServerDelegate;

    public String message320101(String msg) {
        long startTime = System.currentTimeMillis();
        log.info("==============message320101开始时间=====================" + startTime);
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
            //交易状态
            String tranStatus = msg.substring(36, 37);
            bjCrdSysOrderDTO.setTxnstat(tranStatus);
            //商户类型
            String mertype = msg.substring(37, 39);
            //商户号39开始，要后15位
            bjCrdSysOrderDTO.setMercode(msg.substring(44, 59));
            //银行编号
            String bankId = msg.substring(59, 67);
            //用户编号
            String usercode = msg.substring(67, 77);
            bjCrdSysOrderDTO.setUsercode(usercode);
            //设备类型
            bjCrdSysOrderDTO.setPostype(DataTranUtil.dropFirst0(msg.substring(77, 79)));
            //设备编号
            bjCrdSysOrderDTO.setPosid(msg.substring(79, 91));
            //设备流水号
            bjCrdSysOrderDTO.setPosseq(msg.substring(91, 100));
            //操作员号
            String operId = msg.substring(100, 116);
            //psam 卡号
            bjCrdSysOrderDTO.setSamno(operId.substring(0, 12));
            bjCrdSysOrderDTO.setUserid(operId);
            //卡物理类型
            bjCrdSysOrderDTO.setCardtype(DataTranUtil.dropFirst0(msg.substring(116, 118)));
            //系统订单号
            String sysOrder = msg.substring(118, 138);
            bjCrdSysOrderDTO.setPrdordernum(sysOrder);
            //支付订单号
            String payOrder = msg.substring(138, 202);
            //卡号
            bjCrdSysOrderDTO.setTradecard(msg.substring(202, 222).trim());
            bjCrdSysOrderDTO.setCardinnerno(msg.substring(202, 222).trim());
            //卡面号
            bjCrdSysOrderDTO.setCardfaceno(msg.substring(222, 242).trim());
            //交易日期
            bjCrdSysOrderDTO.setTxndate(msg.substring(242, 250));
            //交易时间
            bjCrdSysOrderDTO.setTxntime(msg.substring(250, 256));
            //交易金额
            String amount = DataTranUtil.dropFirst0(msg.substring(256, 264))==null||"".equals(DataTranUtil.dropFirst0(msg.substring(256, 264)))?"0":DataTranUtil.dropFirst0(msg.substring(256, 264));
            bjCrdSysOrderDTO.setTxnamt(amount);
            //交易前余额
            String beforeBalance = DataTranUtil.dropFirst0(msg.substring(264, 272));
            bjCrdSysOrderDTO.setBefbal(beforeBalance==null||"".equals(beforeBalance)?"0":beforeBalance);
            //交易后金额
            String afterBalance = DataTranUtil.dropFirst0(msg.substring(272, 280));
            bjCrdSysOrderDTO.setBlackamt(afterBalance==null || "".equals(afterBalance)?"0":afterBalance);
            //卡计数器
            bjCrdSysOrderDTO.setCardcounter(msg.substring(280, 288));
            //TAC
            String tac = msg.substring(288, 296);
            //MAC2
            String mac2 = msg.substring(296, 304);
            int crmdEnd = 304 + cmdLen;
            if (cmdLen != 0) {
                //特殊域
                BJSpecdata bjSpecdata = null;
                String crmd = msg.substring(304, crmdEnd);
                if (!"0".equals(tranStatus)) {
                    bjSpecdata = new BJSpecdata();
                    //动作集明文信息
                    bjSpecdata.setPlainaction(crmd.substring(0, 40));
                    //密文数据和签名数据
                    bjSpecdata.setCipheraction(crmd.substring(40, crmd.length()));
                    bjSpecdata.setApdudata("FFFFFFFF");
                } else {
                    bjSpecdata = new BJSpecdata();
                    ReslutDataSpecial bjR = new ReslutDataSpecial();
                    List<ReslutData> uDataLis = new ArrayList<ReslutData>();
                    ReslutData uData = new ReslutData();
                    bjSpecdata.setApdudata(tac);
                    //动作集数量
                    int count = Integer.parseInt(crmd.substring(10, 12));
                    int course = 12;
                    for (int i = 0; i < count; i++) {
//                        //交易类型
//                        uData.setTxntype(crmd.substring(course,course+2));
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
                    //密文数据和签名数据
                    bjSpecdata.setCipheraction(crmd.substring(course, crmd.length() - 16));
                    //应收金额
                    bjCrdSysOrderDTO.setReceivableAmt(crmd.substring(crmd.length() - 16, crmd.length() - 8));
                    //商户折扣
                    bjCrdSysOrderDTO.setMerdiscount(crmd.substring(crmd.length() - 4, crmd.length()));

                    //应收金额
                    uData.setAmtreceivable(crmd.substring(crmd.length() - 16, crmd.length() - 8));
                    String discountFlag = crmd.substring(crmd.length() - 4, crmd.length());
                    if("0100".equals(discountFlag)){
                        //用户折扣
                        uData.setUserdiscount("1000");
                        //商户折扣
                        uData.setSettldiscount("1000");
                    }else{
                        //用户折扣
                        uData.setUserdiscount(DataTranUtil.dropFirst0(crmd.substring(crmd.length() - 8, crmd.length() - 4)));
                        //商户折扣
                        uData.setSettldiscount(DataTranUtil.dropFirst0(crmd.substring(crmd.length() - 4, crmd.length())));
                    }

                    uDataLis.add(uData);
                    bjR.setFilerecords(uDataLis);
                    bjSpecdata.setOfflinedata(bjR);
                }
                //特殊域放到dto里
                bjCrdSysOrderDTO.setSpecdata(bjSpecdata);
            }
            //保留字段
            String rev = msg.substring(crmdEnd, msg.length());
            bjCrdSysOrderDTO.setReserved(rev);
            if (MerdeviceConstants.QUERY_RECHARGE.equals(rev)) {
                bjCrdSysOrderDTO.setTxntype("0");
            } else {
                bjCrdSysOrderDTO.setTxntype("2");
            }
            //钱包充值
            bjCrdSysOrderDTO.setChargetype(MerdeviceConstants.CHARGE_TYPE);
            //数据来源
            bjCrdSysOrderDTO.setSource(SourceEnum.MERMACHINE.getCode());
            //通讯流水号
            bjCrdSysOrderDTO.setPostransseq(DataTranUtil.dropFirst0(rev));
            //开始标志 只有申请充值密钥的时候需要
            bjCrdSysOrderDTO.setTradestartflag("1");
            bjCrdSysOrderDTO.setTradeendflag("0");
            //调用产品库签到接口，传输对象BJCrdSysOrderDTO
            DodopalResponse<BJCrdSysOrderDTO> returnDto = tcpServerDelegate.V71Message320101(bjCrdSysOrderDTO);
            log.info("=======320101接口调用外围系统返回===========" + returnDto.getCode());
            if (!MerdeviceConstants.SUCCESS.equals(returnDto.getCode())) {
                returnMessage = MerdeviceConstants.SIGN_OUT_RESULT +
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
            String returnData = returncrdm.getNextstep() + returncrdm.getCipheraction();
            String crdmIsused = "";
            String crdmLen = "";
            if (returnData != null && !"".equals(returnData)) {
                crdmIsused = "10";
                //计算特殊域长度
                crdmLen = String.format("%0" + 4 + "d", returnData.length());
            } else {
                crdmIsused = "00";
                crdmLen = "0000";
            }
            //对商户号进行前边补0操作
            String returnMercode = DataTranUtil.leftAdd0(returnBjCrdSysOrderDto.getMercode(), 20);
            //对版本号进行前边补0
            String returnVer = DataTranUtil.leftAdd0(returnBjCrdSysOrderDto.getVer(), 2);
            returnMessage = MerdeviceConstants.SIGN_OUT_RESULT +
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
                    //交易状态
                    bjCrdSysOrderDTO.getTxnstat() +
                    //商户类型(pos发送直接返回)
                    mertype +
                    //商户号
                    returnMercode +
                    //银行编号
                    bankId +
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
                    //交易前余额
                    DataTranUtil.leftAdd0(returnBjCrdSysOrderDto.getBefbal(), 8) +
                    //交易后金额
                    DataTranUtil.leftAdd0(returnBjCrdSysOrderDto.getBlackamt(), 8) +
                    //卡计数器
                    DataTranUtil.leftAdd0(bjCrdSysOrderDTO.getCardcounter(), 4) +
                    //tac
                    tac +
                    //mac2
                    mac2 +
                    //特殊域
                    returnData.trim() +
//        //交易记录
//        returnBjCrdSysOrderDto.getTxnrecode()+
                    //保留字段
                    DataTranUtil.leftAdd0(returnBjCrdSysOrderDto.getReserved(), 10);
            newStirng = String.format("%0" + 4 + "d", returnMessage.length() + 4);
        } catch (DDPException ex) {
            log.error("320101DDPException===============" + ex.getCode());
            ex.printStackTrace();
            returnMessage = MerdeviceConstants.SIGN_OUT_RESULT +
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
            log.error("320101Exception===============" + ex.getMessage());
            ex.printStackTrace();
            returnMessage = MerdeviceConstants.SIGN_OUT_RESULT +
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
        log.info("==============message320101耗费时间=====================" + (System.currentTimeMillis() - startTime));
        return newStirng + returnMessage;
    }
}
