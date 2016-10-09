package com.dodopal.api.product.facade;

import java.util.List;
import java.util.Map;

import com.dodopal.api.product.dto.ProductYKTDTO;
import com.dodopal.api.product.dto.ProductYktLimitBatchInfoDTO;
import com.dodopal.api.product.dto.ProductYktLimitInfoDTO;
import com.dodopal.api.product.dto.query.ProductYKTQueryDTO;
import com.dodopal.api.product.dto.query.ProductYktLimitBatchInfoQueryDTO;
import com.dodopal.api.product.dto.query.ProductYktLimitInfoQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface ProductYktFacade {

    /**************************************************** 基础信息管理开始 *****************************************************/
	/**
	 * 查询通卡公司(分页)
	 * @param queryDTO 查询条件
	 * @return
	 */
	DodopalResponse<DodopalDataPage<ProductYKTDTO>> findProductYktByPage(ProductYKTQueryDTO queryDTO);

	/**
     * 获取通卡公司基础信息导出
     * @param queryDTO 查询条件
     * @return
     */
    DodopalResponse<List<ProductYKTDTO>> getProductYktListForExportExcel(ProductYKTQueryDTO queryDTO);
    
	/**
	 * 查询通卡公司详细信息
	 * @param yktId 通卡公司ID
	 * @return
	 */
	DodopalResponse<ProductYKTDTO> findProductYktById(String yktId);

	/**
     * 启用/停用通卡公司
     * @param yktCodes 通卡公司codeList
     * @param activate 启用标识
     * @param updateUser 编辑人code
     * @return 设定文件 DodopalResponse<Integer> 返回类型
     * @throws
     */
    DodopalResponse<Integer> startOrStopYkt(List<String> yktCodes, String activate, String updateUser);
    
	/**
	 * 添加通卡公司(单条数据添加)
	 * @param yktDto 通卡公司信息
	 * @return
	 */
	DodopalResponse<Integer> addProductYkt(ProductYKTDTO yktDto);

	/**
	 * 	修改通卡公司
	 * @param yktDTO 通卡公司信息
	 * @return
	 */
	DodopalResponse<Integer> updateProductYkt(ProductYKTDTO yktDto);
    
    /**************************************************** 基础信息管理结束 *****************************************************/
    
    /**************************************************** 额度管理开始 *****************************************************/
	/**
     * 查询额度信息(分页)
     * @param queryDTO 查询条件
     * @return
     */
    DodopalResponse<DodopalDataPage<ProductYktLimitInfoDTO>> findProductYktLimitInfoByPage(ProductYktLimitInfoQueryDTO queryDTO);
	
    /**
     * 获取额度导出信息
     * @param queryDTO 查询条件
     * @return
     */
    DodopalResponse<List<ProductYktLimitInfoDTO>> getProductYktLimitListForExportExcel(ProductYktLimitInfoQueryDTO queryDTO);
    
    /**
     * 查询通卡公司详细信息
     * @param limitId
     * @return
     */
    DodopalResponse<ProductYktLimitInfoDTO> findProductYktLimitInfoById(String limitId);
    
    /**
     *  修改额度信息
     * @param productYktLimitInfoDTO
     * @return
     */
    DodopalResponse<Integer> saveProductYktLimitInfo(ProductYktLimitInfoDTO productYktLimitInfoDTO);
  
    
	/**************************************************** 额度管理结束 *****************************************************/
	
    /**************************************************** 额度批次管理开始 *****************************************************/
    
    
    /**
     * 查询批次申请单信息(分页)
     * @param queryDTO 查询条件
     * @return
     */
    DodopalResponse<DodopalDataPage<ProductYktLimitBatchInfoDTO>> findProductYktLimitBatchInfoByPage(ProductYktLimitBatchInfoQueryDTO queryDTO);
    
    /**
     * 查看批次申请单信息详情
     * @param id 查询条件
     * @return
     */
    DodopalResponse<ProductYktLimitBatchInfoDTO> findProductYktLimitBatchInfoById(String id);
    
    /**
     * 导出批次申请单
     * @param queryDTO 查询条件
     * @return
     */
    DodopalResponse<List<ProductYktLimitBatchInfoDTO>> getProductYktLimitBatchListForExportExcel(ProductYktLimitBatchInfoQueryDTO queryDTO);
    
    /**
     *  追加批次申请单
     * @param ProductYktLimitBatchInfoDTO
     * @return
     */
    DodopalResponse<Integer> addProductYktLimitBatchInfo(ProductYktLimitBatchInfoDTO productYktLimitBatchInfoDTO);
    
    /**
     *  删除批次申请单
     * @param id
     * @return
     */
    DodopalResponse<Integer> deleteProductYktLimitBatchInfo(String id);
    
    /**
     *  修改批次申请单
     * @param ProductYktLimitBatchInfoDTO
     * @return
     */
    DodopalResponse<Integer> saveProductYktLimitBatchInfo(ProductYktLimitBatchInfoDTO productYktLimitBatchInfoDTO);
    
    /**
     *  审核批次申请单
     * @param ProductYktLimitBatchInfoDTO
     * @return
     */
    DodopalResponse<Integer> auditProductYktLimitBatchInfo(ProductYktLimitBatchInfoDTO productYktLimitBatchInfoDTO);
    
    /**
     *  复核批次申请单
     * @param ProductYktLimitBatchInfoDTO
     * @return
     */
    DodopalResponse<Integer> checkProductYktLimitBatchInfo(ProductYktLimitBatchInfoDTO productYktLimitBatchInfoDTO);
    
    
    /**************************************************** 额度批次管理开始 *****************************************************/
	
    
	/**************************************************** 产品库 公交卡充值产品  start****************************************************/
	/**
     * 查询开通一卡通充值业务的启用的一卡通公司名称信息
     * @return
     */
    DodopalResponse<List<Map<String,String>>> queryIcdcNames(String activate);

    /**
     * 查询一卡通公司业务城市
     * @param code
     * @return
     */
    DodopalResponse<List<Map<String,String>>> queryIcdcBusiCities(String code);
    /**************************************************** 产品库 公交卡充值产品  end****************************************************/
    
    /**************************************************** 通卡公司业务费率信息 start***************************************************/
    
    /**
     * 获取所有的通卡公司业务费率信息(门户，oss商户调用)
     * 
     * @return
     */
    DodopalResponse<List<ProductYKTDTO>> getAllYktBusinessRateList();
    /**************************************************** 通卡公司业务费率信息 end***************************************************/
    
    /** 
     * @author  Dingkuiyuan@dodopal.com 
     * 根据城市号查询通卡公司的信息
     * 
     */
    DodopalResponse<ProductYKTDTO> getYktInfoByBusinessCityCode(String cityCode);
   
}
