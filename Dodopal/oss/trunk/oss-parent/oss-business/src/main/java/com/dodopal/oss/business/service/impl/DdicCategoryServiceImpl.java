package com.dodopal.oss.business.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.NamedEntity;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.oss.business.dao.DdicCategoryMapper;
import com.dodopal.oss.business.dao.RoleMapper;
import com.dodopal.oss.business.model.Ddic;
import com.dodopal.oss.business.model.DdicCategory;
import com.dodopal.oss.business.model.Department;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.model.dto.DdicCategoryQuery;
import com.dodopal.oss.business.service.DdicCategoryService;
import com.dodopal.oss.business.service.DdicService;

@Service
public class DdicCategoryServiceImpl implements DdicCategoryService {

    @Autowired
    private DdicCategoryMapper ddicCategoryMapper;
    
    @Autowired
    private RoleMapper roleMapper;
    
    @Autowired
    private DdicService ddicService;

    @Transactional
    @Override
    public String saveOrUpdateDdicCategory(DdicCategory ddicCategory) {
        validateDdicCategory(ddicCategory);
        if (DDPStringUtil.isNotPopulated(ddicCategory.getId())) {
            ddicCategory.setCreateDate(new Date());
            ddicCategory.setUpdateDate(new Date());
            ddicCategoryMapper.insertDdicCategory(ddicCategory);
        } else {
            ddicCategory.setUpdateDate(new Date());
            ddicCategoryMapper.updateDdicCategory(ddicCategory);
        }
        return CommonConstants.SUCCESS;
    }

    @Transactional(readOnly = true)
    @Override
    public List<DdicCategory> findDdicCategorys(DdicCategory ddicCategory) {
        return ddicCategoryMapper.findDdicCategorys(ddicCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public DodopalDataPage<DdicCategory> findDdicCategorysByPage(DdicCategoryQuery ddicCategoryQuery) {
        List<DdicCategory> result = ddicCategoryMapper.findDdicCategorysByPage(ddicCategoryQuery);
        DodopalDataPage<DdicCategory> pages = new DodopalDataPage<>(ddicCategoryQuery.getPage(), result);
        return pages;
    }

    @Transactional
    @Override
    public void deleteDdicCategory(String[] ddicCategoryId) {
            validatedeleteDdicCategory(ddicCategoryId);
            if (ddicCategoryId.length!=0) {
                ddicCategoryMapper.deleteDdicCategory(ddicCategoryId);
            } else {
                throw new DDPException("deleteDepartment.empty:\n", "分类编码被引用");
            }
    }

    
    
    public void validatedeleteDdicCategory(String[] ddicCategoryId) {
        List<String> msg = new ArrayList<String>();
        DdicCategory oldddicCategory = new DdicCategory();
        if (ddicCategoryId.length != 0) {
            for (int i = 0; i < ddicCategoryId.length; i++) {
                oldddicCategory = ddicCategoryMapper.findDdicCategoryById(ddicCategoryId[i]);
                if (oldddicCategory != null) {
                    String categoryCode = oldddicCategory.getCategoryCode();
                    Ddic findByCategoryCode = new Ddic();
                    findByCategoryCode.setCategoryCode(categoryCode);
                    List<Ddic> result = ddicService.findDdics(findByCategoryCode);
                    if (CollectionUtils.isNotEmpty(result)) {
                        msg.add(oldddicCategory.getCategoryName() + ",该分类编码被引用，无法执行删除操作");
                    }
                }

            }
        }
        if (!msg.isEmpty()) {
            throw new DDPException("\n", DDPStringUtil.concatenate(msg, ";<br/>"));
        }
    }
    private void validateDdicCategory(DdicCategory ddicCategory) {
        List<String> msg = new ArrayList<String>();
        if (!DDPStringUtil.existingWithLength(ddicCategory.getCategoryCode(), 64)) {
            msg.add("数据字典分类代码不能为空或长度不能超过64个字符");
        }

        if (!DDPStringUtil.existingWithLength(ddicCategory.getCategoryName(), 64)) {
            msg.add("数据字典分类名称不能为空或长度不能超过64个字符");
        }
        
        if (!DDPStringUtil.lessThan(ddicCategory.getDescription(), 255)) {
            msg.add("数据字典分类描述信息长度不能超过255个字符");
        }

        int count = ddicCategoryMapper.countDdicCategory(ddicCategory.getCategoryCode());
        if ((StringUtils.isEmpty(ddicCategory.getId()) && count >= 1) || (StringUtils.isNotEmpty(ddicCategory.getId()) && count > 1)) {
            msg.add("该分类编码已存在，请您重新输入");
        }
        if (!msg.isEmpty()) {
            throw new DDPException("validate.error:\n", DDPStringUtil.concatenate(msg, ";<br/>"));
        }
    }

    @Override
    public List<NamedEntity> loadCategoryCodes() {
        DdicCategory ddicCategory = new DdicCategory();
        ddicCategory.setActivate(ActivateEnum.ENABLE.getCode());
        List<DdicCategory> ddicCategories = ddicCategoryMapper.findDdicCategorys(ddicCategory);
        List<NamedEntity> results = new ArrayList<NamedEntity>();
        if (CollectionUtils.isNotEmpty(ddicCategories)) {
            for (DdicCategory category : ddicCategories) {
                results.add(new NamedEntity(category.getCategoryCode(), category.getCategoryCode()));
            }
        }
        return results;
    }

    @Override
    @Transactional
    public int batchActivateDdicCategory(String updateUser, List<String> categoryCodes) {
        return ddicCategoryMapper.batchActivateDdicCategory(updateUser, ActivateEnum.ENABLE.getCode(), categoryCodes);
    }

    @Override
    @Transactional
    public int batchInactivateDdicCategory(String updateUser, List<String> categoryCodes) {
        return ddicCategoryMapper.batchActivateDdicCategory(updateUser, ActivateEnum.DISABLE.getCode(), categoryCodes);
    }

}
