package com.dodopal.oss.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.oss.business.model.PosCompany;
import com.dodopal.oss.business.model.dto.PosCompanyQuery;

public interface PosCompanyMapper {

    public void insertPosCompany(PosCompany company);
    
    public void updatePosCompany(PosCompany company);
    
    public void deletePosCompany(String[] id);
    
    public List<PosCompany> findPosCompanysByPage(PosCompanyQuery company);
    
    public List<PosCompany> findPosCompanys(PosCompany company);

    public PosCompany findPosCompanyById(String id);
    
    int countPosCompany(String code);
    
    List<PosCompany> loadPosCompany();
    
    public int batchActivateCompany(@Param("updateUser") String updateUser, @Param("activate") String activate, @Param("ids") List<String> ids);
}
    
