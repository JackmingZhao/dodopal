package com.dodopal.oss.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.bean.YktLimitBatchReportForExport;
import com.dodopal.oss.business.bean.query.YktLimitBatchReportQuery;


public interface YktLimitBatchReportService {
   
    DodopalResponse<DodopalDataPage<YktLimitBatchReportForExport>> findYktLimitBatchReportByPage(YktLimitBatchReportQuery queryDto);
    
    DodopalResponse<List<YktLimitBatchReportForExport>> getYktLimitBatchReportForExport(YktLimitBatchReportQuery queryDto);
    
}
