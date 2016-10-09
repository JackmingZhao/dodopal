/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.product.business.facadeImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.api.card.dto.BJAccountConsumeDTO;
import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.api.card.dto.BJDiscountDTO;
import com.dodopal.api.card.dto.BJIntegralConsumeDTO;
import com.dodopal.api.card.dto.ParameterList;
import com.dodopal.api.card.dto.ReslutDataParameter;
import com.dodopal.api.card.dto.SignParameter;
import com.dodopal.api.product.facade.TcpServerFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.service.BJIcdcAcountPointConsumeService;
import com.dodopal.product.business.service.BJIcdcPurchaseService;
import com.dodopal.product.business.service.BJIcdcRechargeService;
import com.dodopal.product.business.service.CardV71Service;

/**
 * Created by lenovo on 2016/1/25.
 */
@Service("tcpServerFacade")
public class TcpServerFacadeImpl implements TcpServerFacade {
    
    private final static Logger log = LoggerFactory.getLogger(TcpServerFacadeImpl.class);
    
    @Autowired
    BJIcdcRechargeService bjIcdcRechargeService;
    @Autowired
    BJIcdcPurchaseService bjIcdcPurchaseService;
    @Autowired
    BJIcdcAcountPointConsumeService bjIcdcAcountPointConsumeService;
    @Autowired
    CardV71Service cardV71Service;

    
    /******************************  北京城市一卡通充值业务流程接口(V71请求) start   ******************************/

    /**
     * 充值验卡生单(V71请求)
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> checkCardByV71(BJCrdSysOrderDTO crdDTO) {
        DodopalResponse<BJCrdSysOrderDTO> resultResponse = bjIcdcRechargeService.createOrderCheckCardByV71(crdDTO);
        
        if (!ResponseCode.SUCCESS.equals(resultResponse.getCode()) && log.isErrorEnabled()) {
            log.error("北京V71充值验卡生单接口：响应码："+resultResponse.getCode()+"响应消息："+resultResponse.getMessage());
        } 
        if (log.isInfoEnabled()) {
            log.info("北京V71充值验卡生单接口,:响应码:" + resultResponse.getCode() + ",完整响应体:" + JSONObject.toJSONString(resultResponse));
        }
        return resultResponse;
    }

    /**
     * 充值申请接口(V71请求)
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> applyRechargeByV71(BJCrdSysOrderDTO crdDTO) {
        DodopalResponse<BJCrdSysOrderDTO> resultResponse = bjIcdcRechargeService.applyRechargeByV71(crdDTO);
        
        if (!ResponseCode.SUCCESS.equals(resultResponse.getCode()) && log.isErrorEnabled()) {
            log.error("北京V71充值申请接口：响应码："+resultResponse.getCode()+"响应消息："+resultResponse.getMessage());
        }
        if (log.isInfoEnabled()) {
            log.info("北京V71充值申请接口:响应码:" + resultResponse.getCode() + ",完整响应体:" + JSONObject.toJSONString(resultResponse) + ".");
        }
        return resultResponse;
    }

    /**
     * 充值结果上传(V71请求)
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> uploadRechargeResultByV71(BJCrdSysOrderDTO crdDTO) {
        DodopalResponse<BJCrdSysOrderDTO> resultResponse = bjIcdcRechargeService.uploadRechargeResultByV71(crdDTO);
        
        if (!ResponseCode.SUCCESS.equals(resultResponse.getCode()) && log.isErrorEnabled()) {
            log.error("北京V71充值结果上传接口：响应码："+resultResponse.getCode()+"响应消息："+resultResponse.getMessage());
        }
        if (log.isInfoEnabled()) {
            log.info("北京V71充值结果上传接口:响应码:" + resultResponse.getCode() + ",完整响应体:" + JSONObject.toJSONString(resultResponse) + ".");
        }
        return resultResponse;
    }

    /******************************  北京城市一卡通充值业务流程接口(V71请求) end   ******************************/
    
    /******************************  北京城市一卡通消费业务流程接口(V71联机消费) start   ******************************/
    
    /**
     * 消费生单验卡（V71联机消费）
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> createOrderCheckCardOnlineByV71(BJCrdSysOrderDTO crdDTO) {
        DodopalResponse<BJCrdSysOrderDTO> resultResponse = bjIcdcPurchaseService.checkCardCreateOrder(crdDTO, "V71");
        
        if (!ResponseCode.SUCCESS.equals(resultResponse.getCode()) && log.isErrorEnabled()) {
            log.error("北京V71消费验卡生单接口：响应码："+resultResponse.getCode()+"响应消息："+resultResponse.getMessage());
        } 
        if (log.isInfoEnabled()) {
            log.info("北京V71消费验卡生单接口,:响应码:" + resultResponse.getCode() + ",完整响应体:" + JSONObject.toJSONString(resultResponse));
        }

        return resultResponse;
    }

    /**
     * 消费申请消费密钥（V71联机消费）
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> applyConsumeByOnlineV71(BJCrdSysOrderDTO crdDTO) {
        DodopalResponse<BJCrdSysOrderDTO> resultResponse = bjIcdcPurchaseService.applyConsumeByOnlineV71(crdDTO);
        
        if (!ResponseCode.SUCCESS.equals(resultResponse.getCode()) && log.isErrorEnabled()) {
            log.error("北京V71消费申请消费密钥接口：响应码："+resultResponse.getCode()+"响应消息："+resultResponse.getMessage());
        } 
        if (log.isInfoEnabled()) {
            log.info("北京V71消费申请消费密钥接口,:响应码:" + resultResponse.getCode() + ",完整响应体:" + JSONObject.toJSONString(resultResponse));
        }
        
        return resultResponse;
    }

    /**
     * 消费结果上传（V71联机消费）
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> uploadConsumeResultByOnlineV71(BJCrdSysOrderDTO crdDTO) {
        DodopalResponse<BJCrdSysOrderDTO> resultResponse = bjIcdcPurchaseService.uploadConsumeResultByOnlineV71(crdDTO);
        
        if (!ResponseCode.SUCCESS.equals(resultResponse.getCode()) && log.isErrorEnabled()) {
            log.error("北京V71消费结果上传接口：响应码："+resultResponse.getCode()+"响应消息："+resultResponse.getMessage());
        } 
        if (log.isInfoEnabled()) {
            log.info("北京V71消费结果上传接口,:响应码:" + resultResponse.getCode() + ",完整响应体:" + JSONObject.toJSONString(resultResponse));
        }
        
        return resultResponse;
    }
    /******************************  北京城市一卡通消费业务流程接口(V71联机消费) end   ******************************/
    
