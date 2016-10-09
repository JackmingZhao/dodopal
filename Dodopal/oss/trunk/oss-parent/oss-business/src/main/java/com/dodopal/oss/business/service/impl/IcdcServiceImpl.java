package com.dodopal.oss.business.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.product.dto.ProductYKTDTO;
import com.dodopal.api.product.dto.ProductYktLimitBatchInfoDTO;
import com.dodopal.api.product.dto.ProductYktLimitInfoDTO;
import com.dodopal.api.product.dto.query.ProductYKTQueryDTO;
import com.dodopal.api.product.dto.query.ProductYktLimitBatchInfoQueryDTO;
import com.dodopal.api.product.dto.query.ProductYktLimitInfoQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.oss.business.bean.ProductYKTBDBean;
import com.dodopal.oss.business.bean.ProductYktLimitBatchInfoBean;
import com.dodopal.oss.business.bean.ProductYktLimitInfoBean;
import com.dodopal.oss.business.bean.query.ProductYKTBDFindBean;
import com.dodopal.oss.business.bean.query.ProductYktLimitBatchInfoFindBean;
import com.dodopal.oss.business.bean.query.ProductYktLimitInfoFindBean;
import com.dodopal.oss.business.dao.IcdcBusinessCityMapper;
import com.dodopal.oss.business.model.BusinessCity;
import com.dodopal.oss.business.service.IcdcService;
import com.dodopal.oss.delegate.IcdcDelegate;

@Service
public class IcdcServiceImpl implements IcdcService {
    
    private final static Logger log = LoggerFactory.getLogger(IcdcServiceImpl.class);

	@Autowired
	private IcdcDelegate icdcDelegate;
	
	@Autowired
	private IcdcBusinessCityMapper icdcBusinessCityMapper;
   
