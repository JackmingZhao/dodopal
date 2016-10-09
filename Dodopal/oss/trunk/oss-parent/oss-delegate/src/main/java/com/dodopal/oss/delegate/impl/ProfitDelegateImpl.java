package com.dodopal.oss.delegate.impl;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.ProfitCollectDTO;
import com.dodopal.api.users.dto.ProfitDetailsDTO;
import com.dodopal.api.users.dto.query.ProfitQueryDTO;
import com.dodopal.api.users.facade.ProfitFacade;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.delegate.BaseDelegate;
import com.dodopal.oss.delegate.ProfitDelegate;
import com.dodopal.oss.delegate.constant.DelegateConstant;

@Service
public class ProfitDelegateImpl extends BaseDelegate implements ProfitDelegate{

	@Override
	public DodopalResponse<DodopalDataPage<ProfitCollectDTO>> findProfitCollectListByPage(ProfitQueryDTO queryDTO) {
		ProfitFacade facade = getFacade(ProfitFacade.class, DelegateConstant.FACADE_USERS_URL);
	    DodopalResponse<DodopalDataPage<ProfitCollectDTO>> response = facade.findProfitCollectListByPage(queryDTO,SourceEnum.OSS);
		return response;
	}

	@Override
	public DodopalResponse<DodopalDataPage<ProfitDetailsDTO>> findProfitDetailsListByPage(ProfitQueryDTO queryDTO) {
		ProfitFacade facade = getFacade(ProfitFacade.class, DelegateConstant.FACADE_USERS_URL);
	    DodopalResponse<DodopalDataPage<ProfitDetailsDTO>> response = facade.findProfitDetailsListByPage(queryDTO,SourceEnum.OSS);
		return response;
	}
}
