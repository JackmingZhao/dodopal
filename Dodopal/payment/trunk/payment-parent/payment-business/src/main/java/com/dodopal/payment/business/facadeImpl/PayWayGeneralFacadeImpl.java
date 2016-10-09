package com.dodopal.payment.business.facadeImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.payment.dto.PayAwayDTO;
import com.dodopal.api.payment.dto.PayConfigDTO;
import com.dodopal.api.payment.dto.PayWayGeneralDTO;
import com.dodopal.api.payment.dto.query.PayWayGeneralQueryDTO;
import com.dodopal.api.payment.facade.PayWayGeneralFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.PayAwayEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.payment.business.model.PayConfig;
import com.dodopal.payment.business.model.PayWayExternal;
import com.dodopal.payment.business.model.PayWayGeneral;
import com.dodopal.payment.business.model.query.PayWayQuery;
import com.dodopal.payment.business.service.PayWayGeneralService;

/**
 * 
 * @author hxc
 *
 */
@Service("payWayGeneralFacade")
public class PayWayGeneralFacadeImpl implements PayWayGeneralFacade {
    private final static Logger log = LoggerFactory.getLogger(PayWayGeneralFacadeImpl.class);
    
    @Autowired
    private PayWayGeneralService generalService;

    /**
     * 启用停用
     */
    public DodopalResponse<Integer> batchStartOrStop(ActivateEnum activateEnum, List<String> idList, String updateUser) {
        DodopalResponse<Integer>response = new DodopalResponse<Integer>();
        int startOrStop = 0;
        if(idList.isEmpty()){
            response.setCode(ResponseCode.USERS_FIND_USER_ID_NULL);
        }else if(null==activateEnum){
            response.setCode(ResponseCode.USERS_FIND_USER_ACT_NULL);
        }else{
            startOrStop = generalService.batchStartOrStop(activateEnum.getCode(),idList,updateUser);
            response.setResponseEntity(startOrStop);
            response.setCode(ResponseCode.SUCCESS);
        }
        return response;
    }

    /**
     * 删除
     */
    public DodopalResponse<Integer> deletePayWayGeneral(List<String> ids) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        int general=0;
        try {
            if(ids != null && ids.size()>0){
                general = generalService.deletePayWayGeneral(ids);
                response.setCode(ResponseCode.SUCCESS);
            }
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        response.setResponseEntity(general);
        return response;
    }

    /**
     * 新增
     */
    public DodopalResponse<Integer> savePayWayGeneral(PayWayGeneralDTO generalDTO) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        PayWayGeneral payWayGeneral = new PayWayGeneral();
        int num = 0;
        try {
            if(generalDTO!=null){
                PropertyUtils.copyProperties(payWayGeneral, generalDTO);
                num = generalService.savePayWayGeneral(payWayGeneral);
                response.setResponseEntity(num);
                response.setCode(ResponseCode.SUCCESS);
            }else{
                response.setCode(ResponseCode.PAY_GENERAL_NULL);
            }
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            log.debug("PayWayGeneralFacadeImpl call error", e);
        }
        return response;
    }
    
    /**
     *查询（分页） 
     */
    public DodopalResponse<DodopalDataPage<PayWayGeneralDTO>> findPayWayGeneralByPage(PayWayGeneralQueryDTO payAwayQueryDTO) {
        DodopalResponse<DodopalDataPage<PayWayGeneralDTO>> dodopalResponse = new DodopalResponse<DodopalDataPage<PayWayGeneralDTO>>();
        PayWayQuery payWayQuery = new PayWayQuery();
        try {
            PropertyUtils.copyProperties(payWayQuery, payAwayQueryDTO);
            DodopalDataPage<PayWayGeneral> pagelist = generalService.findPayWayGeneralByPage(payWayQuery);
            List<PayWayGeneral> list = pagelist.getRecords();
            List<PayWayGeneralDTO> payDTOList = new ArrayList<PayWayGeneralDTO>();
            if (CollectionUtils.isNotEmpty(list)) {
                for (PayWayGeneral payWayGeneralDTO : list) {
                    PayWayGeneralDTO dto = new PayWayGeneralDTO();
                    PropertyUtils.copyProperties(dto, payWayGeneralDTO);
                    payDTOList.add(dto);
                }
            }
            PageParameter page = DodopalDataPageUtil.convertPageInfo(pagelist);
            DodopalDataPage<PayWayGeneralDTO> pages = new DodopalDataPage<PayWayGeneralDTO>(page, payDTOList);
            dodopalResponse.setCode(ResponseCode.SUCCESS);
            dodopalResponse.setResponseEntity(pages);
        }
        catch (Exception e) {
            log.debug("PayWayGeneralFacadeImpl call error", e);
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }

    /**
     * 修改
     */
    public DodopalResponse<Boolean> updatePayWayGeneral(PayWayGeneralDTO generalDTO) {
        DodopalResponse<Boolean> dodopalResponse = new DodopalResponse<Boolean>();
        PayWayGeneral payWayGeneral = new PayWayGeneral();
        try {
            PropertyUtils.copyProperties(payWayGeneral, generalDTO);
            int rows = generalService.updatePayWayGeneral(payWayGeneral);
            if(rows>0){
                dodopalResponse.setResponseEntity(true);
                dodopalResponse.setCode(ResponseCode.SUCCESS);
            }else{
                dodopalResponse.setResponseEntity(false);
                dodopalResponse.setCode(ResponseCode.SUCCESS);
            }
        }
        catch (Exception e) {
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
            log.debug("PayWayGeneralFacadeImpl call error", e);
        }
        return null;
    }

    /**
     * 根据id查询
     */
    public DodopalResponse<PayWayGeneralDTO> findGeneralById(String id) {
        DodopalResponse<PayWayGeneralDTO> response = new DodopalResponse<PayWayGeneralDTO>();
        PayWayGeneralDTO dto = new PayWayGeneralDTO();
        PayWayGeneral general = new PayWayGeneral();
        try {
            if (id != null && id != "") {
                general = generalService.findPayGeneralById(id);
            }
            PropertyUtils.copyProperties(dto, general);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(dto);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    /**
     * 根据支付类型查询支付名称
     */
    public List<PayConfigDTO> findPayWayNameByPayType(String payType) {
        PayConfig payConfig = new PayConfig();
        List<PayConfigDTO> payConfigDTOList = new ArrayList<PayConfigDTO>();
        List<PayConfig> payConfigList =  generalService.findPayWayNameByPayType(payType);
        if(payConfigList!=null && payConfigList.size()>0){
           
            for(PayConfig pay : payConfigList){
                PayConfigDTO payConfigDTO = new PayConfigDTO();
                try {
                    PropertyUtils.copyProperties(payConfigDTO, pay);
                    payConfigDTOList.add(payConfigDTO);
                }
                catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
                
            }
        }
        
        return payConfigDTOList;
    }

    public int countByPayConfigId(String payConfigId) {
        return generalService.countByPayConfigId(payConfigId);
    }

}
