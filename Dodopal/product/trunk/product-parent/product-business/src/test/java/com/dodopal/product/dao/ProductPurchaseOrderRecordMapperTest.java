package com.dodopal.product.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.product.business.dao.ProductPurchaseOrderRecordMapper;
import com.dodopal.product.business.model.ProductPurchaseOrderRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:product-business-test-context.xml"})
public class ProductPurchaseOrderRecordMapperTest {

    @Autowired
    private ProductPurchaseOrderRecordMapper productPurchaseOrderRecordMapper;

    @Test
    public void testInsertProductOrder() {
        try {

            ProductPurchaseOrderRecord orderRecord = new ProductPurchaseOrderRecord();
            orderRecord.setOrderNum("12222");
            orderRecord.setOrderDay("20151017");

            int prd = productPurchaseOrderRecordMapper.addProductPurchaseOrderRecord(orderRecord);

            System.out.println("##########################################");
            System.out.print(prd);
            System.out.println("##########################################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void testUpdatePurchaseOrderRecordWhenCheckCard() {
//        try {
//
//            ProductPurchaseOrderRecord orderRecord = new ProductPurchaseOrderRecord();
//            orderRecord.setOrderNum("C20151017162945100007");
//            //orderRecord.setBlackAmt(null);
//
//            
////            ProductPurchaseOrderRecord orderRecord2 = new ProductPurchaseOrderRecord();
////            orderRecord.setOrderNum("C20151017162945100007");
////            //orderRecord.setBlackAmt(null);
//            
//            int prd = productPurchaseOrderRecordMapper.updatePurchaseOrderRecordWhenCheckCard(orderRecord);
//
//            System.out.println("##########################################");
//            System.out.print(prd);
//            System.out.println("##########################################");
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
