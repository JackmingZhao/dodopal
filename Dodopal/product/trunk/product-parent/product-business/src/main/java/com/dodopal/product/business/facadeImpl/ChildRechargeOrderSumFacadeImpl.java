package com.dodopal.product.business.facadeImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.ChildRechargeOrderSumDTO;
import com.dodopal.api.product.dto.query.ChildRechargeOrderSumQueryDTO;
import com.dodopal.api.product.facade.ChildRechargeOrderSumFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.product.business.model.ChildRechargeOrderSum;
import com.dodopal.product.business.model.query.ChildRechargeOrderSumQuery;
import com.dodopal.product.business.service.ChildRechargeOrderSumService;

/**
 * 订单记录汇总（一卡通充值）
 * @author lenovo
 */
@Service("childRechargeOrderSumFacade")
public class ChildRechargeOrderSumFacadeImpl implements ChildRechargeOrderSumFacade {
    Logger log = LoggerFactory.getLogger(ChildRechargeOrderSumFacadeImpl.class);
   
    @Autowired
    ChildRechargeOrderSumService ChildRechargeOrderSumService;

    @Override
    public DodopalResponse<DodopalDataPage<ChildRechargeOrderSumDTO>> queryChildRechargeOrder(ChildRechargeOrderSumQueryDTO queryDTO) {
        log.info("订单记录汇总（一卡通充值）查询    ChildRechargeOrderSumFacadeImpl  queryChildRechargeOrder ：查询条件    queryDTO：" +queryDTO.toString());
        DodopalResponse<DodopalDataPage<ChildRechargeOrderSumDTO>> response = new DodopalResponse<DodopalDataPage<ChildRechargeOrderSumDTO>>();
        try {
            ChildRechargeOrderSumQuery query = new ChildRechargeOrderSumQuery();
            PropertyUtils.copyProperties(query, queryDTO);
            DodopalDataPage<ChildRechargeOrderSum> rtResponse = ChildRechargeOrderSumService.queryChildRechargeOrder(query);
            List<ChildRechargeOrderSum> rechargeOrderList = new ArrayList<ChildRechargeOrderSum>();
            if(null!= rtResponse){
                rechargeOrderList = rtResponse.getRecords();
            }
            List<ChildRechargeOrderSumDTO> rechargeOrderDTOList = new ArrayList<ChildRechargeOrderSumDTO>();
            if (CollectionUtils.isNotEmpty(rechargeOrderList)) {
                for (ChildRechargeOrderSum order : rechargeOrderList) {
                    ChildRechargeOrderSumDTO orderDTO = new ChildRechargeOrderSumDTO();
                    PropertyUtils.copyProperties(orderDTO, order);
                    rechargeOrderDTOList.add(orderDTO);
                }
            }
            PageParameter page = DodopalDataPageUtil.convertPageInfo(rtResponse);
            DodopalDataPage<ChildRechargeOrderSumDTO> pages = new DodopalDataPage<ChildRechargeOrderSumDTO>(page, rechargeOrderDTOList);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(pages);
        }
        catch (Exception e) {
            log.error("订单记录汇总（一卡通充值）查询    ChildRechargeOrderSumFacadeImpl  queryChildRechargeOrder  throw e ",e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

}
