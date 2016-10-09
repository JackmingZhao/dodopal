package com.dodopal.payment.business.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.constant.DefaultBankConstant;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.BankGatewayTypeEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.payment.business.dao.PayConfigMapper;
import com.dodopal.payment.business.dao.PayWayExternalMapper;
import com.dodopal.payment.business.dao.PayWayGeneralMapper;
import com.dodopal.payment.business.model.PayConfig;
import com.dodopal.payment.business.model.query.PayConfigQuery;
import com.dodopal.payment.business.service.PayConfigService;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月28日 下午7:28:01
 */
@Service
public class PayConfigServiceImpl implements PayConfigService{
    @Autowired
    PayConfigMapper payConfigMapper;
    
    @Autowired
    private PayWayGeneralMapper generalMapper;
    
    @Autowired
    PayWayExternalMapper payWayExternalMapper;
    
    @Transactional
    public void savePayConfig(PayConfig payConfig) {
        payConfig.setActivate(ActivateEnum.ENABLE.getCode());
        payConfigMapper.savePayConfig(payConfig);
    }

    @Transactional(readOnly = true)
    public DodopalDataPage<PayConfig> findPayConfigByPage(PayConfigQuery query) {
        List<PayConfig> result = payConfigMapper.findPayConfigByPage(query);
        DodopalDataPage<PayConfig> pages = new DodopalDataPage<PayConfig>(query.getPage(), result);
        return pages;
    }
    
   
    
    @Transactional(readOnly = true)
    public  List<PayConfig> findPayConfigPayType(String payType){
        return payConfigMapper.findPayConfigPayType(payType);
    }

    

    @Transactional(readOnly = true)
    public PayConfig findPayConfigById(String id) {
        return payConfigMapper.findPayConfigById(id);
    }

    @Transactional(readOnly = true)
    public Integer findPayConfigByAnotherAccountCode(String anotherCode, String payType) {
        return payConfigMapper.findPayConfigByAnother(anotherCode, payType);
    }
    
    @Transactional(readOnly = true)
    public int findPayConfigByPayWayName(String payWayName,String id) {
        return payConfigMapper.findPayConfigByPayWayName(payWayName, id);
    }
    @Transactional
    public int batchActivatePayConfig(String flag,List<String> ids,String updateUser) {
        int updateRow = 0;
        if(!ids.isEmpty()){
            Map<String, Object> map  =  new HashMap<String, Object>();
            map.put("activate", flag);
            map.put("list", ids);
            map.put("updateUser", updateUser);
            updateRow = payConfigMapper.startOrStopPayConfig(map);
                if(ActivateEnum.DISABLE.getCode().equals(flag)){
                    generalMapper.disableByPayConfigId(map);
                    payWayExternalMapper.disableByPayConfigId(map);
                }
        }
        return updateRow;
    }

    @Transactional
    public int updatePayConfig(PayConfig payConfig) {
        return payConfigMapper.updatePayConfig(payConfig);
    }

