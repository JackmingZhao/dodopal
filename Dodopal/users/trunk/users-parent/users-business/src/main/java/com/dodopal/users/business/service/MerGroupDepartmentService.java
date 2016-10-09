package com.dodopal.users.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.users.business.model.MerGroupDepartment;
import com.dodopal.users.business.model.query.MerGroupDepartmentQuery;

/**
 * @author Dingkuiyuan@dodopal.com
 * @version 创建时间：2015年5月5日 下午2:34:46
 */
public interface MerGroupDepartmentService {
    /** 
     * @Title: saveMerGroupDepartmentMapper 
     * @Description: 保存部门信息
     * @param department    设定文件 
     * void    返回类型 
     * @throws 
     */
    public void saveMerGroupDepartment(MerGroupDepartment department);
    
    
    /** 
     * @Title: findMerGroupDepartmentMapperList 
     * @Description: 获取部门信息列表
     * @param department    设定文件 
     * void    返回类型 
     * @throws 
     */
    public List<MerGroupDepartment> findMerGroupDepartmentList(MerGroupDepartment department);
    

    /** 
     * @Title: findMerGroupDepartmentListByPage 
     * @Description: 分页查询
     * @param department
     * @return    设定文件 
     * DodopalDataPage<MerGroupDepartment>    返回类型 
     * @throws 
     */
    public  DodopalDataPage<MerGroupDepartment> findMerGroupDepartmentListByPage(MerGroupDepartmentQuery department);

    /** 
     * @Title: findMerGpDepByMerCodeAndDeptName 
     * @Description: 根据商户号和部门名称查找 
     * @param department
     * @return    设定文件 
     * MerGroupDepartment    返回类型 
     * @throws 
     */
    public List<MerGroupDepartment> findMerGpDepByMerCodeAndDeptName(MerGroupDepartment department);
    
    
    /** 
     * @Title: findMerGpDepById 
     * @Description: 根据部门id查找部门信息
     * @param Id
     * @return    设定文件 
     * MerGroupDepartment    返回类型 
     * @throws 
     */
    public MerGroupDepartment findMerGpDepById(String id);

    /** 
     * @Title: deleteMerGroupDepartment 
     * @Description: 删除部门
     * @param id
     * @return    设定文件 
     * int    返回类型 
     * @throws 
     */
    public int deleteMerGroupDepartment(List<String> id);
    
    
    /** 
     * @Title: updateMerGroupDepartment 
     * @Description: 更新操作
     * @param department
     * @return    设定文件 
     * int    返回类型 
     * @throws 
     */
    public int updateMerGroupDepartment(MerGroupDepartment department);

    /** 
     * @Title: startOrStopMerGroupDepartment 
     * @Description: 部门停用启用
     * @param act 启用 停用
     * @param ids id
     * @return    设定文件 
     * int    返回类型 
     * @throws 
     */
    public int startOrStopMerGroupDepartment(String act,List<String> ids);
}
