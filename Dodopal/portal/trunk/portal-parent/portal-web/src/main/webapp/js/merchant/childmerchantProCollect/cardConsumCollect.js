$(function(){
	$('#cardConsumeCollectDetailsForm').hide();
	$('#cardConsumeCollectForm').show();
	findCardConsumCollect();
	highlightTitle();
		$('.header-inr-nav ul li a').each(function(){
			if($.trim($(this).text())=="子商户管理"){
				$(this).addClass('cur');
			}
		});
	    $('.tit-h3 a').each(function(){
			if($.trim($(this).text())=="业务订单汇总"){
				$(this).addClass('cur');
			}
		});
		$('.tab-list01 a').each(function(){
			if($.trim($(this).text())=="一卡通消费"){
				$(this).addClass('cur');
			}
		});
	$('#cardConsumCollect').paginator({
		prefix : 'supplier',
		pageSizeOnChange : findCardConsumCollect
	});
	$('#cardConsumCollectDetails').paginator({
		prefix : 'cardConsumCollectDetailsTb',
		pageSizeOnChange : findcardConsumCollectDetails
	});
});

function findCardConsumCollect(page){
	if (!page) {
		page = {
			pageNo : 1,
			pageSize : 10
		};
	}
	var query ={
			states:$('#proOrderState').val(),
			merName: escapePeculiar($.trim($('#merName').val())),
			startDate: escapePeculiar($.trim($('#startDate').val())),
			endDate: escapePeculiar($.trim($('#endDate').val())),
			page:page
	}
	var startDate = query.startDate;
	var endDate = query.endDate;
	compareTime(query,startDate,endDate);
	
	ddpAjaxCall({
		url : "findCardConsumCollect",
		data : query,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#supplierTb tbody').empty();
				if( data.responseEntity!=null &&  data.responseEntity.records.length > 0){
					$(data.responseEntity.records).each(function(i,v){
						$('.null-box').hide();
						html = '<tr>';
						html +='<td class="nobor">&nbsp;</td>';
						html += '<td class="a-center">'
							+ getSequence(
									data.responseEntity, i)
							+ '</td>';
//						html += '<td>'+v.orderdate+'</td>'
						html += '<td>'+v.merName+'</td>';
//						html += '<td>'+v.cityName+'</td>';
//						if(v.proCode == null || v.proCode ==""){
//							html += '<td>'+""+'</td>';
//						}else{
//							html += '<td>'+v.proCode+'</td>';
//						}
						html += '<td>'+v.consumeCount+'</td>';
						html += '<td>'+v.consumeOriginalAmt+'</td>';
						html += '<td>'+fmoney(v.consumeAmt,2)+'</td>';
						html += '<td>'+v.consumeDiscountAmt+'</td>';
//						html += '<td style="word-break:break-all" >'
//							html += v.comments == null
//											? ''
//											: htmlTagFormat(v.comments);
//							html += '</td>';
						html += '<td class="a-center">'
						html += '<a href="#" onClick="querySumDetail(this);" class="text-icon mg0" title="消费详情"></a>';
						html += '</td>';
						html +='<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#supplierTb').append(html);
					});
					$('.com-table01 tbody tr:even').find('td').css("background-color", '#f6fafe');
					$('.com-table02 tbody tr:even').find('td').css("background-color", '#f6fafe');	
					var pageSize = data.responseEntity.pageSize;
					var pageNo = data.responseEntity.pageNo;
					var totalPages = data.responseEntity.totalPages;
					var rows = data.responseEntity.rows;
					$('#cardConsumCollect').paginator('setPage',pageNo,pageSize,totalPages,rows);
					$('#cardConsumCollect select').attr("style","width:45px;padding:0px 0px");
				}
				 else {
						$('.null-box').show();
						$('#cardConsumCollect').paginator('setPage');
						$('#cardConsumCollect select').attr("style","width:45px;padding:0px 0px");
					}
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
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

////导出
//function exportExcel() {
//	var data = {};
//	data.proCode = escapePeculiar($.trim($('#proCode').val()));
//	data.merName = escapePeculiar($.trim($('#merName').val()));
//	data.startDate = escapePeculiar($.trim($('#startDate').val()));
//	data.endDate = escapePeculiar($.trim($('#endDate').val()));
//	$.fileDownload('exportCardConsumCollect', {
//		data : data,
//		failCallback : function(data) {
//			var obj = JSON.parse(data);
//			$.messagerBox({
//				type : 'warn',
//				title : "信息提示",
//				msg : obj.message
//			});
//		}
//	});
//}


function clearConsumeCollect(selector){
	$('#' + selector)[0].reset();
	selectUiInit();
}


function returnSum(){
	$('#cardConsumeCollectDetailsForm').hide();
	$('#cardConsumeCollectForm').show();
};

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

//详情查询
function findcardConsumCollectDetails(page){
	if (!page) {
		page = {
			pageNo : 1,
			pageSize : 10
		};
	}
	var query ={
//			proCode:$('#proCodeSpan').html(),
//			
//			cityName:$('#cityNameDetail').val(),
//			orderDate:$('#orderDateDetail').val(),
			states:$('#statesDetail').val(),
			startDate:$('#startDateDetail').val(),
			endDate:$('#endDateDetail').val(),
			merName:$('#merNameDetail').val(),
			page:page
	}
	ddpAjaxCall({
		url : "findCardConsumCollectDetails",
		data : query,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#cardConsumCollectDetailsTb tbody').empty();
				if( data.responseEntity!=null &&  data.responseEntity.records.length > 0){
					$(data.responseEntity.records).each(function(i,v){
						$('.null-box').hide();
						html = '<tr>';
						html +='<td >&nbsp;</td>';
						html += '<td class="a-center">'
							+ getSequence(
									data.responseEntity, i)
							+ '</td>';
						html += '<td>'+ v.orderNo + '</td>';
						html += '<td>'+ v.merName + '</td>';
						html += '<td>'+ v.cityName + '</td>';
						html += '<td>' + v.originalPrice+ '</td>';
						html += '<td>' + v.txnAmt+ '</td>';
						html += '<td>' + v.befbal+ '</td>';
						html += '<td>' + v.blackAmt+ '</td>';
						html += '<td>' + v.orderCardno+ '</td>';
						html += '<td>' + v.posCode+ '</td>';
						html += '<td>' + v.proOrderState + '</td>';

						html += '<td>' + v.proOrderDate + '</td>';
						
						html += '<td style="word-break:break-all" >'
						html += v.posComments == null
										? ''
										: htmlTagFormat(v.posComments);
						html += '</td>';
						html +='<td >&nbsp;</td>';
						html += '</tr>';
						$('#cardConsumCollectDetailsTb').append(html);
					});
					$('.com-table01 tbody tr:even').find('td').css("background-color",'#f6fafe');
					$('.com-table02 tbody tr:even').find('td').css("background-color",'#f6fafe');	
				}
				 else {
						$('.null-box').show();
					}
				var pageSize = data.responseEntity.pageSize;
				var pageNo = data.responseEntity.pageNo;
				var totalPages = data.responseEntity.totalPages;
				 rows = data.responseEntity.rows;
				$('#cardConsumCollectDetails').paginator('setPage',pageNo,pageSize,totalPages,rows);
				$('#cardConsumCollectDetails select').attr("style","width:45px;padding:0px 0px");
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
	
}

function querySumDetail(obj){
//	var orderDateDetail =$(obj).parent().parent().find("td").eq(2).text();
	var merNameDetail = $(obj).parent().parent().find("td").eq(2).text();
//	var cityNameDetail = $(obj).parent().parent().find("td").eq(4).text();
//	var proCodeSpan = $(obj).parent().parent().find("td").eq(5).text();
//	alert($(obj).parent().parent().find("td").eq(4).text());
	$('#startDateDetail').val($('#startDate').val());
	$('#endDateDetail').val($('#endDate').val());
	$('#statesDetail').val($('#proOrderState').val());
	$('#merNameDetail').val(merNameDetail);
//	$('#cityNameDetail').val(cityNameDetail);
//	$('#orderDateDetail').val(orderDateDetail);
	$('#merNameSpan').html(merNameDetail);
//	$('#proCodeSpan').html(proCodeSpan);
	findcardConsumCollectDetails();
	$('#cardConsumeCollectDetailsForm').show();
	$('#cardConsumeCollectForm').hide();
	
}

//function exportDetailsExcel() {
//	var data = {};
//	data.proCode = escapePeculiar($.trim($('#proCodeSpan').html()));
//	data.cityName = escapePeculiar($('#cityNameDetail').val());
//	data.orderDate = escapePeculiar($('#orderDateDetail').val());
//	data.merName = escapePeculiar($('#merNameDetail').val());
//	$.fileDownload('exportCardConsumCollectDetails', {
//		data : data,
//		failCallback : function(data) {
//			var obj = JSON.parse(data);
//			$.messagerBox({
//				type : 'warn',
//				title : "信息提示",
//				msg : obj.message
//			});
//		}
//	});
//}