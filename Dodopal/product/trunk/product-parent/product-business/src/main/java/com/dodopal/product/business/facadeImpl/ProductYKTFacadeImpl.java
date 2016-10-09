package com.dodopal.product.business.facadeImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.ProductYKTDTO;
import com.dodopal.api.product.dto.ProductYktLimitBatchInfoDTO;
import com.dodopal.api.product.dto.ProductYktLimitInfoDTO;
import com.dodopal.api.product.dto.query.ProductYKTQueryDTO;
import com.dodopal.api.product.dto.query.ProductYktLimitBatchInfoQueryDTO;
import com.dodopal.api.product.dto.query.ProductYktLimitInfoQueryDTO;
import com.dodopal.api.product.facade.ProductYktFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.OpenSignEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.product.business.constant.ProductConstants;
import com.dodopal.product.business.model.ProductYKT;
import com.dodopal.product.business.model.ProductYktLimitBatchInfo;
import com.dodopal.product.business.model.ProductYktLimitInfo;
import com.dodopal.product.business.model.query.ProductYKTQuery;
import com.dodopal.product.business.model.query.ProductYktLimitBatchInfoQuery;
import com.dodopal.product.business.model.query.ProductYktLimitInfoQuery;
import com.dodopal.product.business.service.ProductYKTService;
import com.dodopal.product.business.service.ProductYktLimitInfoService;

@Service("productYKTFacade")
public class ProductYKTFacadeImpl implements ProductYktFacade {
    private final static Logger log = LoggerFactory.getLogger(ProductYKTFacadeImpl.class);

    @Autowired
    private ProductYKTService productYKTService;
    
    @Autowired
    private ProductYktLimitInfoService productYktLimitInfoService;
    
