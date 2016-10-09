package com.dodopal.payment.business.facadeImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.account.dto.AccountTransferDTO;
import com.dodopal.api.account.dto.AccountTransferListDTO;
import com.dodopal.api.payment.dto.PayTraTransactionDTO;
import com.dodopal.api.payment.dto.PayTransferDTO;
import com.dodopal.api.payment.dto.TranscationListResultDTO;
import com.dodopal.api.payment.dto.query.PayTraTransactionQueryDTO;
import com.dodopal.api.payment.dto.query.TranscationRequestDTO;
import com.dodopal.api.payment.facade.PayTraTransactionFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ClearFlagEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.TranInStatusEnum;
import com.dodopal.common.enums.TranOutStatusEnum;
import com.dodopal.common.enums.TranTypeEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.payment.business.model.PayTraTransaction;
import com.dodopal.payment.business.model.query.PayTraTransactionQuery;
import com.dodopal.payment.business.model.query.TranscationRequest;
import com.dodopal.payment.business.service.PayTraTransactionService;
import com.dodopal.payment.business.service.PayTranService;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月22日 下午9:10:36
 */
@Service("payTraTransactionFacade")
public class PayTraTransactionFacadeImpl implements PayTraTransactionFacade {
    private static Logger logger = Logger.getLogger(PayTraTransactionFacadeImpl.class);

    @Autowired
    private PayTraTransactionService payTraService;

    @Autowired
    private PayTranService payTranService;

