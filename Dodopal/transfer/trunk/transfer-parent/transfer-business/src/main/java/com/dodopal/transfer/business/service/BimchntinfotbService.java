package com.dodopal.transfer.business.service;

import com.dodopal.common.model.DodopalResponse;
import com.dodopal.transfer.business.model.old.Bimchntinfotb;

public interface BimchntinfotbService {
    
    //1.读取老系统连锁商户Bimchntinfotb表中的商户
    public DodopalResponse<String> findAllBimchntinfotb();
    public Bimchntinfotb findBimchntinfotb(String mchnitid);
}
