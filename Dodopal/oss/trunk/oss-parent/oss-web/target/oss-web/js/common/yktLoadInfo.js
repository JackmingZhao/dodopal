function initYktLoadInfo() {
	initYktComponent();
	initYktComponentPayment();
	initYktIcLoadPanl();//外接商户圈存
}
//------------ 通卡公司展现框内 通卡公司名称超过7个字截取 start -------------
function formatYKTName(name) {
	var result = name;
	if(name.length > 7) {
		result = name.substring(0, 7)+ "...";
	}
	return result;
}
//------------ 通卡公司展现框内 通卡公司名称超过7个字截取   end-------------

//--------------------------限制用户只能输入小数点后两位 start--------
//obj dec小数位
function checkDecimal(obj, posOrNeg, startWhole, endWhole, startDec, endDec) {
	var rate = obj.val();
	var re;
	var posNeg;
	if (posOrNeg == 1 || posOrNeg == '1') {
		re = new RegExp("^[+]?\\d{" + startWhole + "," + endWhole + "}(\\.\\d{"
				+ startDec + "," + endDec + "})?$");
		posNeg = /^[+]?]*$/;
	} else if (posOrNeg == 2 || posOrNeg == '2') {
		re = new RegExp("^[-]?\\d{" + startWhole + "," + endWhole + "}(\\.\\d{"
				+ startDec + "," + endDec + "})?$");
		posNeg = /^[-]?]*$/;
	} else {
		re = new RegExp("^[+-]?\\d{" + startWhole + "," + endWhole
				+ "}(\\.\\d{" + startDec + "," + endDec + "})?$");
		posNeg = /^[+-]?]*$/;
	}
	if (!re.test(rate) && !posNeg.test(rate)) {
		rate = "";
		obj.focus();
		return false;
	}
}


