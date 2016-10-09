package com.dodopal.oss.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.bean.PosLogBDBean;
import com.dodopal.oss.business.bean.query.PosLogBDFindBean;

public interface PosLogService {
    
    /**
     * 查找POS操作日志 - 分页查找
     * @param queryDto
     * @return
     */
    DodopalResponse<DodopalDataPage<PosLogBDBean>> findPosLogByPage(PosLogBDFindBean queryDto);
    
    /**
     * 查找POS操作日志  导出
     * @param logQueryDTO
     * @return
     */
    DodopalResponse<List<PosLogBDBean>> findPogLogsByList(PosLogBDFindBean logQueryDTO);

}
