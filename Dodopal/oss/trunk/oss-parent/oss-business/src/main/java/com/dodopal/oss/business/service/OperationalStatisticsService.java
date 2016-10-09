package com.dodopal.oss.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.oss.business.model.CityCusReport;
import com.dodopal.oss.business.model.CityInfo;
import com.dodopal.oss.business.model.dto.CityCusReportQuery;

/**
 * 运营报表
 * @author Mikaelyan
 *
 */
public interface OperationalStatisticsService {

	/**
	 * 城市每月运营统计列表
	 * @param que
	 * @return
	 */
	public DodopalDataPage<CityCusReport> findCityCusRepStasByPage(CityCusReportQuery que);
	
	/**
	 * 获取统计运营管理城市信息
	 * @return
	 */
	public List<CityInfo> getCityInfo();
	
	
}
