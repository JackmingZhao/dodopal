/*
 * 前端js 校验的基本校验规则集合。 使用前提是项目中必须引入jQuery
 * 
 * 使用说明： ddpValidationBox.js 是前端所有JS的基本检验规则集， 
 * 
 * 开发人员在使用前端开发校验规则的时候，首先在该JS中引入寻找对应的校验规则，如果没有，可以按照下面的规则添加
 * 
 *  1. 首先定义校验规则名 即function名，参数可以根据需要自行设定名称和个数。
 *  2. 方法的实现体中实现校验规则。
 *  3. 由于这里是一个公共的校验规则集合，不仅限于门户类一个项目使用，所以所有的校验规则只允许返回boolean类型的返回值. 具体消息的内容由各项目自行定义.
 *  4. 为了方便各个项目对校验规则的使用， 各个项目可以根据项目自身的特点，对校验规则进一步抽象，可以参考门户校验规则。 portalValidationBox.js
 *  4. 简写说明： cn/Cn：中文， en/En: 英文， no/No: 数字， us/Us: 下划线
 * 
 */

jQuery
		.extend({
			// 手机号码验证 支持1开头，第二位数字3-8 之间任意一位，后面9位必须是数字 长度必须为11位
			mobile : function(value) {
				return /^[1][3-8]+\d{9}$/.test($.trim(value));
			},
			// 邮件地址验证
			email : function(value) {
				return /^[_a-zA-Z\d\-\./]+@[_a-zA-Z\d\-]+(\.[_a-zA-Z\d\-]+)*(\.(info|biz|com|edu|gov|net|am|bz|cn|cx|hk|jp|tw|vc|vn))$/
						.test(value.toLowerCase());
			},
			// 中文， 英文
			enCn : function(value, minLength, maxLength) {
				var newVal = $.trim(value);//.replace(/[^\x00-\xff]/g, '__');
				return /^[a-zA-Z\u4e00-\u9fa5]*$/.test(value)
						&& newVal.length <= maxLength
						&& newVal.length >= minLength;
			},
			cn : function(value, minLength, maxLength) {
				var newVal = $.trim(value);//.replace(/[^\x00-\xff]/g, '__');
				return /^[\u4e00-\u9fa5]*$/.test(value)
						&& newVal.length <= maxLength
						&& newVal.length >= minLength;
			},

			// 中文数字英文下划线,
			cnEnNoUs : function(value, minLength, maxLength) {
				var newVal = $.trim(value);//.replace(/[^\x00-\xff]/g, '__');
				return /^[\u4e00-\u9fa5_a-zA-Z0-9]+$/.test(value)
						&& newVal.length <= maxLength
						&& newVal.length >= minLength;
						
			},
			// 英文 数字 下划线
			enNoUs : function(value, minLength, maxLength) {
				var newVal = $.trim(value);//.replace(/[^\x00-\xff]/g, '__');
				return /^[a-zA-Z0-9_]*$/.test(value)
						&& newVal.length <= maxLength
						&& newVal.length >= minLength;
			},
			//4-20位字符，支持字母、数字及“_”，且第一位必须为字母
			azEnNoUs : function(value, minLength, maxLength) {
				var newVal = $.trim(value);
				return /^[a-zA-Z][a-zA-Z0-9_]*$/.test(value)
						&& newVal.length <= maxLength
						&& newVal.length >= minLength;
			},
			
			// 中文 英文 数字
			cnEnNo : function(value, minLength, maxLength) {
				var newVal = $.trim(value);
				//.replace(/[^\x00-\xff]/g, '__');
				return /^[a-zA-Z0-9\u4e00-\u9fa5]*$/.test(value)
						&& newVal.length <= maxLength
						&& newVal.length >= minLength;
			},

			// 联系电话 数字、+、-号 长度最长20位
			phone : function(value) {
				///^[\+\d\-]{0,20}$/
				return /^0\d{2,3}-\d{5,9}$|0\d{2,3}-\d{5,9}$/.test(value);
			},
			// 传真 数字、+、-号 长度<=20
			fax : function(value) {
				return /^0\d{2,3}-\d{5,9}$|0\d{2,3}-\d{5,9}$/.test(value);
			},
			zip : function(value) {
				return /^[0-9]\d{5}(?!\d)/.test(value);
			},
			// 首位为字母的
			azNumber : function(value, minLength, maxLength) {
				return /^[a-zA-Z][a-zA-Z0-9\x7f\xff_]{3,20}$/.test(value);
			},
			// 英文，数字
			enNo : function(value, param) {
				return /^[A-Za-z0-9]{1,16}$/.test(value);
			},
			//英文，数字
			enNoMax : function(value, param) {
				return /^[A-Za-z0-9]*$/.test(value);
			},
			// 银行账户
			banknumber : function(value, param) {
				return /^(\d{16}|\d{19})$/.test(value);
			},
			//非身份证的证件号码
			zjNumber : function(value) {
				return /^[A-Za-z0-9]{1,20}$/.test(value);
			},
			
			//密码为纯数字的
			pwdNumber : function(value){
				return /^[0-9]*$/.test(value);
			},
			//小数点两位
			pointNumTwo:function(value) {
				 value = $.trim(value);
				return /^(([1-9]+)|([0-9]+\.[0-9]{1,2}))$/.test(value);
			},
			
			//金额
			moneyPointTwo:function(value){
				 value = $.trim(value);
			     return /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/.test(value);
			},
			// 英文，数字- 4位， 这里主要用于验证码输入的校验
			enNoKap : function(value) {
				return /^[A-Za-z0-9]{4}$/.test(value);
			},
			
			/////////////////////////////////////////////////////////

			// 以下的规则未经严格检验， 大家使用时注意，如果自行测试通过的 ，将对应的检验规则移到本行注释上方

			// 华丽的分割线///////////////////////////////////////
			
			/////////////////////////////////////////////////////////
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			

			// 长度范围
			lengthRange : function(value, minLength, maxLength) {
				value = $.trim(value);
				var newVal = value;//.replace(/[^\x00-\xff]/g, '__');
				return value.length >= minLength && value.length <= maxLength
						&& newVal.length <= maxLength
						&& newVal.length >= minLength;
			},
			// 中文数字英文
			numberEnCn : function(value, minLength, maxLength) {
				value = $.trim(value);
				var newVal = value;//.replace(/[^\x00-\xff]/g, '__');
				return /^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value)
						&& newVal.length <= maxLength
						&& newVal.length >= minLength;
			},

			// 密码校验: 6-20位字符，支持数字、字母及符号
			password : function(value) {
				return  /^[^\s]{6,20}$/.test(value);
			},

			// 身份证号码验证15或18位数字，或者17位数字+X
			identityNum : function(value) {
				return /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(value);
			},

			// 充值金额，10的整数倍
			rechargeAmt : function(value) {
				return /^[1-9][0-9]*0$/.test(value);
			},

			// yyyy-MM-dd 日期格式
			date : function(value) {
				return /^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/.test(value);
			},
			
			username : function(value){
				return /^[a-zA-Z][a-zA-Z0-9\x7f\xff_]{3,19}$/.test(value);
			}
		})