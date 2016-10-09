package com.dodopal.users.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.users.business.model.DirectMerChant;
import com.dodopal.users.business.model.MerKeyType;
import com.dodopal.users.business.model.Merchant;
import com.dodopal.users.business.model.MerchantUser;
import com.dodopal.users.business.service.MerchantService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:users-business-test-context.xml"})
public class MerchantServiceTest {

    @Autowired
    private MerchantService merchantService;

    @Test
    public void testFindMerchant() {
        try {
            Merchant merchant = new Merchant();
            merchant.setMerName("lifeng");
            List<Merchant> resultList = merchantService.findMerchant(merchant);
            
            if(resultList!=null && resultList.size()>0) {
                System.out.println("查询记录数：" + resultList.size());
                for(Merchant mer : resultList) {
                    System.out.println(ReflectionToStringBuilder.toString(mer, ToStringStyle.MULTI_LINE_STYLE));
                }
            } else {
                System.out.println("未查询到结果");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testRegister() {
        try {
            Merchant merchant = new Merchant();
            merchant.setMerName("junit_test");
            merchant.setMerType("18");
            merchant.setMerClassify("0");
            merchant.setMerProperty("0");
            merchant.setMerAdds("徐汇区虹梅路1905号");
            merchant.setMerLinkUser("李峰");
            merchant.setMerLinkUserMobile("17011111111");
            merchant.setMerEmail("1@1");
            merchant.setMerState("0");
            merchant.setMerRegisterDate(new Date());
            merchant.setMerPro("1000");
            merchant.setMerCity("1001");
            merchant.setDelFlg("0");
            merchant.setActivate("0");
            
            MerchantUser merchantUser = new MerchantUser();
            merchantUser.setUserCode("lifenga");
            merchantUser.setMerUserName("lifenga");
            merchantUser.setMerUserPWD("123");
            merchantUser.setMerUserFlag("0");
            merchantUser.setMerUserType("0");
            merchantUser.setMerUserMobile("17011111111");
            merchantUser.setActivate("0");
            merchant.setMerchantUser(merchantUser);

            MerKeyType merKeyType = new MerKeyType();
            merKeyType.setMerMD5PayPwd("qqq");
            merKeyType.setMerMD5BackPayPWD("ppp");
            merchant.setMerKeyType(merKeyType);
            
            AtomicReference<String> randomPWD = new AtomicReference<String>();
            merchantService.register(merchant, randomPWD);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testCheckExist() {
        try {
            Merchant merchant = new Merchant();
            merchant.setActivate(ActivateEnum.ENABLE.getCode());
            merchant.setMerName("上海一卡通");
            boolean bool = merchantService.checkExist(merchant);
            System.out.println(bool);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    @Test
    public void testDirectMerchant() {
        try {
            Merchant merchant = new Merchant();
            merchant.setMerName("lifeng");
            String merParentCode = "013541000000190";
            String merName = "";
            List<DirectMerChant> resultList = merchantService.findMerchantByParentCode(merParentCode, merName);
            
            if(resultList!=null && resultList.size()>0) {
                System.out.println("查询记录数：" + resultList.size());
                for(DirectMerChant mer : resultList) {
                    System.out.println(ReflectionToStringBuilder.toString(mer, ToStringStyle.MULTI_LINE_STYLE));
                }
            } else {
                System.out.println("未查询到结果");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindPayWayExtId() {
        try {
            String merCode = "041981000000685";
            String payWayExtId = merchantService.findPayWayExtId(merCode);
            System.out.println(payWayExtId);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
