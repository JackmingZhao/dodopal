package com.dodopal.oss.business.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.transaction.annotation.Transactional;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.product.dto.ProductConsumeOrderDTO;
import com.dodopal.api.product.dto.ProductConsumeOrderDetailDTO;
import com.dodopal.api.product.dto.ProductConsumerOrderForExport;
import com.dodopal.api.product.dto.query.ProductConsumeOrderQueryDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ClearingMarkEnum;
import com.dodopal.common.enums.FundProcessResultEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.enums.PurchaseOrderRedordStatesEnum;
import com.dodopal.common.enums.PurchaseOrderStatesEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.RateTypeEnum;
import com.dodopal.common.enums.ServiceRateTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.SuspiciousStatesEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.oss.business.bean.ProductConsumeOrder;
import com.dodopal.oss.business.bean.ProductConsumeOrderDetail;
import com.dodopal.oss.business.bean.query.ProductConsumeOrderQuery;
import com.dodopal.oss.business.service.IcdcPrdService;
import com.dodopal.oss.delegate.IcdcPrdDelegate;
import com.dodopal.oss.delegate.MerchantDelegate;
import com.dodopal.oss.delegate.bean.IcdcPrdBean;
import com.dodopal.oss.delegate.bean.IcdcPrdQuery;

@Service
public class IcdcPrdServiceImpl implements IcdcPrdService {
    private final static Logger log = LoggerFactory.getLogger(IcdcPrdServiceImpl.class);
    @Autowired
    IcdcPrdDelegate icdcPrdDelegate;
    @Autowired
    MerchantDelegate merchantDelegate;

