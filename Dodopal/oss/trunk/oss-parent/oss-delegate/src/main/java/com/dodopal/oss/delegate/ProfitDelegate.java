package com.dodopal.oss.delegate;

import com.dodopal.api.users.dto.ProfitCollectDTO;
import com.dodopal.api.users.dto.ProfitDetailsDTO;
import com.dodopal.api.users.dto.query.ProfitQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface ProfitDelegate {
	DodopalResponse<DodopalDataPage<ProfitCollectDTO>> findProfitCollectListByPage(ProfitQueryDTO queryDTO);

	DodopalResponse<DodopalDataPage<ProfitDetailsDTO>> findProfitDetailsListByPage(ProfitQueryDTO queryDTO);

}
