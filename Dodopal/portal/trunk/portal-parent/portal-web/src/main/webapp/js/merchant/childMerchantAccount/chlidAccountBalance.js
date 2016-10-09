$(function(){
	findChlidAccountBalance();
	$('.page-navi').paginator({
		prefix : 'chlidAccountBalance',
		pageSizeOnChange : findChlidAccountBalance
	});
	highlightTitle();
});

function findChlidAccountBalance(page){
	if (!page) {
		page = {
			pageNo : 1,
			pageSize : 10
		};
	}
	var query ={
				merName:escapePeculiar($.trim($('#merName').val())),
				fundType:$('#fundType').val(),
				page:page,
	}
	ddpAjaxCall({
		url : "find",
		data : query,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#accountBalanceTb tbody').empty();
				if( data.responseEntity!=null &&  data.responseEntity.records.length > 0){
					$(data.responseEntity.records).each(function(i,v){
						html = '<tr>';
						html +='<td class="nobor">&nbsp;</td>';
					
						html += '<td>'+getSequence(data.responseEntity,i)+'</td>';
						html += '<td>'+(v.merName == null ?'':v.merName)+'</td>'
						html += '<td>'+fmoney(v.merMoneySum,2)+'</td>';
						html += '<td>'+fmoney(v.frozenAmount,2)+'</td>';
						html += '<td>'+fmoney(v.merMoney,2)+'</td>';
						html += '<td><a href="'+baseUrl+'/merchantChildAct/list?merName='+v.merName+'&merCode='+v.merCode+'" class="orange-btn-text26" >查询</a></td>';
						html += '<td><a href="'+baseUrl+'/merchantChildTranlist/childMerchantRecord?merName='+v.merName+'&merCode='+v.merCode+'" class="orange-btn-text26" >查询</a></td>';
						
						html +='<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#accountBalanceTb').append(html);
					});
					$('.com-table01 tbody tr:even').find('td').css("background-color",'#f6fafe');
					$('.com-table02 tbody tr:even').find('td').css("background-color",'#f6fafe');	
				}
				var pageSize = data.responseEntity.pageSize;
				var pageNo = data.responseEntity.pageNo;
				var totalPages = data.responseEntity.totalPages;
				var rows = data.responseEntity.rows;
				$('.page-navi').paginator('setPage',pageNo,pageSize,totalPages,rows);
				$('.page-navi select').attr("style","width:45px;padding:0px 0px");
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}	
		}
	});
}

//导出
function exportChildified(){
	$("#balanceForm").submit();
}

function clearTranRecord(selector){
	$('#' + selector)[0].reset();
	selectUiInit();
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