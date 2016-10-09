package com.dodopal.portal.delegate;

import java.util.List;

import com.dodopal.api.product.dto.ProductYKTDTO;
import com.dodopal.common.model.DodopalResponse;

public interface ProductYktDelegate {
	  /** 
     * @author  Dingkuiyuan@dodopal.com 
     * @date 创建时间：2015年11月2日 下午1:05:54 
     * @return  根据城市code 
     */
   public DodopalResponse<ProductYKTDTO> getYktInfoByBusinessCityCode(String cityCode);

   /** 
	  * @author  Dingkuiyuan@dodopal.com 
	  * @date 创建时间：2015年12月14日 下午4:17:19 
	  * @version 1.0 
	  * @parameter  
	  * @since  获取所有的通卡公司业务费率信息
	  * @return  
	  */
   public DodopalResponse<List<ProductYKTDTO>> getAllYktBusinessRateList();
}
