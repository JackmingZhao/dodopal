package com.dodopal.portal.delegate;

import java.util.List;

import com.dodopal.api.product.dto.CcOrderFormDTO;
import com.dodopal.api.product.dto.CzOrderByPosCountDTO;
import com.dodopal.api.product.dto.CzOrderByPosDTO;
import com.dodopal.api.product.dto.query.QueryCcOrderFormDTO;
import com.dodopal.api.product.dto.query.QueryCzOrderByPosCountDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface CcOrderDelegate {
    /**
     * 4.2.2.4  手持pos机充值订单查询
     * @param queryCcOrderFormDTO
     * @return
     */
    public DodopalResponse<DodopalDataPage<CcOrderFormDTO>> findCcOrderListByPage(QueryCcOrderFormDTO queryCcOrderFormDTO);
    /***
     * 4.2.2.5  手持pos机充值订单统计
     * @param queryCzOrderByPosCountDto
     * @return
     */
    public DodopalResponse<DodopalDataPage<CzOrderByPosDTO>> findCzOrderByPosByPage(QueryCzOrderByPosCountDTO queryCzOrderByPosCountDto);
    public DodopalResponse<CzOrderByPosCountDTO> findCzOrderByPosCount(QueryCzOrderByPosCountDTO queryCzOrderByPosCountDto);
    /**
     * 4.2.2.5  手持pos机充值订单统计导出
     * @param queryCzOrderByPosCountDto
     * @return
     */
    public DodopalResponse<List<CzOrderByPosDTO>> findCzOrderByPosExcel(QueryCzOrderByPosCountDTO queryCzOrderByPosCountDto);
    /**
     * 4.2.2.4  手持pos机充值订单查询
     * @param queryCcOrderFormDTO
     * @return
     */
    public DodopalResponse<List<CcOrderFormDTO>> findCcOrderListExcel(QueryCcOrderFormDTO queryCcOrderFormDTO);
    
}
