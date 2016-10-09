/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.oss.business.service.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.common.enums.MerTypeEnum;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.api.users.dto.ProfitCollectDTO;
import com.dodopal.api.users.dto.ProfitDetailsDTO;
import com.dodopal.api.users.dto.query.ProfitQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ClearingFlagEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.RateTypeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPLog;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.oss.business.bean.ProfitCollectBean;
import com.dodopal.oss.business.bean.ProfitDetailsBean;
import com.dodopal.oss.business.bean.query.ProfitQuery;
import com.dodopal.oss.business.dao.ClearingTaskMapper;
import com.dodopal.oss.business.model.ClearingDataModel;
import com.dodopal.oss.business.model.MerChant;
import com.dodopal.oss.business.model.MerchantUserInfo;
import com.dodopal.oss.business.model.ProfitModel;
import com.dodopal.oss.business.service.ProfitService;
import com.dodopal.oss.delegate.ProfitDelegate;

/**
 * Created by lenovo on 2015/10/26.
 */
@Service
public class ProfitServiceImpl implements ProfitService {
    private static Logger log = Logger.getLogger(ProfitServiceImpl.class);
    private DDPLog<ProfitServiceImpl> ddpLog = new DDPLog<>(ProfitServiceImpl.class);
    @Autowired
    private ClearingTaskMapper clearingTaskMapper;
    @Autowired
    ProfitDelegate profitDelegate;

    public String getYestoday() {
        return clearingTaskMapper.getYestoday();
    }

    /**
     * @return
     * @description 查询需要分润的数据信息
     */
    @Override
    public List<ClearingDataModel> queryProfitData() {
        return clearingTaskMapper.queryProfitData();
    }

    /**
     * @return
     * @description 分润功能实现
     */
    @Transactional
    @Override
    public void profit(ClearingDataModel clearingDataModel) throws SQLException {
        //查询客户信息（根据customercode）
        List<MerChant> merChantList = null;
        if (MerTypeEnum.CHAIN_STORE_MER.getCode().equals(clearingDataModel.getCustomerType())) {
            String parentCode = clearingTaskMapper.queryParentCode(clearingDataModel.getCustomerNo());
            //查询当前商户的所有的上级商户d
            merChantList = clearingTaskMapper.queryMerchant(parentCode, RateCodeEnum.YKT_RECHARGE.getCode(), clearingDataModel.getSupplierCode());
            MerChant mer = new MerChant();
            mer.setMerCode(clearingDataModel.getCustomerNo());
            mer.setMerParentCode(parentCode);
            mer.setMerRate(clearingDataModel.getCustomerRate());
            mer.setMerRateType(clearingDataModel.getCustomerRateType());
            mer.setMerType(clearingDataModel.getCustomerType());
            merChantList.add(mer);
        } else {
            //查询当前商户的所有的上级商户d
            merChantList = clearingTaskMapper.queryMerchant(clearingDataModel.getCustomerNo(), RateCodeEnum.YKT_RECHARGE.getCode(), clearingDataModel.getSupplierCode());
        }

        if (merChantList.size() == 0) {
            log.info("=========没有查询到商户信息===========");
            throw new DDPException("8000000");
        }
        for (MerChant info : merChantList) {
            if (!info.getMerCode().equals(clearingDataModel.getCustomerNo())) {
                //根据客户号查询客户信息
                MerchantUserInfo merUser = clearingTaskMapper.queryMerchantUserInfo(info.getMerCode(), RateCodeEnum.YKT_RECHARGE.getCode(), clearingDataModel.getSupplierCode());
                MerchantUserInfo SubMerUser = null;
                for (MerChant subInfo : merChantList) {
                    if (merUser.getMerCode().equals(subInfo.getMerParentCode() == null || "".equals(subInfo.getMerParentCode()) ? "" : subInfo.getMerParentCode())) {
                        if (MerTypeEnum.CHAIN_STORE_MER.getCode().equals(subInfo.getMerType())) {
                            SubMerUser = new MerchantUserInfo();
                            SubMerUser.setMerRate(clearingDataModel.getCustomerRate());
                            SubMerUser.setMerRateType(clearingDataModel.getCustomerRateType());
                            SubMerUser.setMerCode(clearingDataModel.getCustomerNo());
                            SubMerUser.setMerName(clearingDataModel.getCustomerName());
                        } else {
                            SubMerUser = clearingTaskMapper.queryMerchantUserInfo(subInfo.getMerCode(), RateCodeEnum.YKT_RECHARGE.getCode(), clearingDataModel.getSupplierCode());
                        }
                    }
                }

                //数据插入分润明细表
                ProfitModel pm = getProfitModel(clearingDataModel, merUser, SubMerUser);
                int pflag = clearingTaskMapper.addProfitData(pm);
                //数据插入清分基础表
                ClearingDataModel cdm = getClearingDataModel(clearingDataModel, merUser, SubMerUser);
                int cflag = clearingTaskMapper.addClearingData(cdm);
            }
            int uflag = clearingTaskMapper.updateClearingFlage(clearingDataModel.getOrderNo(), ClearingFlagEnum.NO_CLEARING.getCode());
        }
    }

