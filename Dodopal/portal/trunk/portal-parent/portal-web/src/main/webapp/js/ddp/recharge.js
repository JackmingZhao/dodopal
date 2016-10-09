$(function() {
	highlightTitle();
	findPayCommon();
	findPayWay();
	initMoney();
	initMainDetail();
});


function initMainDetail(){
	$('[js="del"]').click(function(){
	       var money = $('#money').val();
	       if(money=='0.00'|| money=='0' || money=='0.0'|| money==null || money==''){
	        $('#msg').show();
	        return false;
	       }
	       var payId1= $("#payMore input[name='payId']:checked").val();
	       var payId2= $("#payCommon input[name='payId']:checked").val();
	       if((payId1==null ||payId1=='')&&(payId2==null ||payId2=='')){
	        $('#msg').text("  请选择支付方式");
	        $('#msg').show();
	         return false;
	       }
	       $('#msg').hide();
	       var payType='';
	       $("form>div>div>ul>li>label>input:radio").each(function(key,value){
	  	     if($(value).prop('checked')){
	  	    	 payType = $(value).attr('paytype');
	  	     }
	  	  })
	  	  if(payType=='1'){
	  		$.messagerBox({type: 'warn', title:"信息提示", msg: '暂时不提供一卡通支付'});
	  		  return false;
	  	  }
	       
	       showRechargeDetail();
	       
		// if(payInfoFlag=='0'){
		   //showRechargeDetail();
		// }else{
		//   showRechargeDetail();
		 //  $('[js="delBox"]').show();
		   //$("#rechargeForm").submit();
		// }
	  	  

	});
		
	$('[js="delBox1"]').click(function(){
			$('[js="qurrenjiner"]').hide();
			
		    var flag="false"
		    $("#payInfo :checkbox").each(function(key,value){
		        if($(value).prop('checked')){
		        	flag = "true";
		        	modifyPayInfoFlg(flag);
		        }
		       })
			$('[js="delBox"]').show();
	});
		
	$('.com-ttl-box .common').click(function(event) {
			$(this).addClass('current');
			$('.pay-way-box2').addClass('current');
			$('.pay-navi-ul1').removeClass('current');
			$('.pay-way-box1').removeClass('current');
			$('.com-ttl-box .more').removeClass('current');
		});
		$('.com-ttl-box .more').click(function(event) {
			$(this).addClass('current');
			$('.pay-navi-ul1').addClass('current');
			$('.pay-way-box1').addClass('current');
			$('.pay-way-box2').removeClass('current');
			$('.com-ttl-box .common').removeClass('current');
			
		});

		
		$('#bankOn').click(function(event) {
			$('[js="pay-list"] li:gt(6)').hide();
			if($('[js="pay-list"] li').length>=9){
				//alert($('[js="pay-list"] li').length);
				 $('[js="pay-list"] li.only').show();
				 
				 var a=0;
				    $('[js="pay-list"] li.only').click(function(){
					if(a==0){
						$('[js="pay-list"] li').show();
						$('.i-more').css({'background-position':-31+'px '+-414+'px'});
						$(this).children().children('span').text('收起');
						a=1;
					}else{
						$('[js="pay-list"] li:gt(6)').hide();
						$('[js="pay-list"] li.only').show();
						$('.i-more').css({'background-position':-2+'px '+-414+'px'});
						$(this).children().children('span').text('更多');
						a=0;
					}		
				})
					
			}else{
				 $('[js="pay-list"] li.only').hide();
			}
		   
		})
	
}

//修改支付明细页面是否显示的弹出标记
function modifyPayInfoFlg(flag){
	ddpAjaxCall({
		url : "modifyPayInfoFlg",
		data : flag,
		successFn : function(data) {
			if (data.code == "000000" ) {
				
			}else{
				
			}
		}
	})
}

//用户常用支付方式为空时，隐藏div
function payCommonHidden(){
	$('#commonDiv').hide();
	$('#spanDiv').hide();
	$("#moreDiv").addClass('current');
	$('.pay-way-box1').addClass('current');
	$('.pay-navi-ul1').addClass('current');
	commonHidden = "true";
	//$('#onlinePay input').removeAttr('checked');  
	//$("#onlinePay input[name='payId']:eq(0)").attr("checked",true);
	
}

//支付金额格式化
function initMoney() {
	$("#money").bind('keyup', function() {
		clearNoNum($(this));
	});
	$("#money").focus(function(){
		var money = $('#money').val();
	    if(money=='0.00'|| money=='0' || money=='0.0'|| money==null || money==''){
	    	$('#money').val('');
	    }
		$('#msg').hide();
	})
	$("#money").bind('keydown', function() {
		checkDecimal($(this), 1, 9, 0, 2);
	});
	$("#money").bind('blur', function() {
		clearNoNumOnBlur($(this));
	});
}

