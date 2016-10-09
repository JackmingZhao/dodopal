package com.dodopal.users.business.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.users.business.dao.MerGroupDepartmentMapper;
import com.dodopal.users.business.dao.MerchantUserMapper;
import com.dodopal.users.business.model.MerGroupDepartment;
import com.dodopal.users.business.model.MerGroupUser;
import com.dodopal.users.business.model.MerGroupUserFind;
import com.dodopal.users.business.model.MerchantUser;
import com.dodopal.users.business.model.query.MerGroupDepartmentQuery;
import com.dodopal.users.business.service.MerGroupDepartmentService;
import com.dodopal.users.business.service.MerGroupUserService;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年5月5日 下午3:02:17
 */
@Service
public class MerGroupDepartmentServiceImpl implements MerGroupDepartmentService {
        
    @Autowired
    private MerGroupDepartmentMapper merGroupDepartmentMapper;
    @Autowired
    private MerGroupUserService groupUserService;
    @Autowired
    private MerchantUserMapper merUsermapper; 
    @Override
    @Transactional
    public void saveMerGroupDepartment(MerGroupDepartment department) {
        department.setCreateDate(new Date());
        List<MerGroupDepartment>  list = findMerGpDepByMerCodeAndDeptName(department);
        if(CollectionUtils.isEmpty(list)){
            merGroupDepartmentMapper.saveMerGroupDepartment(department);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<MerGroupDepartment> findMerGroupDepartmentList(MerGroupDepartment department) {
        return merGroupDepartmentMapper.findMerGroupDepartmentList(department);
    }

    @Override
    @Transactional
    public int deleteMerGroupDepartment(List<String> id) {
       //执行一次用户管辖部门关系表的删除操作
        merUsermapper.deleteMerDeptUserByDeptId(id);
       return merGroupDepartmentMapper.deleteMerGroupDepartment(id);
    }

    @Override
    @Transactional
    public int updateMerGroupDepartment(MerGroupDepartment department) {
        return merGroupDepartmentMapper.updateMerGroupDepartment(department);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MerGroupDepartment> findMerGpDepByMerCodeAndDeptName(MerGroupDepartment department) {
        if(StringUtils.isNotBlank(department.getDepName())&&StringUtils.isNotBlank(department.getMerCode())){
            return merGroupDepartmentMapper.findMerGpDepByMerCodeAndDeptName(department);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public MerGroupDepartment findMerGpDepById(String Id) {
        return merGroupDepartmentMapper.findMerGpDepById(Id);
    }

    @Override
    @Transactional(readOnly = true)
    public DodopalDataPage<MerGroupDepartment> findMerGroupDepartmentListByPage(MerGroupDepartmentQuery department) {
        List<MerGroupDepartment> result = merGroupDepartmentMapper.findMerGroupDepartmentListByPage(department);
        DodopalDataPage<MerGroupDepartment> pages = new DodopalDataPage<MerGroupDepartment>(department.getPage(), result);
        return pages;
    }

    @Override
    @Transactional
    public int startOrStopMerGroupDepartment(String act, List<String> ids) {
        int updateRow = 0;
        if(!ids.isEmpty()){
            Map map  =  new HashMap();
            map.put("activate", act);
            map.put("list", ids);
            updateRow = merGroupDepartmentMapper.startOrStopMerGroupDepartment(map);
        }
        return updateRow;
    }

}
