/*************************************************  数据导出  *****************************************************************/
var expExcBySelColTools = {
	'initSelectExportDia': function(expConfInfo) {
		this.expConfInfo = expConfInfo;
		var parDiaHeight = 270;
		this.expConfInfo.parentDiaId = 'selectExportExcelColumnDia';
		var selDataInfo = expConfInfo.selOptions;
		var customShow = expConfInfo.customShow;
		
	//	var showColumn = expConfInfo.showColumn;
		
		(function () {
			var aProp = ' href="#" class="ui-button ui-widget ui-state-default ui-corner-all"  style="margin-right:15px;" ';
			var diaStr = '<div id="'+this.expConfInfo.parentDiaId+'" class="easyui-dialog" title="选择导出列" toolbar="#dlg-toolbar">'
			+'</div>'
			+'<div id="dlg-toolbar" style="text-align:right;padding-bottom:10px;padding-top:10px;">'
			+ '<a id="subExcelColumnBtn" ' +aProp+ '>&nbsp&nbsp确定&nbsp&nbsp</a>'
			+ '<a id="canExcelColumnBtn" ' +aProp+ '>&nbsp&nbsp取消&nbsp&nbsp</a>'
			+'</div>';
			$('body').append(diaStr);
		})();
		$('#'+this.expConfInfo.parentDiaId).dialog({
			collapsible : false,
			modal : true,
			closed : true,
			cache: false,
			closable : true,
			width : 680,
			height : parDiaHeight
		});
		var postPath = $.base+'/expExcel/findExpCol';
		function getTempDiaId() {return this.expConfInfo.parentDiaId;};
		function getExpBtnId() {return this.expConfInfo.btnId;};
		
		if(this.expConfInfo.typeSelStr != '' && this.expConfInfo.typeSelStr.indexOf(",") > 0){
			postPath = $.base+'/expExcel/getExpCol';
		}
		
		ddpAjaxCall({
			url		: postPath,
			data	: this.expConfInfo.typeSelStr,
			successFn : function(data) {
				if(data.code != '000000') {
					msgShow(systemWarnLabel, data.message, 'warning');
				}else {
					var ajaxData = data.responseEntity;
					var selExpCol='<div style="height:200px;overflow:auto;"><table style="width: 99%"><tr>';
					var name_type = '';
					for(var i=0; i<ajaxData.length; i++) {
						var propertyName = ajaxData[i]["propertyName"],
							propertyCode = ajaxData[i]['propertyCode'],
							limText = limitStrLen(ajaxData[i]["propertyName"]);
						var inpAttr = 'name="selCol" type="checkbox" class="cbox" cIndex="'+propertyCode+'" cName="'+propertyName+'" '; 
						var newName = $('body').data(propertyCode);
						if((i)%3 == 0 && i != 0) selExpCol += '</tr><tr>';
						if(customShow != '' && customShow != undefined && Boolean(customShow)){
							if(newName != undefined){
								selExpCol += '<td title="'+propertyName+'" class="ui-widget-content jqgrow ui-row-ltr ui-state-highlight selColTdT selTd">'
								   + '<input '+inpAttr+' checked="checked"></input><a>'+ newName + '</a></td>';
							}
						}else{
							if(ajaxData[i].isChecked == 0) {
								selExpCol += '<td title="'+propertyName+'" class="ui-widget-content jqgrow ui-row-ltr ui-state-highlight selColTdT selTd">'
										   + '<input '+inpAttr+' checked="checked"></input><a>'+ limText + '</a></td>';
							}else {
								selExpCol += '<td title="'+propertyName+'" class="ui-widget-content jqgrow ui-row-ltr selColTdF selTd">'
										   + '<input '+inpAttr+' ></input><a>'+ limText + '</a></td>';
							}
						}
					}
					selExpCol += '</tr></table></div>';
					$('#'+getTempDiaId()).prepend(selExpCol);	// ? cannot share this.data but can share func =_@
					(function() {
						$('.selTd').click(function(e) {
							var td=$(e.currentTarget);
							if(td.hasClass('selColTdT')) {
								td.removeClass('ui-state-highlight');
								td.children('input').prop('checked', false);
								td.addClass('selColTdF');
								td.removeClass('selColTdT');
							}else {
								td.addClass('ui-state-highlight');
								td.children('input').prop('checked', true);
								td.addClass('selColTdT');
								td.removeClass('selColTdF');
							}
						});
						$("#subExcelColumnBtn").click(function() {
							expExcBySelColTools.expSelExpCol();
						});
						$("#canExcelColumnBtn").click(function() {
							$('#'+getTempDiaId()).hide().dialog('close');
						});
						$("#"+getExpBtnId()).click(function() {
							expExcBySelColTools.show();
						});
					})();
				}
			}
		});
		function limitStrLen(str) {
			var len = str.length;
			if(len > 15) {
				return str.substring(0, 15)+ "...";
			}else {
				return str;
			}
		}
	},
	
	"show": function() {
		$('#'+this.expConfInfo.parentDiaId).show().dialog('open');
	},
	
	'expSelExpCol': function() {
		var col = $("input[name='selCol']:checked").map(function () {
			var str = "";
			str += $(this).attr('cIndex');
			return str;
	    }).get().join('@');
		col += '&typeSelStr=' + this.expConfInfo.typeSelStr;
		var boxlen = $("input[name='selCol']:checked").length;
		if(boxlen <= 1) {
			msgShow(systemWarnLabel, "至少选两列", 'warning');
		}else {
			for(var i=0; i<filterConStr.length; i++) {
				var value = eval(filterConStr[i]['value']);
				if(value !== null && value != '') {
					col += '&'+filterConStr[i]['name']+'='+value;
				}
			}
			$('#'+this.expConfInfo.parentDiaId).hide().dialog('close');
			var $preparingFileModal = $("#preparing-file-modal");
			$preparingFileModal.dialog({ modal: true, width: 115 });
			$.fileDownload(this.expConfInfo.toUrl, {
				httpMethod: "POST",
				data:	'col='+col,
				successCallback: function(url) {
					$preparingFileModal.dialog('close');
				},
				failCallback: function(data) {
					$preparingFileModal.dialog('close');
					var response = JSON.parse(data);
					msgShow(systemWarnLabel,response.message, 'warning');
				}
			});
		}
	}
};
/*************************************************  数据导出  *****************************************************************/

