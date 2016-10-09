package com.dodopal.oss.business.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.ProductOrderDTO;
import com.dodopal.api.product.dto.ProductOrderDetailDTO;
import com.dodopal.api.product.dto.ProductOrderForExport;
import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ClearingMarkEnum;
import com.dodopal.common.enums.FundProcessResultEnum;
import com.dodopal.common.enums.LoadFlagEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.enums.RateTypeEnum;
import com.dodopal.common.enums.RechargeOrderInternalStatesEnum;
import com.dodopal.common.enums.RechargeOrderStatesEnum;
import com.dodopal.common.enums.ServiceRateTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.SuspiciousStatesEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.oss.business.bean.ProductOrder;
import com.dodopal.oss.business.service.ProductOrderService;
import com.dodopal.oss.delegate.MerchantDelegate;
import com.dodopal.oss.delegate.ProductOrderDelegate;

@Service
public class ProductOrderServiceImpl implements ProductOrderService {

    @Autowired
    private ProductOrderDelegate productOrderDelegate;
    @Autowired
    MerchantDelegate merchantDelegate;

    /**
     * 异常充值订单查询分页 
     */
    @Override
    public DodopalResponse<DodopalDataPage<ProductOrderDTO>> findExceptionProductOrderByPage(ProductOrderQueryDTO prdOrderQuery) {
        return productOrderDelegate.findExceptionProductOrderByPage(prdOrderQuery);
    }

