package com.dodopal.oss.business.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.dodopal.oss.business.bean.ProfitCollectBean;
import com.dodopal.oss.business.bean.ProfitDetailsBean;
import com.dodopal.oss.business.model.PosCompany;

public class JqgridTableUtils {
    
    
    private static List<String> NON_FORM_DATA = new ArrayList<String>();
    static {
        NON_FORM_DATA.add("serialVersionUID");
        NON_FORM_DATA.add("createDate");
        NON_FORM_DATA.add("updateDate");
        NON_FORM_DATA.add("createUser");
        NON_FORM_DATA.add("updateUser");
    }
    public static void main(String[] args) {
        
     //String gridColumns= buildFormData(ProfitCollectBean.class, "companyId");
    	String gridColumns= buildGridColumns(ProfitDetailsBean.class);
     System.out.println(gridColumns);  
    }
    
    
    public static String buildFormData(Class<?> clazz, String id) {
        Field[] fields = clazz.getDeclaredFields(); 
        StringBuffer buffer = new StringBuffer();
        buffer.append("id : " + "$('#" + id + "').val(),\n");
        for (Field field : fields) {  
            if(!NON_FORM_DATA.contains(field.getName())){
                buffer.append(field.getName() + ":" + "$('#" + field.getName() +  "').val(),\n");
            }
        }
        
        String data = buffer.toString();
        data = data.substring(0, (data.length()-2));
        return data;
    }
    
    
    
    public static String buildGridColumns(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields(); 
        StringBuffer buffer = new StringBuffer();
        for (Field field : fields) {  
            if(!"serialVersionUID".equals(field.getName())) {
                buffer.append("{ name : '");
                buffer.append(field.getName());
                buffer.append("', index : '");
                buffer.append(field.getName());
                buffer.append("', width : 100, align : 'left', sortable : false },");
                buffer.append("\n");
            }
        }
        
        String columns = buffer.toString();
        columns = columns.substring(0, (columns.length()-2));
        return columns;
    }
}
