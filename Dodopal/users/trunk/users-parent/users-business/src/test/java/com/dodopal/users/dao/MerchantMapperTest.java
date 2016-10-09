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

import com.dodopal.users.business.dao.MerchantMapper;
import com.dodopal.users.business.model.Merchant;

/**
 * 类说明 :
 * @author lifeng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:users-business-test-context.xml"})
public class MerchantMapperTest {

    @Autowired
    private MerchantMapper mapper;

    @Test
    public void testFindMerchant() {
        try {
            Merchant merchant = new Merchant();
            merchant.setId("1");

            List<Merchant> result = mapper.findMerchant(merchant);
            if (result != null && result.size() > 0) {
                for (Merchant mer : result) {
                    System.out.println(ReflectionToStringBuilder.toString(mer, ToStringStyle.MULTI_LINE_STYLE));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindMerchantById() {
        try {
            Merchant result = mapper.findMerchantById("1");
            if (result != null) {
                System.out.println(ReflectionToStringBuilder.toString(result, ToStringStyle.MULTI_LINE_STYLE));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateMerchantBatch() {
        try {
            Merchant merchant = new Merchant();
            merchant.setActivate("0");
            int num = mapper.batchUpdateMerchant(merchant, Arrays.asList("1", "2"));
            if (num > 0) {
                System.out.println("成功更新:" + num);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddMerchant() {
        try {
            Merchant merchant = new Merchant();
            merchant.setActivate("0");
            merchant.setMerCode("3");
            merchant.setMerName("33");
            merchant.setMerState("1");
            merchant.setDelFlg("0");
            merchant.setMerClassify("0");
            merchant.setMerProperty("0");

            int num = mapper.addMerchant(merchant);
            if (num > 0) {
                System.out.println("成功更新:" + num);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateMerchant() {
        try {
            Merchant merchant = mapper.findMerchantById("10000000000000000005");
            merchant.setMerName("lifeng");
            merchant.setMerTelephone(null);
            merchant.setMerEmail(null);
            merchant.setUpdateUser("admin");
            merchant.setUpdateDate(new Date());

            int num = mapper.updateMerchant(merchant);
            if (num > 0) {
                System.out.println("成功更新:" + num);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetMerCodeSeq() {
        try {
            String merCodeSeq = mapper.getMerCodeSeq();
            System.out.println(merCodeSeq);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
