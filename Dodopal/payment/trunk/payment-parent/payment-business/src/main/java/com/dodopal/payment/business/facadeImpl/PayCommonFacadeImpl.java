package com.dodopal.payment.business.facadeImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.payment.dto.PayWayCommonDTO;
import com.dodopal.api.payment.dto.query.PayCommonQueryDTO;
import com.dodopal.api.payment.facade.PayCommonFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.payment.business.model.PayWayCommon;
import com.dodopal.payment.business.model.query.PayCommonQuery;
import com.dodopal.payment.business.service.PayCommonService;

@Service("PayCommonFacade")
public class PayCommonFacadeImpl implements PayCommonFacade {

    private final static Logger log = LoggerFactory.getLogger(PayCommonFacadeImpl.class);
    
    @Autowired
    private PayCommonService payCommonService;
    
    public DodopalResponse<DodopalDataPage<PayWayCommonDTO>> findPayCommonByPage(PayCommonQueryDTO commonDto) {
        DodopalResponse<DodopalDataPage<PayWayCommonDTO>> dodopalResponse = new DodopalResponse<DodopalDataPage<PayWayCommonDTO>>();
        PayCommonQuery commonQuery = new PayCommonQuery();
        try {
            PropertyUtils.copyProperties(commonQuery, commonDto);
            DodopalDataPage<PayWayCommon> pagelist = payCommonService.findPayCommonByPage(commonQuery);
            List<PayWayCommon> list = pagelist.getRecords();
            List<PayWayCommonDTO> payDTOList = new ArrayList<PayWayCommonDTO>();
            if (CollectionUtils.isNotEmpty(list)) {
                for (PayWayCommon payWayCommon : list) {
                    PayWayCommonDTO dto = new PayWayCommonDTO();
                    PropertyUtils.copyProperties(dto, payWayCommon);
                    payDTOList.add(dto);
                }
            }
            
            PageParameter page = DodopalDataPageUtil.convertPageInfo(pagelist);
            DodopalDataPage<PayWayCommonDTO> pages = new DodopalDataPage<PayWayCommonDTO>(page, payDTOList);
            dodopalResponse.setCode(ResponseCode.SUCCESS);
            dodopalResponse.setResponseEntity(pages);
        }
        catch (Exception e) {
            log.debug("PayCommonFacadeImpl call error", e);
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }

    /**
     * 删除
     */
    public DodopalResponse<Integer> deletePayCommonByIds(List<String> ids) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        int payCommon=0;
        try {
            if(ids != null && ids.size()>0){
                payCommon = payCommonService.deletePayCommonByIds(ids);
                response.setCode(ResponseCode.SUCCESS);
            }
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        response.setResponseEntity(payCommon);
        return response;
    }

	//新增
	public DodopalResponse<List<Integer>> insertPayWayCommon(
			List<PayWayCommonDTO> commons) {
		DodopalResponse<List<Integer>> response = new DodopalResponse<List<Integer>>();
		List<PayWayCommon> list = new ArrayList<PayWayCommon>();
		try {
			for(PayWayCommonDTO commonDto : commons){
				PayWayCommon payWayCommon = new PayWayCommon();
				PropertyUtils.copyProperties(payWayCommon, commonDto);
				list.add(payWayCommon);
			}
			List<Integer> common = payCommonService.insertPayWayCommon(list);
			response.setCode(ResponseCode.SUCCESS);
			response.setResponseEntity(common);
		} catch (Exception e) {
			 response.setCode(ResponseCode.SYSTEM_ERROR);
	         e.printStackTrace();
		}
		return response;
	}

	//删除
	public DodopalResponse<Integer> deletePaywayCommon(String userCode) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        int payCommon=0;
        try {
                payCommon = payCommonService.deletePaywayCommon(userCode);
                response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        response.setResponseEntity(payCommon);
        return response;
    }

}
