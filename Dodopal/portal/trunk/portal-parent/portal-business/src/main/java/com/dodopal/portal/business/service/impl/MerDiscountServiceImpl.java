package com.dodopal.portal.business.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.users.dto.DirectMerChantDTO;
import com.dodopal.api.users.dto.MerDiscountDTO;
import com.dodopal.api.users.dto.MerDiscountReferDTO;
import com.dodopal.api.users.dto.query.MerDiscountQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerDiscountBean;
import com.dodopal.portal.business.model.MerDiscountAdd;
import com.dodopal.portal.business.model.MerDiscountEdit;
import com.dodopal.portal.business.service.MerDiscountService;
import com.dodopal.portal.delegate.MerDiscountDelegate;
import com.dodopal.portal.delegate.TransferDelegate;

@Service
public class MerDiscountServiceImpl implements MerDiscountService {
    private final static Logger log = LoggerFactory.getLogger(MerDiscountServiceImpl.class);
    @Autowired
    MerDiscountDelegate merDiscountDelegate;
    
    @Autowired
    TransferDelegate transferDelegate;

   //查询商户折扣（分页）
    public DodopalResponse<DodopalDataPage<MerDiscountDTO>> findMerDiscountByPage(MerDiscountQueryDTO merDiscountQueryDTO) {
        DodopalResponse<DodopalDataPage<MerDiscountDTO>> response = new DodopalResponse<DodopalDataPage<MerDiscountDTO>>();
        try {
            response = merDiscountDelegate.findMerDiscountByPage(merDiscountQueryDTO);
        }
        catch (HessianRuntimeException e) {
            log.debug("查询商户折扣（分页） MerDiscountServiceImpl findMerDiscountByPage call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("查询商户折扣（分页） MerDiscountServiceImpl findMerDiscountByPage  throws e:" + e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
            // TODO: handle exception
        }
        return response;
    }

    //停用or启用
    public DodopalResponse<Integer> startOrStopMerDiscount(String activate, List<String> ids) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            response = merDiscountDelegate.startOrStopMerDiscount(activate, ids);
        }
        catch (HessianRuntimeException e) {
            log.debug("停用or启用  MerDiscountServiceImpl startOrStopMerDiscount call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("停用or启用   MerDiscountServiceImpl startOrStopMerDiscount  throws e:" + e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
            // TODO: handle exception
        }
        return response;
    }

