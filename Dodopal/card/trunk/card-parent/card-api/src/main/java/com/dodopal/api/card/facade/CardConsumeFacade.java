package com.dodopal.api.card.facade;

import java.util.List;

import com.dodopal.api.card.dto.CrdSysConsOrderDTO;
import com.dodopal.api.card.dto.query.CrdSysOrderQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface CardConsumeFacade {
	/**
     * 查询卡服务消费订单（分页）
     * @param crdSysOrderQuery
     */
	public DodopalResponse<DodopalDataPage<CrdSysConsOrderDTO>> findCrdSysConsOrderByPage(CrdSysOrderQueryDTO crdSysOrderQuery);
	/**
     * 查询卡服务消费订单
     * @param crdSysOrderQuery
     */
	public DodopalResponse<List<CrdSysConsOrderDTO>> findCrdSysConsOrder(CrdSysOrderQueryDTO crdSysOrderQuery);
}
