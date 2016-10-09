/*
 * oss项目的前端js 校验消息处理
 * 
 * 使用该js的前提是 ：
 * 
 * 1. 项目中必须引入ddpValidationBox.js及其依赖。
 * 
 */

$.extend($.fn.validatebox.defaults.rules, {
	// 中文数字英文下划线
    numberEnCn_: {
        validator: function (value, param) {
        	return $.cnEnNoUs(value, param[0], param[1]);
        },
        message: '{0}{1}位字符,只能中文、数字、英文、下划线'
    },
    // 数字、英文、下划线
    enNoUs: {
        validator: function (value, param) {
        	return $.enNoUs(value, param[0], param[1]);
        },
        message: '{0}{1}位字符,只能数字、英文、下划线'
    },
    // 中文、数字、英文
    cnEnNo: {
        validator: function (value, param) {
        	return $.cnEnNo(value, param[0], param[1]);
        },
        message: '{0}{1}位字符,只能中文、数字、英文'
    },
    // 中文 英文
    enCn : {
    	validator: function (value, param) {
        	return $.enCn(value, param[0], param[1]);
        },
        message: '{0}{1}位字符,只能中文、英文'
    },
    cn : {
    	validator: function (value, param) {
        	return $.cn(value, param[0], param[1]);
        },
        message: '{0}{1}位字符,只能中文'
    },
    //联系电话 数字、+、-号 长度最长20位
    phone : {
      	 validator: function (value, param) {
               return $.phone(value);
           },
           message: '联系电话格式不正确'
      },
      // 传真 数字、+、-号 长度<=20
      fax: {
          validator: function (value, param) {
          	 return   $.fax(value);
          },
          message: '传真格式不正确，比如:021-6778909'
      },
      //邮箱
      email: {
          validator: function (value, param) {
          	 return $.email(value);
          },
          message: '邮箱格式不正确'
      },
      //邮编
      zip: {
          validator: function (value, param) {
              return $.zip(value);
          },
          message: '邮编格式不正确,比如:210000'
      },
      // 长度范围
  	  lengthRange:{
  		 validator: function (value, param) {
  			 	//value = value.replace(/[^\x00-\xff]/g, '__');
  	            return $.lengthRange(value,param[0],param[1]);
  	        },
  	        message: '长度范围为{0}{1}位字符'
  	  },
  	 // pos号段
      posCode : {
      	validator: function (value, param) {
              return /^[0-9]{12}$/.test(value);
          },
          message: '只能12位数字'
      },
      // 银行账户
      banknumber : {
      	validator: function (value, param) {
              return $.banknumber(value);
          },
          message: '请输入正确的银行账户，16或者19位数字'
      },
    // 以下的规则未经严格检验， 大家使用时注意，如果自行测试通过的 ，将对应的检验规则移到本行注释上方
    
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
    
	
	//判断最大的长度
	lengthNumber:{
		 validator: function (value, param) {
			 	//value = value.replace(/[^\x00-\xff]/g, '__');
	            return value.length <= param[0] ;
	        },
	        message: '长度范围最大为{0}位'
	},
	minLength: {
		validator: function(value, param){
			//value = value.replace(/[^\x00-\xff]/g, '__');
			return value.length >= param[0];
		},
		message: ' 请至少输入{0}字.'
	},
	pwdLength: {
		validator: function(value, param){
			//value = value.replace(/[^\x00-\xff]/g, '__');
			return value.length >= 6;
		},
		message: ' 请至少输入6位字符'
	},
	// 数字字母下划线
	numberEnUndreScore: {
        validator: function (value, param) {
            return /^\w+$/.test(value)&&value.length <= param[1] && value.length >=param[0];
        },
        message: '{0}{1}位字符,只能数字、字母、下划线'
    },
    // 中文数字英文
    numberEnCn: {
        validator: function (value, param) {
        	var newVal=$.trim(value);
        	//.replace(/[^\x00-\xff]/g, '__');
            return /^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(newVal)&&newVal.length <= param[1] && newVal.length >=param[0];
        },
        message: '{0}{1}位字符,只能中文、数字、英文'
    },
    
    // 数字英文
    numberEn: {
        validator: function (value, param) {
            return /^[a-zA-Z0-9]+$/.test(value)&&value.length <= param[1] && value.length >=param[0];
        },
        message: '{0}{1}位字符,只能数字、英文'
    },
	
    // 数字
    number : {
    	validator: function (value, param) {
            return /^[0-9]+$/.test(value);
        },
        message: '只能数字'
    },
    
    // 最大位数的纯数字
    numberMaxLength : {
    	validator: function (value, param) {
            return /^[0-9]+$/.test(value)&&value.length <= param[1]&& value.length >=param[0];
        },
        message: '{0}{1}位字符,只能数字'
    },
    
    // 有效的金额
    valuableMoney : {
    	validator: function (value) {
    		return parseInt(value) == 0?false:true
        },
        message: '请输入有效的金额'
    },
    
    //首位为字母的
    AazNumber: {
        validator: function (value, param) {
            return /^[a-zA-Z][a-zA-Z0-9\x7f\xff_]{3,20}$/.test(value);
        },
        message: '4-20位字符，支持字母、数字及“_”，且第一位必须为字母'
    },
    
    mobile: {
        validator: function (value, param) {
        	return /^[1][3-8]+\d{9}$/.test($.trim(value));
        },
        message: '手机号码格式不正确'
    },
    
    
    telephone: {
        validator: function (value, param) {
            return   /^0\d{2,3}-\d{5,9}$|0\d{2,3}-\d{5,9}$/.test(value);
        },
        message: '固定电话格式不正确,比如:021-6778909'
    },
    
//    mobileAndTel: {
//        validator: function (value, param) {
//          return /(^([0\+]\d{2,3})\d{3,4}\-\d{3,8}$)|(^([0\+]\d{2,3})\d{3,4}\d{3,8}$)|(^([0\+]\d{2,3}){0,1}13\d{9}$)|(^\d{3,4}\d{3,8}$)|(^\d{3,4}\-\d{3,8}$)/.test(value);
//        },
//        message: '请正确输入电话号码'
//    },
    
    mobileAndTel: {
        validator: function (value, param) {
        	  //return   /^0\d{2,3}-\d{5,9}|0\d{2,3}-\d{5,9}$/.test(value);
        	return /(^0\d{2,3}-\d{5,9}$|0\d{2,3}-\d{5,9}$)|(^([0\+]\d{2,3}){0,1}13\d{9}$)|(^\d{3,4}\d{3,8}$)|(^\d{3,4}\-\d{3,8}$)/.test(value);

        },
        message: '请正确输入手机号码或固定电话'
    },
    
    
       //ip地址
   IP: {
       validator: function (value, param) {
           return /^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/.test(value);
       },
       message: 'IP地址格式不正确,例如：255.255.255.255'
   },
   
   //端口号
   port : {
       validator: function (value, param) {
           return  /^([0-9]|[1-9]\d|[1-9]\d{2}|[1-9]\d{3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/.test(value);
       },
       message: '请输入正确的端口号'
   },
   
   //英文，数字
   egNumber: {
       validator: function (value, param) {
           return /^[A-Za-z0-9]{1,32}$/.test(value);
       },
       message: '请输入英文或者数字'
   },
   checkPWD: {
       validator: function (value, param) {
    	   return value == $(param[0]).val();
       },
       message: '密码不一致'
   },
   ddpComboboxRequired : {
	   validator: function (value, param) {
           return $(param[0]).combobox('getValue')!="";
       },
       message: '请选择'
   },
   //商户类型
   ddpComboboxMerType : {
	   validator: function (value, param) {
           return $(param['']).combobox('getValue')=="--请选择--";
       },
       message: '请选择一个商户类型'
   },
   //身份证
   ddpComboboxIdCard : {
	   validator: function (value, param) {
		   if($(param[0]).combobox('getValue')=='0'){
			   return $.identityNum(value);
		   }else if($(param[0]).combobox('getValue')=='1'){
			   return $.identityNum(value);
		   }else{
			   return $.zjNumber(value);
		   }
       },
       message: '证件号码输入有误，身份证15位或18位数字，驾照15位或18位数字，护照只能为数字与英文'
   },
   
   
   //特殊字段OSS商户开户使用
   //商户名称
   merName: {
       validator: function (value) {
       	var newVal=value;
           return /^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value);
       },
       message: '请输入店面名称，支持中文、数字及字母'
   },
   //联系人
   merLinkUser : {
   	validator: function (value, param) {
       	return $.enCn(value, param[0], param[1]);
       },
       message: '{0}{1}位字符,支持中文、英文'
   },
   //最长10位， 金额，单位为分
   amt : {
	   	validator: function (value, param) {
	   		if(isNaN(value)) {
				return false;
			} else {
				value = parseFloat(value);
				if(value < 0 || value > 99999999.99 || (value*100) != parseInt(value*100)) {
					return false;
				}
			}
	   		return true;
       },
       message: '请输入数字,且最大支持 99999999.99'
	},
	//正数金额，单位为分
	amount : {
		   	validator: function (value, param) {
		   		if(isNaN(value)) {
					return false;
				} else {
					var dot = value.indexOf(".");
		            if(dot != -1){
		                var dotCnt = value.substring(dot+1,value.length);
		                if(dotCnt.length > 2){
		                    return false;
		                }
		            }
					val = parseFloat(value);
					if((val <= 0)  || (val > 99999999.99)) {
						return false;
					}
				}
		   		return true;
	       },
	       message: '请输入正数金额,且正数最大支持8位，小数最大支持2位小数'
		},
    //正整数
    positiveInteger : {
        validator: function (value, param) {
            return /^[1-9][0-9]{0,}[0]$/.test(value);
        },
        message: '只能为10的倍数的正整数'
    },
    
   //金额，单位为元   0.01<value<10000
	icdcprdAmount : {
		   	validator: function (value, param) {
		   		if(isNaN(value)) {
					return false;
				} else {
					var dot = value.indexOf(".");
		            if(dot != -1){
		                var dotCnt = value.substring(dot+1,value.length);
		                if(dotCnt.length > 2){
		                    return false;
		                }
		            }
					val = parseFloat(value);
					if((val <= 0)  || (val > 10000)) {
						return false;
					}
				}
		   		return true;
	       },
	       message: '请输入0.01~10000范围的金额,小数最大支持2位小数'
		},
    
    //校验金额
	ddpAmount : {
        validator: function (value, param) {
            return  /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/.test(value);
        },
        message: '请输入正数金额,且最大支持2位小数'
	}
});

$.fn.validatebox.defaults.missingMessage = '请输入此项';
