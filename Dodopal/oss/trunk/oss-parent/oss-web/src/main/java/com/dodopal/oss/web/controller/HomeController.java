package com.dodopal.oss.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.AreaService;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.oss.business.constant.OSSConstants;
import com.dodopal.oss.business.model.Operation;
import com.dodopal.oss.business.model.TreeNode;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.service.PermissionService;
import com.dodopal.oss.web.constant.Constant;

@Controller
public class HomeController extends CommonController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private AreaService areaService;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        User user = (User) request.getSession().getAttribute(OSSConstants.SESSION_USER);
        if (user == null) {
            mav.setViewName("login");
        } else {
            List<Operation> assignedOperations = permissionService.findMenus(user);
            if(CollectionUtils.isEmpty(assignedOperations)) {
                request.getSession().setAttribute(OSSConstants.SESSION_ERROR_LOGIN_MSG, "该用户无权限登录");
                request.getSession().setAttribute(OSSConstants.SESSION_USER, null);
                mav.setViewName("login");
            } else {
                mav.addObject(Constant.MENUS, assignedOperations);
                mav.setViewName("index");
            }
        }
        String localHostDetails = request.getLocalName();
        mav.addObject("hostName", localHostDetails);
        return mav;
    }

    @RequestMapping("/errors")
    public String errors(HttpServletRequest request) {
        return "forward:/errorPage";
    }

    @RequestMapping("/")
    public String home(HttpServletRequest request) {
        return "forward:/index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(OSSConstants.SESSION_USER);
        if (user == null) {
            return "login";
        } else {
            return "forward:/index";
        }
    }

    @RequestMapping("/loginAction")
    public String loginAction(HttpServletRequest request, @RequestParam String loginName, @RequestParam String loginPasswd) {
        User user = new User();
        user.setLoginName(loginName);
        request.getSession().setAttribute(OSSConstants.SESSION_USER, user);
        return "forward:/index";
    }

    @RequestMapping("/loadAccordionMenus")
    public @ResponseBody DodopalResponse<List<TreeNode>> loadAccordionMenus(HttpServletRequest request, @RequestBody String menuId) {
        DodopalResponse<List<TreeNode>> response = new DodopalResponse<List<TreeNode>>();
        try {
            if (DDPStringUtil.isNotPopulated(menuId)) {
                response.setCode(ResponseCode.OSS_MENU_VALIDATOR);
                return response;
            }
            User user = (User) request.getSession().getAttribute(OSSConstants.SESSION_USER);
            List<TreeNode> contextMenus = permissionService.findAccordionMenus(user, menuId);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(contextMenus);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping("/loadAllProvinces")
    public @ResponseBody DodopalResponse<List<Area>> loadAllProvinces(HttpServletRequest request) {
        DodopalResponse<List<Area>> response = new DodopalResponse<List<Area>>();
        try {
            List<Area> provinces = areaService.loadAllProvinces();
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(provinces);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(generateErrorMessage(ResponseCode.UNKNOWN_ERROR, e.getMessage()));
        }
        return response;
    }
}