//obj
function checkPlusMinus(obj) {
	var rate = obj.val();
	posNeg1 = /^[+]?]*$/;
	posNeg2 = /^[-]?]*$/;
	if (posNeg1.test(rate) || posNeg2.test(rate)) {
		obj.val = "";
		//obj.focus();
		return false;
	}
}
function clearNoNum(obj) { 
	var rate = obj.val();
	rate = rate.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
	rate = rate.replace(/^\./g,"");  //验证第一个字符是数字而不是.  
	rate = rate.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的  
	rate = rate.replace(".","$#$").replace(/\./g,"").replace("$#$",".");  
	obj.val(rate.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'));//只能输入两个小数  
	var text = obj.val();
	if (text.indexOf(".") < 0) {
		var textChar = text.charAt(text.length - 1);
		if (text.length >= 4  && textChar !=".") {
			obj.val(text.substring(0,3));  
		}
	} else {
		var text01 = text.substring(0,text.indexOf("."));
		var text02 = text.substring(text.indexOf("."),text.length);
		if (text01.length > 3) {
			text01 = text01.substring(0,text01.length-1);
		}
		var text = text01+text02;
		if (text.length > 6) {
			text = "";
		}
		obj.val(text);
	}
}
function clearNoNumOnBlur(obj) { 
	var rate = obj.val();
	rate = rate.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
	rate = rate.replace(/^\./g,"");  //验证第一个字符是数字而不是.  
	rate = rate.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的  
	rate = rate.replace(".","$#$").replace(/\./g,"").replace("$#$",".");  
	obj.val(rate.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'));//只能输入两个小数  
	var text = obj.val();
	if (text.indexOf(".") < 0) {
		var textChar = text.charAt(text.length - 1);
		if (text.length >= 4  && textChar !=".") {
			obj.val(text.substring(0,3));  
		}
	} else {
		var text01 = text.substring(0,text.indexOf("."));
		var text02 = text.substring(text.indexOf("."),text.length);
		if (text01.length > 3) {
			text01 = text01.substring(0,text01.length-1);
		}
		var text = text01+text02;
		if (text.length > 6) {
			text = "";
		} else {
			var textChar = text.charAt(text.length - 1);
			if (textChar ==".") {
				text = text.substring(0,text.length-1);  
			}
		}
		obj.val(text);
	}
}  
//--------------------------限制用户只能输入小数点后两位 end--------


//---------------------------------- 一卡通充值    start -------------------------------
//通卡公司选择
function yktSelector(elementStr) {
	var $element = $('#' + elementStr);
	findyktDialogCheckBox($element);
}

//通卡公司判断是否初始化选择
function findyktDialogCheckBox(element){
	//if(isNotBlank(element.html())){
	if(false){
		//从通卡公司名称获取所有的集合
		var findYKTSelectAll = $('#yktInfoContent').find("input[type=checkbox]");
		//业务费率表里面多选
		var findMerBusRateSelarrrow = $('#yktTbl').getGridParam('selarrrow');
		$.each(findYKTSelectAll, function(index, findYKTElement) {
			$(findYKTElement).attr('checked',false);
			if (findMerBusRateSelarrrow) {
				for(var i=0; i<findMerBusRateSelarrrow.length; i++) {
					//获取业务费率表选中的code
						var rowData = $('#yktTbl').getRowData(findMerBusRateSelarrrow[i]);
						if(findYKTElement.id==rowData.proCode){
							$(findYKTElement).attr('checked',true);
						}
					}
				}
		});
		showDialog('yktDialog');
	}else{
		//初始化打开选择为空
		var merCode=''; //商户类型CODE
		if($('#merParentCode').val() != null && $('#merParentCode').val() != ''){
			merCode = $('#merParentCode').val();
			ddpAjaxCall({
				url : "findYktInfo",
				data : merCode,
				successFn : function(data) {
					if (data.code == "000000") {
						createYKTInfo(element, data);
					} else {
						msgShow(systemWarnLabel, data.message, 'warning');
					}		
				}
			});
		}else{
			merCode = $('#merCode').val();
			ddpAjaxCall({
				url : "findMerProductList",
				data : merCode,
				successFn : function(data) {
					if (data.code == "000000") {
						createYKTInfo(element, data);
					} else {
						msgShow(systemWarnLabel, data.message, 'warning');
					}		
				}
			});
		}
	}
}

//加载通卡公司名称选择面板
function createYKTInfo(element, data) {
	//清空当前所有的数据
	element.html('');
	var sed = element.find("input[type=checkbox]:checked");	
	if(data.code == "000000") {
		var yktInfo = data.responseEntity;
		var col = 1;
		var line = '<tr>';
		for(var i=0; i<yktInfo.length; i++) {
			var id = yktInfo[i].proCode + "_" + yktInfo[i].rateCode;
			if(col == 5) {
				line += '</tr>';
				element.append(line);
				line = '<tr><td><label><input type="checkbox" id="' + id + '" style="width:27px;border:0;" value="' + yktInfo[i].proName + '"';
				if(yktInfo[i].checked == 'true' || yktInfo[i].checked == true) {

					line += 'checked="' + yktInfo[i].checked + '"'; 
				}
				line += '/></label></td><th style="text-align:left;" title="'+yktInfo[i].proName+'" class="easyui-tooltip">' +formatYKTName(yktInfo[i].proName) + '</th>';
				col = 2;
			} else {
				col++;
				line += '<td><label><input type="checkbox" id="' + id + '" style="width:27px;border:0;" value="' + yktInfo[i].proName + '" ';
				if(yktInfo[i].checked == 'true' || yktInfo[i].checked == true) {
					line += 'checked="' + yktInfo[i].checked + '"'; 
				}
				line += '/></label></td><th style="text-align:left;" title="'+yktInfo[i].proName+'" class="easyui-tooltip">' + formatYKTName(yktInfo[i].proName) + '</th>';
			}
		}
		if(line.length >= 4) {
			line += '</tr>';
			element.append(line);
		}
	
		//从通卡公司名称获取所有的集合
		var editYKTSelectAll = $('#yktInfoContent').find("input[type=checkbox]");
		//业务费率表里面多选
		var editBusRateSelarrrow = $('#yktTbl').getGridParam('selarrrow');
		// 临时保存选中的数据
		if (editBusRateSelarrrow) {
			var selectedData = "";
			for (var i = 0; i < editBusRateSelarrrow.length; i++) {
				// 获取业务费率表选中的code
				var rowData = $('#yktTbl').getRowData(editBusRateSelarrrow[i]);
				selectedData += rowData.proCode + "_" + rowData.rateCode + "_" + rowData.rateType + "_" + rowData.rate + ";";
			}
			$('#selectedProRateInfo').val(selectedData);
		}
		$.each(editYKTSelectAll, function(index, editYKTElement) {
			$(editYKTElement).attr('checked',false);
			if (editBusRateSelarrrow) {
				for(var i=0; i<editBusRateSelarrrow.length; i++) {
					//获取业务费率表选中的code
						var rowData = $('#yktTbl').getRowData(editBusRateSelarrrow[i]);
						if(editYKTElement.id==(rowData.proCode+"_"+rowData.rateCode)){
							$(editYKTElement).attr('checked',true);
							break;
						}
					}
				}
		});
		showDialog('yktDialog');
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}



//------------------------------------勾选通卡公司后点击确定start------------------
function initYktComponent() {
	$('#yktDialog').dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : true,
		width : 600,
		height : 280,
		buttons : [ {
			text : '确定',
			handler : yktHandler
		}]
	});
	initYKTTbl();
}

function initYKTTbl() {
	$('#yktTbl').jqGrid({
		datatype : "local",
		colNames : ['通卡公司code','通卡公司名称','业务CODE','业务名称','费率分类','数值'],
		colModel : [
		            { name : 'proCode', hidden : true },
		            { name : 'proName', index : 'proName', width : 100, align : 'left', sortable : false },
		            { name : 'rateCode', index : 'rateCode', width : 60, align : 'left', sortable : false, hidden:true },
		            { name : 'rateName', index : 'rateName', width : 100, align : 'left', sortable : false },
					{ name : 'rateType', index : 'rateType', width : 137, align : 'left', sortable : false , editable:true, edittype:"select",
		            	            editoptions: {value:"1:单笔返点金额(元);2:千分比(‰)", 
		            	            		      dataEvents: [{type: 'change',fn: function(e) {$('#yktTbl').setCell( $(this).parent().parent().attr('id'), 'rate', 0);}}]
		            	            			 },
			          formatter: function(cellval, el, rowData) {
							if(cellval == 1 || cellval == '1') {
								return '单笔返点金额(元)';
							} else if(cellval == 2 || cellval ==  '2'){
								return '千分比(‰)';
							} else if(cellval == 'undefined' || typeof(cellval) == 'undefined'||cellval == 'null'|| cellval == null || cellval == ''){
								return '千分比(‰)';
							} else {
								return cellval;
							}
						}
					},
					{ name : 'rate', index : 'rate', width : 40, align : 'left', sortable : true , editable : true }
				],
		//sortname : 'proCode',
		height : 150,
		width : 529,
		multiselect: true,
		rowNum : 1000,
		forceFit : true,
	   	cellEdit: true,
	   	cellsubmit: 'clientArray',
		loadComplete: function() {
			$(".jqgrow", $('#yktTbl')).each(function (index, row) {
				var $row = $(row);
				$row.find("input:checkbox").attr("checked", "checked");
				$row.find("input:checkbox").attr("disabled", "disabled");
			});
		}
	});
	$('#yktTbl').setGridParam({
		//onkeyup="clearNoNum(this)" onkeydown="checkDecimal(this,1,1,9,0,2)" onblur="checkPlusMinus(this)"
		afterEditCell : function(id, name, val, iRow, iCol) {
			//判断费率输入小数点
			$("#" + iRow + "_" + name, "#yktTbl").bind('keyup', function() {
				clearNoNum($(this));
			});
			$("#" + iRow + "_" + name, "#yktTbl").bind('keydown', function() {
				checkDecimal($(this),1,1,9,0,2);
			});
			$("#" + iRow + "_" + name, "#yktTbl").bind('blur', function() {
				clearNoNumOnBlur($(this));
				//checkPlusMinus($(this));
				$('#yktTbl').saveCell(iRow, iCol);
			});
		},
	   	afterSaveCell : function(rowid, cellname, value, iRow, iCol) {
			if(cellname == "rate") {
				var rate = $('#yktTbl').getCell(rowid, "rate");
				if(isNaN(rate)) {
					$('#yktTbl').setCell(rowid, 'rate', 0);
				} else {
					$('#yktTbl').setCell(rowid, 'rate',rate);
				
				}
			}
		}
	});
}

function yktHandler() {
	//根据商户类型和上级商户类型判断业务类型和数值是否可编辑
	var merParentCode =$('#merParentCode').val();
	var merCode = $('#merCode').val();
	if(isNotBlank(merParentCode)){
		if(isNotBlank(merCode)){
			if($('#merUnVer').val()=='0'){
				$('#yktTbl').setColProp('rateType',{editable: false});
				$('#yktTbl').setColProp('rate',{editable: true});
			}else{
				$('#yktTbl').setColProp('rateType',{editable: true});
				$('#yktTbl').setColProp('rate',{editable: true});
			}
		}else if(isBlank(merCode)){
			$('#yktTbl').setColProp('rateType',{editable: false});
			$('#yktTbl').setColProp('rate',{editable: true});
		}
	}else{
		$('#yktTbl').setColProp('rateType',{editable: true});
		$('#yktTbl').setColProp('rate',{editable: true});
	}
	hideDialog('yktDialog');
	var selectedArray = new Array();
	var $selectAll = $('#yktInfoContent').find("input:checkbox:checked");
	var selected = "";
	$.each($selectAll, function(index, element) {
		var idArray = element.id.split("_");
		var proName = $('#' + element.id).val();
		if(selected != "") {
			selected += ",";
		}
		selected += proName;
		selectedArray.push(idArray[0]);
	});
	//判断是否有值
	if(selectedArray.length != 0){
		$('#yktTblLine').show();
	}else{
		$('#yktTblLine').hide();
	}
	
	$('#providerCode').val(selected);
		ddpAjaxCall({
			url : "loadYktInfo?merParentCode="+merParentCode,
			data : selectedArray,
			successFn : function(data) {
				if (data.code == "000000") {
					var dataArray = data.responseEntity;
					if(dataArray!=null && dataArray.length>0) {
						for(var j=0;j<dataArray.length;j++) {
							var busRate = dataArray[j];
							var selectedProRateInfo = $('#selectedProRateInfo').val();
							if (selectedProRateInfo != null && selectedProRateInfo != "") {
								var proArray = selectedProRateInfo.split(";");
								for (var i = 0; i < proArray.length; i++) {
									var colArray = proArray[i].split("_");
									if(busRate.proCode == colArray[0] && busRate.rateCode==colArray[1]) {
										busRate.rateType = colArray[2];
										busRate.rate = colArray[3];
										break;
									}
								}
							}
						}
					}
					loadJqGridData($('#yktTbl'), dataArray);
					$('#yktTbl').jqGrid('setGridHeight', 27*dataArray.length);
					selectAllLines('yktTbl');
					$('#cb_yktTbl').attr('checked','checked');
					$('#cb_yktTbl').attr('disabled','disabled');
				} else {
					msgShow(systemWarnLabel, data.message, 'warning');
				}		
			}
		});
}

//------------------------------------勾选通卡公司后点击确定end------------------

//---------------------------------------- 一卡通充值    end -------------------------------------

// --------------------------------------- 一卡通消费   start  ----------------------------------
//--------------------- 选择一卡通消费通卡公司  start-----------------------
function yktSelectorPayment(elementStr) {
	var $element = $('#' + elementStr);
	findyktDialogCheckBoxPayment($element);
}

function createYKTInfoPayment(element, data) {
	//清空当前所有的数据
	element.html('');
	var sed = element.find("input[type=checkbox]:checked");	
	if(data.code == "000000") {
		var yktInfo = data.responseEntity;
		var col = 1;
		var line = '<tr>';
		for(var i=0; i<yktInfo.length; i++) {
			var id = yktInfo[i].proCode + "_" + yktInfo[i].rateCode;
			if(col == 5) {
				line += '</tr>';
				element.append(line);
				line = '<tr><td><label><input type="checkbox" id="' + id + '" style="width:27px;border:0;" value="' + yktInfo[i].proName + '"';
				if(yktInfo[i].checked == 'true' || yktInfo[i].checked == true) {

					line += 'checked="' + yktInfo[i].checked + '"'; 
				}
				line += '/></label></td><th style="text-align:left;" title="'+yktInfo[i].proName+'" class="easyui-tooltip">' +formatYKTName(yktInfo[i].proName) + '</th>';
				col = 2;
			} else {
				col++;
				line += '<td><label><input type="checkbox" id="' + id + '" style="width:27px;border:0;" value="' + yktInfo[i].proName + '" ';
				if(yktInfo[i].checked == 'true' || yktInfo[i].checked == true) {
					line += 'checked="' + yktInfo[i].checked + '"'; 
				}
				line += '/></label></td><th style="text-align:left;" title="'+yktInfo[i].proName+'" class="easyui-tooltip">' + formatYKTName(yktInfo[i].proName) + '</th>';
			}
		}
		if(line.length >= 4) {
			line += '</tr>';
			element.append(line);
		}
	
		//从通卡公司名称获取所有的集合
		var editYKTSelectAll = $('#yktInfoContentPayment').find("input[type=checkbox]");
		//业务费率表里面多选
		var editBusRateSelarrrow = $('#yktTblPayment').getGridParam('selarrrow');
		// 临时保存选中的数据
		if (editBusRateSelarrrow) {
			var selectedData = "";
			for (var i = 0; i < editBusRateSelarrrow.length; i++) {
				// 获取业务费率表选中的code
				var rowData = $('#yktTblPayment').getRowData(editBusRateSelarrrow[i]);
				selectedData += rowData.proCode + "_" + rowData.rateCode + "_" + rowData.rateType + "_" + rowData.rate + ";";
			}
			$('#selectedProRateInfoPayment').val(selectedData);
		}
		$.each(editYKTSelectAll, function(index, editYKTElement) {
			$(editYKTElement).attr('checked',false);
			if (editBusRateSelarrrow) {
				for(var i=0; i<editBusRateSelarrrow.length; i++) {
					//获取业务费率表选中的code
						var rowData = $('#yktTblPayment').getRowData(editBusRateSelarrrow[i]);
						if(editYKTElement.id==(rowData.proCode+"_"+rowData.rateCode)){
							$(editYKTElement).attr('checked',true);
							break;
						}
					}
				}
		});
		showDialog('yktDialogPayment');
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}


//通卡公司判断是否初始化选择
function findyktDialogCheckBoxPayment(element){
	if(false){
		//从通卡公司名称获取所有的集合
		var findYKTSelectAll = $('#yktInfoContentPayment').find("input[type=checkbox]");
		//业务费率表里面多选
		var findMerBusRateSelarrrow = $('#yktTblPayment').getGridParam('selarrrow');
		$.each(findYKTSelectAll, function(index, findYKTElement) {
			$(findYKTElement).attr('checked',false);
			if (findMerBusRateSelarrrow) {
				for(var i=0; i<findMerBusRateSelarrrow.length; i++) {
					//获取业务费率表选中的code
						var rowData = $('#yktTblPayment').getRowData(findMerBusRateSelarrrow[i]);
						if(findYKTElement.id==rowData.proCode){
							$(findYKTElement).attr('checked',true);
						}
					}
				}
		});
		showDialog('yktDialogPayment');
	}else{
		//初始化打开选择为空
		var merCode=''; //商户类型CODE
		var merType = $('#merType').combobox('getValue');
		if($('#merParentCode').val() != null && $('#merParentCode').val() != ''){
			merCode = $('#merParentCode').val();
			ddpAjaxCall({
				url : "findYktInfo?rateCode=03&merType=" + merType,
				data : merCode,
				successFn : function(data) {
					if (data.code == "000000") {
						createYKTInfoPayment(element, data);
					} else {
						msgShow(systemWarnLabel, data.message, 'warning');
					}		
				}
			});
		}else{
			merCode = $('#merCode').val();
			ddpAjaxCall({
				url : "findMerProductList?rateCode=03",
				data : merCode,
				successFn : function(data) {
					if (data.code == "000000") {
						createYKTInfoPayment(element, data);
					} else {
						msgShow(systemWarnLabel, data.message, 'warning');
					}		
				}
			});
		}
	}
}
//--------------------- 选择一卡通消费通卡公司  end-----------------------
//------------------------- 勾选通卡公司后点击确定  start------------------------------
function initYktComponentPayment() {
	$('#yktDialogPayment').dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : true,
		width : 600,
		height : 280,
		buttons : [ {
			text : '确定',
			handler : yktHandlerPayment
		}]
	});
	initYKTTblPayment();
}
function yktHandlerPayment() {
	//根据商户类型和上级商户类型判断业务类型和数值是否可编辑
	var merParentCode =$('#merParentCode').val();
	var merType = $('#merType').combobox('getValue');
	var merCode = $('#merCode').val();
	
	// 一卡通消费费率分类可以编辑
	$('#yktTblPayment').setColProp('rateType',{editable: true});
	$('#yktTblPayment').setColProp('rate',{editable: true});
	
	hideDialog('yktDialogPayment');
	var selectedArray = new Array();
	var $selectAll = $('#yktInfoContentPayment').find("input:checkbox:checked");
	var selected = "";
	$.each($selectAll, function(index, element) {
		var idArray = element.id.split("_");
		var proName = $('#' + element.id).val();
		if(selected != "") {
			selected += ",";
		}
		selected += proName;
		selectedArray.push(idArray[0]);
	});
	//判断是否有值
	if(selectedArray.length != 0){
		$('#yktTblLinePayment').show();
	}else{
		$('#yktTblLinePayment').hide();
	}
	
	$('#providerCodePayment').val(selected);
		ddpAjaxCall({
			url : "loadYktInfo?merParentCode="+merParentCode+"&rateCode=03&merType="+merType,
			data : selectedArray,
			successFn : function(data) {
				if (data.code == "000000") {
					var dataArray = data.responseEntity;
					if(dataArray!=null && dataArray.length>0) {
						for(var j=0;j<dataArray.length;j++) {
							var busRate = dataArray[j];
							var selectedProRateInfo = $('#selectedProRateInfoPayment').val();
							if (selectedProRateInfo != null && selectedProRateInfo != "") {
								var proArray = selectedProRateInfo.split(";");
								for (var i = 0; i < proArray.length; i++) {
									var colArray = proArray[i].split("_");
									if(busRate.proCode == colArray[0] && busRate.rateCode==colArray[1]) {
										busRate.rateType = colArray[2];
										busRate.rate = colArray[3];
										break;
									}
								}
							}
						}
					}
					loadJqGridData($('#yktTblPayment'), dataArray);
					$('#yktTblPayment').jqGrid('setGridHeight', 27*dataArray.length);
					selectAllLines('yktTblPayment');
					$('#cb_yktTblPayment').attr('checked','checked');
					$('#cb_yktTblPayment').attr('disabled','disabled');
				} else {
					msgShow(systemWarnLabel, data.message, 'warning');
				}		
			}
		});
}
function initYKTTblPayment() {
	$('#yktTblPayment').jqGrid({
		datatype : "local",
		colNames : ['通卡公司code','通卡公司名称','业务CODE','业务名称','费率分类','数值'],
		colModel : [
		            { name : 'proCode', hidden : true },
		            { name : 'proName', index : 'proName', width : 100, align : 'left', sortable : false },
		            { name : 'rateCode', index : 'rateCode', width : 60, align : 'left', sortable : false, hidden:true },
		            { name : 'rateName', index : 'rateName', width : 100, align : 'left', sortable : false },
					{ name : 'rateType', index : 'rateType', width : 137, align : 'left', sortable : false , editable:true, edittype:"select",
		            	            editoptions: {value:"1:单笔返点金额(元);2:千分比(‰)", 
		            	            		      dataEvents: [{type: 'change',fn: function(e) {$('#yktTblPayment').setCell( $(this).parent().parent().attr('id'), 'rate', 0);}}]
		            	            			 },
			          formatter: function(cellval, el, rowData) {
							if(cellval == 1 || cellval == '1') {
								return '单笔返点金额(元)';
							} else if(cellval == 2 || cellval ==  '2'){
								return '千分比(‰)';
							} else if(cellval == 'undefined' || typeof(cellval) == 'undefined'||cellval == 'null'|| cellval == null || cellval == ''){
								return '千分比(‰)';
							} else {
								return cellval;
							}
						}
					},
					{ name : 'rate', index : 'rate', width : 40, align : 'left', sortable : true , editable : true }
				],
		//sortname : 'proCode',
		height : 150,
		width : 529,
		multiselect: true,
		rowNum : 1000,
		forceFit : true,
	   	cellEdit: true,
	   	cellsubmit: 'clientArray',
		loadComplete: function() {
			$(".jqgrow", $('#yktTblPayment')).each(function (index, row) {
				var $row = $(row);
				$row.find("input:checkbox").attr("checked", "checked");
				$row.find("input:checkbox").attr("disabled", "disabled");
			});
		}
	});
	$('#yktTblPayment').setGridParam({
		//onkeyup="clearNoNum(this)" onkeydown="checkDecimal(this,1,1,9,0,2)" onblur="checkPlusMinus(this)"
		afterEditCell : function(id, name, val, iRow, iCol) {
			//判断费率输入小数点
			$("#" + iRow + "_" + name, "#yktTblPayment").bind('keyup', function() {
				clearNoNum($(this));
			});
			$("#" + iRow + "_" + name, "#yktTblPayment").bind('keydown', function() {
				checkDecimal($(this),1,1,9,0,2);
			});
			$("#" + iRow + "_" + name, "#yktTblPayment").bind('blur', function() {
				clearNoNumOnBlur($(this));
				//checkPlusMinus($(this));
				$('#yktTblPayment').saveCell(iRow, iCol);
			});
		},
	   	afterSaveCell : function(rowid, cellname, value, iRow, iCol) {
			if(cellname == "rate") {
				var rate = $('#yktTblPayment').getCell(rowid, "rate");
				if(isNaN(rate)) {
					$('#yktTblPayment').setCell(rowid, 'rate', 0);
				} else {
					$('#yktTblPayment').setCell(rowid, 'rate',rate);
				
				}
			}
		}
	});
}
//------------------------- 勾选通卡公司后点击确定  start------------------------------
// --------------------------------------- 一卡通消费   end  ----------------------------------





