package com.dodopal.users.service;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.users.business.model.MerFunctionInfo;
import com.dodopal.users.business.service.MerFunctionInfoService;

/**
 * 类说明 ：
 * @author lifeng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:users-business-test-context.xml"})
public class MerFunctionInfoServiceTest {
    @Autowired
    private MerFunctionInfoService merFunctionInfoService;

    @Test
    public void testBatchFindChildMerFunInfo() {
        try {
            List<String> merFunCodes = Arrays.asList("merchant.user");
            List<MerFunctionInfo> result = merFunctionInfoService.batchFindChildMerFunInfo(merFunCodes);
            if(CollectionUtils.isNotEmpty(result)) {
                System.out.println(result.size());
            } else {
                System.out.println("not found");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
