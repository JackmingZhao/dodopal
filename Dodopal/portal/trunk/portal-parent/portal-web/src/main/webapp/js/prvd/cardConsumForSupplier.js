$(function(){
	findCardConsumForSupplier();
	highlightTitle();
	$('#cardConsumForSupplier').paginator({
		prefix : 'supplierTb',
		pageSizeOnChange : findCardConsumForSupplier
	});
});

function exportExcel() {
	var data = {};
	data.proCode = escapePeculiar($.trim($('#proCode').val()));
	data.merName = escapePeculiar($.trim($('#merName').val()));
	data.bind = escapePeculiar($.trim($('#bind').val()));
	data.startDate = escapePeculiar($.trim($('#startDate').val()));
	data.endDate = escapePeculiar($.trim($('#endDate').val()));
	$.fileDownload('exportCardConsumForSupp', {
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
function findCardConsumForSupplier(page){
	if (!page) {
		var _pageNo = escapePeculiar($.trim($("#_pageNo").val()));
		var _pageSize = escapePeculiar($.trim($("#_pageSize").val()));
		var flag = escapePeculiar($.trim($("#flag").val()));
		if(flag != '' && flag != undefined){
			if(_pageNo != undefined && _pageNo !='' && _pageSize !=undefined && _pageSize!= ''){
				page = {
						pageNo :parseInt(_pageNo),
						pageSize:parseInt(_pageSize)
					}
			}else{
				page = {
						pageNo : 1,
						pageSize : 10
					};
			}
			$("#flag").val('');
		}else{
			page = {
					pageNo : 1,
					pageSize : 10
				};
		}
	}
	var query ={
			proCode:$('#proCode').val(),
			bind:$('#bind').val(),
			merName:$('#merName').val(),
			startDate:$('#startDate').val(),
			endDate:$('#endDate').val(),
			page:page
	}
	ddpAjaxCall({
		url : "findCardConsumForSupplier",
		data : query,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				afterFind("allChild");
				$('#supplierTb tbody').empty();
				if( data.responseEntity!=null &&  data.responseEntity.records.length > 0){
					pageSize = data.responseEntity.pageSize;
					pageNo = data.responseEntity.pageNo;
					totalPages = data.responseEntity.totalPages;
					rows = data.responseEntity.rows;
					$(data.responseEntity.records).each(function(i,v){
						$('.null-box').hide();
						html = '<tr>';
						html +='<td class="nobor">&nbsp;</td>';
						html += '<td class="a-center">';
						html += '<input id="'+v.proCode+'" name="cardConsumForSuppliers" type="checkbox" onclick="toggleActivateBtn(\'cardConsumForSuppliers\')"/>'
						html += '</td>';
						html += '<td>'+v.proCode+'</td>';
						html += '<td>'+v.bind+'</td>';
						html += '<td>'+v.consumeCount+'</td>';
						html += '<td>'+v.consumeAmt+'</td>';
						html += '<td>'+v.merName+'</td>';
						
						html += '<td class="a-center">'
						html += '<a href="'+baseUrl+'/prvd/cardConsumDetailForSupplier?proCode='+v.proCode+'&bind='+v.bind+'&merName='+v.merName+'&pageSize='+pageSize+'&pageNo='+pageNo+'"  class="text-icon mg0" title="消费详情"></a>';
						html += '</td>';
						html +='<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#supplierTb').append(html);
					});
					$('.com-table01 tbody tr:even').find('td').css("background-color",'#f6fafe');
					$('.com-table02 tbody tr:even').find('td').css("background-color",'#f6fafe');	
				}
				 else {
						$('.null-box').show();
						$('.page-navi').paginator('setPage');
					}
				$('#cardConsumForSupplier').paginator('setPage',pageNo,pageSize,totalPages,rows);
				$('#cardConsumForSupplier select').attr("style","width:45px;padding:0px 0px");
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
}

function startOrStopUser(flag){
	var ids = [];
	$('input[name=cardConsumForSuppliers]').each(function(i,v){
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
	$('input[name=cardConsumForSuppliers]').each(function(i,v){
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
					findCardConsumForSupplier($('#cardConsumForSupplier').paginator('getPage'));
					toggleActivateBtn('cardConsumForSuppliers');
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
					findCardConsumForSupplier($('#cardConsumForSupplier').paginator('getPage'));
					toggleActivateBtn('cardConsumForSuppliers');
				}else{
					$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
				}
			}
		});
	}
	
}
function afterFind(id){
	$("#"+id).attr('checked',false);
	disableOrEnableBtn(true,'#qiyong');
	disableOrEnableBtn(true,'#tingyong');
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