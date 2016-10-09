package com.dodopal.common.dao;

import java.util.List;

import com.dodopal.common.model.DdicVo;

public interface DdicVoMapper {
    
    public List<DdicVo> findDdics();

    public DdicVo findDdicByCode(String code);
    
    public List<DdicVo> findDdicByCategoryCode(String categoryCode);
    
    public List<String> findDDICCategoryCode();
    
}
    
