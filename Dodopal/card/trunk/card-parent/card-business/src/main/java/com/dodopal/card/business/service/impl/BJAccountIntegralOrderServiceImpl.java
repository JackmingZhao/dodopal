package com.dodopal.card.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.card.business.dao.BJAccountIntegralOrderMapper;
import com.dodopal.card.business.model.BJAccountIntegralOrder;
import com.dodopal.card.business.service.BJAccountIntegralOrderService;
import com.dodopal.card.business.util.DateUtil;
import com.dodopal.common.enums.RateCodeEnum;
@Service
public class BJAccountIntegralOrderServiceImpl implements BJAccountIntegralOrderService{
    @Autowired
    BJAccountIntegralOrderMapper bjAccIntOrderMapper;
    
    @Transactional
    @Override
    public BJAccountIntegralOrder createBJAccountIntegralOrder(BJAccountIntegralOrder order){
        RateCodeEnum busnes = RateCodeEnum.getRateTypeByCode(order.getBusinessType());
        StringBuffer crdOrderNum = new StringBuffer();
        //N + 20150428222211 +五位数据库cycle sequence（循环使用）
        if(null!=busnes){
            if(RateCodeEnum.YKT_CONSUME_ACOUNT.getCode().equals(busnes.getCode())){
                crdOrderNum.append("A");
            }else if(RateCodeEnum.YKT_CONSUME_POINT.getCode().equals(busnes.getCode())){
                crdOrderNum.append("I");
            }
        }
        crdOrderNum.append(DateUtil.getCurrTime());
        crdOrderNum.append(bjAccIntOrderMapper.getBJAccIntConsOrderCodeSeq());
        order.setCrdAccIntOrderNum(crdOrderNum.toString());
        //插入订单表
        bjAccIntOrderMapper.saveBJAccountIntegralOrder(order);
        return order;
    }

    @Override
    public void updateBJAccountIntegralOrder(BJAccountIntegralOrder order) {
        bjAccIntOrderMapper.updateBJAccountIntegralOrderByProOrderNum(order);
    }

    @Override
    public List<BJAccountIntegralOrder> findBJAccountIntegralOrder(BJAccountIntegralOrder order) {
        return bjAccIntOrderMapper.findBJAccountIntegralOrder(order);
    }
}
