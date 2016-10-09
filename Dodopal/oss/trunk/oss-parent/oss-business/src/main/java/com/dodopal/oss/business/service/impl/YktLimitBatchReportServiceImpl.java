package com.dodopal.oss.business.service.impl;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.bean.YktLimitBatchReportForExport;
import com.dodopal.oss.business.bean.query.YktLimitBatchReportQuery;
import com.dodopal.oss.business.dao.YktLimitBatchReportMapper;
import com.dodopal.oss.business.service.YktLimitBatchReportService;

@Service
public class YktLimitBatchReportServiceImpl implements YktLimitBatchReportService {

	@Autowired
	private YktLimitBatchReportMapper yktLimitBatchReportMapper;

    @Override
    public DodopalResponse<DodopalDataPage<YktLimitBatchReportForExport>> findYktLimitBatchReportByPage(YktLimitBatchReportQuery queryDto) {
        
        DodopalResponse<DodopalDataPage<YktLimitBatchReportForExport>> response = new DodopalResponse<DodopalDataPage<YktLimitBatchReportForExport>>();
        response.setCode(ResponseCode.SUCCESS);
        
        List<YktLimitBatchReportForExport> result = yktLimitBatchReportMapper.findYktLimitBatchReportByPage(queryDto);
        
        this.formateList(result);
        DodopalDataPage<YktLimitBatchReportForExport> pages = new DodopalDataPage<YktLimitBatchReportForExport>(queryDto.getPage(), result);
        response.setResponseEntity(pages);
        
        return response;
        
    }

    @Override
    public DodopalResponse<List<YktLimitBatchReportForExport>> getYktLimitBatchReportForExport(YktLimitBatchReportQuery queryDto) {
        
        DodopalResponse<List<YktLimitBatchReportForExport>> response = new DodopalResponse<List<YktLimitBatchReportForExport>>();
        response.setCode(ResponseCode.SUCCESS);
        
        int count = yktLimitBatchReportMapper.getCountForYktLimitBatchReportExport(queryDto);
        
        if (count > ExcelUtil.EXPORT_MAX_COUNT) {
            response.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
            return response;
        }
        
        List<YktLimitBatchReportForExport> result = yktLimitBatchReportMapper.getListForYktLimitBatchReportExport(queryDto);
        this.formateList(result);
        response.setResponseEntity(result);
        
        return response;
        
    }
   
    private void formateList(List<YktLimitBatchReportForExport> dtoList){
        
        DecimalFormat df = new DecimalFormat("0.00");
        
        for (YktLimitBatchReportForExport dto:dtoList) {
            dto.setFinancialPayAmt(dto.getFinancialPayAmt() == null ? null : df.format(Double.valueOf(dto.getFinancialPayAmt())));
            dto.setFinancialPlayFee(dto.getFinancialPlayFee() == null ? null : df.format(Double.valueOf(dto.getFinancialPlayFee())));
            dto.setYktAddLimit(dto.getYktAddLimit() == null ? null : df.format(Double.valueOf(dto.getYktAddLimit())));
            dto.setYktUnaddLimit(dto.getYktUnaddLimit() == null ? null : df.format(Double.valueOf(dto.getYktUnaddLimit())));
        }
    }

}
