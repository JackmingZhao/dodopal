package com.dodopal.oss.business.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.common.enums.ClearingFlagEnum;
import com.dodopal.common.enums.LoadFlagEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.RateTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.ExcelModel;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.dao.ClearingMapper;
import com.dodopal.oss.business.model.ClearingBasic;
import com.dodopal.oss.business.model.ClearingBasicDataDTO;
import com.dodopal.oss.business.model.dto.ClearingInfoQuery;
import com.dodopal.oss.business.service.ClearingService;

@Service
public class ClearingServiceImpl implements ClearingService {

    @Autowired
    private ClearingMapper clearingMapper;

    /**
     * 银行清分分页
     */
    @Override
    public DodopalDataPage<ClearingBasic> findBankClearingByPage(ClearingInfoQuery queryDto) {
        List<ClearingBasic> bankClearingList = clearingMapper.findBankClearingByPage(queryDto);
        for (ClearingBasic basic : bankClearingList) {
        	basic.setOrderDate(DateUtils.dateToString(DateUtils.stringtoDate(basic.getOrderDate(), DateUtils.DATE_FULL_STR), DateUtils.DATE_FULL_STR));
            if (StringUtils.isNotBlank(basic.getOrderFrom())) {
                basic.setOrderFrom(SourceEnum.getSourceNameByCode(basic.getOrderFrom()));//订单来源
            }
            if (StringUtils.isNotBlank(basic.getPayType())) {
                basic.setPayType(PayTypeEnum.getPayTypeNameByCode(basic.getPayType()));//支付类型
            }
            if (StringUtils.isNotBlank(basic.getBusinessType())) {
                basic.setBusinessType(RateCodeEnum.getRateTypeByCode(basic.getBusinessType()).getName());//业务类型
            }
            if (StringUtils.isNotBlank(basic.getBankClearingFlag())) {
                basic.setBankClearingFlag(ClearingFlagEnum.getClearingFlagByCode(basic.getBankClearingFlag()).getName());//银行清分标志
            }
            if (StringUtils.isNotBlank(basic.getDdpBankRate())) {
                basic.setDdpBankRate(String.format("%.4f", Double.valueOf(basic.getDdpBankRate())));//DDP与银行的手续费率
            }
            if (StringUtils.isNotBlank(basic.getDdpToBankFee())) {
                basic.setDdpToBankFee(String.format("%.2f", Double.valueOf(basic.getDdpToBankFee()) / 100));//DDP支付给银行的手续费
            }
            if (StringUtils.isNotBlank(basic.getDdpFromBankShouldFee())) {
                basic.setDdpFromBankShouldFee(String.format("%.2f", Double.valueOf(basic.getDdpFromBankShouldFee()) / 100));//DDP从银行应收业务费用
            }
            if (StringUtils.isNotBlank(basic.getDdpFromBankRealFee())) {
                basic.setDdpFromBankRealFee(String.format("%.2f", Double.valueOf(basic.getDdpFromBankRealFee()) / 100));//DDP从银行实收业务费用
            }
            if (StringUtils.isNotBlank(basic.getOrderCircle())) {
                basic.setOrderCircle(LoadFlagEnum.getLoadFlagNameByCode(basic.getOrderCircle()));//是否圈存
            }
            if(StringUtils.isNotBlank(basic.getBankClearingTime())){//与银行清分时间
            	basic.setBankClearingTime(DateUtils.dateToString(DateUtils.stringtoDate(basic.getBankClearingTime(), DateUtils.DATE_FULL_STR), DateUtils.DATE_FULL_STR));
            }
        }
        DodopalDataPage<ClearingBasic> pages = new DodopalDataPage<ClearingBasic>(queryDto.getPage(), bankClearingList);
        return pages;
    }