    @Override
    public DodopalResponse<DodopalDataPage<PayTraTransactionDTO>> findPayTraTransactionByPage(PayTraTransactionQueryDTO queryDTO) {
        DodopalResponse<DodopalDataPage<PayTraTransactionDTO>> dodopalResponse = new DodopalResponse<DodopalDataPage<PayTraTransactionDTO>>();
        PayTraTransactionQuery traQuery = new PayTraTransactionQuery();
        try {
            PropertyUtils.copyProperties(traQuery, queryDTO);
            DodopalDataPage<PayTraTransaction> pagelist = payTraService.findPayTraTransactionByPage(traQuery);
            List<PayTraTransaction> tranList = pagelist.getRecords();
            List<PayTraTransactionDTO> tranDTOList = new ArrayList<PayTraTransactionDTO>();
            if (CollectionUtils.isNotEmpty(tranList)) {
                for (PayTraTransaction payTran : tranList) {
                    PayTraTransactionDTO payTraDTO = new PayTraTransactionDTO();
                    PropertyUtils.copyProperties(payTraDTO, payTran);
                    tranDTOList.add(payTraDTO);
                }
            }
            PageParameter page = DodopalDataPageUtil.convertPageInfo(pagelist);
            DodopalDataPage<PayTraTransactionDTO> pages = new DodopalDataPage<PayTraTransactionDTO>(page, tranDTOList);
            dodopalResponse.setCode(ResponseCode.SUCCESS);
            dodopalResponse.setResponseEntity(pages);
        }
        catch (Exception e) {
            logger.debug("TraTransactionFacadeImpl call error", e);
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }

    @Override
    public DodopalResponse<PayTraTransactionDTO> findPayTraTransactionInfoById(String id) {
        DodopalResponse<PayTraTransactionDTO> response = new DodopalResponse<PayTraTransactionDTO>();
        PayTraTransactionDTO payTraTransactionDTO = new PayTraTransactionDTO();
        PayTraTransaction payTraTransaction = payTraService.findPayTraTransactionById(id);
        try {
            if (null != payTraTransaction) {
                PropertyUtils.copyProperties(payTraTransactionDTO, payTraTransaction);
            }
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(payTraTransactionDTO);
        }
        catch (Exception e) {
            logger.debug("TraTransactionFacadeImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<List<PayTraTransactionDTO>> findPayTraTransactionByCode(String ext, String merOrUserCode, String createUser) {
        logger.info("门户首页查询最新十条交易记录 PayTraTransactionFacadeImpl findPayTraTransactionByCode  ext: " + ext + ",merOrUserCode:" + merOrUserCode);
        DodopalResponse<List<PayTraTransactionDTO>> response = new DodopalResponse<List<PayTraTransactionDTO>>();
        List<PayTraTransactionDTO> PayTraTransactionDTOList = new ArrayList<PayTraTransactionDTO>();
        try {
            List<PayTraTransaction> payTraTransactionList = payTraService.findPayTraTransactionByCode(ext, merOrUserCode, createUser);
            if (payTraTransactionList != null && payTraTransactionList.size() > 0) {
                for (PayTraTransaction traBean : payTraTransactionList) {
                    PayTraTransactionDTO tranDTO = new PayTraTransactionDTO();
                    PropertyUtils.copyProperties(tranDTO, traBean);
                    PayTraTransactionDTOList.add(tranDTO);
                }

            }
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(PayTraTransactionDTOList);
        }
        catch (Exception e) {
            logger.error("门户首页查询最新十条交易记录 PayTraTransactionFacadeImpl findPayTraTransactionByCode error:" + e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }

        return response;
    }

    @Override
    public DodopalResponse<PayTraTransactionDTO> findTranInfoByTranCode(String tranCode) {
        DodopalResponse<PayTraTransactionDTO> response = new DodopalResponse<PayTraTransactionDTO>();
        response.setCode(ResponseCode.SUCCESS);
        PayTraTransactionDTO payTraTransactionDTO = new PayTraTransactionDTO();
        try {
            PayTraTransaction findTranInfoByTranCode = payTranService.findTranInfoByTranCode(tranCode);
            PropertyUtils.copyProperties(payTraTransactionDTO, findTranInfoByTranCode);
            response.setResponseEntity(payTraTransactionDTO);
        }
        catch (Exception e) {
            logger.error("findTranInfoByTranCode error:" + e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<DodopalDataPage<PayTraTransactionDTO>> findTraRecordByPage(PayTraTransactionQueryDTO queryDTO) {
        DodopalResponse<DodopalDataPage<PayTraTransactionDTO>> response = new DodopalResponse<DodopalDataPage<PayTraTransactionDTO>>();
        PayTraTransactionQuery query = new PayTraTransactionQuery();
        try {
            String resCode = findChildMerchantByPageCheck(queryDTO);
            if (ResponseCode.SUCCESS.equals(resCode)) {
                PropertyUtils.copyProperties(query, queryDTO);
                if (query.getPage() == null) {
                    query.setPage(new PageParameter());
                }

                DodopalDataPage<PayTraTransaction> ddpResult = payTraService.findTraRecordByPage(query);

                if (ddpResult != null) {
                    List<PayTraTransaction> resultList = ddpResult.getRecords();
                    List<PayTraTransactionDTO> resResultList = null;
                    if (resultList != null && resultList.size() > 0) {
                        resResultList = new ArrayList<PayTraTransactionDTO>(resultList.size());
                        for (PayTraTransaction mer : resultList) {
                            PayTraTransactionDTO merDTO = new PayTraTransactionDTO();
                            PropertyUtils.copyProperties(merDTO, mer);
                            resResultList.add(merDTO);
                        }
                    }
                    PageParameter page = DodopalDataPageUtil.convertPageInfo(ddpResult);
                    DodopalDataPage<PayTraTransactionDTO> ddpDTOResult = new DodopalDataPage<PayTraTransactionDTO>(page, resResultList);
                    response.setResponseEntity(ddpDTOResult);
                    response.setCode(resCode);
                }

            } else {
                response.setCode(resCode);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    //接口参数校验
    private String findChildMerchantByPageCheck(PayTraTransactionQueryDTO merQueryDTO) {
        // 上级商户号
        String parentCode = merQueryDTO.getMerParentCode();
        if (StringUtils.isBlank(parentCode)) {
            return ResponseCode.USERS_MER_PARENT_CODE_NULL;
        }

        return ResponseCode.SUCCESS;
    }

    /**
     * 门户系统 转账 生成交易流水 add by xiongzhijing
     * @param paymentTransaction
     * @return
     */
    public DodopalResponse<Boolean> transfercreateTran(List<PayTransferDTO> payTransferDTOList) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        //1.校验参数
        //2.支付交易，生成交易流水 处于待处理 状态
        for (PayTransferDTO payTransferDTO : payTransferDTOList) {
            PayTraTransaction payTraTransaction = new PayTraTransaction();

            String tranCode = payTranService.createPayTranCode(payTransferDTO.getBusinessType(), "");
            //将交易流水号赋给 payTransferDTO
            payTransferDTO.setTradeNum(tranCode);

            payTraTransaction.setTranCode(tranCode);//交易流水号
            payTraTransaction.setAmountMoney(Math.round(payTransferDTO.getAmount() * 100));//金额:“分”为单位
            payTraTransaction.setUserType(payTransferDTO.getSourceCustType());//用户类型(0：个人  1：商户)
            payTraTransaction.setMerOrUserCode(payTransferDTO.getSourceCustNum());//用户号:用户类型是0:(个人)，那么这里的用户号为个人在平台上的唯一编号；用户类型是1(商户)，那么这里为商户号。
            payTraTransaction.setOrderNumber(tranCode);//订单号
            payTraTransaction.setCommodity(RateCodeEnum.ACCT_TRANSFER.getName() + payTransferDTO.getAmount());//商品名称
            payTraTransaction.setBusinessType(payTransferDTO.getBusinessType());//业务类型代码
            payTraTransaction.setSource(SourceEnum.PORTAL.getCode());//来源
            payTraTransaction.setPayType(PayTypeEnum.PAY_ACT.getCode());//支付类型

            payTraTransaction.setRealTranMoney(Math.round(payTransferDTO.getAmount() * 100));//实际交易金额:正整数

            payTraTransaction.setClearFlag(ClearFlagEnum.NO.getCode());//清算标志位(0：未清算  1：已清算)
            payTraTransaction.setComments(payTransferDTO.getComments());//备注
            payTraTransaction.setCreateUser(payTransferDTO.getCreateUser());//创建人
            if (TranTypeEnum.TURN_OUT.getCode().equals(payTransferDTO.getTransferFlag())) {
                payTraTransaction.setTranType(TranTypeEnum.TURN_OUT.getCode());//1：账户充值、3：账户消费、5：退款、7：转出、9：转入11：正调账13：反调账
                payTraTransaction.setTurnOutMerCode(payTransferDTO.getSourceCustNum());//转出商户号
                payTraTransaction.setTurnIntoMerCode(payTransferDTO.getTargetCustNum());//转入商户号
            } else {
                payTraTransaction.setTranType(TranTypeEnum.TURN_INTO.getCode());//1：账户充值、3：账户消费、5：退款、7：转出、9：转入11：正调账13：反调账 
                payTraTransaction.setTurnOutMerCode(payTransferDTO.getTargetCustNum());//转出商户号
                payTraTransaction.setTurnIntoMerCode(payTransferDTO.getSourceCustNum());//转入商户号
            }
            payTraTransaction.setTranOutStatus(TranOutStatusEnum.TRANSFER.getCode());//外部交易状态为(0：待支付1：已取消2：支付中3：已支付4：待退款5：已退款6：待转帐7：转帐成功8：关闭)
            payTraTransaction.setTranInStatus(TranInStatusEnum.TO_DO.getCode());//内部交易状态为(0：待处理1：处理中3：账户冻结成功4：账户冻结失败5：账户解冻成功6：账户解冻失败7：账户扣款成功8：账户扣款失败10：账户加值成功11：账户加值失败)

            //支付交易 插入交易流水记录
            try {
                payTraService.addPaymentTransaction(payTraTransaction);
            }
            catch (Exception e) {
                e.printStackTrace();
                response.setCode(ResponseCode.SYSTEM_ERROR);
                response.setResponseEntity(false);
                return response;
            }

        }

        //3.转换DTO实体   将交易支付的DTO转成账户的DTO
        AccountTransferListDTO accountTransferListDTO = new AccountTransferListDTO();
        List<AccountTransferDTO> accountTransferDTOList = new ArrayList<AccountTransferDTO>();
        for (PayTransferDTO payTransferDTO : payTransferDTOList) {
            AccountTransferDTO accountTransferDTO = new AccountTransferDTO();
            accountTransferDTO.setAmount(Math.round(payTransferDTO.getAmount() * 100));
            accountTransferDTO.setComments(payTransferDTO.getComments());
            accountTransferDTO.setSourceCustNum(payTransferDTO.getSourceCustNum());
            accountTransferDTO.setSourceCustType(payTransferDTO.getSourceCustType());
            accountTransferDTO.setTargetCustNum(payTransferDTO.getTargetCustNum());
            accountTransferDTO.setTargetCustType(payTransferDTO.getTargetCustType());
            accountTransferDTO.setTradeNum(payTransferDTO.getTradeNum());
            accountTransferDTO.setTransferType(payTransferDTO.getTransferFlag());
            accountTransferListDTO.setCreateUser(payTransferDTO.getCreateUser());
            accountTransferListDTO.setOperateId(payTransferDTO.getCreateUser());
            accountTransferDTOList.add(accountTransferDTO);
        }
        accountTransferListDTO.setAccountTransferListDTO(accountTransferDTOList);

        //4.调用账户  生成资金变更记录
        DodopalResponse<Boolean> accountFlag = payTraService.transferAccount(accountTransferListDTO);

        //5.根据账户返回状态 修改交易流水状态
        if (accountFlag.getCode().equals(ResponseCode.SUCCESS) && accountFlag.getResponseEntity()) {
            //账户生成资金变更记录成功
            //修改生成的交易流水的交易状态
            for (PayTransferDTO payTransferDTO : payTransferDTOList) {
                PayTraTransaction payTraTransaction = new PayTraTransaction();
                //区分 转入还是转出 的交易状态
                payTraTransaction.setTranCode(payTransferDTO.getTradeNum());//交易流水号
                if (payTransferDTO.getTransferFlag().equals(TranTypeEnum.TURN_OUT.getCode())) {
                    //转账成功
                    payTraTransaction.setTranOutStatus(TranOutStatusEnum.TRANSFER_SUCCESS.getCode());
                    //账户扣款成功
                    payTraTransaction.setTranInStatus(TranInStatusEnum.ACCOUNT_DEBIT_SUCCESS.getCode());
                } else {
                    //转账成功
                    payTraTransaction.setTranOutStatus(TranOutStatusEnum.TRANSFER_SUCCESS.getCode());
                    //账户加值成功
                    payTraTransaction.setTranInStatus(TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode());
                }

                //修改内、外部交易状态 
                payTraService.UpdateStatesByTransfer(payTraTransaction);
            }
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(true);
        } else {
            //生成资金变更记录失败 ，修改账户内部交易状态
            for (PayTransferDTO payTransferDTO : payTransferDTOList) {
                PayTraTransaction payTraTransaction = new PayTraTransaction();
                //区分 转入还是转出 的交易状态
                payTraTransaction.setTranCode(payTransferDTO.getTradeNum());//交易流水号
                if (payTransferDTO.getTransferFlag().equals(TranTypeEnum.TURN_OUT.getCode())) {
                    //账户扣款失败
                    payTraTransaction.setTranInStatus(TranInStatusEnum.ACCOUNT_DEBIT_FAIL.getCode());
                } else {
                    //账户加值失败
                    payTraTransaction.setTranInStatus(TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode());
                }
                //修改外部交易状态 
                payTraService.UpdateInStatesByTransfer(payTraTransaction);
            }
            response.setCode(accountFlag.getCode());
            //添加阀值
            response.setMessage(accountFlag.getMessage());
            response.setResponseEntity(false);
            return response;
        }
        return response;
    }

    //查询交易所有
    public DodopalResponse<List<PayTraTransactionDTO>> findPayTraTransactionAll(PayTraTransactionQueryDTO queryDTO) {
        DodopalResponse<List<PayTraTransactionDTO>> dodopalResponse = new DodopalResponse<List<PayTraTransactionDTO>>();
        PayTraTransactionQuery traQuery = new PayTraTransactionQuery();
        try {
            PropertyUtils.copyProperties(traQuery, queryDTO);
            List<PayTraTransaction> pagelist = payTraService.findTraRecordAll(traQuery);
            List<PayTraTransactionDTO> tranDTOList = new ArrayList<PayTraTransactionDTO>();
            if (CollectionUtils.isNotEmpty(pagelist)) {
                for (PayTraTransaction payTran : pagelist) {
                    PayTraTransactionDTO payTraDTO = new PayTraTransactionDTO();
                    PropertyUtils.copyProperties(payTraDTO, payTran);
                    tranDTOList.add(payTraDTO);
                }
            }
            dodopalResponse.setCode(ResponseCode.SUCCESS);
            dodopalResponse.setResponseEntity(tranDTOList);
        }
        catch (Exception e) {
            logger.debug("TraTransactionFacadeImpl findPayTraTransactionAll error", e);
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }

    //查询子商户交易记录
    public DodopalResponse<List<PayTraTransactionDTO>> findTraRecordByMerParentCode(String merParentCode) {
        DodopalResponse<List<PayTraTransactionDTO>> response = new DodopalResponse<List<PayTraTransactionDTO>>();
        try {
            List<PayTraTransaction> resultList = payTraService.findTraRecordByMerParentCode(merParentCode);

            List<PayTraTransactionDTO> resResultList = null;
            if (resultList != null && resultList.size() > 0) {
                resResultList = new ArrayList<PayTraTransactionDTO>(resultList.size());
                for (PayTraTransaction mer : resultList) {
                    PayTraTransactionDTO merDTO = new PayTraTransactionDTO();
                    PropertyUtils.copyProperties(merDTO, mer);
                    resResultList.add(merDTO);
                }
                response.setResponseEntity(resResultList);
                response.setCode(ResponseCode.SUCCESS);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    //查询交易所有
    public DodopalResponse<List<PayTraTransactionDTO>> findTrasactionExport(PayTraTransactionQueryDTO queryDTO) {
        DodopalResponse<List<PayTraTransactionDTO>> dodopalResponse = new DodopalResponse<List<PayTraTransactionDTO>>();
        PayTraTransactionQuery traQuery = new PayTraTransactionQuery();
        try {
            PropertyUtils.copyProperties(traQuery, queryDTO);
            List<PayTraTransaction> pagelist = payTraService.findTrasactionExport(traQuery);
            List<PayTraTransactionDTO> tranDTOList = new ArrayList<PayTraTransactionDTO>();
            if (CollectionUtils.isNotEmpty(pagelist)) {
                for (PayTraTransaction payTran : pagelist) {
                    PayTraTransactionDTO payTraDTO = new PayTraTransactionDTO();
                    PropertyUtils.copyProperties(payTraDTO, payTran);
                    tranDTOList.add(payTraDTO);
                }
            }
            dodopalResponse.setCode(ResponseCode.SUCCESS);
            dodopalResponse.setResponseEntity(tranDTOList);
        }
        catch (Exception e) {
            logger.debug("TraTransactionFacadeImpl findPayTraTransactionAll error", e);
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }

    //查询交易记录（手机端和VC端）
    public DodopalResponse<List<TranscationListResultDTO>> queryPayTranscation(TranscationRequestDTO requestDto) {
        DodopalResponse<List<TranscationListResultDTO>> response = new DodopalResponse<List<TranscationListResultDTO>>();
        try {
            TranscationRequest request =new TranscationRequest();
            PropertyUtils.copyProperties(request, requestDto);
            List<TranscationListResultDTO> transcationListResultDTOList = new ArrayList<TranscationListResultDTO>();
            List<PayTraTransaction> transcationList = payTraService.queryPayTranscation(request);
            if (CollectionUtils.isNotEmpty(transcationList)) {
                for(PayTraTransaction payTraTransaction:transcationList){
                    TranscationListResultDTO transcationListResultDTO = new TranscationListResultDTO();
                    transcationListResultDTO.setBusinesstype(payTraTransaction.getBusinessType());
                    transcationListResultDTO.setTrancode(payTraTransaction.getTranCode());
                    transcationListResultDTO.setTranoutstatus(payTraTransaction.getTranOutStatus());
                    transcationListResultDTO.setTrantype(payTraTransaction.getTranType());
                    transcationListResultDTO.setPaywayname(payTraTransaction.getPayWayName());
                    transcationListResultDTO.setCreatedate(DateUtils.formatDate(payTraTransaction.getCreateDate(), DateUtils.DATE_FULL_STR));
                    transcationListResultDTO.setRealtranmoney(payTraTransaction.getRealTranMoney());
                    transcationListResultDTOList.add(transcationListResultDTO);
                }
            }
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(transcationListResultDTOList);
            
        }
        catch (Exception e) {
            logger.error("查询交易记录（手机端和VC端） TraTransactionFacadeImpl queryPayTranscation error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }

        return response;
    }

    @Override
    public DodopalResponse<DodopalDataPage<PayTraTransactionDTO>> findMerCreditsByPage(PayTraTransactionQueryDTO queryDTO) {
        DodopalResponse<DodopalDataPage<PayTraTransactionDTO>> response = new DodopalResponse<DodopalDataPage<PayTraTransactionDTO>>();
        PayTraTransactionQuery query = new PayTraTransactionQuery();
        try {
                PropertyUtils.copyProperties(query, queryDTO);
                if (query.getPage() == null) {
                    query.setPage(new PageParameter());
                }
                DodopalDataPage<PayTraTransaction> ddpResult = payTraService.findMerCreditsByPage(query);
                if (ddpResult != null) {
                    List<PayTraTransaction> resultList = ddpResult.getRecords();
                    List<PayTraTransactionDTO> resResultList = null;
                    if (resultList != null && resultList.size() > 0) {
                        resResultList = new ArrayList<PayTraTransactionDTO>(resultList.size());
                        for (PayTraTransaction mer : resultList) {
                            PayTraTransactionDTO merDTO = new PayTraTransactionDTO();
                            PropertyUtils.copyProperties(merDTO, mer);
                            resResultList.add(merDTO);
                        }
                    }
                    PageParameter page = DodopalDataPageUtil.convertPageInfo(ddpResult);
                    DodopalDataPage<PayTraTransactionDTO> ddpDTOResult = new DodopalDataPage<PayTraTransactionDTO>(page, resResultList);
                    response.setResponseEntity(ddpDTOResult);
                    response.setCode(ResponseCode.SUCCESS);
                }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<List<PayTraTransactionDTO>> findMerCreditsExport(PayTraTransactionQueryDTO queryDTO) {
        DodopalResponse<List<PayTraTransactionDTO>> response = new DodopalResponse<List<PayTraTransactionDTO>>();
        PayTraTransactionQuery query = new PayTraTransactionQuery();
        try {
                PropertyUtils.copyProperties(query, queryDTO);
                if (query.getPage() == null) {
                    query.setPage(new PageParameter());
                }
                List<PayTraTransaction> resultList = payTraService.findMerCreditsExport(query);
                List<PayTraTransactionDTO> resResultList = null;
                if (resultList != null && resultList.size() > 0) {
                    resResultList = new ArrayList<PayTraTransactionDTO>(resultList.size());
                    for (PayTraTransaction mer : resultList) {
                        PayTraTransactionDTO merDTO = new PayTraTransactionDTO();
                        PropertyUtils.copyProperties(merDTO, mer);
                        resResultList.add(merDTO);
                    }
                }
                response.setResponseEntity(resResultList);
                response.setCode(ResponseCode.SUCCESS);
        } 
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<List<PayTraTransactionDTO>> findPayTraTransactionList(PayTraTransactionQueryDTO queryDTO) {
        
        DodopalResponse<List<PayTraTransactionDTO>> response = new DodopalResponse<List<PayTraTransactionDTO>>();
        PayTraTransaction query = new PayTraTransaction();
        try {
                PropertyUtils.copyProperties(query, queryDTO);
                List<PayTraTransaction> resultList = payTraService.findPayTraTransactionList(query);
                List<PayTraTransactionDTO> resResultList = null;
                if (resultList != null && resultList.size() > 0) {
                    resResultList = new ArrayList<PayTraTransactionDTO>(resultList.size());
                    for (PayTraTransaction mer : resultList) {
                        PayTraTransactionDTO merDTO = new PayTraTransactionDTO();
                        PropertyUtils.copyProperties(merDTO, mer);
                        resResultList.add(merDTO);
                    }
                }
                response.setResponseEntity(resResultList);
                response.setCode(ResponseCode.SUCCESS);
        } 
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

}
