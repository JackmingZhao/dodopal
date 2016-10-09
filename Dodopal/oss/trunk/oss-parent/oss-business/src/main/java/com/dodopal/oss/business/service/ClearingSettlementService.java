package com.dodopal.oss.business.service;

import com.dodopal.oss.business.model.ProfitCollect;

import java.sql.SQLException;
import java.util.List;

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
