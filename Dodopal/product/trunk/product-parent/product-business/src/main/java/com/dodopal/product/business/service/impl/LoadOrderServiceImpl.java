package com.dodopal.product.business.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.payment.dto.PayAwayDTO;
import com.dodopal.api.payment.dto.PayTranDTO;
import com.dodopal.api.product.dto.IcdcLoadOrderRequestDTO;
import com.dodopal.api.product.dto.LoadOrderDTO;
import com.dodopal.api.product.dto.LoadOrderExtResponseDTO;
import com.dodopal.api.product.dto.LoadOrderQueryResponseDTO;
import com.dodopal.api.product.dto.LoadOrderRequestDTO;
import com.dodopal.api.product.dto.LoadOrderResponseDTO;
import com.dodopal.api.product.dto.LoadOrderResponseDTO2;
import com.dodopal.api.product.dto.query.LoadOrderQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.LoadOrderStatusEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.OpenSignEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.enums.ProductStatusEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.RateTypeEnum;
import com.dodopal.common.enums.ServiceRateTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.TranTypeEnum;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.ExcelModel;
import com.dodopal.common.service.AreaService;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.common.util.MoneyAlgorithmUtils;
import com.dodopal.product.business.dao.LoadOrderMapper;
import com.dodopal.product.business.dao.PrdProductYktMapper;
import com.dodopal.product.business.dao.ProductYKTMapper;
import com.dodopal.product.business.model.LoadOrder;
import com.dodopal.product.business.model.LoadOrderRecord;
import com.dodopal.product.business.model.PrdProductYktInfoForIcdcRecharge;
import com.dodopal.product.business.model.ProductYKT;
import com.dodopal.product.business.model.query.LoadOrderQuery;
import com.dodopal.product.business.model.query.ProLoadOrderQuery;
import com.dodopal.product.business.service.LoadOrderService;
import com.dodopal.product.delegate.IcdcRechargeDelegate;
import com.dodopal.product.delegate.MerchantDelegate;
import com.dodopal.product.delegate.PayDelegate;

@Service
public class LoadOrderServiceImpl implements LoadOrderService {

    private Logger logger = LoggerFactory.getLogger(LoadOrderServiceImpl.class);

    @Autowired
    private LoadOrderMapper loadOrderMapper;
    @Autowired
    private PrdProductYktMapper prdProductYktMapper;
    @Autowired
    private ProductYKTMapper productYKTMapper;
    @Autowired
    private AreaService areaService;
    @Autowired
    private MerchantDelegate merchantDelegate;
    @Autowired
    private IcdcRechargeDelegate icdcRechargeDelegate;
    @Autowired
    private PayDelegate payDelegate;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 查询公交卡充值圈存订单
     */
    @Override
    @Transactional(readOnly = true)
    public LoadOrder getLoadOrderByLoadOrderNum(String loadOrderNum) {
        LoadOrder loadOrder = loadOrderMapper.getLoadOrderByLoadOrderNum(loadOrderNum);
        return loadOrder;
    }

    /**
     * 调用支付交易成功后__修改公交卡充值圈存订单状态
     * @return
     */
    @Override
    @Transactional
    public DodopalResponse<LoadOrder> updateloadOrderStateAfterAccountDeduct(String loadOrderNum) {
        DodopalResponse<LoadOrder> loadOrderResponse = new DodopalResponse<LoadOrder>();
        loadOrderResponse.setCode(ResponseCode.SUCCESS);

        // 验证圈存订单是否存在，以及圈存订单的状态是否为：订单创建成功
        LoadOrder loadOrder = loadOrderMapper.getLoadOrderByLoadOrderNum(loadOrderNum);
        if (loadOrder == null) {
            logger.error("圈存订单不存在：" + loadOrderNum);
            loadOrderResponse.setCode(ResponseCode.PRODUCT_LOADORDER_BOOK_ERROR);
            return loadOrderResponse;
        }
//        if (!LoadOrderStatusEnum.ORDER_CREATE.getCode().equals(loadOrder.getStatus())) {
//            logger.error("圈存订单状态不正确：" + loadOrder.getStatus());
//            loadOrderResponse.setCode(ResponseCode.PRODUCT_LOADORDER_BOOK_ERROR);
//            return loadOrderResponse;
//        }
        loadOrderResponse.setResponseEntity(loadOrder);

        // 修改圈存订单状态：下单成功（支付成功/账户扣款成功）
        if(LoadOrderStatusEnum.ORDER_CREATE.getCode().equals(loadOrder.getStatus())){
        	 String loadOrderState = LoadOrderStatusEnum.ORDER_SUCCESS.getCode();
             loadOrderMapper.updateLoadOrderStatus(loadOrderState, loadOrderNum, loadOrder.getCreateUser());
        }       

        return loadOrderResponse;
    }

    /**
     * 圈存订单编号 Q+ 20150428222211 +五位数据库cycle sequence（循环使用）
     * @return
     */
    @Transactional(readOnly = true)
    private String generateOrderNum() {
        String orderNum = "Q";
        Date now = new Date();
        String timeStr = simpleDateFormat.format(now);
        orderNum += timeStr;
        return orderNum + loadOrderMapper.selectOrderNumSeq();
    }

