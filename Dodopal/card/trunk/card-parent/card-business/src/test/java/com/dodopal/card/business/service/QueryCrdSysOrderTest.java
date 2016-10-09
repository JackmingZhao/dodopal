package com.dodopal.card.business.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.card.business.dao.CrdSysOrderMapper;
import com.dodopal.card.business.model.dto.CrdSysOrderQuery;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:card-business-test-context.xml"})
public class QueryCrdSysOrderTest {

    @Autowired
    CrdSysOrderMapper crdSysOrderMapper;
    
   // @Test
    public void findCrdSysOrderByPage(){
        CrdSysOrderQuery crdSysOrder = new CrdSysOrderQuery();
        crdSysOrder.setMerCode("123");
        crdSysOrderMapper.findCrdSysOrderByPage(crdSysOrder);
        System.out.println(ReflectionToStringBuilder.toString(crdSysOrder, ToStringStyle.MULTI_LINE_STYLE));
    }
    
    @Test
    public void findCrdSysOrderByCodeTest(){
        String crdOrderNum ="123";
        crdSysOrderMapper.findCrdSysOrderByCode(crdOrderNum);
        System.out.println(ReflectionToStringBuilder.toString(crdOrderNum, ToStringStyle.MULTI_LINE_STYLE));
    }
}
