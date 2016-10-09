package com.dodopal.oss.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.oss.business.model.Department;
import com.dodopal.oss.business.service.DepartmentService;

//@Controller
public class DepartmentController extends CommonController {

    @Autowired
    private DepartmentService departmentService;

//    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("idx");
        return mav;
    }

//    @RequestMapping("/addDepartment")
    public @ResponseBody String addDepartment(HttpServletRequest request, @RequestBody Department department) {
       // 
        //departmentService.insertDepartment(department);
        return "success";
    }

//    @RequestMapping("/deleteDepartment")
    public  @ResponseBody String  deleteDepartment(HttpServletRequest request, @RequestBody String id) {
      //  departmentService.deleteDepartment(id);
        return "success";
    }

//    @RequestMapping("/updateDepartment")
    public  @ResponseBody String  updateDepartment(HttpServletRequest request, @RequestBody Department department) {
      //  departmentService.updateDepartment(department);
        return "success";
    }

//    @RequestMapping("/findDepartment")
    public @ResponseBody List<Department> findDepartment(HttpServletRequest request, @RequestBody Department department) {
        return null;
    }

}
