$(function(){
	findProfit();
	$('#profitManage').paginator({
		prefix : 'profitTb',
		pageSizeOnChange : findProfit
	});
	$('#details').paginator({
		prefix : 'profitDetailsTb',
		pageSizeOnChange : findProfitDetailsView
	});
	highlightTitle();
	$('#profitDetailsView').hide();
});

function findProfit(page){
	if (!page) {
		page = {
			pageNo : 1,
			pageSize : 10
		};
	}
	var query ={
			startDate:$('#createDateStart').val(),
			endDate:$('#createDateEnd').val(),
			page:page
	}
	var beginDate = query.startDate;
	var endDate = query.endDate;
	compareTime(query,beginDate,endDate);
	ddpAjaxCall({
		url : "toProfitManage",
		data : query,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#profitTb tbody').empty();
				if( data.responseEntity!=null &&  data.responseEntity.records.length > 0){
					$(data.responseEntity.records).each(function(i,v){
						i = i + 1;
						$('.null-box').hide();
						html = '<tr>';
						html += '<td class="nobor">&nbsp;</td>';
						html += '<td>'+v.collectDate+'</td>';
						html += '<td>'+v.bussinessType+'</td>'
						html += '<td>'+v.tradeCount+'</td>';
						html += '<td>'+fmoney(v.tradeAmount,2)+'</td>';
						html += '<td>'+fmoney(v.profit,2)+'</td>';
						
						html += '<td class="a-center">'
						html += '<a href="javascript:void(0);" class="text-icon" title="详情" onclick="findProfitDetailsView(null,\''
							+ v.collectDate + '\');"></a>';
						html += '</td>';
						
						html +='<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#profitTb').append(html);
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
				var rows = data.responseEntity.rows;
				$('#profitManage').paginator('setPage',pageNo,pageSize,totalPages,rows);
				$('#profitManage select').attr("style","width:45px;padding:0px 0px");
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}	
		}
	});
	
}

function findProfitDetailsView(page,collectDate){	
	if (!page) {
		page = {
			pageNo : 1,
			pageSize : 10
		};
	}
	if(collectDate==null){
		collectDate = $('#collectDate').val();
	}else{
		$('#collectDate').val(collectDate);
	}	
	var query ={
			collectDate:collectDate,
			page:page
	}
	ddpAjaxCall({
		url : "toProfitDetails",
		data : query,
		successFn : function(data) {

			if (data.code == "000000") {
				var html = '';
				$('#profitDetailsTb tbody').empty();
				
				if( data.responseEntity!=null &&  data.responseEntity.records.length > 0){
					$(data.responseEntity.records).each(function(i,v){
						i = i + 1;
						var customerRate = '';
						$('.null-box').hide();
						html = '<tr>';
						html +='<td class="nobor">&nbsp;</td>';
						if(v.customerRateType == "单笔返点金额(元)"){
							customerRate =(Number(v.customerRate)/100).toFixed(2)
						}else{
							customerRate = v.customerRate;
						}
						html += '<td>'+v.orderNo+'</td>';
						html += '<td>'+formatDate(v.orderTime,'yyyy-MM-dd HH:mm:ss')+'</td>'
						html += '<td>'+v.bussinessType+'</td>';
						html += '<td>'+v.customerRateType+'</td>';
						html += '<td>'+customerRate+'</td>';
						html += '<td>'+(Number(v.orderAmount)/100).toFixed(2)+'</td>';
						html += '<td>'+(Number(v.customerShouldProfit)/100).toFixed(2)+'</td>';
						html += '<td>'+(Number(v.customerRealProfit)/100).toFixed(2)+'</td>';
						html += '<td>'+v.subCustomerName+'</td>';
						html += '<td>'+(Number(v.subCustomerShouldProfit)/100).toFixed(2)+'</td>';
						html += '<td class="a-center">'
						html += '</td>';
						
						html +='<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#profitDetailsTb').append(html);
					});
					$('.com-table01 tbody tr:even').find('td').css("background-color",'#f6fafe');
					$('.com-table02 tbody tr:even').find('td').css("background-color",'#f6fafe');	
					var pageSize = data.responseEntity.pageSize;
					var pageNo = data.responseEntity.pageNo;
					var totalPages = data.responseEntity.totalPages;
					var rows = data.responseEntity.rows;
					console.log("pageNo="+pageNo+"pageSize="+pageSize+"totalPages="+totalPages+"rows"+rows)
					$('#details').paginator('setPage',pageNo,pageSize,totalPages,rows);
					$('#details select').attr("style","width:45px;padding:0px 0px");
				}
				 else {
						$('.null-box').show();
						$('#details').paginator('setPage');
					}
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}	
		}
	});
	
	$('#profitDetailsView').show();
	$('#profitManageView').hide();
}

function clearView(){
	$('#profitDetailsView').hide();
	$('#profitManageView').show();
}

//导出
function exportProfit(){
    $.fileDownload('profitExport', {
		data: $('#recordForm').serialize(),
		failCallback: function() {
			msgShow(systemWarnLabel, "文件导出出错", 'warning');
		}
	})
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
