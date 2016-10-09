package com.dodopal.product.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.ProductStatusEnum;
import com.dodopal.common.enums.ProductTypeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.AreaService;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.product.business.constant.ProductConstants;
import com.dodopal.product.business.dao.PrdProductYktMapper;
import com.dodopal.product.business.dao.ProductYKTMapper;
import com.dodopal.product.business.dao.ProductYktCityInfoMapper;
import com.dodopal.product.business.model.PrdProductYkt;
import com.dodopal.product.business.model.PrdProductYktInfoForIcdcRecharge;
import com.dodopal.product.business.model.query.PrdProductYktQuery;
import com.dodopal.product.business.service.PrdProductYktService;
import com.dodopal.product.delegate.MerchantDelegate;

@Service
public class PrdProductYktServiceImpl implements PrdProductYktService{
    
    @Autowired
    private PrdProductYktMapper mapper; 
    @Autowired
    private ProductYKTMapper productYktMapper; 
    @Autowired
    private AreaService areaService;
    @Autowired
    private MerchantDelegate merchantDelegate;
    @Autowired
    private ProductYktCityInfoMapper pmapper; 
    
    /**
     * 根据条件查询一卡通充值产品列表(分页)
     */
    @Override
    @Transactional(readOnly = true)
    public DodopalDataPage<PrdProductYkt> findPrdProductYktByPage(PrdProductYktQuery productYktQuery){
        List<PrdProductYkt> result = mapper.findPrdProductYktListPage(productYktQuery);
        DodopalDataPage<PrdProductYkt> pages = new DodopalDataPage<PrdProductYkt>(productYktQuery.getPage(), result);
        return pages;
    }
    
    /**
     * 获取一卡通充值产品导出列表信息
     */
    @Override
    public DodopalResponse<List<PrdProductYkt>> getIcdcPrductListForExportExcel(PrdProductYktQuery productYktQuery) {
        DodopalResponse<List<PrdProductYkt>> response = new DodopalResponse<List<PrdProductYkt>>();
        response.setCode(ResponseCode.SUCCESS);
        
        // 查询导出数据是否超过最大限制
        int resultSize = mapper.getCountForIcdcPrductExportExcel(productYktQuery);
        if (resultSize > ExcelUtil.EXPORT_MAX_COUNT){
            response.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
            return response;
        }
        
        // 查询结果集
        List<PrdProductYkt> result = mapper.getListForIcdcPrductExportExcel(productYktQuery);
        response.setResponseEntity(result);
        
        return response;
    }
    
    /**
     * 根据产品编号查询一卡通充值产品信息
     */
    @Override
    @Transactional(readOnly = true)
    public PrdProductYkt findPrdProductYktByProCode(String proCode){
        PrdProductYkt productYkt = mapper.findPrdProductYktByProCode(proCode);
        if(productYkt==null){
            return null;
        }
        return productYkt;
     }
    
    /**
     * 新增一卡通充值产品
     */
    @Override
    @Transactional
    public int savePrdProductYkt(PrdProductYkt productYkt){
        String proCode = getProCode();//获取一卡通产品sequences
        String proName = ProductConstants.EMPTY;
        if (ProductTypeEnum.STANDARD.getCode().equals(productYkt.getProType())) {
            //设置默认一卡通产品名称
            proName = getProName(productYkt.getCityName(),productYkt.getProPrice());
        } else if (ProductTypeEnum.USER_DEFINED.getCode().equals(productYkt.getProType())) {
            //设置自定义一卡通产品名称
            proName = productYkt.getCityName()+ProductConstants.USER_DEFINED;
        }
        String createUser = productYkt.getCreateUser();
        productYkt.setProCode(proCode);
        productYkt.setProName(proName);
        productYkt.setCreateUser(createUser);
        //添加一卡通充值产品
        int saveresult  = mapper.insertPrdProductYkt(productYkt);
        //添加一卡通充值产品版本号
        if(!pmapper.checkExistVersion(productYkt.getCityId())){
            pmapper.addProversion(productYkt.getCityId());
        }else{
            pmapper.updateProversion(productYkt.getCityId());
        }
        return saveresult;
    }
    
    
    /**
     * 检查一卡通充值产品是否存在
     */
    @Override
    @Transactional(readOnly = true)
    public boolean checkExist(PrdProductYkt productYkt) {
        return mapper.checkExist(productYkt);
    }
    
