package com.dodopal.merdevice.delegate;

import com.dodopal.api.card.dto.CrdSysOrderDTO;

public interface CrdMerdeviceDelegate {
    //1.产品库验卡接口
    public String checkCrd(CrdSysOrderDTO crdSysOrderDTO);
}
