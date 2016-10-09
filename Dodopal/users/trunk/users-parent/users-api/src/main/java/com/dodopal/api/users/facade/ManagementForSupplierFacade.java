package com.dodopal.api.users.facade;

import com.dodopal.api.users.dto.MerCountDTO;
import com.dodopal.api.users.dto.PosDTO;
import com.dodopal.api.users.dto.PosQueryDTO;
import com.dodopal.api.users.dto.query.MerCountQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

/**
 * 供应商登录门户调用的接口
 * @author xiongzhijing@dodopal.com
 * @version 1.0 2015年12月20日
 */
public interface ManagementForSupplierFacade {

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

}
