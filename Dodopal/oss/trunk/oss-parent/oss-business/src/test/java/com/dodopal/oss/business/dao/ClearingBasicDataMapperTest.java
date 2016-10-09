package com.dodopal.oss.business.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.oss.business.model.ClearingBasicData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:oss-business-test-context.xml"})
public class ClearingBasicDataMapperTest {

    @Autowired
    private ClearingBasicDataMapper mapper;

    @Test
    public void updateCustomerClearingStateAfterAccountDeduct() {
        ClearingBasicData clearingBasicData = new ClearingBasicData();
        clearingBasicData.setOrderNo("O2015110910344745952");
        clearingBasicData.setCustomerNo("059001000000523");
        clearingBasicData.setSupplierClearingFlag("1");
        clearingBasicData.setDdpToSupplierRealAmount(4444444);
        clearingBasicData.setCustomerRealProfit(1111111);
        clearingBasicData.setCustomerClearingFlag("1");
        mapper.updateCustomerClearingStateAfterAccountDeduct(clearingBasicData);
    }

}
