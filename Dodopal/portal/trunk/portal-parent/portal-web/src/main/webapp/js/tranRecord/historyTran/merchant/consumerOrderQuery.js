$(function(){
	initQueryDate();
	findConsumptionOrderList();
	$('.page-navi').paginator({prefix:'order',pageSizeOnChange:findConsumptionOrderList});
	highlightTitle();
	$('.data-tit a').each(function(){
		console.log($(this).text());
		if($.trim($(this).text())=="消费查询"){
			$(this).addClass('currents').siblings().removeClass();
		}
	});
	initEvent();
});

function findConsumptionOrderList(page){
	if(!page){
		page = {pageNo:1,pageSize:10};
	}

	if(compareDates($("#startdate").val(),"yyyy-MM-dd",$("#enddate").val(),"yyyy-MM-dd")==1){
		var temp = $("#startdate").val();
		$("#startdate").val($("#enddate").val());
		$("#enddate").val(temp);
	}
	
	var orderQuery ={ 
//			code : escapePeculiar($.trim($('#posCode').val())),
			mchnitid:"", /** 商户号*/
			transactionid:escapePeculiar($.trim($("#transactionidQuery").val())), /** 商户订单号*/
			startdate:$("#startdate").val(), /** 查询起始时间*/
			enddate:$("#enddate").val(),/** 查询结束时间*/
			posid:escapePeculiar($.trim($("#posidQuery").val())),/** Pos编号*/
			cardno:escapePeculiar($.trim($("#cardnoQuery").val())),/** 卡号 */
			username:escapePeculiar($.trim($("#username").val())),/** 用户名称 */
			status:$("#statusQuery").val(),/** 订单状态 */
			page:page
	}
	
	ddpAjaxCall({
		url : $.base+"/merJointQuery/findConsumptionOrderList",
		data : orderQuery,
		successFn : function(data) {
			if (data.code == "000000") {
				afterFind();
				var html = '';
				$('#displayTbl tbody').empty();
				if(data.responseEntity.records && data.responseEntity.records.length > 0){
					$(data.responseEntity.records).each(function(i,v){
						$('.null-box').hide();
						html = '<tr>';
						
						html +='<td class="nobor">&nbsp;</td>';
						
						html += '<td>'
						html += v.orderid == null ? '' :htmlTagFormat(v.orderid);//订单号
						html += '</td>';
						
						html += '<td>'
						html += v.mchnitname == null ? '' :htmlTagFormat(v.mchnitname);//商户名称
						html += '</td>';
				
						html += '<td>'
						html += v.username == null ? '' :htmlTagFormat(v.username);//用户名称
						html += '</td>';
						
						html += '<td>'
						html += v.cardno == null ? '' :htmlTagFormat(v.cardno);//卡号
						html += '</td>';
							
						html += '<td>'
						html += v.posid == null ? '' :htmlTagFormat(v.posid);//POS号
						html += '</td>';
						
						html += '<td>'
						html += v.proamt == null ? '' :htmlTagFormat(v.proamt);//交易前金额
						html += '</td>';
						
						html += '<td>'
						html += v.facevalue == null ? '' :htmlTagFormat(v.facevalue);//应收金额
						html += '</td>';
						
						html += '<td>'
						html += v.sale == null ? '' :htmlTagFormat(v.sale);//用户折扣
						html += '</td>';
						
						html += '<td>'
						html += v.amt == null ? '' :htmlTagFormat(v.amt);//实收金额
						html += '</td>';
						
						html += '<td>'
						html += v.blackamt == null ? '' :htmlTagFormat(v.blackamt);//交易后余额
						html += '</td>';
						
						html += '<td>'
						html += v.setsale == null ? '' :htmlTagFormat(v.setsale);//结算折扣
						html += '</td>';
						
						html += '<td>'
						html += v.setamt == null ? '' :htmlTagFormat(v.setamt);//结算金额
						html += '</td>';
						
						html += '<td>'
						html += v.setfee == null ? '' :htmlTagFormat(v.setfee);//结算手续费
						html += '</td>';
							
						html += '<td>'
						html +=	v.checkcarddate == null ? '' :htmlTagFormat(v.checkcarddate);//交易时间
						html += '</td>';
						
						html += '<td>'
						html +=	v.status == null ? '' :htmlTagFormat(v.status);//订单状态
						html += '</td>';
						
//						html += '<td>'
//						html += v.bundlingDate == null ? '' : formatDate(v.bundlingDate,'yyyy-MM-dd HH:mm:ss');
//						html += '</td>';
						
						html +='<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#displayTbl').append(html);
					});
				}else{
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
				$.messagerBox({type: 'error', title:"信息提示", msg: data.message});
			}	
			
		}
	});
}

function afterFind(){
	$("#allPos").attr('checked',false);
	disableOrEnableBtn(true,'#deleteBtn');
}
function checkRemark(){
	var rmlength =$.lengthRange($.trim($('#remarks').val()),0,200);
	var remck = $.validationHandler(rmlength, 'remarksWarn', "长度范围为200字符内");
	return remck;
}

function initEvent(){
	$("input[name=time]").click(function(){
		
		var time = $("input[name='time']:checked").val();
//		formatDate(new Date(),"y-M-d");
		if(time=="1W"){
			$("#startdate").val(formatDate(new Date(new Date()-24*60*60*1000*7),"yyyy-MM-dd"));
			$("#enddate").val(formatDate(new Date(),"yyyy-MM-dd"));
		}else if(time=="1M"){
			$("#startdate").val(formatDate(new Date(new Date()-24*60*60*1000*30),"yyyy-MM-dd"));
			$("#enddate").val(formatDate(new Date(),"yyyy-MM-dd"));
		}else if(time=="3M"){
			$("#startdate").val(formatDate(new Date(new Date()-24*60*60*1000*30*3),"yyyy-MM-dd"));
			$("#enddate").val(formatDate(new Date(),"yyyy-MM-dd"));
		}
	});
}

function myClearQueryForm(){
	clearQueryForm('queryForm');
	initQueryDate();
}

function initQueryDate(){
	$("#startdate").val(formatDate(new Date(),"yyyy-MM-dd"));
	$("#enddate").val(formatDate(new Date(),"yyyy-MM-dd"));
}
