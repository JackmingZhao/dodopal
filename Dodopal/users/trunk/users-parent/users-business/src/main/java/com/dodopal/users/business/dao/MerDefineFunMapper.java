package com.dodopal.users.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.users.business.model.MerDefineFun;

/**
 * 类说明 ：
 * @author lifeng
 */

public interface MerDefineFunMapper {
    public List<MerDefineFun> findMerDefineFunByMerCode(String merCode);

    public int batchAddMerDefineFunList(List<MerDefineFun> list);

    public int deleteMerDefineFunById(String id);

    public int deleteMerDefineFunByMerCode(String merCode);

    public int batchDelDefineFunByMerCodes(@Param("merCodes") List<String> merCodes);
}
