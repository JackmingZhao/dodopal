$(function(){
	findChildMerchant();
	$('.page-navi').paginator({prefix:'childMerchants',pageSizeOnChange:findChildMerchant});
	highlightTitle();
	createAreaSelectWithSelectId("province","cityce");
	$("#xuanzhe").bind("click",function(){
		yktInfo();
	});
	$("#xuanzheNotVer").bind("click",function(){
		notVer_yktName();
	});
	checkFindProductList();
	//初始化数据字典
	childMerchantDDIC();
	
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="子商户管理"){
			$(this).addClass('cur');
		}
	});

});

//初始化加载子商户中的数据字典
function childMerchantDDIC(){
	//子商户开户中的经营范围
	createDdicSelectWithSelectId('merBusinessScopeId','BUSINESS_SCOPE');
	//子商户开户中的证件类型
	createDdicSelectWithSelectId('merUserIdentityType','IDENTITY_TYPE');
	
	//子商户审核通过编辑中的经营范围
	createDdicSelectWithSelectId('merBusinessScopeIdVer','BUSINESS_SCOPE');
	//子商户审核通过编辑中的证件类型
	createDdicSelectWithSelectId('merUserIdentityTypeVer','IDENTITY_TYPE');
	
	
	//子商户审核不通过编辑中的经营范围
	createDdicSelectWithSelectId('merBusinessScopeIdNotVer','BUSINESS_SCOPE');
	//子商户审核不通过编辑中的证件类型
	createDdicSelectWithSelectId('merUserIdentityTypeNotVer','IDENTITY_TYPE');
}



//初始化加载商户费率
function checkFindProductList(){
		$.ajax({
			async : true,
			dataType : "json",
			type : 'post',
			url : "findProductList",
			success : function(data) {
				findProductList(data);
			}
		});
}
function findProductList(data){
	if (data.code == "000000") {
		if(data.responseEntity!=null&&data.responseEntity.length!=0){
			$('#yktLine').show();
			$('#notYktLine').show();
		}else{
			$('#yktLine').hide();
			$('#notYktLine').hide();
		}
	}
}
//---------------------------------------------初始化加载子商户信息开始----------------------------------
//查询子商户信息
function findChildMerchant(page){
	var merPro = getProvinceValue('province');
	var merCity = getCityValue('cityce');
	var activate = $('#activateQuery').val();
	if(!page){
		page = {pageNo:1,pageSize:10};
	}
	var queryChildBean ={ 
			merName:escapePeculiar($.trim($('#merNameQuery').val())),
			merLinkUser:escapePeculiar($.trim($('#merLinkUserQuery').val())),
			activate:activate,
			merPro:merPro,
			merCity:merCity,
			page:page
	}
	ddpAjaxCall({
		url : "childFindMerchantType",
		data : queryChildBean,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				afterFind();
				$('#childMerchantTbl tbody').empty();
				var i = 1;
				if( data.responseEntity.records &&  data.responseEntity.records.length > 0){
					$(data.responseEntity.records).each(function(i,v){
						i = i+1;
						$('.null-box').hide();
						html = '<tr>';
						html +='<td class="nobor">&nbsp;</td>';
						
					
						
						html += '<td class="a-center">';
						html += '<input id="'+v.merCode+'" name="childMerchants" type="checkbox" onclick="toggleActivateBtn(\'childMerchants\')"/>'
						html += '</td>';
						
						
						html += '<td>';
						html += i++ + '';
						html += '</td>';
						
//						html += '<td style="word-break:break-all" >'
//						html += v.merCode;
//						html += '</td>';
						
						html += '<td style="word-break:break-all" >'
						html += v.merName;
						html += '</td>';
						
						html += '<td style="word-break:break-all" >'
						html += v.merType == null ? '' : findmerType(v.merType);
						html += '</td>';
						
						html += '<td style="word-break:break-all" >'
						html += v.merLinkUser;
						html += '</td>';
							
						html += '<td style="word-break:break-all" >'
						html += v.merLinkUserMobile;
						html += '</td>';
						
//						html += '<td style="word-break:break-all" >'
//						html += v.merProName;
//						html += '</td>';
//								
//						html += '<td style="word-break:break-all" >'
//						html += v.merCityName;
//						html += '</td>';
							
						html += '<td style="word-break:break-all" >'
						html += v.merState == null ? '' : findmerState(v.merState);
						html += '</td>';
									
						html += '<td style="word-break:break-all" >'
						html += v.activate == '0'? '启用' :'停用';
						html += '</td>';
								
						html += '<td style="word-break:break-all" >'
						html += v.createDate == null ? '' : formatDate(v.createDate,'yyyy-MM-dd HH:mm:ss');
						html += '</td>';
						
						html += '<td class="a-center">'
						if(hasPermission('merchant.child.info.modify')){
						html += '<a href="javascript:void(0);" class="edit-icon" title="编辑子商户" onclick="editChildMerchant(\''+v.merCode+'\');"></a>';
						}
						if(hasPermission('merchant.child.info.view')){
						html += '<a href="javascript:void(0);" class="text-icon" title="详情" onclick="viewChildMerchant(\''+v.merCode+'\');"></a>';
						}
						html += '</td>';
						
						html +='<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#childMerchantTbl').append(html);
					});
					$('.com-table01 tbody tr:even').find('td').css("background-color",'#f6fafe');
					$('.com-table02 tbody tr:even').find('td').css("background-color",'#f6fafe');	
				}
				else{
					$('.null-box').show();
				}
				var pageSize = data.responseEntity.pageSize;
				var pageNo = data.responseEntity.pageNo;
				var totalPages = data.responseEntity.totalPages;
				var rows = data.responseEntity.rows;
				$('.page-navi').paginator('setPage',pageNo,pageSize,totalPages,rows);
				
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}	
			
		}
	});
}
function afterFind(){
	$("#allChild").attr('checked',false);
	disableOrEnableBtn(true,'#qiyong1');
	disableOrEnableBtn(true,'#tingyong1');
}

