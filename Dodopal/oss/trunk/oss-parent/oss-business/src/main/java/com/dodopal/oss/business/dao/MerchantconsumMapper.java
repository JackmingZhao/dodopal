package com.dodopal.oss.business.dao;

import java.util.List;

import com.dodopal.oss.business.bean.MerchantConsumBean;
import com.dodopal.oss.business.bean.query.MerchantConsumQuery;

public interface MerchantconsumMapper {


	/**
	 * 查询商户消费明细统计List
	 * @param merchantConsumQuery
	 * @return
	 */
	public List<MerchantConsumBean> findMerchantConsumInfoByPage(MerchantConsumQuery merchantConsumQuery);
	
	/**
	 * 获取当前总计路数
	 * @param merchantConsumQuery
	 * @return
	 */
	public int findMerchantConsumInfoCount(MerchantConsumQuery merchantConsumQuery) ;
	
	/**
	 * 导出数据集合
	 * @param merchantConsumQuery
	 * @return
	 */
	public List<MerchantConsumBean> findMerchantConsumInfoExcel(MerchantConsumQuery merchantConsumQuery) ;


}
