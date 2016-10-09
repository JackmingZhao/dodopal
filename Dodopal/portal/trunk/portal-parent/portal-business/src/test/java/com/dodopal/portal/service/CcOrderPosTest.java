package com.dodopal.portal.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.CcOrderFormBean;
import com.dodopal.portal.business.bean.query.QueryCcOrderFormBean;
import com.dodopal.portal.business.service.CcOrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:portal-business-test-context.xml"})
public class CcOrderPosTest {
@Autowired
CcOrderService ccOrderService;
//@Test
public void findCcOrderTest() {
    DodopalResponse<DodopalDataPage<CcOrderFormBean>> response = new DodopalResponse<DodopalDataPage<CcOrderFormBean>>();
    try {
        QueryCcOrderFormBean queryCcOrderFormBean = new QueryCcOrderFormBean();
        queryCcOrderFormBean.setMchnitid("411101101000033");
        response = ccOrderService.findCcOrderByPage(queryCcOrderFormBean);
    }
    catch (Exception e) {
        e.printStackTrace();
        response.setCode(ResponseCode.PORTAL_CHILD_MERCHANT_FIND_ERR);
    }
  System.out.println(response.getCode());
}
}
