package com.dodopal.account.dao;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.account.business.dao.AccountChangeChildMerMapper;
import com.dodopal.account.business.model.AccountChildMerchant;
import com.dodopal.account.business.model.ChildMerchantAccountChange;
import com.dodopal.account.business.model.query.ChildMerFundChangeQuery;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:account-business-test-context.xml"})
public class AccountChangeChildMerMapperTest {
    
    @Autowired
    private AccountChangeChildMerMapper testMapper;

    @Test
    public void findAccountFundInfo() {
        try {
            ChildMerFundChangeQuery query = new ChildMerFundChangeQuery();
            query.setMerParentCode("013541000000190");
            query.setFundType("0");
            List<ChildMerchantAccountChange> mapList = testMapper.findAccountChangeChildMerByList(query);//testMapper.findAccountChildMerByList("013541000000190", "","0");

            if (mapList != null) {
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(mapList, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
                for (ChildMerchantAccountChange accountChildMerchant : mapList) {
                    System.out.println("##########################################");
                    System.out.println(ReflectionToStringBuilder.toString(accountChildMerchant, ToStringStyle.MULTI_LINE_STYLE));
                    System.out.println("##########################################");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}
