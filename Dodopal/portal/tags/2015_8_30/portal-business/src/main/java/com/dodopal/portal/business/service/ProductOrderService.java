package com.dodopal.portal.business.service;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.ProductOrderBean;
import com.dodopal.portal.business.bean.ProductOrderDetailBean;
import com.dodopal.portal.business.bean.query.ProductOrderQueryBean;

public interface ProductOrderService {
    //公交卡充值订单查询 (分页)
    public DodopalResponse<DodopalDataPage<ProductOrderBean>> findProductOrderByPage(ProductOrderQueryBean prdOrderQuery);
    //公交卡充值订单查看详情
    public DodopalResponse<ProductOrderDetailBean> findProductOrderByCode(String proOrderNum);
}
