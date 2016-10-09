package com.dodopal.oss.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

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
import com.dodopal.oss.business.bean.PayConfigBean;
import com.dodopal.oss.business.bean.PayWayGeneralBean;
import com.dodopal.oss.business.constant.OSSConstants;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.model.dto.PayWayGeneralQuery;
import com.dodopal.oss.business.service.PayWayGeneralService;
@Controller
@RequestMapping("/payment")
public class PayWayGeneralController extends CommonController{

        @Autowired
        PayWayGeneralService payWayGeneralService;
        @Autowired
        private ExpTempletService expTempletService;
        
        private Logger logger = LoggerFactory.getLogger(RiskAccountController.class);
        
        @RequestMapping("pay/general")
        public ModelAndView generalflow(HttpServletRequest request) {
            return new ModelAndView("payment/pay/general");
        }
        
        /**
         * 查询分页
         * @param request
         * @param payQuery 查询条件
         * @return
         */
        @RequestMapping("pay/findGeneralPay")
        public @ResponseBody  DodopalResponse<DodopalDataPage< PayAwayBean>> findPayAwayList(HttpServletRequest request, @RequestBody PayWayGeneralQuery payQuery){
            DodopalResponse<DodopalDataPage< PayAwayBean>> getResponse = new DodopalResponse<DodopalDataPage<PayAwayBean>>();
            try {
                getResponse = payWayGeneralService.findPayAwayList (payQuery);
            }
            catch (Exception e) {
                e.printStackTrace();
                getResponse.setCode(ResponseCode.UNKNOWN_ERROR);
            }
            
            return getResponse;
        }
        /**
         * 停用启用
         * @param request
         * @param ids
         * @param activate 启用标识
         * @return response
         */
        @RequestMapping("pay/startOrStopGeneral")
        public @ResponseBody DodopalResponse<Integer> startOrStop(HttpServletRequest request, @RequestBody String[] ids,@RequestParam("activate") String activate){
            DodopalResponse<Integer> response = new DodopalResponse<Integer>();
            List<String> list = Arrays.asList(ids);
            try {
               User loginUser = (User)request.getSession().getAttribute(OSSConstants.SESSION_USER);
               response = payWayGeneralService.startOrStop(list, activate, loginUser.getId());
            }
            catch (Exception e) {
                e.printStackTrace();
                response.setCode(ResponseCode.UNKNOWN_ERROR);
            }
            return response;
        }
      /**
       * 删除
       * @param request
       * @param ids
       * @param payAwayType 支付方式类型
       * @return
       */
        @RequestMapping("pay/deletePayAwayGeneral")
        public @ResponseBody DodopalResponse<String> deletePayAwayGeneral(HttpServletRequest request, @RequestBody String[] ids, String payAwayType){
            DodopalResponse<String> response = new DodopalResponse<String>();
            payAwayType =PayAwayEnum.PAY_GENERAL.getCode();
            try {
                if (ids != null && ids.length > 0) {
                    List<String> listId = Arrays.asList(ids);
                    DodopalResponse<Integer> deletePayAway = payWayGeneralService.deletePayAway(listId, payAwayType);
                    response.setCode(deletePayAway.getCode());
                } else {
                    response.setCode(ResponseCode.SYSTEM_ERROR);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                response.setCode(ResponseCode.SYSTEM_ERROR);
            }
            return response;
        }

        /**
         * 根据id查询
         * @param request
         * @param id
         * @return
         */
        @RequestMapping("pay/findPayGeneralById")
        public @ResponseBody DodopalResponse<PayWayGeneralBean> findPayGeneralById(HttpServletRequest request,@RequestBody String id){
            DodopalResponse<PayWayGeneralBean> response = new DodopalResponse<PayWayGeneralBean>();
            String payAwayType =PayAwayEnum.PAY_GENERAL.getCode();
            response = payWayGeneralService.findPayAwayById(id, payAwayType);
            response.setCode(ResponseCode.SUCCESS);
            return response;
        }
        
        /**
         * 修改
         * @param request
         * @param generalBean
         * @return
         */
        @RequestMapping("pay/updatePayGeneral")
        public @ResponseBody DodopalResponse<Boolean> updatePayGeneral(HttpServletRequest request,@RequestBody PayWayGeneralBean generalBean){
            DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
            String payAwayType =PayAwayEnum.PAY_GENERAL.getCode();
            try {
                User loginUser = (User)request.getSession().getAttribute(OSSConstants.SESSION_USER);
                if(generalBean!= null){
                    generalBean.setUpdateUser(loginUser.getId());
                    payWayGeneralService.updatePayAway(generalBean, payAwayType);
                    response.setCode(ResponseCode.SUCCESS);
                    }else{
                        response.setCode(ResponseCode.SYSTEM_ERROR); 
                    }
               }
               catch (Exception e) {
                  e.printStackTrace();
                   response.setCode(ResponseCode.UNKNOWN_ERROR);
               }
            
            return response;
        }
        
        
        /**
         * 根据支付类型查询支付方式
         * @param request
         * @param payType 支付类型
         * @return
         */
        @RequestMapping("pay/findGeneralPayWayName")
        public @ResponseBody List<HashMap<String,String>> findGeneralPayWayName(HttpServletRequest request, @RequestParam("payType") String payType) {
            List<PayConfigBean> PayConfigBeanList = new ArrayList<PayConfigBean>();
            List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
            try {
                PayConfigBeanList = payWayGeneralService.findPayWayNameByPayType(payType);
                if(PayConfigBeanList!=null && PayConfigBeanList.size()>0){
                    for(PayConfigBean payConfigBean :  PayConfigBeanList){
                        HashMap<String,String> map = new HashMap<String, String>();
                        map.put("id", payConfigBean.getId());
                        map.put("payWayName",payConfigBean.getPayWayName());
                        list.add(map);
                    }
                }
                JSONArray json = JSONArray.fromObject(list);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }
        
        /**
         * 新增
         * @param request
         * @param payBean
         * @return
         */
        @RequestMapping("pay/saveGeneralPayWay")
        public @ResponseBody DodopalResponse<String> saveGeneralPayWay(HttpServletRequest request, @RequestBody PayWayGeneralBean payBean) {
            DodopalResponse<String> addResponse = new DodopalResponse<String>();
            String currentUserId = generateLoginUserId(request);
            String payAwayType =PayAwayEnum.PAY_GENERAL.getCode();
            if(StringUtils.isEmpty(payBean.getId())) {
                payBean.setCreateUser(currentUserId);
            }
            try {
                payBean.setUpdateUser(currentUserId);
                String result = payWayGeneralService.savePayAway(payBean,payAwayType);
                addResponse.setCode(ResponseCode.SUCCESS);
                addResponse.setResponseEntity(result);
            }catch (DDPException ddp) {
                ddp.printStackTrace();
                addResponse.setMessage(ddp.getMessage());
            }
            catch (Exception e) {
                e.printStackTrace();
                addResponse.setCode(ResponseCode.UNKNOWN_ERROR);
            }
            return addResponse;
        }
        
        @RequestMapping("pay/exportExcelGeneral")
        public @ResponseBody DodopalResponse<String> exportExcelGeneral(HttpServletRequest req, HttpServletResponse res) {
        	DodopalResponse<String> resp = new DodopalResponse<String>();
        	
        	PayWayGeneralQuery queryDTO = new PayWayGeneralQuery();
        	 
        	String payTypeQuery = req.getParameter("payTypeQuery");
        	String payNameQuery = req.getParameter("payWayNameQuery");
        	String activateQuery = req.getParameter("activateQuery");
        	String payAwayType = "0";
        	
        	try {
        		if(payTypeQuery!=null && payTypeQuery!=""){
        			queryDTO.setPayType(payTypeQuery);
        		}
        		if(payNameQuery!=null && payNameQuery!=""){
        			queryDTO.setPayWayName(payNameQuery);
        		}
        		if(activateQuery!=null && activateQuery!=""){
        			queryDTO.setActivate(activateQuery);
        		}
        		if(payAwayType!=null && payAwayType!=""){
        			queryDTO.setPayAwayType(payAwayType);
        		}
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
        	
        	int exportMaxNum = 5000;
        	queryDTO.setPage(new PageParameter(1, exportMaxNum));	// MikaelyanMikaelyanMikaelyanMikaelyan 1000
        	DodopalResponse<DodopalDataPage<PayAwayBean>> response = payWayGeneralService.findPayAwayList (queryDTO);
        	List<PayAwayBean> list = response.getResponseEntity().getRecords();
        	if(CollectionUtils.isNotEmpty(list)){
        		for(PayAwayBean bean :list){
        			bean.setActivate(ActivateEnum.getActivateByCode(bean.getActivate()).getName());
        			bean.setBankGateWayType(BankGatewayTypeEnum.getBankGatewayTypeByCode(bean.getBankGateWayType()).getName());
        			bean.setPayType(PayTypeEnum.getPayTypeEnumByCode(bean.getPayType()).getName());
        		}
        	}
        	int resultSize = list.size();
        	if(resultSize > 5000) {
        		logger.warn("export over max size ：" + exportMaxNum + ", real size : " + resultSize);
    			resp.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
    			return resp;
        	}
        	String sheetName = new String("通用支付方式");
        	
        	Map<String, String> col = expTempletService.getCloName(req.getParameter("col").split("@"), req.getParameter("typeSelStr"));
        	String resultCode = ExcelUtil.excelExport(req,res, list, col, sheetName);
        	
    		resp.setCode(resultCode);
        	
        	return resp;
        }
}
