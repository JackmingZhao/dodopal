/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.oss.business.task;

import com.dodopal.common.exception.DDPException;
import com.dodopal.common.util.DDPLog;
import com.dodopal.oss.business.model.PayTransaction;
import com.dodopal.oss.business.model.ProductOrder;
import com.dodopal.oss.business.model.Purchase;
import com.dodopal.oss.business.service.ClearingTaskService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by lenovo on 2015/10/14.
 */
public class ClearingTask {
    private static Logger log = Logger.getLogger(ClearingTask.class);
    private DDPLog<ClearingTask> ddpLog = new DDPLog<>(ClearingTask.class);
    @Autowired
    ClearingTaskService clearingTaskService;

    public void YKTRechargeClearingData() {
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
            } catch (DDPException e) {
                e.printStackTrace();
                log.info("=======DDPEXCEPTION=========" + e.getMessage());
                continue;
            }catch (SQLException e) {
                e.printStackTrace();
                log.info("=======一卡通充值添加清分数据到清分基础表出错=========" + e.getMessage());
                continue;
            } catch (Exception e) {
                e.printStackTrace();
                log.info("=======一卡通充值清分异常=========" + e.getMessage());
                continue;
            }
        }

        log.info("===========一卡通充值清分程序结束==================");
    }

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
            } catch (DDPException e) {
                e.printStackTrace();
                log.info("=======DDPEXCEPTION=========" + e.getMessage());
                continue;
            }catch (SQLException e) {
                e.printStackTrace();
                log.info("=======账户充值添加清分数据到清分基础表出错=========" + e.getMessage());
                continue;
            } catch (Exception e) {
                e.printStackTrace();
                log.info("=======一卡通充值清分异常=========" + e.getMessage());
                continue;
            }
        }

        log.info("===========账户充值清分程序结束==================");
    }

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
            } catch (DDPException e) {
                e.printStackTrace();
                log.info("=======DDPEXCEPTION=========" + e.getMessage());
                continue;
            }catch (SQLException e) {
                e.printStackTrace();
                log.info("=======一卡通收单添加清分数据到清分基础表出错=========" + e.getMessage());
                continue;
            } catch (Exception e) {
                e.printStackTrace();
                log.info("=======一卡通充值清分异常=========" + e.getMessage());
                continue;
            }
        }
        log.info("===========一卡通收单清分程序结束==================");
    }
}
