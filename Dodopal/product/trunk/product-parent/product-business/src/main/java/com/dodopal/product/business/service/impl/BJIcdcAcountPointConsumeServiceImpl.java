/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.product.business.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.card.dto.BJAccountConsumeDTO;
import com.dodopal.api.card.dto.BJIntegralConsumeDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.YktAPCOStatesEnum;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.AreaService;
import com.dodopal.common.util.DateUtils;
import com.dodopal.product.business.dao.YktConsumeRecordMapper;
import com.dodopal.product.business.model.ProductYKT;
import com.dodopal.product.business.model.YktConsumeRecord;
import com.dodopal.product.business.service.BJIcdcAcountPointConsumeService;
import com.dodopal.product.business.service.ProductYKTService;
import com.dodopal.product.delegate.BJIcdcAcountPointConsumeDelegate;
import com.dodopal.product.delegate.BJIcdcPurchaseDelegate;

/**
 * 北京通卡账户积分消费ServiceImpl实现
 */
@Service
public class BJIcdcAcountPointConsumeServiceImpl implements BJIcdcAcountPointConsumeService {

    private final static Logger log = LoggerFactory.getLogger(BJIcdcAcountPointConsumeServiceImpl.class);

    @Autowired
    private BJIcdcAcountPointConsumeDelegate bjIcdcAcountPointConsumeDelegate;
    @Autowired
    private BJIcdcPurchaseDelegate bjIcdcPurchaseDelegate;
    @Autowired
    private ProductYKTService productYKTService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private YktConsumeRecordMapper yktConsumeRecordMapper;