    /**
     * 通卡清分分页
     */
    @Override
    public DodopalDataPage<ClearingBasic> findYktClearingByPage(ClearingInfoQuery queryDto) {
        List<ClearingBasic> yktClearingList = clearingMapper.findYktClearingByPage(queryDto);
        for (ClearingBasic basic : yktClearingList) {
        	basic.setOrderDate(DateUtils.dateToString(DateUtils.stringtoDate(basic.getOrderDate(), DateUtils.DATE_FULL_STR), DateUtils.DATE_FULL_STR));
            if (StringUtils.isNotBlank(basic.getOrderFrom())) {
                basic.setOrderFrom(SourceEnum.getSourceNameByCode(basic.getOrderFrom()));//订单来源
            }
            if (StringUtils.isNotBlank(basic.getPayType())) {
                basic.setPayType(PayTypeEnum.getPayTypeNameByCode(basic.getPayType()));//支付类型
            }
            if (StringUtils.isNotBlank(basic.getBusinessType())) {
                basic.setBusinessType(RateCodeEnum.getRateTypeByCode(basic.getBusinessType()).getName());//业务类型
            }
            if (StringUtils.isNotBlank(basic.getSupplierClearingFlag())) {
                basic.setSupplierClearingFlag(ClearingFlagEnum.getClearingFlagByCode(basic.getSupplierClearingFlag()).getName());//供货商标志
            }
            if (StringUtils.isNotBlank(basic.getDdpToSupplierShouldAmount())) {
                basic.setDdpToSupplierShouldAmount(String.format("%.2f", Double.valueOf(basic.getDdpToSupplierShouldAmount()) / 100));//DDP应支付供应商本金
            }
            if (StringUtils.isNotBlank(basic.getDdpToSupplierRealAmount())) {
                basic.setDdpToSupplierRealAmount(String.format("%.2f", Double.valueOf(basic.getDdpToSupplierRealAmount()) / 100));//DDP实际支付供应商本金
            }
            if (StringUtils.isNotBlank(basic.getDdpSupplierRate())) {
                basic.setDdpSupplierRate(String.format("%.4f", Double.valueOf(basic.getDdpSupplierRate())));//DDP与供应商之间的费率
            }
            if (StringUtils.isNotBlank(basic.getSupplierToDdpShouldRebate())) {
                basic.setSupplierToDdpShouldRebate(String.format("%.2f", Double.valueOf(basic.getSupplierToDdpShouldRebate()) / 100));//供应商应支付DDP返点
            }
            if (StringUtils.isNotBlank(basic.getSupplierToDdpRealRebate())) {
                basic.setSupplierToDdpRealRebate(String.format("%.2f", Double.valueOf(basic.getSupplierToDdpRealRebate()) / 100));//供应商实际支付DDP返点
            }
            if (StringUtils.isNotBlank(basic.getOrderCircle())) {
                basic.setOrderCircle(LoadFlagEnum.getLoadFlagNameByCode(basic.getOrderCircle()));//是否圈存
            }
            
            if(StringUtils.isNotBlank(basic.getSupplierClearingTime())){//与供应商清分时间
            	basic.setSupplierClearingTime(DateUtils.dateToString(DateUtils.stringtoDate(basic.getSupplierClearingTime(), DateUtils.DATE_FULL_STR), DateUtils.DATE_FULL_STR));
            }
        }
        DodopalDataPage<ClearingBasic> pages = new DodopalDataPage<ClearingBasic>(queryDto.getPage(), yktClearingList);
        return pages;
    }

