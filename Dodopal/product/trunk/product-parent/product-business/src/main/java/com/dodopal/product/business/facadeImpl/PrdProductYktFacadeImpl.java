package com.dodopal.product.business.facadeImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dodopal.api.product.dto.PrdProductYktDTO;
import com.dodopal.api.product.dto.query.PrdProductYktQueryDTO;
import com.dodopal.api.product.facade.PrdProductYktFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ProductStatusEnum;
import com.dodopal.common.enums.ProductTypeEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.product.business.model.PrdProductYkt;
import com.dodopal.product.business.model.query.PrdProductYktQuery;
import com.dodopal.product.business.service.PrdProductYktService;
import com.dodopal.product.delegate.MerchantDelegate;

@Service("prdProductYktFacade")
public class PrdProductYktFacadeImpl implements PrdProductYktFacade{
    
    private static Logger logger = Logger.getLogger(PrdProductYktFacadeImpl.class);
    @Autowired
    private PrdProductYktService productYktService;
    @Autowired
    private MerchantDelegate merchantDelegate;
    /**
     * 查询一卡通充值产品信息（分页）
     * @param productYktQueryDTO
     * @return
     */
    @Override
    public DodopalResponse<DodopalDataPage<PrdProductYktDTO>> findPrdProductYktByPage(PrdProductYktQueryDTO productYktQueryDTO){
        DodopalResponse<DodopalDataPage<PrdProductYktDTO>> response = new DodopalResponse<DodopalDataPage<PrdProductYktDTO>>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            PrdProductYktQuery productYktQuery = new PrdProductYktQuery();
            PropertyUtils.copyProperties(productYktQuery, productYktQueryDTO);
            if (productYktQuery.getPage() == null) {
                productYktQuery.setPage(new PageParameter());
            }
            
            DodopalDataPage<PrdProductYkt> ddpResult = productYktService.findPrdProductYktByPage(productYktQuery);
            if (ddpResult != null) {
                    
                List<PrdProductYkt> resultList = ddpResult.getRecords();
                List<PrdProductYktDTO> resResultList = null;
                if (resultList != null && resultList.size() > 0) {
                    resResultList = new ArrayList<PrdProductYktDTO>(resultList.size());
                    for (PrdProductYkt pro : resultList) {
                        PrdProductYktDTO proDTO = new PrdProductYktDTO();
                        PropertyUtils.copyProperties(proDTO,pro);
                        resResultList.add(proDTO);
                    }
                }
                PageParameter page =  DodopalDataPageUtil.convertPageInfo(ddpResult);
                DodopalDataPage<PrdProductYktDTO> ddpDTOResult = new DodopalDataPage<PrdProductYktDTO>(page, resResultList);
                response.setResponseEntity(ddpDTOResult);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    /**
     * 获取一卡通充值产品导出信息
     */
    @Override
    public DodopalResponse<List<PrdProductYktDTO>> getIcdcPrductListForExportExcel(PrdProductYktQueryDTO productYktQueryDTO) {
        DodopalResponse<List<PrdProductYktDTO>> response = new DodopalResponse<List<PrdProductYktDTO>>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            PrdProductYktQuery productYktQuery = new PrdProductYktQuery();
            PropertyUtils.copyProperties(productYktQuery, productYktQueryDTO);
            
            DodopalResponse<List<PrdProductYkt>> ddpResult = productYktService.getIcdcPrductListForExportExcel(productYktQuery);
            response.setCode(ddpResult.getCode());
            
            if (ResponseCode.SUCCESS.equals(ddpResult.getCode())) {
                List<PrdProductYkt> resultList = ddpResult.getResponseEntity();
                List<PrdProductYktDTO> resResultList = null;
                if (resultList != null && resultList.size() > 0) {
                    resResultList = new ArrayList<PrdProductYktDTO>(resultList.size());
                    for (PrdProductYkt pro : resultList) {
                        PrdProductYktDTO proDTO = new PrdProductYktDTO();
                        PropertyUtils.copyProperties(proDTO,pro);
                        resResultList.add(proDTO);
                    }
                }
                response.setResponseEntity(resResultList);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    /**
     * 根据产品编号查询一卡通充值产品信息
     */
    @Override
    public DodopalResponse<PrdProductYktDTO> findPrdProductYktByProCode(String proCode){
        DodopalResponse<PrdProductYktDTO> response = new DodopalResponse<PrdProductYktDTO>();
        PrdProductYktDTO productYktDTO = new PrdProductYktDTO();
        try {
            if (StringUtils.isBlank(proCode)) {
                response.setCode(ResponseCode.PRODUCT_YKT_PRO_CODE_NULL);
                return response;
            }else{
                PrdProductYkt productYkt = productYktService.findPrdProductYktByProCode(proCode);
                if(productYkt!=null){
                    PropertyUtils.copyProperties(productYktDTO,productYkt);
                }else{
                    productYktDTO = null;
                }
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(productYktDTO);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    /**
     * 新增一卡通充值产品
     */
    @Override
    public DodopalResponse<Integer> addPrdProductYkt(PrdProductYktDTO productYktDTO){
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            //校验参数和检验一卡通产品是否重复
            String resCode = addPrdProductYktCheck(productYktDTO);
            if (ResponseCode.SUCCESS.equals(resCode)) {
                PrdProductYkt productYkt = new PrdProductYkt();
                PropertyUtils.copyProperties(productYkt, productYktDTO);
                productYkt.setProType(ProductTypeEnum.STANDARD.getCode());
                int saveresult = productYktService.savePrdProductYkt(productYkt);
                if(saveresult==-1){
                    response.setCode(ResponseCode.PRODUCT_YKT_EXIST);
                }
                response.setResponseEntity(saveresult);
            }else {
                response.setCode(resCode);
            }
        } 
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    
    /**
     * 新增一卡通充值产品参数校验
     * @param productYktDTO
     * @return
     */
    private String addPrdProductYktCheck(PrdProductYktDTO productYktDTO) {
        String yktCode = productYktDTO.getYktCode();
        if (StringUtils.isBlank(yktCode)) {
            return ResponseCode.PRODUCT_YKT_CODE_NULL;
        }
        String cityId = productYktDTO.getCityId();
        if (StringUtils.isBlank(cityId)) {
            return ResponseCode.PRODUCT_CITYID_NULL;
        }
        String cityName = productYktDTO.getCityName();
        if (StringUtils.isBlank(cityName)) {
            return ResponseCode.PRODUCT_CITYNAME_NULL;
        }
        
        int proPrice = productYktDTO.getProPrice();
        if(proPrice==0){
            return ResponseCode.PRODUCT_PRO_PAYPRICE_NULL;
        }else{
            
//            if(proPrice%1000!=0){
//                return ResponseCode.PRODUCT_PRO_PAYPRICE_ERROR;
//            }
        }
        PrdProductYkt productYkt = new PrdProductYkt();
        productYkt.setYktCode(yktCode);
        productYkt.setCityId(cityId);
        productYkt.setProPrice(proPrice);
        if(productYktService.checkExist(productYkt)){
            return ResponseCode.PRODUCT_YKT_EXIST;
        }
        return ResponseCode.SUCCESS;
    }
    
    /**
     * 检查一卡通充值产品是否存在
     */
    @Override
    public DodopalResponse<Boolean> checkPrdProductYktExist(String yktCode, String cityId, int proPrice){
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            if (StringUtils.isBlank(yktCode)) {
                response.setCode(ResponseCode.PRODUCT_YKT_CODE_NULL);
                return response;
            }
            if (StringUtils.isBlank(cityId)) {
                response.setCode(ResponseCode.PRODUCT_CITYID_NULL);
                return response;
            }
            if(proPrice==0){
                response.setCode(ResponseCode.PRODUCT_PRO_PAYPRICE_NULL);
                return response;
            }else{
               /* if(proPrice%1000!=0){
                    response.setCode(ResponseCode.PRODUCT_PRO_PAYPRICE_ERROR);
                    return response;
                }*/
            }
            PrdProductYkt productYkt = new PrdProductYkt();
            productYkt.setYktCode(yktCode);
            productYkt.setCityId(cityId);
            productYkt.setProPrice(proPrice);
            boolean bool = productYktService.checkExist(productYkt);
            if (bool) {
                response.setCode(ResponseCode.PRODUCT_YKT_EXIST);
            }
            response.setResponseEntity(bool);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    /**
     * 修改一卡通充值产品
     */
    @Override
    public DodopalResponse<Integer> updatePrdProductYkt(PrdProductYktDTO productYktDTO){
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            String resCode = updatePrdProductYktCheck(productYktDTO);
            if(ResponseCode.SUCCESS.equals(resCode)){
                PrdProductYkt productYkt = new PrdProductYkt();
                PropertyUtils.copyProperties(productYkt, productYktDTO);
                int updateresult = productYktService.updatePrdProductYkt(productYkt);
                response.setResponseEntity(updateresult);
            }else{
                response.setCode(resCode);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    /**
     * 修改一卡通充值产品参数校验
     * @param productYktDTO
     * @return
     */
    private String updatePrdProductYktCheck(PrdProductYktDTO productYktDTO) {
        if (productYktDTO == null) {
            return ResponseCode.PRODUCT_YKT_ERR;
        }
        String proCode = productYktDTO.getProCode();
        if (StringUtils.isBlank(proCode)) {
            return ResponseCode.PRODUCT_YKT_CODE_NULL;
        }
        String updateUser = productYktDTO.getUpdateUser();
        if (StringUtils.isBlank(updateUser)) {
            return ResponseCode.UPDATE_USER_NULL;
        }
        return ResponseCode.SUCCESS;
    }
    
    /**
     * 批量上/下架一卡通充值产品
     */
    @Override
    public DodopalResponse<Integer> updatePrdProductYktStatus(String proStatus, List<String> proCodes, String updateUser){
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);
        if (StringUtils.isBlank(proStatus) || ProductStatusEnum.getProductStatusByCode(proStatus) == null) {
            response.setCode(ResponseCode.PRODUCT_YKT_STATUS_ERROR);
        }
        if (CollectionUtils.isEmpty(proCodes)) {
            response.setCode(ResponseCode.PRODUCT_YKT_CODE_NULL);
        }
        try {
            int updateresult = productYktService.updatePrdProductYktStatus(proStatus, proCodes, updateUser);
            response.setResponseEntity(updateresult);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    

    /**
     * 基于城市查询公交卡充值产品
     */
    @Override
    public DodopalResponse<List<PrdProductYktDTO>> findAvailableIcdcProductsInCity(String cityId){
        DodopalResponse<List<PrdProductYktDTO>> response = new DodopalResponse<List<PrdProductYktDTO>>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            if (StringUtils.isBlank(cityId)) {
                response.setCode(ResponseCode.PRODUCT_CITYID_NULL);
                return response;
            }else{
                List<PrdProductYkt> productYktList = productYktService.findAvailableIcdcProductsInCity(cityId);
                if (productYktList != null && productYktList.size() > 0) {
                    List<PrdProductYktDTO> resultList = new ArrayList<PrdProductYktDTO>(productYktList.size());
                    for (PrdProductYkt proTemp : productYktList){
                        PrdProductYktDTO proDTO = new PrdProductYktDTO();
                        PropertyUtils.copyProperties(proDTO,proTemp);
                        resultList.add(proDTO);
                    }
                    response.setResponseEntity(resultList);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    
    /**
     * 基于城市查询公交卡充值产品(分页)
     */
    @Override
    public DodopalResponse<DodopalDataPage<PrdProductYktDTO>> findAvailableIcdcProductsInCityByPage(PrdProductYktQueryDTO productYktQueryDTO){
        DodopalResponse<DodopalDataPage<PrdProductYktDTO>> response = new DodopalResponse<DodopalDataPage<PrdProductYktDTO>>();
        response.setCode(ResponseCode.SUCCESS);
        try {
        	PrdProductYktQuery productYktQuery = new PrdProductYktQuery();
            PropertyUtils.copyProperties(productYktQuery, productYktQueryDTO);
            if (productYktQuery.getPage() == null) {
                productYktQuery.setPage(new PageParameter());
            }
            if (StringUtils.isBlank(productYktQuery.getCityId())) {
                response.setCode(ResponseCode.PRODUCT_CITYID_NULL);
                return response;
            }else{
                DodopalDataPage<PrdProductYkt> productYktList = productYktService.findAvailableIcdcProductsInCityByPage(productYktQuery);
                if (productYktList != null) {
                    
                    List<PrdProductYkt> resultList = productYktList.getRecords();
                    List<PrdProductYktDTO> resResultList = null;
                    if (resultList != null && resultList.size() > 0) {
                        resResultList = new ArrayList<PrdProductYktDTO>(resultList.size());
                        for (PrdProductYkt pro : resultList) {
                            PrdProductYktDTO proDTO = new PrdProductYktDTO();
                            PropertyUtils.copyProperties(proDTO,pro);
                            resResultList.add(proDTO);
                        }
                    }
                    PageParameter page =  DodopalDataPageUtil.convertPageInfo(productYktList);
                    DodopalDataPage<PrdProductYktDTO> ddpDTOResult = new DodopalDataPage<PrdProductYktDTO>(page, resResultList);
                    response.setResponseEntity(ddpDTOResult);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    /**
     * 查询商户签约城市公交卡充值产品
     */
    @Override
    public DodopalResponse<List<PrdProductYktDTO>> findAvailableIcdcProductsForMerchant(String merchantNum, String cityId){
        DodopalResponse<List<PrdProductYktDTO>> response = new DodopalResponse<List<PrdProductYktDTO>>();
        response.setCode(ResponseCode.SUCCESS);
        DodopalResponse<List<PrdProductYkt>> productResponse = new DodopalResponse<List<PrdProductYkt>>();
        try {
            if (StringUtils.isBlank(merchantNum)) {
                response.setCode(ResponseCode.PRODUCT_PRO_MERCHANTNUM_NULL);
                return response;
            }else{
                productResponse = productYktService.findAvailableIcdcProductsForMerchant(merchantNum, cityId);
                if (ResponseCode.SUCCESS.equals(productResponse.getCode())) {
                    List<PrdProductYkt> productYktList = productResponse.getResponseEntity();
                    if (productYktList != null && productYktList.size() > 0) {
                        List<PrdProductYktDTO> resultList = new ArrayList<PrdProductYktDTO>(productYktList.size());
                        for (PrdProductYkt proTemp : productYktList){
                            PrdProductYktDTO proDTO = new PrdProductYktDTO();
                            PropertyUtils.copyProperties(proDTO,proTemp);
                            resultList.add(proDTO);
                        }
                        response.setResponseEntity(resultList);
                    }else{
                        productResponse.setCode(ResponseCode.PRODUCT_YKT_ERR);
                    }
                }else{
                    response.setCode(productResponse.getCode());
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    /**
     * 查询商户签约城市公交卡充值产品(分页)
     */
	public DodopalResponse<DodopalDataPage<PrdProductYktDTO>> findAvailableIcdcProductsForMerchantByPage(
			PrdProductYktQueryDTO productYktQueryDTO) {
        DodopalResponse<DodopalDataPage<PrdProductYktDTO>> response = new DodopalResponse<DodopalDataPage<PrdProductYktDTO>>();
        response.setCode(ResponseCode.SUCCESS);
        try {
        	PrdProductYktQuery productYktQuery = new PrdProductYktQuery();
            PropertyUtils.copyProperties(productYktQuery, productYktQueryDTO);
            if (productYktQuery.getPage() == null) {
                productYktQuery.setPage(new PageParameter());
            }
            
            if (StringUtils.isBlank(productYktQueryDTO.getMerCode())) {
                response.setCode(ResponseCode.PRODUCT_PRO_MERCHANTNUM_NULL);
                return response;
            }else{
                DodopalDataPage<PrdProductYkt> productYktList = productYktService.findAvailableIcdcProductsForMerchantByPage(productYktQuery);
                if (productYktList != null) {
                    List<PrdProductYkt> resultList = productYktList.getRecords();
                    List<PrdProductYktDTO> resResultList = null;
                    if (resultList != null && resultList.size() > 0) {
                        resResultList = new ArrayList<PrdProductYktDTO>(resultList.size());
                        for (PrdProductYkt pro : resultList) {
                            PrdProductYktDTO proDTO = new PrdProductYktDTO();
                            PropertyUtils.copyProperties(proDTO,pro);
                            //成本价不足一分算一分
                            if(proDTO.getProPayPrice()==0){
                            	proDTO.setProPayPrice(1);
                            }
                            resResultList.add(proDTO);
                        }
                    }
                    PageParameter page =  DodopalDataPageUtil.convertPageInfo(productYktList);
                    DodopalDataPage<PrdProductYktDTO> ddpDTOResult = new DodopalDataPage<PrdProductYktDTO>(page, resResultList);
                    response.setResponseEntity(ddpDTOResult);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

}
