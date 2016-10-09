/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.product.delegate;

import com.dodopal.api.card.dto.BJDiscountDTO;
import com.dodopal.api.card.dto.ParameterList;
import com.dodopal.api.card.dto.SignParameter;
import com.dodopal.common.model.DodopalResponse;

/**
 * Created by lenovo on 2016/3/21.
 */
public interface CardV71Delegate {
    /**
     * @desription 签到
     * @param signParameter
     * @return
     */
    DodopalResponse<SignParameter> signin(SignParameter signParameter);
    /**
     * @desription 签退
     * @param signParameter
     * @return
     */
    DodopalResponse<SignParameter> signout(SignParameter signParameter);

    /**
     * @desription 参数下载
     * @param parameterList
     * @return
     */
    DodopalResponse<ParameterList> paraDownload(ParameterList parameterList);

    /**
     * 查询脱机消费优惠信息
     * @param bjDiscountDTO
     * @return
     */
    DodopalResponse<BJDiscountDTO> queryDiscountAmt(BJDiscountDTO bjDiscountDTO);
}
