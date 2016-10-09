package com.dodopal.oss.business.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.ExcelModel;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.dao.ClearingBankMapper;
import com.dodopal.oss.business.dao.ClearingBankTxnMapper;
import com.dodopal.oss.business.dao.ClearingMerMapper;
import com.dodopal.oss.business.dao.ClearingMerTxnMapper;
import com.dodopal.oss.business.dao.ClearingYktCityMapper;
import com.dodopal.oss.business.dao.ClearingYktMapper;
import com.dodopal.oss.business.model.ClearingBank;
import com.dodopal.oss.business.model.ClearingBankTxn;
import com.dodopal.oss.business.model.ClearingMer;
import com.dodopal.oss.business.model.ClearingMerTxn;
import com.dodopal.oss.business.model.ClearingYkt;
import com.dodopal.oss.business.model.ClearingYktCity;
import com.dodopal.oss.business.model.dto.BankClearingResultQuery;
import com.dodopal.oss.business.model.dto.MerClearingResultQuery;
import com.dodopal.oss.business.model.dto.YktClearingResultQuery;
import com.dodopal.oss.business.service.ClearingResultService;

/**
 * 账户管理——清算管理（银行，供应商（通卡），商户） serviceImpl
 * @author dodopal
 */
@Service
public class ClearingResultServiceImpl implements ClearingResultService {

    @Autowired
    private ClearingBankMapper clearingBankMapper;
    @Autowired
    private ClearingBankTxnMapper clearingBankTxnMapper;
    @Autowired
    private ClearingYktMapper clearingYktMapper;
    @Autowired
    private ClearingYktCityMapper clearingYktCityMapper;
    @Autowired
    private ClearingMerMapper clearingMerMapper;
    @Autowired
    private ClearingMerTxnMapper clearingMerTxnMapper;

    //***********************************************   银行清算管理 START    **************************************************//

    /**
     * 银行清算分页查询
     */
    @Override
    public DodopalDataPage<ClearingBank> findBankClearingResultByPage(BankClearingResultQuery queryDto) {
        List<ClearingBank> bankClearingList = clearingBankMapper.findBankClearingResultByPage(queryDto);
        DodopalDataPage<ClearingBank> pages = new DodopalDataPage<ClearingBank>(queryDto.getPage(), bankClearingList);
        return pages;
    }

    /**
     * 具体银行业务类型清算查询
     */
    @Override
    public List<ClearingBankTxn> getBankTxnClearingResult(BankClearingResultQuery queryDto) {
        List<ClearingBankTxn> bankTxnClearing = clearingBankTxnMapper.getBankTxnClearingResult(queryDto);
        return bankTxnClearing;
    }

