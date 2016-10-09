package com.dodopal.users.business.dao;

import java.util.List;
import java.util.Map;

import com.dodopal.users.business.model.MerGroupDepartment;
import com.dodopal.users.business.model.query.MerGroupDepartmentQuery;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年5月5日 上午10:17:53
 */
public interface MerGroupDepartmentMapper {
    /** 
     * @Title: saveMerGroupDepartment 
     * @Description: 保存部门
     * @param department    设定文件 
     * void    返回类型 
     * @throws 
     */
    public void saveMerGroupDepartment(MerGroupDepartment department);
    
    /** 
     * @Title: findMerGroupDepartmentList 
     * @Description: 查找部门列表
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
     * List<MerGroupDepartment>    返回类型 
     * @throws 
     */
    public  List<MerGroupDepartment> findMerGroupDepartmentListByPage(MerGroupDepartmentQuery department);

    
    /** 
     * @Title: findMerGpDepById 
     * @Description:根据id查找部门信息
     * @param id
     * @return    设定文件 
     * MerGroupDepartment    返回类型 
     * @throws 
     */
    public MerGroupDepartment findMerGpDepById(String id);
 
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
     * @Title: deleteMerGroupDepartment 
     * @Description: 删除部门信息
     * @param id
     * @return    设定文件 
     * int    返回类型 
     * @throws 
     */
    public int deleteMerGroupDepartment(List<String> id);
    
    /** 
     * @Title: updateMerGroupDepartment 
     * @Description: 更新部门信息
     * @param department
     * @return    设定文件 
     * int    返回类型 
     * @throws 
     */
    public int updateMerGroupDepartment(MerGroupDepartment department);
    
    /** 
     * @Title: startOrStopMerGroupDepartment 
     * @Description: 停用启用部门
     * @param map
     * @return    设定文件 
     * int    返回类型 
     * @throws 
     */
    public int startOrStopMerGroupDepartment(Map<String ,String> map);
}
