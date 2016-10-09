package com.dodopal.portal.business.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.account.dto.AccountFundDTO;
import com.dodopal.api.payment.dto.PayServiceRateDTO;
import com.dodopal.api.payment.dto.PayTraTransactionDTO;
import com.dodopal.api.payment.dto.PayWayDTO;
import com.dodopal.api.users.dto.MerUserCardBDDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.DelFlgEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.AccountFundBean;
import com.dodopal.portal.business.bean.PayServiceRateBean;
import com.dodopal.portal.business.bean.PayWayBean;
import com.dodopal.portal.business.bean.TraTransactionBean;
import com.dodopal.portal.business.model.MerchantUser;
import com.dodopal.portal.business.service.DdpService;
import com.dodopal.portal.delegate.DdpDelegate;
import com.dodopal.portal.delegate.MerOperatorDelegate;
import com.dodopal.portal.delegate.MerUserDelegate;
import com.dodopal.portal.delegate.MerchantDelegate;

/**
 * 账户充值
 * @author xiongzhijing@dodopal.com
 */
@Service
public class DdpServiceImpl implements DdpService {

    private final static Logger log = LoggerFactory.getLogger(DdpServiceImpl.class);
    @Autowired
    private DdpDelegate ddpDelegate;
    @Autowired
    private MerOperatorDelegate merUserDelegate;

    @Autowired
    MerchantDelegate merchantdelegate;
    @Autowired
    MerUserDelegate UserDelegate;

