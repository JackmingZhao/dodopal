package com.dodopal.users.business.service;

import java.util.List;

import com.dodopal.users.business.model.MerRoleFun;

/**
 * 类说明 ：
 * @author lifeng
 */

public interface MerRoleFunService {
    public List<MerRoleFun> findMerRoleFun(MerRoleFun merRoleFun);

    public MerRoleFun findMerRoleFunById(String id);
    
    public List<MerRoleFun> findMerRoleFunByMerRoleCode(String merRoleCode);

    public int delMerRoleFunByMerRoleCode(String merRoleCode);

    public int batchAddMerRoleFun(List<MerRoleFun> list);

    public int updateActivateById(String id, String activate);

    public int updateActivateByMerRoleCode(String merRoleCode, String activate, List<MerRoleFun> list);
}
