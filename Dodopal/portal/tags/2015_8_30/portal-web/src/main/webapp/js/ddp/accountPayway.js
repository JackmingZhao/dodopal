$(function(){
	highlightTitle();
	accountSetPayWay();
});

//常用支付
function accountSetPayWay(){
	ddpAjaxCall({
			url : "accountSetPayWay",
			data : '',
			successFn :function(data) {
				if (data.code == "000000") {
					loadPayWayCommon(data.responseEntity);
			}
			}
	});
}

function loadPayWayCommon(account){
	var html ='';
	if(account!=null && account!=''){
		$(account).each(function(i,v){
			html +='<li><label><input type="checkbox" value="'+v.id+'" name="payId"><img alt="'
			+v.payName+'"  title="'+v.payName+'" src="../images/'+v.payLogo+'"  style="height:20px;"></label></li>'
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
								html3 += '<li><label><input onclick="checkNo(this)" type="checkbox" value="'+v.id+'" name="payId"><img alt="'
										+ v.payName+ '" src="../images/'+v.payLogo+'" title="'+ v.payName+ '" style="height:23px;"></label></li>'
							}
							if (v.payType == '2') {
								html2 +='<li><label><input onclick="checkNo(this)" type="checkbox" value="'+v.id+'" name="payId"><img alt="'+
								v.payName+'" src="../images/'+v.payLogo+'" title="'+ v.payName+ '" style="height:23px;"></label></li>'
							}
						})
		html3 += '<li class="only"><a  onclick="showAll();" flg="0" id ="showId" class="blue">展开所有银行</a></li>';
		$('#internetBank').append(html3);
		$('#thrPayment').append(html2);
	}
	$("#accountBasicInfo").hide();
	$("#accountSetPayWay").hide();
	$('[js="pay-list"] li:gt(10)').hide();
	$('[js="pay-list"] li.only').show();
	$("#accountMorePayWay").show();
}

function showAll(){
		if($('#showId').attr("flg")=="0"){
			$('[js="pay-list"] li').show();
			$('#showId').attr("flg","1");
			$(this).find('.blue').text('收起所有银行');
		}else{
			$('[js="pay-list"] li:gt(10)').hide();
			$('[js="pay-list"] li.only').show();
			$('#showId').attr("flg","0");
			$(this).find('.blue').text('展开所有银行');
		}	
}

function closeMore(){
	$("#accountMorePayWay").hide();
	$("#accountSetPayWay").show();
	$("#accountCommon").show();
	$("#internetBank").empty();
	$('#thrPayment').empty();
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
				accountSetPayWay();
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