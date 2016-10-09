package com.dodopal.product.business.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.MerClassifyEnum;
import com.dodopal.common.enums.MerPropertyEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.OpenSignEnum;
import com.dodopal.common.enums.ProductStatusEnum;
import com.dodopal.common.enums.ProductTypeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.AreaService;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.product.business.constant.ProductConstants;
import com.dodopal.product.business.dao.ProductYKTMapper;
import com.dodopal.product.business.dao.ProductYktCityInfoMapper;
import com.dodopal.product.business.model.PrdProductYkt;
import com.dodopal.product.business.model.ProductYKT;
import com.dodopal.product.business.model.ProductYktLimitInfo;
import com.dodopal.product.business.model.query.ProductYKTQuery;
import com.dodopal.product.business.service.PrdProductYktService;
import com.dodopal.product.business.service.ProductYKTService;
import com.dodopal.product.business.service.ProductYktLimitInfoService;
import com.dodopal.product.delegate.MerchantDelegate;
import com.dodopal.product.delegate.ProductYktDelegate;

@Service
public class ProductYKTServiceImpl implements ProductYKTService {
    
    @Autowired
    private ProductYktLimitInfoService productYktLimitInfoService;
    
    @Autowired
    private PrdProductYktService prdProductYktService;
    
    @Autowired
    private AreaService areaService;
    
    @Autowired
    private ProductYktDelegate productYktDelegate;
    
    @Autowired
    private ProductYKTMapper productYKTMapper;
    
    @Autowired
    private ProductYktCityInfoMapper productYktCityInfoMapper;
    
    @Autowired
    private MerchantDelegate  merchantDelegate;
    
    @Override
    @Transactional(readOnly = true)
    public DodopalDataPage<ProductYKT> findProductYktByPage(ProductYKTQuery query){
        List<ProductYKT> result = productYKTMapper.findProductYktByPage(query);
        List<ProductYKT> yktCityInfoResult = productYktCityInfoMapper.findProductYktCityInfo(query);
        Map<String, String> cityMap = new HashMap<String, String>();
        
        for (ProductYKT dto:yktCityInfoResult) {
            if (!cityMap.containsKey(dto.getYktCode())) {
                cityMap.put(dto.getYktCode(), dto.getBusinessCityName());
            } else {
                StringBuffer sbBuffer = new StringBuffer();
                sbBuffer.append(cityMap.get(dto.getYktCode()));
                sbBuffer.append(ProductConstants.COMMA);
                sbBuffer.append(dto.getBusinessCityName());
                cityMap.put(dto.getYktCode(), sbBuffer.toString());
            }
        }
        
        for (ProductYKT dto:result) {
            dto.setBusinessCityName(cityMap.get(dto.getYktCode()));
           }
        
        DodopalDataPage<ProductYKT> pages = new DodopalDataPage<ProductYKT>(query.getPage(), result);
        return pages;
    }

    @Override
    public DodopalResponse<List<ProductYKT>> getProductYktListForExportExcel(ProductYKTQuery query) {
        
        DodopalResponse<List<ProductYKT>> response = new DodopalResponse<List<ProductYKT>>();
        response.setCode(ResponseCode.SUCCESS);
        
        //  检查导出数据是否超过限制列数
        int exportCount = productYKTMapper.getCountForProductYktExportExcel(query);
        if (exportCount > ExcelUtil.EXPORT_MAX_COUNT) {
            response.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
            return response;
        }
        
        //  获取导出数据
        List<ProductYKT> list = productYKTMapper.getListForProductYktExportExcel(query);
        List<ProductYKT> yktCityInfoResult = productYktCityInfoMapper.findProductYktCityInfo(query);
        Map<String, String> cityMap = new HashMap<String, String>();
        
        for (ProductYKT dto:yktCityInfoResult) {
            if (!cityMap.containsKey(dto.getYktCode())) {
                cityMap.put(dto.getYktCode(), dto.getBusinessCityName());
            } else {
                StringBuffer sbBuffer = new StringBuffer();
                sbBuffer.append(cityMap.get(dto.getYktCode()));
                sbBuffer.append(ProductConstants.COMMA);
                sbBuffer.append(dto.getBusinessCityName());
                cityMap.put(dto.getYktCode(), sbBuffer.toString());
            }
        }
        
        for (ProductYKT dto:list) {
            dto.setBusinessCityName(cityMap.get(dto.getYktCode()));
           }
        
        response.setResponseEntity(list);
        return response;
    }
    
