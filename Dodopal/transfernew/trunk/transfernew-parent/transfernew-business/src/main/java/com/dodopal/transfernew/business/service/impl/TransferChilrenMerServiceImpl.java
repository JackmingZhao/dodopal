package com.dodopal.transfernew.business.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.TransferProCtiyCodeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.transfernew.business.dao.GroupinfotbMapper;
import com.dodopal.transfernew.business.dao.GroupinproxytbMapper;
import com.dodopal.transfernew.business.dao.LogTransferMapper;
import com.dodopal.transfernew.business.dao.ProxyinfotbMapper;
import com.dodopal.transfernew.business.dao.ProxypostbMapper;
import com.dodopal.transfernew.business.dao.TransferProxyMapper;
import com.dodopal.transfernew.business.model.LogTransfer;
import com.dodopal.transfernew.business.model.TransferProxy;
import com.dodopal.transfernew.business.model.old.Groupinfotb;
import com.dodopal.transfernew.business.model.old.Groupinproxytb;
import com.dodopal.transfernew.business.model.old.Proxypostb;
import com.dodopal.transfernew.business.service.ChilrenMerchantService;
import com.dodopal.transfernew.business.service.TransferChilrenMerService;

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

    @Autowired
    ProxypostbMapper proxypostbMapper;

    @Autowired
    TransferProxyMapper transferProxyMapper;

    public DodopalResponse<String> transferChilrenMerService(String citycode) {
        //groupinfotb   cityno=100000-1110
        DodopalResponse<String> response = new DodopalResponse<String>();

        //定位批次号
        String batchId = DateUtils.getCurrDate(DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR);
        logger.info("==================普通网点和加盟网点==数据迁移==扫描程序。。。======================");

        logger.info("==================查询 城市的集团列表 ==========================");
        try {

            TransferProCtiyCodeEnum cityEnum = TransferProCtiyCodeEnum.getByCityCode(citycode);
            String cityno = cityEnum.getProxyCityNo();

            List<Groupinfotb> groupinfotbList = groupinfotbMapper.findGroupinfotbALL(cityno);

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

                            TransferProxy transferProxy = transferProxyMapper.findTransferProxyByProxyId(groupinproxytb.getProxyid());
                            if (transferProxy == null) {
                                logger.info("网点id：" + groupinproxytb.getProxyid() + "在transferProxy表没有记录。");
                                continue;
                            }

                            //新增日志
                            LogTransfer logTransfer = new LogTransfer();
                            logTransfer.setBatchId(batchId);//日志批次号
                            logTransfer.setOldMerchantId(groupinproxytb.getProxyid());//老商户ID
                            logTransfer.setOldMerchantType("2");//老商户类型

                            try {
                                //将网点下pos迁移成商户
                                if ("0".equals(transferProxy.getTransferType())) {

                                    List<Proxypostb> proxypostbList = proxypostbMapper.findProxypostbListById(groupinproxytb.getProxyid());
                                    logger.info("=================网点有pos 每个pos迁移成对应 对应的商户。=================");

                                    for (Proxypostb proxypostb : proxypostbList) {
                                        logger.info("=================posid 编号为" + proxypostb.getPosid() + "=================");
                                        response = chilrenMerchantService.childrenMerchantTransferByPos(groupinproxytb.getProxyid(), groupinproxytb.getGroupid(), proxypostb.getPosid(), transferProxy.getTransferMerType(), transferProxy.getCityNo(), batchId);
                                    }

                                } else {
                                    //迁移网点，将pos挂在网点下
                                    response = chilrenMerchantService.childrenMerchantTransfer(groupinproxytb.getProxyid(), groupinproxytb.getGroupid(), transferProxy.getTransferMerType(), transferProxy.getCityNo(), batchId);
                                }

                          
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
        return response;
    }

}
