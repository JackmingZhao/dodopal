package com.dodopal.users.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.users.business.model.MerFunctionInfo;
import com.dodopal.users.business.model.MerRole;
import com.dodopal.users.business.service.MerRoleService;

/**
 * 类说明 ：
 * @author lifeng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:users-business-test-context.xml"})
public class MerRoleServiceTest {
    @Autowired
    private MerRoleService merRoleService;

    @Test
    public void testAddMerRole() {
        try {
            List<MerFunctionInfo> merRoleFunList = new ArrayList<MerFunctionInfo>();
            
            MerFunctionInfo merFunInfo1 = new MerFunctionInfo();
            merFunInfo1.setMerFunCode("100001");
            merFunInfo1.setMerFunName("我的都都宝");
            merRoleFunList.add(merFunInfo1);
            
            MerFunctionInfo merFunInfo2 = new MerFunctionInfo();
            merFunInfo2.setMerFunCode("100002");
            merFunInfo2.setMerFunName("交易记录");
            merRoleFunList.add(merFunInfo2);

            MerRole merRole = new MerRole();
            merRole.setActivate("0");
            merRole.setMerCode("030641000000001");
            merRole.setMerRoleName("我的小跟班");
            merRole.setMerRoleFunList(merRoleFunList);

            int num = merRoleService.addMerRole(merRole);
            System.out.println(num);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDelMerRoleByMerRoleCode() {
        try {
            merRoleService.delMerRoleByMerRoleCode("030641000000001", "10000024");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateMerRole() {
        try {
            List<MerFunctionInfo> merRoleFunList = new ArrayList<MerFunctionInfo>();

            MerFunctionInfo merFunInfo1 = new MerFunctionInfo();
            merFunInfo1.setMerFunCode("100001");
            merFunInfo1.setMerFunName("我的都都宝");
            merRoleFunList.add(merFunInfo1);

            MerFunctionInfo merFunInfo2 = new MerFunctionInfo();
            merFunInfo2.setMerFunCode("100003");
            merFunInfo2.setMerFunName("应用中心");
            merRoleFunList.add(merFunInfo2);

            MerRole merRole = merRoleService.findMerRoleByMerRoleCode("030641000000001", "10000024");
            merRole.setMerRoleFunList(merRoleFunList);

            int num = merRoleService.updateMerRole(merRole);
            System.out.println(num);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
