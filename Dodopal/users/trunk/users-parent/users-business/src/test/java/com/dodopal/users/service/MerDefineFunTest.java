package com.dodopal.users.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.users.business.model.MerDefineFun;
import com.dodopal.users.business.service.MerDefineFunService;

/**
 * 类说明 ：
 * @author lifeng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:users-business-test-context.xml"})
public class MerDefineFunTest {
    @Autowired
    private MerDefineFunService merDefineFunService;

    @Test
    public void testBatchAddMerDefineFunList() {
        try {
            List<MerDefineFun> merDefineFunList = new ArrayList<MerDefineFun>();
            MerDefineFun merDefineFun1 =  new MerDefineFun();
            merDefineFun1.setCreateUser("128");
            merDefineFun1.setMerCode("030641000000001");
            merDefineFun1.setMerFunCode("merchant");
            merDefineFun1.setMerFunName("商户管理");
            merDefineFunList.add(merDefineFun1);
            
            MerDefineFun merDefineFun2 =  new MerDefineFun();
            merDefineFun2.setCreateUser("128");
            merDefineFun2.setMerCode("030641000000001");
            merDefineFun2.setMerFunCode("merchant.user");
            merDefineFun2.setMerFunName("用户管理");
            merDefineFunList.add(merDefineFun2);
            
            int num = merDefineFunService.batchAddMerDefineFunList(merDefineFunList);
            System.out.println(num);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
