package com.dodopal.api.product.facade;

import java.util.List;

import com.dodopal.api.product.dto.ProductConsumeOrderDTO;
import com.dodopal.api.product.dto.ProductConsumeOrderDetailDTO;
import com.dodopal.api.product.dto.ProductConsumerOrderForExport;
import com.dodopal.api.product.dto.ProductOrderDTO;
import com.dodopal.api.product.dto.ProductOrderDetailDTO;
import com.dodopal.api.product.dto.ProductOrderForExport;
import com.dodopal.api.product.dto.PurchaseOrderDTO;
import com.dodopal.api.product.dto.RechargeOrderDTO;
import com.dodopal.api.product.dto.query.ProductConsumeOrderQueryDTO;
import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.api.product.dto.query.PurchaseOrderQueryDTO;
import com.dodopal.api.product.dto.query.RechargeOrderQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

/**
 * 公交卡充值订单对外的api接口
 */

public interface ProductOrderFacade {
    
    /**
     * 门户网点充值订单汇总查询
     * @param queryDto
     * @return
     */
    public DodopalResponse<RechargeOrderDTO> sumRechargeOrder(RechargeOrderQueryDTO queryDto);
    
    /**
     * 门户网点充值订单详细记录查询
     * @param queryDto
     * @return
     */
    public DodopalResponse<DodopalDataPage<RechargeOrderDTO>> findRechargeOrderByPage(RechargeOrderQueryDTO queryDto);
    
    /**
     * 门户网点充值订单详细记录导出查询
     * @param queryDto
     * @return
     */
    public DodopalResponse<List<RechargeOrderDTO>> findRechargeOrderForExport(RechargeOrderQueryDTO queryDto);
    
    /**
     * 门户网点消费订单汇总查询
     * @param queryDto
     * @return
     */
    public DodopalResponse<PurchaseOrderDTO> sumPurchaseOrder(PurchaseOrderQueryDTO queryDto);
    
    /**
     * 门户网点消费订单详细记录查询
     * @param queryDto
     * @return
     */
    public DodopalResponse<DodopalDataPage<PurchaseOrderDTO>> findPurchaseOrderByPage(PurchaseOrderQueryDTO queryDto);
    
    /**
     * 门户网点消费订单详细记录导出查询
     * @param queryDto
     * @return
     */
    public DodopalResponse<List<PurchaseOrderDTO>> findPurchaseOrderForExport(PurchaseOrderQueryDTO queryDto);
    
    /**
     * 异常充值订单查询分页  add by shenXiang  2015-11-05
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<DodopalDataPage<ProductOrderDTO>> findExceptionProductOrderByPage(ProductOrderQueryDTO prdOrderQuery);
    
    /**
     * 5.2  产品库中公交卡充值订单 --5.2.1 订单查询
     * 
     * 产品库提供标准的订单查询页面给终端商户（各种网点）和个人。
     * 
     */
    public DodopalResponse<DodopalDataPage<ProductOrderDTO>> findProductOrder(ProductOrderQueryDTO prdOrderQuery);

    /**
     * OSS一卡通充值订单导出查询
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<List<ProductOrderForExport>> findProductOrdersForExport(ProductOrderQueryDTO prdOrderQuery);
    
    /**
     * 充值异常订单导出
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<List<ProductOrderDTO>> findProductExceptionOrdersForExport(ProductOrderQueryDTO prdOrderQuery);
    
    /**
     * 5.2 产品库中公交卡充值订单 --5.2.2 订单查看 用户选择一条公交卡充值订单，点击“查看”按钮，页面向用户展示详情。
     * 订单编号、商户名称、产品编号
     * 、产品名称、充值金额、城市、实付金额、成本价（商户进货价）、订单时间、商户利润（这个字段对个人用户不要显示）、卡号、充值前金额
     * 、充值后金额、充值前账户可用余额、充值后账户可用余额、订单状态、外部订单号（仅限于外部商户）、操作员名称、POS编号、POS备注。
     */
    public DodopalResponse<ProductOrderDetailDTO> findProductOrderDetails(String proOrderNum);
    
    /**
     * 5.2  产品库中公交卡充值订单 --5.2.1 子商户订单查询
     * 
     * 产品库提供标准的订单查询页面给终端商户（各种网点）和个人。
     * 
     */
    public DodopalResponse<DodopalDataPage<ProductOrderDTO>> findChildMerProductOrder(ProductOrderQueryDTO prdOrderQuery);
  /**
   *  5.2 导出子商户业务订单
   * @param prdOrderQuery
   * @return
   */
    public List<ProductOrderDTO> findChildMerProductOrderExcel(ProductOrderQueryDTO prdOrderQuery);
    
    /**
     * 查询 一卡通消费 收单记录(分页)
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<DodopalDataPage<ProductConsumeOrderDTO>> findProductConsumeOrderByPage(ProductConsumeOrderQueryDTO prdOrderQuery);
    
    /**
     * 根据消费订单号 orderNum 查询一卡通消费订单详情
     * @param orderNum 订单号
     * @return
     */
    public DodopalResponse<ProductConsumeOrderDetailDTO> findProductConsumeOrderDetails(String orderNum);
    
    /**
     *  导出 查询 一卡通消费 收单记录
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<List<ProductConsumerOrderForExport>> getConsumerOrderListForExportExcel(ProductConsumeOrderQueryDTO prdOrderQuery);
    
    /**
     * 查询 一卡通消费异常记录
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<DodopalDataPage<ProductConsumeOrderDTO>> findProductConsumeOrderExptionByList(ProductConsumeOrderQueryDTO prdOrderQuery);
    
    /**
     * 导出异常消费记录
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<List<ProductConsumeOrderDTO>> excelExceptionProductOrderExport(ProductConsumeOrderQueryDTO prdOrderQuery);
    
    
    /**
     * 根据商户和pos查询一卡通充值
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<DodopalDataPage<ProductOrderDTO>> findRechargeProductOrderByPage(ProductOrderQueryDTO prdOrderQuery);
}
