package com.dodopal.product.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.common.enums.LoadOrderStatusEnum;
import com.dodopal.product.business.dao.LoadOrderMapper;
import com.dodopal.product.business.model.LoadOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:product-business-test-context.xml"})
public class LoadOrderMapperTest {

    @Autowired
    private LoadOrderMapper loadOrderMapper;

    private String generateOrderNum() {
        String orderNum = "Q";
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHMMSS");
        String timeStr = simpleDateFormat.format(now);
        orderNum += timeStr;
        return orderNum + loadOrderMapper.selectOrderNumSeq();
    }

    @Test
    public void testInsertLoadOrder() {
        try {
            LoadOrder order = new LoadOrder();
            order.setOrderNum(generateOrderNum());
            order.setExtMerOrderNum("1231231231231231");
            order.setExtMerOrderTime("20160107100000");
            order.setCardFaceNum("1000751091100659");
            order.setExchangedCardNum("11111111111");
            order.setProductCode("1001445");
            order.setProductName("北京50元");
            order.setYktCode("1900901");
            order.setYktName("北京一卡通");
            order.setCityCode("1110");
            order.setCityName("北京");
            order.setLoadAmt((long)5000);
            order.setStatus(LoadOrderStatusEnum.ORDER_SUCCESS.getCode());
            order.setCustomerCode("123");
            order.setCustomerName("test");
            order.setCustomerType("1");
            double m = (double)5.2;
            System.out.println(m);
            order.setLoadRate(m);
            order.setLoadRateType("1");
            order.setComments("shen.............");
            loadOrderMapper.insertLoadOrder(order);
            System.out.println("##########################################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
