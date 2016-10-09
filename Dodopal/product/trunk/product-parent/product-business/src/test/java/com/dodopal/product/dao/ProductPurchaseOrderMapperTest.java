package com.dodopal.product.dao;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.common.util.DateUtils;
import com.dodopal.product.business.dao.ProductPurchaseOrderMapper;
import com.dodopal.product.business.model.ProductPurchaseOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:product-business-test-context.xml"})
public class ProductPurchaseOrderMapperTest {

    @Autowired
    private ProductPurchaseOrderMapper productPurchaseOrderMapper;

    @Test
    public void testInsertProductOrder() {
        try {

            ProductPurchaseOrder purchaseOrder = new ProductPurchaseOrder();
            purchaseOrder.setOrderNum(getProductPurchaseOrderNum());
            purchaseOrder.setBusinessType("03");
            purchaseOrder.setClearingMark("0");
            purchaseOrder.setComments("ceshi");
            purchaseOrder.setCreateUser("junit");
            purchaseOrder.setCustomerCode("123123123");
            purchaseOrder.setCustomerName("ceshi");
            purchaseOrder.setCustomerType("15");
            purchaseOrder.setFundProcessResult("0");
            purchaseOrder.setMerGain(null);
            purchaseOrder.setMerRate(0.1);
            purchaseOrder.setMerRateType("1");
            purchaseOrder.setOrderDay("20151017");
            purchaseOrder.setOrderNum("123123");
            purchaseOrder.setOriginalPrice(3000);
            purchaseOrder.setPayType("2");
            purchaseOrder.setPayWay("123123");
            purchaseOrder.setReceivedPrice(30000);
            purchaseOrder.setServiceRate(6.0);
            purchaseOrder.setServiceRateType("1");
            purchaseOrder.setSource("2");
            purchaseOrder.setStates("0");
            purchaseOrder.setUserId("123123");
            int prd = productPurchaseOrderMapper.addProductPurchaseOrder(purchaseOrder);

            System.out.println("##########################################");
            System.out.print(prd);
            System.out.println("##########################################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getProductPurchaseOrderNum() {
        String prdOrderNum = "C";
        String timeStr = DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR);
        prdOrderNum += timeStr;
        return prdOrderNum + productPurchaseOrderMapper.getPrdPurchaseOrderNumSeq();
    }

}
