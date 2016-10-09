package com.dodopal.transfernew.business.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.TransferProCtiyCodeEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.transfernew.business.dao.GroupinfotbMapper;
import com.dodopal.transfernew.business.dao.LogTransferMapper;
import com.dodopal.transfernew.business.model.LogTransfer;
import com.dodopal.transfernew.business.model.old.Groupinfotb;
import com.dodopal.transfernew.business.service.GroupinfoService;
import com.dodopal.transfernew.business.service.TransferGroupinfoService;

@Service
public class TransferGroupinfoServiceImpl implements TransferGroupinfoService {
	private Logger logger = LoggerFactory.getLogger(TransferGroupinfoServiceImpl.class);

	@Autowired
	GroupinfoService groupinfoService;

	@Autowired
	GroupinfotbMapper groupinfotbMapper;

	@Autowired
    LogTransferMapper logTransferMapper;

	public DodopalResponse<String> transferGroupinfo(String citycode) {

		DodopalResponse<String> response = new DodopalResponse<String>();

		logger.info("==================集团基本信息==数据迁移==扫描程序。。。======================");

		logger.info("==================查询 城市的集团列表 ==========================");
		try {
		    TransferProCtiyCodeEnum cityEnum = TransferProCtiyCodeEnum.getByCityCode(citycode);
		    String cityno = cityEnum.getProxyCityNo();
		    
			List<Groupinfotb> groupinfotbList = groupinfotbMapper.findGroupinfotb(cityno);

			if (CollectionUtils.isEmpty(groupinfotbList)) {
				logger.error("==================未发现需要迁移的城市下集团信息==========================");
			} else {
				String  batchId = DateUtils.getCurrDate(DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR);

				
				for (Groupinfotb groupinfotb : groupinfotbList) {
					LogTransfer logTransfer = new LogTransfer();
					logger.info("==================集团id:" + groupinfotb.getGroupid() + ",开始迁移==========================");
					try {
						response = groupinfoService.groupInfoTranfer(Long.toString(groupinfotb.getGroupid()), citycode);
						logger.info("==================集团id:" + groupinfotb.getGroupid() + ",迁移成功==========================");
						
						logTransfer.setNewMerchantCode(response.getResponseEntity());//新商户号
			            logTransfer.setNewMerchantType(MerTypeEnum.CHAIN.getCode());//新商户类型
			            logTransfer.setState("0");//成功和失败的状态
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("==================集团id:" + groupinfotb.getGroupid() + ",迁移失败==========================",e);
						
						logTransfer.setState("1");//成功和失败的状态
						logTransfer.setMemo(e.getMessage());
					}
					// 增加迁移日志
					try {
						logTransfer.setBatchId(batchId);//日志批次号
			            logTransfer.setOldMerchantId(Long.toString(groupinfotb.getGroupid()));//老商户ID
			            logTransfer.setOldMerchantType("1");//老商户类型
			            logTransferMapper.insartLogTransfer(logTransfer); 
					} catch (Exception e) {
						logger.error("集团迁移日志写入失败" + e.getMessage() ,e);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

}
