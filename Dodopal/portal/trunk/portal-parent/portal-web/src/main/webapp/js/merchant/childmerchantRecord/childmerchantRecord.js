$(function() {
	findMerTranRecord();
	findTranOutStatus();
	$('.page-navi').paginator({
						prefix : 'childmerchantRecord',
						pageSizeOnChange : findMerTranRecord
					});
	highlightTitle();
	initTranDialog();
	$('#childRecordView').hide();
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="子商户管理"){
			$(this).addClass('cur');
		}
	});
});

function initTranDialog(){
	$("#realMinTranMoney").bind('keyup', function() {
		clearNoNum($(this));
	});
//	$("#realMinTranMoney").bind('keydown', function() {
//		checkDecimal($(this),1,9,0,2);
//	});
//	$("#realMinTranMoney").bind('blur', function() {
//		clearNoNumOnBlur($(this));
//	});
	$("#realMaxTranMoney").bind('keyup', function() {
		clearNoNum($(this));
	});
//	$("#realMaxTranMoney").bind('keydown', function() {
//		checkDecimal($(this),1,9,0,2);
//	});
//	$("#realMaxTranMoney").bind('blur', function() {
//		clearNoNumOnBlur($(this));
//	});
}

function findMerTranRecord(page){
	if (!page) {
		page = {
			pageNo : 1,
			pageSize : 10
		};
	}
	var query ={
				merCode:$('#merCode').val(),
				merOrUserName:escapePeculiar($.trim($('#merOrUserName').val())),
				createDateStart:$('#createDateStart').val(),
				createDateEnd:$('#createDateEnd').val(),
				tranName:$('#tranName').val(),
				tranType:$('#tranType').val(),
				tranCode:escapePeculiar($.trim($('#tranCode').val())),
				realMinTranMoney:escapePeculiar($.trim($('#realMinTranMoney').val())),
				realMaxTranMoney:escapePeculiar($.trim($('#realMaxTranMoney').val())),
				tranOutStatus:$('#tranOutStatus').val(),
				orderNumber :escapePeculiar($.trim($('#orderNumber').val())),
				page:page,
	}
	var beginDate = query.createDateStart;
	var endDate = query.createDateEnd;
	var min = query.realMinTranMoney;
	var max = query.realMaxTranMoney;
	compareTime(query,beginDate,endDate);
	compareMoney(query,min,max);
	ddpAjaxCall({
		url : "find",
		data : query,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#transactionRecordTb tbody').empty();
				if( data.responseEntity!=null &&  data.responseEntity.records.length > 0){
					$(data.responseEntity.records).each(function(i,v){
						html = '<tr>';
						html +='<td class="nobor">&nbsp;</td>';
					
						html += '<td>'+getSequence(data.responseEntity,i)+'</td>';
						html += '<td>'+v.merOrUserName+'</td>';
						html += '<td>'+v.tranCode+'</td>';
						html += '<td>'+(v.businessType == null ?'':v.businessType)+'</td>'
						html += '<td>'+fmoney(v.realTranMoney,2)+'</td>';
						html += '<td>'+(v.payWayName == null ? '' : v.payWayName)+'</td>';
						html += '<td>'+(v.tranType == null ? '' : v.tranType) +'</td>';
						html += '<td>'+v.tranOutStatus+'</td>';
						html += '<td>'+(v.createDate == null ? '' : formatDate(v.createDate,'yyyy-MM-dd HH:mm:ss'))+'</td>';
						
						html += '<td class="a-center">';
						html += '<a href="javascript:void(0);" class="text-icon" title="详情" onclick="findchildTranscationRecordView(\''
							+ v.tranCode + '\');");"></a>';
						html += '</td>';
						
						html +='<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#transactionRecordTb').append(html);
					});
					$('.com-table01 tbody tr:even').find('td').css("background-color",'#f6fafe');
					$('.com-table02 tbody tr:even').find('td').css("background-color",'#f6fafe');	
				}
				var pageSize = data.responseEntity.pageSize;
				var pageNo = data.responseEntity.pageNo;
				var totalPages = data.responseEntity.totalPages;
				var rows = data.responseEntity.rows;
				$('.page-navi').paginator('setPage',pageNo,pageSize,totalPages,rows);
				$('.page-navi select').attr("style","width:45px;padding:0px 0px");
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}	
		}
	});
}


