package com.dodopal.common.util;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;


/**
 * 金钱算法工具类
 * 
 * @author dodopal
 *
 */
public class MoneyAlgorithmUtils {
    
    //private static String regesp= "^(-)*(d+).(dd).*$";// 暂时没找到准确的正则表达式
    
    private MoneyAlgorithmUtils(){}
    
    /**
     * 两数相乘取整（去除小数部分）
     * 
     * @param money 充值金额
     * @param rate 千分比数值（1-商户费率）
     */
    public static long multiplyToIntValue(String money, String rate) {
        if (StringUtils.isBlank(money) || StringUtils.isBlank(rate)) {
            return 0;
        }
        try{
            Double.parseDouble(money);
            Double.parseDouble(rate);
        }catch(Exception ex){
            return 0;
        }
//        Pattern pattern = Pattern.compile(regesp);
//        Matcher moneyMatcher = pattern.matcher(money);
//        Matcher rateMatcher = pattern.matcher(rate);
//        if (!moneyMatcher.matches() || !rateMatcher.matches()) {
//            return 0;
//        }
        BigDecimal moneyBigDecimal = new BigDecimal(money);
        BigDecimal rateBigDecimal = new BigDecimal(rate);
        long amount = moneyBigDecimal.multiply(rateBigDecimal).intValue();
        return amount;
    }
    /**
     * 两数相乘取整（去除小数部分+1）
     * 
     * @param money 充值金额
     * @param rate 千分比数值（1-商户费率）
     */
    public static long multiplyToIntValueAddOne(String money, String rate) {
        if (StringUtils.isBlank(money) || StringUtils.isBlank(rate)) {
            return 0;
        }
        try{
            Double.parseDouble(money);
            Double.parseDouble(rate);
        }catch(Exception ex){
            return 0;
        }
//        Pattern pattern = Pattern.compile(regesp);
//        Matcher moneyMatcher = pattern.matcher(money);
//        Matcher rateMatcher = pattern.matcher(rate);
//        if (!moneyMatcher.matches() || !rateMatcher.matches()) {
//            return 0;
//        }
        BigDecimal moneyBigDecimal = new BigDecimal(money);
        BigDecimal rateBigDecimal = new BigDecimal(rate);
        BigDecimal amount = moneyBigDecimal.multiply(rateBigDecimal);
        long amountResult = moneyBigDecimal.multiply(rateBigDecimal).intValue();
        if (amountResult >= 0 && amount.compareTo(new BigDecimal(String.valueOf(amountResult))) > 0) {
            // 若乘积为正值则（去除小数部分+1）
            amountResult +=1;
        } else if (amountResult < 0 && amount.compareTo(new BigDecimal(String.valueOf(amountResult))) < 0){
            // 若乘积为负值则（去除小数部分-1）
            amountResult -=1;
        }
        return amountResult;
    }
    
//    // 测试方法
//    public static void main(String[] args) {
//        long amountResult1 = multiplyToIntValue("0.5","0.3");
//        System.out.println(amountResult1);
//        long amountResult2 = multiplyToIntValueAddOne("0.5","0.3");
//        System.out.println(amountResult2);
//    }


}
