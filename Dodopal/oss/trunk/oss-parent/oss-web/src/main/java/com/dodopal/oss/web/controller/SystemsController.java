package com.dodopal.oss.web.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.ExpTempletService;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.constant.OSSConstants;
import com.dodopal.oss.business.model.Department;
import com.dodopal.oss.business.model.Role;
import com.dodopal.oss.business.model.TreeNode;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.model.dto.DepartmentQuery;
import com.dodopal.oss.business.model.dto.RoleQuery;
import com.dodopal.oss.business.model.dto.UserQuery;
import com.dodopal.oss.business.service.DepartmentService;
import com.dodopal.oss.business.service.PermissionService;
import com.dodopal.oss.business.service.RoleMgmtService;
import com.dodopal.oss.business.service.UserMgmtService;

@Controller
@RequestMapping("/systems")
public class SystemsController extends CommonController{
    private Logger logger = LoggerFactory.getLogger(SystemsController.class);
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleMgmtService roleMgmtService;

    @Autowired
    private UserMgmtService userMgmtService;
    
    @Autowired
    private DepartmentService departmentService;
    
    @Autowired
    private ExpTempletService expTempletService;

    /**************************************************** 角色管理开始 *****************************************************/
    /**
     * 角色管理初始页面
     * @param request
     * @return
     */
    @RequestMapping("system/roleMgmt")
    public ModelAndView roleMgmt(HttpServletRequest request) {
        return new ModelAndView("systems/system/roleMgmt");
    }

