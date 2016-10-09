package com.dodopal.oss.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.oss.business.dao.OperationalStatisticsMapper;
import com.dodopal.oss.business.model.CityCusReport;
import com.dodopal.oss.business.model.CityInfo;
import com.dodopal.oss.business.model.dto.CityCusReportQuery;
import com.dodopal.oss.business.service.OperationalStatisticsService;

/**
 * @author Mikaelyan
 *
 */
@Service
public class OperationalStatisticsServiceImpl implements OperationalStatisticsService {
	
	@Autowired
	private OperationalStatisticsMapper oprStaMapper;
	
	// 城市每月运营统计列表
	@Transactional(readOnly = true)
	public DodopalDataPage<CityCusReport> findCityCusRepStasByPage(CityCusReportQuery que) {
		List<CityCusReport> dataList = oprStaMapper.findCityCusRepStasByPage(que);
		DodopalDataPage<CityCusReport> result = new DodopalDataPage<CityCusReport>(que.getPage(), dataList);
		return result;
	}

	// 获取统计运营管理城市信息
	@Transactional(readOnly = true)
	public List<CityInfo> getCityInfo() {
		
		List<CityInfo> cityInfo = oprStaMapper.getCityInfo();
		return cityInfo;
	}
	
	

}
