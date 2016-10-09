package com.dodopal.payment.dao;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.payment.business.dao.PaymentMapper;
import com.dodopal.payment.business.model.PayTraTransaction;
import com.dodopal.payment.business.model.Payment;
import com.dodopal.payment.business.model.query.PayTraTransactionQuery;
import com.dodopal.payment.business.model.query.PaymentQuery;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月24日 下午8:58:03
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:payment-business-test-context.xml"})
public class PaymentTest {
    @Autowired
    private PaymentMapper mapper;

    @Test
    public void testFindTest() {
        try {
            PaymentQuery query = new PaymentQuery();
            query.setPayWayName("财");
            query.setPayType("1");
            PageParameter page = new PageParameter();
            page.setPageNo(1);
            page.setPageSize(10);
            query.setPage(page);
            List<Payment> testResult = mapper.findPayMentByPage(query);

            if (testResult != null) {
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(testResult, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
                for (Payment dp : testResult) {
                    System.out.println("##########################################");
                    System.out.println(ReflectionToStringBuilder.toString(dp, ToStringStyle.MULTI_LINE_STYLE));
                    System.out.println("##########################################");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testFindByTranCodeTest() {
        //Payment payment = mapper.findPaymentByTranCode("T001439860954729100004");
        //System.out.println(ReflectionToStringBuilder.toString(payment, ToStringStyle.MULTI_LINE_STYLE));
    }
}
