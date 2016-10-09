package com.dodopal.portal.delegate;

import java.util.List;

import com.dodopal.api.product.dto.ChildRechargeOrderSumDTO;
import com.dodopal.api.product.dto.ProductOrderDTO;
import com.dodopal.api.product.dto.ProductOrderDetailDTO;
import com.dodopal.api.product.dto.query.ChildRechargeOrderSumQueryDTO;
import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface ChildMerProductOrderDelegate {
    /**
     *  查询子商户公交卡充值订单查询 (分页)
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<DodopalDataPage<ProductOrderDTO>> findChildMerProductOrderByPage(ProductOrderQueryDTO prdOrderQuery);
    /**
     * 查看公交卡充值订单查看详情
     * @param proOrderNum
     * @return
     */
    public DodopalResponse<ProductOrderDetailDTO> findChildMerProductOrderByCode(String proOrderNum);
    /**
     * 导出子商户业务订单信息
     * @param proOrderNum
     * @return
     */
    public List<ProductOrderDTO> findChildMerProductOrderExcel(ProductOrderQueryDTO prdOrderQuery);


    /**
     * 查询下级商户一卡通充值订单记录汇总信息
     * @param queryDTO
     * @return
     */
    DodopalResponse<DodopalDataPage<ChildRechargeOrderSumDTO>> queryRechargeForChildOrder(ChildRechargeOrderSumQueryDTO queryDTO);
}
