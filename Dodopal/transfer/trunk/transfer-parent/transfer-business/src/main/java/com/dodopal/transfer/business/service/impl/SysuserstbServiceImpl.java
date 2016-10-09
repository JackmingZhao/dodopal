package com.dodopal.transfer.business.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.QueryBase;
import com.dodopal.common.util.DateUtils;
import com.dodopal.transfer.business.dao.LogTransferMapper;
import com.dodopal.transfer.business.dao.SysuserstbMapper;
import com.dodopal.transfer.business.model.LogTransfer;
import com.dodopal.transfer.business.model.old.Sysuserstb;
import com.dodopal.transfer.business.service.PersonalInfoService;
import com.dodopal.transfer.business.service.SysuserstbService;

@Service
public class SysuserstbServiceImpl implements SysuserstbService {
    //private MerchantService merchantService;
	private Logger logger = LoggerFactory.getLogger(SysuserstbServiceImpl.class);

    @Autowired
    private PersonalInfoService personalInfoService;
    @Autowired
    private SysuserstbMapper sysuserstbMapper;
    @Autowired
    private LogTransferMapper logTransferMapper;

    @Override
    public DodopalResponse<String> findAllSysuserstb(String mchnitid) {
        DodopalResponse<String> req = new DodopalResponse<String>();
        //List<Sysuserstb> listSysUser = sysuserstbMapper.findAllSysuserstb(mchnitid);
        //定位批次号
        //       // String  batchId = DateUtils.getCurrDate(DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR);
        //        if(listSysUser.size()!=0){
        //            for(Sysuserstb sysuser : listSysUser) {
        //               // req =  merchantService.insertMerchantChinaJoin(sysuser,batchId);
        //            }
        //        }
        return req;
    }

    @Override
    public Sysuserstb findSysuserstb(String userid) {
        return sysuserstbMapper.findSysuserstb(userid);
    }

    /**
     * 2016-03-17 测试个人信息的
     */
    @Override
    public DodopalResponse<String> findGRAllSysuserstbByPage(String totalPages) {
        DodopalResponse<String> req = new DodopalResponse<String>();
        QueryBase queryBase = new QueryBase();
        PageParameter page = new PageParameter();
        page.setPageSize(100);
        queryBase.setPage(page);
        if(StringUtils.isNotBlank(totalPages)){
            page.setPageNo(Integer.parseInt(totalPages));
        }
        List<Sysuserstb> listBim = sysuserstbMapper.findSysUserstbAllByPage(queryBase);
       // for (int i = 1; i < page.getTotalPages(); i++) {
            try {
                //定位批次号
                String batchId = DateUtils.getCurrDate(DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR);
                listBim = sysuserstbMapper.findSysUserstbAllByPage(queryBase);
                if (listBim!=null && listBim.size()>0) {
                    for (Sysuserstb bim : listBim) {
                    	LogTransfer logTransfer = new LogTransfer();
                    	logTransfer.setBatchId(batchId);//日志批次号
                        logTransfer.setOldMerchantId(bim.getUserid());//老商户ID
                        logTransfer.setOldMerchantType("0");//老商户类型
                        try {
                            req = personalInfoService.insertSysUserstb(bim.getUserid(), batchId);
                            logTransfer.setNewMerchantCode(req.getResponseEntity());//新商户号
                            logTransfer.setNewMerchantType(MerTypeEnum.PERSONAL.getCode());//新商户类型
                            logTransfer.setState("0");//成功和失败的状态
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            logTransfer.setBatchId(batchId);//日志批次号
                            logTransfer.setState("1");//成功和失败的状态
                            logTransfer.setMemo(e.getMessage());//导入描述
                            logger.error("个人用户数据库日志写入失败，异常信息:" + e.getMessage(), e);
                        }finally{
                        	logger.info("批次号batchId：" + batchId);
                            logTransferMapper.insartLogTransfer(logTransfer);
                        }

                    }
                }
            }
            catch (Exception e) {
               e.printStackTrace();
            }
            
        //}
        return req;
    }

}
