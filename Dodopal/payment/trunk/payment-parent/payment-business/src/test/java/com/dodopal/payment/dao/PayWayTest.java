package com.dodopal.payment.dao;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.payment.business.dao.PayWayMapper;
import com.dodopal.payment.business.model.PayWay;
import com.dodopal.payment.business.model.Payment;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年8月13日 下午3:14:33
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:payment-business-test-context.xml"})
public class PayWayTest {
    @Autowired
    private PayWayMapper mapper;

//    @Test
//    public void selectPayWayTest(){
//       /* List<PayWay>testResult = mapper.findCommonGeneral();
//        
//        if (testResult != null) {
//            System.out.println("##########################################");
//            System.out.println(ReflectionToStringBuilder.toString(testResult, ToStringStyle.MULTI_LINE_STYLE));
//            System.out.println("##########################################");
//            for (PayWay dp : testResult) {
//                System.out.println("##########################################");
//                System.out.println(ReflectionToStringBuilder.toString(dp, ToStringStyle.MULTI_LINE_STYLE));
//                System.out.println("##########################################");
//            }
//        }*/
//    }
    
    
    @Test
    public void savePayWayTest(){
        List<PayWay>testResult = mapper.findPayWayGeneral("2");
        
        if (testResult != null) {
            System.out.println("##########################################");
            System.out.println(ReflectionToStringBuilder.toString(testResult, ToStringStyle.MULTI_LINE_STYLE));
            System.out.println("##########################################");
            for (PayWay dp : testResult) {
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(dp, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
            }
        }
    }
    
}
