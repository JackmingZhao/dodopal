package com.dodopal.card.business.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.card.business.facadeImpl.FrontFailReportCardFacadeImpl;
import com.dodopal.common.model.DodopalResponse;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:card-business-test-context.xml"})
public class FrontFailReportServiceTest {

    @Autowired
    private FrontFailReportCardFacadeImpl frontFailReportCardFacadeImpl;

    @Test
    public void queryCardSysOrder() {
        try {
            CrdSysOrderDTO dto = new CrdSysOrderDTO();
            dto.setPrdordernum("123");
        	DodopalResponse<CrdSysOrderDTO> rs = frontFailReportCardFacadeImpl.frontFailReportFun(dto);
        	System.out.println(rs.getCode());
        	System.out.println(rs.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
