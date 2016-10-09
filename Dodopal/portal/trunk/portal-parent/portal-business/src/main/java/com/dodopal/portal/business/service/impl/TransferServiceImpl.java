package com.dodopal.portal.business.service.impl;

import java.text.DecimalFormat;
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
import com.dodopal.api.payment.dto.PayTransferDTO;
import com.dodopal.api.users.dto.DirectMerChantDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.DelFlgEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.TranTypeEnum;
import com.dodopal.common.enums.TransferFlagEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.DirectMerChantBean;
import com.dodopal.portal.business.bean.MerchantUserBean;
import com.dodopal.portal.business.model.PortalTransfer;
import com.dodopal.portal.business.service.TransferService;
import com.dodopal.portal.delegate.MerOperatorDelegate;
import com.dodopal.portal.delegate.MerchantDelegate;
import com.dodopal.portal.delegate.TransferDelegate;

@Service
public class TransferServiceImpl implements TransferService {
    private final static Logger log = LoggerFactory.getLogger(TransferServiceImpl.class);
    @Autowired
    TransferDelegate transferDelegate;

    @Autowired
    private MerOperatorDelegate merUserDelegate;

    @Autowired
    MerchantDelegate merchantdelegate;

    //门户转账 根据上级商户查询直营网点
    public DodopalResponse<List<DirectMerChantBean>> findMerchantByParentCode(String merParentCode, String merName) {
        log.info("门户转账 根据上级商户查询直营网点 TransferServiceImpl findMerchantByParentCode this merParentCode:" + merParentCode + ",merName:" + merName);
        DodopalResponse<List<DirectMerChantBean>> response = new DodopalResponse<List<DirectMerChantBean>>();
        DecimalFormat df = new DecimalFormat("0.00");
        try {
            DodopalResponse<List<DirectMerChantDTO>> rtResponse = transferDelegate.findMerchantByParentCode(merParentCode, merName);

            List<DirectMerChantBean> DirectMerChantBeanList = new ArrayList<DirectMerChantBean>();
            if (rtResponse.getCode().equals(ResponseCode.SUCCESS)) {
                try {
                    if (rtResponse.getResponseEntity() != null && rtResponse.getResponseEntity().size() > 0) {
                        List<DirectMerChantDTO> DirectMerChantDTOList = rtResponse.getResponseEntity();
                        for (DirectMerChantDTO merchantDTO : DirectMerChantDTOList) {
                            DirectMerChantBean directMerChantBean = new DirectMerChantBean();
                            directMerChantBean.setMerName(merchantDTO.getMerName());
                            directMerChantBean.setMerCode(merchantDTO.getMerCode());
                            if (MerTypeEnum.PERSONAL.getCode().equals(merchantDTO.getMerType())) {
                                directMerChantBean.setMerType(MerUserTypeEnum.PERSONAL.getCode());
                            } else {
                                directMerChantBean.setMerType(MerUserTypeEnum.MERCHANT.getCode());
                            }
                            directMerChantBean.setMerParentCode(merchantDTO.getMerParentCode());
                            directMerChantBean.setMerMoney(df.format((double) merchantDTO.getMerMoney() / 100));
                            DirectMerChantBeanList.add(directMerChantBean);
                        }

                    }
                    response.setCode(rtResponse.getCode());
                    response.setResponseEntity(DirectMerChantBeanList);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    log.error("门户转账 根据上级商户查询直营网点 TransferServiceImpl findMerchantByParentCode  throws e:" + e);
                    response.setCode(ResponseCode.SYSTEM_ERROR);
                    // TODO: handle exception
                }

            } else {
                response.setCode(rtResponse.getCode());
            }

        }
        catch (HessianRuntimeException e) {
            log.debug("门户转账 根据上级商户查询直营网点 TransferServiceImpl findMerchantByParentCode call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("门户转账 根据上级商户查询直营网点 TransferServiceImpl findMerchantByParentCode  throws e:" + e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    //转账or提取额度
    public DodopalResponse<Boolean> accountTransfer(PortalTransfer portalTransfer) {
        log.info("转账or提取额度   TransferServiceImpl accountTransfer portalTransfer " + portalTransfer.toString());

        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        try {
            DodopalResponse<MerchantUserDTO> user = merUserDelegate.findMerOperatorByUserCode(portalTransfer.getMerCode(), portalTransfer.getUserCode());

            if (user.getCode().equals(ResponseCode.SUCCESS) && user.getResponseEntity() != null) {
                if (DelFlgEnum.DELETE.getCode().equals(user.getResponseEntity().getDelFlag())) {
                    response.setCode(ResponseCode.USERS_FIND_USER_ERR);
                    return response;
                }
                if (ActivateEnum.DISABLE.getCode().equals(user.getResponseEntity().getActivate())) {
                    response.setCode(ResponseCode.USERS_USER_DISABLE);
                    return response;
                }

            } else {
                response.setCode(ResponseCode.USERS_FIND_USER_ERR);
                return response;
            }

            DodopalResponse<MerchantDTO> merchant = merchantdelegate.findMerchants(portalTransfer.getMerCode());

            if (merchant.getCode().equals(ResponseCode.SUCCESS) && merchant.getResponseEntity() != null) {
                if (DelFlgEnum.DELETE.getCode().equals(merchant.getResponseEntity().getDelFlg())) {
                    response.setCode(ResponseCode.USERS_FIND_MERCHANT_ERR);
                    return response;
                }
                if (ActivateEnum.DISABLE.getCode().equals(merchant.getResponseEntity().getActivate())) {
                    response.setCode(ResponseCode.USERS_MERCHANT_DISABLE);
                    return response;
                }

            } else {
                response.setCode(ResponseCode.USERS_FIND_MERCHANT_ERR);
                return response;
            }
            
            
            List<PayTransferDTO> payTransferDTOList = new ArrayList<PayTransferDTO>();

            if (portalTransfer.getDirectMer() != null && portalTransfer.getDirectMer().size() > 0) {
                List<Map<String, String>> DirectMerList = portalTransfer.getDirectMer();

                for (int i = 0; i < DirectMerList.size(); i++) {
                    //多个直营网点
                    Map<String, String> map = DirectMerList.get(i);
                    Set<Entry<String, String>> entries = map.entrySet();
                    PayTransferDTO payTransferDTO = new PayTransferDTO();//主账户转出
                    PayTransferDTO payTransferDTO1 = new PayTransferDTO();//直营网点转入

                    //主账户转出 or 转入

                    payTransferDTO.setAmount(Double.valueOf(portalTransfer.getMoney()).doubleValue());
                    payTransferDTO.setBusinessType(portalTransfer.getBusinessType());
                    payTransferDTO.setComments(portalTransfer.getComment());
                    payTransferDTO.setCreateUser(portalTransfer.getCreateUser());

                    payTransferDTO.setSourceCustType(portalTransfer.getSourceCustType());
                    payTransferDTO.setSourceCustNum(portalTransfer.getSourceCusCode());
                    // 0 转账， 1 提取额度
                    if (TransferFlagEnum.TRANSFER.getCode().equals(portalTransfer.getTransferFlag())) {
                        //主账户转出
                        payTransferDTO.setTransferFlag(TranTypeEnum.TURN_OUT.getCode());
                    } else {
                        //主账户 转入
                        payTransferDTO.setTransferFlag(TranTypeEnum.TURN_INTO.getCode());
                    }

                    //直营网点转入 or转出
                    payTransferDTO1.setAmount(Double.valueOf(portalTransfer.getMoney()).doubleValue());
                    payTransferDTO1.setBusinessType(portalTransfer.getBusinessType());
                    payTransferDTO1.setComments(portalTransfer.getComment());
                    payTransferDTO1.setCreateUser(portalTransfer.getCreateUser());
                    payTransferDTO1.setTargetCustType(portalTransfer.getSourceCustType());
                    payTransferDTO1.setTargetCustNum(portalTransfer.getSourceCusCode());
                    // 0 转账， 1 提取额度
                    if (TransferFlagEnum.TRANSFER.getCode().equals(portalTransfer.getTransferFlag())) {
                        //直营网点 转入
                        payTransferDTO1.setTransferFlag(TranTypeEnum.TURN_INTO.getCode());
                    } else {
                        //直营网点 转出
                        payTransferDTO1.setTransferFlag(TranTypeEnum.TURN_OUT.getCode());
                    }

                    for (Entry<String, String> entry : entries) {

                        if (entry.getKey().equals("merType")) {
                            payTransferDTO.setTargetCustType(entry.getValue());
                        }
                        if (entry.getKey().equals("merCode")) {
                            payTransferDTO.setTargetCustNum(entry.getValue());
                        }

                        if (entry.getKey().equals("merType")) {
                            payTransferDTO1.setSourceCustType(entry.getValue());
                        }
                        if (entry.getKey().equals("merCode")) {
                            payTransferDTO1.setSourceCustNum(entry.getValue());
                            
                            try {
                                DodopalResponse<MerchantDTO> merchant1 = merchantdelegate.findMerchants(entry.getValue());

                                if (merchant1.getCode().equals(ResponseCode.SUCCESS) && merchant1.getResponseEntity() != null) {
                                    if (DelFlgEnum.DELETE.getCode().equals(merchant1.getResponseEntity().getDelFlg())) {
                                        response.setCode(ResponseCode.USERS_FIND_MERCHANT_ERR);
                                        return response;
                                    }
                                    if (ActivateEnum.DISABLE.getCode().equals(merchant1.getResponseEntity().getActivate())) {
                                        response.setCode(ResponseCode.USERS_MERCHANT_DISABLE);
                                        return response;
                                    }
                                    if(!merchant1.getResponseEntity().getMerParentCode().equals(portalTransfer.getMerCode())){
                                       
                                        response.setCode(ResponseCode.USERS_CHILD_PARENT_INCONFORMITY);
                                        return response;
                                    }

                                } else {
                                    response.setCode(ResponseCode.USERS_FIND_MERCHANT_ERR);
                                    return response;
                                }
                            }
                            catch (HessianRuntimeException e) {
                                log.debug("转账or提取额度 校验直营网点 是否启用 是否被删除 TransferServiceImpl accountTransfer merchantdelegate.findMerchants call HessianRuntimeException error", e);
                                e.printStackTrace();
                                response.setCode(ResponseCode.HESSIAN_ERROR);
                            }
                            catch (Exception e) {
                                log.debug("TransferServiceImpl accountTransfer  call Exception e:"+e);
                                e.printStackTrace();
                                response.setCode(ResponseCode.SYSTEM_ERROR);              
                               }
                        }


                    }
                    payTransferDTOList.add(payTransferDTO);
                    payTransferDTOList.add(payTransferDTO1);
                }

            }
            try {
                response = transferDelegate.transferAccount(payTransferDTOList);
            }
            catch (HessianRuntimeException e) {
                log.debug("转账or提取额度 TransferServiceImpl accountTransfer call HessianRuntimeException error", e);
                e.printStackTrace();
                response.setCode(ResponseCode.HESSIAN_ERROR);
            }
            catch (Exception e) {
                log.debug("转账or提取额度TransferServiceImpl accountTransfer call error", e);
                e.printStackTrace();
                response.setCode(ResponseCode.UNKNOWN_ERROR);
                // TODO: handle exception
            }
            
        }
        catch (HessianRuntimeException e) {
            log.debug("转账or提取额度 校验当前用户商户是否启用 TransferServiceImpl accountTransfer  call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("TransferServiceImpl accountTransfer  call Exception e:"+e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            // TODO: handle exception
        }

       

        return response;
    }


  //门户转账 根据上级商户查询直营网点
    public DodopalResponse<List<DirectMerChantBean>> findMerchantByParentCode(String merParentCode, String merName,String businessType) {
        log.info("门户转账 根据上级商户查询直营网点 TransferServiceImpl findMerchantByParentCode this merParentCode:" + merParentCode + ",merName:" + merName+",businessType:"+businessType);
        DodopalResponse<List<DirectMerChantBean>> response = new DodopalResponse<List<DirectMerChantBean>>();
        DecimalFormat df = new DecimalFormat("0.00");
        try {
            DodopalResponse<List<DirectMerChantDTO>> rtResponse = transferDelegate.findMerchantByParentCode(merParentCode, merName,businessType);

            List<DirectMerChantBean> DirectMerChantBeanList = new ArrayList<DirectMerChantBean>();
            if (rtResponse.getCode().equals(ResponseCode.SUCCESS)) {
                try {
                    if (rtResponse.getResponseEntity() != null && rtResponse.getResponseEntity().size() > 0) {
                        List<DirectMerChantDTO> DirectMerChantDTOList = rtResponse.getResponseEntity();
                        for (DirectMerChantDTO merchantDTO : DirectMerChantDTOList) {
                            DirectMerChantBean directMerChantBean = new DirectMerChantBean();
                            directMerChantBean.setMerName(merchantDTO.getMerName());
                            directMerChantBean.setMerCode(merchantDTO.getMerCode());
                            if (MerTypeEnum.PERSONAL.getCode().equals(merchantDTO.getMerType())) {
                                directMerChantBean.setMerType(MerUserTypeEnum.PERSONAL.getCode());
                            } else {
                                directMerChantBean.setMerType(MerUserTypeEnum.MERCHANT.getCode());
                            }
                            directMerChantBean.setMerParentCode(merchantDTO.getMerParentCode());
                            directMerChantBean.setMerMoney(df.format((double) merchantDTO.getMerMoney() / 100));
                            DirectMerChantBeanList.add(directMerChantBean);
                        }

                    }
                    response.setCode(rtResponse.getCode());
                    response.setResponseEntity(DirectMerChantBeanList);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    log.error("门户转账 根据上级商户查询直营网点 TransferServiceImpl findMerchantByParentCode  throws e:" + e);
                    response.setCode(ResponseCode.SYSTEM_ERROR);
                    // TODO: handle exception
                }

            } else {
                response.setCode(rtResponse.getCode());
            }

        }
        catch (HessianRuntimeException e) {
            log.debug("门户转账 根据上级商户查询直营网点 TransferServiceImpl findMerchantByParentCode call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("门户转账 根据上级商户查询直营网点 TransferServiceImpl findMerchantByParentCode  throws e:" + e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    //门户转账 根据上级商户查询直营网点
    public DodopalResponse<List<DirectMerChantBean>> findDirectTransferFilter(String merParentCode, String merName,String businessType) {
    	log.info("门户转账 根据上级商户查询直营网点 TransferServiceImpl findMerchantByParentCode this merParentCode:" + merParentCode + ",merName:" + merName+",businessType:"+businessType);
    	DodopalResponse<List<DirectMerChantBean>> response = new DodopalResponse<List<DirectMerChantBean>>();
    	DecimalFormat df = new DecimalFormat("0.00");
    	try {
    		DodopalResponse<List<DirectMerChantDTO>> rtResponse = transferDelegate.findDirectTransferFilter(merParentCode, merName,businessType);
    		
    		List<DirectMerChantBean> DirectMerChantBeanList = new ArrayList<DirectMerChantBean>();
    		if (rtResponse.getCode().equals(ResponseCode.SUCCESS)) {
    			try {
    				if (rtResponse.getResponseEntity() != null && rtResponse.getResponseEntity().size() > 0) {
    					List<DirectMerChantDTO> DirectMerChantDTOList = rtResponse.getResponseEntity();
    					for (DirectMerChantDTO merchantDTO : DirectMerChantDTOList) {
    						DirectMerChantBean directMerChantBean = new DirectMerChantBean();
    						directMerChantBean.setMerName(merchantDTO.getMerName());
    						directMerChantBean.setMerCode(merchantDTO.getMerCode());
    						if (MerTypeEnum.PERSONAL.getCode().equals(merchantDTO.getMerType())) {
    							directMerChantBean.setMerType(MerUserTypeEnum.PERSONAL.getCode());
    						} else {
    							directMerChantBean.setMerType(MerUserTypeEnum.MERCHANT.getCode());
    						}
    						directMerChantBean.setMerParentCode(merchantDTO.getMerParentCode());
    						directMerChantBean.setMerMoney(df.format((double) merchantDTO.getMerMoney() / 100));
    						DirectMerChantBeanList.add(directMerChantBean);
    					}
    					
    				}
    				response.setCode(rtResponse.getCode());
    				response.setResponseEntity(DirectMerChantBeanList);
    			}
    			catch (Exception e) {
    				e.printStackTrace();
    				log.error("门户转账 根据上级商户查询直营网点 TransferServiceImpl findMerchantByParentCode  throws e:" + e);
    				response.setCode(ResponseCode.SYSTEM_ERROR);
    				// TODO: handle exception
    			}
    			
    		} else {
    			response.setCode(rtResponse.getCode());
    		}
    		
    	}
    	catch (HessianRuntimeException e) {
    		log.debug("门户转账 根据上级商户查询直营网点 TransferServiceImpl findMerchantByParentCode call HessianRuntimeException error", e);
    		e.printStackTrace();
    		response.setCode(ResponseCode.HESSIAN_ERROR);
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		log.error("门户转账 根据上级商户查询直营网点 TransferServiceImpl findMerchantByParentCode  throws e:" + e);
    		response.setCode(ResponseCode.SYSTEM_ERROR);
    	}
    	return response;
    }
}
