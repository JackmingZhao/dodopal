package com.dodopal.api.card.facade;

import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.common.model.DodopalResponse;

/**
 * 北京V61充值
 */
public interface BJIcdcRechargeCardFacade {

    /**
     * @Title: queryCheckCard
     * @Description: 验卡查询方法
     */
    public DodopalResponse<BJCrdSysOrderDTO> queryCheckCard(BJCrdSysOrderDTO crdDTO);

    /**
     * @Title: createCrdOrderCard
     * @Description: 卡服务订单创建方法
     */
    public DodopalResponse<BJCrdSysOrderDTO> createCrdOrderCard(BJCrdSysOrderDTO crdDTO);
    
    
    /*
     * 二次验卡
     */
    public DodopalResponse<BJCrdSysOrderDTO> getApdu(BJCrdSysOrderDTO crdDTO);
    
    /**
     * @Title: getLoadOrder
     * @Description: 充值申请获得圈存指令方法
     */
    public DodopalResponse<BJCrdSysOrderDTO> getLoadOrder(BJCrdSysOrderDTO crdDTO);

    /**
     * @Title: uploadResultChkUdpOrderFun
     * @Description: 结果上传校验更新订单方法
     */
    public DodopalResponse<BJCrdSysOrderDTO> uploadResultChkUdpOrder(BJCrdSysOrderDTO crdDTO);

    /**
     * @Title: uploadResultChkUdpOrderFun
     * @Description: 充值结果上传方法
     */
    public DodopalResponse<BJCrdSysOrderDTO> uploadResult(BJCrdSysOrderDTO crdDTO);

}
