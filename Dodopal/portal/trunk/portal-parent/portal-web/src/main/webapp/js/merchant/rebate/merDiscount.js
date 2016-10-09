$(function(){
	findMerDiscountList();
	$('.page-navi').paginator({prefix:'pos',pageSizeOnChange:findMerDiscountList});
	highlightTitle();
	$('#merDiscountDetail').hide();
	
});

function findMerDiscountList(page){
	
	if(!page){
		page = {pageNo:1,pageSize:10};
	}
	
	var merDiscountQueryDTO ={ 
			merCode :'',
			activate:$('#fag').val(),
			page:page
	}
	
	ddpAjaxCall({
		url : "findMerDiscountList",
		data : merDiscountQueryDTO,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#displayTbl tbody').empty();
				toggleActivateBtn('merDiscount');
				if(data.responseEntity.records && data.responseEntity.records.length > 0){
					$(data.responseEntity.records).each(function(i,v){
						$('.null-box').hide();
						html = '<tr>';
						
						html +='<td class="nobor">&nbsp;</td>';
						
						html += '<td class="a-center">';
						html += '<input id="'+v.id+'" name="merDiscount" value="'+v.merCode+'" type="checkbox" onclick="toggleActivateBtn(\'merDiscount\')"/>'
						html += '</td>';
						
						html += '<td>'
						html += ++i+(data.responseEntity.pageNo-1)*data.responseEntity.pageSize;
						html += '</td>';
						
						html += '<td>'
						html += v.discount;
						html += '</td>';
						
						
						html += '<td>'
						html += v.sort;
						html += '</td>';
						
						html += '<td>'
						html += v.activate == '0'? '启用' :'停用';
						html += '</td>';
						
						
						html += '<td>'
						html += v.createDate == null ? '' : formatDate(v.createDate,'yyyy-MM-dd HH:mm:ss');
						html += '</td>';
					
						
						html += '<td class="a-center">'
						if(hasPermission('merchant.rebate.modify')){
							html += '<a href="'+baseUrl+'/merchant/merDiscountEdit?id='+v.id+'" class="edit-icon" title="编辑折扣信息"></a>';
						}
						if(hasPermission('merchant.rebate.view')){
							html += '<a href="javascript:void(0);" class="text-icon" onClick="findMerDiscountDetail(this)"  title="折扣详情"></a>';
						}
						html += '</td>';
						
						html +='<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#displayTbl').append(html);
					});
				}
				else{
					$('.null-box').show();
				}
				
				var pageSize = data.responseEntity.pageSize;
				var pageNo = data.responseEntity.pageNo;
				var totalPages = data.responseEntity.totalPages;
				var rows = data.responseEntity.rows;
				$('.page-navi').paginator('setPage',pageNo,pageSize,totalPages,rows);
				$('.com-table01 tbody tr:even').find('td').css("background-color",'#f6fafe');
				$('.com-table02 tbody tr:even').find('td').css("background-color",'#f6fafe');				
			}else{
				//TODO handle error
				$.messagerBox({type: 'error', title:"信息提示", msg: data.message});
			}	
			
		}
	});
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

function startOrStopUser(flag){
	var ids = [];
	$('input[name=merDiscount]').each(function(i,v){
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
	$('input[name=merDiscount]').each(function(i,v){
		if($(v).is(':checked')){
			ids.push($(v).attr('id'));
		}
	});
	
	var activate = '';
	if(flag == true){
		activate = '0';
		ddpAjaxCall({
			url : "batchStartMerOperator",
			data : ids,
			successFn : function(data) {
				if (data.code == "000000") {
					findMerDiscountList($('#merDiscountPaginator').paginator('getPage'));
					//$('#qiyongBox').hide();
					toggleActivateBtn('merDiscount');
				}else{
					$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
				}
			}
		});
	}else{
		activate = '1';
		ddpAjaxCall({
			url : "batchStopMerOperator",
			data : ids,
			successFn : function(data) {
				if (data.code == "000000") {
					findMerDiscountList($('#merDiscountPaginator').paginator('getPage'));
					//$('#tingyongBox').hide();
					toggleActivateBtn('merDiscount');
				}else{
					$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
				}
			}
		});
	}
	
}


//查看详情
function findMerDiscountDetail(obj){
	var id = $(obj).parent().parent().find("input").attr('id');
	
	ddpAjaxCall({
		url : "findMerDiscountDetail",
		data : id,
		successFn : function(data) {
			if (data.code == "000000") {
				loadTranRecord(data.responseEntity);
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


//加载详情页面信息
function loadTranRecord(data) {
	$('#discountSpan').html(data.discount+'折');
	$('#sortSpan').html(data.sort);
	$('#activateSpan').html(data.activate=='0'?'启用':'停用');
	$('#childMerSpan').html(data.merName);
	$('#childMer').html();
	$('#merDiscountMain').hide();
	$('#merDiscountDetail').show();
	
}

//详情页返回
function btnReturn(){
	$('#detailForm')[0].reset();
	$('#merDiscountMain').show();
	$('#merDiscountDetail').hide();
}

function clearBtn(){
	$('#queryForm')[0].reset();
}
