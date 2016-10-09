package com.dodopal.portal.business.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.product.dto.ChildRechargeOrderSumDTO;
import com.dodopal.api.product.dto.ProductOrderDTO;
import com.dodopal.api.product.dto.ProductOrderDetailDTO;
import com.dodopal.api.product.dto.PurchaseOrderDTO;
import com.dodopal.api.product.dto.RechargeOrderDTO;
import com.dodopal.api.product.dto.query.ChildRechargeOrderSumQueryDTO;
import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.api.product.dto.query.PurchaseOrderQueryDTO;
import com.dodopal.api.product.dto.query.RechargeOrderQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.ExcelModel;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.portal.business.bean.ChildRechargeOrderSumBean;
import com.dodopal.portal.business.bean.ProductOrderBean;
import com.dodopal.portal.business.bean.ProductOrderDetailBean;
import com.dodopal.portal.business.bean.PurchaseOrderBean;
import com.dodopal.portal.business.bean.RechargeOrderBean;
import com.dodopal.portal.business.bean.query.ChildRechargeOrderSumQuery;
import com.dodopal.portal.business.bean.query.ProductOrderQueryBean;
import com.dodopal.portal.business.bean.query.PurchaseOrderQuery;
import com.dodopal.portal.business.bean.query.RechargeOrderQuery;
import com.dodopal.portal.business.service.ProductOrderService;
import com.dodopal.portal.delegate.ChildMerProductOrderDelegate;
import com.dodopal.portal.delegate.ProductOrderDelegate;

@Service
public class ProductOrderServiceImpl implements ProductOrderService {
    private final static Logger log = LoggerFactory.getLogger(ProductOrderServiceImpl.class);
    @Autowired
    ProductOrderDelegate productOrderDelegate;
    @Autowired
    ChildMerProductOrderDelegate childMerProductOrderDelegate;

    /**
     * 订单查询 产品库提供标准的订单查询页面给终端商户（各种网点）和个人。
     */
    @Override
    public DodopalResponse<DodopalDataPage<ProductOrderBean>> findProductOrderByPage(ProductOrderQueryBean prdOrderQuery) {
        log.info("input portal prdOrderQuery :" + prdOrderQuery);
        DodopalResponse<DodopalDataPage<ProductOrderBean>> response = new DodopalResponse<DodopalDataPage<ProductOrderBean>>();
        ProductOrderQueryDTO proOrderQueryDto = new ProductOrderQueryDTO();
        //订单编号 文本框 精确  用户输入
        if (StringUtils.isNotBlank(prdOrderQuery.getProOrderNum())) {
            proOrderQueryDto.setProOrderNum(prdOrderQuery.getProOrderNum());
        }
        //订单外部状态
        if (StringUtils.isNotBlank(prdOrderQuery.getProOrderState())) {
            proOrderQueryDto.setProOrderState(prdOrderQuery.getProOrderState());
        }
        //订单创建时间 日历选择框 范围  用户选择
        if (prdOrderQuery.getOrderDateStart() != null) {
            proOrderQueryDto.setOrderDateStart(prdOrderQuery.getOrderDateStart());
        }
        if (prdOrderQuery.getOrderDateEnd() != null) {
            proOrderQueryDto.setOrderDateEnd(prdOrderQuery.getOrderDateEnd());
        }
        //卡号    文本框 精确  用户输入
        if (StringUtils.isNotBlank(prdOrderQuery.getOrderCardno())) {
            proOrderQueryDto.setOrderCardno(prdOrderQuery.getOrderCardno());
        }
        //业务城市    精确  
        if (StringUtils.isNotBlank(prdOrderQuery.getCityName())) {
            proOrderQueryDto.setCityName(prdOrderQuery.getCityName());
        }
        //充值金额（起/止）
        if (prdOrderQuery.getTxnAmtStart() != null) {
            proOrderQueryDto.setTxnAmtStart(prdOrderQuery.getTxnAmtStart());
            ;
        }
        if (prdOrderQuery.getTxnAmtEnd() != null) {
            proOrderQueryDto.setTxnAmtEnd(prdOrderQuery.getTxnAmtEnd());
            ;
        }
        //POS机编号
        if (StringUtils.isNotBlank(prdOrderQuery.getPosCode())) {
            proOrderQueryDto.setPosCode(prdOrderQuery.getPosCode());
        }
        //外部订单号
        if (StringUtils.isNotBlank(prdOrderQuery.getMerOrderNum())) {
            proOrderQueryDto.setCityName(prdOrderQuery.getMerOrderNum());
        }
        //商户号
        if (StringUtils.isNotBlank(prdOrderQuery.getMerCode())) {
            proOrderQueryDto.setMerCode(prdOrderQuery.getMerCode());
        }
        //分页参数
        if (prdOrderQuery.getPage() != null) {
            proOrderQueryDto.setPage(prdOrderQuery.getPage());
            ;
        }
        DodopalResponse<DodopalDataPage<ProductOrderDTO>> proDTO = productOrderDelegate.findProductOrderByPage(proOrderQueryDto);
        List<ProductOrderBean> productOrderBeanList = new ArrayList<ProductOrderBean>();
        if (ResponseCode.SUCCESS.equals(proDTO.getCode())) {
            if (proDTO.getResponseEntity() != null && CollectionUtils.isNotEmpty(proDTO.getResponseEntity().getRecords())) {
                for (ProductOrderDTO productOrderDTOList : proDTO.getResponseEntity().getRecords()) {
                    ProductOrderBean productOrderBean = new ProductOrderBean();
                    productOrderBean.setProOrderNum(productOrderDTOList.getProOrderNum());
                    productOrderBean.setProOrderDate(productOrderDTOList.getProOrderDate());
                    productOrderBean.setOrderCardno(productOrderDTOList.getOrderCardno());
                    productOrderBean.setTxnAmt(productOrderDTOList.getTxnAmt());
                    productOrderBean.setBefbal(productOrderDTOList.getBefbal());
                    productOrderBean.setBlackAmt(productOrderDTOList.getBlackAmt());
                    productOrderBean.setReceivedPrice(productOrderDTOList.getReceivedPrice());
                    productOrderBean.setMerGain(productOrderDTOList.getMerGain());
                    productOrderBean.setPosCode(productOrderDTOList.getPosCode());
                    productOrderBean.setCityName(productOrderDTOList.getCityName());
                    productOrderBean.setUserId(productOrderDTOList.getUserId());
                    productOrderBean.setUserName(productOrderDTOList.getUserName());
                    productOrderBeanList.add(productOrderBean);
                }
            }

            log.info(" return code:" + proDTO.getCode());
            //后台传入总页数，总条数，当前页
            PageParameter page = DodopalDataPageUtil.convertPageInfo(proDTO.getResponseEntity());
            DodopalDataPage<ProductOrderBean> pages = new DodopalDataPage<ProductOrderBean>(page, productOrderBeanList);
            response.setCode(proDTO.getCode());
            response.setResponseEntity(pages);
        } else {
            response.setCode(proDTO.getCode());
        }
        return response;
    }

