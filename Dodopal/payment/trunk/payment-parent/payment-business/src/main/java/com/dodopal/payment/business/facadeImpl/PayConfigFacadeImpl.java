package com.dodopal.payment.business.facadeImpl;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.payment.dto.PayConfigDTO;
import com.dodopal.api.payment.dto.query.PayConfigQueryDTO;
import com.dodopal.api.payment.facade.PayConfigFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.BankGatewayTypeEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.payment.business.model.PayConfig;
import com.dodopal.payment.business.model.query.PayConfigQuery;
import com.dodopal.payment.business.service.PayConfigService;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月28日 下午8:18:03
 */
@Service("payConfigFacade")
public class PayConfigFacadeImpl implements PayConfigFacade {

    private final static Logger log = LoggerFactory.getLogger(PayConfigFacadeImpl.class);

    @Autowired
    PayConfigService payConfigService;

    public DodopalResponse<Boolean> savePayConfig(PayConfigDTO payConfigDTO, PayTypeEnum payTypeEnum) {
        DodopalResponse<Boolean> dodopalResponse = new DodopalResponse<Boolean>();
        String checkMessage = checkPayConfigDTO(payConfigDTO, payTypeEnum);
        //进行参数校验
        if(StringUtils.isNotBlank(checkMessage)){
            dodopalResponse.setCode(checkMessage);
            return dodopalResponse;
        }
        PayConfig payConfig = new PayConfig();
        try {
            //参数转换
            PropertyUtils.copyProperties(payConfig, payConfigDTO);
        
            String accountMessage=checkAnotherAccount(payConfigDTO);
            //账户支付
            if (PayTypeEnum.PAY_ACT.getCode().equals(payTypeEnum.getCode())) {
    
                //一卡通支付    
            } else if (PayTypeEnum.PAY_CARD.getCode().equals(payTypeEnum.getCode())) {
                //构造image图片名称
                payConfig.setImageName((payConfigDTO.getPayWayName() + ".png"));
                if(StringUtils.isNotBlank(accountMessage)){
                    dodopalResponse.setCode(checkMessage);
                    return dodopalResponse;
                }
                payConfigService.savePayConfig(payConfig);
                
                //在线支付    
            } else if (PayTypeEnum.PAY_ONLINE.getCode().equals(payTypeEnum.getCode())) {
                 if(StringUtils.isNotBlank(accountMessage)){
                     dodopalResponse.setCode(checkMessage);
                     return dodopalResponse;
                 }
    
                //银行支付    
            } else if (PayTypeEnum.PAY_BANK.getCode().equals(payTypeEnum.getCode())) {
                 if(StringUtils.isNotBlank(accountMessage)){
                     dodopalResponse.setCode(checkMessage);
                     return dodopalResponse;
                 }
            }
            dodopalResponse.setCode(ResponseCode.SUCCESS);
        }catch (Exception e) {
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
            log.debug("PayConfigFacadeImpl call error", e);
            e.printStackTrace();
        }
        return dodopalResponse;
    }

    private String checkPayConfigDTO(PayConfigDTO payConfigDTO,PayTypeEnum payTypeEnum){
        if(null==payConfigDTO){
            if (log.isDebugEnabled()) {
                log.debug("Check PayConfigDTO is null,return ResponseCode PAY_CONFIG_NULL");
            }
            return ResponseCode.PAY_CONFIG_NULL;
        }else if(null==payTypeEnum||StringUtils.isBlank(payConfigDTO.getPayType())){
            if (log.isDebugEnabled()) {
                log.debug("Check PayConfigDTO.PayType is null,return ResponseCode PAY_TYPE_NULL");
            }
            return ResponseCode.PAY_TYPE_NULL;
        }else if(StringUtils.isBlank(payConfigDTO.getPayTypeName())){
            if (log.isDebugEnabled()) {
                log.debug("Check PayConfigDTO.PayTypeName is null,return ResponseCode PAY_TYPE_NULL");
            }
            return ResponseCode.PAY_TYPE_NAME_NULL;
        }else if (StringUtils.isBlank(payConfigDTO.getPayWayName())){
            if (log.isDebugEnabled()) {
                log.debug("Check PayConfigDTO.PayTypeName is null,return ResponseCode PAY_TYPE_NULL");
            }
            return ResponseCode.PAY_WAY_NAME_NULL;
        }else if (payConfigDTO.getProceRate()<0&&payConfigDTO.getProceRate()>=10000){
            if (log.isDebugEnabled()) {
                log.debug("Check PayConfigDTO.PayTypeName is null,return ResponseCode PAY_ICDC_CITYNAME_NULL");
            }
            return ResponseCode.PAY_ICDC_CITYNAME_NULL;
        }else if (StringUtils.isBlank(payConfigDTO.getGatewayNumber())) {
            if (log.isDebugEnabled()) {
                log.debug("Check PayConfigDTO.gatewayNumber is null,return ResponseCode PAY_GATEWAY_NUMBER_NULL");
            }
            return ResponseCode.PAY_GATEWAY_NUMBER_NULL;
        }
        return null;
    }
    