    /**
     * 商户清分分页
     */
    @Override
    public DodopalDataPage<ClearingBasic> findMerClearingByPage(ClearingInfoQuery queryDto) {
        List<ClearingBasic> merClearingList = clearingMapper.findMerClearingByPage(queryDto);
        for (ClearingBasic basic : merClearingList) {
        	basic.setOrderDate(DateUtils.dateToString(DateUtils.stringtoDate(basic.getOrderDate(), DateUtils.DATE_FULL_STR), DateUtils.DATE_FULL_STR));
            if (StringUtils.isNotBlank(basic.getOrderFrom())) {
                basic.setOrderFrom(SourceEnum.getSourceNameByCode(basic.getOrderFrom()));//订单来源
            }
            if (StringUtils.isNotBlank(basic.getPayType())) {
                basic.setPayType(PayTypeEnum.getPayTypeNameByCode(basic.getPayType()));//支付类型
            }
            if (StringUtils.isNotBlank(basic.getBusinessType())) {
                basic.setBusinessType(RateCodeEnum.getRateTypeByCode(basic.getBusinessType()).getName());//业务类型
            }
            if (StringUtils.isNotBlank(basic.getCustomerType())) {
                basic.setCustomerType(MerTypeEnum.getNameByCode(basic.getCustomerType()));//商户类型
            }
            if (StringUtils.isNotBlank(basic.getCustomerRateType())) {//商户费率类型
                if (StringUtils.isNotBlank(basic.getCustomerRate())) {//商户业务费率
                    if (RateTypeEnum.SINGLE_AMOUNT.getCode().equals(basic.getCustomerRateType())) {//费率类型为 单笔返点金额(元) (1)
                        basic.setCustomerRate(String.format("%.2f", (Double.valueOf(basic.getCustomerRate()) / 100)));
                    } else if (RateTypeEnum.PERMILLAGE.getCode().equals(basic.getCustomerRateType())) {//费率类型为 千分比(‰) (2)
                        basic.setCustomerRate(String.format("%.2f", (Double.valueOf(basic.getCustomerRate()))));
                    }
                }
                basic.setCustomerRateType(RateTypeEnum.getRateTypeByCode(basic.getCustomerRateType()).getName());
            }
            if (StringUtils.isNotBlank(basic.getCustoerRealPayAmount())) {
                basic.setCustoerRealPayAmount(String.format("%.2f", Double.valueOf(basic.getCustoerRealPayAmount()) / 100));//商户实际支付金额
            }
            if (StringUtils.isNotBlank(basic.getCustomerShouldProfit())) {
                basic.setCustomerShouldProfit(String.format("%.2f", Double.valueOf(basic.getCustomerShouldProfit()) / 100));//商户应得分润
            }
            if (StringUtils.isNotBlank(basic.getCustomerRealProfit())) {
                basic.setCustomerRealProfit(String.format("%.2f", Double.valueOf(basic.getCustomerRealProfit()) / 100));//商户实得分润
            }
            if (StringUtils.isNotBlank(basic.getCustomerAccountShouldAmount())) {
                basic.setCustomerAccountShouldAmount(String.format("%.2f", Double.valueOf(basic.getCustomerAccountShouldAmount()) / 100));//商户账户应加值
            }
            if (StringUtils.isNotBlank(basic.getCustomerAccountRealAmount())) {
                basic.setCustomerAccountRealAmount(String.format("%.2f", Double.valueOf(basic.getCustomerAccountRealAmount()) / 100));//商户账户实际加值
            }
            if (StringUtils.isNotBlank(basic.getDdpGetMerchantPayFee())) {
                basic.setDdpGetMerchantPayFee(String.format("%.2f", Double.valueOf(basic.getDdpGetMerchantPayFee()) / 100));//DDP应收商户支付方式服务费
            }
            if (StringUtils.isNotBlank(basic.getDdpToCustomerRealFee())) {
                basic.setDdpToCustomerRealFee(String.format("%.2f", Double.valueOf(basic.getDdpToCustomerRealFee()) / 100));//DDP实际转给商户业务费用
            }
            if (StringUtils.isNotBlank(basic.getCustomerClearingFlag())) {
                basic.setCustomerClearingFlag(ClearingFlagEnum.getClearingFlagByCode(basic.getCustomerClearingFlag()).getName());//客户清分标志
            }
            if (StringUtils.isNotBlank(basic.getOrderCircle())) {
                basic.setOrderCircle(LoadFlagEnum.getLoadFlagNameByCode(basic.getOrderCircle()));//是否圈存
            }
            if(StringUtils.isNotBlank(basic.getCustomerClearingTime())){//与客户清分时间
            	basic.setCustomerClearingTime(DateUtils.dateToString(DateUtils.stringtoDate(basic.getCustomerClearingTime(), DateUtils.DATE_FULL_STR), DateUtils.DATE_FULL_STR));
            }
        }
        DodopalDataPage<ClearingBasic> pages = new DodopalDataPage<ClearingBasic>(queryDto.getPage(), merClearingList);
        return pages;
    }

