package com.dodopal.oss.business.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.oss.business.model.Pos;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:oss-business-test-context.xml"})
public class PosMapperTest {

    @Autowired
    private PosMapper posMapper;
    
//    @Test
    public void testselectMultipleKeys() {
        List<String> list = posMapper.selectMultipleKeys(10);
        System.out.println(list);
    }
    
    @Test
    public void testInsertPosBatch() {
        List<Pos> list = new ArrayList<Pos>();
        List<String> keys = posMapper.selectMultipleKeys(6);
        for(int i =10 ; i< 5; i++) {
            Pos pos = new Pos();
            pos.setCode("231231000" + i);
            pos.setBind("1");
            pos.setComments("Junit Test");
            pos.setId("1");
            pos.setMaxTimes(123);
            pos.setMerchantCode("test");
            pos.setMerchantName("test");
            pos.setVersion("12312");
            pos.setPosCompanyCode("a12");
            pos.setPosTypeCode("test");
            pos.setStatus("1");
            pos.setUnitCost(new BigDecimal(1));
            pos.setId(keys.get(i-10));
            list.add(pos);
        }
        posMapper.insertPosBatch(list);
    }

//    @Test
    public void testUpdateVersion() {
        try {
            String codes = "test,test2";
            String version = "1111";
            List<String> list = new ArrayList<String>();
            Map<String, Object> map = new HashMap<String, Object>();
            String[] codeArray = codes.split(",");
            for (String code : codeArray) {
                list.add(code);
            }
            map.put("list", list);
            map.put("version", version);
            posMapper.modifyVersion(map);

            Pos pos = new Pos();
            pos.setCode("test");
            List<Pos> posList = posMapper.findPoss(pos);
            if(posList != null) {
                for(Pos p : posList) {
                    System.out.println("################"+ ReflectionToStringBuilder.toString(p, ToStringStyle.MULTI_LINE_STYLE) + "##########################"); 
                }
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
//    @Test
    public void testUpdateMaxTimes() {
        try {
            String codes = "test,test2";
            int maxTimes = 2222;
            List<String> list = new ArrayList<String>();
            Map<String, Object> map = new HashMap<String, Object>();
            String[] codeArray = codes.split(",");
            for (String code : codeArray) {
                list.add(code);
            }
            map.put("list", list);
            map.put("maxTimes", maxTimes);
            posMapper.modifyLimitation(map);

            Pos pos = new Pos();
            pos.setCode("test");
            List<Pos> posList = posMapper.findPoss(pos);
            if(posList != null) {
                for(Pos p : posList) {
                    System.out.println("################"+ ReflectionToStringBuilder.toString(p, ToStringStyle.MULTI_LINE_STYLE) + "##########################"); 
                }
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
//    @Test
    public void testUpdateCityCode() {
        try {
            String codes = "test,test2";
            String cityCode = "test";
            List<String> list = new ArrayList<String>();
            Map<String, Object> map = new HashMap<String, Object>();
            String[] codeArray = codes.split(",");
            for (String code : codeArray) {
                list.add(code);
            }
            map.put("list", list);
            map.put("cityCode", cityCode);
            map.put("cityName", "test");
            map.put("provinceCode", "test");
            map.put("provinceName", "test");
            posMapper.modifyCity(map);

            Pos pos = new Pos();
            pos.setCode("test");
            List<Pos> posList = posMapper.findPoss(pos);
            if(posList != null) {
                for(Pos p : posList) {
                    System.out.println("################"+ ReflectionToStringBuilder.toString(p, ToStringStyle.MULTI_LINE_STYLE) + "##########################"); 
                }
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