    @Override
    @Transactional(readOnly = true)
    public ProductYKT findProductYktById(String id) {
        ProductYKT productYKT = null;
        if(StringUtils.isNotBlank(id)){
            List<ProductYKT> result = productYKTMapper.findProductYktById(id);
            StringBuffer cityNameBuffer = new StringBuffer();
            StringBuffer cityCodeBuffer = new StringBuffer();
            for (ProductYKT dto:result) {
                productYKT = dto;
                cityNameBuffer.append(ProductConstants.COMMA);
                cityNameBuffer.append(dto.getBusinessCityName());
                cityCodeBuffer.append(ProductConstants.COMMA);
                cityCodeBuffer.append(dto.getBusinessCityId());
            }
            String cityName = cityNameBuffer.toString().substring(1);
            String cityCode = cityCodeBuffer.toString().substring(1);
            productYKT.setBusinessCityName(cityName);
            productYKT.setBusinessCityId(cityCode);
            return productYKT;
        }
        return productYKT;
    }

    @Override
    @Transactional
    public int batchUpdateYktActivate(String activate, List<String> yktCodes, String updateUser) {
        ProductYKT productYKT = new ProductYKT();
        productYKT.setActivate(activate);
        productYKT.setUpdateUser(updateUser);
        int num = productYKTMapper.batchUpdateYktActivate(productYKT, yktCodes);
        return num;
    }

    @Override
    @Transactional
    public int addProductYkt(ProductYKT productYKT) {
        
        // 新添加通卡公司基础信息
        int num = productYKTMapper.addProductYkt(productYKT);
        String[] BusinessCitys = productYKT.getBusinessCityId().split(ProductConstants.COMMA);        
        List<ProductYKT> yktCityRelationList = new ArrayList<ProductYKT>();
        for (String cityId : BusinessCitys) {
            ProductYKT productYkt = new ProductYKT();
            productYkt.setYktCode(productYKT.getYktCode());
            productYkt.setCityId(cityId);
            productYkt.setCreateUser(productYKT.getCreateUser());
            yktCityRelationList.add(productYkt);
        }
        // 新添加通卡公司城市关系表
        productYktCityInfoMapper.addYktCityRelationBatch(yktCityRelationList);
        
        // 新增通卡公司时，同时新增一条通卡公司额度信息
        ProductYktLimitInfo limitInfo = new ProductYktLimitInfo();
        limitInfo.setYktCode(productYKT.getYktCode());
        limitInfo.setCreateUser(productYKT.getCreateUser());
        productYktLimitInfoService.addProductYktLimitInfo(limitInfo);
        
        // 新增通卡公司的同时，如果开通了一卡通充值业务，需要自动为该通卡公司创建公交卡充值产品
        if (OpenSignEnum.OPENED.getCode().equals(productYKT.getYktIsRecharge())) {
            this.creatProduct(productYKT, "0");
        }

        // 通卡公司开通了一卡通支付业务.创建支付方式
        if (OpenSignEnum.OPENED.getCode().equals(productYKT.getYktIsPay())) {
            this.createIcdcPay(productYKT);
        }
        
        //添加供应商商户信息
        MerchantDTO merchantDTO = new MerchantDTO();
        MerchantUserDTO merUserdDTO = new MerchantUserDTO();
        merchantDTO.setMerName(productYKT.getYktName()); //商户名称 =通卡公司名称
        merchantDTO.setMerType(MerTypeEnum.PROVIDER.getCode());//商户类型 = 供应商
        merchantDTO.setMerClassify(MerClassifyEnum.OFFICIAL.getCode());//商户分类 = 正式商户 merClassify
        merchantDTO.setMerProperty(MerPropertyEnum.STANDARD.getCode());//商户属性 = 标准商户 merProperty
        merUserdDTO.setMerUserName(DDPStringUtil.getRandomLowerAZ(4)+productYKT.getYktCode());//用户名 = 4位随机英文字母 + 通卡公司code
        if(StringUtils.isNotBlank(productYKT.getYktLinkUser())){
            merchantDTO.setMerLinkUser(productYKT.getYktLinkUser());//联系人 = 通卡公司名称
        }else{
            merchantDTO.setMerLinkUser(productYKT.getYktName());//联系人 = 通卡公司名称
        }
       if(StringUtils.isNotBlank(productYKT.getYktMobile())){
           merchantDTO.setMerLinkUserMobile(productYKT.getYktMobile());//手机号 = 通卡公司code（后台不做手机号验证）
       }else{
           merchantDTO.setMerLinkUserMobile(productYKT.getYktCode());//手机号 = 通卡公司code（后台不做手机号验证）
       }
       
        merchantDTO.setActivate(ActivateEnum.ENABLE.getCode());//启用标识 = 启用 
        merchantDTO.setMerPro(productYKT.getProvinceId());//省份
        merchantDTO.setMerCity(productYKT.getCityId());//城市
        merchantDTO.setMerAdds(productYKT.getYktAddress());//详细地址 = 通卡录入的详细地址 merAdds
        merchantDTO.setCreateUser(productYKT.getCreateUser());//创建人
        merchantDTO.setMerchantUserDTO(merUserdDTO);
        
        DodopalResponse<String> resp =  merchantDelegate.addProviderRegister(merchantDTO);
        if(!ResponseCode.SUCCESS.equals(resp.getCode())){
           throw new DDPException(resp.getCode()); 
        }
        // 产品库表添加商户号
        productYKT.setMerCode(resp.getResponseEntity());
        num =productYKTMapper.updatePrdMerCode(productYKT);
        return num;
    }

