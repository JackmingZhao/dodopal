package com.dodopal.portal.business.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.CcOrderFormBean;
import com.dodopal.portal.business.bean.CzOrderByPosBean;
import com.dodopal.portal.business.bean.CzOrderByPosCountBean;
import com.dodopal.portal.business.bean.query.QueryCcOrderFormBean;
import com.dodopal.portal.business.bean.query.QueryCzOrderByPosCountBean;

public interface CcOrderService {
    /****
     *  4.2.2.4  手持pos机充值订单查询
     * @param queryCcOrderFormBean
     * @return
     */
    public DodopalResponse<DodopalDataPage<CcOrderFormBean>> findCcOrderByPage(QueryCcOrderFormBean queryCcOrderFormBean);
    /***
     * 4.2.2.5  手持pos机充值订单统计
     * @param queryCzOrderByPosCountBean
     * @return
     */
    public DodopalResponse<DodopalDataPage<CzOrderByPosBean>> findCzOrderByPosByPage(QueryCzOrderByPosCountBean queryCzOrderByPosCountBean);
    public DodopalResponse<CzOrderByPosCountBean> findCzOrderByPosCount(QueryCzOrderByPosCountBean queryCzOrderByPosCountBean);
    /****
     *  4.2.2.4  手持pos机充值订单查询
     * @param queryCcOrderFormBean
     * @return
     */
    public DodopalResponse<String> findCcOrderExcel(HttpServletRequest request,HttpServletResponse response,QueryCcOrderFormBean queryCcOrderFormBean);
   
    /**
     *  4.2.2.5  手持pos机充值订单统计导出
     * @param queryCzOrderByPosCountBean
     * @return
     */
    public DodopalResponse<String> findCzOrderByPosCountExcel(HttpServletRequest request,HttpServletResponse response,QueryCzOrderByPosCountBean queryCzOrderByPosCountBean);

}
