package com.dodopal.portal.delegate;

import java.util.List;

import com.dodopal.api.users.dto.ProfitCollectDTO;
import com.dodopal.api.users.dto.ProfitDetailsDTO;
import com.dodopal.api.users.dto.query.ProfitQueryDTO;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface ProfitDelegate {

	/**
	 * 查询(分页)
	 * @param queryDTO
	 * @return
	 */
	DodopalResponse<DodopalDataPage<ProfitCollectDTO>> findProfitCollectListByPage(ProfitQueryDTO queryDTO);

	/**
	 * 详情
	 * @param queryDTO
	 * @return
	 */
	DodopalResponse<DodopalDataPage<ProfitDetailsDTO>> findProfitDetailsListByPage(ProfitQueryDTO queryDTO);
	
	/**
	 * 查询
	 * @param queryDTO
	 * @return
	 */
	DodopalResponse<List<ProfitCollectDTO>> findProfitCollect(ProfitQueryDTO queryDTO);
}