    //更多支付方式
    public DodopalResponse<List<PayWayBean>> findPayWay(boolean ext, String merCode) {
        log.info("findPayWay this merCode:" + merCode + ",ext:" + ext);
        DodopalResponse<List<PayWayBean>> response = new DodopalResponse<List<PayWayBean>>();
        try {
            DodopalResponse<List<PayWayDTO>> payWayDTOList = ddpDelegate.findPayWay(ext, merCode);
            List<PayWayBean> payWayBeanList = new ArrayList<PayWayBean>();
            if (payWayDTOList.getResponseEntity() != null && payWayDTOList.getResponseEntity().size() > 0) {
                for (PayWayDTO payWayDTO : payWayDTOList.getResponseEntity()) {
                    PayWayBean payWayBean = new PayWayBean();
                    PropertyUtils.copyProperties(payWayBean, payWayDTO);
                    payWayBeanList.add(payWayBean);
                }

            }
            response.setCode(payWayDTOList.getCode());
            response.setResponseEntity(payWayBeanList);
        }
        catch (HessianRuntimeException e) {
            log.debug("DdpServiceImpl findPayWay call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
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
            DodopalResponse<List<PayWayDTO>> payWayDTOList = ddpDelegate.findCommonPayWay(ext, userCode);
            List<PayWayBean> payWayBeanList = new ArrayList<PayWayBean>();
            if (payWayDTOList.getResponseEntity() != null && payWayDTOList.getResponseEntity().size() > 0) {
                for (PayWayDTO payWayDTO : payWayDTOList.getResponseEntity()) {
                    PayWayBean payWayBean = new PayWayBean();
                    PropertyUtils.copyProperties(payWayBean, payWayDTO);
                    payWayBeanList.add(payWayBean);
                }
            }
            response.setCode(payWayDTOList.getCode());
            response.setResponseEntity(payWayBeanList);
        }
        catch (HessianRuntimeException e) {
            log.debug("DdpServiceImpl findCommonPayWay call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
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
            DodopalResponse<AccountFundDTO> rtResponse = ddpDelegate.findAccountBalance(aType, custNum);
            AccountFundBean accountFundBean = new AccountFundBean();
            if (rtResponse.getResponseEntity() != null) {
                PropertyUtils.copyProperties(accountFundBean, rtResponse.getResponseEntity());
            }
            response.setCode(rtResponse.getCode());
            response.setResponseEntity(accountFundBean);
        }
        catch (HessianRuntimeException e) {
            log.debug("DdpServiceImpl findAccountBalance call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.error("DdpServiceImpl findAccountBalance 查询门户首页可用余额 throws e:" + e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<Integer> findMerUserCardBDCount(String merOrUserName) {
        log.info("DdpServiceImpl findMerUserCardBDCount 查询门户首页绑定卡数 merOrUserName=" + merOrUserName);
        DodopalResponse<Integer> response = null;
        MerUserCardBDDTO merUserCardBDDTO = new MerUserCardBDDTO();
        merUserCardBDDTO.setMerUserName(merOrUserName);
        try {
            response = ddpDelegate.findMerUserCardBDCount(merUserCardBDDTO);
        }
        catch (HessianRuntimeException e) {
            log.debug("DdpServiceImpl findAccountBalance call HessianRuntimeException error", e);
            e.printStackTrace();
            response = new DodopalResponse<Integer>();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.error("DdpServiceImpl findAccountBalance 查询门户首页可用余额 throws e:" + e);
            e.printStackTrace();
            response = new DodopalResponse<Integer>();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }

        return response;
    }

    //根据 个人or企业 、 用户号or商户号，查询门户首页交易记录 最新的十条数据
    public DodopalResponse<List<TraTransactionBean>> findTraTransactionByCode(String ext, String merOrUserCode,String createUser) {
        log.info("DdpServiceImpl findTraTransactionByCode 查询门户首页交易记录 最新的十条数据 this 个人or企业   ext:" + ext + ",用户号or商户号  merOrUserCode:" + merOrUserCode+",createUser:"+createUser);
        DodopalResponse<List<TraTransactionBean>> response = new DodopalResponse<List<TraTransactionBean>>();
        List<TraTransactionBean> traBeanList = new ArrayList<TraTransactionBean>();
        DecimalFormat df = new DecimalFormat("0.00");

        try {
            DodopalResponse<List<PayTraTransactionDTO>> rtResponse = ddpDelegate.findTraTransactionByCode(ext, merOrUserCode,createUser);
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
                    amountMoney = df.format((double) traDTO.getAmountMoney() / 100);
                    //将实际交易金额格式化为两位小数（0.00）并处理成单位为（元），数据库存的是（分）
                    realTranMoney = df.format((double) traDTO.getRealTranMoney() / 100);

                    traBean.setAmountMoney(amountMoney);
                    traBean.setTranType(traDTO.getTranType());
                    traBean.setOrderNumber(traDTO.getOrderNumber());
                    traBean.setCommodity(traDTO.getCommodity());
                    traBean.setBusinessType(traDTO.getBusinessType());
                    traBean.setOrderDate(traDTO.getOrderDate());
                    traBean.setPayType(traDTO.getPayType());
                    traBean.setPayWay(traDTO.getPayWay());
                    traBean.setPayWayName(traDTO.getPayWayName());
                    traBean.setRealTranMoney(realTranMoney);
                    traBean.setCreateDate(traDTO.getCreateDate());
                    traBean.setCreateUser(traDTO.getCreateUser());
                    traBean.setId(traDTO.getId());
                    traBeanList.add(traBean);
                }
                response.setCode(rtResponse.getCode());
                response.setResponseEntity(traBeanList);
            } else {
                response.setCode(rtResponse.getCode());
            }

        }
        catch (HessianRuntimeException e) {
            log.debug("查询门户首页交易记录 最新的十条数据 DdpServiceImpl findTraTransactionByCode call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.error("DdpServiceImpl findTraTransactionByCode 查询门户首页交易记录 最新的十条数据 throws e:" + e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            // TODO: handle exception
        }
        return response;
    }

    @Override
    public DodopalResponse<Boolean> modifyPayInfoFlg(MerchantUser user) {
        log.info("修改支付确认信息 DdpServiceImpl modifyPayInfoFlg user" + user.toString());
        //1.校验
        //2.转换

        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        try {
            MerchantUserDTO MerchantUserDTO = new MerchantUserDTO();
            PropertyUtils.copyProperties(MerchantUserDTO, user);
            try {
                response = ddpDelegate.modifyPayInfoFlg(MerchantUserDTO);
            }
            catch (HessianRuntimeException e) {
                log.debug("修改支付确认信息 DdpServiceImpl modifyPayInfoFlg call HessianRuntimeException error", e);
                e.printStackTrace();
                response.setCode(ResponseCode.HESSIAN_ERROR);
            }
            catch (Exception e) {
                log.debug("修改支付确认信息 DdpServiceImpl modifyPayInfoFlg call  error", e);
                e.printStackTrace();
                response.setCode(ResponseCode.SYSTEM_ERROR);
                // TODO: handle exception
            }

        }
        catch (Exception e) {
            log.debug("修改支付确认信息 DdpServiceImpl modifyPayInfoFlg call  error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            // TODO: handle exception
        }
        return response;
    }

    //查询用户信息
    @Override
    public DodopalResponse<MerchantUser> findUserInfoByUserName(String userNameOrMobile) {
        log.info("查询用户信息 DdpServiceImpl findUserInfoByUserName userNameOrMobile:" + userNameOrMobile);
        DodopalResponse<MerchantUser> response = new DodopalResponse<MerchantUser>();
        try {
            DodopalResponse<MerchantUserDTO> rtResponse = ddpDelegate.findUserInfoByUserName(userNameOrMobile);
            MerchantUser user = new MerchantUser();
            if (rtResponse.getCode().equals(ResponseCode.SUCCESS) && rtResponse.getResponseEntity() != null) {
                MerchantUserDTO merchantUserDTO = rtResponse.getResponseEntity();
                PropertyUtils.copyProperties(user, merchantUserDTO);
            }
            response.setCode(rtResponse.getCode());
            response.setResponseEntity(user);
        }
        catch (HessianRuntimeException e) {
            log.debug("查询用户信息 DdpServiceImpl findUserInfoByUserName call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("查询用户信息  DdpServiceImpl findUserInfoByUserName call  error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }

        return response;
    }

    //账户充值校验用户 商户合法性
    public DodopalResponse<Boolean> checkUserAndMer(String userCode, String merCode, String userId, String merType) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();

        if (MerTypeEnum.PERSONAL.getCode().equals(merType)) {
            try {
                DodopalResponse<MerchantUserDTO> rtResponse = UserDelegate.findMerUser(userId);
                if (rtResponse.getCode().equals(ResponseCode.SUCCESS) && rtResponse.getResponseEntity() != null) {
                    response.setCode(ResponseCode.SUCCESS);
                    if (DelFlgEnum.DELETE.getCode().equals(rtResponse.getResponseEntity().getDelFlag())) {
                        response.setCode(ResponseCode.USERS_FIND_USER_ERR);
                        return response;
                    }
                    if (ActivateEnum.DISABLE.getCode().equals(rtResponse.getResponseEntity().getActivate())) {
                        response.setCode(ResponseCode.USERS_USER_DISABLE);
                        return response;
                    }

                } else {
                    response.setCode(ResponseCode.USERS_FIND_USER_ERR);
                    return response;
                }
            }
            
            catch (HessianRuntimeException e) {
                log.debug("账户充值 校验当前用户是否启用 DdpServiceImpl checkUserAndMer  call HessianRuntimeException error", e);
                e.printStackTrace();
                response.setCode(ResponseCode.HESSIAN_ERROR);
            }
            catch (Exception e) {
                log.debug("账户充值 校验当前用户是否启用 DdpServiceImpl checkUserAndMer   call Exception e:" + e);
                e.printStackTrace();
                response.setCode(ResponseCode.SYSTEM_ERROR);
                // TODO: handle exception
            }
            
            

        } else {
            
            
            try {
                DodopalResponse<MerchantUserDTO> user = merUserDelegate.findMerOperatorByUserCode(merCode, userCode);
                if (user.getCode().equals(ResponseCode.SUCCESS) && user.getResponseEntity() != null) {
                    response.setCode(ResponseCode.SUCCESS);
                    if (DelFlgEnum.DELETE.getCode().equals(user.getResponseEntity().getDelFlag())) {
                        response.setCode(ResponseCode.USERS_FIND_USER_ERR);
                        return response;
                    }
                    if (ActivateEnum.DISABLE.getCode().equals(user.getResponseEntity().getActivate())) {
                        response.setCode(ResponseCode.USERS_USER_DISABLE);
                        return response;
                    }

                } else {
                    response.setCode(ResponseCode.USERS_FIND_USER_ERR);
                    return response;
                }

                DodopalResponse<MerchantDTO> merchant = merchantdelegate.findMerchants(merCode);

                if (merchant.getCode().equals(ResponseCode.SUCCESS) && merchant.getResponseEntity() != null) {
                    if (DelFlgEnum.DELETE.getCode().equals(merchant.getResponseEntity().getDelFlg())) {
                        response.setCode(ResponseCode.USERS_FIND_MERCHANT_ERR);
                        return response;
                    }
                    if (ActivateEnum.DISABLE.getCode().equals(merchant.getResponseEntity().getActivate())) {
                        response.setCode(ResponseCode.USERS_MERCHANT_DISABLE);
                        return response;
                    }

                } else {
                    response.setCode(ResponseCode.USERS_FIND_MERCHANT_ERR);
                    return response;
                }
            }
            catch (HessianRuntimeException e) {
                log.debug("账户充值 校验当前用户商户是否启用 DdpServiceImpl checkUserAndMer  call HessianRuntimeException error", e);
                e.printStackTrace();
                response.setCode(ResponseCode.HESSIAN_ERROR);
            }
            catch (Exception e) {
                log.debug("账户充值 校验当前用户商户是否启用 DdpServiceImpl checkUserAndMer   call Exception e:" + e);
                e.printStackTrace();
                response.setCode(ResponseCode.SYSTEM_ERROR);
                // TODO: handle exception
            }

        }

        return response;
    }

    //获取支付服务费率和支付服务费率类型
    public DodopalResponse<PayServiceRateBean> findPayServiceRate(String payWayId, String busType, long amout) {
        log.info("获取支付服务费率和支付服务费率类型   DdpServiceImpl findPayServiceRate 支付方式id(通用) payWayId："+payWayId+",业务类型 busType:"+busType+",金额 amout:"+amout);
        DodopalResponse<PayServiceRateBean> response = new DodopalResponse<PayServiceRateBean>();
        try {
            PayServiceRateBean payServiceRateBean = new PayServiceRateBean();
            DodopalResponse<PayServiceRateDTO> rtResponse = ddpDelegate.findPayServiceRate(payWayId, busType, amout);
            if(rtResponse.getCode().equals(ResponseCode.SUCCESS)&&rtResponse.getResponseEntity()!=null){
                PayServiceRateDTO  payServiceRateDTO = rtResponse.getResponseEntity();
                PropertyUtils.copyProperties(payServiceRateBean, payServiceRateDTO);
                
            }
            response.setCode(rtResponse.getCode());
            response.setResponseEntity(payServiceRateBean);
        } 
        catch (HessianRuntimeException e) {
            log.debug("获取支付服务费率和支付服务费率类型   DdpServiceImpl findPayServiceRate  call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            // TODO: handle exception
            log.debug("获取支付服务费率和支付服务费率类型   DdpServiceImpl findPayServiceRate  call Exception error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
       
        return response;
    }

}
