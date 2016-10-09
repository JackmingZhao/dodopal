package com.dodopal.oss.business.service;

import java.util.List;

import com.dodopal.oss.business.bean.MerchantExcelBean;
import com.dodopal.oss.business.bean.MerchantNotUnauditedExpBean;
import com.dodopal.oss.business.bean.MerchantUnauditedExpBean;
import com.dodopal.oss.business.model.dto.MerchantQuery;

public interface MerchantExpService {
     //导出信息总条数
     public int exportExcelCountService(MerchantQuery merchantQuery);
    //导出已审核商户信息报表
     public List<MerchantExcelBean> exportExcelVerifiedService(MerchantQuery merchantQuery);
     //导出未审核商户信息报表
     public List<MerchantUnauditedExpBean> exportExcelUnauditedService(MerchantQuery merchantQuery);
     //导出审核不通过商户信息报表
     public List<MerchantNotUnauditedExpBean> exportExcelNotUnauditedService(MerchantQuery merchantQuery);
}
