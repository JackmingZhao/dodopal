package com.dodopal.oss.business.dao;

import java.util.List;

import com.dodopal.oss.business.bean.YktLimitBatchReportForExport;
import com.dodopal.oss.business.bean.query.YktLimitBatchReportQuery;

public interface YktLimitBatchReportMapper {
    
    public List<YktLimitBatchReportForExport> findYktLimitBatchReportByPage(YktLimitBatchReportQuery queryDto);
    
    public int getCountForYktLimitBatchReportExport(YktLimitBatchReportQuery queryDto);
    
    public List<YktLimitBatchReportForExport> getListForYktLimitBatchReportExport(YktLimitBatchReportQuery queryDto);
    
}
    
