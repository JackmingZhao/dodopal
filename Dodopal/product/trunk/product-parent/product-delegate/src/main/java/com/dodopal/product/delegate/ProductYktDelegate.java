package com.dodopal.product.delegate;

import java.util.List;
import java.util.Map;

import com.dodopal.common.model.DodopalResponse;

public interface ProductYktDelegate {

    DodopalResponse<Boolean> icdcPayCreate(List<Map<String,Object>> list);

}
