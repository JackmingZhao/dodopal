package com.dodopal.api.users.facade;

import com.dodopal.api.users.dto.MobileUserDTO;
import com.dodopal.api.users.dto.PortalUserDTO;
import com.dodopal.common.facade.BaseFacade;
import com.dodopal.common.model.DodopalResponse;

/**
 * 类说明 ：
 * 
 * @author lifeng
 */

public interface UserLoginFacade extends BaseFacade{
	/**
	 * 用户门户登录、权限获取
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public DodopalResponse<PortalUserDTO> login(String userName, String password);

	/**
	 * 手机端、VC端登录
	 * 
	 * @param userName
	 * @param password
	 * @param source
	 * @param loginid 老系统用户id
	 * @param usertype 老系统用户类型，个人0，集团1，网点2，商户3
	 * @return
	 */
	public DodopalResponse<MobileUserDTO> login(String userName, String password, String source, String loginid, String usertype);

	/**
	 * 老系统联合登录
	 * @param loginid
	 * @param password
	 * @param usertype
	 * @return
	 */
	public DodopalResponse<PortalUserDTO> unionLogin(String loginid, String password, String usertype);
}
