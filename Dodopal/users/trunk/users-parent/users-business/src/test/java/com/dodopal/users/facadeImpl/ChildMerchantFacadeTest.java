package com.dodopal.users.facadeImpl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantQueryDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.api.users.facade.ChildMerchantFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
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
public class ChildMerchantFacadeTest {
    private ChildMerchantFacade childMerchantFacade;
    @Before
    public void setUp() {
        String hessianUrl = "http://localhost:8082/users-web/hessian/index.do?id=";
        childMerchantFacade = RemotingCallUtil.getHessianService(hessianUrl, ChildMerchantFacade.class);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testFindMerchant() {
        try {
            PageParameter page = new PageParameter();
            page.setPageNo(1);
            page.setPageSize(2);

            MerchantQueryDTO merQuery = new MerchantQueryDTO();
            merQuery.setMerParentCode("064291000000146");
            merQuery.setPage(page);

            DodopalResponse<DodopalDataPage<MerchantDTO>> response = childMerchantFacade.findChildMerchantByPage(merQuery);
            if (ResponseCode.SUCCESS.equals(response.getCode()) && response.getResponseEntity() != null) {
                DodopalDataPage<MerchantDTO> ddpResult = response.getResponseEntity();
                List<MerchantDTO> resultList = ddpResult.getRecords();
                for (MerchantDTO mer : resultList) {
                    System.out.println(ReflectionToStringBuilder.toString(mer, ToStringStyle.MULTI_LINE_STYLE));
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
    public void testFindChildMerchantByMerCode() {
        try {
            String merCode = "072751000000147";
            String merParentCode = "064291000000146";
            DodopalResponse<MerchantDTO> response = childMerchantFacade.findChildMerchantByMerCode(merCode, merParentCode);
            if (ResponseCode.SUCCESS.equals(response.getCode()) && response.getResponseEntity() != null) {
                MerchantDTO merDTO = response.getResponseEntity();
                System.out.println(ReflectionToStringBuilder.toString(merDTO, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println(ReflectionToStringBuilder.toString(merDTO.getMerKeyTypeDTO(), ToStringStyle.MULTI_LINE_STYLE));
            } else {
                System.out.println(response.getCode());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpChildMerchantActivate() {
        try {
            String[] merCodes = {"072751000000147", "037621000000138"};
            String activate = ActivateEnum.DISABLE.getCode();
            String merParentCode = "064291000000146";
            DodopalResponse<Integer> response = childMerchantFacade.upChildMerchantActivate(merCodes, activate, merParentCode, "111");
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
    public void testSaveChildMerchant() {
        try {
            MerchantDTO merchantDTO = new MerchantDTO();
            merchantDTO.setMerParentCode("030641000000001");
            merchantDTO.setMerName("junit_test1");
            merchantDTO.setMerType("13");
            merchantDTO.setMerClassify("0");
            merchantDTO.setMerProperty("0");
            merchantDTO.setMerAdds("徐汇区虹梅路1905号");
            merchantDTO.setMerLinkUser("李峰");
            merchantDTO.setMerLinkUserMobile("17022222222");
            merchantDTO.setMerEmail("1@1");
            merchantDTO.setMerState("0");
            merchantDTO.setMerRegisterDate(new Date());
            merchantDTO.setMerPro("1000");
            merchantDTO.setMerCity("1100");
            merchantDTO.setDelFlg("0");
            merchantDTO.setActivate("0");
            merchantDTO.setCreateUser("lifeng");
            
            MerchantUserDTO merchantUserDTO = new MerchantUserDTO();
            merchantUserDTO.setMerUserName("lifengc");
            merchantUserDTO.setMerUserPWD("123");
            merchantUserDTO.setMerUserFlag("0");
            merchantUserDTO.setMerUserType("0");
            merchantUserDTO.setMerUserMobile("17011111111");
            merchantUserDTO.setActivate("0");
            merchantDTO.setMerchantUserDTO(merchantUserDTO);

            DodopalResponse<String> response = childMerchantFacade.saveChildMerchant(merchantDTO);
            if(ResponseCode.SUCCESS.equals(response.getCode())) {
                System.out.println(response.getResponseEntity());
            } else {
                System.out.println(response.getCode());
                System.out.println(response.getMessage());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testUpChildMerchant() {
        try {
            MerchantDTO merchantDTO = new MerchantDTO();
            merchantDTO.setMerCode("018491000000022");
            merchantDTO.setMerParentCode("030641000000001");
            merchantDTO.setMerBankName("银行啊");
            merchantDTO.setUpdateUser("lifeng");

            MerchantUserDTO merchantUserDTO = new MerchantUserDTO();
            merchantUserDTO.setMerUserBirthday(new Date());
            merchantDTO.setMerchantUserDTO(merchantUserDTO);

            DodopalResponse<String> response = childMerchantFacade.upChildMerchant(merchantDTO);
            if(ResponseCode.SUCCESS.equals(response.getCode())) {
                System.out.println("更新成功！");
            } else {
                System.out.println(response.getCode());
                System.out.println(response.getMessage());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
