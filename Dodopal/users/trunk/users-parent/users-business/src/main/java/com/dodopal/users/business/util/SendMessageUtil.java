package com.dodopal.users.business.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.dodopal.common.enums.MoblieCodeTypeEnum;
import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.common.util.MD5;
import com.dodopal.users.business.constant.UsersConstants;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
public class SendMessageUtil {
	private HttpServletResponse response;
	Logger log = LogManager.getLogger(SendMessageUtil.class);	
	/**
	 *  发送短信
	 * @param mobiltel 手机号
	 * @param flag 短信类型
	 *              1：个人都都宝注册；4：个人变更终端设备；5：个人找回密码；6：网点客户端注册
	 * @return 发送结果
	 * @throws IOException
	 */
	public Map<String,String> send(String mobiltel,String flag) throws IOException{
		return send(mobiltel, flag, null);
	}

	public Map<String,String> send(String mobiltel,String flag, String dypwd) throws IOException{
        //1、生成序号和动态密码
        int ran = (int) (Math.random() * 99) + 1;
        //序号
        String pwdseq = String.valueOf(ran);
        //动态密码
        if(StringUtils.isBlank(dypwd)) {
            if(MoblieCodeTypeEnum.USER_RG.getCode().equals(flag)) {
                dypwd = this.getCharacterAndNumber(4).toLowerCase();
            } else if(MoblieCodeTypeEnum.USER_DE_UP.getCode().equals(flag)) {
                dypwd = this.getCharacterAndNumber(8).toLowerCase();
            } else if(MoblieCodeTypeEnum.USER_PWD.getCode().equals(flag)) {
                dypwd = this.getCharacterAndNumber(8).toLowerCase();
            } else if(MoblieCodeTypeEnum.MER_RG.getCode().equals(flag)) {
                dypwd = this.getCharacterAndNumber(4).toLowerCase();
            } else {
                dypwd = this.getCharacterAndNumber(8).toLowerCase();
            }
        }
        log.info("手机号："+mobiltel+"动态密码："+dypwd+"密码序列："+pwdseq);
        //2、取得发送验证码地址
        String address = DodopalAppVarPropsUtil.getStringProp(UsersConstants.SEND_MOB_MSG_RUL);
        if (StringUtils.isBlank(address)) {
            log.info("address is null");
            return null;
        }
        log.info("SendMessageUtil duanxin address="+address);
        String content = dypwd + "," + pwdseq;
        String key = MD5.MD5Encode(MD5.MD5Encode(mobiltel + "dodopal" + content + flag));
        address += "?mobile=" + mobiltel + "&content=" + content + "&key=" + key + "&flag=" + flag;
        GetPageResult gpr = new GetPageResult();
        //3、发送验证码并返回
//      String sendRs = gpr.writeResult(address);
        Map<String,String> resultMap = new HashMap<String,String>();
        //返回动态密码
        resultMap.put("dypwd", dypwd);
        //返回动态序号
        resultMap.put("pwdseq", pwdseq);
        //返回发送信息结果
        resultMap.put("result", gpr.writeResult(address));
        return resultMap;
    }

	/**
	 * 生成动态密码
	 * @param length
	 * @return
	 */
	private String getCharacterAndNumber(int length){
		String val = "";                
		Random random = new Random();      
		for(int i = 0; i < length; i++){
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字
			if("char".equalsIgnoreCase(charOrNum)){
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母  
				val += (char) (choice + random.nextInt(26));
			}
			else if("num".equalsIgnoreCase(charOrNum)) // 数字          
			{              
				val += String.valueOf(random.nextInt(10));          
			}  
		}
		return val;
	}
	/**
	 * 返回xml信息
	 * @throws IOException 
	 */
	public void backSendMessageXml(String requestype,String backcode,String pwdseq,String dypwd,String verifystring) throws IOException{
		response.setContentType("text/xml;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		sb.append("<cupMobile type=\"appSendMessageBack\">");
		sb.append("<requestype>"+requestype+"</requestype>");
		sb.append("<backcode>"+backcode+"</backcode>");
		sb.append("<pwdseq>"+pwdseq+"</pwdseq>");
		sb.append("<dypwd>"+dypwd+"</dypwd>");
		sb.append("<verifystring>"+verifystring+"</verifystring>");
		sb.append("</cupMobile>");
		log.info("发送短信 返回xml====="+sb.toString());
		pw.println(sb.toString());
		pw.flush();
		pw.close();
	}
	/**
	 * 获取商户的paypwd
	 */
/*	private String hqMchPayPwd(String mchnitid){
		Logger log = LogManager.getLogger(SendMessageUtil.class);
		String mchpaypwd = null;
		try {
			mchpaypwd = individualImpl.getPayPwd(mchnitid);
		} catch (RuntimeException e) {
			log.info("查询商户支付密码时出错---"+e);
			return "error";
		}
		return mchpaypwd;
	}*/
/*	static String getProperties(String name) throws FileNotFoundException{
    	String ret = null;
        Properties prop = new Properties(); 
        //String path="E://"; //绝对路径
        String path="/home/webgate/web/runs/";
//        String path = "/pay-settlement/11 Code/配置文件/开发/";
        InputStream in =new FileInputStream(path+"/opt/app/cap/appVar_all.properties"); 
        try {
            prop.load(in); 
            ret= prop.getProperty(name).trim(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        }
		return ret; 
    }*/
	
    
	
}
