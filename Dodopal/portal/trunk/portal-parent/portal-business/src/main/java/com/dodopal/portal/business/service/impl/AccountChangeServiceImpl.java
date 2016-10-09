package com.dodopal.portal.business.service.impl;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.dodopal.common.enums.AccTranTypeEnum;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.account.dto.FundChangeDTO;
import com.dodopal.api.account.dto.query.FundChangeQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.ExcelModel;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.portal.business.bean.AccountChange;
import com.dodopal.portal.business.model.query.FundChangeQuery;
import com.dodopal.portal.business.service.AccountChangeService;
import com.dodopal.portal.delegate.AccountChangeDelegate;

@Service
public class AccountChangeServiceImpl implements AccountChangeService{

	private final static Logger log = LoggerFactory.getLogger(AccountChangeServiceImpl.class);
	
	@Autowired
	private AccountChangeDelegate accountChangeDelegate;
	
	/**
	 * 资金变更记录
	 */
	public DodopalResponse<DodopalDataPage<AccountChange>> findFundsChangeRecordsByPage(FundChangeQuery query) {
		log.info("findFundsChangeRecordsByPage this query:"+query);
		
		DodopalResponse<DodopalDataPage<AccountChange>> response = new DodopalResponse<DodopalDataPage<AccountChange>>();
		FundChangeQueryDTO queryDTO = new FundChangeQueryDTO();
		//开始时间
		if (StringUtils.isNotBlank(query.getStartDate())) {
			queryDTO.setStartDate(format(query.getStartDate()));
        }
		//结束时间
        if (StringUtils.isNotBlank(query.getEndDate())) {
        	queryDTO.setEndDate(format(query.getEndDate()));
        }
        //最小交易金额范围
        if (StringUtils.isNotBlank(query.getChangeAmountMin())) {
        	//queryDTO.setChangeAmountMin(Long.parseLong(query.getChangeAmountMin())*100);
        	if(StringUtils.isNotBlank(query.getChangeAmountMax())){
        		if(Double.parseDouble(query.getChangeAmountMax()) < Double.parseDouble(query.getChangeAmountMin())){
        			queryDTO.setChangeAmountMin(new Double(Double.parseDouble(query.getChangeAmountMax())*100).longValue());
        		}else{
        			queryDTO.setChangeAmountMin(new Double(Double.parseDouble(query.getChangeAmountMin())*100).longValue());
        		}
        	}else{
        		queryDTO.setChangeAmountMin(new Double(Double.parseDouble(query.getChangeAmountMin())*100).longValue());
        	}
        }
         //最大交易金额范围
        if (StringUtils.isNotBlank(query.getChangeAmountMax())) {
        	//queryDTO.setChangeAmountMax(Long.parseLong(query.getChangeAmountMax())*100);
        	if(StringUtils.isNotBlank(query.getChangeAmountMin())){
        		if(Double.parseDouble(query.getChangeAmountMax()) < Double.parseDouble(query.getChangeAmountMin())){
        			queryDTO.setChangeAmountMax(new Double(Double.parseDouble(query.getChangeAmountMin())*100).longValue());
        		}else{
        			queryDTO.setChangeAmountMax(new Double(Double.parseDouble(query.getChangeAmountMax())*100).longValue());
        		}
        	}else{
        		queryDTO.setChangeAmountMax(new Double(Double.parseDouble(query.getChangeAmountMax())*100).longValue());
        	}
        }
     	//变动类型
        if (StringUtils.isNotBlank(query.getChangeType())) {
        	queryDTO.setChangeType(query.getChangeType());
        }
        //资金类别
        if (StringUtils.isNotBlank(query.getFundType())) {
        	queryDTO.setFundType(query.getFundType());
        }
        //主账户数据库id
        if (StringUtils.isNotBlank(query.getAcid())) {
        	queryDTO.setAcid(query.getAcid());
        }
        if (StringUtils.isNotBlank(query.getCreateUser())) {
        	queryDTO.setCreateUser(query.getCreateUser());
        }
      //分页参数
        if (query.getPage() != null) {
        	queryDTO.setPage(query.getPage());;
        }
        
        DodopalResponse<DodopalDataPage<FundChangeDTO>> dodopalResponse = accountChangeDelegate.findFundsChangeRecordsByPage(queryDTO);
        
        try {
        	if(ResponseCode.SUCCESS.equals(dodopalResponse.getCode())){
        		List<AccountChange> list = new ArrayList<AccountChange>();
        		if (dodopalResponse.getResponseEntity() != null && CollectionUtils.isNotEmpty(dodopalResponse.getResponseEntity().getRecords())) {
        			for(FundChangeDTO dto : dodopalResponse.getResponseEntity().getRecords()){
        				AccountChange accountChange = new AccountChange();
        				PropertyUtils.copyProperties(accountChange, dto);
        				accountChange.setChangeAmount((double) dto.getChangeAmount() / 100);
        				accountChange.setBeforeChangeAmount((double) dto.getBeforeChangeAmount() / 100);
        				accountChange.setBeforeChangeAvailableAmount((double) dto.getBeforeChangeAvailableAmount() / 100);
        				accountChange.setBeforeChangeFrozenAmount((double)dto.getBeforeChangeFrozenAmount()/100);
						//冻结
						if(AccTranTypeEnum.ACC_FREEZE.getCode().equals(dto.getChangeType())){
							accountChange.setAfterChangeAvailableAmount((double) (dto.getBeforeChangeAvailableAmount() - dto.getChangeAmount()) / 100);
							accountChange.setAfterChangeFrozenAmount((double)(dto.getBeforeChangeFrozenAmount()+dto.getChangeAmount())/100);
						}
						//扣款
						if(AccTranTypeEnum.ACC_CONSUME.getCode().equals(dto.getChangeType())){
							accountChange.setAfterChangeAvailableAmount((double)dto.getBeforeChangeAvailableAmount()/100);
							accountChange.setAfterChangeFrozenAmount((double)(dto.getBeforeChangeFrozenAmount()-dto.getChangeAmount())/100);
						}
						//解冻
						if(AccTranTypeEnum.ACC_UNFREEZE.getCode().equals(dto.getChangeType())){
							accountChange.setAfterChangeAvailableAmount((double)(dto.getBeforeChangeAvailableAmount()+dto.getChangeAmount())/100);
							accountChange.setAfterChangeFrozenAmount((double)(dto.getBeforeChangeFrozenAmount()-dto.getChangeAmount())/100);
						}//充值
						if(AccTranTypeEnum.ACC_RECHARGE.getCode().equals(dto.getChangeType())){
							accountChange.setAfterChangeAvailableAmount((double)(dto.getBeforeChangeAvailableAmount()+dto.getChangeAmount())/100);
							accountChange.setAfterChangeFrozenAmount((double)dto.getBeforeChangeFrozenAmount()/100);
						}//转入
						if(AccTranTypeEnum.ACC_TRANSFER_IN.getCode().equals(dto.getChangeType())){
							accountChange.setAfterChangeAvailableAmount((double)(dto.getBeforeChangeAvailableAmount()+dto.getChangeAmount())/100);
							accountChange.setAfterChangeFrozenAmount((double)dto.getBeforeChangeFrozenAmount()/100);
						}
						//转出
						if(AccTranTypeEnum.ACC_TRANSFER_OUT.getCode().equals(dto.getChangeType())){
							accountChange.setAfterChangeAvailableAmount((double)(dto.getBeforeChangeAvailableAmount()-dto.getChangeAmount())/100);
							accountChange.setAfterChangeFrozenAmount((double)dto.getBeforeChangeFrozenAmount()/100);
						}
						//正调账
						if(AccTranTypeEnum.ACC_PT_AD.getCode().equals(dto.getChangeType())){
							accountChange.setAfterChangeAvailableAmount((double)(dto.getBeforeChangeAvailableAmount()+dto.getChangeAmount())/100);
							accountChange.setAfterChangeFrozenAmount((double)dto.getBeforeChangeFrozenAmount()/100);
						}
						//反调账
						if(AccTranTypeEnum.ACC_NEG_AD.getCode().equals(dto.getChangeType())){
							accountChange.setAfterChangeAvailableAmount((double)(dto.getBeforeChangeAvailableAmount()-dto.getChangeAmount())/100);
							accountChange.setAfterChangeFrozenAmount((double)dto.getBeforeChangeFrozenAmount()/100);
						}
        				list.add(accountChange);
        			}
        		}
        		//后台传入总页数，总条数，当前页
				PageParameter page = DodopalDataPageUtil.convertPageInfo(dodopalResponse.getResponseEntity());
				DodopalDataPage<AccountChange> pages = new DodopalDataPage<AccountChange>(page, list);
				response.setCode(dodopalResponse.getCode());
				response.setResponseEntity(pages);
        		
        	}else{
        		response.setCode(dodopalResponse.getCode());
        	}
        	
			
		} catch (Exception e) {
			e.printStackTrace();
            log.error("AccountChangeServiceImpl findFundsChangeRecordsByPage throws:" ,e);
            response.setCode(dodopalResponse.getCode());
		}
        
		return response;
	}

	
	/**
	 * 时间转换
	 * @param date
	 * @return
	 */
	public  Date format(String date){
		DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
		Date response = new Date();
		try {
			response = fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return response;
	}


	//资金变更记录
	public DodopalResponse<String> excelExport(HttpServletResponse response,FundChangeQueryDTO query) {
		DodopalResponse<List<FundChangeDTO>> changeRecordsAll = accountChangeDelegate.findFundsChangeRecordsAll(query);
		List<FundChangeDTO> tranDTO = changeRecordsAll.getResponseEntity();
		List<AccountChange> tranList = new ArrayList<AccountChange>();
		DodopalResponse<String> excelExport = new DodopalResponse<String>();
		try {
		    ExcelModel excelModel = new ExcelModel("accountChangeRecord");
            excelModel.setDataStartIndex(3); //数据开始行, 可以不设置,默认为3(第四行)
            
			if (CollectionUtils.isNotEmpty(tranDTO)) {
			for(FundChangeDTO dto : tranDTO){
				AccountChange transaction = new AccountChange();
				PropertyUtils.copyProperties(transaction, dto);
				tranList.add(transaction);
			}
			
			
			 if (tranList != null && tranList.size()>0){
				 List<List<String>> dataList = new ArrayList<List<String>>(tranDTO.size());	
				 for (AccountChange data:tranList) {
					 List<String> temp = new ArrayList<String>();
						//交易流水号
						temp.add(data.getTranCode());
						//资金类别
						temp.add(data.getFundType());
						//变动类型
						temp.add(data.getChangeType());
						//变动金额
						temp.add(new DecimalFormat("0.00").format(data.getChangeAmount()/100));
						//变动前账户总余额
						temp.add(new DecimalFormat("0.00").format(data.getBeforeChangeAmount()/100));
						//变动前可用余额
						temp.add(new DecimalFormat("0.00").format(data.getBeforeChangeAvailableAmount()/100));
						//变动前冻结金额
						temp.add(new DecimalFormat("0.00").format(data.getBeforeChangeFrozenAmount()/100));
						//资金变更时间
						temp.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(data.getCreateDate()));
						// 【添加数据行】
                        dataList.add(temp);
					}	
				// 【将数据集加入model】
                 excelModel.setDataList(dataList);
				}
			//excel 模板名称
			 	return ExcelUtil.excelExport(excelModel, response);
			}else{
			    return ExcelUtil.excelExport(excelModel, response);
				//excelExport.setCode(ResponseCode.CARD_EXPORT_NULL_DATA);
			}
		} catch (Exception e) {
			excelExport.setCode(ResponseCode.SYSTEM_ERROR);
			e.printStackTrace();
		}
		return excelExport;
	}
}
