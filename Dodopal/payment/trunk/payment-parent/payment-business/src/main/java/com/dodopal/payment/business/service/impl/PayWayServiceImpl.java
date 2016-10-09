package com.dodopal.payment.business.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.payment.business.dao.PayWayMapper;
import com.dodopal.payment.business.model.PayWay;
import com.dodopal.payment.business.service.PayWayService;
@Service
public class PayWayServiceImpl implements PayWayService {

    @Autowired
   private PayWayMapper payWayMapper;

    //查询外接商户的外接支付方式
    @Transactional(readOnly = true)
    public List<PayWay> findPayWayExternal(String merCode) {
        return payWayMapper.findPayWayExternal(merCode);
    }

    //查询非外接商户的通用支付方式
    @Transactional(readOnly = true)
    public List<PayWay> findPayWayGeneral(String payType) {
        return payWayMapper.findPayWayGeneral(payType);
    }

	//常用支付方式（外接）
    @Transactional(readOnly = true)
	public List<PayWay> findCommonExternal(String merCode) {
		return payWayMapper.findCommonExternal(merCode);
	}

	//常用支付方式（非外接）
    @Transactional(readOnly = true)
	public List<PayWay> findCommonGeneral(String userCode) {
		return payWayMapper.findCommonGeneral(userCode);
	}

    @Override
    @Transactional(readOnly = true)
    public List<PayWay> findPayWayByPayType(String merCode, String payType) {
        if(StringUtils.isBlank(merCode)){
            return payWayMapper.findPayWayGeneralByPayType(payType);
        }else{
            return payWayMapper.findPayWayExternalByPayType(merCode, payType);
        }
    }

}