    /**
     * 银行清算结果导出
     */
    @Override
    public DodopalResponse<String> excelExportBankClearingResult(HttpServletResponse response, BankClearingResultQuery prdOrderQuery) {

        List<ClearingBank> lstData = clearingBankMapper.getBankClearingResultForExport(prdOrderQuery);
        
        List<ClearingBankTxn> txnList = clearingBankTxnMapper.getBankTxnClearingResult(prdOrderQuery);
        
        //超过导出限制抛异常
        if (lstData.size() + txnList.size() > ExcelUtil.EXPORT_MAX_COUNT) {
            throw new DDPException(ResponseCode.EXCEL_EXPORT_OVER_MAX);
        }

        // 【写法一，使用通用模板】
        // 列标题名称
        List<String> titleList = Arrays.asList("清算日期", "支付网关", "交易笔数", "交易金额（元）", "银行手续费（元）", "转账金额（元）", "清算时间");
        // 导出文件名(不支持中文)
        String fileName = "CLEARING_BANK";
        // sheet标题
        String title = "支付网关分类清算表";
        ExcelModel excelModel = new ExcelModel(fileName, title, titleList);

        //【写法二，使用自定义模板，需指定模板名称，并指定数据开始行，模板中请删除第三行以后的数据(包括样式)，否则无法写入】
        // ExcelModel excelModel = new ExcelModel("icdcOrderList");
        // excelModel.setDataStartIndex(3); //数据开始行, 可以不设置,默认为3(第四行)

        //【处理数据，结果为List<List<String>>】
        if (CollectionUtils.isNotEmpty(lstData)) {
            List<List<String>> dataList = new ArrayList<List<String>>(lstData.size());
            for (ClearingBank data : lstData) {
                List<String> rowList = new ArrayList<String>();
                // 清算日期
                rowList.add(data.getClearingDay());
                // 支付网关
                rowList.add(data.getPayGatewayView());
                // 交易笔数
                rowList.add(String.valueOf(data.getTradeCount()));
                // 交易金额（元）
                rowList.add(String.valueOf(data.getTradeAmount()));
                // 银行手续费（元）
                rowList.add(String.valueOf(data.getBankFee()));
                // 转账金额（元）
                rowList.add(String.valueOf(data.getTransferAmount()));
                // 清算时间
                rowList.add(DateUtils.formatDate(data.getCreateDate(), DateUtils.DATE_FULL_STR));

                // 【添加数据行】
                dataList.add(rowList);
                
                for (ClearingBankTxn txn : txnList) {
                    if (StringUtils.isNotBlank(data.getPayGateway()) && StringUtils.isNotBlank(data.getPayGateway())) {
                        if (data.getPayGateway().equals(txn.getPayGateway()) && data.getClearingDay().equals(txn.getClearingDay())) {
                            List<String> rowTxnList = new ArrayList<String>();
                            // 清算日期
                            rowTxnList.add("");
                            // 支付网关
                            rowTxnList.add(RateCodeEnum.getRateTypeByCode(txn.getTxnType()).getName());
                            // 交易笔数
                            rowTxnList.add(String.valueOf(txn.getTradeCount()));
                            // 交易金额（元）
                            rowTxnList.add(String.valueOf(txn.getTradeAmount()));
                            // 银行手续费（元）
                            rowTxnList.add(String.valueOf(txn.getBankFee()));
                            // 转账金额（元）
                            rowTxnList.add(String.valueOf(txn.getTransferAmount()));
                            // 清算时间
                            rowTxnList.add("");
                            
                            // 【添加数据行】
                            dataList.add(rowTxnList);
                        }
                    }
                }
            }
            // 【将数据集加入model】
            excelModel.setDataList(dataList);
        }

        return ExcelUtil.excelExport(excelModel, response);
    }

    //***********************************************   银行清算管理 END    **************************************************//

    //***********************************************   供应商清算管理 START    **************************************************//
    /**
     * 供应商（通卡）清算分页查询
     */
    @Override
    public DodopalDataPage<ClearingYkt> findYktClearingResultByPage(YktClearingResultQuery queryDto) {
        List<ClearingYkt> yktClearingList = clearingYktMapper.findYktClearingResultByPage(queryDto);
        DodopalDataPage<ClearingYkt> pages = new DodopalDataPage<ClearingYkt>(queryDto.getPage(), yktClearingList);
        return pages;
    }

    /**
     * 具体供应商（通卡）业务城市清算查询
     */
    @Override
    public List<ClearingYktCity> getYktCityClearingResult(YktClearingResultQuery queryDto) {
        List<ClearingYktCity> yktCityClearingList = clearingYktCityMapper.getYktCityClearingResult(queryDto);
        return yktCityClearingList;
    }

