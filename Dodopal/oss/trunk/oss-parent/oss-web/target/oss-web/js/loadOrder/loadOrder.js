$(function() {
//	findLoadOrder();
});

//下单
function bookLoadOrder(){
	var productNum = $('#productNum').val();
	var cityCode =	$('#cityCode').val();
	var chargeAmt='';
	if(productNum!=null && productNum!=''){
		chargeAmt ='';
	}else{
		chargeAmt = $('#rechargeAmt').val();
	}
	
	var loadOrderRequestDTO = {
			  sourceOrderNum : $('#sourceOrderNum').val(),
			  cardNum : $('#cardNum').val(),
			  merchantNum : $('#merchantNum').val(),
			  productNum : $('#productNum').val(),
			  cityCode  : cityCode,
			  chargeAmt : chargeAmt,
			  sourceOrderTime : $('#sourceOrderTime').val(),
			  signType : $('#signType').val(),
			  signData : $('#signData').val(),
			  signCharset : $('#signData').val()
	}
	ddpAjaxCall({
		url : "bookLoadOrder",
		data : loadOrderRequestDTO,
		successFn : function(data) {
			$('#loadOrderContent').text(data.responseEntity);
		}
	});
}
//6.1查看全部清掉
function findLoadOrder(){
	ddpAjaxCall({
		url : "findLoadOrder",
		successFn : function(data) {
			$('#loadOrderContent').text(data.responseEntity);
		}
	});
}


//6.3	根据外接商户的订单号查询圈存订单状态
function findLoadOrderStatus(){
	var findLoadOrderStatusDto = {
			  sourceOrderNum : $('#sourceOrderNumT').val(),
			  merchantNum : $('#merchantNumT').val(),
			  signType : $('#signTypeT').val(),
			  signData : $('#signDataT').val(),
			  signCharset  : $('#signCharsetT').val()
	}
	ddpAjaxCall({
		url : "findLoadOrderStatus",
		data : findLoadOrderStatusDto,
		successFn : function(data) {
			$('#findLoadOrderStatusP').text(data.responseEntity);
		}
	});
}