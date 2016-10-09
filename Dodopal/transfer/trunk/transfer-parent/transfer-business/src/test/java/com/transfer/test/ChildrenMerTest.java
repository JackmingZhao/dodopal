package com.transfer.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.transfer.business.service.TransferChilrenMerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:transfer-business-test-context.xml"})
public class ChildrenMerTest {
    
    @Autowired
    private TransferChilrenMerService transferChilrenMerService;

    @Test
    public void testTransfer() {
        try {
            System.out.println("===============111======================");
            transferChilrenMerService.transferChilrenMerService();
            System.out.println("===============222======================");
        }
        catch (Exception e) {
            System.out.println("===============333======================");
            System.out.println(e.getMessage());
        }

    }

}
