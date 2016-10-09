package com.dodopal.portal.business.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.product.dto.ChargeOrderDTO;
import com.dodopal.api.product.dto.ConsumptionOrderCountDTO;
import com.dodopal.api.product.dto.ConsumptionOrderDTO;
import com.dodopal.api.product.dto.query.ChargeOrderQueryDTO;
import com.dodopal.api.product.dto.query.ConsumptionOrderQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.portal.business.bean.ChargeOrder;
import com.dodopal.portal.business.bean.ConsumptionOrder;
import com.dodopal.portal.business.bean.ConsumptionOrderCount;
import com.dodopal.portal.business.service.MerJointQueryService;
import com.dodopal.portal.delegate.MerJointQueryDelegate;
@Service("merJointQueryService")
public class MerJointQueryServiceImpl implements MerJointQueryService{
    private final static Logger log = LoggerFactory.getLogger(MerJointQueryServiceImpl.class);

    @Autowired
    MerJointQueryDelegate merJointQueryDelegate;

    @Override
    public DodopalResponse<DodopalDataPage<ChargeOrder>> findChargeOrderByPage(ChargeOrderQueryDTO queryDTO) {
        DodopalResponse<DodopalDataPage<ChargeOrder>> response = new DodopalResponse<DodopalDataPage<ChargeOrder>>();

        try{
            DodopalResponse<DodopalDataPage<ChargeOrderDTO>> dtoResult = merJointQueryDelegate.findChargeOrderByPage(queryDTO);
            if(dtoResult.isSuccessCode()){
                List<ChargeOrder> beanList = new ArrayList<ChargeOrder>();
                if(CollectionUtils.isNotEmpty(dtoResult.getResponseEntity().getRecords())){
                     for(ChargeOrderDTO tempDTO:dtoResult.getResponseEntity().getRecords()){
                         ChargeOrder order = new ChargeOrder();
                         PropertyUtils.copyProperties(order, tempDTO);
                         beanList.add(order);
                      }
                     
                }
                PageParameter page =  DodopalDataPageUtil.convertPageInfo(dtoResult.getResponseEntity());
                DodopalDataPage<ChargeOrder> pages = new DodopalDataPage<ChargeOrder>(page, beanList);
                response.setResponseEntity(pages);
             }
            response.setCode(dtoResult.getCode());
        }catch(HessianRuntimeException e){
            e.printStackTrace();
            log.error("查询商户充值订单（联合查询历史交易）发生Hessian异常",e);
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }catch(Exception e){
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            log.error("查询商户充值订单（联合查询历史交易）",e);
        }
        return response;
    }

    @Override
    public DodopalResponse<DodopalDataPage<ConsumptionOrder>> findConsumptionOrderByPage(ConsumptionOrderQueryDTO queryDTO) {
        DodopalResponse<DodopalDataPage<ConsumptionOrder>> response = new DodopalResponse<DodopalDataPage<ConsumptionOrder>>();

        try{
            DodopalResponse<DodopalDataPage<ConsumptionOrderDTO>> dtoResult = merJointQueryDelegate.findConsumptionOrderByPage(queryDTO);
            if(dtoResult.isSuccessCode()){
                List<ConsumptionOrder> beanList = new ArrayList<ConsumptionOrder>();
                if(CollectionUtils.isNotEmpty(dtoResult.getResponseEntity().getRecords())){
                     for(ConsumptionOrderDTO tempDTO:dtoResult.getResponseEntity().getRecords()){
                         ConsumptionOrder order = new ConsumptionOrder();
                         PropertyUtils.copyProperties(order, tempDTO);
                         beanList.add(order);
                      }
                     
                }
                PageParameter page =  DodopalDataPageUtil.convertPageInfo(dtoResult.getResponseEntity());
                DodopalDataPage<ConsumptionOrder> pages = new DodopalDataPage<ConsumptionOrder>(page, beanList);
                response.setResponseEntity(pages);
             }
            response.setCode(dtoResult.getCode());
        }catch(HessianRuntimeException e){
            e.printStackTrace();
            log.error("查询商户消费订单（联合查询历史交易）发生Hessian异常",e);
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }catch(Exception e){
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            log.error("查询商户消费订单（联合查询历史交易）",e);
        }
        return response;
    }

