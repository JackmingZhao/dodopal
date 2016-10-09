package com.dodopal.users.facadeImpl;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.api.users.dto.MerCountDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantQueryDTO;
import com.dodopal.api.users.dto.query.MerCountQueryDTO;
import com.dodopal.api.users.facade.ManagementForSupplierFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.hessian.RemotingCallUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:users-business-test-context.xml"})
public class ManagementForSupplierFacadeTest {
    private ManagementForSupplierFacade managementForSupplierFacade;
    
    @Before
    public void setUp() {
        String hessianUrl = "http://localhost:8082/users-web/hessian/index.do?id=";
        managementForSupplierFacade = RemotingCallUtil.getHessianService(hessianUrl, ManagementForSupplierFacade.class);
    }
    
    
    @Test
    public void testFindMerchant() {
        try {
            PageParameter page = new PageParameter();
            page.setPageNo(1);
            page.setPageSize(50);

            MerCountQueryDTO merQuery = new MerCountQueryDTO();
            merQuery.setCityName("北京");
            merQuery.setPage(page);

            DodopalResponse<DodopalDataPage<MerCountDTO>> response = managementForSupplierFacade.countMerchantForSupplier(merQuery);
            if (ResponseCode.SUCCESS.equals(response.getCode()) && response.getResponseEntity() != null) {
                DodopalDataPage<MerCountDTO> ddpResult = response.getResponseEntity();
                List<MerCountDTO> resultList = ddpResult.getRecords();
                for (MerCountDTO mer : resultList) {
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

}
