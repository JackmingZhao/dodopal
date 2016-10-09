package com.dodopal.oss.business.service;


import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.oss.business.model.Department;
import com.dodopal.oss.business.model.dto.DepartmentQuery;

public interface DepartmentService {

    public String saveAndUpdateDepartment(Department department);

    public void deleteDepartment(String[] depCode);

    public Department findDepartmentById(String depCode);

    public List<Department> findDepartments(Department department);
    /**
     * 查找部门信息 - 分页查找
     * @param type
     * @return
     */
    DodopalDataPage<Department> findDepartmentByPage(DepartmentQuery department);
    /**
     * 停用和启用
     * @param depCodes
     * @param activate
     */
    public void activateDepartment(String[] depCodes, boolean activate);
}
