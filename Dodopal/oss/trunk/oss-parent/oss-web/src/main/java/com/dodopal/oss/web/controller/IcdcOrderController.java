package com.dodopal.oss.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.api.product.dto.ProductOrderDetailDTO;
import com.dodopal.api.product.dto.ProductOrderForExport;
import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.ExpTempletService;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.bean.ProductOrder;
import com.dodopal.oss.business.service.IcdcPrdService;
import com.dodopal.oss.business.service.ProductOrderService;

/**
 * 公交卡充值订单
 */
@Controller
@RequestMapping("/product")
public class IcdcOrderController {

    private Logger logger = LoggerFactory.getLogger(IcdcOrderController.class);
    
    @Autowired
    ProductOrderService productOrderService;
    @Autowired
    private IcdcPrdService icdcPrdService;
    @Autowired
    private ExpTempletService expTempletService;
    

    @RequestMapping("/buscardbusiness/icdcOrder")
    public ModelAndView cardEncryptMgmt(HttpServletRequest request) {
        return new ModelAndView("product/buscardbusiness/icdcOrder");
    }

    /**
     * 5.4.1    订单查询
     */
    @RequestMapping("/buscardbusiness/findProductOrders")
    public @ResponseBody DodopalResponse<DodopalDataPage<ProductOrder>> findProductOrder(HttpServletRequest request, @RequestBody ProductOrderQueryDTO queryDto) {
        DodopalResponse<DodopalDataPage<ProductOrder>> response = new DodopalResponse<DodopalDataPage<ProductOrder>>();
        try {
            response = productOrderService.findProductOrder(queryDto);
        }
        catch (Exception e) {
            logger.error("查询公交卡充值订单时发生错误:", e);
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }
    
    /**
     * 5.4.2    订单查看
     */
    @RequestMapping("/buscardbusiness/viewProductOrderDetails")
    public @ResponseBody DodopalResponse<ProductOrderDetailDTO> viewProductOrderDetails(HttpServletRequest request, @RequestBody String proOrderNum) {
        DodopalResponse<ProductOrderDetailDTO> response = null;
        try {
            response = productOrderService.findProductOrderDetails(proOrderNum);
        }
        catch (Exception e) {
            response = new DodopalResponse<ProductOrderDetailDTO>();
            logger.error("查询公交卡充值订单时发生错误:", e);
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**
     * 充值订单导出
     */
    @RequestMapping("/buscardbusiness/exportIcdcOrder")
    public @ResponseBody DodopalResponse<String>  exportIcdcOrder(HttpServletRequest request,HttpServletResponse response) {
    	DodopalResponse<String> exportResponse = new DodopalResponse<String>();
    	try {
    		ProductOrderQueryDTO prdOrderQuery = new ProductOrderQueryDTO();
    		prdOrderQuery.setProOrderNum(request.getParameter("proOrderNumQuery"));
    		prdOrderQuery.setProOrderState(request.getParameter("proOrderStateQuery"));
    		prdOrderQuery.setOrderDateStart(DateUtils.stringtoDate(request.getParameter("orderDateStartQuery"), DateUtils.DATE_SMALL_STR));
    		prdOrderQuery.setOrderDateEnd(DateUtils.stringtoDate(request.getParameter("orderDateEndQuery"), DateUtils.DATE_SMALL_STR));
    		prdOrderQuery.setOrderCardno(request.getParameter("orderCardnoQuery"));
    		prdOrderQuery.setPosCode(request.getParameter("posCodeQuery"));
    		prdOrderQuery.setMerOrderNum(request.getParameter("merOrderNumQuery"));
    		String txnAmtStartQuery = request.getParameter("txnAmtStartQuery");
    		if(StringUtils.isNotBlank(txnAmtStartQuery)) {
    			Double d = Double.valueOf(txnAmtStartQuery)*100;
    			prdOrderQuery.setTxnAmtStart(d.toString());
    		}
    		String txnAmtEndQuery = request.getParameter("txnAmtEndQuery");
    		if(StringUtils.isNotBlank(txnAmtEndQuery)) {
    			Double d = Double.valueOf(txnAmtEndQuery)*100;
    			prdOrderQuery.setTxnAmtEnd(d.toString());
    		}
    		prdOrderQuery.setProInnerState(request.getParameter("proInnerStateQuery"));
    		prdOrderQuery.setMerName(request.getParameter("merNameQuery"));
    		prdOrderQuery.setMerType(request.getParameter("merTypeQuery"));
    		prdOrderQuery.setPayType(request.getParameter("payTypeQuery"));
    		prdOrderQuery.setYktCode(request.getParameter("yktCode"));
            prdOrderQuery.setSuspiciouStates(request.getParameter("suspiciouStates"));

            DodopalResponse<List<ProductOrderForExport>> list = productOrderService.getRechargeOrderListForExportExcel(prdOrderQuery);
            exportResponse.setCode(list.getCode());
            
            if (!ResponseCode.SUCCESS.equals(list.getCode())) {
                return exportResponse;
            }
            List<ProductOrderForExport>  listExport = list.getResponseEntity();
            
            String sheetName = new String("一卡通充值订单信息");
            
            Map<String, String> col = expTempletService.getCloName(request.getParameter("col").split("@"), request.getParameter("typeSelStr"));
            String resultCode = ExcelUtil.excelExport(request, response, listExport, col, sheetName);
            
            exportResponse.setCode(resultCode);
            
            return exportResponse;
    		
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			exportResponse = new DodopalResponse<String>();
			exportResponse.setCode(ResponseCode.UNKNOWN_ERROR);
			exportResponse.setMessage(e.getMessage());
		}
    	return exportResponse;
    }
    
    /**
     * 获取通卡公司combox下拉列表信息
     * @param request
     * @return
     */
    @RequestMapping("/buscardbusiness/findAllYktNames")
    public @ResponseBody List<Map<String, String>> getIcdcNames(HttpServletRequest request,@RequestParam(value = "activate", required = false) String activate) {
        DodopalResponse<List<Map<String, String>>> rs = null;
        List<Map<String, String>> jsonData = new ArrayList<>();
        rs = icdcPrdService.queryIcdcNames(activate);
        List<Map<String, String>> mapList = rs.getResponseEntity();
        for (int i = 0; i < mapList.size(); i++) {
            Map<String, String> value = new HashMap<>();
            Map<String, String> map = mapList.get(i);
            Set<String> k = map.keySet();
            for (String key : k) {
                value.put("id", key);
                value.put("name", map.get(key));
                jsonData.add(value);
                break;
            }
        }
        return jsonData;
    }
}
