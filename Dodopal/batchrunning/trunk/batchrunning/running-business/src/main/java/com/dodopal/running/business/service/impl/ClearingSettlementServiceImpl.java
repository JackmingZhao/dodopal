package com.dodopal.running.business.service.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.running.business.dao.ClearingSettlementMapper;
import com.dodopal.running.business.model.ProfitCollect;
import com.dodopal.running.business.service.ClearingSettlementService;

@Service
public class ClearingSettlementServiceImpl implements ClearingSettlementService {

    @Autowired
    private ClearingSettlementMapper mapper;

    private final static Logger logger = LoggerFactory.getLogger(ClearingSettlementServiceImpl.class);

    @Transactional
    @Override
    public void distributionData() {
        //1.获取DB系统时间
        String sysdate = mapper.getSysdate();
        logger.info("[清算]sysdate==" + sysdate);
        //2.支付网关清算
        settlementBank(sysdate);
        //3.通卡公司清算
        settlementYkt(sysdate);
        //4.商户清算
        settlementMer(sysdate);
    }

    /*
     * 支付网关清算
     */
    private void settlementBank(String sysdate) {
        //1.插入支付网关表
        logger.info("[清算]支付网关start");
        mapper.insertBank(sysdate);
        logger.info("[清算]支付网关end");

        //2.插入支付网关业务类型表
        logger.info("[清算]支付网关业务类型start");
        mapper.insertBankTxn(sysdate);
        logger.info("[清算]支付网关业务类型end");

        //3.更新清分基础表数据
        logger.info("[清算]更新基础数据-支付网关start");
        mapper.updateBank(sysdate);
        logger.info("[清算]更新基础数据-支付网关end");
    }

    /*
     * 一卡通清算
     */
    private void settlementYkt(String sysdate) {
        //1.插入通卡公司表
        logger.info("[清算]通卡公司start");
        mapper.insertYkt(sysdate);
        logger.info("[清算]通卡公司end");

        //2.插入通卡公司城市表
        logger.info("[清算]通卡公司城市start");
        mapper.insertYktCity(sysdate);
        logger.info("[清算]通卡公司城市end");

        //3.更新清分基础表数据
        logger.info("[清算]更新基础数据-通卡公司start");
        mapper.updateYkt(sysdate);
        logger.info("[清算]更新基础数据-通卡公司end");
    }

    /*
     * 商户清算
     */
    private void settlementMer(String sysdate) {
        //1.插入商户表
        logger.info("[清算]商户start");
        mapper.insertMer(sysdate);
        logger.info("[清算]商户end");

        //2.插入商户表
        logger.info("[清算]商户业务类型start");
        mapper.insertMerTxn(sysdate);
        logger.info("[清算]商户业务类型end");

        //3.更新清分基础表数据
        logger.info("[清算]更新基础数据-商户start");
        mapper.updateMer(sysdate);
        logger.info("[清算]更新基础数据-商户end");
    }

    /**
     * 分润汇总数据信息查询
     * @return
     */

    @Override
    public List<ProfitCollect> queryProfitCollect() {
        return mapper.queryProfitCollect();
    }

    /**
     * 分润汇总数据处理
     * @param profitCollect
     */
    @Transactional
    @Override
    public void profitCollect(ProfitCollect profitCollect) throws SQLException{
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
        //根据客户号查询客户名称
        String customerName = mapper.queryMerchantName(profitCollect.getCustomerCode());
        profitCollect.setCollectDate(dateStr);
        profitCollect.setCustomerName(customerName);
        mapper.addProfitCollect(profitCollect);
    }

    public void deleteProfitCollect(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
        //先删除当天生成的数据
        mapper.deleteProfitCollect(dateStr);
    }
}
