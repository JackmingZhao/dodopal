package com.dodopal.oss.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.NamedEntity;
import com.dodopal.oss.business.model.DdicCategory;
import com.dodopal.oss.business.model.dto.DdicCategoryQuery;

public interface DdicCategoryService {

    /**
     * 保存或修改数据字典分类信息
     * @param ddicCategory
     * @return
     */
    String saveOrUpdateDdicCategory(DdicCategory ddicCategory);

    /**
     * 查找数据字典分类
     * @param ddicCategory
     * @return
     */
    List<DdicCategory> findDdicCategorys(DdicCategory ddicCategory);

    /**
     * 查找数据字典分类(分页)
     * @param ddicCategoryQuery
     * @return
     */
    public DodopalDataPage<DdicCategory> findDdicCategorysByPage(DdicCategoryQuery ddicCategoryQuery);

    /**
     * 删除数据字典分类
     * @param ddicCategoryId
     */
    void deleteDdicCategory(String[] ddicCategoryId);
    
    /**
     * 加载数据字典分类代码
     * 
     */
    List<NamedEntity> loadCategoryCodes();

    /**
     * 批量启用数据字典分类
     * @param updateUser
     * @param categoryCodes
     * @return
     */
    public int batchActivateDdicCategory(String updateUser, List<String> categoryCodes);

    /**
     * 批量停用数据字典分类
     * @param updateUser
     * @param categoryCodes
     * @return
     */
    public int batchInactivateDdicCategory(String updateUser, List<String> categoryCodes);
}
