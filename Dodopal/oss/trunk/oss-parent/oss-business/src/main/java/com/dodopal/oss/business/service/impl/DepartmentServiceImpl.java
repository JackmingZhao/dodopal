package com.dodopal.oss.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.oss.business.dao.DepartmentMapper;
import com.dodopal.oss.business.dao.UserMapper;
import com.dodopal.oss.business.model.Department;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.model.dto.DepartmentQuery;
import com.dodopal.oss.business.model.dto.UserQuery;
import com.dodopal.oss.business.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper mapper;
    @Autowired
    private UserMapper userMapper;
    
    @Transactional(readOnly = true)
    @Override
    public DodopalDataPage<Department> findDepartmentByPage(DepartmentQuery department) {
    	   List<Department> result = mapper.findDepartmentByPage(department);
           DodopalDataPage<Department> pages = new DodopalDataPage<Department>(department.getPage(), result);
           return pages;
    }

    @Transactional
    public String saveAndUpdateDepartment(Department department) {
    	validateDepartment(department);
        if (StringUtils.isNotBlank(department.getId())) {
        	department.setUpdateUser(department.getCreateUser());
        	mapper.updateDepartment(department);
        } else {
        	department.setCreateUser(department.getCreateUser());
        	department.setUpdateUser(department.getCreateUser());
            mapper.insertDepartment(department);
        }
        return CommonConstants.SUCCESS;
    }
    
    private void validateDepartment(Department department) {
        List<String> msg = new ArrayList<String>();
        if (!DDPStringUtil.existingWithLength(department.getDepCode(), 10)) {
            msg.add("部门编码不能为空或长度不能超过10个字符");
        }

        if (!DDPStringUtil.existingWithLength(department.getDepName(), 20)) {
            msg.add("部门名称不能为空或长度不能超过20个字符");
        }
        
        if (!DDPStringUtil.lessThan(department.getRemark(), 255)) {
            msg.add("备注信息长度不能超过255个字符");
        }
        
        if(StringUtils.isBlank(department.getId())) { //新建部门-1.  部门编码和现有的部门编码不能重复。2.  部门名称不能与现有的部门名称重复。
        	Department queryDepartment = new Department();
            queryDepartment.setDepCode(department.getDepCode());
            List<Department> departments = mapper.findDepartments(queryDepartment);
            if(CollectionUtils.isNotEmpty(departments)) {
                msg.add("部门编码已存在,请重新输入");
            }
            queryDepartment = new Department();
            queryDepartment.setDepName(department.getDepName());
            departments = mapper.findDepartments(queryDepartment);
            if(CollectionUtils.isNotEmpty(departments)) {
                msg.add("部门名称已存在,请重新输入");
            }
        } else{
            Department queryDepartment = new Department();
            queryDepartment = new Department();
            queryDepartment.setDepName(department.getDepName());
            List<Department> departments = mapper.findDepartments(queryDepartment);
            if(CollectionUtils.isNotEmpty(departments)) {
                if(!departments.get(0).getDepCode().equals(department.getDepCode())){
                    msg.add("部门名称已存在,请重新输入");
                }
            }
        }
        if (!msg.isEmpty()) {
            throw new DDPException("validate.error:\n", DDPStringUtil.concatenate(msg, ";<br/>"));
        }
    }
    

    @Transactional
    public void deleteDepartment(String[] depCode) {
    	validatedeleteDepartment(depCode);
    	 if (depCode.length!=0) {
    		 mapper.deleteDepartment(depCode);
         } else {
             throw new DDPException("deleteDepartment.empty:\n", "部门编码不能为空");
         }
       
    }
    public void validatedeleteDepartment(String[] depCode){
    	 List<String> msg = new ArrayList<String>();
    	 Department oldDepartment =new Department();
    	if (depCode.length!=0){
    	    for(int i=0;i<depCode.length;i++){
    	        oldDepartment = mapper.findDepartmentById(depCode[i]);
    	  
            if (oldDepartment != null) {
                     UserQuery user = new UserQuery();
                     List<User> userList=new ArrayList<User>();
                     user.setDepartmentCode(oldDepartment.getDepCode());
                     userList = userMapper.findUsers(user);
                     if (userList.size()!=0) {
                         msg.add(oldDepartment.getDepName()+ ":中含有人员信息，不能删除\n");
                     }
                }
            
    	    }
    }
    	   if (!msg.isEmpty()) {
               throw new DDPException("\n", DDPStringUtil.concatenate(msg, ";<br/>"));
           }
   }
    
    @Transactional(readOnly = true)
    public Department findDepartmentById(String depCode) {
    	  if (StringUtils.isNotEmpty(depCode)) {
    		  return mapper.findDepartmentById(depCode);
          } else {
              throw new DDPException("findDepartmentById.Idempty:\n", "部门编码为空");
          }
    	
      
    }

    @Transactional
    @Override
	public void activateDepartment(String[] depCodes, boolean activate) {
    	  if(activate) {
    		  mapper.startActivateDepartment(depCodes);
          } else{
        	  mapper.disableActivateDepartment(depCodes);
          }
	}

	@Override
	public List<Department> findDepartments(Department department) {
		return mapper.findDepartments(department);
	}

    

}
