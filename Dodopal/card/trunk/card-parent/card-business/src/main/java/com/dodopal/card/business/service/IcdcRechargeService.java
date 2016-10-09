package com.dodopal.card.business.service;

import java.util.Map;

import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.card.business.model.CrdSysOrder;
import com.dodopal.card.business.model.CrdSysSupplement;
import com.dodopal.common.model.DodopalResponse;

public interface IcdcRechargeService {
    /**
     * @Title: queryCrdSysOrderCountByPrdOrderId
     * @Description: 通过产品库订单号查询卡服务订单记录数量
     * @param prdOrderNum 产品库订单号
     * @author 袁越
     * @return 订单数量
     */
	public Integer queryCrdSysOrderCountByPrdOrderId(String prdOrderNum);
    
    /**
     * @Title queryCardSysOrderByPrdId
     * @Description 查询卡服务订单
     * @param prdOrderNum 产品库订单号
     * @author 袁越
     * @return
     */
    public CrdSysOrder queryCardSysOrderByPrdId(String prdOrderNum);
    
    /**
     * @Title queryCardSysOrderStatusByPrdId
     * @Description 查询卡服务订单状态
     * @param prdOrderNum
     * @author 袁越
     * @return
     */
    public String queryCardSysOrderStatusByPrdId(String prdOrderNum);
    
    /**
     * @Title queryCrdSysSupplementByCrdId
     * @Description 查询卡服务子订单
     * @param crdSysOrderNum 卡服务订单号
     * @author 袁越
     * @return
     */
    public CrdSysSupplement queryCrdSysSupplementByCrdId(String crdSysOrderNum);
    
    /**
     * @Title: updateCardSysOrderByPrdOrderId 
     * @Description: 更新卡服务订单表：交易应答码，卡服务订单状态，前卡服务订单状态
     * @param value 要更新的数据，键：字段名，值字段值
     * @return 更新条数
     */
	public int updateCardSysOrderByCrdOrderId(Map<String, Object> value);
	
	/**
     * @Title: updateCardSysSupplementByCrdOrderId 
     * @Description: 更新卡服务订单子表
     * @param value 要更新的数据，键：字段名，值字段值
     * @return 更新条数
     */
	public int updateCardSysSupplementByCrdOrderId(Map<String, Object> value);
	
	/**
     * @Title: updateCardSysOrderBackAmtByCrdOrderId 
     * @Description: 更新卡服务订单表：交易后卡余额(单位:分)=交易前卡余额(单位:分)+交易金额(单位:分)
     * @return 更新条数
     */
	public int updateCardSysOrderBackAmtByCrdOrderId(String crdOrderId);
	
	/**
     * @Title: updateResetCardSysOrderBackAmtByCrdOrderId 
     * @Description: 更新卡服务订单表：交易后卡余额(单位:分)=交易前卡余额(单位:分)
     * @return 更新条数
     */
	public int updateResetCardSysOrderBackAmtByCrdOrderId(String crdOrderId);
	
	/**
     * @Title: updateClearCardSysOrderBackAmtByCrdOrderId 
     * @Description: 清空卡服务订单表：交易后卡余额(单位:分)=null
     * @return 更新条数
     */
	public int updateClearCardSysOrderBackAmtByCrdOrderId(String crdOrderId);
	
	/**
     * @Title: getLoadInitFun 
     * @Description: 充值验卡获得圈存初始化指令方法
     * @return 响应
     */
	public DodopalResponse<CrdSysOrderDTO> getLoadInitFun(CrdSysOrderDTO crdDTO);

	/**
     * @Title: uplodResultChkUdpOrderFun 
     * @Description: 结果上传校验更新订单方法
     * @return 响应
     */
	public DodopalResponse<CrdSysOrderDTO> uploadResultChkUdpOrderFun(CrdSysOrderDTO crdDTO);

	/**
     * @Title: queryCardSysOrderByCrdId 
     * @Description: 根据卡服务订单编号查出卡服务订单
     * @return 响应
     */
    public CrdSysOrder queryCardSysOrderByCrdId(String crdOrderNum);
}
