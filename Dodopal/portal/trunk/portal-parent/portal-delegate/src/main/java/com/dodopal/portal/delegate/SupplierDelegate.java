package com.dodopal.portal.delegate;

import com.dodopal.api.users.dto.MerCountDTO;
import com.dodopal.api.users.dto.OperUserDTO;
import com.dodopal.api.users.dto.PosDTO;
import com.dodopal.api.users.dto.PosQueryDTO;
import com.dodopal.api.users.dto.query.MerCountQueryDTO;
import com.dodopal.common.enums.PosOperTypeEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface SupplierDelegate {
    
    /**
     * 查询商户在某城市的pos信息
     * @param PosQueryDTO 
     * @return
     */
    public DodopalResponse<DodopalDataPage<PosDTO>> countMerchantPosForSupplier(PosQueryDTO findDto);

     /**
      * 供应商(通卡)用户统计城市下所有的商户的信息
      * @param MerCountQueryDTO
      * @return
      */
    public DodopalResponse<DodopalDataPage<MerCountDTO>> countMerchantForSupplier(MerCountQueryDTO merCountQueryDTO);
    
    /**
     * 停用、启用pos
     * @param posOper
     * @param merCode
     * @param pos
     * @param operUser
     * @return
     */
    public DodopalResponse<String> posOper(PosOperTypeEnum posOper, String merCode, String[] pos, OperUserDTO operUser);

}
