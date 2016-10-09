<script type="text/javascript">
$(function() {
	initMainComponent();
	initDetailComponent();
	parent.closeGlobalWaitingDialog();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
});

function initMainComponent() {
	autoResize({
		dataGrid : {
			selector : '#dataTbl',
			offsetHeight : 90,
			offsetWidth : 0
		},
		callback : initDataTbl,
		toolbar : [ true, 'top' ],
		dialogs : [ 'dataDialog' ]
	});
}

function initDetailComponent() {
	initDataDialog();
}

function initDataTbl(size) {
	var option = {
			datatype : function (pdata) {
				findData();
		    },
		    colNames : [ 'userId','用户名', '姓名', '性别', '手机号码', '电子邮箱', '省份','城市','来源','开户时间'],
			colModel : [ 
			{
				name : 'userId',
				index : 'userId',
				hidden : true
			}, {
				name : 'merUserName',
				index : 'merUserName',
				width : 100,
				align : 'left',
				sortable : false
			}, {
				name : 'merUserNickName',
				index : 'merUserNickName',
				width : 100,
				align : 'left',
				sortable : false
			}, {
				name : 'merUserSex',
				index : 'merUserSex',
				width : 100,
				align : 'left',
				sortable : false,
				formatter: function(cellval, el, rowData) {
				
					if(cellval == '0') {return '男'} 
					else if(cellval == '1') {return '女'	} 
					else { return ''};
				}
			}, {
				name : 'merUserMobile',
				index : 'merUserMobile',
				width : 100,
				align : 'left',
				sortable : false
			}, {
				name : 'merUserEmail',
				index : 'merUserEmail',
				width : 100,
				align : 'left',
				sortable : false
			},
			{
			name : 'merUserProName',
			index : 'merUserProName',
			width : 70,
			align : 'left',
			sortable : false
			},{
			name : 'merUserCityName',
			index : 'merUserCityName',
			width : 70,
			align : 'left',
			sortable : false},{
			name : 'merUserSourceView',
			index : 'merUserSourceView',
			width : 70,
			align : 'left',
			sortable : false
		
		},{
				name : 'createDate',
				index : 'createDate',
				width : 100,
				align : 'left',
				sortable : false,
				formatter: cellDateFormatter
			
			}],
			//caption : "用户列表",
			//sortname : 'name',
			//multiselect: true,
			height : size.height - 70,
			width : size.width,
			pager : '#dataTblPagination',			
			toolbar : [ true, "top" ]
	};
	option = $.extend(option, jqgrid_server_opts);
	$('#dataTbl').jqGrid(option);
	$("#t_dataTbl").append($('#dataTblToolbar'));
}
function initDataDialog() {
	$('#dataDialog').dialog({
		collapsible : true,
		modal : true,
		closed : true,
		closable : false
	});
}

function showDataDialog() {
	$('#dataDialog').show().dialog('open');
}

function hideDataDialog() {
	$('#dataDialog').hide().dialog('close');
}


