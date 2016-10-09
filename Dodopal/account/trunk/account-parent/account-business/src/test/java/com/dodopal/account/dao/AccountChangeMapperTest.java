package com.dodopal.account.dao;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.account.business.dao.AccountChangeMapper;
import com.dodopal.account.business.model.AccountChange;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:account-business-test-context.xml"})
public class AccountChangeMapperTest {
    @Autowired
    private AccountChangeMapper testMapper;

    @Test
    public void testFindTest() {
        try {
            com.dodopal.account.business.model.query.FundChangeQuery test = new com.dodopal.account.business.model.query.FundChangeQuery();
            test.setAcid("");
            test.setChangeAmountMax(100);
            test.setChangeAmountMin(50);
            List<AccountChange> testResult = testMapper.findFundsChangeRecordsByPage(test);

            if (testResult != null) {
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(testResult, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
                for (AccountChange dp : testResult) {
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

}
