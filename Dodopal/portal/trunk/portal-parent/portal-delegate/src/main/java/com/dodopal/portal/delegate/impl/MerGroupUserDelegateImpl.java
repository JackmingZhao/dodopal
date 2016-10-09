package com.dodopal.portal.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerGroupUserDTO;
import com.dodopal.api.users.dto.MerGroupUserFindDTO;
import com.dodopal.api.users.dto.query.MerGroupUserQueryDTO;
import com.dodopal.api.users.facade.MerGroupUserFacade;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.MerGroupUserDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;

@Service("merGroupUserDelegateImpl")
public class MerGroupUserDelegateImpl extends BaseDelegate implements MerGroupUserDelegate {

	@Override
	public DodopalResponse<List<MerGroupUserDTO>> findMerGpUsers(MerGroupUserFindDTO findDTO, String findFlg) {
		MerGroupUserFacade facade = getFacade(MerGroupUserFacade.class, DelegateConstant.FACADE_USERS_URL);
		return facade.findMerGpUsers(findDTO, findFlg);
	}
	
	@Override
	public DodopalResponse<DodopalDataPage<MerGroupUserDTO>> findMerGpUsersByPage(MerGroupUserQueryDTO findDTO, SourceEnum source) {
		MerGroupUserFacade facade = getFacade(MerGroupUserFacade.class, DelegateConstant.FACADE_USERS_URL);
		return facade.findMerGpUsersByPage(findDTO, source);
	}

	@Override
	public DodopalResponse<MerGroupUserDTO> findMerGpUserById(String gpUserId) {
		MerGroupUserFacade facade = getFacade(MerGroupUserFacade.class, DelegateConstant.FACADE_USERS_URL);
		return facade.findMerGpUserById(gpUserId);
	}

	@Override
	public DodopalResponse<String> saveMerGpUsers(List<MerGroupUserDTO> gpUserDtos) {
		MerGroupUserFacade facade = getFacade(MerGroupUserFacade.class, DelegateConstant.FACADE_USERS_URL);
		return facade.saveMerGpUsers(gpUserDtos);
	}

	@Override
	public DodopalResponse<String> saveMerGpUser(MerGroupUserDTO gpUserDto) {
		MerGroupUserFacade facade = getFacade(MerGroupUserFacade.class, DelegateConstant.FACADE_USERS_URL);
		return facade.saveMerGpUser(gpUserDto);
	}

	@Override
	public DodopalResponse<Integer> updateMerGpUser(MerGroupUserDTO gpUserDto) {
		MerGroupUserFacade facade = getFacade(MerGroupUserFacade.class, DelegateConstant.FACADE_USERS_URL);
		return facade.updateMerGpUser(gpUserDto);
	}

	@Override
	public DodopalResponse<Integer> deleteMerGpUser(String gpUserId) {
		MerGroupUserFacade facade = getFacade(MerGroupUserFacade.class, DelegateConstant.FACADE_USERS_URL);
		return facade.deleteMerGpUser(gpUserId);
	}

	@Override
	public DodopalResponse<String> checkCardCode(String merCode,String cardCode,String gpUserId){
		MerGroupUserFacade facade = getFacade(MerGroupUserFacade.class, DelegateConstant.FACADE_USERS_URL);
		return facade.checkCardCode(merCode,cardCode,gpUserId);
	}
}