    @Transactional
    public void updatePayConfigBankGateway(List<String> ids,String updateUser,String payConfigId,BankGatewayTypeEnum toBankGateWayType) {
        PayConfig toPayconfig = new PayConfig();
        toPayconfig = payConfigMapper.findPayConfigById(payConfigId);
        for(String tempId : ids){
            PayConfig temPayConfig = payConfigMapper.findPayConfigById(tempId);
            temPayConfig.setPayChannelMark(toPayconfig.getPayChannelMark());
            temPayConfig.setPayKey(toPayconfig.getPayKey());
            temPayConfig.setUpdateUser(updateUser);
            try {
                //获取默认银行的数据
                temPayConfig.setDefaultBank(defaultBankChange(temPayConfig.getBankGatewayType(),temPayConfig.getDefaultBank(),toBankGateWayType.getCode(),temPayConfig.getGatewayNumber()));
            }
            catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            temPayConfig.setProceRate(toPayconfig.getProceRate());
            temPayConfig.setAfterProceRate(toPayconfig.getAfterProceRate());
            temPayConfig.setAfterProceRateDate(toPayconfig.getAfterProceRateDate());
            temPayConfig.setAnotherAccountCode(toPayconfig.getAnotherAccountCode());
            temPayConfig.setBankGatewayType(toPayconfig.getBankGatewayType());
            temPayConfig.setBankGatewayName(toPayconfig.getBankGatewayName());
            payConfigMapper.updatePayConfigBankGateway(temPayConfig);
        }
    }
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: defaultBankChange 
     * @Description: 默认银行网关切换 关键匹配数据为原默认银行与ddpGw
     * @param oldBankGateWayType 旧的银行网关类型
     * @param oldDefaultBank  旧的默认银行
     * @param toBankGateWayType 要转换成的银行网关类型
     * @param ddpGw 网关号
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException    设定文件 
     * String    返回类型 
     * @throws 
     */
    private  String defaultBankChange(String oldBankGateWayType,String oldDefaultBank,String toBankGateWayType,String ddpGw) throws IllegalArgumentException, IllegalAccessException{
       // BankGatewayTypeEnum
        Map<String,Object> defaultBankMap = getBankConstant();
        String toGateTypeFlag = BankGatewayTypeEnum.getBankGatewayTypeByCode(toBankGateWayType).toString();
        String oldGateTypeFlag= BankGatewayTypeEnum.getBankGatewayTypeByCode(oldBankGateWayType).toString();
        String oldBankName = null;
            Set<String> set = defaultBankMap.keySet();
            for (String s:set) {
               if(defaultBankMap.get(s).equals(oldDefaultBank)){
                   oldBankName = s;
                   break;
               }
            }
            //这里添加两个银行有相同的默认银行时的特殊处理 start
            if(ddpGw.indexOf(DefaultBankConstant.SDB_GW_TENG)>-1){
                if(toBankGateWayType.equals(BankGatewayTypeEnum.GW_TENG.getCode())){
                    return DefaultBankConstant.SDB_GW_TENG;
                }else{
                    return DefaultBankConstant.SDB_GW_ALI;
                }
            }else if(ddpGw.indexOf(DefaultBankConstant.PAB_GW_TENG)>-1){
                if(toBankGateWayType.equals(BankGatewayTypeEnum.GW_TENG.getCode())){
                    return DefaultBankConstant.PAB_GW_TENG;
                }else{
                    return DefaultBankConstant.SPABANK_GW_ALI;
                }
            }
            //特殊处理 end
            //根据默认银行名称及默认银行的类型flag来查找 
            /**
             * GW_ALI/GW_TENG
             */
            String[] realBankName = oldBankName.split(oldGateTypeFlag);
            //比如ICBC_GW_ALI -->ICBC
            if(null!=(String) defaultBankMap.get(realBankName[0]+toGateTypeFlag)){
                //使用旧的网关类型切到银行的名称，并拼接需要转换的key 加入get不等于null的时候
                //map.get(ICBC_GW_TENG)
                return  (String) defaultBankMap.get(realBankName[0]+toGateTypeFlag);
            }else{
                //否则通过split切换匹配并从map获取值
                realBankName = oldBankName.split(toGateTypeFlag);
                return (String) defaultBankMap.get(realBankName[0]+toGateTypeFlag);
            }
    }
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: getBankConstant 
     * @Description: 从常量内获取所有的银行支付宝与财付通的数据
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException    设定文件 
     * Map<String,Object>    返回类型 
     * @throws 
     */
    private  Map<String,Object> getBankConstant() throws IllegalArgumentException, IllegalAccessException {
        DefaultBankConstant bankConstant = new DefaultBankConstant();
        Field[] fields = bankConstant.getClass().getDeclaredFields();  
        Map<String,Object> resultMap = new HashMap<String,Object>();
          for(int i=0;fields!=null && i<fields.length;i++){  
              String modifier = Modifier.toString(fields[i].getModifiers());
              if (modifier != null && modifier.indexOf("final")> -1) {
                    resultMap.put(fields[i].getName(), fields[i].get(fields[i].getName()));
              }
          }
          return resultMap;
    }
    /**
     * @description 查询支付配置参数
     * @return
     */
    @Transactional(readOnly = true)
    public PayConfig queryPayInfo(String tranCode) {
        return payConfigMapper.queryPayInfo(tranCode);
    }
    /**
     * @description 根据支付方式id查询支付配置信息
     * @param payWayId (支付方式id)
     * @return PayConfig
     */
    public PayConfig queryPayInfoByPayWayId(String payWayId) {
        return payConfigMapper.queryPayInfoByPayWayId(payWayId);
    }
}
