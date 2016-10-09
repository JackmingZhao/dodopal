/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.merdevice.delegate.impl;

import com.dodopal.api.card.dto.*;
import com.dodopal.api.product.facade.TcpServerFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.merdevice.delegate.BaseDelegate;
import com.dodopal.merdevice.delegate.DelegateConstant.DelegateConstant;
import com.dodopal.merdevice.delegate.TcpServerDelegate;
import org.springframework.stereotype.Service;

/**
 * Created by lenovo on 2016/1/25.
 */
@Service
public class TcpServerDelegateImpl extends BaseDelegate implements TcpServerDelegate {
    /**
     * description 调用产品库签到接口
     * @param signParameter
     * @return
     */
    @Override
    public DodopalResponse<SignParameter> signin(SignParameter signParameter) {
        TcpServerFacade tcpServerFacade = getFacade(TcpServerFacade.class, DelegateConstant.FACADE_PRODUCT_URL );
        return tcpServerFacade.signin(signParameter);
    }
    /**
     * description 调用产品库签退接口
     * @param signParameter
     * @return
     */
    @Override
    public DodopalResponse<SignParameter> signout(SignParameter signParameter) {
        TcpServerFacade tcpServerFacade = getFacade(TcpServerFacade.class, DelegateConstant.FACADE_PRODUCT_URL );
        return tcpServerFacade.signout(signParameter);
    }

    /**
     * @description V71 参数下载
     * @param parameterList
     * @return
     */
    @Override
    public DodopalResponse<ParameterList> paraDownload(ParameterList parameterList) {
        TcpServerFacade tcpServerFacade = getFacade(TcpServerFacade.class, DelegateConstant.FACADE_PRODUCT_URL );
        return tcpServerFacade.paraDownload(parameterList);
    }

    /**
     * @description message2011验卡接口
     * @param bjCrdSysOrderDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> V71Message2011(BJCrdSysOrderDTO bjCrdSysOrderDTO){
        TcpServerFacade tcpServerFacade = getFacade(TcpServerFacade.class, DelegateConstant.FACADE_PRODUCT_URL );
        return tcpServerFacade.checkCardByV71(bjCrdSysOrderDTO);
    }

    /**
     * @description message2101申请充值密钥接口
     * @param bjCrdSysOrderDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> V71Message2101(BJCrdSysOrderDTO bjCrdSysOrderDTO){
        TcpServerFacade tcpServerFacade = getFacade(TcpServerFacade.class, DelegateConstant.FACADE_PRODUCT_URL );
        return tcpServerFacade.applyRechargeByV71(bjCrdSysOrderDTO);
    }
    /**
     * @description message2201结果上传接口
     * @param bjCrdSysOrderDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> V71Message2201(BJCrdSysOrderDTO bjCrdSysOrderDTO){
        TcpServerFacade tcpServerFacade = getFacade(TcpServerFacade.class, DelegateConstant.FACADE_PRODUCT_URL );
        return tcpServerFacade.uploadRechargeResultByV71(bjCrdSysOrderDTO);
    }
    /**
     * @description message3201消费验卡
     * @param reslutDataParameter
     * @return
     */
    public DodopalResponse<ReslutDataParameter> V71Message3201(ReslutDataParameter reslutDataParameter){
        TcpServerFacade tcpServerFacade = getFacade(TcpServerFacade.class, DelegateConstant.FACADE_PRODUCT_URL );
        return tcpServerFacade.uploadConsumeResultByOfflineV71(reslutDataParameter);
    }
    /**
     * @description message3011消费验卡
     * @param bjCrdSysOrderDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> V71Message3011(BJCrdSysOrderDTO bjCrdSysOrderDTO){
        TcpServerFacade tcpServerFacade = getFacade(TcpServerFacade.class, DelegateConstant.FACADE_PRODUCT_URL );
        return tcpServerFacade.createOrderCheckCardOnlineByV71(bjCrdSysOrderDTO);
    }
    /**
     * @description message3101消费验卡
     * @param bjCrdSysOrderDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> V71Message3101(BJCrdSysOrderDTO bjCrdSysOrderDTO){
        TcpServerFacade tcpServerFacade = getFacade(TcpServerFacade.class, DelegateConstant.FACADE_PRODUCT_URL );
        return tcpServerFacade.applyConsumeByOnlineV71(bjCrdSysOrderDTO);
    }

    /**
     * @description message320101消费上传
     * @param bjCrdSysOrderDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> V71Message320101(BJCrdSysOrderDTO bjCrdSysOrderDTO){
        TcpServerFacade tcpServerFacade = getFacade(TcpServerFacade.class, DelegateConstant.FACADE_PRODUCT_URL );
        return tcpServerFacade.uploadConsumeResultByOnlineV71(bjCrdSysOrderDTO);
    }
    /**
     * @description message320101消费上传
     * @param bjDiscountDTO
     * @return
     */
    @Override
    public DodopalResponse<BJDiscountDTO> discoutnQuery3301(BJDiscountDTO bjDiscountDTO) {
        TcpServerFacade tcpServerFacade = getFacade(TcpServerFacade.class, DelegateConstant.FACADE_PRODUCT_URL );
        return tcpServerFacade.queryDiscountAmt(bjDiscountDTO);
    }
    /**
     * @description message3401 账户消费申请
     * @param bjAccountConsumeDTO
     * @return
     */
    @Override
    public DodopalResponse<BJAccountConsumeDTO> accountConsume(BJAccountConsumeDTO bjAccountConsumeDTO) {
        TcpServerFacade tcpServerFacade = getFacade(TcpServerFacade.class, DelegateConstant.FACADE_PRODUCT_URL );
        return tcpServerFacade.applyYktAcountConsume(bjAccountConsumeDTO);
    }
    /**
     * @description message3401 账户消费撤销
     * @param bjAccountConsumeDTO
     * @return
     */
    @Override
    public DodopalResponse<BJAccountConsumeDTO> accountConsumeRevoke(BJAccountConsumeDTO bjAccountConsumeDTO) {
        TcpServerFacade tcpServerFacade = getFacade(TcpServerFacade.class, DelegateConstant.FACADE_PRODUCT_URL );
        return tcpServerFacade.cancelYktAcountConsume(bjAccountConsumeDTO);
    }

    @Override
    public DodopalResponse<BJAccountConsumeDTO> accountConsumeReslut(BJAccountConsumeDTO bjAccountConsumeDTO) {
        TcpServerFacade tcpServerFacade = getFacade(TcpServerFacade.class, DelegateConstant.FACADE_PRODUCT_URL );
        return tcpServerFacade.uploadYktAcountConsume(bjAccountConsumeDTO);
    }

    @Override
    public DodopalResponse<BJIntegralConsumeDTO> integralConsume(BJIntegralConsumeDTO bjIntegralConsumeDTO) {
        return null;
    }
}