    /**
     * @param clearingDataModel
     * @param merUser
     * @return
     * @description 获取添加清分数据信息
     */
    @Transactional
    public ClearingDataModel getClearingDataModel(ClearingDataModel clearingDataModel, MerchantUserInfo merUser, MerchantUserInfo SubMerUser) {
        ClearingDataModel cdm = new ClearingDataModel();
        cdm.setOrderNo(clearingDataModel.getOrderNo());//订单交易号
        cdm.setOrderDate(clearingDataModel.getOrderDate());//订单时间
        cdm.setCustomerNo(merUser.getMerCode());//客户编号
        cdm.setCustomerName(merUser.getMerName());// 客户名称
        cdm.setCustomerType(merUser.getMerUserType());//客户类型
        cdm.setBusinessType(RateCodeEnum.YKT_RECHARGE.getCode());//业务类型
        cdm.setCustomerRateType(merUser.getMerRateType());//商户业务费率类型
        cdm.setCustomerRate(merUser.getMerRate());//商户业务费率
        cdm.setOrderAmount(clearingDataModel.getOrderAmount());//订单金额
        cdm.setCustomerRealPayAmount(0);//商户实际支付金额
        cdm.setDdpToCustomerRealFee(0);//DDP实际转给商户业务费用
        //计算商户应得分润
        long customerShouldProfit = 0;
        //如果费率类型是千分比
        if (RateTypeEnum.PERMILLAGE.getCode().equals(merUser.getMerRateType())) {
            String amount = String.valueOf((double) clearingDataModel.getOrderAmount() * merUser.getMerRate() / 1000);
            if (amount.contains(".")) {
                customerShouldProfit = Long.valueOf(amount.substring(0, amount.indexOf(".")));
            } else {
                customerShouldProfit = Long.valueOf(amount);
            }
            //如果费率类型是单笔
        } else if (RateTypeEnum.SINGLE_AMOUNT.getCode().equals(merUser.getMerRateType())) {
            customerShouldProfit = Math.round(merUser.getMerRate());
        }
        cdm.setCustomerShouldProfit(customerShouldProfit);//商户应得分润
        //查询下级商户信息
//        List<MerchantUserInfo> subMerUser = clearingTaskMapper.querySubMerchantUserInfo(merUser.getMerCode());
        if (SubMerUser == null) {
            cdm.setCustomerRealProfit(customerShouldProfit);//商户实际分润  应得分润减去下级商户分润
        } else {
            long subCustomerShouldProfit = 0;
//            for (MerchantUserInfo merchantUserInfo : subMerUser) {
//                for (MerChant merchant : merChantList) {
//                    if (!merchantUserInfo.getMerCode().equals(clearingDataModel.getCustomerNo()) && merchantUserInfo.getMerParentCode().equals(merchant.getMerCode())) {
            //计算下级商户应得分润
            //如果费率类型是千分比
            if (RateTypeEnum.PERMILLAGE.getCode().equals(SubMerUser.getMerRateType())) {
                String amount = String.valueOf((double) clearingDataModel.getOrderAmount() * SubMerUser.getMerRate() / 1000);
                if (amount.contains(".")) {
                    subCustomerShouldProfit = Long.valueOf(amount.substring(0, amount.indexOf(".")));
                } else {
                    subCustomerShouldProfit = Long.valueOf(amount);
                }
                //如果费率类型是单笔
            } else if (RateTypeEnum.SINGLE_AMOUNT.getCode().equals(SubMerUser.getMerRateType())) {
                subCustomerShouldProfit = Math.round(SubMerUser.getMerRate());
            }
            cdm.setCustomerRealProfit(customerShouldProfit - subCustomerShouldProfit);//商户实际分润  应得分润减去下级商户分润
            cdm.setSubMerchantCode(SubMerUser.getMerCode());//下级商户code
            cdm.setSubMerchantName(SubMerUser.getMerName());//下级商户名称
            cdm.setSubMerchantShouldProfit(subCustomerShouldProfit);//下级商户应得分润
//                    }
//
//                }
//            }
        }
        cdm.setDdpGetMerchantPayFee(0);//ddp应收商户支付方式服务费
        cdm.setSupplierCode(clearingDataModel.getSupplierCode());//供应商code
        cdm.setSupplierName(clearingDataModel.getSupplierName());//供应商name
        cdm.setCityCode(clearingDataModel.getCityCode());//城市code
        cdm.setCityName(clearingDataModel.getCityName());//城市名称
        cdm.setBankClearingFlag(ClearingFlagEnum.NO_CLEARING.getCode());//与银行清分状态
        cdm.setSupplierClearingFlag(ClearingFlagEnum.NO_CLEARING.getCode());//与供应商清分状态
        cdm.setCustomerClearingFlag(ClearingFlagEnum.ALREADY_CLEARING.getCode());//与客户清分状态
        cdm.setCustomerClearingTime(getYestoday());//与客户清分时间
        cdm.setDataFlag("1");//分润标识，标识数据来源清分
        return cdm;
    }