//查看交易记录详情
function findchildTranscationRecordView(tranCode) {
	ddpAjaxCall({
				url : "childfindMerchantView",
				data : tranCode,
				successFn : function(data) {
					if (data.code == "000000") {
						loadChildTranRecord(data.responseEntity);
					} else {
						$.messagerBox({
									type : 'warn',
									title : "信息提示",
									msg : data.message
								});
					}
				}
			});
}

//清空子商户详情界面界面信息
function cleartranRecordView() {
	$('#childTranRecordViewTable tr td').html('');
	$('#tranOutStatusDetail').html('');
	$('#childRecordView ul li').html('');
}

//加载交易记录信息
function loadChildTranRecord(data) {
	cleartranRecordView();
	// 交易状态
	$('#tranOutStatusDetail').html(data.tranOutStatus);
	// 订单时间
	if (data.orderDate) {
		data.orderDate = formatDate(data.orderDate, 'yyyy-MM-dd HH:mm:ss');
		$('#orderDate').html("订单时间："+data.orderDate);
	}else{
		$('#orderDate').html("订单时间：");
	}
	// 交易时间
	if (data.createDate) {
		data.createDate = formatDate(data.createDate, 'yyyy-MM-dd HH:mm:ss');
		$('#createDate').html("交易时间："+data.createDate);
	}else{
		$('#createDate').html("交易时间："+data.createDate);
	}
	// 备注
	if (data.comments) {
		$('#comments').html("备注："+data.comments);
	}else{
		$('#comments').html("备注：");
	}
	// 交易流水号
	$('#tranCodeView').html(data.tranCode);
	// 订单编号
	$('#orderNumberView').html(data.orderNumber);
	// 交易类型
	$('#tranTypeView').html(data.tranType);
	// 业务名称
	$('#businessTypeView').html(data.businessType);
	
	// 应付金额（元）
	$('#amountMoneyView').html(data.amountMoney);
	// 实付金额（元）
	$('#realTranMoneyView').html(data.realTranMoney);
	
	$('#childTranRecordMain').hide();
	$('#childRecordView').show();
	
}

//查看界面返回
function clearView() {
	$('#childRecordView').hide();
	$('#childTranRecordMain').show();
}


function compareTime(query,beginDate,endDate){
	 var d1 = new Date(beginDate.replace(/\-/g, "\/")); 
	 var d2 = new Date(endDate.replace(/\-/g, "\/")); 

	  if(beginDate!=""&&endDate!=""&&d1 >=d2) 
	 { 
	  var temp = endDate;
	  endDate = beginDate;
	  beginDate = temp;
	  $('#createDateStart').val(beginDate)
	  $('#createDateEnd').val(endDate)
	  query.createDateStart = beginDate;
	  query.createDateEnd = endDate;
	 }
}

function compareMoney(query,min,max){
	if(min != "" && max != "" && parseFloat(min)>parseFloat(max)){
		var temp = max;
		max = min;
		min = temp;
		$('#realMinTranMoney').val(min);
		$('#realMaxTranMoney').val(max);
		query.realMinTranMoney = min;
		query.realMaxTranMoney = max;
	}
}

