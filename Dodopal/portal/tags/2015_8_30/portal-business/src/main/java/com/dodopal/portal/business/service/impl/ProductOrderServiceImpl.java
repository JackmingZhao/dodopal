package com.dodopal.portal.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.ProductOrderDTO;
import com.dodopal.api.product.dto.ProductOrderDetailDTO;
import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.portal.business.bean.ProductOrderBean;
import com.dodopal.portal.business.bean.ProductOrderDetailBean;
import com.dodopal.portal.business.bean.query.ProductOrderQueryBean;
import com.dodopal.portal.business.service.ProductOrderService;
import com.dodopal.portal.delegate.ProductOrderDelegate;
@Service
public class ProductOrderServiceImpl implements ProductOrderService {
    private final static Logger log = LoggerFactory.getLogger(ProductOrderServiceImpl.class);
    @Autowired
    ProductOrderDelegate productOrderDelegate;
    /**
     * 订单查询 产品库提供标准的订单查询页面给终端商户（各种网点）和个人。
     */
    @Override
    public DodopalResponse<DodopalDataPage<ProductOrderBean>> findProductOrderByPage(ProductOrderQueryBean prdOrderQuery) {
        log.info("input portal prdOrderQuery :"+prdOrderQuery);
        DodopalResponse<DodopalDataPage<ProductOrderBean>> response  = new DodopalResponse<DodopalDataPage<ProductOrderBean>>();
        ProductOrderQueryDTO proOrderQueryDto= new ProductOrderQueryDTO();
        //订单编号 文本框 精确  用户输入
        if (StringUtils.isNotBlank(prdOrderQuery.getProOrderNum())) {
            proOrderQueryDto.setProOrderNum(prdOrderQuery.getProOrderNum());
        }
        //订单外部状态
        if (StringUtils.isNotBlank(prdOrderQuery.getProOrderState())) {
            proOrderQueryDto.setProOrderState(prdOrderQuery.getProOrderState());
        }
        //订单创建时间 日历选择框 范围  用户选择
        if (prdOrderQuery.getOrderDateStart()!=null) {
            proOrderQueryDto.setOrderDateStart(prdOrderQuery.getOrderDateStart());
        }
        if (prdOrderQuery.getOrderDateEnd()!=null) {
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
        if (prdOrderQuery.getTxnAmtStart()!=null) {
            proOrderQueryDto.setTxnAmtStart(prdOrderQuery.getTxnAmtStart());;
        }
        if (prdOrderQuery.getTxnAmtEnd()!=null) {
            proOrderQueryDto.setTxnAmtEnd(prdOrderQuery.getTxnAmtEnd());;
        }
        //POS机编号
        if (StringUtils.isNotBlank(prdOrderQuery.getPosCode())) {
            proOrderQueryDto.setPosCode(prdOrderQuery.getPosCode());
        }
        //外部订单号
        if (StringUtils.isNotBlank(prdOrderQuery.getMerOrderNum())) {
            proOrderQueryDto.setCityName(prdOrderQuery.getMerOrderNum());
        }
        //分页参数
        if (prdOrderQuery.getPage() != null) {
            proOrderQueryDto.setPage(prdOrderQuery.getPage());;
        }
        DodopalResponse<DodopalDataPage<ProductOrderDTO>> proDTO = productOrderDelegate.findProductOrderByPage(proOrderQueryDto);
        List<ProductOrderBean> productOrderBeanList = new ArrayList<ProductOrderBean>();
        if (ResponseCode.SUCCESS.equals(proDTO.getCode())) {
            if (proDTO.getResponseEntity() != null && CollectionUtils.isNotEmpty(proDTO.getResponseEntity().getRecords())) {
            for(ProductOrderDTO productOrderDTOList : proDTO.getResponseEntity().getRecords() ){
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
           
            log.info(" return code:"+proDTO.getCode());
            //后台传入总页数，总条数，当前页
            PageParameter page = DodopalDataPageUtil.convertPageInfo(proDTO.getResponseEntity());
            DodopalDataPage<ProductOrderBean> pages = new DodopalDataPage<ProductOrderBean>(page, productOrderBeanList);
            response.setCode(proDTO.getCode());
            response.setResponseEntity(pages);
        }else {
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

}
