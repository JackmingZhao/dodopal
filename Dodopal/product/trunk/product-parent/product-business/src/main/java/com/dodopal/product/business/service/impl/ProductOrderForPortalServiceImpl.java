package com.dodopal.product.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.api.product.dto.PurchaseOrderDTO;
import com.dodopal.api.product.dto.RechargeOrderDTO;
import com.dodopal.api.product.dto.query.PurchaseOrderQueryDTO;
import com.dodopal.api.product.dto.query.RechargeOrderQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.dao.ProductOrderForPortalMapper;
import com.dodopal.product.business.model.PurchaseOrder;
import com.dodopal.product.business.model.RechargeOrder;
import com.dodopal.product.business.model.query.PurchaseOrderQuery;
import com.dodopal.product.business.model.query.RechargeOrderQuery;
import com.dodopal.product.business.service.ProductOrderForPortalService;

/**
 *  门户专用：产品库公交卡充值订单ServiceImpl
 * @author dodopal
 */
@Service
public class ProductOrderForPortalServiceImpl implements ProductOrderForPortalService {

    private Logger logger = LoggerFactory.getLogger(ProductOrderForPortalServiceImpl.class);

    @Autowired
    private ProductOrderForPortalMapper productOrderForPortalMapper;

    @Override
    @Transactional(readOnly = true)
    public DodopalResponse<RechargeOrderDTO> sumRechargeOrder(RechargeOrderQueryDTO queryDto) {
        DodopalResponse<RechargeOrderDTO> response = new DodopalResponse<RechargeOrderDTO>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            RechargeOrderQuery query = new RechargeOrderQuery();
            if (queryDto != null) {
                PropertyUtils.copyProperties(query, queryDto);
            }
            RechargeOrder rechargeOrder = productOrderForPortalMapper.sumRechargeOrder(query);
            RechargeOrderDTO responseDTO = new RechargeOrderDTO();
            if (rechargeOrder != null) {
                PropertyUtils.copyProperties(responseDTO, rechargeOrder);
                response.setResponseEntity(responseDTO);
            }
        }
        catch (Exception e) {
            logger.error("门户查询网点充值订单汇总信息出错", e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public DodopalResponse<DodopalDataPage<RechargeOrderDTO>> findRechargeOrderByPage(RechargeOrderQueryDTO queryDto) {
        DodopalResponse<DodopalDataPage<RechargeOrderDTO>> response = new DodopalResponse<DodopalDataPage<RechargeOrderDTO>>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            RechargeOrderQuery query = new RechargeOrderQuery();
            if (queryDto != null) {
                PropertyUtils.copyProperties(query, queryDto);
            }
            List<RechargeOrder> rechargeOrderList = productOrderForPortalMapper.findRechargeOrderByPage(query);
            List<RechargeOrderDTO> orderList = new ArrayList<RechargeOrderDTO>();
            if (CollectionUtils.isNotEmpty(rechargeOrderList)) {
                for (RechargeOrder order : rechargeOrderList) {
                    RechargeOrderDTO dto = new RechargeOrderDTO();
                    PropertyUtils.copyProperties(dto, order);
                    orderList.add(dto);
                }
            }
            DodopalDataPage<RechargeOrderDTO> pages = new DodopalDataPage<RechargeOrderDTO>(queryDto.getPage(), orderList);
            response.setResponseEntity(pages);
        }
        catch (Exception e) {
            logger.error("门户查询网点充值订单详细信息出错", e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public DodopalResponse<List<RechargeOrderDTO>> findRechargeOrderForExport(RechargeOrderQueryDTO queryDto) {
        DodopalResponse<List<RechargeOrderDTO>> response = new DodopalResponse<List<RechargeOrderDTO>>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            RechargeOrderQuery query = new RechargeOrderQuery();
            if (queryDto != null) {
                PropertyUtils.copyProperties(query, queryDto);
            }
            List<RechargeOrder> rechargeOrderList = productOrderForPortalMapper.findRechargeOrderForExport(query);
            List<RechargeOrderDTO> orderList = new ArrayList<RechargeOrderDTO>();
            if (CollectionUtils.isNotEmpty(rechargeOrderList)) {
                for (RechargeOrder order : rechargeOrderList) {
                    RechargeOrderDTO dto = new RechargeOrderDTO();
                    PropertyUtils.copyProperties(dto, order);
                    orderList.add(dto);
                }
            }
            response.setResponseEntity(orderList);
        }
        catch (Exception e) {
            logger.error("门户查询网点充值订单导出详细信息出错", e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public DodopalResponse<PurchaseOrderDTO> sumPurchaseOrder(PurchaseOrderQueryDTO queryDto) {
        DodopalResponse<PurchaseOrderDTO> response = new DodopalResponse<PurchaseOrderDTO>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            PurchaseOrderQuery query = new PurchaseOrderQuery();
            if (queryDto != null) {
                PropertyUtils.copyProperties(query, queryDto);
            }
            PurchaseOrder purchaseOrder = productOrderForPortalMapper.sumPurchaseOrder(query);
            PurchaseOrderDTO responseDTO = new PurchaseOrderDTO();
            if (purchaseOrder != null) {
                PropertyUtils.copyProperties(responseDTO, purchaseOrder);
                response.setResponseEntity(responseDTO);
            }
        }
        catch (Exception e) {
            logger.error("门户查询网点消费订单汇总信息出错", e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public DodopalResponse<DodopalDataPage<PurchaseOrderDTO>> findPurchaseOrderByPage(PurchaseOrderQueryDTO queryDto) {
        DodopalResponse<DodopalDataPage<PurchaseOrderDTO>> response = new DodopalResponse<DodopalDataPage<PurchaseOrderDTO>>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            PurchaseOrderQuery query = new PurchaseOrderQuery();
            if (queryDto != null) {
                PropertyUtils.copyProperties(query, queryDto);
            }
            List<PurchaseOrder> PurchaseOrderList = productOrderForPortalMapper.findPurchaseOrderByPage(query);
            List<PurchaseOrderDTO> orderList = new ArrayList<PurchaseOrderDTO>();
            if (CollectionUtils.isNotEmpty(PurchaseOrderList)) {
                for (PurchaseOrder order : PurchaseOrderList) {
                    PurchaseOrderDTO dto = new PurchaseOrderDTO();
                    PropertyUtils.copyProperties(dto, order);
                    orderList.add(dto);
                }
            }
            DodopalDataPage<PurchaseOrderDTO> pages = new DodopalDataPage<PurchaseOrderDTO>(queryDto.getPage(), orderList);
            response.setResponseEntity(pages);
        }
        catch (Exception e) {
            logger.error("门户查询网点消费订单详细信息出错", e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public DodopalResponse<List<PurchaseOrderDTO>> findPurchaseOrderForExport(PurchaseOrderQueryDTO queryDto) {
        DodopalResponse<List<PurchaseOrderDTO>> response = new DodopalResponse<List<PurchaseOrderDTO>>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            PurchaseOrderQuery query = new PurchaseOrderQuery();
            if (queryDto != null) {
                PropertyUtils.copyProperties(query, queryDto);
            }
            List<PurchaseOrder> purchaseOrderList = productOrderForPortalMapper.findPurchaseOrderForExport(query);
            List<PurchaseOrderDTO> orderList = new ArrayList<PurchaseOrderDTO>();
            if (CollectionUtils.isNotEmpty(purchaseOrderList)) {
                for (PurchaseOrder order : purchaseOrderList) {
                    PurchaseOrderDTO dto = new PurchaseOrderDTO();
                    PropertyUtils.copyProperties(dto, order);
                    orderList.add(dto);
                }
            }
            response.setResponseEntity(orderList);
        }
        catch (Exception e) {
            logger.error("门户查询网点消费订单导出详细信息出错", e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
}
