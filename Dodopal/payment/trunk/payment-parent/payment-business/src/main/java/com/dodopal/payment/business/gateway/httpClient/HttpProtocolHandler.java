package com.dodopal.payment.business.gateway.httpClient;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.util.IdleConnectionTimeoutThread;

import java.io.IOException;
import java.net.UnknownHostException;
/**
 * Created by lenovo on 2015/8/4.
 */
public class HttpProtocolHandler
{
    private static String DEFAULT_CHARSET = "UTF-8";
    private int defaultConnectionTimeout = 8000;
    private int defaultSoTimeout = 30000;
    private int defaultIdleConnTimeout = 60000;
    private int defaultMaxConnPerHost = 30;
    private int defaultMaxTotalConn = 80;
    private static final long defaultHttpConnectionManagerTimeout = 3000L;
    private HttpConnectionManager connectionManager;
    private static HttpProtocolHandler httpProtocolHandler = new HttpProtocolHandler();
    public static HttpProtocolHandler getInstance()
    {
        return httpProtocolHandler;
    }
    private HttpProtocolHandler()
    {
        this.connectionManager = new MultiThreadedHttpConnectionManager();
        this.connectionManager.getParams().setDefaultMaxConnectionsPerHost(this.defaultMaxConnPerHost);
        this.connectionManager.getParams().setMaxTotalConnections(this.defaultMaxTotalConn);
        IdleConnectionTimeoutThread ict = new IdleConnectionTimeoutThread();
        ict.addConnectionManager(this.connectionManager);
        ict.setConnectionTimeout(this.defaultIdleConnTimeout);
        ict.start();
    }
    public HttpResponse execute(HttpRequest request)
    {
        HttpClient httpclient = new HttpClient(this.connectionManager);
        int connectionTimeout = this.defaultConnectionTimeout;
        if (request.getConnectionTimeout() > 0) {
            connectionTimeout = request.getConnectionTimeout();
        }
        httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
        int soTimeout = this.defaultSoTimeout;
        if (request.getTimeout() > 0) {
            soTimeout = request.getTimeout();
        }
        httpclient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);
        httpclient.getParams().setConnectionManagerTimeout(3000L);
        String charset = request.getCharset();
        charset = charset == null ? DEFAULT_CHARSET : charset;
        HttpMethod method = null;
        if (request.getMethod().equals("GET")) {
            method = new GetMethod(request.getUrl());
            method.getParams().setCredentialCharset(charset);
            method.setQueryString(request.getQueryString());
        } else {
            method = new PostMethod(request.getUrl());
            ((PostMethod)method).addParameters(request.getParameters());
            method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; text/html; charset=" + charset);
        }
        method.addRequestHeader("User-Agent", "Mozilla/4.0");
        HttpResponse response = new HttpResponse();
        try
        {
            httpclient.executeMethod(method);
            if (request.getResultType().equals(HttpResultType.STRING))
                response.setStringResult(method.getResponseBodyAsString());
            else if (request.getResultType().equals(HttpResultType.BYTES)) {
                response.setByteResult(method.getResponseBody());
            }
            response.setResponseHeaders(method.getResponseHeaders());
        }
        catch (UnknownHostException localUnknownHostException)
        {
            return null;
        }
        catch (IOException localIOException)
        {
            return null;
        }
        catch (Exception localException)
        {
            return null;
        } finally {
            method.releaseConnection(); } method.releaseConnection();
        return response;
    }
    protected String toString(NameValuePair[] nameValues)
    {
        if ((nameValues == null) || (nameValues.length == 0)) {
            return "null";
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < nameValues.length; i++) {
            NameValuePair nameValue = nameValues[i];
            if (i == 0)
                buffer.append(nameValue.getName() + "=" + nameValue.getValue());
            else {
                buffer.append("&" + nameValue.getName() + "=" + nameValue.getValue());
            }
        }
        return buffer.toString();
    }
}