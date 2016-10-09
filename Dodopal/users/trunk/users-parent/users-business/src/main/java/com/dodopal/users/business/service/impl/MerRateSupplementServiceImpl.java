package com.dodopal.users.business.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.users.business.dao.MerRateSupplementMapper;
import com.dodopal.users.business.model.MerRateSupplement;
import com.dodopal.users.business.service.MerRateSupplementService;

/** 
 * @author lifeng@dodopal.com
 */
@Service
public class MerRateSupplementServiceImpl implements MerRateSupplementService {
	@Autowired
	MerRateSupplementMapper mapper;

	@Override
	@Transactional(readOnly = true)
	public List<MerRateSupplement> findMerRateSpmtsByMerCode(String merCode) {
		return mapper.findMerRateSpmtsByMerCode(merCode);
	}

	@Override
	@Transactional(readOnly = true)
	public MerRateSupplement findMerRateUrl(String merCode, String rateCode) {
		return mapper.findMerRateUrl(merCode, rateCode);
	}

	@Override
	@Transactional
	public int batchAddMerRateSpmts(List<MerRateSupplement> list, String merCode) {
		if (StringUtils.isNotBlank(merCode)) {
			mapper.deleteMerRateSpmtsByMerCode(merCode);
		}
		if (CollectionUtils.isEmpty(list)) {
			return 0;
		}
		return mapper.batchAddMerRateSpmts(list);
	}

	@Override
	@Transactional
	public int deleteMerRateSpmtsByMerCode(String merCode) {
		return mapper.deleteMerRateSpmtsByMerCode(merCode);
	}

	@Override
	@Transactional
	public int batchDelMerRateSpmtsByMerCodes(List<String> merCodes) {
		return mapper.batchDelMerRateSpmtsByMerCodes(merCodes);
	}

}