//其他支付方式
function findPayWay() {
	ddpAjaxCall({
		url : "findPayWay",
		data : '',
		successFn : function(data) {
			if (data.code == "000000" && data.responseEntity != null) {
				loadPayWaySource(data.responseEntity);
			}else{
			}
		}
	})
}

//其他支付方式
function loadPayWaySource(PayWayBeanList) {
	if (PayWayBeanList != null && PayWayBeanList != '') {
		// 银行支付
		var html3 = '<ul class="pay-way-list clearfix" style="display:none;" js="pay-list">';
		// 在线支付
		var html2 = '<ul id="onlinePay" class="pay-way-list clearfix" >';
		// 一卡通支付
		var html1 = '<ul class="pay-way-list clearfix" style="display:none;">';
		$(PayWayBeanList).each(function(i, v) {
							if (v.payType == '3') {
								html3 += '<li><label><input type="radio"  name="payId" value="'+ v.id+ '" title="'+ v.payName+ '" js="'+v.payServiceRate+'" paytype="'+v.payType+'"/><span class="span-img"><s></s><img src="'+payimg+ v.payLogo+ '" alt="'+ v.payName+ '" title="'+ v.payName+ '"/></span></label></li>'}
							if (v.payType == '2') {
								  //微信支付  掩藏
								  if(v.bankGatewayType=='3'){}else{
								html2 += '<li><label><input type="radio"  name="payId" value="'+ v.id+ '" title="'+ v.payName+ '" js="'+v.payServiceRate+'" paytype="'+v.payType+'"/><span class="span-img"><s></s><img src="'+payimg+ v.payLogo+ '" alt="'+ v.payName+ '" title="'+ v.payName+ '"/></span></label></li>'}
							   }
						    if (v.payType == '1') {
								html1 += '<li><label><input type="radio"  name="payId" value="'+ v.id+ '" title="'+ v.payName+ '" js="'+v.payServiceRate+'" paytype="'+v.payType+'"/><span class="span-img"><s></s><img src="'+payimg+ v.payLogo+ '" alt="'+ v.payName+ '" title="'+ v.payName+ '"/></span></label></li>'}
						})
		html3 += '<li class="more-link only"><a><i class="i-more"></i><span>更多</span></a></li></ul>';
		html2 += '</ul>';
		html1 += '</ul>';
		$('#payMore').append(html2 + html3 + html1);
	}
	if(commonHidden=="true"){
		//$('#onlinePay input').removeAttr('checked');  
		$($("#onlinePay input").get(0)).prop("checked",true);
	}

}

//用户常用支付方式
function findPayCommon() {
	ddpAjaxCall({
		url : "findCommonPayWay",
		data : '',
		successFn : function(data) {
			if (data.code == "000000") {
				loadPayWayCommonSource(data.responseEntity);
			}else{
				payCommonHidden();
			}
		}
	})
}

//用户常用支付方式
function loadPayWayCommonSource(PayWayBeanList) {
	if (PayWayBeanList != null && PayWayBeanList != '') {

		var merClassify = $("#merClassify").val();
		// 用户常用支付方式
		var html = '<p class="recharge_mode"></p> <ul id="commonshow" class="pay-way-list clearfix">';
		$(PayWayBeanList).each(function(i, v) {
			                // 3. 银行支付  2.在线支付  1.一卡通支付
			                //merClassify 0为正式商户 1为测试商户  ,null为
							if(merClassify=='1'){
								if (v.payType == '2' || v.payType == '3' || v.payType=='1') {
									html += '<li><label><input type="radio" name="payId" value="'+ v.id+ '" title="'+ v.payName+ '" js="'+v.payServiceRate+'"  paytype="'+v.payType+'"/><span class="span-img"><s></s><img src="'+payimg+ v.payLogo+ '" alt="'+ v.payName+ '"  title="'+ v.payName+ '"/></span></label></li>'
								}
							}else{
								if (v.payType == '2' || v.payType == '3') {
									    html += '<li><label><input type="radio" name="payId" value="'+ v.id+ '" title="'+ v.payName+ '" js="'+v.payServiceRate+'"  paytype="'+v.payType+'"/><span class="span-img"><s></s><img src="'+payimg+ v.payLogo+ '" alt="'+ v.payName+ '"  title="'	+ v.payName+ '"/></span></label></li>'
								  }
							}
						})
		html += '</ul>';
		$('#payCommon').append(html);
		//$('#commonshow input').removeAttr('checked');  
		$($("#commonshow input").get(0)).prop("checked",true);
	}else{
		payCommonHidden();
	}
}





