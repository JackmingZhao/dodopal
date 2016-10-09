package com.dodopal.product.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.AuditStateEnum;
import com.dodopal.common.enums.CheckStateEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.product.business.dao.ProductYktLimitInfoMapper;
import com.dodopal.product.business.model.ProductYktLimitBatchInfo;
import com.dodopal.product.business.model.ProductYktLimitInfo;
import com.dodopal.product.business.model.query.ProductYktLimitBatchInfoQuery;
import com.dodopal.product.business.model.query.ProductYktLimitInfoQuery;
import com.dodopal.product.business.service.ProductYktLimitInfoService;

@Service
public class ProductYktLimitInfoServiceImpl implements ProductYktLimitInfoService {

    /**
     * 数据库最大容量
     */
    private static final long TOTALBALANCE_MAX = 9999999999L;

    @Autowired
    private ProductYktLimitInfoMapper productYktLimitInfoMapper;

    @Override
    @Transactional
    public int addProductYktLimitInfo(ProductYktLimitInfo limitInfo) {
        int num = productYktLimitInfoMapper.addProductYktLimitInfo(limitInfo);
        return num;
    }

    @Override
    @Transactional(readOnly = true)
    public DodopalDataPage<ProductYktLimitInfo> findProductYktLimitInfoByPage(ProductYktLimitInfoQuery query) {
        List<ProductYktLimitInfo> result = productYktLimitInfoMapper.findProductYktLimitInfoByPage(query);
        DodopalDataPage<ProductYktLimitInfo> pages = new DodopalDataPage<ProductYktLimitInfo>(query.getPage(), result);
        return pages;
    }

    @Override
    public DodopalResponse<List<ProductYktLimitInfo>> getProductYktLimitListForExportExcel(ProductYktLimitInfoQuery query) {
        
        DodopalResponse<List<ProductYktLimitInfo>> response = new DodopalResponse<List<ProductYktLimitInfo>>();
        
        //  检查导出数据是否超过限制列数
        int exportCount = productYktLimitInfoMapper.getCountForProductYktLimitExportExcel(query);
        if (exportCount > ExcelUtil.EXPORT_MAX_COUNT) {
            response.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
            return response;
        }
        
        //  获取导出数据
        List<ProductYktLimitInfo> list = productYktLimitInfoMapper.getListForProductYktLimitExportExcel(query);
       
        response.setCode(ResponseCode.SUCCESS);
        response.setResponseEntity(list);
        return response;
    }
    
    @Override
    @Transactional(readOnly = true)
    public ProductYktLimitInfo findProductYktLimitInfoById(String limitId) {
        ProductYktLimitInfo result = productYktLimitInfoMapper.findProductYktLimitInfoById(limitId);
        return result;
    }

    @Override
    @Transactional
    public int updateProductYktLimitInfo(ProductYktLimitInfo limitInfo) {
        int num = productYktLimitInfoMapper.updateProductYktLimitInfo(limitInfo);
        return num;
    }

    /**
     * 分页查询批次申请单列表信息
     */
    @Override
    @Transactional(readOnly = true)
    public DodopalDataPage<ProductYktLimitBatchInfo> findProductYktLimitBatchInfoByPage(ProductYktLimitBatchInfoQuery query) {
        List<ProductYktLimitBatchInfo> result = productYktLimitInfoMapper.findProductYktLimitBatchInfoByPage(query);
        DodopalDataPage<ProductYktLimitBatchInfo> pages = new DodopalDataPage<ProductYktLimitBatchInfo>(query.getPage(), result);
        return pages;
    }

    /**
     * 获取批次申请单详细
     */
    @Override
    @Transactional(readOnly = true)
    public ProductYktLimitBatchInfo findProductYktLimitBatchInfoById(String id) {
        ProductYktLimitBatchInfo result = productYktLimitInfoMapper.findProductYktLimitBatchInfoById(id);
        return result;
    }

    /**
     * 获取批次申请单导出EXCEL信息
     */
    @Override
    public DodopalResponse<List<ProductYktLimitBatchInfo>> getProductYktLimitBatchListForExportExcel(ProductYktLimitBatchInfoQuery query) {
        
        DodopalResponse<List<ProductYktLimitBatchInfo>> response = new DodopalResponse<List<ProductYktLimitBatchInfo>>();
        
        //  检查导出数据是否超过限制列数
        int exportCount = productYktLimitInfoMapper.getCountForProductYktBatchLimitExportExcel(query);
        if (exportCount > ExcelUtil.EXPORT_MAX_COUNT) {
            response.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
            return response;
        }
        
        //  获取导出数据
        List<ProductYktLimitBatchInfo> list = productYktLimitInfoMapper.getListForProductYktBatchLimitExportExcel(query);
       
        response.setCode(ResponseCode.SUCCESS);
        response.setResponseEntity(list);
        return response;
    }

