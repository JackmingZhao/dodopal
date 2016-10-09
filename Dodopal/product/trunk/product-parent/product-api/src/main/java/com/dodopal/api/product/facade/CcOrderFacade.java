package com.dodopal.api.product.facade;


import java.util.List;

import com.dodopal.api.product.dto.CcOrderFormDTO;
import com.dodopal.api.product.dto.CzOrderByPosCountDTO;
import com.dodopal.api.product.dto.CzOrderByPosDTO;
import com.dodopal.api.product.dto.query.QueryCcOrderFormDTO;
import com.dodopal.api.product.dto.query.QueryCzOrderByPosCountDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface CcOrderFacade {
  
    /***
     * 4.2.2.4  手持pos机充值订单查询
     * @param queryCcOrderFormDTO
     * @return
     */
    public DodopalResponse<DodopalDataPage<CcOrderFormDTO>> findCcOrderFormByPage(QueryCcOrderFormDTO queryCcOrderFormDTO);
    /***
     * 4.2.2.5  手持pos机充值订单统计
     * @param queryCzOrderByPosCountDTO
     * @return
     */
    public DodopalResponse<DodopalDataPage<CzOrderByPosDTO>> findCzOrderByPosCountByPage(QueryCzOrderByPosCountDTO queryCzOrderByPosCountDTO);
    public DodopalResponse<CzOrderByPosCountDTO> findCzOrderByPosCountAll(QueryCzOrderByPosCountDTO queryCzOrderByPosCountDTO);
    
    /***
     * 4.2.2.4  手持pos机充值订单查询
     * @param queryCcOrderFormDTO
     * @return
     */
    public DodopalResponse<List<CcOrderFormDTO>> findCcOrderFormExcel(QueryCcOrderFormDTO queryCcOrderFormDTO);
   
    /**
     * 4.2.2.5  手持pos机充值订单统计导出
     * @param queryCzOrderByPosCountDTO
     * @return
     */
    public DodopalResponse<List<CzOrderByPosDTO>> findCzOrderByPosCountExcel(QueryCzOrderByPosCountDTO queryCzOrderByPosCountDTO);
    
}
