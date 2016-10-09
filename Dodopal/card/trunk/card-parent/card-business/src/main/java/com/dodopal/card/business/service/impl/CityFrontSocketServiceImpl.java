package com.dodopal.card.business.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.card.business.log.LogHelper;
import com.dodopal.card.business.log.SysLogHelper;
import com.dodopal.card.business.model.dto.CityFrontOrderDTO;
import com.dodopal.card.business.service.CityFrontSocketService;
import com.dodopal.card.business.socket.SocketOperator;
import com.dodopal.card.business.util.DateUtil;
import com.dodopal.common.constant.ResponseCode;

@Service
public class CityFrontSocketServiceImpl implements CityFrontSocketService {

    @Autowired
    private LogHelper logHelper;

    @Autowired
    private SysLogHelper sysLogHelper;

    /**
     * 通过socket完成向城市前置json格式的报文发送
     * @param ip
     * @param port
     * @param crdDTO
     * @return
     */
    @Override
    public CrdSysOrderDTO sendCityFrontSocket(String ip, int port, CrdSysOrderDTO crdDTO, int txnType) {
        //日志方法开始时间
        Long tradestart = Long.valueOf(DateUtil.getCurrSdTime());
        //日志响应码描述
        StringBuffer respexplain = new StringBuffer();
        //日志响应码
        String logRespcode = ResponseCode.SUCCESS;

        //        String statckTrace = "";

        //城市前置传输对象
        CityFrontOrderDTO cityFrontOrderDTO = new CityFrontOrderDTO();
        BeanUtils.copyProperties(crdDTO, cityFrontOrderDTO);

        //发送到城市前置json
        String jsonStr = "";
        //城市前置返回json
        String retJsonStr = "";
        CrdSysOrderDTO retCrdDTO = new CrdSysOrderDTO();
        try {
            //封装
            jsonStr = JSONObject.toJSONString(cityFrontOrderDTO);
            //发送socket
            Long socketStart = Long.valueOf(DateUtil.getCurrSdTime());
            //防止超时无返回的情况，在发送socke之前记录一条日志
            logHelper.recordLogFun(jsonStr, tradestart, logRespcode, respexplain.toString(), "向城市前置发送socket(前)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), retJsonStr, txnType);
            retJsonStr = new SocketOperator().AsyncClient(jsonStr, ip, port);
            Long socketEnd = Long.valueOf(DateUtil.getCurrSdTime());
            //解析
            CityFrontOrderDTO retCityDTO = JSONObject.parseObject(retJsonStr, CityFrontOrderDTO.class);
            if (!ResponseCode.SUCCESS.equals(retCityDTO.getRespcode())) {
                logRespcode = retCityDTO.getRespcode();
                respexplain.append("[与城市前置交互成功,返回交易应答码异常],");
            }
            //把产品库传输的对象数据回填到城市前置返回的对象中
            retCrdDTO = backfillData(retCityDTO, crdDTO);
            respexplain.append("[socket交互时长:" + (socketEnd - socketStart) + "ms]");
        }
        catch (Exception e) {
            //调用城市前置失败
            logRespcode = ResponseCode.CARD_SOCKET_CONNECT_ERROR;
            BeanUtils.copyProperties(crdDTO, retCrdDTO);
            retCrdDTO.setRespcode(ResponseCode.CARD_SOCKET_CONNECT_ERROR);
            respexplain.append(e.getMessage());
            //            statckTrace = e.getMessage();
        }
        finally {
            //记录日志
            logHelper.recordLogFun(jsonStr, tradestart, logRespcode, respexplain.toString(), "向城市前置发送socket(后)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), retJsonStr, txnType);
        }
        //        sysLogHelper.recordLogFun(jsonStr, tradestart, logRespcode, respexplain.toString(), "向城市前置发送socket(后)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), retJsonStr, crdDTO.getSource(), statckTrace);
        return retCrdDTO;
    }

    private CrdSysOrderDTO backfillData(CityFrontOrderDTO retCityDTO, CrdSysOrderDTO crdDTO) {
        //消息类型
        crdDTO.setMessagetype(retCityDTO.getMessagetype());

        //版本号
        crdDTO.setVer(retCityDTO.getVer());

        //前端发送时间格式如下 YYYYMMDDhhmmss
        crdDTO.setSysdatetime(retCityDTO.getSysdatetime());
        //交易应答码
        crdDTO.setRespcode(retCityDTO.getRespcode());

        //一卡通代码
        crdDTO.setYktcode(retCityDTO.getYktcode());

        //城市代码
        crdDTO.setCitycode(retCityDTO.getCitycode());

        //卡物理类型
        crdDTO.setCardtype(retCityDTO.getCardtype());

        //卡号(验卡返回 充值和结果回传)
        crdDTO.setCardinnerno(retCityDTO.getCardinnerno());

        //卡面号(验卡返回 充值和结果回传)
        crdDTO.setCardfaceno(retCityDTO.getCardfaceno());

        //0-查询
        crdDTO.setTxntype(retCityDTO.getTxntype());

        //0-交易透传 1-交易结束
        crdDTO.setTradeendflag(retCityDTO.getTradeendflag());

        //0-交易透传 1-交易开始
        crdDTO.setTradestartflag(retCityDTO.getTradestartflag());

        //设备类型 0：NFC手机；1：V61 V60；2：V70
        crdDTO.setPostype(retCityDTO.getPostype());

        //充值类型 0:钱包;1:月票
        crdDTO.setChargetype(retCityDTO.getChargetype());

        //设备编号
        crdDTO.setPosid(retCityDTO.getPosid());

        //设备流水号
        crdDTO.setPosseq(retCityDTO.getPosseq());

        //交易流水号(充值和结果回传上来)
        crdDTO.setTxnseq(retCityDTO.getTxnseq());

        //用户编号
        crdDTO.setUsercode(retCityDTO.getUsercode());

        //卡服务订单号
        crdDTO.setCrdordernum(retCityDTO.getCrdordernum());

        //交易日期(后台返回一笔交易此字段保持一致)
        crdDTO.setTxndate(retCityDTO.getTxndate());

        //交易时间(后台返回一笔交易此字段保持一致)
        crdDTO.setTxntime(retCityDTO.getTxntime());

        //交易金额(查询时不需要传)
        crdDTO.setTxnamt(retCityDTO.getTxnamt());

        //交易前卡余额(查询时不需要传)
        crdDTO.setBefbal(retCityDTO.getBefbal());

        //交易后卡余额
        crdDTO.setBlackamt(retCityDTO.getBlackamt());

        //卡内允许最大金额
        crdDTO.setCardlimit(retCityDTO.getCardlimit());

        //安全认证码
        crdDTO.setSecmac(retCityDTO.getSecmac());

        //卡计数器由前置圈存指令请求返回
        crdDTO.setCardcounter(retCityDTO.getCardcounter());

        //结果上传交易状态0：成功 1：失败 2：未知
        crdDTO.setTxnstat(retCityDTO.getTxnstat());

        //特殊域
        crdDTO.setSpecdata(retCityDTO.getSpecdata());

        //交易记录域JSON数组
        crdDTO.setTxnrecode(retCityDTO.getTxnrecode());

        //金融IC卡文件数据JSON数组
        crdDTO.setBankfile(retCityDTO.getBankfile());

        //APDU指令域JSON数组
        crdDTO.setApdu(retCityDTO.getApdu());

        //上传后台原路返回(返存前端)
        crdDTO.setRetdata(retCityDTO.getRetdata());

        //保留字段
        crdDTO.setReserved(retCityDTO.getReserved());

        // 一卡通中心联机交易流水号(昆山特有)
        crdDTO.setYkttxnseq(retCityDTO.getYkttxnseq());

        return crdDTO;
    }
}