    @Override
    @Transactional
    public int updateProductYkt(ProductYKT productYKT) {
        
        // 更新通卡公司基本信息
        int num = productYKTMapper.updateProductYkt(productYKT);
        
        // 删除已存在的通卡公司业务城市的关系
        productYktCityInfoMapper.moveYktCityRelationBatch(productYKT.getYktCode());
        
        // 取得通卡公司业务城市code数组
        String[] newCitys= productYKT.getBusinessCityId().split(ProductConstants.COMMA);
        
        List<ProductYKT> yktCityRelationList = new ArrayList<ProductYKT>();
        for (String cityId:newCitys) {
            ProductYKT addProductYKT = new ProductYKT();
            addProductYKT.setYktCode(productYKT.getYktCode());
            addProductYKT.setCityId(cityId);
            addProductYKT.setCreateUser(productYKT.getUpdateUser());
            yktCityRelationList.add(addProductYKT);
        }
       productYktCityInfoMapper.addYktCityRelationBatch(yktCityRelationList);
        
       // 通卡公司开通一卡通充值业务，需要自动为该通卡公司创建公交卡充值产品
       if (OpenSignEnum.OPENED.getCode().equals(productYKT.getYktIsRecharge())) {
           this.creatProduct(productYKT,"1");
       }
       
       // 通卡公司开通了一卡通支付业务.创建支付方式
       if (OpenSignEnum.OPENED.getCode().equals(productYKT.getYktIsPay())) {
           this.createIcdcPay(productYKT);
       }
       
       //添加供应商商户信息
       MerchantDTO merchantDTO = new MerchantDTO();
       MerchantUserDTO merUserdDTO = new MerchantUserDTO();
       merchantDTO.setMerName(productYKT.getYktName()); //商户名称 =通卡公司名称
       merUserdDTO.setMerUserName(DDPStringUtil.getRandomLowerAZ(4)+productYKT.getYktCode());//用户名 = 4位随机英文字母 + 通卡公司code
       if(StringUtils.isNotBlank(productYKT.getYktLinkUser())){
           merchantDTO.setMerLinkUser(productYKT.getYktLinkUser());//如果为空 联系人 = 一卡通联系人
       }else{
           merchantDTO.setMerLinkUser(productYKT.getYktName());//联系人 = 通卡公司的名称
       }
       if(StringUtils.isNotBlank(productYKT.getYktMobile())){
           merchantDTO.setMerLinkUserMobile(productYKT.getYktMobile());//如果为空 手机号 = 联系人手机号码
       }else{
           merchantDTO.setMerLinkUserMobile(productYKT.getYktCode());//如果为空 手机号 = 通卡公司code（后台不做手机号验证）
       }
    
       merchantDTO.setActivate(ActivateEnum.ENABLE.getCode());//启用标识 = 启用 
       merchantDTO.setMerPro(productYKT.getProvinceId());//省份
       merchantDTO.setMerCity(productYKT.getCityId());//城市
       merchantDTO.setMerAdds(productYKT.getYktAddress());//详细地址 = 通卡录入的详细地址 merAdds
       merchantDTO.setUpdateUser(productYKT.getUpdateUser());//创建人
       merchantDTO.setMerCode(productYKT.getMerCode());//商户号
       merchantDTO.setMerchantUserDTO(merUserdDTO);
       
       DodopalResponse<String> resp =  merchantDelegate.upProviderRegister(merchantDTO);
       if(!ResponseCode.SUCCESS.equals(resp.getCode())){
          throw new DDPException(resp.getCode()); 
       }
       
        return num;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkYktNameExsit(ProductYKT productYKT) {
        boolean result = false;
        int count = productYKTMapper.getYktCountByName(productYKT);
        if (count>0) {
            result = true;
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkYktCodeExsit(ProductYKT productYKT) {
        boolean result = false;
        int count = productYKTMapper.getYktCountByCode(productYKT);
        if (count>0) {
            result = true;
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkYktBusinessCityExsit(String yktCode, String[] cityIds) {
        boolean result = false;
        int count = productYktCityInfoMapper.getYktCityRelationCountByCityIds(yktCode, cityIds);
        if (count>0) {
            result = true;
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductYKT> getIsRechargeYktMap(String activate) {
        List<ProductYKT> yktList = new ArrayList<ProductYKT>();
        yktList = productYKTMapper.getIsRechargeYktMap(activate);
        return yktList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductYKT> getBusinessCityByYktCode(String yktCode) {
        List<ProductYKT> cityList = new ArrayList<ProductYKT>();
        cityList = productYktCityInfoMapper.getBusinessCityByYktCode(yktCode);
        return cityList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductYKT> getAllYktBusinessRateList() {
        List<ProductYKT> result = productYKTMapper.getAllYktBusinessRateList();
        return result;
    }

    @Override
    @Transactional
    public DodopalResponse<Boolean> icdcPayCreate(List<Map<String, Object>> list) {
        DodopalResponse<Boolean> response = productYktDelegate.icdcPayCreate(list);
        return response;
    }
    

    /**
     * 通卡公司,如果开通了一卡通充值业务，需要自动为该通卡公司创建公交卡充值产品
     */
    @Transactional
    private void creatProduct(ProductYKT productYKT, String type){
        PrdProductYkt productYkt = new PrdProductYkt();
        productYkt.setYktCode(productYKT.getYktCode());
        productYkt.setYktName(productYKT.getYktName());
        productYkt.setProStatus(ProductStatusEnum.ENABLE.getCode());
        if ("0".equals(type)) {
            productYkt.setCreateUser(productYKT.getCreateUser());
        } else if ("1".equals(type)) {
            productYkt.setCreateUser(productYKT.getUpdateUser());
        }
        String[] businessCitys = productYKT.getBusinessCityId().split(ProductConstants.COMMA);
        for (String cityId:businessCitys) {
            String cityName = areaService.findCityByCityCode(cityId).getCityName();
            productYkt.setCityId(cityId);
            productYkt.setCityName(cityName);
            // 新增标准产品
            int[] cardDenomination = ProductConstants.PRODUCT_STANDARD_CARD_DENOMINATION;
            for (int proPrice:cardDenomination) {
                productYkt.setProPrice(proPrice*100);
                productYkt.setProType(ProductTypeEnum.STANDARD.getCode());
                if (!prdProductYktService.checkExist(productYkt)) {
                    prdProductYktService.savePrdProductYkt(productYkt);
                }
            }
            // 新增一条自定义产品
            productYkt.setProPrice(ProductConstants.PRODUCT__USER_DEFINED_CARD_DENOMINATION);
            productYkt.setProType(ProductTypeEnum.USER_DEFINED.getCode());
            if (!prdProductYktService.checkExist(productYkt)) {
                prdProductYktService.savePrdProductYkt(productYkt);
            }
        }
    }
    
    
    /**
     * 通卡公司,如果开通了一卡通支付业务 ,创建支付方式
     */
    @Transactional(readOnly = true)
    private void createIcdcPay(ProductYKT productYKT){
        
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();

        String[] businessCitys = productYKT.getBusinessCityId().split(ProductConstants.COMMA);

        String yktName = productYKT.getYktName();
        if (StringUtils.isBlank(yktName)) {
            yktName = productYKTMapper.getYktInfoByYktCode(productYKT.getYktCode()).getYktName();
        }
        
        for (String cityCode:businessCitys) {
            Map<String, Object>  map = new HashMap<String, Object>();
            String cityName = ProductConstants.EMPTY;
            Area area = areaService.findCityByCityCode(cityCode);
            if (area != null) {
                cityName = area.getCityName();
            }
            map.put(ProductConstants.CREAT_ICDCPAY_MAP_KEY_CITYCODE, cityCode);
            map.put(ProductConstants.CREAT_ICDCPAY_MAP_KEY_CITYNAME, cityName);
            map.put(ProductConstants.CREAT_ICDCPAY_MAP_KEY_RATE, productYKT.getYktPayRate());
            map.put("bankGatewayType", productYKT.getYktCode());
            map.put("bankGatewayName", yktName);
            list.add(map);
        }
        this.icdcPayCreate(list);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductYKT getYktInfoByBusinessCityCode(String cityCode) {
        ProductYKT productYKT = productYKTMapper.getYktInfoByBusinessCityCode(cityCode);
        return productYKT;
    }

	@Override
	public boolean CheckProversionChange(String cityCode, String proversion) {
		if(productYktCityInfoMapper.getProversion(cityCode,proversion)==0){
			return false;
		}
		return true;
	}
	public String getProversionByCityCode(String cityCode) {
		return productYktCityInfoMapper.getProversionByCityCode(cityCode);
	}

	/**
	 * 一卡通充值__检验通卡合法性（充值业务的停启用状态、通卡系统的维护时间）
	 */
    @Override
    public DodopalResponse<ProductYKT> validateYktServiceNormalForIcdcRecharge(String yktCode) {
        DodopalResponse<ProductYKT> response = new DodopalResponse<ProductYKT>();
        
        ProductYKT productYkt = productYKTMapper.getYktInfoByYktCode(yktCode);

        // 1，检验通卡公司是否处于启用状态,相关充值业务是否处于开通状态
        if (productYkt == null || !ActivateEnum.ENABLE.getCode().equals(productYkt.getActivate()) 
            || !OpenSignEnum.OPENED.getCode().equals(productYkt.getYktIsRecharge())) {

            // 城市尚未启用，请联系客服人员
            response.setCode(ResponseCode.PRODUCT_CHECK_YKT_STATUS_DISABLE);
            return response;
        }

        // 2，检验服务器时间是否处于充值限制时间段以内（时间段内，不允许做充值业务）
        boolean sign = false;// 处于维护时间段内标识，默认false:不处于时间段内
        String rechargeLimitStartTime = productYkt.getYktRechargeLimitStartTime();// 充值限制开始时间
        String rechargeLimitEndTime = productYkt.getYktRechargeLimitEndTime();// 充值限制结束时间
        if (StringUtils.isNotBlank(rechargeLimitStartTime) && StringUtils.isNotBlank(rechargeLimitEndTime)) {

            // 服务器系统当前时间(格式：HH:mm:ss)
            String currentTime = DateUtils.getCurrDate(DateUtils.DATE_FORMAT_HHMMSS_STR);
            
            // 判断服务器当前时间是否处于充值限制时间段以内，设置sign值
            if (DateUtils.timeSub(rechargeLimitStartTime, rechargeLimitEndTime, DateUtils.DATE_FORMAT_HHMMSS_STR) > 0) {
                if (DateUtils.timeSub(rechargeLimitStartTime, currentTime, DateUtils.DATE_FORMAT_HHMMSS_STR) >= 0 
                    && DateUtils.timeSub(currentTime, rechargeLimitEndTime, DateUtils.DATE_FORMAT_HHMMSS_STR) >= 0) {
                    sign = true;
                }
            } else if (DateUtils.timeSub(rechargeLimitStartTime, rechargeLimitEndTime, DateUtils.DATE_FORMAT_HHMMSS_STR) == 0) {
                if (DateUtils.timeSub(rechargeLimitStartTime, currentTime, DateUtils.DATE_FORMAT_HHMMSS_STR) == 0) {
                    sign = true;
                }
            } else {
                boolean repairSgin1 = false;
                if (DateUtils.timeSub(rechargeLimitStartTime, currentTime, DateUtils.DATE_FORMAT_HHMMSS_STR) >= 0 
                    && DateUtils.timeSub(currentTime, DateUtils.DAY_TIME_END, DateUtils.DATE_FORMAT_HHMMSS_STR) >= 0) {
                    repairSgin1 = true;
                }
                boolean repairSgin2 = false;
                if (DateUtils.timeSub(DateUtils.DAY_TIME_START, currentTime, DateUtils.DATE_FORMAT_HHMMSS_STR) >= 0 
                    && DateUtils.timeSub(currentTime, rechargeLimitEndTime, DateUtils.DATE_FORMAT_HHMMSS_STR) >= 0) {
                    repairSgin2 = true;
                }
                sign = repairSgin1 || repairSgin2;
            }
        }

        // 当服务器当前时间处于充值限制时间段内，返回错误码。不允许做充值业务
        if (sign) {
            response.setCode(ResponseCode.PRODUCT_CHECK_YKT_SERVICE_REPAIRING);// 通卡公司系统维护中
            response.setMessage(response.getMessage()+"（"+rechargeLimitStartTime+"至"+rechargeLimitEndTime+"）");
            return response;
        }
        
        response.setCode(ResponseCode.SUCCESS);
        response.setResponseEntity(productYkt);
        return response;
    }
    
    /**
     * 一卡通消费__检验通卡合法性（消费业务的停启用状态、通卡系统的维护时间）
     */
    @Override
    public DodopalResponse<ProductYKT> validateYktServiceNormalForIcdcConsume(String yktCode) {
        DodopalResponse<ProductYKT> response = new DodopalResponse<ProductYKT>();
        
        ProductYKT productYkt = productYKTMapper.getYktInfoByYktCode(yktCode);

        // 1，检验通卡公司是否处于启用状态,相关充值消费业务是否处于开通状态
        if (productYkt == null || !ActivateEnum.ENABLE.getCode().equals(productYkt.getActivate()) 
            || !OpenSignEnum.OPENED.getCode().equals(productYkt.getYktIsPay())) {

            // 城市尚未启用，请联系客服人员
            response.setCode(ResponseCode.PRODUCT_CHECK_YKT_STATUS_DISABLE);
            return response;
        }

        // 2，检验服务器时间是否处于消费限制时间段以内（时间段内，不允许做消费业务）
        boolean sign = false;// 处于维护时间段内标识，默认false:不处于时间段内
        String consumeLimitStartTime = productYkt.getYktConsumeLimitStartTime();// 消费限制开始时间
        String consumeLimitEndTime = productYkt.getYktConsumeLimitEndTime();// 消费限制结束时间
        if (StringUtils.isNotBlank(consumeLimitStartTime) && StringUtils.isNotBlank(consumeLimitEndTime)) {

            // 服务器系统当前时间(格式：HH:mm:ss)
            String currentTime = DateUtils.getCurrDate(DateUtils.DATE_FORMAT_HHMMSS_STR);
            
            // 判断服务器当前时间是否处于消费限制时间段内，设置sign值
            if (DateUtils.timeSub(consumeLimitStartTime, consumeLimitEndTime, DateUtils.DATE_FORMAT_HHMMSS_STR) > 0) {
                if (DateUtils.timeSub(consumeLimitStartTime, currentTime, DateUtils.DATE_FORMAT_HHMMSS_STR) >= 0 
                    && DateUtils.timeSub(currentTime, consumeLimitEndTime, DateUtils.DATE_FORMAT_HHMMSS_STR) >= 0) {
                    sign = true;
                }
            } else if (DateUtils.timeSub(consumeLimitStartTime, consumeLimitEndTime, DateUtils.DATE_FORMAT_HHMMSS_STR) == 0 ) {
                if (DateUtils.timeSub(consumeLimitStartTime, currentTime, DateUtils.DATE_FORMAT_HHMMSS_STR) == 0) {
                    sign = true;
                }
            } else {
                boolean repairSgin1 = false;
                if (DateUtils.timeSub(consumeLimitStartTime, currentTime, DateUtils.DATE_FORMAT_HHMMSS_STR) >= 0 
                    && DateUtils.timeSub(currentTime, DateUtils.DAY_TIME_END, DateUtils.DATE_FORMAT_HHMMSS_STR) >= 0) {
                    repairSgin1 = true;
                }
                boolean repairSgin2 = false;
                if (DateUtils.timeSub(DateUtils.DAY_TIME_START, currentTime, DateUtils.DATE_FORMAT_HHMMSS_STR) >= 0 
                    && DateUtils.timeSub(currentTime, consumeLimitEndTime, DateUtils.DATE_FORMAT_HHMMSS_STR) >= 0) {
                    repairSgin2 = true;
                }
                sign = repairSgin1 || repairSgin2;
            }
        }

        // 当服务器当前时间处于消费限制时间段内，返回错误码。不允许做消费业务
        if (sign) {
            response.setCode(ResponseCode.PRODUCT_CHECK_YKT_SERVICE_REPAIRING);// 通卡公司系统维护中
            response.setMessage(response.getMessage()+"（"+consumeLimitStartTime+"至"+consumeLimitEndTime+"）");
            return response;
        }
        
        response.setCode(ResponseCode.SUCCESS);
        response.setResponseEntity(productYkt);
        return response;
    }


}
