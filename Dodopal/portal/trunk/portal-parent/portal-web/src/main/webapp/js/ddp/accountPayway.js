$(function(){
	highlightTitle();
	accountSetPayWay();
	var a=0;
});


//常用支付
function accountSetPayWay(code){
	ddpAjaxCall({
			url : "accountSetPayWay",
			data : '',
			successFn :function(data) {
				if (data.code == "000000" || code=="000000") {
					loadPayWayCommon(data.responseEntity);
			}
			}
	});
}

function loadPayWayCommon(account){
	var html ='';
	if(account!=null && account!=''){
		$(account).each(function(i,v){
			html +='<li><label><input type="hidden"  value="'+v.id+'" name="showPayId"><span class="span-img"><s></s><img src="'+payimg+ v.payLogo+ '" alt="'+ v.payName+ '" title="'+ v.payName+ '"/></span></label></li>'
		})
		$('#accountCommon').html(html);
	}
	$("#accountMorePayWay").hide();
	$("#accountSetPayWay").show();
	$("#internetBank").empty();
	$('#thrPayment').empty();
}

/**
 * 显示修改常用支付方式
 */
function findMorePayWay() {
	ddpAjaxCall({
		url : "accountMorePayWay",
		data : '',
		successFn : function(data) {
			if (data.code == "000000" && data.responseEntity != null) {
				loadMorePayWaySource(data.responseEntity);
			}else{
			}
		}
	})
}

function loadMorePayWaySource(accountMore){
	if (accountMore != null && accountMore != '') {
		// 网银支付
		var html3 = '';
		// 第三方支付
		var html2 = '';
		$(accountMore).each(function(i, v) {
							if (v.payType == '3') {
								html3 += '<li><label><input onclick="checkNo(this)" type="checkbox" id="'+v.id+'" value="'+v.id+'" name="payId"><span class="span-img"><s></s><img src="'+payimg+ v.payLogo+ '" alt="'+ v.payName+ '"  title="'+ v.payName+ '"/></span></label></li>'
							}
							if (v.payType == '2') {
								 if(v.bankGatewayType=='3'){}else{
								html2 +='<li><label><input onclick="checkNo(this)" type="checkbox" id="'+v.id+'" value="'+v.id+'" name="payId"><span class="span-img"><s></s><img src="'+payimg+ v.payLogo+ '" alt="'+ v.payName+ '"  title="'+ v.payName+ '"/></span></label></li>'
								 }
						     }
						})
					
		//html3 += '<li class="only"><a onclick="showAll();" id ="showId" flg="0" class="blue">展开所有银行</a></li>';
		html3 += '<li class="more-link only"><a  onclick="showAll();" flg="0" id ="showId" class="blue"><i class="i-more"></i><span>更多</span></a></li>';
		$('#internetBank').append(html3);
		$('#thrPayment').append(html2);
		$("input[name='showPayId']").each(function(i,v){
			var pId = $(this).val();
			$("#"+pId).attr("checked","checked"); 
		});
	}
	if($('[js="pay-list"] li:gt(10)')){
		$('[js="pay-list"] li:gt(10)').hide();
		$('[js="pay-list"] li.only').show();
	}else{
		$('#showId').children('span').text('');
		$('#showId').children('i').remove();
	}
	$("#accountBasicInfo").hide();
	$("#accountSetPayWay").hide();
	$("#accountMorePayWay").show();
	
}

function showAll(){
		if($('#showId').attr("flg")=="0"){
			$('[js="pay-list"] li').show();
			$('#showId').attr("flg","1");
			$('.i-more').css({'background-position':-31+'px '+-414+'px'});
			$('#showId').children('span').text('收起');
		}else{
			$('[js="pay-list"] li:gt(6)').hide();
			$('[js="pay-list"] li.only').show();
			$('#showId').attr("flg","0");
			$('.i-more').css({'background-position':-2+'px '+-414+'px'});
			$('#showId').children('span').text('更多');
		}	
}

function closeMore(){
	$("#accountMorePayWay").hide();
	$("#accountSetPayWay").show();
	$("#accountCommon").show();
	$("#internetBank").empty();
	$('#thrPayment').empty();
	window.location.reload();
	$('#showId').empty();
}
function insertCommon(){
	var payWayId="";
	$("input[name='payId']:checked").each(function(){
		if(this.checked){ 
			payWayId+=this.value+",";
		} 
	}); 
	ddpAjaxCall({
		url : "accountSetSave",
		type : "POST",
		data : payWayId,
		dataType : "json",
		successFn : function(data) {
			if (data.code == "000000") {
				var code = data.code;
				location.reload();
				accountSetPayWay(code);
			} else {
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
}


function checkNo(vvv){
	if($("input[type='checkbox']:checked").length > 5) {
		vvv.checked=false;
	}
}