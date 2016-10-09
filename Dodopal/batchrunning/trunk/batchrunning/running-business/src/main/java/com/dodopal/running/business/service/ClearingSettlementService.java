package com.dodopal.running.business.service;

import java.sql.SQLException;
import java.util.List;

import com.dodopal.running.business.model.ProfitCollect;

public interface ClearingSettlementService {
    public void distributionData();

    /**
     * 查询分润汇总信息
     * @return
     */
    public List<ProfitCollect> queryProfitCollect();

    /**
     * 分润汇总处理
     * @param profitCollect
     */
    public void profitCollect(ProfitCollect profitCollect) throws SQLException;

    public void deleteProfitCollect();
}
