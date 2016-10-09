package com.dodopal.account.dao;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.account.business.dao.AccountRiskControllerDefaultMapper;
import com.dodopal.account.business.model.AccountControllerDefault;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:account-business-test-context.xml"})
public class AccountControllerDefaultMapperTest {
    @Autowired
    private AccountRiskControllerDefaultMapper mapper;

    @Test
    public void testFindAccountRiskControllerDefaultItemListByPage() {
        try {
            List<AccountControllerDefault> resultDto = mapper.findAccountRiskControllerDefaultItemList();

            if (resultDto != null) {
                System.out.println("##########################################");
                for (AccountControllerDefault dto : resultDto) {
                    System.out.println(ReflectionToStringBuilder.toString(dto, ToStringStyle.MULTI_LINE_STYLE));
                }
                System.out.println("##########################################");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdate() {
        try {
            List<AccountControllerDefault> resultDto = mapper.findAccountRiskControllerDefaultItemList();
            if (resultDto != null) {
                AccountControllerDefault acctDefault = resultDto.get(0);
                if (acctDefault != null) {
                    mapper.updateAccountRiskControllerDefaultItem(acctDefault);
                    System.out.println("##########################################");
                    System.out.println("update done.");
                    System.out.println("##########################################");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
