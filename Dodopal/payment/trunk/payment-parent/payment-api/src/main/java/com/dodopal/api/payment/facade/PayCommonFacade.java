package com.dodopal.api.payment.facade;

import java.util.List;

import com.dodopal.api.payment.dto.PayWayCommonDTO;
import com.dodopal.api.payment.dto.query.PayCommonQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

/**
 * 
 * @author hxc
 * @version 创建时间：2015年8月11日
 *
 */
public interface PayCommonFacade {

    /**
     * 查询
     * @param dto
     * @return DodopalResponse<DodopalDataPage<PayWayCommonDTO>>
     */
    DodopalResponse<DodopalDataPage<PayWayCommonDTO>> findPayCommonByPage(PayCommonQueryDTO dto);
    
    /**
     * 删除
     * @param ids
     * @return DodopalResponse<Integer>
     */
    DodopalResponse<Integer> deletePayCommonByIds(List<String> ids);
    
    /**
     * 新增
     * @param commons
     * @return DodopalResponse<Integer>
     */
    DodopalResponse<List<Integer>> insertPayWayCommon(List<PayWayCommonDTO> commons);
    
    
    /**
     * 删除
     * @return DodopalResponse<Integer>
     */
    DodopalResponse<Integer> deletePaywayCommon(String userCode);
}
