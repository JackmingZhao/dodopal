package com.dodopal.portal.business.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.CcOrderFormDTO;
import com.dodopal.api.product.dto.CzOrderByPosCountDTO;
import com.dodopal.api.product.dto.CzOrderByPosDTO;
import com.dodopal.api.product.dto.query.QueryCcOrderFormDTO;
import com.dodopal.api.product.dto.query.QueryCzOrderByPosCountDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.portal.business.bean.CcOrderFormBean;
import com.dodopal.portal.business.bean.CzOrderByPosBean;
import com.dodopal.portal.business.bean.CzOrderByPosCountBean;
import com.dodopal.portal.business.bean.query.QueryCcOrderFormBean;
import com.dodopal.portal.business.bean.query.QueryCzOrderByPosCountBean;
import com.dodopal.portal.business.service.CcOrderService;
import com.dodopal.portal.delegate.CcOrderDelegate;

@Service
public class CcOrderServiceImpl implements CcOrderService {
    private static Logger log = Logger.getLogger(CcOrderServiceImpl.class);
    @Autowired
    CcOrderDelegate ccOrderDelegate;

    @Override
    public DodopalResponse<DodopalDataPage<CcOrderFormBean>> findCcOrderByPage(QueryCcOrderFormBean queryCcOrderFormBean) {
        DodopalResponse<DodopalDataPage<CcOrderFormBean>> response = new DodopalResponse<DodopalDataPage<CcOrderFormBean>>();
        QueryCcOrderFormDTO queryCcDto = new QueryCcOrderFormDTO();
        try {
            //商户号
            if (StringUtils.isNotBlank(queryCcOrderFormBean.getMchnitid())) {
                queryCcDto.setMchnitid(queryCcOrderFormBean.getMchnitid());
            }
            //商户订单号
            if (StringUtils.isNotBlank(queryCcOrderFormBean.getBankorderid())) {
                queryCcDto.setBankorderid(queryCcOrderFormBean.getBankorderid());
            }
//            //查询起始时间
//            if (StringUtils.isNotBlank(queryCcOrderFormBean.getStartdate())) {
//                queryCcDto.setStartdate(queryCcOrderFormBean.getStartdate());
//            }
//            //查询结束时间
//            if (StringUtils.isNotBlank(queryCcOrderFormBean.getEnddate())) {
//                queryCcDto.setEnddate(queryCcOrderFormBean.getEnddate());
//            }
//            
            //查询起始时间
            if (StringUtils.isNotBlank(queryCcOrderFormBean.getStartdate())) {
                queryCcDto.setStartdate(new SimpleDateFormat("yyyyMMdd").format(DateFormat.getDateInstance().parse(queryCcOrderFormBean.getStartdate())));
            }
            //查询结束时间
            if (StringUtils.isNotBlank(queryCcOrderFormBean.getEnddate())) {
                queryCcDto.setEnddate(new SimpleDateFormat("yyyyMMdd").format(DateFormat.getDateInstance().parse(queryCcOrderFormBean.getEnddate())));
            }
            //Pos编号
            if (StringUtils.isNotBlank(queryCcOrderFormBean.getPosid())) {
                queryCcDto.setPosid(queryCcOrderFormBean.getPosid());
            }
            //卡号
            if (StringUtils.isNotBlank(queryCcOrderFormBean.getCardno())) {
                queryCcDto.setCardno(queryCcOrderFormBean.getCardno());
            }
            //用户名称
            if (StringUtils.isNotBlank(queryCcOrderFormBean.getUsername())) {
                queryCcDto.setUsername(queryCcOrderFormBean.getUsername());
            }

            //订单状态
            if (StringUtils.isNotBlank(queryCcOrderFormBean.getStatus())) {
                if (queryCcOrderFormBean.getStatus().equals("1000000024")) {
                    queryCcDto.setStatus("a.orderstates=1000000024 or a.orderstates=1000000022 or  a.orderstates=1000000002");
                } else if (queryCcOrderFormBean.getStatus().equals("1000000006")) {
                    queryCcDto.setStatus("a.orderstates=1000000006 or a.orderstates=1000000007 or a.orderstates=1000000023");
                } else if (queryCcOrderFormBean.getStatus().equals("1000000005")) {
                    queryCcDto.setStatus("a.orderstates=1000000005 or a.orderstates=1000000025 or a.orderstates=1000000030 or a.orderstates=1000000004");
                } else if (queryCcOrderFormBean.getStatus().equals("1000000001")) {
                    queryCcDto.setStatus("a.orderstates=1000000001");
                } else if (queryCcOrderFormBean.getStatus().equals("")) {
                    queryCcDto.setStatus("");
                }
            }
            //分页参数
            if (queryCcOrderFormBean.getPage() != null) {
                queryCcDto.setPage(queryCcOrderFormBean.getPage());
                ;
            }
            // 查询列表信息
            DodopalResponse<DodopalDataPage<CcOrderFormDTO>> ccOrdeDtoList = ccOrderDelegate.findCcOrderListByPage(queryCcDto);
            List<CcOrderFormBean> ccOrderFormBeanList = new ArrayList<CcOrderFormBean>();
            if (ResponseCode.SUCCESS.equals(ccOrdeDtoList.getCode())) {
                if (ccOrdeDtoList.getResponseEntity() != null && CollectionUtils.isNotEmpty(ccOrdeDtoList.getResponseEntity().getRecords())) {
                    for (CcOrderFormDTO ccOrderFormDTO : ccOrdeDtoList.getResponseEntity().getRecords()) {
                        CcOrderFormBean ccorderBean = new CcOrderFormBean();
                        ccorderBean.setMchnitname(ccOrderFormDTO.getMchnitname());
                        ccorderBean.setUserid(ccOrderFormDTO.getUserid());
                        ccorderBean.setBankorderid(ccOrderFormDTO.getBankorderid());
                        ccorderBean.setAmt(ccOrderFormDTO.getAmt());
                        ccorderBean.setFlamt(ccOrderFormDTO.getFlamt());
                        ccorderBean.setPaidamt(ccOrderFormDTO.getPaidamt());
                        ccorderBean.setPosid(ccOrderFormDTO.getPosid());
                        ccorderBean.setCardno(ccOrderFormDTO.getCardno());
                        ccorderBean.setSenddate(ccOrderFormDTO.getSenddate());
                        ccorderBean.setSendtime(ccOrderFormDTO.getSendtime());
                        ccorderBean.setStatus(ccOrderFormDTO.getStatus());
                        ccOrderFormBeanList.add(ccorderBean);
                    }

                }
                log.info(" return code:" + ccOrdeDtoList.getCode());
                //后台传入总页数，总条数，当前页
                PageParameter page = DodopalDataPageUtil.convertPageInfo(ccOrdeDtoList.getResponseEntity());
                DodopalDataPage<CcOrderFormBean> pages = new DodopalDataPage<CcOrderFormBean>(page, ccOrderFormBeanList);
                response.setCode(ccOrdeDtoList.getCode());
                response.setResponseEntity(pages);
            } else {
                response.setCode(ccOrdeDtoList.getCode());
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public DodopalResponse<DodopalDataPage<CzOrderByPosBean>> findCzOrderByPosByPage(QueryCzOrderByPosCountBean queryCzOrderByPosCountBean) {
        DodopalResponse<DodopalDataPage<CzOrderByPosBean>> response = new DodopalResponse<DodopalDataPage<CzOrderByPosBean>>();
        QueryCzOrderByPosCountDTO queryCzOrderByPosCountDto = new QueryCzOrderByPosCountDTO();
        try {
            //商户号
            if (StringUtils.isNotBlank(queryCzOrderByPosCountBean.getMchnitid())) {
                queryCzOrderByPosCountDto.setMchnitid(queryCzOrderByPosCountBean.getMchnitid());
            }
            //查询起始时间
            if (StringUtils.isNotBlank(queryCzOrderByPosCountBean.getStartdate())) {
                queryCzOrderByPosCountDto.setStartdate(new SimpleDateFormat("yyyyMMdd").format(DateFormat.getDateInstance().parse(queryCzOrderByPosCountBean.getStartdate())));
            }
            //查询结束时间
            if (StringUtils.isNotBlank(queryCzOrderByPosCountBean.getEnddate())) {
                queryCzOrderByPosCountDto.setEnddate(new SimpleDateFormat("yyyyMMdd").format(DateFormat.getDateInstance().parse(queryCzOrderByPosCountBean.getEnddate())));
            }
            //分页参数
            if (queryCzOrderByPosCountBean.getPage() != null) {
                queryCzOrderByPosCountDto.setPage(queryCzOrderByPosCountBean.getPage());
                ;
            }
            // 查询列表信息
            DodopalResponse<DodopalDataPage<CzOrderByPosDTO>> czOrdeDtoList = ccOrderDelegate.findCzOrderByPosByPage(queryCzOrderByPosCountDto);
            List<CzOrderByPosBean> ccOrderFormBeanList = new ArrayList<CzOrderByPosBean>();
            if (ResponseCode.SUCCESS.equals(czOrdeDtoList.getCode())) {
                if (czOrdeDtoList.getResponseEntity() != null && CollectionUtils.isNotEmpty(czOrdeDtoList.getResponseEntity().getRecords())) {
                    for (CzOrderByPosDTO czOrderCountDTO : czOrdeDtoList.getResponseEntity().getRecords()) {
                        CzOrderByPosBean czOrderByPosCountBean = new CzOrderByPosBean();
                        czOrderByPosCountBean.setPosid(czOrderCountDTO.getPosid());
                        czOrderByPosCountBean.setUsername(czOrderCountDTO.getUsername());
                        czOrderByPosCountBean.setTradeCount(czOrderCountDTO.getTradeCount());
                        czOrderByPosCountBean.setTradeMoney(czOrderCountDTO.getTradeMoney());
                        czOrderByPosCountBean.setTradeSucceedCount(czOrderCountDTO.getTradeSucceedCount());
                        czOrderByPosCountBean.setTradeSucceedMoney(czOrderCountDTO.getTradeSucceedMoney());
                        czOrderByPosCountBean.setTradeErrorCount(czOrderCountDTO.getTradeErrorCount());
                        czOrderByPosCountBean.setTradeErrorMoney(czOrderCountDTO.getTradeErrorMoney());
                        czOrderByPosCountBean.setDoubtfulTradeCount(czOrderCountDTO.getDoubtfulTradeCount());
                        czOrderByPosCountBean.setDoubtfulTradeMoney(czOrderCountDTO.getDoubtfulTradeMoney());
                        ccOrderFormBeanList.add(czOrderByPosCountBean);
                    }

                }
                log.info(" return code:" + czOrdeDtoList.getCode());
                //后台传入总页数，总条数，当前页
                PageParameter page = DodopalDataPageUtil.convertPageInfo(czOrdeDtoList.getResponseEntity());
                DodopalDataPage<CzOrderByPosBean> pages = new DodopalDataPage<CzOrderByPosBean>(page, ccOrderFormBeanList);
                response.setCode(czOrdeDtoList.getCode());
                response.setResponseEntity(pages);
            } else {
                response.setCode(czOrdeDtoList.getCode());
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public DodopalResponse<CzOrderByPosCountBean> findCzOrderByPosCount(QueryCzOrderByPosCountBean queryCzOrderByPosCountBean) {
        DodopalResponse<CzOrderByPosCountBean> response = new DodopalResponse<CzOrderByPosCountBean>();
        QueryCzOrderByPosCountDTO queryCzOrderByPosCountDto = new QueryCzOrderByPosCountDTO();
        try {
            //商户号
            if (StringUtils.isNotBlank(queryCzOrderByPosCountBean.getMchnitid())) {
                queryCzOrderByPosCountDto.setMchnitid(queryCzOrderByPosCountBean.getMchnitid());
            }
            //查询起始时间
            if (StringUtils.isNotBlank(queryCzOrderByPosCountBean.getStartdate())) {
                queryCzOrderByPosCountDto.setStartdate(new SimpleDateFormat("yyyyMMdd").format(DateFormat.getDateInstance().parse(queryCzOrderByPosCountBean.getStartdate())));
            }
            //查询结束时间
            if (StringUtils.isNotBlank(queryCzOrderByPosCountBean.getEnddate())) {
                queryCzOrderByPosCountDto.setEnddate(new SimpleDateFormat("yyyyMMdd").format(DateFormat.getDateInstance().parse(queryCzOrderByPosCountBean.getEnddate())));
            }
            //分页参数
            if (queryCzOrderByPosCountBean.getPage() != null) {
                queryCzOrderByPosCountDto.setPage(queryCzOrderByPosCountBean.getPage());
                ;
            }
            // 查询列表信息
            DodopalResponse<CzOrderByPosCountDTO> czOrdeDto = ccOrderDelegate.findCzOrderByPosCount(queryCzOrderByPosCountDto);
            CzOrderByPosCountDTO czOrderCountDTO = new CzOrderByPosCountDTO();
            czOrderCountDTO = czOrdeDto.getResponseEntity();
            CzOrderByPosCountBean czOrderByPosCountBean = new CzOrderByPosCountBean();
            if (ResponseCode.SUCCESS.equals(czOrdeDto.getCode())) {
                if (czOrdeDto.getResponseEntity() != null) {
                    czOrderByPosCountBean.setTradeCountSum(czOrderCountDTO.getTradeCountSum());
                    czOrderByPosCountBean.setTradeMoneySum(czOrderCountDTO.getTradeMoneySum());
                    czOrderByPosCountBean.setTradeErrorMoneySum(czOrderCountDTO.getTradeErrorMoneySum());
                    czOrderByPosCountBean.setTradeErrorCountSum(czOrderCountDTO.getTradeErrorCountSum());
                    czOrderByPosCountBean.setDoubtfulTradeCountSum(czOrderCountDTO.getDoubtfulTradeCountSum());
                    czOrderByPosCountBean.setDoubtfulTradeMoneySum(czOrderCountDTO.getDoubtfulTradeMoneySum());
                    czOrderByPosCountBean.setTradeSucceedCountSum(czOrderCountDTO.getTradeSucceedCountSum());
                    czOrderByPosCountBean.setTradeSucceedMoneySum(czOrderCountDTO.getTradeSucceedMoneySum());
                }
                log.info(" return code:" + czOrdeDto.getCode());
                //后台传入总页数，总条数，当前页
                response.setCode(czOrdeDto.getCode());
                response.setResponseEntity(czOrderByPosCountBean);
            } else {
                response.setCode(czOrdeDto.getCode());
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public DodopalResponse<String> findCzOrderByPosCountExcel(HttpServletRequest request, HttpServletResponse response, QueryCzOrderByPosCountBean queryCzOrderByPosCountBean) {
        QueryCzOrderByPosCountDTO queryCzOrderByPosCountDto = new QueryCzOrderByPosCountDTO();
        DodopalResponse<String> excelExport = new DodopalResponse<String>();
        //商户号
        if (StringUtils.isNotBlank(queryCzOrderByPosCountBean.getMchnitid())) {
            queryCzOrderByPosCountDto.setMchnitid(queryCzOrderByPosCountBean.getMchnitid());
        }
        //查询起始时间
        if (StringUtils.isNotBlank(queryCzOrderByPosCountBean.getStartdate())) {
            queryCzOrderByPosCountDto.setStartdate(queryCzOrderByPosCountBean.getStartdate());
        }
        //查询结束时间
        if (StringUtils.isNotBlank(queryCzOrderByPosCountBean.getEnddate())) {
            queryCzOrderByPosCountDto.setEnddate(queryCzOrderByPosCountBean.getEnddate());
        }
        try {
            // 查询列表信息
            DodopalResponse<List<CzOrderByPosDTO>> czOrdeDtoList = ccOrderDelegate.findCzOrderByPosExcel(queryCzOrderByPosCountDto);
            List<CzOrderByPosBean> ccOrderFormBeanList = new ArrayList<CzOrderByPosBean>();
            if (ResponseCode.SUCCESS.equals(czOrdeDtoList.getCode())) {
                if (czOrdeDtoList.getResponseEntity() != null && CollectionUtils.isNotEmpty(czOrdeDtoList.getResponseEntity())) {
                    for (CzOrderByPosDTO czOrderCountDTO : czOrdeDtoList.getResponseEntity()) {
                        CzOrderByPosBean czOrderByPosCountBean = new CzOrderByPosBean();
                        czOrderByPosCountBean.setPosid(czOrderCountDTO.getPosid());
                        czOrderByPosCountBean.setUsername(czOrderCountDTO.getUsername());
                        czOrderByPosCountBean.setTradeCount(czOrderCountDTO.getTradeCount());
                        czOrderByPosCountBean.setTradeMoney(czOrderCountDTO.getTradeErrorMoney());
                        czOrderByPosCountBean.setTradeSucceedCount(czOrderCountDTO.getTradeSucceedCount());
                        czOrderByPosCountBean.setTradeSucceedMoney(czOrderCountDTO.getTradeSucceedMoney());
                        czOrderByPosCountBean.setTradeErrorCount(czOrderCountDTO.getTradeErrorCount());
                        czOrderByPosCountBean.setTradeErrorMoney(czOrderCountDTO.getTradeErrorMoney());
                        czOrderByPosCountBean.setDoubtfulTradeCount(czOrderCountDTO.getDoubtfulTradeCount());
                        czOrderByPosCountBean.setDoubtfulTradeMoney(czOrderCountDTO.getDoubtfulTradeMoney());
                        ccOrderFormBeanList.add(czOrderByPosCountBean);
                    }
                }
                Map<String, String> index = new LinkedHashMap<String, String>();
                index.put("posid", "POS号");
                index.put("username", "用户名称");
                index.put("tradeCount", "交易总笔数");
                index.put("tradeMoney", "交易总金额");
                index.put("tradeSucceedCount", "交易成功笔数");
                index.put("tradeSucceedMoney", "交易成功金额");
                index.put("tradeErrorCount", "交易失败笔数");
                index.put("tradeErrorMoney", "交易失败金额");
                index.put("doubtfulTradeCount", "可疑交易笔数");
                index.put("doubtfulTradeMoney", "可疑交易金额");
                String code = ExcelUtil.excelExport(request, response, ccOrderFormBeanList, index, "手持POS机消费统计");
                excelExport.setCode(code);
            }
        }
        catch (Exception e) {
            excelExport.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        return excelExport;
    }

    @Override
    public DodopalResponse<String> findCcOrderExcel(HttpServletRequest request, HttpServletResponse response, QueryCcOrderFormBean queryCcOrderFormBean) {
        QueryCcOrderFormDTO queryCcDto = new QueryCcOrderFormDTO();
        DodopalResponse<String> excelExport = new DodopalResponse<String>();
        try {
            //商户号
            if (StringUtils.isNotBlank(queryCcOrderFormBean.getMchnitid())) {
                queryCcDto.setMchnitid(queryCcOrderFormBean.getMchnitid());
            }
            //商户订单号
            if (StringUtils.isNotBlank(queryCcOrderFormBean.getBankorderid())) {
                queryCcDto.setBankorderid(queryCcOrderFormBean.getBankorderid());
            }
            //查询起始时间
            if (StringUtils.isNotBlank(queryCcOrderFormBean.getStartdate())) {
                queryCcDto.setStartdate(new SimpleDateFormat("yyyyMMdd").format(DateFormat.getDateInstance().parse(queryCcOrderFormBean.getStartdate())));
            }
            //查询结束时间
            if (StringUtils.isNotBlank(queryCcOrderFormBean.getEnddate())) {
                queryCcDto.setEnddate(new SimpleDateFormat("yyyyMMdd").format(DateFormat.getDateInstance().parse(queryCcOrderFormBean.getEnddate())));
            }
            //Pos编号
            if (StringUtils.isNotBlank(queryCcOrderFormBean.getPosid())) {
                queryCcDto.setPosid(queryCcOrderFormBean.getPosid());
            }
            //卡号
            if (StringUtils.isNotBlank(queryCcOrderFormBean.getCardno())) {
                queryCcDto.setCardno(queryCcOrderFormBean.getCardno());
            }
            //用户名称
            if (StringUtils.isNotBlank(queryCcOrderFormBean.getUsername())) {
                queryCcDto.setUsername(queryCcOrderFormBean.getUsername());
            }

            //订单状态
            if (StringUtils.isNotBlank(queryCcOrderFormBean.getStatus())) {
                if (queryCcOrderFormBean.getStatus().equals("1000000024")) {
                    queryCcDto.setStatus("a.orderstates=1000000024 or a.orderstates=1000000022 or  a.orderstates=1000000002");
                } else if (queryCcOrderFormBean.getStatus().equals("1000000006")) {
                    queryCcDto.setStatus("a.orderstates=1000000006 or a.orderstates=1000000007 or a.orderstates=1000000023");
                } else if (queryCcOrderFormBean.getStatus().equals("1000000005")) {
                    queryCcDto.setStatus("a.orderstates=1000000005 or a.orderstates=1000000025 or a.orderstates=1000000030 or a.orderstates=1000000004");
                } else if (queryCcOrderFormBean.getStatus().equals("1000000001")) {
                    queryCcDto.setStatus("a.orderstates=1000000001");
                } else if (queryCcOrderFormBean.getStatus().equals("")) {
                    queryCcDto.setStatus("");
                }
            }
            // 查询列表信息
            DodopalResponse<List<CcOrderFormDTO>> ccOrdeDtoList = ccOrderDelegate.findCcOrderListExcel(queryCcDto);
            List<CcOrderFormBean> ccOrderFormBeanList = new ArrayList<CcOrderFormBean>();
            if (ResponseCode.SUCCESS.equals(ccOrdeDtoList.getCode())) {
                if (ccOrdeDtoList.getResponseEntity() != null && CollectionUtils.isNotEmpty(ccOrdeDtoList.getResponseEntity())) {
                    for (CcOrderFormDTO ccOrderFormDTO : ccOrdeDtoList.getResponseEntity()) {
                        CcOrderFormBean ccorderBean = new CcOrderFormBean();
                        ccorderBean.setMchnitname(ccOrderFormDTO.getMchnitname());
                        ccorderBean.setUserid(ccOrderFormDTO.getUserid());
                        ccorderBean.setBankorderid(ccOrderFormDTO.getBankorderid());
                        ccorderBean.setAmt(ccOrderFormDTO.getAmt());
                        ccorderBean.setFlamt(ccOrderFormDTO.getFlamt());
                        ccorderBean.setPaidamt(ccOrderFormDTO.getPaidamt());
                        ccorderBean.setPosid(ccOrderFormDTO.getPosid());
                        ccorderBean.setCardno(ccOrderFormDTO.getCardno());
                        ccorderBean.setStatus(ccOrderFormDTO.getStatus());
                        ccorderBean.setOrdertime1(ccOrderFormDTO.getSenddate()+" "+ccOrderFormDTO.getSendtime());
                        ccOrderFormBeanList.add(ccorderBean);
                    }

                }
                Map<String, String> index = new LinkedHashMap<String, String>();
                index.put("bankorderid", "订单号");
                index.put("mchnitname", "商户名称");
                index.put("userid", "用户名称");
                index.put("cardno", "卡号");
                index.put("posid", "POS号");
                index.put("amt", "交易金额");
                index.put("paidamt", "实收金额");
                index.put("flamt", "返利金额");
                index.put("ordertime1", "交易时间");
                index.put("status", "订单状态");
                String code = ExcelUtil.excelExport(request, response, ccOrderFormBeanList, index, "手持POS机充值查询");
                excelExport.setCode(code);
            }
        }
        catch (Exception e) {
            excelExport.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        return excelExport;
    }

}
