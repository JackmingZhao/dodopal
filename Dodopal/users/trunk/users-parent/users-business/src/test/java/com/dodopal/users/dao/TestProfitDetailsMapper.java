package com.dodopal.users.dao;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.users.business.dao.ProfitDetailsMapper;
import com.dodopal.users.business.model.ProfitDetails;
import com.dodopal.users.business.model.query.ProfitQuery;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:users-business-test-context.xml"})
public class TestProfitDetailsMapper {
    @Autowired
    private ProfitDetailsMapper mapper;
    
    @Test
    public void findList(){
    	ProfitQuery query = new ProfitQuery();
    	try{
    	 List<ProfitDetails> users = mapper.findProfitDetails(query);
    	 if(users!=null){
             System.out.println("##########################################");

              for (ProfitDetails dp : users) {
                 System.out.println("##########################################");
                 System.out.println(ReflectionToStringBuilder.toString(dp, ToStringStyle.MULTI_LINE_STYLE));
                 System.out.println("##########################################");
             }
         }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}
