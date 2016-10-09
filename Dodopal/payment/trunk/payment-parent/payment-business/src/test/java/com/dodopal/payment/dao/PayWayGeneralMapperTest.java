package com.dodopal.payment.dao;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.payment.business.dao.PayWayGeneralMapper;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月28日 下午5:10:33
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:payment-business-test-context.xml"})
public class PayWayGeneralMapperTest {
    @Autowired
    PayWayGeneralMapper mapper;
}
