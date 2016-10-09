package com.dodopal.users.business.service;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.users.business.model.MerLimitExtractConfirm;
import com.dodopal.users.business.model.query.MerLimitExtractConfirmQuery;

public interface MerLimitExtractConfirmService {

	public void insertMerLimitExtractConfirm(MerLimitExtractConfirm merLimitExtractConfirm);
	
	public int findMerLimitExtractConfirmByCode(MerLimitExtractConfirm merLimitExtractConfirm);
	/**
     * 根据商户号查看连锁加盟网点或者连锁商户的额度提取记录
     * Name: JOE
     * Time:2016-01-05
     * @param merLimitExtractConfirmQuery
     */
    public DodopalDataPage<MerLimitExtractConfirm>  findMerLimitExtractConfirmByPage(MerLimitExtractConfirmQuery merLimitExtractConfirmQuery);
    /**
     * 修改连锁加盟网点或者连锁商户的额度提取状态
     * Name: JOE
     * Time:2016-01-05
     * @param merLimitExtractConfirm
     * @return
     */
    public DodopalResponse<Integer> updateMerLimitExtractConfirmByCode(MerLimitExtractConfirm merLimitExtractConfirm);
}
