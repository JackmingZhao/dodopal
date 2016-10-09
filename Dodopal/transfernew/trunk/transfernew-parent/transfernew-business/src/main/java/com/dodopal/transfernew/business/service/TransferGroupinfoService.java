package com.dodopal.transfernew.business.service;

import com.dodopal.common.model.DodopalResponse;

/**
 * 集团信息迁移
 * @author tao
 *
 */
public interface TransferGroupinfoService {
    /**
     * 集团迁移
     * @param citycode 城市
     * @return
     */
	 public DodopalResponse<String> transferGroupinfo(String citycode);

}
