package com.dodopal.payment.dao;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.payment.business.dao.PayConfigMapper;
import com.dodopal.payment.business.dao.PayWayGeneralMapper;
import com.dodopal.payment.business.model.PayConfig;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月28日 下午5:11:34
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:payment-business-test-context.xml"})
public class PayConfigMapperTest {
    @Autowired
    PayConfigMapper mapper;
    
    @Test
    public void savePayConfigTest(){
        PayConfig config = new PayConfig();
        config.setActivate("0");
        config.setAfterProceRate(2.1);
        config.setAfterProceRateDate(new Date());
        config.setAnotherAccountCode("1");
        config.setBankGatewayType("1");
        config.setCreateDate(new Date());
        config.setDefaultBank("广发银行");
        config.setGatewayNumber("网关号");
        config.setImageName("imageName");
        config.setPayChannelMark("支付渠道标示");
        config.setPayKey("私钥");
        config.setPayTypeName("0");
        config.setPayType("payType");
        config.setPayWayName("支付方式名称");
        config.setProceRate(2.1);
        mapper.savePayConfig(config);
    }
    @Test
    public void findByAnother(){
        System.out.println(mapper.findPayConfigByAnother("1", "0"));
    }
    
    @Test
    public void findByPayWayName(){
        System.out.println(mapper.findPayConfigByPayWayName("交通银行","10000000000000000044"));
    }
}
