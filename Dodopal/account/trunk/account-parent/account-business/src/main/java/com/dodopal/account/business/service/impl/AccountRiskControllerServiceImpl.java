package com.dodopal.account.business.service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.account.business.constant.AccountConstants;
import com.dodopal.account.business.dao.AccountChangeSumMapper;
import com.dodopal.account.business.dao.AccountControlMapper;
import com.dodopal.account.business.dao.AccountFundMapper;
import com.dodopal.account.business.model.AccountChangeSum;
import com.dodopal.account.business.model.AccountControl;
import com.dodopal.account.business.model.AccountFund;
import com.dodopal.account.business.service.AccountRiskControllerService;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.AccStatusEnum;
import com.dodopal.common.enums.FundTypeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;

@Service
public class AccountRiskControllerServiceImpl implements AccountRiskControllerService {
    
    @Autowired
    private AccountFundMapper accountFundMapper;
    @Autowired
    private AccountControlMapper accountControlMapper;
    @Autowired
    private AccountChangeSumMapper accountChangeSumMapper;
    
    /**
     * 账户转账（转出）风控检查
     */
    @Override
    public DodopalResponse<Boolean> allowedToTransfer(String custType, String custNum, long amount, String operateId,int totalCount) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        
        //1.检查该资金账户是否处于“正常”状态
        AccountFund accountFund = accountFundMapper.queryAccountFundInfoByCustTypeAndCustNum(custType,custNum,FundTypeEnum.FUND.getCode());
        if(accountFund == null){
            response.setCode(ResponseCode.ACC_ILLEGAL_DEDUCT_ERROR);
            response.setResponseEntity(false);
            return response;
        }
        if (!AccStatusEnum.ENABLE.getCode().equals(accountFund.getState())) {
            response.setCode(ResponseCode.ACC_TARGETCUST_OUT_STATE_ERROR);
            response.setResponseEntity(false);
            return response;
        }
        
        //2.账户系统根据输入参数定位到相应的资金变动累计记录和资金账户风控记录，进行检查
        String nowDate = DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDD_STR);
        AccountControl accountControl = accountControlMapper.queryAccountControl(accountFund.getFundAccountCode());
        AccountChangeSum accountChangeSum = accountChangeSumMapper.queryAccountChangeSum(accountFund.getFundAccountCode(), nowDate);
        if(accountControl == null){
            response.setCode(ResponseCode.ACC_TRAN_CONTROL_EMPTY);//资金账户风控记录信息不存在
            response.setResponseEntity(false);
            return response;
        }
        // 资金账户日转账累计信息不存在时，创建一条记录
        if(accountChangeSum == null){
            accountChangeSum = new AccountChangeSum(accountFund.getFundAccountCode(), nowDate, 0, 0, 0, 0, nowDate);
            accountChangeSum.setCreateUser(operateId);
            accountChangeSum.setCreateDate(new Date());
            accountChangeSumMapper.addAccountChangeSum(accountChangeSum);
        }
        // 资金账户日转账累计信息中“日转账交易累计限额”合计本次“转出金额”不能大于数据库可容纳最大数据限额
        if(accountChangeSum.getDayTranLimit() + amount > AccountConstants.TOTALBALANCE_TRAN_SUM_MAX){
            response.setCode(ResponseCode.ACC_TRAN_DAYSUMLIMIT_ERROR);
            throw new DDPException(response.getCode(), response.getMessage()+"(元):"+(double)accountControl.getDayTransferSumLimit()/100+"");
        }
        // 单笔转出金额检查（日转账交易单笔限额）
        if(accountControl.getDayTransferSingleLimit() < amount){
            response.setCode(ResponseCode.ACC_TRAN_DAYSINGLELIMIT_EXCEED);
            response.setMessage(response.getMessage()+"(元):"+new DecimalFormat("0.00").format((double)accountControl.getDayTransferSingleLimit()/100)+"");
            response.setResponseEntity(false);
            return response;
        }
        // 累计转出金额检查（日转账交易累计限额）
        if(accountControl.getDayTransferSumLimit() < accountChangeSum.getDayTranLimit() + amount){
            response.setCode(ResponseCode.ACC_TRAN_DAYSUMLIMIT_EXCEED);
            response.setResponseEntity(false);
            response.setMessage(response.getMessage()+"(元):"+new DecimalFormat("0.00").format((double)accountControl.getDayTransferSumLimit()/100)+"");
            return response;
        }
        // 累计转出次数（日转账交易最大次数）
        if(accountControl.getDayTransferMax() < accountChangeSum.getDayTranTimes() + totalCount){
            response.setCode(ResponseCode.ACC_TRAN_DAYSUMTIMES_EXCEED);
            response.setResponseEntity(false);
            response.setMessage(response.getMessage()+":"+accountControl.getDayTransferMax()+"");
            return response;
        }
        response.setCode(ResponseCode.SUCCESS);
        response.setResponseEntity(true);
        return response;
    }

}
