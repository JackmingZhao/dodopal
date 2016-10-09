package com.dodopal.product.business.dao;

import java.util.List;

import com.dodopal.product.business.model.PurchaseOrder;
import com.dodopal.product.business.model.RechargeOrder;
import com.dodopal.product.business.model.query.PurchaseOrderQuery;
import com.dodopal.product.business.model.query.RechargeOrderQuery;

public interface ProductOrderForPortalMapper {

    RechargeOrder sumRechargeOrder(RechargeOrderQuery queryDto);
    
    List<RechargeOrder> findRechargeOrderByPage(RechargeOrderQuery queryDto);

    List<RechargeOrder> findRechargeOrderForExport(RechargeOrderQuery queryDto);
    
    PurchaseOrder sumPurchaseOrder(PurchaseOrderQuery queryDto);
    
    List<PurchaseOrder> findPurchaseOrderByPage(PurchaseOrderQuery queryDto);

    List<PurchaseOrder> findPurchaseOrderForExport(PurchaseOrderQuery queryDto);

}
