package com.dodopal.portal.business.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.YktCardConsumStatisticsDTO;
import com.dodopal.api.product.dto.YktCardConsumTradeDetailDTO;
import com.dodopal.api.product.dto.query.RechargeStatisticsYktQueryDTO;
import com.dodopal.api.product.dto.query.YktCardConsumTradeDetailQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ConsumeOrderStatesEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.portal.business.bean.TransactionDetailsBean;
import com.dodopal.portal.business.bean.YktCardConsumStatisticsBean;
import com.dodopal.portal.business.bean.query.RechargeStatisticsYktQuery;
import com.dodopal.portal.business.service.CardConsumForSupplierService;
import com.dodopal.portal.delegate.CardConsumForSupplierDelegate;
import com.dodopal.portal.delegate.ManagementForSupplierDelegate;

@Service
public class CardConsumForSupplierServiceImpl implements CardConsumForSupplierService{
    private final static Logger log = LoggerFactory.getLogger(CardConsumForSupplierServiceImpl.class);
    @Autowired
    private CardConsumForSupplierDelegate cardConsumForSupplierDelegate;
    @Autowired
    private ManagementForSupplierDelegate managementForSupplierDelegate;

    /**
     * 一卡通消费统计查询
     * @param query
     * @return
     */
    public DodopalResponse<DodopalDataPage<YktCardConsumStatisticsBean>> queryCardConsumForSupplier(
            RechargeStatisticsYktQuery query) {
        DodopalResponse<DodopalDataPage<YktCardConsumStatisticsBean>> response = new DodopalResponse<DodopalDataPage<YktCardConsumStatisticsBean>>();
        RechargeStatisticsYktQueryDTO queryDTO = new RechargeStatisticsYktQueryDTO();
        log.info("CardConsumForSupplierServiceImpl queryCardConsumForSupplier is start query:"+query);
        //pos号
        if(query.getProCode()!=null){
            queryDTO.setProCode(query.getProCode());
        }
        //启用标识
        if(query.getBind()!=null){
            queryDTO.setBind(query.getBind());
        }
        //商户名称
        if(query.getMerName()!=null){
            queryDTO.setMerName(query.getMerName());
        }
        
        //订单状态
        if(query.getStates()!=null){
            queryDTO.setStates(query.getStates());
        }
        //开始日期
        if (query.getStartDate()!=null) {
            queryDTO.setStratDate(query.getStartDate());
        }
        //结束日期
        if (query.getEndDate()!=null) {
            queryDTO.setEndDate(query.getEndDate());
        }
        //城市
        if(query.getCityName() != null){
            queryDTO.setCityName(query.getCityName());
        }
        //城市
        if(query.getYktCode() != null){
            queryDTO.setYktCode(query.getYktCode());
        }
         //分页参数
        if (query.getPage() != null) {
            queryDTO.setPage(query.getPage());;
        }
  
        DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>> dodopalResponse = cardConsumForSupplierDelegate.queryCardConsumForSupplier(queryDTO);
        List<YktCardConsumStatisticsDTO> list = dodopalResponse.getResponseEntity().getRecords();
        List<YktCardConsumStatisticsBean> beanList = new ArrayList<YktCardConsumStatisticsBean>();
        if(dodopalResponse.getResponseEntity()!=null && CollectionUtils.isNotEmpty(list)){
            for(YktCardConsumStatisticsDTO dto : list){
                YktCardConsumStatisticsBean bean = new YktCardConsumStatisticsBean();
                bean.setBind(dto.getBind());
                bean.setProCode(dto.getProCode());
                bean.setMerName(dto.getMerName());
                if(StringUtils.isBlank(dto.getConsumeCount())){
                	 bean.setConsumeCount("0");
                }else{
                	 bean.setConsumeCount(dto.getConsumeCount());
                }
                if(StringUtils.isBlank(dto.getConsumeAmt())){
                	 bean.setConsumeAmt("0.00");
                }else{
                	 bean.setConsumeAmt(String.format("%.2f", Double.valueOf(dto.getConsumeAmt()) / 100));
                }
                beanList.add(bean);
            }
        }
        //后台传入总页数，总条数，当前页
        PageParameter page = DodopalDataPageUtil.convertPageInfo(dodopalResponse.getResponseEntity());
        DodopalDataPage<YktCardConsumStatisticsBean> pages = new DodopalDataPage<YktCardConsumStatisticsBean>(page, beanList);
        response.setCode(dodopalResponse.getCode());
        response.setResponseEntity(pages);
        log.info("CardConsumForSupplierServiceImpl queryCardConsumForSupplier is end code:"+response.getCode());
        return response;
    }