    private String checkAnotherAccount(PayConfigDTO payConfigDTO){
        if(StringUtils.isBlank(payConfigDTO.getAnotherAccountCode())){
           return ResponseCode.PAY_ANOTHER_ACCOUNT_CODE_NULL;
        }
        return null;
    }

    @Override
    public DodopalResponse<DodopalDataPage<PayConfigDTO>> findPayConfigByPage(PayConfigQueryDTO queryDTO) {
        DodopalResponse<DodopalDataPage<PayConfigDTO>> dodopalResponse = new DodopalResponse<DodopalDataPage<PayConfigDTO>>();
        PayConfigQuery payConfigQuery = new PayConfigQuery();
        try {
            PropertyUtils.copyProperties(payConfigQuery, queryDTO);
            DodopalDataPage<PayConfig> pagelist = payConfigService.findPayConfigByPage(payConfigQuery);
            List<PayConfig> payConfigList = pagelist.getRecords();
            List<PayConfigDTO> payDTOList = new ArrayList<PayConfigDTO>();
            if (CollectionUtils.isNotEmpty(payConfigList)) {
                for (PayConfig payConfigTemp : payConfigList) {
                    PayConfigDTO PayConfigDTO = new PayConfigDTO();
                    PropertyUtils.copyProperties(PayConfigDTO, payConfigTemp);
                    payDTOList.add(PayConfigDTO);
                }
            }
            PageParameter page = DodopalDataPageUtil.convertPageInfo(pagelist);
            DodopalDataPage<PayConfigDTO> pages = new DodopalDataPage<PayConfigDTO>(page, payDTOList);
            dodopalResponse.setCode(ResponseCode.SUCCESS);
            dodopalResponse.setResponseEntity(pages);
        }
        catch (Exception e) {
            log.error("PaymentFacadeImpl call error", e);
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }

    @Override
    public DodopalResponse<PayConfigDTO> findPayConfigById(String id) {
        DodopalResponse<PayConfigDTO> dodopalResponse = new DodopalResponse<PayConfigDTO>();
        PayConfigDTO configDTO = new PayConfigDTO();
        PayConfig payConfig = payConfigService.findPayConfigById(id);
        if(null!=payConfig){
            try {
                PropertyUtils.copyProperties(configDTO, payConfig);
                dodopalResponse.setCode(ResponseCode.SUCCESS);
            }catch (Exception e) {
                log.debug("PaymentFacadeImpl call error", e);
                e.printStackTrace();
                dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
            }
        }
        dodopalResponse.setResponseEntity(configDTO);
        return dodopalResponse;
    }

    /* (non-Javadoc)
     * 根据接口定义List内存放的map
     * key 分别为cityCode城市编码   cityName城市名称
     * rate费率
     */
    @Override
    public DodopalResponse<Boolean> icdcPayCreate(List<Map<String, Object>> list) {
        DodopalResponse<Boolean> dodopalResponse = new DodopalResponse<Boolean>();
        String checkMessage = checkIcdcPay(list);
        if(StringUtils.isNotBlank(checkMessage)){
            if (log.isInfoEnabled()) {
                log.info("icdcPayCreate Method Check list Data error,return ResponseCode [{}]",checkMessage);
            }
            dodopalResponse.setCode(checkMessage);
            dodopalResponse.setResponseEntity(false);
            return dodopalResponse;
        }
        try {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date afterDate;
            //初始化时保存后手续费率的生效时间
            afterDate = dateFormat.parse("2099-12-31 00:00:00");
            if(CollectionUtils.isNotEmpty(list)){
                for(Map<String,Object>tempMap:list){
                    String cityCode = (String) tempMap.get("cityCode");
                    String bankGatewayType = (String) tempMap.get("bankGatewayType");
                    String bankGatewayName = (String) tempMap.get("bankGatewayName");
                    Double rate = null;
                    
                    if(tempMap.get("rate") instanceof Double){
                        rate = (Double)tempMap.get("rate");
                    }else{
                        rate = Double.parseDouble((String)tempMap.get("rate"));
                    }
                    PayConfig payconfig = new PayConfig();
                    payconfig.setAfterProceRateDate(afterDate);
                    payconfig.setImageName((cityCode + ".png"));
                    payconfig.setGatewayNumber("DDP-"+cityCode);
                    payconfig.setAfterProceRate(rate);
                    payconfig.setProceRate(rate);
                    payconfig.setBankGatewayName(bankGatewayName);
                    payconfig.setBankGatewayType(bankGatewayType);
                    payconfig.setPayWayName((String) tempMap.get("cityName"));
                    payconfig.setAnotherAccountCode(cityCode); 
                    payconfig.setPayType(PayTypeEnum.PAY_CARD.getCode());
                    payconfig.setPayTypeName(PayTypeEnum.PAY_CARD.getName());
                    if(payConfigService.findPayConfigByAnotherAccountCode(cityCode, PayTypeEnum.PAY_CARD.getCode())<1){
                        payConfigService.savePayConfig(payconfig);
                    }
                    if (log.isInfoEnabled()) {
                        log.info("save Success——icdcPayCreate");
                    }
                }
                dodopalResponse.setResponseEntity(true);
                dodopalResponse.setCode(ResponseCode.SUCCESS);
            }
        }catch (Exception e) {
            log.error("PaymentFacadeImpl call error", e);
            e.printStackTrace();
            dodopalResponse.setResponseEntity(false);
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }
    
    private  String checkIcdcPay(List<Map<String, Object>> list){
        if(CollectionUtils.isNotEmpty(list)){
            for(Map<String,Object>tempMap:list){
                if(StringUtils.isBlank((String)tempMap.get("cityCode"))){
                    return ResponseCode.PAY_ICDC_CITYCODE_NULL;
                }
                if(StringUtils.isBlank((String)tempMap.get("cityName"))){
                    return ResponseCode.PAY_ICDC_CITYNAME_NULL;
                }
            }
        }
        return null;
    }

    @Override
    public DodopalResponse<Boolean> batchActivatePayConfig(String flag, List<String> ids, String updateUser) {
        DodopalResponse<Boolean> dodopalResponse = new DodopalResponse<Boolean>();
        int rows = payConfigService.batchActivatePayConfig(flag, ids, updateUser);
        dodopalResponse.setCode(ResponseCode.SUCCESS);
        dodopalResponse.setResponseEntity(false);
        return dodopalResponse;
    }

    @Override
    public DodopalResponse<Boolean> updatePayConfig(PayConfigDTO payConfigDTO) {
        DodopalResponse<Boolean> dodopalResponse = new DodopalResponse<Boolean>();
        String checkCode = checkUpdate(payConfigDTO);
        if(StringUtils.isNotBlank(checkCode)){
            dodopalResponse.setCode(checkCode);
            return dodopalResponse;
        }
        PayConfig payconfig = new PayConfig();
        try {
            PropertyUtils.copyProperties(payconfig, payConfigDTO);
            payConfigService.updatePayConfig(payconfig);
            dodopalResponse.setResponseEntity(true);
            dodopalResponse.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            log.error("PaymentFacadeImpl call error", e);
            e.printStackTrace();
            dodopalResponse.setResponseEntity(false);
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }
    
    private String checkUpdate(PayConfigDTO payConfigDTO){
        if(null==payConfigDTO){
            return ResponseCode.PAY_CONFIG_NULL;
        }else if (StringUtils.isBlank(payConfigDTO.getPayWayName())){
            return ResponseCode.PAY_WAY_NAME_NULL;
        }else if(payConfigDTO.getAfterProceRate()<0&&payConfigDTO.getProceRate()>=10000){
            return ResponseCode.PAY_RATE_NULL;
        }else if(payConfigService.findPayConfigByPayWayName(payConfigDTO.getPayWayName(), payConfigDTO.getId())>0){
            return ResponseCode.PAY_WAY_NAME_REPEAT;
        }
        return null;
    }

    @Override
    public DodopalResponse<Boolean> changeGatewayNumber(List<String> ids,String updateUser,String payConfigId,BankGatewayTypeEnum toBankGateWayType) {
        DodopalResponse<Boolean> dodopalResponse = new DodopalResponse<Boolean>();
        if(StringUtils.isBlank(payConfigId)){
            dodopalResponse.setCode(ResponseCode.PAY_TO_GW_ALI_ID_NULL);
        }
        try{
             payConfigService.updatePayConfigBankGateway(ids, updateUser, payConfigId,toBankGateWayType);
             dodopalResponse.setResponseEntity(true);
             dodopalResponse.setCode(ResponseCode.SUCCESS);
        }catch(Exception e){
            log.error("PaymentFacadeImpl call error", e);
            e.printStackTrace();
            dodopalResponse.setResponseEntity(false);
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }

   
    
}
