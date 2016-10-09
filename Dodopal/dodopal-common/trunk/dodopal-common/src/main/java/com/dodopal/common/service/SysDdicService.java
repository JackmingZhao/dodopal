package com.dodopal.common.service;




/**
 * 系统字典服务
 */
public interface SysDdicService {
		
	String  BEAN_NAME="sysDdicService";
	
	/**
     * 获取脚本路径
     * @return
     */
    public String getScriptUrl();
    /**
     * 获取样式路径
     * @return
     */
    public String getStyleUrl();
    
    /**
     * 获取图片路径
     * @return
     */
    public String getImgUrl();
    
    /**
     * 获取OCX路径
     * @return
     */
    public String getOcxUrl();
    
    /**
     * 重新加载map
     */
    public void doReload();
   
}