    /**
     * 个人门户web端____创建公交卡充值圈存订单
     */
    @Override
    @Transactional
    public DodopalResponse<LoadOrder> bookLoadOrder(IcdcLoadOrderRequestDTO requestDto) {
        DodopalResponse<LoadOrder> response = new DodopalResponse<LoadOrder>();
        response.setCode(ResponseCode.PRODUCT_LOADORDER_REQUEST_ERROR);
        LoadOrder loadOrder = new LoadOrder();

        // 入参参数一般性验证
        if (!DDPStringUtil.existingWithLength(requestDto.getCardNum(), 32)) {
            logger.error("cardNum 不能为空或长度不能大于32位" + requestDto.getCardNum());
            return response;
        }
        if (!DDPStringUtil.existingWithLength(requestDto.getCustomerCode(), 40)) {
            logger.error("customerCode 不能为空或长度不能大于32位" + requestDto.getCustomerCode());
            return response;
        }
        if (DDPStringUtil.isNotPopulated(requestDto.getProductNum())) {
            logger.error("productNum 不能为空");
            return response;
        }
        if (!SourceEnum.PORTAL.getCode().equals(requestDto.getSource())) {
            logger.error("source 来源不正(0：门户)：" + requestDto.getSource());
            return response;
        }
        if (StringUtils.isBlank(PayTypeEnum.getPayTypeNameByCode(requestDto.getPayType()))) {
            logger.error("payType 不能为空");
            return response;
        }
        if (StringUtils.isBlank(requestDto.getPayWayId())) {
            logger.error("payWayId 不能为空");
            return response;
        }
        loadOrder.setSource(requestDto.getSource());
        loadOrder.setCardFaceNum(requestDto.getCardNum());
        loadOrder.setPayType(requestDto.getPayType());
        loadOrder.setPaywayId(requestDto.getPayWayId());

        // 产品合法性验证
        PrdProductYktInfoForIcdcRecharge productInfo = prdProductYktMapper.getProductInfoForIcdcRecharge(requestDto.getProductNum());
        if (productInfo == null || !ProductStatusEnum.ENABLE.getCode().equals(productInfo.getProStatus())) {
            logger.error("该产品已下架，请重新选择产品。");
            response.setCode(ResponseCode.PRODUCT_CHECK_PRODUCT_STATUS_DISABLE);// 该产品已下架，请重新选择产品。
            return response;
        }
        Area area = areaService.findCityByCityCode(productInfo.getCityCode());
        if (area == null) {
            logger.error("城市尚未启用，请联系客服人员。");
            response.setCode(ResponseCode.PRODUCT_CHECK_YKT_STATUS_DISABLE);// 城市尚未启用，请联系客服人员。
            return response;
        }
        loadOrder.setProductCode(productInfo.getProCode());
        loadOrder.setProductName(productInfo.getProName());
        loadOrder.setLoadAmt(productInfo.getProPrice());
        loadOrder.setCityCode(productInfo.getCityCode());
        loadOrder.setCityName(area.getCityName());

        // 通卡合法性验证(根据产品编号获取通卡编号，检验通卡公司是否处于启用状态,相关充值业务是否处于开通状态)
        ProductYKT productYkt = productYKTMapper.getYktInfoByYktCode(productInfo.getYktCode());
        if (productYkt == null || !ActivateEnum.ENABLE.getCode().equals(productYkt.getActivate()) || !OpenSignEnum.OPENED.getCode().equals(productYkt.getYktIsRecharge())) {
            logger.error("城市尚未启用，请联系客服人员。");
            response.setCode(ResponseCode.PRODUCT_CHECK_YKT_STATUS_DISABLE);// 城市尚未启用，请联系客服人员
            return response;
        }
        loadOrder.setYktCode(productYkt.getYktCode());
        loadOrder.setYktName(productYkt.getYktName());

        // 商户/个人合法性验证
        DodopalResponse<Map<String, String>> merMapResponse = merchantDelegate.validateMerchantForIcdcLoad(MerUserTypeEnum.PERSONAL, requestDto.getCustomerCode(), productYkt.getYktCode());
        if (!ResponseCode.SUCCESS.equals(merMapResponse.getCode())) {
            logger.error("商户合法性验证不通过：" + merMapResponse.getMessage());
            response.setCode(merMapResponse.getCode());
            return response;
        }
        Map<String, String> merInfoMap = merMapResponse.getResponseEntity();
        String rateType = merInfoMap.containsKey("rateType") ? merInfoMap.get("rateType") : "";
        double rate = merInfoMap.containsKey("rate") ? Double.parseDouble(merInfoMap.get("rate")) : 0d;
        String custName = merInfoMap.containsKey("custName") ? merInfoMap.get("custName") : "";

        loadOrder.setCustomerCode(requestDto.getCustomerCode());// 这里的客户是用户
        loadOrder.setCustomerName(custName);
        loadOrder.setCustomerType(MerUserTypeEnum.PERSONAL.getCode());// 固定值：0__个人
        loadOrder.setLoadRate(rate);
        loadOrder.setLoadRateType(rateType);
        loadOrder.setCustomerPayAmt(loadOrder.getLoadAmt());
        loadOrder.setCustomerGain(loadOrder.getCustomerPayAmt() - loadOrder.getLoadAmt());

        // 个人用户__支付服务费与服务费率类型(固定值：0，千分比)
        loadOrder.setPayServiceRate((double) 0);
        loadOrder.setPayServiceType(ServiceRateTypeEnum.PERMILLAGE.getCode());

        loadOrder.setOrderNum(this.generateOrderNum());// 数据库SEQ
        loadOrder.setStatus(LoadOrderStatusEnum.ORDER_CREATE.getCode());// 状态初始值：订单创建成功 
        loadOrder.setCreateUser(requestDto.getCustomerCode());
        loadOrderMapper.insertLoadOrder(loadOrder);
        response.setCode(ResponseCode.SUCCESS);
        response.setResponseEntity(loadOrder);
        return response;
    }

