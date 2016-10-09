package com.dodopal.users.business.service;

import java.util.List;

import com.dodopal.users.business.model.MerAutoAmt;

/**
 * @author lifeng@dodopal.com
 */

public interface MerAutoAmtService {

	public MerAutoAmt findMerAutoAmtByMerCode(String merCode);

	public int addMerAutoAmt(MerAutoAmt merAutoAmt);

	public int updateMerAutoAmt(MerAutoAmt merAutoAmt);

	public int deleteMerAutoAmtByMerCode(String merCode);
	
	public int batchDelMerAutoAmtByMerCodes(List<String> merCodes);
}
