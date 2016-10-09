package com.dodopal.oss.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.dodopal.oss.business.bean.MerchantUserBean;
import com.dodopal.oss.business.bean.PayAwayBean;
import com.dodopal.oss.business.bean.PaymentBean;
import com.dodopal.oss.business.model.dto.PayMentQuery;
import com.dodopal.oss.business.model.dto.PayWayGeneralQuery;
import com.dodopal.oss.business.service.PaymentService;


/**
 * 支付管理
 * @author hxc
 *
 */
@Controller
@RequestMapping("/payment")
public class PayMgmtController extends CommonController{
    
    @Autowired
    PaymentService paymentService;
    @Autowired
    private ExpTempletService expTempletService;
    
    private Logger logger = LoggerFactory.getLogger(RiskAccountController.class);
    
    @RequestMapping("pay/payFlow")
    public ModelAndView findPaymentListByPage(HttpServletRequest request){
        return new ModelAndView("payment/pay/payFlow");
    }
    
    /**
     * 支付流水查询
     * @param request
     * @param payMent
     * @return
     */
    @RequestMapping("pay/findPayFlow")
    public @ResponseBody DodopalResponse<DodopalDataPage<PaymentBean>> findPaymentListByPage(HttpServletRequest request,@RequestBody PayMentQuery payMent){
        DodopalResponse<DodopalDataPage<PaymentBean>> response = new DodopalResponse<DodopalDataPage<PaymentBean>>();
        try {
          response = paymentService.findPaymentListByPage(payMent);
      }catch (Exception e) {
          e.printStackTrace();
          response.setCode(ResponseCode.UNKNOWN_ERROR);
      }
      return response;
    }

    /**
     * 查询详细流水信息
     * @return
     */
    @RequestMapping("pay/findPayFlowById")
    public @ResponseBody DodopalResponse<PaymentBean>  findPayFlowById(HttpServletRequest request,@RequestBody String pid){
       
        DodopalResponse<PaymentBean> response = new DodopalResponse<PaymentBean>();
        try {
            response = paymentService.findPayment(pid);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
     return response;
   }
    
    
    @RequestMapping("pay/exportPayFlow")
    public @ResponseBody DodopalResponse<String> exportExcelGeneral(HttpServletRequest req, HttpServletResponse res) {
    	DodopalResponse<String> resp = new DodopalResponse<String>();
    	
    	PayMentQuery queryDTO = new PayMentQuery();
    	 
    	String payWayName = req.getParameter("payWayName");
    	String payStatus = req.getParameter("payStatus");
    	String tranCode = req.getParameter("tranCode");
    	String payType = req.getParameter("payType");;
    	
    	try {
    		if(payWayName!=null && payWayName!=""){
    			queryDTO.setPayWayName(payWayName);
    		}
    		if(payStatus!=null && payStatus!=""){
    			queryDTO.setPayStatus(payStatus);
    		}
    		if(tranCode!=null && tranCode!=""){
    			queryDTO.setTranCode(tranCode);
    		}
    		if(payType!=null && payType!=""){
    			queryDTO.setPayType(payType);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	int exportMaxNum = 5000;
    	queryDTO.setPage(new PageParameter(1, exportMaxNum));	// MikaelyanMikaelyanMikaelyanMikaelyan 1000
    	DodopalResponse<DodopalDataPage<PaymentBean>> response = paymentService.findPaymentListByPage(queryDTO);
    	List<PaymentBean> list = response.getResponseEntity().getRecords();
    	int resultSize = list.size();
    	if(resultSize > 5000) {
    		logger.warn("export over max size ：" + exportMaxNum + ", real size : " + resultSize);
			resp.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
			return resp;
    	}
    	String sheetName = new String("支付流水");
    	
    	Map<String, String> col = expTempletService.getCloName(req.getParameter("col").split("@"), req.getParameter("typeSelStr"));
    	String resultCode = ExcelUtil.excelExport(req,res, list, col, sheetName);
    	
		resp.setCode(resultCode);
    	
    	return resp;
    }
    
}
