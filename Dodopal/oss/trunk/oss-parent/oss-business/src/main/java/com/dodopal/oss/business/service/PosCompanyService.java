package com.dodopal.oss.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.NamedEntity;
import com.dodopal.oss.business.model.PosCompany;
import com.dodopal.oss.business.model.dto.PosCompanyQuery;

public interface PosCompanyService {

    /**
     * 保存或修改pos 厂商信息
     * @param company
     * @return
     */
    String saveOrUpdatePosCompany(PosCompany company);

    /**
     * 查找pos 厂商
     * @param company
     * @return
     */
    DodopalDataPage<PosCompany> findPosCompanysByPage(PosCompanyQuery company);
    
    
    /**
     * 根据companyID 查找pos 厂商
     * @param company
     * @return
     */
    PosCompany findPosCompanyById(String companyId);

    /**
     * 删除pos 厂商
     * @param companyId
     */
    void deletePosCompany(String[] companyIds);
    
    /**
     * 批量启用POS厂商
     * @param updateUser
     * @param ids
     * @return
     */
    public int batchActivatePosCompany(String updateUser, List<String> ids);

    /**
     * 批量停用POS厂商
     * @param updateUser
     * @param ids
     * @return
     */
    public int batchInactivatePosCompany(String updateUser, List<String> ids);
    
    
    List<NamedEntity> loadPosCompany();
   
}
