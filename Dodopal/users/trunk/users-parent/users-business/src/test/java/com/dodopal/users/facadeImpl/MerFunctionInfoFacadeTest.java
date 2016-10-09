package com.dodopal.users.facadeImpl;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.api.users.dto.MerFunctionInfoDTO;
import com.dodopal.api.users.facade.MerFunctionInfoFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.model.DodopalResponse;

/**
 * 类说明 ：
 * @author lifeng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:users-business-test-context.xml"})
public class MerFunctionInfoFacadeTest {
    @Autowired
    private MerFunctionInfoFacade merFunctionInfoFacade;

    @Test
    public void testFindAllFuncInfoForOSS() {
        try {
            DodopalResponse<List<MerFunctionInfoDTO>> response = merFunctionInfoFacade.findAllFuncInfoForOSS("12");
            if (ResponseCode.SUCCESS.equals(response.getCode()) && response.getResponseEntity() != null) {
                List<MerFunctionInfoDTO> resultList = response.getResponseEntity();
                if (resultList != null && resultList.size() > 0) {
                    System.out.println("查询记录数：" + resultList.size());
                    for (MerFunctionInfoDTO merFunInfo : resultList) {
                        System.out.println(ReflectionToStringBuilder.toString(merFunInfo, ToStringStyle.MULTI_LINE_STYLE));
                    }
                } else {
                    System.out.println("未查询到结果");
                }
            } else {
                System.out.println(response.getMessage());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindFuncInfoByMerType() {
        try {
            DodopalResponse<List<MerFunctionInfoDTO>> response = merFunctionInfoFacade.findFuncInfoByMerType(MerTypeEnum.DDP_MER);
            if (ResponseCode.SUCCESS.equals(response.getCode()) && response.getResponseEntity() != null) {
                List<MerFunctionInfoDTO> resultList = response.getResponseEntity();
                if (resultList != null && resultList.size() > 0) {
                    System.out.println("查询记录数：" + resultList.size());
                    for (MerFunctionInfoDTO merFunInfo : resultList) {
                        System.out.println(ReflectionToStringBuilder.toString(merFunInfo, ToStringStyle.MULTI_LINE_STYLE));
                    }
                } else {
                    System.out.println("未查询到结果");
                }
            } else {
                System.out.println(response.getMessage());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
