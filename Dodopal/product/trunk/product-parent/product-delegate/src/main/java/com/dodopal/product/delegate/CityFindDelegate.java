package com.dodopal.product.delegate;

import java.util.List;

import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalResponse;

public interface CityFindDelegate {
    /**
     * 查询商户或个人用户开通的通卡对应的城市列表(必须开通一卡通充值)
     * @param custType(客户类型：0个人1商户)
     * @param custCode(客户号)
     * @return
     */
    public DodopalResponse<List<Area>> findYktCitys(MerUserTypeEnum custType, String custCode);


}



