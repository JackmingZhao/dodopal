package com.dodopal.api.users.facade;

import java.util.List;

import com.dodopal.api.users.dto.ProfitCollectDTO;
import com.dodopal.api.users.dto.ProfitDetailsDTO;
import com.dodopal.api.users.dto.query.ProfitQueryDTO;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

/** 
  * @author  Dingkuiyuan@dodopal.com 
  * @date 创建时间：2015年10月26日 下午2:00:45 
  * @version 1.0 
  * 分润管理查询
  */
public interface ProfitFacade {
	 /** 
	  * @author  Dingkuiyuan@dodopal.com 
	  * @date 创建时间：2015年10月26日 下午2:00:41 
	  * @version 1.0 
	  * @parameter  
	  * @since  
	  * @return  
	  */
	DodopalResponse<DodopalDataPage<ProfitCollectDTO>> findProfitCollectListByPage(ProfitQueryDTO queryDTO,SourceEnum source);

	DodopalResponse<DodopalDataPage<ProfitDetailsDTO>> findProfitDetailsListByPage(ProfitQueryDTO queryDTO,SourceEnum source);

	DodopalResponse<List<ProfitCollectDTO>> findProfitCollect(ProfitQueryDTO query,SourceEnum source);
}
