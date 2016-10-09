package com.dodopal.running.business.task;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.log.ActivemqLogPublisher;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.SysLog;
import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.running.business.constant.RunningConstants;
import com.dodopal.running.business.model.PayTraTransaction;
import com.dodopal.running.business.model.SysTimeThreshold;
import com.dodopal.running.business.model.User;
import com.dodopal.running.business.service.AccountRechargeExceptionHandleService;
import com.dodopal.running.business.service.SysTimeThresholdService;
import com.dodopal.running.business.service.TraTransactionService;
import com.dodopal.running.business.service.UserMgmtService;
/**
 * 账户充值 补单程序 自动批处理
 * @author xiongzhijing@dodopal.com
 * @version 2015年11月5日
 */
public class AccountRechargeTask {
    
    private static Logger logger = Logger.getLogger(AccountRechargeTask.class);
    //private DDPLog<AccountRechargeTask> ddpLog = new DDPLog<>(AccountRechargeTask.class);
    
    @Autowired
    private SysTimeThresholdService  SysTimeThresholdService;
    
    @Autowired
    private TraTransactionService traTransactionService;
    
    @Autowired
    private AccountRechargeExceptionHandleService  accountRechargeException;
    
    @Autowired
    private  UserMgmtService userMgmtService;
    
    public void accountRecharge(){
        logger.info("===========进入账户充值==补单==扫描程序。。。============");
        
        SysTimeThreshold sysTimeThreshold = SysTimeThresholdService.findSysTimeThresholdByCode(RateCodeEnum.ACCT_RECHARGE.getCode());
        
        if(sysTimeThreshold==null){
            logger.error("===========未发现需要处理的账户充值的交易记录 Threshold！！！");
        }else{
            logger.info("===========账户充值==补单== Threshold 为" + sysTimeThreshold.getThreshold()+ "秒 。");
        }
        
        List<PayTraTransaction> tranList = traTransactionService.findPayTraTransactionByList(sysTimeThreshold.getThreshold());
        
        if(tranList==null || tranList.size()==0){
            logger.info("===========未发现需要补单的账户充值的交易记录 ！===========");
        }else{
            logger.info("===========共扫描到需要的账户充值记录有  " + tranList.size() + "条。===========");
        }
        
        // oss系统  admin 的id
        User  user= userMgmtService.findUserByLoginName(RunningConstants.ADMIN_USER);
        
        if(user==null){
            logger.info("===========未发现oss系统 补单程序的 系统管理员 ！===========");
            
        }else{
            logger.info("===========oss系统 补单程序的 系统管理员 admin   的 ID是 ====="+ user.getId() );
           
        }
        
        for (PayTraTransaction tran : tranList) {
            logger.info("===========账户充值补单程序的交易流水号=======" + tran.getTranCode());
            
            SysLog log = new SysLog();
            DodopalResponse<String> response = new DodopalResponse<String>();
            try {
         
                String operatorId = user.getId();
                log.setServerName(CommonConstants.SYSTEM_NAME_OSS);
                log.setOrderNum(tran.getOrderNumber());
                log.setTranNum(tran.getTranCode());
                log.setClassName(AccountRechargeTask.class.toString());
                log.setMethodName("exceptionHandle");
                log.setInParas("交易流水号："+tran.getTranCode()+"；操作员ID:"+operatorId);
            
                response =  accountRechargeException.exceptionHandle(tran.getTranCode(), operatorId);
                
                log.setOutParas(JSONObject.toJSONString(response));
                if(ResponseCode.SUCCESS.equals(response.getCode())){
                    log.setTradeType(0);
                    log.setRespCode(response.getCode());
                }else{
                    log.setTradeType(1);
                    log.setRespCode(response.getCode());
                }
                
            } catch (DDPException e) {
                e.printStackTrace();
                logger.info("===========DDPEXCEPTION=========" + e.getMessage());
                log.setStatckTrace(e.getStackTrace().toString());
                continue;
            }catch (Exception e) {
                e.printStackTrace();
                logger.info("===========账户充值补单程序异常=========" + e.getMessage());
                log.setStatckTrace(e.getStackTrace().toString());
                continue;
            }finally{
                ActivemqLogPublisher.publishLog2Queue(log, DodopalAppVarPropsUtil.getStringProp(RunningConstants.SERVER_LOG_NAME), DodopalAppVarPropsUtil.getStringProp(RunningConstants.SERVER_LOG_URL));
            }
            
          
        }

        logger.info("===========账户充值补单程序结束==================");
    }
}
