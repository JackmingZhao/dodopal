package com.dodopal.product.business.model.query;

import com.dodopal.common.model.QueryBase;
/**
 * 一卡通充值统计
 * @author hxc
 *
 */
public class RechargeStatisticsYktQuery extends QueryBase{

	private static final long serialVersionUID = -8675093522097131246L;


		//pos号
		private String proCode;
		//启用标识
		private String bind;
		//商户名称
		private String merName;
		//开始日期
		private String stratDate;
		//结束日期
		private String endDate;
		//城市
		private String cityName;
		
		public String getProCode() {
			return proCode;
		}
		public void setProCode(String proCode) {
			this.proCode = proCode;
		}
		public String getBind() {
			return bind;
		}
		public void setBind(String bind) {
			this.bind = bind;
		}
		public String getMerName() {
			return merName;
		}
		public void setMerName(String merName) {
			this.merName = merName;
		}
		public String getStratDate() {
			return stratDate;
		}
		public void setStratDate(String stratDate) {
			this.stratDate = stratDate;
		}
		public String getEndDate() {
			return endDate;
		}
		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}
		public String getCityName() {
			return cityName;
		}
		public void setCityName(String cityName) {
			this.cityName = cityName;
		}
		
		
}
