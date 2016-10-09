package com.dodopal.api.users.facade;

import java.util.List;

import com.dodopal.api.users.dto.MerFunctionInfoDTO;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.model.DodopalResponse;

/**
 * 类说明 ：
 * @author lifeng
 */

public interface MerFunctionInfoFacade {
    /**
     * 查找门户系统功能合集(OSS开户使用)
     * @return
     */
    public DodopalResponse<List<MerFunctionInfoDTO>> findAllFuncInfoForOSS(String merType);

    /**
     * 根据商户类型查询对应功能信息
     * @param merType
     * @return
     */
    public DodopalResponse<List<MerFunctionInfoDTO>> findFuncInfoByMerType(MerTypeEnum merType);
}
