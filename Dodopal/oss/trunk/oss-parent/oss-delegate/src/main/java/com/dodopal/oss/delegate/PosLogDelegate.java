package com.dodopal.oss.delegate;

import java.util.List;

import com.dodopal.api.users.dto.PosLogDTO;
import com.dodopal.api.users.dto.PosLogQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface PosLogDelegate {
    
    /**
     * 查找POS操作日志  分页
     * @param findDto
     * @return
     */
    public DodopalResponse<DodopalDataPage<PosLogDTO>> findPogLogsByPage(PosLogQueryDTO dto);
    
    /**
     * 查找POS操作日志  导出
     * @param findDto
     * @return
     */
    public DodopalResponse<List<PosLogDTO>> findPogLogsByList(PosLogQueryDTO findDto);

}