    /**
     * 供应商（通卡）清算结果导出
     */
    @Override
    public DodopalResponse<String> excelExportYktClearingResult(HttpServletResponse response, YktClearingResultQuery prdOrderQuery) {

        List<ClearingYkt> lstData = clearingYktMapper.getYktClearingResultForExport(prdOrderQuery);
        
        List<ClearingYktCity> txnList = clearingYktCityMapper.getYktCityClearingResult(prdOrderQuery);
        
        //超过导出限制抛异常
        if (lstData.size() + txnList.size() > ExcelUtil.EXPORT_MAX_COUNT) {
            throw new DDPException(ResponseCode.EXCEL_EXPORT_OVER_MAX);
        }

        // 【写法一，使用通用模板】
        // 列标题名称
        List<String> titleList = Arrays.asList("清算日期", "通卡公司编号", "通卡公司名称", "交易笔数", "交易金额（元）", "手续费（元）", "转账金额（元）", "清算时间");
        // 导出文件名(不支持中文)
        String fileName = "CLEARING_YKT";
        // sheet标题
        String title = "通卡公司分类清算表";
        ExcelModel excelModel = new ExcelModel(fileName, title, titleList);

        //【写法二，使用自定义模板，需指定模板名称，并指定数据开始行，模板中请删除第三行以后的数据(包括样式)，否则无法写入】
        // ExcelModel excelModel = new ExcelModel("icdcOrderList");
        // excelModel.setDataStartIndex(3); //数据开始行, 可以不设置,默认为3(第四行)

        //【处理数据，结果为List<List<String>>】
        if (CollectionUtils.isNotEmpty(lstData)) {
            List<List<String>> dataList = new ArrayList<List<String>>(lstData.size());
            for (ClearingYkt data : lstData) {
                List<String> rowList = new ArrayList<String>();
                // 清算日期
                rowList.add(data.getClearingDay());
                // 通卡公司编号
                rowList.add(data.getYktCode());
                // 通卡公司编号
                rowList.add(data.getYktName());
                // 交易笔数
                rowList.add(String.valueOf(data.getTradeCount()));
                // 交易金额（元）
                rowList.add(String.valueOf(data.getTradeAmount()));
                // 手续费（元）
                rowList.add(String.valueOf(data.getYktFee()));
                // 转账金额（元）
                rowList.add(String.valueOf(data.getTransferAmount()));
                // 清算时间
                rowList.add(DateUtils.formatDate(data.getCreateDate(), DateUtils.DATE_FULL_STR));

                // 【添加数据行】
                dataList.add(rowList);
                
                for (ClearingYktCity txn : txnList) {
                    if (txn.getYktCode().equals(data.getYktCode()) && txn.getClearingDay().equals(data.getClearingDay())) {
                        List<String> rowTxnList = new ArrayList<String>();
                        // 清算日期
                        rowTxnList.add("");
                        // 通卡公司编号
                        rowTxnList.add("");
                        // 通卡公司编号
                        rowTxnList.add(txn.getCityName());
                        // 交易笔数
                        rowTxnList.add(String.valueOf(txn.getTradeCount()));
                        // 交易金额（元）
                        rowTxnList.add(String.valueOf(txn.getTradeAmount()));
                        // 手续费（元）
                        rowTxnList.add(String.valueOf(txn.getYktFee()));
                        // 转账金额（元）
                        rowTxnList.add(String.valueOf(txn.getTransferAmount()));
                        // 清算时间
                        rowTxnList.add("");
                        
                        // 【添加数据行】
                        dataList.add(rowTxnList);
                    }
                }
            }
            // 【将数据集加入model】
            excelModel.setDataList(dataList);
        }

        return ExcelUtil.excelExport(excelModel, response);
    }

    //***********************************************   供应商清算管理 END    **************************************************//

    //***********************************************   商户清算管理 START    **************************************************//

    /**
     * 商户清算分页查询
     */
    @Override
    public DodopalDataPage<ClearingMer> findMerClearingResultByPage(MerClearingResultQuery queryDto) {
        List<ClearingMer> merClearingList = clearingMerMapper.findMerClearingResultByPage(queryDto);
        DodopalDataPage<ClearingMer> pages = new DodopalDataPage<ClearingMer>(queryDto.getPage(), merClearingList);
        return pages;
    }

    /**
     * 具体商户业务类型清算查询
     */
    @Override
    public List<ClearingMerTxn> getMerTxnClearingResult(MerClearingResultQuery queryDto) {
        List<ClearingMerTxn> merTxnClearingList = clearingMerTxnMapper.getMerTxnClearingResult(queryDto);
        return merTxnClearingList;
    }