    /******************************  北京城市一卡通消费业务流程接口(V71脱机消费) start   ******************************/
    
    /**
     * 消费结果上传（V71脱机消费）
     */
    @Override
    public DodopalResponse<ReslutDataParameter> uploadConsumeResultByOfflineV71(ReslutDataParameter crdDTO) {
        DodopalResponse<ReslutDataParameter> resultResponse = bjIcdcPurchaseService.uploadConsumeResultByOfflineV71(crdDTO);
        return resultResponse;
    }
    /******************************  北京城市一卡通消费业务流程接口(V71脱机消费) end   ******************************/

    /**
     *@description 签到接口，提供tcpservice调用
     * @param signParameter
     * @return
     */
    @Override
    public DodopalResponse<SignParameter> signin(SignParameter signParameter) {
        return cardV71Service.signin(signParameter);
    }
    /**
     *@description 签到接口，提供tcpservice调用
     * @param signParameter
     * @return
     */
    @Override
    public DodopalResponse<SignParameter> signout(SignParameter signParameter) {
        return cardV71Service.signout(signParameter);
    }

    /**
     *@description 参数下载接口，提供tcpservice调用
     * @param parameterList
     * @return
     */
    @Override
    public DodopalResponse<ParameterList> paraDownload(ParameterList parameterList) {
        return cardV71Service.paraDownload(parameterList);
    }

    /**
     * 查询脱机消费优惠信息
     * @param bjDiscountDTO
     * @return
     */
    @Override
    public DodopalResponse<BJDiscountDTO> queryDiscountAmt(BJDiscountDTO bjDiscountDTO) {
        return cardV71Service.queryDiscountAmt(bjDiscountDTO);
    }

    /******************************  北京通卡“账户消费”业务流程接口 start   ******************************/
    /**
     * 通卡账户消费申请
     * @param bjIntegralConsumeDTO
     * @return
     */
    @Override
    public DodopalResponse<BJAccountConsumeDTO> applyYktAcountConsume(BJAccountConsumeDTO bjAccountConsumeDTO) {
        return bjIcdcAcountPointConsumeService.applyYktAcountConsume(bjAccountConsumeDTO);
    }
    
    /**
     * 通卡账户消费结果上送
     * @param bjIntegralConsumeDTO
     * @return
     */
    @Override
    public DodopalResponse<BJAccountConsumeDTO> uploadYktAcountConsume(BJAccountConsumeDTO bjAccountConsumeDTO) {
        return bjIcdcAcountPointConsumeService.uploadYktAcountConsume(bjAccountConsumeDTO);
    }

    /**
     * 通卡账户消费撤销申请
     * @param bjIntegralConsumeDTO
     * @return
     */
    @Override
    public DodopalResponse<BJAccountConsumeDTO> cancelYktAcountConsume(BJAccountConsumeDTO bjAccountConsumeDTO) {
        return bjIcdcAcountPointConsumeService.cancelYktAcountConsume(bjAccountConsumeDTO);
    }
    /******************************  北京通卡“账户消费”业务流程接口 end   ******************************/
    
    /******************************  北京通卡“积分消费”业务流程接口 start   ******************************/
    /**
     * 通卡积分消费申请
     * @param bjIntegralConsumeDTO
     * @return
     */
    @Override
    public DodopalResponse<BJIntegralConsumeDTO> applyYktPointConsume(BJIntegralConsumeDTO bjIntegralConsumeDTO) {
        return bjIcdcAcountPointConsumeService.applyYktPointConsume(bjIntegralConsumeDTO);
    }

    /**
     * 通卡积分消费结果上送
     * @param bjIntegralConsumeDTO
     * @return
     */
    @Override
    public DodopalResponse<BJIntegralConsumeDTO> uploadYktPointConsume(BJIntegralConsumeDTO bjIntegralConsumeDTO) {
        return bjIcdcAcountPointConsumeService.uploadYktPointConsume(bjIntegralConsumeDTO);
    }

    /**
     * 通卡积分消费撤销申请
     * @param bjIntegralConsumeDTO
     * @return
     */
    @Override
    public DodopalResponse<BJIntegralConsumeDTO> cancelYktPointConsume(BJIntegralConsumeDTO bjIntegralConsumeDTO) {
        return bjIcdcAcountPointConsumeService.cancelYktPointConsume(bjIntegralConsumeDTO);
    }
    /******************************  北京通卡“积分消费”业务流程接口 end   ******************************/
}
