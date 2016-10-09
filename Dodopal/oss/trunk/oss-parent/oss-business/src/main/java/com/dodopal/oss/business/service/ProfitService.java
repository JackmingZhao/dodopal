package com.dodopal.oss.business.service;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.bean.ProfitCollectBean;
import com.dodopal.oss.business.bean.ProfitDetailsBean;
import com.dodopal.oss.business.bean.query.ProfitQuery;
import com.dodopal.oss.business.model.ClearingDataModel;

import java.sql.SQLException;
import java.util.List;

public interface ProfitService {
	DodopalResponse<DodopalDataPage<ProfitCollectBean>> findProfitCollectListByPage(ProfitQuery query);

	DodopalResponse<DodopalDataPage<ProfitDetailsBean>> findProfitDetailsListByPage(ProfitQuery query);

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
