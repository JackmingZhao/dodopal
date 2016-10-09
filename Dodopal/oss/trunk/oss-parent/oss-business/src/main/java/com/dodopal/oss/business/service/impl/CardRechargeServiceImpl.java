package com.dodopal.oss.business.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.oss.business.dao.CardRechargeMapper;
import com.dodopal.oss.business.model.CardRecharge;
import com.dodopal.oss.business.model.CardRechargeDTO;
import com.dodopal.oss.business.model.dto.CardRechargeQuery;
import com.dodopal.oss.business.service.CardRechargeService;

@Service
public class CardRechargeServiceImpl implements CardRechargeService {
    private Logger logger = LoggerFactory.getLogger(CardRechargeServiceImpl.class);
    
    @Autowired
    private CardRechargeMapper cardRechargeMapper;
    
    @Override
    @Transactional(readOnly = true)
    public DodopalDataPage<CardRecharge> findCardRechargeByPage(CardRechargeQuery query) {
        List<CardRecharge> result = cardRechargeMapper.findCardRechargeByPage(query);
        DodopalDataPage<CardRecharge> pages = new DodopalDataPage<CardRecharge>(query.getPage(), result);
        return pages;
    }

    @Override
    @Transactional(readOnly = true)
    public CardRecharge findCardRecharge(String id) {
        if(StringUtils.isNotEmpty(id)){
            CardRecharge cardRecharge = cardRechargeMapper.findCardRecharge(id);
            return cardRecharge;
        }else{
            throw new DDPException("id.empty:\n", "主键不能为空.");
        }
    }

	@Override
	public int findCardRechargeCount(CardRechargeQuery query) {
		// TODO Auto-generated method stub
		return cardRechargeMapper.findCardRechargeCount(query);
	}

	@Override
	public List<CardRechargeDTO> findCardRechargeExcel(CardRechargeQuery query) {
		// TODO Auto-generated method stub
		return cardRechargeMapper.findCardRechargeExcel(query);
	}

}