// --------------------------------------- 外接商户 圈存   start  ----------------------------------
//--------------------- 外接商户 圈存 选择通卡公司  start-----------------------
function yktIcLoadPanel(elementStr) {
	var $element = $('#' + elementStr);
	findyktIcLoadDialogCheckBox($element);
}
//通卡公司判断是否初始化选择
function findyktIcLoadDialogCheckBox(element){
	if(false){
		//从通卡公司名称获取所有的集合
		var findYKTSelectAll = $('#yktIcLoad').find("input[type=checkbox]");
		//业务费率表里面多选
		var findMerBusRateSelarrrow = $('#yktIcLoadTbl').getGridParam('selarrrow');
		$.each(findYKTSelectAll, function(index, findYKTElement) {
			$(findYKTElement).attr('checked',false);
			if (findMerBusRateSelarrrow) {
				for(var i=0; i<findMerBusRateSelarrrow.length; i++) {
					//获取业务费率表选中的code
						var rowData = $('#yktIcLoadTbl').getRowData(findMerBusRateSelarrrow[i]);
						if(findYKTElement.id==rowData.proCode){
							$(findYKTElement).attr('checked',true);
						}
					}
				}
		});
		showDialog('yktIcLoadDialog');
	}else{
		//初始化打开选择为空
		var merCode=''; //商户类型CODE
		var merType = $('#merType').combobox('getValue');
		if($('#merParentCode').val() != null && $('#merParentCode').val() != ''){
			merCode = $('#merParentCode').val();
			ddpAjaxCall({
				url : "findYktInfo?rateCode=01&merType=" + merType,
				data : merCode,
				successFn : function(data) {
					if (data.code == "000000") {
						createYktIcLoadInfo(element, data);
					} else {
						msgShow(systemWarnLabel, data.message, 'warning');
					}		
				}
			});
		}else{
			merCode = $('#merCode').val();
			ddpAjaxCall({
				url : "findMerProductList?rateCode=01",
				data : merCode,
				successFn : function(data) {
					if (data.code == "000000") {
						createYktIcLoadInfo(element, data);
					} else {
						msgShow(systemWarnLabel, data.message, 'warning');
					}		
				}
			});
		}
	}
}
function createYktIcLoadInfo(element, data) {
	//清空当前所有的数据
	element.html('');
	var sed = element.find("input[type=checkbox]:checked");	
	if(data.code == "000000") {
		var yktInfo = data.responseEntity;
		var col = 1;
		var line = '<tr>';
		for(var i=0; i<yktInfo.length; i++) {
			var id = yktInfo[i].proCode + "_" + yktInfo[i].rateCode;
			if(col == 5) {
				line += '</tr>';
				element.append(line);
				line = '<tr><td><label><input type="checkbox" id="' + id + '" style="width:27px;border:0;" value="' + yktInfo[i].proName + '"';
				if(yktInfo[i].checked == 'true' || yktInfo[i].checked == true) {

					line += 'checked="' + yktInfo[i].checked + '"'; 
				}
				line += '/></label></td><th style="text-align:left;" title="'+yktInfo[i].proName+'" class="easyui-tooltip">' +formatYKTName(yktInfo[i].proName) + '</th>';
				col = 2;
			} else {
				col++;
				line += '<td><label><input type="checkbox" id="' + id + '" style="width:27px;border:0;" value="' + yktInfo[i].proName + '" ';
				if(yktInfo[i].checked == 'true' || yktInfo[i].checked == true) {
					line += 'checked="' + yktInfo[i].checked + '"'; 
				}
				line += '/></label></td><th style="text-align:left;" title="'+yktInfo[i].proName+'" class="easyui-tooltip">' + formatYKTName(yktInfo[i].proName) + '</th>';
			}
		}
		if(line.length >= 4) {
			line += '</tr>';
			element.append(line);
		}
	
		//从通卡公司名称获取所有的集合
		var editYKTSelectAll = $('#yktIcLoad').find("input[type=checkbox]");
		//业务费率表里面多选
		var editBusRateSelarrrow = $('#yktIcLoadTbl').getGridParam('selarrrow');
		// 临时保存选中的数据
		if (editBusRateSelarrrow) {
			var selectedData = "";
			for (var i = 0; i < editBusRateSelarrrow.length; i++) {
				// 获取业务费率表选中的code
				var rowData = $('#yktIcLoadTbl').getRowData(editBusRateSelarrrow[i]);
				selectedData += rowData.proCode + "_" + rowData.rateCode + "_" + rowData.rateType + "_" + rowData.rate + ";";
			}
			$('#selectedProRateIcLoadInfo').val(selectedData);
		}
		$.each(editYKTSelectAll, function(index, editYKTElement) {
			$(editYKTElement).attr('checked',false);
			if (editBusRateSelarrrow) {
				for(var i=0; i<editBusRateSelarrrow.length; i++) {
					//获取业务费率表选中的code
						var rowData = $('#yktIcLoadTbl').getRowData(editBusRateSelarrrow[i]);
						//因为圈存相当于充值 所以此处改成 01  2015-12-29 JOE
						if(editYKTElement.id==(rowData.proCode+"_"+"01")){
							$(editYKTElement).attr('checked',true);
							break;
						}
					}
				}
		});
		showDialog('yktIcLoadDialog');
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}



//--------------------- 选择一卡通消费通卡公司  end-----------------------
//------------------------- 勾选通卡公司后点击确定  start------------------------------
function initYktIcLoadPanl() {
	$('#yktIcLoadDialog').dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : true,
		width : 600,
		height : 280,
		buttons : [ {
			text : '确定',
			handler : yktIcLoad
		}]
	});
	initYktIcLoadTbl();
}
function yktIcLoad() {
	//根据商户类型和上级商户类型判断业务类型和数值是否可编辑
	var merParentCode =$('#merParentCode').val();
	var merType = $('#merType').combobox('getValue');
	var merCode = $('#merCode').val();
	
	// 圈存费率分类可以编辑
	$('#yktIcLoadTbl').setColProp('rateType',{editable: true});
	$('#yktIcLoadTbl').setColProp('rate',{editable: true});
	
	hideDialog('yktIcLoadDialog');
	var selectedArray = new Array();
	var $selectAll = $('#yktIcLoad').find("input:checkbox:checked");
	var selected = "";
	$.each($selectAll, function(index, element) {
		var idArray = element.id.split("_");
		var proName = $('#' + element.id).val();
		if(selected != "") {
			selected += ",";
		}
		selected += proName;
		selectedArray.push(idArray[0]);
	});
	//判断是否有值
	if(selectedArray.length != 0){
		$('#yktIcLoadTblLine').show();
	}else{
		$('#yktIcLoadTblLine').hide();
	}
	
	$('#icLoad').val(selected);
		ddpAjaxCall({
			url : "loadYktInfo?merParentCode="+merParentCode+"&rateCode=01&merType="+merType,
			data : selectedArray,
			successFn : function(data) {
				if (data.code == "000000") {
					var dataArray = data.responseEntity;
					if(dataArray!=null && dataArray.length>0) {
						for(var j=0;j<dataArray.length;j++) {
							var busRate = dataArray[j];
							if(busRate.rateCode=='01'){
								busRate.rateName = '圈存充值';
								busRate.rateCode = '04';
							}
							var selectedProRateInfo = $('#selectedProRateIcLoadInfo').val();
							if (selectedProRateInfo != null && selectedProRateInfo != "") {
								var proArray = selectedProRateInfo.split(";");
								for (var i = 0; i < proArray.length; i++) {
									var colArray = proArray[i].split("_");
									if(busRate.proCode == colArray[0] && busRate.rateCode==colArray[1]) {
										busRate.rateType = colArray[2];
										busRate.rate = colArray[3];
										break;
									}
								}
							}
							
						}
					}
					loadJqGridData($('#yktIcLoadTbl'), dataArray);
					$('#yktIcLoadTbl').jqGrid('setGridHeight', 27*dataArray.length);
					selectAllLines('yktIcLoadTbl');
					$('#cb_yktIcLoadTbl').attr('checked','checked');
					$('#cb_yktIcLoadTbl').attr('disabled','disabled');
				} else {
					msgShow(systemWarnLabel, data.message, 'warning');
				}		
			}
		});
}


