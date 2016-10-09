package com.dodopal.oss.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.bean.PrdRateBean;

public interface PrdRateService {
    DodopalResponse<List<PrdRateBean>> findPrdRate(String merType);
}
