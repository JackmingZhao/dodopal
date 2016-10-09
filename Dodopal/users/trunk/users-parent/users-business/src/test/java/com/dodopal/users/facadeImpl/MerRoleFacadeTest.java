package com.dodopal.users.facadeImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.api.users.dto.MerFunctionInfoDTO;
import com.dodopal.api.users.dto.MerRoleDTO;
import com.dodopal.api.users.dto.query.MerRoleQueryDTO;
import com.dodopal.api.users.facade.MerRoleFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.hessian.RemotingCallUtil;

/**
 * 类说明 ：
 * @author lifeng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:users-business-test-context.xml"})
public class MerRoleFacadeTest {
    private MerRoleFacade merRoleFacade;
    
    @Before
    public void setUp() {
        String hessianUrl = "http://localhost:8082/users-web/hessian/index.do?id=";
        merRoleFacade = RemotingCallUtil.getHessianService(hessianUrl, MerRoleFacade.class);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testFindMerRoleByPage() {
        try {
            PageParameter page = new PageParameter();
            page.setPageNo(2);
            page.setPageSize(3);

            MerRoleQueryDTO merRoleQueryDTO = new MerRoleQueryDTO();
            merRoleQueryDTO.setMerCode("030641000000001");
            merRoleQueryDTO.setPage(page);

            DodopalResponse<DodopalDataPage<MerRoleDTO>> response = merRoleFacade.findMerRoleByPage(merRoleQueryDTO);
            if (ResponseCode.SUCCESS.equals(response.getCode()) && response.getResponseEntity()!=null) {
                List<MerRoleDTO> resultList = response.getResponseEntity().getRecords();
                for(MerRoleDTO merRole : resultList) {
                    System.out.println(ReflectionToStringBuilder.toString(merRole, ToStringStyle.MULTI_LINE_STYLE));
                }
            } else {
                System.out.println(response.getCode());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBatchDelMerRoleByCodes() {
        try {
            String merCode = "030641000000001";
            List<String> merRoleCodes = Arrays.asList("10000004","10000005");
            DodopalResponse<Integer> response = merRoleFacade.batchDelMerRoleByCodes(merCode, merRoleCodes);
            if (ResponseCode.SUCCESS.equals(response.getCode()) && response.getResponseEntity()!=null) {
                System.out.println(response.getResponseEntity());
            } else {
                System.out.println(response.getCode());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindMerRoleByMerRoleCode() {
        try {
            String merCode = "030641000000001";
            String merRoleCode = "10000025";
            DodopalResponse<MerRoleDTO> response = merRoleFacade.findMerRoleByMerRoleCode(merCode, merRoleCode);
            if (ResponseCode.SUCCESS.equals(response.getCode()) && response.getResponseEntity()!=null) {
                System.out.println(ReflectionToStringBuilder.toString(response.getResponseEntity(), ToStringStyle.MULTI_LINE_STYLE));
            } else {
                System.out.println(response.getCode());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindMerFuncInfoByUserCode() {
        try {
            String merUserCode = "16533100000000001";
            DodopalResponse<List<MerFunctionInfoDTO>> response = merRoleFacade.findMerFuncInfoByUserCode(merUserCode);
            List<MerFunctionInfoDTO> merFunList = response.getResponseEntity();
            if (ResponseCode.SUCCESS.equals(response.getCode()) && response.getResponseEntity()!=null) {
                for(MerFunctionInfoDTO merFunInfoDTOTemp : merFunList) {
                    System.out.println(ReflectionToStringBuilder.toString(merFunInfoDTOTemp, ToStringStyle.MULTI_LINE_STYLE));
                }
            } else {
                System.out.println(response.getCode());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testAddMerRole() {
        try {
            MerRoleDTO merRoleDTO = new MerRoleDTO();
            merRoleDTO.setMerCode("030641000000001");
            merRoleDTO.setMerRoleName("测试角色5");
            merRoleDTO.setDescription("测试角色5描述");
            
            List<MerFunctionInfoDTO> merFunInfoList = new ArrayList<MerFunctionInfoDTO>();
            MerFunctionInfoDTO merFun1 = new MerFunctionInfoDTO();
            merFun1.setMerFunCode("100001");
            merFun1.setMerFunName("我的都都宝");
            merFunInfoList.add(merFun1);

            merRoleDTO.setMerRoleFunDTOList(merFunInfoList);

            DodopalResponse<Integer> response = merRoleFacade.addMerRole(merRoleDTO);
            if (ResponseCode.SUCCESS.equals(response.getCode()) && response.getResponseEntity()!=null) {
                System.out.println(response.getResponseEntity());
            } else {
                System.out.println(response.getCode());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testUpdateMerRole() {
        MerRoleDTO merRoleDTO = new MerRoleDTO();
        merRoleDTO.setMerCode("030641000000001");
        merRoleDTO.setMerRoleCode("10000042");
        merRoleDTO.setMerRoleName("测试角色5");
        merRoleDTO.setDescription("测试角色5描述修改");
        merRoleDTO.setUpdateUser("lifeng");

        List<MerFunctionInfoDTO> merFunInfoList = new ArrayList<MerFunctionInfoDTO>();

        MerFunctionInfoDTO merFun1 = new MerFunctionInfoDTO();
        merFun1.setMerFunCode("100001");
        merFun1.setMerFunName("我的都都宝");
        merFunInfoList.add(merFun1);

        MerFunctionInfoDTO merFun2 = new MerFunctionInfoDTO();
        merFun2.setMerFunCode("100002");
        merFun2.setMerFunName("交易记录");
        merFunInfoList.add(merFun2);

        merRoleDTO.setMerRoleFunDTOList(merFunInfoList);

        DodopalResponse<Integer> response = merRoleFacade.updateMerRole(merRoleDTO);
        if (ResponseCode.SUCCESS.equals(response.getCode()) && response.getResponseEntity()!=null) {
            System.out.println(response.getResponseEntity());
        } else {
            System.out.println(response.getCode());
        }
    }
}
