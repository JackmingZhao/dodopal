package com.dodopal.users.service;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.users.business.service.MerTypeDisableRelationService;

/**
 * 类说明 ：
 * @author lifeng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:users-business-test-context.xml"})
public class MerTypeDisableRelationTest {
    @Autowired
    private MerTypeDisableRelationService merTypeDisableRelationService;

    @Test
    public void testFindAllRelationType() {
        try {
            Map<String, List<String>> result = merTypeDisableRelationService.findDisableRelationType();
            if (result.size() > 0) {
                System.out.println("查询成功!!!");
                System.out.println(result.size());
            } else {
                System.out.println("未查询到数据!!!");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
