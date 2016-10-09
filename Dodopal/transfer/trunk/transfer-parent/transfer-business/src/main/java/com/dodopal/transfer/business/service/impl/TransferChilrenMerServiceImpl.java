package com.dodopal.transfer.business.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.transfer.business.dao.GroupinfotbMapper;
import com.dodopal.transfer.business.dao.GroupinproxytbMapper;
import com.dodopal.transfer.business.dao.LogTransferMapper;
import com.dodopal.transfer.business.dao.ProxyinfotbMapper;
import com.dodopal.transfer.business.model.LogTransfer;
import com.dodopal.transfer.business.model.old.Groupinfotb;
import com.dodopal.transfer.business.model.old.Groupinproxytb;
import com.dodopal.transfer.business.model.old.Proxyinfotb;
import com.dodopal.transfer.business.service.ChilrenMerchantService;
import com.dodopal.transfer.business.service.TransferChilrenMerService;

/**
 * 普通网点和加盟网点数据迁移
 * @author
 */
@Service
public class TransferChilrenMerServiceImpl implements TransferChilrenMerService {
    private Logger logger = LoggerFactory.getLogger(TransferChilrenMerServiceImpl.class);

    @Autowired
    ChilrenMerchantService chilrenMerchantService;

    @Autowired
    GroupinfotbMapper groupinfotbMapper;

    @Autowired
    GroupinproxytbMapper groupinproxytbMapper;

    @Autowired
    ProxyinfotbMapper proxyinfotbMapper;

    @Autowired
    LogTransferMapper logTransferMapper;

    public DodopalResponse<String> transferChilrenMerService() {
        //groupinfotb   cityno=100000-1110
        DodopalResponse<String> response = new DodopalResponse<String>();
        //定位批次号
        String  batchId = DateUtils.getCurrDate(DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR);
        logger.info("==================普通网点和加盟网点==数据迁移==扫描程序。。。======================");

        logger.info("==================查询 cityno=100000-1110 的集团列表 ==========================");
        try {
            
            List<Groupinfotb> groupinfotbList = groupinfotbMapper.findGroupinfotbALL();

            if (CollectionUtils.isEmpty(groupinfotbList)) {
                logger.error("==================未发现城市下集团信息==========================");
            } else {

                for (Groupinfotb groupinfotb : groupinfotbList) {
                    logger.info("==============集团id：" + groupinfotb.getGroupid() + ",集团名称：" + groupinfotb.getGroupname() + "  查询集团下的网点信息。===================");

                    List<Groupinproxytb> groupinproxytbList = groupinproxytbMapper.findGroupinproxytb(Long.toString(groupinfotb.getGroupid()));

                    if (CollectionUtils.isEmpty(groupinproxytbList)) {

                        logger.error("==================未发现集团id：" + groupinfotb.getGroupid() + ",集团名称：" + groupinfotb.getGroupname() + "下 的网点信息。==========================");

                    } else {
                        for (Groupinproxytb groupinproxytb : groupinproxytbList) {

                            logger.info("==================网点id:" + groupinproxytb.getProxyid() + ",接入数据迁移程序。==========================");

                            //新增日志
                            LogTransfer logTransfer = new LogTransfer();
                            logTransfer.setBatchId(batchId);//日志批次号
                            logTransfer.setOldMerchantId(groupinproxytb.getProxyid());//老商户ID
                            logTransfer.setOldMerchantType("2");//老商户类型
                            try {
                            	Proxyinfotb proxyinfotb = proxyinfotbMapper.findProxyinfotbById(groupinproxytb.getProxyid());
								if (proxyinfotb == null) {
									logger.info("网点id：" + groupinproxytb.getProxyid() + "对应的网点信息不存在或已迁移。");
									continue;
								}
                                response = chilrenMerchantService.childrenMerchantTransfer(groupinproxytb.getProxyid(),groupinproxytb.getGroupid(), batchId);

                                logTransfer.setNewMerchantCode(response.getResponseEntity());//新商户号
                                logTransfer.setNewMerchantType(response.getMessage());//新商户类型
                                logTransfer.setState("0");//成功和失败的状态
                                logTransfer.setMemo(ResponseCode.SUCCESS);//导入描述
                            }
                            catch (DDPException e) {
                                e.printStackTrace();
                                logger.info("===========DDPEXCEPTION==" + response.getMessage() + "=======" + e.getMessage());
                                logTransfer.setState("1");//成功和失败的状态
                                logTransfer.setMemo(e.getMessage());//导入描述
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                                logger.info("==========Exception==" + response.getMessage() + "========" + e.getMessage());
                                logTransfer.setState("1");//成功和失败的状态
                                logTransfer.setMemo(e.getMessage());//导入描述
                            }
                            finally {
                            	 try {
                                     logger.info("批次号batchId：" + batchId);
                                     logTransferMapper.insartLogTransfer(logTransfer);
                                 }
                                 catch (Exception e2) {
                                	 logger.error("集团下网点数据库日志写入失败，异常信息:" + e2.getMessage(), e2);
                                 }
                            }

                        }
                    }
                }
            }

        }
        catch (Exception e) {
           e.printStackTrace();
        }
        return  response;
    }

}
