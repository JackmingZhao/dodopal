package com.dodopal.oss.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.oss.business.model.PosType;
import com.dodopal.oss.business.model.dto.PosTypeQuery;

public interface PosTypeMapper {

public void insertPosType(PosType type);
    
    public void updatePosType(PosType type);
    
    public void deletePosType(String[] codes);
    
    public List<PosType> findPosTypesByPage(PosTypeQuery type);
    
    public List<PosType> findPosTypes(PosType type);

    public PosType findPosTypeById(String id);
    
    int countPosType(String code);
    
    int countPosTypeByName(String name);
    
    public void activatePosType(String[] typeIds);
    
    public void inactivatePosType(String[] typeIds);
    
    List<PosType> loadPosType();
    
    
    public int batchActivateType(@Param("updateUser") String updateUser, @Param("activate") String activate, @Param("ids") List<String> ids);
}
    
