package com.dodopal.users.business.dao;

import java.util.List;

import com.dodopal.users.business.model.MerLimitExtractConfirm;
import com.dodopal.users.business.model.query.MerLimitExtractConfirmQuery;

public interface MerLimitExtractConfirmMapper {

	public void insertMerLimitExtractConfirm(MerLimitExtractConfirm merLimitExtractConfirm);
	
	public int findMerLimitExtractConfirmByCode(MerLimitExtractConfirm merLimitExtractConfirm);
	/**
	 * 根据商户号查看连锁加盟网点或者连锁商户的额度提取记录
	 * Name: JOE
	 * Time:2016-01-05
	 * @param merLimitExtractConfirmQuery
	 */
	public List<MerLimitExtractConfirm> findMerLimitExtractConfirmByPage(MerLimitExtractConfirmQuery merLimitExtractConfirmQuery);
	/**
	 * 修改连锁加盟网点或者连锁商户的额度提取状态
	 * Name: JOE
     * Time:2016-01-05
	 * @param merLimitExtractConfirm
	 * @return
	 */
	public int updateMerLimitExtractConfirmByCode(MerLimitExtractConfirm merLimitExtractConfirm);
}
