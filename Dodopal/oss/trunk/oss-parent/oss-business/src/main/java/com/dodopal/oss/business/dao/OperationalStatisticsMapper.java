package com.dodopal.oss.business.dao;

import java.util.List;

import com.dodopal.oss.business.model.CityCusReport;
import com.dodopal.oss.business.model.CityInfo;
import com.dodopal.oss.business.model.dto.CityCusReportQuery;

public interface OperationalStatisticsMapper {

	// 城市每月运营统计列表
	public List<CityCusReport> findCityCusRepStasByPage(CityCusReportQuery que);
	
	// 获取统计运营管理城市信息
	public List<CityInfo> getCityInfo();

	
}
