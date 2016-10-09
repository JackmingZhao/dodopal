package com.dodopal.product.business.facadeImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.ProxyCardAddDTO;
import com.dodopal.api.product.dto.ProxyCountAllByIDDTO;
import com.dodopal.api.product.dto.ProxyOffLineOrderTbDTO;
import com.dodopal.api.product.dto.query.ProxyCardAddQueryDTO;
import com.dodopal.api.product.dto.query.ProxyOffLineOrderTbQueryDTO;
import com.dodopal.api.product.facade.ProxyCardAddQueryFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.product.business.model.ProxyCardAdd;
import com.dodopal.product.business.model.ProxyCountAllByID;
import com.dodopal.product.business.model.ProxyOffLineOrderTb;
import com.dodopal.product.business.model.query.ProxyCardAddQuery;
import com.dodopal.product.business.model.query.ProxyOffLineOrderTbQuery;
import com.dodopal.product.business.service.ProxyCardAddQueryService;

@Service("proxyCardAddQueryFacade")
public class ProxyCardAddQueryFacadeImpl implements ProxyCardAddQueryFacade {

    @Autowired
    ProxyCardAddQueryService proxyCardAddQueryService;

    //城市一卡通充值记录查询
    @Override
    public DodopalResponse<DodopalDataPage<ProxyCardAddDTO>> cardTradeList(ProxyCardAddQueryDTO proxyCardAddQueryDTO) {
        DodopalResponse<DodopalDataPage<ProxyCardAddDTO>> response = new DodopalResponse<DodopalDataPage<ProxyCardAddDTO>>();
        try {
            ProxyCardAddQuery proxyCardAddQuery = new ProxyCardAddQuery();
            PropertyUtils.copyProperties(proxyCardAddQuery, proxyCardAddQueryDTO);

            List<ProxyCardAddDTO> proxyCardAddDTOList = new ArrayList<ProxyCardAddDTO>();
            DodopalDataPage<ProxyCardAdd> rtResponse = proxyCardAddQueryService.cardTradeList(proxyCardAddQuery);

            if (rtResponse != null && rtResponse.getRecords() != null && rtResponse.getRecords().size() > 0) {
                for (ProxyCardAdd proxyCardAdd : rtResponse.getRecords()) {
                    ProxyCardAddDTO proxyCardAddDTO = new ProxyCardAddDTO();
                    PropertyUtils.copyProperties(proxyCardAddDTO, proxyCardAdd);
                    proxyCardAddDTOList.add(proxyCardAddDTO);
                }
            }

            //获取分页信息
            PageParameter page = DodopalDataPageUtil.convertPageInfo(rtResponse);
            DodopalDataPage<ProxyCardAddDTO> pages = new DodopalDataPage<ProxyCardAddDTO>(page, proxyCardAddDTOList);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(pages);

        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    //查询城市一卡通充值记录 统计
    @Override
    public DodopalResponse<ProxyCountAllByIDDTO> findCardTradeListCount(ProxyCardAddQueryDTO proxyCardAddQueryDTO) {
        DodopalResponse<ProxyCountAllByIDDTO> response = new DodopalResponse<ProxyCountAllByIDDTO>();
        try {
            ProxyCardAddQuery proxyCardAddQuery = new ProxyCardAddQuery();
            PropertyUtils.copyProperties(proxyCardAddQuery, proxyCardAddQueryDTO);
            ProxyCountAllByID  proxyCountAllByID = proxyCardAddQueryService.findCardTradeListCount(proxyCardAddQuery);
           
            ProxyCountAllByIDDTO proxyCountAllByIDDTO = new ProxyCountAllByIDDTO();
            
            if(proxyCountAllByID!=null){
                PropertyUtils.copyProperties(proxyCountAllByIDDTO, proxyCountAllByID);
            }
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(proxyCountAllByIDDTO);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<DodopalDataPage<ProxyOffLineOrderTbDTO>> offLineTradeList(ProxyOffLineOrderTbQueryDTO proxyOffLineOrderTbQueryDTO) {
        DodopalResponse<DodopalDataPage<ProxyOffLineOrderTbDTO>> response = new DodopalResponse<DodopalDataPage<ProxyOffLineOrderTbDTO>>();
        try {
            ProxyOffLineOrderTbQuery proxyOffLineOrderTbQuery = new ProxyOffLineOrderTbQuery();
            PropertyUtils.copyProperties(proxyOffLineOrderTbQuery, proxyOffLineOrderTbQueryDTO);

            List<ProxyOffLineOrderTbDTO> proxyOffLineOrderTbDTOList = new ArrayList<ProxyOffLineOrderTbDTO>();
            DodopalDataPage<ProxyOffLineOrderTb> rtResponse = proxyCardAddQueryService.offLineTradeList(proxyOffLineOrderTbQuery);

            if (rtResponse != null && rtResponse.getRecords() != null && rtResponse.getRecords().size() > 0) {
                for (ProxyOffLineOrderTb proxyOffLineOrderTb : rtResponse.getRecords()) {
                    ProxyOffLineOrderTbDTO proxyOffLineOrderTbDTO = new ProxyOffLineOrderTbDTO();
                    PropertyUtils.copyProperties(proxyOffLineOrderTbDTO, proxyOffLineOrderTb);
                    proxyOffLineOrderTbDTOList.add(proxyOffLineOrderTbDTO);
                }
            }

            //获取分页信息
            PageParameter page = DodopalDataPageUtil.convertPageInfo(rtResponse);
            DodopalDataPage<ProxyOffLineOrderTbDTO> pages = new DodopalDataPage<ProxyOffLineOrderTbDTO>(page, proxyOffLineOrderTbDTOList);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(pages);

        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<ProxyCountAllByIDDTO> findoffLineTradeListCount(ProxyOffLineOrderTbQueryDTO proxyOffLineOrderTbQueryDTO) {
        DodopalResponse<ProxyCountAllByIDDTO> response = new DodopalResponse<ProxyCountAllByIDDTO>();
        try {
            ProxyOffLineOrderTbQuery proxyOffLineOrderTbQuery = new ProxyOffLineOrderTbQuery();
            PropertyUtils.copyProperties(proxyOffLineOrderTbQuery, proxyOffLineOrderTbQueryDTO);
            ProxyCountAllByID  proxyCountAllByID = proxyCardAddQueryService.findoffLineTradeListCount(proxyOffLineOrderTbQuery);
           
            ProxyCountAllByIDDTO proxyCountAllByIDDTO = new ProxyCountAllByIDDTO();
            
            if(proxyCountAllByID!=null){
                PropertyUtils.copyProperties(proxyCountAllByIDDTO, proxyCountAllByID);
            }
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(proxyCountAllByIDDTO);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

}