//支付详细页面
function showRechargeDetail(){
	var merType = $("#merType").val();
	var merExternal = $("#merExternal").val();
	var payWayId = '';
	
	var rechargeMoney = $("#money").val();
	var rechargeDetail = '';
	var rate =0;//服务费率
	var rateMoney = 0;
	var rateMM=0;
	$("form>div>div>ul>li>label>input:radio").each(function(key,value){
	     if($(value).prop('checked')){
	    	 rechargeDetail = $(value).prop('title');
	    	 rate = $(value).attr('js');
	    	 payWayId = $(value).val();
	    	 //alert(rate);
	     }
	})
	//外接商户
	if(merExternal=="true"){
		rateMoney = (parseFloat(rechargeMoney)+parseFloat((parseFloat(rate)*rechargeMoney)/1000));
		//alert(rateMoney);
		var ratetest = rateMoney.toString();
		if (ratetest.toString().indexOf(".") >0) {
			var text02 =ratetest.substring(ratetest.indexOf(".")+1, ratetest.length);
			if(text02.length>=3){
				rateMM = ((Number(rateMoney.toFixed(3))+0.005).toFixed(2))*100000000000/100000000000;
				//rateMoney = 
			}else{
				rateMM = rateMoney.toFixed(2);
			}
			$('#realMoney').val(rateMM);
		}else{
			$('#realMoney').val(rateMoney);
			rateMM = rateMoney;
		}
		
		//alert(rateMM);
		$('#rechargeDetail').empty();
		var html='';
		html = '<li> <em>支付方式：</em><span class="orange">';
		html+=rechargeDetail;
		html+='</span></li>';
		html+='<li> <em>充值金额：</em><span class="orange">';
		html+=rechargeMoney;
		html+='</span>元</li>';
		html+='<li> <em>实付金额：</em><span class="orange">';
		html+=rateMM;
		html+='</span>元</li>';
		$("#rechargeDetail").append(html);	
		
		if(payInfoFlag!='0'){
			$("#rechargeForm").submit();
			$('[js="delBox"]').show();
		}else{
			 $('[ js="qurrenjiner"]').show(); 
		}
		
	//非外接商户	
	}else{
		//个人  不收支付服务费
		 if(merType=='99'){
			 $('#realMoney').val(rechargeMoney);
			 $('#rechargeDetail').empty();
				var html='';
				html = '<li> <em>支付方式：</em><span class="orange">';
				html+=rechargeDetail;
				html+='</span></li>';
				html+='<li> <em>充值金额：</em><span class="orange">';
				html+=rechargeMoney;
				html+='</span>元</li>';
				html+='<li> <em>实付金额：</em><span class="orange">';
				html+=rechargeMoney;
				html+='</span>元</li>';
				$("#rechargeDetail").append(html);	
				
				if(payInfoFlag!='0'){
					$("#rechargeForm").submit();
					$('[js="delBox"]').show();
				}else{
					 $('[ js="qurrenjiner"]').show(); 
				}
			 
			 
		 }else{
			 
	   //非外接  非个人 收取支付服务费
		ddpAjaxCall({
			url : "findPayServiceRate?payWayId="+payWayId+"&rechargeMoney="+rechargeMoney,
			data : '',
			successFn : function(data) {
				if (data.code == "000000" && data.responseEntity!=null) {
					var payServiceRateBean = data.responseEntity;
					//单笔
					if(payServiceRateBean.rateType=="1"){
						rateMM =  (parseFloat(rechargeMoney)+parseFloat(payServiceRateBean.rate/100));
						
						$('#realMoney').val(rateMM);
						//alert(rateMM);
					//千分比
					}else{
						rateMoney = (parseFloat(rechargeMoney)+parseFloat((parseFloat(payServiceRateBean.rate)*rechargeMoney)/1000));
						//alert(rateMoney);
						var ratetest = rateMoney.toString();
						if (ratetest.toString().indexOf(".") >0) {
							var text02 =ratetest.substring(ratetest.indexOf(".")+1, ratetest.length);
							if(text02.length>=3){
								rateMM = ((Number(rateMoney.toFixed(3))+0.005).toFixed(2))*100000000000/100000000000;
								//rateMoney = 
							}else{
								rateMM = rateMoney.toFixed(2);
							}
							$('#realMoney').val(rateMM);
						}else{
							$('#realMoney').val(rateMoney);
							rateMM = rateMoney;
						}
					}
					//alert(rateMM);
					$('#rechargeDetail').empty();
					var html='';
					html = '<li> <em>支付方式：</em><span class="orange">';
					html+=rechargeDetail;
					html+='</span></li>';
					html+='<li> <em>充值金额：</em><span class="orange">';
					html+=rechargeMoney;
					html+='</span>元</li>';
					html+='<li> <em>实付金额：</em><span class="orange">';
					html+=rateMM;
					html+='</span>元</li>';
					$("#rechargeDetail").append(html);	
					
					if(payInfoFlag!='0'){
						$("#rechargeForm").submit();
						 $('[js="delBox"]').show();
					}else{
						 $('[ js="qurrenjiner"]').show(); 
					}
					
				}else{
					$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
				}
			}
		})
		
		 }
	}

	

}


