package com.dodopal.portal.delegate;

import java.util.List;

import com.dodopal.api.product.dto.ProductOrderDTO;
import com.dodopal.api.product.dto.ProductOrderDetailDTO;
import com.dodopal.api.product.dto.PurchaseOrderDTO;
import com.dodopal.api.product.dto.RechargeOrderDTO;
import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.api.product.dto.query.PurchaseOrderQueryDTO;
import com.dodopal.api.product.dto.query.RechargeOrderQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface ProductOrderDelegate {
    //公交卡充值订单查询 (分页)
    public DodopalResponse<DodopalDataPage<ProductOrderDTO>> findProductOrderByPage(ProductOrderQueryDTO prdOrderQuery);
    //公交卡充值订单查看详情
    public DodopalResponse<ProductOrderDetailDTO> findProductOrderByCode(String proOrderNum);
    
    /**
     * 根据商户和pos号查询一卡通充值
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<DodopalDataPage<ProductOrderDTO>> findRechargeProductOrderByPage(ProductOrderQueryDTO prdOrderQuery);
    
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
 
}