    /**
     * 5.2 产品库中公交卡充值订单 --5.2.2 订单查看 用户选择一条公交卡充值订单，点击“查看”按钮，页面向用户展示详情。
     * 订单编号、商户名称、产品编号
     * 、产品名称、充值金额、城市、实付金额、成本价（商户进货价）、订单时间、商户利润（这个字段对个人用户不要显示）、卡号、充值前金额
     * 、充值后金额、充值前账户可用余额、充值后账户可用余额、订单状态、外部订单号（仅限于外部商户）、操作员名称、POS编号、POS备注。
     */
    @Override
    public DodopalResponse<ProductOrderDetailBean> findProductOrderByCode(String proOrderNum) {
        DodopalResponse<ProductOrderDetailBean> reponse = new DodopalResponse<ProductOrderDetailBean>();
        log.info("input portal proOrderNum:" + proOrderNum);
        DodopalResponse<ProductOrderDetailDTO> productOrderDetailDTO = productOrderDelegate.findProductOrderByCode(proOrderNum);

        try {
            if (productOrderDetailDTO.getCode().equals(ResponseCode.SUCCESS)) {
                ProductOrderDetailBean productOrderDetailBean = new ProductOrderDetailBean();
                PropertyUtils.copyProperties(productOrderDetailBean, productOrderDetailDTO.getResponseEntity());
                reponse.setResponseEntity(productOrderDetailBean);
                reponse.setCode(ResponseCode.SUCCESS);
            } else {
                reponse.setCode(productOrderDetailDTO.getCode());
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return reponse;
    }

    @Override
    public DodopalResponse<DodopalDataPage<ProductOrderBean>> findChildMerProductOrderByPage(ProductOrderQueryBean prdOrderQuery) {
        log.info("input portal prdOrderQuery :" + prdOrderQuery);
        DodopalResponse<DodopalDataPage<ProductOrderBean>> response = new DodopalResponse<DodopalDataPage<ProductOrderBean>>();
        ProductOrderQueryDTO proOrderQueryDto = new ProductOrderQueryDTO();
        //订单编号 文本框 精确  用户输入
        if (StringUtils.isNotBlank(prdOrderQuery.getProOrderNum())) {
            proOrderQueryDto.setProOrderNum(prdOrderQuery.getProOrderNum());
        }
        //订单外部状态
        if (StringUtils.isNotBlank(prdOrderQuery.getProOrderState())) {
            proOrderQueryDto.setProOrderState(prdOrderQuery.getProOrderState());
        }
        //订单创建时间 日历选择框 范围  用户选择
        if (prdOrderQuery.getOrderDateStart() != null) {
            proOrderQueryDto.setOrderDateStart(prdOrderQuery.getOrderDateStart());
        }
        if (prdOrderQuery.getOrderDateEnd() != null) {
            proOrderQueryDto.setOrderDateEnd(prdOrderQuery.getOrderDateEnd());
        }
        //卡号    文本框 精确  用户输入
        if (StringUtils.isNotBlank(prdOrderQuery.getOrderCardno())) {
            proOrderQueryDto.setOrderCardno(prdOrderQuery.getOrderCardno());
        }
        //业务城市    精确  
        if (StringUtils.isNotBlank(prdOrderQuery.getCityName())) {
            proOrderQueryDto.setCityName(prdOrderQuery.getCityName());
        }
        //充值金额（起/止）
        if (prdOrderQuery.getTxnAmtStart() != null) {
            proOrderQueryDto.setTxnAmtStart(prdOrderQuery.getTxnAmtStart());
            ;
        }
        if (prdOrderQuery.getTxnAmtEnd() != null) {
            proOrderQueryDto.setTxnAmtEnd(prdOrderQuery.getTxnAmtEnd());
            ;
        }
        //POS机编号
        if (StringUtils.isNotBlank(prdOrderQuery.getPosCode())) {
            proOrderQueryDto.setPosCode(prdOrderQuery.getPosCode());
        }
        //外部订单号
        if (StringUtils.isNotBlank(prdOrderQuery.getMerOrderNum())) {
            proOrderQueryDto.setCityName(prdOrderQuery.getMerOrderNum());
        }
        //商户号
        if (StringUtils.isNotBlank(prdOrderQuery.getMerCode())) {
            proOrderQueryDto.setMerCode(prdOrderQuery.getMerCode());
        }

        //商户号
        if (StringUtils.isNotBlank(prdOrderQuery.getMerName())) {
            proOrderQueryDto.setMerName(prdOrderQuery.getMerName());
        }
        //分页参数
        if (prdOrderQuery.getPage() != null) {
            proOrderQueryDto.setPage(prdOrderQuery.getPage());
            ;
        }
        DodopalResponse<DodopalDataPage<ProductOrderDTO>> proDTO = childMerProductOrderDelegate.findChildMerProductOrderByPage(proOrderQueryDto);
        List<ProductOrderBean> productOrderBeanList = new ArrayList<ProductOrderBean>();
        if (ResponseCode.SUCCESS.equals(proDTO.getCode())) {
            if (proDTO.getResponseEntity() != null && CollectionUtils.isNotEmpty(proDTO.getResponseEntity().getRecords())) {
                for (ProductOrderDTO productOrderDTOList : proDTO.getResponseEntity().getRecords()) {
                    ProductOrderBean productOrderBean = new ProductOrderBean();
                    productOrderBean.setProOrderNum(productOrderDTOList.getProOrderNum());
                    productOrderBean.setProOrderDate(productOrderDTOList.getProOrderDate());
                    productOrderBean.setOrderCardno(productOrderDTOList.getOrderCardno());
                    productOrderBean.setPosCode(productOrderDTOList.getPosCode());
                    productOrderBean.setCityName(productOrderDTOList.getCityName());
                    productOrderBean.setUserId(productOrderDTOList.getUserId());
                    productOrderBean.setMerName(productOrderDTOList.getMerName());
                    productOrderBean.setUserName(productOrderDTOList.getUserName());
                    productOrderBean.setShowTxnAmt(productOrderDTOList.getShowTxnAmt());
                    productOrderBean.setShowBefbal(productOrderDTOList.getShowBefbal());
                    productOrderBean.setShowBlackAmt(productOrderDTOList.getShowBlackAmt());
                    productOrderBean.setProOrderState(productOrderDTOList.getProOrderState());
                    productOrderBean.setShowReceivedPrice(productOrderDTOList.getShowReceivedPrice());
                    productOrderBean.setShowMerGain(productOrderDTOList.getShowMerGain());
                    productOrderBean.setPosComments(productOrderDTOList.getPosComments());
                    productOrderBeanList.add(productOrderBean);
                }
            }

            log.info(" return code:" + proDTO.getCode());
            //后台传入总页数，总条数，当前页
            PageParameter page = DodopalDataPageUtil.convertPageInfo(proDTO.getResponseEntity());
            DodopalDataPage<ProductOrderBean> pages = new DodopalDataPage<ProductOrderBean>(page, productOrderBeanList);
            response.setCode(proDTO.getCode());
            response.setResponseEntity(pages);
        } else {
            response.setCode(proDTO.getCode());
        }
        return response;
    }

    @Override
    public DodopalResponse<ProductOrderDetailBean> findChildMerProductOrderByCode(String proOrderNum) {
        DodopalResponse<ProductOrderDetailBean> reponse = new DodopalResponse<ProductOrderDetailBean>();
        log.info("input portal proOrderNum:" + proOrderNum);
        DodopalResponse<ProductOrderDetailDTO> productOrderDetailDTO = childMerProductOrderDelegate.findChildMerProductOrderByCode(proOrderNum);

        try {
            if (productOrderDetailDTO.getCode().equals(ResponseCode.SUCCESS)) {
                ProductOrderDetailBean productOrderDetailBean = new ProductOrderDetailBean();
                PropertyUtils.copyProperties(productOrderDetailBean, productOrderDetailDTO.getResponseEntity());
                reponse.setResponseEntity(productOrderDetailBean);
                reponse.setCode(ResponseCode.SUCCESS);
            } else {
                reponse.setCode(productOrderDetailDTO.getCode());
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return reponse;
    }

    @Override
    /**
     * Title:子商户订单导出功能
     * Time:2015-10-15 16:56
     * Name: qjc
     */
    public DodopalResponse<String> childProductOrderExcelExport(HttpServletResponse response, ProductOrderQueryDTO prdOrderQuery) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        try {
            List<ProductOrderDTO> lstData = childMerProductOrderDelegate.findChildMerProductOrderExcel(prdOrderQuery);
            List<ProductOrderBean> orderList = new ArrayList<ProductOrderBean>();
            if (CollectionUtils.isNotEmpty(lstData)) {
                for (ProductOrderDTO orderDto : lstData) {
                    ProductOrderBean productOrderBean = new ProductOrderBean();
                    productOrderBean.setId(orderDto.getId());
                    productOrderBean.setProOrderNum(orderDto.getProOrderNum());
                    productOrderBean.setProOrderDate(orderDto.getProOrderDate());
                    productOrderBean.setOrderCardno(orderDto.getOrderCardno());
                    productOrderBean.setPosCode(orderDto.getPosCode());
                    productOrderBean.setCityName(orderDto.getCityName());
                    productOrderBean.setUserId(orderDto.getUserId());
                    productOrderBean.setMerName(orderDto.getMerName());
                    productOrderBean.setUserName(orderDto.getUserName());
                    productOrderBean.setShowTxnAmt(orderDto.getShowTxnAmt());
                    productOrderBean.setShowBefbal(orderDto.getShowBefbal());
                    productOrderBean.setShowBlackAmt(orderDto.getShowBlackAmt());
                    productOrderBean.setProOrderState(orderDto.getProOrderState());
                    productOrderBean.setShowReceivedPrice(orderDto.getShowReceivedPrice());
                    productOrderBean.setShowMerGain(orderDto.getShowMerGain());
                    productOrderBean.setPosComments(orderDto.getPosComments());
                    orderList.add(productOrderBean);
                }
                ExcelModel excelModel = new ExcelModel("childMerProductOrderList");
                excelModel.setDataStartIndex(3); //数据开始行, 可以不设置,默认为3(第四行)
                if (CollectionUtils.isNotEmpty(orderList)) {
                    List<List<String>> dataList = new ArrayList<List<String>>(lstData.size());
                    for (ProductOrderBean data : orderList) {
                        List<String> rowList = new ArrayList<String>();
                        // 订单编号
                        rowList.add(data.getProOrderNum());
                        // 商户名称
                        rowList.add(data.getMerName());
                        // 城市
                        rowList.add(data.getCityName());
                        // 充值前金额
                        rowList.add(data.getShowBefbal());
                        // 充值金额
                        rowList.add(data.getShowTxnAmt());
                        // 实付金额
                        rowList.add(data.getShowReceivedPrice());
                        // 充值后金额
                        rowList.add(data.getShowBlackAmt());
                        // 商户利润
                        rowList.add(data.getShowMerGain());
                        // POS号
                        rowList.add(data.getPosCode());
                        // 订单状态
                        rowList.add(data.getProOrderStateView());
                        // 订单时间
                        rowList.add(DateUtils.formatDate(data.getProOrderDate(), DateUtils.DATE_FULL_STR));
                        //pos备注
                        rowList.add(data.getPosComments());
                        // 【添加数据行】
                        dataList.add(rowList);
                    }
                    // 【将数据集加入model】
                    excelModel.setDataList(dataList);

                }
                return ExcelUtil.excelExport(excelModel, response);
            } else {
                rep.setCode(ResponseCode.EXCEL_EXPORT_NULL_DATA);
            }
        }
        catch (Exception e) {
            log.error("子商户业务订单查询导出异常:" + e.getMessage(), e);
        }
        return rep;
    }

    //查询下级商户一卡通充值 订单汇总 信息
    @Override
    public DodopalResponse<DodopalDataPage<ChildRechargeOrderSumBean>> queryRechargeForChildOrderSum(ChildRechargeOrderSumQuery query) {
        DodopalResponse<DodopalDataPage<ChildRechargeOrderSumBean>> response = new DodopalResponse<DodopalDataPage<ChildRechargeOrderSumBean>>();
        ChildRechargeOrderSumQueryDTO queryDTO = new ChildRechargeOrderSumQueryDTO();
        try {
            log.info("查询下级商户一卡通充值 订单汇总 信息 ChildMerChantRecordServiceImpl queryRechargeForChildOrderSum is start query:" + query);
            //pos号
            if (query.getPosCode() != null) {
                queryDTO.setPosCode(query.getPosCode());
            }
            //商户编号
            if (query.getMerCode() != null) {
                queryDTO.setMerCode(query.getMerCode());
            }

            //城市名称
            if (query.getCityName() != null) {
                queryDTO.setCityName(query.getCityName());
            }
            
            //充值订单状态
            if (query.getProOrderState() != null) {
                queryDTO.setProOrderState(query.getProOrderState());
            }
            
            //子商户名称
            if (query.getMerName() != null) {
                queryDTO.setMerName(query.getMerName());
            }
            //开始日期
            if (query.getStartDate() != null) {
                queryDTO.setStratDate(query.getStartDate());
            }
            //结束日期
            if (query.getEndDate() != null) {
                queryDTO.setEndDate(query.getEndDate());
            }
            //分页参数
            if (query.getPage() != null) {
                queryDTO.setPage(query.getPage());
                ;
            }

            DodopalResponse<DodopalDataPage<ChildRechargeOrderSumDTO>> dodopalResponse = childMerProductOrderDelegate.queryRechargeForChildOrder(queryDTO);
            List<ChildRechargeOrderSumDTO> list = new ArrayList<ChildRechargeOrderSumDTO>();
            if (ResponseCode.SUCCESS.equals(dodopalResponse.getCode()) && dodopalResponse.getResponseEntity() != null) {
                list = dodopalResponse.getResponseEntity().getRecords();
            }
            List<ChildRechargeOrderSumBean> beanList = new ArrayList<ChildRechargeOrderSumBean>();
            if (dodopalResponse.getResponseEntity() != null && CollectionUtils.isNotEmpty(list)) {
                for (ChildRechargeOrderSumDTO dto : list) {
                    ChildRechargeOrderSumBean bean = new ChildRechargeOrderSumBean();
                    bean.setPosCode(dto.getPosCode());
                    bean.setMerCode(dto.getMerCode());
                    bean.setMerName(dto.getMerName());
                    bean.setCityName(dto.getCityName());
                    bean.setTradeDate(dto.getTradeDate());
                    bean.setRechargeCount(dto.getRechargeCount());
                    bean.setRechargeAmt((double) dto.getRechargeAmt() / 100);
                    bean.setPosComments(dto.getPosComments());
                    beanList.add(bean);
                }
            }
            //后台传入总页数，总条数，当前页
            PageParameter page = DodopalDataPageUtil.convertPageInfo(dodopalResponse.getResponseEntity());
            DodopalDataPage<ChildRechargeOrderSumBean> pages = new DodopalDataPage<ChildRechargeOrderSumBean>(page, beanList);
            response.setCode(dodopalResponse.getCode());
            response.setResponseEntity(pages);
            log.info("查询下级商户一卡通充值 订单汇总 信息 RechargeForSupplierServiceImpl queryCardRechargeForSupplier is end code:" + response.getCode());
        }
        catch (HessianRuntimeException e) {
            log.debug("查询下级商户一卡通充值 订单汇总 信息 RechargeForSupplierServiceImpl queryCardRechargeForSupplier call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("查询下级商户一卡通充值 订单汇总 信息 RechargeForSupplierServiceImpl queryCardRechargeForSupplier  throws e:" + e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    public DodopalResponse<DodopalDataPage<ProductOrderBean>> findRechargeProductOrderByPage(ProductOrderQueryBean prdOrderQuery) {
        log.info("input portal prdOrderQuery :" + prdOrderQuery);
        DodopalResponse<DodopalDataPage<ProductOrderBean>> response = new DodopalResponse<DodopalDataPage<ProductOrderBean>>();
        ProductOrderQueryDTO proOrderQueryDto = new ProductOrderQueryDTO();
        try {

            //商户名称
            if (StringUtils.isNotBlank(prdOrderQuery.getMerName())) {
                proOrderQueryDto.setMerName(prdOrderQuery.getMerName());
            }
            //商户编号
            if (StringUtils.isNotBlank(prdOrderQuery.getMerCode())) {
                proOrderQueryDto.setMerCode(prdOrderQuery.getMerCode());
            }
            //订单外部状态
            if (StringUtils.isNotBlank(prdOrderQuery.getProOrderState())) {
                proOrderQueryDto.setProOrderState(prdOrderQuery.getProOrderState());
            }
            //订单创建时间 日历选择框 范围  用户选择
            if (prdOrderQuery.getOrderDateStart() != null) {
                proOrderQueryDto.setOrderDateStart(prdOrderQuery.getOrderDateStart());
            }
            if (prdOrderQuery.getOrderDateEnd() != null) {
                proOrderQueryDto.setOrderDateEnd(prdOrderQuery.getOrderDateEnd());
            }
            if (prdOrderQuery.getPrdOrderDay() != null) {
                proOrderQueryDto.setPrdOrderDay(prdOrderQuery.getPrdOrderDay());
            }

            //卡号    文本框 精确  用户输入
            if (StringUtils.isNotBlank(prdOrderQuery.getOrderCardno())) {
                proOrderQueryDto.setOrderCardno(prdOrderQuery.getOrderCardno());
            }
            //业务城市    精确  
            if (StringUtils.isNotBlank(prdOrderQuery.getCityName())) {
                proOrderQueryDto.setCityName(prdOrderQuery.getCityName());
            }
            //充值金额（起/止）
            if (prdOrderQuery.getTxnAmtStart() != null) {
                proOrderQueryDto.setTxnAmtStart(prdOrderQuery.getTxnAmtStart());
                ;
            }
            if (prdOrderQuery.getTxnAmtEnd() != null) {
                proOrderQueryDto.setTxnAmtEnd(prdOrderQuery.getTxnAmtEnd());
                ;
            }
            //POS机编号
            if (StringUtils.isNotBlank(prdOrderQuery.getPosCode())) {
                proOrderQueryDto.setPosCode(prdOrderQuery.getPosCode());
            }
            //外部订单号
            if (StringUtils.isNotBlank(prdOrderQuery.getMerOrderNum())) {
                proOrderQueryDto.setCityName(prdOrderQuery.getMerOrderNum());
            }
            //商户号
            if (StringUtils.isNotBlank(prdOrderQuery.getMerCode())) {
                proOrderQueryDto.setMerCode(prdOrderQuery.getMerCode());
            }
            //分页参数
            if (prdOrderQuery.getPage() != null) {
                proOrderQueryDto.setPage(prdOrderQuery.getPage());
                ;
            }
            DodopalResponse<DodopalDataPage<ProductOrderDTO>> proDTO = productOrderDelegate.findRechargeProductOrderByPage(proOrderQueryDto);
            List<ProductOrderBean> productOrderBeanList = new ArrayList<ProductOrderBean>();
            if (ResponseCode.SUCCESS.equals(proDTO.getCode())) {
                if (proDTO.getResponseEntity() != null && CollectionUtils.isNotEmpty(proDTO.getResponseEntity().getRecords())) {
                    for (ProductOrderDTO productOrderDTOList : proDTO.getResponseEntity().getRecords()) {
                        ProductOrderBean productOrderBean = new ProductOrderBean();
                        productOrderBean.setProOrderNum(productOrderDTOList.getProOrderNum());
                        productOrderBean.setProOrderDate(productOrderDTOList.getProOrderDate());
                        productOrderBean.setOrderCardno(productOrderDTOList.getOrderCardno());
                        productOrderBean.setPosCode(productOrderDTOList.getPosCode());
                        productOrderBean.setCityName(productOrderDTOList.getCityName());
                        productOrderBean.setUserId(productOrderDTOList.getUserId());
                        productOrderBean.setMerName(productOrderDTOList.getMerName());
                        productOrderBean.setUserName(productOrderDTOList.getUserName());
                        productOrderBean.setShowTxnAmt(productOrderDTOList.getShowTxnAmt());
                        productOrderBean.setShowBefbal(productOrderDTOList.getShowBefbal());
                        productOrderBean.setShowBlackAmt(productOrderDTOList.getShowBlackAmt());
                        productOrderBean.setProOrderState(productOrderDTOList.getProOrderState());
                        productOrderBean.setShowReceivedPrice(productOrderDTOList.getShowReceivedPrice());
                        productOrderBean.setShowMerGain(productOrderDTOList.getShowMerGain());
                        productOrderBean.setPosComments(productOrderDTOList.getPosComments());
                        productOrderBeanList.add(productOrderBean);
                    }
                }

                log.info(" return code:" + proDTO.getCode());
                //后台传入总页数，总条数，当前页
                PageParameter page = DodopalDataPageUtil.convertPageInfo(proDTO.getResponseEntity());
                DodopalDataPage<ProductOrderBean> pages = new DodopalDataPage<ProductOrderBean>(page, productOrderBeanList);
                response.setCode(proDTO.getCode());
                response.setResponseEntity(pages);
            } else {
                response.setCode(proDTO.getCode());
            }
        }
        catch (HessianRuntimeException e) {
            log.debug("查询下级商户一卡通充值 订单汇总 详细信息 RechargeForSupplierServiceImpl findRechargeProductOrderByPage call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("查询下级商户一卡通充值 订单汇总 详细信息 RechargeForSupplierServiceImpl findRechargeProductOrderByPage  throws e:" + e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    public Date format(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date response = new Date();
        try {
            response = fmt.parse(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return response;
    }

    //导出
    public DodopalResponse<String> excelExport(HttpServletRequest request, HttpServletResponse response, ChildRechargeOrderSumQuery query) {
        DodopalResponse<String> excelExport = new DodopalResponse<String>();
        List<ChildRechargeOrderSumBean> beanList = null;
        try {
            log.info("ChildMerChantRecordServiceImpl queryRechargeForChildOrderSum is start query:" + query);
            ChildRechargeOrderSumQueryDTO queryDTO = new ChildRechargeOrderSumQueryDTO();

            //pos号
            if (query.getPosCode() != null) {
                queryDTO.setPosCode(query.getPosCode());
            }
            //商户编号
            if (query.getMerCode() != null) {
                queryDTO.setMerCode(query.getMerCode());
            }
            
            //充值订单状态
            if (query.getProOrderState() != null) {
                queryDTO.setProOrderState(query.getProOrderState());
            }

            //城市名称
            if (query.getCityName() != null) {
                queryDTO.setCityName(query.getCityName());
            }
            //子商户名称
            if (query.getMerName() != null) {
                queryDTO.setMerName(query.getMerName());
            }
            //开始日期
            if (query.getStartDate() != null) {
                queryDTO.setStratDate(query.getStartDate());
            }
            //结束日期
            if (query.getEndDate() != null) {
                queryDTO.setEndDate(query.getEndDate());
            }

            int exportMaxNum = 100000;
            queryDTO.setPage(new PageParameter(1, exportMaxNum));

            DodopalResponse<DodopalDataPage<ChildRechargeOrderSumDTO>> dodopalResponse = childMerProductOrderDelegate.queryRechargeForChildOrder(queryDTO);
            List<ChildRechargeOrderSumDTO> list = new ArrayList<ChildRechargeOrderSumDTO>();
            if (ResponseCode.SUCCESS.equals(dodopalResponse.getCode()) && null != dodopalResponse.getResponseEntity()) {
                list = dodopalResponse.getResponseEntity().getRecords();
            }

            beanList = new ArrayList<ChildRechargeOrderSumBean>();

            Map<String, String> index = new LinkedHashMap<String, String>();
//            index.put("tradeDate", "交易日期");
            index.put("merName", "商户名称");
//            index.put("cityName", "城市名称");
//            index.put("posCode", "POS号");
            index.put("rechargeCount", "充值笔数");
            index.put("rechargeAmt", "充值金额（元）");
//            index.put("posComments", "POS备注");
            
            if (dodopalResponse.getResponseEntity() != null && CollectionUtils.isNotEmpty(list)) {
                for (ChildRechargeOrderSumDTO dto : list) {
                    ChildRechargeOrderSumBean bean = new ChildRechargeOrderSumBean();
                    bean.setPosCode(dto.getPosCode() == null ? "" : dto.getPosCode());
                    bean.setMerCode(dto.getMerCode() == null ? "" : dto.getMerCode());
                    bean.setMerName(dto.getMerName() == null ? "" : dto.getMerName());
                    bean.setCityName(dto.getCityName() == null ? "" : dto.getCityName());
                    bean.setTradeDate(dto.getTradeDate() == null ? "" : dto.getTradeDate());
                    bean.setRechargeCount(dto.getRechargeCount());
                    bean.setRechargeAmt((double) dto.getRechargeAmt() / 100);
                    bean.setPosComments(dto.getPosComments());
                    beanList.add(bean);
                }

                String code = ExcelUtil.excelExport(request, response, beanList, index, "子商户一卡通充值订单汇总");
                excelExport.setCode(code);

            } else {
                beanList = new ArrayList<ChildRechargeOrderSumBean>();
                String code = ExcelUtil.excelExport(request, response, beanList, index, "子商户一卡通充值订单汇总");
                excelExport.setCode(code);
            }

        }
        catch (Exception e) {
            excelExport.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        return excelExport;

    }

    // 导出    根据商户和pos查询一卡通充值记录详情
    public DodopalResponse<String> excelExportSumDetail(HttpServletRequest request, HttpServletResponse response, ProductOrderQueryBean prdOrderQuery) {
        DodopalResponse<String> excelExport = new DodopalResponse<String>();
        try {
            ProductOrderQueryDTO proOrderQueryDto = new ProductOrderQueryDTO();
            //商户名称
            if (StringUtils.isNotBlank(prdOrderQuery.getMerName())) {
                proOrderQueryDto.setMerName(prdOrderQuery.getMerName());
            }
            //商户编号
            if (StringUtils.isNotBlank(prdOrderQuery.getMerCode())) {
                proOrderQueryDto.setMerCode(prdOrderQuery.getMerCode());
            }
            //订单外部状态
            if (StringUtils.isNotBlank(prdOrderQuery.getProOrderState())) {
                proOrderQueryDto.setProOrderState(prdOrderQuery.getProOrderState());
            }
            //订单创建时间 日历选择框 范围  用户选择
            if (prdOrderQuery.getOrderDateStart() != null) {
                proOrderQueryDto.setOrderDateStart(prdOrderQuery.getOrderDateStart());
            }
            if (prdOrderQuery.getOrderDateEnd() != null) {
                proOrderQueryDto.setOrderDateEnd(prdOrderQuery.getOrderDateEnd());
            }
            if (prdOrderQuery.getPrdOrderDay() != null) {
                proOrderQueryDto.setPrdOrderDay(prdOrderQuery.getPrdOrderDay());
            }

            //卡号    文本框 精确  用户输入
            if (StringUtils.isNotBlank(prdOrderQuery.getOrderCardno())) {
                proOrderQueryDto.setOrderCardno(prdOrderQuery.getOrderCardno());
            }
            //业务城市    精确  
            if (StringUtils.isNotBlank(prdOrderQuery.getCityName())) {
                proOrderQueryDto.setCityName(prdOrderQuery.getCityName());
            }
            //充值金额（起/止）
            if (prdOrderQuery.getTxnAmtStart() != null) {
                proOrderQueryDto.setTxnAmtStart(prdOrderQuery.getTxnAmtStart());
                ;
            }
            if (prdOrderQuery.getTxnAmtEnd() != null) {
                proOrderQueryDto.setTxnAmtEnd(prdOrderQuery.getTxnAmtEnd());
                ;
            }
            //POS机编号
            if (StringUtils.isNotBlank(prdOrderQuery.getPosCode())) {
                proOrderQueryDto.setPosCode(prdOrderQuery.getPosCode());
            }
            //外部订单号
            if (StringUtils.isNotBlank(prdOrderQuery.getMerOrderNum())) {
                proOrderQueryDto.setCityName(prdOrderQuery.getMerOrderNum());
            }
            //商户号
            if (StringUtils.isNotBlank(prdOrderQuery.getMerCode())) {
                proOrderQueryDto.setMerCode(prdOrderQuery.getMerCode());
            }
            int exportMaxNum = 100000;
            proOrderQueryDto.setPage(new PageParameter(1, exportMaxNum));

            DodopalResponse<DodopalDataPage<ProductOrderDTO>> proDTO = productOrderDelegate.findRechargeProductOrderByPage(proOrderQueryDto);
            List<ProductOrderBean> productOrderBeanList = new ArrayList<ProductOrderBean>();

            Map<String, String> index = new LinkedHashMap<String, String>();
            index.put("proOrderNum", "订单编号");
            index.put("merName", "商户名称");
            index.put("cityName", "城市名称");
            index.put("showTxnAmt", "充值金额（元）");
            index.put("showReceivedPrice", "实付金额（元）");
            index.put("showMerGain", "利润（元）");
            index.put("orderCardno", "卡号");
            index.put("posCode", "POS号");
            index.put("proOrderStateView", "订单状态");
            index.put("proOrderDate", "订单创建时间");
            index.put("posComments", "POS备注");
            if (ResponseCode.SUCCESS.equals(proDTO.getCode())) {
                if (proDTO.getResponseEntity() != null && CollectionUtils.isNotEmpty(proDTO.getResponseEntity().getRecords())) {
                    for (ProductOrderDTO productOrderDTOList : proDTO.getResponseEntity().getRecords()) {
                        ProductOrderBean productOrderBean = new ProductOrderBean();
                        productOrderBean.setProOrderNum(productOrderDTOList.getProOrderNum());
                        productOrderBean.setProOrderDate(productOrderDTOList.getProOrderDate());
                        productOrderBean.setOrderCardno(productOrderDTOList.getOrderCardno());
                        productOrderBean.setPosCode(productOrderDTOList.getPosCode());
                        productOrderBean.setCityName(productOrderDTOList.getCityName());
                        productOrderBean.setUserId(productOrderDTOList.getUserId());
                        productOrderBean.setMerName(productOrderDTOList.getMerName());
                        productOrderBean.setUserName(productOrderDTOList.getUserName());
                        productOrderBean.setShowTxnAmt(productOrderDTOList.getShowTxnAmt());
                        productOrderBean.setShowBefbal(productOrderDTOList.getShowBefbal());
                        productOrderBean.setShowBlackAmt(productOrderDTOList.getShowBlackAmt());
                        productOrderBean.setProOrderState(productOrderDTOList.getProOrderState());
                        productOrderBean.setShowReceivedPrice(productOrderDTOList.getShowReceivedPrice());
                        productOrderBean.setShowMerGain(productOrderDTOList.getShowMerGain());
                        productOrderBean.setPosComments(productOrderDTOList.getPosComments());
                        productOrderBeanList.add(productOrderBean);
                    }
                }
                String code = ExcelUtil.excelExport(request, response, productOrderBeanList, index, "子商户一卡通充值订单汇总详细信息");
                excelExport.setCode(code);
            } else {
                String code = ExcelUtil.excelExport(request, response, productOrderBeanList, index, "子商户一卡通充值订单汇总详细信息");
                excelExport.setCode(code);
            }
        }
        catch (Exception e) {
            excelExport.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        return excelExport;
    }

    @Override
    public DodopalResponse<RechargeOrderBean> sumRechargeOrder(RechargeOrderQuery query) {
        DodopalResponse<RechargeOrderBean> dodopalResult = new DodopalResponse<RechargeOrderBean>();
        RechargeOrderQueryDTO queryDto = new RechargeOrderQueryDTO();
        try {
            PropertyUtils.copyProperties(queryDto, query);
            DodopalResponse<RechargeOrderDTO> dtoResult = productOrderDelegate.sumRechargeOrder(queryDto);
            if(dtoResult.isSuccessCode()){
                RechargeOrderBean orderBean = new RechargeOrderBean();
                if(null!=dtoResult.getResponseEntity()){
                    PropertyUtils.copyProperties(orderBean, dtoResult.getResponseEntity());
                    if(StringUtils.isNotBlank(orderBean.getSumAmt())){
                        orderBean.setSumAmt(String.format("%.2f",(double) Long.valueOf(orderBean.getSumAmt()) / 100));
                    }else{
                        orderBean.setSumAmt("0");
                    }
                }
                dodopalResult.setResponseEntity(orderBean);
            }
            dodopalResult.setCode(dtoResult.getCode());
        } catch (HessianRuntimeException e) {
            e.printStackTrace();
            log.error("调用产品库发生Hessian异常",e);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ProductOrderServiceImpl sumRechargeOrder异常",e);
        }
        return dodopalResult;
    }

    @Override
    public DodopalResponse<DodopalDataPage<RechargeOrderBean>> findRechargeOrderByPage(RechargeOrderQuery query) {
        DodopalResponse<DodopalDataPage<RechargeOrderBean>> dodopalResult = new DodopalResponse<DodopalDataPage<RechargeOrderBean>>();
        RechargeOrderQueryDTO queryDto = new RechargeOrderQueryDTO();
        try {
            PropertyUtils.copyProperties(queryDto, query);
            DodopalResponse<DodopalDataPage<RechargeOrderDTO>> dtoResult = productOrderDelegate.findRechargeOrderByPage(queryDto);
            if(dtoResult.isSuccessCode()){
                List<RechargeOrderBean> beanList = new ArrayList<RechargeOrderBean>();
                if(CollectionUtils.isNotEmpty(dtoResult.getResponseEntity().getRecords())){
                    for(RechargeOrderDTO orderDTO : dtoResult.getResponseEntity().getRecords()){
                        RechargeOrderBean orderBean = new RechargeOrderBean();
                        PropertyUtils.copyProperties(orderBean, orderDTO);
                        if(StringUtils.isNotBlank(orderBean.getTxnAmt())){
                            orderBean.setTxnAmt(String.format("%.2f",(double) Long.valueOf(orderBean.getTxnAmt()) / 100));

                        }
                        if(StringUtils.isNotBlank(orderBean.getReceivedPrice())){
                            orderBean.setReceivedPrice(String.format("%.2f",(double) Long.valueOf(orderBean.getReceivedPrice()) / 100));
                        }
                        if(StringUtils.isNotBlank(orderBean.getMerGain())){
                            orderBean.setMerGain(String.format("%.2f",(double) Long.valueOf(orderBean.getMerGain()) / 100));
                        }
                        beanList.add(orderBean);
                    }
                }
                PageParameter page = DodopalDataPageUtil.convertPageInfo(dtoResult.getResponseEntity());
                DodopalDataPage<RechargeOrderBean> pages = new DodopalDataPage<RechargeOrderBean>(page, beanList);
                dodopalResult.setResponseEntity(pages);
            }
            dodopalResult.setCode(dtoResult.getCode());
        } catch (HessianRuntimeException e) {
            e.printStackTrace();
            log.error("调用产品库发生Hessian异常",e);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ProductOrderServiceImpl findRechargeOrderByPage异常",e);
        }
        return dodopalResult;
    }


    @Override
    public DodopalResponse<PurchaseOrderBean> sumPurchaseOrder(PurchaseOrderQuery query) {
        DodopalResponse<PurchaseOrderBean> dodopalResult = new DodopalResponse<PurchaseOrderBean>();
        PurchaseOrderQueryDTO queryDto = new PurchaseOrderQueryDTO();
        try {
            PropertyUtils.copyProperties(queryDto, query);
            DodopalResponse<PurchaseOrderDTO> dtoResult = productOrderDelegate.sumPurchaseOrder(queryDto);
            if(dtoResult.isSuccessCode()){
                PurchaseOrderBean orderBean = new PurchaseOrderBean();
                if(null!=dtoResult.getResponseEntity()){
                    PropertyUtils.copyProperties(orderBean, dtoResult.getResponseEntity());
                    if(StringUtils.isNotBlank(orderBean.getSumAmt())){
                        orderBean.setSumAmt(String.format("%.2f",(double) Long.valueOf(orderBean.getSumAmt()) / 100));
                    }else{
                        orderBean.setSumAmt("0");
                    }
                }
                dodopalResult.setResponseEntity(orderBean);
            }
            dodopalResult.setCode(dtoResult.getCode());
        } catch (HessianRuntimeException e) {
            e.printStackTrace();
            log.error("调用产品库发生Hessian异常",e);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ProductOrderServiceImpl sumRechargeOrder异常",e);
        }
        return dodopalResult;
    }

    @Override
    public DodopalResponse<DodopalDataPage<PurchaseOrderBean>> findPurchaseOrderByPage(PurchaseOrderQuery query) {
        DodopalResponse<DodopalDataPage<PurchaseOrderBean>> dodopalResult = new DodopalResponse<DodopalDataPage<PurchaseOrderBean>>();
        PurchaseOrderQueryDTO queryDto = new PurchaseOrderQueryDTO();
        try {
            PropertyUtils.copyProperties(queryDto, query);
            DodopalResponse<DodopalDataPage<PurchaseOrderDTO>> dtoResult = productOrderDelegate.findPurchaseOrderByPage(queryDto);
            if(dtoResult.isSuccessCode()){
                List<PurchaseOrderBean> beanList = new ArrayList<PurchaseOrderBean>();
                if(CollectionUtils.isNotEmpty(dtoResult.getResponseEntity().getRecords())){
                    for(PurchaseOrderDTO orderDTO : dtoResult.getResponseEntity().getRecords()){
                        PurchaseOrderBean orderBean = new PurchaseOrderBean();
                        PropertyUtils.copyProperties(orderBean, orderDTO);
                        //应收金额
                        if(StringUtils.isNotBlank(orderBean.getOriginalPrice())){
                            orderBean.setOriginalPrice(String.format("%.2f",(double) Long.valueOf(orderBean.getOriginalPrice()) / 100));
                        }
                        //实收金额
                        if(StringUtils.isNotBlank(orderBean.getReceivedPrice())){
                            orderBean.setReceivedPrice(String.format("%.2f",(double) Long.valueOf(orderBean.getReceivedPrice()) / 100));
                        }
                        // 消费前金额
                        if(StringUtils.isNotBlank(orderBean.getBefbal())){
                            orderBean.setBefbal(String.format("%.2f",(double) Long.valueOf(orderBean.getBefbal()) / 100));
                        }
                        //消费后金额
                        if(StringUtils.isNotBlank(orderBean.getBlackAmt())){
                            orderBean.setBlackAmt(String.format("%.2f",(double) Long.valueOf(orderBean.getBlackAmt()) / 100));
                        }
                        
                        beanList.add(orderBean);
                    }
                }
                PageParameter page = DodopalDataPageUtil.convertPageInfo(dtoResult.getResponseEntity());
                DodopalDataPage<PurchaseOrderBean> pages = new DodopalDataPage<PurchaseOrderBean>(page, beanList);
                dodopalResult.setResponseEntity(pages);
            }
            dodopalResult.setCode(dtoResult.getCode());
        } catch (HessianRuntimeException e) {
            e.printStackTrace();
            log.error("调用产品库发生Hessian异常",e);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ProductOrderServiceImpl findRechargeOrderByPage异常",e);
        }
        return dodopalResult;   
    }

    @Override
    public DodopalResponse<String> exportFindRechargeOrder(HttpServletRequest request,HttpServletResponse response,RechargeOrderQuery query) {
        DodopalResponse<String> excelExport = new DodopalResponse<String>();
        try {
            RechargeOrderQueryDTO queryDTO = new RechargeOrderQueryDTO();
            PropertyUtils.copyProperties(queryDTO, query);
            List<RechargeOrderBean> exportList = new ArrayList<RechargeOrderBean>();
            DodopalResponse<List<RechargeOrderDTO>> dodopalResult = productOrderDelegate.findRechargeOrderForExport(queryDTO);
            Map<String, String> index = new LinkedHashMap<String, String>();
            index.put("proOrderNum", "订单编号");
            index.put("cityName", "城市名称");
            index.put("txnAmt", "充值金额（元）");
            index.put("receivedPrice", "实付金额（元）");
            index.put("merGain", "利润（元）");
            index.put("posCode", "POS号");
            index.put("proOrderStateStr", "订单状态");
            index.put("proOrderCreateDate", "订单创建时间");
            if (dodopalResult.isSuccessCode()) {
                if (CollectionUtils.isNotEmpty(dodopalResult.getResponseEntity())) {
                    for (RechargeOrderDTO orderDTO : dodopalResult.getResponseEntity()) {
                        RechargeOrderBean productOrderBean = new RechargeOrderBean();
                        PropertyUtils.copyProperties(productOrderBean, orderDTO);
                        
                        if(StringUtils.isNotBlank(productOrderBean.getTxnAmt())){
                            productOrderBean.setTxnAmt(String.format("%.2f",(double) Long.valueOf(productOrderBean.getTxnAmt()) / 100));
                        }
                        
                        if(StringUtils.isNotBlank(productOrderBean.getReceivedPrice())){
                            productOrderBean.setReceivedPrice(String.format("%.2f",(double) Long.valueOf(productOrderBean.getReceivedPrice()) / 100));
                        }
                        if(StringUtils.isNotBlank(productOrderBean.getMerGain())){
                            productOrderBean.setMerGain(String.format("%.2f",(double) Long.valueOf(productOrderBean.getMerGain()) / 100));
                        }
                        exportList.add(productOrderBean);
                    }
                }
                String code = ExcelUtil.excelExport(request, response, exportList, index, "网点一卡通充值订单汇总详细信息");
                excelExport.setCode(code);
            } else {
                String code = ExcelUtil.excelExport(request, response, exportList, index, "网点一卡通充值订单汇总详细信息");
                excelExport.setCode(code);
            }
        }
        catch (Exception e) {
            excelExport.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        return excelExport;
    }

    @Override
    public DodopalResponse<String> exportPurchaseOrder(HttpServletRequest request,HttpServletResponse response,PurchaseOrderQuery query) {
        DodopalResponse<String> excelExport = new DodopalResponse<String>();
        try {
            PurchaseOrderQueryDTO queryDTO = new PurchaseOrderQueryDTO();
            PropertyUtils.copyProperties(queryDTO, query);
            List<PurchaseOrderBean> exportList = new ArrayList<PurchaseOrderBean>();
            DodopalResponse<List<PurchaseOrderDTO>> dodopalResult = productOrderDelegate.findPurchaseOrderForExport(queryDTO);
            Map<String, String> index = new LinkedHashMap<String, String>();
            index.put("proOrderNum", "订单编号");
            index.put("cityName", "城市名称");
            index.put("cardNum", "卡号");
            index.put("posCode", "POS号");
            index.put("originalPrice", "应付金额（元）");
            index.put("receivedPrice", "实付金额（元）");
            index.put("befbal", "消费前金额（元）");
            index.put("blackAmt", "消费后金额（元）");
            index.put("proOrderStateStr", "订单状态");
            index.put("proOrderCreateDate", "订单创建时间");
            
            if (dodopalResult.isSuccessCode()) {
                if (CollectionUtils.isNotEmpty(dodopalResult.getResponseEntity())) {
                    for (PurchaseOrderDTO orderDTO : dodopalResult.getResponseEntity()) {
                        PurchaseOrderBean productOrderBean = new PurchaseOrderBean();
                        PropertyUtils.copyProperties(productOrderBean, orderDTO);
                        //应收金额
                        if(StringUtils.isNotBlank(productOrderBean.getOriginalPrice())){
                            productOrderBean.setOriginalPrice(String.format("%.2f",(double) Long.valueOf(productOrderBean.getOriginalPrice()) / 100));
                        }
                        //实收金额
                        if(StringUtils.isNotBlank(productOrderBean.getReceivedPrice())){
                            productOrderBean.setReceivedPrice(String.format("%.2f",(double) Long.valueOf(productOrderBean.getReceivedPrice()) / 100));
                        }
                        // 消费前金额
                        if(StringUtils.isNotBlank(productOrderBean.getBefbal())){
                            productOrderBean.setBefbal(String.format("%.2f",(double) Long.valueOf(productOrderBean.getBefbal()) / 100));
                        }
                        //消费后金额
                        if(StringUtils.isNotBlank(productOrderBean.getBlackAmt())){
                            productOrderBean.setBlackAmt(String.format("%.2f",(double) Long.valueOf(productOrderBean.getBlackAmt()) / 100));
                        }
                        exportList.add(productOrderBean);
                    }
                }
                String code = ExcelUtil.excelExport(request, response, exportList, index, "网点一卡通消费订单汇总详细信息");
                excelExport.setCode(code);
            } else {
                String code = ExcelUtil.excelExport(request, response, exportList, index, "网点一卡通消费订单汇总详细信息");
                excelExport.setCode(code);
            }
        }
        catch (Exception e) {
            excelExport.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        return excelExport;
    }

}
