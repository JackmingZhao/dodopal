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
	
	//监听输入值是否为数字或者带小数点后两位的
	initIsNotNumber();
	
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="子商户管理"){
			$(this).addClass('cur');
		}
	});
	
	initEducationAndIsMarried();
	
});

//初始化添加、（审核通过）编辑、（审核不通过）编辑  页面 的学历 和婚姻状况字段
function initEducationAndIsMarried(){
	createDdicSelectWithSelectId("isMarried","IS_MARRIED_TYPE");
	createDdicSelectWithSelectId("education","EDUCATION_TYPE");
	
	createDdicSelectWithSelectId("merUserIsMarriedVer","IS_MARRIED_TYPE");
	createDdicSelectWithSelectId("merUserEducationVer","EDUCATION_TYPE");
	
	
	createDdicSelectWithSelectId("merUserIsMarriedNotVer","IS_MARRIED_TYPE");
	createDdicSelectWithSelectId("merUserEducationNotVer","EDUCATION_TYPE");
}



//监听输入值是否为数字或者带小数点后两位的
function initIsNotNumber(){
	//年收入
$("#income").bind('keyup', function() {
		notNumber($(this));
	});

//添加页面
//额度阀值
$("#threshold").bind('keyup', function() {
	clearAutoLimitThresholdNoNum($(this));
	});
//自动分配额度			
$("#limit").bind('keyup', function() {
		clearAutoLimitThresholdNoNum($(this));
	});

//编辑审核通过页面
//额度阀值
$("#thresholdEditVer").bind('keyup', function() {
	clearAutoLimitThresholdNoNum($(this));
	});
//自动分配额度			
$("#limitEditVer").bind('keyup', function() {
		clearAutoLimitThresholdNoNum($(this));
	});

//编辑审核不通过页面
//额度阀值
$("#thresholdEditNotVer").bind('keyup', function() {
	clearAutoLimitThresholdNoNum($(this));
	});
//自动分配额度			
$("#limitEditNotVer").bind('keyup', function() {
		clearAutoLimitThresholdNoNum($(this));
	});

}

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
	
	//子商户开户中的开户银行
	createDdicSelectWithSelectId('merBankName','MER_BANK');
	//子商户审核通过编辑中的开户银行
	createDdicSelectWithSelectId('merBankNameVer','MER_BANK');
	//子商户审核不通过编辑中的开户银行
	createDdicSelectWithSelectId('merBankNameNotVer','MER_BANK');
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
						html += v.merTypeView == null ? '' :v.merTypeView;
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
						html += v.merStateView;
						html += '</td>';
									
						html += '<td style="word-break:break-all" >'
						html += v.activateView;
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
	var merRateSpmtList =data.merRateSpmtList;
	var merDdpInfoBean = data.merDdpInfoBean;
	var merAutoAmtBean = data.merAutoAmtBean;
	
	if(data.createDate){
		data.createDate = formatDate(data.createDate,'yyyy-MM-dd HH:mm:ss');
	}
	
	
	if(merDdpInfoBean==null){
		$('#isAutoViewShow').hide();
		$('#thresholdViewShow').hide();
		$('#isAutoJoinViewShow').hide();
	}else{
		
	// 13 连锁商户直营网点
	//if(data.merType=='13'){
		if(merDdpInfoBean.isAutoDistribute=='0'){
			$('#isAutoViewShow').show();
			$('#thresholdViewShow').show();
			$('#isAutoSpan').html("是");
			if(merAutoAmtBean==null){
				$('#thresholdSpan').html('');
				$('#limitSpan').html('');
			}else{
				$('#thresholdSpan').html(merAutoAmtBean.limitThreshold==null?'':merAutoAmtBean.limitThreshold +'元');
				$('#limitSpan').html(merAutoAmtBean.autoLimitThreshold==null?'':merAutoAmtBean.autoLimitThreshold +'元');
			}
			$('#isAutoJoinViewShow').hide();
		}
		if(merDdpInfoBean.isAutoDistribute=='1'){
			$('#isAutoViewShow').show();
			$('#isAutoSpan').html("否");
			$('#thresholdViewShow').hide();
			$('#isAutoJoinViewShow').hide();
		}
		if(merDdpInfoBean.isAutoDistribute=='2'){
			$('#isAutoViewShow').show();
			$('#isAutoSpan').html("共享额度");
			$('#thresholdViewShow').hide();
			$('#isAutoJoinViewShow').hide();
		}

		
//	}
//	//连锁商户加盟网点
//	if(data.merType=='14'){
//		$('#isAutoViewShow').hide();
//		$('#thresholdViewShow').hide();
//		$('#isAutoJoinViewShow').show();
//		if(merDdpInfoBean.limitSource=='0'){
//			$('#isAutoJoinSpan').html("自己购买");
//		}else{
//			$('#isAutoJoinSpan').html("上级分配");
//		}
//		
//	}
	
	}
	//商户编码
	$('#merCodeSpan').val(data.merCode);
	//商户类型
	$('#merTypeSpan').html(data.merTypeView);
	//审核状态
	$('#merStateSpan').html(data.merStateView);
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
	//业务类型
	var rateCodeView ='';
		$(merRateSpmtList).each(function(i,v){
		if(rateCodeView != '') {
				rateCodeView += ',';
			}
			rateCodeView += v.rateCodeView;
		});
		$('#PrdRateTypeSpan').html(rateCodeView);
	//通卡公司
	if(merBusRateBeanList!=null && merBusRateBeanList!=''){
		loadYkt(merBusRateBeanList);
		$('#yktDIVView').show();
	}else{
		$('#yktDIVView').hide();
	}
	//经营范围getMerBusinessScopeIdView
	$('#merBusinessScopeIdSpan').html(data.merBusinessScopeIdView);
	//邮箱
	$('#merEmailSpan').html(data.merEmail);
	//固定电话
	$('#merTelephoneSpan').html(data.merTelephone);
	//传真
	$('#merFaxSpan').html(data.merFax);
	//证件类型MerUserIdentityTypeView
	$('#merUserIdentityTypeSpan').html(merchantUserBeans.merUserIdentityTypeView);
	//证件号码
	$('#merUserIdentityNumberSpan').html(merchantUserBeans.merUserIdentityNumber);
	//邮编
	$('#merZipSpan').html(data.merZip);
	//开户银行
	$('#merBankNameSpan').html(data.merBankNameView);
	//开户行账号
	$('#merBankAccountSpan').html(data.merBankAccount);
	//开户名称
	$('#merBankUserNameSpan').html(data.merBankUserName);
	//备注
	$('#merUserRemarkSpan').html(merchantUserBeans.merUserRemark);
	if(merchantUserBeans.income==null||merchantUserBeans.income==''){
		merchantUserBeans.income =='';
	}else{
		merchantUserBeans.income = Number(merchantUserBeans.income/100).toFixed(2);
	}
	//学历
	$('#merUserEducationSpan').html(merchantUserBeans.education==null ? '' :merchantUserBeans.educationView);
	//生日
	$('#merUserBirthdaySpan').html(merchantUserBeans.birthday==null? '' : merchantUserBeans.birthday);
	//收入
	$('#merUserIncomeSpan').html(merchantUserBeans.income);
	//婚姻状况
	$('#merUserMarStatusSpan').html(merchantUserBeans.isMarried == null ? '' :merchantUserBeans.isMarriedView);
	
	openChildMerchantView(4);
}
//加载通卡公司费率
function loadYkt(merBusRateBeanList) {
	var selected = '';
	var proNameArray = new Array();
	if(merBusRateBeanList && merBusRateBeanList.length > 0){
		$(merBusRateBeanList).each(function(i,v){
			var isFound = false;
			for(var i = 0;i<proNameArray.length;i++) {
				if(v.proName == proNameArray[i]) {
					isFound = true;
				}
			}
			if(!isFound) {
				if(selected != '') {
						selected += ',';
					}
				selected += v.proName;
				proNameArray.push(v.proName);
			}
			
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
			html += v.rateTypeView;
			html += '</td>';
			
			html += '<td>'
			html += v.rate;
			html += '</td>';
				
			html +='<td class="nobor">&nbsp;</td>';
			html += '</tr>';
			
			$('#yktTableTbodyView').append(html);
		});
		
	}
	$('#yktinfoViewSpan').html(selected);
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
		//新增子商户业务类型
		findPrdRateType();
		$("#PrdRateType").find("input[type=checkbox]").attr("checked", false);
		$("#PrdRateType").find("input[type=checkbox]").attr("disabled", false);
		$('#yktRateTypeTable tbody').empty();
		$('#rateTypeView').hide();
		
		$('#isAutoShow').hide();
		$('#thresholdShow').hide();
        //$('#isAutoJoinShow').hide();
        
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
		//审核不通过的与类型
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


