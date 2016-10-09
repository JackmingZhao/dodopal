package com.dodopal.product.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.model.YktPsam;
import com.dodopal.product.business.model.query.YktPsamQuery;

public interface YktPsamService {
    
    //查询 分页
    public DodopalDataPage<YktPsam> findYktPsamByPage(YktPsamQuery yktPsamQuery);

    //批量删除
    public DodopalResponse<String> batchDeleteYktPsamByIds(List<String> ids);

    //批量启用商户
    public DodopalResponse<String> batchActivateMerByIds(List<String> ids);

    //批量修改重新下载参数
    public DodopalResponse<String> batchNeedDownLoadPsamByIds(List<String> ids);

    /**
     * 新增psam卡
     * @param yktPsam
     */
    public DodopalResponse<String> addYktPsam(YktPsam yktPsam);

    /**
     * 编辑psam卡
     * @param yktPsam
     */
    public DodopalResponse<String> updateYktPsam(YktPsam yktPsam);

    /**
     * 根据id查询psam卡信息
     * @param id
     * @return
     */
    public DodopalResponse<YktPsam> findPsamById(String id);
    
}
