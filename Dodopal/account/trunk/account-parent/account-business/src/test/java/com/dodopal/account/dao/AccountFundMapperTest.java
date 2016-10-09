package com.dodopal.account.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.account.business.dao.AccountFundMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:account-business-test-context.xml"})
public class AccountFundMapperTest {
    @Autowired
    private AccountFundMapper testMapper;

    @Test
    public void findAccountFundInfo() {
        try {
            List<Map<String, String>> mapList = testMapper.findAccountFundInfo("1","081581000000535");

            if (mapList != null) {
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(mapList, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
                for (Map<String, String> map : mapList) {
                    System.out.println("##########################################");
                    System.out.println(ReflectionToStringBuilder.toString(map, ToStringStyle.MULTI_LINE_STYLE));
                    System.out.println("##########################################");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