    /**
     * 获取一卡通产品sequences
     */
    @Override
    @Transactional(readOnly = true)
    public String getProCode() {
        StringBuffer sb = new StringBuffer();
        //2位产品大类
        sb.append(ProductConstants.procuct_ykt_type);
        //数据库截取10位sequence
        String seq = mapper.getProCodeSeq();
        sb.append(seq.substring(15,20));
        return sb.toString();
    }
    
    /**
     * 获取一卡通产品名称
     */
    @Override
    @Transactional(readOnly = true)
    public String getProName(String cityName,double proPrice){
        StringBuffer sb = new StringBuffer();
        //一卡通业务城市名称
        //String cityName = mapper.getCityName(cityId);
        sb.append(cityName);
        sb.append(proPrice/100);
        sb.append("元");
        return sb.toString();
    }
    
    /**
     * 修改一卡通充值产品
     */
    @Override
    @Transactional
    public int updatePrdProductYkt(PrdProductYkt productYkt){
        int updateresult  = mapper.updatePrdProductYkt(productYkt);
        return updateresult;
    }
    
    
   /**
    * 批量上/下架一卡通充值产品
    */
    @Override
    @Transactional
    public int updatePrdProductYktStatus(String proStatus, List<String> proCodes, String updateUser){
        int updateresult = mapper.updatePrdProductYktStatus(proStatus, proCodes, updateUser);
        //修改产品版本号
        for(String codeTmp:proCodes)  
        {  
            PrdProductYkt productYkt = this.findPrdProductYktByProCode(codeTmp);
            if(productYkt!=null){
                if(!pmapper.checkExistVersion(productYkt.getCityId())){
                    pmapper.addProversion(productYkt.getCityId());
                }else{
                    pmapper.updateProversion(productYkt.getCityId());
                }
            }
        }  
        return updateresult;
    }
    
    /**
     * 基于城市查询公交卡充值产品
     */
    @Override
    @Transactional
    public List<PrdProductYkt> findAvailableIcdcProductsInCity(String cityId){
        return mapper.findAvailableIcdcProductsInCity(cityId);
    }
    
