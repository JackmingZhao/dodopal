package com.dodopal.transfernew.business.dao;

import java.util.List;

import com.dodopal.transfernew.business.model.transfer.PosMerTb;

public interface PosMerTbMapper {

    /**
     * 
     * @param posMerTb
     */
    public void addPosMerTb(PosMerTb posMerTb);

    public int batchAddPosMerTb(List<PosMerTb> list);
}
