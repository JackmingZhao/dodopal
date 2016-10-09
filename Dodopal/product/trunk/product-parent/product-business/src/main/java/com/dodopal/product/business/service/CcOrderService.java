package com.dodopal.product.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.model.CcOrderForm;
import com.dodopal.product.business.model.CzOrderByPosCount;
import com.dodopal.product.business.model.CzOrderByPosCountAll;
import com.dodopal.product.business.model.query.QueryCcOrderForm;
import com.dodopal.product.business.model.query.QueryCzOrderByPosCount;

public interface CcOrderService {
    /**
     * 4.2.2.4  手持pos机充值订单查询
     * @param queryCcOrderForm
     * @return
     */
    public DodopalDataPage<CcOrderForm> findCcOrderFormByPage(QueryCcOrderForm queryCcOrderForm);
    /****
     * 4.2.2.5  手持pos机充值订单统计
     * @param queryCzOrderByPosCount
     * @return
     */
    public DodopalDataPage<CzOrderByPosCount> findCzOrderByPosCountByPage(QueryCzOrderByPosCount queryCzOrderByPosCount);
  
    public DodopalResponse<CzOrderByPosCountAll> findCzOrderByPosCountAll(QueryCzOrderByPosCount queryCzOrderByPosCount);
    /**
     * 4.2.2.4  手持pos机充值订单查询导出
     * @param queryCcOrderForm
     * @return
     */
    public DodopalResponse<List<CcOrderForm>> findCcOrderFormExcel(QueryCcOrderForm queryCcOrderForm);
    /**
     * 4.2.2.5  手持pos机充值订单统计导出
     * @param queryCzOrderByPosCount
     * @return
     */
    public DodopalResponse<List<CzOrderByPosCount>> findCzOrderByPosCountExcel(QueryCzOrderByPosCount queryCzOrderByPosCount);
    
}
