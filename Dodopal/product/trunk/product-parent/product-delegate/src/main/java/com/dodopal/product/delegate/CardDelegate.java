package com.dodopal.product.delegate;

import java.util.List;

import com.dodopal.api.users.dto.MerUserCardBDDTO;
import com.dodopal.api.users.dto.MerUserCardBDFindDTO;
import com.dodopal.common.model.DodopalResponse;

public interface CardDelegate {

    /**
     * 用户绑卡
     * @param cardBDDTO 
     * @return DodopalResponse<MerUserCardBDDTO>
     */
    DodopalResponse<MerUserCardBDDTO> saveMerUserCardBD(MerUserCardBDDTO cardBDDTO);
    
    //用户解绑
    /**
     * 卡片管理，用户解绑
     * @param userId 用户号（userId）
     * @param ids  选择的卡片记录 
     * @param resource 来源（记日志使用）
     * @return  DodopalResponse<String>
     */
    DodopalResponse<String> unBundlingCard(String userId,String operName,List<String> ids,String source);
    
    //查询列表
    /**
     * 查询用户卡片列表
     * @param cardBDFindDTO
     * @return
     */
    DodopalResponse<List<MerUserCardBDDTO>> findMerUserCardBD(MerUserCardBDFindDTO cardBDFindDTO);
}
