package com.dodopal.transfer.business.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.transfer.business.dao.BimchntinfotbMapper;
import com.dodopal.transfer.business.dao.LogTransferMapper;
import com.dodopal.transfer.business.model.LogTransfer;
import com.dodopal.transfer.business.model.old.Bimchntinfotb;
import com.dodopal.transfer.business.service.BimchntinfotbService;
import com.dodopal.transfer.business.service.MerchantService;
@Service
public class BimchntinfotbServiceImpl implements BimchntinfotbService {
	private Logger logger = LoggerFactory.getLogger(BimchntinfotbServiceImpl.class);

    @Autowired
    private BimchntinfotbMapper bimMapper;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private LogTransferMapper logTransferMapper;

    /***
     * 连锁商户信息
     */
    @Override
    public DodopalResponse<String> findAllBimchntinfotb() {
        DodopalResponse<String> req = new DodopalResponse<String>();
        List<Bimchntinfotb> listBim = bimMapper.findAllBimchntinfotb();
        //定位批次号
        String  batchId = DateUtils.getCurrDate(DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR);
        if(listBim.size()!=0){
            for(Bimchntinfotb bim : listBim) {
            	//新增日志
                LogTransfer logTransfer = new LogTransfer();
                logTransfer.setBatchId(batchId);//日志批次号
                logTransfer.setOldMerchantId(bim.getMchnitid());//老商户ID
                logTransfer.setOldMerchantType("3");//老商户类型
                try {
                    req =  merchantService.insertMerchant(bim,batchId);

                    logTransfer.setNewMerchantCode(req.getResponseEntity());//新商户号
                    logTransfer.setNewMerchantType(MerTypeEnum.CHAIN.getCode());//新商户类型
                    logTransfer.setState("0");//成功和失败的状态
                }
                catch (Exception e) {
                    logTransfer.setState("1");//成功和失败的状态
                    logTransfer.setMemo(e.getMessage());//导入描述
                    e.printStackTrace();
                } finally {
                	try {
                        logger.info("批次号batchId：" + batchId);
                        logTransferMapper.insartLogTransfer(logTransfer);
                    }
                    catch (Exception e2) {
                   	 logger.error("商户数据库日志写入失败，异常信息:" + e2.getMessage(), e2);
                    }
                }
               
            }
        }
        return req;
    }



    @Override
    public Bimchntinfotb findBimchntinfotb(String mchnitid) {
        return bimMapper.findBimchntinfotb(mchnitid);
    }

}