    /**
     * @param clearingDataModel
     * @param merUser
     * @return
     * @description 获取添加分润数据信息
     */
    @Transactional
    public ProfitModel getProfitModel(ClearingDataModel clearingDataModel, MerchantUserInfo merUser, MerchantUserInfo SubMerUser) {
        ProfitModel pm = new ProfitModel();
        pm.setOrderNo(clearingDataModel.getOrderNo());//订单交易号
        pm.setOrderTime(clearingDataModel.getOrderDate());//订单时间
        pm.setCustomerCode(merUser.getMerCode());//商户编号
        pm.setCustomerName(merUser.getMerName());//商户名称
        pm.setBussinessType(RateCodeEnum.YKT_RECHARGE.getCode());//业务类型
        pm.setCustomerType(merUser.getMerUserType());//商户类型
        pm.setSource(clearingDataModel.getOrderFrom());//来源
        pm.setIscircle(clearingDataModel.getOrderCircle());//是否圈存
        pm.setCustomerRateType(merUser.getMerRateType());//商户业务费率类型
        pm.setCustomerRate(merUser.getMerRate());//商户业务费率
        pm.setOrderAmount(clearingDataModel.getOrderAmount());//订单金额
        //计算商户应得分润
        long customerShouldProfit = 0;
        //如果费率类型是千分比
        if (RateTypeEnum.PERMILLAGE.getCode().equals(merUser.getMerRateType())) {
            String amount = String.valueOf((double) clearingDataModel.getOrderAmount() * merUser.getMerRate() / 1000);
            if (amount.contains(".")) {
                customerShouldProfit = Long.valueOf(amount.substring(0, amount.indexOf(".")));
            } else {
                customerShouldProfit = Long.valueOf(amount);
            }
            //如果费率类型是单笔
        } else if (RateTypeEnum.SINGLE_AMOUNT.getCode().equals(merUser.getMerRateType())) {
            customerShouldProfit = Math.round(merUser.getMerRate());
        }
        pm.setCustomerShouldProfit(customerShouldProfit);//商户应得分润
        //查询下级商户信息
        //查询下级商户信息
//        List<MerchantUserInfo> subMerUser = clearingTaskMapper.querySubMerchantUserInfo(merUser.getMerCode());
        if (SubMerUser == null) {
            pm.setCustomerRealProfit(customerShouldProfit);//商户实际分润  应得分润减去下级商户分润
        } else {
            long subCustomerShouldProfit = 0;
//            for (MerchantUserInfo merchantUserInfo : subMerUser) {
//                for (MerChant merchant : merChantList) {
//                    if (!merchantUserInfo.getMerCode().equals(clearingDataModel.getCustomerNo()) && merchantUserInfo.getMerParentCode().equals(merchant.getMerCode())) {
            //计算下级商户应得分润
            //如果费率类型是千分比
            if (RateTypeEnum.PERMILLAGE.getCode().equals(SubMerUser.getMerRateType())) {
                String amount = String.valueOf((double) clearingDataModel.getOrderAmount() * SubMerUser.getMerRate() / 1000);
                if (amount.contains(".")) {
                    subCustomerShouldProfit = Long.valueOf(amount.substring(0, amount.indexOf(".")));
                } else {
                    subCustomerShouldProfit = Long.valueOf(amount);
                }
                //如果费率类型是单笔
            } else if (RateTypeEnum.SINGLE_AMOUNT.getCode().equals(SubMerUser.getMerRateType())) {
                subCustomerShouldProfit = Math.round(SubMerUser.getMerRate());
            }
            pm.setCustomerRealProfit(customerShouldProfit - subCustomerShouldProfit);//商户实际分润  应得分润减去下级商户分润
            pm.setSubCustomerCode(SubMerUser.getMerCode());//下级商户code
            pm.setSubCustomerName(SubMerUser.getMerName());//下级商户名称
            pm.setSubCustomerShouldProfit(subCustomerShouldProfit);//下级商户应得分润
//                    }
//
//                }
//            }
        }
//        pm.setCustomerSettlementTime(); //与客户结算时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
        pm.setProfitDate(dateStr);//分润日期
        return pm;
    }