    /**
     * 商户清算结果导出
     */
    @Override
    public DodopalResponse<String> excelExportMerClearingResult(HttpServletResponse response, MerClearingResultQuery prdOrderQuery) {

        //主表数据
        List<ClearingMer> lstData = clearingMerMapper.getMerClearingResultForExport(prdOrderQuery);
        //明细表数据
        List<ClearingMerTxn> txnList = clearingMerTxnMapper.getMerTxnClearingResult(prdOrderQuery);

        //超过导出限制抛异常
        if (lstData.size() + txnList.size() > ExcelUtil.EXPORT_MAX_COUNT) {
            throw new DDPException(ResponseCode.EXCEL_EXPORT_OVER_MAX);
        }

        // 【写法一，使用通用模板】
        // 列标题名称
        List<String> titleList = Arrays.asList("清算日期", "商户编号", "上级商户名称", "商户名称", "交易笔数", "交易金额（元）", "服务费（元）", "分润（元）", "实收本金（元）", "转出金额（元）", "清算时间");
        // 导出文件名(不支持中文)
        String fileName = "CLEARING_MER";
        // sheet标题
        String title = "商户分类清算表";
        ExcelModel excelModel = new ExcelModel(fileName, title, titleList);

        //【写法二，使用自定义模板，需指定模板名称，并指定数据开始行，模板中请删除第三行以后的数据(包括样式)，否则无法写入】
        // ExcelModel excelModel = new ExcelModel("icdcOrderList");
        // excelModel.setDataStartIndex(3); //数据开始行, 可以不设置,默认为3(第四行)

        //【处理数据，结果为List<List<String>>】
        if (CollectionUtils.isNotEmpty(lstData)) {
            List<List<String>> dataList = new ArrayList<List<String>>(lstData.size());
            for (ClearingMer data : lstData) {
                List<String> rowList = new ArrayList<String>();
                // 清算日期
                rowList.add(data.getClearingDay());
                // 商户编号
                rowList.add(data.getMerCode());
                // 上级商户名称
                if (StringUtils.isNotBlank(data.getParentMerName())) {
                    rowList.add(data.getParentMerName());
                } else {
                    rowList.add("");
                }
                // 商户名称
                rowList.add(data.getMerName());
                // 交易笔数
                rowList.add(String.valueOf(data.getTradeCount()));
                // 交易金额（元）
                rowList.add(String.valueOf(data.getTradeAmount()));
                // 服务费（元）
                rowList.add(String.valueOf(data.getServiceFee()));
                // 分润（元）
                rowList.add(String.valueOf(data.getMerProfit()));
                // 实收本金（元）
                rowList.add(String.valueOf(data.getRealFee()));
                // 转出金额（元）
                rowList.add(String.valueOf(data.getTransferAmount()));
                // 清算时间
                rowList.add(DateUtils.formatDate(data.getCreateDate(), DateUtils.DATE_FULL_STR));

                // 【添加数据行】
                dataList.add(rowList);

                //加入明细数据update by guanjl 20151202 start
                for (ClearingMerTxn txn : txnList) {
                    if (txn.getMerCode().equals(data.getMerCode()) && txn.getClearingDay().equals(data.getClearingDay())) {
                        List<String> rowTxnList = new ArrayList<String>();
                        // 清算日期
                        rowTxnList.add("");
                        // 商户编号
                        rowTxnList.add("");
                        // 上级商户名称
                        rowTxnList.add("");
                        // 商户名称--业务类型
                        rowTxnList.add(RateCodeEnum.getRateTypeByCode(txn.getTxnType()).getName());
                        // 交易笔数
                        rowTxnList.add(String.valueOf(txn.getTradeCount()));
                        // 交易金额（元）
                        rowTxnList.add(String.valueOf(txn.getTradeAmount()));
                        // 服务费（元）
                        rowTxnList.add(String.valueOf(txn.getServiceFee()));
                        // 分润（元）
                        rowTxnList.add(String.valueOf(txn.getMerProfit()));
                        // 实收本金（元）
                        rowTxnList.add(String.valueOf(txn.getRealFee()));
                        // 转出金额（元）
                        rowTxnList.add(String.valueOf(txn.getTransferAmount()));
                        // 清算时间
                        rowTxnList.add("");
                        // 【添加数据行】
                        dataList.add(rowTxnList);
                    }
                }
                //加入明细数据update by guanjl 20151202 end
            }
            // 【将数据集加入model】
            excelModel.setDataList(dataList);
        }

        return ExcelUtil.excelExport(excelModel, response);
    }

