package com.dodopal.product.business.service;

import com.dodopal.api.users.dto.PosDTO;
import com.dodopal.api.users.dto.PosQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

/** 
 * @author lifeng@dodopal.com
 */

public interface PosService {
	public DodopalResponse<DodopalDataPage<PosDTO>> findPosListPage(PosQueryDTO findDto);
}