    /**
     * 订单查询 产品库提供标准的订单查询页面给终端商户（各种网点）和个人。
     */
    @Override
    public DodopalResponse<DodopalDataPage<ProductOrder>> findProductOrder(ProductOrderQueryDTO prdOrderQuery) {
        DodopalResponse<DodopalDataPage<ProductOrder>> response = new DodopalResponse<DodopalDataPage<ProductOrder>>();
        DodopalResponse<DodopalDataPage<ProductOrderDTO>> productOrderDTO = productOrderDelegate.findProductOrder(prdOrderQuery);
        
        				
        List<ProductOrderDTO> records = productOrderDTO.getResponseEntity().getRecords();
        List<ProductOrder> list = new ArrayList<ProductOrder>();
        try {
            if (CollectionUtils.isNotEmpty(records)) {
                for (ProductOrderDTO dto : records) {
                    ProductOrder productOrder = new ProductOrder();
                    PropertyUtils.copyProperties(productOrder, dto);
                    DodopalResponse<MerchantDTO> merchant = merchantDelegate.findMerchantByCode(dto.getMerCode());
                    productOrder.setMerchantbean(merchant.getResponseEntity());
                    productOrder.setWeekDay(getWeekOfDate(productOrder.getProOrderDate()));
                    list.add(productOrder);
                }
            }
            PageParameter page = DodopalDataPageUtil.convertPageInfo(productOrderDTO.getResponseEntity());
            DodopalDataPage<ProductOrder> pages = new DodopalDataPage<ProductOrder>(page, list);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(pages);
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**
     * 订单查看 用户选择一条公交卡充值订单，点击“查看”按钮，页面向用户展示详情。 订单编号、商户名称、产品编号
     * 、产品名称、充值金额、城市、实付金额、成本价（商户进货价）、订单时间、商户利润（这个字段对个人用户不要显示）、卡号、充值前金额
     * 、充值后金额、充值前账户可用余额、充值后账户可用余额、订单状态、外部订单号（仅限于外部商户）、操作员名称、POS编号、POS备注。
     */
    @Override
    public DodopalResponse<ProductOrderDetailDTO> findProductOrderDetails(String proOrderNum) {
        return productOrderDelegate.findProductOrderDetails(proOrderNum);
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
     * 充值异常订单导出
     */
    @Override
    public DodopalResponse<List<ProductOrderDTO>> excelExceptionProductOrderExport(ProductOrderQueryDTO prdOrderQuery) {
        return productOrderDelegate.findProductExceptionOrdersForExport(prdOrderQuery);
    }

    /**
     * 充值订单导出
     */
    @Override
    public DodopalResponse<List<ProductOrderForExport>> getRechargeOrderListForExportExcel(ProductOrderQueryDTO prdOrderQuery) {
        DodopalResponse<List<ProductOrderForExport>> response = new DodopalResponse<List<ProductOrderForExport>>();
        try {
            DodopalResponse<List<ProductOrderForExport>> productOrderDTOResponse = productOrderDelegate.findProductOrdersForExport(prdOrderQuery);
            response.setCode(productOrderDTOResponse.getCode());

            if (ResponseCode.SUCCESS.equals(productOrderDTOResponse.getCode())) {
                List<ProductOrderForExport> records = productOrderDTOResponse.getResponseEntity();
                for (ProductOrderForExport dto : records) {
                    DecimalFormat df = new DecimalFormat("0.00");
                    // 一卡通充值费率
                    dto.setYktRechargeRate(dto.getYktRechargeRate() == null ? dto.getYktRechargeRate() : df.format(Double.valueOf(dto.getYktRechargeRate())));
                    // 充值面额
                    dto.setTxnAmt(dto.getTxnAmt() == null ? dto.getTxnAmt() : df.format(Double.valueOf(dto.getTxnAmt()) / 100));
                    // 商户实付金额(DDP实收金额)
                    dto.setReceivedPrice(dto.getReceivedPrice() == null ? dto.getReceivedPrice() : df.format(Double.valueOf(dto.getReceivedPrice()) / 100));
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
                    if (ServiceRateTypeEnum.SINGLE_AMOUNT.getCode().equals(dto.getPayServiceType())) {
                        dto.setPayServiceType(ServiceRateTypeEnum.SINGLE_AMOUNT.getName());
                        // 服务费率
                        dto.setPayServiceRate(dto.getPayServiceRate() == null ? dto.getPayServiceRate() : df.format(Double.valueOf(dto.getPayServiceRate()) / 100));
                    } else if (ServiceRateTypeEnum.PERMILLAGE.getCode().equals(dto.getPayServiceType())) {
                        dto.setPayServiceType(ServiceRateTypeEnum.PERMILLAGE.getName());
                        // 服务费率
                        dto.setPayServiceRate(dto.getPayServiceRate() == null ? dto.getPayServiceRate() : df.format(Double.valueOf(dto.getPayServiceRate())));
                    }
                    // 商户进货价（DDP的出货价）
                    dto.setMerPurchaasePrice(dto.getMerPurchaasePrice() == null ? dto.getMerPurchaasePrice() : df.format(Double.valueOf(dto.getMerPurchaasePrice()) / 100));
                    // 商户利润
                    dto.setMerGain(dto.getMerGain() == null ? dto.getMerGain() : df.format(Double.valueOf(dto.getMerGain()) / 100));
                    // 公交卡充值前金额
                    dto.setBefbal(dto.getBefbal() == null ? dto.getBefbal() : df.format(Double.valueOf(dto.getBefbal()) / 100));
                    // 公交卡充值后金额
                    dto.setBlackAmt(dto.getBlackAmt() == null ? dto.getBlackAmt() : df.format(Double.valueOf(dto.getBlackAmt()) / 100));
                    // 支付类型
                    dto.setPayType(PayTypeEnum.getPayTypeNameByCode(dto.getPayType()));
                    // 订单状态
                    if (RechargeOrderStatesEnum.checkCodeExist(dto.getProOrderState())) {
                        dto.setProOrderState(RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(dto.getProOrderState()).getName());
                    }
                    // 订单内部状态
                    dto.setProInnerState(RechargeOrderInternalStatesEnum.getRechargeOrderInternalStatesNameByCode(dto.getProInnerState()));
                    // 订单前内部状态
                    dto.setProBeforInnerState(RechargeOrderInternalStatesEnum.getRechargeOrderInternalStatesNameByCode(dto.getProBeforInnerState()));
                    // 可疑处理状态
                    dto.setProSuspiciousState(SuspiciousStatesEnum.getSuspiciousStateNameByCode(dto.getProSuspiciousState()));
                    // 客户类型
                    dto.setMerUserType(MerTypeEnum.getNameByCode(dto.getMerUserType()));
                    // 圈存标识
                    dto.setLoadFlag(LoadFlagEnum.getLoadFlagNameByCode(dto.getLoadFlag()));
                    // 来源
                    dto.setSource(SourceEnum.getSourceNameByCode(dto.getSource()));
                    // 清算标志
                    dto.setClearingMark(ClearingMarkEnum.getClearingMarkNameByCode(dto.getClearingMark()));
                    // 资金处理结果
                    if (FundProcessResultEnum.checkCodeExist(dto.getFundProcessResult())) {
                        dto.setFundProcessResult(FundProcessResultEnum.getFundProcessResultByCode(dto.getFundProcessResult()).getName());
                    }
                    // 充值时间段
                    String time = DateUtils.formatDate(DateUtils.stringtoDate(dto.getProOrderDate(), DateUtils.DATE_FULL_STR), DateUtils.DATE_FORMAT_HHMMSS_STR);
                    dto.setProOrderTime(getTime(time));
                    dto.setProOrderDate(DateUtils.formatDate(DateUtils.stringtoDate(dto.getProOrderDate(), DateUtils.DATE_FULL_STR), DateUtils.DATE_FULL_STR));
                    
                    dto.setCreateDate(DateUtils.formatDate(DateUtils.stringtoDate(dto.getCreateDate(), DateUtils.DATE_FULL_STR), DateUtils.DATE_FULL_STR));
                    dto.setUpdateDate(DateUtils.formatDate(DateUtils.stringtoDate(dto.getUpdateDate(), DateUtils.DATE_FULL_STR), DateUtils.DATE_FULL_STR));
                    
                    DodopalResponse<MerchantDTO> merchant = merchantDelegate.findMerchantByCode(dto.getMerCode());
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
                    dto.setWeekDay(getWeekOfDate(DateUtils.stringtoDate(dto.getProOrderDate(), DateUtils.DATE_FULL_STR)));
                }
                response.setResponseEntity(records);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
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

