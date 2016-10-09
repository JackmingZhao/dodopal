package com.dodopal.portal.business.service;

import com.dodopal.api.users.dto.MerLimitExtractConfirmDTO;
import com.dodopal.api.users.dto.query.MerLimitExtractConfirmQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerLimitExtractConfirmBean;
import com.dodopal.portal.business.bean.MerLimitExtractConfirmBeanQueryDTO;

public interface MerLimitExtractConfirmService {
	public DodopalResponse<Boolean> insertMerLimitExtractConfirm(MerLimitExtractConfirmDTO merLimitExtractConfirm);
	
	public DodopalResponse<Integer> findMerLimitExtractConfirmByCode(MerLimitExtractConfirmDTO merLimitExtractConfirm);
	
	/**
     * 根据商户号查看连锁加盟网点或者连锁商户的额度提取记录
     * Name: JOE
     * Time:2016-01-05
     * @param merLimitQueryDto
     */
    public DodopalResponse<DodopalDataPage<MerLimitExtractConfirmBean>>  findMerLimitExtractConfirmByPage(MerLimitExtractConfirmBeanQueryDTO merLimitBeanQueryDto);
    /**
     * 修改连锁加盟网点或者连锁商户的额度提取状态
     * Name: JOE
     * Time:2016-01-05
     * @param merLimitDto
     * @return
     */
    public DodopalResponse<Integer> updateMerLimitExtractConfirmByCode(MerLimitExtractConfirmBean merLimitBean);
}
