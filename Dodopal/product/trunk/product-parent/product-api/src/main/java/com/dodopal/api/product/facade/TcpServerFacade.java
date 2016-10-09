/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.api.product.facade;
import com.dodopal.api.card.dto.BJAccountConsumeDTO;
import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.api.card.dto.BJDiscountDTO;
import com.dodopal.api.card.dto.BJIntegralConsumeDTO;
import com.dodopal.api.card.dto.ReslutDataParameter;
import com.dodopal.api.card.dto.SignParameter;
import com.dodopal.api.card.dto.ParameterList;
import com.dodopal.common.model.DodopalResponse;

/**
 * Created by lenovo on 2016/1/25.
 */
public interface TcpServerFacade {
    
    /**
     *@description 签到接口，提供tcpservice调用
     * @param signParameter
     * @return
     */
    public DodopalResponse<SignParameter> signin(SignParameter signParameter);

    /**
     *@description 签退接口，提供tcpservice调用
     * @param signParameter
     * @return
     */
    public DodopalResponse<SignParameter> signout(SignParameter signParameter);

    /**
     *@description 参数下载接口，提供tcpservice调用
     * @param parameterList
     * @return
     */
    public DodopalResponse<ParameterList> paraDownload(ParameterList parameterList);
    
    /**
     * 查询脱机消费优惠信息
     * @param bjDiscountDTO
     * @return
     */
    public DodopalResponse<BJDiscountDTO> queryDiscountAmt(BJDiscountDTO bjDiscountDTO);
    
    /******************************  北京充值业务流程接口(V71请求) start   ******************************/
    /**
     * 充值验卡生单（V71充值请求）
     * @param crdDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> checkCardByV71(BJCrdSysOrderDTO crdDTO);
    
    /**
     * 充值申请接口（V71充值请求）
     * @param crdDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> applyRechargeByV71(BJCrdSysOrderDTO crdDTO);
    
    /**
     * 充值结果上传（V71充值请求）
     * @param crdDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> uploadRechargeResultByV71(BJCrdSysOrderDTO crdDTO);
    
    /******************************  北京充值业务流程接口(V71请求) end   ********************************/
    
    /******************************  北京V71消费业务流程接口 start   ******************************/
    /**
     * 消费生单验卡（V71联机消费）
     * @param crdDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> createOrderCheckCardOnlineByV71(BJCrdSysOrderDTO crdDTO);
    
    /**
     * 消费申请消费密钥（V71联机消费）
     * @param crdDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> applyConsumeByOnlineV71(BJCrdSysOrderDTO crdDTO);
    
    /**
     * 消费结果上传（V71联机消费）
     * @param crdDTO
     * @return
     */
    public DodopalResponse<BJCrdSysOrderDTO> uploadConsumeResultByOnlineV71(BJCrdSysOrderDTO crdDTO);
    
    /**
     * 消费结果上传（V71脱机消费）
     * @param crdDTO
     * @return
     */
    public DodopalResponse<ReslutDataParameter> uploadConsumeResultByOfflineV71(ReslutDataParameter crdDTO);
    
    /******************************  北京V71消费业务流程接口 end   ********************************/
    
    
    /******************************  北京通卡“账户消费”业务流程接口 start   ******************************/
    
    /**
     * 通卡账户消费申请/查询
     * @param bjAccountConsumeDTO
     * @return
     */
    public DodopalResponse<BJAccountConsumeDTO> applyYktAcountConsume(BJAccountConsumeDTO bjAccountConsumeDTO);
    
    /**
     * 通卡账户消费结果上送
     * @param bjAccountConsumeDTO
     * @return
     */
    public DodopalResponse<BJAccountConsumeDTO> uploadYktAcountConsume(BJAccountConsumeDTO bjAccountConsumeDTO);
    
    /**
     * 通卡账户消费撤销申请
     * @param bjAccountConsumeDTO
     * @return
     */
    public DodopalResponse<BJAccountConsumeDTO> cancelYktAcountConsume(BJAccountConsumeDTO bjAccountConsumeDTO);
    
    
    /******************************  北京通卡“账户消费”业务流程接口 end   ******************************/
    
    
    /******************************  北京通卡“积分消费”业务流程接口 start   ******************************/
    
    /**
     * 通卡积分消费申请/查询
     * @param bjIntegralConsumeDTO
     * @return
     */
    public DodopalResponse<BJIntegralConsumeDTO> applyYktPointConsume(BJIntegralConsumeDTO bjIntegralConsumeDTO);
    
    /**
     * 通卡积分消费结果上送
     * @param bjIntegralConsumeDTO
     * @return
     */
    public DodopalResponse<BJIntegralConsumeDTO> uploadYktPointConsume(BJIntegralConsumeDTO bjIntegralConsumeDTO);
    
    /**
     * 通卡积分消费撤销申请
     * @param bjIntegralConsumeDTO
     * @return
     */
    public DodopalResponse<BJIntegralConsumeDTO> cancelYktPointConsume(BJIntegralConsumeDTO bjIntegralConsumeDTO);
    
    /******************************  北京通卡“积分消费”业务流程接口 end   ******************************/
    
}
