package com.dodopal.payment.dao;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.common.enums.TranTypeEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.payment.business.dao.PayTraTransactionMapper;
import com.dodopal.payment.business.model.PayTraTransaction;
import com.dodopal.payment.business.model.query.PayTraTransactionQuery;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月24日 下午4:37:33
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:payment-business-test-context.xml"})
public class PayTraTransactionMapperTest {
    @Autowired
    private PayTraTransactionMapper mapper;

    @Test
    public void testFindTest() {
        try {
            PayTraTransactionQuery query = new PayTraTransactionQuery();
            query.setMerOrUserName("k2s");
            PageParameter page = new PageParameter();
            page.setPageNo(1);
            page.setPageSize(10);
            query.setPage(page);
            List<PayTraTransaction> testResult = mapper.findPayTraTransactionByPage(query);

            if (testResult != null) {
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(testResult, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
                for (PayTraTransaction dp : testResult) {
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
    public void testFindListTest() {
        try {
            PayTraTransaction transactionQuery = new PayTraTransaction();
            transactionQuery.setUserType("1");
            List<PayTraTransaction> testResult = mapper.findPayTraTransactionList(transactionQuery);

            if (testResult != null) {
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(testResult, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
                for (PayTraTransaction dp : testResult) {
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
        PayTraTransaction transactionQuery = mapper.findPayTraTransactionByTranCode("T001439860954729100004");
        System.out.println(ReflectionToStringBuilder.toString(transactionQuery, ToStringStyle.MULTI_LINE_STYLE));

    }
    
    
    
    @Test
    public void testFindListTest1() {
        try {
            PayTraTransaction transactionQuery = new PayTraTransaction();
            transactionQuery.setUserType("1");
            List<PayTraTransaction> testResult = mapper.findPayTraTransactionByCode("1","014381000000169","");

            if (testResult != null) {
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(testResult, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
                for (PayTraTransaction dp : testResult) {
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
    public void TestUpdate(){
    	
    	  PayTraTransaction transactionQuery = new PayTraTransaction();
    	transactionQuery.setId("10000000000000000433");
        transactionQuery.setTranInStatus("6");
        transactionQuery.setTranOutStatus("5");
        mapper.updateTranStatus(transactionQuery); 
    }
}