    /**
     * 银行导出
     */
    @Override
    public DodopalResponse<String> excelExportBank(HttpServletResponse response, ClearingInfoQuery bankQuery) {
        List<ClearingBasic> bankList = clearingMapper.exportBankClearing(bankQuery);

        // 列标题名称
        List<String> titleList = Arrays.asList("订单交易号", "订单时间", "业务类型", "支付类型", "支付方式名称", "订单来源", "是否圈存订单", "DDP与银行的手续费率‰", "DDP支付给银行的手续费(元)", "DDP从银行应收业务费用(元)", "DDP从银行实收业务费用(元)", "与银行清分状态", "与银行清分时间");
        // 导出文件名(不支持中文)
        String fileName = "BANK_CLEARING";
        // sheet标题
        String title = "银行清分";
        ExcelModel excelModel = new ExcelModel(fileName, title, titleList);

        //【处理数据，结果为List<List<String>>】
        if (CollectionUtils.isNotEmpty(bankList)) {
            List<List<String>> dataList = new ArrayList<List<String>>(bankList.size());
            for (ClearingBasic basic : bankList) {
                List<String> rowList = new ArrayList<String>();

                rowList.add(basic.getOrderNo());//订单交易号
                rowList.add(basic.getOrderDate());//订单时间
                if (StringUtils.isNotBlank(basic.getBusinessType())) {
                    rowList.add(RateCodeEnum.getRateTypeByCode(basic.getBusinessType()).getName());//业务类型
                } else {
                    rowList.add("");
                }

                if (StringUtils.isNotBlank(basic.getPayType())) {
                    rowList.add(PayTypeEnum.getPayTypeNameByCode(basic.getPayType()));//支付类型
                } else {
                    rowList.add("");
                }

                rowList.add(basic.getPayWayName());//支付方式

                if (StringUtils.isNotBlank(basic.getOrderFrom())) {
                    rowList.add(SourceEnum.getSourceNameByCode(basic.getOrderFrom()));//订单来源
                } else {
                    rowList.add("");
                }

                if (StringUtils.isNotBlank(basic.getOrderCircle())) {
                    rowList.add(LoadFlagEnum.getLoadFlagNameByCode(basic.getOrderCircle()));//是否圈存
                } else {
                    rowList.add("");
                }

                if (StringUtils.isNotBlank(basic.getDdpBankRate())) {
                    rowList.add(String.format("%.4f", Double.valueOf(basic.getDdpBankRate())));//DDP与银行的手续费率
                } else {
                    rowList.add("");
                }

                if (StringUtils.isNotBlank(basic.getDdpToBankFee())) {
                    rowList.add(String.format("%.2f", Double.valueOf(basic.getDdpToBankFee()) / 100));//DDP支付给银行的手续费
                } else {
                    rowList.add("");
                }

                if (StringUtils.isNotBlank(basic.getDdpFromBankShouldFee())) {
                    rowList.add(String.format("%.2f", Double.valueOf(basic.getDdpFromBankShouldFee()) / 100));//DDP从银行应收业务费用
                } else {
                    rowList.add("");
                }

                if (StringUtils.isNotBlank(basic.getDdpFromBankRealFee())) {
                    rowList.add(String.format("%.2f", Double.valueOf(basic.getDdpFromBankRealFee()) / 100));//DDP从银行实收业务费用
                } else {
                    rowList.add("");
                }

                if (StringUtils.isNotBlank(basic.getBankClearingFlag())) {
                    rowList.add(ClearingFlagEnum.getClearingFlagByCode(basic.getBankClearingFlag()).getName());//银行清分标志
                } else {
                    rowList.add("");
                }

                rowList.add(basic.getBankClearingTime());//与银行清分时间
                // 【添加数据行】
                dataList.add(rowList);
            }
            // 【将数据集加入model】
            excelModel.setDataList(dataList);
        }

        return ExcelUtil.excelExport(excelModel, response);
    }

