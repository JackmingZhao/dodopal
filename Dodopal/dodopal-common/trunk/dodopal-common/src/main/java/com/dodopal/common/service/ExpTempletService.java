package com.dodopal.common.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dodopal.common.model.ExpTemplet;

/**
 * @author Mikaelyan
 * @version 创建时间：2015-12-03 15:47:50
 * 导出Excel时  用户选择的要导出的列
 * @
 */
public interface ExpTempletService {
	
    /** 
     * @author Mikaelyan
     * @Title: findExpSelItem 
     * @Description: 获取导出的Excel的列的信息
     * @return    设定文件 
     * List<ExpTemplet>    返回类型 
     * @throws 
     */
	public List<ExpTemplet> findExpSelItem(String templetCode);
	
	/**
	 * @author Mikaelyan
	 * @param index	
	 * @Description: 根据列索引获取导出的Excel的 列头的 名字
	 * @return
	 */
	public String[] findExpSelNameArrByIndexes(String[] index, String typeSelStr);
	
	/**
	 * @author Mikaelyan
	 * @param index
	 * @param typeSelStr
	 * @return 得到 列索引 和 列名字 的 有序键值对Map
	 */
	public Map<String, String> getCloName(String[] index, String typeSelStr);
	
	/**
	 * @param temCodes:列表code集合;多个用,分割
	 * @return
	 */
	public List<ExpTemplet> findExpTempletsByTempletCodes(@Param("temCodes") String[] temCodes);
	
	/**
	 * @param temCodes:列表code集合;多个用,分割
	 * @param typeSelStr:属性集合;多个用,分割
	 * @return
	 */
	public String[] findExpTempNameArrByIndexs(@Param("temCodes") String[] temCodes, @Param("typeSelStr") String typeSelStr);
}
