package com.dodopal.portal.delegate;


import com.dodopal.api.product.dto.YktCardConsumStatisticsDTO;
import com.dodopal.api.product.dto.YktCardConsumTradeDetailDTO;
import com.dodopal.api.product.dto.query.RechargeStatisticsYktQueryDTO;
import com.dodopal.api.product.dto.query.YktCardConsumTradeDetailQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface CardConsumForSupplierDelegate {

    /**
     * 一卡通消费统计查询
     * @param query
     * @return
     */
    public DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>> queryCardConsumForSupplier(RechargeStatisticsYktQueryDTO query);
    
    /**
     * 一卡通消费统计查询
     * @param query
     * @return
     */
    public DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>> exportCardConsumForSupp(RechargeStatisticsYktQueryDTO query);
    /**
     * 一卡通消费交易详细导出
     * @param queryDTO
     * @return
     */
 //  public DodopalResponse<DodopalDataPage<YktCardConsumTradeDetailDTO>> exportCardConsumDetailsForSupp(YktCardConsumTradeDetailQueryDTO queryDTO);

    /**
     * 一卡通消费业务订单汇总
     * @param query
     * @return
     */
    public DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>> findCardConsumCollectByPage(RechargeStatisticsYktQueryDTO query);
}
