package com.dodopal.product.business.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.dao.CcOrderMapper;
import com.dodopal.product.business.model.CcOrderForm;
import com.dodopal.product.business.model.CzOrderByPosCount;
import com.dodopal.product.business.model.CzOrderByPosCountAll;
import com.dodopal.product.business.model.query.QueryCcOrderForm;
import com.dodopal.product.business.model.query.QueryCzOrderByPosCount;
import com.dodopal.product.business.service.CcOrderService;

@Service
public class CcOrderServiceImpl implements CcOrderService {

    @Autowired
    CcOrderMapper ccOrderMapper;

    @Override
    @Transactional(readOnly = true)
    public DodopalDataPage<CcOrderForm> findCcOrderFormByPage(QueryCcOrderForm queryCcOrderForm) {
        if (StringUtils.isNotBlank(queryCcOrderForm.getMchnitid())) {
            String mchnitid = ccOrderMapper.findMchnitidByMerCode(queryCcOrderForm.getMchnitid());
            if (mchnitid != null) {
                queryCcOrderForm.setMchnitid(mchnitid);
            } else {
                queryCcOrderForm.setMchnitid("");
            }

        }
        List<CcOrderForm> ccorderList = ccOrderMapper.findAllCcOrderByPage(queryCcOrderForm);
        DodopalDataPage<CcOrderForm> pages = new DodopalDataPage<CcOrderForm>(queryCcOrderForm.getPage(), ccorderList);
        return pages;
    }

    @Override
    public DodopalDataPage<CzOrderByPosCount> findCzOrderByPosCountByPage(QueryCzOrderByPosCount queryCzOrderByPosCount) {
        if (StringUtils.isNotBlank(queryCzOrderByPosCount.getMchnitid())) {
            String mchnitid = ccOrderMapper.findMchnitidByMerCode(queryCzOrderByPosCount.getMchnitid());
            if (mchnitid != null) {
                queryCzOrderByPosCount.setMchnitid(mchnitid);
            } else {
                queryCzOrderByPosCount.setMchnitid("");
            }
        }
        List<CzOrderByPosCount> czOrderByPosCountList = ccOrderMapper.findCzOrderByPosCountByPage(queryCzOrderByPosCount);
        DodopalDataPage<CzOrderByPosCount> pages = new DodopalDataPage<CzOrderByPosCount>(queryCzOrderByPosCount.getPage(), czOrderByPosCountList);
        return pages;
    }

    @Override
    public DodopalResponse<CzOrderByPosCountAll> findCzOrderByPosCountAll(QueryCzOrderByPosCount queryCzOrderByPosCount) {
        if (StringUtils.isNotBlank(queryCzOrderByPosCount.getMchnitid())) {
            String mchnitid = ccOrderMapper.findMchnitidByMerCode(queryCzOrderByPosCount.getMchnitid());
            if (mchnitid != null) {
                queryCzOrderByPosCount.setMchnitid(mchnitid);
            } else {
                queryCzOrderByPosCount.setMchnitid("");
            }
        }
        CzOrderByPosCountAll czOrderByPosCount = ccOrderMapper.findCzOrderByPosCountAll(queryCzOrderByPosCount);
        DodopalResponse<CzOrderByPosCountAll> rep = new DodopalResponse<CzOrderByPosCountAll>();
        rep.setResponseEntity(czOrderByPosCount);
        return rep;
    }

    @Override
    public DodopalResponse<List<CzOrderByPosCount>> findCzOrderByPosCountExcel(QueryCzOrderByPosCount queryCzOrderByPosCount) {
        if (StringUtils.isNotBlank(queryCzOrderByPosCount.getMchnitid())) {
            String mchnitid = ccOrderMapper.findMchnitidByMerCode(queryCzOrderByPosCount.getMchnitid());
            if (mchnitid != null) {
                queryCzOrderByPosCount.setMchnitid(mchnitid);
            } else {
                queryCzOrderByPosCount.setMchnitid("");
            }
        }
        List<CzOrderByPosCount> czOrderByPosCountList = ccOrderMapper.findCzOrderByPosCountExcel(queryCzOrderByPosCount);
        DodopalResponse<List<CzOrderByPosCount>> rep = new DodopalResponse<List<CzOrderByPosCount>>();
        rep.setResponseEntity(czOrderByPosCountList);
        return rep;
    }

    @Override
    public DodopalResponse<List<CcOrderForm>> findCcOrderFormExcel(QueryCcOrderForm queryCcOrderForm) {
        if (StringUtils.isNotBlank(queryCcOrderForm.getMchnitid())) {
            String mchnitid = ccOrderMapper.findMchnitidByMerCode(queryCcOrderForm.getMchnitid());
            if (mchnitid != null) {
                queryCcOrderForm.setMchnitid(mchnitid);
            } else {
                queryCcOrderForm.setMchnitid("");
            }
        }
        List<CcOrderForm> ccorderList = ccOrderMapper.findAllCcOrderExcel(queryCcOrderForm);
        DodopalResponse<List<CcOrderForm>> rep = new DodopalResponse<List<CcOrderForm>>();
        rep.setResponseEntity(ccorderList);
        return rep;
    }

}
