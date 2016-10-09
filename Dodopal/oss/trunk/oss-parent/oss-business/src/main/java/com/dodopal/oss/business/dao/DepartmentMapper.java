package com.dodopal.oss.business.dao;

import java.util.List;

import com.dodopal.oss.business.model.Department;
import com.dodopal.oss.business.model.dto.DepartmentQuery;

public interface DepartmentMapper {

    public int insertDepartment(Department department);

    public int updateDepartment(Department department);

    public void deleteDepartment(String[] depCode);

    public Department findDepartmentById(String depCode);
    
    public List<Department> findDepartmentByPage(DepartmentQuery department);
    
    public List<Department> findDepartments(Department department);
    
    public void startActivateDepartment(String[] depCodes);
    
    public void disableActivateDepartment(String[] depCodes);
}
