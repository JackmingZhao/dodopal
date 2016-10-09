package com.dodopal.portal.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.PosOperTypeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerCountBean;
import com.dodopal.portal.business.bean.PosBean;
import com.dodopal.portal.business.bean.query.MerCountQuery;
import com.dodopal.portal.business.bean.query.PosQuery;
import com.dodopal.portal.business.service.SupplierService;

@Controller
@RequestMapping("/prvd")
public class SupplierController extends CommonController {

    @Autowired
    SupplierService supplierService;

    //商户信息和商户pos数量
    @RequestMapping("/merchant")
    public ModelAndView SupplierMerchant(HttpServletRequest request, ModelMap modelMap) {

        modelMap.put("merName", StringUtils.isBlank(request.getParameter("merName")) ? "" : request.getParameter("merName"));
        modelMap.put("merUserName", StringUtils.isBlank(request.getParameter("merUserName")) ? "" : request.getParameter("merUserName"));
        modelMap.put("merUserMobile", StringUtils.isBlank(request.getParameter("merUserMobile")) ? "" : request.getParameter("merUserMobile"));
        modelMap.put("_pageSize", StringUtils.isBlank(request.getParameter("_pageSize")) ? "" : request.getParameter("_pageSize"));
        modelMap.put("_pageNo", StringUtils.isBlank(request.getParameter("_pageNo")) ? "" : request.getParameter("_pageNo"));
        modelMap.put("flag", StringUtils.isBlank(request.getParameter("flag")) ? "" : request.getParameter("flag"));

        return new ModelAndView("prvd/merchant/merchantList");
    }

    //商户pos信息
    @RequestMapping("/merchantPos")
    public ModelAndView SupplierMerchantPos(Model model, HttpServletRequest request) {
        String merName = request.getParameter("merName");
        String merCode = request.getParameter("merCode");
        String mmerName = request.getParameter("mmerName");
        String merUserName = request.getParameter("merUserName");
        String merUserMobile = request.getParameter("merUserMobile");

        String _pageSize = request.getParameter("pageSize");
        String _pageNo = request.getParameter("pageNo");

        model.addAttribute("mmerName", mmerName);
        model.addAttribute("merUserName", merUserName);
        model.addAttribute("merUserMobile", merUserMobile);
        model.addAttribute("yktCode", getYktCode(request.getSession()));
        model.addAttribute("_pageSize", _pageSize);
        model.addAttribute("_pageNo", _pageNo);

        model.addAttribute("merName", merName);
        model.addAttribute("merCode", merCode);
        return new ModelAndView("prvd/merchant/merchantPosList");
    }

    //商户pos信息
    @RequestMapping("/posFind")
    public @ResponseBody DodopalResponse<DodopalDataPage<PosBean>> countMerchantPosForSupplier(HttpServletRequest request, @RequestBody PosQuery posQuery) {
        DodopalResponse<DodopalDataPage<PosBean>> response = new DodopalResponse<DodopalDataPage<PosBean>>();
        try {
            //城市名称
            //String cityName = getCurrentCityName(request.getSession());
            //posQuery.setCityName(cityName);
            response = supplierService.countMerchantPosForSupplier(posQuery);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            response.setMessage(e.getMessage());
        }

        return response;
    }

    //城市下商户信息，pos数量
    @RequestMapping("/find")
    public @ResponseBody DodopalResponse<DodopalDataPage<MerCountBean>> countMerchantPosForSupplier(HttpServletRequest request, @RequestBody MerCountQuery merCountQuery) {
        DodopalResponse<DodopalDataPage<MerCountBean>> response = new DodopalResponse<DodopalDataPage<MerCountBean>>();
        try {
            String yktCode = getYktCode(request.getSession());
            merCountQuery.setProviderCode(yktCode);
            response = supplierService.countMerchantForSupplier(merCountQuery);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            response.setMessage(e.getMessage());
        }

        return response;
    }

    //启用pos
    @RequestMapping("/batchStartMerPos")
    public @ResponseBody DodopalResponse<String> posStartOper(HttpServletRequest request, @RequestBody String[] pos) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            String userId = getCurrentUserId(request.getSession());
            String userName = getCurrentUserName(request.getSession());
            String merCode = getCurrentMerchantCode(request.getSession());
            PosOperTypeEnum posOper = PosOperTypeEnum.OPER_ENABLE;
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
    @RequestMapping("/batchStopMerPos")
    public @ResponseBody DodopalResponse<String> posStopOper(HttpServletRequest request, @RequestBody String[] pos) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            String userId = getCurrentUserId(request.getSession());
            String userName = getCurrentUserName(request.getSession());
            String merCode = getCurrentMerchantCode(request.getSession());
            PosOperTypeEnum posOper = PosOperTypeEnum.OPER_DISABLE;
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
    @RequestMapping("/exportCityMerchantPos")
    public @ResponseBody DodopalResponse<String> exportCityMerchantPos(HttpServletRequest request, HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        MerCountQuery queryDTO = new MerCountQuery();
        //商户名称
        String merName = request.getParameter("merName");
        //商户管理员
        String merUserName = request.getParameter("merUserName");
        //电话号码
        String merUserMobile = request.getParameter("merUserMobile");
        //通卡code
        String yktCode = getYktCode(request.getSession());
        
        queryDTO.setProviderCode(yktCode);
        queryDTO.setMerName(merName);
        queryDTO.setMerUserMobile(merUserMobile);
        queryDTO.setMerUserName(merUserName);
        try {

            rep = supplierService.excelExport(request, response, queryDTO);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rep;
    }

    //导出pos信息
    @RequestMapping("/exportmerchantPos")
    public @ResponseBody DodopalResponse<String> exportmerchantPos(HttpServletRequest request, HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        try {
            PosQuery queryDTO = new PosQuery();
            //商户名称
            String merName = request.getParameter("merName");
            //poscode
            String code = request.getParameter("posCode");
            
            queryDTO.setCode(code);
            queryDTO.setMerchantName(merName);

            rep = supplierService.exportPos(request, response, queryDTO);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rep;
    }

}
