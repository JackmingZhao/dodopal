package com.dodopal.api.card.facade;

import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.common.model.DodopalResponse;

public interface IcdcRechargeCardFacade {
    
    /**
     * @Title: getLoadInit 
     * @Description: 获得圈存初始化指令
     * @param  所需参数:
     * @return 返回参数:
     */
    public DodopalResponse<CrdSysOrderDTO> getLoadInitFun(CrdSysOrderDTO crdDTO);
    
    /**
     * @Title: getLoadOrder 
     * @Description: 获得圈存指令
     * @param  所需参数:
     * @return 返回参数:
     */
    public DodopalResponse<CrdSysOrderDTO> getLoadOrderFun(CrdSysOrderDTO crdDTO);
    
    
    /**
     * @Title: uplodResultChkUdpOrder 
     * @Description: 结果上传校验更新订单
     * @param  所需参数:
     * @return 返回参数:
     */
    public DodopalResponse<CrdSysOrderDTO> uploadResultChkUdpOrderFun(CrdSysOrderDTO crdDTO);
    
    
    
    /**
     * @Title: uplodResult 
     * @Description: 充值结果上传
     * @param  所需参数:
     * @return 返回参数:
     */
    public DodopalResponse<CrdSysOrderDTO> uploadResultFun(CrdSysOrderDTO crdDTO);
    
}