    @Override
    public DodopalResponse<DodopalDataPage<ConsumptionOrderCount>> findConsumptionOrderCountByPage(ChargeOrderQueryDTO queryDTO) {
        
        DodopalResponse<DodopalDataPage<ConsumptionOrderCount>> response = new DodopalResponse<DodopalDataPage<ConsumptionOrderCount>>();

        try{
            DodopalResponse<DodopalDataPage<ConsumptionOrderCountDTO>> dtoResult = merJointQueryDelegate.findConsumptionOrderCountByPage(queryDTO);
            DodopalResponse<ConsumptionOrderCount> count = findConsumptionOrderCount(queryDTO);
            if(dtoResult.isSuccessCode()){
                List<ConsumptionOrderCount> beanList = new ArrayList<ConsumptionOrderCount>();
                if(CollectionUtils.isNotEmpty(dtoResult.getResponseEntity().getRecords())){
                     for(ConsumptionOrderCountDTO tempDTO:dtoResult.getResponseEntity().getRecords()){
                         ConsumptionOrderCount order = new ConsumptionOrderCount();
                         PropertyUtils.copyProperties(order, tempDTO);
                         order.setCount(count.getResponseEntity());
                         beanList.add(order);
                      }
                }
                PageParameter page =  DodopalDataPageUtil.convertPageInfo(dtoResult.getResponseEntity());
                DodopalDataPage<ConsumptionOrderCount> pages = new DodopalDataPage<ConsumptionOrderCount>(page, beanList);
                response.setResponseEntity(pages);
             }
            response.setCode(dtoResult.getCode());
        }catch(HessianRuntimeException e){
            e.printStackTrace();
            log.error("查询商户消费统计（联合查询历史交易）发生Hessian异常",e);
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }catch(Exception e){
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            log.error("查询商户消费统计（联合查询历史交易）",e);
        }
        return response;
    }

    @Override
    public DodopalResponse<ConsumptionOrderCount> findConsumptionOrderCount(ChargeOrderQueryDTO queryDTO) {
        DodopalResponse<ConsumptionOrderCount> response = new DodopalResponse<ConsumptionOrderCount>();

        try{
            DodopalResponse<ConsumptionOrderCountDTO> dtoResult = merJointQueryDelegate.findConsumptionOrderCount(queryDTO);
            if(dtoResult.isSuccessCode()){
                ConsumptionOrderCount order = new ConsumptionOrderCount();
                PropertyUtils.copyProperties(order, dtoResult.getResponseEntity());
                response.setResponseEntity(order);
             }
            response.setCode(dtoResult.getCode());
        }catch(HessianRuntimeException e){
            e.printStackTrace();
            log.error("查询商户消费统计合计（联合查询历史交易）发生Hessian异常",e);
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }catch(Exception e){
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            log.error("查询商户消费统计合计（联合查询历史交易）",e);
        }
        return response;
    }