    /**************************************************** 基础信息管理开始 *****************************************************/
    @Override
    public DodopalResponse<DodopalDataPage<ProductYKTBDBean>> findProductYktByPage(ProductYKTBDFindBean queryDto) {
        
        DodopalResponse<DodopalDataPage<ProductYKTBDBean>> response = new DodopalResponse<DodopalDataPage<ProductYKTBDBean>>();
        try {
            
            ProductYKTQueryDTO findDto = new ProductYKTQueryDTO();
            PropertyUtils.copyProperties(findDto, queryDto);
            
            DodopalResponse<DodopalDataPage<ProductYKTDTO>>  getResponse  = icdcDelegate.findProductYktByPage(findDto); 
            
            if (ResponseCode.SUCCESS.equals(getResponse.getCode())) {
                List<ProductYKTBDBean> resResult = new ArrayList<ProductYKTBDBean>();
                ProductYKTBDBean retBean = null;
                for (ProductYKTDTO retDTO : getResponse.getResponseEntity().getRecords()) {
                    retBean = new ProductYKTBDBean();
                    PropertyUtils.copyProperties(retBean, retDTO);
                    resResult.add(retBean);
                }
                PageParameter page = DodopalDataPageUtil.convertPageInfo(getResponse.getResponseEntity());
                DodopalDataPage<ProductYKTBDBean> pages = new DodopalDataPage<ProductYKTBDBean>(page, resResult);
                response.setCode(getResponse.getCode());
                response.setResponseEntity(pages);
            } else {
                response.setCode(getResponse.getCode());
            }
            
        }catch(HessianRuntimeException e){
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    @Override
    public DodopalResponse<List<ProductYKTBDBean>> getProductYktListForExportExcel(ProductYKTBDFindBean queryDto) {
        
        DodopalResponse<List<ProductYKTBDBean>> response = new DodopalResponse<List<ProductYKTBDBean>>();
        try {
            
            ProductYKTQueryDTO findDto = new ProductYKTQueryDTO();
            PropertyUtils.copyProperties(findDto, queryDto);
            
            DodopalResponse<List<ProductYKTDTO>>  getResponse  = icdcDelegate.getProductYktListForExportExcel(findDto); 
            
            if (ResponseCode.SUCCESS.equals(getResponse.getCode())) {
                List<ProductYKTBDBean> resResult = new ArrayList<ProductYKTBDBean>();
                ProductYKTBDBean retBean = null;
                for (ProductYKTDTO retDTO : getResponse.getResponseEntity()) {
                    retBean = new ProductYKTBDBean();
                    PropertyUtils.copyProperties(retBean, retDTO);
                    resResult.add(retBean);
                }
                response.setCode(getResponse.getCode());
                response.setResponseEntity(resResult);
            } else {
                response.setCode(getResponse.getCode());
            }
            
        }catch(HessianRuntimeException e){
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    @Override
    public DodopalResponse<Integer> saveOrUpdateYkt(ProductYKTBDBean productYKTBDBean) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();

        try {
            ProductYKTDTO productYkt = new ProductYKTDTO();
            PropertyUtils.copyProperties(productYkt, productYKTBDBean);

            if (StringUtils.isEmpty(productYKTBDBean.getId())) {
                response = icdcDelegate.addProductYkt(productYkt);
            } else {
                response = icdcDelegate.updateProductYkt(productYkt);
            }
        }
        catch (HessianRuntimeException e) {
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    @Override
    public DodopalResponse<ProductYKTBDBean> findProductYktById(String id) {
        DodopalResponse<ProductYKTBDBean> response = new DodopalResponse<ProductYKTBDBean>();
        try {
            DodopalResponse<ProductYKTDTO> productYKTDTO = icdcDelegate.findProductYktById(id);
            if (ResponseCode.SUCCESS.equals(productYKTDTO.getCode())) {
                ProductYKTBDBean productYKTBDBean = new ProductYKTBDBean();
                PropertyUtils.copyProperties(productYKTBDBean, productYKTDTO.getResponseEntity());
                response.setResponseEntity(productYKTBDBean);
                response.setCode(ResponseCode.SUCCESS);
            } else {
                response.setCode(productYKTDTO.getCode());
            }
        }catch(HessianRuntimeException e){
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }catch (Exception e) {
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response; 
    }
    
    @Override
    public DodopalResponse<Integer> startOrStopYkt(List<String> yktCodes, String activate, String updateUser) {
        DodopalResponse<Integer> result = icdcDelegate.startOrStopYkt(yktCodes, activate, updateUser);
        return result;
    }
    
    /**************************************************** 基础信息管理结束 *****************************************************/
    
    /**************************************************** 额度管理开始 *****************************************************/
    @Override
    public DodopalResponse<DodopalDataPage<ProductYktLimitInfoBean>> findProductYktLimitInfoByPage(ProductYktLimitInfoFindBean queryDto) {
        
        DodopalResponse<DodopalDataPage<ProductYktLimitInfoBean>> response = new DodopalResponse<DodopalDataPage<ProductYktLimitInfoBean>>();
        try {
            
            ProductYktLimitInfoQueryDTO findDto = new ProductYktLimitInfoQueryDTO();
            PropertyUtils.copyProperties(findDto, queryDto);
            
            DodopalResponse<DodopalDataPage<ProductYktLimitInfoDTO>>  getResponse  = icdcDelegate.findProductYktLimitInfoByPage(findDto); 
            
            if (ResponseCode.SUCCESS.equals(getResponse.getCode())) {
                List<ProductYktLimitInfoBean> resResult = new ArrayList<ProductYktLimitInfoBean>();
                ProductYktLimitInfoBean retBean = null;
                for (ProductYktLimitInfoDTO retDTO : getResponse.getResponseEntity().getRecords()) {
                    retBean = new ProductYktLimitInfoBean();
                    PropertyUtils.copyProperties(retBean, retDTO);
                    resResult.add(retBean);
                }
                PageParameter page = DodopalDataPageUtil.convertPageInfo(getResponse.getResponseEntity());
                DodopalDataPage<ProductYktLimitInfoBean> pages = new DodopalDataPage<ProductYktLimitInfoBean>(page, resResult);
                response.setCode(getResponse.getCode());
                response.setResponseEntity(pages);
            } else {
                response.setCode(getResponse.getCode());
            }
            
        }catch(HessianRuntimeException e){
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    @Override
    public DodopalResponse<List<ProductYktLimitInfoBean>> getProductYktLimitListForExportExcel(ProductYktLimitInfoFindBean queryDto) {
        
        DodopalResponse<List<ProductYktLimitInfoBean>> response = new DodopalResponse<List<ProductYktLimitInfoBean>>();
        try {
            
            ProductYktLimitInfoQueryDTO findDto = new ProductYktLimitInfoQueryDTO();
            PropertyUtils.copyProperties(findDto, queryDto);
            
            DodopalResponse<List<ProductYktLimitInfoDTO>>  getResponse  = icdcDelegate.getProductYktLimitListForExportExcel(findDto); 
            
            if (ResponseCode.SUCCESS.equals(getResponse.getCode())) {
                List<ProductYktLimitInfoBean> resResult = new ArrayList<ProductYktLimitInfoBean>();
                ProductYktLimitInfoBean retBean = null;
                for (ProductYktLimitInfoDTO retDTO : getResponse.getResponseEntity()) {
                    retBean = new ProductYktLimitInfoBean();
                    PropertyUtils.copyProperties(retBean, retDTO);
                    resResult.add(retBean);
                }
                response.setCode(getResponse.getCode());
                response.setResponseEntity(resResult);
            } else {
                response.setCode(getResponse.getCode());
            }
            
        }catch(HessianRuntimeException e){
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    @Override
    public DodopalResponse<ProductYktLimitInfoBean> findProductYktLimitInfoById(String id) {
        DodopalResponse<ProductYktLimitInfoBean> response = new DodopalResponse<ProductYktLimitInfoBean>();
        try {
            DodopalResponse<ProductYktLimitInfoDTO> productYktLimitInfoDTO = icdcDelegate.findProductYktLimitInfoById(id);
            if (ResponseCode.SUCCESS.equals(productYktLimitInfoDTO.getCode())) {
                ProductYktLimitInfoBean productYktLimitInfoBean = new ProductYktLimitInfoBean();
                PropertyUtils.copyProperties(productYktLimitInfoBean, productYktLimitInfoDTO.getResponseEntity());
                response.setResponseEntity(productYktLimitInfoBean);
                response.setCode(ResponseCode.SUCCESS);
            } else {
                response.setCode(productYktLimitInfoDTO.getCode());
            }
        }catch(HessianRuntimeException e){
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }catch (Exception e) {
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response; 
    }
    
    @Override
    public DodopalResponse<Integer> saveProductYktLimitInfo(ProductYktLimitInfoBean productYktLimitInfoBean) {

        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            ProductYktLimitInfoDTO productYktLimitInfoDTO = new ProductYktLimitInfoDTO();
            PropertyUtils.copyProperties(productYktLimitInfoDTO, productYktLimitInfoBean);
            response = icdcDelegate.saveProductYktLimitInfo(productYktLimitInfoDTO);
        }
        catch (HessianRuntimeException e) {
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
  
    /**************************************************** 额度管理结束 *****************************************************/

    /**************************************************** 额度批次信息管理开始 *****************************************************/
    @Override
    public DodopalResponse<DodopalDataPage<ProductYktLimitBatchInfoBean>> findProductYktLimitBatchInfoByPage(ProductYktLimitBatchInfoFindBean queryDto) {
        
        DodopalResponse<DodopalDataPage<ProductYktLimitBatchInfoBean>> response = new DodopalResponse<DodopalDataPage<ProductYktLimitBatchInfoBean>>();
        try {
            
            ProductYktLimitBatchInfoQueryDTO findDto = new ProductYktLimitBatchInfoQueryDTO();
            PropertyUtils.copyProperties(findDto, queryDto);
            
            DodopalResponse<DodopalDataPage<ProductYktLimitBatchInfoDTO>>  getResponse  = icdcDelegate.findProductYktLimitBatchInfoByPage(findDto); 
            
            if (ResponseCode.SUCCESS.equals(getResponse.getCode())) {
                List<ProductYktLimitBatchInfoBean> resResult = new ArrayList<ProductYktLimitBatchInfoBean>();
                ProductYktLimitBatchInfoBean retBean = null;
                for (ProductYktLimitBatchInfoDTO retDTO : getResponse.getResponseEntity().getRecords()) {
                    retBean = new ProductYktLimitBatchInfoBean();
                    PropertyUtils.copyProperties(retBean, retDTO);
                    resResult.add(retBean);
                }
                PageParameter page = DodopalDataPageUtil.convertPageInfo(getResponse.getResponseEntity());
                DodopalDataPage<ProductYktLimitBatchInfoBean> pages = new DodopalDataPage<ProductYktLimitBatchInfoBean>(page, resResult);
                response.setCode(getResponse.getCode());
                response.setResponseEntity(pages);
            } else {
                response.setCode(getResponse.getCode());
            }
            
        }catch(HessianRuntimeException e){
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    @Override
    public DodopalResponse<ProductYktLimitBatchInfoBean> findProductYktLimitBatchInfoById(String id) {
        
        DodopalResponse<ProductYktLimitBatchInfoBean> response = new DodopalResponse<ProductYktLimitBatchInfoBean>();
        try {
            
            DodopalResponse<ProductYktLimitBatchInfoDTO>  getResponse  = icdcDelegate.findProductYktLimitBatchInfoById(id); 
            
            if (ResponseCode.SUCCESS.equals(getResponse.getCode())) {
                ProductYktLimitBatchInfoBean retBean = new ProductYktLimitBatchInfoBean();
                PropertyUtils.copyProperties(retBean, getResponse.getResponseEntity());
                response.setCode(getResponse.getCode());
                response.setResponseEntity(retBean);
            } else {
                response.setCode(getResponse.getCode());
            }
            
        }catch(HessianRuntimeException e){
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    
    @Override
    public DodopalResponse<Integer> addProductYktLimitBatchInfo(ProductYktLimitBatchInfoBean productYktLimitBatchInfoBean) {

        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            ProductYktLimitBatchInfoDTO productYktLimitBatchInfoDTO = new ProductYktLimitBatchInfoDTO();
            PropertyUtils.copyProperties(productYktLimitBatchInfoDTO, productYktLimitBatchInfoBean);
            response = icdcDelegate.addProductYktLimitBatchInfo(productYktLimitBatchInfoDTO);
        }
        catch (HessianRuntimeException e) {
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    @Override
    public DodopalResponse<Integer> deleteProductYktLimitBatchInfo(String id) {

        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            response = icdcDelegate.deleteProductYktLimitBatchInfo(id);
        }
        catch (HessianRuntimeException e) {
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    @Override
    public DodopalResponse<Integer> saveProductYktLimitBatchInfo(ProductYktLimitBatchInfoBean productYktLimitBatchInfoBean) {

        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            ProductYktLimitBatchInfoDTO productYktLimitBatchInfoDTO = new ProductYktLimitBatchInfoDTO();
            PropertyUtils.copyProperties(productYktLimitBatchInfoDTO, productYktLimitBatchInfoBean);
            response = icdcDelegate.saveProductYktLimitBatchInfo(productYktLimitBatchInfoDTO);
        }
        catch (HessianRuntimeException e) {
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    @Override
    public DodopalResponse<Integer> aduitYktLimitBatchInfo(ProductYktLimitBatchInfoBean productYktLimitBatchInfoBean) {

        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            ProductYktLimitBatchInfoDTO productYktLimitBatchInfoDTO = new ProductYktLimitBatchInfoDTO();
            PropertyUtils.copyProperties(productYktLimitBatchInfoDTO, productYktLimitBatchInfoBean);
            response = icdcDelegate.auditProductYktLimitBatchInfo(productYktLimitBatchInfoDTO);
        }
        catch (HessianRuntimeException e) {
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    @Override
    public DodopalResponse<Integer> checkYktLimitBatchInfo(ProductYktLimitBatchInfoBean productYktLimitBatchInfoBean) {

        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            ProductYktLimitBatchInfoDTO productYktLimitBatchInfoDTO = new ProductYktLimitBatchInfoDTO();
            PropertyUtils.copyProperties(productYktLimitBatchInfoDTO, productYktLimitBatchInfoBean);
            response = icdcDelegate.checkProductYktLimitBatchInfo(productYktLimitBatchInfoDTO);
        }
        catch (HessianRuntimeException e) {
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    @Override
    public DodopalResponse<List<ProductYktLimitBatchInfoBean>> getProductYktLimitBatchListForExportExcel(ProductYktLimitBatchInfoFindBean queryDto) {

        DodopalResponse<List<ProductYktLimitBatchInfoBean>> response = new DodopalResponse<List<ProductYktLimitBatchInfoBean>>();
        
        try {
            ProductYktLimitBatchInfoQueryDTO findDto = new ProductYktLimitBatchInfoQueryDTO();
            PropertyUtils.copyProperties(findDto, queryDto);
            
            DodopalResponse<List<ProductYktLimitBatchInfoDTO>> getResponse = icdcDelegate.getProductYktLimitBatchListForExportExcel(findDto);
            
            response.setCode(getResponse.getCode());
            
            List<ProductYktLimitBatchInfoBean> lstData = new ArrayList<ProductYktLimitBatchInfoBean>();
            
            if (ResponseCode.SUCCESS.equals(getResponse.getCode())) {
                ProductYktLimitBatchInfoBean retBean = null;
                for (ProductYktLimitBatchInfoDTO retDTO : getResponse.getResponseEntity()) {
                    retBean = new ProductYktLimitBatchInfoBean();
                    PropertyUtils.copyProperties(retBean, retDTO);
                    lstData.add(retBean);
                }
                response.setResponseEntity(lstData);
            }  
        }catch(HessianRuntimeException e){
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("IcdcServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    
    /**************************************************** 额度批次信息管理开始 *****************************************************/
    
    
    /**
     * 通卡公司业务城市选项卡生成(By 拼音首字母)
     * @param param
     * @return
     */
    @Override
    public List<BusinessCity> getBusinessCity(String[] param) {
         List<BusinessCity>  allList = icdcBusinessCityMapper.findAllCityByCityabridge(param);
         List<BusinessCity>  newList = new ArrayList<BusinessCity>();
         List<BusinessCity>  usedList = icdcBusinessCityMapper.findBusinessCityByCityabridge(param);
         Map<String, BusinessCity> usedCityMap = new HashMap<String, BusinessCity>();
         for (BusinessCity city:usedList) {
             usedCityMap.put(city.getCityCode(), city);
         }
         for (BusinessCity city:allList) {
             BusinessCity newCity = new BusinessCity();
             city.setSort(city.getSort().toUpperCase());
             if (usedCityMap.containsKey(city.getCityCode())) {
                 city.setStates("1");
                 city.setYktCode(usedCityMap.get(city.getCityCode()).getYktCode());
             } else {
                 city.setStates("0");
             }
             if (!"0".equals(city.getParentCode()) && !"-1".equals(city.getParentCode())) {
                 newCity = city;
                 newList.add(newCity);
             }
         }
        return newList;
    }

    /**
     * 通卡公司业务城市选项卡生成(By 城市名称模糊查询)
     * @param param
     * @return
     */
    @Override
    public List<BusinessCity> getBusinessCityByName(String cityName) {
         List<BusinessCity>  allList = icdcBusinessCityMapper.findAllCityByName(cityName);
         List<BusinessCity>  newList = new ArrayList<BusinessCity>();
         List<BusinessCity>  usedList = icdcBusinessCityMapper.findBusinessCityByName(cityName);
         Map<String, BusinessCity> usedCityMap = new HashMap<String, BusinessCity>();
         for (BusinessCity city:usedList) {
             usedCityMap.put(city.getCityCode(), city);
         }
         for (BusinessCity city:allList) {
             BusinessCity newCity = new BusinessCity();
             if (usedCityMap.containsKey(city.getCityCode())) {
                 city.setStates("1");
                 city.setYktCode(usedCityMap.get(city.getCityCode()).getYktCode());
             } else {
                 city.setStates("0");
             }
             if (!"0".equals(city.getParentCode()) && !"-1".equals(city.getParentCode())) {
                 newCity = city;
                 newList.add(newCity);
             }
         }
        return newList;
    }

}
