package com.dodopal.product.service;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.api.product.dto.PurchaseOrderDTO;
import com.dodopal.api.product.dto.query.PurchaseOrderQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.product.business.service.ProductOrderForPortalService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:product-business-test-context.xml"})
public class ProductOrderForPortalServiceTest {

    @Autowired
    private ProductOrderForPortalService productOrderService;

    @Test
    public void testfindPurchaseOrderForExport() {
        try {
            PurchaseOrderQueryDTO queryDto = new PurchaseOrderQueryDTO();
            queryDto.setMerCode("046571000001908");
            queryDto.setOrderDateStart(DateUtils.stringtoDate("2016-01-01", DateUtils.DATE_SMALL_STR));
            queryDto.setOrderDateEnd(DateUtils.stringtoDate("2016-05-05", DateUtils.DATE_SMALL_STR));
            queryDto.setProOrderState("0");
            DodopalResponse<List<PurchaseOrderDTO>> response = productOrderService.findPurchaseOrderForExport(queryDto);

            System.out.println("##########################################");

            if (response != null && ResponseCode.SUCCESS.equals(response.getCode())) {
                System.out.println("##########################################");
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(response.getResponseEntity(), ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
            }
            System.out.println("##########################################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testfindPurchaseOrderByPage() {
        try {
            PurchaseOrderQueryDTO queryDto = new PurchaseOrderQueryDTO();
            queryDto.setMerCode("046571000001908");
            queryDto.setOrderDateStart(DateUtils.stringtoDate("2016/1/1", DateUtils.DATE_SMALL_STR));
            queryDto.setOrderDateEnd(DateUtils.stringtoDate("2016/5/5", DateUtils.DATE_SMALL_STR));
            queryDto.setProOrderState("0");
            PageParameter page= new PageParameter();
            page.setPageNo(1);
            page.setPageSize(3);
            queryDto.setPage(page);
            DodopalResponse<DodopalDataPage<PurchaseOrderDTO>> response = productOrderService.findPurchaseOrderByPage(queryDto);

            System.out.println("##########################################");

            if (response != null && ResponseCode.SUCCESS.equals(response.getCode())) {
                System.out.println("##########################################");
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(response.getResponseEntity(), ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
            }
            System.out.println("##########################################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testsumPurchaseOrder() {
        try {
            PurchaseOrderQueryDTO queryDto = new PurchaseOrderQueryDTO();
            queryDto.setMerCode("046571000001908");
            queryDto.setOrderDateStart(DateUtils.stringtoDate("2016/1/1", DateUtils.DATE_SMALL_STR));
            queryDto.setOrderDateEnd(DateUtils.stringtoDate("2016/5/5", DateUtils.DATE_SMALL_STR));
            queryDto.setProOrderState("0");
            DodopalResponse<PurchaseOrderDTO> response = productOrderService.sumPurchaseOrder(queryDto);

            System.out.println("##########################################");

            if (response != null && ResponseCode.SUCCESS.equals(response.getCode())) {
                System.out.println("##########################################");
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(response.getResponseEntity(), ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
            }
            System.out.println("##########################################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


//  @Test
//  public void testfindRechargeOrderForExport() {
//      try {
//          RechargeOrderQueryDTO queryDto = new RechargeOrderQueryDTO();
//          queryDto.setMerCode("068001000001912");
//          queryDto.setOrderDateStart(DateUtils.stringtoDate("2016/1/1", DateUtils.DATE_SMALL_STR));
//          queryDto.setOrderDateEnd(DateUtils.stringtoDate("2016/5/5", DateUtils.DATE_SMALL_STR));
//          queryDto.setProOrderState("0");
//          DodopalResponse<List<RechargeOrderDTO>> response = productOrderService.findRechargeOrderForExport(queryDto);
//
//          System.out.println("##########################################");
//
//          if (response != null && ResponseCode.SUCCESS.equals(response.getCode())) {
//              System.out.println("##########################################");
//              System.out.println("##########################################");
//              System.out.println(ReflectionToStringBuilder.toString(response.getResponseEntity(), ToStringStyle.MULTI_LINE_STYLE));
//              System.out.println("##########################################");
//          }
//          System.out.println("##########################################");
//      }
//      catch (Exception e) {
//          e.printStackTrace();
//      }
//  }
  
//  @Test
//  public void testfindRechargeOrderByPage() {
//      try {
//          RechargeOrderQueryDTO queryDto = new RechargeOrderQueryDTO();
//          queryDto.setMerCode("068001000001912");
//          queryDto.setOrderDateStart(DateUtils.stringtoDate("2016/1/1", DateUtils.DATE_SMALL_STR));
//          queryDto.setOrderDateEnd(DateUtils.stringtoDate("2016/5/5", DateUtils.DATE_SMALL_STR));
//          queryDto.setProOrderState("0");
//          PageParameter page= new PageParameter();
//          page.setPageNo(1);
//          page.setPageSize(5);
//          queryDto.setPage(page);
//          DodopalResponse<DodopalDataPage<RechargeOrderDTO>> response = productOrderService.findRechargeOrderByPage(queryDto);
//
//          System.out.println("##########################################");
//
//          if (response != null && ResponseCode.SUCCESS.equals(response.getCode())) {
//              System.out.println("##########################################");
//              System.out.println("##########################################");
//              System.out.println(ReflectionToStringBuilder.toString(response.getResponseEntity(), ToStringStyle.MULTI_LINE_STYLE));
//              System.out.println("##########################################");
//          }
//          System.out.println("##########################################");
//      }
//      catch (Exception e) {
//          e.printStackTrace();
//      }
//  }
  
//  @Test
//  public void testSumRechargeOrder() {
//      try {
//          RechargeOrderQueryDTO queryDto = new RechargeOrderQueryDTO();
//          queryDto.setMerCode("068001000001912");
//          queryDto.setOrderDateStart(DateUtils.stringtoDate("2016/1/1", DateUtils.DATE_SMALL_STR));
//          queryDto.setOrderDateEnd(DateUtils.stringtoDate("2016/5/5", DateUtils.DATE_SMALL_STR));
//          queryDto.setProOrderState("0");
//          DodopalResponse<RechargeOrderDTO> response = productOrderService.sumRechargeOrder(queryDto);
//
//          System.out.println("##########################################");
//
//          if (response != null && ResponseCode.SUCCESS.equals(response.getCode())) {
//              System.out.println("##########################################");
//              System.out.println("##########################################");
//              System.out.println(ReflectionToStringBuilder.toString(response.getResponseEntity(), ToStringStyle.MULTI_LINE_STYLE));
//              System.out.println("##########################################");
//          }
//          System.out.println("##########################################");
//      }
//      catch (Exception e) {
//          e.printStackTrace();
//      }
//  }

}