/* 以下为校验费率js */
function clearNoNumOnBlur(obj) {
	var rate = obj.val();
	rate = rate.replace(/[^\d.]/g, ""); // 清除“数字”和“.”以外的字符
	rate = rate.replace(/^\./g, ""); // 验证第一个字符是数字而不是.
	rate = rate.replace(/\.{2,}/g, "."); // 只保留第一个. 清除多余的
	rate = rate.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
	obj.val(rate.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));// 只能输入两个小数
	var text = obj.val();
	if (text.indexOf(".") < 0) {
		if(text.length >= 2 ){
		    var textchar0 = text.charAt(0);
		    var textchar1 = text.charAt(1);
		    if(textchar0=='0'&& textchar1=='0'){
		    	obj.val(parseFloat("0"));
		    }
		    if(textchar0=='0'&& textchar1!='0'){
		    	obj.val(parseFloat(textchar1));
		    }
		}
		var textChar = text.charAt(text.length - 1);
		if (text.length >= 8 && textChar != ".") {
			obj.val(parseFloat(text.substring(0, 8)));
		}
	} else {
		var text01 = text.substring(0, text.indexOf("."));
		var text02 = text.substring(text.indexOf("."), text.length);
		if(text01.length >= 2 ){
		    var textchar0 = text01.charAt(0);
		    var textchar1 = text01.charAt(1);
		    if(textchar0=='0'&& textchar1=='0'){
		    	text01='0';
		    }
		    if(textchar0=='0'&& textchar1!='0'){
		    	text01=textchar1;
		    }
		}
		if (text01.length > 8) {
			text01 = text01.substring(0, text01.length - 1);
		}
		var text = text01 + text02;
		if (text.length > 11) {
			text = "";
		} else {
			var textChar = text.charAt(text.length - 1);
			if (textChar == ".") {
				text = text.substring(0, text.length - 1);
			}
		}
		obj.val(text);
	}
}

function checkDecimal(obj, startWhole, endWhole, startDec, endDec) {
	var rate = obj.val();
	var re;
	var posNeg;
	re = new RegExp("^[+]?\\d{" + startWhole + "," + endWhole + "}(\\.\\d{"
			+ startDec + "," + endDec + "})?$");
	posNeg = /^[+]?]*$/;

	if (!re.test(rate) && !posNeg.test(rate)) {
		rate = "";
		obj.focus();
		return false;
	}
}

function clearNoNum(obj) {
	var rate = obj.val();
	rate = rate.replace(/[^\d.]/g, ""); // 清除“数字”和“.”以外的字符
	rate = rate.replace(/^\./g, ""); // 验证第一个字符是数字而不是.
	rate = rate.replace(/\.{2,}/g, "."); // 只保留第一个. 清除多余的
	rate = rate.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
	obj.val(rate.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));// 只能输入两个小数
	var text = obj.val();
	if (text.indexOf(".") < 0) {
		if(text.length >= 2 ){
		    var textchar0 = text.charAt(0);
		    var textchar1 = text.charAt(1);
		    if(textchar0=='0'&& textchar1=='0'){
		    	obj.val(parseFloat("0"));
		    }
		    if(textchar0=='0'&& textchar1!='0'){
		    	obj.val(parseFloat(textchar1));
		    }
		}
		var textChar = text.charAt(text.length - 1);
		if (text.length >= 8 && textChar != ".") {
			obj.val(parseFloat(text.substring(0, 8)));
		}
	} else {
		var text01 = text.substring(0, text.indexOf("."));
		var text02 = text.substring(text.indexOf("."), text.length);
		if(text01.length >= 2 ){
		    var textchar0 = text01.charAt(0);
		    var textchar1 = text01.charAt(1);
		    if(textchar0=='0'&& textchar1=='0'){
		    	text01='0';
		    }
		    if(textchar0=='0'&& textchar1!='0'){
		    	text01=textchar1;
		    }
		}
		if (text01.length > 8) {
			text01 = text01.substring(0, text01.length - 1);
		}
		var text = text01 + text02;
		if (text.length > 11) {
			text = "";
		}
		obj.val(text);
	}
	
}
