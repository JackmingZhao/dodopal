package com.dodopal.product.facade;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.api.product.dto.CcOrderFormDTO;
import com.dodopal.api.product.dto.query.QueryCcOrderFormDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.product.business.model.CcOrderForm;
import com.dodopal.product.business.model.query.QueryCcOrderForm;
import com.dodopal.product.business.service.CcOrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:product-business-test-context.xml"})
public class CcOrderFaceTest {
    @Autowired
    CcOrderService ccOrderService;
    @Test
    public void findCcOrderForm() {
        System.out.println("============================================");
        DodopalResponse<DodopalDataPage<CcOrderFormDTO>> response = new DodopalResponse<DodopalDataPage<CcOrderFormDTO>>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            QueryCcOrderFormDTO queryCcOrderFormDTO = new QueryCcOrderFormDTO();
            queryCcOrderFormDTO.setMchnitid("411101101000033");
            QueryCcOrderForm queryDTO = new QueryCcOrderForm();
            BeanUtils.copyProperties(queryCcOrderFormDTO, queryDTO);
            //分页总条数如果为空新塞值进去
            if (queryDTO.getPage() == null) {
                queryDTO.setPage(new PageParameter());
            }
            DodopalDataPage<CcOrderForm> ddpResult = ccOrderService.findCcOrderFormByPage(queryDTO);
            if (ddpResult != null) {
                List<CcOrderForm> resultList = ddpResult.getRecords();
                List<CcOrderFormDTO> resResultList = null;
                if (resultList != null && resultList.size() > 0) {
                    resResultList = new ArrayList<CcOrderFormDTO>(resultList.size());
                    for (CcOrderForm cor : resultList) {
                        CcOrderFormDTO cofDto = new CcOrderFormDTO();
                        BeanUtils.copyProperties(cor, cofDto);
                        resResultList.add(cofDto);
                    }
                }
                PageParameter page = DodopalDataPageUtil.convertPageInfo(ddpResult);
                DodopalDataPage<CcOrderFormDTO> ddpDTOResult = new DodopalDataPage<CcOrderFormDTO>(page, resResultList);
                response.setResponseEntity(ddpDTOResult);
            } else {
                response.setCode(ResponseCode.V71_POS_ERROR);
            }
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        System.out.println(response.getCode());
    }
}
