package com.dodopal.api.card.facade;

import java.util.List;

import com.dodopal.api.card.dto.CrdSysOrderTDTO;
import com.dodopal.api.card.dto.query.CrdSysOrderQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface QueryCrdSysOrderFacade {
    /**
     * @Title findCrdSysOrderByPage
     * @Description 查询卡服务订单
     * @param crdDTO
     * @return
     */
    public DodopalResponse<DodopalDataPage<CrdSysOrderTDTO>> findCrdSysOrderByPage(CrdSysOrderQueryDTO crdSysOrderQueryDTO);
 
    /**
     * 查询卡服务订单详情
     * @param crdOrderNum
     * @return
     */
   public DodopalResponse<CrdSysOrderTDTO> findCrdSysOrderByCode(String crdOrderNum);
   
   /**
    * 导出卡服务订单
    * @return
    */
   public DodopalResponse<List<CrdSysOrderTDTO>> findCrdSysOrderAll(CrdSysOrderQueryDTO crdSysOrderQueryDTO);
   
   

}
