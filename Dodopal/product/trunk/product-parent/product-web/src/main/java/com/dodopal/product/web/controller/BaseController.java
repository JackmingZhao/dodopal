package com.dodopal.product.web.controller;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.md5.SignUtils;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.product.web.param.BaseRequest;
import com.dodopal.product.web.param.BaseResponse;

/**
 * @author lifeng@dodopal.com
 */

public abstract class BaseController extends CommonController {

	/**
	 * json转换为对象
	 */
	protected <T extends BaseRequest> T convertJsonToRequest(String jsondata, Class<T> clazz) {
		T request = null;
		try {
			request = JSONObject.parseObject(jsondata, clazz);
		} catch (Exception e) {
			throw new DDPException(ResponseCode.JSON_ERROR);
		}
		return request;
	}

	/**
	 * 验签:sign_type、sign、track_id不参与验签
	 */
	protected <T extends BaseRequest> void signCheck(String key, T obj) {
		// String trackId = obj.getTrack_id();
		String signOld = obj.getSign();

		// 不参与验签
		obj.setSign_type(null);
		obj.setSign(null);
		// obj.setTrack_id(null);

		// 获取新的签名MAP
		DodopalResponse<Map<String, String>> rep = SignUtils.getSignMap(obj);
		// 新的数据签名
		String signNew = SignUtils.sign(SignUtils.createLinkString(rep.getResponseEntity()), key, CommonConstants.CHARSET_UTF_8);

		// ----签名后重设参数
		// obj.setTrack_id(trackId);

		// 对比签名
		if (!signNew.equals(signOld)) {
			throw new DDPException(ResponseCode.PRODUCT_REQ_SIGN_ERROR);
		}
	}

	/**
	 * 签名:sign_type、sign、track_id不参与签名
	 */
	protected <T extends BaseResponse> void sign(String key, T obj) {
		// String trackId = obj.getTrack_id();
		// 设置编码字符集
		obj.setInput_charset(CommonConstants.CHARSET_UTF_8);
		// 不参与签名
		obj.setSign_type(null);
		obj.setSign(null);
		// obj.setTrack_id(null);

		// 把对象转换为签名map
		DodopalResponse<Map<String, String>> rep = SignUtils.getSignMap(obj);
		// 数据签名
		String sign = SignUtils.sign(SignUtils.createLinkString(rep.getResponseEntity()), key, CommonConstants.CHARSET_UTF_8);

		// ----签名后重设参数
		// obj.setTrack_id(trackId);
		// 设置签名
		obj.setSign(sign);
		obj.setSign_type(CommonConstants.SIGN_TYPE_MD5);
	}

	/**
	 * 主要校验sign_type、sign、input_charset、reqdate
	 */
	protected void baseCheck(BaseRequest request) {
		// 编码字符集
		String inputCharset = request.getInput_charset();
		if (StringUtils.isBlank(inputCharset)) {
			throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_INPUT_CHARSET_NULL);
		}

		// 签名方式 TODO
//		String signType = request.getSign_type();
//		if (StringUtils.isBlank(signType)) {
//			throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_SIGN_TYPE_ERROR);
//		}

		// 签名
		String sign = request.getSign();
		if (StringUtils.isBlank(sign)) {
			throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_SIGN_NULL);
		}

		// 请求时间
		String reqdate = request.getReqdate();
		if (StringUtils.isBlank(reqdate)) {
			throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_REQDATE_ERROR);
		}

		if (reqdate.length() != 14) {
			throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_REQDATE_ERROR);
		}

		Date date = DateUtils.stringtoDate(reqdate, DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR);
		if (date == null) {
			throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_REQDATE_ERROR);
		}

	}
	
    /**
     * list转化String
     * @param list 转化源
     * @param array 单个dto转化String的顺序数组（dto内部属性字符串数组）
     * @param splitSign1 第一层分隔符
     * @param splitSign2 第二层分隔符
     * @return
     * @throws Exception
     */
	protected static <T> String listToString(List<T> list, String[] array, String splitSign1, String splitSign2) throws Exception {

        String result = "";
        StringBuffer resultBuffer = new StringBuffer();
        if (list != null && list.size() > 0) {
            for (T dto : list) {
                resultBuffer.append(splitSign1);
                resultBuffer.append(dtoToString(dto, array, splitSign2));
            }
            result = resultBuffer.toString().substring(1);
        }
        return result;
    }

    /**
     * dto转化String
     * @param object 转化源
     * @param array dto转化String的顺序数组（dto内部属性字符串数组）
     * @param splitSign 分隔符
     * @return
     * @throws Exception
     */
	protected static String dtoToString(Object object, String[] array, String splitSign) throws Exception {

        StringBuffer result = new StringBuffer();
        if (object != null) {
            Class<?> clz = object.getClass();// 拿到该类
            Field[] fields = clz.getDeclaredFields();// 获取实体类的所有属性，返回Field数组
            
            for (int i =0;i<array.length;i++) {
                for (int j =0;j<fields.length;j++) {
                    Field field = fields[j];
                    if (field.getName().equals(array[i])) {
                        Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
                        Object val = (Object) m.invoke(object);// 调用getter方法获取属性值
                        result.append(splitSign);
                        if (val != null) {
                            result.append(val);
                        }
                    }
                }
            }
        }
        
        return result.toString().substring(1);
    }

    /**
     * 属性名转化
     * @param fildeName
     * @return
     * @throws Exception
     */
    private static String getMethodName(String fildeName) throws Exception {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }
}
