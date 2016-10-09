package com.dodopal.product.delegate;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.api.product.dto.ProductOrderDTO;
import com.dodopal.api.product.dto.ProductOrderDetailDTO;
import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:product-delegate-test-context.xml"})
public class ProductOrderDelegateTest {

    @Autowired
    private ProductOrderDelegate productOrderDelegate;

    @Test
    public void testFindProductOrderDetail() {
        try {

            String proOrderNum = "O20150819130841810003";
            DodopalResponse<ProductOrderDetailDTO> response = productOrderDelegate.findProductOrderDetails(proOrderNum);

            System.out.println("##########################################");

            if (response != null && ResponseCode.SUCCESS.equals(response.getCode())) {
                System.out.println("##########################################");
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(response.getResponseEntity(), ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
            }
            System.out.println("##########################################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

        @Test
    public void testFindProductOrders() {
        try {

            ProductOrderQueryDTO prdOrderQuery = new ProductOrderQueryDTO();
//            prdOrderQuery.setProOrderNum("O20150819130841810003");
            DodopalResponse<DodopalDataPage<ProductOrderDTO>> response = productOrderDelegate.findProductOrder(prdOrderQuery);

            System.out.println("##########################################");

            if (response != null && ResponseCode.SUCCESS.equals(response.getCode())) {
                System.out.println("##########################################");
                for (ProductOrderDTO dp : response.getResponseEntity().getRecords()) {
                    System.out.println("##########################################");
                    System.out.println(ReflectionToStringBuilder.toString(dp, ToStringStyle.MULTI_LINE_STYLE));
                    System.out.println("##########################################");
                }
            }
            System.out.println("##########################################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