function findUser(){
    var selrow = $("#dataTbl").getGridParam('selrow');
	if (selrow) {
		var rowData = $('#dataTbl').getRowData(selrow);
		var userId = rowData.userId;
		//window.location.href ="findMerUser.htm?userId="+userId;
		
		ddpAjaxCall({
				url : "findUser",
				data : userId,
				successFn : loadUser
			});
			
		showDataDialog();
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function resetPwd(){
    var selrow = $("#dataTbl").getGridParam('selrow');
	if (selrow) {
		var rowData = $('#dataTbl').getRowData(selrow);
		var userId = rowData.userId;
		var merUserNickName = rowData.merUserNickName;
		var merUserMobile = rowData.merUserMobile;
		$.messager.confirm(systemConfirmLabel,"系统将随机生成一个密码，重置后将以短信形式发送到该用户手机中，您确定重置此用户密码吗?</br><table><tr><th>姓名:</th><td>"+merUserNickName+"</td></tr><tr><th>手机号码:</th><td>"+merUserMobile+"</td></tr></table>", function(r) {
			if (r) {
				ddpAjaxCall({
				url : "resetPwd",
				data : userId,
				successFn : function(data) {
					if (data.code == "000000") {
						msgShow(systemWarnLabel, data.message, 'warning');
					}else{
						msgShow(systemWarnLabel, data.code + ":" + data.message , 'warning');
					}	
				}
				});
				
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
	
}


function loadUser(data){
	var user = data.responseEntity;
	$("#VmerUserMobile").html(user.merUserMobile);
	$("#VmerUserName").html(user.merUserName);
	$("#VmerUserNickName").html(user.merUserNickName);
	$("#VmerUserIdentityNumber").html(user.merUserIdentityNumber);
	$("#VmerUserEmail").html(user.merUserEmail);
	$("#VmerUserSex").html(user.merUserSexView);
	$("#VmerUserAdds").html(htmlTagFormat(user.merUserAdds));
	$("#VcreateDate").html(user.createDateView);
	$('#educationView').html(user.educationView);
	$('#isMarriedView').html(user.isMarriedView);
	$('#birthdayView').html(user.birthday);
	$('#MerUserProName').html(user.merUserProName);
	$('#MerUserCityName').html(user.merUserCityName);
	$('#incomeView').html(Number(user.income/100)+"元");

}

function afterAddUser(data) {
	if(data.code == "000000") {
		hideDataDialog();
		findData();
	} else {
		msgShow(systemWarnLabel, data.code+":" + data.message, 'warning');
	}
}

function afterDeleteUser(data) {
	if(data.code == "000000") {
		findUser();
	} else {
		msgShow(systemWarnLabel, data.code+":" + data.message, 'warning');
	}
}

function findData(defaultPageNo) {
	var merUser = {
	    merUserType : '0',
	    merUserName : escapePeculiar($.trim($('#merUserName').val())),
		merUserMobile : escapePeculiar($.trim($('#merUserMobile').val())),
		merUserNickName : escapePeculiar($.trim($('#merUserNickName').val())),	
		createDateStart : $('#createDateStart').val(),
		createDateEnd : $('#createDateEnd').val(),	
		merUserSex : $('#merUserSex').combobox('getValue'),
		page : getJqgridPage('dataTbl', defaultPageNo)
	}
	ddpAjaxCall({
		url : "findMerUsersByPage",
		data : merUser,
		successFn : function(data) {
			if (data.code == "000000") {			
				loadJqGridPageData($('#dataTbl'), data.responseEntity);
			}else{
			msgShow(systemWarnLabel, data.code+":" + data.message, 'warning');  
			}		
		}
	});
}

function resetDataQuery() {	
	$('#findTB').find('input,select').each(function(){
      $(this).val('');
    });
}

function retFindJosn(){
  //  var str = "";     
  //  $('#findTB').find('input,select').each(function(){
  //    $this = $(this);      
  //    var name = $this.attr('name');
  //    str = str +'"'+ name + '" : "' + $this.val() + '",'     
  //  });
  //  str = str.substr(0,str.length-1);
  //  var retJson = '[{ ' + str + ' }]';
    
   //alert(retJson);
    
   // var jsonStr = JSON.stringify(data.responseEntity);
   //		alert(jsonStr);
   //	  var json = JSON.parse(retJson);
	  window.location.href ="http://localhost:8099/users-web/index";
			
			  
  //  return retJson;
}
//数据导出
var expConfInfo = {
	"btnId"			: "btnSelExcCol", 					
	"typeSelStr"	: "MerchantUserBean", 	
	"toUrl"			: "exportExcelMerUserListControl" ,
	"parDiaHeight"  : "200"
};
var filterConStr = [
		{'name':'merUserName', 'value': "escapePeculiar($.trim($('#merUserName').val()))"},	
		{'name':'merUserNickName','value': "escapePeculiar($.trim($('#merUserNickName').val()))"},
		{'name':'createDateStart', 'value': "$('#createDateStart').val()"},	
		{'name':'createDateEnd','value': "$('#createDateEnd').val()"},
		{'name':'merUserMobile', 'value': "escapePeculiar($.trim($('#merUserMobile').val()))"},	
		{'name':'merUserSex','value': "$('#merUserSex').combobox('getValue')"}
		
	];
</script>