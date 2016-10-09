package com.dodopal.portal.delegate;

import com.dodopal.api.product.dto.ProductOrderDTO;
import com.dodopal.api.product.dto.ProductOrderDetailDTO;
import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface ProductOrderDelegate {
    //公交卡充值订单查询 (分页)
    public DodopalResponse<DodopalDataPage<ProductOrderDTO>> findProductOrderByPage(ProductOrderQueryDTO prdOrderQuery);
    //公交卡充值订单查看详情
    public DodopalResponse<ProductOrderDetailDTO> findProductOrderByCode(String proOrderNum);
}
