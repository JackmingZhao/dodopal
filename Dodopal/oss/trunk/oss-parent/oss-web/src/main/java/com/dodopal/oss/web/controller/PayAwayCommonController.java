package com.dodopal.oss.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.BankGatewayTypeEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.ExpTempletService;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.bean.PayAwayBean;
import com.dodopal.oss.business.bean.PayAwayCommonBean;
import com.dodopal.oss.business.model.dto.PayAwayCommonQuery;
import com.dodopal.oss.business.model.dto.PayWayGeneralQuery;
import com.dodopal.oss.business.service.PayAwayCommonService;
/**
 * 用户常用支付方式
 * @author xiongzhijing@dodopal.com
 * @version 2015年8月11 日
 */
@Controller
@RequestMapping("/payment")
public class PayAwayCommonController extends CommonController {

    @Autowired
    PayAwayCommonService payAwayCommonService;
    @Autowired
    private ExpTempletService expTempletService;
    
    private Logger logger = LoggerFactory.getLogger(RiskAccountController.class);
    
    @RequestMapping("pay/userCommon")
    public ModelAndView traflow(HttpServletRequest request) {
        return new ModelAndView("payment/pay/userCommon");
    }

    //查询分页
    @RequestMapping("pay/findPayAwayCommon")
    public @ResponseBody DodopalResponse<DodopalDataPage<PayAwayCommonBean>> findPayAwayByPage(HttpServletRequest request, @RequestBody PayAwayCommonQuery payQuery) {
        DodopalResponse<DodopalDataPage<PayAwayCommonBean>> getResponse = new DodopalResponse<DodopalDataPage<PayAwayCommonBean>>();
        try {
            getResponse = payAwayCommonService.findPayAwayCommonList(payQuery);
        }
        catch (Exception e) {
            e.printStackTrace();
            getResponse.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return getResponse;
    }

    //删除
    @RequestMapping("pay/deleteUserCommon")
    public @ResponseBody DodopalResponse<String> deletePayCommon(HttpServletRequest request, @RequestBody String[] id) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            if (id != null && id.length > 0) {
                List<String> ids = new ArrayList<String>(Arrays.asList(id));
                DodopalResponse<Integer> dodopalnum = payAwayCommonService.deletePayAwayCommon(ids);
                response.setCode(dodopalnum.getCode());
            } 
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    @RequestMapping("pay/exportPayAwayCommon")
    public @ResponseBody DodopalResponse<String> exportExcelGeneral(HttpServletRequest req, HttpServletResponse res) {
    	DodopalResponse<String> resp = new DodopalResponse<String>();
    	
    	PayAwayCommonQuery queryDTO = new PayAwayCommonQuery();
    	 
    	String userNameQuery = req.getParameter("userNameQuery");
    	String payTypeQuery = req.getParameter("payTypeQuery");
    	String payWayNameQuery = req.getParameter("payWayNameQuery");
    	String activateQuery = req.getParameter("activateQuery");
    	
    	try {
    		if(userNameQuery!=null && userNameQuery!=""){
    			queryDTO.setUserName(userNameQuery);
    		}
    		if(payTypeQuery!=null && payTypeQuery!=""){
    			queryDTO.setPayType(payTypeQuery);
    		}
    		if(payWayNameQuery!=null && payWayNameQuery!=""){
    			queryDTO.setPayWayName(payWayNameQuery);
    		}
    		if(activateQuery!=null && activateQuery!=""){
    			queryDTO.setActivate(activateQuery);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	int exportMaxNum = 5000;
    	queryDTO.setPage(new PageParameter(1, exportMaxNum));	// MikaelyanMikaelyanMikaelyanMikaelyan 1000
    	DodopalResponse<DodopalDataPage<PayAwayCommonBean>> response = payAwayCommonService.findPayAwayCommonList(queryDTO);
    	List<PayAwayCommonBean> list = response.getResponseEntity().getRecords();
    	if(CollectionUtils.isNotEmpty(list)){
    		for(PayAwayCommonBean bean :list){
    			bean.setActivate(ActivateEnum.getActivateByCode(bean.getActivate()).getName());
    			bean.setPayType(PayTypeEnum.getPayTypeEnumByCode(bean.getPayType()).getName());
    		}
    	}
    	int resultSize = list.size();
    	if(resultSize > 5000) {
    		logger.warn("export over max size ：" + exportMaxNum + ", real size : " + resultSize);
			resp.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
			return resp;
    	}
    	String sheetName = new String("用户常用支付方式");
    	
    	Map<String, String> col = expTempletService.getCloName(req.getParameter("col").split("@"), req.getParameter("typeSelStr"));
    	String resultCode = ExcelUtil.excelExport(req,res, list, col, sheetName);
    	
		resp.setCode(resultCode);
    	
    	return resp;
    }
}
