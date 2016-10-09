package com.dodopal.portal.delegate.impl;

import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.YktCardConsumStatisticsDTO;
import com.dodopal.api.product.dto.query.RechargeStatisticsYktQueryDTO;
import com.dodopal.api.product.facade.ManagementForSupplierFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.CardConsumForSupplierDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;

@Service("CardConsumForSupplierDelegate")
public class CardConsumForSupplierDelegateImpl extends BaseDelegate implements CardConsumForSupplierDelegate{
    /**
     * 一卡通消费统计查询
     * @param query
     * @return
     */
    public DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>> queryCardConsumForSupplier(
            RechargeStatisticsYktQueryDTO query) {
        ManagementForSupplierFacade facade = getFacade(ManagementForSupplierFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>> response = facade.queryCardConsumForSupplier(query);
        return response;
    }
    /**
     * 一卡通消费统计导出
     * @param query
     * @return
     */
    public DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>> exportCardConsumForSupp(
            RechargeStatisticsYktQueryDTO query) {
        ManagementForSupplierFacade facade = getFacade(ManagementForSupplierFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>> response = facade.exportCardConsumForSupp(query);
        return response;
    }

    /**
     * 一卡通消费业务订单汇总
     * @param query
     * @return
     */
	public DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>> findCardConsumCollectByPage(RechargeStatisticsYktQueryDTO query) {
		ManagementForSupplierFacade facade = getFacade(ManagementForSupplierFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
		DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>> response = facade.findCardConsumCollectByPage(query);
        return response;
	}

    
}
