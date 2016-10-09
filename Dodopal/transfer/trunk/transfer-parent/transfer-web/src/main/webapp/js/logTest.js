$(function(){
	findLogTransfer();
	$('.page-navi').paginator({prefix:'logTransfer',pageSizeOnChange:findLogTransfer});
	highlightTitle();
});
//查询子商户信息
function findLogTransfer(page){
	var batchId = $('#batchId').val();
	if(!page){
		page = {pageNo:1,pageSize:10};
	}
	var logTransfer ={ 
			batchId:batchId,
			page:page
	}
	ddpAjaxCall({
		url : "findLogTransfer",
		data : logTransfer,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#logTransferTbl tbody').empty();
				var i = 1;
				if( data.responseEntity.records &&  data.responseEntity.records.length > 0){
					$(data.responseEntity.records).each(function(i,v){
						i = i+1;
						$('.null-box').hide();
						html = '<tr>';
						html +='<td class="nobor">&nbsp;</td>';
						
						html += '<td style="word-break:break-all" >'
						html += v.id;
						html += '</td>';
						
						html += '<td style="word-break:break-all" >'
						html += v.batchId;
						html += '</td>';
						
						html += '<td style="word-break:break-all" >'
						html += v.oldMerchantType;
						html += '</td>';
							
						html += '<td style="word-break:break-all" >'
						html += v.newMerchantCode;
						html += '</td>';
						
							
						html += '<td style="word-break:break-all" >'
						html += v.newMerchantType;
						html += '</td>';
									
						html += '<td class="a-center">'
						html += '<a href="javascript:void(0);" class="text-icon" title="查看异常"></a>';
						html += '</td>';
						
						html +='<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#logTransferTbl').append(html);
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
