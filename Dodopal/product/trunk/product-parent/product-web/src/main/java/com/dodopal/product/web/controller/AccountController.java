package com.dodopal.product.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dodopal.api.payment.dto.PayTranDTO;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.TranTypeEnum;
import com.dodopal.product.business.bean.*;
import com.dodopal.product.business.service.MerchantService;
import com.dodopal.product.business.service.MerchantUserService;
import com.dodopal.product.business.service.PayService;
import com.dodopal.product.web.param.*;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.product.business.service.AccountQueryService;

/**
 * 查询账户余额 资金变更记录（手机端和VC端接入）
 *
 * @author xiongzhijing@dodopal.com
 */
@Controller
@RequestMapping("/account")
public class AccountController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    AccountQueryService accountQueryService;
    @Autowired
    PayService payService;
    @Autowired
    MerchantUserService merchantUserService;
    @Autowired
    MerchantService merchantService;

    /**
     * 3.9 查询资金变更记录(移动端、VC端接口)
     */
    @RequestMapping("/queryAccountChange")
    public
    @ResponseBody
    QueryAccountChangeResponse queryAccountChange(HttpServletRequest request) {
        QueryAccountChangeResponse response = new QueryAccountChangeResponse();
        String key = "123";// TODO:签名验签秘钥如何获取
        try {
            String jsondata = request.getParameter("jsondata");// 获取jsondata
            if (StringUtils.isBlank(jsondata)) {
                if (logger.isInfoEnabled()) {
                    logger.info("queryAccountChange:查询资金变更记录查询接口:接收到的jsondata参数为空");
                }
                response.setRespcode(ResponseCode.JSON_ERROR);
                return response;
            }
            if (logger.isInfoEnabled()) {
                logger.info("queryAccountChange:查询资金变更记录查询接口:接收到的jsondata参数:" + jsondata);
            }
            QueryAccountChangeRequest queryAccountChangeRequest = convertJsonToRequest(jsondata, QueryAccountChangeRequest.class);// 转换jsondata
            baseCheck(queryAccountChangeRequest);// 通用参数校验
            queryAccountChangeRequestCheck(queryAccountChangeRequest);// 业务入参校验
            signCheck(key, queryAccountChangeRequest);// 验签

            QueryAccountChangeRequestDTO requestDto = new QueryAccountChangeRequestDTO();
            requestDto.setChangetype(queryAccountChangeRequest.getChangetype());
            requestDto.setCustcode(queryAccountChangeRequest.getCustcode());
            requestDto.setCusttype(queryAccountChangeRequest.getCusttype());
            requestDto.setEndamt(queryAccountChangeRequest.getEndamt());
            requestDto.setEnddate(queryAccountChangeRequest.getEnddate());
            requestDto.setStratamt(queryAccountChangeRequest.getStratamt());
            requestDto.setStratdate(queryAccountChangeRequest.getStratdate());
            requestDto.setUserid(queryAccountChangeRequest.getUserid());

            // 查询
            DodopalResponse<List<QueryAccountChangeResultDTO>> rTResponse = accountQueryService.queryAccountChange(requestDto);

            if (ResponseCode.SUCCESS.equals(rTResponse.getCode())) {
                response.setRespcode(rTResponse.getCode());
                response.setAccountChangeDTOList(rTResponse.getResponseEntity());
            } else {
                response.setRespcode(rTResponse.getCode());
            }
            //TODO 签名
            // sign(key, response);
        } catch (DDPException e) {
            response.setRespcode(e.getCode());
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setRespcode(ResponseCode.SYSTEM_ERROR);
        }
        if (logger.isInfoEnabled()) {
            logger.info("queryAccountChange:公交卡充值订单查询接口:返回respcode:" + response.getRespcode());
        }
        return response;
    }


    /**
     * 3.8 查询账户余额(移动端、VC端接口)
     */
    @RequestMapping("/queryAccountBalance")
    public
    @ResponseBody
    QueryAccountBalanceResponse queryAccountBalance(HttpServletRequest request) {
        QueryAccountBalanceResponse response = new QueryAccountBalanceResponse();
        String key = "123";// TODO:签名验签秘钥如何获取
        try {
            String jsondata = request.getParameter("jsondata");// 获取jsondata
            if (StringUtils.isBlank(jsondata)) {
                if (logger.isInfoEnabled()) {
                    logger.info("queryAccountBalance:查询账户余额接口:接收到的jsondata参数为空");
                }
                response.setRespcode(ResponseCode.JSON_ERROR);
                return response;
            }
            if (logger.isInfoEnabled()) {
                logger.info("queryAccountBalance:查询账户余额接口:接收到的jsondata参数:" + jsondata);
            }
            QueryAccountBalanceRequest queryAccountBalanceRequest = convertJsonToRequest(jsondata, QueryAccountBalanceRequest.class);// 转换jsondata
            baseCheck(queryAccountBalanceRequest);// 通用参数校验
            queryAccountBeanRequestCheck(queryAccountBalanceRequest);// 业务入参校验

            signCheck(key, queryAccountBalanceRequest);// 验签

            // 查询
            DodopalResponse<AccountBean> rTResponse = accountQueryService.queryAccountBalance(queryAccountBalanceRequest.getCusttype(), queryAccountBalanceRequest.getCustcode());

            if (ResponseCode.SUCCESS.equals(rTResponse.getCode()) && rTResponse.getResponseEntity() != null) {
                response.setRespcode(rTResponse.getCode());
                AccountBean accountBean = rTResponse.getResponseEntity();
                response.setAuthavailablebalance(accountBean.getAuthavailablebalance());
                response.setAuthfrozenamount(accountBean.getAuthfrozenamount());
                response.setAuthtotalbalance(accountBean.getAuthtotalbalance());
                response.setAvailablebalance(accountBean.getAvailablebalance());
                response.setFrozenamount(accountBean.getFrozenamount());
                response.setTotalbalance(accountBean.getTotalbalance());
                response.setFundtype(accountBean.getFundtype());
            } else {
                response.setRespcode(rTResponse.getCode());
            }
            //TODO 签名
            sign(key, response);
        } catch (DDPException e) {
            response.setRespcode(e.getCode());
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setRespcode(ResponseCode.SYSTEM_ERROR);
        }
        if (logger.isInfoEnabled()) {
            logger.info("queryAccountBalance:查询账户余额接口:返回respcode:" + response.getRespcode());
        }
        return response;
    }


    /**
     * 3.5 查询资金变更记录     业务入参检查
     *
     * @param request
     */
    private void queryAccountChangeRequestCheck(QueryAccountChangeRequest request) {
        // 客户类型    String[1]   不为空且（个人用户：设值为0 固定；商户用户：设置为 1 固定）
        String custtype = request.getCusttype();
        if (!MerUserTypeEnum.contains(custtype)) {
            throw new DDPException(ResponseCode.PRODUCT_MOBILE_CUSTTYPE_ERROR);
        }
        // 客户号    String[40]   不为空
        String custcode = request.getCustcode();
        if (!DDPStringUtil.existingWithLength(custcode, 40)) {
            throw new DDPException(ResponseCode.PRODUCT_MOBILE_CUSTCODE_NULL);
        }
        // 用户id    String[20]   不为空
        String userid = request.getUserid();
        if (!DDPStringUtil.existingWithLength(userid, 20)) {
            throw new DDPException(ResponseCode.PRODUCT_MOBILE_USERID_NULL);
        }
    }


    /**
     * 3.4 查询账户余额     业务入参检查
     *
     * @param request
     */
    private void queryAccountBeanRequestCheck(QueryAccountBalanceRequest request) {
        // 客户类型    String[1]   不为空且（个人用户：设值为0 固定；商户用户：设置为 1 固定）
        String custtype = request.getCusttype();
        if (!MerUserTypeEnum.contains(custtype)) {
            throw new DDPException(ResponseCode.PRODUCT_MOBILE_CUSTTYPE_ERROR);
        }
        // 客户号    String[40]   不为空
        String custcode = request.getCustcode();
        if (!DDPStringUtil.existingWithLength(custcode, 40)) {
            throw new DDPException(ResponseCode.PRODUCT_MOBILE_CUSTCODE_NULL);
        }

    }


    /**
     * 3.7 账户充值接口(移动端、VC端接口)
     */
    @RequestMapping("/accountRecharge")
    public
    @ResponseBody
    AccountRechargeRes accountRecharge(HttpServletRequest request) {
        logger.info("==========账户充值接口结束结束==================");
        String key = "123";
        String tranCode = "";
        AccountRechargeRes response = new AccountRechargeRes();
        try {
            String jsondata = request.getParameter("jsondata");// 获取jsondata
            if (StringUtils.isBlank(jsondata)) {
                if (logger.isInfoEnabled()) {
                    logger.info("acountRecharge:账户充值接口:接收到的jsondata参数为空");
                }
                response.setRespcode(ResponseCode.JSON_ERROR);
                return response;
            }
            if (logger.isInfoEnabled()) {
                logger.info("acountRecharge:账户充值接口:接收到的jsondata参数:" + jsondata);
            }
            AccountRecharge accountRecharge = convertJsonToRequest(jsondata, AccountRecharge.class);// 转换jsondata
            baseCheck(accountRecharge);// 通用参数校验
            accountRechargeCheck(accountRecharge);// 业务入参校验
            signCheck(key, accountRecharge);// 验签
            PayTranDTO payTranDTO = new PayTranDTO();
            if (MerUserTypeEnum.MERCHANT.getCode().equals(accountRecharge.getCusttype())) {
                DodopalResponse<MerchantBean> merchantBeanDodopalRes = merchantService.findMerchantByCode(accountRecharge.getCustcode());
                if (ResponseCode.SUCCESS.equals(merchantBeanDodopalRes.getCode())) {
                    MerchantBean merchantBean = merchantBeanDodopalRes.getResponseEntity();
                    if (merchantBean != null) {
                        if (MerTypeEnum.EXTERNAL.getCode().equals(merchantBean.getMerType())) {
                            //是否外接
                            payTranDTO.setExtFlg(new Boolean(true));
                        } else {
                            //是否外接
                            payTranDTO.setExtFlg(new Boolean(false));
                        }
                    }
                }
            }
            //客户号
            payTranDTO.setCusTomerCode(accountRecharge.getCustcode());
            //客户类型
            payTranDTO.setCusTomerType(accountRecharge.getCusttype());
            //业务类型
            payTranDTO.setBusinessType(RateCodeEnum.ACCT_RECHARGE.getCode());
            //商品名称
            payTranDTO.setGoodsName(RateCodeEnum.ACCT_RECHARGE.getCode() + accountRecharge.getAmont());
            //来源
            payTranDTO.setSource(accountRecharge.getSource());
            //operateid
            payTranDTO.setOperatorId(accountRecharge.getUserid());
            //金额
            payTranDTO.setAmount(Double.parseDouble(accountRecharge.getAmont()));
            payTranDTO.setPayId(accountRecharge.getPayid());
            payTranDTO.setTranType(TranTypeEnum.ACCOUNT_RECHARGE.getCode());
            logger.info("==========账户充值调用mobilepay接口开始==================");
            DodopalResponse<String> res = payService.mobilePay(payTranDTO);
            logger.info("==========账户充值调用mobilepay接口结束，返回==================" + tranCode);
            if (ResponseCode.SUCCESS.equals(res.getCode())) {
                tranCode = res.getResponseEntity();
                response.setRespcode(ResponseCode.SUCCESS);
                response.setPaymentTranNo(tranCode);
                // 签名
                sign(key, response);
            } else {
                response.setRespcode(res.getCode());
            }
        } catch (DDPException e) {
            e.printStackTrace();
            logger.error("账户充值出现错误" + e.getMessage());
            response.setRespcode(e.getCode());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("账户充值异常" + e.getMessage());
            response.setRespcode(ResponseCode.SYSTEM_ERROR);
            return response;
        }
        logger.info("==========账户充值接口结束结束==================");
        return response;
    }

    private void accountRechargeCheck(AccountRecharge request) {
        // 客户类型    String[1]   不为空且（个人用户：设值为0 固定；商户用户：设置为 1 固定）
        String custtype = request.getCusttype();
        if (!MerUserTypeEnum.contains(custtype)) {
            throw new DDPException(ResponseCode.PRODUCT_MOBILE_CUSTTYPE_ERROR);
        }
        // 客户号    String[40]   不为空
        String custcode = request.getCustcode();
        if (!DDPStringUtil.existingWithLength(custcode, 40)) {
            throw new DDPException(ResponseCode.PRODUCT_MOBILE_CUSTCODE_NULL);
        }
        // 用户id    String[20]   不为空
        String userid = request.getUserid();
        if (!DDPStringUtil.existingWithLength(userid, 20)) {
            throw new DDPException(ResponseCode.PRODUCT_MOBILE_USERID_NULL);
        }
        double amount = Double.parseDouble(request.getAmont());
        if (amount <= 0) {
            //交易金额非法
            throw new DDPException(ResponseCode.PAY_AMOUNT_ERROR);
        }
    }
}
