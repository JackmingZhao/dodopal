package com.dodopal.api.card.facade;

import com.dodopal.api.card.dto.BJDiscountDTO;
import com.dodopal.api.card.dto.ParameterList;
import com.dodopal.api.card.dto.PosSignInOutDTO;
import com.dodopal.api.card.dto.ReslutDataParameter;
import com.dodopal.api.card.dto.SignParameter;
import com.dodopal.common.model.DodopalResponse;

/**
 * 签到签退接口
 */
public interface SignInSignOutCardFacade {

    /**
     * 签到
     * @param signParameter
     * @return
     */
    public DodopalResponse<SignParameter> SignIn(SignParameter signParameter);
    
    
    /**
     * V61签到签退
     * @param posSignInOutDTO
     * @return
     */
    public DodopalResponse<PosSignInOutDTO> posSignInOutForV61(PosSignInOutDTO posSignInOutDTO);

    /**
     * 参数下载
     * @param parameterList
     * @return
     */
    public DodopalResponse<ParameterList> downloadParameter(ParameterList parameterList);

    /**
     * 批量结果上传
     * @param parameter
     * @return
     */
    public DodopalResponse<ReslutDataParameter> batchUploadResult(ReslutDataParameter parameter);

    /**
     * 签退
     * @param signParameter
     * @return
     */
    public DodopalResponse<SignParameter> SignOut(SignParameter signParameter);
    
    /**
     * 查询脱机消费优惠信息
     * @param signParameter
     * @return
     */
    public DodopalResponse<BJDiscountDTO> queryDiscountAmt(BJDiscountDTO bjDiscountDTO);

}
