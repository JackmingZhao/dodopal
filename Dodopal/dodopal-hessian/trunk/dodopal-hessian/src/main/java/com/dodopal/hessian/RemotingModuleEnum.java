package com.dodopal.hessian;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dodopal.common.util.DodopalAppVarPropsUtil;




public enum RemotingModuleEnum {

 
	;
	private final static Logger log = LoggerFactory.getLogger(RemotingModuleEnum.class);
	
	private String key;
	private String name;
	private List<String> allUrls = new ArrayList<String>();

	private RemotingModuleEnum(String key,String name ){
		this.key = key;
		this.name = name;
		for(StringTokenizer tokens = new StringTokenizer(DodopalAppVarPropsUtil.getStringProp(key), ","); tokens.hasMoreTokens();) {
			String url = tokens.nextToken().trim();
			if(!"".equals(url)) {
				allUrls.add(url);
			}
		}
	}

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public List<String> getAllUrls() {
		return this.allUrls;
	}

	public String getUniqueUrl() {
		if(this.allUrls.size() > 1) {
			log.warn("There are more than one url in the list!" + allUrls);
		}

		return this.allUrls.get(0);
	}
}
