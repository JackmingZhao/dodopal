package com.dodopal.portal.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.PrdRateBean;

public interface PrdRateService {
    DodopalResponse<List<PrdRateBean>> findPrdRate();
}
