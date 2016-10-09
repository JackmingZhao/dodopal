package com.dodopal.common.service;

import java.util.List;
import java.util.Map;

import com.dodopal.common.model.DdicVo;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月10日 下午2:03:50
 */
public interface DdicService {

    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findDdicList 
     * @Description: 获取数据字典
     * @return    设定文件 
     * List<DdicVo>    返回类型 
     * @throws 
     */
    public List<DdicVo> findDdicList();
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findDDICCategoryCode 
     * @Description: 获取数据字典大分类
     * @return    设定文件 
     * List<DdicCategoryVo>    返回类型 
     * @throws 
     */
    public List<String> findDDICCategoryCode();
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: getDdicMap 
     * @Description: 获取数据字典map
     * @return    设定文件 
     * Map<String,Object>    返回类型 
     * @throws 
     */
    public Map<String,List<DdicVo>> getDdicMap();
    
    
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: getDdicNameByCode 
     * @Description: 根据大类code及小类code获取Name
     * @return    设定文件 
     * String    返回类型 
     * @throws 
     */
    public String getDdicNameByCode(String categoryCode,String code);
    
    /** 
     * 直接充数据库取值
     * @Title: getDdicNameByCodeFormDB 
     * @Description: 根据大类code及小类code获取Name
     * @return    设定文件 
     * String    返回类型 
     * @throws 
     */
    public String getDdicNameByCodeFormDB(String categoryCode,String code);
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2015年12月17日 下午7:30:39 
      * @version 1.0 
      * @parameter  
      * @since  重置数据字典中的缓存
      * @return  
      */
    public Map<String,List<DdicVo>> resetDdicMap(); 
}
