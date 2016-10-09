package com.dodopal.users.business.service;

import java.util.List;

import com.dodopal.users.business.model.MerRateSupplement;

/**
 * @author lifeng@dodopal.com
 */

public interface MerRateSupplementService {
	public List<MerRateSupplement> findMerRateSpmtsByMerCode(String merCode);

	public MerRateSupplement findMerRateUrl(String merCode, String rateCode);

	public int batchAddMerRateSpmts(List<MerRateSupplement> list, String merCode);

	public int deleteMerRateSpmtsByMerCode(String merCode);

	public int batchDelMerRateSpmtsByMerCodes(List<String> merCodes);
}
