$(function() {
	highlightTitle();
	initMoney();
});

function initMoney() {
	$("#collectionsMoney").bind('keyup', function() {
		clearNoNum($(this));
	});
	$("#collectionsMoney").bind('keydown', function() {
		checkDecimal($(this), 1, 9, 0, 2);
	});
	$("#collectionsMoney").bind('blur', function() {
		clearNoNumOnBlur($(this));
	});
	$("#collectionsMoney").focus(function(){
		var money = $('#collectionsMoney').val();
	    if(money=='0.00'|| money=='0' || money=='0.0'|| money==null || money==''){
	    	$('#collectionsMoney').val('');
	    }
	})
	
	$("#paymentMoney").bind('keyup', function() {
		clearNoNum($(this));
	});
	$("#paymentMoney").bind('keydown', function() {
		checkDecimal($(this), 1, 9, 0, 2);
	});
	$("#paymentMoney").bind('blur', function() {
		clearNoNumOnBlur($(this));
	});
	$("#paymentMoney").focus(function(){
		var money = $('#paymentMoney').val();
	    if(money=='0.00'|| money=='0' || money=='0.0'|| money==null || money==''){
	    	$('#paymentMoney').val('');
	    }
	})
}

// 转账 
function findDirectTransfer(merName,businessType){
		ddpAjaxCall({
			url : "findDirectTransferType?businessType="+businessType,
			data : merName,
			successFn : function(data) {
				var html = '';
				if (data.code == "000000") {
					$('#transferPage tbody').empty();
					if(data.responseEntity!=null && data.responseEntity.length > 0){
						$(data.responseEntity).each(function(i,v){
							html += '<tr>';
							html+='<td class="taLeft">';
							html+='<label><input type="checkbox" id="'+v.merCode+'" name="'+v.merName+'" value="'+v.merType+'" />'+v.merName+'</label>';
							html+='</td>';
							html+='<td>'+v.merMoney+'</td>';
							html+='</tr>';
						})
						$('#transferPage').append(html);
					}
				}else{
					$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
					}
			}
		})
}

//提取额度 
function findDirectExtraction(merName,businessType){
	ddpAjaxCall({
		url : "findDirectTransferType?businessType="+businessType,
		data :merName,
		successFn : function(data) {
			var html = '';
			if (data.code == "000000") {
				$('#extractionPage tbody').empty();
				if(data.responseEntity!=null && data.responseEntity.length > 0){
					$(data.responseEntity).each(function(i,v){
						html += '<tr>';
						html+='<td class="taLeft">'
						html+='<label><input type="radio" name="rad01" id="'+v.merCode+'" value="'+v.merType+'" title="'+v.merName+'"/>'+v.merName+'</label>';
						html+='</td>';
						html+='<td>'+v.merMoney+'</td>';
						html+='</tr>';
					})
					$('#extractionPage').append(html);
				}
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
				}
		}
	})
}

function queryTransfer(){
	var transName = $("#transName").val();
	var transferType = $("#transferType").val();
	var flag = $("#flag").val();
	if(parseInt(flag) == 1){
		findDirectTransferFilter(transName,transferType);
	}else{
		findDirectTransfer(transName,transferType);
	}
}

//模糊查询 转账 的直营网点
function queryTransferPage(type,typeName,flag){
	$("#transDiv").html(typeName);
	$('.select-r ul').hide();
	var merName = escapePeculiar($.trim($('#transName').val()));
	$("#flag").val(flag);
	if(parseInt(flag) == 1){
		findDirectTransferFilter(transName,type);
	}else{//获取直营网点信息
		findDirectTransfer(merName,type);
	}
	$("#transferType").val(type);
}

function queryExtraction(){
	var extractionName = $("#extractionName").val();
	var extractionType = $("#extractionType").val();
	findDirectExtraction(extractionName,extractionType);
}

//模糊查询 提取额度 的直营网点
function queryExtractionPage(businessType,typeName){
	$("#extractionDiv").html(typeName);
	$('.select-r ul').hide();
	var merName = escapePeculiar($.trim($('#extractionName').val()));
	//var businessType = escapePeculiar($.trim($('#businessType').val()));
	findDirectExtraction(merName,businessType);
	$("#extractionType").val(businessType);
}


