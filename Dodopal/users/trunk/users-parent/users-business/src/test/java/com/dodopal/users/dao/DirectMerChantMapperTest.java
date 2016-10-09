package com.dodopal.users.dao;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.users.business.dao.DirectMerChantMapper;
import com.dodopal.users.business.model.DirectMerChant;
import com.dodopal.users.business.model.Merchant;

/**
 * 类说明 :
 * @author lifeng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:users-business-test-context.xml"})
public class DirectMerChantMapperTest {

    @Autowired
    private DirectMerChantMapper mapper;

    @Test
    public void testFindMerchant() {
        try {
            String merParentCode = "013541000000190";
            String merName = "";
            List<DirectMerChant> result = mapper.findMerchantByParentCode(merParentCode, merName);
            if (result != null && result.size() > 0) {
                for (DirectMerChant mer : result) {
                    System.out.println(ReflectionToStringBuilder.toString(mer, ToStringStyle.MULTI_LINE_STYLE));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}

