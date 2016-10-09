package com.dodopal.oss.delegate;

import java.util.List;

import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantQueryDTO;
import com.dodopal.common.model.DodopalResponse;

public interface MerchantExpDelegate {
    DodopalResponse<Integer> getExpMerchantCount(MerchantQueryDTO merchantQueryDTO);
    DodopalResponse<List<MerchantDTO>> getExportMerchantList(MerchantQueryDTO merchantQueryDTO);
}