//转账
function accountTransfer(obj){
	//$('#accountTransferTrue').removeAttr('href');
	//$('#accountTransferTrue').removeAttr('onclick');
	$(obj).closest('.pop-win').hide();
	
	var directMer= transferMerAr;
	var collectionsMoney = $('#collectionsMoney').val();
	var collectionsComments = $('#collectionsComments').val();
	var transferType = $("#transferType").val();
	
	var portalTransfer = {
			sourceCusCode:'',
			sourceCustType:'',
			directMer:directMer,
			money:collectionsMoney,
			comment:collectionsComments,
			transferFlag:'0',
			businessType:'',
			createUser:'',
			userCode:'',
			merCode:''
	}
	Transfer(portalTransfer);
	/*if(parseInt(transferType) == 13){//直营网点
	}
	if(parseInt(transferType) == 14){//加盟网点
		//判断加盟网点是否允许自动转账
	}
	*/
}


//转账or 提取额度 调用ajax
function Transfer(portalTransfer){
	ddpAjaxCall({
		url : "accountTransfer",
		data : portalTransfer,
		successFn : function(data) {
			var html = '';
			if (data.code == "000000") {
				if(data.responseEntity){
					clearInput();
					findDirectTransfer('');
					findDirectExtraction('');
					var windowReload=function(){
						   window.location.reload();
						}
					$.messagerBox({type:'warn', title:"信息提示", msg: "转账成功",confirmOnClick: windowReload});
					//$.messagerBox({type: "warn", title:"信息提示", msg: "转账成功"});
				}
			  //console.log(data.responseEntity);
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	})
}


/*
 *Time:2016-04-14
 *Name:Joe
 *Title:在提取加盟、直营网店额度时，都需要下级确认  提取加盟网点额度    or  连锁直营网点额度  
 */
function transFerLeague(portalTransfer){
	ddpAjaxCall({
		url : "transFerLeague",
		data : portalTransfer,
		successFn : function(data) {
			var html = '';
			var windowReload=function(){
				window.location.reload();
			}
			if (data.code == "000000") {
					clearInput();
					findDirectTransfer('');
					findDirectExtraction('');
					$.messagerBox({type:'warn', title:"信息提示", msg: data.message,confirmOnClick: windowReload});
			}else{
				$.messagerBox({type:'warn', title:"信息提示", msg: data.message,confirmOnClick: windowReload});
			}
		}
	})
}




//提取额度 (转账)
function ExtractionTransfer(obj){
	//$('#transferTrue').removeAttr('href');
	//$('#transferTrue').removeAttr('onclick');
	
	$(obj).closest('.pop-win').hide();
	
	var directMer= extractionMerAr;
	var paymentMoney = $('#paymentMoney').val();
	var paymentComments = $('#paymentComments').val();
	
	var extractionType = $("#extractionType").val();
	
	if(parseInt(extractionType) == 14){
		paymentMoney = paymentMoney *100;
	}
	
	if(parseInt(extractionType) == 13){
		paymentMoney = paymentMoney *100;
	}
	
	var portalTransfer = {
			sourceCusCode:'',
			sourceCustType:'',
			directMer:directMer,
			money:paymentMoney,
			comment:paymentComments,
			transferFlag:'1',
			businessType:'',
			createUser:'',
			userCode:'',
			merCode:''
	}
	
	
	if(parseInt(extractionType) == 13){//直营网点
		//Transfer(portalTransfer);
		transFerLeague(portalTransfer);
	}
	if(parseInt(extractionType) == 14){//加盟网点
		//DOTO 需要调用   给额度提取表新增一条记录
		transFerLeague(portalTransfer);
	}
}

//转账的详细页面
function createTransferDetail(){
	var html ='';
	$('#transferDetail tbody').empty();
	  var collectionsMoney = $('#collectionsMoney').val();
	if(isNotBlank(transferMerAr)){
		$(transferMerAr).each(function(i,v){
			html = '<tr>';
			html+='<td>'
			html+=v.merName;
			html+='</td>';
			html+='<td>'+collectionsMoney+'</td>';
			html+='</tr>';
			$('#transferDetail').append(html);
		})
		
	}
}

//提取额度的详细页面
function createExtractionDetail(){
	var html ='';
	$('#extractionDetail ').empty();
	var paymentComments='';
	var paymentMoney = $('#paymentMoney').val();
	var comments = $('#paymentComments').val();

	if(comments.length>20){
		paymentComments=comments.substring(0,20)+'...';
	}else{
		paymentComments=comments;
	}
	
	if(isNotBlank(extractionMerAr)){
		$(extractionMerAr).each(function(i,v){
			html ='<dt>付款方：</dt>';
			html+='<dd>';
			html+= v.merName;
			html+='</dt>';
			html+='<dt>提取金额：</dt>';
			html+='<dd>'+paymentMoney+'元</dd>';
			html+='<dt>备注：</dt>';
			html+='<dd style="word-break:break-all" title="'+htmlTagFormat(comments)+'">';
			html+=htmlTagFormat(paymentComments);
			html+='</dd>';
			$('#extractionDetail').append(html);
		})
		
	}
}

//清空输入框
function clearInput(){
	$('#collections').val('');
	$('#collectionsMoney').val('');
    $('#collectionsComments').val('');
    $('#payment').val('');
	$('#paymentMoney').val('');
	$('#paymentComments').val('');
}

//关闭窗口
function popclo(obj){
	$(obj).closest('.pop-win').hide();
}

//提取额度选择直营网点
function extractionPopclo(obj){
  var inputName = '';
  extractionMerAr = new Array();
  
  var tbodyObj = $("#extractionPage");
  $("table :radio").each(function(key,value){
     if($(value).prop('checked')){
     var merCode = $(value).prop('id');
     var merType = $(value).val();
     var merName = $(value).prop('title');
     inputName = merName;
     var extractionMap = {"merType":merType,"merCode":merCode,"merName":merName};
     extractionMerAr.push(extractionMap);
     }
   })
   if(inputName.length<=0){
	   $.messagerBox({type: 'warn', title:"信息提示", msg: "请选择直营网点"});
	   return;
   }else{
	   $(obj).closest('.pop-win').hide();
	   $('#payment').val(inputName);
   }
   

   //alert(JSON.stringify(extractionMerAr));
}



//分配额度选择直营网点
function transferPopclo(obj){
  var inputName = '';
  transferMerAr = new Array();
  var tbodyObj = $("#transferPage");
  $("table :checkbox").each(function(key,value){
     if($(value).prop('checked')){
    	 var merCode = $(value).prop('id');
    	 var merType = $(value).val();
    	 var merName = $(value).prop('name');
    	 if(merName == ""){
    		 inputName = '';
    	 }else{
    		 inputName += merName +',';
    		 var transferMap = {merType:merType,merCode:merCode,merName:merName};
    		 transferMerAr.push(transferMap);
    	 }
     }
   })
   if(inputName.length>0){
   inputName = inputName.substring(0,inputName.length-1);
   $(obj).closest('.pop-win').hide();
   $('#collections').val(inputName);
   }else{
	   $.messagerBox({type: 'warn', title:"信息提示", msg: "请选择直营网点"});
	   return;
   }
 
   
}

function checkAll(){
	    if($("#checkAll").prop("checked")){ 
	        $("input[type='checkbox']").prop("checked", true);
	    }else{    
	        $("input[type='checkbox']").prop("checked", false); 
	    }    
}

/* 以下为校验费率js */
function clearNoNumOnBlur(obj) {
	var rate = obj.val();
	rate = rate.replace(/[^\d.]/g, ""); // 清除“数字”和“.”以外的字符
	rate = rate.replace(/^\./g, ""); // 验证第一个字符是数字而不是.
	rate = rate.replace(/\.{2,}/g, "."); // 只保留第一个. 清除多余的
	rate = rate.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
	obj.val(rate.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));// 只能输入两个小数
	var text = obj.val();
	if (text.indexOf(".") < 0) {
		if(text.length >= 2 ){
		    var textchar0 = text.charAt(0);
		    var textchar1 = text.charAt(1);
		    if(textchar0=='0'&& textchar1=='0'){
		    	obj.val(parseFloat("0"));
		    }
		    if(textchar0=='0'&& textchar1!='0'){
		    	obj.val(parseFloat(textchar1));
		    }
		}
		var textChar = text.charAt(text.length - 1);
		if (text.length >= 9 && textChar != ".") {
			obj.val(parseFloat(text.substring(0, 8)));
		}
	} else {
		var text01 = text.substring(0, text.indexOf("."));
		var text02 = text.substring(text.indexOf("."), text.length);
		if(text01.length >= 2 ){
		    var textchar0 = text01.charAt(0);
		    var textchar1 = text01.charAt(1);
		    if(textchar0=='0'&& textchar1=='0'){
		    	text01='0';
		    }
		    if(textchar0=='0'&& textchar1!='0'){
		    	text01=textchar1;
		    }
		}
		if (text01.length > 8) {
			text01 = text01.substring(0, text01.length - 1);
		}
		var text = text01 + text02;
		if (text.length > 11) {
			text = "";
		} else {
			var textChar = text.charAt(text.length - 1);
			if (textChar == ".") {
				text = text.substring(0, text.length - 1);
			}
		}
		obj.val(text);
	}
}

function checkDecimal(obj, startWhole, endWhole, startDec, endDec) {
	var rate = obj.val();
	var re;
	var posNeg;
	re = new RegExp("^[+]?\\d{" + startWhole + "," + endWhole + "}(\\.\\d{"
			+ startDec + "," + endDec + "})?$");
	posNeg = /^[+]?]*$/;

	if (!re.test(rate) && !posNeg.test(rate)) {
		rate = "";
		obj.focus();
		return false;
	}
}

