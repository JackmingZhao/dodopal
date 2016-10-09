package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public enum PosClassifyEnum {
    
    POS_WEB("0","web(家用机)"),
    
    POS_IN_HAND("1","手持机(商用机)");
    
    private static final Map<String, PosClassifyEnum> map = new HashMap<String, PosClassifyEnum>(values().length);

    static {
        for (PosClassifyEnum posClassifyEnum : values()) {
            map.put(posClassifyEnum.getCode(), posClassifyEnum);
        }
    }
    
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
   
   PosClassifyEnum(String code, String name){
       this.code = code;
       this.name = name;
   }

   /** 根据状态码获取其对应的枚举状态 **/
   public static PosClassifyEnum parseByCode(String source) {
       for (PosClassifyEnum codes : values()) {
           if (codes.code.equals(source)) {
               return codes;
           }
       }
       return null;
   }
   

   public static PosClassifyEnum getPosClassifyByCode(String code) {
       if (StringUtils.isBlank(code)) {
           return null;
       }
       return map.get(code);
   }
   
}
