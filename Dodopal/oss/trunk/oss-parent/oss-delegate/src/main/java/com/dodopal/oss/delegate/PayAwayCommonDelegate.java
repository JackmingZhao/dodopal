package com.dodopal.oss.delegate;

import java.util.List;

import com.dodopal.api.payment.dto.PayWayCommonDTO;
import com.dodopal.api.payment.dto.query.PayCommonQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
/**
 * 用户常用支付方式 PayAwayCommonDelegate
 * @author xiongzhijing@dodopal.com
 * @version 2015年8月12日 
 */
public interface PayAwayCommonDelegate {
    /**
     * 查询用户常用支付方式（分页）
     * @param queryDTO 查询条件封装的在传输时的实体
     * @return DodopalResponse<DodopalDataPage<PayWayCommonDTO>>
     */
    public DodopalResponse<DodopalDataPage<PayWayCommonDTO>> findPayAwayCommonListByPage(PayCommonQueryDTO queryDTO);
   
    /**
     * 批量删除用户常用支付方式
     * @param ids 用户常用支付方式的id
     * @return DodopalResponse<Integer>
     */
    public DodopalResponse<Integer> deletePayAwayCommon (List<String> ids);

}
