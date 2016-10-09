package com.dodopal.product.business.facadeImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.ChargeOrderDTO;
import com.dodopal.api.product.dto.ConsumptionOrderCountDTO;
import com.dodopal.api.product.dto.ConsumptionOrderDTO;
import com.dodopal.api.product.dto.query.ChargeOrderQueryDTO;
import com.dodopal.api.product.dto.query.ConsumptionOrderQueryDTO;
import com.dodopal.api.product.facade.MerJointQueryFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.product.business.model.ChargeOrder;
import com.dodopal.product.business.model.ConsumptionOrder;
import com.dodopal.product.business.model.ConsumptionOrderCount;
import com.dodopal.product.business.model.query.ChargeOrderQuery;
import com.dodopal.product.business.model.query.ConsumptionOrderQuery;
import com.dodopal.product.business.service.MerJointQueryService;
@Service("merJointQueryFacade")
public class MerJointQueryFacadeImpl implements MerJointQueryFacade {
    
    @Autowired
    MerJointQueryService merjointQueryService;

    @Override
    public DodopalResponse<DodopalDataPage<ChargeOrderDTO>> findChargeOrderByPage(ChargeOrderQueryDTO queryDTO) {
        ChargeOrderQuery  query = new ChargeOrderQuery();
        DodopalResponse<DodopalDataPage<ChargeOrderDTO>> response = new DodopalResponse<DodopalDataPage<ChargeOrderDTO>>();
        List <ChargeOrderDTO> orderList = new ArrayList<ChargeOrderDTO>();
        try {
            checkMerCode(queryDTO);
            PropertyUtils.copyProperties(query, queryDTO);
            DodopalDataPage<ChargeOrder> result = merjointQueryService.findChargeOrderByPage(query);
            if(CollectionUtils.isNotEmpty(result.getRecords())){
                for(ChargeOrder temp :result.getRecords()){
                    ChargeOrderDTO dto = new ChargeOrderDTO();
                    PropertyUtils.copyProperties(dto, temp);
                    orderList.add(dto);
                }
            }
            PageParameter page =  DodopalDataPageUtil.convertPageInfo(result);
            DodopalDataPage<ChargeOrderDTO> pages = new DodopalDataPage<ChargeOrderDTO>(page, orderList);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(pages);
        }catch(DDPException e){
            response.setCode(e.getCode());
            e.printStackTrace();
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public DodopalResponse<DodopalDataPage<ConsumptionOrderDTO>> findConsumptionOrderByPage(ConsumptionOrderQueryDTO queryDTO) {
        ConsumptionOrderQuery  query = new ConsumptionOrderQuery();
        DodopalResponse<DodopalDataPage<ConsumptionOrderDTO>> response = new DodopalResponse<DodopalDataPage<ConsumptionOrderDTO>>();
        List <ConsumptionOrderDTO> orderList = new ArrayList<ConsumptionOrderDTO>();
        try {
            checkMerCode(queryDTO);
            PropertyUtils.copyProperties(query, queryDTO);
            DodopalDataPage<ConsumptionOrder> result = merjointQueryService.findConsumptionOrderByPage(query);
            if(CollectionUtils.isNotEmpty(result.getRecords())){
                for(ConsumptionOrder temp :result.getRecords()){
                    ConsumptionOrderDTO dto = new ConsumptionOrderDTO();
                    PropertyUtils.copyProperties(dto, temp);
                    orderList.add(dto);
                }
            }
            PageParameter page =  DodopalDataPageUtil.convertPageInfo(result);
            DodopalDataPage<ConsumptionOrderDTO> pages = new DodopalDataPage<ConsumptionOrderDTO>(page, orderList);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(pages);
        }catch(DDPException e){
            response.setCode(e.getCode());
            e.printStackTrace();
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public DodopalResponse<DodopalDataPage<ConsumptionOrderCountDTO>> findConsumptionOrderCountByPage(ChargeOrderQueryDTO queryDTO) {
        ChargeOrderQuery  query = new ChargeOrderQuery();
        DodopalResponse<DodopalDataPage<ConsumptionOrderCountDTO>> response = new DodopalResponse<DodopalDataPage<ConsumptionOrderCountDTO>>();
        List <ConsumptionOrderCountDTO> orderList = new ArrayList<ConsumptionOrderCountDTO>();
        try {
            checkMerCode(queryDTO);
            PropertyUtils.copyProperties(query, queryDTO);
            DodopalDataPage<ConsumptionOrderCount> result = merjointQueryService.findConsumptionOrderCountByPage(query);
            if(CollectionUtils.isNotEmpty(result.getRecords())){
                for(ConsumptionOrderCount temp :result.getRecords()){
                    ConsumptionOrderCountDTO dto = new ConsumptionOrderCountDTO();
                    PropertyUtils.copyProperties(dto, temp);
                    orderList.add(dto);
                }
            }
            PageParameter page =  DodopalDataPageUtil.convertPageInfo(result);
            DodopalDataPage<ConsumptionOrderCountDTO> pages = new DodopalDataPage<ConsumptionOrderCountDTO>(page, orderList);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(pages);
        }catch(DDPException e){
            response.setCode(e.getCode());
            e.printStackTrace();
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public DodopalResponse<ConsumptionOrderCountDTO> findConsumptionOrderCount(ChargeOrderQueryDTO queryDTO) {
        ChargeOrderQuery  query = new ChargeOrderQuery();
        DodopalResponse<ConsumptionOrderCountDTO> response = new DodopalResponse<ConsumptionOrderCountDTO>();
        try {
            checkMerCode(queryDTO);
            PropertyUtils.copyProperties(query, queryDTO);
            ConsumptionOrderCount result = merjointQueryService.findConsumptionOrderCount(query);
            ConsumptionOrderCountDTO dto = new ConsumptionOrderCountDTO();
            if(null!=result){
                PropertyUtils.copyProperties(dto, result);
            }
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(dto);
        }catch(DDPException e){
            response.setCode(e.getCode());
            e.printStackTrace();
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public DodopalResponse<List<ChargeOrderDTO>> exportChargeOrder(ChargeOrderQueryDTO queryDTO) {
        ChargeOrderQuery  query = new ChargeOrderQuery();
        DodopalResponse<List<ChargeOrderDTO>> response = new DodopalResponse<List<ChargeOrderDTO>>();
        List <ChargeOrderDTO> orderList = new ArrayList<ChargeOrderDTO>();
        try {
            checkMerCode(queryDTO);
            PropertyUtils.copyProperties(query, queryDTO);
            List <ChargeOrder> result = merjointQueryService.exportChargeOrder(query);
            if(CollectionUtils.isNotEmpty(result)){
                for(ChargeOrder temp :result){
                    ChargeOrderDTO dto = new ChargeOrderDTO();
                    PropertyUtils.copyProperties(dto, temp);
                    orderList.add(dto);
                }
            }
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(orderList);
        }catch(DDPException e){
            response.setCode(e.getCode());
            e.printStackTrace();
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public DodopalResponse<List<ConsumptionOrderDTO>> exportConsumptionOrder(ConsumptionOrderQueryDTO queryDTO) {
        ConsumptionOrderQuery  query = new ConsumptionOrderQuery();
        DodopalResponse<List<ConsumptionOrderDTO>> response = new DodopalResponse<List<ConsumptionOrderDTO>>();
        List <ConsumptionOrderDTO> orderList = new ArrayList<ConsumptionOrderDTO>();
        try {
            checkMerCode(queryDTO);
            PropertyUtils.copyProperties(query, queryDTO);
            List<ConsumptionOrder> result = merjointQueryService.exportConsumptionOrder(query);
            if(CollectionUtils.isNotEmpty(result)){
                for(ConsumptionOrder temp :result){
                    ConsumptionOrderDTO dto = new ConsumptionOrderDTO();
                    PropertyUtils.copyProperties(dto, temp);
                    orderList.add(dto);
                }
            }
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(orderList);
        }catch(DDPException e){
            response.setCode(e.getCode());
            e.printStackTrace();
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public DodopalResponse<List<ConsumptionOrderCountDTO>> exportConsumptionOrderCount(ChargeOrderQueryDTO queryDTO) {
        ChargeOrderQuery  query = new ChargeOrderQuery();
        DodopalResponse<List<ConsumptionOrderCountDTO>> response = new DodopalResponse<List<ConsumptionOrderCountDTO>>();
        List <ConsumptionOrderCountDTO> orderList = new ArrayList<ConsumptionOrderCountDTO>();
        try {
            checkMerCode(queryDTO);
            PropertyUtils.copyProperties(query, queryDTO);
            List<ConsumptionOrderCount> result = merjointQueryService.exportConsumptionOrderCount(query);
            if(CollectionUtils.isNotEmpty(result)){
                for(ConsumptionOrderCount temp :result){
                    ConsumptionOrderCountDTO dto = new ConsumptionOrderCountDTO();
                    PropertyUtils.copyProperties(dto, temp);
                    orderList.add(dto);
                }
            }
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(orderList);
        }catch(DDPException e){
            response.setCode(e.getCode());
            e.printStackTrace();
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        return response;
    }

    public void checkMerCode(ChargeOrderQueryDTO queryDTO){
        if(StringUtils.isBlank(queryDTO.getMchnitid())){
            throw new DDPException(ResponseCode.PRODUCT_PRO_MERCHANTNUM_NULL);
        }
    }
    public void checkMerCode(ConsumptionOrderQueryDTO queryDTO){
        if(StringUtils.isBlank(queryDTO.getMchnitid())){
            throw new DDPException(ResponseCode.PRODUCT_PRO_MERCHANTNUM_NULL);
        }
    }
    
}
