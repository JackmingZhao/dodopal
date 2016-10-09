package com.dodopal.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.common.model.ExpTemplet;

/**
 * @author Mikaelyan
 */
public interface ExpTempletMapper {

	/**
	 * 查询单个导出模版信息
	 * @param TempletCode:列表code
	 * @return
	 */
	public List<ExpTemplet> findExpTempletsByTempletCode(String TempletCode);
	
	/**
	 * @param indexArr:属性
	 * @param typeSelStr:列表code
	 * @return
	 */
	public List<ExpTemplet> findExpTempNameArrByIndex(@Param("indexArr") String[] indexArr, @Param("typeSelStr") String typeSelStr);
	
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
	public List<ExpTemplet> findExpTempNameArrByIndexs(@Param("temCodes") String[] temCodes, @Param("typeSelStr") String [] typeSelStr);
}
