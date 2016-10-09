package com.dodopal.product.delegate;

import com.dodopal.api.users.dto.PosDTO;
import com.dodopal.api.users.dto.PosQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

/**
 * @author lifeng@dodopal.com
 */

public interface PosDelegate {
	public DodopalResponse<DodopalDataPage<PosDTO>> findPosListPage(PosQueryDTO findDto);
}
