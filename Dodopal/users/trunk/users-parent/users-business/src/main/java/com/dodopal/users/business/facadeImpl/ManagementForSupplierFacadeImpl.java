package com.dodopal.users.business.facadeImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerCountDTO;
import com.dodopal.api.users.dto.PosDTO;
import com.dodopal.api.users.dto.PosQueryDTO;
import com.dodopal.api.users.dto.query.MerCountQueryDTO;
import com.dodopal.api.users.facade.ManagementForSupplierFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.AreaService;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.users.business.model.MerCount;
import com.dodopal.users.business.model.Merchant;
import com.dodopal.users.business.model.Pos;
import com.dodopal.users.business.model.PosQuery;
import com.dodopal.users.business.model.query.MerCountQuery;
import com.dodopal.users.business.service.ManagementForSupplierService;
import com.dodopal.users.business.service.MerchantService;
import com.dodopal.users.business.service.PosService;

/**
 * 供应商登录门户调用的接口
 */
@Service("managementForSupplierFacade")
public class ManagementForSupplierFacadeImpl implements ManagementForSupplierFacade {

    @Autowired
    private PosService posService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private ManagementForSupplierService supplierService;

    /**
     * 查询商户所在城市的pos信息
     */
    @Override
    public DodopalResponse<DodopalDataPage<PosDTO>> countMerchantPosForSupplier(PosQueryDTO findDto) {
        DodopalResponse<DodopalDataPage<PosDTO>> response = new DodopalResponse<DodopalDataPage<PosDTO>>();
        //校验入参
        if (StringUtils.isBlank(findDto.getMerchantName())) {
            response.setCode(ResponseCode.USERS_MER_NAME_NULL);
            return response;
        }

        try {
            PosQuery findBean = new PosQuery();
            //外部对象转为内部使用对象
            PropertyUtils.copyProperties(findBean, findDto);
            List<PosDTO> dtoList = new ArrayList<PosDTO>();
            List<Pos> posList = posService.findPosinfoByPage(findBean);

            if (posList != null && posList.size() > 0) {
                for (Pos itme : posList) {
                    PosDTO poDto = new PosDTO();
                    PropertyUtils.copyProperties(poDto, itme);
                    dtoList.add(poDto);
                }
            }
            //分页信息设置
            PageParameter page = findBean.getPage();
            DodopalDataPage<PosDTO> pages = new DodopalDataPage<PosDTO>(page, dtoList);
            response.setResponseEntity(pages);
            response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return response;
    }

    // 查询城市下所有商户的信息
    @Override
    public DodopalResponse<DodopalDataPage<MerCountDTO>> countMerchantForSupplier(MerCountQueryDTO merCountQueryDTO) {
        // TODO Auto-generated method stub
        DodopalResponse<DodopalDataPage<MerCountDTO>> response = new DodopalResponse<DodopalDataPage<MerCountDTO>>();
        try {
            MerCountQuery merCountQuery = new MerCountQuery();
            PropertyUtils.copyProperties(merCountQuery, merCountQueryDTO);
            DodopalDataPage<MerCount>  rtResponse =  supplierService.findMerCountByPage(merCountQuery);
            List<MerCount> merCountList = new ArrayList<MerCount>();
            if(null!=rtResponse){
                merCountList = rtResponse.getRecords();
            }
         
            List<MerCountDTO> merCountDTOList  = new ArrayList<MerCountDTO>();
            
            if(CollectionUtils.isNotEmpty(merCountList)){
                
                for (MerCount merCount : merCountList) {
                    MerCountDTO merCountDTO = new MerCountDTO();
                    PropertyUtils.copyProperties(merCountDTO, merCount);
                    //pos数量
                    int i = posService.posCount(merCount.getMerCode());
                    merCountDTO.setPosCount(i+"");
                    //店面地址
                    Merchant merchant = merchantService.findMerchantByMerCode(merCount.getMerCode());
                    if(null!=merchant){
                        String merPro = merchant.getMerProName()==null ? "":merchant.getMerProName();
                        String merCity = merchant.getMerCityName() ==null ? "":merchant.getMerCityName();
                        String merAdds = merchant.getMerAdds() ==null ? "":merchant.getMerAdds();
                        merCountDTO.setMerAddress(merPro+merCity+merAdds); 
                    }else{
                        merCountDTO.setMerAddress("");
                    }
                    merCountDTOList.add(merCountDTO);
                }
            }
               
            //获取分页信息
            PageParameter page = DodopalDataPageUtil.convertPageInfo(rtResponse);
            DodopalDataPage<MerCountDTO> pages = new DodopalDataPage<MerCountDTO>(page, merCountDTOList);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(pages);
            
            
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        return response;
    }

}