//清空子商户信息
function clearChildMerChant(selector){
	$('#' + selector)[0].reset();
	resetArea('province','cityce');
}
//---------------------------------------------初始化加载子商户信息结束----------------------------------
//---------------------------------------------子商户所有类型CODE转换开始--------------------------------
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
//---------------------------------------------子商户所有类型CODE转换结束--------------------------------
//---------------------------------------------子商户查看详情开始---------------------------------------
//查看子商户详情
function viewChildMerchant(merCode){
	ddpAjaxCall({
		url : "childfindMerchantByCode",
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
//清空子商户详情界面界面信息
function clearChildMerchantView(){
	$('#childMerchantViewTable tr td span').html('');
	//清空查看子商户详情界面上的通卡公司费率
	$('#yktTable tbody').empty();
	$('#yktTableTbodyView').html('');
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
	openChildMerchantView(4);
}
//加载通卡公司费率
function loadYkt(merBusRateBeanList) {
	var selected = '';
	if(merBusRateBeanList && merBusRateBeanList.length > 0){
		$(merBusRateBeanList).each(function(i,v){
			if(selected != '') {
				selected += ',';
			}
			selected += v.proName;
			
			$('.null-box').hide();
			html = '<tr>';
			html +='<td class="nobor">&nbsp;</td>';
			
			html += '<td>'
			html += v.proName;
			html += '</td>';
			
			html += '<td>'
			html += v.rateName;
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
//---------------------------------------------子商户查看详情结束-------------------------------------
//----------------------------------------------子商户停启用开始--------------------------------------
//停启用按钮样式选择
function toggleActivateBtn(cbName){
	var ids = [];
	$('input[name='+cbName+']').each(function(i,v){
		if($(v).is(':checked')){
			ids.push(v);
		}
	});
	
	if(ids.length > 0){
		disableOrEnableBtn(false,'#qiyong1');
		disableOrEnableBtn(false,'#tingyong1');
	}else{
		disableOrEnableBtn(true,'#qiyong1');
		disableOrEnableBtn(true,'#tingyong1');
	}
	
}
//停启用全选和单选的样式处理
function childCheckAll(source,cbName){
	 toggle(source,'childMerchants');
	 var qiyong = "#qiyong1";
	 var tingyong = "#tingyong1";
	 var qiyonghref = $(qiyong).attr("href");
	 var tingyonghref = $(tingyong).attr("href");
	 if(qiyonghref!="")
		$(qiyong).attr("myhref",qiyonghref);
	 if(tingyonghref!="")
		$(tingyong).attr("myhref",tingyonghref);
	 if(source.checked&&$('input[name=childMerchants]').length>0){
		 disableOrEnableBtn(false,'#qiyong1');
		 disableOrEnableBtn(false,'#tingyong1');
	 }else{
		 disableOrEnableBtn(true,'#qiyong1');
		 disableOrEnableBtn(true,'#tingyong1');
	 }
}


//停启用弹出框样式选择
function startOrStopChildMer(flag){
	var ids = [];
	$('input[name="childMerchants"]:checked').each(function(i,v){
		if($(v).is(':checked')){
			ids.push(v);
		}
	});
	if(ids.length == 0){
		return;
	}
	if(flag == true){
		$.messagerBox({type:"confirm", title:"确认", msg: "您确认启用？", confirmOnClick: childStartAndStop, param:true});
	}else{
		$.messagerBox({type:"confirm", title:"确认", msg: "您确认停用？", confirmOnClick: childStartAndStop, param:false});
	}
}

//停启用后台选择
function childStartAndStop(flag){
	var merCodes = [];
	$('input[name=childMerchants]').each(function(i,v){
		if($(v).is(':checked')){
			merCodes.push($(v).attr('id'));
		}
	});
	if(flag == true){
		ddpAjaxCall({
			url : "startAndDisableChildMerchant?activate=0",
			data : merCodes,
			successFn : function(data) {
				if (data.code == "000000") {
					findChildMerchant($('.page-navi').paginator('getPage'));
					$('#qiyong').hide();
					toggleActivateBtn('qiyong');
				}else{
					$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
				}
			}
		});
	}else{
		ddpAjaxCall({
			url : "startAndDisableChildMerchant?activate=1",
			data : merCodes,
			successFn : function(data) {
				if (data.code == "000000") {
					findChildMerchant($('.page-navi').paginator('getPage'));
					$('#tingyong').hide();
					toggleActivateBtn('tingyong');
				}else{
					$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
				}
			}
		});
	}
}
//----------------------------------------------子商户停启用结束--------------------------------------

//---------------------------------------------子商户信息编辑开始-------------------------------------
//审核和审核不通过打开子商户编辑界面
function editChildMerchant(merCode){
	ddpAjaxCall({
		url : "childfindMerchantByCode",
		data : merCode,
		successFn : function(data) {
			if (data.code == "000000") {
				openEditChildMerchant(data.responseEntity)
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
	
	
}

//审核和未审核打开子商户编辑界面
function openEditChildMerchant(data){
	if(data.merState=='0'){
		$.messagerBox({type: 'warn', title:"信息提示", msg: "未审核状态无法编辑，请您静心等待审核结果!"});
		return;
	}
	if(data.merState=='1'){
		loadChildMerchantVerEdit(data);
	}
	//审核不通过
	if(data.merState=='2'){
		createAreaSelectWithSelectId("provinceNotVer","cityNotVer");
		loadChildMerchantNotVerEdit(data);
	}
}
//---------------------------------------------子商户信息编辑结束-------------------------------------
//---------------------------------------------界面加载，取消，返回顺序开始--------------------------
//0:主界面。1:添加界面。2:已审核编辑界面。3:审核不通过编辑界面。4:查看子商户详情界面
//打开界面
function openChildMerchantView(flag){
	$(".error").html("");
	$(".tip-error").html("");
	$(".tip-red-error").html("");
	if(flag=="0"){
		$('#addChildMerchantForm')[0].reset();
		$('#childMerchantVerEditForm')[0].reset();
		$('#childMerchantNotVerForm')[0].reset();
		$('#childMerchantDetail').hide();
		$('#childMerchantDetailView').hide();
		$('#childMerchantEdit').hide();
		$('#childMerchantNotVerEdit').hide();
		$('#yktTable').hide();
		$('#yktTableVer').hide();
	}else if(flag=="1"){
		$('#yktRateTypeTable tbody').empty();
		$('#rateTypeView').hide();
		createAreaSelectWithSelectId("provinceAdd","cityAdd");
		$("input[name='activate']").attr("disabled",true);
		$('#addChildMerchantForm')[0].reset();
		$('#childMerchantVerEditForm')[0].reset();
		$('#childMerchantNotVerForm')[0].reset();
		$('#childMerchantMain').hide();
		$('#childMerchantDetail').show();
		$('#childMerchantDetailView').hide();
		$('#childMerchantEdit').hide();
		$('#childMerchantNotVerEdit').hide();
	
	}else if(flag=="2"){
		$('#addChildMerchantForm')[0].reset();
		$('#childMerchantVerEditForm')[0].reset();
		$('#childMerchantNotVerForm')[0].reset();
		$('#childMerchantMain').hide();
		$('#childMerchantDetail').hide();
		$('#childMerchantDetailView').hide();
		$('#childMerchantNotVerEdit').hide();
		
	}else if(flag=="3"){
		createAreaSelectWithSelectId("provinceNotVer","cityNotVer");
		$("input[name='activateNotVer']").attr("disabled",true);
		$('#addChildMerchantForm')[0].reset();
		$('#childMerchantVerEditForm')[0].reset();
		$('#childMerchantNotVerForm')[0].reset();
		$('#childMerchantMain').hide();
		$('#childMerchantDetail').hide();
		$('#childMerchantDetailView').hide();
		$('#childMerchantEdit').hide();
		$('#yktTable').hide();
		$('#yktTableVer').hide();
	}else if(flag=="4"){
		$('#addChildMerchantForm')[0].reset();
		$('#childMerchantVerEditForm')[0].reset();
		$('#childMerchantNotVerForm')[0].reset();
		$('#childMerchantMain').hide();
		$('#childMerchantDetail').hide();
		$('#childMerchantDetailView').show();
		$('#childMerchantEdit').hide();
		$('#childMerchantNotVerEdit').hide();
		$('#yktTable').show();
		$('#yktTableVer').hide();
	}
}
//取消或者返回
function clearChileMerchantView(domainName){
	var callbackFn = function(){
		$('#addChildMerchantForm')[0].reset();
		$('#childMerchantVerEditForm')[0].reset();
		$('#childMerchantNotVerForm')[0].reset();
		$('#'+domainName+'Main').show();
		$('#'+domainName+'Detail').hide();
		$('#'+domainName+'DetailView').hide();
		$('#'+domainName+'Edit').hide();
		$('#'+domainName+'NotVerEdit').hide();
	}
	$.messagerBox({type:"confirm", title:"确认", msg: "确认要取消当前修改？", confirmOnClick: callbackFn});
}

//查看界面返回
function clearView(domainName){
		$('#addChildMerchantForm')[0].reset();
		$('#childMerchantVerEditForm')[0].reset();
		$('#childMerchantNotVerForm')[0].reset();
		$('#'+domainName+'Main').show();
		$('#'+domainName+'Detail').hide();
		$('#'+domainName+'DetailView').hide();
		$('#'+domainName+'Edit').hide();
		$('#'+domainName+'NotVerEdit').hide();
}
//---------------------------------------------界面加载，取消，返回顺序结束--------------------------


