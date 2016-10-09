package com.dodopal.oss.business.task;

import com.dodopal.common.exception.DDPException;
import com.dodopal.oss.business.model.ProfitCollect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dodopal.oss.business.service.ClearingSettlementService;

import java.sql.SQLException;
import java.util.List;

public class SettlementTask {
    @Autowired
    private ClearingSettlementService settlementService;

    private final static Logger logger = LoggerFactory.getLogger(SettlementTask.class);

    public void distributionData() {
        logger.info("=========================================================================[清算]START=========================================================");
        try {
            settlementService.distributionData();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        logger.info("=========================================================================[清算]END===========================================================");
    }

    public void profitCollect() {
        logger.info("=============分润汇总跑批程序启动==============");
        List<ProfitCollect> profitCollect = settlementService.queryProfitCollect();
        if (profitCollect.size() == 0) {
            logger.info("=============没有需要分润汇总数据==============");
            return;
        }
        settlementService.deleteProfitCollect();
        for (ProfitCollect profitInfo : profitCollect) {
            try {
                settlementService.profitCollect(profitInfo);
            } catch (DDPException e) {
                logger.info("========DDPException==========" + e.getMessage());
                e.printStackTrace();
                continue;
            } catch (SQLException e) {
                logger.info("=======分润汇总操作数据库出错=========" + e.getMessage());
                e.printStackTrace();
                continue;
            } catch (Exception e) {
                logger.info("=======分润汇总程序出错=========" + e.getMessage());
                e.printStackTrace();
                continue;
            }
        }

        logger.info("=============分润汇总跑批程序结束==============");
    }

}