    /**
     * 追加批次申请单
     */
    @Override
    @Transactional
    public DodopalResponse<Integer> addProductYktLimitBatchInfo(ProductYktLimitBatchInfo productYktLimitBatchInfo) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);

        int num = productYktLimitInfoMapper.addProductYktLimitBatchInfo(productYktLimitBatchInfo);
        response.setResponseEntity(num);
        return response;
    }

    /**
     * 删除批次申请单
     */
    @Override
    @Transactional
    public DodopalResponse<Integer> deleteProductYktLimitBatchInfo(String id) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);
        int num = 0;
        ProductYktLimitBatchInfo batchInfo = productYktLimitInfoMapper.findProductYktLimitBatchInfoById(id);
        if (batchInfo != null) {
            if (AuditStateEnum.UN_AUDIT.getCode().equals(batchInfo.getAuditState())) {

                // 只有“未审核”的申请单可被删除
                num = productYktLimitInfoMapper.deleteProductYktLimitBatchInfo(id);
            } else {
                response.setCode(ResponseCode.PRODUCT_DELETE_APPLY_ORDER_ERROR);// 只能删除未审核的申请单！
            }
        }
        response.setResponseEntity(num);
        return response;
    }

    /**
     * 修改批次申请单
     */
    @Override
    @Transactional
    public DodopalResponse<Integer> updateProductYktLimitBatchInfo(ProductYktLimitBatchInfo limitbatchInfo) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);
        int num = 0;

        // 只有“未审核”的申请单，才可以被修改
        ProductYktLimitBatchInfo batchInfo = productYktLimitInfoMapper.findProductYktLimitBatchInfoById(limitbatchInfo.getId());
        if (batchInfo != null) {
            if (AuditStateEnum.UN_AUDIT.getCode().equals(batchInfo.getAuditState())) {

                // 申请额度的位数超过数据库最大位数
                if (limitbatchInfo.getApplyAmtLimit() > TOTALBALANCE_MAX) {
                    response.setCode(ResponseCode.PRODUCT_FIND_APPLY_ADD_LIMIT_OUTNUMBER);
                    return response;
                }
                num = productYktLimitInfoMapper.updateProductYktLimitBatchInfo(limitbatchInfo);
            } else {
                response.setCode(ResponseCode.PRODUCT_UPDATE_APPLY_ORDER_ERROR);// 只能编辑未审核的申请单！
            }
        } else {
            response.setCode(ResponseCode.PRODUCT_APPLY_ORDER_NOT_EXSIT);// 申请单不存在
        }

        response.setResponseEntity(num);
        return response;
    }

    /**
     * 审核批次申请单
     */
    @Override
    @Transactional
    public DodopalResponse<Integer> auditProductYktLimitBatchInfo(ProductYktLimitBatchInfo limitbatchInfo) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);

        // 只有“未审核”的申请单，才可以被修改
        ProductYktLimitBatchInfo batchInfo = productYktLimitInfoMapper.findProductYktLimitBatchInfoById(limitbatchInfo.getId());

        if (batchInfo != null) {
            if (AuditStateEnum.UN_AUDIT.getCode().equals(batchInfo.getAuditState())) {

                // 审核通过
                if (AuditStateEnum.AUDIT_SUCCESS.getCode().equals(limitbatchInfo.getAuditState())) {

                    // 财务打款额度的位数超过数据库最大位数
                    if (limitbatchInfo.getFinancialPayAmt() > TOTALBALANCE_MAX) {
                        response.setCode(ResponseCode.PRODUCT_FIND_FINANCIAL_PAY_AMT_OUTNUMBER);
                        return response;
                    }

                    // 打款手续费的位数超过数据库最大位数
                    if (limitbatchInfo.getFinancialPayFee() > TOTALBALANCE_MAX) {
                        response.setCode(ResponseCode.PRODUCT_FIND_PLAY_FEE__OUTNUMBER);
                        return response;
                    }

                    // 获取最新的通卡额度购买批次
                    int nextValue = productYktLimitInfoMapper.getNextBatchCodeByYktCode(limitbatchInfo.getYktCode());
                    limitbatchInfo.setBatchCode(nextValue);
                    
                    // 审核通过之后，设置复核状态：待复核
                    limitbatchInfo.setCheckState(CheckStateEnum.UN_CHECK.getCode());

                }

                // 更新批次申请单审核信息
                productYktLimitInfoMapper.auditProductYktLimitBatchInfo(limitbatchInfo);
            } else {
                response.setCode(ResponseCode.PRODUCT_AUDIT_APPLY_ORDER_ERROR);// 只能审核未审核的申请单！
            }
        } else {
            response.setCode(ResponseCode.PRODUCT_APPLY_ORDER_NOT_EXSIT);// 申请单不存在
        }

        return response;
    }

    /**
     * 复核批次申请单
     */
    @Override
    @Transactional
    public DodopalResponse<Integer> checkProductYktLimitBatchInfo(ProductYktLimitBatchInfo limitbatchInfo) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);

        // 只有“审核通过”且未处于“带复核”或“复核中”的申请单，才可以被修改
        ProductYktLimitBatchInfo batchInfo = productYktLimitInfoMapper.findProductYktLimitBatchInfoById(limitbatchInfo.getId());

        if (batchInfo == null) {
            response.setCode(ResponseCode.PRODUCT_APPLY_ORDER_NOT_EXSIT);// 申请单不存在
            return response;
        } else {
            if (!AuditStateEnum.AUDIT_SUCCESS.getCode().equals(batchInfo.getAuditState())) {
                response.setCode(ResponseCode.PRODUCT_FIND_CHECK_AUDIT_PASS);// 只能复核审核通过的申请单！
                return response;
            }
            
            if (CheckStateEnum.CHECK_DONE.getCode().equals(batchInfo.getCheckState())) {
                response.setCode(ResponseCode.PRODUCT_FIND_CHECK_DONE);// 该申请单已经复核完了！
                return response;
            }
            
            // 设置复核状态（判断本次复核是否已经复核完了）
            limitbatchInfo.setYktAddLimit(limitbatchInfo.getOldYktAddLimit() + limitbatchInfo.getNewYktAddLimit());
            
            if (limitbatchInfo.getYktAddLimit() > batchInfo.getFinancialPayAmt()) {
                response.setCode(ResponseCode.PRODUCT_FIND_CHECK_YKTADDLIMIT);// 复核通卡本次追加额度参数有误（累计额度不得超过财务打款额度）！
                return response;
            } else if (limitbatchInfo.getYktAddLimit() < batchInfo.getFinancialPayAmt()) {
                // 当本次复核通卡增加额度依然没有达到财务打款额度时，将复核状态设置：复核中
                limitbatchInfo.setCheckState(CheckStateEnum.CHECK_ING.getCode());
            } else if (limitbatchInfo.getYktAddLimit().equals(batchInfo.getFinancialPayAmt())) {
                // 当本次复核通卡增加额度依然没有达到财务打款额度时，将复核状态设置：复核完了
                limitbatchInfo.setCheckState(CheckStateEnum.CHECK_DONE.getCode());
            }
            
            // 通卡增加额度的位数超过数据库最大位数
            if (limitbatchInfo.getYktAddLimit() > TOTALBALANCE_MAX) {
                response.setCode(ResponseCode.PRODUCT_FIND_YKT_ADD_LIMIT_OUTNUMBER);
                return response;
            }

            // 查询通卡现行的额度信息
            ProductYktLimitInfo oldYktlimitInfo = productYktLimitInfoMapper.findYktLimitInfoByYktCode(limitbatchInfo.getYktCode());
            long totalAmtLimit = limitbatchInfo.getNewYktAddLimit();
            if (oldYktlimitInfo.getTotalAmtLimit() != null) {
                totalAmtLimit += oldYktlimitInfo.getTotalAmtLimit();
            }
            // 追加判断累加的总额度会不会超过数据库容量最大额度
            if (totalAmtLimit > TOTALBALANCE_MAX) {
                response.setCode(ResponseCode.PRODUCT_FIND_ADD_LIMIT_OUTNUMBER);
                return response;
            }
            long surPlusLimit = limitbatchInfo.getNewYktAddLimit();
            if (oldYktlimitInfo.getSurPlusLimit() != null) {
                surPlusLimit += oldYktlimitInfo.getSurPlusLimit();
            }
            // 追加判断对冲的总额度会不会超过剩余额度
            if (surPlusLimit < 0) {
                response.setCode(ResponseCode.PRODUCT_FIND_SUB_LIMIT_OUTNUMBER);
                return response;
            }
            
            // 审核通过，更新对应通卡公司总额度信息
            ProductYktLimitInfo newYktLimitInfo = new ProductYktLimitInfo();
            newYktLimitInfo.setYktCode(limitbatchInfo.getYktCode());
            newYktLimitInfo.setTotalAmtLimit(totalAmtLimit);
            newYktLimitInfo.setSurPlusLimit(surPlusLimit);
            newYktLimitInfo.setUpdateUser(limitbatchInfo.getAuditUser());
            productYktLimitInfoMapper.updateYktLimitInfoAfterAuditBatch(newYktLimitInfo);
        }

        // 更新批次申请单审核信息
        productYktLimitInfoMapper.checkProductYktLimitBatchInfo(limitbatchInfo);

        return response;
    }

}
