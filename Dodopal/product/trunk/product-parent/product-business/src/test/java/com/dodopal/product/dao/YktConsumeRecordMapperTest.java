package com.dodopal.product.dao;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.common.enums.YktAPCOStatesEnum;
import com.dodopal.product.business.dao.YktConsumeRecordMapper;
import com.dodopal.product.business.model.YktConsumeRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:product-business-test-context.xml"})
public class YktConsumeRecordMapperTest {

    @Autowired
    private YktConsumeRecordMapper yktConsumeRecordMapper;

//    @Test
//    public void testInsertProductOrder() {
//        try {
//
//            YktConsumeRecord record = new YktConsumeRecord();
//            
//            record.setOrderNum("Z20160524142600000");
//            record.setYktCode("111111");
//            record.setYktName("111111");
//            record.setCityCode("22222");
//            record.setCityName("222222");
//            record.setMerCode("333333333");
//            record.setMerName("333333333");
//            record.setMerType("33");
//            record.setBusinessType(RateCodeEnum.YKT_CONSUME_ACOUNT.getCode());
//            record.setCardNum("444444444444444444");
//            record.setPosCode("5555555555555");
//            record.setPosType("0");
//            record.setYktMerCode("6666666666666");
//            record.setYktMerAcountno("6666666666666");
//            record.setYktAcountAmt(Long.parseLong("10000"));
//            //record.setYktPointAmt(0l);
//            record.setStates(YktAPCOStatesEnum.CREATION_SUCCESS.getCode());
//            //record.setBeforeStates("");
//            record.setOrderDate(new Date());
//            record.setOrderDay(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDD_STR));
//            record.setOperId("77777777777777");
//            record.setCreateUser("88888888888888");
//            record.setCreateDate(new Date());
//            int prd =  yktConsumeRecordMapper.addOrderRecord(record);
//
//            System.out.println("##########################################");
//            System.out.println(prd);
//            System.out.println("##########################################");
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    @Test
    public void testupdateByOrderNum() {
        try {

            
            YktConsumeRecord record = new YktConsumeRecord();
            record =  yktConsumeRecordMapper.selectByOrderNum("Z20160524142600000");
            
            System.out.println("##########################################");
            System.out.println(JSONObject.toJSONString(record));
            System.out.println("##########################################");
            
            YktConsumeRecord newrecord = new YktConsumeRecord();
            newrecord.setOrderNum("Z20160524142600000");
            
            newrecord.setStates(YktAPCOStatesEnum.CONSUME_CANCEL_SUCCESS.getCode());
            newrecord.setBeforeStates(YktAPCOStatesEnum.CONSUME_SUCCESS.getCode());
            newrecord.setUpdateUser(record.getCreateUser());
            newrecord.setUpdateDate(new Date());
                
            int n =  yktConsumeRecordMapper.updateByOrderNum(newrecord);

            record =  yktConsumeRecordMapper.selectByOrderNum("Z20160524142600000");
            
            System.out.println("##########################################");
            System.out.println(n);
            System.out.println(JSONObject.toJSONString(record));
            System.out.println("##########################################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
