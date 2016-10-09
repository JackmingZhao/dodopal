package com.dodopal.users.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.users.business.model.MerRateSupplement;

/**
 * @author lifeng@dodopal.com
 */

public interface MerRateSupplementMapper {

	public List<MerRateSupplement> findMerRateSpmtsByMerCode(String merCode);

	public MerRateSupplement findMerRateUrl(@Param("merCode") String merCode, @Param("rateCode") String rateCode);

	public int batchAddMerRateSpmts(@Param("list") List<MerRateSupplement> list);

	public int deleteMerRateSpmtsByMerCode(String merCode);

	public int batchDelMerRateSpmtsByMerCodes(@Param("merCodes") List<String> merCodes);
}
