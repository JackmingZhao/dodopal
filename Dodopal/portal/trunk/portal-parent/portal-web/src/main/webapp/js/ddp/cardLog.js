$(function() {
	findUserCardLog();
	$('#cardLogTablePaginator').paginator({prefix:'cardLogTable',pageSizeOnChange:findUserCardLog});
	
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="卡片管理"){
			$(this).addClass('cur');
		}
	});
});

function findUserCardLog(page){
	console.log('findUserCardLog page ::' + page);
	
	if(!page){
		page = {pageNo:1,pageSize:10};
	}
	
	var query ={
		    userCode:'',
		    operName:'',
		    code:'',
		    source:'',
			page:page
   }
	ddpAjaxCall({
		url : "findUserCardLog",
		data : query,
		successFn : function(data) {
			var html = '';
			if (data.code == "000000") {
			
				$('#cardLogTableTb tbody').empty();
				
				if( data.responseEntity!=null &&  data.responseEntity.records.length > 0){
					$(data.responseEntity.records).each(function(i,v){
						var tdCss = getTdCss(i);
						html = '<tr>';
						html += '<td class="nobor">&nbsp;</td>';
						
						html += '<td class="'+tdCss+'">';
						html += 	getSequence(data.responseEntity,i);
						html += '</td>';
						
						html += '<td class="'+tdCss+'">';
						html +=v.code ==null? '' : v.code;
						html +=	'</td>';
						
						html += '<td class="'+tdCss+'">';
						html +=htmlTagFormat(v.cardName ==null? '' : v.cardName);
						html +='</td>';
						
						html += '<td class="'+tdCss+'">';
						html+= htmlTagFormat(v.merUserNickName==null? '' : v.merUserNickName);
						html +='</td>';
						
						html += '<td class="'+tdCss+'">';
						html += v.operStatus ==null? '' : v.operStatus ;
						html +='</td>';
						
						html += '<td class="'+tdCss+'">';
						html +=  v.updateDate == null ? '' : formatDate(v.updateDate,'yyyy-MM-dd HH:mm:ss');
						html += '</td>';
						
						html += '<td class="'+tdCss+'">';
						html += htmlTagFormat(v.source ==null? '' : v.source) ;
						html += '</td>';
						
						html += '<td class="'+tdCss+'">';
						html += htmlTagFormat(v.operName ==null? '' : v.operName );
						html += '</td>';
						
						html += '<td class="nobor">&nbsp;</td>';
						
						html += '</tr>';
						$('#cardLogTableTb').append(html);
					
					});
					var pageSize = data.responseEntity.pageSize;
					var pageNo = data.responseEntity.pageNo;
					var totalPages = data.responseEntity.totalPages;
					var rows = data.responseEntity.rows;
					$('#cardLogTablePaginator').paginator('setPage',pageNo,pageSize,totalPages,rows);
					
					}else{
						$('#cardLogTablePaginator').paginator('setPage');
					}
				
				}else{
					$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
					}
				}
	});
}