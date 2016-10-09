package com.dodopal.oss.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.card.dto.CrdSysConsOrderDTO;
import com.dodopal.api.card.dto.query.CrdSysOrderQueryDTO;
import com.dodopal.api.card.facade.CardConsumeFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.delegate.BaseDelegate;
import com.dodopal.oss.delegate.CrdConsumeOrderDelegate;
import com.dodopal.oss.delegate.constant.DelegateConstant;

@Service("crdConsumeOrderDelegate")
public class CrdConsumeOrderDelegateImpl extends BaseDelegate implements CrdConsumeOrderDelegate{

	/**
     * 查询卡服务消费订单（分页）
     * @param crdSysOrderQuery
     */
	public DodopalResponse<DodopalDataPage<CrdSysConsOrderDTO>> findCrdSysConsOrderByPage(CrdSysOrderQueryDTO crdSysOrderQuery) {
		CardConsumeFacade facade = getFacade(CardConsumeFacade.class, DelegateConstant.FACADE_CARD_URL);
		DodopalResponse<DodopalDataPage<CrdSysConsOrderDTO>> response = facade.findCrdSysConsOrderByPage(crdSysOrderQuery);
		return response;
	}

	/**
     * 查询卡服务消费订单
     * @param crdSysOrderQuery
     */
	public DodopalResponse<List<CrdSysConsOrderDTO>> findCrdSysConsOrder(CrdSysOrderQueryDTO crdSysOrderQuery) {
		CardConsumeFacade facade = getFacade(CardConsumeFacade.class, DelegateConstant.FACADE_CARD_URL);
		DodopalResponse<List<CrdSysConsOrderDTO>> response = facade.findCrdSysConsOrder(crdSysOrderQuery);
		return response;
	}

}
