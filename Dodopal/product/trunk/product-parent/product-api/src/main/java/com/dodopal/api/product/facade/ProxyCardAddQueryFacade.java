package com.dodopal.api.product.facade;

import com.dodopal.api.product.dto.ProxyCardAddDTO;
import com.dodopal.api.product.dto.ProxyCountAllByIDDTO;
import com.dodopal.api.product.dto.ProxyOffLineOrderTbDTO;
import com.dodopal.api.product.dto.query.ProxyCardAddQueryDTO;
import com.dodopal.api.product.dto.query.ProxyOffLineOrderTbQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

/**
 * 联合查询 城市一卡通充值、消费记录查询（供门户调用）
 * @author lenovo
 *
 */
public interface ProxyCardAddQueryFacade {

    /**
     * 城市一卡通充值记录查询 （供门户调用）
     * @param yktPsamQueryDTO
     * @return
     */
    public DodopalResponse<DodopalDataPage<ProxyCardAddDTO>> cardTradeList(ProxyCardAddQueryDTO proxyCardAddQueryDTO);
    
    /**
     * 城市一卡通充值记录 统计 （供门户调用）
     * @param proxyCardAddQueryDTO
     * @return
     */
    public DodopalResponse<ProxyCountAllByIDDTO> findCardTradeListCount(ProxyCardAddQueryDTO proxyCardAddQueryDTO);
    
    
    /**
     * 城市一卡通消费记录查询 （供门户调用）
     * @param proxyCardAddQueryDTO
     * @return
     */
    public DodopalResponse<DodopalDataPage<ProxyOffLineOrderTbDTO>> offLineTradeList(ProxyOffLineOrderTbQueryDTO proxyOffLineOrderTbQueryDTO);
  
    /**
     * 城市一卡通消费记录 统计（供门户调用）
     * @param proxyCardAddQueryDTO
     * @return
     */
    public DodopalResponse<ProxyCountAllByIDDTO> findoffLineTradeListCount(ProxyOffLineOrderTbQueryDTO proxyOffLineOrderTbQueryDTO);
    
    
}
