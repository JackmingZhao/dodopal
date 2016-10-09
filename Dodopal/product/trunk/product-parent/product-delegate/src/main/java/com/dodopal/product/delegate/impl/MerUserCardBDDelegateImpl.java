package com.dodopal.product.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerUserCardBDDTO;
import com.dodopal.api.users.dto.MerUserCardBDFindDTO;
import com.dodopal.api.users.dto.UserCardRecordDTO;
import com.dodopal.api.users.facade.MerUserCardBDFacade;
import com.dodopal.api.users.facade.UserCardRecordQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.delegate.BaseDelegate;
import com.dodopal.product.delegate.MerUserCardBDDelegate;
import com.dodopal.product.delegate.constant.DelegateConstant;
@Service("merUserCardBDDelegate")
public class MerUserCardBDDelegateImpl extends BaseDelegate implements MerUserCardBDDelegate{

	@Override
	public DodopalResponse<List<MerUserCardBDDTO>> findMerUserCardBD(
			MerUserCardBDFindDTO cardBDFindDTO) {
		 MerUserCardBDFacade merUserCardBD = getFacade(MerUserCardBDFacade.class, DelegateConstant.FACADE_USERS_URL);
	     return merUserCardBD.findMerUserCardBD(cardBDFindDTO);
	}

	@Override
	public DodopalResponse<MerUserCardBDDTO> saveMerUserCardBD(
			MerUserCardBDDTO cardBDDTO) {
		 MerUserCardBDFacade merUserCardBD = getFacade(MerUserCardBDFacade.class, DelegateConstant.FACADE_USERS_URL);
	     return merUserCardBD.saveMerUserCardBD(cardBDDTO);
	}

	@Override
	public DodopalResponse<DodopalDataPage<UserCardRecordDTO>> findUserCardRecordByPage(UserCardRecordQueryDTO query) {
		MerUserCardBDFacade merUserCardBD = getFacade(MerUserCardBDFacade.class, DelegateConstant.FACADE_USERS_URL);
	     return merUserCardBD.findUserCardRecordByPage(query);
	}

}
