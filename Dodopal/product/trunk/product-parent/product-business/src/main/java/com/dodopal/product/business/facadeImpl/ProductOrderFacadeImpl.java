package com.dodopal.product.business.facadeImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.ProductConsumeOrderDTO;
import com.dodopal.api.product.dto.ProductConsumeOrderDetailDTO;
import com.dodopal.api.product.dto.ProductConsumerOrderForExport;
import com.dodopal.api.product.dto.ProductOrderDTO;
import com.dodopal.api.product.dto.ProductOrderDetailDTO;
import com.dodopal.api.product.dto.ProductOrderForExport;
import com.dodopal.api.product.dto.PurchaseOrderDTO;
import com.dodopal.api.product.dto.RechargeOrderDTO;
import com.dodopal.api.product.dto.query.ProductConsumeOrderQueryDTO;
import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.api.product.dto.query.PurchaseOrderQueryDTO;
import com.dodopal.api.product.dto.query.RechargeOrderQueryDTO;
import com.dodopal.api.product.facade.ProductOrderFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.service.ProductOrderForPortalService;
import com.dodopal.product.business.service.ProductOrderService;

@Service("productOrderFacade")
public class ProductOrderFacadeImpl implements ProductOrderFacade {
    
    private static Logger logger = Logger.getLogger(ProductOrderFacadeImpl.class);
    
    @Autowired
    private ProductOrderService productOrderService;
    @Autowired
    private ProductOrderForPortalService productOrderForPortalService;

