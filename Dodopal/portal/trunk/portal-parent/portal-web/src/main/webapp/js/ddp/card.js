$(function() {
	highlightTitle();
	findUserCard();
	initDetail();
});

function initDetail() {
	$('.header-inr-nav ul li a').each(function() {
		if ($.trim($(this).text()) == "卡片管理") {
			$(this).addClass('cur');
		}
	});
	
}

function startOrStopUnbind(){
	var ids = [];
	var code='';
	$('input[name=radio01]').each(function(i,v){
		if($(v).is(':checked')){
			ids.push($(v).val());
			code=$(v).prop("title");
		}
	});
	
	if(ids.length == 0){
		$.messagerBox({
			type : 'warn',
			title : "信息提示",
			msg : "请选择卡片"
		});
		return;
	}
	
	$('#innerBox').empty();
	var html='';
	html='<div class="tips">';
	html+= '<div class="alert-icons alert-icons1"></div>';
    html+=' <span>确认要解绑此卡片';
    html+=code;
    html+='吗？</span>';
    $('#innerBox').append(html);	
	$("#cardUnbindDetail").show();
}



function startOrStopUnbindConfirm(){
	var ids = [];
	$('input[name=radio01]').each(function(i,v){
		if($(v).is(':checked')){
			ids.push($(v).val());
		}
	});
	
	ddpAjaxCall({
		url : "unbindCardByUser",
		data : ids,
		successFn : function(data) {
			if (data.code == "000000") {
				
				$("#cardUnbindDetail").hide();
				findUserCard();
				$.messagerBox({
					type : 'warn',
					title : "信息提示",
					msg : data.message
				});
				
			}else{
				$.messagerBox({
					type : 'warn',
					title : "信息提示",
					msg : data.message
				});
			}
		}
	})

}



function findUserCard() {
	ddpAjaxCall({
		url : "findUserCard",
		data : '',
		successFn : function(data) {
			var html = '';
			if (data.code == "000000") {

				$('#cardTable tbody').empty();

				if (data.responseEntity != null
						&& data.responseEntity.length > 0) {
					$(data.responseEntity)
							.each(
									function(i, v) {
										var tdCss = getTdCss(i);
										html = '<tr>';
										html += '<td class="nobor">&nbsp;</td>';

										html += '<td class="' + tdCss
												+ ' a-center">';
										html += '<input type="radio"  name="radio01" value="'
												+ v.id + '" title="'+v.cardCode+'" />';
										html += '</td>';

										html += '<td class="' + tdCss + '">';
										html += v.cardCode == null ? ''
												: v.cardCode;
										html += '</td>';

										html += '<td class="' + tdCss + '">'
										html += htmlTagFormat(v.cardName == null ? ''
												: v.cardName);
										html += '</td>';

										html += '<td class="' + tdCss + '"style="word-break:break-all">'
										html += htmlTagFormat(v.remark == null ? ''
												: v.remark);
										html += '</td>';

										html += '<td class="'
												+ tdCss
												+ ' a-center"><a href="#" onclick="openEditWin(this)"  title="编辑" class="edit-icon"></a>'
										html += '</td>';
										html += '<td class="nobor">&nbsp;</td>';

										html += '</tr>';
										$('#cardTable').append(html);

									});
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
}



function openEditWin(obj){
	var id = $(obj).parent().parent().find("input").val();
	 $("#cardId").val(id);
	 
		ddpAjaxCall({
			url : "findUserCardById",
			data : id,
			successFn : function(data) {
				if (data.code == "000000") {
					$("#cardName").val(data.responseEntity.cardName);
					$("#remark").val(data.responseEntity.remark);
					$("#editBox").show();
				}
			}
		})
		
	/*var id ='';
	$("#cardTable input:radio").each(function(key,value){
	     if($(value).prop('checked')){
	    	 id = $(value).val();
	    	 
	     }
	})
	 //$("#cardId").val(id);  */
	
}

function editCard(obj){
	$("#editBox").hide();
	var cardBean={
		id : $("#cardId").val(),
		cardName : $("#cardName").val(),
		remark : $("#remark").val()
	}
	ddpAjaxCall({
		url : "editUserCard",
		data : cardBean,
		successFn : function(data) {
			var html = '';
			if (data.code == "000000") {
			 var callbackFn = function(value){
					 window.location.reload();
				}
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message,confirmOnClick: callbackFn});
			}else{
				var callbackFn1 = function(value){
				}
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message,confirmOnClick: callbackFn1});
			}
		}
	})
	
	
}