//查看子商户详情
function viewChildMerchant(merCode){
	ddpAjaxCall({
		url : "childfindMerchant",
		data : merCode,
		successFn : function(data) {
			if (data.code == "000000") {
				loadChildMerchant(data.responseEntity);
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
}

//加载子商户信息
function loadChildMerchant(data){
	clearChildMerchantView();
	var merchantUserBeans = data.merchantUserBean;
	var merBusRateBeanList = data.merBusRateBeanList;
	if(data.createDate){
		data.createDate = formatDate(data.createDate,'yyyy-MM-dd HH:mm:ss');
	}
	
	//商户编码
	$('#merCodeSpan').val(data.merCode);
	//商户类型
	
	$('#merTypeSpan').html(findmerType(data.merType));
	//审核状态
	$('#merStateSpan').html(findmerState(data.merState));
	//商户名称
	$('#merNameSpan').html(data.merName);
	//用户名
	$('#merUserNameSpan').html(merchantUserBeans.merUserName);
	//联系人
	$('#merLinkUserSpan').html(data.merLinkUser);
	//手机号码
	$('#merLinkUserMobileSpan').html(data.merLinkUserMobile);
	//地址
	$('#merAddsSpan').html(data.merProName+data.merCityName+data.merAdds);
	//通卡公司
	if(merBusRateBeanList!=null && merBusRateBeanList!=''){
	loadYkt(merBusRateBeanList);
		$('#yktDIVView').show();
	}else{
		$('#yktDIVView').hide();
	}
	//经营范围
	$('#merBusinessScopeIdSpan').html(findmerBusinessScopeId(data.merBusinessScopeId));
	//邮箱
	$('#merEmailSpan').html(data.merEmail);
	//固定电话
	$('#merTelephoneSpan').html(data.merTelephone);
	//传真
	$('#merFaxSpan').html(data.merFax);
	//证件类型
	$('#merUserIdentityTypeSpan').html(findmerUserIdentityType(merchantUserBeans.merUserIdentityType));
	//证件号码
	$('#merUserIdentityNumberSpan').html(merchantUserBeans.merUserIdentityNumber);
	//邮编
	$('#merZipSpan').html(data.merZip);
	//开户银行
	$('#merBankNameSpan').html(findBankNumber(data.merBankName));
	//开户行账号
	$('#merBankAccountSpan').html(data.merBankAccount);
	//开户名称
	$('#merBankUserNameSpan').html(data.merBankUserName);
	//备注
	$('#merUserRemarkSpan').html(merchantUserBeans.merUserRemark);
	openChildMerchantView();
	
}

//清空子商户详情界面界面信息
function clearChildMerchantView(){
	$('#childMerchantViewTable tr td span').html('');
	//清空查看子商户详情界面上的通卡公司费率
	$('#yktTable tbody').empty();
	$('#yktTableTbodyView').html('');
}

function openChildMerchantView(){
	$('#childMerchantDetailView').show();
	$('#yktTable').show();
	$('#tranRecordList').hide();
}

function clearTranRecord(selector){
	$('#' + selector)[0].reset();
	selectUiInit();
}

//导出
//function exportChildified(){
//   // $("#recordForm").submit();
//    $.fileDownload('merChildTanlistExport', {
//		data: $('#recordForm').serialize(),
//		failCallback: function() {
//			msgShow(systemWarnLabel, "文件导出出错", 'warning');
//		}
//	})
//}

//格式化金额 s-金额 n-保留小数个数
function fmoney(s, n)  
{  
   n = n > 0 && n <= 20 ? n : 2;  
   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
   var l = s.split(".")[0].split("").reverse(),  
   r = s.split(".")[1];  
   t = "";  
   for(i = 0; i < l.length; i ++ )  
   {  
      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
   }  
   return t.split("").reverse().join("") + "." + r;  
}  

//商户类型
function findmerType(merType){
		if(merType == '10'){
			return '代理商户';
		}else if(merType == '11'){
			return '代理商自有网点';
		}else if(merType == '12'){
			return '连锁商户';
		}else if(merType == '13'){
			return '直营';
		}else if(merType == '14'){
			return '加盟';
		}else{
			return '';
		}
}
//审核状态
function findmerState(merState){
	if(merState == '0'){
		return '未审核';
	}else if(merState == '1'){
		return '审核通过';
	}else if(merState == '2'){
		return '审核不通过';
	}else{
		return '';
	}
}

//开户银行
function findBankNumber(merBankName){
	if(merBankName == '1'){
		return '工商银行';
	}else if(merBankName == '2'){
		return '交通银行';
	}else if(merBankName == '3'){
		return '建设银行';
	}else if(merBankName == '4'){
		return '农业银行';
	}else if(merBankName == '5'){
		return '中国银行';
	}else{
		return '';
	}
}
//经营范围
function findmerBusinessScopeId(merBusinessScopeIdName){
	if(merBusinessScopeIdName == 'BLD'){
		return '便利店';
	}else if(merBusinessScopeIdName == 'ZHJF'){
		return '综合缴费';
	}else if(merBusinessScopeIdName == 'SHFW'){
		return '生活服务';
	}else if(merBusinessScopeIdName == 'CY'){
		return '餐饮';
	}else if(merBusinessScopeIdName == 'OTHER'){
		return '其他';
	}else{
		return '';
	}
}
//证件类型
function findmerUserIdentityType(merUserIdentityTypeName){
	if(merUserIdentityTypeName == '0'){
		return '身份证';
	}else if(merUserIdentityTypeName == '1'){
		return '驾照';
	}else if(merUserIdentityTypeName == '2'){
		return '护照';
	}else{
		return '';
	}
}
//业务费率名称
function busRateType(rateType){
	if(rateType=='1'){
		return '单笔返点金额(元)';
	}else if(rateType=='2'){
		return '千分比(‰)';
	}else{
		return '';
	}
}

//截取通卡公司7位名称
function formatYKTName(name) {
	var result = name;
	if(name.length > 7) {
		result = name.substring(0, 7)+ "...";
	}
	return result;
}
//加载通卡公司费率
function loadYkt(merBusRateBeanList) {
	var selected = '';
	if(merBusRateBeanList && merBusRateBeanList.length > 0){
		$(merBusRateBeanList).each(function(i,v){
			if(selected != '') {
				selected += ',';
			}
			selected += v.proName == null ? '' : v.proName;
			
			$('.null-box').hide();
			html = '<tr>';
			html +='<td class="nobor">&nbsp;</td>';
			
			html += '<td>'
			html += v.proName == null ? '' : v.proName;
			html += '</td>';
			
			html += '<td>'
			html += v.rateName  == null ? '' : v.rateName;
			html += '</td>';
			
			html += '<td>'
			html += v.rateType == null ? '' : busRateType(v.rateType);
			html += '</td>';
			
			html += '<td>'
			html += v.rate;
			html += '</td>';
				
			html +='<td class="nobor">&nbsp;</td>';
			html += '</tr>';
			
			$('#yktTableTbodyView').append(html);
		});
		$('#yktinfoViewSpan').html(selected);
		
	}
}


/*校验金额*/
//function clearNoNumOnBlur(obj) { 
//	var rate = obj.val();
//	rate = rate.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
//	rate = rate.replace(/^\./g,"");  //验证第一个字符是数字而不是.  
//	rate = rate.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的  
//	rate = rate.replace(".","$#$").replace(/\./g,"").replace("$#$",".");  
//	obj.val(rate.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'));//只能输入两个小数  
//	var text = obj.val();
//	if (text.indexOf(".") < 0) {
//		var textChar = text.charAt(text.length - 1);
//		if (text.length >= 5  && textChar !=".") {
//			obj.val(parseFloat(text.substring(0,4)));  
//		}
//	} else {
//		var text01 = text.substring(0,text.indexOf("."));
//		var text02 = text.substring(text.indexOf("."),text.length);
//		if (text01.length > 4) {
//			text01 = text01.substring(0,text01.length-1);
//		}
//		var text = text01+text02;
//		if (text.length > 7) {
//			text = "";
//		} else {
//			var textChar = text.charAt(text.length - 1);
//			if (textChar ==".") {
//				text = text.substring(0,text.length-1);  
//			}
//		}
//		obj.val(text);
//	}
//}  
//
//function checkDecimal(obj, startWhole, endWhole, startDec, endDec) {
//	var rate = obj.val();
//	var re;
//	var posNeg;
//		re = new RegExp("^[+]?\\d{" + startWhole + "," + endWhole + "}(\\.\\d{"
//				+ startDec + "," + endDec + "})?$");
//		posNeg = /^[+]?]*$/;
//	
//	if (!re.test(rate) && !posNeg.test(rate)) {
//		rate = "";
//		obj.focus();
//		return false;
//	}
//}

function clearNoNum(obj) { 
	var rate = obj.val();
	rate = rate.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
	rate = rate.replace(/^\./g,"");  //验证第一个字符是数字而不是.  
	rate = rate.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的  
	rate = rate.replace(".","$#$").replace(/\./g,"").replace("$#$",".");  
	obj.val(rate.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'));//只能输入两个小数  
	var text = obj.val();
//	if (text.indexOf(".") < 0) {
//		var textChar = text.charAt(text.length - 1);
//		if (text.length >= 5  && textChar !=".") {
//			obj.val(parseFloat(text.substring(0,4)));  
//		}
//	} else {
//		var text01 = text.substring(0,text.indexOf("."));
//		var text02 = text.substring(text.indexOf("."),text.length);
//		if (text01.length > 4) {
//			text01 = text01.substring(0,text01.length-1);
//		}
//		var text = text01+text02;
//		if (text.length > 7) {
//			text = "";
//		}
//		obj.val(text);
//	}
}

//初始化加载交易状态
function findTranOutStatus() {
	$.ajax({
				async : false,
				type : 'post',
				url : 'findTranOutStatus',
				dataType : "json",
				success : function(data) {
					$.each(data, function(key, value) {
								$('#tranOutStatus').append($("<option/>", {
											value : value.code,
											text : value.name
										}));
							});

				}
			});
}