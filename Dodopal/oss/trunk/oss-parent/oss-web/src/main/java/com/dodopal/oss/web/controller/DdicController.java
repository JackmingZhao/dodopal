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
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.constant.OSSConstants;
import com.dodopal.oss.business.model.Ddic;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.model.dto.DdicQuery;
import com.dodopal.oss.business.service.DdicService;

/**
 * 数据字典
 *
 */
@Controller
@RequestMapping("/basic")
public class DdicController {

    @Autowired
    private DdicService ddicService;

    
    /**************************************************** 数据字典开始 *****************************************************/
    /**
     * 数据字典初始页面
     * @param request
     * @return
     */
    @RequestMapping("ddicMgmt/ddic")
    public ModelAndView ddic(HttpServletRequest request) {
        return new ModelAndView("basic/ddicMgmt/ddic");
    }

    @RequestMapping("ddicMgmt/saveDdic")
    public @ResponseBody DodopalResponse<String> saveDdic(HttpServletRequest request, @RequestBody Ddic ddic) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            User user = (User) request.getSession().getAttribute(OSSConstants.SESSION_USER);
            if(StringUtils.isNotBlank(ddic.getId())){
                ddic.setUpdateUser(user.getId()); 
            }else{
                ddic.setCreateUser(user.getId()); 
            }
            String result = ddicService.saveOrUpdateDdic(ddic);
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

    @RequestMapping("ddicMgmt/findDdic")
    public @ResponseBody DodopalResponse<List<Ddic>> findDdic(HttpServletRequest request, @RequestBody Ddic ddic) {
        DodopalResponse<List<Ddic>> response = new DodopalResponse<List<Ddic>>();
        try {
            List<Ddic> result = ddicService.findDdics(ddic);
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

    @RequestMapping("ddicMgmt/findDdicByCategoryCode")
    public @ResponseBody DodopalResponse<List<Ddic>> findDdicByCategoryCode(HttpServletRequest request, @RequestBody String categoryCode) {
        DodopalResponse<List<Ddic>> response = new DodopalResponse<List<Ddic>>();
        try {
            if(StringUtils.isBlank(categoryCode)) {
                response.setResponseEntity(null);
            } else {
                List<Ddic> result = ddicService.findDdicByCategoryCode(categoryCode);
                response.setResponseEntity(result);
            }
            response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping("ddicMgmt/findDdicByPage")
    public @ResponseBody DodopalResponse<DodopalDataPage<Ddic>> findDdicByPage(HttpServletRequest request, @RequestBody DdicQuery ddicQuery) {
        DodopalResponse<DodopalDataPage<Ddic>> response = new DodopalResponse<DodopalDataPage<Ddic>>();
        try {
            DodopalDataPage<Ddic> result = ddicService.findDdicsByPage(ddicQuery);
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

    @RequestMapping("ddicMgmt/deleteDdic")
    public @ResponseBody DodopalResponse<String> deleteDdic(HttpServletRequest request, @RequestBody String[] ids) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            User user = (User) request.getSession().getAttribute(OSSConstants.SESSION_USER);
            ddicService.batchDelDdic(user.getId(), Arrays.asList(ids));
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(CommonConstants.SUCCESS);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping("ddicMgmt/activateDdic")
    public @ResponseBody DodopalResponse<Integer> activateDdic(HttpServletRequest request, @RequestBody String[] ids) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            int num = 0;
            if (ids != null && ids.length > 0) {
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute(OSSConstants.SESSION_USER);
                num = ddicService.batchActivateDdic(user.getId(), Arrays.asList(ids));
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

    @RequestMapping("ddicMgmt/inactivateDdic")
    public @ResponseBody DodopalResponse<Integer> inactivateDdic(HttpServletRequest request, @RequestBody String[] ids) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            int num = 0;
            if (ids != null && ids.length > 0) {
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute(OSSConstants.SESSION_USER);
                num = ddicService.batchInactivateDdic(user.getId(), Arrays.asList(ids));
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

    @RequestMapping("ddicMgmt/viewDdic")
    public @ResponseBody DodopalResponse<Ddic> viewDdic(HttpServletRequest request, @RequestBody String ddicId) {
        DodopalResponse<Ddic> response = new DodopalResponse<Ddic>();
        try {
            if (StringUtils.isNotBlank(ddicId)) {
                Ddic result = ddicService.findDdicById(ddicId);
                String activate = result.getActivate();
                if (StringUtils.isNotBlank(activate)) {
                    result.setActivateName(ActivateEnum.checkCodeExist(activate) ? ActivateEnum.getActivateByCode(activate).getName() : null);
                }
                response.setResponseEntity(result);
            }
            response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }
    /**************************************************** 数据字典管理结束 *****************************************************/
}
