$(function(){
	findTraBeanList();
	highlightTitle();
});



function findTraBeanList(){
	ddpAjaxCall({
		url : "findTraBeanList",
		data : '',
		successFn : function(data) {
			console.log(data);
			
			var html = '';
			var html1 = '';
			if (data.code == "000000") {
				console.log(data);
			
				$('#displayTbl tbody').empty();
				
				if(data.responseEntity!=null && data.responseEntity.length > 0){
					$(data.responseEntity).each(function(i,v){
						var tdCss = getTdCss(i);
						html = '<tr>';
						html += '<td class="nobor">&nbsp;</td>';
						
						html += '<td class="'+tdCss+' a-center">';
						html += 	getSequence(data.responseEntity,i);
						html += '</td>';
						
						html += '<td class="'+tdCss+'">';
						html += v.tranCode;
						html += '</td>';
						
						html += '<td class="'+tdCss+'">'
						html += v.amountMoney;
						html += '</td>';
						
						html += '<td class="'+tdCss+'">'
						html += v.realTranMoney;
						html += '</td>';
						
						html += '<td class="'+tdCss+'">'
						html += v.businessType==null? '':v.businessType;
						html += '</td>';
					
						html += '<td class="'+tdCss+'">'
						html += v.tranType==null? '':v.tranType;
						html += '</td>';
						
						html += '<td class="'+tdCss+'">'
						html += v.payWay==null?'' :v.payWay;
						html += '</td>';
						
						html += '<td class="'+tdCss+'">'
						html += v.tranOutStatus==null? '':v.tranOutStatus;
						html += '</td>';
						
						html += '<td class="'+tdCss+'">';
						html +=  v.createDate == null ? '' : formatDate(v.createDate,'yyyy-MM-dd hh:mm:ss');
						html += '</td>';
							
						html += '<td class="'+tdCss+' a-center">'
						html += '<a href="/portal-web/tran/tranDetail?tranCode='+v.tranCode+'" title="查看" class="text-icon"></a>';
						html += '</td>';
						
						html += '<td class="nobor">&nbsp;</td>';
						
						html += '</tr>';
						$('#displayTbl').append(html);
					
					});
					}else{
						$("#lookInfo").hide();
					}
				
				}else{
					$("#lookInfo").hide();
					$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
					}
				}
	});
}