    @Override
    public DodopalResponse<String> excelExportYkt(HttpServletResponse response, ClearingInfoQuery bankQuery) {
        List<ClearingBasic> yktList = clearingMapper.exportYktClearing(bankQuery);

        // 列标题名称
        List<String> titleList = Arrays.asList("订单交易号", "订单时间", "业务类型", "支付类型", "支付方式名称", "订单来源", "是否圈存订单", "供应商名称", "DDP应支付供应商本金(元)", "DDP实际支付供应商本金(元)", "DDP与供应商之间的费率‰", "供应商应支付DDP返点(元)", "供应商实际支付DDP返点(元)", "与供应商清分状态", "与供应商清分时间");
        // 导出文件名(不支持中文)
        String fileName = "YKT_CLEARING";
        // sheet标题
        String title = "供应商清分";
        ExcelModel excelModel = new ExcelModel(fileName, title, titleList);

        //【处理数据，结果为List<List<String>>】
        if (CollectionUtils.isNotEmpty(yktList)) {
            List<List<String>> dataList = new ArrayList<List<String>>(yktList.size());
            for (ClearingBasic basic : yktList) {
                List<String> rowList = new ArrayList<String>();

                rowList.add(basic.getOrderNo());//订单交易号
                rowList.add(basic.getOrderDate());//订单时间
                if (StringUtils.isNotBlank(basic.getBusinessType())) {
                    rowList.add(RateCodeEnum.getRateTypeByCode(basic.getBusinessType()).getName());//业务类型
                } else {
                    rowList.add("");
                }

                if (StringUtils.isNotBlank(basic.getPayType())) {
                    rowList.add(PayTypeEnum.getPayTypeNameByCode(basic.getPayType()));//支付类型
                } else {
                    rowList.add("");
                }

                rowList.add(basic.getPayWayName());//支付方式

                if (StringUtils.isNotBlank(basic.getOrderFrom())) {
                    rowList.add(SourceEnum.getSourceNameByCode(basic.getOrderFrom()));//订单来源
                } else {
                    rowList.add("");
                }

                if (StringUtils.isNotBlank(basic.getOrderCircle())) {
                    rowList.add(LoadFlagEnum.getLoadFlagNameByCode(basic.getOrderCircle()));//是否圈存
                } else {
                    rowList.add("");
                }

                rowList.add(basic.getSupplierName());//供应商名称

                if (StringUtils.isNotBlank(basic.getDdpToSupplierShouldAmount())) {
                    rowList.add(String.format("%.2f", Double.valueOf(basic.getDdpToSupplierShouldAmount()) / 100));//DDP应支付供应商本金
                } else {
                    rowList.add("");
                }
                if (StringUtils.isNotBlank(basic.getDdpToSupplierRealAmount())) {
                    rowList.add(String.format("%.2f", Double.valueOf(basic.getDdpToSupplierRealAmount()) / 100));//DDP实际支付供应商本金
                } else {
                    rowList.add("");
                }
                if (StringUtils.isNotBlank(basic.getDdpSupplierRate())) {
                    rowList.add(String.format("%.4f", Double.valueOf(basic.getDdpSupplierRate())));//DDP与供应商之间的费率
                } else {
                    rowList.add("");
                }
                if (StringUtils.isNotBlank(basic.getSupplierToDdpShouldRebate())) {
                    rowList.add(String.format("%.2f", Double.valueOf(basic.getSupplierToDdpShouldRebate()) / 100));//供应商应支付DDP返点
                } else {
                    rowList.add("");
                }
                if (StringUtils.isNotBlank(basic.getSupplierToDdpRealRebate())) {
                    rowList.add(String.format("%.2f", Double.valueOf(basic.getSupplierToDdpRealRebate()) / 100));//供应商实际支付DDP返点
                } else {
                    rowList.add("");
                }
                if (StringUtils.isNotBlank(basic.getSupplierClearingFlag())) {
                    rowList.add(ClearingFlagEnum.getClearingFlagByCode(basic.getSupplierClearingFlag()).getName());//供货商清分标志
                } else {
                    rowList.add("");
                }
                rowList.add(basic.getSupplierClearingTime());//与供货商清分时间
                // 【添加数据行】
                dataList.add(rowList);
            }
            // 【将数据集加入model】
            excelModel.setDataList(dataList);
        }

        return ExcelUtil.excelExport(excelModel, response);
    }

