package com.dodopal.product.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.product.business.dao.ProductOrderMapper;
import com.dodopal.product.business.model.ProductOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:product-business-test-context.xml"})
public class ProductOrderMapperTest {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHMMSS");
    @Autowired
    private ProductOrderMapper productOrderMapper;

    @Test
    public void testGetOrderListByLoadOrderNum() {
        try {

            String orderNum = "Q2016042616552010087";
            List<ProductOrder>  orderList = productOrderMapper.getOrderListByLoadOrderNum(orderNum);

            System.out.println("##########################################");

            if (orderList != null) {
                System.out.println("##########################################");
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(orderList, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
            }
            System.out.println("##########################################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
//
////    @Test
//    public void testFindProductOrderDetail() {
//        try {
//
//            String orderNum = "O20150819130841810003";
//            ProductOrder order = productOrderMapper.findProductOrderDetails(orderNum);
//
//            System.out.println("##########################################");
//
//            if (order != null) {
//                System.out.println("##########################################");
//                System.out.println("##########################################");
//                System.out.println(ReflectionToStringBuilder.toString(order, ToStringStyle.MULTI_LINE_STYLE));
//                System.out.println("##########################################");
//            }
//            System.out.println("##########################################");
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testInsertProductOrder() {
//        try {
//
//            ProductOrder order = new ProductOrder();
//            order.setProOrderNum(generateProductOrderNum());
//            order.setBefbal(100);
//            order.setBlackAmt(null);
//            order.setCityName("测试");
//            order.setClearingMark("0");
//            order.setComments("Junit test");
//            order.setCreateUser("Junit");
//            order.setMerCode("123");
//            order.setProOrderDay("20150901");
//            int prd = productOrderMapper.addProductOrder(order);
//
//            System.out.println("##########################################");
//            System.out.print(prd);
//            System.out.println("##########################################");
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//        @Test
//    public void testFindProductOrders() {
//        try {
//
//            ProductOrderQueryDTO prdOrderQuery = new ProductOrderQueryDTO();
//
//            List<ProductOrder> orders = productOrderMapper.findProductOrdersByPage(prdOrderQuery);
//
//            System.out.println("##########################################");
//
//            if (orders != null) {
//                System.out.println("##########################################");
//                for (ProductOrder dp : orders) {
//                    System.out.println("##########################################");
//                    System.out.println(ReflectionToStringBuilder.toString(dp, ToStringStyle.MULTI_LINE_STYLE));
//                    System.out.println("##########################################");
//                }
//            }
//            System.out.println("##########################################");
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 产品库公交卡充值订单编号生成规则O + 20150428222211 +五位数据库cycle sequence（循环使用）
     */
    private String generateProductOrderNum() {
        String prdOrderNum = "O";
        Date now = new Date();
        String timeStr = simpleDateFormat.format(now);
        prdOrderNum += timeStr;
        return prdOrderNum + productOrderMapper.getPrdOrderCodeSeq();
    }

}
