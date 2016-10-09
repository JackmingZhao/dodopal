package com.dodopal.payment.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.payment.business.dao.PayWayCommonMapper;
import com.dodopal.payment.business.model.PayWayCommon;
import com.dodopal.payment.business.model.query.PayCommonQuery;
import com.dodopal.payment.business.service.PayCommonService;

@Service
public class PayCommonServiceImpl implements PayCommonService {
    
    @Autowired
	private PayWayCommonMapper payWayCommonMapper;

    /**
     * 查询
     */
    public DodopalDataPage<PayWayCommon> findPayCommonByPage(PayCommonQuery query) {
        List<PayWayCommon> commonByPage = payWayCommonMapper.findPayWayCommonByPage(query);
        DodopalDataPage<PayWayCommon> dodopalDataPage = new DodopalDataPage<PayWayCommon>(query.getPage(), commonByPage);
        return dodopalDataPage;
    }

    /**
     * 删除
     */
    public int deletePayCommonByIds(List<String> ids) {
        return payWayCommonMapper.deletePayWayCommon(ids);
    }
    
    /**
     * 新增
     */
    public List<Integer> insertPayWayCommon(List<PayWayCommon> commons){
    	List<Integer> list = new ArrayList<Integer>();
    	for(PayWayCommon common : commons){
    		Integer i = payWayCommonMapper.insertPayWayCommon(common);
    		list.add(i);
    	}
    	return list;
    }

	/**
	 * 根据userCode删除
	 */
	public int deletePaywayCommon(String userCode) {
		return payWayCommonMapper.deletePaywayCommon(userCode);
	}


    @Transactional(readOnly = true)
    public List<String> findPaywayCommonByPayWayId(String payWayId) {
        return payWayCommonMapper.findPaywayCommonByPayWayId(payWayId);
    }


}
