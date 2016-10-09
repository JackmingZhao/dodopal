package com.dodopal.api.card.facade;

import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.common.model.DodopalResponse;

public interface QueryCheckCardFacade {

    /**
     * @Title: queryCheckCard 
     * @Description: 卡服务验卡查询方法
     * @param  验卡所需参数:messagetype、ver、sysdatetime、productcode、mercode、yktcode、citycode
     *                  cardtype、tradecard、tradestartflag、source、txntype、postype、posid、
     *                  usercode、uid、ats、file05、file06、file11、file15、rand、custom
     * @return 验卡返回参数:messagetype、ver、sysdatetime、productcode、mercode、yktcode、citycode
     *                  cardtype、tradecard、tradestartflag、source、txntype、postype、posid、
     *                  usercode、uid、ats、file05、file06、file11、file15、rand、custom、respcode、
     *                  cardinnerno、cardfaceno、tradeendflag、txnseq、txndate、txntime、cardlimit、
     *                  apdu
     */
    public DodopalResponse<CrdSysOrderDTO> queryCheckCardFun(CrdSysOrderDTO crdDTO);
    
    /**
     * @Title: createCrdOrderCard 
     * @Description: 卡服务订单创建方法
     * @param  创建订单所需参数:
     * @return 创建订单返回参数:
     */
    public DodopalResponse<CrdSysOrderDTO> createCrdOrderCardFun(CrdSysOrderDTO crdDTO);
}
