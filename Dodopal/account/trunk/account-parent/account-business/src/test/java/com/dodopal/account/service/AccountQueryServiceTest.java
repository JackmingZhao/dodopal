package com.dodopal.account.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.account.business.service.AccountQueryService;
import com.dodopal.api.account.dto.FundAccountInfoDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:account-business-test-context.xml"})
public class AccountQueryServiceTest {

    @Autowired
    private AccountQueryService testService;

    @Test
    public void findFundAccountInfo() {
        try {
            FundAccountInfoDTO testResult = testService.findFundAccountInfo("1","014381000000169");

            if (testResult != null) {
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(testResult, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}
