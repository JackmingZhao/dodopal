package com.dodopal.oss.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.oss.business.model.DdicCategory;
import com.dodopal.oss.business.model.dto.DdicCategoryQuery;

public interface DdicCategoryMapper {

    public void insertDdicCategory(DdicCategory ddicCategory);

    public void updateDdicCategory(DdicCategory ddicCategory);

    public void deleteDdicCategory(String[] id);

    public List<DdicCategory> findDdicCategorys(DdicCategory ddicCategory);

    public List<DdicCategory> findDdicCategorysByPage(DdicCategoryQuery ddicCategoryQuery);

    public DdicCategory findDdicCategoryById(String id);

    public DdicCategory findDdicCategoryByCode(String code);

    public int countDdicCategory(String categoryCode);

    public int batchActivateDdicCategory(@Param("updateUser") String updateUser, @Param("activate") String activate, @Param("categoryCodes") List<String> categoryCodes);
}
