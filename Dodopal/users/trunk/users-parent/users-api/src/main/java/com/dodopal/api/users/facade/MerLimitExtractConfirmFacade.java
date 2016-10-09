package com.dodopal.api.users.facade;

import com.dodopal.api.users.dto.MerLimitExtractConfirmDTO;
import com.dodopal.api.users.dto.query.MerLimitExtractConfirmQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface MerLimitExtractConfirmFacade {
	public DodopalResponse<Boolean> insertMerLimitExtractConfirm(MerLimitExtractConfirmDTO merLimitExtractConfirm);

	public DodopalResponse<Integer> findMerLimitExtractConfirmByCode(MerLimitExtractConfirmDTO merLimitExtractConfirm);
	/**
	 * 根据商户号查看连锁加盟网点或者连锁商户的额度提取记录
     * Name: JOE
     * Time:2016-01-05
	 * @param merLimitExtractConfirmQuery
	 * @return
	 */
    public  DodopalResponse<DodopalDataPage<MerLimitExtractConfirmDTO>>  findMerLimitExtractConfirmByPage(MerLimitExtractConfirmQueryDTO merLimitQueryDto);
   /**
    * 修改连锁加盟网点或者连锁商户的额度提取状态
    * Name: JOE
    * Time:2016-01-05
    * @param merLimitExtractConfirm
    * @return
    */
    public DodopalResponse<Integer> updateMerLimitExtractConfirmByCode(MerLimitExtractConfirmDTO merLimitExtractConfirm);
}
