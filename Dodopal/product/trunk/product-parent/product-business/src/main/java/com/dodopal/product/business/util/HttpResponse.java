/**
 * ����ͨ������ݴ������޹�˾�Ϻ��ֹ�˾
 */
package com.dodopal.product.business.util;
import org.apache.commons.httpclient.Header;
import java.io.UnsupportedEncodingException;
/**
 * Created by lenovo on 2015/8/4.
 */
public class HttpResponse
{
    private Header[] responseHeaders;
    private String stringResult;
    private byte[] byteResult;
    public Header[] getResponseHeaders()
    {
        return this.responseHeaders;
    }
    public void setResponseHeaders(Header[] responseHeaders) {
        this.responseHeaders = responseHeaders;
    }
    public byte[] getByteResult() {
        if (this.byteResult != null) {
            return this.byteResult;
        }
        if (this.stringResult != null) {
            return this.stringResult.getBytes();
        }
        return null;
    }
    public void setByteResult(byte[] byteResult) {
        this.byteResult = byteResult;
    }
    public String getStringResult() throws UnsupportedEncodingException {
        if (this.stringResult != null) {
            return this.stringResult;
        }
        if (this.byteResult != null) {
            return new String(this.byteResult, "UTF-8");
        }
        return null;
    }
    public void setStringResult(String stringResult) {
        this.stringResult = stringResult;
    }
}

