package com.dodopal.portal.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.api.product.dto.query.RechargeStatisticsYktQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.PosOperTypeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.portal.business.bean.RechargeStatisticsYktBean;
import com.dodopal.portal.business.bean.TransactionDetailsBean;
import com.dodopal.portal.business.bean.query.ProductOrderQueryBean;
import com.dodopal.portal.business.bean.query.RechargeStatisticsYktQuery;
import com.dodopal.portal.business.service.RechargeForSupplierService;
import com.dodopal.portal.business.service.SupplierService;

@Controller
@RequestMapping("/prvd")
public class RechargeForSupplierController extends CommonController{

	private static Logger log = Logger.getLogger(RechargeForSupplierController.class);
	
	@Autowired
	private RechargeForSupplierService rechargeSupplierService;
	@Autowired
	private SupplierService supplierService;
	
	//一卡通充值统计
	@RequestMapping("/rechargeForSupplier")
    public ModelAndView ddpCard(Model model, HttpServletRequest request) {
		model.addAttribute("orderDateStart", DateUtils.getCurrentDateSub(-7, DateUtils.DATE_SMALL_STR));
        model.addAttribute("orderDateEnd", DateUtils.getCurrDate(DateUtils.DATE_SMALL_STR));
        return new ModelAndView("prvd/rechargeForSupplier");
    }
	
//	 //一卡通充值详情
//    @RequestMapping("/rechargeForDetails")
//    public ModelAndView SupplierMerchantPos(Model model,HttpServletRequest request) {
//        String posCode = request.getParameter("posCode");
//        String merName = request.getParameter("merName");
//        model.addAttribute("posCode", posCode);
//        model.addAttribute("merName", merName);
//        model.addAttribute("orderDateStart", DateUtils.getCurrentDateSub(-7, DateUtils.DATE_SMALL_STR));
//        model.addAttribute("orderDateEnd", DateUtils.getCurrDate(DateUtils.DATE_SMALL_STR));
//        return new ModelAndView("prvd/rechargeForDetails");
//    }
	//查询
	@RequestMapping("/findRechargeForSupplier")
	public @ResponseBody DodopalResponse<DodopalDataPage<RechargeStatisticsYktBean>> findRechargeForSupplier(HttpServletRequest request,@RequestBody RechargeStatisticsYktQuery query){
		DodopalResponse<DodopalDataPage<RechargeStatisticsYktBean>> response = new DodopalResponse<DodopalDataPage<RechargeStatisticsYktBean>>();
		try {
	        String yktCode = getYktCode(request.getSession());
	        query.setYktCode(yktCode);
			response = rechargeSupplierService.queryCardRechargeForSupplier(query);
			
		} catch (Exception e) {
			e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}
	
	@RequestMapping("/findRechargeDetails")
	public @ResponseBody DodopalResponse<DodopalDataPage<TransactionDetailsBean>> queryCardRechargeDetails(HttpServletRequest request,@RequestBody ProductOrderQueryBean query){
		DodopalResponse<DodopalDataPage<TransactionDetailsBean>> response = new DodopalResponse<DodopalDataPage<TransactionDetailsBean>>();
		try {
	        String yktCode = getYktCode(request.getSession());
	        query.setYktCode(yktCode);
			response = rechargeSupplierService.queryCardRechargeDetails(query);
		} catch (Exception e) {
			e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}
	
	//启用pos
	   @RequestMapping("batchStartRecharge")
	   public @ResponseBody DodopalResponse<String> startOrStopMerSupplier(HttpServletRequest request, @RequestBody String[] pos) {
	       DodopalResponse<String> response = new DodopalResponse<String>();
	       try {
	           String userId = getCurrentUserId(request.getSession());
	           String userName = getCurrentUserName(request.getSession());
	           String merCode = getCurrentMerchantCode(request.getSession());
	           PosOperTypeEnum posOper = PosOperTypeEnum.OPER_ENABLE ;
	           response = supplierService.posOper(posOper, merCode, pos, userId, userName);
	       }
	       catch (DDPException e) {
	           response.setCode(ResponseCode.SYSTEM_ERROR);
	           response.setMessage(e.getMessage());
	       }
	       catch (Exception e) {
	           e.printStackTrace();
	           response.setCode(ResponseCode.UNKNOWN_ERROR);
	           response.setMessage(e.getMessage());
	       }
	       return response;
	   }
	   
	   //pos停用
	   @RequestMapping("batchStopRecharge")
	   public @ResponseBody DodopalResponse<String> posStopOper(HttpServletRequest request, @RequestBody String[] pos) {
	       DodopalResponse<String> response = new DodopalResponse<String>();
	       try {
	           String userId = getCurrentUserId(request.getSession());
	           String userName = getCurrentUserName(request.getSession());
	           String merCode = getCurrentMerchantCode(request.getSession());
	           PosOperTypeEnum posOper = PosOperTypeEnum.OPER_DISABLE ;
	           response = supplierService.posOper(posOper, merCode, pos, userId, userName);
	       }
	       catch (DDPException e) {
	           response.setCode(ResponseCode.SYSTEM_ERROR);
	           response.setMessage(e.getMessage());
	       }
	       catch (Exception e) {
	           e.printStackTrace();
	           response.setCode(ResponseCode.UNKNOWN_ERROR);
	           response.setMessage(e.getMessage());
	       }
	       return response;
	   }
	   
	   //导出
	    @RequestMapping("/exportRechargeForSupplier")
	    public @ResponseBody DodopalResponse<String>  exportRechargeForSupplier(HttpServletRequest request,HttpServletResponse response) {
	        DodopalResponse<String> rep = new DodopalResponse<String>();
	        RechargeStatisticsYktQueryDTO queryDTO = new RechargeStatisticsYktQueryDTO();
	        //pos号
	        String proCode = request.getParameter("proCode");
	        String merName = request.getParameter("merName");
	        String bind = request.getParameter("bind");
	         //城市名称
	        String cityName = getCurrentCityName(request.getSession());
	        String yktCode = getYktCode(request.getSession());
	        if(yktCode != null && yktCode !=  ""){
	        	queryDTO.setYktCode(yktCode);
	        }
	        if(proCode != null && proCode !=  ""){
	        	 queryDTO.setProCode(proCode);
    		}
	        if(cityName != null && cityName !=  ""){
	        	 queryDTO.setCityName(cityName);
	        }
	        if(merName != null && merName !=  ""){
	        	queryDTO.setMerName(merName);
	        }
	        if(bind != null && bind !=  ""){
	        	queryDTO.setBind(bind);
	        }
	       
	        try {
	            rep = rechargeSupplierService.exportRechargeForSupplier(request,response,queryDTO);
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        return rep;
	    }
	    
	    
	  //导出详情
	    @RequestMapping("/exportCardRechargeDetails")
	    public @ResponseBody DodopalResponse<String>  exportCardRechargeDetails(HttpServletRequest request,HttpServletResponse response) {
	        DodopalResponse<String> rep = new DodopalResponse<String>();
	        ProductOrderQueryDTO queryDTO = new ProductOrderQueryDTO();
	        String posCode = request.getParameter("posCode");
	        String merName = request.getParameter("merName");
	        String proOrderNum = request.getParameter("proOrderNum");
	        String startDate = request.getParameter("orderDateStart");
	        String endDate = request.getParameter("orderDateEnd");
	        String yktCode = getYktCode(request.getSession());
	        if(yktCode != null && yktCode !=  ""){
	        	queryDTO.setYktCode(yktCode);
	        }
	        if(posCode != null && posCode !=  ""){
	        	queryDTO.setPosCode(posCode);
	        }
	        if(merName != null && merName !=  ""){
	        	queryDTO.setMerName(merName);
	        }
	        if(proOrderNum != null && proOrderNum !=  ""){
	        	queryDTO.setProOrderNum(proOrderNum);
	        }
	        if(startDate != null && startDate !=  ""){
	        	queryDTO.setOrderDateStart(DateUtils.stringtoDate(startDate, "yyyy-MM-dd"));
	        }
	        if(endDate != null && endDate !=  ""){
	        	queryDTO.setOrderDateEnd(DateUtils.stringtoDate(endDate, "yyyy-MM-dd"));
	        }
	        try {
	            rep = rechargeSupplierService.exportCardRechargeDetails(request,response,queryDTO);
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        return rep;
	    }
	    
}
