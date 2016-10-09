package com.dodopal.portal.delegate;

import java.util.List;

import com.dodopal.api.product.dto.PrdRateDTO;
import com.dodopal.common.model.DodopalResponse;

public interface PrdRateDelegate {
    public DodopalResponse<List<PrdRateDTO>> findPrdRate();
}
