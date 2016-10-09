package com.dodopal.product.dao;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.product.business.dao.ProductYktLimitInfoMapper;
import com.dodopal.product.business.model.ProductYktLimitBatchInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:product-business-test-context.xml"})
public class YktLimitBatchInfoMapperTest {

    @Autowired
    private ProductYktLimitInfoMapper productYktLimitInfoMapper;

    @Test
    public void testFindPrdProductYktByCityCode() {
        ProductYktLimitBatchInfo limitbatchInfo = new ProductYktLimitBatchInfo();
        limitbatchInfo.setApplyAmtLimit(null);
        limitbatchInfo.setApplyDate(new Date());
        limitbatchInfo.setApplyExplaination(null);
        limitbatchInfo.setApplyUser("123");
        limitbatchInfo.setApplyUserName("ceshi");
        limitbatchInfo.setAuditDate(new Date());
        limitbatchInfo.setAuditExplaination("null");
        limitbatchInfo.setAuditState("2");
        limitbatchInfo.setAuditUser("312");
        limitbatchInfo.setAuditUserName("cehsi 321");
        limitbatchInfo.setBatchCode(1);
        limitbatchInfo.setCreateUser("4352342");
        limitbatchInfo.setFinancialPayDate(new Date());
        limitbatchInfo.setFinancialPayFee((long)555);
        limitbatchInfo.setPaymentChannel("qudao");
        limitbatchInfo.setRemark("beizhu");
        limitbatchInfo.setYktAddLimit((long)234);
        limitbatchInfo.setYktCode("2222");
        limitbatchInfo.setYktName("hehe");
        int num = productYktLimitInfoMapper.addProductYktLimitBatchInfo(limitbatchInfo);
        
        ProductYktLimitBatchInfo limitbatchInfo2 = new ProductYktLimitBatchInfo();
        limitbatchInfo2.setApplyAmtLimit((long)256);
        limitbatchInfo2.setApplyDate(new Date());
        limitbatchInfo2.setApplyUser("123");
        limitbatchInfo2.setApplyUserName("ceshi");
        limitbatchInfo2.setAuditState("0");
        limitbatchInfo2.setBatchCode(2);
        limitbatchInfo2.setCreateUser("4352342");
        limitbatchInfo2.setYktCode("2222");
        limitbatchInfo2.setYktName("hehe");
        int num2 = productYktLimitInfoMapper.addProductYktLimitBatchInfo(limitbatchInfo2);
        
        System.out.println(num);
    }
    
  
}
