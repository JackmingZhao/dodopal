package com.dodopal.oss.business.model;

import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.ClearingFlagEnum;
import com.dodopal.common.enums.LoadFlagEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.PayGatewayEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.RateTypeEnum;
import com.dodopal.common.enums.ServiceRateTypeEnum;
import com.dodopal.common.enums.SettlementFlagEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.model.BaseEntity;
import com.dodopal.common.util.DateUtils;

/*
 * 清分表DTO
 */
public class ClearingBasicDataDTO extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/* 订单交易号 */
	private String orderNo;
	/* 订单时间 */
	private String orderDate;
	/* 订单日期 */
	private String orderDay;
	/* 商户编号 */
	private String customerNo;
	/* 商户名称 */
	private String customerName;
	/* 商户类型 */
	private String customerType;
	/* 业务类型 */
	private String businessType;
	/* 商户业务费率类型 */
	private String customerRateType;
	/* 商户业务费率 */
	private String customerRate;
	/* 订单金额 */
	private String orderAmount;
	/* 商户实际支付金额 */
	private String custoerRealPayAmount;
	/* 商户应得分润 */
	private String customerShouldProfit;
	/* 商户实际分润 */
	private String customerRealProfit;
	/* 商户账户应加值 */
	private String customerAccountShouldAmount;
	/* 商户账户实际加值 */
	private String customerAccountRealAmount;
	/* 与客户清分状态 */
	private String customerClearingFlag;
	/* 与客户清分时间 */
	private String customerClearingTime;
	/* 与客户结算状态 */
	private String customerSettlementFlag;
	/* 与客户结算时间 */
	private String customerSettlementTime;
	/* DDP应收商户支付方式服务费 */
	private String ddpGetMerchantPayFee;
	/* DDP实际转给商户业务费用 */
	private String ddpToCustomerRealFee;
	/* 下级商户编号 */
	private String subMerchantCode;
	/* 下级商户名称 */
	private String subMerchantName;
	/* 下级商户应得分润 */
	private String subMerchantShouldProfit;
	/* 支付网关 */
	private String payGateway;
	/* 支付类型 */
	private String payType;
	/* 支付方式 */
	private String payWay;
	/* 服务费率类型 */
	private String serviceRateType;
	/* 服务费率 */
	private String serviceRate;
	/* DDP与银行的手续费率 */
	private String ddpBankRate;
	/* DDP支付给银行的手续费 */
	private String ddpToBankFee;
	/* DDP从银行应收业务费用 */
	private String ddpFromBankShouldFee;
	/* DDP从银行实收业务费用 */
	private String ddpFromBankRealFee;
	/* 与银行清分状态 */
	private String bankClearingFlag;
	/* 与银行清分时间 */
	private String bankClearingTime;
	/* 与银行结算状态 */
	private String bankSettlementFlag;
	/* 与银行结算时间 */
	private String bankSettlementTime;
	/* 供应商编码 */
	private String supplierCode;
	/* 供应商名称 */
	private String supplierName;
	/* 城市编码 */
	private String cityCode;
	/* 城市名称 */
	private String cityName;
	/* DDP应支付供应商本金 */
	private String ddpToSupplierShouldAmount;
	/* DDP实际支付供应商本金 */
	private String ddpToSupplierRealAmount;
	/* DDP与供应商之间的费率 */
	private String ddpSupplierRate;
	/* 供应商应支付DDP返点 */
	private String supplierToDdpShouldRebate;
	/* 供应商实际支付DDP返点 */
	private String supplierToDdpRealRebate;
	/* 与供应商清分状态 */
	private String supplierClearingFlag;
	/* 与供应商清分时间 */
	private String supplierClearingTime;
	/* 与供应商结算状态 */
	private String supplierSettlementFlag;
	/* 与供应商结算时间 */
	private String supplierSettlementTime;
	/* 订单来源 */
	private String orderFrom;
	/* 是否圈存订单 */
	private String orderCircle;
	/* 上级商户分润计算状态 */
	private String topMerchantProfitFlag;
	/* 支付方式名称 */
	private String payWayName;
	/* 分润标识 */
	private String dataFlag;

	public String getOrderNo() {
		return orderNo;
	}

	public String getOrderDate() {
		if (StringUtils.isBlank(orderDate)) {
			return "";
		} else {
			return DateUtils.dateToString(DateUtils.stringtoDate(orderDate, DateUtils.DATE_FULL_STR),
					DateUtils.DATE_FULL_STR);
		}
	}

	public String getOrderDay() {
		if (StringUtils.isBlank(orderDay)) {
			return "";
		} else {
			return DateUtils.dateToString(DateUtils.stringtoDate(orderDay, DateUtils.DATE_FORMAT_YYMMDD_STR),
					DateUtils.DATE_SMALL_STR);
		}
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getCustomerType() {
		if (StringUtils.isBlank(this.customerType)) {
			return "";
		} else if (MerTypeEnum.getMerTypeByCode(this.customerType) == null) {
			return "";
		} else {
			return MerTypeEnum.getMerTypeByCode(this.customerType).getName();
		}
	}

	public String getBusinessType() {
		if (StringUtils.isBlank(businessType)) {
			return "";
		} else if (RateCodeEnum.getRateTypeByCode(this.businessType) == null) {
			return "";
		} else {
			return RateCodeEnum.getRateTypeByCode(this.businessType).getName();
		}
	}

	public String getCustomerRateType() {
		if (StringUtils.isBlank(this.customerRateType)) {
			return "";
		} else if (RateTypeEnum.getRateTypeByCode(this.customerRateType) == null) {
			return "";
		} else {
			return RateTypeEnum.getRateTypeByCode(this.customerRateType).getName();
		}
	}

	public String getCustomerRate() {
		if ("千分比(‰)".equals(getCustomerRateType())) {
			if (StringUtils.isBlank(this.customerRate)) {
				return "0 ‰";
			} else if (this.customerRate.startsWith(".")) {
				return "0" + customerRate + " ‰";
			} else {
				return customerRate + " ‰";
			}
		} else if ("单笔返点金额()".equals(getCustomerRateType())) {
			if (StringUtils.isBlank(this.customerRate) || "0".equals(this.customerRate)) {
				return "0.00 ";
			} else {
				return df((Double.valueOf(this.customerRate)) / 100) + " ";
			}
		} else {
			return customerRate;
		}
	}

	public String getOrderAmount() {
		if (StringUtils.isBlank(orderAmount)) {
			return "0.00";
		} else {
			return df(Double.valueOf(orderAmount) / 100) ;
		}
	}

	public String getCustoerRealPayAmount() {
		if (StringUtils.isBlank(this.custoerRealPayAmount) || "0".equals(this.custoerRealPayAmount)) {
			return "0.00 ";
		} else {
			return df(Double.valueOf(custoerRealPayAmount) / 100) ;
		}
	}

	public String getCustomerShouldProfit() {
		if (StringUtils.isBlank(this.customerShouldProfit) || "0".equals(this.customerShouldProfit)) {
			return "0.00 ";
		} else {
			return df((Double.valueOf(this.customerShouldProfit)) / 100) ;
		}
	}

	// 商户实际分润
	public String getCustomerRealProfit() {
		if (StringUtils.isBlank(this.customerRealProfit) || "0".equals(this.customerRealProfit)) {
			return "0.00 ";
		} else {
			return df((Double.valueOf(this.customerRealProfit)) / 100);
		}
	}

	public String getCustomerAccountShouldAmount() {
		if (StringUtils.isBlank(this.customerAccountShouldAmount) || "0".equals(this.customerAccountShouldAmount)) {
			return "0.00 ";
		} else {
			return df((Double.valueOf(this.customerAccountShouldAmount)) / 100);
		}
	}

	public String getCustomerAccountRealAmount() {
		if (StringUtils.isBlank(this.customerAccountRealAmount) || "0".equals(this.customerAccountRealAmount)) {
			return "0.00 ";
		} else {
			return df((Double.valueOf(this.customerAccountRealAmount)) / 100);
		}
	}

	public String getCustomerClearingFlag() {
		if (StringUtils.isBlank(customerClearingFlag)) {
			return "状态未知";
		} else if (ClearingFlagEnum.getClearingFlagByCode(customerClearingFlag) == null) {
			return "";
		} else {
			return ClearingFlagEnum.getClearingFlagByCode(this.customerClearingFlag).getName();
		}
	}

	public String getCustomerClearingTime() {
		if (StringUtils.isBlank(customerClearingTime)) {
			return "";
		} else {
			return DateUtils.dateToString(DateUtils.stringtoDate(customerClearingTime, DateUtils.DATE_SMALL_STR),
					DateUtils.DATE_SMALL_STR);
		}
	}

	public String getCustomerSettlementFlag() {
		if (StringUtils.isBlank(customerSettlementFlag)) {
			return "未知";
		} else if (SettlementFlagEnum.getSettlementFlagByCode(customerClearingFlag) == null) {
			return "";
		} else {
			return SettlementFlagEnum.getSettlementFlagByCode(this.customerClearingFlag).getName();
		}
	}

	public String getCustomerSettlementTime() {
		if (StringUtils.isBlank(customerSettlementTime)) {
			return "";
		} else {
			return DateUtils.dateToString(DateUtils.stringtoDate(customerSettlementTime, DateUtils.DATE_FULL_STR),
					DateUtils.DATE_FULL_STR);
		}
	}

	public String getDdpGetMerchantPayFee() {
		if (StringUtils.isBlank(this.ddpGetMerchantPayFee) || "0".equals(this.ddpGetMerchantPayFee)) {
			return "0.00 ";
		} else {
			return df(Double.valueOf(this.ddpGetMerchantPayFee) / 100);
		}
	}

	public String getDdpToCustomerRealFee() {
		if (StringUtils.isBlank(this.ddpToCustomerRealFee) || "0".equals(this.ddpToCustomerRealFee)) {
			return "0.00 ";
		} else {
			return df(Double.valueOf(this.ddpToCustomerRealFee) / 100);
		}
	}

	public String getSubMerchantCode() {
		return subMerchantCode;
	}

	public String getSubMerchantName() {
		return subMerchantName;
	}

	public String getSubMerchantShouldProfit() {
		if (StringUtils.isBlank(this.subMerchantShouldProfit) || "0".equals(this.subMerchantShouldProfit)) {
			return "0.00 ";
		} else {
			return df(Double.valueOf(this.subMerchantShouldProfit) / 100);
		}
	}

	public String getPayGateway() {
		if (StringUtils.isBlank(this.payGateway)) {
			return "";
		} else if (PayGatewayEnum.getPayGatewayEnumByCode(this.payGateway) == null) {
			return "";
		} else {
			return PayGatewayEnum.getPayGatewayEnumByCode(this.payGateway).getName();
		}
	}

	public String getPayType() {
		if (StringUtils.isBlank(this.payType)) {
			return "";
		} else if (PayTypeEnum.getPayTypeEnumByCode(this.payType) == null) {
			return "";
		} else {
			return PayTypeEnum.getPayTypeEnumByCode(this.payType).getName();
		}
	}

	public String getPayWay() {
		return payWay;
	}

	public String getServiceRateType() {
		if (StringUtils.isBlank(this.serviceRateType)) {
			return "";
		} else if (ServiceRateTypeEnum.getServiceRateTypeByCode(this.serviceRateType) == null) {
			return "";
		} else {
			return ServiceRateTypeEnum.getServiceRateTypeByCode(this.serviceRateType).getName();
		}
	}

	public String getServiceRate() {
		if ("费率（千分比）".equals(getServiceRateType())) {
			if (StringUtils.isBlank(this.serviceRate)) {
				return "0 ‰";
			} else if (this.serviceRate.startsWith(".")) {
				return "0" + serviceRate + " ‰";
			} else {
				return serviceRate + " ‰";
			}
		} else if ("单笔（）".equals(getServiceRateType())) {
			if (StringUtils.isBlank(this.serviceRate) || "0".equals(this.serviceRate)) {
				return "0.00 ";
			} else {
				return df((Double.valueOf(this.serviceRate)) / 100);
			}
		} else {
			return serviceRate;
		}
	}

	public String getDdpBankRate() {
		return ddpBankRate;
	}

	public String getDdpToBankFee() {
		if (StringUtils.isBlank(this.ddpToBankFee) || "0".equals(this.ddpToBankFee)) {
			return "0.00 ";
		} else {
			return df((Double.valueOf(ddpToBankFee) / 100));
		}
	}

	public String getDdpFromBankShouldFee() {
		if (StringUtils.isBlank(this.ddpFromBankShouldFee) || "0".equals(this.ddpFromBankShouldFee)) {
			return "0.00 ";
		} else {
			return df((Double.valueOf(ddpFromBankShouldFee) / 100));
		}
	}

	public String getDdpFromBankRealFee() {
		if (StringUtils.isBlank(this.ddpFromBankRealFee) || "0".equals(this.ddpFromBankRealFee)) {
			return "0.00 ";
		} else {
			return df((Double.valueOf(ddpFromBankRealFee) / 100));
		}
	}

	public String getBankClearingFlag() {
		if (StringUtils.isBlank(bankClearingFlag)) {
			return "状态未知";
		} else if (ClearingFlagEnum.getClearingFlagByCode(bankClearingFlag) == null) {
			return "";
		} else {
			return ClearingFlagEnum.getClearingFlagByCode(this.bankClearingFlag).getName();
		}
	}

	public String getBankClearingTime() {
		if (StringUtils.isBlank(bankClearingTime)) {
			return "";
		} else {
			return DateUtils.dateToString(DateUtils.stringtoDate(bankClearingTime, DateUtils.DATE_SMALL_STR),
					DateUtils.DATE_SMALL_STR);
		}
	}

	public String getBankSettlementFlag() {
		if (StringUtils.isBlank(bankSettlementFlag)) {
			return "未知";
		} else if (SettlementFlagEnum.getSettlementFlagByCode(bankSettlementFlag) == null) {
			return "";
		} else {
			return SettlementFlagEnum.getSettlementFlagByCode(this.bankSettlementFlag).getName();
		}
	}

	public String getBankSettlementTime() {
		if (StringUtils.isBlank(bankSettlementTime)) {
			return "";
		} else {
			return DateUtils.dateToString(DateUtils.stringtoDate(bankSettlementTime, DateUtils.DATE_FULL_STR),
					DateUtils.DATE_FULL_STR);
		}
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public String getDdpToSupplierShouldAmount() {
		if (StringUtils.isBlank(this.ddpToSupplierShouldAmount) || "0".equals(this.ddpToSupplierShouldAmount)) {
			return "0.00 ";
		} else {
			return df((Double.valueOf(ddpToSupplierShouldAmount) / 100));
		}
	}

	public String getDdpToSupplierRealAmount() {
		if (StringUtils.isBlank(this.ddpToSupplierRealAmount) || "0".equals(this.ddpToSupplierRealAmount)) {
			return "0.00 ";
		} else {
			return df((Double.valueOf(ddpToSupplierRealAmount) / 100));
		}
	}

	public String getDdpSupplierRate() {
		 if (StringUtils.isBlank(this.ddpSupplierRate) || "0".equals(this.ddpSupplierRate)) {
	            return "0 ‰";
	        } else {
	            if(this.ddpSupplierRate.startsWith(".")){
	                return "0"+ddpSupplierRate+" ‰";
	            }else{
	                return ddpSupplierRate+" ‰";
	            }
	        }
	}

	public String getSupplierToDdpShouldRebate() {
		 if(StringUtils.isBlank(this.supplierToDdpShouldRebate) ||"0".equals(this.supplierToDdpShouldRebate)){
	            return "0.00 ";
	        }else{
	            return df((Double.valueOf(this.supplierToDdpShouldRebate)) / 100);
	        }
	}

	public String getSupplierToDdpRealRebate() {
		 if(StringUtils.isBlank(this.supplierToDdpRealRebate) ||"0".equals(this.supplierToDdpRealRebate)){
	            return "0.00 ";
	        }else{
	            return df((Double.valueOf(this.supplierToDdpRealRebate)) / 100);
	        }
	}

	public String getSupplierClearingFlag() {
		if (StringUtils.isBlank(supplierClearingFlag)) {
			return "状态未知";
		} else if (ClearingFlagEnum.getClearingFlagByCode(supplierClearingFlag) == null) {
			return "";
		} else {
			return ClearingFlagEnum.getClearingFlagByCode(this.supplierClearingFlag).getName();
		}
	}

	public String getSupplierClearingTime() {
		if (StringUtils.isBlank(supplierClearingTime)) {
			return "";
		} else {
			return DateUtils.dateToString(DateUtils.stringtoDate(supplierClearingTime, DateUtils.DATE_SMALL_STR),
					DateUtils.DATE_SMALL_STR);
		}
	}

	public String getSupplierSettlementFlag() {
		if (StringUtils.isBlank(supplierSettlementFlag)) {
			return "未知";
		} else if (SettlementFlagEnum.getSettlementFlagByCode(supplierSettlementFlag) == null) {
			return "";
		} else {
			return SettlementFlagEnum.getSettlementFlagByCode(this.supplierSettlementFlag).getName();
		}
	}

	public String getSupplierSettlementTime() {
		return supplierSettlementTime;
	}

	public String getOrderFrom() {
		if (StringUtils.isBlank(this.orderFrom)) {
            return "";
        } else if (SourceEnum.getSourceByCode(this.orderFrom) == null) {
            return "";
        } else {
            return SourceEnum.getSourceByCode(this.orderFrom).getName();
        }
	}

	public String getOrderCircle() {
		if (StringUtils.isBlank(this.orderCircle)) {
            return "";
        } else if (LoadFlagEnum.getLoadFlagByCode(this.orderCircle) == null) {
            return "";
        } else {
            return LoadFlagEnum.getLoadFlagByCode(this.orderCircle).getName();
        }
	}

	public String getTopMerchantProfitFlag() {
		//0 不需要  1需要
		String flag="";
		if(StringUtils.isBlank(topMerchantProfitFlag)){
			flag= "";
		}else if("0".equals(topMerchantProfitFlag)){
			flag= "不需要";
		}else if("1".equals(topMerchantProfitFlag)){
			flag= "需要";
		}
		return flag;
	}

	public String getPayWayName() {
		return payWayName;
	}

	public String getDataFlag() {
		//0  来自清分;1  来自分润
		String flag = "";
		if(StringUtils.isBlank(dataFlag)){
			flag ="";
		}else if ("0".equals(dataFlag)) {
			return "来自清分";
		}else if ("1".equals(dataFlag)) {
			return "来自分润";
		}
		return flag;
	}

	private static String df(double dou) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(dou);
	}
	
	public static void main(String[] args) {
		System.out.println(DateUtils.dateToString(DateUtils.stringtoDate("20151225", DateUtils.DATE_FORMAT_YYMMDD_STR), DateUtils.DATE_SMALL_STR));
	}
}
