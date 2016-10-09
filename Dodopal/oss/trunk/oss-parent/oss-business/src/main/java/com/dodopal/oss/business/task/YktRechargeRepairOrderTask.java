package com.dodopal.oss.business.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.util.DateUtils;
import com.dodopal.oss.business.model.ProductOrder;
import com.dodopal.oss.business.model.SysTimeThreshold;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.service.SysTimeThresholdService;
import com.dodopal.oss.business.service.UserMgmtService;
import com.dodopal.oss.business.service.YktRepairOrderService;

/**
 * 产品库补单批处理
 * @author lenovo
 */
public class YktRechargeRepairOrderTask {
    @Autowired
    private YktRepairOrderService yktRepairOrderService;
    @Autowired
    private UserMgmtService userMgmtService;
    @Autowired
    SysTimeThresholdService sysTimeThresholdService;

    private final static Logger log = LoggerFactory.getLogger(YktRechargeRepairOrderTask.class);

    public void repairPrdOrder() {
        Long startTime = Long.valueOf(DateUtils.getCurrSdTime());
        log.info("=============[一卡通充值-补单]==扫描程序==START===============");
        try {
            //1.根据 code = '01'(一卡通充值),取时间阀值
            SysTimeThreshold sysTimeThreshold = sysTimeThresholdService.findSysTimeThresholdByCode(RateCodeEnum.YKT_RECHARGE.getCode());
            if (sysTimeThreshold == null) {
                log.error("===========[一卡通充值-补单]未发现Threshold！！！============");
            } else {
                log.info("===========[一卡通充值-补单]Threshold 为 " + sysTimeThreshold.getThreshold() + "==============");
                //查询userid
                User user = userMgmtService.findUserByLoginName("admin");
                //2.1查询支付异常订单
                List<ProductOrder> payList = yktRepairOrderService.queryPayExp(sysTimeThreshold.getThreshold());
                if (payList.size() > 0) {
                    log.info("共扫描到需要补单的[支付异常]订单记录  " + payList.size() + "条。");
                    //处理支付异常数据
                    yktRepairOrderService.repairPayExp(payList, user.getId());
                } else {
                    log.info("未发现需要补单的[支付异常]订单记录 ！");
                }

                //2.2查询资金解冻订单
                List<ProductOrder> fundList = yktRepairOrderService.queryDeblockFund(sysTimeThreshold.getThreshold());
                if (fundList.size() > 0) {
                    log.info("共扫描到需要补单的[资金解冻]订单记录  " + fundList.size() + "条。");
                    //处理资金解冻数据
                    yktRepairOrderService.repairDeblockFund(fundList, user.getId());
                } else {
                    log.info("未发现需要补单的[资金解冻]订单记录 ！");
                }

                //2.3查询资金扣款
                List<ProductOrder> deList = yktRepairOrderService.queryDeduct(sysTimeThreshold.getThreshold());
                if (deList.size() > 0) {
                    log.info("共扫描到需要补单的[资金扣款]订单记录  " + deList.size() + "条。");
                    //处理资金扣款数据
                    yktRepairOrderService.repairDeduct(deList, user.getId());
                } else {
                    log.info("未发现需要补单的[资金扣款]订单记录 ！");
                }
                Long endTime = Long.valueOf(DateUtils.getCurrSdTime());
                log.info("=============[一卡通充值-补单]==扫描程序==END====共执行" + (endTime - startTime) + "ms===============");
            }
        }
        catch (Exception e) {
            log.info("=======[一卡通充值-补单]==ERROR=========" + e.getMessage());
            e.printStackTrace();
        }
    }

}
