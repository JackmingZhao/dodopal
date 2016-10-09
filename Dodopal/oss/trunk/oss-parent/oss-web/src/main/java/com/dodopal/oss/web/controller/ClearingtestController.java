/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.oss.web.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.util.DateUtils;
import com.dodopal.oss.business.model.ClearingDataModel;
import com.dodopal.oss.business.model.PayTransaction;
import com.dodopal.oss.business.model.ProductOrder;
import com.dodopal.oss.business.model.ProfitCollect;
import com.dodopal.oss.business.model.Purchase;
import com.dodopal.oss.business.model.SysTimeThreshold;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.service.ClearingSettlementService;
import com.dodopal.oss.business.service.ClearingTaskService;
import com.dodopal.oss.business.service.ProfitService;
import com.dodopal.oss.business.service.SysTimeThresholdService;
import com.dodopal.oss.business.service.UserMgmtService;
import com.dodopal.oss.business.service.YktRepairOrderService;

/**
 * Created by lenovo on 2015/10/27.
 */
@Controller
public class ClearingtestController {
    private static Logger log = Logger.getLogger(ClearingtestController.class);
    @Autowired
    ClearingTaskService clearingTaskService;
    @Autowired
    SysTimeThresholdService sysTimeThresholdService;
    @Autowired
    private YktRepairOrderService yktRepairOrderService;
    @Autowired
    private ClearingSettlementService settlementService;
    @Autowired
    private UserMgmtService userMgmtService;
    @Autowired
    private ProfitService profitService;

    @RequestMapping("/clearing")
    public ModelAndView payment(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("clearingtest");
        return mv;
    }

    @RequestMapping("/YKTRechargeClearingData")
    public void YKTRechargeClearingData(HttpServletRequest request) {
        log.info("===========一卡通充值清分程序启动==================");
        //查询应该清分的数据
        List<ProductOrder> clearingData = clearingTaskService.queryYKTRchargeClearingData();
        if (clearingData.size() == 0) {
            log.info("===========一卡通充值没有需要清分的数据===========");
            return;
        }
        for (ProductOrder po : clearingData) {
            log.info("=====一卡通充值清分的订单号=======" + po.getProOrderNum());
            try {
                clearingTaskService.addYKTRechargeClearingData(po);
            }
            catch (DDPException e) {
                e.printStackTrace();
                log.info("=======DDPEXCEPTION=========" + e.getMessage());
                continue;
            }
            catch (SQLException e) {
                e.printStackTrace();
                log.info("=======一卡通充值添加清分数据到清分基础表出错=========" + e.getMessage());
                continue;
            }
            catch (Exception e) {
                e.printStackTrace();
                log.info("=======一卡通充值清分异常=========" + e.getMessage());
                continue;
            }
        }

        log.info("===========一卡通充值清分程序结束==================");
    }

    @RequestMapping("/accountRechargeClearingData")
    public void accountRechargeClearingData() {
        log.info("===========账户充值清分程序启动==================");
        //查询应该清分的数据
        List<PayTransaction> clearingData = clearingTaskService.queryAccountRechargeClearingData();
        if (clearingData.size() == 0) {
            log.info("===========账户充值没有需要清分的数据===========");
            return;
        }
        for (PayTransaction pt : clearingData) {
            log.info("=====账户充值清分的交易流水号=======" + pt.getTranCode());
            try {
                clearingTaskService.addAccountRechargeClearingData(pt);
            }
            catch (DDPException e) {
                e.printStackTrace();
                log.info("=======DDPEXCEPTION=========" + e.getMessage());
                continue;
            }
            catch (SQLException e) {
                e.printStackTrace();
                log.info("=======账户充值添加清分数据到清分基础表出错=========" + e.getMessage());
                continue;
            }
            catch (Exception e) {
                e.printStackTrace();
                log.info("=======一卡通充值清分异常=========" + e.getMessage());
                continue;
            }
        }

        log.info("===========账户充值清分程序结束==================");
    }

    @RequestMapping("/YKTPurchaseClearingData")
    public void YKTPurchaseClearingData() {
        log.info("===========一卡通收单清分程序启动==================");
        //查询应该清分的数据
        List<Purchase> clearingData = clearingTaskService.queryYKTPurchaseClearingData();
        if (clearingData.size() == 0) {
            log.info("===========一卡通收单没有需要清分的数据===========");
            return;
        }

        for (Purchase purchase : clearingData) {
            log.info("=====一卡通收单清分的订单号=======" + purchase.getOrderNum());
            try {
                clearingTaskService.addYKTPurchaseClearingData(purchase);
            }
            catch (DDPException e) {
                e.printStackTrace();
                log.info("=======DDPEXCEPTION=========" + e.getMessage());
                continue;
            }
            catch (SQLException e) {
                e.printStackTrace();
                log.info("=======一卡通收单添加清分数据到清分基础表出错=========" + e.getMessage());
                continue;
            }
            catch (Exception e) {
                e.printStackTrace();
                log.info("=======一卡通充值清分异常=========" + e.getMessage());
                continue;
            }
        }
        log.info("===========一卡通收单清分程序结束==================");
    }

    @RequestMapping("/settement")
    public void settementClearingData(HttpServletRequest request) {
        log.info("=========================================================================[清算]START=========================================================");
        try {
            settlementService.distributionData();
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        log.info("=========================================================================[清算]END===========================================================");
    }

    @RequestMapping("/profit")
    public void profit() {
        log.info("=============分润跑批程序启动==============");
        List<ClearingDataModel> getProfitData = profitService.queryProfitData();
        if (getProfitData.size() == 0) {
            log.info("=============没有需要分润的数据==============");
            return;
        }
        for (ClearingDataModel profitInfo : getProfitData) {
            try {
                profitService.profit(profitInfo);
            }
            catch (DDPException e) {
                log.info("========DDPException==========" + e.getMessage());
                e.printStackTrace();
                continue;
            }
            catch (SQLException e) {
                log.info("=======分润程序操作数据库出错=========" + e.getMessage());
                e.printStackTrace();
                continue;
            }
            catch (Exception e) {
                log.info("=======分润程序出错=========" + e.getMessage());
                e.printStackTrace();
                continue;
            }
        }

        log.info("=============分润跑批程序结束==============");
    }

    @RequestMapping("/profitCollect")
    public void profitCollect() {
        log.info("=============分润汇总跑批程序启动==============");
        List<ProfitCollect> profitCollect = settlementService.queryProfitCollect();
        if (profitCollect.size() == 0) {
            log.info("=============没有需要分润汇总数据==============");
            return;
        }
        settlementService.deleteProfitCollect();
        for (ProfitCollect profitInfo : profitCollect) {
            try {
                settlementService.profitCollect(profitInfo);
            }
            catch (DDPException e) {
                log.info("========DDPException==========" + e.getMessage());
                e.printStackTrace();
                continue;
            }
            catch (SQLException e) {
                log.info("=======分润汇总操作数据库出错=========" + e.getMessage());
                e.printStackTrace();
                continue;
            }
            catch (Exception e) {
                log.info("=======分润汇总程序出错=========" + e.getMessage());
                e.printStackTrace();
                continue;
            }
        }

        log.info("=============分润汇总跑批程序结束==============");
    }

    @RequestMapping("/repairOrder")
    public void repairOrder(HttpServletRequest request) {
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