    /**
     * 基于城市查询公交卡充值产品(分页)
     */
    @Override
    @Transactional
    public DodopalDataPage<PrdProductYkt> findAvailableIcdcProductsInCityByPage(PrdProductYktQuery productYktQuery){
        List<PrdProductYkt> result = mapper.findAvailableIcdcProductsInCityByPage(productYktQuery);
        DodopalDataPage<PrdProductYkt> pages = new DodopalDataPage<PrdProductYkt>(productYktQuery.getPage(), result);
        return pages;
    }
    
    
    /**
     * 查询商户签约城市公交卡充值产品
     */
    @Override
    @Transactional
    public DodopalResponse<List<PrdProductYkt>>  findAvailableIcdcProductsForMerchant(String merchantNum, String cityId){
        DodopalResponse<MerchantDTO> resultResponse = new DodopalResponse<MerchantDTO>();
        DodopalResponse<List<PrdProductYkt>> productResponse = new DodopalResponse<List<PrdProductYkt>>();
        productResponse.setCode(ResponseCode.SUCCESS);
        try{
            resultResponse = merchantDelegate.findMerchantByCode(merchantNum);
            if (ResponseCode.SUCCESS.equals(resultResponse.getCode())) {
                MerchantDTO merchantDTO = resultResponse.getResponseEntity();
                String merType = merchantDTO.getMerType();//取到商户类型
                if (MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType)) {//连锁直营网点商户的产品取自上一级父类的产品
                    merchantNum = merchantDTO.getMerParentCode();
                }
                String yikcode =  productYktMapper.getYktInfoByBusinessCityCode(cityId).getYktCode();
                List<PrdProductYkt> productYktList = mapper.findAvailableIcdcProductsForMerchant(merchantNum, cityId,yikcode);
                if (productYktList != null && productYktList.size() > 0) {
                    productResponse.setResponseEntity(productYktList);
                }else{
                    productResponse.setCode(ResponseCode.PRODUCT_YKT_ERR);
                }
            }else{
                productResponse.setCode(resultResponse.getCode());
            }
        }catch(HessianRuntimeException e){
            throw new DDPException(ResponseCode.PRODUCT_CALL_USERS_ERROR);
        }catch (Exception e) {
            e.printStackTrace();
            throw new DDPException(ResponseCode.SYSTEM_ERROR);
        }
        return productResponse;
    }
    
    
    /**
     * 查询商户签约城市公交卡充值产品
     */
    @Override
    @Transactional
    public DodopalDataPage<PrdProductYkt> findAvailableIcdcProductsForMerchantByPage(PrdProductYktQuery productYktQuery){
        List<PrdProductYkt> result = mapper.findAvailableIcdcProductsForMerchantByPage(productYktQuery);
        DodopalDataPage<PrdProductYkt> pages = new DodopalDataPage<PrdProductYkt>(productYktQuery.getPage(), result);
        return pages;
    }
    

    /**
     * 一卡通充值生单接口调用__检验公交卡充值产品合法性
     */
    @Override
    public DodopalResponse<PrdProductYktInfoForIcdcRecharge> validateProductForIcdcRecharge(String proCode) {
        DodopalResponse<PrdProductYktInfoForIcdcRecharge> response = new DodopalResponse<PrdProductYktInfoForIcdcRecharge>();
        response.setCode(ResponseCode.SUCCESS);
        
        PrdProductYktInfoForIcdcRecharge info = mapper.getProductInfoForIcdcRecharge(proCode);
        
        // 判断产品是否存在
        if (info == null || !ProductStatusEnum.ENABLE.getCode().equals(info.getProStatus())) {
            // 该产品已下架，请重新选择产品。
            response.setCode(ResponseCode.PRODUCT_CHECK_PRODUCT_STATUS_DISABLE);
            return response;
        }
        
        Area area =  areaService.findCityByCityCode(info.getCityCode());
        if (area == null) {
            // 城市尚未启用，请联系客服人员。
            response.setCode(ResponseCode.PRODUCT_CHECK_YKT_STATUS_DISABLE);
            return response;
        }
        
        info.setCityName(area.getCityName());
        response.setResponseEntity(info);
        return response;
    }
    
    /**
     * 根据城市获取该城市的自定义产品信息
     */
    @Override
    public DodopalResponse<PrdProductYktInfoForIcdcRecharge> getProductInfoByCityId(String cityId) {
        DodopalResponse<PrdProductYktInfoForIcdcRecharge> response = new DodopalResponse<PrdProductYktInfoForIcdcRecharge>();
        response.setCode(ResponseCode.SUCCESS);
        
        PrdProductYktInfoForIcdcRecharge info = mapper.getProductInfoByCityId(cityId);
        
        // 判断产品是否存在
        if (info == null) {
            // 该产品已下架，请重新选择产品。
            response.setCode(ResponseCode.PRODUCT_CHECK_PRODUCT_STATUS_DISABLE);
            return response;
        }
        
        Area area =  areaService.findCityByCityCode(cityId);
        if (area == null) {
            // 城市尚未启用，请联系客服人员。
            response.setCode(ResponseCode.PRODUCT_CHECK_YKT_STATUS_DISABLE);
            return response;
        }
        
        info.setCityName(area.getCityName());
        response.setResponseEntity(info);
        return response;
    }

}
