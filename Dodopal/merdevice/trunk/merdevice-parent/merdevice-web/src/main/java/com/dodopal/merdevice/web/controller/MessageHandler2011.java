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

/**
 * Created by lenovo on 2016/1/20.
 * 交易-充值
 */
@Controller
public class MessageHandler2011 {
    private static Logger log = Logger.getLogger(MessageHandler2011.class);
    @Autowired
    TcpServerDelegate tcpServerDelegate;

    public String message2011(String msg) {
        long startTime = System.currentTimeMillis();
        log.info("==============message2011开始时间====================="+startTime);
        //首先解析msg 对msg进行透析
        BJCrdSysOrderDTO bjCrdSysOrderDTO = new BJCrdSysOrderDTO();
        String returnMessage = "";
        String newStirng = "";
        String reVer="";
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
            //商户签名算法
            String mchAlgo = msg.substring(58, 60); //bjCrdSysOrderDTO.
            //待签数据长度
            int contextLen = Integer.parseInt(msg.substring(60, 64)); //bjCrdSysOrderDTO.
            int contextLenEnd = 64 + contextLen;
            //待签数据
            String context = msg.substring(64, contextLenEnd);
            //签名值长度
            int signLen = Integer.parseInt(msg.substring(contextLenEnd, contextLenEnd + 4));
            //签名值
            String sign = msg.substring(contextLenEnd + 4, contextLenEnd + 4 + signLen);
            //银行编号
            String bankId = msg.substring(contextLenEnd + 4 + signLen, contextLenEnd + 4 + signLen + 8);
            //用户编号
            String usercode = msg.substring(contextLenEnd + signLen + 12, contextLenEnd + signLen + 22);
            bjCrdSysOrderDTO.setUsercode(usercode);
            //设备类型
            bjCrdSysOrderDTO.setPostype(msg.substring(contextLenEnd + signLen + 22, contextLenEnd + signLen + 24));
            //设备编号
            bjCrdSysOrderDTO.setPosid(msg.substring(contextLenEnd + signLen + 24, contextLenEnd + signLen + 36));
            //设备流水号
            bjCrdSysOrderDTO.setPosseq(msg.substring(contextLenEnd + signLen + 36, contextLenEnd + signLen + 45));
            //操作员号
            String operId = msg.substring(contextLenEnd + signLen + 45, contextLenEnd + signLen + 61);
            bjCrdSysOrderDTO.setUserid(operId);
            //psam 卡号
            bjCrdSysOrderDTO.setSamno(operId.substring(0, 12));
            //系统订单号
            String sysOrder = msg.substring(contextLenEnd + signLen + 61, contextLenEnd + signLen + 81);
            //支付订单号
            String payOrder = msg.substring(contextLenEnd + signLen + 81, contextLenEnd + signLen + 145);
            bjCrdSysOrderDTO.setMerordercode(payOrder);
            //卡物理类型
            bjCrdSysOrderDTO.setCardtype(msg.substring(contextLenEnd + signLen + 145, contextLenEnd + signLen + 147));
            //卡号
            bjCrdSysOrderDTO.setCardinnerno(msg.substring(contextLenEnd + signLen + 147, contextLenEnd + signLen + 167).trim());
            //卡面号
            bjCrdSysOrderDTO.setCardfaceno(msg.substring(contextLenEnd + signLen + 167, contextLenEnd + signLen + 187).trim());
            bjCrdSysOrderDTO.setTradecard(msg.substring(contextLenEnd + signLen + 167, contextLenEnd + signLen + 187).trim());
            //交易日期
            bjCrdSysOrderDTO.setTxndate(msg.substring(contextLenEnd + signLen + 187, contextLenEnd + signLen + 195));
            //交易时间
            bjCrdSysOrderDTO.setTxntime(msg.substring(contextLenEnd + signLen + 195, contextLenEnd + signLen + 201));
            //交易金额
            String amount = DataTranUtil.dropFirst0(msg.substring(contextLenEnd + signLen + 201, contextLenEnd + signLen + 209))==null||"".equals(DataTranUtil.dropFirst0(msg.substring(contextLenEnd + signLen + 201, contextLenEnd + signLen + 209)))?"0":DataTranUtil.dropFirst0(msg.substring(contextLenEnd + signLen + 201, contextLenEnd + signLen + 209));
            bjCrdSysOrderDTO.setTxnamt(amount);
            String testAmount = msg.substring(contextLenEnd + signLen + 209, contextLenEnd + signLen + 217);
            if(testAmount.contains("-")){
                //卡余额
                String balance = DataTranUtil.dropFirst0(testAmount.replace("-", "0"));
                bjCrdSysOrderDTO.setBefbal(balance==null || "".equals(balance)?"0":"-"+balance);
            }else{
                //卡余额
                String balance = DataTranUtil.dropFirst0(testAmount);
                bjCrdSysOrderDTO.setBefbal(balance == null || "".equals(balance)?"0":balance);
            }

            //特殊域
            String crmd = msg.substring(contextLenEnd + signLen + 217, contextLenEnd + signLen + 217 + cmdLen);
            //保留字段
            String rev = msg.substring(contextLenEnd + signLen + 217 + cmdLen,contextLenEnd + signLen + 217 + cmdLen+10);
            bjCrdSysOrderDTO.setReserved(rev);
            BJSpecdata bjSpecdata = null;
            if (MerdeviceConstants.QUERY_RECHARGE.equals(rev)) {
                bjCrdSysOrderDTO.setTxntype("0");
                bjSpecdata = new BJSpecdata();
                String[] dtoTran = new String[10];
                for (int i = 0; i < 10; i++) {
                    dtoTran[i] = crmd.substring(i * 50, (i + 1) * 50);
                }
                bjCrdSysOrderDTO.setTxnrecode(dtoTran);
            } else {
                bjCrdSysOrderDTO.setTxntype("1");
                bjSpecdata = new BJSpecdata();
                //动作集数量
                int count = Integer.parseInt(crmd.substring(10, 12));
                int course = 12;
    //        String plainaction = "";
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
            }
            bjCrdSysOrderDTO.setSpecdata(bjSpecdata);
            //钱包充值
            bjCrdSysOrderDTO.setChargetype(MerdeviceConstants.CHARGE_TYPE);
            //数据来源
            bjCrdSysOrderDTO.setSource(SourceEnum.MERMACHINE.getCode());
            //通讯流水号
            bjCrdSysOrderDTO.setPostransseq("".equals(DataTranUtil.dropFirst0(rev))?"0":DataTranUtil.dropFirst0(rev));
            //调用产品库签到接口，传输对象BJCrdSysOrderDTO
            DodopalResponse<BJCrdSysOrderDTO> returnDto = tcpServerDelegate.V71Message2011(bjCrdSysOrderDTO);
            log.info("=======2011接口调用外围系统返回==========="+returnDto.getCode());
            if (!MerdeviceConstants.SUCCESS.equals(returnDto.getCode())) {
                returnMessage = MerdeviceConstants.CHECK_CARD +
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

            //商户签名值算法
            String returnmchAlgo = "00";
            //待签数据
            String returnContext = DataTranUtil.leftAdd0(returnBjCrdSysOrderDto.getMercode(),20) + DataTranUtil.leftAdd0(returnBjCrdSysOrderDto.getPrdordernum()==null||"".equals(returnBjCrdSysOrderDto.getPrdordernum())?"0":returnBjCrdSysOrderDto.getPrdordernum(),20) + payOrder + returnBjCrdSysOrderDto.getSysdatetime();
            //待签数据长度
            String returnContextLen = String.format("%0" + 4 + "d", returnContext.length());
            //签名值
            String returnSign = V71MD5.MD5Encode(returnContext + MerdeviceConstants.SIGN_KEY);
            //签名值长度
            String returnSignLen = String.format("%0" + 4 + "d", returnSign.length());
            String leftAmount=returnBjCrdSysOrderDto.getBefbal();
            if(leftAmount.contains("-")){
                String test = leftAmount.substring(leftAmount.indexOf("-"),leftAmount.indexOf("-")+1);
                String test1 = leftAmount.substring(leftAmount.indexOf("-") + 1, leftAmount.length());
                String out = DataTranUtil.leftAdd0(test1,7);
                leftAmount = test+out;
            }else{
                leftAmount = DataTranUtil.leftAdd0(leftAmount,8);
            }
            returnMessage = MerdeviceConstants.CHECK_CARD +
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
                    //商户签名算法
                    returnmchAlgo +
                    //待签数据长度
                    returnContextLen +
                    //待签数据
                    returnContext +
                    //签名值长度
                    returnSignLen +
                    //签名值
                    returnSign +
                    //银行编号
                    bankId +
                    //用户编号
                    usercode +
                    //设备类型
                    DataTranUtil.leftAdd0(returnBjCrdSysOrderDto.getPostype(), 2) +
                    //设备编号
                    returnBjCrdSysOrderDto.getPosid() +
                    //设备流水号
                    DataTranUtil.leftAdd0(returnBjCrdSysOrderDto.getPosseq(), 9) +
                    //操作员号(pos发送直接返回)
                    operId
                    +
                    //系统订单号
                    DataTranUtil.leftAdd0(returnBjCrdSysOrderDto.getPrdordernum()==null||"".equals(returnBjCrdSysOrderDto.getPrdordernum())?"0":returnBjCrdSysOrderDto.getPrdordernum(),20) +
                    //支付订单号(pos发送直接返回)
                    payOrder +
                    //卡物理类型
                    DataTranUtil.leftAdd0(returnBjCrdSysOrderDto.getCardtype(), 2) +
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
                    leftAmount +
    //        //特殊域
    //        returnBjCrdSysOrderDto.getSpecdata()+
    //        //交易记录
    //        returnBjCrdSysOrderDto.getTxnrecode()+
                    //保留字段
                    DataTranUtil.leftAdd0(returnBjCrdSysOrderDto.getReserved(), 10);
            newStirng = String.format("%0" + 4 + "d", returnMessage.length() + 4);
        } catch (DDPException ex) {
            log.error("2011DDPException==============="+ex.getCode());
            ex.printStackTrace();
            returnMessage = MerdeviceConstants.CHECK_CARD +
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
        }catch (Exception ex) {
            log.error("2011Exception==============="+ex.getMessage());
            ex.printStackTrace();
            returnMessage = MerdeviceConstants.CHECK_CARD +
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
        log.info("==============message2011耗费时间====================="+(System.currentTimeMillis()-startTime));
        return newStirng + returnMessage;
    }
}
