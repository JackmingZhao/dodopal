package com.dodopal.product.delegate;

import com.dodopal.api.users.dto.MerKeyTypeDTO;
import com.dodopal.common.enums.MerKeyTypeMD5PwdEnum;
import com.dodopal.common.model.DodopalResponse;

public interface MerKeyTypeDelegate {
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年4月26日 下午7:31:27 
      * @version 1.0 
      * @parameter  
      * @描述 获取MD5加密验签秘钥
      * @return  
      */
    DodopalResponse<MerKeyTypeDTO> findMerMD5PayPWDOrBackPayPWD(MerKeyTypeDTO merKeyTypeDTO,MerKeyTypeMD5PwdEnum keyTypeMD5PwdEnum);
}
