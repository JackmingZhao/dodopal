/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.oss.business.task;

import com.dodopal.common.exception.DDPException;
import com.dodopal.common.util.DDPLog;
import com.dodopal.oss.business.bean.MerchantBean;
import com.dodopal.oss.business.model.ClearingDataModel;
import com.dodopal.oss.business.model.MerchantUser;
import com.dodopal.oss.business.model.MerchantUserInfo;
import com.dodopal.oss.business.service.ProfitService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by lenovo on 2015/10/26.
 */
public class ProfitTask {

    private static Logger log = Logger.getLogger(ProfitTask.class);
    private DDPLog<ProfitTask> ddpLog = new DDPLog<>(ProfitTask.class);
    @Autowired
    private ProfitService profitService;

    public void profit() {
        log.info("=============分润跑批程序启动==============");
        List<ClearingDataModel> getProfitData = profitService.queryProfitData();
        if (getProfitData.size() == 0) {
            log.info("=============没有需要分润的数据==============");
        }
        for (ClearingDataModel profitInfo : getProfitData) {
            try {
                profitService.profit(profitInfo);
            } catch (DDPException e) {
                log.info("========DDPException==========" + e.getMessage());
                e.printStackTrace();
                continue;
            } catch (SQLException e) {
                log.info("=======分润程序操作数据库出错=========" + e.getMessage());
                e.printStackTrace();
                continue;
            } catch (Exception e) {
                log.info("=======分润程序出错=========" + e.getMessage());
                e.printStackTrace();
                continue;
            }
        }

        log.info("=============分润跑批程序结束==============");
    }
}
