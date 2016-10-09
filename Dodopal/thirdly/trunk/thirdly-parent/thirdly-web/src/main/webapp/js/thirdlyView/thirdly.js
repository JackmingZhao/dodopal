$(function() {
$("#merCodeQuery").val('');
		});
		
		
var merCode='';


function isNotNullOrEmpty(strVal) {
if (strVal == '' || strVal == null || strVal == undefined) {
alert("外接商户号不能为空！");
return false;
} else {
return true;
}
}
function merCodeCheck(){
merCode=$("#merCodeQuery").val();
if(isNotNullOrEmpty(merCode)){
$("#viewOne").hide();
$("#viewTwo").show();
}
}
		
// 获取当前系统时间
function dataTiem() {
	var d = new Date(), str = '';
	str += d.getFullYear(); // 获取当前年份
	str += d.getMonth() + 1; // 获取当前月份（0——11）
	str += d.getDate();
	str += d.getHours();
	str += d.getMinutes();
	str += d.getSeconds();
	return str;
}
//随机数
function getRandom(b,e){   
    if(!b && b!=0 || !e){return "?";}   
    return Math.floor( ( Math.random() * e ) + b );   
}
// 第一次生单返回信息
function thirdlyMgmt() {
	// 编码字符集
	var input_charset = 'UTF-8';
	// 签名方式
	var sign_type = 'MD5';
	// 商户号v 059891000000084
	var mercode = merCode;
	// 业务编码
	var businesscode = '01';
	// 交易时间
	var trandate = dataTiem();
	// 操作员编号
	var operationcode = '11';
	var signData = {
		input_charset : input_charset,
		mercode : mercode,
		trandate : trandate,
		businesscode : businesscode,
		operationcode : operationcode
	};
	$.ajax({
		async : false,
		type : 'post',
		url : "signDate",
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		data : JSON.stringify(signData),
		success : function(data, status) {
			// var url2 = 'https://www.baidu.com/';
			var url = 'http://192.168.1.250:5002/external/toCardRecharge?input_charset='
					+ input_charset
					+ '&sign_type='
					+ sign_type
					+ '&sign='
					+ data
					+ '&mercode='
					+ mercode
					+ '&trandate='
					+ trandate
					+ '&businesscode='
					+ businesscode
					+ '&operationcode='
					+ operationcode;
			$('#waijie').attr("src", url);
		}
	});
}
//第二步去支付
function xiadan() {
	// 编码字符集
	var input_charset = 'UTF-8';
	// 签名方式
	var sign_type = 'MD5';
	// 交易时间
	var trandate = dataTiem();
	// 订单号
	var ordernum =$("#prdordernumSpan").text();
	// 外部订单号
	var extordernum = 'W' + trandate+getRandom(10001,99999);
	// 操作员编号
	var signPayData = {
		input_charset : input_charset,
		trandate : trandate,
		ordernum : ordernum,
		extordernum : extordernum
	};
	$.ajax({
		async : false,
		type : 'post',
		url : "singThirdlyData",
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		data : JSON.stringify(signPayData),
		success : function(data, status) {
			var url = 'http://192.168.1.250:5002/external/toCallRechargeResult?input_charset='
					+ input_charset
					+ '&sign_type='
					+ sign_type
					+ '&sign='
					+ data
					+ '&trandate='
					+ trandate
					+ '&ordernum='
					+ ordernum
					+ '&extordernum=' + extordernum;
			window.location.href = url;
		}
	});
}
//第三步返回结果
function eidtClear(){
	alert('');
// 编码字符集
	var input_charset = 'UTF-8';
	// 签名方式
	var sign_type = 'MD5';
	// 商户号v 059891000000084
	var mercode = merCode;
	// 业务编码
	var businesscode = '01';
	// 交易时间
	var trandate = dataTiem();
	// 操作员编号
	var operationcode = '11';
	var signData = {
		input_charset : input_charset,
		mercode : mercode,
		trandate : trandate,
		businesscode : businesscode,
		operationcode : operationcode
	};
	$.ajax({
		async : false,
		type : 'post',
		url : "signDate",
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		data : JSON.stringify(signData),
		success : function(data, status) {
			var url = 'http://192.168.1.250:5002/external/toCardRecharge?input_charset='
					+ input_charset
					+ '&sign_type='
					+ sign_type
					+ '&sign='
					+ data
					+ '&mercode='
					+ mercode
					+ '&trandate='
					+ trandate
					+ '&businesscode='
					+ businesscode
					+ '&operationcode='
					+ operationcode;
					window.location.href = url;
		}
	});
}