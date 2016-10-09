package com.dodopal.oss.delegate.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.LoadOrderDTO;
import com.dodopal.api.product.dto.LoadOrderRequestDTO;
import com.dodopal.api.product.dto.query.LoadOrderQueryDTO;
import com.dodopal.api.product.facade.LoadOrderFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.delegate.BaseDelegate;
import com.dodopal.oss.delegate.LoadOrderDelegate;
import com.dodopal.oss.delegate.constant.DelegateConstant;

@Service
public class LoadOrderDelegateImpl  extends BaseDelegate implements LoadOrderDelegate {

    @Override
    public DodopalResponse<DodopalDataPage<LoadOrderDTO>> findLoadOrders(LoadOrderQueryDTO queryDto) {
        LoadOrderFacade loadOrderFacade = getFacade(LoadOrderFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        return loadOrderFacade.findLoadOrders(queryDto);
    }
    
    public DodopalResponse<LoadOrderDTO> findLoadOrderByOrderNum(String orderNum){
        LoadOrderFacade loadOrderFacade = getFacade(LoadOrderFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        return loadOrderFacade.findLoadOrderByOrderNum(orderNum);
    }
    
    ///////////////////以下是测试代码/////////////////////////////////
    
    
    @Override
    public DodopalResponse<String> bookLoadOrder(LoadOrderRequestDTO loadOrderRequestDTO) {
        StringBuffer sbf= new StringBuffer();
        String url ="http://192.168.1.242:5002/product-web/loadOrder/bookLoadOrder";
     // String url ="http://localhost:8084/product-web/loadOrder/bookLoadOrder";
        sbf.append("?");
        sbf.append("sourceOrderNum="+loadOrderRequestDTO.getSourceOrderNum()+"&");
        sbf.append("cardNum="+loadOrderRequestDTO.getCardNum()+"&");
        sbf.append("merchantNum="+loadOrderRequestDTO.getMerchantNum()+"&");
        sbf.append("productNum="+loadOrderRequestDTO.getProductNum()+"&");
        sbf.append("cityCode="+loadOrderRequestDTO.getCityCode()+"&");
        sbf.append("chargeAmt="+loadOrderRequestDTO.getChargeAmt()+"&");
        sbf.append("sourceOrderTime="+loadOrderRequestDTO.getSourceOrderTime()+"&");
        sbf.append("signType="+loadOrderRequestDTO.getSignType()+"&");
        sbf.append("signData="+loadOrderRequestDTO.getSignData()+"&");
        sbf.append("signCharset="+loadOrderRequestDTO.getSignCharset()); 
        String result = sendGet(url,sbf.toString());
        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setResponseEntity(result);
        return response;
    }

    @Override
    public DodopalResponse<String> findLoadOrder() {
        String url ="http://192.168.1.242:5002/product-web/loadOrder/findLoadOrder";
        // String url ="http://localhost:8084/product-web/loadOrder/findLoadOrder";
        String result = sendGet(url,"");
        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setResponseEntity(result);
        return response;
    }

    
    
    @Override
    public DodopalResponse<String> findLoadOrderStatus(LoadOrderRequestDTO loadOrderRequestDTO) {
        StringBuffer sbf= new StringBuffer();
         String url ="http://192.168.1.242:5002/product-web/loadOrder/findLoadOrderStatus";
         //String url ="http://localhost:8084/product-web/loadOrder/findLoadOrderStatus";
        sbf.append("?");
        sbf.append("sourceOrderNum="+loadOrderRequestDTO.getSourceOrderNum()+"&");
        sbf.append("merchantNum="+loadOrderRequestDTO.getMerchantNum()+"&");
        sbf.append("signType="+loadOrderRequestDTO.getSignType()+"&");
        sbf.append("signData="+loadOrderRequestDTO.getSignData()+"&");
        sbf.append("signCharset="+loadOrderRequestDTO.getSignCharset()); 
        String result = sendGet(url,sbf.toString());
        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setResponseEntity(result);
        return response;
    }    

    //Get方法
    public static String sendGet(String url,String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url+param ;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }


    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public DodopalResponse<List<LoadOrderDTO>> findLoadOrdersExport(LoadOrderQueryDTO queryDto) {
        LoadOrderFacade loadOrderFacade = getFacade(LoadOrderFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        return loadOrderFacade.findLoadOrdersExport(queryDto);
    }

    
}
