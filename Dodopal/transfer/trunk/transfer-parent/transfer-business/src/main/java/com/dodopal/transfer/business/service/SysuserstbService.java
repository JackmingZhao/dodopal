package com.dodopal.transfer.business.service;

import com.dodopal.common.model.DodopalResponse;
import com.dodopal.transfer.business.model.old.Sysuserstb;

public interface SysuserstbService {
    //测试用户注册
    public Sysuserstb findSysuserstb(String userid);
  //1.读取老系统连锁加盟Sysuserstb表中的商户
    public DodopalResponse<String> findAllSysuserstb(String mchnitid);
    public  DodopalResponse<String> findGRAllSysuserstbByPage(String totalPages);
}
