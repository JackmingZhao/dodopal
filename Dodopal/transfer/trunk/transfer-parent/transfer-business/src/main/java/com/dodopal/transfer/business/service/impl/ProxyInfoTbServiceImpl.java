package com.dodopal.transfer.business.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.transfer.business.dao.LogTransferMapper;
import com.dodopal.transfer.business.dao.ProxyinfotbMapper;
import com.dodopal.transfer.business.model.LogTransfer;
import com.dodopal.transfer.business.model.old.Proxyinfotb;
import com.dodopal.transfer.business.service.MerchantService;
import com.dodopal.transfer.business.service.ProxyInfoTbService;

@Service
public class ProxyInfoTbServiceImpl implements ProxyInfoTbService {
	
	private Logger logger = LoggerFactory.getLogger(MerchantServiceImpl.class);
	
	@Autowired
    private ProxyinfotbMapper proxyinfotbMapper;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private LogTransferMapper logTransferMapper;

	@Override
	public DodopalResponse<String> insertDataByProxyId() {
		DodopalResponse<String> response = new DodopalResponse<String>();
		try {
			/**************************** 步骤1：在网点信息表proxyInfoTb中查询出需要迁移的个人网点 ************************************/
			List<Proxyinfotb> proxyinfotbList = proxyinfotbMapper.findProxyInfoTbByCitynoAndProxytypeid("100000-1110", 1); 	// // 在老系统网点信息表proxyInfoTb中按查询条件cityno=100000-1110&proxytypeid=1查询出需要迁移的个人网点
	        //定位批次号
	        String  batchId = DateUtils.getCurrDate(DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR);
			for(Proxyinfotb proxyinfotb : proxyinfotbList) {
				// 日志
				LogTransfer logTransfer = new LogTransfer();
				logTransfer.setBatchId(batchId);// 日志批次号
				logTransfer.setOldMerchantId(String.valueOf(proxyinfotb.getProxyid()));// 老商户ID
				logTransfer.setOldMerchantType("2");// 老商户类型
				try {
					DodopalResponse<String> insertResponse = merchantService.insertMerchantFromProxy(proxyinfotb, batchId);

					logTransfer.setNewMerchantCode(insertResponse.getResponseEntity());// 新商户号
					logTransfer.setNewMerchantType(MerTypeEnum.DDP_MER.getCode());// 新商户类型
					logTransfer.setState("0");// 成功和失败的状态
				} catch (Exception ex) {
					logger.error("" + proxyinfotb.getProxyid(), ex);
					ex.printStackTrace();
					logTransfer.setState("1");// 成功和失败的状态
					logTransfer.setMemo(ex.getMessage());// 导入描述
				} finally {
					try {
						int num = logTransferMapper.insartLogTransfer(logTransfer);
						if (num == 0) {
							logger.warn("个人网点数据库日志写入失败");
						}
					} catch (Exception e) {
						logger.error("个人网点数据库日志写入失败，异常信息:" + e.getMessage(), e);
					}
				}
			}
		}catch(Exception e) {
			
		}
		
		
		
		return response;
	}

	
	
	
}
