package com.dodopal.product.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.product.business.model.CcOrderForm;
import com.dodopal.product.business.model.CzOrderByPosCount;
import com.dodopal.product.business.model.CzOrderByPosCountAll;
import com.dodopal.product.business.model.query.QueryCcOrderForm;
import com.dodopal.product.business.model.query.QueryCzOrderByPosCount;

public interface CcOrderMapper {
    /** 
     * @version 1.0 
     * @描述 根据mercode查找老平台的merid
     * @return  
     */
   public String findMchnitidByMerCode(@Param("merCode")String merCode);
    /****
     * 4.2.2.4  手持pos机充值订单查询
     * @param queryCcOrderForm
     * @return
     */
    public List<CcOrderForm> findAllCcOrderByPage(QueryCcOrderForm queryCcOrderForm);
    /****
     * 4.2.2.5  手持pos机充值订单统计
     * @param queryCzOrderByPosCount
     * @return
     */
    public List<CzOrderByPosCount> findCzOrderByPosCountByPage(QueryCzOrderByPosCount queryCzOrderByPosCount);
    public CzOrderByPosCountAll findCzOrderByPosCountAll(QueryCzOrderByPosCount queryCzOrderByPosCount);
    /**
     * 4.2.2.4  手持pos机充值订单查询导出
     * @param queryCcOrderForm
     * @return
     */
    public List<CcOrderForm> findAllCcOrderExcel(QueryCcOrderForm queryCcOrderForm);
    /**
     * 4.2.2.5  手持pos机充值订单统计导出
     * @param queryCzOrderByPosCount
     * @return
     */
    public List<CzOrderByPosCount> findCzOrderByPosCountExcel(QueryCzOrderByPosCount queryCzOrderByPosCount);
}
