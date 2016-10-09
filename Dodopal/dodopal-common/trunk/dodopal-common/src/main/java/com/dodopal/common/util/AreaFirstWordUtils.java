package com.dodopal.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.dodopal.common.model.Area;

public class AreaFirstWordUtils {
	 public static Map<String,List<Area>> createAreaMap(List<Area> allArea ){
		 if(CollectionUtils.isEmpty(allArea)){
			 return null;
		 }
	    	Map<String,List<Area>> allAreaWordMap = new HashMap<String,List<Area>>();
	    	for(Area tempArea:allArea){
	    		if(StringUtils.isNotBlank(tempArea.getCityAbridge())){
	    			String firstWord = tempArea.getCityAbridge().substring(0, 1).toUpperCase();
	    			if(!allAreaWordMap.containsKey(firstWord)){
	    				allAreaWordMap.put(firstWord,new ArrayList<Area>());
	    			}
	    			List<Area> sList = allAreaWordMap.get(firstWord);
	    			sList.add(tempArea);
	    		}
	    	}
			return allAreaWordMap;
	    }
}