    @Override
    public DodopalResponse<Integer> updateMerDiscount(MerDiscountDTO merDiscountDTO) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            response = merDiscountDelegate.updateMerDiscount(merDiscountDTO);
        }
        catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return response;
    }

    //新增商户折扣
    public DodopalResponse<Integer> saveMerDiscount(MerDiscountDTO merDiscountDTO) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            response = merDiscountDelegate.saveMerDiscount(merDiscountDTO);
        }
        catch (HessianRuntimeException e) {
            log.debug("新增商户折扣  MerDiscountServiceImpl saveMerDiscount call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("新增商户折扣   MerDiscountServiceImpl saveMerDiscount  throws e:" + e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

   //查询商户折扣详情
    public DodopalResponse<MerDiscountBean> findMerDiscountById(String id,String merParentCode,String merName) {
        DodopalResponse<MerDiscountBean> response = new DodopalResponse<MerDiscountBean>();
        //获取直营网点的名称
        String merNameStr  = "";
        MerDiscountBean merDiscountBean = new MerDiscountBean();
        try {
            //查询根据 折扣id 查询商户其直营网点对应的折扣记录
            DodopalResponse<List<MerDiscountReferDTO>> rtResponse = merDiscountDelegate.findMerDiscountReferByList(id);
            List<MerDiscountReferDTO> merDiscountReferDTOList  = new ArrayList<MerDiscountReferDTO>();
            if(ResponseCode.SUCCESS.equals(rtResponse.getCode())&& rtResponse.getResponseEntity()!=null){
                merDiscountReferDTOList = rtResponse.getResponseEntity();
                
            }
            
            //根据商户号和  直营网点的名称  查询其直营网点
            DodopalResponse<List<DirectMerChantDTO>> rResponse = transferDelegate.findMerchantByParentCode(merParentCode, merName);
           
            List<DirectMerChantDTO> directMerChantDTOList  = new ArrayList<DirectMerChantDTO>();
            
            if(ResponseCode.SUCCESS.equals(rResponse.getCode())&& rResponse.getResponseEntity()!=null){
                directMerChantDTOList = rResponse.getResponseEntity();
                
            }
            
           //连锁商户获取 其 商户折扣 对应的直营网点的名称  拼成字符串
            if(merDiscountReferDTOList.size()>0 && directMerChantDTOList.size()>0){
                for(DirectMerChantDTO directMerChantDTO:directMerChantDTOList){
                    for(MerDiscountReferDTO merDiscountReferDTO:merDiscountReferDTOList){
                        if(directMerChantDTO.getMerCode().equals(merDiscountReferDTO.getMerCode())){
                            merNameStr += directMerChantDTO.getMerName()+",";
                        }
                    }
                }
                merNameStr= merNameStr.substring(0, merNameStr.length()-1);
            }
            
            
            DodopalResponse<MerDiscountDTO> result = merDiscountDelegate.findMerDiscountById(id); 
            
            if(ResponseCode.SUCCESS.equals(result.getCode())&& result.getResponseEntity()!=null){
                MerDiscountDTO merDiscountDTO = result.getResponseEntity();
                merDiscountBean.setActivate(merDiscountDTO.getActivate());
                merDiscountBean.setDiscount(merDiscountDTO.getDiscount());
                merDiscountBean.setCreateDate(merDiscountDTO.getCreateDate());
                merDiscountBean.setId(merDiscountDTO.getId());
                merDiscountBean.setMerCode(merDiscountDTO.getMerCode());
                merDiscountBean.setSort(merDiscountDTO.getSort());
                merDiscountBean.setMerName(merNameStr);
                merDiscountBean.setUpdateDate(merDiscountDTO.getUpdateDate());
                response.setCode(result.getCode());
                response.setResponseEntity(merDiscountBean);
                
            }else{
                response.setCode(result.getCode());
            }
            
            
        }
        catch (HessianRuntimeException e) {
            log.debug("查询商户折扣详情  MerDiscountServiceImpl findMerDiscountById call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("查询商户折扣详情   MerDiscountServiceImpl findMerDiscountById  throws e:" + e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        
        return response;
    }

    //新增商户折扣
    public DodopalResponse<Boolean> addMerDiscount(MerDiscountAdd merDiscountAdd) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        try {
            //查询商户折扣是否已经存在
            DodopalResponse<Integer> countResponse = merDiscountDelegate.findMerDiscountNum(merDiscountAdd.getMerCode(),Double.toString((Double.valueOf(merDiscountAdd.getDiscount())/10) * 100));
            if(ResponseCode.SUCCESS.equals(countResponse.getCode())&&countResponse.getResponseEntity()>0){
                response.setCode(ResponseCode.PORTAL_USER_DISCOUNT_EXIST);
                return response;
            }else{
                //新增商户折扣
                MerDiscountDTO merDiscountDTO = new MerDiscountDTO();
                merDiscountDTO.setDiscount(Double.toString((Double.valueOf(merDiscountAdd.getDiscount())/10) * 100));
                merDiscountDTO.setSort(merDiscountAdd.getSort());
                merDiscountDTO.setMerCode(merDiscountAdd.getMerCode());
                merDiscountDTO.setActivate(merDiscountAdd.getActivateFlag());
                DodopalResponse<Integer> response1= merDiscountDelegate.saveMerDiscount(merDiscountDTO);  
                if(ResponseCode.SUCCESS.equals(response1.getCode())&&response1.getResponseEntity()>0){
                    
                    //新增直营网点 的折扣
                    if (merDiscountAdd.getDirectMer() != null && merDiscountAdd.getDirectMer().size() > 0) {
                        List<Map<String, String>> DirectMerList = merDiscountAdd.getDirectMer();
                        
                        DodopalResponse<MerDiscountDTO> response2 =  merDiscountDelegate.findMerDiscountByCode(merDiscountAdd.getMerCode(),Double.toString((Double.valueOf(merDiscountAdd.getDiscount())/10) * 100));

                        for (int i = 0; i < DirectMerList.size(); i++) {
                            //多个直营网点
                            Map<String, String> map = DirectMerList.get(i);
                            Set<Entry<String, String>> entries = map.entrySet();
                            
                            for (Entry<String, String> entry : entries) {

                                if (entry.getKey().equals("merCode")) {
                                    MerDiscountReferDTO merDiscountReferDTO = new MerDiscountReferDTO();
                                    merDiscountReferDTO.setMerCode(entry.getValue());
                                    merDiscountReferDTO.setMerDiscountId(response2.getResponseEntity().getId());
                                    try {
                                        DodopalResponse<Integer> response3 = merDiscountDelegate.insertMerDiscountRefer(merDiscountReferDTO); 
                                    }
                                    catch (HessianRuntimeException e) {
                                        log.debug("新增直营网点 折扣  MerDiscountServiceImpl merDiscountDelegate.insertMerDiscountRefer call HessianRuntimeException error", e);
                                        e.printStackTrace();
                                        response.setCode(ResponseCode.HESSIAN_ERROR);
                                    }
                                    catch (Exception e) {
                                        log.debug("新增直营网点 折扣  MerDiscountServiceImpl merDiscountDelegate.insertMerDiscountRefer call Exception error", e);
                                        e.printStackTrace();
                                        response.setCode(ResponseCode.SYSTEM_ERROR);
                                    }
                                  
                                }
                            }
                        }
                        response.setCode(ResponseCode.SUCCESS);
                        
                    }else{
                        response.setCode(ResponseCode.SUCCESS);
                    }
                    
                    
                }else{
                    response.setCode(ResponseCode.PORTAL_USER_ADD_DISCOUNT_FAIL);
                    
                }
                
            }
            
        }
        catch (HessianRuntimeException e) {
            log.debug("新增商户折扣  MerDiscountServiceImpl addMerDiscount call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
     
    }

      // 修改商户折扣
    public DodopalResponse<Boolean> editMerDiscount(MerDiscountEdit merDiscountEdit) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        try {
            //查询表中该 商户折扣表 id 对应的商户折扣详细信息（用来校验 编辑该商户折扣的数据）
            DodopalResponse<MerDiscountDTO> response1 =  merDiscountDelegate.findMerDiscountById(merDiscountEdit.getId());
            if(ResponseCode.SUCCESS.equals(response1.getCode())&& response1.getResponseEntity()!=null){
                //编辑后  验证商户和之前的折扣是否一致
                //当不一致时  校验商户折扣的唯一性
                if(!response1.getResponseEntity().getDiscount().equals(merDiscountEdit.getDiscount())){
                     //查询该商户该折扣在表里的记录条数
                     DodopalResponse<Integer> countResponse = merDiscountDelegate.findMerDiscountNum(merDiscountEdit.getMerCode(),Double.toString((Double.valueOf(merDiscountEdit.getDiscount())/10) * 100));
                     if(ResponseCode.SUCCESS.equals(countResponse.getCode())&&countResponse.getResponseEntity()>0){
                         response.setCode(ResponseCode.PORTAL_USER_DISCOUNT_EXIST);
                         return response;
                     }
                
                }
                
                //修改商户折扣
                MerDiscountDTO merDiscountDTO = new MerDiscountDTO();
                merDiscountDTO.setDiscount(Double.toString((Double.valueOf(merDiscountEdit.getDiscount())/10) * 100));
                merDiscountDTO.setSort(merDiscountEdit.getSort());
                merDiscountDTO.setMerCode(merDiscountEdit.getMerCode());
                merDiscountDTO.setActivate(merDiscountEdit.getActivateFlag());
                merDiscountDTO.setId(merDiscountEdit.getId());
                //修改该商户折扣
                DodopalResponse<Integer> response2 = merDiscountDelegate.updateMerDiscount(merDiscountDTO);
                
                if(ResponseCode.SUCCESS.equals(response2.getCode()) && response2.getResponseEntity()>0){
                    
                    //删除该商户折扣对应的直营网点的折扣（针对 连锁商户）
                    try {
                        DodopalResponse<Integer> response4 =   merDiscountDelegate.deleteMerDiscountRefer(merDiscountEdit.getId());

                   }
                    catch (HessianRuntimeException e) {
                        log.debug("编辑商户折扣  删除该商户折扣对应的直营网点的折扣（针对 连锁商户）  MerDiscountServiceImpl merDiscountDelegate.deleteMerDiscountRefer call HessianRuntimeException error", e);
                        e.printStackTrace();
                        response.setCode(ResponseCode.HESSIAN_ERROR);
                    }
                    catch (Exception e) {
                        log.debug("编辑商户折扣  删除该商户折扣对应的直营网点的折扣（针对 连锁商户）  MerDiscountServiceImpl merDiscountDelegate.deleteMerDiscountRefer call Exception error", e);
                        e.printStackTrace();
                        response.setCode(ResponseCode.SYSTEM_ERROR);
                    }
                    
                    
                    //新增直营网点 的折扣
                    if (merDiscountEdit.getDirectMer() != null && merDiscountEdit.getDirectMer().size() > 0) {
                        List<Map<String, String>> DirectMerList = merDiscountEdit.getDirectMer();
                        
                        for (int i = 0; i < DirectMerList.size(); i++) {
                            //多个直营网点
                            Map<String, String> map = DirectMerList.get(i);
                            Set<Entry<String, String>> entries = map.entrySet();
                            
                            for (Entry<String, String> entry : entries) {

                                if (entry.getKey().equals("merCode")) {
                                    MerDiscountReferDTO merDiscountReferDTO = new MerDiscountReferDTO();
                                    merDiscountReferDTO.setMerCode(entry.getValue());
                                    merDiscountReferDTO.setMerDiscountId(merDiscountEdit.getId());
                                    try {
                                        DodopalResponse<Integer> response3 = merDiscountDelegate.insertMerDiscountRefer(merDiscountReferDTO); 
                                    }
                                    catch (HessianRuntimeException e) {
                                        log.debug("新增直营网点 折扣  MerDiscountServiceImpl merDiscountDelegate.insertMerDiscountRefer call HessianRuntimeException error", e);
                                        e.printStackTrace();
                                        response.setCode(ResponseCode.HESSIAN_ERROR);
                                    }
                                    catch (Exception e) {
                                        log.debug("新增直营网点 折扣  MerDiscountServiceImpl merDiscountDelegate.insertMerDiscountRefer call Exception error", e);
                                        e.printStackTrace();
                                        response.setCode(ResponseCode.SYSTEM_ERROR);
                                    }
                                  
                                }
                            }
                        }
                        response.setCode(ResponseCode.SUCCESS);
                        
                    }else{
                        response.setCode(ResponseCode.SUCCESS);
                    }
                    
                    
                    
                }
                    
                    
                
            }else{
                response.setCode(response1.getCode());
                
            }
            
        }
        catch (HessianRuntimeException e) {
            log.debug("修改商户折扣  MerDiscountServiceImpl editMerDiscount call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
      
        // TODO Auto-generated method stub
        return response;
    }
    

}