    //***********************************************   商户清算管理 END    **************************************************//

    /**
     * @author Mikaelyan
     * excel导出
     */
	public DodopalResponse<String> findBankClearingResultExportByPage(HttpServletRequest req, HttpServletResponse res, BankClearingResultQuery prdOrderQuery, Map<String, String> col) {
		DodopalResponse<String> ddpres = new DodopalResponse<String>();
		
		List<ClearingBank> lstData = clearingBankMapper.getBankClearingResultForExport(prdOrderQuery);
        List<ClearingBankTxn> txnList = clearingBankTxnMapper.getBankTxnClearingResult(prdOrderQuery);
        List<ClearingBank> dataList = new ArrayList<ClearingBank>();
        if (CollectionUtils.isNotEmpty(lstData)) {
            for (ClearingBank data : lstData) {
            	ClearingBank temp = new ClearingBank();
            	
            	String cd = data.getClearingDay();
            	temp.setClearingDay(cd.substring(0, 4)+"-"+cd.substring(4, 6)+"-"+cd.substring(6, 8));
            	temp.setPayGateway(data.getPayGatewayView());
            	temp.setTradeCount(data.getTradeCount());
            	temp.setTradeAmount(data.getTradeAmount());
            	temp.setBankFee(data.getBankFee());
            	temp.setTransferAmount(data.getTransferAmount());
            	temp.setClearingDate(data.getClearingDate());
            	temp.setCreateUser(data.getCreateUser());
            	temp.setCreateDate(data.getCreateDate());
            	temp.setUpdateUser(data.getUpdateUser());
            	temp.setUpdateDate(data.getUpdateDate());
            	
            	dataList.add(temp);
                for (ClearingBankTxn txn : txnList) {
                    if (StringUtils.isNotBlank(data.getPayGateway()) && StringUtils.isNotBlank(data.getPayGateway())) {
                        if (data.getPayGateway().equals(txn.getPayGateway()) && data.getClearingDay().equals(txn.getClearingDay())) {
                        	ClearingBank cb = new ClearingBank();
                            // 清算日期
                            cb.setClearingDay("");
                            // 支付网关
                            cb.setPayGateway(RateCodeEnum.getRateTypeByCode(txn.getTxnType()).getName());
                            // 交易笔数
                            cb.setTradeCount(txn.getTradeCount());
                            // 交易金额（元）
                            cb.setTradeAmount(txn.getTradeAmount());
                            // 银行手续费（元）
                            cb.setBankFee(txn.getBankFee());
                            // 转账金额（元）
                            cb.setTransferAmount(txn.getTransferAmount());
                            
                            // 【添加数据行】
                            dataList.add(cb);
                        }
                    }
                }
            }
        }
        
        String sheetName = "银行清算";
        String resultCode = ExcelUtil.excelExport(req, res, dataList, col, sheetName);
        ddpres.setCode(resultCode);
        return ddpres;
	}

