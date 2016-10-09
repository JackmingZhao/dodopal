package com.dodopal.common.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dodopal.common.util.DDPStringUtil;

/**
 * 用于DDP 后台验证的所有validation 主要指各种正则表达式验证
 */
public class DDPValidationHandler {
	public static boolean validate(String value, boolean required, DdpValidationBoxEnum... validators) {
		if (required && DDPStringUtil.isNotPopulated(value)) {
			return false;
		}
		if (validators != null) {
			for (DdpValidationBoxEnum validator : validators) {
				Pattern pattern = Pattern.compile(validator.getReg());
				Matcher matcher = pattern.matcher(value);
				if (!matcher.matches()) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean validate(String value, boolean required, int minLength,int maxLength,DdpValidationBoxEnum... validators) {
		if (required && DDPStringUtil.isNotPopulated(value)) {
			return false;
		}
		if (validators != null) {
			for (DdpValidationBoxEnum validator : validators) {
				Pattern pattern = Pattern.compile(validator.getReg());
				Matcher matcher = pattern.matcher(value);
				if (!matcher.matches()) {
					return false;
				}
			}
		}
		
		if(value.length() >= minLength && value.length() <= maxLength){
			return true;
		}else{
			return false;
		}
		
	}
	
	public static void main(String[] args) {
		DDPValidationHandler.validate("部", true, 2, 20, DdpValidationBoxEnum.EN_CN);
    }
}
