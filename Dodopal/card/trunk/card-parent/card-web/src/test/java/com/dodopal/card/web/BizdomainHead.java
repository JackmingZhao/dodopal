package com.dodopal.card.web;

import com.dodopal.api.card.dto.CrdSysOrderDTO;

public class BizdomainHead {
    private  Bizdomain bizdomain;
    
    private CrdSysOrderDTO carddomain;

    public CrdSysOrderDTO getCarddomain() {
        return carddomain;
    }

    public void setCarddomain(CrdSysOrderDTO carddomain) {
        this.carddomain = carddomain;
    }

    public Bizdomain getBizdomain() {
        return bizdomain;
    }

    public void setBizdomain(Bizdomain bizdomain) {
        this.bizdomain = bizdomain;
    }
    
}
