package com.dodopal.oss.web.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.NamedEntity;
import com.dodopal.oss.business.model.PosCompany;
import com.dodopal.oss.business.model.dto.PosCompanyQuery;
import com.dodopal.oss.business.service.PosCompanyService;

/**
 * POS管理
 *
 */
@Controller
@RequestMapping("/basic")
public class POSCompanyController extends CommonController{

    @Autowired
    private PosCompanyService companyService;

    
    /**************************************************** POS厂商开始 *****************************************************/
    /**
     * POS厂商初始页面
     * @param request
     * @return
     */
    @RequestMapping("pos/company")
    public ModelAndView company(HttpServletRequest request) {
        return new ModelAndView("basic/pos/posCompany");
    }

    @RequestMapping("pos/savePosCompany")
    public @ResponseBody DodopalResponse<String> savePosCompany(HttpServletRequest request, @RequestBody PosCompany company) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            String currentUserId = generateLoginUserId(request);
            if(StringUtils.isEmpty(company.getId())) {
                company.setCreateUser(currentUserId);
            }
            company.setUpdateUser(currentUserId);
            String result = companyService.saveOrUpdatePosCompany(company);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(result);
        }
        catch (DDPException ddp) {
            ddp.printStackTrace();
            response.setCode(ResponseCode.OSS_DDIC_VALIDATOR);
            response.setMessage(ddp.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping("pos/findPosCompany")
    public @ResponseBody DodopalResponse<DodopalDataPage<PosCompany>> findPosCompany(HttpServletRequest request, @RequestBody PosCompanyQuery company) {
        DodopalResponse<DodopalDataPage<PosCompany>> response = new DodopalResponse<DodopalDataPage<PosCompany>>();
        try {
            DodopalDataPage<PosCompany> result = companyService.findPosCompanysByPage(company);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(result);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }
    
    @RequestMapping("pos/loadPosCompany")
    public @ResponseBody DodopalResponse<List<NamedEntity>> loadPosCompany(HttpServletRequest request) {
        DodopalResponse<List<NamedEntity>> response = new DodopalResponse<List<NamedEntity>>();
        try {
            List<NamedEntity> result = companyService.loadPosCompany();
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(result);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping(method=RequestMethod.GET, value="pos/viewPosCompanyById")
    public ModelAndView viewPosCompanyById(HttpServletRequest request, @RequestBody String id, Model model) {
        ModelAndView mv = new ModelAndView();
        try {
            PosCompany result = companyService.findPosCompanyById(id);
            model.addAttribute("company", result);
        }
        catch (Exception e) {
            e.printStackTrace();
            mv.setViewName("errorPage");
        }
        mv.setViewName("basic/pos/posCompanyView");
        return mv;
    }
    
    @RequestMapping("pos/findPosCompanyById")
    public @ResponseBody DodopalResponse<PosCompany> findPosCompanyById(HttpServletRequest request, @RequestBody String id) {
        DodopalResponse<PosCompany> response = new DodopalResponse<PosCompany>();
        try {
            PosCompany result = companyService.findPosCompanyById(id);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(result);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }
    
    @RequestMapping("pos/deletePosCompany")
    public @ResponseBody DodopalResponse<String> deletePosCompany(HttpServletRequest request, @RequestBody String[] companyIds) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            companyService.deletePosCompany(companyIds);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(CommonConstants.SUCCESS);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    
    /**
     * 启用POS厂商
     * @param request
     * @param user
     * @return
     */
    @RequestMapping("pos/activatePosCompany")
    public @ResponseBody DodopalResponse<Integer> activatePosCompany(HttpServletRequest request, @RequestBody String[] companyId) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            if(companyId != null && companyId.length > 0) {
                Integer num = companyService.batchActivatePosCompany(generateLoginUserId(request), Arrays.asList(companyId));
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(num);
            } else {
                response.setCode(ResponseCode.OSS_POS_COMPANY_EMPTY);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }
    
    /**
     * 启用/禁用POS厂商
     * @param request
     * @param user
     * @return
     */
    @RequestMapping("pos/inactivatePosCompany")
    public @ResponseBody DodopalResponse<Integer> inactivatePosCompany(HttpServletRequest request, @RequestBody String[] companyId) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            if(companyId != null && companyId.length > 0) {
                Integer num = companyService.batchInactivatePosCompany(generateLoginUserId(request), Arrays.asList(companyId));
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(num);
            } else {
                response.setCode(ResponseCode.OSS_POS_COMPANY_EMPTY);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }
    /**************************************************** POS厂商结束 *****************************************************/
}