    /**
     * 5.2 产品库中公交卡充值订单 --5.2.1 订单查询 产品库提供标准的订单查询页面给终端商户（各种网点）和个人。
     */
    @Override
    public DodopalResponse<DodopalDataPage<ProductOrderDTO>> findProductOrder(ProductOrderQueryDTO prdOrderQuery) {
        DodopalResponse<DodopalDataPage<ProductOrderDTO>> response = null;
        try {
            if (prdOrderQuery == null) {
                prdOrderQuery = new ProductOrderQueryDTO();
            }
            if (prdOrderQuery.getPage() == null) {
                prdOrderQuery.setPage(new PageParameter());
            }
            response = productOrderService.findProductOrderByPage(prdOrderQuery);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            response = new DodopalResponse<DodopalDataPage<ProductOrderDTO>>();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<List<ProductOrderForExport>> findProductOrdersForExport(ProductOrderQueryDTO prdOrderQuery) {
        DodopalResponse<List<ProductOrderForExport>> response = null;
        try {
            response = productOrderService.findProductOrdersForExport(prdOrderQuery);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            response = new DodopalResponse<List<ProductOrderForExport>>();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    /**
     * 充值异常订单导出
     */
    @Override
    public DodopalResponse<List<ProductOrderDTO>> findProductExceptionOrdersForExport(ProductOrderQueryDTO prdOrderQuery) {
        DodopalResponse<List<ProductOrderDTO>> response = null;
        try {
            response = productOrderService.findProductExceptionOrdersForExport(prdOrderQuery);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            response = new DodopalResponse<List<ProductOrderDTO>>();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
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
    public DodopalResponse<ProductOrderDetailDTO> findProductOrderDetails(String proOrderNum) {
        DodopalResponse<ProductOrderDetailDTO> response = null;
        try {
            response = productOrderService.findProductOrderDetails(proOrderNum);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            response = new DodopalResponse<ProductOrderDetailDTO>();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<DodopalDataPage<ProductOrderDTO>> findChildMerProductOrder(ProductOrderQueryDTO prdOrderQuery) {
        DodopalResponse<DodopalDataPage<ProductOrderDTO>> response = null;
        try {
            if (prdOrderQuery == null) {
                prdOrderQuery = new ProductOrderQueryDTO();
            }
            if (prdOrderQuery.getPage() == null) {
                prdOrderQuery.setPage(new PageParameter());
            }
            response = productOrderService.findChildMerProductOrdersByPage(prdOrderQuery);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            response = new DodopalResponse<DodopalDataPage<ProductOrderDTO>>();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public List<ProductOrderDTO> findChildMerProductOrderExcel(ProductOrderQueryDTO prdOrderQuery) {
        List<ProductOrderDTO> listDto = new ArrayList<ProductOrderDTO>();
        try {
            listDto = productOrderService.findChildMerProductOrderExcel(prdOrderQuery);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return listDto;
    }

    /**
     * 查询 一卡通消费 收单记录
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<DodopalDataPage<ProductConsumeOrderDTO>> findProductConsumeOrderByPage(ProductConsumeOrderQueryDTO prdOrderQuery) {
        DodopalResponse<DodopalDataPage<ProductConsumeOrderDTO>> response = new DodopalResponse<DodopalDataPage<ProductConsumeOrderDTO>>();
        try {
            response = productOrderService.findProductConsumeOrderByPage(prdOrderQuery);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return response;
    }

    /**
     * 根据消费订单号 orderNum 查询一卡通消费订单详情
     * @param orderNum 订单号
     * @return
     */
    public DodopalResponse<ProductConsumeOrderDetailDTO> findProductConsumeOrderDetails(String orderNum) {
        DodopalResponse<ProductConsumeOrderDetailDTO> response = new DodopalResponse<ProductConsumeOrderDetailDTO>();
        try {
            response = productOrderService.findProductConsumeOrderDetails(orderNum);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("产品库中公交卡 消费订单查询出错", e);
            response.setCode(ResponseCode.PRODUCT_ORDER_QUERY_ERROR);
        }
        return response;
    }

    /**
     * 异常充值订单查询分页 add by shenXiang 2015-11-05
     */
    @Override
    public DodopalResponse<DodopalDataPage<ProductOrderDTO>> findExceptionProductOrderByPage(ProductOrderQueryDTO prdOrderQuery) {
        DodopalResponse<DodopalDataPage<ProductOrderDTO>> response = new DodopalResponse<DodopalDataPage<ProductOrderDTO>>();
        try {
            if (prdOrderQuery == null) {
                prdOrderQuery = new ProductOrderQueryDTO();
            }
            if (prdOrderQuery.getPage() == null) {
                prdOrderQuery.setPage(new PageParameter());
            }
            response = productOrderService.findExceptionProductOrderByPage(prdOrderQuery);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("异常充值订单查询分页出错", e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<DodopalDataPage<ProductConsumeOrderDTO>> findProductConsumeOrderExptionByList(ProductConsumeOrderQueryDTO prdOrderQuery) {
        DodopalResponse<DodopalDataPage<ProductConsumeOrderDTO>> response = new DodopalResponse<DodopalDataPage<ProductConsumeOrderDTO>>();
        try {
            response = productOrderService.findProductConsumeOrdersExptionByPage(prdOrderQuery);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return response;
    }

    /**
     * 导出异常信息记录
     */
    @Override
    public DodopalResponse<List<ProductConsumeOrderDTO>> excelExceptionProductOrderExport(ProductConsumeOrderQueryDTO prdOrderQuery) {
        DodopalResponse<List<ProductConsumeOrderDTO>> response = new DodopalResponse<List<ProductConsumeOrderDTO>>();
        try {
            response = productOrderService.excelExceptionProductOrderExport(prdOrderQuery);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return response;
    }

    /**
     *  导出查询 一卡通消费 收单记录 
     */
    @Override
    public DodopalResponse<List<ProductConsumerOrderForExport>> getConsumerOrderListForExportExcel(ProductConsumeOrderQueryDTO prdOrderQuery) {
        DodopalResponse<List<ProductConsumerOrderForExport>> response = new DodopalResponse<List<ProductConsumerOrderForExport>>();
        try {
            response = productOrderService.getConsumerOrderListForExportExcel(prdOrderQuery);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("产品库中公交卡 消费订单查询导出出错", e);
            response.setCode(ResponseCode.PRODUCT_ORDER_QUERY_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<DodopalDataPage<ProductOrderDTO>> findRechargeProductOrderByPage(ProductOrderQueryDTO prdOrderQuery) {
        DodopalResponse<DodopalDataPage<ProductOrderDTO>> response = null;
        try {
            if (prdOrderQuery == null) {
                prdOrderQuery = new ProductOrderQueryDTO();
            }
            if (prdOrderQuery.getPage() == null) {
                prdOrderQuery.setPage(new PageParameter());
            }
            response = productOrderService.findRechargeProductOrderByPage(prdOrderQuery);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            response = new DodopalResponse<DodopalDataPage<ProductOrderDTO>>();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<RechargeOrderDTO> sumRechargeOrder(RechargeOrderQueryDTO queryDto) {
        DodopalResponse<RechargeOrderDTO> response = productOrderForPortalService.sumRechargeOrder(queryDto);
        return response;
    }

    @Override
    public DodopalResponse<DodopalDataPage<RechargeOrderDTO>> findRechargeOrderByPage(RechargeOrderQueryDTO queryDto) {
        DodopalResponse<DodopalDataPage<RechargeOrderDTO>> response = productOrderForPortalService.findRechargeOrderByPage(queryDto);
        return response;
    }

    @Override
    public DodopalResponse<List<RechargeOrderDTO>> findRechargeOrderForExport(RechargeOrderQueryDTO queryDto) {
        DodopalResponse<List<RechargeOrderDTO>> response = productOrderForPortalService.findRechargeOrderForExport(queryDto);
        return response;
    }

    @Override
    public DodopalResponse<PurchaseOrderDTO> sumPurchaseOrder(PurchaseOrderQueryDTO queryDto) {
        DodopalResponse<PurchaseOrderDTO> response = productOrderForPortalService.sumPurchaseOrder(queryDto);
        return response;
    }

    @Override
    public DodopalResponse<DodopalDataPage<PurchaseOrderDTO>> findPurchaseOrderByPage(PurchaseOrderQueryDTO queryDto) {
        DodopalResponse<DodopalDataPage<PurchaseOrderDTO>> response = productOrderForPortalService.findPurchaseOrderByPage(queryDto);
        return response;
    }

    @Override
    public DodopalResponse<List<PurchaseOrderDTO>> findPurchaseOrderForExport(PurchaseOrderQueryDTO queryDto) {
        DodopalResponse<List<PurchaseOrderDTO>> response = productOrderForPortalService.findPurchaseOrderForExport(queryDto);
        return response;
    }

}
