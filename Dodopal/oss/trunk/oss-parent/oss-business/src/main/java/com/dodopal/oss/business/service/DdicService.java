package com.dodopal.oss.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.oss.business.model.Ddic;
import com.dodopal.oss.business.model.dto.DdicQuery;

public interface DdicService {

    /**
     * 保存或修改数据字典信息
     * @param ddic
     * @return
     */
    String saveOrUpdateDdic(Ddic ddic);

    /**
     * 查找数据字典
     * @param ddic
     * @return
     */
    List<Ddic> findDdics(Ddic ddic);

    public DodopalDataPage<Ddic> findDdicsByPage(DdicQuery ddicQuery);

    /**
     * 查看数据字典
     * @param id
     * @return
     */
    public Ddic findDdicById(String id);

    /**
     * 根据分类编码查询数据字典
     * @param categoryCode
     * @return
     */
    public List<Ddic> findDdicByCategoryCode(String categoryCode);

    /**
     * 删除数据字典
     * @param ddicId
     */
    void deleteDdic(String ddicId);

    /**
     * 批量删除数据字典(逻辑删除)
     * @param updateUser
     * @param ids
     */
    public void batchDelDdic(String updateUser, List<String> ids);

    /**
     * 批量启用数据字典
     * @param updateUser
     * @param codes
     * @return
     */
    public int batchActivateDdic(String updateUser, List<String> ids);

    /**
     * 批量停用数据字典
     * @param updateUser
     * @param codes
     * @return
     */
    public int batchInactivateDdic(String updateUser, List<String> ids);
}
