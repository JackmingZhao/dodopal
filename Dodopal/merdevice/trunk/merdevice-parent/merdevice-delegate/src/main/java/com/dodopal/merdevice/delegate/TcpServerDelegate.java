/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.merdevice.delegate;

import com.dodopal.api.card.dto.*;
import com.dodopal.common.model.DodopalResponse;

/**
 * Created by lenovo on 2016/1/25.
 */
public interface TcpServerDelegate {
    /**
     * description 调用产品库签到接口
     * @param signParameter
     * @return
     */
    public DodopalResponse<SignParameter> signin(SignParameter signParameter);
    /**
     * description 调用产品库签退接口
     * @param signParameter
     * @return
     */
    public DodopalResponse<SignParameter> signout(SignParameter signParameter);
    /**
     * description 调用产品库参数下载接口
     * @param parameterList
     * @return
     */
    public DodopalResponse<ParameterList> paraDownload(ParameterList parameterList);
    /**
     * description 调用产品库2011接口
     * @param bjCrdSysOrderDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> V71Message2011(BJCrdSysOrderDTO bjCrdSysOrderDTO);
    /**
     * description 调用产品库2101接口
     * @param bjCrdSysOrderDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> V71Message2101(BJCrdSysOrderDTO bjCrdSysOrderDTO);
    /**
     * description 调用产品库2201接口
     * @param bjCrdSysOrderDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> V71Message2201(BJCrdSysOrderDTO bjCrdSysOrderDTO);
     /**
     * description 调用产品库3201接口
     * @param reslutDataParameter
     * @return
     */
    public DodopalResponse<ReslutDataParameter> V71Message3201(ReslutDataParameter reslutDataParameter);
    /**
     * description 调用产品库3011接口
     * @param bjCrdSysOrderDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> V71Message3011(BJCrdSysOrderDTO bjCrdSysOrderDTO);
    /**
     * description 调用产品库3101接口
     * @param bjCrdSysOrderDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> V71Message3101(BJCrdSysOrderDTO bjCrdSysOrderDTO);
    /**
     * description 调用产品库320101接口
     * @param bjCrdSysOrderDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> V71Message320101(BJCrdSysOrderDTO bjCrdSysOrderDTO);

    /**
     * description 调用产品库3301接口
     * @param bjDiscountDTO
     * @return
     */
    public DodopalResponse<BJDiscountDTO> discoutnQuery3301(BJDiscountDTO bjDiscountDTO);

    /**
     * description 调用产品库3401接口
     * @param bjAccountConsumeDTO
     * @return
     */
    public DodopalResponse<BJAccountConsumeDTO> accountConsume(BJAccountConsumeDTO bjAccountConsumeDTO);

    /**
     * description 调用产品库3411接口
     * @param bjAccountConsumeDTO
     * @return
     */
    public DodopalResponse<BJAccountConsumeDTO> accountConsumeRevoke(BJAccountConsumeDTO bjAccountConsumeDTO);

    /**
     * description 调用产品库3421接口
     * @param bjAccountConsumeDTO
     * @return
     */
    public DodopalResponse<BJAccountConsumeDTO> accountConsumeReslut(BJAccountConsumeDTO bjAccountConsumeDTO);

    /**
     * description 调用产品库3501接口
     * @param bjIntegralConsumeDTO
     * @return
     */
    public DodopalResponse<BJIntegralConsumeDTO> integralConsume(BJIntegralConsumeDTO bjIntegralConsumeDTO);
}