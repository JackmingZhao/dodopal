package com.dodopal.portal.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.ProfitCollectDTO;
import com.dodopal.api.users.dto.ProfitDetailsDTO;
import com.dodopal.api.users.dto.query.ProfitQueryDTO;
import com.dodopal.api.users.facade.ProfitFacade;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.ProfitDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;

@Service("profitDelegate")
public class ProfitDelegateImpl extends BaseDelegate implements ProfitDelegate{

	/**
	 * 查询
	 * @param queryDTO
	 * @return
	 */
	public DodopalResponse<DodopalDataPage<ProfitCollectDTO>> findProfitCollectListByPage(
			ProfitQueryDTO queryDTO) {
		ProfitFacade facade = getFacade(ProfitFacade.class, DelegateConstant.FACADE_USERS_URL);
		DodopalResponse<DodopalDataPage<ProfitCollectDTO>> response = facade.findProfitCollectListByPage(queryDTO, SourceEnum.PORTAL);
		return response;
	}

	/**
	 * 详情
	 * @param queryDTO
	 * @return
	 */
	public DodopalResponse<DodopalDataPage<ProfitDetailsDTO>> findProfitDetailsListByPage(
			ProfitQueryDTO queryDTO) {
		ProfitFacade facade = getFacade(ProfitFacade.class, DelegateConstant.FACADE_USERS_URL);
		DodopalResponse<DodopalDataPage<ProfitDetailsDTO>> response = facade.findProfitDetailsListByPage(queryDTO, SourceEnum.PORTAL);
		return response;
	}

	@Override
	public DodopalResponse<List<ProfitCollectDTO>> findProfitCollect(ProfitQueryDTO queryDTO) {
		ProfitFacade facade = getFacade(ProfitFacade.class, DelegateConstant.FACADE_USERS_URL);
		DodopalResponse<List<ProfitCollectDTO>> response = facade.findProfitCollect(queryDTO, SourceEnum.PORTAL);
		return response;
	}

}
