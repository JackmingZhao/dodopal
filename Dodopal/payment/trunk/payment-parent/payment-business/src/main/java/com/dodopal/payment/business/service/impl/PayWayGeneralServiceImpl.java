package com.dodopal.payment.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.payment.business.dao.PayConfigMapper;
import com.dodopal.payment.business.dao.PayWayGeneralMapper;
import com.dodopal.payment.business.model.PayConfig;
import com.dodopal.payment.business.model.PayWayGeneral;
import com.dodopal.payment.business.model.query.PayWayQuery;
import com.dodopal.payment.business.service.PayWayGeneralService;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class PayWayGeneralServiceImpl implements PayWayGeneralService {
    
    @Autowired
    private PayWayGeneralMapper generalMapper;
    @Autowired
    private PayConfigMapper  payConfigMapper;

    /**
     * 启用停用
     */
    public int batchStartOrStop(String flag, List<String> ids, String updateUser) {
        int response = generalMapper.startOrStopUser(flag, updateUser, ids);
        return response;
    }

    /**
     * 删除
     */
    public int deletePayWayGeneral(List<String> ids) {
        return generalMapper.deletePayWayGeneral(ids);
    }
    
    /**
     * 新增
     */
    public int savePayWayGeneral(PayWayGeneral general) {
        return  generalMapper.savePayWayGeneral(general);
    }
    
    /**
     * 查询
     */
    public DodopalDataPage<PayWayGeneral> findPayWayGeneralByPage(PayWayQuery payWayQuery) {
        List<PayWayGeneral> generalByPage = generalMapper.findPayWayGeneralByPage(payWayQuery);
        DodopalDataPage<PayWayGeneral> dodopalDataPage = new DodopalDataPage<PayWayGeneral>(payWayQuery.getPage(), generalByPage);
        return dodopalDataPage;
    }

    /**
     * 修改
     */
    public int updatePayWayGeneral(PayWayGeneral general) {
        return generalMapper.updatePayWayGeneral(general);
    }


    /**
     * 根据id查询
     */
    @Transactional(readOnly = true)
    public PayWayGeneral findPayGeneralById(String id) {
        return generalMapper.findPayWayGeneralById(id);
    }
    
    /**
     * 根据支付类型查询支付名称
     */
    @Transactional(readOnly = true)
    public List<PayConfig>  findPayWayNameByPayType(String payType){
        return payConfigMapper.findPayConfigPayType(payType);
   }

    /**
     * 查询payConfig记录数
     */
    @Transactional(readOnly = true)
    public int countByPayConfigId(String payConfigId) {
        return generalMapper.countByPayConfigId(payConfigId);
    }
    /**
     * 根据支付方式id或者支付配置信息id
     * @param payWayId
     * @return
     */
    @Override
    public PayWayGeneral queryPayConfigId(String payWayId) {
        return generalMapper.queryPayConfigId(payWayId);
    }
    
    /**
     * 根据支付方式id或者支付配置信息id
     * @param payWayId 支付方式id
     * @return
     */
    @Transactional(readOnly = true)
    public PayWayGeneral queryPayConfigIdByPayWayId(String payWayId) {
        return generalMapper.queryPayConfigIdByPayWayId(payWayId);
    }
    
}
