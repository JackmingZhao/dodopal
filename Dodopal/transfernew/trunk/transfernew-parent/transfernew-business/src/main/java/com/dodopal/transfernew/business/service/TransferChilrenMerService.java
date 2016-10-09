package com.dodopal.transfernew.business.service;

import com.dodopal.common.model.DodopalResponse;

/**
 * 普通网点和加盟网点数据迁移
 * @author lenovo
 *
 */
public interface TransferChilrenMerService {
    
    /**
     * 网点迁移
     * @param procode 省份
     * @param citycode 城市
     * @return
     */
   public DodopalResponse<String> transferChilrenMerService(String citycode);
}
