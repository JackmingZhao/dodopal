package com.dodopal.oss.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
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

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.BankGatewayTypeEnum;
import com.dodopal.common.enums.PayAwayEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.ExpTempletService;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.bean.PayAwayBean;
import com.dodopal.oss.business.bean.PayAwayCommonBean;
import com.dodopal.oss.business.bean.PayConfigBean;
import com.dodopal.oss.business.constant.OSSConstants;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.model.dto.PayAwayCommonQuery;
import com.dodopal.oss.business.model.dto.PayAwayQuery;
import com.dodopal.oss.business.service.PayAwayService;
import com.dodopal.oss.business.service.PayConfigService;
/**
 * 外接支付方式
 * @author xiongzhijing 
 * @version 2015年7月30日创建
 *
 */
@Controller
@RequestMapping("/payment")
public class PayAwayController extends CommonController {

    @Autowired
    PayAwayService payAwayService;
    
    @Autowired
    PayConfigService payConfigService;

    @Autowired
    private ExpTempletService expTempletService;
    
    private Logger logger = LoggerFactory.getLogger(RiskAccountController.class);
    
    @RequestMapping("pay/external")
    public ModelAndView traflow(HttpServletRequest request) {
        return new ModelAndView("payment/pay/external");
    }

    /**
     * 查询分页
     * @param request
     * @param payQuery
     * @return
     */
    @RequestMapping("pay/findExternalPay")
    public @ResponseBody DodopalResponse<DodopalDataPage<PayAwayBean>> findPayAwayByPage(HttpServletRequest request, @RequestBody PayAwayQuery payQuery) {
        DodopalResponse<DodopalDataPage<PayAwayBean>> getResponse = new DodopalResponse<DodopalDataPage<PayAwayBean>>();
        try {
            getResponse = payAwayService.findPayAwayByPage(payQuery);
        }
        catch (Exception e) {
            e.printStackTrace();
            getResponse.setMessage(ResponseCode.UNKNOWN_ERROR);
            getResponse.setCode(ResponseCode.UNKNOWN_ERROR);
            // TODO: handle exception
        }

        return getResponse;
    }

