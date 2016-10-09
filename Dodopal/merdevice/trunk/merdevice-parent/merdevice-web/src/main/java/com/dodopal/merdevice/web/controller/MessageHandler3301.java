/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.merdevice.web.controller;

import com.dodopal.api.card.dto.BJDiscountDTO;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.merdevice.business.constant.MerdeviceConstants;
import com.dodopal.merdevice.business.util.DataTranUtil;
import com.dodopal.merdevice.delegate.TcpServerDelegate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by lenovo on 2016/5/12
 * 脱机消费优惠信息查询
 */
@Controller
public class MessageHandler3301 {
    private static Logger log = Logger.getLogger(MessageHandler3301.class);
    @Autowired
    TcpServerDelegate tcpServerDelegate;

    public String message3301(String msg) {
        long startTime = System.currentTimeMillis();
        log.info("==============message3301开始时间=====================" + startTime);
        //首先解析msg 对msg进行透析
        BJDiscountDTO bjDiscountDTO = new BJDiscountDTO();
        String returnMessage = "";
        String newStirng = "";
        String reVer = "";
        String reDate = "";
        String reCityCode = "";
        try {
            //消息类型
            bjDiscountDTO.setMessagetype(msg.substring(0, 4));
            //版本号
            reVer = msg.substring(4, 6);
            bjDiscountDTO.setVer(reVer);
            //发送时间
            reDate = msg.substring(6, 20);
            bjDiscountDTO.setSysdatetime(reDate);
            //特殊域启用该标志
            String isTSused = msg.substring(20, 22);
            //特殊域长度
            int cmdLen = Integer.parseInt(msg.substring(22, 26));
            //应答码
            bjDiscountDTO.setRespcode(msg.substring(26, 32));
            //城市代码
            reCityCode = msg.substring(32, 36);
            bjDiscountDTO.setCitycode(reCityCode);
            //商户类型
            String mertype = msg.substring(36, 38);
            bjDiscountDTO.setMertype(mertype);
            //商户号38开始，要后15位
            bjDiscountDTO.setMercode(msg.substring(43, 58));
            //设备类型
            bjDiscountDTO.setPostype(DataTranUtil.dropFirst0(msg.substring(58, 60)));
            //设备编号
            bjDiscountDTO.setPosid(msg.substring(60, 72));
            //操作员号
            String operId = msg.substring(72, 88);
            //操作员id
            bjDiscountDTO.setOperid(operId);
           //结算日期
            bjDiscountDTO.setSettdate(msg.substring(88, 96));
           //通讯流水号
            bjDiscountDTO.setComseq(msg.substring(96, 106));
            //终端ic交易流水号
            bjDiscountDTO.setIcseq(msg.substring(106, 116));
            //终端账户交易流水号
            bjDiscountDTO.setAccseq(msg.substring(116, 126));
            //批次号
            bjDiscountDTO.setBatchid(msg.substring(126, 136));
            //卡号
            bjDiscountDTO.setCardno(msg.substring(136, 152));
            //交易前金额
            bjDiscountDTO.setBefbal(msg.substring(152, 160));
            //交易金额
            bjDiscountDTO.setAmout(msg.substring(160, 168));
            //系统时间
            bjDiscountDTO.setDatetime(msg.substring(168,182));
            //特殊域结束位置
            int cmdEnd = 182 + cmdLen;
            //特殊域
            String crdm = msg.substring(182,cmdEnd);
            //特殊域
            bjDiscountDTO.setCrdm(crdm);
            //保留字段
            bjDiscountDTO.setReserved(msg.substring(cmdEnd,msg.length()));
            //调用产品库签到接口，传输对象BJCrdSysOrderDTO
            DodopalResponse<BJDiscountDTO> returnDto = tcpServerDelegate.discoutnQuery3301(bjDiscountDTO);
            log.info("=======3301接口调用外围系统返回===========" + returnDto.getCode());
            if (!MerdeviceConstants.SUCCESS.equals(returnDto.getCode())) {
                returnMessage = MerdeviceConstants.DISCOUNT_QUERY +
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
            BJDiscountDTO returnBJDiscountDTO = returnDto.getResponseEntity();
            String returncrdm = returnBJDiscountDTO.getCrdm()==null?"":returnBJDiscountDTO.getCrdm();
            String crdmIsused = "";
            String crdmLen = "";
            if (returncrdm != null && !"".equals(returncrdm)) {
                crdmIsused = "10";
                //计算特殊域长度
                crdmLen = String.format("%0" + 4 + "d",returncrdm.length());
            } else {
                crdmIsused = "00";
                crdmLen = "0000";
            }
            //对商户号进行前边补0操作
            String returnMercode = DataTranUtil.leftAdd0(returnBJDiscountDTO.getMercode(), 20);
            //对版本号进行前边补0
            String returnVer = DataTranUtil.leftAdd0(returnBJDiscountDTO.getVer(), 2);
            returnMessage = MerdeviceConstants.DISCOUNT_QUERY +
                    //版本号
                    returnVer +
                    //发送时间
                    returnBJDiscountDTO.getSysdatetime() +
                    //特殊域启用该标志
                    crdmIsused +
                    //特殊域长度
                    crdmLen +
                    //应答码
                    returnBJDiscountDTO.getRespcode() +
                    //城市代码
                    returnBJDiscountDTO.getCitycode() +
                    mertype +
                    //商户号
                    returnMercode +
                    //设备类型
                    DataTranUtil.leftAdd0(returnBJDiscountDTO.getPostype(), 2) +
                    //设备编号
                    DataTranUtil.leftAdd0(returnBJDiscountDTO.getPosid(), 12) +
                    //操作员号(pos发送直接返回)
                    operId +
                    //结算日期
                    returnBJDiscountDTO.getSettdate()+
                    //通讯流水号
                    returnBJDiscountDTO.getComseq()+
                    //终端ic交易流水号
                    returnBJDiscountDTO.getIcseq()+
                    //终端账户交易流水号
                    returnBJDiscountDTO.getAccseq()+
                    //批次号
                    returnBJDiscountDTO.getBatchid()+
                    //卡号
                    String.format("%-16s", returnBJDiscountDTO.getCardno()) +
                    //交易前金额
                    DataTranUtil.leftAdd0(returnBJDiscountDTO.getBefbal(), 8) +
                    //交易金额
                    DataTranUtil.leftAdd0(returnBJDiscountDTO.getAmout(), 8) +
                    //系统时间
                    returnBJDiscountDTO.getDatetime()+
                    //特殊域
                    returncrdm +
                    //保留字段
                    DataTranUtil.leftAdd0(returnBJDiscountDTO.getReserved(), 10);
            newStirng = String.format("%0" + 4 + "d", returnMessage.length() + 4);
        } catch (DDPException ex) {
            log.error("3301DDPException===============" + ex.getCode());
            ex.printStackTrace();
            returnMessage = MerdeviceConstants.DISCOUNT_QUERY +
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
            log.error("3301Exception===============" + ex.getMessage());
            ex.printStackTrace();
            returnMessage = MerdeviceConstants.DISCOUNT_QUERY +
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
        log.info("==============message3301耗费时间=====================" + (System.currentTimeMillis() - startTime));
        return newStirng + returnMessage;
    }
}