    @Override
    public DodopalResponse<DodopalDataPage<ProfitCollectBean>> findProfitCollectListByPage(ProfitQuery query) {
        ProfitQueryDTO queryDTO = new ProfitQueryDTO();
        DodopalResponse<DodopalDataPage<ProfitCollectBean>> dodopalResponse = new DodopalResponse<DodopalDataPage<ProfitCollectBean>>();
        try {
            List<ProfitCollectBean> resResult = new ArrayList<ProfitCollectBean>();
            PropertyUtils.copyProperties(queryDTO, query);
            DodopalDataPage<ProfitCollectBean> pages = null;
            DodopalResponse<DodopalDataPage<ProfitCollectDTO>> dtoResponse = profitDelegate.findProfitCollectListByPage(queryDTO);
            if (ResponseCode.SUCCESS.equals(dtoResponse.getCode())) {
                for (ProfitCollectDTO collectDTO : dtoResponse.getResponseEntity().getRecords()) {
                    ProfitCollectBean collectBean = new ProfitCollectBean();
                    PropertyUtils.copyProperties(collectBean, collectDTO);
                    resResult.add(collectBean);
                }
                PageParameter page = DodopalDataPageUtil.convertPageInfo(dtoResponse.getResponseEntity());
                pages = new DodopalDataPage<ProfitCollectBean>(page, resResult);
            }
            dodopalResponse.setCode(dtoResponse.getCode());
            dodopalResponse.setResponseEntity(pages);
        }catch(HessianRuntimeException e){
       	 dodopalResponse.setCode(ResponseCode.OSS_USER_CONNECTION_ERROR);
            e.printStackTrace();
        } catch (Exception e) {
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        return dodopalResponse;
    }

    @Override
    public DodopalResponse<DodopalDataPage<ProfitDetailsBean>> findProfitDetailsListByPage(
            ProfitQuery query) {
        ProfitQueryDTO queryDTO = new ProfitQueryDTO();
        DodopalResponse<DodopalDataPage<ProfitDetailsBean>> dodopalResponse = new DodopalResponse<DodopalDataPage<ProfitDetailsBean>>();
        try {
            List<ProfitDetailsBean> resResult = new ArrayList<ProfitDetailsBean>();
            PropertyUtils.copyProperties(queryDTO, query);
            DodopalDataPage<ProfitDetailsBean> pages = null;
            DodopalResponse<DodopalDataPage<ProfitDetailsDTO>> dtoResponse = profitDelegate.findProfitDetailsListByPage(queryDTO);
            if (ResponseCode.SUCCESS.equals(dtoResponse.getCode())) {
                for (ProfitDetailsDTO detailsDTO : dtoResponse.getResponseEntity().getRecords()) {
                    ProfitDetailsBean detailsBean = new ProfitDetailsBean();
                    PropertyUtils.copyProperties(detailsBean, detailsDTO);
                    resResult.add(detailsBean);
                }
                PageParameter page = DodopalDataPageUtil.convertPageInfo(dtoResponse.getResponseEntity());
                pages = new DodopalDataPage<ProfitDetailsBean>(page, resResult);
            }
            dodopalResponse.setCode(dtoResponse.getCode());
            dodopalResponse.setResponseEntity(pages);
        }catch(HessianRuntimeException e){
        	 dodopalResponse.setCode(ResponseCode.OSS_USER_CONNECTION_ERROR);
             e.printStackTrace();
        } catch (Exception e) {
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        return dodopalResponse;
    }

    
}