	/**
	 * @author Mikaelyan
	 * Excel导出
	 */
	public DodopalResponse<String> excelExportYktClearingResultExport(HttpServletRequest req, HttpServletResponse res, YktClearingResultQuery queryDto, Map<String, String> col) {
		DodopalResponse<String> ddpres = new DodopalResponse<String>();
		
		List<ClearingYkt> lstData = clearingYktMapper.getYktClearingResultForExport(queryDto);
        List<ClearingYktCity> txnList = clearingYktCityMapper.getYktCityClearingResult(queryDto);
        List<ClearingYkt> dataList = new ArrayList<ClearingYkt>();
        
        if (CollectionUtils.isNotEmpty(lstData)) {
            for (ClearingYkt data : lstData) {
            	ClearingYkt temp = new ClearingYkt();
            	String cd = data.getClearingDay();
            	temp.setClearingDay(cd.substring(0, 4)+"-"+cd.substring(4, 6)+"-"+cd.substring(6));
            	temp.setYktCode(data.getYktCode());
            	temp.setYktName(data.getYktName());
            	temp.setTradeCount(data.getTradeCount());
            	temp.setTradeAmount(data.getTradeAmount());
            	temp.setYktFee(data.getYktFee());
            	temp.setTransferAmount(data.getTransferAmount());
            	temp.setClearingDate(data.getClearingDate());
            	temp.setCreateUser(data.getCreateUser());
            	temp.setCreateDate(data.getCreateDate());
            	temp.setUpdateUser(data.getUpdateUser());
            	temp.setUpdateDate(data.getUpdateDate());

                dataList.add(temp);
                
                for (ClearingYktCity txn : txnList) {
                    if (txn.getYktCode().equals(data.getYktCode()) && txn.getClearingDay().equals(data.getClearingDay())) {
                    	ClearingYkt cy = new ClearingYkt();
                    	cy.setClearingDay("");
                    	cy.setYktCode("");
                    	cy.setYktName(txn.getCityName());
                    	cy.setTradeCount(txn.getTradeCount());
                    	cy.setTradeAmount(txn.getTradeAmount());
                    	cy.setYktFee(txn.getYktFee());
                        cy.setTransferAmount(txn.getTransferAmount());
                        
                        dataList.add(cy);
                    }
                }
            }
        }
        String sheetName = new String("供应商清算");
        String resultCode = ExcelUtil.excelExport(req, res, dataList, col, sheetName);
        ddpres.setCode(resultCode);
		return ddpres;
	}

	/**
	 * @author Mikaelyan
	 * EXCEL导出
	 */
	public DodopalResponse<String> excelExportMerClearingResultExport(HttpServletRequest req, HttpServletResponse res, MerClearingResultQuery queryDto, Map<String, String> col) {
		DodopalResponse<String> ddpres = new DodopalResponse<String>();
		
        List<ClearingMer> lstData = clearingMerMapper.getMerClearingResultForExport(queryDto);
        List<ClearingMerTxn> txnList = clearingMerTxnMapper.getMerTxnClearingResult(queryDto);
        List<ClearingMer> dataList = new ArrayList<ClearingMer>();
        
        if (CollectionUtils.isNotEmpty(lstData)) {
            for (ClearingMer data : lstData) {
                ClearingMer temp = new ClearingMer();
                String cd = data.getClearingDay();

                temp.setClearingDay(cd.substring(0, 4)+"-"+cd.substring(4, 6)+"-"+cd.substring(6, 8));
                temp.setMerCode(data.getMerCode());
                temp.setParentMerName(data.getParentMerName());
                temp.setMerName(data.getMerName());
                
                temp.setTradeCount(data.getTradeCount());
                temp.setTradeAmount(data.getTradeAmount());
                
                temp.setServiceFee(data.getServiceFee());
                temp.setMerProfit(data.getMerProfit());
                
                temp.setRealFee(data.getRealFee());
                temp.setTransferAmount(data.getTransferAmount());
                temp.setClearingDate(data.getClearingDate());
                
                temp.setCreateUser(data.getCreateUser());
                temp.setCreateDate(data.getCreateDate());
                temp.setUpdateUser(data.getUpdateUser());
                temp.setUpdateDate(data.getUpdateDate());

                dataList.add(temp);
                for (ClearingMerTxn txn : txnList) {
                    if (txn.getMerCode().equals(data.getMerCode()) && txn.getClearingDay().equals(data.getClearingDay())) {
                    	ClearingMer cm = new ClearingMer();
                    	cm.setClearingDay("");
                    	cm.setMerCode("");
                    	cm.setParentMerName("");
                    	cm.setMerName(RateCodeEnum.getRateTypeByCode(txn.getTxnType()).getName());
                    	cm.setTradeCount(txn.getTradeCount());
                    	cm.setTradeAmount(txn.getTradeAmount());
                    	cm.setServiceFee(txn.getServiceFee());
                    	cm.setMerProfit(txn.getMerProfit());
                    	cm.setRealFee(txn.getRealFee());
                    	cm.setTransferAmount(txn.getTransferAmount());
                        dataList.add(cm);
                    }
                }
            }
        }
        String sheetName = new String("商户清算");
        String resultCode = ExcelUtil.excelExport(req, res, dataList, col, sheetName);
        ddpres.setCode(resultCode);
        return ddpres;
	}
	
	
	
}
