/**
 * @(#) RemotingCallType.java
 * module  : RemotingCallFramework
 * version : 版本管理系统中的文件版本
 * date    : 2008-12-3最近修改的日期
 * name    : 马仁配
 */
package com.dodopal.hessian;

public interface RemotingCallType {
	/**
	 * 以提交http form的方式远程调用服务.
	 */
	public static int RCT_HTTP_POST = 0;

	/**
	 * 以hessian通讯方式远程调用服务.
	 */
	public static int RCT_HTTP_HESSIAN = 1;
}