function clearNoNum(obj) {
	var rate = obj.val();
	rate = rate.replace(/[^\d.]/g, ""); // 清除“数字”和“.”以外的字符
	rate = rate.replace(/^\./g, ""); // 验证第一个字符是数字而不是.
	rate = rate.replace(/\.{2,}/g, "."); // 只保留第一个. 清除多余的
	rate = rate.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
	obj.val(rate.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));// 只能输入两个小数
	var text = obj.val();
	if (text.indexOf(".") < 0) {
		if(text.length >= 2 ){
		    var textchar0 = text.charAt(0);
		    var textchar1 = text.charAt(1);
		    if(textchar0=='0'&& textchar1=='0'){
		    	obj.val(parseFloat("0"));
		    }
		    if(textchar0=='0'&& textchar1!='0'){
		    	obj.val(parseFloat(textchar1));
		    }
		}
		var textChar = text.charAt(text.length - 1);
		if (text.length >= 8 && textChar != ".") {
			obj.val(parseFloat(text.substring(0, 8)));
		}
	} else {
		var text01 = text.substring(0, text.indexOf("."));
		var text02 = text.substring(text.indexOf("."), text.length);
		if(text01.length >= 2 ){
		    var textchar0 = text01.charAt(0);
		    var textchar1 = text01.charAt(1);
		    if(textchar0=='0'&& textchar1=='0'){
		    	text01='0';
		    }
		    if(textchar0=='0'&& textchar1!='0'){
		    	text01=textchar1;
		    }
		}
		if (text01.length > 8) {
			text01 = text01.substring(0, text01.length - 1);
		}
		var text = text01 + text02;
		if (text.length > 11) {
			text = "";
		}
		obj.val(text);
	}
}

function findDirectTransferFilter(merName,businessType){
	ddpAjaxCall({
		url : "findDirectTransferFilter?businessType="+businessType,
		data :merName,
		successFn : function(data) {
			var html = '';
			if (data.code == "000000") {
				$('#transferPage tbody').empty();
				if(data.responseEntity!=null && data.responseEntity.length > 0){
					$(data.responseEntity).each(function(i,v){
						html += '<tr>';
						html+='<td class="taLeft">';
						html+='<label><input type="checkbox" id="'+v.merCode+'" name="'+v.merName+'" value="'+v.merType+'" />'+v.merName+'</label>';
						html+='</td>';
						html+='<td>'+v.merMoney+'</td>';
						html+='</tr>';
					})
					
					$('#transferPage').append(html);
				}
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
				}
		}
	})
}
