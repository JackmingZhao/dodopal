package com.dodopal.running.business.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.log.ActivemqLogPublisher;
import com.dodopal.common.model.SysLog;
import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.running.business.dao.YktRepairMapper;
import com.dodopal.running.business.model.ProductOrder;
import com.dodopal.running.business.service.RechargeOrderExceptionHandleService;
import com.dodopal.running.business.service.YktRepairOrderService;

@Service
public class YktRepairOrderServiceImpl implements YktRepairOrderService {
    private final static Logger logger = LoggerFactory.getLogger(YktRepairOrderService.class);

    @Autowired
    private YktRepairMapper yktRepairMapper;

    @Autowired
    private RechargeOrderExceptionHandleService exceptionHandleService;

    /**
     * 查询支付异常订单
     * @return
     */
    @Override
    public List<ProductOrder> queryPayExp(int thresholdTime) {
        List<ProductOrder> list = yktRepairMapper.queryPayExp(thresholdTime);
        return list;
    }

    /**
     * 查询资金解冻订单
     * @return
     */
    @Override
    public List<ProductOrder> queryDeblockFund(int thresholdTime) {
        List<ProductOrder> list = yktRepairMapper.queryDeblockFund(thresholdTime);
        return list;
    }

    /**
     * 查询资金扣款
     * @return
     */
    @Override
    public List<ProductOrder> queryDeduct(int thresholdTime) {
        List<ProductOrder> list = yktRepairMapper.queryDeduct(thresholdTime);
        return list;
    }

    /**
     * 处理支付异常数据
     */
    @Override
    public void repairPayExp(List<ProductOrder> list, String userId) {
        String logServerName = DodopalAppVarPropsUtil.getStringProp("server.log.name");
        String logServerUrl = DodopalAppVarPropsUtil.getStringProp("server.log.url");
        for (ProductOrder productOrder : list) {
            SysLog sysLog = new SysLog();
            sysLog.setServerName(CommonConstants.SYSTEM_NAME_OSS);
            sysLog.setOrderNum(productOrder.getProOrderNum());
            sysLog.setClassName(this.getClass().getName());
            sysLog.setMethodName("repairPayExp");
            sysLog.setTradeType(0L);
            sysLog.setRespCode(ResponseCode.SUCCESS);
            sysLog.setInParas(JSONObject.toJSONString(productOrder));
            try {
                logger.info("[支付异常]开始处理：扫描到产品库订单号 = " + productOrder.getProOrderNum());
                //账户加值异常处理
                exceptionHandleService.accountRechargeHandle(productOrder, userId);
                logger.info("[支付异常]产品库订单号 = " + productOrder.getProOrderNum() + "处理结束");
            }
            catch (Exception e) {
                logger.info("=======[支付异常]处理出错=========" + e.getMessage());
                e.printStackTrace();
                sysLog.setRespCode(ResponseCode.SYSTEM_ERROR);
                sysLog.setTradeType(1L);
                sysLog.setStatckTrace(e.getStackTrace().toString());
                continue;
            }
            finally {
                //记录日志
                ActivemqLogPublisher.publishLog2Queue(sysLog, logServerName, logServerUrl);
            }
        }
    }

    /**
     * 处理资金解冻数据
     */
    @Override
    public void repairDeblockFund(List<ProductOrder> list, String userId) {
        String logServerName = DodopalAppVarPropsUtil.getStringProp("server.log.name");
        String logServerUrl = DodopalAppVarPropsUtil.getStringProp("server.log.url");
        for (ProductOrder productOrder : list) {
            SysLog sysLog = new SysLog();
            sysLog.setServerName(CommonConstants.SYSTEM_NAME_OSS);
            sysLog.setOrderNum(productOrder.getProOrderNum());
            sysLog.setClassName(this.getClass().getName());
            sysLog.setMethodName("repairDeblockFund");
            sysLog.setTradeType(0L);
            sysLog.setRespCode(ResponseCode.SUCCESS);
            sysLog.setInParas(JSONObject.toJSONString(productOrder));
            try {
                logger.info("[资金解冻]开始处理：扫描到产品库订单号 = " + productOrder.getProOrderNum());
                //资金解冻
                exceptionHandleService.accountUnfreezeHandle(productOrder, userId);
                logger.info("[资金解冻]产品库订单号 = " + productOrder.getProOrderNum() + "处理结束");
            }
            catch (Exception e) {
                logger.info("=======[资金解冻]处理出错=========" + e.getMessage());
                e.printStackTrace();
                sysLog.setRespCode(ResponseCode.SYSTEM_ERROR);
                sysLog.setTradeType(1L);
                sysLog.setStatckTrace(e.getStackTrace().toString());
                continue;
            }
            finally {
                //记录日志
                ActivemqLogPublisher.publishLog2Queue(sysLog, logServerName, logServerUrl);
            }
        }
    }

    /*
     * 处理资金扣款数据
     */
    @Override
    public void repairDeduct(List<ProductOrder> list, String userId) {
        String logServerName = DodopalAppVarPropsUtil.getStringProp("server.log.name");
        String logServerUrl = DodopalAppVarPropsUtil.getStringProp("server.log.url");
        for (ProductOrder productOrder : list) {
            SysLog sysLog = new SysLog();
            sysLog.setServerName(CommonConstants.SYSTEM_NAME_OSS);
            sysLog.setOrderNum(productOrder.getProOrderNum());
            sysLog.setClassName(this.getClass().getName());
            sysLog.setMethodName("repairDeduct");
            sysLog.setTradeType(0L);
            sysLog.setRespCode(ResponseCode.SUCCESS);
            sysLog.setInParas(JSONObject.toJSONString(productOrder));
            try {
                logger.info("[资金扣款]开始处理：扫描到产品库订单号 = " + productOrder.getProOrderNum());
                //资金扣款
                exceptionHandleService.accountDeductHandle(productOrder, userId);
                logger.info("[资金扣款]产品库订单号 = " + productOrder.getProOrderNum() + "处理结束");
            }
            catch (Exception e) {
                logger.info("=======[资金扣款]处理出错=========" + e.getMessage());
                e.printStackTrace();
                sysLog.setRespCode(ResponseCode.SYSTEM_ERROR);
                sysLog.setTradeType(1L);
                sysLog.setStatckTrace(e.getStackTrace().toString());
                continue;
            }
            finally {
                //记录日志
                ActivemqLogPublisher.publishLog2Queue(sysLog, logServerName, logServerUrl);
            }
        }
    }

}
