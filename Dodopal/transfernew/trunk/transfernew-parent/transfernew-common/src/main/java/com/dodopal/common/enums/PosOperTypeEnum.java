package com.dodopal.common.enums;

/**
 * POS操作类型
 * 
 */
public enum PosOperTypeEnum {

	 OPER_BUNDLING("0","绑定"),
	 
	 OPER_UNBUNDLING("1","解绑"),
	 
	 OPER_ENABLE("2","启用"),
	 
	 OPER_DISABLE("3","禁用"),
	 
	 OPER_DELETE("4","删除");
	private String code;
	
	private String name;
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	PosOperTypeEnum(String code, String name){
		this.code = code;
		this.name = name;
	}

	/** 根据状态码获取其对应的枚举状态 **/
	public static PosOperTypeEnum parseByCode(String source) {
		for (PosOperTypeEnum codes : values()) {
			if (codes.code.equals(source)) {
				return codes;
			}
		}
		return null;
	}
	
}
