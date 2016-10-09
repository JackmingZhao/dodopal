/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.merdevice.web.controller;

import com.dodopal.api.card.dto.*;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.merdevice.business.constant.MerdeviceConstants;
import com.dodopal.merdevice.business.util.DataTranUtil;
import com.dodopal.merdevice.delegate.TcpServerDelegate;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.RootLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by lenovo on 2016/1/20.
 * 参数下载报文处理
 */
@Controller
public class MessageHandler5101 {
    @Autowired
    TcpServerDelegate tcpServerDelegate;
    private static Logger log = Logger.getLogger(MessageHandler5101.class);

    public String message5101(String msg) throws UnsupportedEncodingException {
        long startTime = System.currentTimeMillis();
        log.info("==============message5101开始时间=====================" + startTime);
        //首先解析msg 对msg进行透析
        ParameterList parameterList = new ParameterList();
        String toReturn = "";
        String newStirng = "";
        String reVer = "";
        String reDate = "";
        String reCityCode = "";
        try {
            //消息类型
            parameterList.setMessagetype(msg.substring(0, 4));
            //版本号
            reVer = msg.substring(4, 6);
            parameterList.setVer(reVer);
            //发送时间
            reDate = msg.substring(6, 20);
            parameterList.setSysdatetime(reDate);
            //特殊域启用标志
            parameterList.setIstsused(msg.substring(20, 22));
            //特殊域长度
            String cmdLen = msg.substring(22, 26);
            //应答码
            parameterList.setRespcode(msg.substring(26, 32));
            //城市代码
            reCityCode = msg.substring(32, 36);
            parameterList.setCitycode(reCityCode);
            //商户类型
            parameterList.setMertype(msg.substring(36, 38));
            //商户号38开始，要后15位
            parameterList.setMercode(msg.substring(43, 58));
            //设备类型
            parameterList.setPostype(msg.substring(58, 60));
            //设备编号
            parameterList.setPosid(msg.substring(60, 72));
            //操作员号
            String reoperId = msg.substring(72, 88);
            parameterList.setOperid(reoperId);
            //PSAM号
            parameterList.setSamno(msg.substring(88, 100));
            //结算日期
            parameterList.setSettdate(msg.substring(100, 108));
            //系统时间
            parameterList.setDatetime(msg.substring(108, 122));
            //下载标志
            parameterList.setDownflag(msg.substring(122, 123));
            //计算特殊域的长度
            int len = Integer.parseInt(cmdLen);
            //计算特殊域的结束位置
            int end = 123 + len;
            //对特殊域内容进行拆解
            String crdm = msg.substring(123, end);

            SpecialModel specialModel = new SpecialModel();
            //下载参数编号
            specialModel.setParno(crdm.substring(0, 2));
            //请求记录数 一次下载的记录数
            specialModel.setReqtotal(crdm.substring(2, 6));
            //记录下载开始索引
            String reqIndex = crdm.substring(6, 10);
            specialModel.setReqindex(reqIndex);
        /*//该类参数总记录条数
        specialModel.setTotal(crdm.substring(10, 14));
        //剩余记录条数
        specialModel.setSurplus(crdm.substring(14, 18));
        //本次发送记录数
        specialModel.setSize(crdm.substring(18, 22));*/
            //特殊域
            parameterList.setCrdm(specialModel);
            parameterList.setReserved(msg.substring(end, msg.length()));
            //调用产品库下载参数列表，传输对象parameterList
            DodopalResponse<ParameterList> returnParameterList = tcpServerDelegate.paraDownload(parameterList);
            log.info("=======5101接口调用外围系统返回===========" + returnParameterList.getCode());
            if (!MerdeviceConstants.SUCCESS.equals(returnParameterList.getCode())) {
                toReturn = MerdeviceConstants.PARAMETER_DOWNLOAD + reVer + reDate +
                        "00" + "0000" + returnParameterList.getCode() + reCityCode;
                //2报文长度获取，以便封装返回时使用：
                //3对报文进行
                newStirng = String.format("%0" + 4 + "d", toReturn.length()+4);
                return newStirng + toReturn;
            }
            ParameterList returnPara = returnParameterList.getResponseEntity();
            //拆解特殊域的内容
            SpecialModel returnSpecialModel = returnPara.getCrdm();
            String crdmReturnMsg = String.format("%0" + 2 + "d", Integer.parseInt(returnSpecialModel.getParno() == null || "".equals(returnSpecialModel.getParno()) ? "0" : returnSpecialModel.getParno())) +
                    String.format("%0" + 4 + "d", Integer.parseInt(returnSpecialModel.getReqtotal() == null || "".equals(returnSpecialModel.getReqtotal()) ? "0" : returnSpecialModel.getReqtotal())) +
                    String.format("%0" + 4 + "d", Integer.parseInt(reqIndex)) +
                    String.format("%0" + 4 + "d", Integer.parseInt(returnSpecialModel.getTotal() == null || "".equals(returnSpecialModel.getTotal()) ? "0" : returnSpecialModel.getTotal())) +
                    String.format("%0" + 4 + "d", Integer.parseInt(returnSpecialModel.getSurplus() == null || "".equals(returnSpecialModel.getSurplus()) ? "0" : returnSpecialModel.getSurplus())) +
                    String.format("%0" + 4 + "d", Integer.parseInt(returnSpecialModel.getSize() == null || "".equals(returnSpecialModel.getSize()) ? "0" : returnSpecialModel.getSize()));
            log.info("====参数编号2,一次记录数4,索引4,总记录4,剩余记录4,本次记录4=====" + crdmReturnMsg);
            List returnListPar = returnSpecialModel.getListPars();
            String recordList = "";
            if (returnListPar.size() > 0) {
                //01黑名单
                if (MerdeviceConstants.BLACKLIST.equals(specialModel.getParno())) {
                    String cardNo = "";
                    BlankListParameter returnpara = null;
                    for (int i = 0; i < returnListPar.size(); i++) {
                        cardNo = "";
                        returnpara = (BlankListParameter) returnListPar.get(i);
                        if (returnpara.getCardcode() != null && !"".equals(returnpara.getCardcode())) {
                            //对商户号进行前边补0操作
                            cardNo = DataTranUtil.leftAdd0(returnpara.getCardcode(), 16);
                        }
                        recordList = recordList + cardNo;
                    }
                }
                //02消费可用卡类型
                if (MerdeviceConstants.ConsumeCardTypeParameter.equals(specialModel.getParno())) {
                    //卡物理类型
                    String cardphyType = "";
                    //卡逻辑类型
                    String cardtype = "";
                    //卡物理类型名称
                    String cardtypename = "";
                    //卡片属性
                    String cardsproperty = "";
                    //联机消费额度处理方式
                    String fhlimitmna = "";
                    //保留
                    String resv = "";
                    ConsumeCardTypeParameter returnpara = null;
                    for (int i = 0; i < returnListPar.size(); i++) {
                        //卡物理类型
                        cardphyType = "";
                        //卡逻辑类型
                        cardtype = "";
                        //卡物理类型名称
                        cardtypename = "";
                        //卡片属性
                        cardsproperty = "";
                        //联机消费额度处理方式
                        fhlimitmna = "";
                        //保留
                        resv = "";
                        returnpara = (ConsumeCardTypeParameter) returnListPar.get(i);
                        if (returnpara.getCardphytype() != null && !"".equals(returnpara.getCardphytype())) {
                            //卡物理类型前边补0操作
                            cardphyType = DataTranUtil.leftAdd0(returnpara.getCardphytype(), 2);
                        }
                        if (returnpara.getCardtype() != null && !"".equals(returnpara.getCardtype())) {
                            //卡逻辑类型前边补0操作
                            cardtype = DataTranUtil.leftAdd0(returnpara.getCardtype(), 2);
                        }
                        if (returnpara.getCardtypename() != null && !"".equals(returnpara.getCardtypename())) {
                            //对卡物理类型名称前边补0操作
                            cardtypename = DataTranUtil.rightAddSpace(returnpara.getCardtypename(), 16);
                            //也可以直接用String占位符去做
//                        int hanziLen = returnpara.getCardtypename().getBytes().length;
//                        cardtypename = String.format("%-"+(16-hanziLen+returnpara.getCardtypename().length())+"s", returnpara.getCardtypename());
                        }
                        if (returnpara.getCardproperty() != null && !"".equals(returnpara.getCardproperty())) {
                            //对卡片属性前边补0操作
                            cardsproperty = DataTranUtil.leftAdd0(returnpara.getCardproperty(), 2);
                        }
                        if (returnpara.getFhlimitmana() != null && !"".equals(returnpara.getFhlimitmana())) {
                            //对联机消费额度处理方式前边补0操作
                            fhlimitmna = DataTranUtil.leftAdd0(returnpara.getFhlimitmana(), 2);
                        }
                        resv = returnpara.getResv();
                        if (returnpara.getResv() != null && !"".equals(returnpara.getResv())) {
                            //对保留字段进行前边补0操作
                            resv = DataTranUtil.leftAdd0(returnpara.getResv(), 24);
                        }
                        recordList = recordList + cardphyType + cardtype + cardtypename + cardsproperty + fhlimitmna + resv;
                    }
                }
                //03终端运营参数
                if (MerdeviceConstants.TerminalParameter.equals(specialModel.getParno())) {
                    //分账单位代码
                    String unitid = "";
                    //发送单位代码
                    String sendunitid = "";
                    //接收方单位代码
                    String receunitid = "";
                    //代理商简码
                    String mchntcode = "";
                    //商户代码
                    String mchntid = "";
                    //商户名称
                    String mchntname = "";
                    //网点名称
                    String netname = "";
                    //终端代码
                    String posid = "";
                    //卡片联机消费交易临界值
                    String fhtxncritical = "";
                    //用户密钥处理方式
                    String keytype = "";
                    //数据上传模式
                    String dataupmode = "";
                    //定时上传时间
                    String timingupmode = "";
                    //退款方式
                    String refundtype = "";
                    TerminalParameter returnpara = null;
                    for (int i = 0; i < returnListPar.size(); i++) {
                        //分账单位代码
                        unitid = "";
                        //发送单位代码
                        sendunitid = "";
                        //接收方单位代码
                        receunitid = "";
                        //代理商简码
                        mchntcode = "";
                        //商户代码
                        mchntid = "";
                        //商户名称
                        mchntname = "";
                        //网点名称
                        netname = "";
                        //终端代码
                        posid = "";
                        //卡片联机消费交易临界值
                        fhtxncritical = "";
                        //用户密钥处理方式
                        keytype = "";
                        //数据上传模式
                        dataupmode = "";
                        //定时上传时间
                        timingupmode = "";
                        //退款方式
                        refundtype = "";
                        returnpara = (TerminalParameter) returnListPar.get(i);
                        if (returnpara.getUnitid() != null && !"".equals(returnpara.getUnitid())) {
                            //分账单位代码前边补0操作
                            unitid = DataTranUtil.leftAdd0(returnpara.getUnitid(), 8);
                        }
                        if (returnpara.getSendunitid() != null && !"".equals(returnpara.getSendunitid())) {
                            //发送方单位代码前边补0操作
                            sendunitid = DataTranUtil.leftAdd0(returnpara.getSendunitid(), 8);
                        }
                        if (returnpara.getReceunitid() != null && !"".equals(returnpara.getReceunitid())) {
                            //接收方单位代码前边补0操作
                            receunitid = DataTranUtil.leftAdd0(returnpara.getReceunitid(), 8);
                        }
                        if (returnpara.getMchntcode() != null && !"".equals(returnpara.getMchntcode())) {
                            //代理商简码前边补0操作
                            mchntcode = DataTranUtil.leftAdd0(returnpara.getMchntcode(), 2);
                        }
                        if (returnpara.getMchntid() != null && !"".equals(returnpara.getMchntid())) {
                            //商户代码前边补0操作
                            mchntid = DataTranUtil.leftAdd0(returnpara.getMchntid(), 12);
                        }
                        if (returnpara.getMchntname() != null && !"".equals(returnpara.getMchntname())) {
                            //商户名称前边补0操作
                            mchntname = DataTranUtil.rightAddSpace(returnpara.getMchntname(), 40);
                            //也可以用String 占位符去实现
//                        int hanziLen = returnpara.getMchntname().getBytes().length;
//                        mchntname = String.format("%-" + (40 - hanziLen + returnpara.getMchntname().length()) + "s", returnpara.getMchntname());
                        }

                        if (returnpara.getNetname() != null && !"".equals(returnpara.getNetname())) {
                            //网点名称前边补0操作
                            //商户名称前边补0操作
                            netname = DataTranUtil.rightAddSpace(returnpara.getNetname(), 40);
                            //也可以用String 占位符去实现
//                        int hanziLen = returnpara.getNetname().getBytes().length;
//                        netname = String.format("%-" + (40 - hanziLen + returnpara.getNetname().length()) + "s", returnpara.getNetname());
                        }
                        if (returnpara.getPosid() != null && !"".equals(returnpara.getPosid())) {
                            //终端代码前边补0操作
                            posid = DataTranUtil.leftAdd0(returnpara.getPosid(), 8);
                        }
                        if (returnpara.getFhtxncritical() != null && !"".equals(returnpara.getFhtxncritical())) {
                            //卡片联机消费交易临界值前边补0操作
                            fhtxncritical = DataTranUtil.leftAdd0(returnpara.getFhtxncritical(), 8);
                        }
                        keytype = returnpara.getKeytype();
                        if (keytype != null && !"".equals(keytype)) {
                            //用户密钥处理方式进行前边补0操作
                            keytype = DataTranUtil.leftAdd0(returnpara.getKeytype(), 2);
                        }
                        if (returnpara.getDataupmode() != null && !"".equals(returnpara.getDataupmode())) {
                            //数据上传模式前边补0操作
                            dataupmode = DataTranUtil.leftAdd0(returnpara.getDataupmode(), 2);
                        }
                        if (returnpara.getTimingupmode() != null && !"".equals(returnpara.getTimingupmode())) {
                            //定时上传时间前边补0操作
                            timingupmode = DataTranUtil.leftAdd0(returnpara.getTimingupmode(), 4);
                        }
                        if (returnpara.getRefundtype() != null && !"".equals(returnpara.getRefundtype())) {
                            //退款方式前边补0操作
                            refundtype = DataTranUtil.leftAdd0(returnpara.getRefundtype(), 2);
                        }
                        recordList = recordList + unitid + sendunitid + receunitid + mchntcode + mchntid + mchntname +
                                netname + posid + fhtxncritical + keytype + dataupmode + timingupmode + refundtype;
                    }
                }

                //04区域黑名单参数
                if (MerdeviceConstants.AreaBlankListParameter.equals(specialModel.getParno())) {
                    //开始卡号
                    String startcardno = "";
                    //结束卡号
                    String endcardno = "";
                    AreaBlankListParameter returnpara = null;
                    for (int i = 0; i < returnListPar.size(); i++) {
                        //开始卡号
                        startcardno = "";
                        //结束卡号
                        endcardno = "";
                        returnpara = (AreaBlankListParameter) returnListPar.get(i);
                        if (returnpara.getStartcardno() != null && !"".equals(returnpara.getStartcardno())) {
                            //开始卡号前边补0操作
                            startcardno = DataTranUtil.leftAdd0(returnpara.getStartcardno(), 16);
                        }
                        if (returnpara.getEndcardno() != null && !"".equals(returnpara.getEndcardno())) {
                            //结束卡号前边补0操作
                            endcardno = DataTranUtil.leftAdd0(returnpara.getEndcardno(), 16);
                        }
                        recordList = recordList + startcardno + endcardno;
                    }
                }
                //05增量黑名单参数
                if (MerdeviceConstants.IncrementBlankListParameter.equals(specialModel.getParno())) {
                    //卡号
                    String cardno = "";
                    //黑名单标志
                    String blankType = "";
                    IncrementBlankListParameter returnpara = null;
                    for (int i = 0; i < returnListPar.size(); i++) {
                        //卡号
                        cardno = "";
                        //黑名单标志
                        blankType = "";
                        returnpara = (IncrementBlankListParameter) returnListPar.get(i);
                        if (returnpara.getCardno() != null && !"".equals(returnpara.getCardno())) {
                            //卡号前边补0操作
                            cardno = DataTranUtil.leftAdd0(returnpara.getCardno(), 16);
                        }
                        if (returnpara.getBlankType() != null && !"".equals(returnpara.getBlankType())) {
                            //黑名单标志前边补0操作
                            blankType = DataTranUtil.leftAdd0(returnpara.getBlankType(), 2);
                        }
                        recordList = recordList + cardno + blankType;
                    }
                }
                //06终端菜单参数
                if (MerdeviceConstants.TerminalMenuParameter.equals(specialModel.getParno())) {
                    //菜单级别
                    String menulevel = "";
                    //菜单名称
                    String menuname = "";
                    //交易类型
                    String txntype = "";
                    //交易状态
                    String txnstatus = "";
                    //菜单手臂动作集集合长度
                    String menufirstlistsize = "";
                    //菜单首笔动作集
                    String menufristactionset = "";
                    TerminalMenuParameter returnpara = null;
                    for (int i = 0; i < returnListPar.size(); i++) {
                        //菜单级别
                        menulevel = "";
                        //菜单名称
                        menuname = "";
                        //交易类型
                        txntype = "";
                        //交易状态
                        txnstatus = "";
                        //菜单手臂动作集集合长度
                        menufirstlistsize = "";
                        //菜单首笔动作集
                        menufristactionset = "";
                        returnpara = (TerminalMenuParameter) returnListPar.get(i);
                        if (returnpara.getMenulevel() != null && !"".equals(returnpara.getMenulevel())) {
                            //菜单级别前边补0操作
                            menulevel = DataTranUtil.rightAddSpace(returnpara.getMenulevel(), 6);
                        } else {
                            menulevel = DataTranUtil.rightAddSpace("", 6);
                        }
                        if (returnpara.getMenuname() != null && !"".equals(returnpara.getMenuname())) {
                            //菜单名称前边补0操作
                            menuname = DataTranUtil.rightAddSpace(returnpara.getMenuname(), 12);
                            //也可以用String 占位符实现
//                        int hanziLen = returnpara.getMenuname().getBytes().length;
//                        menuname = String.format("%-" + (12 - hanziLen + returnpara.getMenuname().length()) + "s", returnpara.getMenuname());
                        }
                        if (returnpara.getTxntype() != null && !"".equals(returnpara.getTxntype())) {
                            //交易类型
                            txntype = DataTranUtil.leftAdd0(returnpara.getTxntype(), 4);
                        }
                        if (returnpara.getTxnstatus() != null && !"".equals(returnpara.getTxnstatus())) {
                            //交易状态前边补0操作
                            txnstatus = DataTranUtil.leftAdd0(returnpara.getTxnstatus(), 4);
                        }
                        if (returnpara.getMenufristactionset() != null && !"".equals(returnpara.getMenufristactionset())) {
                            //首笔动作集集合前边补0操作
                            menufirstlistsize = String.format("%0" + 4 + "d", returnpara.getMenufristactionset().length());
//                        menufirstlistsize = DataTranUtil.leftAdd0(returnpara.getMenufristactionset(), 4);
                        }
                        if (returnpara.getMenufristactionset() != null && !"".equals(returnpara.getMenufristactionset())) {
                            //首笔动作集合
                            menufristactionset = returnpara.getMenufristactionset();
                        }
                        recordList = recordList + menulevel + menuname + txntype + txnstatus + menufirstlistsize + menufristactionset;
                    }
                }
                //07灰名单参数
                if (MerdeviceConstants.GrayListParameter.equals(specialModel.getParno())) {
                    //灰名单标识
                    String graylistflag = "";
                    //灰名单数值
                    String graylistval = "";
                    GrayListParameter returnpara = null;
                    for (int i = 0; i < returnListPar.size(); i++) {
                        //灰名单标识
                        graylistflag = "";
                        //灰名单数值
                        graylistval = "";
                        returnpara = (GrayListParameter) returnListPar.get(i);
                        if (returnpara.getGraylistflag() != null && !"".equals(returnpara.getGraylistflag())) {
                            //灰名单标识前边补0操作
                            graylistflag = DataTranUtil.leftAdd0(returnpara.getGraylistflag(), 2);
                        }
                        if (returnpara.getGraylistval() != null && !"".equals(returnpara.getGraylistval())) {
                            //灰名单数值前边补0操作
                            graylistval = DataTranUtil.leftAdd0(returnpara.getGraylistval(), 8);
                        }
                        recordList = recordList + graylistflag + graylistval;
                    }
                }
                //33消费折扣参数
                if (MerdeviceConstants.ConsumeDiscountParameter.equals(specialModel.getParno())) {
                    //用户折扣
                    String trandiscount = "";
                    //结算折扣
                    String settdiscount = "";
                    ConsumeDiscountParameter returnpara = null;
                    for (int i = 0; i < returnListPar.size(); i++) {
                        //用户折扣
                        trandiscount = "";
                        //结算折扣
                        settdiscount = "";
                        returnpara = (ConsumeDiscountParameter) returnListPar.get(i);
                        if (returnpara.getTrandiscount() != null && !"".equals(returnpara.getTrandiscount())) {
                            //用户折扣前边补0操作
                            trandiscount = DataTranUtil.leftAdd0(returnpara.getTrandiscount(), 4);
                        }
                        if (returnpara.getSettdiscount() != null && !"".equals(returnpara.getSettdiscount())) {
                            //结算折扣前边补0操作
                            settdiscount = DataTranUtil.leftAdd0(returnpara.getSettdiscount(), 4);
                        }
                        recordList = recordList + trandiscount + settdiscount;
                    }
                }
                //34分时段消费折扣参数
                if (MerdeviceConstants.SubPeriodDiscountParameter.equals(specialModel.getParno())) {
                    //折扣日期
                    String discountdate = "";
                    //折扣开始时间
                    String begintime = "";
                    //折扣结束时间
                    String endtime = "";
                    //用户折扣
                    String trandiscount = "";
                    //结算折扣
                    String settdiscount = "";
                    SubPeriodDiscountParameter returnpara = null;
                    for (int i = 0; i < returnListPar.size(); i++) {
                        //折扣日期
                        discountdate = "";
                        //折扣开始时间
                        begintime = "";
                        //折扣结束时间
                        endtime = "";
                        //用户折扣
                        trandiscount = "";
                        //结算折扣
                        settdiscount = "";
                        returnpara = (SubPeriodDiscountParameter) returnListPar.get(i);
                        if (returnpara.getDiscountdate() != null && !"".equals(returnpara.getDiscountdate())) {
                            //折扣日期前边补0操作
                            discountdate = DataTranUtil.leftAdd0(returnpara.getDiscountdate(), 8);
                        }
                        if (returnpara.getBegintime() != null && !"".equals(returnpara.getBegintime())) {
                            //折扣开始时间前边补0操作
                            begintime = DataTranUtil.leftAdd0(returnpara.getBegintime(), 4);
                        }
                        if (returnpara.getEndtime() != null && !"".equals(returnpara.getEndtime())) {
                            //折扣结束时间前边补0操作
                            endtime = DataTranUtil.leftAdd0(returnpara.getEndtime(), 4);
                        }
                        if (returnpara.getTrandiscount() != null && !"".equals(returnpara.getTrandiscount())) {
                            //用户折扣前边补0操作
                            trandiscount = DataTranUtil.leftAdd0(returnpara.getTrandiscount(), 3);
                        }
                        if (returnpara.getSettdiscount() != null && !"".equals(returnpara.getSettdiscount())) {
                            //结算折扣前边补0操作
                            settdiscount = DataTranUtil.leftAdd0(returnpara.getSettdiscount(), 3);
                        }
                        recordList = recordList + discountdate + begintime + endtime + trandiscount + settdiscount;
                    }
                }
            } else {

            }
            //特殊域启用标志
            String crdmIsused = "";
            String crdmLen = "";
            String testrecordList = URLDecoder.decode(recordList, "gbk");
            String recordListLen = String.format("%0" + 4 + "d", testrecordList.getBytes("gbk").length);
            //特殊域长度
            crdmReturnMsg = crdmReturnMsg + recordListLen + recordList;
            if (crdmReturnMsg != null && !"".equals(crdmReturnMsg)) {
                crdmIsused = "10";
                String testcrdmReturnMsg = URLDecoder.decode(crdmReturnMsg, "gbk");
                //计算特殊域长度
                crdmLen = String.format("%0" + 4 + "d", testcrdmReturnMsg.getBytes("gbk").length);
            } else {
                crdmIsused = "00";
                crdmLen = "0000";
            }
            //计算特殊域参数记录总长度

            toReturn = MerdeviceConstants.PARAMETER_DOWNLOAD +
                    //版本号
                    DataTranUtil.leftAdd0(parameterList.getVer(), 2) +
                    //发送时间
                    parameterList.getSysdatetime() +
                    //特殊域启用标志和特殊域长度
                    crdmIsused + crdmLen +
                    //返回码
                    parameterList.getRespcode() +
                    //城市代码
                    parameterList.getCitycode() +
                    //商户类型
                    DataTranUtil.leftAdd0(parameterList.getMertype(), 2) +
                    //商户号
                    DataTranUtil.leftAdd0(parameterList.getMercode(), 20) +
                    //设备类型
                    DataTranUtil.leftAdd0(parameterList.getPostype(), 2) +
                    //设备编号
                    DataTranUtil.leftAdd0(parameterList.getPosid(), 12) +
                    //操作员id
                    reoperId +
                    //sam号
                    parameterList.getSamno() +
                    //结算日期
                    parameterList.getSettdate() +
                    //系统时间
                    parameterList.getDatetime() +
                    //下载标志
                    parameterList.getDownflag() +
                    //特殊域
                    crdmReturnMsg +
                    //保留字段
                    DataTranUtil.leftAdd0(parameterList.getReserved(), 10);
            //2报文长度获取，以便封装返回时使用：
            //3对报文进行
            String testtoReturn = URLDecoder.decode(toReturn, "gbk");
            int newStringlen = testtoReturn.getBytes("gbk").length;
            newStirng = String.format("%0" + 4 + "d", newStringlen + 4);
        } catch (DDPException ex) {
            log.error("5101DDPException===============" + ex.getCode());
            ex.printStackTrace();
            toReturn = MerdeviceConstants.PARAMETER_DOWNLOAD +
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
            log.error("5101Exception===============" + ex.getMessage());
            ex.printStackTrace();
            toReturn = MerdeviceConstants.PARAMETER_DOWNLOAD +
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
        log.info("==============message5101耗费时间=====================" + (System.currentTimeMillis() - startTime));
        return newStirng + toReturn;

    }
}
