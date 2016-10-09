package com.dodopal.api.users.facade;

import java.util.List;

import com.dodopal.api.users.dto.PosLogDTO;
import com.dodopal.api.users.dto.PosLogQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

/**
 * POS操作日志接口
 * @author
 *
 */
public interface PosLogFacade {
	
  /**
   * Pos操作日志查询
   * @param findDto 查询条件
   * @return
   */
	DodopalResponse<DodopalDataPage<PosLogDTO>> findPosLogList(PosLogQueryDTO findDto);
	
	/**
	 * Pos操作日志   导出
	 * @param findDto
	 * @return
	 */
    DodopalResponse<List<PosLogDTO>> findPogLogsByList(PosLogQueryDTO findDto) ;
    
     

}
