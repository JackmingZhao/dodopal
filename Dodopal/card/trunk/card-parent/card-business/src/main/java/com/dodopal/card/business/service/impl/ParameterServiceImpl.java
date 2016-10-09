package com.dodopal.card.business.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.card.business.dao.ParameterMapper;
import com.dodopal.card.business.model.query.ParameterQuery;
import com.dodopal.card.business.service.ParameterService;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.util.DateUtils;
import com.dodopal.api.card.dto.*;

/**
 * @author Dingkuiyuan@dodopal.com
 * @date 创建时间：2016年3月18日 下午1:33:36
 * @version 1.0 参数下载
 */
@Service
public class ParameterServiceImpl implements ParameterService {

    @Autowired
    ParameterMapper parameterMapper;

    @Transactional(readOnly = true)
    public DodopalDataPage<BlankListParameter> findBlankListParameterByPage(ParameterQuery query) {
        List<BlankListParameter> result = parameterMapper.findBlankListParameterByPage(query);
        DodopalDataPage<BlankListParameter> pages = new DodopalDataPage<BlankListParameter>(query.getPage(), result);
        return pages;
    }

    @Transactional(readOnly = true)
    public DodopalDataPage<ConsumeCardTypeParameter> findConsumeCardTypeParameterByPage(ParameterQuery query) {
        List<ConsumeCardTypeParameter> result = parameterMapper.findConsumeCardTypeParameterByPage(query);
        DodopalDataPage<ConsumeCardTypeParameter> pages = new DodopalDataPage<ConsumeCardTypeParameter>(query.getPage(), result);
        return pages;
    }

    @Transactional(readOnly = true)
    public DodopalDataPage<TerminalParameter> findTerminalParameterByPage(ParameterQuery query) {
        List<TerminalParameter> result = parameterMapper.findTerminalParameterByPage(query);
        DodopalDataPage<TerminalParameter> pages = new DodopalDataPage<TerminalParameter>(query.getPage(), result);
        return pages;
    }

    @Transactional(readOnly = true)
    public DodopalDataPage<AreaBlankListParameter> findAreaBlankListParameterByPage(ParameterQuery query) {
        List<AreaBlankListParameter> result = parameterMapper.findAreaBlankListParameterByPage(query);
        DodopalDataPage<AreaBlankListParameter> pages = new DodopalDataPage<AreaBlankListParameter>(query.getPage(), result);
        return pages;
    }

    @Transactional(readOnly = true)
    public DodopalDataPage<IncrementBlankListParameter> findIncrementBlankListParameterByPage(ParameterQuery query) {
        List<IncrementBlankListParameter> result = parameterMapper.findIncrementBlankListParameterByPage(query);
        DodopalDataPage<IncrementBlankListParameter> pages = new DodopalDataPage<IncrementBlankListParameter>(query.getPage(), result);
        return pages;
    }

    @Transactional(readOnly = true)
    public DodopalDataPage<TerminalMenuParameter> findTerminalMenuParameterByPage(ParameterQuery query) {
        List<TerminalMenuParameter> result = parameterMapper.findTerminalMenuParameterByPage(query);
        DodopalDataPage<TerminalMenuParameter> pages = new DodopalDataPage<TerminalMenuParameter>(query.getPage(), result);
        return pages;
    }

    @Transactional(readOnly = true)
    public DodopalDataPage<GrayListParameter> findGrayListParameterByPage(ParameterQuery query) {
        List<GrayListParameter> result = parameterMapper.findGrayListParameterByPage(query);
        DodopalDataPage<GrayListParameter> pages = new DodopalDataPage<GrayListParameter>(query.getPage(), result);
        return pages;
    }

    @Transactional(readOnly = true)
    public DodopalDataPage<ConsumeDiscountParameter> findConsumeDiscountParameterByPage(ParameterQuery query) {
        query.getPage().setRows(1);
        query.getPage().setTotalPages(1);
        List<ConsumeDiscountParameter> list = new ArrayList<ConsumeDiscountParameter>();
        ConsumeDiscountParameter consumeDiscountParameter = new ConsumeDiscountParameter();
        //2016年3月25日16:37:36 先补8个0
        consumeDiscountParameter.setTrandiscount("0000");
        consumeDiscountParameter.setSettdiscount("0000");
        DodopalDataPage<ConsumeDiscountParameter> result = new DodopalDataPage<ConsumeDiscountParameter>(query.getPage(), list);
        return result;
        //        List<Map<String, String>> result = parameterMapper.findConsumeDiscountParameterByPage(query);
        //        DodopalDataPage<Map<String, String>> pages = new DodopalDataPage<Map<String, String>>(query.getPage(), result);
        //        return pages; 
    }

    @Transactional(readOnly = true)
    public DodopalDataPage<SubPeriodDiscountParameter> findSubPeriodDiscountParameterByPage(ParameterQuery query) {
        query.setToday(new Date());
        Calendar cal = Calendar.getInstance();
        cal.setTime(query.getToday());
        int w = cal.get(Calendar.DAY_OF_WEEK);
        query.setTodayWeek(String.valueOf(w - 1));

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(query.getToday());
        calendar.add(Calendar.DATE, 1);
        query.setTomorrow(calendar.getTime());
        cal.setTime(query.getToday());
        w = cal.get(Calendar.DAY_OF_WEEK);
        query.setTomorrowWeek(String.valueOf(w - 1));

        List<SubPeriodDiscountParameter> result = parameterMapper.findSubPeriodDiscountParameterByPage(query);

        DodopalDataPage<SubPeriodDiscountParameter> pages = new DodopalDataPage<SubPeriodDiscountParameter>(query.getPage(), result);
        return pages;
    }

    @Override
    @Transactional
    public int updateDownloadFlag(String pno, String oldFlag, String posId) {
        Map<String, String> queryParam = new HashMap<String, String>();
        int index = Integer.valueOf(pno);
        StringBuilder sb = new StringBuilder(oldFlag);
        if (index <= oldFlag.length()) {//不能写死为常量
            //小于8序列为通卡的下载标志位
            //更新为已下载0
            sb.replace(index - 1, index, "0");
        }
        queryParam.put("posId", posId);
        queryParam.put("downloadFlag", sb.toString());
        return parameterMapper.updateDownloadFlag(queryParam);
    }

    @Override
    public String findSubPeriodDiscountParameterCount(ParameterQuery query) {
        query.setToday(new Date());
        Calendar cal = Calendar.getInstance();
        cal.setTime(query.getToday());
        int w = cal.get(Calendar.DAY_OF_WEEK);
        if((w-1)==0)
            query.setTodayWeek("7");
        else
            query.setTodayWeek(String.valueOf(w - 1));
        
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(query.getToday());
        calendar.add(Calendar.DATE, 1);
        query.setTomorrow(calendar.getTime());
        w = calendar.get(Calendar.DAY_OF_WEEK);
        if((w-1)==0)
            query.setTomorrowWeek("7");
        else
            query.setTomorrowWeek(String.valueOf(w - 1));
        
        return parameterMapper.findSubPeriodDiscountParameterCount(query);
    }
    
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        int w = cal.get(Calendar.DAY_OF_WEEK);
        if((w-1)==0)
            System.out.println("7");
        else
            System.out.println(String.valueOf(w - 1));
        
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE, 1);
        w = calendar.get(Calendar.DAY_OF_WEEK);
        if((w-1)==0)
            System.out.println("7");
        else
            System.out.println(String.valueOf(w - 1));
    }
}
