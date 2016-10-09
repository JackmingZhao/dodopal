package com.dodopal.portal.business.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.account.dto.AccountFundDTO;
import com.dodopal.api.payment.dto.PayTraTransactionDTO;
import com.dodopal.api.payment.dto.PayWayDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.AccountFundBean;
import com.dodopal.portal.business.bean.PayWayBean;
import com.dodopal.portal.business.bean.TraTransactionBean;
import com.dodopal.portal.business.service.DdpService;
import com.dodopal.portal.delegate.DdpDelegate;

@Service
public class DdpServiceImpl implements DdpService {

    private final static Logger log = LoggerFactory.getLogger(DdpServiceImpl.class);
    @Autowired
    private DdpDelegate DdpDelegate;

    //更多支付方式
    public DodopalResponse<List<PayWayBean>> findPayWay(boolean ext, String merCode) {
        log.info("findPayWay this merCode:" + merCode + ",ext:" + ext);
        DodopalResponse<List<PayWayBean>> response = new DodopalResponse<List<PayWayBean>>();
        try {
            DodopalResponse<List<PayWayDTO>> payWayDTOList = DdpDelegate.findPayWay(ext, merCode);
            List<PayWayBean> payWayBeanList = new ArrayList<PayWayBean>();
            if (payWayDTOList.getResponseEntity() != null && payWayDTOList.getResponseEntity().size() > 0) {
                for (PayWayDTO payWayDTO : payWayDTOList.getResponseEntity()) {
                    PayWayBean payWayBean = new PayWayBean();
                    PropertyUtils.copyProperties(payWayBean, payWayDTO);
                    payWayBeanList.add(payWayBean);
                }
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(payWayBeanList);

            }
        }
        catch (Exception e) {
            log.error("DdpServiceImpl findPayWay throws: " + e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }

        return response;
    }

    //用户常用支付方式
    public DodopalResponse<List<PayWayBean>> findCommonPayWay(boolean ext, String userCode) {
        log.info("DdpServiceImpl findCommonPayWay this userCode:" + userCode + ",ext:" + ext);
        DodopalResponse<List<PayWayBean>> response = new DodopalResponse<List<PayWayBean>>();
        try {
            DodopalResponse<List<PayWayDTO>> payWayDTOList = DdpDelegate.findCommonPayWay(ext, userCode);
            List<PayWayBean> payWayBeanList = new ArrayList<PayWayBean>();
            if (payWayDTOList.getResponseEntity() != null && payWayDTOList.getResponseEntity().size() > 0) {
                for (PayWayDTO payWayDTO : payWayDTOList.getResponseEntity()) {
                    PayWayBean payWayBean = new PayWayBean();
                    PropertyUtils.copyProperties(payWayBean, payWayDTO);
                    payWayBeanList.add(payWayBean);
                }
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(payWayBeanList);

            }
        }
        catch (Exception e) {
            log.error("DdpServiceImpl findCommonPayWay throws: " + e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }

        return response;

    }

    //查询门户首页 可用余额
    public DodopalResponse<AccountFundBean> findAccountBalance(String aType, String custNum) {
        log.info("DdpServiceImpl findAccountBalance 查询门户首页可用余额 this 个人or企业   aType:" + aType + ",用户号or商户号  custNum:" + custNum);
        DodopalResponse<AccountFundBean> response = new DodopalResponse<AccountFundBean>();
        try {
            DodopalResponse<AccountFundDTO> rtResponse = DdpDelegate.findAccountBalance(aType, custNum);
            if (rtResponse.getResponseEntity() != null) {
                AccountFundBean accountFundBean = new AccountFundBean();
                PropertyUtils.copyProperties(accountFundBean, rtResponse.getResponseEntity());
                response.setCode(rtResponse.getCode());
                response.setResponseEntity(accountFundBean);
            }

        }
        catch (Exception e) {
            log.error("DdpServiceImpl findAccountBalance 查询门户首页可用余额 throws e:"+e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    //根据 个人or企业 、 用户号or商户号，查询门户首页交易记录 最新的十条数据
    public DodopalResponse<List<TraTransactionBean>> findTraTransactionByCode(String ext, String merOrUserCode) {
        log.info("DdpServiceImpl findTraTransactionByCode 查询门户首页交易记录 最新的十条数据 this 个人or企业   ext:" + ext + ",用户号or商户号  merOrUserCode:" + merOrUserCode);
        DodopalResponse<List<TraTransactionBean>> response = new DodopalResponse<List<TraTransactionBean>>();
        List<TraTransactionBean> traBeanList = new ArrayList<TraTransactionBean>();
        DecimalFormat df = new DecimalFormat("0.00");   
       
        try {
            DodopalResponse<List<PayTraTransactionDTO>> rtResponse = DdpDelegate.findTraTransactionByCode(ext, merOrUserCode);
            if (rtResponse.getCode().equals(ResponseCode.SUCCESS) && rtResponse.getResponseEntity() != null) {
               String amountMoney = "0.00";
               String realTranMoney = "0.00";
                List<PayTraTransactionDTO> traDTOList = rtResponse.getResponseEntity();
                for (PayTraTransactionDTO traDTO : traDTOList) {
                    TraTransactionBean traBean = new TraTransactionBean();
                    //PropertyUtils.copyProperties(traBean, traDTO);
                    //traBean.setMerOrUserCode(traDTO.getMerOrUserCode());
                    
                    //交易流水号
                    traBean.setTranCode(traDTO.getTranCode());
                    //交易名称
                    traBean.setTranName(traDTO.getTranName());
                    //交易外部状态
                    traBean.setTranOutStatus(traDTO.getTranOutStatus());
                    //交易内部状态
                    traBean.setTranInStatus(traDTO.getTranInStatus());
                    //将交易金额格式化为两位小数（0.00）并处理成单位为（元），数据库存的是（分）
                    amountMoney = df.format((double)traDTO.getAmountMoney()/100);
                    //将实际交易金额格式化为两位小数（0.00）并处理成单位为（元），数据库存的是（分）
                    realTranMoney = df.format((double)traDTO.getRealTranMoney()/100);
                    
                    traBean.setAmountMoney(amountMoney);
                    traBean.setTranType(traDTO.getTranType());
                    traBean.setOrderNumber(traDTO.getOrderNumber());
                    traBean.setCommodity(traDTO.getCommodity());
                    traBean.setBusinessType(traDTO.getBusinessType());
                    traBean.setOrderDate(traDTO.getOrderDate());
                    traBean.setPayType(traDTO.getPayType());
                    traBean.setPayWay(traDTO.getPayWay());
                    traBean.setRealTranMoney(realTranMoney);
                    traBean.setCreateDate(traDTO.getCreateDate());
                    traBean.setCreateUser(traDTO.getCreateUser());
                    traBean.setId(traDTO.getId());
                    traBeanList.add(traBean);
                }
                response.setCode(rtResponse.getCode());
                response.setResponseEntity(traBeanList);
            }else{
                response.setCode(ResponseCode.SYSTEM_ERROR);
            }
            
        }
        catch (Exception e) {
            log.error("DdpServiceImpl findTraTransactionByCode 查询门户首页交易记录 最新的十条数据 throws e:"+e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            // TODO: handle exception
        }
        return response;
    }

}