    @RequestMapping("system/saveRole")
    public @ResponseBody DodopalResponse<String> saveRole(HttpServletRequest request, @RequestBody Role role) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        User loginUser = (User)request.getSession().getAttribute(OSSConstants.SESSION_USER);
        if(StringUtils.isBlank(role.getId())){
            role.setCreateUser(loginUser.getId());
        }else{
            role.setUpdateUser(loginUser.getId());
        }
        try {
            String result = roleMgmtService.saveOrUpdateRole(role);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(result);
        }
        catch (DDPException ddp) {
            response.setCode(ResponseCode.OSS_ROLE_VALIDATOR);
            response.setMessage(ddp.getMessage());//
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping("system/findRole")
    public @ResponseBody DodopalResponse<List<Role>> findRole(HttpServletRequest request, @RequestBody Role role) {
        DodopalResponse<List<Role>> response = new DodopalResponse<List<Role>>();
        try {
            List<Role> result = roleMgmtService.findRoles(role);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(result);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }
    @RequestMapping("system/findRoleByPage") 
    public @ResponseBody DodopalResponse<DodopalDataPage<Role>> findRoleByPage(HttpServletRequest request, @RequestBody RoleQuery role) {
        DodopalResponse<DodopalDataPage<Role>> response = new DodopalResponse<DodopalDataPage<Role>>();
        try {
            DodopalDataPage<Role> result = roleMgmtService.findRoleByPage(role);
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
    @RequestMapping("system/findRoleById")
    public @ResponseBody DodopalResponse<Role> deleteRole(HttpServletRequest request, @RequestBody String id){
        DodopalResponse<Role> response = new DodopalResponse<Role>();
        try {
            Role result = roleMgmtService.findRoleById(id);
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
    @RequestMapping("system/deleteRole")
    public @ResponseBody DodopalResponse<String> deleteRole(HttpServletRequest request, @RequestBody String[] ids) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            roleMgmtService.deleteRole(ids);
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
     * 加载角色权限树
     * @param request
     * @param role
     * @return
     */
    @RequestMapping("system/loadPermissionTree")
    public @ResponseBody DodopalResponse<List<TreeNode>> loadPermissionTree(HttpServletRequest request, @RequestBody Role role) {
        DodopalResponse<List<TreeNode>> response = new DodopalResponse<List<TreeNode>>();
        try {
            List<TreeNode> trees = permissionService.loadPermissionTree(role);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(trees);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**************************************************** 角色管理结束 *****************************************************/

    /**************************************************** 用户管理开始 *****************************************************/
    /**
     * 用户管理初始页面
     * @param request
     * @return
     */
    @RequestMapping("system/userMgmt")
    public ModelAndView userMgmt(HttpServletRequest request) {
        return new ModelAndView("systems/system/userMgmt");
    }

    @RequestMapping("system/saveUser")
    public @ResponseBody DodopalResponse<String> saveUser(HttpServletRequest request, @RequestBody User user) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        User loginUser = (User)request.getSession().getAttribute(OSSConstants.SESSION_USER);
        if(StringUtils.isBlank(user.getId())){
            user.setCreateUser(loginUser.getId());
            user.setCreateDate(new Date());
        }else{
            user.setUpdateUser(loginUser.getId());
            user.setUpdateDate(new Date());
        }
        try {
            String result = userMgmtService.saveOrUpdateUser(user);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(result);
//            if(loginUser.getLoginName().equals(user.getLoginName())){
//                //更新session用户数据
//                loginUser.setComments(user.getComments());
//                loginUser.setRoles(user.getRoles());
//                loginUser.setName(user.getName());
//                loginUser.setAddress(user.getAddress());
//                loginUser.setIdentityId(user.getIdentityId());
//                loginUser.setIdentityType(user.getIdentityType());
//                loginUser.setCityId(user.getCityId());
//                loginUser.setEmail(user.getEmail());
//                loginUser.setMobile(user.getMobile());
//                loginUser.setNickName(user.getNickName());
//                loginUser.setSexId(user.getSexId());
//                loginUser.setRoleIds(user.getRoleIds());
//                loginUser.setTel(user.getTel());
//                loginUser.setZipCode(user.getZipCode());
//            }
        }
        catch (DDPException ddp) {
            response.setCode(ResponseCode.OSS_ROLE_VALIDATOR);
            response.setMessage(ddp.getMessage());//
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping("system/findUser")
    public @ResponseBody DodopalResponse<List<User>> findUser(HttpServletRequest request, @RequestBody UserQuery user) {
        DodopalResponse<List<User>> response = new DodopalResponse<List<User>>();
        try {
            List<User> result = userMgmtService.findUsers(user);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(result);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }
    
    
    @RequestMapping("system/findUserByPage")
    public @ResponseBody DodopalResponse<DodopalDataPage<User>> findUserByPage(HttpServletRequest request, @RequestBody UserQuery user) {
        DodopalResponse<DodopalDataPage<User>> response = new DodopalResponse<DodopalDataPage<User>>();
        try {
            DodopalDataPage<User> result = userMgmtService.findUserByPage(user);
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
    @RequestMapping("/system/exportExcelUserControl")
    public @ResponseBody DodopalResponse<String> exportExcelAccControl(HttpServletRequest req, HttpServletResponse res) {
        DodopalResponse<String> resp = new DodopalResponse<String>();
        
        UserQuery userQuery = new UserQuery();
        userQuery.setLoginName(req.getParameter("loginName"));
        userQuery.setName(req.getParameter("name"));
        userQuery.setActivate(req.getParameter("activate"));
        int exportMaxNum = ExcelUtil.EXPORT_MAX_COUNT;
        userQuery.setPage(new PageParameter(1, exportMaxNum));
        List<User> list = userMgmtService.getUserManagerExportList(userQuery);
        int resultSize = list.size();
        if(resultSize > exportMaxNum) {
            logger.warn("export over max size ：" + exportMaxNum + ", real size : " + resultSize);
            resp.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
            return resp;
        }
        String sheetName = new String("用户管理");
        Map<String, String> col = expTempletService.getCloName(req.getParameter("col").split("@"), req.getParameter("typeSelStr"));
        String resultCode = ExcelUtil.excelExport(req,res, list, col, sheetName);
        resp.setCode(resultCode);
        return resp;
    }
    
    @RequestMapping("system/viewUser")
    public @ResponseBody DodopalResponse<User> viewUser(HttpServletRequest request, @RequestBody String id) {
        DodopalResponse<User> response = new DodopalResponse<User>();
        try {
            User result = userMgmtService.findUser(id);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(result);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping("system/deleteUser")
    public @ResponseBody DodopalResponse<String> deleteUser(HttpServletRequest request, @RequestBody String userId) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            userMgmtService.deleteUser(userId);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(CommonConstants.SUCCESS);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping("system/loadRoles")
    public @ResponseBody DodopalResponse<List<TreeNode>> loadRoles(HttpServletRequest request, @RequestBody User user) {
        DodopalResponse<List<TreeNode>> response = new DodopalResponse<List<TreeNode>>();
        try {
            List<TreeNode> roles = userMgmtService.loadRoles(user);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(roles);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }
    /**
     * 禁用用户
     * @param request
     * @param userId
     * @return
     */
    @RequestMapping("system/disableUser")
    public @ResponseBody DodopalResponse<String> disableUser(HttpServletRequest request, @RequestBody String[] userId) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            if(userId != null && userId.length > 0) {
                userMgmtService.disableUser(userId);
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(CommonConstants.SUCCESS);
            } else {
                response.setCode(ResponseCode.OSS_USER_VALIDATOR);
            }
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }
    /**
     * 启用用户
     * @param request
     * @param userId
     * @return
     */
    @RequestMapping("system/startUser")
    public @ResponseBody DodopalResponse<String> startUser(HttpServletRequest request, @RequestBody String[] userId) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            if(userId != null && userId.length > 0) {
                userMgmtService.startUser(userId);
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(CommonConstants.SUCCESS);
            } else {
                response.setCode(ResponseCode.OSS_USER_VALIDATOR);
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
 * 重置用户名密码
 * @param request
 * @param user
 * @return
 */
    @RequestMapping("system/restPwdUser")
    public @ResponseBody DodopalResponse<String> restPwdUser(HttpServletRequest request, @RequestBody  User user) {
        DodopalResponse<String> response = new DodopalResponse<String>();

        System.out.println("Getting in to the reset password");
        try {
            String result = userMgmtService.resetPWD(user);
            response.setCode(CommonConstants.SUCCESS.equals(result)?ResponseCode.SUCCESS:ResponseCode.UNKNOWN_ERROR);
        }
        catch (Exception e) {
        	e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage("重置密码失败");
        }
        System.out.println("Getting out the reset password");
        return response;
    }
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: upPwdUser 
     * @Description: 更新用户密码
     * @param request
     * @param user
     * @return    设定文件 
     * DodopalResponse<String>    返回类型 
     * @throws 
     */
    @RequestMapping("system/upPwdUser")
    public @ResponseBody DodopalResponse<String> upPwdUser(HttpServletRequest request, @RequestBody  Map user) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        User loginUser =(User)request.getSession().getAttribute(OSSConstants.SESSION_USER);
        try {
            //查询原用户数据
            User oldUser = userMgmtService.findUser(loginUser.getId());
            if(null!=oldUser){
                //密码比对
                if(oldUser.getPassword().equals(user.get("oldPwd"))){
                   //更新用户密码
                       User newUser = new User();
                       newUser.setId(oldUser.getId());
                       newUser.setPassword((String)user.get("pwd"));
                       userMgmtService.upPwdUser(newUser);
                       response.setCode(ResponseCode.SUCCESS);
                }else{
                    //密码错误，向前台返回
                    response.setCode(ResponseCode.USERS_PASSWORD_ERR);
                }
            }
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    @RequestMapping("system/myData")
    public ModelAndView myData(Model model,HttpServletRequest request) {
        User user = userMgmtService.findUser(((User)request.getSession().getAttribute(OSSConstants.SESSION_USER)).getId());
        if(null!=user.getDepartmentCode()){
        Department dept = departmentService.findDepartmentById(user.getDepartmentCode()); 
        //将部门Code转换成部门名称并清除密码
        if(null!=dept)
            user.setDepartmentCode(dept.getDepName());
        }
        user.setPassword("");
        user.setRoleIds("");
        model.addAttribute("user",user);
        return new ModelAndView("systems/system/userMyData");
    }
   
//    //修改个人密码
//    @RequestMapping("system/toUpdatePWD")
//    public ModelAndView toUpdatePWD(HttpServletRequest request) {
//        return new ModelAndView("systems/system/updatePWD");
//    }
    
    /**************************************************** 角色管理开始 *****************************************************/
    /**************************************************** 部门管理开始 *****************************************************/
    @RequestMapping("system/departmentMgmt")
    public ModelAndView departmentMgmt(HttpServletRequest request) {
    	 return new ModelAndView("systems/system/departmentMgmt");
    }

    @RequestMapping("system/saveDepartment")
public @ResponseBody DodopalResponse<String> saveAndUpdateDepartment(HttpServletRequest request, @RequestBody Department departments) {
    DodopalResponse<String> response = new DodopalResponse<String>();
    try {
    	HttpSession session = request.getSession();
  		User user =(User)session.getAttribute(OSSConstants.SESSION_USER);
  		departments.setCreateUser(user.getId());
        String result = departmentService.saveAndUpdateDepartment(departments);
        response.setCode(ResponseCode.SUCCESS);
        response.setResponseEntity(result);
    }
    catch (DDPException ddp) {
        response.setCode(ResponseCode.OSS_DEPARTMENT_VALIDATOR);
        response.setMessage(ddp.getMessage());
    }
    catch (Exception e) {
        response.setCode(ResponseCode.UNKNOWN_ERROR);
    }
    return response;
}

@RequestMapping("system/findDepartmentPage")
public @ResponseBody DodopalResponse<DodopalDataPage<Department>> findDepartmentPage(HttpServletRequest request, @RequestBody DepartmentQuery department){  
    DodopalResponse<DodopalDataPage<Department>> response = new DodopalResponse<DodopalDataPage<Department>>();
    try {
        DodopalDataPage<Department> result = departmentService.findDepartmentByPage(department);
        response.setCode(ResponseCode.SUCCESS);
        response.setResponseEntity(result);
    }
    catch (Exception e) {
        e.printStackTrace();
        response.setCode(ResponseCode.UNKNOWN_ERROR);
    }
    return response;
}


@RequestMapping("system/deleteDepartment")
public @ResponseBody DodopalResponse<String> deleteDepartment(HttpServletRequest request, @RequestBody String[] depCodes) {
    DodopalResponse<String> response = new DodopalResponse<String>();
    try {
    	departmentService.deleteDepartment(depCodes);
        response.setCode(ResponseCode.SUCCESS);
        response.setResponseEntity(CommonConstants.SUCCESS);
    }
    catch (DDPException ddp) {
        response.setCode(ResponseCode.OSS_DEPARTMENT_VALIDATOR);
        response.setMessage(ddp.getMessage());
    }
    catch (Exception e) {
        response.setCode(ResponseCode.UNKNOWN_ERROR);
    }
    return response;
}

@RequestMapping("system/findDepartmentById")
public @ResponseBody DodopalResponse<Department> findDepartmentById(HttpServletRequest request,  @RequestBody String depCode) {
    DodopalResponse<Department> response = new DodopalResponse<Department>();
    try {
    	Department result = departmentService.findDepartmentById(depCode);
        response.setCode(ResponseCode.SUCCESS);
        response.setResponseEntity(result);
    }
    catch (Exception e) {
        e.printStackTrace();
        response.setCode(ResponseCode.UNKNOWN_ERROR);
    }
    return response;
}
    
/**
 * 启用部门信息
 * @param request
 * @param depCode
 * @return
 */
@RequestMapping("system/startDepartment")
public @ResponseBody DodopalResponse<String> startDepartment(HttpServletRequest request, @RequestBody String[] depCode) {
    DodopalResponse<String> response = new DodopalResponse<String>();
    try {
        if(depCode != null && depCode.length > 0) {
        	departmentService.activateDepartment(depCode,true);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(CommonConstants.SUCCESS);
        } else {
            response.setCode(ResponseCode.OSS_DEPARTMENT_VALIDATOR);
        }
    }
    catch (Exception e) {
        e.printStackTrace();
        response.setCode(ResponseCode.UNKNOWN_ERROR);
    }
    return response;
}


/**
 * 禁用部门信息
 * @param request
 * @param depCode
 * @return
 */
@RequestMapping("system/disableDepartment")
public @ResponseBody DodopalResponse<String> disableDepartment(HttpServletRequest request, @RequestBody String[] depCode) {
    DodopalResponse<String> response = new DodopalResponse<String>();
    try {
        if(depCode != null && depCode.length > 0) {
        	departmentService.activateDepartment(depCode,false);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(CommonConstants.SUCCESS);
        } else {
            response.setCode(ResponseCode.OSS_DEPARTMENT_VALIDATOR);
        }
    }
    catch (Exception e) {
        e.printStackTrace();
        response.setCode(ResponseCode.UNKNOWN_ERROR);
    }
    return response;
}

@RequestMapping("system/findDepartment")
public @ResponseBody DodopalResponse<List<Department>> findDepartment(HttpServletRequest request, @RequestBody Department department) {
    DodopalResponse<List<Department>> response = new DodopalResponse<List<Department>>();
    try {
        List<Department> result = departmentService.findDepartments(department);
        response.setResponseEntity(result);
        response.setCode(ResponseCode.SUCCESS);
    }
    catch (Exception e) {
        response.setCode(ResponseCode.UNKNOWN_ERROR);
    }
    return response;
}



    /**************************************************** 部门管理结束 *****************************************************/
    
    
}
