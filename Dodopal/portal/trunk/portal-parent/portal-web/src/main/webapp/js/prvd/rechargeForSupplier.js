$(function(){
	findrechargeForSupplier();
	highlightTitle();
	$('#rechargeForSupplier').paginator({
		prefix : 'supplierTb',
		pageSizeOnChange : findrechargeForSupplier
	});
	$('#rechargeForDetails').paginator({
		prefix : 'supplierDetailsTb',
		pageSizeOnChange : findrechargeForDetails
	});
	$('#rechargeForSupplierDetailsForm').hide();
//	$('#rechargeForSupplierForm').show();
});

function findrechargeForSupplier(page){
	if (!page) {
		page = {
			pageNo : 1,
			pageSize : 10
		};
	}
	var query ={
			proCode:$('#proCode').val(),
			bind:$('#bind').val(),
			merName:$('#merName').val(),
//			startDate:$('#startDate').val(),
//			endDate:$('#endDate').val(),
			page:page
	}
	ddpAjaxCall({
		url : "findRechargeForSupplier",
		data : query,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				afterFind("allChild");
				$('#supplierTb tbody').empty();
				if( data.responseEntity!=null &&  data.responseEntity.records.length > 0){
					$(data.responseEntity.records).each(function(i,v){
						$('.null-box').hide();
						html = '<tr>';
						html +='<td class="nobor">&nbsp;</td>';
						html += '<td class="a-center"><input type="checkbox" id="'+v.proCode+'"  name="rechargeForSuppliers"  onclick="toggleActivateBtn(\'rechargeForSuppliers\')"  /></td>'
						html += '<td>'+v.proCode+'</td>';
						html += '<td>'+v.bind+'</td>'
						html += '<td>'+v.rechargeCount+'</td>';
						html += '<td>'+fmoney(v.rechargeAmt,2)+'</td>';
						if(v.merName == null || v.merName ==""){
							html += '<td>'+""+'</td>';
						}else{
							html += '<td>'+v.merName+'</td>';
						}
						
						html += '<td class="a-center">'
						html += '<a href="#" onClick="querySumDetail(this);" class="text-icon mg0" title="充值详情"></a>';
						html += '</td>';
						
						html +='<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#supplierTb').append(html);
					});
					$('.com-table01 tbody tr:even').find('td').css("background-color",'#f6fafe');
					$('.com-table02 tbody tr:even').find('td').css("background-color",'#f6fafe');	
				}
				var pageSize = data.responseEntity.pageSize;
				var pageNo = data.responseEntity.pageNo;
				var totalPages = data.responseEntity.totalPages;
				var rows = data.responseEntity.rows;
				console.log("pageNo="+pageNo+"pageSize="+pageSize+"totalPages="+totalPages+"rows"+rows)
				$('#rechargeForSupplier').paginator('setPage',pageNo,pageSize,totalPages,rows);
				$('#rechargeForSupplier select').attr("style","width:45px;padding:0px 0px");
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
	
}

function exportExcel() {
	var data = {};
	data.proCode = escapePeculiar($.trim($('#proCode').val()));
	data.merName = escapePeculiar($.trim($('#merName').val()));
	data.bind = escapePeculiar($.trim($('#bind').val()));
	$.fileDownload('exportRechargeForSupplier', {
		data : data,
		failCallback : function(data) {
			var obj = JSON.parse(data);
			$.messagerBox({
				type : 'warn',
				title : "信息提示",
				msg : obj.message
			});
		}
	});
}

function startOrStopUser(flag){
	var ids = [];
	$('input[name=rechargeForSuppliers]').each(function(i,v){
		if($(v).is(':checked')){
			ids.push($(v).attr('id'));
		}
	});
	if(ids.length == 0){
		return;
	}
	
	if(flag == true){
		$.messagerBox({type:"confirm", title:"确认", msg: "您确认启用？", confirmOnClick: startOrStopUserConfirm, param:true});
	}else{
		$.messagerBox({type:"confirm", title:"确认", msg: "您确认停用？", confirmOnClick: startOrStopUserConfirm, param:false});
	}
}


function startOrStopUserConfirm(flag) {
	var ids = [];
	$('input[name=rechargeForSuppliers]').each(function(i,v){
		if($(v).is(':checked')){
			ids.push($(v).attr('id'));
		}
	});
	
	var activate = '';
	if(flag == true){
		activate = '0';
		ddpAjaxCall({
			url : "batchStartRecharge",
			data : ids,
			successFn : function(data) {
				if (data.code == "000000") {
					findrechargeForSupplier($('#rechargeForSupplier').paginator('getPage'));
					toggleActivateBtn('rechargeForSuppliers');
				}else{
					$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
				}
			}
		});
	}else{
		activate = '1';
		ddpAjaxCall({
			url : "batchStopRecharge",
			data : ids,
			successFn : function(data) {
				if (data.code == "000000") {
					findrechargeForSupplier($('#rechargeForSupplier').paginator('getPage'));
					toggleActivateBtn('rechargeForSuppliers');
				}else{
					$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
				}
			}
		});
	}
	
}

function compareTime(query,startDate,endDate){
	 var d1 = new Date(startDate.replace(/\-/g, "\/")); 
	 var d2 = new Date(endDate.replace(/\-/g, "\/")); 

	  if(startDate!=""&&endDate!=""&&d1 >=d2) 
	 { 
	  var temp = endDate;
	  endDate = startDate;
	  startDate = temp;
	  $('#startDate').val(startDate)
	  $('#endDate').val(endDate)
	  query.startDate = startDate;
	  query.endDate = endDate;
	 }
}

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


function clearRecharge(selector){
	$('#' + selector)[0].reset();
	selectUiInit();
}

//停用 启用
function toggleActivateBtn(cbName){
	var ids = [];
	$('input[name='+cbName+']').each(function(i,v){
		if($(v).is(':checked')){
			ids.push(v);
		}
	});
	
	if(ids.length > 0){
		disableOrEnableBtn(false,'#qiyong');
		disableOrEnableBtn(false,'#tingyong');
	}else{
		disableOrEnableBtn(true,'#qiyong');
		disableOrEnableBtn(true,'#tingyong');
	}
	
}

/**
 * 查询置灰
 * @param id
 */
function afterFind(id){
	$("#"+id).attr('checked',false);
	disableOrEnableBtn(true,'#qiyong');
	disableOrEnableBtn(true,'#tingyong');
}

function querySumDetail(obj){
//	alert($(obj).parent().parent().find("td").eq(2).text());
	var posCodeSpan =$(obj).parent().parent().find("td").eq(2).text();
	$('#posCodeSpan').html(posCodeSpan);
	$('#posCodeDetail').val(posCodeSpan);
	findrechargeForDetails();
	$('#rechargeForSupplierDetailsForm').show();
	$('#rechargeForSupplierForm').hide();
	
}

function findrechargeForDetails(page){
	if (!page) {
		page = {
			pageNo : 1,
			pageSize : 10
		};
	}
	var query ={
			posCode:$('#posCodeDetail').val(),
			merName:$('#merNameDetail').val(),
			proOrderNum:$('#proOrderNumDetail').val(),
			orderDateStart:$('#orderDateStart').val(),
			orderDateEnd:$('#orderDateEnd').val(),
			page:page
	}
	var orderDateStart = query.orderDateStart;
	var orderDateEnd = query.orderDateEnd;
	compareTimeDetails(query,orderDateStart,orderDateEnd);
	ddpAjaxCall({
		url : "findRechargeDetails",
		data : query,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#supplierDetailsTb tbody').empty();
				if( data.responseEntity!=null &&  data.responseEntity.records.length > 0){
					$(data.responseEntity.records).each(function(i,v){
						$('.null-box').hide();
						html = '<tr>';
						html +='<td class="nobor">&nbsp;</td>';

						html += '<td>'+ v.orderNo + '</td>';
						html += '<td>'+ v.merName + '</td>';
						html += '<td>' + v.txnAmt+ '</td>';
						html += '<td>' + v.orderCardno+ '</td>';
						html += '<td>' + v.blackAmt + '</td>';

						html += '<td>' + v.proOrderState + '</td>';

						html += '<td>' + v.proOrderDate + '</td>';
						
						html +='<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#supplierDetailsTb').append(html);
					});
					$('.com-table01 tbody tr:even').find('td').css("background-color",'#f6fafe');
					$('.com-table02 tbody tr:even').find('td').css("background-color",'#f6fafe');	
				}
				var pageSize = data.responseEntity.pageSize;
				var pageNo = data.responseEntity.pageNo;
				var totalPages = data.responseEntity.totalPages;
				var rows = data.responseEntity.rows;
				console.log("pageNo="+pageNo+"pageSize="+pageSize+"totalPages="+totalPages+"rows"+rows)
				$('#rechargeForDetails').paginator('setPage',pageNo,pageSize,totalPages,rows);
				$('#rechargeForDetails select').attr("style","width:45px;padding:0px 0px");
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
	
}

function exportDetailExcel() {
	var data = {};
	data.posCode =$('#posCodeDetail').val();
	data.merName=$('#merNameDetail').val();
	data.proOrderNum=$('#proOrderNumDetail').val();
	data.orderDateStart=$('#orderDateStart').val();
	data.orderDateEnd=$('#orderDateEnd').val();
	$.fileDownload('exportCardRechargeDetails', {
		data : data,
		failCallback : function(data) {
			var obj = JSON.parse(data);
			$.messagerBox({
				type : 'warn',
				title : "信息提示",
				msg : obj.message
			});
		}
	});
}

function returnSum(){
	$('#rechargeForSupplierDetailsForm').hide();
	clearRecharge("recordDetailForm");
	$('#rechargeForSupplierForm').show();
};

function compareTimeDetails(query,orderDateStart,orderDateEnd){
	 var d1 = new Date(orderDateStart.replace(/\-/g, "\/")); 
	 var d2 = new Date(orderDateEnd.replace(/\-/g, "\/")); 

	  if(orderDateStart!=""&&orderDateEnd!=""&&d1 >=d2) 
	 { 
	  var temp = orderDateEnd;
	  orderDateEnd = orderDateStart;
	  orderDateStart = temp;
	  $('#orderDateStart').val(orderDateStart)
	  $('#orderDateEnd').val(orderDateEnd)
	  query.orderDateStart = orderDateStart;
	  query.orderDateEnd = orderDateEnd;
	 }
}