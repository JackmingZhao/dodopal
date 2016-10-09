package com.dodopal.oss.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.oss.business.bean.MerchantConsumBean;
import com.dodopal.oss.business.bean.query.MerchantConsumQuery;

public interface MerchantconsumService {


	/**
	 * 查询商户消费明细统计List
	 * @param merchantConsumQuery
	 * @return
	 */
	public DodopalDataPage<MerchantConsumBean> findMerchantConsumInfo(MerchantConsumQuery merchantConsumQuery);
	
	/**
	 * 获取导出总记录数
	 * @param merchantConsumQuery
	 * @return
	 */
	public int findMerchantConsumInfoCount(MerchantConsumQuery merchantConsumQuery);
	
	/**
	 * 获取导出记录的集合
	 * @param merchantConsumQuery
	 * @return
	 */
	public List<MerchantConsumBean> findMerchantConsumInfoExcel(MerchantConsumQuery merchantConsumQuery);

}