    @Override
    public DodopalResponse<String> excelExportMer(HttpServletResponse response, ClearingInfoQuery bankQuery) {
        List<ClearingBasic> merList = clearingMapper.exportMerClearing(bankQuery);

        // 列标题名称
        List<String> titleList = Arrays.asList("订单交易号", "订单时间", "业务类型", "支付类型", "支付方式名称", "订单来源", "是否圈存订单", "商户编号", "商户名称", "商户类型", "商户业务费率类型", "商户业务费率", "商户实际支付金额(元)", "商户应得分润(元)", "商户实际分润(元)", "商户账户应加值(元)", "商户账户实际加值(元)", "与客户清分状态", "与客户清分时间", "DDP应收商户支付方式服务费(元)", "DDP实际转给商户业务费用(元)");
        // 导出文件名(不支持中文)
        String fileName = "MER_CLEARING";
        // sheet标题
        String title = "商户清分";
        ExcelModel excelModel = new ExcelModel(fileName, title, titleList);

        //【处理数据，结果为List<List<String>>】
        if (CollectionUtils.isNotEmpty(merList)) {
            List<List<String>> dataList = new ArrayList<List<String>>(merList.size());
            for (ClearingBasic basic : merList) {
                List<String> rowList = new ArrayList<String>();

                rowList.add(basic.getOrderNo());//订单交易号
                rowList.add(basic.getOrderDate());//订单时间
                if (StringUtils.isNotBlank(basic.getBusinessType())) {
                    rowList.add(RateCodeEnum.getRateTypeByCode(basic.getBusinessType()).getName());//业务类型
                } else {
                    rowList.add("");
                }

                if (StringUtils.isNotBlank(basic.getPayType())) {
                    rowList.add(PayTypeEnum.getPayTypeNameByCode(basic.getPayType()));//支付类型
                } else {
                    rowList.add("");
                }

                rowList.add(basic.getPayWayName());//支付方式

                if (StringUtils.isNotBlank(basic.getOrderFrom())) {
                    rowList.add(SourceEnum.getSourceNameByCode(basic.getOrderFrom()));//订单来源
                } else {
                    rowList.add("");
                }

                if (StringUtils.isNotBlank(basic.getOrderCircle())) {
                    rowList.add(LoadFlagEnum.getLoadFlagNameByCode(basic.getOrderCircle()));//是否圈存
                } else {
                    rowList.add("");
                }

                rowList.add(basic.getCustomerNo());//商户编号
                rowList.add(basic.getCustomerName());//商户名称

                if (StringUtils.isNotBlank(basic.getCustomerType())) {
                    rowList.add(MerTypeEnum.getNameByCode(basic.getCustomerType()));//商户类型
                } else {
                    rowList.add("");
                }

                if (StringUtils.isNotBlank(basic.getCustomerRateType())) {//商户费率类型
                    rowList.add(RateTypeEnum.getRateTypeByCode(basic.getCustomerRateType()).getName());//商户费率类型
                } else {
                    rowList.add("");
                }
                if (StringUtils.isNotBlank(basic.getCustomerRate())) {//商户业务费率
                    if (RateTypeEnum.SINGLE_AMOUNT.getCode().equals(basic.getCustomerRateType())) {//费率类型为 单笔返点金额(元) (1)
                        rowList.add(String.format("%.2f", (Double.valueOf(basic.getCustomerRate()) / 100)));
                    } else if (RateTypeEnum.PERMILLAGE.getCode().equals(basic.getCustomerRateType())) {//费率类型为 千分比(‰) (2)
                        rowList.add(basic.getCustomerRate());
                    } else {
                        rowList.add("");
                    }
                } else {
                    rowList.add("");
                }
                if (StringUtils.isNotBlank(basic.getCustoerRealPayAmount())) {
                    rowList.add(String.format("%.2f", Double.valueOf(basic.getCustoerRealPayAmount()) / 100));//商户实际支付金额
                } else {
                    rowList.add("");
                }
                if (StringUtils.isNotBlank(basic.getCustomerShouldProfit())) {
                    rowList.add(String.format("%.2f", Double.valueOf(basic.getCustomerShouldProfit()) / 100));//商户应得分润
                } else {
                    rowList.add("");
                }
                if (StringUtils.isNotBlank(basic.getCustomerRealProfit())) {
                    rowList.add(String.format("%.2f", Double.valueOf(basic.getCustomerRealProfit()) / 100));//商户实得分润
                } else {
                    rowList.add("");
                }
                if (StringUtils.isNotBlank(basic.getCustomerAccountShouldAmount())) {
                    rowList.add(String.format("%.2f", Double.valueOf(basic.getCustomerAccountShouldAmount()) / 100));//商户账户应加值
                } else {
                    rowList.add("");
                }
                if (StringUtils.isNotBlank(basic.getCustomerAccountRealAmount())) {
                    rowList.add(String.format("%.2f", Double.valueOf(basic.getCustomerAccountRealAmount()) / 100));//商户账户实际加值
                } else {
                    rowList.add("");
                }

                if (StringUtils.isNotBlank(basic.getCustomerClearingFlag())) {
                    rowList.add(ClearingFlagEnum.getClearingFlagByCode(basic.getCustomerClearingFlag()).getName());//与客户清分状态
                } else {
                    rowList.add("");
                }
                rowList.add(basic.getCustomerClearingTime());//与客户清分时间

                if (StringUtils.isNotBlank(basic.getDdpGetMerchantPayFee())) {
                    rowList.add(String.format("%.2f", Double.valueOf(basic.getDdpGetMerchantPayFee()) / 100));//DDP应收商户支付方式服务费
                } else {
                    rowList.add("");
                }
                if (StringUtils.isNotBlank(basic.getDdpToCustomerRealFee())) {
                    rowList.add(String.format("%.2f", Double.valueOf(basic.getDdpToCustomerRealFee()) / 100));//DDP实际转给商户业务费用
                } else {
                    rowList.add("");
                }

                // 【添加数据行】
                dataList.add(rowList);
            }
            // 【将数据集加入model】
            excelModel.setDataList(dataList);
        }

        return ExcelUtil.excelExport(excelModel, response);
    }

	@Override
	public int findBankClearingCount(ClearingInfoQuery queryDto) {
		// TODO Auto-generated method stub
		return clearingMapper.findBankClearingCount(queryDto);
	}

	@Override
	public List<ClearingBasicDataDTO> excelExportBank(ClearingInfoQuery bankQuery) {
		// TODO Auto-generated method stub
		return clearingMapper.excelExportBankClearing(bankQuery);
	}

	@Override
	public int findYktClearingCount(ClearingInfoQuery queryDto) {
		// TODO Auto-generated method stub
		return clearingMapper.findYktClearingCount(queryDto);
	}

	@Override
	public List<ClearingBasicDataDTO> excelExportYkt(ClearingInfoQuery queryDto) {
		// TODO Auto-generated method stub
		return clearingMapper.excelExportYktClearing(queryDto);
	}

	@Override
	public int findMerClearingCount(ClearingInfoQuery bankQuery) {
		// TODO Auto-generated method stub
		return clearingMapper.findMerClearingCount(bankQuery);
	}

	@Override
	public List<ClearingBasicDataDTO> excelExportMer(ClearingInfoQuery bankQuery) {
		// TODO Auto-generated method stub
		return clearingMapper.excelExportMerClearing(bankQuery);
	}

}
