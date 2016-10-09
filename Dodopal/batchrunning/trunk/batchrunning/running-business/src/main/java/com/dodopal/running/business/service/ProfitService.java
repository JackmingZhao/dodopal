package com.dodopal.running.business.service;

import com.dodopal.running.business.model.ClearingDataModel;

import java.sql.SQLException;
import java.util.List;

public interface ProfitService {
	/**
	 * @description 查询需要分润的数据信息
	 * @return
	 */
	public List<ClearingDataModel> queryProfitData();
	/**
	 * @description  分润实现
	 * @return
	 */
	public void profit(ClearingDataModel clearingDataModel) throws SQLException;
}
