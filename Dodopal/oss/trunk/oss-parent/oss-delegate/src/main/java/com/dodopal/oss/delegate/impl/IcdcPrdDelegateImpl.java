package com.dodopal.oss.delegate.impl;

import com.dodopal.api.product.dto.PrdProductYktDTO;
import com.dodopal.api.product.dto.ProductConsumeOrderDTO;
import com.dodopal.api.product.dto.ProductConsumeOrderDetailDTO;
import com.dodopal.api.product.dto.ProductConsumerOrderForExport;
import com.dodopal.api.product.dto.query.PrdProductYktQueryDTO;
import com.dodopal.api.product.dto.query.ProductConsumeOrderQueryDTO;
import com.dodopal.api.product.facade.PrdProductYktFacade;
import com.dodopal.api.product.facade.ProductOrderFacade;
import com.dodopal.api.product.facade.ProductYktFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.oss.delegate.BaseDelegate;
import com.dodopal.oss.delegate.IcdcPrdDelegate;
import com.dodopal.oss.delegate.bean.IcdcPrdBean;
import com.dodopal.oss.delegate.bean.IcdcPrdQuery;
import com.dodopal.oss.delegate.constant.DelegateConstant;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("icdcPrdDelegateImpl")
public class IcdcPrdDelegateImpl extends BaseDelegate implements IcdcPrdDelegate {
    
