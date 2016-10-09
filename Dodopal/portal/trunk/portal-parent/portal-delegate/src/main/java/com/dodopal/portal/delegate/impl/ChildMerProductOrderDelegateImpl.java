package com.dodopal.portal.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.ChildRechargeOrderSumDTO;
import com.dodopal.api.product.dto.ProductOrderDTO;
import com.dodopal.api.product.dto.ProductOrderDetailDTO;
import com.dodopal.api.product.dto.query.ChildRechargeOrderSumQueryDTO;
import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.api.product.facade.ChildRechargeOrderSumFacade;
import com.dodopal.api.product.facade.ProductOrderFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.ChildMerProductOrderDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;
@Service("childMerProductOrderDelegate")
public class ChildMerProductOrderDelegateImpl extends BaseDelegate implements ChildMerProductOrderDelegate {

    @Override
    public DodopalResponse<DodopalDataPage<ProductOrderDTO>> findChildMerProductOrderByPage(ProductOrderQueryDTO prdOrderQuery) {
        ProductOrderFacade proFacade = getFacade(ProductOrderFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        DodopalResponse<DodopalDataPage<ProductOrderDTO>> response = proFacade.findChildMerProductOrder(prdOrderQuery);
        return response;
    }

    @Override
    public DodopalResponse<ProductOrderDetailDTO> findChildMerProductOrderByCode(String proOrderNum) {
        ProductOrderFacade proFacade = getFacade(ProductOrderFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        DodopalResponse<ProductOrderDetailDTO> response =proFacade.findProductOrderDetails(proOrderNum);
        return response;
    }

    @Override
    public List<ProductOrderDTO> findChildMerProductOrderExcel(ProductOrderQueryDTO prdOrderQuery) {
        ProductOrderFacade proFacade = getFacade(ProductOrderFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        List<ProductOrderDTO> listDto = proFacade.findChildMerProductOrderExcel(prdOrderQuery);
        return listDto;
    }
    
    /**
     *  查询下级商户一卡通充值订单记录汇总信息
     */
    public DodopalResponse<DodopalDataPage<ChildRechargeOrderSumDTO>> queryRechargeForChildOrder(ChildRechargeOrderSumQueryDTO queryDTO) {
        ChildRechargeOrderSumFacade facade = getFacade(ChildRechargeOrderSumFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        DodopalResponse<DodopalDataPage<ChildRechargeOrderSumDTO>> response = facade.queryChildRechargeOrder(queryDTO);
        return response;
    }

}
