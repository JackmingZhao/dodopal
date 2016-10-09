package com.dodopal.oss.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.oss.business.bean.MerchantConsumBean;
import com.dodopal.oss.business.bean.query.MerchantConsumQuery;
import com.dodopal.oss.business.dao.MerchantconsumMapper;
import com.dodopal.oss.business.service.MerchantconsumService;

@Service
public class MerchantconsumServiceImpl implements MerchantconsumService{
	@Autowired
	private MerchantconsumMapper merchantconsumMapper;
	
	@Override
	@Transactional(readOnly=true)
	public DodopalDataPage<MerchantConsumBean> findMerchantConsumInfo(MerchantConsumQuery merchantConsumQuery) {
		List<MerchantConsumBean> records=merchantconsumMapper.findMerchantConsumInfoByPage(merchantConsumQuery);
		DodopalDataPage<MerchantConsumBean> dataPage = new DodopalDataPage<>(merchantConsumQuery.getPage(), records);
		return dataPage;
	}

	@Override
	public int findMerchantConsumInfoCount(MerchantConsumQuery merchantConsumQuery) {
		return merchantconsumMapper.findMerchantConsumInfoCount(merchantConsumQuery);
	}

	@Override
	public List<MerchantConsumBean> findMerchantConsumInfoExcel (MerchantConsumQuery merchantConsumQuery){
		return merchantconsumMapper.findMerchantConsumInfoExcel(merchantConsumQuery);
	}

}
