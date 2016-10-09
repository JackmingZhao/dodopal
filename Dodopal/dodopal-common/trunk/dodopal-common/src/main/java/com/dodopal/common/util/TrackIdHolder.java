package com.dodopal.common.util;


/**
 * 追踪号
 * 
 * @author lifeng@dodopal.com
 */

public class TrackIdHolder {
	private static ThreadLocal<String> trackIdHolder = new ThreadLocal<String>();

	/**
	 * 返回当前trackId
	 * 
	 * @return 如果不存在，返回字符串：null
	 */
	public static String get() {
		return trackIdHolder.get();
	}

	/**
	 * 保存自定义trackId
	 * 
	 * @return
	 */
	public static void set(String trackId) {
		trackIdHolder.set(trackId);
	}

	/**
	 * 生成随机trackId，生成规则：yyyyMMddHHmmssSSS + max以内随机数
	 * @param max
	 * @return
	 */
	public static String setRandomTrackId(int max) {
		StringBuffer trackId = new StringBuffer();
		trackId.append(DateUtils.getCurrDate(DateUtils.DATE_FORMAT_YYMMDDHHMMSSSSS_STR));
		trackId.append(DDPStringUtil.getRandomNumStr(max));
		set(trackId.toString());
		return get();
	}

	/**
	 * 生成随机trackId，生成规则：yyyyMMddHHmmssSSS + 9999以内四位随机数
	 * 
	 * @return 随机生成的trackId
	 */
	public static String setDefaultRandomTrackId() {
		return setRandomTrackId(9999);
	}

}
