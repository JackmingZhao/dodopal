package com.dodopal.users.business.service;

import java.util.List;

import com.dodopal.users.business.model.MerDefineFun;

/**
 * 类说明 ：
 * @author lifeng
 */

public interface MerDefineFunService {
    public List<MerDefineFun> findMerDefineFunByMerCode(String merCode);

    public int batchAddMerDefineFunList(List<MerDefineFun> merDefineFunList);

    public int deleteMerDefineFunById(String id);
    
    public int deleteMerDefineFunByMerCode(String merCode);

    public int batchDelDefineFunByMerCodes(List<String> merCodes);
}
