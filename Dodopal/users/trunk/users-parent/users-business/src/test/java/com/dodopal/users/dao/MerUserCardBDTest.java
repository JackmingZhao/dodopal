package com.dodopal.users.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.dodopal.users.business.dao.MerUserCardBDMapper;
import com.dodopal.users.business.model.MerUserCardBD;
import com.dodopal.users.business.model.MerchantUser;

/**
 * @author Dingkuiyuan@dodopal.com
 * @version 创建时间：2015年5月4日 下午2:28:47
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:users-business-test-context.xml"})
public class MerUserCardBDTest {
    @Autowired
    private MerUserCardBDMapper bdMapper;
    
    //@Test
    public void saveMerUserCardBDTest(){
        MerUserCardBD merUserCardBd = new MerUserCardBD();
        merUserCardBd.setMerUserName("lisi");
        merUserCardBd.setCardCode("210");
        String year = "2015-05-04 16:15";
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        try {
            dateFormat.parse(year);
        }
        catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        merUserCardBd.setCreateDate(new Date());
        merUserCardBd.setBundLingType("1");
        bdMapper.saveMerUserCardBD(merUserCardBd);
    }
   // @Test
    public void findMerUserCardBDTest(){
        MerUserCardBD merUserCardBd = new MerUserCardBD();
//        String year = "2015-05-04 16:15:43";
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//            merUserCardBd.setBundLingDateStart(dateFormat.parse(year));
//        }
//        catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        merUserCardBd.setMerUserName("zhangsan");
      List<MerUserCardBD>list =   bdMapper.findMerUserCardBD(merUserCardBd);
        if(list!=null){
            System.out.println("##########################################");
            System.out.println(ReflectionToStringBuilder.toString(list, ToStringStyle.MULTI_LINE_STYLE));
            System.out.println("##########################################");

             for (MerUserCardBD dp : list) {
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(dp, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
            }
        }
    }
    @Test
    public void testUnBund(){
        List list = new ArrayList();
        list.add("10000000000000000041");
        list.add("10000000000000000022");
        Map map  =  new HashMap();
        map.put("name", "yy");
        map.put("list", list);
        int a  = bdMapper.unBundlingCard(map);
        System.out.println(a);
    }
}
