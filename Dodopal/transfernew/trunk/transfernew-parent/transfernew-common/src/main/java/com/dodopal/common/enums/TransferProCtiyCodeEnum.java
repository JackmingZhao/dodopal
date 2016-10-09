package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public enum TransferProCtiyCodeEnum {
	BEIJING("1000", "北京", "1110", "北京市", "100000", "北京一卡通", "100000-1110"),
	QINGDAO("2500", "山东省", "1532", "青岛市", "266000", "青岛一卡通", "266000-1532"),
	XIAMEN("3500", "福建省", "1592", "厦门市", "361000", "厦门一卡通", "361000-1592"),
	SHENZHEN("5100", "广东省", "1755", "深圳市", "518000", "深圳一卡通", "518000-1755"),
	CHONGQING("4000", "重庆", "1123", "重庆市", "400000", "重庆一卡通", "400000-1123");

	private static final Map<String, TransferProCtiyCodeEnum> map = new HashMap<String, TransferProCtiyCodeEnum>(values().length);

	static {
		for (TransferProCtiyCodeEnum transferProCtiyCodeEnum : values()) {
			map.put(transferProCtiyCodeEnum.getCityCode(), transferProCtiyCodeEnum);
		}
	}

	private String proCode;
	private String proName;
	private String cityCode;
	private String cityName;
	private String yktCode;
	private String yktName;
	private String proxyCityNo;

	private TransferProCtiyCodeEnum(String proCode, String proName, String cityCode, String cityName, String yktCode, String yktName, String proxyCityNo) {
		this.proCode = proCode;
		this.proName = proName;
		this.cityCode = cityCode;
		this.cityName = cityName;
		this.yktCode = yktCode;
		this.yktName = yktName;
		this.proxyCityNo = proxyCityNo;
	}

	public String getProCode() {
		return proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getYktCode() {
		return yktCode;
	}

	public void setYktCode(String yktCode) {
		this.yktCode = yktCode;
	}

	public String getYktName() {
		return yktName;
	}

	public void setYktName(String yktName) {
		this.yktName = yktName;
	}

	public String getProxyCityNo() {
		return proxyCityNo;
	}

	public void setProxyCityNo(String proxyCityNo) {
		this.proxyCityNo = proxyCityNo;
	}

	public static TransferProCtiyCodeEnum getByCityCode(String cityCode) {
		if (StringUtils.isBlank(cityCode)) {
			return null;
		}
		return map.get(cityCode);
	}
}
