/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.merdevice.web.controller;

import com.dodopal.api.card.dto.ReslutData;
import com.dodopal.api.card.dto.ReslutDataParameter;
import com.dodopal.api.card.dto.ReslutDataSpecial;
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
 * 签退结果上传报文处理
 */
@Controller
public class MessageHandler3201 {
    private static Logger log = Logger.getLogger(MessageHandler3201.class);
    @Autowired
    TcpServerDelegate tcpServerDelegate;

    public String message3201(String msg) {
        long startTime = System.currentTimeMillis();
        log.info("==============message3201开始时间=====================" + startTime);
        //首先解析msg 对msg进行透析
        ReslutDataParameter reslutDataParameter = new ReslutDataParameter();
        String toReturn = "";
        String newStirng = "";
        String reVer = "";
        String reDate = "";
        String reCityCode = "";
        try {
            //消息类型
            reslutDataParameter.setMessagetype(msg.substring(0, 4));
            //版本号
            reVer = msg.substring(4, 6);
            reslutDataParameter.setVer(reVer);
            //发送时间
            reDate = msg.substring(6, 20);
            reslutDataParameter.setSysdatetime(reDate);
            //特殊域启用标志
            reslutDataParameter.setIstsused(msg.substring(20, 22));
            //特殊域长度
            String cmdLen = msg.substring(22, 26);
            //应答码
            reslutDataParameter.setRespcode(msg.substring(26, 32));
            //城市代码
            String recitycode = msg.substring(32, 36);
            reslutDataParameter.setCitycode(recitycode);
            //交易状态
            reslutDataParameter.setTxnstat(msg.substring(36, 37));
            //商户类型
            reslutDataParameter.setMertype(msg.substring(37, 39));
            //商户号39开始，要后15位
            reslutDataParameter.setMercode(msg.substring(44, 59));
            //银行编号
            String rebankid = msg.substring(59, 67);
            reslutDataParameter.setBankid(rebankid);
            //用户编号
            String usercode = msg.substring(67, 77);
            reslutDataParameter.setUserid(usercode);
            //设备类型
            reslutDataParameter.setPostype(msg.substring(77, 79));
            //设备编号
            reslutDataParameter.setPosid(msg.substring(79, 91));
            //设备流水号
            reslutDataParameter.setPosseq(msg.substring(91, 100));
            //操作员号
            String operId = msg.substring(100, 116);
            reslutDataParameter.setUserid(operId);
            reslutDataParameter.setOperid(operId);
            //卡物理类型
            reslutDataParameter.setCardphytype(msg.substring(116, 118));
            //系统订单号
            String sysOrder = msg.substring(118, 138);
            reslutDataParameter.setPrdordernum(sysOrder);
            reslutDataParameter.setPrdordernum(sysOrder);
            //支付订单号
            String payOrder = msg.substring(138, 202);
            reslutDataParameter.setPayorder(payOrder);
            //卡号
            reslutDataParameter.setCardno(msg.substring(202, 222).trim());
            //卡面号
            reslutDataParameter.setCardfaceno(msg.substring(222, 242).trim());
            //交易日期
            reslutDataParameter.setTxndate(msg.substring(242, 250));
            //交易时间
            reslutDataParameter.setTxntime(msg.substring(250, 256));
            //交易金额
            reslutDataParameter.setTxnamt(msg.substring(256, 264));
            //交易前余额
            String beforeBalance = DataTranUtil.dropFirst0(msg.substring(264, 272));
            reslutDataParameter.setBefbal(beforeBalance == null || "".equals(beforeBalance) ? "0" : beforeBalance);
            //交易后金额
            String afterBalance = DataTranUtil.dropFirst0(msg.substring(272, 280));
            reslutDataParameter.setAftbal(afterBalance == null || "".equals(afterBalance) ? "0" : afterBalance);
            //卡计数器
            reslutDataParameter.setCardcnt(msg.substring(280, 288));
            //TAC
            reslutDataParameter.setTac(msg.substring(288, 296));
            //MAC2
            reslutDataParameter.setMac2(msg.substring(296, 304));
            //获取特殊域
            //计算特殊域的长度
            int len = Integer.parseInt(cmdLen);
            int end = 304 + len;
            //截取特殊域的内容
            String crdm = msg.substring(304, end);
            //对特殊域内容就行拆解
            ReslutDataSpecial returnReslutDataSpecial = new ReslutDataSpecial();
            //总记录数
            returnReslutDataSpecial.setTotalrecnum(crdm.substring(0, 4));
            //本次记录数
            String recordLenStr = crdm.substring(4, 8);
            returnReslutDataSpecial.setRecnum(recordLenStr);
            //剩余记录数
            returnReslutDataSpecial.setLeftnum(crdm.substring(8, 12));
            //获取n条记录数据
            String NRecord = crdm.substring(12, crdm.length());
            //将每一条记录解析出来
            int NRecordLen = NRecord.length();
            int subCount = Integer.parseInt(recordLenStr); //NRecordLen / 234;
            List<ReslutData> reslutDatasList = new ArrayList<ReslutData>();
            for (int i = 0; i < subCount; i++) {
                String NRecordSingle = NRecord.substring(i * 234, (i + 1) * 234);
                ReslutData reslutDataSingle = new ReslutData();
                //交易类型
                reslutDataSingle.setTxntype(NRecordSingle.substring(0, 2));
                //存储在sam卡中的编号
                reslutDataSingle.setSam(NRecordSingle.substring(2, 14));
                //交易金额
                reslutDataSingle.setTxnamt(NRecordSingle.substring(14, 22));
                //交易顺序号
                reslutDataSingle.setTxnseq(NRecordSingle.substring(22, 30));
                //交易后一卡通卡内余额
                reslutDataSingle.setAftbal(NRecordSingle.substring(30, 38));
                //交易日期
                reslutDataSingle.setTxndate(NRecordSingle.substring(38, 46));
                //交易时间
                reslutDataSingle.setTxntime(NRecordSingle.substring(46, 52));
                //卡序列号，一卡通内部序列号CSN,没有CSN时写FF
                reslutDataSingle.setCardseq(NRecordSingle.substring(52, 60));
                //卡计数器，一卡通卡中累计交易计数
                reslutDataSingle.setCardcnt(NRecordSingle.substring(60, 64));
                //卡号
                reslutDataSingle.setCardno(NRecordSingle.substring(64, 80));
                //有SAM对相关数据项计算得出的交易验证码
                reslutDataSingle.setTac(NRecordSingle.substring(80, 88));
                //交易前余额，一卡通交易前卡内余额
                reslutDataSingle.setBefbal(NRecordSingle.substring(88, 96));
                //卡逻辑类型
                reslutDataSingle.setCardtype(NRecordSingle.substring(96, 98));
                //卡物理类型
                reslutDataSingle.setCardphytype(NRecordSingle.substring(98, 100));
                //记录序号，在数据包中的顺序编号
                reslutDataSingle.setRecno(NRecordSingle.substring(100, 108));
                //锁卡时0x00标识黑名单列表内的卡锁卡，0x01因卡本身异常锁卡，消费时填写0x00
                reslutDataSingle.setTxnstat(NRecordSingle.substring(108, 110));
                //密钥及算法标识
                reslutDataSingle.setAlgorithm(NRecordSingle.substring(110, 112));
                //钱包交易序号
                reslutDataSingle.setBaltxnseq(NRecordSingle.substring(112, 116));
                //终端交易序号，针对CPU用户卡，PSAM产生，M1卡填写0X00000000
                reslutDataSingle.setTermtxnseq(NRecordSingle.substring(116, 124));
//            //个性化信息
                reslutDataSingle.setPlivatemsg(NRecordSingle.substring(124, 218));
                //应收金额
                reslutDataSingle.setRecvamount(NRecordSingle.substring(124, 132));
                //商户代码
                reslutDataSingle.setUnitid(NRecordSingle.substring(132, 144));
                //设备代码,没有时填0
                reslutDataSingle.setDevid(NRecordSingle.substring(144, 184));
                //批次号，没有时填0
                reslutDataSingle.setBatchid(NRecordSingle.substring(184, 192));
                //操作员号，没有时填0
                reslutDataSingle.setOperid(NRecordSingle.substring(192, 198));
                //
                reslutDataSingle.setResv(NRecordSingle.substring(198, 218));
                //应收金额
                reslutDataSingle.setAmtreceivable(NRecordSingle.substring(218, 226));
                if(!"0A".equals(reslutDataSingle.getTxntype().toUpperCase())){
                	//结算折扣 从230开始，第一位是折扣的标识
                    String discountFlag = NRecordSingle.substring(230, 231);
                    if ("1".equals(discountFlag)) {
                        //用户折扣
                        reslutDataSingle.setUserdiscount(DataTranUtil.dropFirst0(NRecordSingle.substring(226, 230)));
                        reslutDataSingle.setSettldiscount(DataTranUtil.dropFirst0(NRecordSingle.substring(231, 234)));
                    }
                    if ("0".equals(discountFlag)) {
                        //用户折扣
                        reslutDataSingle.setUserdiscount(String.valueOf(Integer.parseInt(DataTranUtil.dropFirst0(NRecordSingle.substring(226, 230))) * 10));
                        reslutDataSingle.setSettldiscount(String.valueOf(Integer.parseInt(NRecordSingle.substring(231, 234)) * 10));
                    }
                }                
                reslutDatasList.add(reslutDataSingle);
            }
            returnReslutDataSpecial.setFilerecords(reslutDatasList);
            reslutDataParameter.setCrdm(returnReslutDataSpecial);
            reslutDataParameter.setReserved(msg.substring(end, end + 10));
            //调用产品库接口，对脱机消费结果进行处理
            DodopalResponse<ReslutDataParameter> returnParameter = tcpServerDelegate.V71Message3201(reslutDataParameter);
            log.info("=======3201接口调用外围系统返回===========" + returnParameter.getCode());
            if (!MerdeviceConstants.SUCCESS.equals(returnParameter.getCode())) {
                toReturn = MerdeviceConstants.SIGN_OUT_RESULT +
                        //版本号
                        reVer +
                        //发送时间
                        reDate +
                        //特殊域启用该标志
                        "00" +
                        //特殊域长度
                        "0000" +
                        //应答码
                        returnParameter.getCode() +
                        //城市代码
                        recitycode;
                //2报文长度获取，以便封装返回时使用：
                //3对报文进行
                newStirng = String.format("%0" + 4 + "d", toReturn.length() + 4);
                return newStirng + toReturn;
            }
            ReslutDataParameter returnData = returnParameter.getResponseEntity();
//        ReslutDataSpecial returnSpecialModel = returnData.getCrdm();
//        String crdmReturnMsg = String.format("%0" + 4 + "d", returnSpecialModel.getTotalrecnum()) + String.format("%0" + 4 + "d", returnSpecialModel.getRecnum()) +
//                String.format("%0" + 4 + "d", returnSpecialModel.getLeftnum());
//
//        String recordDetail = "";
//        //脱机消费文件
//        List<ReslutData> fileRecords = returnSpecialModel.getFilerecords();
//        ReslutData record = null;
//        for (int i = 0; i < fileRecords.size(); i++) {
//            record = fileRecords.get(i);
//            recordDetail = recordDetail + record.toString();
//        }
            String crdmIsused = "00";
            String crdmLen = "0000";
//        if (returnSpecialModel != null) {
//            crdmIsused = "10";
//            //计算特殊域长度
//            crdmLen = String.format("%0" + 4 + "d", (crdmReturnMsg + recordDetail).length());
//        } else {
//            crdmIsused = "00";
//            crdmLen = "0000";
//        }
//        //计算特殊域参数记录总长度
//        crdmReturnMsg = crdmReturnMsg + recordDetail;
            toReturn = MerdeviceConstants.SIGN_OUT_RESULT +
                    //版本号
                    DataTranUtil.leftAdd0(returnData.getVer(), 2) +
                    //发送时间
                    returnData.getSysdatetime() +
                    //特殊域启用该标志
                    crdmIsused +
                    //特殊域长度
                    crdmLen +
                    //应答码
                    returnData.getRespcode() +
                    //城市代码
                    returnData.getCitycode() +
                    //交易状态
                    returnData.getTxnstat() +
                    //商户类型
                    DataTranUtil.leftAdd0(returnData.getMertype(), 2) +
                    //商户号
                    DataTranUtil.leftAdd0(returnData.getMercode(), 20) +
                    //银行编号
                    rebankid +
                    //用户编号
                    usercode +
                    //设备类型
                    DataTranUtil.leftAdd0(returnData.getPostype(), 2) +
                    //设备编号
                    DataTranUtil.leftAdd0(returnData.getPosid(), 12) +
                    //设备流水号
                    DataTranUtil.leftAdd0(returnData.getPosseq(), 9) +
                    //操作员号(pos发送直接返回)
                    operId +
                    //卡物理类型
                    DataTranUtil.leftAdd0(returnData.getCardphytype(), 2) +
                    //系统订单号
                    DataTranUtil.leftAdd0(returnData.getPrdordernum(), 20) +
                    //支付订单号(pos发送直接返回)
                    payOrder +
                    //卡号
                    String.format("%-20s", returnData.getCardno()) +
                    //卡面号
                    String.format("%-20s", returnData.getCardfaceno()) +
                    //交易日期
                    returnData.getTxndate() +
                    //交易时间
                    returnData.getTxntime() +
                    //交易金额
                    DataTranUtil.leftAdd0(returnData.getTxnamt(), 8) +
                    //交易前余额
                    DataTranUtil.leftAdd0(returnData.getBefbal(), 8) +
                    //交易后余额
                    DataTranUtil.leftAdd0(returnData.getAftbal(), 8) +
                    //卡计数器
                    DataTranUtil.leftAdd0(returnData.getCardcnt(), 4) +
                    //TAC
                    returnData.getTac() +
                    //MAC2
                    returnData.getMac2() +

//        //特殊域
//                crdmReturnMsg +
                    //保留字段
                    DataTranUtil.leftAdd0(returnData.getReserved(), 10);
            //2报文长度获取，以便封装返回时使用：
            //3对报文进行
            newStirng = String.format("%0" + 4 + "d", toReturn.length() + 4);
        } catch (DDPException ex) {
            log.error("3201DDPException===============" + ex.getCode());
            ex.printStackTrace();
            toReturn = MerdeviceConstants.SIGN_OUT_RESULT +
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
            newStirng = String.format("%0" + 4 + "d", toReturn.length() + 4);
            return newStirng + toReturn;
        } catch (Exception ex) {
            log.error("3201Exception===============" + ex.getMessage());
            ex.printStackTrace();
            toReturn = MerdeviceConstants.SIGN_OUT_RESULT +
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
            newStirng = String.format("%0" + 4 + "d", toReturn.length() + 4);
            return newStirng + toReturn;
        }
        log.info("==============message3201耗费时间=====================" + (System.currentTimeMillis() - startTime));
        return newStirng + toReturn;
    }
    
    public static void main(String[] args) {
		String ss = "320104201604201822411002460000001110002000000000000000000000000555500999999990230222003883900000211199998090001900010100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000002016042018224100001000000037320000273200000703FFFFFFFFFFFFFFFF0001000100000A999980900019000000007F520000940E0000201604201808220FB3798BBF0210007510911006560C8E64DD940E00000102010000000001000000000000000000000032500000013030303030303030333032323230303338383339A1040000000001FFFFFFFFFFFFFFFFFFFF00000000000000000000001982";
	
		MessageHandler3201 message3201 = new MessageHandler3201();
		message3201.message3201(ss);
    }
}