    /**
     * 分页查询符合条件一卡通充值产品信息
     * @param icdcPrdQuery
     * @return
     */
    public DodopalResponse<DodopalDataPage<IcdcPrdBean>> queryIcdcPrdPageByCondition(IcdcPrdQuery icdcPrdQuery) {
        //调用第三方接口
        PrdProductYktFacade facade = getFacade(PrdProductYktFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        PrdProductYktQueryDTO queryDto = new PrdProductYktQueryDTO();
        queryDto.setProName(icdcPrdQuery.getNameQuery());
        queryDto.setCityId(icdcPrdQuery.getCityQuery());
        queryDto.setYktCode(icdcPrdQuery.getSupplierQuery());
        String valueQuery = icdcPrdQuery.getValueQuery();
        if ("".equals(valueQuery)) {
            queryDto.setProPrice("-1");
        } else {
            queryDto.setProPrice(new Integer(icdcPrdQuery.getValueQuery()) * 100 + "");
        }
        queryDto.setProStatus(icdcPrdQuery.getSaleUporDownQuery());
        queryDto.setPage(icdcPrdQuery.getPage());
        DodopalResponse<DodopalDataPage<PrdProductYktDTO>> dtoResponse = facade.findPrdProductYktByPage(queryDto);
        DodopalDataPage<PrdProductYktDTO> dtoEntity = dtoResponse.getResponseEntity();
        List<PrdProductYktDTO> dtoRecords = dtoEntity.getRecords();

        //组装返回内容
        DodopalResponse<DodopalDataPage<IcdcPrdBean>> response = new DodopalResponse<>();
        List<IcdcPrdBean> beanList = new ArrayList<>();
        if (dtoRecords != null) {
            for (int i = 0; i < dtoRecords.size(); i++) {
                PrdProductYktDTO dto = dtoRecords.get(i);
                IcdcPrdBean bean = new IcdcPrdBean();
                bean.setName(dto.getProName());
                bean.setCode(dto.getProCode());
                bean.setCity(dto.getCityName());
                bean.setSupplier(dto.getYktName());
                bean.setRate(dto.getProRate());
                BigDecimal costPrice = new BigDecimal(dto.getProPayPrice());
                bean.setCostPrice(costPrice.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_UP).toString());
                BigDecimal valuePrice = new BigDecimal(dto.getProPrice());
                bean.setValuePrice(valuePrice.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_UP).toString());
                String status = dto.getProStatus();
                if (status.equals("0")) {
                    status = "上架";
                } else if (status.equals("1")) {
                    status = "下架";
                }
                bean.setSaleUporDown(status);
                bean.setComments(dto.getRemark());
                bean.setId(dto.getId());
                bean.setCreateDate(dto.getCreateDate());
                bean.setCreateUser(dto.getCreateUser());
                bean.setUpdateDate(dto.getUpdateDate());
                bean.setUpdateUser(dto.getUpdateUser());
                beanList.add(bean);
            }

        }
        PageParameter rePage = DodopalDataPageUtil.convertPageInfo(dtoEntity);
        DodopalDataPage<IcdcPrdBean> dodopalDataPage = new DodopalDataPage(rePage, beanList);
        response.setCode(dtoResponse.getCode());
        response.setResponseEntity(dodopalDataPage);
        return response;
    }

    /**
     * 获取=一卡通充值产品导出信息
     * @param icdcPrdQuery
     * @return
     */
    @Override
    public DodopalResponse<List<IcdcPrdBean>> getIcdcPrductListForExportExcel(IcdcPrdQuery icdcPrdQuery) {
        DodopalResponse<List<IcdcPrdBean>> response = new DodopalResponse<>();
        
        //调用第三方接口
        PrdProductYktFacade facade = getFacade(PrdProductYktFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        PrdProductYktQueryDTO queryDto = new PrdProductYktQueryDTO();
        queryDto.setProName(icdcPrdQuery.getNameQuery());
        queryDto.setCityId(icdcPrdQuery.getCityQuery());
        queryDto.setYktCode(icdcPrdQuery.getSupplierQuery());
        String valueQuery = icdcPrdQuery.getValueQuery();
        if (StringUtils.isBlank(valueQuery)) {
            queryDto.setProPrice("-1");
        } else {
            queryDto.setProPrice(new Integer(icdcPrdQuery.getValueQuery()) * 100 + "");
        }
        queryDto.setProStatus(icdcPrdQuery.getSaleUporDownQuery());
        queryDto.setPage(icdcPrdQuery.getPage());
        
        DodopalResponse<List<PrdProductYktDTO>> dtoResponse = facade.getIcdcPrductListForExportExcel(queryDto);
        response.setCode(dtoResponse.getCode());
        List<PrdProductYktDTO> dtoRecords = dtoResponse.getResponseEntity();

        //组装返回内容
        List<IcdcPrdBean> beanList = new ArrayList<>();
        if (dtoRecords != null) {
            for (int i = 0; i < dtoRecords.size(); i++) {
                PrdProductYktDTO dto = dtoRecords.get(i);
                IcdcPrdBean bean = new IcdcPrdBean();
                bean.setName(dto.getProName());
                bean.setCode(dto.getProCode());
                bean.setCity(dto.getCityName());
                bean.setSupplier(dto.getYktName());
                bean.setRate(dto.getProRate());
                BigDecimal costPrice = new BigDecimal(dto.getProPayPrice());
                bean.setCostPrice(costPrice.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_UP).toString());
                BigDecimal valuePrice = new BigDecimal(dto.getProPrice());
                bean.setValuePrice(valuePrice.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_UP).toString());
                String status = dto.getProStatus();
                if (status.equals("0")) {
                    status = "上架";
                } else if (status.equals("1")) {
                    status = "下架";
                }
                bean.setSaleUporDown(status);
                bean.setComments(dto.getRemark());
                bean.setId(dto.getId());
                bean.setCreateDate(dto.getCreateDate());
                bean.setCreateUser(dto.getCreateUser());
                bean.setUpdateDate(dto.getUpdateDate());
                bean.setUpdateUser(dto.getUpdateUser());
                beanList.add(bean);
            }
        }
        response.setResponseEntity(beanList);
        return response;
    }
    
    /**
     * 更新一卡通充值产品信息
     * @param icdcPrdDto
     * @return
     */
    public DodopalResponse<String> updateIcdcPrd(List<IcdcPrdBean> icdcPrdDto) {
        PrdProductYktFacade facade = getFacade(PrdProductYktFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        DodopalResponse<String> response = new DodopalResponse<>();
        String code = "";
        for (IcdcPrdBean bean : icdcPrdDto) {
            PrdProductYktDTO dto = new PrdProductYktDTO();
            dto.setProCode(bean.getCode());
            dto.setRemark(bean.getComments());
            dto.setUpdateUser(bean.getUpdateUser());
            dto.setUpdateDate(bean.getUpdateDate());
            DodopalResponse<Integer> rs = facade.updatePrdProductYkt(dto);
            code = rs.getCode();
            if (!code.equals(ResponseCode.SUCCESS)) {
                break;
            }
        }
        response.setCode(code);
        return response;
    }

    @Override
    public DodopalResponse<String> putAwayIcdcPrd(List<String> icdcPrdCode, String updateUser) {
        PrdProductYktFacade facade = getFacade(PrdProductYktFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        DodopalResponse<String> response = new DodopalResponse<>();
        DodopalResponse<Integer> rs = facade.updatePrdProductYktStatus("0", icdcPrdCode, updateUser);
        response.setCode(rs.getCode());
        response.setResponseEntity(rs.getResponseEntity() + "");
        return response;
    }

    @Override
    public DodopalResponse<String> soltOutIcdcPrd(List<String> icdcPrdCode, String updateUser) {
        PrdProductYktFacade facade = getFacade(PrdProductYktFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        DodopalResponse<String> response = new DodopalResponse<>();
        DodopalResponse<Integer> rs = facade.updatePrdProductYktStatus("1", icdcPrdCode, updateUser);
        response.setCode(rs.getCode());
        response.setResponseEntity(rs.getResponseEntity() + "");
        return response;
    }

    /**
     * 保存一卡通充值产品入库
     * @param icdcPrdDto
     * @return
     */
    public DodopalResponse<String> saveIcdcPrd(List<IcdcPrdBean> icdcPrdDto) {
        PrdProductYktFacade facade = getFacade(PrdProductYktFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        DodopalResponse<String> response = new DodopalResponse<>();
        String code = "";
        for (IcdcPrdBean bean : icdcPrdDto) {
            PrdProductYktDTO dto = new PrdProductYktDTO();
            dto.setProName(bean.getName());
            dto.setProCode(bean.getCode());
            dto.setYktName(bean.getSupplier());
            dto.setYktCode(bean.getSupplierCode());
            dto.setCityName(bean.getCity());
            dto.setCityId(bean.getCityId());
            dto.setProStatus(bean.getSaleUporDown());
            dto.setRemark(bean.getComments());
            String price = bean.getValuePrice();
            //BigDecimal iprice = new BigDecimal(price);
            dto.setProPrice((int) (Double.valueOf(price) * 100));
            dto.setCreateUser(bean.getCreateUser());
            dto.setCreateDate(bean.getCreateDate());
            DodopalResponse<Integer> rs = facade.addPrdProductYkt(dto);
            code = rs.getCode();
            if (!code.equals(ResponseCode.SUCCESS)) {
                break;
            }
        }
        response.setCode(code);
        return response;
    }

    @Override
    public DodopalResponse<IcdcPrdBean> queryIcdcPrdByCode(String code) {
        PrdProductYktFacade facade = getFacade(PrdProductYktFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        DodopalResponse<PrdProductYktDTO> dtoResponse = facade.findPrdProductYktByProCode(code);
        DodopalResponse<IcdcPrdBean> response = new DodopalResponse<>();
        if (ResponseCode.SUCCESS.equals(dtoResponse.getCode())) {
            PrdProductYktDTO dto = dtoResponse.getResponseEntity();
            IcdcPrdBean bean = new IcdcPrdBean();
            bean.setName(dto.getProName());
            bean.setCode(dto.getProCode());
            bean.setCity(dto.getCityName());
            bean.setSupplier(dto.getYktName());
            bean.setRate(dto.getProRate());
            BigDecimal costPrice = new BigDecimal(dto.getProPayPrice());
            bean.setCostPrice(costPrice.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_UP).toString());
            BigDecimal valuePrice = new BigDecimal(dto.getProPrice());
            bean.setValuePrice(valuePrice.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_UP).toString());
            String status = dto.getProStatus();
            if (status.equals("0")) {
                status = "上架";
            } else if (status.equals("1")) {
                status = "下架";
            }
            bean.setSaleUporDown(status);
            bean.setComments(dto.getRemark());
            bean.setId(dto.getId());
            bean.setCreateDate(dto.getCreateDate());
            bean.setCreateUser(dto.getCreateUser());
            bean.setUpdateDate(dto.getUpdateDate());
            bean.setUpdateUser(dto.getUpdateUser());
            response.setResponseEntity(bean);
        }
        response.setCode(dtoResponse.getCode());
        return response;
    }

    @Override
    public DodopalResponse<List<Map<String, String>>> queryIcdcNames(String activate) {
        DodopalResponse<List<Map<String, String>>> response = null;
        ProductYktFacade facade = getFacade(ProductYktFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        response = facade.queryIcdcNames(activate);
        return response;
    }

    @Override
    public DodopalResponse<List<Map<String, String>>> queryIcdcBusiCities(String code) {
        DodopalResponse<List<Map<String, String>>> response = null;
        ProductYktFacade facade = getFacade(ProductYktFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        response = facade.queryIcdcBusiCities(code);
        return response;
    }

    @Override
    public DodopalResponse<Boolean> checkPrdProductYktExist(List<IcdcPrdBean> icdcPrdBean) {
        DodopalResponse<Boolean> response = null;
        PrdProductYktFacade facade = getFacade(PrdProductYktFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        for (IcdcPrdBean bean : icdcPrdBean) {
            String price = bean.getValuePrice();
            //BigDecimal iprice = new BigDecimal(price);
            response = facade.checkPrdProductYktExist(bean.getSupplierCode(), bean.getCityId(), (int) (Double.valueOf(price) * 100));
            if (!response.getCode().equals(ResponseCode.SUCCESS)) {
                return response;
            }
        }
        return response;
    }

    /**
     * 查询 一卡通消费 收单记录
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<DodopalDataPage<ProductConsumeOrderDTO>> findProductConsumeOrderByPage(ProductConsumeOrderQueryDTO prdOrderQuery) {
        ProductOrderFacade facade = getFacade(ProductOrderFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        DodopalResponse<DodopalDataPage<ProductConsumeOrderDTO>> response = facade.findProductConsumeOrderByPage(prdOrderQuery);
        return response;
    }

    /**
     * 根据消费订单号 orderNum 查询一卡通消费订单详情
     * @param orderNum 订单号
     * @return
     */
    public DodopalResponse<ProductConsumeOrderDetailDTO> findProductConsumeOrderDetails(String orderNum) {
        ProductOrderFacade facade = getFacade(ProductOrderFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        DodopalResponse<ProductConsumeOrderDetailDTO> response = facade.findProductConsumeOrderDetails(orderNum);
        return response;
    }

    /**
     * 查询 一卡通消费 收单记录
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<DodopalDataPage<ProductConsumeOrderDTO>> findProductConsumeOrdersExptionByList(ProductConsumeOrderQueryDTO prdOrderQuery) {
        ProductOrderFacade facade = getFacade(ProductOrderFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        DodopalResponse<DodopalDataPage<ProductConsumeOrderDTO>> response = facade.findProductConsumeOrderExptionByList(prdOrderQuery);
        return response;
    }

    /**
     * 导出异常消费记录
     */
    @Override
    public DodopalResponse<List<ProductConsumeOrderDTO>> excelExceptionProductOrderExport(ProductConsumeOrderQueryDTO prdOrderQuery) {
        ProductOrderFacade facade = getFacade(ProductOrderFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        DodopalResponse<List<ProductConsumeOrderDTO>> response = facade.excelExceptionProductOrderExport(prdOrderQuery);
        return response;
    }
    
    @Override
    public DodopalResponse<List<ProductConsumerOrderForExport>> getConsumerOrderListForExportExcel(ProductConsumeOrderQueryDTO prdOrderQuery) {
        ProductOrderFacade facade = getFacade(ProductOrderFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        DodopalResponse<List<ProductConsumerOrderForExport>> response = facade.getConsumerOrderListForExportExcel(prdOrderQuery);
        return response;
    }

}
