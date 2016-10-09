package com.dodopal.oss.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.oss.business.model.Ddic;
import com.dodopal.oss.business.model.dto.DdicQuery;

public interface DdicMapper {

    public void insertDdic(Ddic ddic);

    public void updateDdic(Ddic ddic);

    public void deleteDdic(String id);

    public List<Ddic> findDdics(Ddic ddic);

    public List<Ddic> findDdicsByPage(DdicQuery ddicQuery);

    public Ddic findDdicById(String id);

    public List<Ddic> findDdicByCategoryCode(String categoryCode);

    public int countDdic(String code, String categoryCode);

    public int batchActivateDdic(@Param("updateUser") String updateUser, @Param("activate") String activate, @Param("ids") List<String> ids);

    public int batchDelDdic(@Param("updateUser")String updateUser, @Param("ids") List<String> ids);

    public Ddic findDdicByCategoryAndCode(String code, String categoryCode);
}
    
