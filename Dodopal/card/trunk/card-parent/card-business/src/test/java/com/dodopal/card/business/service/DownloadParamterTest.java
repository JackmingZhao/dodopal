package com.dodopal.card.business.service;

import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.api.card.dto.ParameterList;
import com.dodopal.api.card.dto.SpecialModel;
import com.dodopal.api.card.dto.SubPeriodDiscountParameter;
import com.dodopal.api.card.facade.BJIcdcRechargeCardV71Facade;
import com.dodopal.api.card.facade.SignInSignOutCardFacade;
import com.dodopal.card.business.dao.ParameterMapper;
import com.dodopal.card.business.model.query.ParameterQuery;
import com.dodopal.common.enums.SpecialModelParamNOEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalAppVarPropsUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:card-business-test-context.xml"})
public class DownloadParamterTest {
    @Autowired
    SignInSignOutCardFacade signInSignOutCardFacade;
    @Autowired
    ParameterMapper parameterMapper;
    @Autowired
    ParameterService parameterService;
    @Autowired
    BJIcdcRechargeCardV71Facade bjIcdcRechargeCardV71Facade;
    
    //@Test
    public void findCrdSysOrderByCodeTest(){
        ParameterList parameterList = new ParameterList();
        SpecialModel model = new SpecialModel();
        model.setReqtotal("10");
        model.setReqindex("0");
        model.setParno(SpecialModelParamNOEnum.p06.getCode());
        parameterList.setCrdm(model);
        DodopalResponse<ParameterList> li =  signInSignOutCardFacade.downloadParameter(parameterList);
        if (li != null) {
            System.out.println("##########################################");
            System.out.println(ReflectionToStringBuilder.toString(li.getResponseEntity(), ToStringStyle.MULTI_LINE_STYLE));
            System.out.println("##########################################");
            for (Object dp : li.getResponseEntity().getCrdm().getListPars()) {
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(dp, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
            }
        }
    }
    //@Test
    public void findDIS(){
        ParameterQuery query = new ParameterQuery();
        query.setMerCode("321");
        
        DodopalDataPage<SubPeriodDiscountParameter> li =  parameterService.findSubPeriodDiscountParameterByPage(query);
        if (li != null) {
            System.out.println("##########################################");
            System.out.println(ReflectionToStringBuilder.toString(li.getRecords(), ToStringStyle.MULTI_LINE_STYLE));
            System.out.println("##########################################");
            for (SubPeriodDiscountParameter  dp : li.getRecords()) {
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(dp, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
            }
        }
    }
    
    //@Test
    public void findPosTest(){
        PageParameter page = new PageParameter();
        ParameterQuery query = new ParameterQuery();
        page.setPageNo(0);
        page.setPageSize(100);
        query.setPage(page);
        parameterMapper.findTerminalMenuParameterByPage(query);
    }
    
    
    
}