    public DodopalResponse<String> exportChargeOrder(HttpServletRequest request,HttpServletResponse response,ChargeOrderQueryDTO queryDTO) {
        DodopalResponse<List<ChargeOrderDTO>> resultDodopal = merJointQueryDelegate.exportChargeOrder(queryDTO);
        List<ChargeOrderDTO> tranDTO = resultDodopal.getResponseEntity();
        List<ChargeOrder> tranList = new ArrayList<ChargeOrder>();
        DodopalResponse<String> excelExport = new DodopalResponse<String>();
        try {
            if(resultDodopal.isSuccessCode()){
                if(CollectionUtils.isNotEmpty(tranDTO)){
                    for(ChargeOrderDTO dto : tranDTO){
                        ChargeOrder bean = new ChargeOrder();
                        PropertyUtils.copyProperties(bean, dto);
                        tranList.add(bean);
                    }
                }
                Map<String, String> index = new LinkedHashMap<String, String>();
                index.put("transaction_id", "订单号");
                index.put("mchnitname", "商户名称");
                index.put("username", "用户名称");
                index.put("cardno", "卡号");
                index.put("posid", "POS号");
                index.put("amt", "交易金额");
                index.put("paidamt", "实收金额");
                index.put("rebatesamt", "返利金额");
                index.put("sendtime", "订单时间");
                index.put("statusStr", "订单状态");
                String code = ExcelUtil.excelExport(request, response, tranList, index, "一卡通充值");
                excelExport.setCode(code);
            }else{
                excelExport.setCode(resultDodopal.getCode());
            }
        } catch (Exception e) {
            excelExport.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        return excelExport;
    }

    @Override
    public DodopalResponse<String> exportConsumptionOrder(HttpServletRequest request,HttpServletResponse response,ConsumptionOrderQueryDTO queryDTO) {
        DodopalResponse<List<ConsumptionOrderDTO>> resultDodopal = merJointQueryDelegate.exportConsumptionOrder(queryDTO);
        List<ConsumptionOrderDTO> orderDTO = resultDodopal.getResponseEntity();
        List<ConsumptionOrder> orderList = new ArrayList<ConsumptionOrder>();
        DodopalResponse<String> excelExport = new DodopalResponse<String>();
        try {
            if(resultDodopal.isSuccessCode()){
                if(CollectionUtils.isNotEmpty(orderDTO)){
                    for(ConsumptionOrderDTO dto : orderDTO){
                        ConsumptionOrder bean = new ConsumptionOrder();
                        PropertyUtils.copyProperties(bean, dto);
                        orderList.add(bean);
                    }
                }
                Map<String, String> index = new LinkedHashMap<String, String>();
                index.put("orderid", "订单号");
                index.put("mchnitname", "商户名称");
                index.put("username", "用户名称");
                index.put("cardno", "卡号");
                index.put("posid", "POS号");
                index.put("proamt", "交易前金额");
                index.put("facevalue", "应收金额");
                index.put("sale", "用户折扣");
                index.put("amt", "实收金额");
                index.put("blackamt", "交易后余额");
                index.put("setsale", "结算折扣");
                index.put("setamt", "结算金额");
                index.put("setfee", "结算手续");
                index.put("checkcarddate", "交易时间");
                index.put("status", "订单状态");
                
                String code = ExcelUtil.excelExport(request, response, orderList, index, "一卡通消费");
                excelExport.setCode(code);
            }else{
                excelExport.setCode(resultDodopal.getCode());
            }
        } catch (Exception e) {
            excelExport.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        return excelExport;
    }

    @Override
    public DodopalResponse<String> exportConsumptionOrderCount(HttpServletRequest request,HttpServletResponse response,ChargeOrderQueryDTO queryDTO) {
        DodopalResponse<List<ConsumptionOrderCountDTO>> resultDodopal = merJointQueryDelegate.exportConsumptionOrderCount(queryDTO);
        List<ConsumptionOrderCountDTO> orderDTO = resultDodopal.getResponseEntity();
        List<ConsumptionOrderCount> orderList = new ArrayList<ConsumptionOrderCount>();
        DodopalResponse<String> excelExport = new DodopalResponse<String>();
        try {
            if(resultDodopal.isSuccessCode()){
                if(CollectionUtils.isNotEmpty(orderDTO)){
                    for(ConsumptionOrderCountDTO dto : orderDTO){
                        ConsumptionOrderCount bean = new ConsumptionOrderCount();
                        PropertyUtils.copyProperties(bean, dto);
                        orderList.add(bean);
                    }
                }
                Map<String, String> index = new LinkedHashMap<String, String>();
                index.put("username", "用户名称");
                index.put("jiaoyichenggongbishu", "交易成功笔数");
                index.put("jiaoyichenggongzongjine", "交易成功总金额");
                index.put("shishouzongjine", "实收总金额");
                index.put("shishouzongjine", "结算总金额");
                index.put("jiesuanzongshouxufei", "结算总手续费");
                String code = ExcelUtil.excelExport(request, response, orderList, index, "一卡通消费统计");
                excelExport.setCode(code);
             }else{
                 excelExport.setCode(resultDodopal.getCode());
             }
        } catch (Exception e) {
            excelExport.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        return excelExport;
    }

}
