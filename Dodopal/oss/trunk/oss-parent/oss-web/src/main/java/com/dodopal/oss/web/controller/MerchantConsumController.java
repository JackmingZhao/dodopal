package com.dodopal.oss.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.ExpTempletService;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.bean.MerchantConsumBean;
import com.dodopal.oss.business.bean.query.MerchantConsumQuery;
import com.dodopal.oss.business.service.IcdcPrdService;
import com.dodopal.oss.business.service.MerchantconsumService;
/**
 * 数据中心-财务报表-商户消费明细
 * @author tao
 *
 */
@Controller
@RequestMapping("/statistics/finance/report")
public class MerchantConsumController extends CommonController{

	private Logger logger = LoggerFactory.getLogger(MerchantConsumController.class);
	
	@Autowired
	private MerchantconsumService merchantconsumService; 
	@Autowired
    private IcdcPrdService icdcPrdService;
	@Autowired
	private ExpTempletService expTempletService;
	
	@RequestMapping("/merConsumption")
   public ModelAndView merchantConsumInfo(HttpServletRequest request,ModelMap modelMap) {
		modelMap.put("startDate", DateUtils.getCurrentDateSub(-7,DateUtils.DATE_SMALL_STR));
		modelMap.put("endDate", DateUtils.getCurrDate(DateUtils.DATE_SMALL_STR));
       return new ModelAndView("statistics/finance/merchantConsumInfo");
   }
	
	@RequestMapping("/findMerchantConsumInfoByPage")
	public @ResponseBody DodopalResponse<DodopalDataPage<MerchantConsumBean>> findMerchantConsumInfoByPage(HttpServletRequest request,@RequestBody MerchantConsumQuery merchantConsumQuery){
		DodopalResponse<DodopalDataPage<MerchantConsumBean>> response = new DodopalResponse<DodopalDataPage<MerchantConsumBean>>();
		try {
			DodopalDataPage<MerchantConsumBean> dataPage = merchantconsumService.findMerchantConsumInfo(merchantConsumQuery);
			response.setResponseEntity(dataPage);
			response.setCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("商户消费明细统计发生错误:"+e.getMessage(), e);
           response.setCode(ResponseCode.UNKNOWN_ERROR);
		}
		return response;
	}
	
	/**
     * 获取通卡公司
     * @param request
     * @return
     */
    @RequestMapping(value = "/getIcdcNames")
    public @ResponseBody List<Map<String, String>> getIcdcNames(HttpServletRequest request, @RequestParam(value = "activate", required = false) String activate) {
        DodopalResponse<List<Map<String, String>>> rs = null;
        List<Map<String, String>> jsonData = new ArrayList<>();
        rs = icdcPrdService.queryIcdcNames(activate);
        List<Map<String, String>> mapList = rs.getResponseEntity();
        for (int i = 0; i < mapList.size(); i++) {
            Map<String, String> value = new HashMap<>();
            Map<String, String> map = mapList.get(i);
            Set<String> k = map.keySet();
            for (String key : k) {
                value.put("yktCode", key);
                value.put("yktName", map.get(key));
                jsonData.add(value);
                break;
            }
        }
        return jsonData;
    }
	
	
	@RequestMapping("exportMerchantConsumInfo")
   public @ResponseBody DodopalResponse<String> exportExcelCustomerRecharge(HttpServletRequest request, HttpServletResponse response) {
	   	DodopalResponse<String> resp = new DodopalResponse<String>();
	   	String merName = request.getParameter("merName");
	   	String yktCode = request.getParameter("yktCode");
		String bussinessType = request.getParameter("bussinessType");
	   	String startDate = request.getParameter("startDate");
	   	String endDate = request.getParameter("endDate");
	   	MerchantConsumQuery merchantConsumQuery = new MerchantConsumQuery();
	   	merchantConsumQuery.setYktCode(yktCode);
	   	merchantConsumQuery.setMerName(merName);
	   	merchantConsumQuery.setBussinessType(bussinessType);
	   	merchantConsumQuery.setStartDate(startDate);
	   	merchantConsumQuery.setEndDate(endDate);
	   	int count = merchantconsumService.findMerchantConsumInfoCount(merchantConsumQuery);
	   	int exportMaxNum = 50000;
	   	if(count > exportMaxNum){
	   		logger.warn("export over max size ：" + exportMaxNum + ", real size : " + count);
			resp.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
			return resp;
	   	}
	   	try{
	   		List<MerchantConsumBean> list = merchantconsumService.findMerchantConsumInfoExcel(merchantConsumQuery);
	   		String sheetName = new String("商户消费明细统计");
		   	Map<String, String> col = expTempletService.getCloName(request.getParameter("col").split("@"), request.getParameter("typeSelStr"));
		   	String resultCode = ExcelUtil.excelExport(request,response, list, col, sheetName);
		   	resp.setCode(resultCode);
	   	}catch (Exception e) {
			logger.error("商户消费明细统计导出发生错误:"+e.getMessage(), e);
			resp.setCode(ResponseCode.UNKNOWN_ERROR);
			}
	   	return resp;
	   }
}
