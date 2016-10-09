package com.dodopal.portal.delegate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.api.users.dto.MerFunctionInfoDTO;
import com.dodopal.api.users.dto.MerRoleDTO;
import com.dodopal.api.users.dto.query.MerRoleQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

/**
 * 类说明 ：
 * @author lifeng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:portal-delegate-test-context.xml"})
public class MerRoleDelegateTest {
    @Autowired
    MerRoleDelegate merRoleDelegate;

    @Test
    public void testFindMerRoleByPage() {
        try {
            PageParameter page = new PageParameter();
            page.setPageNo(1);
            page.setPageSize(2);

            MerRoleQueryDTO merRoleQueryDTO = new MerRoleQueryDTO();
            merRoleQueryDTO.setMerCode("030641000000001");
            merRoleQueryDTO.setPage(page);

            DodopalResponse<DodopalDataPage<MerRoleDTO>> response = merRoleDelegate.findMerRoleByPage(merRoleQueryDTO);
            if (ResponseCode.SUCCESS.equals(response.getCode()) && response.getResponseEntity() != null) {
                List<MerRoleDTO> resultList = response.getResponseEntity().getRecords();
                for (MerRoleDTO merRole : resultList) {
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
    public void testFindMerRoleByMerRoleCode() {
        try {
            String merCode = "030641000000001";
            String merRoleCode = "10000025";

            DodopalResponse<MerRoleDTO> response = merRoleDelegate.findMerRoleByMerRoleCode(merCode, merRoleCode);
            MerRoleDTO merRoleDTO = response.getResponseEntity();
            if (ResponseCode.SUCCESS.equals(response.getCode()) && merRoleDTO != null) {
                System.out.println(ReflectionToStringBuilder.toString(merRoleDTO, ToStringStyle.MULTI_LINE_STYLE));
                List<MerFunctionInfoDTO> merRoleFunDTOList = merRoleDTO.getMerRoleFunDTOList();
                if (CollectionUtils.isNotEmpty(merRoleFunDTOList)) {
                    for (MerFunctionInfoDTO merFunctionInfoDTOTemp : merRoleFunDTOList)
                        System.out.println(ReflectionToStringBuilder.toString(merFunctionInfoDTOTemp, ToStringStyle.MULTI_LINE_STYLE));
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
            List<MerFunctionInfoDTO> merRoleFunDTOList = new ArrayList<MerFunctionInfoDTO>();

            MerFunctionInfoDTO merFunInfo1 = new MerFunctionInfoDTO();
            merFunInfo1.setMerFunCode("100001");
            merFunInfo1.setMerFunName("我的都都宝");
            merRoleFunDTOList.add(merFunInfo1);

            MerFunctionInfoDTO merFunInfo2 = new MerFunctionInfoDTO();
            merFunInfo2.setMerFunCode("100002");
            merFunInfo2.setMerFunName("交易记录");
            merRoleFunDTOList.add(merFunInfo2);

            MerFunctionInfoDTO merFunInfo3 = new MerFunctionInfoDTO();
            merFunInfo3.setMerFunCode("100003");
            merFunInfo3.setMerFunName("应用中心");
            merRoleFunDTOList.add(merFunInfo3);

            MerRoleDTO merRoleDTO = new MerRoleDTO();
            merRoleDTO.setMerCode("030641000000001");
            merRoleDTO.setMerRoleName("junit_add_role");
            merRoleDTO.setMerRoleFunDTOList(merRoleFunDTOList);

            DodopalResponse<Integer> response = merRoleDelegate.addMerRole(merRoleDTO);
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
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
        try {
            List<MerFunctionInfoDTO> merRoleFunDTOList = new ArrayList<MerFunctionInfoDTO>();

            MerFunctionInfoDTO merFunInfo1 = new MerFunctionInfoDTO();
            merFunInfo1.setMerFunCode("100001");
            merFunInfo1.setMerFunName("我的都都宝");
            merRoleFunDTOList.add(merFunInfo1);

            MerFunctionInfoDTO merFunInfo2 = new MerFunctionInfoDTO();
            merFunInfo2.setMerFunCode("100002");
            merFunInfo2.setMerFunName("交易记录");
            merRoleFunDTOList.add(merFunInfo2);

            DodopalResponse<MerRoleDTO> response1 = merRoleDelegate.findMerRoleByMerRoleCode("030641000000001", "10000027");
            MerRoleDTO merRoleDTO = response1.getResponseEntity();
            merRoleDTO.setMerRoleFunDTOList(merRoleFunDTOList);

            DodopalResponse<Integer> response = merRoleDelegate.updateMerRole(merRoleDTO);
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
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
    public void testBatchDelMerRoleByCodes() {
        try {
            String merCode = "030641000000001";
            List<String> merRoleCodes = Arrays.asList("10000027");

            DodopalResponse<Integer> response = merRoleDelegate.batchDelMerRoleByCodes(merCode, merRoleCodes);
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
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
    public void testFindMerFuncInfoByUserCode() {
        try {
            String userCode = "16533100000000001";

            DodopalResponse<List<MerFunctionInfoDTO>> response = merRoleDelegate.findMerFuncInfoByUserCode(userCode);
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                List<MerFunctionInfoDTO> merRoleFunDTOList = response.getResponseEntity();
                if (CollectionUtils.isNotEmpty(merRoleFunDTOList)) {
                    for (MerFunctionInfoDTO merFunctionInfoDTOTemp : merRoleFunDTOList)
                        System.out.println(ReflectionToStringBuilder.toString(merFunctionInfoDTOTemp, ToStringStyle.MULTI_LINE_STYLE));
                }
            } else {
                System.out.println(response.getCode());
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