    /**
     * 消费业务：城市、通卡、商户、POS相关校验
     * @param cityCode
     * @param posId
     * @return
     */
    private DodopalResponse<Map<String, String>> consumeCheck(String cityCode, String posId){
        DodopalResponse<Map<String, String>> mapResponse = new DodopalResponse<Map<String, String>>();
        mapResponse.setCode(ResponseCode.SUCCESS);

        // 根据城市编号查询通卡编号
        Area area = areaService.findCityByCityCode(cityCode);
        if (null == area) {
            mapResponse.setCode(ResponseCode.PRODUCT_CHECK_YKT_STATUS_DISABLE);
            return mapResponse;
        }
        ProductYKT productYKT = productYKTService.getYktInfoByBusinessCityCode(cityCode);
        if (null == productYKT) {
            mapResponse.setCode(ResponseCode.PRODUCT_CHECK_YKT_STATUS_DISABLE);
            return mapResponse;
        }
        // 检验通卡公司的合法性
        DodopalResponse<ProductYKT> yktResponse = productYKTService.validateYktServiceNormalForIcdcConsume(productYKT.getYktCode());
        if (!ResponseCode.SUCCESS.equals(yktResponse.getCode())) {
            mapResponse.setCode(yktResponse.getCode());
            return mapResponse;
        }
        productYKT = yktResponse.getResponseEntity();

        // 调用用户系统,验证当前商户/操作人员是否合法
        DodopalResponse<Map<String, String>> merCheckResponse = null;
        try {
            merCheckResponse = bjIcdcPurchaseDelegate.validateMerchantForIcdcPurchase("", "", posId, productYKT.getYktCode(), "");
        }
        catch (HessianRuntimeException e) {
            mapResponse.setCode(ResponseCode.PRODUCT_CALL_USERS_ERROR);
            return mapResponse;
        }
        if (!ResponseCode.SUCCESS.equals(merCheckResponse.getCode())) {
            mapResponse.setCode(merCheckResponse.getCode());
            return mapResponse;
        }

        Map<String, String> merInfoMap = merCheckResponse.getResponseEntity();
        merInfoMap.put("yktCode", productYKT.getYktCode());
        merInfoMap.put("yktName", productYKT.getYktName());
        merInfoMap.put("cityName", area.getCityName());
        
        mapResponse.setResponseEntity(merInfoMap);
        return mapResponse;
    }
    /**
     * (账户消费：Z ;积分消费：J)+ 20150428222211 +五位数据库cycle sequence（循环使用）
     */
    @Transactional(readOnly = true)
    private String getOrderNum(String businessType) {
        String orderNum = "";
        if (RateCodeEnum.YKT_CONSUME_ACOUNT.getCode().equals(businessType)) {
            orderNum = "Z";
        } else if (RateCodeEnum.YKT_CONSUME_POINT.getCode().equals(businessType)) {
            orderNum = "J";
        }
        String timeStr = DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR);
        orderNum += timeStr;
        return orderNum + yktConsumeRecordMapper.getOrderNumSeq();
    }

    /****************************** 北京通卡“账户消费”业务流程接口 start ******************************/
    /**
     * 通卡账户消费申请
     * @param bjAccountConsumeDTO
     * @return
     */
    @Override
    public DodopalResponse<BJAccountConsumeDTO> applyYktAcountConsume(BJAccountConsumeDTO bjAccountConsumeDTO) {
        DodopalResponse<BJAccountConsumeDTO> response = new DodopalResponse<BJAccountConsumeDTO>();
        response.setCode(ResponseCode.SUCCESS);

        if (log.isInfoEnabled()) {
            log.info("通卡账户消费申请接口:入参:" + JSONObject.toJSONString(bjAccountConsumeDTO));
        }

        // 消费业务：城市、通卡、商户、POS相关校验
        DodopalResponse<Map<String, String>> mapResponse = this.consumeCheck(bjAccountConsumeDTO.getCitycode(), bjAccountConsumeDTO.getPoscode());
        
        if (!ResponseCode.SUCCESS.equals(mapResponse.getCode())) {
            response.setCode(mapResponse.getCode());
            response.setResponseEntity(bjAccountConsumeDTO);
            return response;
        }
        Map<String, String> merInfoMap = mapResponse.getResponseEntity();
        String merCode = merInfoMap.containsKey("merCode") ? merInfoMap.get("merCode") : "";
        String merName = merInfoMap.containsKey("merName") ? merInfoMap.get("merName") : "";
        String merType = merInfoMap.containsKey("merType") ? merInfoMap.get("merType") : "";
        String userId = merInfoMap.containsKey("userId") ? merInfoMap.get("userId") : "";
        String yktCode = merInfoMap.containsKey("yktCode") ? merInfoMap.get("yktCode") : "";
        String yktName = merInfoMap.containsKey("yktName") ? merInfoMap.get("yktName") : "";
        String cityName = merInfoMap.containsKey("cityName") ? merInfoMap.get("cityName") : "";
        if (StringUtils.isEmpty(bjAccountConsumeDTO.getMercode().replaceAll("0", ""))) {
            bjAccountConsumeDTO.setMercode(merCode);
        }

        YktConsumeRecord record = null;
        if (StringUtils.isBlank(bjAccountConsumeDTO.getProordernum().replaceAll("0", ""))) {

            // 当产品库收到对应的产品库订单号为全0时，生成账户消费的产品库订单，添加订单状态为订单创建成功
            record = new YktConsumeRecord();
            record.setOrderNum(this.getOrderNum(RateCodeEnum.YKT_CONSUME_ACOUNT.getCode()));
            record.setYktCode(yktCode);
            record.setYktName(yktName);
            record.setCityCode(bjAccountConsumeDTO.getCitycode());
            record.setCityName(cityName);
            record.setMerCode(merCode);
            record.setMerName(merName);
            record.setMerType(merType);
            record.setBusinessType(RateCodeEnum.YKT_CONSUME_ACOUNT.getCode());
            record.setCardNum(bjAccountConsumeDTO.getCardno());
            record.setPosCode(bjAccountConsumeDTO.getPoscode());
            record.setPosType(bjAccountConsumeDTO.getPostype());
            record.setYktAcountAmt(Long.parseLong(bjAccountConsumeDTO.getTxnamt()));
            //record.setYktPointAmt(0l);
            record.setStates(YktAPCOStatesEnum.CREATION_SUCCESS.getCode());
            //record.setBeforeStates("");
            record.setOrderDate(new Date());
            record.setOrderDay(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDD_STR));
            record.setOperId(bjAccountConsumeDTO.getOperid());
            record.setCreateUser(userId);
            record.setCreateDate(new Date());
            yktConsumeRecordMapper.addOrderRecord(record);
            bjAccountConsumeDTO.setProordernum(record.getOrderNum());

        } else {

            // 当产品库收到对应的产品库订单号非全0时(多账户情况)，不生成订单,判断前状态是订单创建成功
            record = yktConsumeRecordMapper.selectByOrderNum(bjAccountConsumeDTO.getProordernum());

            if (null == record) {
                response.setCode(ResponseCode.PRODUCT_CHECK_CARD_ORDER_NOT_EXIST);
                response.setResponseEntity(bjAccountConsumeDTO);
                return response;
            }

            if (!YktAPCOStatesEnum.CREATION_SUCCESS.getCode().equals(record.getStates())) {
                response.setCode(ResponseCode.PRODUCT_CHECK_CARD_ORDER_STATUS_ERROR);
                response.setResponseEntity(bjAccountConsumeDTO);
                return response;
            }

        }

        // 调用卡服务账户消费申请查询接口
        try {
            response = bjIcdcAcountPointConsumeDelegate.applyYktAcountConsume(bjAccountConsumeDTO);
        }
        catch (HessianRuntimeException e) {
            response.setCode(ResponseCode.PRODUCT_CALL_CARD_ERROR);
            response.setResponseEntity(bjAccountConsumeDTO);
            return response;
        }
        if (null == response.getResponseEntity()) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            response.setResponseEntity(bjAccountConsumeDTO);
            return response;
        }

        // 根据卡服务的返回交易应答码并且结束标志为1时更新订单状态为账户消费申请成功或账户消费申请失败
        if ("1".equals(response.getResponseEntity().getTradeendflag())) {
            YktConsumeRecord newRecord = new YktConsumeRecord();
            newRecord.setOrderNum(bjAccountConsumeDTO.getProordernum());
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                newRecord.setStates(YktAPCOStatesEnum.CONSUME_APPLY_SUCCESS.getCode());
            } else {
                newRecord.setStates(YktAPCOStatesEnum.CONSUME_APPLY_FAILED.getCode());
            }
            newRecord.setBeforeStates(YktAPCOStatesEnum.CREATION_SUCCESS.getCode());
            newRecord.setUpdateUser(record.getCreateUser());
            newRecord.setUpdateDate(new Date());
            yktConsumeRecordMapper.updateByOrderNum(newRecord);
        }

        if (log.isInfoEnabled()) {
            log.info("通卡账户消费申请接口:返回响应码:" + response.getCode() + ",完整响应体:" + JSONObject.toJSONString(response));
        }
        return response;
    }

    /**
     * 通卡账户消费结果上送(成功/失败/取消)
     * @param bjAccountConsumeDTO
     * @return
     */
    @Override
    public DodopalResponse<BJAccountConsumeDTO> uploadYktAcountConsume(BJAccountConsumeDTO bjAccountConsumeDTO) {
        DodopalResponse<BJAccountConsumeDTO> response = new DodopalResponse<BJAccountConsumeDTO>();
        response.setCode(ResponseCode.SUCCESS);

        if (log.isInfoEnabled()) {
            log.info("通卡账户消费结果上送接口:入参:" + JSONObject.toJSONString(bjAccountConsumeDTO));
        }

        // 消费业务：城市、通卡、商户、POS相关校验
        DodopalResponse<Map<String, String>> mapResponse = this.consumeCheck(bjAccountConsumeDTO.getCitycode(), bjAccountConsumeDTO.getPoscode());
        
        if (!ResponseCode.SUCCESS.equals(mapResponse.getCode())) {
            response.setCode(mapResponse.getCode());
            response.setResponseEntity(bjAccountConsumeDTO);
            return response;
        }

        // 取消交易无订单号时,产品库直接透传到卡服务由卡服务进行订单校验
        if (!("1".equals(bjAccountConsumeDTO.getTxnstat()) && StringUtils.isBlank(bjAccountConsumeDTO.getProordernum().replaceAll("0", "")))) {
            
            // 获取产品库订单信息
            YktConsumeRecord yktConsumeRecord = yktConsumeRecordMapper.selectByOrderNum(bjAccountConsumeDTO.getProordernum());

            if (null == yktConsumeRecord) {
                response.setCode(ResponseCode.PRODUCT_CHECK_CARD_ORDER_NOT_EXIST);
                response.setResponseEntity(bjAccountConsumeDTO);
                return response;
            }

            if ("0".equals(bjAccountConsumeDTO.getTxnstat())) {

                // 产品库当接到成功的请求时，产品库检查状态要符合（账户消费申请成功，账户消费成功）
                if (!YktAPCOStatesEnum.CONSUME_APPLY_SUCCESS.getCode().equals(yktConsumeRecord.getStates()) 
                    && !YktAPCOStatesEnum.CONSUME_SUCCESS.getCode().equals(yktConsumeRecord.getStates())) {
                    
                    response.setCode(ResponseCode.PRODUCT_CHECK_CARD_ORDER_STATUS_ERROR);
                    response.setResponseEntity(bjAccountConsumeDTO);
                    return response;
                }

                // 当状态是账户消费申请成功更新状态为账户消费成功
                if (YktAPCOStatesEnum.CONSUME_APPLY_SUCCESS.getCode().equals(yktConsumeRecord.getStates())) {
                    YktConsumeRecord record = new YktConsumeRecord();
                    record.setOrderNum(bjAccountConsumeDTO.getProordernum());
                    record.setStates(YktAPCOStatesEnum.CONSUME_SUCCESS.getCode());
                    record.setBeforeStates(yktConsumeRecord.getStates());
                    record.setUpdateUser(yktConsumeRecord.getCreateUser());
                    record.setUpdateDate(new Date());
                    yktConsumeRecordMapper.updateByOrderNum(record);
                }

            } else if ("1".equals(bjAccountConsumeDTO.getTxnstat())) {

                // 产品库当接到失败的请求时，产品库检查状态要符合（账户消费申请成功,账户消费申请失败,账户消费失败）
                if (!YktAPCOStatesEnum.CONSUME_APPLY_SUCCESS.getCode().equals(yktConsumeRecord.getStates()) 
                    && !YktAPCOStatesEnum.CONSUME_APPLY_FAILED.getCode().equals(yktConsumeRecord.getStates()) 
                    && !YktAPCOStatesEnum.CONSUME_FAILED.getCode().equals(yktConsumeRecord.getStates())) {

                    response.setCode(ResponseCode.PRODUCT_CHECK_CARD_ORDER_STATUS_ERROR);
                    response.setResponseEntity(bjAccountConsumeDTO);
                    return response;
                }

                if (YktAPCOStatesEnum.CONSUME_APPLY_SUCCESS.getCode().equals(yktConsumeRecord.getStates())) {

                    // 当状态是账户消费申请成功更新状态为账户消费失败
                    YktConsumeRecord record = new YktConsumeRecord();
                    record.setOrderNum(bjAccountConsumeDTO.getProordernum());
                    record.setStates(YktAPCOStatesEnum.CONSUME_FAILED.getCode());
                    record.setBeforeStates(yktConsumeRecord.getStates());
                    record.setUpdateUser(yktConsumeRecord.getCreateUser());
                    record.setUpdateDate(new Date());
                    yktConsumeRecordMapper.updateByOrderNum(record);

                } else if (YktAPCOStatesEnum.CONSUME_FAILED.getCode().equals(yktConsumeRecord.getStates())) {

                    // 当状态是账户消费申请失败直接返回全零应答码不需调用卡服务接口
                    response.setCode(ResponseCode.SUCCESS);
                    response.setResponseEntity(bjAccountConsumeDTO);
                    return response;
                }
            }
        } else {
            log.info("取消交易无订单号时,产品库直接透传到卡服务由卡服务进行订单校验。");
        }
      
        // 调用卡服务账户消费结果上送接口
        try {
            response = bjIcdcAcountPointConsumeDelegate.uploadYktAcountConsume(bjAccountConsumeDTO);
        }
        catch (HessianRuntimeException e) {
            response.setCode(ResponseCode.PRODUCT_CALL_CARD_ERROR);
            response.setResponseEntity(bjAccountConsumeDTO);
            return response;
        }
        
        if (log.isInfoEnabled()) {
            log.info("通卡账户消费结果上送:返回响应码:" + response.getCode() + ",完整响应体:" + JSONObject.toJSONString(response));
        }
        
        return response;
    }

    /**
     * 通卡账户消费撤销申请
     * @param bjAccountConsumeDTO
     * @return
     */
    @Override
    public DodopalResponse<BJAccountConsumeDTO> cancelYktAcountConsume(BJAccountConsumeDTO bjAccountConsumeDTO) {
        DodopalResponse<BJAccountConsumeDTO> response = new DodopalResponse<BJAccountConsumeDTO>();
        response.setCode(ResponseCode.SUCCESS);

        if (log.isInfoEnabled()) {
            log.info("通卡账户消费撤销申请接口:入参:" + JSONObject.toJSONString(bjAccountConsumeDTO));
        }

        // 消费业务：城市、通卡、商户、POS相关校验
        DodopalResponse<Map<String, String>> mapResponse = this.consumeCheck(bjAccountConsumeDTO.getCitycode(), bjAccountConsumeDTO.getPoscode());
        
        if (!ResponseCode.SUCCESS.equals(mapResponse.getCode())) {
            response.setCode(mapResponse.getCode());
            response.setResponseEntity(bjAccountConsumeDTO);
            return response;
        }

        // 获取产品库订单信息
        YktConsumeRecord yktConsumeRecord = yktConsumeRecordMapper.selectByOrderNum(bjAccountConsumeDTO.getProordernum());

        if (null == yktConsumeRecord) {
            response.setCode(ResponseCode.PRODUCT_CHECK_CARD_ORDER_NOT_EXIST);
            response.setResponseEntity(bjAccountConsumeDTO);
            return response;
        }

        // 根据产品库订单号校验订单合法性(只有产品库订单状态为账户消费成功才能进行后续交易)
        if (!YktAPCOStatesEnum.CONSUME_SUCCESS.getCode().equals(yktConsumeRecord.getStates())) {

            response.setCode(ResponseCode.PRODUCT_CHECK_CARD_ORDER_STATUS_ERROR);
            response.setResponseEntity(bjAccountConsumeDTO);
            return response;
        }
        
        // 当产品库订单状态为: 账户消费撤销成功直接返回交易应答码成功无需再调用卡服务。
        if (YktAPCOStatesEnum.CONSUME_CANCEL_SUCCESS.getCode().equals(yktConsumeRecord.getStates())) {
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(bjAccountConsumeDTO);
            return response;
        }
        
        // 调用卡服务账户消费撤销申请查询接口
        try {
            response = bjIcdcAcountPointConsumeDelegate.cancelYktAcountConsume(bjAccountConsumeDTO);
        }
        catch (HessianRuntimeException e) {
            response.setCode(ResponseCode.PRODUCT_CALL_CARD_ERROR);
            response.setResponseEntity(bjAccountConsumeDTO);
            return response;
        }
        if (null == response.getResponseEntity()) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            response.setResponseEntity(bjAccountConsumeDTO);
            return response;
        }

        // 根据卡服务的返回交易应答码并且结束标志为1时更新产品库订单状态为账户消费撤销成功或(保留原账户消费成功状态)
        if (ResponseCode.SUCCESS.equals(response.getCode()) && "1".equals(response.getResponseEntity().getTradeendflag())) {
            YktConsumeRecord record = new YktConsumeRecord();
            record.setOrderNum(bjAccountConsumeDTO.getProordernum());
            record.setStates(YktAPCOStatesEnum.CONSUME_CANCEL_SUCCESS.getCode());
            record.setBeforeStates(yktConsumeRecord.getStates());
            record.setUpdateUser(yktConsumeRecord.getCreateUser());
            record.setUpdateDate(new Date());
            yktConsumeRecordMapper.updateByOrderNum(record);
        }

        if (log.isInfoEnabled()) {
            log.info("通卡账户消费撤销申请接口:返回响应码:" + response.getCode() + ",完整响应体:" + JSONObject.toJSONString(response));
        }

        return response;
    }

    /****************************** 北京通卡“账户消费”业务流程接口 end ******************************/

    /****************************** 北京通卡“积分消费”业务流程接口 start ******************************/

    /**
     * 通卡积分消费申请
     * @param bjIntegralConsumeDTO
     * @return
     */
    @Override
    public DodopalResponse<BJIntegralConsumeDTO> applyYktPointConsume(BJIntegralConsumeDTO bjIntegralConsumeDTO) {
        DodopalResponse<BJIntegralConsumeDTO> response = new DodopalResponse<BJIntegralConsumeDTO>();
        response.setCode(ResponseCode.SUCCESS);

        if (log.isInfoEnabled()) {
            log.info("通卡积分消费申请接口:入参:" + JSONObject.toJSONString(bjIntegralConsumeDTO));
        }

        // 消费业务：城市、通卡、商户、POS相关校验
        DodopalResponse<Map<String, String>> mapResponse = this.consumeCheck(bjIntegralConsumeDTO.getCitycode(), bjIntegralConsumeDTO.getPoscode());
        
        if (!ResponseCode.SUCCESS.equals(mapResponse.getCode())) {
            response.setCode(mapResponse.getCode());
            response.setResponseEntity(bjIntegralConsumeDTO);
            return response;
        }
        Map<String, String> merInfoMap = mapResponse.getResponseEntity();
        String merCode = merInfoMap.containsKey("merCode") ? merInfoMap.get("merCode") : "";
        String merName = merInfoMap.containsKey("merName") ? merInfoMap.get("merName") : "";
        String merType = merInfoMap.containsKey("merType") ? merInfoMap.get("merType") : "";
        String userId = merInfoMap.containsKey("userId") ? merInfoMap.get("userId") : "";
        String yktCode = merInfoMap.containsKey("yktCode") ? merInfoMap.get("yktCode") : "";
        String yktName = merInfoMap.containsKey("yktName") ? merInfoMap.get("yktName") : "";
        String cityName = merInfoMap.containsKey("cityName") ? merInfoMap.get("cityName") : "";
        if (StringUtils.isEmpty(bjIntegralConsumeDTO.getMercode().replaceAll("0", ""))) {
            bjIntegralConsumeDTO.setMercode(merCode);
        }

        // 当产品库收到对应的产品库订单号为全0时，生成账户消费的产品库订单，添加订单状态为订单创建成功
        YktConsumeRecord record = new YktConsumeRecord();
        record.setOrderNum(this.getOrderNum(RateCodeEnum.YKT_CONSUME_POINT.getCode()));
        record.setYktCode(yktCode);
        record.setYktName(yktName);
        record.setCityCode(bjIntegralConsumeDTO.getCitycode());
        record.setCityName(cityName);
        record.setMerCode(merCode);
        record.setMerName(merName);
        record.setMerType(merType);
        record.setBusinessType(RateCodeEnum.YKT_CONSUME_POINT.getCode());
        record.setCardNum(bjIntegralConsumeDTO.getCardno());
        record.setPosCode(bjIntegralConsumeDTO.getPoscode());
        record.setPosType(bjIntegralConsumeDTO.getPostype());
        //record.setYktMerAcountno(bjIntegralConsumeDTO.getAccountno());
        //record.setYktAcountAmt(Long.parseLong(bjAccountConsumeDTO.getTxnamt()));
        record.setYktPointAmt(Long.parseLong(bjIntegralConsumeDTO.getPreautheamt()));
        record.setStates(YktAPCOStatesEnum.CREATION_SUCCESS.getCode());
        //record.setBeforeStates("");
        record.setOrderDate(new Date());
        record.setOrderDay(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDD_STR));
        record.setOperId(bjIntegralConsumeDTO.getOperid());
        record.setCreateUser(userId);
        record.setCreateDate(new Date());
        yktConsumeRecordMapper.addOrderRecord(record);
        bjIntegralConsumeDTO.setProordernum(record.getOrderNum());

        // 调用卡服务积分消费申请接口
        try {
            response = bjIcdcAcountPointConsumeDelegate.applyYktPointConsume(bjIntegralConsumeDTO);
        }
        catch (HessianRuntimeException e) {
            response.setCode(ResponseCode.PRODUCT_CALL_CARD_ERROR);
            response.setResponseEntity(bjIntegralConsumeDTO);
            return response;
        }
        if (null == response.getResponseEntity()) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            response.setResponseEntity(bjIntegralConsumeDTO);
            return response;
        }

        // 根据卡服务的返回交易应答码并且结束标志为1时更新订单状态为积分消费申请成功或积分消费申请失败
        if ("1".equals(response.getResponseEntity().getTradeendflag())) {
            YktConsumeRecord newRecord = new YktConsumeRecord();
            newRecord.setOrderNum(bjIntegralConsumeDTO.getProordernum());
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                newRecord.setStates(YktAPCOStatesEnum.CONSUME_APPLY_SUCCESS.getCode());
            } else {
                newRecord.setStates(YktAPCOStatesEnum.CONSUME_APPLY_FAILED.getCode());
            }
            newRecord.setBeforeStates(YktAPCOStatesEnum.CREATION_SUCCESS.getCode());
            newRecord.setUpdateUser(record.getCreateUser());
            newRecord.setUpdateDate(new Date());
            yktConsumeRecordMapper.updateByOrderNum(newRecord);
        }

        if (log.isInfoEnabled()) {
            log.info("通卡积分消费申请接口:返回响应码:" + response.getCode() + ",完整响应体:" + JSONObject.toJSONString(response));
        }
        return response;
    }

    /**
     * 通卡积分消费结果上送
     * @param bjIntegralConsumeDTO
     * @return
     */
    @Override
    public DodopalResponse<BJIntegralConsumeDTO> uploadYktPointConsume(BJIntegralConsumeDTO bjIntegralConsumeDTO) {
        DodopalResponse<BJIntegralConsumeDTO> response = new DodopalResponse<BJIntegralConsumeDTO>();
        response.setCode(ResponseCode.SUCCESS);

        if (log.isInfoEnabled()) {
            log.info("通卡积分消费结果上送接口:入参:" + JSONObject.toJSONString(bjIntegralConsumeDTO));
        }

        // 消费业务：城市、通卡、商户、POS相关校验
        DodopalResponse<Map<String, String>> mapResponse = this.consumeCheck(bjIntegralConsumeDTO.getCitycode(), bjIntegralConsumeDTO.getPoscode());
        
        if (!ResponseCode.SUCCESS.equals(mapResponse.getCode())) {
            response.setCode(mapResponse.getCode());
            response.setResponseEntity(bjIntegralConsumeDTO);
            return response;
        }

        // 取消交易无订单号时,产品库直接透传到卡服务由卡服务进行订单校验
        if (!("1".equals(bjIntegralConsumeDTO.getTxnstat()) && StringUtils.isBlank(bjIntegralConsumeDTO.getProordernum().replaceAll("0", "")))) {
            
            // 获取产品库订单信息
            YktConsumeRecord yktConsumeRecord = yktConsumeRecordMapper.selectByOrderNum(bjIntegralConsumeDTO.getProordernum());

            if (null == yktConsumeRecord) {
                response.setCode(ResponseCode.PRODUCT_CHECK_CARD_ORDER_NOT_EXIST);
                response.setResponseEntity(bjIntegralConsumeDTO);
                return response;
            }

            if ("0".equals(bjIntegralConsumeDTO.getTxnstat())) {

                // 产品库当接到成功的请求时，产品库检查状态要符合（积分消费申请成功，积分消费成功）
                if (!YktAPCOStatesEnum.CONSUME_APPLY_SUCCESS.getCode().equals(yktConsumeRecord.getStates()) 
                    && !YktAPCOStatesEnum.CONSUME_SUCCESS.getCode().equals(yktConsumeRecord.getStates())) {
                    
                    response.setCode(ResponseCode.PRODUCT_CHECK_CARD_ORDER_STATUS_ERROR);
                    response.setResponseEntity(bjIntegralConsumeDTO);
                    return response;
                }

                // 当状态是积分消费申请成功更新状态为积分消费成功
                if (YktAPCOStatesEnum.CONSUME_APPLY_SUCCESS.getCode().equals(yktConsumeRecord.getStates())) {
                    YktConsumeRecord record = new YktConsumeRecord();
                    record.setOrderNum(bjIntegralConsumeDTO.getProordernum());
                    record.setStates(YktAPCOStatesEnum.CONSUME_SUCCESS.getCode());
                    record.setBeforeStates(yktConsumeRecord.getStates());
                    record.setUpdateUser(yktConsumeRecord.getCreateUser());
                    record.setUpdateDate(new Date());
                    yktConsumeRecordMapper.updateByOrderNum(record);
                }

            } else if ("1".equals(bjIntegralConsumeDTO.getTxnstat())) {

                // 产品库当接到失败的请求时，产品库检查状态要符合（积分消费申请成功,积分消费申请失败,积分消费失败）
                if (!YktAPCOStatesEnum.CONSUME_APPLY_SUCCESS.getCode().equals(yktConsumeRecord.getStates()) 
                    && !YktAPCOStatesEnum.CONSUME_APPLY_FAILED.getCode().equals(yktConsumeRecord.getStates()) 
                    && !YktAPCOStatesEnum.CONSUME_FAILED.getCode().equals(yktConsumeRecord.getStates())) {

                    response.setCode(ResponseCode.PRODUCT_CHECK_CARD_ORDER_STATUS_ERROR);
                    response.setResponseEntity(bjIntegralConsumeDTO);
                    return response;
                }

                if (YktAPCOStatesEnum.CONSUME_APPLY_SUCCESS.getCode().equals(yktConsumeRecord.getStates())) {

                    // 当状态是积分消费申请成功更新状态为账户消费失败
                    YktConsumeRecord record = new YktConsumeRecord();
                    record.setOrderNum(bjIntegralConsumeDTO.getProordernum());
                    record.setStates(YktAPCOStatesEnum.CONSUME_FAILED.getCode());
                    record.setBeforeStates(yktConsumeRecord.getStates());
                    record.setUpdateUser(yktConsumeRecord.getCreateUser());
                    record.setUpdateDate(new Date());
                    yktConsumeRecordMapper.updateByOrderNum(record);

                } else if (YktAPCOStatesEnum.CONSUME_FAILED.getCode().equals(yktConsumeRecord.getStates())) {

                    // 当状态是积分消费申请失败直接返回全零应答码不需调用卡服务接口
                    response.setCode(ResponseCode.SUCCESS);
                    response.setResponseEntity(bjIntegralConsumeDTO);
                    return response;
                }
            }
        } else {
            log.info("取消交易无订单号时,产品库直接透传到卡服务由卡服务进行订单校验。");
        }
      
        // 调用卡服务积分消费结果上送接口
        try {
            response = bjIcdcAcountPointConsumeDelegate.uploadYktPointConsume(bjIntegralConsumeDTO);
        }
        catch (HessianRuntimeException e) {
            response.setCode(ResponseCode.PRODUCT_CALL_CARD_ERROR);
            response.setResponseEntity(bjIntegralConsumeDTO);
            return response;
        }
        
        if (log.isInfoEnabled()) {
            log.info("通卡积分消费结果上送:返回响应码:" + response.getCode() + ",完整响应体:" + JSONObject.toJSONString(response));
        }
        
        return response;
    }

    /**
     * 通卡积分消费撤销申请
     * @param bjIntegralConsumeDTO
     * @return
     */
    @Override
    public DodopalResponse<BJIntegralConsumeDTO> cancelYktPointConsume(BJIntegralConsumeDTO bjIntegralConsumeDTO) {
        DodopalResponse<BJIntegralConsumeDTO> response = new DodopalResponse<BJIntegralConsumeDTO>();
        response.setCode(ResponseCode.SUCCESS);

        if (log.isInfoEnabled()) {
            log.info("通卡积分消费撤销申请接口:入参:" + JSONObject.toJSONString(bjIntegralConsumeDTO));
        }

        // 消费业务：城市、通卡、商户、POS相关校验
        DodopalResponse<Map<String, String>> mapResponse = this.consumeCheck(bjIntegralConsumeDTO.getCitycode(), bjIntegralConsumeDTO.getPoscode());
        
        if (!ResponseCode.SUCCESS.equals(mapResponse.getCode())) {
            response.setCode(mapResponse.getCode());
            response.setResponseEntity(bjIntegralConsumeDTO);
            return response;
        }

        // 获取产品库订单信息
        YktConsumeRecord yktConsumeRecord = yktConsumeRecordMapper.selectByOrderNum(bjIntegralConsumeDTO.getProordernum());

        if (null == yktConsumeRecord) {
            response.setCode(ResponseCode.PRODUCT_CHECK_CARD_ORDER_NOT_EXIST);
            response.setResponseEntity(bjIntegralConsumeDTO);
            return response;
        }

        // 根据产品库订单号校验订单合法性(只有产品库订单状态为积分消费成功才能进行后续交易)
        if (!YktAPCOStatesEnum.CONSUME_SUCCESS.getCode().equals(yktConsumeRecord.getStates())) {

            response.setCode(ResponseCode.PRODUCT_CHECK_CARD_ORDER_STATUS_ERROR);
            response.setResponseEntity(bjIntegralConsumeDTO);
            return response;
        }
        
        // 当产品库订单状态为: 积分消费撤销成功直接返回交易应答码成功无需再调用卡服务。
        if (YktAPCOStatesEnum.CONSUME_CANCEL_SUCCESS.getCode().equals(yktConsumeRecord.getStates())) {
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(bjIntegralConsumeDTO);
            return response;
        }
        
        // 调用卡服务账户消费撤销申请查询接口
        try {
            response = bjIcdcAcountPointConsumeDelegate.cancelYktPointConsume(bjIntegralConsumeDTO);
        }
        catch (HessianRuntimeException e) {
            response.setCode(ResponseCode.PRODUCT_CALL_CARD_ERROR);
            response.setResponseEntity(bjIntegralConsumeDTO);
            return response;
        }
        if (null == response.getResponseEntity()) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            response.setResponseEntity(bjIntegralConsumeDTO);
            return response;
        }

        // 根据卡服务的返回交易应答码并且结束标志为1时更新产品库订单状态为积分消费撤销成功或(保留原积分消费成功状态)
        if (ResponseCode.SUCCESS.equals(response.getCode()) && "1".equals(response.getResponseEntity().getTradeendflag())) {
            YktConsumeRecord record = new YktConsumeRecord();
            record.setOrderNum(bjIntegralConsumeDTO.getProordernum());
            record.setStates(YktAPCOStatesEnum.CONSUME_CANCEL_SUCCESS.getCode());
            record.setBeforeStates(yktConsumeRecord.getStates());
            record.setUpdateUser(yktConsumeRecord.getCreateUser());
            record.setUpdateDate(new Date());
            yktConsumeRecordMapper.updateByOrderNum(record);
        }

        if (log.isInfoEnabled()) {
            log.info("通卡积分消费撤销申请接口:返回响应码:" + response.getCode() + ",完整响应体:" + JSONObject.toJSONString(response));
        }

        return response;
    }
    /****************************** 北京通卡“积分消费”业务流程接口 end ******************************/
}
