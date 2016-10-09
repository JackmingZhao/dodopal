package com.dodopal.portal.business.service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dodopal.api.product.dto.query.RechargeStatisticsYktQueryDTO;
import com.dodopal.api.product.dto.query.YktCardConsumTradeDetailQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.YktCardConsumStatisticsBean;
import com.dodopal.portal.business.bean.query.RechargeStatisticsYktQuery;

public interface CardConsumForSupplierService {
    /**
     * 一卡通消费统计查询
     * @param query
     * @return
     */
    public DodopalResponse<DodopalDataPage<YktCardConsumStatisticsBean>> queryCardConsumForSupplier(RechargeStatisticsYktQuery query);
    
    /**
     * 一卡通消费统计导出
     * @param response
     * @param merCountQuery
     * @return
     */

    public DodopalResponse<String> exportCardConsumForSupp(HttpServletRequest request,HttpServletResponse response,RechargeStatisticsYktQueryDTO queryDTO);
    
    
    /**
     * 一卡通消费业务订单汇总
     * @param query
     * @return
     */
    public DodopalResponse<DodopalDataPage<YktCardConsumStatisticsBean>> findCardConsumCollectByPage(RechargeStatisticsYktQuery query);
    
    
  //  public DodopalResponse<String> ForSupp(HttpServletRequest request,HttpServletResponse response,YktCardConsumTradeDetailQueryDTO queryDTO);
    /**
     * 业务订单汇总一卡通消费导出
     * @param request
     * @param response
     * @param queryDTO
     * @return
     */
    public DodopalResponse<String> exportCardConsumCollect(HttpServletRequest request,HttpServletResponse response,RechargeStatisticsYktQueryDTO queryDTO);

    public DodopalResponse<String> exportCardConsumDetailsForSupp(HttpServletRequest request, HttpServletResponse response,YktCardConsumTradeDetailQueryDTO queryDTO);
    
}
