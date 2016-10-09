$(function() {
	findTranRecord();
	$('.page-navi').paginator({
						prefix : 'transactionRecord',
						pageSizeOnChange : findTranRecord
					});
			highlightTitle();
});


function findTranRecord(page){
	if (!page) {
		page = {
			pageNo : 1,
			pageSize : 10
		};
	}
	var query ={ 
			page:page
	}
	ddpAjaxCall({
		url : "findPayTraTransactionByPage",
		data : query,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#transactionRecordTb tbody').empty();
				if( data.responseEntity!=null &&  data.responseEntity.records.length > 0){
					$(data.responseEntity.records).each(function(i,v){
						html = '<tr>';
						html +='<td class="nobor">&nbsp;</td>';
					
						html += '<td>'+v.tranCode+'</td>';
						html += '<td>'+v.businessType+'</td>'
						html += '<td>'+v.realTranMoney+'</td>';
						html += '<td>'+v.payWay+'</td>';
						html += '<td>'+(v.tranType == null ? '' : v.tranType) +'</td>';
						html += '<td>'+v.tranOutStatus+'</td>';
						html += '<td>'+(v.createDate == null ? '' : formatDate(v.createDate,'yyyy-MM-dd HH:mm:ss'))+'</td>';
						
						html += '<td class="a-center">'
						html += '<a href="/portal-web/tran/tranDetail?tranCode='+v.tranCode+'" class="text-icon" title="详情" onclick="viewTranRecord(\''+v.tranCode+'\');"></a>';
						html += '</td>';
						
						html +='<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#transactionRecordTb').append(html);
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

function clearTranRecord(){
//	$("select option").html("")
	$("#tranType option:first").prop("selected", 'selected');
	//$("#recordForm").reset();
}

//查看详情
function viewTranRecord(tranCode){
	ddpAjaxCall({
		url : "findTranRecordByCode",
		data : tranCode,
		successFn : function(data) {
			if (data.code == "000000") {
				loadTranRecord(data.responseEntity);
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
}

function loadTranRecord(data){
	clearTranRecordView();
	if(data.orderDate){
		data.orderDate = formatDate(data.orderDate,'yyyy-MM-dd HH:mm:ss');
	}
	if(data.createDate){
		data.createDate = formatDate(data.createDate,'yyyy-MM-dd HH:mm:ss');
	}
	$('#orderDate').html("订单时间："+data.orderDate);
	$('#createDate').html("交易时间："+data.createDate);
	
	$('#tranCode').html(data.tranCode);
	$('#orderNumber').html(data.orderNumber);
	$('#businessType').html(data.businessType);
	$('#tranType').html(data.tranType);
	$('#realTranMoney1').html(data.realTranMoney1);
	$('#realTranMoney2').html(data.realTranMoney2);
	$('#realTranMoney3').html(data.realTranMoney3);
	$('#tranRecordList').hide();
	$('#tranRecordView').show();
	
}

function clearTranRecordView(){
	$('#TranRecordTb tr td span').html('');
	$('#orderTime').html('订单时间：');
	$('#tranTime').html('交易时间：');
}