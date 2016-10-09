package com.dodopal.portal.delegate.impl;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerLimitExtractConfirmDTO;
import com.dodopal.api.users.dto.query.MerLimitExtractConfirmQueryDTO;
import com.dodopal.api.users.facade.MerLimitExtractConfirmFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.MerLimitExtractConfirmDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;

@Service
public class MerLimitExtractConfirmDelegateImpl extends BaseDelegate implements MerLimitExtractConfirmDelegate {

	@Override
	public DodopalResponse<Boolean> insertMerLimitExtractConfirm(MerLimitExtractConfirmDTO merLimitExtractConfirm) {
		MerLimitExtractConfirmFacade facade = getFacade(MerLimitExtractConfirmFacade.class, DelegateConstant.FACADE_USERS_URL);
		return facade.insertMerLimitExtractConfirm(merLimitExtractConfirm);
	}

	@Override
	public DodopalResponse<Integer> findMerLimitExtractConfirmByCode(MerLimitExtractConfirmDTO merLimitExtractConfirm) {
		MerLimitExtractConfirmFacade facade = getFacade(MerLimitExtractConfirmFacade.class, DelegateConstant.FACADE_USERS_URL);
		return facade.findMerLimitExtractConfirmByCode(merLimitExtractConfirm);
	}

    @Override
    public DodopalResponse<DodopalDataPage<MerLimitExtractConfirmDTO>> findMerLimitExtractConfirmByPage(MerLimitExtractConfirmQueryDTO merLimitQueryDto) {
        MerLimitExtractConfirmFacade facade = getFacade(MerLimitExtractConfirmFacade.class, DelegateConstant.FACADE_USERS_URL);
        return facade.findMerLimitExtractConfirmByPage(merLimitQueryDto);
    }

    @Override
    public DodopalResponse<Integer> updateMerLimitExtractConfirmByCode(MerLimitExtractConfirmDTO merLimitDto) {
        MerLimitExtractConfirmFacade facade = getFacade(MerLimitExtractConfirmFacade.class, DelegateConstant.FACADE_USERS_URL);
        return facade.updateMerLimitExtractConfirmByCode(merLimitDto);
    }

}
