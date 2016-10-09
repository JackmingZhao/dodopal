//6.2  根据卡号获取可用于一卡通充值的圈存订单
function findAvailableLoadOrdersByCardNum(){
	var cardNum = $('#cardNum').val();
	ddpAjaxCall({
		url : "findAvailableLoadOrdersByCardNum",
		data : cardNum,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#findAvailableOrderTbl tbody').empty();
				if( data.responseEntity&&  data.responseEntity.length > 0){
					$(data.responseEntity).each(function(i,v){
						$('.null-box').hide();
						html = '<tr>';
						html += '<td>';
						html += v.orderNum
						html += '</td>';
						
						
						html += '<td>'
						html += v.sourceOrderNum;
						html += '</td>';
						
						html += '<td>'
						html += v.cardNum;
						html += '</td>';
						
						html += '<td>'
						html += v.merchantNum;
						html += '</td>';
						
						html += '</tr>';
						$('#findAvailableOrderTbl').append(html);
					});
				}
			}else{
				alert(data.code+"----->"+data.message);
			}	
			
		}
	});
}