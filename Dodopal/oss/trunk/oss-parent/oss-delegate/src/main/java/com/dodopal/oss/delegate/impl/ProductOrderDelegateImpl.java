package com.dodopal.oss.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.ProductOrderDTO;
import com.dodopal.api.product.dto.ProductOrderDetailDTO;
import com.dodopal.api.product.dto.ProductOrderForExport;
import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.api.product.facade.ProductOrderFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.delegate.BaseDelegate;
import com.dodopal.oss.delegate.ProductOrderDelegate;
import com.dodopal.oss.delegate.constant.DelegateConstant;

@Service("productOrderDelegate")
public class ProductOrderDelegateImpl extends BaseDelegate implements ProductOrderDelegate {

    
    /**
     * 异常充值订单查询分页  add by shenXiang  2015-11-05
     */
    @Override
    public DodopalResponse<DodopalDataPage<ProductOrderDTO>> findExceptionProductOrderByPage(ProductOrderQueryDTO prdOrderQuery) {
        ProductOrderFacade facade = getFacade(ProductOrderFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        return facade.findExceptionProductOrderByPage(prdOrderQuery);
    }

    
    /**
     * 订单查询 产品库提供标准的订单查询页面给终端商户（各种网点）和个人。
     */
    @Override
    public DodopalResponse<DodopalDataPage<ProductOrderDTO>> findProductOrder(ProductOrderQueryDTO prdOrderQuery) {
        ProductOrderFacade facade = getFacade(ProductOrderFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        return facade.findProductOrder(prdOrderQuery);
    }

    /**
     * 订单查看 用户选择一条公交卡充值订单，点击“查看”按钮，页面向用户展示详情。 订单编号、商户名称、产品编号
     * 、产品名称、充值金额、城市、实付金额、成本价（商户进货价）、订单时间、商户利润（这个字段对个人用户不要显示）、卡号、充值前金额
     * 、充值后金额、充值前账户可用余额、充值后账户可用余额、订单状态、外部订单号（仅限于外部商户）、操作员名称、POS编号、POS备注。
     */
    @Override
    public DodopalResponse<ProductOrderDetailDTO> findProductOrderDetails(String proOrderNum) {
        ProductOrderFacade facade = getFacade(ProductOrderFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        return facade.findProductOrderDetails(proOrderNum);
    }

	@Override
	public DodopalResponse<List<ProductOrderForExport>> findProductOrdersForExport(ProductOrderQueryDTO prdOrderQuery) {
		ProductOrderFacade facade = getFacade(ProductOrderFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
		return facade.findProductOrdersForExport(prdOrderQuery);
	}

	/**
	 * 充值异常订单导出
	 */
    @Override
    public DodopalResponse<List<ProductOrderDTO>> findProductExceptionOrdersForExport(ProductOrderQueryDTO prdOrderQuery) {
        ProductOrderFacade facade = getFacade(ProductOrderFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        return facade.findProductExceptionOrdersForExport(prdOrderQuery);
    }
}