    //导出
    @Override
    public DodopalResponse<String> exportCardConsumForSupp(HttpServletRequest request,HttpServletResponse response,RechargeStatisticsYktQueryDTO queryDTO) {
        DodopalResponse<String> dodopalResponse = new DodopalResponse<String>();
        DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>>  res = cardConsumForSupplierDelegate.exportCardConsumForSupp(queryDTO);
        if(ResponseCode.SUCCESS.equals(res.getCode())){
            Map<String, String> index =new LinkedHashMap<String, String>();
            index.put("proCode", "POS号");
            index.put("bind", "启用标识");
            index.put("consumeCount", " 消费交易笔数");
            index.put("consumeAmt", "消费交易金额(元)");
            index.put("merName", "当前绑定商户");
            List<YktCardConsumStatisticsBean> beanList = null;
            if(res.getResponseEntity() != null && res.getResponseEntity().getRecords() != null && res.getResponseEntity().getRecords().size() > 0){
                List<YktCardConsumStatisticsDTO> list = res.getResponseEntity().getRecords();
                beanList =new ArrayList<YktCardConsumStatisticsBean>(list.size());
                for (YktCardConsumStatisticsDTO cardConsumTradeDetailDTO : list) {
                    YktCardConsumStatisticsBean bean = new YktCardConsumStatisticsBean();
                    if(StringUtils.isBlank(cardConsumTradeDetailDTO.getProCode())){
                        bean.setProCode("");
                    }else{
                        bean.setProCode(cardConsumTradeDetailDTO.getProCode());
                    }
                    if(StringUtils.isBlank(cardConsumTradeDetailDTO.getConsumeCount())){
                   	 bean.setConsumeCount("0");
                    }else{
                   	 bean.setConsumeCount(cardConsumTradeDetailDTO.getConsumeCount());
                    }
                    if(StringUtils.isBlank(cardConsumTradeDetailDTO.getConsumeAmt())){
                    	bean.setConsumeAmt("0.00");
                    }else{
                   	 bean.setConsumeAmt(String.format("%.2f", Double.valueOf(cardConsumTradeDetailDTO.getConsumeAmt()) / 100));
                    }
                    if(StringUtils.isNotBlank(cardConsumTradeDetailDTO.getBind())){
                        bean.setBind(cardConsumTradeDetailDTO.getBind());
                    }
                    
                    if(cardConsumTradeDetailDTO.getMerName()==null){
                        bean.setMerName("");
                    }else{
                        bean.setMerName(cardConsumTradeDetailDTO.getMerName());
                    }
                    beanList.add(bean);
                }
                String code = ExcelUtil.excelExport(request, response, beanList, index, "商户一卡通消费列表");
                dodopalResponse.setCode(code);
            }else{
                beanList = new ArrayList<YktCardConsumStatisticsBean>();
                String code = ExcelUtil.excelExport(request, response, beanList, index, "商户一卡通消费列表");
                dodopalResponse.setCode(code);
            }
        }else{
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }


    /**
     * 一卡通消费业务订单汇总
     * @param query
     * @return
     */
	public DodopalResponse<DodopalDataPage<YktCardConsumStatisticsBean>> findCardConsumCollectByPage(RechargeStatisticsYktQuery query) {
        DodopalResponse<DodopalDataPage<YktCardConsumStatisticsBean>> response = new DodopalResponse<DodopalDataPage<YktCardConsumStatisticsBean>>();
        RechargeStatisticsYktQueryDTO queryDTO = new RechargeStatisticsYktQueryDTO();
        log.info("CardConsumForSupplierServiceImpl findCardConsumCollectByPage is start query:"+query);
        //pos号
        if(query.getProCode()!=null){
            queryDTO.setProCode(query.getProCode());
        }
        //启用标识
        if(query.getBind()!=null){
            queryDTO.setBind(query.getBind());
        }
        
        //订单状态
        if(query.getStates()!=null){
            queryDTO.setStates(query.getStates());
        }
        //商户名称
        if(query.getMerName()!=null){
            queryDTO.setMerName(query.getMerName());
        }
        //开始日期
        if (query.getStartDate()!=null) {
            queryDTO.setStratDate(query.getStartDate());
        }
        //结束日期
        if (query.getEndDate()!=null) {
            queryDTO.setEndDate(query.getEndDate());
        }
        //城市
        if(query.getCityName() != null){
            queryDTO.setCityName(query.getCityName());
        }
        //上级商户号
        if(query.getParentCode() != null){
            queryDTO.setParentCode(query.getParentCode());
        }
         //分页参数
        if (query.getPage() != null) {
            queryDTO.setPage(query.getPage());;
        }
  
        DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>> dodopalResponse = cardConsumForSupplierDelegate.findCardConsumCollectByPage(queryDTO);
        List<YktCardConsumStatisticsDTO> list = dodopalResponse.getResponseEntity().getRecords();
        List<YktCardConsumStatisticsBean> beanList = new ArrayList<YktCardConsumStatisticsBean>();
        if(dodopalResponse.getResponseEntity()!=null && CollectionUtils.isNotEmpty(list)){
            for(YktCardConsumStatisticsDTO dto : list){
                YktCardConsumStatisticsBean bean = new YktCardConsumStatisticsBean();
                bean.setBind(dto.getBind());
                bean.setProCode(dto.getProCode());
                if(dto.getMerName()==null||dto.getMerName()==""){
                	bean.setMerName("");
                }else{
                	bean.setMerName(dto.getMerName());
                }
                bean.setOrderdate(dto.getOrderdate());
                bean.setConsumeCount(dto.getConsumeCount());
                String consumAmt =  String.format("%.2f", Double.valueOf(dto.getConsumeAmt()) / 100);
                bean.setConsumeAmt(consumAmt);
                String consumeDiscountAmt =  String.format("%.2f", (Double.valueOf(dto.getConsumeOriginalAmt())-Double.valueOf(dto.getConsumeAmt())) / 100);
                bean.setConsumeDiscountAmt(consumeDiscountAmt);
                String consumeOriginalAmt = String.format("%.2f", Double.valueOf(dto.getConsumeOriginalAmt()) / 100);
                bean.setConsumeOriginalAmt(consumeOriginalAmt);
                bean.setCityName(dto.getCityName());
                bean.setComments(dto.getComments());
                beanList.add(bean);
            }
        }
        //后台传入总页数，总条数，当前页
        PageParameter page = DodopalDataPageUtil.convertPageInfo(dodopalResponse.getResponseEntity());
        DodopalDataPage<YktCardConsumStatisticsBean> pages = new DodopalDataPage<YktCardConsumStatisticsBean>(page, beanList);
        response.setCode(dodopalResponse.getCode());
        response.setResponseEntity(pages);
        log.info("CardConsumForSupplierServiceImpl findCardConsumCollectByPage is end code:"+response.getCode());
        return response;
    }


      //业务订单汇总一卡通消费导出
	public DodopalResponse<String> exportCardConsumCollect(HttpServletRequest request, HttpServletResponse response,RechargeStatisticsYktQueryDTO queryDTO) {
        DodopalResponse<String> dodopalResponse = new DodopalResponse<String>();
        DodopalResponse<DodopalDataPage<YktCardConsumStatisticsDTO>>  res = cardConsumForSupplierDelegate.findCardConsumCollectByPage(queryDTO);
        if(ResponseCode.SUCCESS.equals(res.getCode())){
            Map<String, String> index =new LinkedHashMap<String, String>();
            //index.put("orderdate", "交易日期");
            index.put("merName", "商户名称");
//            index.put("cityName", "城市");
//            index.put("proCode", "POS号");
            index.put("consumeCount", " 消费交易笔数");
            index.put("consumeAmt", "消费实付金额");
            index.put("consumeOriginalAmt", "消费应付金额");
            index.put("consumeDiscountAmt", "折扣金额");
//            index.put("comments", "POS备注");
            List<YktCardConsumStatisticsBean> beanList = null;
            if(res.getResponseEntity() != null && res.getResponseEntity().getRecords() != null && res.getResponseEntity().getRecords().size() > 0){
                List<YktCardConsumStatisticsDTO> list = res.getResponseEntity().getRecords();
                beanList =new ArrayList<YktCardConsumStatisticsBean>(list.size());
                for (YktCardConsumStatisticsDTO cardConsumTradeDetailDTO : list) {
                    YktCardConsumStatisticsBean bean = new YktCardConsumStatisticsBean();
                    if(StringUtils.isBlank(cardConsumTradeDetailDTO.getOrderdate())){
                        bean.setOrderdate("");
                    }else{
                        bean.setOrderdate(cardConsumTradeDetailDTO.getOrderdate());
                    }
                    if(cardConsumTradeDetailDTO.getMerName()==null){
                        bean.setMerName("");
                    }else{
                        bean.setMerName(cardConsumTradeDetailDTO.getMerName());
                    }
                    if(cardConsumTradeDetailDTO.getCityName()==null){
                        bean.setCityName("");
                    }else{
                        bean.setCityName(cardConsumTradeDetailDTO.getCityName());
                    }
                    if(StringUtils.isBlank(cardConsumTradeDetailDTO.getProCode())){
                        bean.setProCode("");
                    }else{
                        bean.setProCode(cardConsumTradeDetailDTO.getProCode());
                    }
                    String consumeCount = cardConsumTradeDetailDTO.getConsumeCount();
                    if(StringUtils.isBlank(consumeCount)){
                        bean.setConsumeCount("");
                    }else{
                        bean.setConsumeCount(consumeCount);
                    }
                    if(StringUtils.isNotBlank(cardConsumTradeDetailDTO.getConsumeAmt())){
                        String consumAmt =  String.format("%.2f", Double.valueOf(cardConsumTradeDetailDTO.getConsumeAmt()) / 100);
                        bean.setConsumeAmt(consumAmt);
                    }
                    
                    if(StringUtils.isNotBlank(cardConsumTradeDetailDTO.getConsumeOriginalAmt())){
                        String consumeDiscountAmt =  String.format("%.2f", (Double.valueOf(cardConsumTradeDetailDTO.getConsumeOriginalAmt())-Double.valueOf(cardConsumTradeDetailDTO.getConsumeAmt())) / 100);
                        bean.setConsumeDiscountAmt(consumeDiscountAmt);
                    }
                    
                    if(StringUtils.isNotBlank(cardConsumTradeDetailDTO.getConsumeOriginalAmt())){
                        String consumeOriginalAmt =  String.format("%.2f",Double.valueOf(cardConsumTradeDetailDTO.getConsumeOriginalAmt()) / 100);
                        bean.setConsumeOriginalAmt(consumeOriginalAmt);
                    }
                    
                    String comments = cardConsumTradeDetailDTO.getComments();
                    if(StringUtils.isBlank(comments)){
                        bean.setComments("");
                    }else{
                        bean.setComments(comments);
                    }
                    beanList.add(bean);
                }
                String code = ExcelUtil.excelExport(request, response, beanList, index, "业务订单汇总一卡通消费记录");
                dodopalResponse.setCode(code);
            }else{
                beanList = new ArrayList<YktCardConsumStatisticsBean>();
                String code = ExcelUtil.excelExport(request, response, beanList, index, "业务订单汇总一卡通消费记录");
                dodopalResponse.setCode(code);
            }
        }else{
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }
   //一卡通消费详情导出
	@Override
	public DodopalResponse<String> exportCardConsumDetailsForSupp(HttpServletRequest request,HttpServletResponse response,
			YktCardConsumTradeDetailQueryDTO queryDTO) {
		DodopalResponse<String> dodopalResponse = new DodopalResponse<String>();
		queryDTO.setPage(new PageParameter(1,5000));
		DodopalResponse<DodopalDataPage<YktCardConsumTradeDetailDTO>>  res = managementForSupplierDelegate.queryCardConsumDetails(queryDTO);
		if(ResponseCode.SUCCESS.equals(res.getCode())){
			String sheetName = "";
			Map<String, String> index =new LinkedHashMap<String, String>();
			sheetName = "一卡通消费交易记录-"+queryDTO.getProCode();
			index.put("orderNo", "订单编号");
			index.put("merName", "商户名称");
			index.put("txnAmt", "消费金额(元)");
			index.put("orderCardno", "卡号");
			index.put("blackAmt", "	消费后卡内余额(元)");
			index.put("proOrderState", "订单状态");
			index.put("proOrderDate", "消费时间");
			List<TransactionDetailsBean> beanList = null;
			if(res.getResponseEntity() != null && res.getResponseEntity().getRecords() != null && res.getResponseEntity().getRecords().size() > 0){
				List<YktCardConsumTradeDetailDTO> list = res.getResponseEntity().getRecords();
				beanList =new ArrayList<TransactionDetailsBean>(list.size());
				for (YktCardConsumTradeDetailDTO cardConsumTradeDetailDTO : list) {
					TransactionDetailsBean bean = new TransactionDetailsBean();
					if(StringUtils.isBlank(cardConsumTradeDetailDTO.getOrderNo())){
						bean.setOrderNo("");
					}else{
						bean.setOrderNo(cardConsumTradeDetailDTO.getOrderNo());
					}
					
					bean.setProOrderDate(DateUtils.dateToString(cardConsumTradeDetailDTO.getProOrderDate(), DateUtils.DATE_FULL_STR));
					if(StringUtils.isBlank(cardConsumTradeDetailDTO.getConsumeAmt())){
						bean.setTxnAmt("");
					}else{
						bean.setTxnAmt(String.format("%.2f", Double.valueOf(cardConsumTradeDetailDTO.getConsumeAmt()) / 100));
					}
					if(StringUtils.isBlank(cardConsumTradeDetailDTO.getCardNo())){
						bean.setOrderCardno("");
					}else{
						bean.setOrderCardno(cardConsumTradeDetailDTO.getCardNo());
					}
					
					if(cardConsumTradeDetailDTO.getBlackAmt()==null){
						bean.setBlackAmt("");
					}else{
						bean.setBlackAmt(String.format("%.2f",Double.valueOf(cardConsumTradeDetailDTO.getBlackAmt())/100));
					}
					if(StringUtils.isBlank(cardConsumTradeDetailDTO.getMerName())){
						bean.setMerName("");
					}else{
						bean.setMerName(cardConsumTradeDetailDTO.getMerName());
					}
					bean.setProOrderState(ConsumeOrderStatesEnum.getConsumeOrderStatesEnumByCode(cardConsumTradeDetailDTO.getOrderStates()) == null? "":ConsumeOrderStatesEnum.getConsumeOrderStatesEnumByCode(cardConsumTradeDetailDTO.getOrderStates()).getName());
					beanList.add(bean);
				}
				String code = ExcelUtil.excelExport(request, response, beanList, index, sheetName);
				dodopalResponse.setCode(code);
			}else{
				beanList = new ArrayList<TransactionDetailsBean>();
				String code = ExcelUtil.excelExport(request, response, beanList, index, sheetName);
				dodopalResponse.setCode(code);
			}
		}else{
			dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return dodopalResponse;
	}
}
