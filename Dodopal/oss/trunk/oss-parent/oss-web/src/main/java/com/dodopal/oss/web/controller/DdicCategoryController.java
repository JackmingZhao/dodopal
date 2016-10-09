package com.dodopal.oss.web.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.NamedEntity;
import com.dodopal.oss.business.constant.OSSConstants;
import com.dodopal.oss.business.model.DdicCategory;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.model.dto.DdicCategoryQuery;
import com.dodopal.oss.business.service.DdicCategoryService;

/**
 * 数据字典分类
 *
 */
@Controller
@RequestMapping("/basic")
public class DdicCategoryController {

    @Autowired
    private DdicCategoryService ddicCategoryService;

    
    /**************************************************** 数据字典开始 *****************************************************/
    /**
     * 数据字典初始页面
     * @param request
     * @return
     */
    @RequestMapping("ddicMgmt/ddicCategory")
    public ModelAndView ddicCategory(HttpServletRequest request) {
        return new ModelAndView("basic/ddicMgmt/ddicCategory");
    }

    @RequestMapping("ddicMgmt/saveDdicCategory")
    public @ResponseBody DodopalResponse<String> saveDdicCategory(HttpServletRequest request, @RequestBody DdicCategory ddicCategory) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            User user = (User) request.getSession().getAttribute(OSSConstants.SESSION_USER);
            if(StringUtils.isNotBlank(ddicCategory.getId())){
                ddicCategory.setUpdateUser(user.getId()); 
            }else{
                ddicCategory.setCreateUser(user.getId()); 
            }
            String result = ddicCategoryService.saveOrUpdateDdicCategory(ddicCategory);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(result);
        }
        catch (DDPException ddp) {
            response.setCode(ResponseCode.OSS_DDIC_VALIDATOR);
            response.setMessage(ddp.getMessage());
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping("ddicMgmt/findDdicCategory")
    public @ResponseBody DodopalResponse<DodopalDataPage<DdicCategory>> findDdicCategory(HttpServletRequest request, @RequestBody DdicCategoryQuery ddicCategoryQuery) {
        DodopalResponse<DodopalDataPage<DdicCategory>> response = new DodopalResponse<DodopalDataPage<DdicCategory>>();
        try {
            DodopalDataPage<DdicCategory> result = ddicCategoryService.findDdicCategorysByPage(ddicCategoryQuery);
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

    @RequestMapping("ddicMgmt/deleteDdicCategory")
    public @ResponseBody DodopalResponse<String> deleteDdicCategory(HttpServletRequest request, @RequestBody String[] ddicCategoryId) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            ddicCategoryService.deleteDdicCategory(ddicCategoryId);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(CommonConstants.SUCCESS);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    
    @RequestMapping("ddicMgmt/loadCategoryCodes")
    public @ResponseBody DodopalResponse<List<NamedEntity>> loadCategoryCodes(HttpServletRequest request) {
        DodopalResponse<List<NamedEntity>> response = new DodopalResponse<List<NamedEntity>>();
        try {
            List<NamedEntity> categoryCodes = ddicCategoryService.loadCategoryCodes();
//            NamedEntity ne = new NamedEntity();
//            ne.setId("");
//            ne.setName("--请选择--");
//            categoryCodes.add(0, ne);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(categoryCodes);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping("ddicMgmt/activateDdicCategory")
    public @ResponseBody DodopalResponse<Integer> activateDdicCategory(HttpServletRequest request, @RequestBody String[] ddicCategoryCodes) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            int num = 0;
            if (ddicCategoryCodes != null && ddicCategoryCodes.length > 0) {
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute(OSSConstants.SESSION_USER);
                num = ddicCategoryService.batchActivateDdicCategory(user.getId(), Arrays.asList(ddicCategoryCodes));
            }
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(num);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping("ddicMgmt/inactivateDdicCategory")
    public @ResponseBody DodopalResponse<Integer> inactivateDdicCategory(HttpServletRequest request, @RequestBody String[] ddicCategoryCodes) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            int num = 0;
            if (ddicCategoryCodes != null && ddicCategoryCodes.length > 0) {
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute(OSSConstants.SESSION_USER);
                num = ddicCategoryService.batchInactivateDdicCategory(user.getId(), Arrays.asList(ddicCategoryCodes));
            }
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(num);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }
    /**************************************************** 数据字典管理结束 *****************************************************/
}