    //条件查询
    @Transactional(readOnly = true)
    @Override
    public DodopalResponse<DodopalDataPage<IcdcPrdBean>> queryIcdcPrdPageByCondition(IcdcPrdQuery icdcPrdQuery) {
        DodopalResponse<DodopalDataPage<IcdcPrdBean>> response = null;
        try {
            response = icdcPrdDelegate.queryIcdcPrdPageByCondition(icdcPrdQuery);
        }
        catch (HessianRuntimeException e) {
            log.debug("queryIcdcPrdPageByCondition call error", e);
            e.printStackTrace();
            response = new DodopalResponse<>();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("queryIcdcPrdPageByCondition call error", e);
            e.printStackTrace();
            response = new DodopalResponse<>();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Transactional
    public DodopalResponse<String> updateIcdcPrd(List<IcdcPrdBean> icdcPrdBean) {
        DodopalResponse<String> response = null;
        try {
            response = icdcPrdDelegate.updateIcdcPrd(icdcPrdBean);
        }
        catch (HessianRuntimeException e) {
            log.debug("updateIcdcPrd call error", e);
            e.printStackTrace();
            response = new DodopalResponse<>();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("updateIcdcPrd call error", e);
            e.printStackTrace();
            response = new DodopalResponse<>();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Transactional
    @Override
    public DodopalResponse<String> saveIcdcPrd(List<IcdcPrdBean> icdcPrdBean) {
        DodopalResponse<String> response = null;
        DodopalResponse<Boolean> isExsitResponse = null;
        try {
            isExsitResponse = icdcPrdDelegate.checkPrdProductYktExist(icdcPrdBean);
            //已经存在
            if (!isExsitResponse.getCode().equals(ResponseCode.SUCCESS)) {
                response = new DodopalResponse<>();
                response.setCode(isExsitResponse.getCode());
                return response;
            }
            response = icdcPrdDelegate.saveIcdcPrd(icdcPrdBean);
        }
        catch (HessianRuntimeException e) {
            log.debug("saveIcdcPrd call error", e);
            e.printStackTrace();
            response = new DodopalResponse<>();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("saveIcdcPrd call error", e);
            e.printStackTrace();
            response = new DodopalResponse<>();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Transactional(readOnly = true)
    @Override
    public DodopalResponse<IcdcPrdBean> queryIcdcPrdByCode(String code) {
        DodopalResponse<IcdcPrdBean> response = null;
        try {
            response = icdcPrdDelegate.queryIcdcPrdByCode(code);
        }
        catch (HessianRuntimeException e) {
            log.debug("queryIcdcPrdByCode call error", e);
            e.printStackTrace();
            response = new DodopalResponse<>();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("queryIcdcPrdByCode call error", e);
            e.printStackTrace();
            response = new DodopalResponse<>();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Transactional
    @Override
    public DodopalResponse<String> putAwayIcdcPrd(List<String> icdcPrdCode, String updateUser) {
        DodopalResponse<String> response = null;
        try {
            response = icdcPrdDelegate.putAwayIcdcPrd(icdcPrdCode, updateUser);
        }
        catch (HessianRuntimeException e) {
            log.debug("putAwayIcdcPrd call error", e);
            e.printStackTrace();
            response = new DodopalResponse<>();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("putAwayIcdcPrd call error", e);
            e.printStackTrace();
            response = new DodopalResponse<>();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Transactional
    @Override
    public DodopalResponse<String> soltOutIcdcPrd(List<String> icdcPrdCode, String updateUser) {
        DodopalResponse<String> response = null;
        try {
            response = icdcPrdDelegate.soltOutIcdcPrd(icdcPrdCode, updateUser);
        }
        catch (HessianRuntimeException e) {
            log.debug("soltOutIcdcPrd call error", e);
            e.printStackTrace();
            response = new DodopalResponse<>();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("soltOutIcdcPrd call error", e);
            e.printStackTrace();
            response = new DodopalResponse<>();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<List<Map<String, String>>> queryIcdcNames(String type) {
        DodopalResponse<List<Map<String, String>>> response = null;
        try {
            response = icdcPrdDelegate.queryIcdcNames(type);
        }
        catch (HessianRuntimeException e) {
            log.debug("queryIcdcNames call error", e);
            e.printStackTrace();
            response = new DodopalResponse<>();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("queryIcdcNames call error", e);
            e.printStackTrace();
            response = new DodopalResponse<>();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Transactional(readOnly = true)
    @Override
    public DodopalResponse<List<Map<String, String>>> queryIcdcBusiCities(String code) {
        DodopalResponse<List<Map<String, String>>> response = null;
        try {
            response = icdcPrdDelegate.queryIcdcBusiCities(code);
        }
        catch (HessianRuntimeException e) {
            log.debug("queryIcdcBusiCities call error", e);
            e.printStackTrace();
            response = new DodopalResponse<>();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("queryIcdcBusiCities call error", e);
            e.printStackTrace();
            response = new DodopalResponse<>();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    /**
     * 查询 一卡通消费 收单记录
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<DodopalDataPage<ProductConsumeOrder>> findProductConsumeOrderByPage(ProductConsumeOrderQuery prdOrderQuery) {
        DodopalResponse<DodopalDataPage<ProductConsumeOrder>> response = new DodopalResponse<DodopalDataPage<ProductConsumeOrder>>();
        try {
            ProductConsumeOrderQueryDTO queryDTO = new ProductConsumeOrderQueryDTO();
            PropertyUtils.copyProperties(queryDTO, prdOrderQuery);
            DodopalResponse<DodopalDataPage<ProductConsumeOrderDTO>> productConsumeOrders = icdcPrdDelegate.findProductConsumeOrderByPage(queryDTO);
            List<ProductConsumeOrderDTO> records = productConsumeOrders.getResponseEntity().getRecords();
            List<ProductConsumeOrder> list = new ArrayList<ProductConsumeOrder>();
            if (CollectionUtils.isNotEmpty(records)) {
                for (ProductConsumeOrderDTO record : records) {
                    DodopalResponse<MerchantDTO> merchant = merchantDelegate.findMerchantByCode(record.getCustomerCode());
                    ProductConsumeOrder order = new ProductConsumeOrder();
                    PropertyUtils.copyProperties(order, record);
                    order.setMerchantbean(merchant.getResponseEntity());
                    order.setWeekDay(getWeekOfDate(order.getProOrderDate()));
                    list.add(order);
                }
            }
            PageParameter page = DodopalDataPageUtil.convertPageInfo(productConsumeOrders.getResponseEntity());
            DodopalDataPage<ProductConsumeOrder> pages = new DodopalDataPage<ProductConsumeOrder>(page, list);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(pages);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("产品库中公交卡 消费订单查询出错", e);
            response.setCode(ResponseCode.PRODUCT_ORDER_QUERY_ERROR);
        }
        return response;
    }

    /**
     * 根据消费订单号 orderNum 查询一卡通消费订单详情
     * @param orderNum 订单号
     * @return
     */
    public DodopalResponse<ProductConsumeOrderDetail> findProductConsumeOrderDetails(String orderNum) {
        log.info("IcdcPrdServiceImpl findProductConsumeOrderDetails orderNum=" + orderNum);
        DodopalResponse<ProductConsumeOrderDetail> response = new DodopalResponse<ProductConsumeOrderDetail>();
        try {
            DodopalResponse<ProductConsumeOrderDetailDTO> orderDetails = icdcPrdDelegate.findProductConsumeOrderDetails(orderNum);
            if (DDPStringUtil.isNotPopulated(orderNum)) {
                response.setCode(ResponseCode.PRODUCT_ORDER_NUMBER_EMPTY);
            } else {
                PropertyUtils.copyProperties(response, orderDetails);
            }
        }
        catch (Exception e) {
            log.error("产品库中公交卡消费订单查看详细信息出错", e);
            response.setCode(ResponseCode.PRODUCT_ORDER_DETAIL_QUERY_ERROR);
        }
        return response;
    }

    /**
     * 根据日期获得星期
     * @param date
     * @return
     */
    public String getWeekOfDate(Date date) {
        String[] weekDaysName = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        //String[] weekDaysCode = {"0", "1", "2", "3", "4", "5", "6"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysName[intWeek];
    }

    /**
     * 查询 一卡通消费 收单记录
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<DodopalDataPage<ProductConsumeOrder>> findProductConsumeOrdersExptionByPage(ProductConsumeOrderQuery prdOrderQuery) {
        DodopalResponse<DodopalDataPage<ProductConsumeOrder>> response = new DodopalResponse<DodopalDataPage<ProductConsumeOrder>>();
        try {
            ProductConsumeOrderQueryDTO queryDTO = new ProductConsumeOrderQueryDTO();
            PropertyUtils.copyProperties(queryDTO, prdOrderQuery);
            DodopalResponse<DodopalDataPage<ProductConsumeOrderDTO>> productConsumeOrders = icdcPrdDelegate.findProductConsumeOrdersExptionByList(queryDTO);
            List<ProductConsumeOrderDTO> records = productConsumeOrders.getResponseEntity().getRecords();
            List<ProductConsumeOrder> list = new ArrayList<ProductConsumeOrder>();
            if (CollectionUtils.isNotEmpty(records)) {
                for (ProductConsumeOrderDTO record : records) {
                    ProductConsumeOrder order = new ProductConsumeOrder();
                    PropertyUtils.copyProperties(order, record);
                    list.add(order);
                }
            }
            PageParameter page = DodopalDataPageUtil.convertPageInfo(productConsumeOrders.getResponseEntity());
            DodopalDataPage<ProductConsumeOrder> pages = new DodopalDataPage<ProductConsumeOrder>(page, list);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(pages);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("产品库中公交卡 消费订单查询出错", e);
            response.setCode(ResponseCode.PRODUCT_ORDER_QUERY_ERROR);
        }
        return response;
    }
    
    /**
     * 导出消费异常记录
     */
    @Override
    public DodopalResponse<List<ProductConsumeOrder>> excelExceptionProductOrderExport(ProductConsumeOrderQuery prdOrderQuery) {
        DodopalResponse<List<ProductConsumeOrder>> response = new DodopalResponse<List<ProductConsumeOrder>>();
        try {
            ProductConsumeOrderQueryDTO queryDTO = new ProductConsumeOrderQueryDTO();
            PropertyUtils.copyProperties(queryDTO, prdOrderQuery);
            DodopalResponse<List<ProductConsumeOrderDTO>> productConsumeOrders = icdcPrdDelegate.excelExceptionProductOrderExport(queryDTO);
            if (ResponseCode.SUCCESS.equals(productConsumeOrders.getCode())) {
                List<ProductConsumeOrderDTO> records = productConsumeOrders.getResponseEntity();
                List<ProductConsumeOrder> list = new ArrayList<ProductConsumeOrder>();
                if (CollectionUtils.isNotEmpty(records)) {
                    for (ProductConsumeOrderDTO record : records) {
                        ProductConsumeOrder order = new ProductConsumeOrder();
                        PropertyUtils.copyProperties(order, record);
                        order.setPayServiceType(ServiceRateTypeEnum.getServiceRateTypeByCode(record.getPayServiceType()).getName());
                        // 折扣
                        if (StringUtils.isBlank(order.getMerDiscount()) || "0".equals(order.getMerDiscount()) || "10".equals(order.getMerDiscount())) {
                            order.setMerDiscount("无");
                        }
                        list.add(order);
                    }
                }
                response.setResponseEntity(list);
                response.setCode(ResponseCode.SUCCESS);
            } else {
                response.setCode(productConsumeOrders.getCode());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("导出消费异常记录失败", e);
            response.setCode(ResponseCode.PRODUCT_ORDER_QUERY_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<List<IcdcPrdBean>> getIcdcPrductListForExportExcel(IcdcPrdQuery icdcPrdQuery) {
        DodopalResponse<List<IcdcPrdBean>> response = null;
        try {
            response = icdcPrdDelegate.getIcdcPrductListForExportExcel(icdcPrdQuery);
        }
        catch (HessianRuntimeException e) {
            log.debug("getIcdcPrductListForExportExcel call error", e);
            e.printStackTrace();
            response = new DodopalResponse<>();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("getIcdcPrductListForExportExcel call error", e);
            e.printStackTrace();
            response = new DodopalResponse<>();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    @Override
    public DodopalResponse<List<ProductConsumerOrderForExport>> getConsumerOrderListForExportExcel(ProductConsumeOrderQuery prdOrderQuery) {
        
        DodopalResponse<List<ProductConsumerOrderForExport>> rep = new DodopalResponse<List<ProductConsumerOrderForExport>>();
        
        try {
            ProductConsumeOrderQueryDTO queryDTO = new ProductConsumeOrderQueryDTO();
            PropertyUtils.copyProperties(queryDTO, prdOrderQuery);
            
            DodopalResponse<List<ProductConsumerOrderForExport>> orderDTOs = icdcPrdDelegate.getConsumerOrderListForExportExcel(queryDTO);
            rep.setCode(orderDTOs.getCode());
            
            if (ResponseCode.SUCCESS.equals(orderDTOs.getCode())) {
                List<ProductConsumerOrderForExport> prdConsumeList = orderDTOs.getResponseEntity();
                for (ProductConsumerOrderForExport dto : prdConsumeList) {
                    DecimalFormat df = new DecimalFormat("0.00");
                    // 折扣
                    if (StringUtils.isBlank(dto.getMerDiscount()) || "0".equals(dto.getMerDiscount()) || "10".equals(dto.getMerDiscount())) {
                        dto.setMerDiscount("无");
                    }
                    // 一卡通支付费率
                    dto.setYktPayRate(dto.getYktPayRate() == null ? dto.getYktPayRate() : df.format(Double.valueOf(dto.getYktPayRate())));
                    // 应收金额
                    dto.setOriginalPrice(dto.getOriginalPrice() == null ? dto.getOriginalPrice() : df.format(Double.valueOf(dto.getOriginalPrice()) / 100));
                    // 实收金额
                    dto.setReceivedPrice(dto.getReceivedPrice() == null ? dto.getReceivedPrice() : df.format(Double.valueOf(dto.getReceivedPrice()) / 100));
                    // 客户类型
                    dto.setCustomerType(MerTypeEnum.getNameByCode(dto.getCustomerType()));
                    // 业务类型
                    if (RateCodeEnum.checkCodeExist(dto.getBusinessType())) {
                        dto.setBusinessType(RateCodeEnum.getRateTypeByCode(dto.getBusinessType()).getName());
                    }
                    // 商户费率类型
                    if (RateTypeEnum.PERMILLAGE.getCode().equals(dto.getMerRateType())) {
                        dto.setMerRateType(RateTypeEnum.PERMILLAGE.getName());
                        // 商户费率
                        dto.setMerRate(dto.getMerRate() == null ? dto.getMerRate() : df.format(Double.valueOf(dto.getMerRate())));
                    } else if (RateTypeEnum.SINGLE_AMOUNT.getCode().equals(dto.getMerRateType())) {
                        dto.setMerRateType(RateTypeEnum.SINGLE_AMOUNT.getName());
                        // 商户费率
                        dto.setMerRate(dto.getMerRate() == null ? dto.getMerRate() : df.format(Double.valueOf(dto.getMerRate()) / 100));
                    }
                    // 服务费率类型
                    if (ServiceRateTypeEnum.SINGLE_AMOUNT.getCode().equals(dto.getServiceRateType())) {
                        dto.setServiceRateType(ServiceRateTypeEnum.SINGLE_AMOUNT.getName());
                        // 服务费率
                        dto.setServiceRate(dto.getServiceRate() == null ? dto.getServiceRate() : df.format(Double.valueOf(dto.getServiceRate()) / 100));
                    } else if (ServiceRateTypeEnum.PERMILLAGE.getCode().equals(dto.getServiceRateType())) {
                        dto.setServiceRateType(ServiceRateTypeEnum.PERMILLAGE.getName());
                     // 服务费率
                        dto.setServiceRate(dto.getServiceRate() == null ? dto.getServiceRate() : df.format(Double.valueOf(dto.getServiceRate())));
                    }
                    // 商户利润
                    dto.setMerGain(dto.getMerGain() == null ? dto.getMerGain() : df.format(Double.valueOf(dto.getMerGain()) / 100));
                    // 支付类型
                    dto.setPayType(PayTypeEnum.getPayTypeNameByCode(dto.getPayType()));
                    // 订单状态
                    if (PurchaseOrderStatesEnum.checkCodeExist(dto.getStates())) {
                        dto.setStates(PurchaseOrderStatesEnum.getProductConsumptionOrderStateByCode(dto.getStates()).getName());
                    }
                    // 来源
                    dto.setSource(SourceEnum.getSourceNameByCode(dto.getSource()));
                    // 清算标志
                    dto.setClearingMark(ClearingMarkEnum.getClearingMarkNameByCode(dto.getClearingMark()));
                    // 资金处理结果
                    if (FundProcessResultEnum.checkCodeExist(dto.getFundProcessResult())) {
                        dto.setFundProcessResult(FundProcessResultEnum.getFundProcessResultByCode(dto.getFundProcessResult()).getName());
                    }
                    // 支付前金额
                    dto.setBefbal(dto.getBefbal() == null ? dto.getBefbal() : df.format(Double.valueOf(dto.getBefbal()) / 100));
                    // 支付后金额
                    dto.setBlackAmt(dto.getBlackAmt() == null ? dto.getBlackAmt() : df.format(Double.valueOf(dto.getBlackAmt()) / 100));
                    // 内部状态
                    if (PurchaseOrderRedordStatesEnum.checkCodeExist(dto.getInnerStates())) {
                        dto.setInnerStates(PurchaseOrderRedordStatesEnum.getProductConsumptionOrderRedordStateByCode(dto.getInnerStates()).getName());
                    }
                    // 前内部状态
                    if (PurchaseOrderRedordStatesEnum.checkCodeExist(dto.getBeforInnerStates())) {
                        dto.setBeforInnerStates(PurchaseOrderRedordStatesEnum.getProductConsumptionOrderRedordStateByCode(dto.getBeforInnerStates()).getName());
                    }
                    // 可疑处理状态
                    dto.setSuspiciousStates(SuspiciousStatesEnum.getSuspiciousStateNameByCode(dto.getSuspiciousStates()));
                    
                    // 消费时间段
                    String time = DateUtils.formatDate(DateUtils.stringtoDate(dto.getOrderDate(), DateUtils.DATE_FULL_STR), DateUtils.DATE_FORMAT_HHMMSS_STR);
                    dto.setOrderTime(getTime(time));
                    dto.setOrderDate(DateUtils.formatDate(DateUtils.stringtoDate(dto.getOrderDate(), DateUtils.DATE_FULL_STR), DateUtils.DATE_FULL_STR));
                    
                    dto.setCreateDate(DateUtils.formatDate(DateUtils.stringtoDate(dto.getCreateDate(), DateUtils.DATE_FULL_STR), DateUtils.DATE_FULL_STR));
                    dto.setUpdateDate(DateUtils.formatDate(DateUtils.stringtoDate(dto.getUpdateDate(), DateUtils.DATE_FULL_STR), DateUtils.DATE_FULL_STR));
                    
                    DodopalResponse<MerchantDTO> merchant = merchantDelegate.findMerchantByCode(dto.getCustomerCode());
                    if (ResponseCode.SUCCESS.equals(merchant.getCode())) {
                        if (merchant.getResponseEntity() != null) {
                            dto.setMerParentName(merchant.getResponseEntity().getMerParentName());
                            dto.setMerBusinessScopeId(merchant.getResponseEntity().getMerBusinessScopeId());
                            // 经营范围
                            dto.setMerBusinessScopeId(dto.getMerBusinessScopeIdView());
                        }
                        if (merchant.getResponseEntity().getMerDdpInfo() != null) {
                            dto.setTradeArea(merchant.getResponseEntity().getMerDdpInfo().getTradeArea());
                        }
                    }
                    dto.setWeekDay(getWeekOfDate(DateUtils.stringtoDate(dto.getOrderDate(), DateUtils.DATE_FULL_STR)));
                }
                rep.setResponseEntity(prdConsumeList);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            rep.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return rep;
    }
    
    private static String getTime(String time){
        String timeString="";
        if (time.substring(0, 2).equals("23")) {
            timeString="23-24";
        } else {
            String hourStartStr=time.substring(0, 2);
            String hourEndStr="";
            int hour = Integer.parseInt(time.substring(0, 2));
            if (hour+1 <10) {
                hourEndStr = "0"+(hour+1);
            } else {
                hourEndStr = ""+(hour+1);
            }
            timeString = hourStartStr+"-"+ (hourEndStr);
        }
        return timeString;
    }
    
}