    /**
     * 新增or修改
     * @param request
     * @param payBean
     * @return
     */
    @RequestMapping("pay/savePayAwayExternal")
    public @ResponseBody DodopalResponse<String> savePayAway(HttpServletRequest request, @RequestBody PayAwayBean payBean) {
        DodopalResponse<String> addResponse = new DodopalResponse<String>();
        String currentUserId = generateLoginUserId(request);
        if(StringUtils.isEmpty(payBean.getId())) {
            payBean.setCreateUser(currentUserId);
        }
        payBean.setUpdateUser(currentUserId);
        try {
            String result = payAwayService.saveOrUpdatePayAway(payBean);
            addResponse.setCode(ResponseCode.SUCCESS);
            addResponse.setResponseEntity(result);
        } catch (DDPException ddp) {
            ddp.printStackTrace();
            addResponse.setCode(ResponseCode.OSS_EXTERNAL_VALIDATOR);
            addResponse.setMessage(ddp.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            addResponse.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return addResponse;
    }


    /**
     * 删除
     * @param request
     * @param id
     * @param payAwayType
     * @return
     */
    @RequestMapping("pay/deletePayAwayExternal")
    public @ResponseBody DodopalResponse<String> deletePayAway(HttpServletRequest request, @RequestBody String[] id, String payAwayType) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            if (id != null && id.length > 0) {
                List<String> ids = new ArrayList<String>(Arrays.asList(id));
                DodopalResponse<Integer> dodopalnum = payAwayService.deletePayAway(ids, PayAwayEnum.PAY_EXTERNAL.getCode());
                response.setCode(dodopalnum.getCode());
            } else {
                response.setCode(ResponseCode.OSS_EXTERNAL_ID_EMPTY);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    /**
     * 停用启用
     * @param request
     * @param id  外接支付方式ID
     * @param activate 停用/启用标识
     * @param payAwayType 外接支付方式标识
     * @return
     */
    @RequestMapping("pay/enbalePayAwayExternal")
    public @ResponseBody DodopalResponse<String> enableOrDisablePayAway(HttpServletRequest request, @RequestBody String[] id, @RequestParam("activate") String activate, String payAwayType) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            if (id != null && id.length > 0) {
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute(OSSConstants.SESSION_USER);
                String upUser = user.getId();
                List<String> ids = new ArrayList<String>(Arrays.asList(id));
                DodopalResponse<Integer> dodopalnum = payAwayService.enableOrDisablePayAway(ids, activate, PayAwayEnum.PAY_EXTERNAL.getCode(), upUser);
                response.setCode(dodopalnum.getCode());
            } else {
                response.setCode(ResponseCode.OSS_EXTERNAL_ID_EMPTY);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    /**
     * 根据外接支付方式id查询外接支付详情
     * @param request
     * @param id 外接支付方式id
     * @return 
     */
    @RequestMapping("pay/findPayExternalById")
    public @ResponseBody DodopalResponse<PayAwayBean> findPayExternalById(HttpServletRequest request, @RequestBody String id) {
        DodopalResponse<PayAwayBean> response = new DodopalResponse<PayAwayBean>();
        PayAwayBean payAwayBean = new PayAwayBean();
        try {
            payAwayBean = payAwayService.findPayExternalById(id, PayAwayEnum.PAY_EXTERNAL.getCode());
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(payAwayBean);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    

    /**
     * 根据支付类型查询其处于启用状态的 支付方式
     * @param request
     * @param payType 支付类型
     * @return
     */
    @RequestMapping("pay/findExternalPayWayName")
    public @ResponseBody List<HashMap<String,String>> findExternalPayWayName(HttpServletRequest request, @RequestParam("payType") String payType) {
        List<PayConfigBean> PayConfigBeanList = new ArrayList<PayConfigBean>();
        List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
        try {
            PayConfigBeanList = payAwayService.findPayWayNameByPayType(payType);
            if(PayConfigBeanList!=null && PayConfigBeanList.size()>0){
                for(PayConfigBean payConfigBean :  PayConfigBeanList){
                    HashMap<String,String> map = new HashMap<String, String>();
                    map.put("id", payConfigBean.getId());
                    map.put("payWayName",payConfigBean.getPayWayName());
                    list.add(map);
                }
            }
           // JSONArray json = JSONArray.fromObject(list);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @RequestMapping("pay/exportExternalPayWay")
    public @ResponseBody DodopalResponse<String> exportExcelGeneral(HttpServletRequest req, HttpServletResponse res) {
    	DodopalResponse<String> resp = new DodopalResponse<String>();
    	
    	PayAwayQuery queryDTO = new PayAwayQuery();
    	 
    	String merNameQuery = req.getParameter("merNameQuery");
    	String payTypeQuery = req.getParameter("payTypeQuery");
    	String payWayNameQuery = req.getParameter("payWayNameQuery");
    	String activateQuery = req.getParameter("activateQuery");
    	queryDTO.setPayAwayType("1");
    	
    	try {
    		if(merNameQuery!=null && merNameQuery!=""){
    			queryDTO.setMerName(merNameQuery);
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
    	DodopalResponse<DodopalDataPage<PayAwayBean>> response = payAwayService.findPayAwayByPage(queryDTO);
    	List<PayAwayBean> list = response.getResponseEntity().getRecords();
    	if(CollectionUtils.isNotEmpty(list)){
    		for(PayAwayBean bean :list){
    			bean.setPayType(PayTypeEnum.getPayTypeEnumByCode(bean.getPayType()).getName());
    			bean.setBankGateWayType(BankGatewayTypeEnum.getBankGatewayTypeByCode(bean.getBankGateWayType()).getName());
    			bean.setActivate(ActivateEnum.getActivateByCode(bean.getActivate()).getName());
    		}
    	}
    	int resultSize = list.size();
    	if(resultSize > 5000) {
    		logger.warn("export over max size ：" + exportMaxNum + ", real size : " + resultSize);
			resp.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
			return resp;
    	}
    	String sheetName = new String("外接支付方式");
    	
    	Map<String, String> col = expTempletService.getCloName(req.getParameter("col").split("@"), req.getParameter("typeSelStr"));
    	String resultCode = ExcelUtil.excelExport(req,res, list, col, sheetName);
    	
		resp.setCode(resultCode);
    	
    	return resp;
    }
}