function initYktIcLoadTbl() {
	$('#yktIcLoadTbl').jqGrid({
		datatype : "local",
		colNames : ['通卡公司code','通卡公司名称','业务CODE','业务名称','费率分类','数值'],
		colModel : [
		            { name : 'proCode', hidden : true },
		            { name : 'proName', index : 'proName', width : 100, align : 'left', sortable : false },
		            { name : 'rateCode', index : 'rateCode', width : 60, align : 'left', sortable : false, hidden:true },
		            { name : 'rateName', index : 'rateName', width : 100, align : 'left', sortable : false },
					{ name : 'rateType', index : 'rateType', width : 137, align : 'left', sortable : false , editable:true, edittype:"select",
		            	            editoptions: {value:"1:单笔返点金额(元);2:千分比(‰)", 
		            	            		      dataEvents: [{type: 'change',fn: function(e) {$('#yktTblPayment').setCell( $(this).parent().parent().attr('id'), 'rate', 0);}}]
		            	            			 },
			          formatter: function(cellval, el, rowData) {
							if(cellval == 1 || cellval == '1') {
								return '单笔返点金额(元)';
							} else if(cellval == 2 || cellval ==  '2'){
								return '千分比(‰)';
							} else if(cellval == 'undefined' || typeof(cellval) == 'undefined'||cellval == 'null'|| cellval == null || cellval == ''){
								return '千分比(‰)';
							} else {
								return cellval;
							}
						}
					},
					{ name : 'rate', index : 'rate', width : 40, align : 'left', sortable : true , editable : true }
				],
		//sortname : 'proCode',
		height : 150,
		width : 529,
		multiselect: true,
		rowNum : 1000,
		forceFit : true,
	   	cellEdit: true,
	   	cellsubmit: 'clientArray',
		loadComplete: function() {
			$(".jqgrow", $('#yktIcLoadTbl')).each(function (index, row) {
				var $row = $(row);
				$row.find("input:checkbox").attr("checked", "checked");
				$row.find("input:checkbox").attr("disabled", "disabled");
			});
		}
	});
	$('#yktIcLoadTbl').setGridParam({
		afterEditCell : function(id, name, val, iRow, iCol) {
			//判断费率输入小数点
			$("#" + iRow + "_" + name, "#yktIcLoadTbl").bind('keyup', function() {
				clearNoNum($(this));
			});
			$("#" + iRow + "_" + name, "#yktIcLoadTbl").bind('keydown', function() {
				checkDecimal($(this),1,1,9,0,2);
			});
			$("#" + iRow + "_" + name, "#yktIcLoadTbl").bind('blur', function() {
				clearNoNumOnBlur($(this));
				$('#yktIcLoadTbl').saveCell(iRow, iCol);
			});
		},
	   	afterSaveCell : function(rowid, cellname, value, iRow, iCol) {
			if(cellname == "rate") {
				var rate = $('#yktIcLoadTbl').getCell(rowid, "rate");
				if(isNaN(rate)) {
					$('#yktIcLoadTbl').setCell(rowid, 'rate', 0);
				} else {
					$('#yktIcLoadTbl').setCell(rowid, 'rate',rate);
				
				}
			}
		}
	});
}
//------------------------- 勾选通卡公司后点击确定  start------------------------------
// --------------------------------------- 外接商户 圈存   end  ----------------------------------