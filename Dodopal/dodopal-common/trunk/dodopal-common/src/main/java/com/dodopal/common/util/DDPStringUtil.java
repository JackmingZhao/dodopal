package com.dodopal.common.util;

import java.text.NumberFormat;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class DDPStringUtil {
	public static final String SYMBOL_COMMA = ",";
	
	public static final String trim(String s){
		if(s==null)
			return s;
		
		 s = s.trim();    
		 while(s.startsWith(" "))       
			 s = s.substring(1,s.length());
		
		return s;
	}
	
	/**
	 * 用逗号连接对象数组为字符串
	 */
	public static final String concatenate(Object[] array){
		return concatenate(array, SYMBOL_COMMA);
	}
	
	/**
	 * 用指定符号连接对象数组为字符串,如果没有指定分隔符号,则默认用逗号
	 */
	public static final String concatenate(Object[] array, String separator){
		if(StringUtils.isBlank(separator))
			separator = SYMBOL_COMMA;
		
		String s = "";
		for(Object o : array)
			s = s + separator + o.toString();
		
//		if(StringUtils.isNotBlank(s))
//			return s.substring(1);
//		else
			return s;
	}
	
	public static final String concatenate(Object[] array, String separator, boolean separateFromStart, int removeLength){
		
		if(StringUtils.isBlank(separator))
			separator = SYMBOL_COMMA;
		
		if(removeLength==-1)
			removeLength = separator.length();
		
		String s = "";
		if(separateFromStart)
			for(Object o : array)
				s = s + separator + o.toString();
		else
			for(Object o : array)
				s = s + o.toString() + separator;
		
		if(StringUtils.isNotBlank(s)){
			if(separateFromStart)
				return s.substring(removeLength);
			return s.substring(0,s.length()-removeLength);
		}else
			return s;
	}
	
	
//	? or aimManager.id=? or aifManager.id=? or aipManager.id=? or crsManager.id=? or ctsSalesManager.id=? or ctsOpManager.id=? or salesSupport.id=? or serviceSupport.id=?
	
	/**
	 * 用逗号连接对象集合为字符串
	 */
	public static final String concatenate(List<String> array){
		return concatenate(array, SYMBOL_COMMA);
	}
	
	/**
	 * 用指定符号连接对象集合为字符串,如果没有指定分隔符号,则默认用逗号
	 */
	public static final String concatenate(List<String> array, String separator){
		if(StringUtils.isBlank(separator))
			separator = SYMBOL_COMMA;
		
		String s = "";
		
		int maxSize = array.size() < 10 ? array.size(): 10;
		for(int i =0 ; i< maxSize; i++) {
			if(StringUtils.isBlank(s)){
				s = array.get(i);
			}else{
				s = s + separator + array.get(i);
			}
		}
			
		
		if(StringUtils.isNotBlank(s))
			return s.substring(1);
		else
			return s;
	}
	
	/**
	 * 构造字符串的sql语句
	 */
	public static final String concatenateSql(Collection<String> ls){
		if(ls==null || CollectionUtils.isEmpty(ls))
			return "";
		
		String s = "";
		for(String o : ls)
			s = s + ",'" + o + "'";
		
		return s.substring(1);
	}
	
	/**
	 * 构造字符串的sql语句
	 */
	public static final String concatenateSql(String s, String delim){
		if(s.indexOf(delim)<0)
			return "'" + s + "'";
		
		String ss = "";
		StringTokenizer st = new StringTokenizer(s,delim);
		while(st.hasMoreTokens())
			ss = ss + ",'" + st.nextToken() + "'";
		
		return ss.substring(1);
	}
	
	/**
	 * 构造字符串的sql语句
	 */
	public static final String concatenateSql(String[] ls){
		if(ls==null)
			return "";
		
		String s = "";
		for(String o : ls)
			s = s + ",'" + o + "'";
		
		return s.substring(1);
	}
	
	/**
	 * 指定数组中每个元素都不为空
	 */
    public static boolean isAllAvailable(String[] ss){
    	if(ss==null || ss.length==0)
    		return false;
    	
    	for(String s : ss){
			if(StringUtils.isBlank(s))
				return false;
		}
    	
    	return true;
    }
    
    /**
	 * 空数组，即指定的数组中每个元素都是blank
	 */
    public static boolean isAllBlank(String[] ss){
    	if(ss==null || ss.length==0)
    		return true;
    	
    	for(String s : ss){
			if(StringUtils.isNotBlank(s))
				return false;
		}
    	
    	return true;
    }
	
	/**
	 * 验证邮件地址是否合法
	 */
	public static final boolean validateEmailAddress(String address){
		if(StringUtils.isBlank(address))
			return false;
		
		if(!address.contains("@") || address.length()<6)
			return false;
		
		return true;
	}
	
	public static final String[] toArray(List<String> list){
		if(CollectionUtils.isEmpty(list))
			return null;
		
		String[] array = new String[list.size()];
		for(int i=0; i<list.size(); i++)
			array[i] = list.get(i);
		
		return array;
	}
	
	public static final boolean in(String s, String[]ss){
		for(String s1 : ss)
			if(s.equals(s1))
				return true;
		
		return false;
	}
	
	public static final boolean isTrue(String s){
		if(StringUtils.isNotBlank(s) && s.equalsIgnoreCase("true"))
			return true;
		
		return false;
	}
	
	/**
	 * 表示某个字符串如果提供，最大长度不能超过指定值
	 */
	public static final boolean lessThan(String s,int maxLen){
		if(StringUtils.isBlank(s))
			return true;
		
		return s.length()<=maxLen?true:false;
	}
	
	/**
	 * 表示某个字符串必须提供，而且最大长度不能超过指定值
	 */
	public static final boolean existingWithLength(String s,int maxLen){
		if(StringUtils.isNotBlank(s) && s.length()<=maxLen)
			return true;
		
		return false;
	}
	
	/**
     * 表示某个字符串必须提供，而且长度不能超过指定值范围
     */
    public static final boolean existingWithLengthRange(String s, int minLen, int maxLen){
        if(StringUtils.isNotBlank(s) && s.length()<=maxLen && s.length() >= minLen)
            return true;
        
        return false;
    }
    
    public static final boolean lengthRange(String s, int minLen, int maxLen){
        if(StringUtils.isNotBlank(s)) {
            String newStr = s.replace("[\\x00-\\xff]", "--");
            return newStr.length()<=maxLen && newStr.length() >= minLen;
        } else {
            return false;
        }
    }
	
	/**
	 * 从字符串s中，如果是s1开头，则删除之
	 */
	public static String removeFrist(String s, String s1){
		return remove(s,s1,0);
	}
	
	/**
	 * 从字符串s中，如果是s1结尾，则删除之
	 */
	public static String removeLast(String s, String s1){
		return remove(s,s1,1);
	}
	
	/**
	 * 从字符串s中，如果是s1开头（p=0）或者结尾(p!=0)，则删除之
	 */
	public static String remove(String s, String s1, int p){
		int len = s1.length();
		if(p==0){
			if(s.startsWith(s1)){
				return s.substring(len);
			}	
		}else{
			if(s.endsWith(s1)){
				int index = s.lastIndexOf(s1);
				if(index>-1)
					return s.substring(0,index);
			}
		}
		return s;
	}
	
	/**
     * @param s
     * @return boolean
     */
    public static boolean isPopulated(String s) {
        return (s != null) && ((s = s.trim()).length() > 0) && (!(s.equalsIgnoreCase("null")));
    }

    /**
     * @param s
     * @return boolean
     */
    public static boolean isNotPopulated(String s) {
        return (s == null) || ((s = s.trim()).length() == 0) || (s.equalsIgnoreCase("null"));
    }
    
    public static boolean isPopulatedCombobx(String s) {
        return isPopulated(s) && !"sel".equalsIgnoreCase(s);
    }
    
    public static boolean isNotPopulatedCombobx(String s) {
        return !isPopulatedCombobx(s);
    }
    
    /**
     * 手机号验证
     * @param str
     * @return
     */
	public static boolean isMobile(String str) {   
        Pattern p = null;  
        Matcher m = null;  
        boolean b = false;   
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号  
        m = p.matcher(str);  
        b = m.matches();   
        return b;  
    } 

	/**
	 * 生成随机字符串(字母（包含大小写）、数字)
	 * @param length
	 * @return
	 */
    public static String getCharacterAndNumber(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母  
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字          
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    private static final char[] base = {
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
        'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
        'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
        '4', '5', '6', '7', '8', '9'
    };
    /**
     * 生成随机字符串(小写字母和数字)
     * @param length
     * @return
     */
    public static String getRandomStr(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length);
            sb.append(base[number]);
        }
        return sb.toString();
    }

    private static final char[] lowerAZ = {
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
        'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
        'u', 'v', 'w', 'x', 'y', 'z'
    };
    /**
     * 生成随机字符串(小写字母)
     * @param length
     * @return
     */
    public static String getRandomLowerAZ(int length) {
    	Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(lowerAZ.length);
            sb.append(lowerAZ[number]);
        }
        return sb.toString();
    }

    public static boolean isNumberic(String number) {
        if (isNotPopulated(number)) {
            return false;
        } else {
            int len = number.length();
            for (int i = 0; i < len; i++) {
                char ch = number.charAt(i);
                if (!Character.isDigit(ch)) {
                    return false;
                }
            }
            return true;
        }
    }
 
	/**
	 * 生成随机数字字符串，长度与最大值长度相同，长度不足前端补0
	 * 
	 * @param max
	 *            最大值
	 * @return 例如传入参数：999；则返回009、099、999等
	 */
	public static String getRandomNumStr(int max) {
		int number = new Random().nextInt(max) + 1;
		NumberFormat formatter = NumberFormat.getNumberInstance();
		formatter.setMinimumIntegerDigits(String.valueOf(max).length());
		formatter.setGroupingUsed(false);
		return formatter.format(number);
	}
}