    /**************************************************** 基础信息管理开始 *****************************************************/
    /**
     * 查询通卡公司(分页)
     * 
     * @param queryDTO 查询条件
     * @return 
     */
    @Override
    public DodopalResponse<DodopalDataPage<ProductYKTDTO>> findProductYktByPage(ProductYKTQueryDTO queryDTO) {
        DodopalResponse<DodopalDataPage<ProductYKTDTO>> dodopalResponse = new DodopalResponse<DodopalDataPage<ProductYKTDTO>>();
        ProductYKTQuery productYKTQuery = new ProductYKTQuery();
        try {
            PropertyUtils.copyProperties(productYKTQuery, queryDTO);
            DodopalDataPage<ProductYKT> pagelist = productYKTService.findProductYktByPage(productYKTQuery);
            List<ProductYKT> productList = pagelist.getRecords();
            List<ProductYKTDTO> resultList = new ArrayList<ProductYKTDTO>();
            if (CollectionUtils.isNotEmpty(productList)) {
                for (ProductYKT productYKT : productList) {
                    ProductYKTDTO productYKTDTO = new ProductYKTDTO();
                    PropertyUtils.copyProperties(productYKTDTO, productYKT);
                    resultList.add(productYKTDTO);
                }
            }
            PageParameter page = DodopalDataPageUtil.convertPageInfo(pagelist);
            DodopalDataPage<ProductYKTDTO> pages = new DodopalDataPage<ProductYKTDTO>(page, resultList);
            dodopalResponse.setCode(ResponseCode.SUCCESS);
            dodopalResponse.setResponseEntity(pages);
        }
        catch (Exception e) {
            log.error("ProductYKTFacadeImpl call error", e);
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }

    /**
     * 查询通卡公司导出信息
     * 
     * @param queryDTO 查询条件
     * @return 
     */
    @Override
    public DodopalResponse<List<ProductYKTDTO>> getProductYktListForExportExcel(ProductYKTQueryDTO queryDTO) {
       
        DodopalResponse<List<ProductYKTDTO>> dodopalResponse = new DodopalResponse<List<ProductYKTDTO>>();
        ProductYKTQuery productYKTQuery = new ProductYKTQuery();
        try {
            
            PropertyUtils.copyProperties(productYKTQuery, queryDTO);
                        
            DodopalResponse<List<ProductYKT>> productList = productYKTService.getProductYktListForExportExcel(productYKTQuery);
            List<ProductYKTDTO> resultList = new ArrayList<ProductYKTDTO>();
            if (ResponseCode.SUCCESS.equals(productList.getCode())) {
                for (ProductYKT productYKT : productList.getResponseEntity()) {
                    ProductYKTDTO productYKTDTO = new ProductYKTDTO();
                    PropertyUtils.copyProperties(productYKTDTO, productYKT);
                    resultList.add(productYKTDTO);
                }
            }
            dodopalResponse.setCode(ResponseCode.SUCCESS);
            dodopalResponse.setResponseEntity(resultList);
        }
        catch (Exception e) {
            log.error("ProductYKTFacadeImpl call error", e);
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;

    }
    
    /**
     * 查询通卡公司详细信息
     * 
     * @param yktId 通卡公司ID
     * @return
     */
    @Override
    public DodopalResponse<ProductYKTDTO> findProductYktById(String yktId) {
        DodopalResponse<ProductYKTDTO> dodopalResponse = new DodopalResponse<ProductYKTDTO>();
        ProductYKT productYKT = productYKTService.findProductYktById(yktId);
        ProductYKTDTO productYKTDTO = new ProductYKTDTO();
        try {
            if (null != productYKT) {
                PropertyUtils.copyProperties(productYKTDTO, productYKT);
            }
            dodopalResponse.setCode(ResponseCode.SUCCESS);
            dodopalResponse.setResponseEntity(productYKTDTO);
        }
        catch (Exception e) {
            log.error("ProductYKTFacadeImpl call error", e);
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }

    /**
     * 停启用通卡公司
     * 
     * @param yktCodes 一卡通代码
     * @param activate 启用/停用状态
     * @param updateUser 更新者
     * @return
     */
    @Override
    public DodopalResponse<Integer> startOrStopYkt(List<String> yktCodes, String activate, String updateUser) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);
        if (StringUtils.isBlank(activate) || ActivateEnum.getActivateByCode(activate) == null) {
            response.setCode(ResponseCode.ACTIVATE_ERROR);
        }
        if (CollectionUtils.isEmpty(yktCodes)) {
            response.setCode(ResponseCode.PRODUCT_FIND_YKT_CODE_NULL);
        }
        try {
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                int updateNum = productYKTService.batchUpdateYktActivate(activate, yktCodes, updateUser);
                response.setResponseEntity(updateNum);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    /**
     * 新增通卡公司
     * 
     * @param ProductYKTDTO 通卡公司
     * @return
     */
    @Override
    public DodopalResponse<Integer> addProductYkt(ProductYKTDTO yktDto) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);
        ProductYKT productYKT = new ProductYKT();
        log.info("+yktDto.getYktRechargeLimitStartTime:"+yktDto.getYktRechargeLimitStartTime());
        log.info("yktDto.getYktRechargeLimitEndTime:"+yktDto.getYktRechargeLimitEndTime());
        try {
            PropertyUtils.copyProperties(productYKT, yktDto);
            
            // 保存验证
            response = validateSaveYkt(productYKT);
            

            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                log.info("productYKT.getYktRechargeLimitStartTime:"+productYKT.getYktRechargeLimitStartTime());
                log.info("productYKT.getYktRechargeLimitEndTime:"+productYKT.getYktRechargeLimitEndTime());
                int result = productYKTService.addProductYkt(productYKT);
                response.setResponseEntity(result);
            }
        }
        catch (DDPException de){
            log.error("DDPException call error", de);
            de.printStackTrace();
            response.setCode(de.getCode());
        }
        catch (Exception e) {
            log.error("ProductYKTFacadeImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    /**
     * 更新通卡公司
     * 
     * @param ProductYKTDTO 通卡公司
     * @return
     */
    @Override
    public DodopalResponse<Integer> updateProductYkt(ProductYKTDTO yktDto) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);
        ProductYKT productYKT = new ProductYKT();
        try {
            PropertyUtils.copyProperties(productYKT, yktDto);
            response = validateSaveYkt(productYKT);
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                int result = productYKTService.updateProductYkt(productYKT);
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(result);
            }
        }
        catch(DDPException e2){
            log.error("ProductYKTFacadeImpl call error", e2);
            e2.printStackTrace();
            response.setCode(e2.getCode());  
        }
        catch (Exception e) {
            log.error("ProductYKTFacadeImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    
    /**
     * 保存通卡公司信息验证
     * 
     * @param ProductYKT 通卡公司
     * @return
     */
    private DodopalResponse<Integer> validateSaveYkt(ProductYKT yktDto) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);
        if (StringUtils.isBlank(yktDto.getYktCode())) {
            response.setCode(ResponseCode.PRODUCT_FIND_YKT_CODE_NULL);
        } else if (StringUtils.isBlank(yktDto.getYktName())) {
            response.setCode(ResponseCode.PRODUCT_FIND_YKT_NAME_NULL);
        } else if (StringUtils.isBlank(yktDto.getProvinceId())) {
            response.setCode(ResponseCode.PRODUCT_FIND_YKT_PROVINCE_NULL);
        } else if (StringUtils.isBlank(yktDto.getCityId())) {
            response.setCode(ResponseCode.PRODUCT_FIND_YKT_CITY_NULL);
        } else if (StringUtils.isBlank(yktDto.getYktAddress())) {
            response.setCode(ResponseCode.PRODUCT_FIND_YKT_ADDRESS_NULL);
        } else if (StringUtils.isBlank(yktDto.getYktPayFlag())) {
            response.setCode(ResponseCode.PRODUCT_FIND_YKT_PATTYPE_NULL);
        } else if (StringUtils.isBlank(yktDto.getActivate()) || ActivateEnum.getActivateByCode(yktDto.getActivate()) == null) {
            response.setCode(ResponseCode.PRODUCT_FIND_YKT_ACTIVATE_NULL);
        } else if (StringUtils.isBlank(yktDto.getYktIsRecharge())) {
            response.setCode(ResponseCode.PRODUCT_FIND_YKT_IS_RECHARGE_NULL);
        } else if (OpenSignEnum.OPENED.getCode().equals(yktDto.getYktIsRecharge()) && StringUtils.isBlank(yktDto.getYktRechargeSetType())) {
            response.setCode(ResponseCode.PRODUCT_FIND_YKT_RECHARGE_SETTYPE_NULL);
        } else if (OpenSignEnum.OPENED.getCode().equals(yktDto.getYktIsRecharge()) && StringUtils.isBlank(yktDto.getYktRechargeSetPara())) {
            response.setCode(ResponseCode.PRODUCT_FIND_YKT_RECHARGE_SETPARA_NULL);
        } else if (StringUtils.isBlank(yktDto.getYktIsPay())) {
            response.setCode(ResponseCode.PRODUCT_FIND_YKT_IS_PAY_NULL);
        } else if (OpenSignEnum.OPENED.getCode().equals(yktDto.getYktIsPay()) && StringUtils.isBlank(yktDto.getYktPaysetType())) {
            response.setCode(ResponseCode.PRODUCT_FIND_YKT_PAY_SETTYPE_NULL);
        } else if (OpenSignEnum.OPENED.getCode().equals(yktDto.getYktIsPay()) && StringUtils.isBlank(yktDto.getYktPaySetPara())) {
            response.setCode(ResponseCode.PRODUCT_FIND_YKT_PAY_SETPARA_NULL);
        } else if (StringUtils.isBlank(yktDto.getBusinessCityId())) {
            response.setCode(ResponseCode.PRODUCT_FIND_YKT_BUSINESSCITY_NULL);
        } else if (productYKTService.checkYktCodeExsit(yktDto)) {
            response.setCode(ResponseCode.PRODUCT_FIND_YKT_CODE_EXSIT);
        } else if (productYKTService.checkYktNameExsit(yktDto)) {
            response.setCode(ResponseCode.PRODUCT_FIND_YKT_NAME_EXSIT);
        } else if (productYKTService.checkYktBusinessCityExsit(yktDto.getYktCode(), yktDto.getBusinessCityId().split(ProductConstants.COMMA))) {
            response.setCode(ResponseCode.PRODUCT_FIND_YKT_BUSINESSCITY_EXSIT);
        }
        return response;
    }

    /**************************************************** 基础信息管理结束 *****************************************************/
    /**************************************************** 额度管理开始 *****************************************************/
    /**
     * 查询额度信息(分页)
     * 
     * @param queryDTO 查询条件
     * @return
     */
    @Override
    public DodopalResponse<DodopalDataPage<ProductYktLimitInfoDTO>> findProductYktLimitInfoByPage(ProductYktLimitInfoQueryDTO queryDTO) {
        DodopalResponse<DodopalDataPage<ProductYktLimitInfoDTO>> dodopalResponse = new DodopalResponse<DodopalDataPage<ProductYktLimitInfoDTO>>();
        ProductYktLimitInfoQuery productYktLimitInfoQuery = new ProductYktLimitInfoQuery();
        try {
            PropertyUtils.copyProperties(productYktLimitInfoQuery, queryDTO);
            DodopalDataPage<ProductYktLimitInfo> pagelist = productYktLimitInfoService.findProductYktLimitInfoByPage(productYktLimitInfoQuery);
            List<ProductYktLimitInfo> productYktLimitInfoList = pagelist.getRecords();
            List<ProductYktLimitInfoDTO> resultList = new ArrayList<ProductYktLimitInfoDTO>();
            if (CollectionUtils.isNotEmpty(productYktLimitInfoList)) {
                for (ProductYktLimitInfo productYktLimitInfo : productYktLimitInfoList) {
                    ProductYktLimitInfoDTO productYktLimitInfoDTO = new ProductYktLimitInfoDTO();
                    PropertyUtils.copyProperties(productYktLimitInfoDTO, productYktLimitInfo);
                    resultList.add(productYktLimitInfoDTO);
                }
            }
            PageParameter page = DodopalDataPageUtil.convertPageInfo(pagelist);
            DodopalDataPage<ProductYktLimitInfoDTO> pages = new DodopalDataPage<ProductYktLimitInfoDTO>(page, resultList);
            dodopalResponse.setCode(ResponseCode.SUCCESS);
            dodopalResponse.setResponseEntity(pages);
        }
        catch (Exception e) {
            log.error("ProductYKTFacadeImpl call error", e);
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }

    /**
     * 获取额度导出信息
     * 
     * @param queryDTO 查询条件
     * @return
     */
    @Override
    public DodopalResponse<List<ProductYktLimitInfoDTO>> getProductYktLimitListForExportExcel(ProductYktLimitInfoQueryDTO queryDTO) {
        DodopalResponse<List<ProductYktLimitInfoDTO>> dodopalResponse = new DodopalResponse<List<ProductYktLimitInfoDTO>>();
        ProductYktLimitInfoQuery productYktLimitInfoQuery = new ProductYktLimitInfoQuery();
        try {
            PropertyUtils.copyProperties(productYktLimitInfoQuery, queryDTO);
            DodopalResponse<List<ProductYktLimitInfo>> listResponse = productYktLimitInfoService.getProductYktLimitListForExportExcel(productYktLimitInfoQuery);
            List<ProductYktLimitInfoDTO> resultList = new ArrayList<ProductYktLimitInfoDTO>();
            if (ResponseCode.SUCCESS.equals(listResponse.getCode())) {
                for (ProductYktLimitInfo productYktLimitInfo : listResponse.getResponseEntity()) {
                    ProductYktLimitInfoDTO productYktLimitInfoDTO = new ProductYktLimitInfoDTO();
                    PropertyUtils.copyProperties(productYktLimitInfoDTO, productYktLimitInfo);
                    resultList.add(productYktLimitInfoDTO);
                }
                dodopalResponse.setCode(ResponseCode.SUCCESS);
                dodopalResponse.setResponseEntity(resultList);
            } else {
                dodopalResponse.setCode(listResponse.getCode());
            }
        }
        catch (Exception e) {
            log.error("ProductYKTFacadeImpl call error", e);
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }
    
    /**
     * 查询额度信息明细
     * 
     * @param limitId 额度信息ID
     * @return
     */
    @Override
    public DodopalResponse<ProductYktLimitInfoDTO> findProductYktLimitInfoById(String limitId) {
        DodopalResponse<ProductYktLimitInfoDTO> dodopalResponse = new DodopalResponse<ProductYktLimitInfoDTO>();
        ProductYktLimitInfo productYktLimitInfo = productYktLimitInfoService.findProductYktLimitInfoById(limitId);
        ProductYktLimitInfoDTO productYktLimitInfoDTO = new ProductYktLimitInfoDTO();
        try {
            if (null != productYktLimitInfo) {
                PropertyUtils.copyProperties(productYktLimitInfoDTO, productYktLimitInfo);
            }
            dodopalResponse.setCode(ResponseCode.SUCCESS);
            dodopalResponse.setResponseEntity(productYktLimitInfoDTO);
        }
        catch (Exception e) {
            log.error("ProductYKTFacadeImpl call error", e);
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }
    
    /**
     * 修改额度信息明细
     * 
     * @param ProductYktLimitInfoDTO 额度信息
     * @return
     */
    @Override
    public DodopalResponse<Integer> saveProductYktLimitInfo(ProductYktLimitInfoDTO productYktLimitInfoDTO) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);
        ProductYktLimitInfo productYktLimitInfo = new ProductYktLimitInfo();
        try {
            PropertyUtils.copyProperties(productYktLimitInfo, productYktLimitInfoDTO);
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                int result = productYktLimitInfoService.updateProductYktLimitInfo(productYktLimitInfo);
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(result);
            }
        }
        catch (Exception e) {
            log.error("ProductYKTFacadeImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
 
    /**************************************************** 额度管理结束 *****************************************************/
    /**************************************************** 额度批次管理开始 *****************************************************/
    
    /**
     * 查看批次信息(分頁)
     * 
     * @param ProductYktLimitBatchInfoQueryDTO 查询条件
     * @return
     */
    @Override
    public DodopalResponse<DodopalDataPage<ProductYktLimitBatchInfoDTO>> findProductYktLimitBatchInfoByPage(ProductYktLimitBatchInfoQueryDTO queryDTO) {
        DodopalResponse<DodopalDataPage<ProductYktLimitBatchInfoDTO>> dodopalResponse = new DodopalResponse<DodopalDataPage<ProductYktLimitBatchInfoDTO>>();
        ProductYktLimitBatchInfoQuery productYktLimitBatchInfoQuery = new ProductYktLimitBatchInfoQuery();
        try {
            PropertyUtils.copyProperties(productYktLimitBatchInfoQuery, queryDTO);
            DodopalDataPage<ProductYktLimitBatchInfo> pagelist = productYktLimitInfoService.findProductYktLimitBatchInfoByPage(productYktLimitBatchInfoQuery);
            List<ProductYktLimitBatchInfo> productYktLimitBatchInfoList = pagelist.getRecords();
            List<ProductYktLimitBatchInfoDTO> resultList = new ArrayList<ProductYktLimitBatchInfoDTO>();
            if (CollectionUtils.isNotEmpty(productYktLimitBatchInfoList)) {
                for (ProductYktLimitBatchInfo productYktLimitBatchInfo : productYktLimitBatchInfoList) {
                    ProductYktLimitBatchInfoDTO productYktLimitBatchInfoDTO = new ProductYktLimitBatchInfoDTO();
                    PropertyUtils.copyProperties(productYktLimitBatchInfoDTO, productYktLimitBatchInfo);
                    resultList.add(productYktLimitBatchInfoDTO);
                }
            }
            PageParameter page = DodopalDataPageUtil.convertPageInfo(pagelist);
            DodopalDataPage<ProductYktLimitBatchInfoDTO> pages = new DodopalDataPage<ProductYktLimitBatchInfoDTO>(page, resultList);
            dodopalResponse.setCode(ResponseCode.SUCCESS);
            dodopalResponse.setResponseEntity(pages);
        }
        catch (Exception e) {
            log.error("ProductYKTFacadeImpl call error", e);
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }
    
    /**
     * 查看额度批次详情
     */
    @Override
    public DodopalResponse<ProductYktLimitBatchInfoDTO> findProductYktLimitBatchInfoById(String id) {
        DodopalResponse<ProductYktLimitBatchInfoDTO> dodopalResponse = new DodopalResponse<ProductYktLimitBatchInfoDTO>();
        try {
            ProductYktLimitBatchInfoDTO productYktLimitBatchInfoDTO = new ProductYktLimitBatchInfoDTO();
            ProductYktLimitBatchInfo batchInfo = productYktLimitInfoService.findProductYktLimitBatchInfoById(id);
            if (batchInfo != null) {
                PropertyUtils.copyProperties(productYktLimitBatchInfoDTO, batchInfo);
            }
            dodopalResponse.setCode(ResponseCode.SUCCESS);
            dodopalResponse.setResponseEntity(productYktLimitBatchInfoDTO);
        }
        catch (Exception e) {
            log.error("ProductYKTFacadeImpl call error", e);
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }
    
    /**
     * 导出批次信息
     * 
     * @param ProductYktLimitBatchInfoQueryDTO 查询条件
     * @return
     */
    @Override
    public DodopalResponse<List<ProductYktLimitBatchInfoDTO>> getProductYktLimitBatchListForExportExcel(ProductYktLimitBatchInfoQueryDTO queryDTO) {
        DodopalResponse<List<ProductYktLimitBatchInfoDTO>> dodopalResponse = new DodopalResponse<List<ProductYktLimitBatchInfoDTO>>();
        ProductYktLimitBatchInfoQuery productYktLimitBatchInfoQuery = new ProductYktLimitBatchInfoQuery();
        try {
            PropertyUtils.copyProperties(productYktLimitBatchInfoQuery, queryDTO);
            DodopalResponse<List<ProductYktLimitBatchInfo>> productYktLimitBatchInfoList = productYktLimitInfoService.getProductYktLimitBatchListForExportExcel(productYktLimitBatchInfoQuery);
            dodopalResponse.setCode(productYktLimitBatchInfoList.getCode());
            List<ProductYktLimitBatchInfoDTO> resultList = new ArrayList<ProductYktLimitBatchInfoDTO>();
            if (ResponseCode.SUCCESS.equals(productYktLimitBatchInfoList.getCode())) {
                for (ProductYktLimitBatchInfo productYktLimitBatchInfo : productYktLimitBatchInfoList.getResponseEntity()) {
                    ProductYktLimitBatchInfoDTO productYktLimitBatchInfoDTO = new ProductYktLimitBatchInfoDTO();
                    PropertyUtils.copyProperties(productYktLimitBatchInfoDTO, productYktLimitBatchInfo);
                    resultList.add(productYktLimitBatchInfoDTO);
                }
                dodopalResponse.setResponseEntity(resultList);
            }
        }
        catch (Exception e) {
            log.error("ProductYKTFacadeImpl call error", e);
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }
    
    /**
     * 追加购买额度申请单
     * 
     * @param ProductYktLimitBatchInfoDTO 批次信息
     * @return
     */
    @Override
    public DodopalResponse<Integer> addProductYktLimitBatchInfo(ProductYktLimitBatchInfoDTO productYktLimitBatchInfoDTO) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);
        ProductYktLimitBatchInfo productYktLimitBatchInfo = new ProductYktLimitBatchInfo();
        try {
            PropertyUtils.copyProperties(productYktLimitBatchInfo, productYktLimitBatchInfoDTO);
            response = productYktLimitInfoService.addProductYktLimitBatchInfo(productYktLimitBatchInfo);
        }
        catch (Exception e) {
            log.error("ProductYKTFacadeImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    /**
     * 删除购买额度申请单
     * 
     * @param id
     * @return
     */
    @Override
    public DodopalResponse<Integer> deleteProductYktLimitBatchInfo(String id) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            response = productYktLimitInfoService.deleteProductYktLimitBatchInfo(id);
        }
        catch (Exception e) {
            log.error("ProductYKTFacadeImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    /**
     * 修改额度购买申请单
     * 
     * @param ProductYktLimitBatchInfoDTO 批次信息
     * @return
     */
    @Override
    public DodopalResponse<Integer> saveProductYktLimitBatchInfo(ProductYktLimitBatchInfoDTO productYktLimitBatchInfoDTO) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        ProductYktLimitBatchInfo productYktLimitBatchInfo = new ProductYktLimitBatchInfo();
        try {
            PropertyUtils.copyProperties(productYktLimitBatchInfo, productYktLimitBatchInfoDTO);
            response = productYktLimitInfoService.updateProductYktLimitBatchInfo(productYktLimitBatchInfo);
        }
        catch (Exception e) {
            log.error("ProductYKTFacadeImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    /**
     * 审核额度购买申请单
     * 
     * @param ProductYktLimitBatchInfoDTO 批次信息
     * @return
     */
    @Override
    public DodopalResponse<Integer> auditProductYktLimitBatchInfo(ProductYktLimitBatchInfoDTO productYktLimitBatchInfoDTO) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        ProductYktLimitBatchInfo productYktLimitBatchInfo = new ProductYktLimitBatchInfo();
        try {
            PropertyUtils.copyProperties(productYktLimitBatchInfo, productYktLimitBatchInfoDTO);
            response = productYktLimitInfoService.auditProductYktLimitBatchInfo(productYktLimitBatchInfo);
        }
        catch (Exception e) {
            log.error("ProductYKTFacadeImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    /**
     * 复核额度购买申请单
     * 
     * @param ProductYktLimitBatchInfoDTO 批次信息
     * @return
     */
    @Override
    public DodopalResponse<Integer> checkProductYktLimitBatchInfo(ProductYktLimitBatchInfoDTO productYktLimitBatchInfoDTO) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        ProductYktLimitBatchInfo productYktLimitBatchInfo = new ProductYktLimitBatchInfo();
        try {
            PropertyUtils.copyProperties(productYktLimitBatchInfo, productYktLimitBatchInfoDTO);
            response = productYktLimitInfoService.checkProductYktLimitBatchInfo(productYktLimitBatchInfo);
        }
        catch (Exception e) {
            log.error("ProductYKTFacadeImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    /**************************************************** 额度批次管理开始 *****************************************************/
    /**************************************************** 产品库 公交卡充值产品  start****************************************************/
    @Override
    public DodopalResponse<List<Map<String, String>>> queryIcdcNames(String activate) {
        DodopalResponse<List<Map<String, String>>> response = new DodopalResponse<List<Map<String, String>>>();
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        try {
            List<ProductYKT> list = productYKTService.getIsRechargeYktMap(activate);
            for (ProductYKT dto : list) {
                Map<String, String> yktMap = new HashMap<String, String>();
                yktMap.put(dto.getYktCode(), dto.getYktName());
                result.add(yktMap);
            }
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(result);
        }
        catch (Exception e) {
            log.error("ProductYKTFacadeImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    /**
     * 审核额度购买申请单
     * 
     * @param ProductYktLimitBatchInfoDTO 批次信息
     * @return
     */
    @Override
    public DodopalResponse<List<Map<String, String>>> queryIcdcBusiCities(String code) {
        DodopalResponse<List<Map<String, String>>> response = new DodopalResponse<List<Map<String, String>>>();
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        try {
            List<ProductYKT> list = productYKTService.getBusinessCityByYktCode(code);
            for (ProductYKT dto : list) {
                Map<String, String> cityMap = new HashMap<String, String>();
                cityMap.put(dto.getBusinessCityId(), dto.getBusinessCityName());
                result.add(cityMap);
            }
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(result);
        }
        catch (Exception e) {
            log.error("ProductYKTFacadeImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    /****************************************************产品库 公交卡充值产品  end****************************************************/
    /**************************************************** 通卡公司业务费率信息 start***************************************************/
    @Override
    public DodopalResponse<List<ProductYKTDTO>> getAllYktBusinessRateList() {
        DodopalResponse<List<ProductYKTDTO>> response = new DodopalResponse<List<ProductYKTDTO>>();
        List<ProductYKTDTO> resultList = new ArrayList<ProductYKTDTO>();
        try {
            List<ProductYKT> list = productYKTService.getAllYktBusinessRateList();
            if (CollectionUtils.isNotEmpty(list)) {
                for (ProductYKT productYKT : list) {
                    ProductYKTDTO productYKTDTO = new ProductYKTDTO();
                    PropertyUtils.copyProperties(productYKTDTO, productYKT);
                    resultList.add(productYKTDTO);
                }
            }
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(resultList);
        }
        catch (Exception e) {
            log.error("ProductYKTFacadeImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    /**************************************************** 通卡公司业务费率信息 end***************************************************/

    @Override
	public DodopalResponse<ProductYKTDTO> getYktInfoByBusinessCityCode(
			String cityCode) {
		 DodopalResponse<ProductYKTDTO>  response = new DodopalResponse<ProductYKTDTO>();
		if(StringUtils.isBlank(cityCode)){
			response.setCode(ResponseCode.PRODUCT_FIND_YKT_CITY_NULL);
		}
		ProductYKTDTO prYktdto = new ProductYKTDTO();
		try {
			ProductYKT  productYKT =productYKTService.getYktInfoByBusinessCityCode(cityCode);
			if(null!=productYKT){
					PropertyUtils.copyProperties(prYktdto, productYKT);
			}
			response.setCode(ResponseCode.SUCCESS);
			response.setResponseEntity(prYktdto);
		} catch (Exception e) {
			 log.error("ProductYKTFacadeImpl getYktInfoByBusinessCityCode call error", e);
			 response.setCode(ResponseCode.SYSTEM_ERROR);
			e.printStackTrace();
		}
		return response;
	}

}
