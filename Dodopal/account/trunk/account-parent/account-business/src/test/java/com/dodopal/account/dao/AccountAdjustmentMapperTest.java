package com.dodopal.account.dao;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.account.business.bean.AccountFundAdjustInfoDTO;
import com.dodopal.account.business.dao.AccountAdjustmentMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:account-business-test-context.xml"})
public class AccountAdjustmentMapperTest {
    @Autowired
    private AccountAdjustmentMapper testMapper;

    @Test
    public void findAccountFundInfo() {
        try {
            AccountFundAdjustInfoDTO resultDto = testMapper.getAccountFundInfoByAdjustCode("014381000000169");

            if (resultDto != null) {
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(resultDto, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
