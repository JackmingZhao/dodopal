package com.dodopal.product.business.service;

import java.util.List;

import com.dodopal.api.product.dto.PurchaseOrderDTO;
import com.dodopal.api.product.dto.RechargeOrderDTO;
import com.dodopal.api.product.dto.query.PurchaseOrderQueryDTO;
import com.dodopal.api.product.dto.query.RechargeOrderQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

/**
 * 门户专用：产品库公交卡充值订单Service
 * @author dodopal
 *
 */
public interface ProductOrderForPortalService {
    
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
