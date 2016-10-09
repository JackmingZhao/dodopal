package com.dodopal.oss.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.dao.AcctReChargeClearingMapper;
import com.dodopal.oss.business.dao.CardConsumeClearingMapper;
import com.dodopal.oss.business.model.AcctReChargeClearing;
import com.dodopal.oss.business.model.AcctReChargeClearingDTO;
import com.dodopal.oss.business.model.CardConsumeClearing;
import com.dodopal.oss.business.model.CardConsumeClearingDTO;
import com.dodopal.oss.business.model.dto.AcctReChargeClearingQuery;
import com.dodopal.oss.business.model.dto.CardConsumeClearingQuery;
import com.dodopal.oss.business.model.dto.CardRechargeQuery;
import com.dodopal.oss.business.service.*;

@Service
public class ClearingDetailServiceImpl implements ClearingDetailService{
    @Autowired
    private AcctReChargeClearingMapper  acctChargeClearingMapper;
    @Autowired
    private CardConsumeClearingMapper cardConsumeClearingMapper;
    /**
     * 按条件查询账户充值清分产品列表
     * @param icdcPrdQuery
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public DodopalResponse<DodopalDataPage<AcctReChargeClearing>> queryAcctRechargeClearingPage(AcctReChargeClearingQuery acctChargeClearingQuery){
        DodopalResponse<DodopalDataPage<AcctReChargeClearing>> pages =new DodopalResponse<DodopalDataPage<AcctReChargeClearing>>();
        List<AcctReChargeClearing> result = acctChargeClearingMapper.queryAcctRechargeClearingPage(acctChargeClearingQuery);
        DodopalDataPage<AcctReChargeClearing> page = new DodopalDataPage<AcctReChargeClearing>(acctChargeClearingQuery.getPage(), result);
        pages.setResponseEntity(page);
        return pages;
    }
  /**
     * 按条件查询账户充值清分产品信息详情
     * @param code
     * @return
     */

    public DodopalResponse<AcctReChargeClearing> queryAcctRechargeClearingDetails(String id) {
        DodopalResponse<AcctReChargeClearing> response= new DodopalResponse<AcctReChargeClearing>();
        AcctReChargeClearing acctReChargeClearing =  acctChargeClearingMapper.queryAcctRechargeClearingDetails(id);
        response.setResponseEntity(acctReChargeClearing);
        return response;
    }
    
    public DodopalResponse<DodopalDataPage<CardConsumeClearing>> queryCardConsumeClearingPage(CardConsumeClearingQuery query){
        DodopalResponse<DodopalDataPage<CardConsumeClearing>> pages =new DodopalResponse<DodopalDataPage<CardConsumeClearing>>();
        List<CardConsumeClearing> result = cardConsumeClearingMapper.queryCardConsumeClearingPage(query);
        DodopalDataPage<CardConsumeClearing> page = new DodopalDataPage<CardConsumeClearing>(query.getPage(), result);
        pages.setResponseEntity(page);
        return pages;
        
    }
    /**
     * 根据产品编号查询一卡通消费产品信息
     * @param proCode
     * @return
     */
    public DodopalResponse<CardConsumeClearing> queryCardConsumeClearingDetails(String id){
        DodopalResponse<CardConsumeClearing> response= new DodopalResponse<CardConsumeClearing>();
        CardConsumeClearing cardConsumeClearing =  cardConsumeClearingMapper.queryCardConsumeClearingDetails(id);
        response.setResponseEntity(cardConsumeClearing);
        return response;
        
    }
	@Override
	public int queryAcctRechargeClearingCount(AcctReChargeClearingQuery query) {
		// TODO Auto-generated method stub
		return acctChargeClearingMapper.queryAcctRechargeClearingCount(query);
	}
	@Override
	public List<AcctReChargeClearingDTO> queryAcctRechargeClearingExcel(AcctReChargeClearingQuery query) {
		// TODO Auto-generated method stub
		return acctChargeClearingMapper.queryAcctRechargeClearingExcel(query);
	}
	@Override
	public int findCardConsumeClearingCount(CardConsumeClearingQuery query) {
		// TODO Auto-generated method stub
		return cardConsumeClearingMapper.findCardConsumeClearingCount(query);
	}
	@Override
	public List<CardConsumeClearingDTO> findCardConsumeClearingExcel(CardConsumeClearingQuery query) {
		// TODO Auto-generated method stub
		return cardConsumeClearingMapper.findCardConsumeClearingExcel(query);
	}
    
}