    /**
     * 外接商户___下圈存订单入参参数一般性验证
     * @param requestDto
     * @return
     */
    private boolean checkExtMerLoadOrderParamter(LoadOrderRequestDTO requestDto) {

        if (requestDto != null) {
            if (!DDPStringUtil.existingWithLength(requestDto.getSourceOrderNum(), 64)) {
                logger.error("sourceOrderNum 不能为空或长度不能大于64位");
                return false;
            }
            if (!DDPStringUtil.existingWithLength(requestDto.getCardNum(), 32)) {
                logger.error("cardNum 不能为空或长度不能大于32位");
                return false;
            }
            if (!DDPStringUtil.existingWithLength(requestDto.getMerchantNum(), 40)) {
                logger.error("merchantNum 不能为空或长度不能大于32位");
                return false;
            }
            if (DDPStringUtil.isNotPopulated(requestDto.getProductNum()) && DDPStringUtil.isNotPopulated(requestDto.getCityCode())) {
                logger.error("productNum和cityCode 至少提供一个");
                return false;
            }
            if (DDPStringUtil.isNotPopulated(requestDto.getProductNum()) && DDPStringUtil.isNotPopulated(requestDto.getChargeAmt())) {
                logger.error("没有提供productNum，必须提供rechargeAmt");
                return false;
            }
            if (DDPStringUtil.isNotPopulated(requestDto.getProductNum()) && DDPStringUtil.isPopulated(requestDto.getChargeAmt())) {
                String rechargeAmt = requestDto.getChargeAmt();
                try {
                    int amt = Integer.parseInt(rechargeAmt);
                    if (amt < 1) {
                        logger.error("rechargeAmt必须是正整数.");
                        return false;
                    }
                }
                catch (Exception e) {
                    logger.error("rechargeAmt必须是数字类型.");
                    return false;
                }
            }
            if (DDPStringUtil.isNotPopulated(requestDto.getSourceOrderTime())) {
                logger.error("sourceOrderTime 必须提供");
                return false;
            } else {
                Date date = DateUtils.stringtoDate(requestDto.getSourceOrderTime(), DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR);
                if (date == null) {
                    logger.error("sourceOrderTime 格式不正确, 格式必须为: yyyyMMddHHmmss");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 外接商户调用____创建公交卡充值圈存订单
     */
    @Override
    @Transactional
    public DodopalResponse<LoadOrderResponseDTO> bookExtMerLoadOrder(LoadOrderRequestDTO requestDto) {
        DodopalResponse<LoadOrderResponseDTO> response = new DodopalResponse<LoadOrderResponseDTO>();
        response.setCode(ResponseCode.PRODUCT_LOADORDER_REQUEST_ERROR);
        LoadOrder newOrder = new LoadOrder();
        // 1，入参参数一般性验证
        if (!this.checkExtMerLoadOrderParamter(requestDto)) {
            return response;
        }
        newOrder.setCardFaceNum(requestDto.getCardNum());

        // 2，校验外部订单号是否重复下单
        LoadOrder exsitLoadOrder = loadOrderMapper.getLoadOrderByExtMerOrderNum(requestDto.getSourceOrderNum());
        if (exsitLoadOrder != null) {
            logger.error("外部订单号已存在");
            return response;
        }
        newOrder.setExtMerOrderNum(requestDto.getSourceOrderNum());
        newOrder.setExtMerOrderTime(requestDto.getSourceOrderTime());

        // 3，产品合法性验证
        if (DDPStringUtil.isPopulated(requestDto.getProductNum())) {
            PrdProductYktInfoForIcdcRecharge productInfo = prdProductYktMapper.getProductInfoForIcdcRecharge(requestDto.getProductNum());
            if (productInfo == null || !ProductStatusEnum.ENABLE.getCode().equals(productInfo.getProStatus())) {
                logger.error("该产品已下架，请重新选择产品。");
                return response;
            }
            Area area = areaService.findCityByCityCode(productInfo.getCityCode());
            if (area == null) {
                logger.error("城市尚未启用，请联系客服人员。");
                return response;
            }
            newOrder.setProductCode(productInfo.getProCode());
            newOrder.setProductName(productInfo.getProName());
            newOrder.setCityCode(productInfo.getCityCode());
            newOrder.setCityName(area.getCityName());
            newOrder.setLoadAmt(productInfo.getProPrice());
            newOrder.setYktCode(productInfo.getYktCode());
        } else {
            Area area = areaService.findCityByCityCode(requestDto.getCityCode());
            if (area == null) {
                logger.error("城市尚未启用，请联系客服人员。");
                return response;
            }
            long amt = Integer.parseInt(requestDto.getChargeAmt());
            PrdProductYktInfoForIcdcRecharge productInfo = prdProductYktMapper.getProductInfoByCityId(requestDto.getCityCode());
            if (productInfo == null) {
                logger.error("城市尚未启用，请联系客服人员。");
                return response;
            }
            newOrder.setProductCode(productInfo.getProCode());
            newOrder.setProductName(productInfo.getProName());
            newOrder.setCityCode(requestDto.getCityCode());
            newOrder.setCityName(area.getCityName());
            newOrder.setLoadAmt(amt * 100);
            newOrder.setYktCode(productInfo.getYktCode());
        }

        // 4，通卡合法性验证(根据产品编号获取通卡编号，检验通卡公司是否处于启用状态,相关充值业务是否处于开通状态)
        ProductYKT productYkt = productYKTMapper.getYktInfoByYktCode(newOrder.getYktCode());
        if (productYkt == null || !ActivateEnum.ENABLE.getCode().equals(productYkt.getActivate()) || !OpenSignEnum.OPENED.getCode().equals(productYkt.getYktIsRecharge())) {
            logger.error("城市尚未启用，请联系客服人员。");
            response.setCode(ResponseCode.PRODUCT_CHECK_YKT_STATUS_DISABLE);// 城市尚未启用，请联系客服人员
            return response;
        }
        newOrder.setYktCode(productYkt.getYktCode());
        newOrder.setYktName(productYkt.getYktName());

        // 5，商户合法性验证
        DodopalResponse<Map<String, String>> merMapResponse = merchantDelegate.validateMerchantForIcdcLoad(MerUserTypeEnum.MERCHANT, requestDto.getMerchantNum(), newOrder.getYktCode());
        if (!ResponseCode.SUCCESS.equals(merMapResponse.getCode())) {
            logger.error("商户合法性验证不通过：" + merMapResponse.getMessage());
            response.setCode(merMapResponse.getCode());
            return response;
        }
        Map<String, String> merInfoMap = merMapResponse.getResponseEntity();
        String rateType = merInfoMap.containsKey("rateType") ? merInfoMap.get("rateType") : "";
        double rate = merInfoMap.containsKey("rate") ? Double.parseDouble(merInfoMap.get("rate")) : 0d;
        String custName = merInfoMap.containsKey("custName") ? merInfoMap.get("custName") : "";
        String extMerPayWayId = merInfoMap.containsKey("payWayExtId") ? merInfoMap.get("payWayExtId") : "";// 外接商户的支付方式ID
        //String extMerAdmin = merInfoMap.containsKey("userCode") ? merInfoMap.get("userCode") : "";// 外接商户在DDP平台的管理员编号
        String extMerAdminId = merInfoMap.containsKey("userId") ? merInfoMap.get("userId") : "";// 外接商户在DDP平台的管理员Id

        newOrder.setCustomerCode(requestDto.getMerchantNum());// 这里的客户是用户
        newOrder.setCustomerName(custName);
        newOrder.setCustomerType(MerUserTypeEnum.MERCHANT.getCode());// 固定值：1__企业（外接商户）
        newOrder.setLoadRate(rate);
        newOrder.setLoadRateType(rateType);
        long customerPayAmt = 0;
        if (RateTypeEnum.PERMILLAGE.getCode().equals(rateType)) {
            double realRate = 1 - rate / 1000;
            customerPayAmt = MoneyAlgorithmUtils.multiplyToIntValueAddOne(String.valueOf(newOrder.getLoadAmt()), String.valueOf(realRate));
        } else if (RateTypeEnum.SINGLE_AMOUNT.getCode().equals(rateType)) {
            customerPayAmt = newOrder.getLoadAmt() - (int) rate;
        }
        newOrder.setCustomerPayAmt(customerPayAmt);
        newOrder.setCustomerGain(newOrder.getLoadAmt() - newOrder.getCustomerPayAmt());

        newOrder.setOrderNum(this.generateOrderNum());// 数据库SEQ
        newOrder.setStatus(LoadOrderStatusEnum.ORDER_CREATE.getCode());// 状态初始值：订单创建成功 
        newOrder.setCreateUser(extMerAdminId);// 外接商户的创单人（在DDP平台的管理员Id）
        newOrder.setPayType(PayTypeEnum.PAY_ACT.getCode());
        newOrder.setPaywayId(extMerPayWayId);

        // 外接支付方式、调用支付交易系统，获取支付服务费率；服务费率类型:2_千分比（固定值）
        if (StringUtils.isNotBlank(extMerPayWayId)) {
            DodopalResponse<PayAwayDTO> getPayyExternalServiceRateResponse = null;
            PayAwayDTO payAwayDTO = null;
            try {
                getPayyExternalServiceRateResponse = icdcRechargeDelegate.findPayExternalById(extMerPayWayId);
                payAwayDTO = getPayyExternalServiceRateResponse.getResponseEntity();
            }
            catch (HessianRuntimeException e) {
                logger.error("调用支付交易系统获取支付服务费率与服务费率类型失败:Hessian异常");
                response.setCode(ResponseCode.PRODUCT_CALL_PAYMENT_ERROR);
                return response;
            }
            if (!ResponseCode.SUCCESS.equals(getPayyExternalServiceRateResponse.getCode()) || payAwayDTO == null) {
                logger.error("获取支付服务费率与服务费率类型失败:" + response.getMessage());
                response.setCode(getPayyExternalServiceRateResponse.getCode());
                return response;
            }
            newOrder.setPayServiceRate(payAwayDTO.getPayServiceRate());
            newOrder.setPayServiceType(ServiceRateTypeEnum.PERMILLAGE.getCode());

            // 服务费费率类型: 费率（千分比）(遵循金额算法相乘有小数则取整+1)
            long receivedPrice = MoneyAlgorithmUtils.multiplyToIntValueAddOne(String.valueOf(newOrder.getLoadAmt()), String.valueOf(payAwayDTO.getPayServiceRate() / 1000));
            newOrder.setCustomerPayAmt(newOrder.getCustomerPayAmt() + receivedPrice);
            newOrder.setCustomerGain(newOrder.getLoadAmt() - newOrder.getCustomerPayAmt());
        }
        // 外接商户来源：（默认值：EXT_LOAD("8","外接圈存")）
        newOrder.setSource(SourceEnum.EXT_LOAD.getCode());
        loadOrderMapper.insertLoadOrder(newOrder);

        // 调用支付交易扣款
        LoadOrder order = loadOrderMapper.getLoadOrderByLoadOrderNum(newOrder.getOrderNum());
        PayTranDTO payTranDTO = new PayTranDTO();
        payTranDTO.setCusTomerCode(order.getCustomerCode());
        payTranDTO.setCusTomerType(order.getCustomerType());
        payTranDTO.setOrderCode(order.getOrderNum());
        payTranDTO.setBusinessType(RateCodeEnum.IC_LOAD.getCode());
        payTranDTO.setGoodsName(order.getProductName());
        payTranDTO.setSource(order.getSource());
        payTranDTO.setOrderDate(order.getOrderTime());
        payTranDTO.setCityCode(order.getCityCode());
        payTranDTO.setExtFlg(true);
        payTranDTO.setAmount(order.getCustomerPayAmt());
        payTranDTO.setOperatorId(order.getCreateUser());
        payTranDTO.setPayId(order.getPaywayId());
        payTranDTO.setTranType(TranTypeEnum.ACCOUNT_CONSUMPTION.getCode());
        DodopalResponse<Boolean> payResponse = payDelegate.loadOrderDeductAccountAmt(payTranDTO);

        // 更新订单状态
        if (ResponseCode.SUCCESS.equals(payResponse.getCode())) {
            this.updateloadOrderStateAfterAccountDeduct(newOrder.getOrderNum());
        }

        LoadOrderResponseDTO result = new LoadOrderResponseDTO();
        result.setResponseCode(payResponse.getCode());
        result.setOrderNum(newOrder.getOrderNum());
        result.setSourceOrderNum(requestDto.getSourceOrderNum());
        response.setCode(payResponse.getCode());
        response.setResponseEntity(result);
        return response;
    }

    /**
     * 手机APP端____创建公交卡充值圈存订单
     */
    @Override
    @Transactional
    public DodopalResponse<LoadOrder> bookMobileLoadOrder(IcdcLoadOrderRequestDTO requestDto) {
        DodopalResponse<LoadOrder> response = new DodopalResponse<LoadOrder>();
        response.setCode(ResponseCode.PRODUCT_LOADORDER_REQUEST_ERROR);
        LoadOrder loadOrder = new LoadOrder();

        // 入参参数一般性验证
        if (!DDPStringUtil.existingWithLength(requestDto.getCardNum(), 32)) {
            logger.error("cardNum 不能为空或长度不能大于32位" + requestDto.getCardNum());
            return response;
        }
        if (!DDPStringUtil.existingWithLength(requestDto.getCustomerCode(), 40)) {
            logger.error("customerCode 不能为空或长度不能大于32位" + requestDto.getCustomerCode());
            return response;
        }
        if (DDPStringUtil.isNotPopulated(requestDto.getProductNum())) {
            logger.error("productNum 不能为空");
            return response;
        }
        if (!(SourceEnum.AN_PHONE.getCode().equals(requestDto.getSource()) || SourceEnum.IOS_PHONE.getCode().equals(requestDto.getSource()))) {
            logger.error("source 来源不正(6:安卓手机；7：IOS手机)：" + requestDto.getSource());
            return response;
        }
        if (StringUtils.isBlank(PayTypeEnum.getPayTypeNameByCode(requestDto.getPayType()))) {
            logger.error("payType 不能为空");
            return response;
        }
        if (StringUtils.isBlank(requestDto.getPayWayId())) {
            logger.error("payWayId 不能为空");
            return response;
        }
        loadOrder.setSource(requestDto.getSource());
        loadOrder.setCardFaceNum(requestDto.getCardNum());
        loadOrder.setPayType(requestDto.getPayType());
        loadOrder.setPaywayId(requestDto.getPayWayId());

        // 产品合法性验证
        PrdProductYktInfoForIcdcRecharge productInfo = prdProductYktMapper.getProductInfoForIcdcRecharge(requestDto.getProductNum());
        if (productInfo == null || !ProductStatusEnum.ENABLE.getCode().equals(productInfo.getProStatus())) {
            logger.error("该产品已下架，请重新选择产品。");
            response.setCode(ResponseCode.PRODUCT_CHECK_PRODUCT_STATUS_DISABLE);// 该产品已下架，请重新选择产品。
            return response;
        }
        Area area = areaService.findCityByCityCode(productInfo.getCityCode());
        if (area == null) {
            logger.error("城市尚未启用，请联系客服人员。");
            response.setCode(ResponseCode.PRODUCT_CHECK_YKT_STATUS_DISABLE);// 城市尚未启用，请联系客服人员。
            return response;
        }
        loadOrder.setProductCode(productInfo.getProCode());
        loadOrder.setProductName(productInfo.getProName());
        loadOrder.setLoadAmt(productInfo.getProPrice());
        loadOrder.setCityCode(productInfo.getCityCode());
        loadOrder.setCityName(area.getCityName());

        // 通卡合法性验证(根据产品编号获取通卡编号，检验通卡公司是否处于启用状态,相关充值业务是否处于开通状态)
        ProductYKT productYkt = productYKTMapper.getYktInfoByYktCode(productInfo.getYktCode());
        if (productYkt == null || !ActivateEnum.ENABLE.getCode().equals(productYkt.getActivate()) || !OpenSignEnum.OPENED.getCode().equals(productYkt.getYktIsRecharge())) {
            logger.error("城市尚未启用，请联系客服人员。");
            response.setCode(ResponseCode.PRODUCT_CHECK_YKT_STATUS_DISABLE);// 城市尚未启用，请联系客服人员
            return response;
        }
        loadOrder.setYktCode(productYkt.getYktCode());
        loadOrder.setYktName(productYkt.getYktName());

        // 商户/个人合法性验证
        DodopalResponse<Map<String, String>> merMapResponse = merchantDelegate.validateMerchantForIcdcLoad(MerUserTypeEnum.PERSONAL, requestDto.getCustomerCode(), productYkt.getYktCode());
        if (!ResponseCode.SUCCESS.equals(merMapResponse.getCode())) {
            logger.error("商户合法性验证不通过：" + merMapResponse.getMessage());
            response.setCode(merMapResponse.getCode());
            return response;
        }
        Map<String, String> merInfoMap = merMapResponse.getResponseEntity();
        String rateType = merInfoMap.containsKey("rateType") ? merInfoMap.get("rateType") : "";
        double rate = merInfoMap.containsKey("rate") ? Double.parseDouble(merInfoMap.get("rate")) : 0d;
        String custName = merInfoMap.containsKey("custName") ? merInfoMap.get("custName") : "";

        loadOrder.setCustomerCode(requestDto.getCustomerCode());// 这里的客户是用户
        loadOrder.setCustomerName(custName);
        loadOrder.setCustomerType(MerUserTypeEnum.PERSONAL.getCode());// 固定值：0__个人
        loadOrder.setLoadRate(rate);
        loadOrder.setLoadRateType(rateType);
        loadOrder.setCustomerPayAmt(loadOrder.getLoadAmt());
        loadOrder.setCustomerGain(loadOrder.getLoadAmt() - loadOrder.getCustomerPayAmt());

        // 个人用户__支付服务费与服务费率类型(固定值：0，千分比)
        loadOrder.setPayServiceRate((double) 0);
        loadOrder.setPayServiceType(ServiceRateTypeEnum.PERMILLAGE.getCode());

        loadOrder.setOrderNum(this.generateOrderNum());// 数据库SEQ
        loadOrder.setStatus(LoadOrderStatusEnum.ORDER_CREATE.getCode());// 状态初始值：订单创建成功 
        loadOrder.setCreateUser(requestDto.getCustomerCode());
        loadOrderMapper.insertLoadOrder(loadOrder);

        response.setCode(ResponseCode.SUCCESS);

        // 判断手机端用户选择的支付类型：是否为账户支付，调用支付交易扣款
        if (PayTypeEnum.PAY_ACT.getCode().equals(loadOrder.getPayType())) {
            LoadOrder order = loadOrderMapper.getLoadOrderByLoadOrderNum(loadOrder.getOrderNum());
            PayTranDTO payTranDTO = new PayTranDTO();
            payTranDTO.setCusTomerCode(order.getCustomerCode());
            payTranDTO.setCusTomerType(order.getCustomerType());
            payTranDTO.setOrderCode(order.getOrderNum());
            payTranDTO.setBusinessType(RateCodeEnum.IC_LOAD.getCode());
            payTranDTO.setGoodsName(order.getProductName());
            payTranDTO.setSource(order.getSource());
            payTranDTO.setOrderDate(order.getOrderTime());
            payTranDTO.setCityCode(order.getCityCode());
            payTranDTO.setExtFlg(true);
            payTranDTO.setAmount(order.getCustomerPayAmt());
            payTranDTO.setOperatorId(order.getCreateUser());
            payTranDTO.setPayId(order.getPaywayId());
            payTranDTO.setTranType(TranTypeEnum.ACCOUNT_CONSUMPTION.getCode());
            DodopalResponse<Boolean> payResponse = payDelegate.loadOrderDeductAccountAmt(payTranDTO);

            // 更新订单状态
            if (ResponseCode.SUCCESS.equals(payResponse.getCode())) {
                this.updateloadOrderStateAfterAccountDeduct(loadOrder.getOrderNum());
            }
            response.setCode(payResponse.getCode());
        } else {
            LoadOrder order = loadOrderMapper.getLoadOrderByLoadOrderNum(loadOrder.getOrderNum());

            //  移动端非账户支付，调用支付交易系统，生成交易流水            
            PayTranDTO payTranDTO = new PayTranDTO();
            payTranDTO.setCusTomerType(order.getCustomerType());
            payTranDTO.setCusTomerCode(order.getCustomerCode());
            payTranDTO.setOrderCode(order.getOrderNum());
            payTranDTO.setBusinessType(RateCodeEnum.IC_LOAD.getCode());
            payTranDTO.setGoodsName(order.getProductName());
            payTranDTO.setAmount(order.getCustomerPayAmt());
            payTranDTO.setSource(order.getSource());
            payTranDTO.setOrderDate(order.getOrderTime());
            payTranDTO.setCityCode(order.getCityCode());
            payTranDTO.setPayId(order.getPaywayId());
            payTranDTO.setTranType(TranTypeEnum.ACCOUNT_CONSUMPTION.getCode());

            //String proUrl = DodopalAppVarPropsUtil.getStringProp(DelegateConstant.PRODUCT_URL);
            //payTranDTO.setNotifyUrl(proUrl+"/callBackOrder?orderNum="+prdOrder.getProOrderNum()+"&cityCode="+crdDTO.getCitycode());
            //payTranDTO.setNotifyUrl(proUrl+"/callBackOrder");
            payTranDTO.setExtFlg(false);
            payTranDTO.setOperatorId(order.getCreateUser());
            DodopalResponse<String> getTranNumByMobilepay = null;
            try {
                getTranNumByMobilepay = icdcRechargeDelegate.mobilepay(payTranDTO);
            }
            catch (HessianRuntimeException e) {
                logger.error("移动端一卡通充值，生单环节调用支付交易系统生成交易流水失败:Hessian异常");
                response.setCode(ResponseCode.PRODUCT_CALL_PAYMENT_ERROR);
                return response;
            }
            if (!ResponseCode.SUCCESS.equals(getTranNumByMobilepay.getCode()) || StringUtils.isBlank(getTranNumByMobilepay.getResponseEntity())) {
                logger.error("移动端充值圈存，生单环节生成交易流水失败:" + getTranNumByMobilepay.getMessage());
                response.setCode(getTranNumByMobilepay.getCode());
                return response;
            }
            response.setCode(getTranNumByMobilepay.getCode());
        }
        response.setResponseEntity(loadOrder);
        return response;
    }

    /**
     * 6.2 根据卡号获取可用于一卡通充值的圈存订单 产品库内部调用 符合条件的圈存订单信息列表或者为空。具体包括：
     * 圈存订单编号、外部订单号、卡号、商户号
     * @param cardNum
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public DodopalResponse<List<LoadOrderQueryResponseDTO>> findAvailableLoadOrdersByCardNum(String cardNum) {
        DodopalResponse<List<LoadOrderQueryResponseDTO>> response = new DodopalResponse<List<LoadOrderQueryResponseDTO>>();

        // cardNum String  32  Y   需要进行充值的一卡通卡面号
        if (!DDPStringUtil.existingWithLength(cardNum, 32)) {
            logger.error("cardNum 不能为空或长度不能大于32位");
            response.setCode(ResponseCode.PRODUCT_LOADORDER_FINDBYCARDNUM_ERROR);
            return response;
        }
        List<LoadOrder> orders = loadOrderMapper.findAvailableLoadOrdersByCardNum(cardNum);
        List<LoadOrderQueryResponseDTO> resultList = new ArrayList<LoadOrderQueryResponseDTO>();
        if (CollectionUtils.isNotEmpty(orders)) {
            for (LoadOrder order : orders) {
                LoadOrderQueryResponseDTO dto = new LoadOrderQueryResponseDTO();
                dto.setCardNum(order.getCardFaceNum());
                dto.setSourceOrderNum(order.getExtMerOrderNum());
                dto.setMerchantNum(order.getCustomerCode());
                dto.setMerchantName(order.getCustomerName());
                dto.setOrderNum(order.getOrderNum());
                dto.setProductCode(order.getProductCode());
                dto.setChargeAmt(order.getLoadAmt());
                dto.setCityCode(order.getCityCode());
                resultList.add(dto);
            }
        }
        response.setCode(ResponseCode.SUCCESS);
        response.setResponseEntity(resultList);
        return response;
    }

    /*********************************************************************************************************/
    /************************************* 华丽分割线 *************************************/
    /*********************************************************************************************************/

    @Override
    @Transactional(readOnly = true)
    public DodopalResponse<List<LoadOrderResponseDTO2>> findLoadOrder() {
        DodopalResponse<List<LoadOrderResponseDTO2>> response = new DodopalResponse<List<LoadOrderResponseDTO2>>();
        List<LoadOrder> orders = loadOrderMapper.findLoadOrder();
        List<LoadOrderResponseDTO2> resultList = new ArrayList<LoadOrderResponseDTO2>();
        if (CollectionUtils.isNotEmpty(orders)) {
            for (LoadOrder order : orders) {
                LoadOrderResponseDTO2 dto = new LoadOrderResponseDTO2();
                //TODO dto.setSourceOrderNum(order.getSourceOrderNum());
                dto.setOrderNum(order.getOrderNum());
                dto.setResponseCode(ResponseCode.SUCCESS);
                resultList.add(dto);
            }
        }
        response.setCode(ResponseCode.SUCCESS);
        response.setResponseEntity(resultList);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public DodopalResponse<LoadOrderExtResponseDTO> findLoadOrderStatus(LoadOrderRequestDTO requestDto) {
        DodopalResponse<LoadOrderExtResponseDTO> response = new DodopalResponse<LoadOrderExtResponseDTO>();
        response.setCode(ResponseCode.PRODUCT_LOADORDER_QUERYSTATUS_INCORRECT);
        if (requestDto == null) {
            return response;
        } else {
            // sourceOrderNum   String  64  Y   调用方平台对应的订单编号
            if (!DDPStringUtil.existingWithLength(requestDto.getSourceOrderNum(), 64)) {
                logger.error("sourceOrderNum 不能为空或长度不能大于64位");
                return response;
            }
            // merchantNum String  40  Y   调用方平台对应的商户号（由DDP预先分配）
            if (!DDPStringUtil.existingWithLength(requestDto.getMerchantNum(), 40)) {
                logger.error("merchantNum 不能为空或长度不能大于32位");
                return response;
            }

            // signType    String          签名类型。比如：RSA、MD5。 如果没有指明，默认为MD5。
            // signData    String      Y   签名值
            //            if (DDPStringUtil.isNotPopulated(requestDto.getSignData())) {
            //                logger.error("signData 必须提供");
            //                return response;
            //            }
            // TODO 验签 
            // signCharset String          签名字符集，比如utf-8。 如果没有指定，默认为GBK
        }

        LoadOrder order = loadOrderMapper.findLoadOrderStatus(requestDto);
        if (order != null) {
            LoadOrderExtResponseDTO dto = new LoadOrderExtResponseDTO();
            dto.setOrderNum(order.getOrderNum());
            // TODO dto.setSourceOrderNum(order.getSourceOrderNum());
            // TODO  dto.setSourceOrderTime(simpleDateFormat.format(order.getSourceOrderTime()));
            // TODO dto.setCardNum(order.getCardNum());
            // TODO dto.setOrderStatus(order.getOrderStatus());
            response.setResponseEntity(dto);
        }
        response.setCode(ResponseCode.SUCCESS);
        return response;
    }

    /**
     * 查询公交卡充值圈存订单 根据调用方提供的查询条件，查询并且返回符合条件的圈存订单信息列表或者为空
     */
    @Override
    @Transactional(readOnly = true)
    public DodopalResponse<DodopalDataPage<LoadOrderDTO>> findLoadOrdersByPage(LoadOrderQueryDTO queryDto) {
        DodopalResponse<DodopalDataPage<LoadOrderDTO>> response = new DodopalResponse<DodopalDataPage<LoadOrderDTO>>();
        try {
            List<LoadOrder> loadOrders = loadOrderMapper.findLoadOrdersByPage(queryDto);
            List<LoadOrderDTO> result = new ArrayList<LoadOrderDTO>();
            if (CollectionUtils.isNotEmpty(loadOrders)) {
                for (LoadOrder b : loadOrders) {
                    LoadOrderDTO dto = new LoadOrderDTO();
                    BeanUtils.copyProperties(dto, b);
                    result.add(dto);
                }
            }
            DodopalDataPage<LoadOrderDTO> pages = new DodopalDataPage<LoadOrderDTO>(queryDto.getPage(), result);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(pages);
        }
        catch (Exception e) {
            logger.error("查询公交卡充值圈存订单时发生错误:", e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    /**
     * 7.3.2 公交卡充值流程中检验圈存订单合法性 根据圈存订单号去查询圈存订单是否存在（联合参数中的卡号和圈存订单编号进行查询） 提供方 :
     * 产品库圈存订单管理类 访问范围: 产品库公交卡充值业务流程类 如果验证通过，则返回000000。同时返回：产品编号和充值金额。
     */
    @Override
    @Transactional(readOnly = true)
    public DodopalResponse<LoadOrderDTO> validateLoadOrderForIcdcRecharge(LoadOrderQuery orderQuery) {
        DodopalResponse<LoadOrderDTO> response = new DodopalResponse<LoadOrderDTO>();
        response.setCode(ResponseCode.SUCCESS);
        LoadOrderDTO loadOrderDTO = null;
        if (orderQuery == null || DDPStringUtil.isNotPopulated(orderQuery.getOrderNum())) {
            response.setCode(ResponseCode.PRODUCT_LOADORDER_PARAM_INVALID);
        } else {
            LoadOrder loadOrder = loadOrderMapper.getLoadOrderByLoadOrderNum(orderQuery.getOrderNum());
            if (loadOrder == null) {
                response.setCode(ResponseCode.PRODUCT_LOADORDER_NOT_EXIST);
            } else {
                if (LoadOrderStatusEnum.ORDER_FAILURE.getCode().equals(loadOrder.getStatus()) || LoadOrderStatusEnum.ORDER_SUCCESS.getCode().equals(loadOrder.getStatus())) {
                    loadOrderDTO = new LoadOrderDTO();
                    loadOrderDTO.setProductCode(loadOrder.getProductCode());
                    loadOrderDTO.setLoadAmt(loadOrder.getLoadAmt());
                    loadOrderDTO.setCustomerPayAmt(loadOrder.getCustomerPayAmt());
                    loadOrderDTO.setCustomerGain(loadOrder.getCustomerGain());
                } else {
                    response.setCode(ResponseCode.PRODUCT_LOADORDER_INCORRECT);
                }
            }
        }
        response.setResponseEntity(loadOrderDTO);
        return response;
    }

    /**
     * 圈存订单查询(分页)
     */
    @Override
    public DodopalResponse<DodopalDataPage<LoadOrderRecord>> findLoadOrderListByPage(ProLoadOrderQuery prdOrderQuery) {
        DodopalResponse<DodopalDataPage<LoadOrderRecord>> response = new DodopalResponse<DodopalDataPage<LoadOrderRecord>>();
        try {
            List<LoadOrder> loadOrders = loadOrderMapper.findLoadOrderListByPage(prdOrderQuery);
            List<LoadOrderRecord> list = new ArrayList<LoadOrderRecord>();
            if (CollectionUtils.isNotEmpty(loadOrders)) {
                for (LoadOrder order : loadOrders) {
                    LoadOrderRecord record = new LoadOrderRecord();
                    BeanUtils.copyProperties(record, order);
                    record.setLoadAmtView(String.format("%.2f", Double.valueOf(order.getLoadAmt() + "") / 100));
                    record.setCustomerPayAmtView(String.format("%.2f", Double.valueOf(order.getLoadAmt() + "") / 100));
                    list.add(record);
                }
            }
            DodopalDataPage<LoadOrderRecord> pages = new DodopalDataPage<LoadOrderRecord>(prdOrderQuery.getPage(), list);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(pages);
        }
        catch (Exception e) {
            logger.error("查询圈存订单时发生错误:", e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<String> excelLoadOrder(HttpServletResponse response, ProLoadOrderQuery prdOrderQuery) {
        DodopalResponse<String> reponse = new DodopalResponse<String>();
        try {
            List<LoadOrder> loadOrders = loadOrderMapper.excelLoadOrder(prdOrderQuery);
            List<LoadOrderRecord> list = new ArrayList<LoadOrderRecord>();
            if (loadOrders.size() > ExcelUtil.EXPORT_MAX_COUNT) {
                reponse.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
            } else {
                if (CollectionUtils.isNotEmpty(loadOrders)) {
                    for (LoadOrder order : loadOrders) {
                        LoadOrderRecord record = new LoadOrderRecord();
                        BeanUtils.copyProperties(record, order);
                        record.setLoadAmtView(String.format("%.2f", Double.valueOf(order.getLoadAmt() + "") / 100));
                        record.setCustomerPayAmtView(String.format("%.2f", Double.valueOf(order.getLoadAmt() + "") / 100));
                        list.add(record);
                    }
                } else {
                    reponse.setCode(ResponseCode.EXCEL_EXPORT_NULL_DATA);
                    return reponse;
                }
            }

            //【写法二，使用自定义模板，需指定模板名称，并指定数据开始行】
            ExcelModel excelModel = new ExcelModel("LoadOrderList");
            excelModel.setDataStartIndex(3); //数据开始行, 可以不设置,默认为3(第四行)
            List<List<String>> dataList = new ArrayList<List<String>>(list.size());
            for (LoadOrderRecord data : list) {
                List<String> rowList = new ArrayList<String>();
                //订单编号
                if (StringUtils.isNotBlank(data.getOrderNum())) {
                    rowList.add(data.getOrderNum());
                } else {
                    rowList.add("");
                }
                //城市名称
                if (StringUtils.isNotBlank(data.getCityName())) {
                    rowList.add(data.getCityName());
                } else {
                    rowList.add("");
                }
                //应收金额
                if (StringUtils.isNotBlank(data.getLoadAmtView())) {
                    rowList.add(data.getLoadAmtView());
                } else {
                    rowList.add("");
                }
                //实收金额
                if (StringUtils.isNotBlank(data.getCustomerPayAmtView())) {
                    rowList.add(data.getCustomerPayAmtView());
                } else {
                    rowList.add("");
                }
                //卡号
                if (StringUtils.isNotBlank(data.getCardFaceNum())) {
                    rowList.add(data.getCardFaceNum());
                } else {
                    rowList.add("");
                }
                //订单状态
                if (StringUtils.isNotBlank(data.getStatus())) {
                    rowList.add(data.getStatus());
                } else {
                    rowList.add("");
                }
                //订单时间
                if (data.getOrderTime() != null) {
                    rowList.add(DateUtils.dateToString(data.getOrderTime(), DateUtils.DATE_FULL_STR));
                } else {
                    rowList.add("");
                }
                // 【添加数据行】
                dataList.add(rowList);
            }
            // 【将数据集加入model】
            excelModel.setDataList(dataList);
            reponse.setCode(ResponseCode.SUCCESS);
            return ExcelUtil.excelExport(excelModel, response);
        }
        catch (Exception e) {
            reponse.setCode(ResponseCode.UNKNOWN_ERROR);
            e.printStackTrace();
        }
        return reponse;
    }

    @Override
    @Transactional(readOnly = true)
    public LoadOrder viewLoadOrderByLoadOrderNum(String loadOrderNum) {
        return loadOrderMapper.viewLoadOrderByLoadOrderNum(loadOrderNum);
    }
    
    @Transactional(readOnly = true)
    public List<LoadOrder> findLoadOrdersExport(LoadOrderQueryDTO query){
       return loadOrderMapper.findLoadOrdersExport(query);
    }

    @Override
    public DodopalResponse<LoadOrder> bookLoadOrderForPaySys(IcdcLoadOrderRequestDTO icdcLoadOrderRequestDTO) {
        DodopalResponse<LoadOrder> response = new DodopalResponse<LoadOrder>();
        response.setCode(ResponseCode.PRODUCT_LOADORDER_REQUEST_ERROR);
        LoadOrder loadOrder = new LoadOrder();

        // 入参参数一般性验证
        if (!DDPStringUtil.existingWithLength(icdcLoadOrderRequestDTO.getCardNum(), 32)) {
            logger.error("cardNum 不能为空或长度不能大于32位" + icdcLoadOrderRequestDTO.getCardNum());
            return response;
        }
        if (!DDPStringUtil.existingWithLength(icdcLoadOrderRequestDTO.getCustomerCode(), 40)) {
            logger.error("customerCode 不能为空或长度不能大于32位" + icdcLoadOrderRequestDTO.getCustomerCode());
            return response;
        }
        if (DDPStringUtil.isNotPopulated(icdcLoadOrderRequestDTO.getProductNum())) {
            logger.error("productNum 不能为空");
            return response;
        }
        loadOrder.setSource(icdcLoadOrderRequestDTO.getSource());
        loadOrder.setCardFaceNum(icdcLoadOrderRequestDTO.getCardNum());
        loadOrder.setPayType(icdcLoadOrderRequestDTO.getPayType());
        loadOrder.setPaywayId(icdcLoadOrderRequestDTO.getPayWayId());

        // 产品合法性验证
        PrdProductYktInfoForIcdcRecharge productInfo = prdProductYktMapper.getProductInfoForIcdcRecharge(icdcLoadOrderRequestDTO.getProductNum());
        if (productInfo == null || !ProductStatusEnum.ENABLE.getCode().equals(productInfo.getProStatus())) {
            logger.error("该产品已下架，请重新选择产品。");
            response.setCode(ResponseCode.PRODUCT_CHECK_PRODUCT_STATUS_DISABLE);// 该产品已下架，请重新选择产品。
            return response;
        }
        Area area = areaService.findCityByCityCode(productInfo.getCityCode());
        if (area == null) {
            logger.error("城市尚未启用，请联系客服人员。");
            response.setCode(ResponseCode.PRODUCT_CHECK_YKT_STATUS_DISABLE);// 城市尚未启用，请联系客服人员。
            return response;
        }
        loadOrder.setProductCode(productInfo.getProCode());
        loadOrder.setProductName(productInfo.getProName());
        loadOrder.setLoadAmt(productInfo.getProPrice());
        loadOrder.setCityCode(area.getCityCode());
        loadOrder.setCityName(area.getCityName());

        // 通卡合法性验证(根据产品编号获取通卡编号，检验通卡公司是否处于启用状态,相关充值业务是否处于开通状态)
        ProductYKT productYkt = productYKTMapper.getYktInfoByYktCode(productInfo.getYktCode());
        if (productYkt == null || !ActivateEnum.ENABLE.getCode().equals(productYkt.getActivate()) || !OpenSignEnum.OPENED.getCode().equals(productYkt.getYktIsRecharge())) {
            logger.error("城市尚未启用，请联系客服人员。");
            response.setCode(ResponseCode.PRODUCT_CHECK_YKT_STATUS_DISABLE);// 城市尚未启用，请联系客服人员
            return response;
        }
        loadOrder.setYktCode(productYkt.getYktCode());
        loadOrder.setYktName(productYkt.getYktName());
        // 商户/个人合法性验证
        DodopalResponse<Map<String, String>> merMapResponse = merchantDelegate.validateMerchantForIcdcLoad(MerUserTypeEnum.MERCHANT, icdcLoadOrderRequestDTO.getCustomerCode(), productYkt.getYktCode());
        if (!ResponseCode.SUCCESS.equals(merMapResponse.getCode())) {
            logger.error("商户合法性验证不通过：" + merMapResponse.getMessage());
            response.setCode(merMapResponse.getCode());
            return response;
        }
        Map<String, String> merInfoMap = merMapResponse.getResponseEntity();
        String rateType = merInfoMap.containsKey("rateType") ? merInfoMap.get("rateType") : "";
        double rate = merInfoMap.containsKey("rate") ? Double.parseDouble(merInfoMap.get("rate")) : 0d;
        String custName = merInfoMap.containsKey("custName") ? merInfoMap.get("custName") : "";

        loadOrder.setCustomerCode(icdcLoadOrderRequestDTO.getCustomerCode());// 这里的客户是用户
        loadOrder.setCustomerName(custName);
        loadOrder.setCustomerType(MerUserTypeEnum.MERCHANT.getCode());// 固定值：商户
        loadOrder.setLoadRate(rate);
        loadOrder.setLoadRateType(rateType);
        loadOrder.setCustomerPayAmt(loadOrder.getLoadAmt());
        loadOrder.setCustomerGain(loadOrder.getCustomerPayAmt() - loadOrder.getLoadAmt());

        // 个人用户__支付服务费与服务费率类型(固定值：0，千分比)
        loadOrder.setPayServiceRate((double) 0);
        loadOrder.setPayServiceType(ServiceRateTypeEnum.PERMILLAGE.getCode());

        loadOrder.setOrderNum(this.generateOrderNum());// 数据库SEQ
        loadOrder.setStatus(LoadOrderStatusEnum.ORDER_CREATE.getCode());// 状态初始值：订单创建成功 
        loadOrder.setCreateUser(icdcLoadOrderRequestDTO.getCustomerCode());
        loadOrderMapper.insertLoadOrder(loadOrder);
        response.setCode(ResponseCode.SUCCESS);
        response.setResponseEntity(loadOrder);
        return response;
    }

    @Override
    @Transactional
    public int updateLoadOrderStatus(String loadOrderStatus, String loadOrderNum, String updateUser) {
        int num = loadOrderMapper.updateLoadOrderStatus(loadOrderStatus, loadOrderNum, updateUser);
        return num;
    }
    
}
