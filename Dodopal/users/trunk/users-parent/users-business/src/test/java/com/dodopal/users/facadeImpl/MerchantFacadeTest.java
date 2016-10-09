package com.dodopal.users.facadeImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.api.users.dto.DirectMerChantDTO;
import com.dodopal.api.users.dto.MerBusRateDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantQueryDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.api.users.dto.query.MerchantUserQueryDTO;
import com.dodopal.api.users.facade.MerchantFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.hessian.RemotingCallUtil;

/**
 * 类说明 :
 * @author lifeng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:users-business-test-context.xml"})
public class MerchantFacadeTest {
    //@Autowired
    private MerchantFacade merchantFacade;

    @Before
    public void setUp() {
        String hessianUrl = "http://localhost:8082/users-web/hessian/index.do?id=";
        merchantFacade = RemotingCallUtil.getHessianService(hessianUrl, MerchantFacade.class);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testFindMerchant() {
        try {
            MerchantDTO merchantDto = new MerchantDTO();
            merchantDto.setMerState("0");
            //merchantDto.setMerCode("M1234567890");
            merchantDto.setMerName("lifeng");
            DodopalResponse<List<MerchantDTO>> response = merchantFacade.findMerchant(merchantDto);

            if (ResponseCode.SUCCESS.equals(response.getCode()) && response.getResponseEntity() != null) {
                List<MerchantDTO> resultList = response.getResponseEntity();
                if (resultList != null && resultList.size() > 0) {
                    System.out.println("查询记录数：" + resultList.size());
                    for (MerchantDTO mer : resultList) {
                        System.out.println(ReflectionToStringBuilder.toString(mer, ToStringStyle.MULTI_LINE_STYLE));
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
    public void testFindMerchantByMerCode() {
        try {
            DodopalResponse<MerchantDTO> response = merchantFacade.findMerchantByMerCode("030641000000001");
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
    public void testFindMerchantRelationByMerCode() {
        // 商户号
        String merCode = "M1234567890";

        try {
            DodopalResponse<List<MerchantDTO>> response = merchantFacade.findMerchantRelationByMerCode(merCode);
            if (ResponseCode.SUCCESS.equals(response.getCode()) && response.getResponseEntity() != null) {
                List<MerchantDTO> resultList = response.getResponseEntity();
                for (MerchantDTO mer : resultList) {
                    System.out.println(mer.getMerCode() + "--" + mer.getMerName() + "--" + mer.getMerParentCode() + "--" + mer.getMerType());
                    // System.out.println(ReflectionToStringBuilder.toString(mer, ToStringStyle.MULTI_LINE_STYLE));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindMerOperators() {
        // 商户号
        String merCode = "M1234567890";

        try {
            MerchantUserDTO merchantUserDTO = new MerchantUserDTO();
            merchantUserDTO.setMerCode(merCode);

            DodopalResponse<List<MerchantUserDTO>> response = merchantFacade.findMerOperators(merchantUserDTO);
            if (ResponseCode.SUCCESS.equals(response.getCode()) && response.getResponseEntity() != null) {
                List<MerchantUserDTO> resultList = response.getResponseEntity();
                for (MerchantUserDTO mer : resultList) {
                    System.out.println(mer.getUserCode() + "--" + mer.getMerUserName() + "--" + mer.getMerUserMobile());
                    // System.out.println(ReflectionToStringBuilder.toString(mer, ToStringStyle.MULTI_LINE_STYLE));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindMerOperatorByPage() {
        // 商户号
        String merCode = "030641000000001";

        PageParameter page = new PageParameter();
        page.setPageNo(1);
        page.setPageSize(20);

        try {
            MerchantUserQueryDTO userQueryDTO = new MerchantUserQueryDTO();
            userQueryDTO.setMerCode(merCode);
            userQueryDTO.setPage(page);

            DodopalResponse<DodopalDataPage<MerchantUserDTO>> response = merchantFacade.findMerOperatorByPage(userQueryDTO);
            if (ResponseCode.SUCCESS.equals(response.getCode()) && response.getResponseEntity() != null) {
                List<MerchantUserDTO> resultList = response.getResponseEntity().getRecords();
                System.out.println(resultList.size());
                for (MerchantUserDTO mer : resultList) {
                    System.out.println(ReflectionToStringBuilder.toString(mer, ToStringStyle.MULTI_LINE_STYLE));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindMerchantByPage() {
        try {
            PageParameter page = new PageParameter();
            page.setPageNo(1);
            page.setPageSize(2);

            MerchantQueryDTO merQuery = new MerchantQueryDTO();
            merQuery.setPage(page);

            DodopalResponse<DodopalDataPage<MerchantDTO>> response = merchantFacade.findMerchantByPage(merQuery);
            if (ResponseCode.SUCCESS.equals(response.getCode()) && response.getResponseEntity() != null) {
                DodopalDataPage<MerchantDTO> ddpResult = response.getResponseEntity();
                List<MerchantDTO> resultList = ddpResult.getRecords();
                for (MerchantDTO mer : resultList) {
                    System.out.println(ReflectionToStringBuilder.toString(mer, ToStringStyle.MULTI_LINE_STYLE));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBatchActivateMerOperator() {
        try {
            String merCode = "030641000000001";
            String updateUser = "lifeng";
            List<String> userCodes = Arrays.asList("10501100000000021", "15167100000000003");
            DodopalResponse<Integer> response = merchantFacade.batchActivateMerOperator(merCode, updateUser, ActivateEnum.ENABLE, userCodes);
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
    public void testBatchDelRejectMerchantByMerCodes() {
        try {
            List<String> merCodes = Arrays.asList("040531000000041");
            DodopalResponse<Integer> response = merchantFacade.batchDelRejectMerchantByMerCodes(merCodes, "admin");
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
    public void testResetOperatorPwd() {
        String merCode = "030641000000001";
        String id = "10000000000000000001";
        String password = "caf1a3dfb505ffed0d024130f58c5cfa"; //caf1a3dfb505ffed0d024130f58c5cfa
        String updateUser = "admin";
        try {
            DodopalResponse<Boolean> response = merchantFacade.resetOperatorPwd(merCode, id, password, updateUser);
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
    public void testUpdateMerchantForPortal() {
        MerchantDTO merchantDTO = new MerchantDTO();
        merchantDTO.setMerCode("018491000000022");
        merchantDTO.setUpdateUser("lifeng");
        merchantDTO.setMerLinkUser("啊啊啊");
        merchantDTO.setMerEmail("lifeng@111.com");

        MerchantUserDTO merUserDTO = new MerchantUserDTO();
        merchantDTO.setMerchantUserDTO(merUserDTO);
        try {
            DodopalResponse<String> response = merchantFacade.updateMerchantForPortal(merchantDTO);
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
    public void testFindMerBusRateByMerCode() {
        try {
            DodopalResponse<List<MerBusRateDTO>> response = merchantFacade.findMerBusRateByMerCode("029251000000208");
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                List<MerBusRateDTO> resultList = response.getResponseEntity();
                if (resultList != null && resultList.size() > 0) {
                    for (MerBusRateDTO mer : resultList) {
                        System.out.println(ReflectionToStringBuilder.toString(mer, ToStringStyle.MULTI_LINE_STYLE));
                    }
                } else {
                    System.out.println(response.getCode());
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
    public void testCheckRelationMerchantProviderAndRateType() {
        try {
            MerchantDTO merDTO = new MerchantDTO();
            merDTO.setMerCode("021011000000429");

            List<MerBusRateDTO> merRateList = new ArrayList<MerBusRateDTO>();

            MerBusRateDTO merRate1 = new MerBusRateDTO();
            merRate1.setProviderCode("3400");
            merRate1.setRateCode("01");
            merRate1.setRateType("2");
            merRate1.setRate(4);
            merRateList.add(merRate1);

            MerBusRateDTO merRate2 = new MerBusRateDTO();
            merRate2.setProviderCode("3500");
            merRate2.setRateCode("01");
            merRate2.setRateType("1");
            merRate2.setRate(2000);
            merRateList.add(merRate2);
            
            merDTO.setMerBusRateList(merRateList);

            DodopalResponse<Boolean> response = merchantFacade.checkRelationMerchantProviderAndRateType(merDTO);
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                System.out.println("没有不符合的条件");
            } else {
                System.out.println(response.getCode());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testValidateMerchantForIcdcRecharge() {
    	try {
    		String merchantNum = "013541000000190";
    		String userId = "10000000000000000296";
    		String posId = "200000000058";
    		String providerCode = "22223333";
    		String source = SourceEnum.PORTAL.getCode();

            DodopalResponse<Map<String, String>> response = merchantFacade.validateMerchantForIcdcRecharge(merchantNum, userId, posId, providerCode, source);
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                System.out.println("校验通过");
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
    public void testFindMerCitys() {
    	try {
    		String merCode = "060271000000127";

            DodopalResponse<List<Area>> response = merchantFacade.findMerCitys(merCode);
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                System.out.println("校验通过");
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
    public void testFindMer() {
        try {
            String merParentCode = "013541000000190";
            String merName = "";
            DodopalResponse<List<DirectMerChantDTO>> response = merchantFacade.findMerchantByParentCode(merParentCode, merName);
           if (ResponseCode.SUCCESS.equals(response.getCode())) {
               System.out.println("校验通过");
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
    public void testFindYktCitys() {
    	try {
    		MerUserTypeEnum userType = MerUserTypeEnum.PERSONAL;
    		//String merCode = "060271000000127";
    		String userCode = "33669100000000680";

            DodopalResponse<List<Area>> response = merchantFacade.findYktCitys(userType, userCode);
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                System.out.println("校验通过");
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
    public void testValidateMerchantForIcdcPurchase() {
    	try {
    		String merchantNum = "013541000000190";
    		String userId = "10000000000000000296";
    		String posId = "200000000058";
    		String providerCode = "22223333";
    		String source = SourceEnum.PORTAL.getCode();

            DodopalResponse<Map<String, String>> response = merchantFacade.validateMerchantForIcdcPurchase(merchantNum, userId, posId, providerCode, source);
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                System.out.println("校验通过");
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
