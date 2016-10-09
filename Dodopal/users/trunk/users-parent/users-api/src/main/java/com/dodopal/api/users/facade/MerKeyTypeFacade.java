package com.dodopal.api.users.facade;

import com.dodopal.api.users.dto.MerKeyTypeDTO;
import com.dodopal.common.enums.MerKeyTypeMD5PwdEnum;
import com.dodopal.common.model.DodopalResponse;

public interface MerKeyTypeFacade {
    /** 
     * @Title: findMerMD5PayPwd 
     * @Description: 签名秘钥查看
     * @param merKeyTypeDTO   merCode
     * @return    设定文件 
     * DodopalResponse<MerKeyTypeDTO>    返回类型 
     * @throws 
     */
   // DodopalResponse<MerKeyTypeDTO> findMerMD5PayPwd(MerKeyTypeDTO merKeyTypeDTO);

    /** 
     * @Title: findMerMD5BackPayPWD 
     * @Description: 签名秘钥，验签秘钥查看
     * @param merKeyTypeDTO    merCode
     * @return    设定文件 
     * DodopalResponse<MerKeyTypeDTO>    返回类型 
     * @throws 
     */
    DodopalResponse<MerKeyTypeDTO> findMerMD5PayPWDOrBackPayPWD(MerKeyTypeDTO merKeyTypeDTO,MerKeyTypeMD5PwdEnum keyTypeMD5PwdEnum);
    
    /** 
     * @Title: updateMerMD5PayPwd 
     * @Description: 签名秘钥更新 /验签秘钥更新
     * @param merKeyTypeDTO
     * @return    设定文件 
     * DodopalResponse<MerKeyTypeDTO>    返回类型 
     * @throws 
     */
    DodopalResponse<Boolean> updateMerMD5PayPwdOrBackPayPWD(MerKeyTypeDTO merKeyTypeDTO,String oldPWD,MerKeyTypeMD5PwdEnum keyTypeMD5PwdEnum);
     
    /** 
     * @Title: updateMerMD5BackPayPWD 
     * @Description: 验签秘钥更新
     * @param merKeyTypeDTO    设定文件 
     * void    返回类型 
     * @throws 
     */
   // DodopalResponse <Boolean> updateMerMD5BackPayPWD(MerKeyTypeDTO merKeyTypeDTO,String oldMerMD5BackPayPWD);

    }
