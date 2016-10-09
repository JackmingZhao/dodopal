$(function(){
	findCardconsumForDetails();
	highlightTitle();
	$('.page-navi').paginator({
		prefix : 'supplierDetailsTb',
		pageSizeOnChange : findCardconsumForDetails
	});
	$('#_excelDownLoad').click(function(){
		exportExcel('exportCardConsumDetailsForSupp','frm')
    });
});

function findCardconsumForDetails(page){
	if (!page) {
		page = {
				pageNo : 1,
				pageSize : 10
			};
		}
	var query ={
			proCode:$('#proCode').val(),
			merName:$('#merName').val(),
			orderNo:$('#proOrderNum').val(),
			startDate:$('#orderDateStart').val(),
			endDate:$('#orderDateEnd').val(),
			page:page
	}
	var startDate = query.startDate;
	var endDate = query.endDate;
	compareTime(query,startDate,endDate);
	ddpAjaxCall({
		url : "findCardConsumDetails",
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
					var pageSize = data.responseEntity.pageSize;
					var pageNo = data.responseEntity.pageNo;
					var totalPages = data.responseEntity.totalPages;
					var rows = data.responseEntity.rows;
					$('.page-navi').paginator('setPage',pageNo,pageSize,totalPages,rows);
				}
				 else {
						$('.null-box').show();
						$('.page-navi').paginator('setPage');
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
	  $('#orderDateStart').val(startDate)
	  $('#orderDateEnd').val(endDate)
	  query.startDate = startDate;
	  query.endDate = endDate;
	 }
}

