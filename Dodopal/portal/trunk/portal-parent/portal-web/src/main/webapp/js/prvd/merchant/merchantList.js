$(function() {
	//alert("111");
	findCityMerchantPos();
	$('.page-navi').paginator({prefix : 'cityMerchantPos',pageSizeOnChange : findCityMerchantPos});
	highlightTitle();
});

function findCityMerchantPos(page) {
	if (!page) {
		var _pageNo = escapePeculiar($.trim($("#_pageNo").val()));
		var _pageSize = escapePeculiar($.trim($("#_pageSize").val()));
		var flag = escapePeculiar($.trim($("#flag").val()));
		if(flag != '' && flag != undefined){
			if(_pageNo != undefined && _pageNo !='' && _pageSize !=undefined && _pageSize!= ''){
				page = {
						pageNo :parseInt(_pageNo),
						pageSize:parseInt(_pageSize)
					}
			}else{
				page = {
						pageNo : 1,
						pageSize : 10
					};
			}
			$("#flag").val('');
		}else{
			page = {
					pageNo : 1,
					pageSize : 10
				};
		}
	}
	
	var merName = escapePeculiar($.trim($('#merName').val()));
	var merUserName = escapePeculiar($.trim($('#merUserName').val()));
	var merUserMobile = escapePeculiar($.trim($('#merUserMobile').val()));
	
	var merCountQuery = {
		merName : merName,
		merUserName : merUserName,
		merUserMobile : merUserMobile,
		page : page,
	}
	ddpAjaxCall({
		url : baseUrl+"/prvd/find",
		data : merCountQuery,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#cityMerchantPosTb tbody').empty();
				if (data.responseEntity != null
						&& data.responseEntity.records.length > 0) {
					pageSize = data.responseEntity.pageSize;
					pageNo = data.responseEntity.pageNo;
					totalPages = data.responseEntity.totalPages;
					rows = data.responseEntity.rows;
					$(data.responseEntity.records)
							.each(
									function(i, v) {
										html = '<tr>';
										html += '<td class="nobor">&nbsp;</td>';
										html += '<td class="a-center">'+ getSequence(data.responseEntity, i)+ '</td>';
										html += '<td>'+ (v.merName == null ? '': v.merName) + '</td>'
										html += '<td>' + (v.merUserName ==null ? '':v.merUserName)+ '</td>';
										html += '<td>' + (v.merUserMobile == null ? '' :+ v.merUserMobile)+ '</td>';
										html += '<td>' + v.posCount + '</td>';

										html += '<td>' + (v.merAddress == null ? '':v.merAddress) + '</td>';

										html += '<td>' + (v.activateView == null ?'' : v.activateView )+ '</td>';
										html += '<td class="a-center">';
										if(hasPermission('prvd.merchant.pos')){
										html += '<a href="'+baseUrl+'/prvd/merchantPos?merName='+v.merName+'&merCode='+v.merCode+'&mmerName='+merName+'&merUserName='+merUserName+'&merUserMobile='+merUserMobile+'&pageSize='+pageSize+'&pageNo='+pageNo+'"  class="info-icon mg0" title="POS信息"></a>';
										}
										if(hasPermission('prvd.merchant.tran')){
										html +='<a href="'+baseUrl+'/transactionDetails/detail?merName='+v.merName+'&merCode='+v.merCode+'&mmerName='+merName+'&merUserName='+merUserName+'&merUserMobile='+merUserMobile+'&pageSize='+pageSize+'&pageNo='+pageNo+'"  class="text-icon mg0" title="交易明细"></a>';
										}
										html += '</td>';
										html += '<td class="nobor">&nbsp;</td>';
										html += '</tr>';
										$('#cityMerchantPosTb').append(html);
									});

					$('.com-table01 tbody tr:even').find('td').css(
							"background-color", '#f6fafe');
					$('.com-table02 tbody tr:even').find('td').css(
							"background-color", '#f6fafe');
					
					$('.page-navi').paginator('setPage', pageNo, pageSize,
							totalPages, rows);
					$('.page-navi select').attr("style",
							"width:45px;padding:0px 0px");
				} else {
					$('.null-box').show();
					$('.page-navi').paginator('setPage');
				}
			} else {
				$.messagerBox({
					type : 'warn',
					title : "信息提示",
					msg : data.message
				});
			}
		}
	});
//	var flag = window.location.search;
//	if(flag.indexOf('?') >= 0){
//		window.history.pushState("","", "merchant");
//	}
}

function clearForm(){
	$('#merName').val('');
	$('#merUserName').val('');
	$('#merUserMobile').val('');
}

