package com.dodopal.portal.business.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dodopal.api.product.dto.query.ChargeOrderQueryDTO;
import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.ChildRechargeOrderSumBean;
import com.dodopal.portal.business.bean.ProductOrderBean;
import com.dodopal.portal.business.bean.ProductOrderDetailBean;
import com.dodopal.portal.business.bean.PurchaseOrderBean;
import com.dodopal.portal.business.bean.RechargeOrderBean;
import com.dodopal.portal.business.bean.query.ChildRechargeOrderSumQuery;
import com.dodopal.portal.business.bean.query.ProductOrderQueryBean;
import com.dodopal.portal.business.bean.query.PurchaseOrderQuery;
import com.dodopal.portal.business.bean.query.RechargeOrderQuery;

public interface ProductOrderService {
    //公交卡充值订单查询 (分页)
    public DodopalResponse<DodopalDataPage<ProductOrderBean>> findProductOrderByPage(ProductOrderQueryBean prdOrderQuery);
    //公交卡充值订单查看详情
    public DodopalResponse<ProductOrderDetailBean> findProductOrderByCode(String proOrderNum);
    
    /**
     * 查询子商户公交卡充值订单查询 (分页)
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<DodopalDataPage<ProductOrderBean>> findChildMerProductOrderByPage(ProductOrderQueryBean prdOrderQuery);
    /**
     * 查询子商户公交卡充值订单查看详情
     * @param proOrderNum
     * @return
     */
    public DodopalResponse<ProductOrderDetailBean> findChildMerProductOrderByCode(String proOrderNum);

    /**
     * 列表画面的 导出按钮 处理
     * 
     * @param response HttpServletResponse
     * @return 导出结果
     */
    public DodopalResponse<String> childProductOrderExcelExport(HttpServletResponse response,ProductOrderQueryDTO prdOrderQuery);
  


    /**
     * 子商户管理， 订单汇总，查询下级商户一卡通充值订单
     * @param query
     * @return
     */
    public DodopalResponse<DodopalDataPage<ChildRechargeOrderSumBean>> queryRechargeForChildOrderSum(ChildRechargeOrderSumQuery query);
    
    
    /**
     * 根据商户和pos查询一卡通充值记录
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<DodopalDataPage<ProductOrderBean>> findRechargeProductOrderByPage(ProductOrderQueryBean prdOrderQuery);
   
    /**
     * 导出    根据商户和pos查询一卡通充值记录
     * @param response
     * @param queryDTO
     * @return
     */
    public DodopalResponse<String> excelExport(HttpServletRequest request,HttpServletResponse response, ChildRechargeOrderSumQuery queryDTO);
    
    /**
     *  * 导出    根据商户和pos查询一卡通充值记录详情
     * @param response
     * @param queryDTO
     * @return
     */
    public DodopalResponse<String> excelExportSumDetail(HttpServletRequest request,HttpServletResponse response, ProductOrderQueryBean queryDTO);

    
    
    /**
     * 门户网点充值订单汇总查询
     * @param queryDto
     * @return
     */
    public DodopalResponse<RechargeOrderBean> sumRechargeOrder(RechargeOrderQuery query);
    
    /**
     * 门户网点充值订单详细记录查询
     * @param queryDto
     * @return
     */
    public DodopalResponse<DodopalDataPage<RechargeOrderBean>> findRechargeOrderByPage(RechargeOrderQuery query);
    
    /**
     * 门户网点充值订单详细记录导出查询
     * @param queryDto
     * @return
     */
    public DodopalResponse<String> exportFindRechargeOrder(HttpServletRequest request,HttpServletResponse response,RechargeOrderQuery query);
    
    /**
     * 门户网点消费订单汇总查询
     * @param queryDto
     * @return
     */
    public DodopalResponse<PurchaseOrderBean> sumPurchaseOrder(PurchaseOrderQuery query);
    
    /**
     * 门户网点消费订单详细记录查询
     * @param queryDto
     * @return
     */
    public DodopalResponse<DodopalDataPage<PurchaseOrderBean>> findPurchaseOrderByPage(PurchaseOrderQuery query);
    
    /**
     * 门户网点消费订单详细记录导出查询
     * @param queryDto
     * @return
     */
    public DodopalResponse<String> exportPurchaseOrder(HttpServletRequest request,HttpServletResponse response,PurchaseOrderQuery query);
 

}
