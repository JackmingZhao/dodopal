package com.dodopal.api.product.facade;

import java.util.List;

import com.dodopal.api.product.dto.PrdRateDTO;
import com.dodopal.common.model.DodopalResponse;


public interface PrdRateFacade {
    public DodopalResponse<List<PrdRateDTO>> findPrdRate();
}
