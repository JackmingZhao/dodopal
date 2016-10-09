package com.dodopal.oss.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.NamedEntity;
import com.dodopal.oss.business.model.PosType;
import com.dodopal.oss.business.model.dto.PosTypeQuery;

public interface PosTypeService {
    
    /**
     * 保存或修改pos 类型信息
     * @param type
     * @return
     */
    String saveOrUpdatePosType(PosType type);

    /**
     * 查找pos 类型
     * @param type
     * @return
     */
    List<PosType> findPosTypes(PosType type);
    
    /**
     * 查找pos 类型 - 分页查找
     * @param type
     * @return
     */
    DodopalDataPage<PosType> findPosTypeByPage(PosTypeQuery type);
    
    
    /**
     * 根据typeID 查找pos 类型
     * @param type
     * @return
     */
    PosType findPosTypeById(String typeId);

    /**
     * 删除pos 类型
     * @param codes
     */
    void deletePosType(String[] codes);
    
    /**
     * 批量启用POS类型
     * @param updateUser
     * @param ids
     * @return
     */
    public int batchActivatePosType(String updateUser, List<String> ids);

    /**
     * 批量停用POS类型
     * @param updateUser
     * @param ids
     * @return
     */
    public int batchInactivatePosType(String updateUser, List<String> ids);
    
    List<NamedEntity>  loadPosType();
    
}
