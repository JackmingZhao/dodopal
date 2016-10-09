package com.dodopal.product.facade;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.api.product.dto.PersonalHisOrderDTO;
import com.dodopal.api.product.dto.PrdProductYktDTO;
import com.dodopal.api.product.dto.query.PersonalHisOrderQueryDTO;
import com.dodopal.api.product.facade.PersonalHisOrderFacade;
import com.dodopal.api.product.facade.PrdProductYktFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.hessian.RemotingCallUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:product-business-test-context.xml"})
public class PrdProductFacadeTest {

    //@Autowired
    private PrdProductYktFacade productYktFacade;
    
    private PersonalHisOrderFacade personalHisOrderFacade;
    

    @Before
    public void setUp() {
        String hessianUrl = "http://localhost:8084/product-web/hessian/index.do?id=";
        //productYktFacade = RemotingCallUtil.getHessianService(hessianUrl, PrdProductYktFacade.class);
        personalHisOrderFacade = RemotingCallUtil.getHessianService(hessianUrl, PersonalHisOrderFacade.class);
    }

    @After
    public void tearDown() {

    }
    
    @Test
    public void testFindPrdProductYktByProCode(){
        String proCode = "1000015";
        DodopalResponse<PrdProductYktDTO> response = productYktFacade.findPrdProductYktByProCode(proCode);
        if (ResponseCode.SUCCESS.equals(response.getCode()) && response.getResponseEntity() != null) {
            PrdProductYktDTO proDTO = response.getResponseEntity();
            System.out.println(ReflectionToStringBuilder.toString(proDTO, ToStringStyle.MULTI_LINE_STYLE));
        } else {
            System.out.println(response.getCode());
        }
    }
    
    
    @Test
    public void testFindAvailableIcdcProductsForMerchant(){
        String cityId="1566";
        String merchantNum="004971000000428";
        DodopalResponse<List<PrdProductYktDTO>> response = productYktFacade.findAvailableIcdcProductsForMerchant(merchantNum, cityId);
        if (ResponseCode.SUCCESS.equals(response.getCode()) && response.getResponseEntity() != null) {
            List<PrdProductYktDTO> resultList = response.getResponseEntity();
            if (resultList != null && resultList.size() > 0) {
                System.out.println("查询记录数：" + resultList.size());
                for (PrdProductYktDTO proTemp : resultList){
                    System.out.println(ReflectionToStringBuilder.toString(proTemp, ToStringStyle.MULTI_LINE_STYLE));
                }
            }
        }else {
            System.out.println(response.getCode());
        }
    }

    @Test
    public void testPersonalFindRechargeOrderByPage() {
    	try {
    		PersonalHisOrderQueryDTO personalHisOrderQueryDTO = new PersonalHisOrderQueryDTO();
        	personalHisOrderQueryDTO.setPage(new PageParameter(2, 30));
        	personalHisOrderQueryDTO.setUserid("1133679");
        	DodopalResponse<DodopalDataPage<PersonalHisOrderDTO>> response = personalHisOrderFacade.findRechargeOrderByPage(personalHisOrderQueryDTO);
        	System.out.println(response.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
}
