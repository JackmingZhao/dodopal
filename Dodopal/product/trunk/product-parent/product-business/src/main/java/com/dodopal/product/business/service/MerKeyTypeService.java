package com.dodopal.product.business.service;

import com.dodopal.common.enums.MerKeyTypeMD5PwdEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.model.MerKeyType;

public interface MerKeyTypeService {
    public DodopalResponse<MerKeyType> findMerMD5PayPWDOrBackPayPWD(String merCode, MerKeyTypeMD5PwdEnum keyTypeMD5PwdEnum);
}
