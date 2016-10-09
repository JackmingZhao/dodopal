$(function(){
	initQueryDate();
	findConsumptionOrderCountList();
	$('#pageSpan').paginator({prefix:'order',pageSizeOnChange:findConsumptionOrderCountList});
	highlightTitle();
	$('.data-tit a').each(function(){
		if($.trim($(this).text())=="消费统计"){
			$(this).addClass('currents').siblings().removeClass();
		}
	});
	initEvent();
});

function findConsumptionOrderCountList(page){
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
			transactionid:"", /** 商户订单号*/
			startdate:$("#startdate").val(), /** 查询起始时间*/
			enddate:$("#enddate").val(),/** 查询结束时间*/
			posid:"",/** Pos编号*/
			cardno:"",/** 卡号 */
			username:"",/** 用户名称 */
			status:"",/** 订单状态 */
			page:page
	}
	  
      
	ddpAjaxCall({
		url : $.base+"/merJointQuery/findConsumptionOrderCountList",
		data : orderQuery,
		successFn : function(data) {
			if (data.code == "000000") {
				afterFind();
				var html = '';
				$('#displayTbl tbody').empty();
				if(data.responseEntity.records && data.responseEntity.records.length > 0){
					$(data.responseEntity.records).each(function(i,v){
						if(i==0){
							$("#jybs").html(v.count.jiaoyichenggongbishu);
							$("#jycgje").html(v.count.jiaoyichenggongzongjine);
							$("#sszje").html(v.count.shishouzongjine);
							$("#jszje").html(v.count.jiesuanzongjine);
							$("#jssxf").html(v.count.jiesuanzongshouxufei);
						}
						$('.null-box').hide();
						html = '<tr>';
						
						html +='<td class="nobor">&nbsp;</td>';
						
						html += '<td>'
						html += v.posid == null ? '' :htmlTagFormat(v.posid);//POS号
						html += '</td>';
						
						html += '<td>'
						html += v.username == null ? '' :htmlTagFormat(v.username);//用户名称
						html += '</td>';
				
						html += '<td>'
						html += v.jiaoyichenggongbishu == null ? '' :htmlTagFormat(v.jiaoyichenggongbishu);//交易成功笔数
						html += '</td>';
						
						html += '<td>'
						html += v.jiaoyichenggongzongjine == null ? '' :htmlTagFormat(v.jiaoyichenggongzongjine);//交易成功总金额
						html += '</td>';
							
						html += '<td>'
						html += v.shishouzongjine == null ? '' :htmlTagFormat(v.shishouzongjine);//实收总金额
						html += '</td>';
						
						html += '<td>'
						html += v.jiesuanzongjine == null ? '' :htmlTagFormat(v.jiesuanzongjine);//结算总金额
						html += '</td>';
						
						html += '<td>'
						html += v.jiesuanzongshouxufei == null ? '' :htmlTagFormat(v.jiesuanzongshouxufei);//结算总手续费
						html += '</td>';
						
						html +='<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#displayTbl').append(html);
					});
				}else{
					$('.null-box').show();
					$("#jybs").html(0);
					$("#jycgje").html(0);
					$("#sszje").html(0);
					$("#jszje").html(0);
					$("#jssxf").html(0);
				}
				var pageSize = data.responseEntity.pageSize;
				var pageNo = data.responseEntity.pageNo;
				var totalPages = data.responseEntity.totalPages;
				var rows = data.responseEntity.rows;
				$('#pageSpan').paginator('setPage',pageNo,pageSize,totalPages,rows);
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
//		formatDate(new Date(),"yyyy-MM-dd");
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