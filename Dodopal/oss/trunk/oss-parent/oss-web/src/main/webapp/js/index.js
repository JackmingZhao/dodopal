var areaData = {};

$(function() {
	tabClose();
	tabCloseEven();
	initGlobalWaitingDialog();
	var $firstMenu = $('#menuPanle a:first');
	if($firstMenu) {
		$firstMenu.click();
	}
	loadAllProvinces();
	initUpdatePWD();
});

function loadAllProvinces() {
	ddpAjaxCall({
		url : "loadAllProvinces",
		successFn : function(data) {
			if(data.code == "000000") {
				areaData = data.responseEntity;
//				areaData.unshift({cityCode:'',cityName:'--请选择--'});
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});
}

function loadMenu(menuId) {
	var $allMenus = $('#menuPanle a');
	$.each($allMenus, function(index, element) {	
		$(element).removeClass();
	});
	$('#' + menuId).addClass('current-nav');
	initMenu();
	// 初始化导航菜单数据
	ddpAjaxCall({
		url : "loadAccordionMenus",
		data : menuId,
		successFn : buildContextMenu
	});
}

function initMenu() {
	$('#west').empty();
	$('#west').append("<div id='nav' class='easyui-accordion'></div>");
	// 设置导航菜单的动态效果
	$("#nav").accordion({
		animate : true,
		fit : true,
		border : false
	});
}

function buildContextMenu(data) {
	// 遍历菜单数据，构建导航菜单以及子菜单
	if (data.code == "000000") {
		// 菜单树处理
		$.each(data.responseEntity, function(index, element) {
			if (element && element.children) {
				var menuList = "<ul id='treeMenu-" + element.id + "' style='margin:5px;'></ul>";
				$('#nav').accordion('add', {
					title : element.text,
					content : menuList,
					iconCls : element.iconCls
				});
				$('#treeMenu-' + element.id).tree({
					data : element.children,
					lines : true,
					onClick : function(node) {
						if ($(node).tree('isLeaf', node.target)) {
							var url = node.attributes.url; 
							if(url && url != 'null' && url != '') {
								showGlobalWaitingDialog();
								var tabTitle = node.text;
								
								var menuId = node.id;
								var menuCode = node.attributes.code;
								var icon = node.iconCls;
								addTab(tabTitle, url, icon, menuCode);
								// close waiting dialog
								var timer = null;
								var time = 0;
								timer = setInterval(function() {
									if (time > 300) {
										clearInterval(timer);
										closeGlobalWaitingDialog();
									} else {
										time = time + 50;
									}
								}, 50);
							} else {
								//如果程序设置正确 这里永远不应该走到这一步，万一走到这一步，说明程序开发或部署有问题。
								msgShow(systemInfoLabel, "未找到对应的url,如需帮助请联系管理员.", 'warning');
							}
						}
					}
				});
			}
		});
	} else {
		msgShow(systemInfoLabel, data.messagae, 'warning');
	}
}
//查看个人资料
function viewMyData(){
	addTab("我的资料", "systems/system/myData", "icon-system-user", "system.myData");
}
function initUpdatePWD() {
	$('#viewMerchantUser').dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : false,
		width : 680,
		height : 300
	});
}


function addTab(subtitle, url, icon, menuCode) {
	if (!$('#tabs').tabs('exists', subtitle)) {
		$('#tabs').tabs('add', {
			id : menuCode,
			title : subtitle,
			content : createFrame(url, menuCode),
			closable : true,
			icon : icon
		});
	} else {
		$('#tabs').tabs('select', subtitle);
		var selectedTab = $('#tabs').tabs('getSelected');
		$('#tabs').tabs('update', {
			tab: selectedTab,
			options: {
				content : createFrame(url, menuCode)
			}
		});
	}
	tabClose();
}

function createFrame(url, menuCode) {
	return '<iframe scrolling="auto" frameborder="0" id="' + menuCode
			+ '" src="' + url + '" style="width:100%;height:100%;"></iframe>';
}

function tabClose() {
	// 双击关闭TAB选项卡
	$(".tabs-inner").dblclick(function() {
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close', subtitle);
	});

	// 为选项卡绑定右键
	$(".tabs-inner").bind('contextmenu', function(e) {
		$('#tabsMenu').menu('show', {
			left : e.pageX,
			top : e.pageY
		});

		var subtitle = $(this).children(".tabs-closable").text();

		$('#tabsMenu').data("currtab", subtitle);
		$('#tabs').tabs('select', subtitle);
		return false;
	});
}
// 绑定右键菜单事件
function tabCloseEven() {
	// 刷新
	$('#tabsMenu-tabupdate').click(function() {
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		var menuCode = $(currTab.panel('options').content).attr('menuCode');
		$('#tabs').tabs('update', {
			tab : currTab,
			options : {
				content : createFrame(url, menuCode)
			}
		});
	});

	// 关闭当前
	$('#tabsMenu-tabclose').click(function() {
		var currtab_title = $('#tabsMenu').data("currtab");
		$('#tabs').tabs('close', currtab_title);
	});

	// 全部关闭
	$('#tabsMenu-tabcloseall').click(function() {
		$('.tabs-inner span').each(function(i, n) {
			var t = $(n).text();
			$('#tabs').tabs('close', t);
		});
	});

	// 关闭除当前之外的TAB
	$('#tabsMenu-tabcloseother').click(function() {
		$('#tabsMenu-tabcloseright').click();
		$('#tabsMenu-tabcloseleft').click();
	});

	// 关闭当前右侧的TAB
	$('#tabsMenu-tabcloseright').click(function() {
		var nextall = $('.tabs-selected').nextAll();
		if (nextall.length == 0) {
			return false;
		}
		nextall.each(function(i, n) {
			var t = $('a:eq(0) span', $(n)).text();
			$('#tabs').tabs('close', t);
		});
		return false;
	});

	// 关闭当前左侧的TAB
	$('#tabsMenu-tabcloseleft').click(function() {
		var prevall = $('.tabs-selected').prevAll();
		if (prevall.length == 0) {
			return false;
		}
		prevall.each(function(i, n) {
			var t = $('a:eq(0) span', $(n)).text();
			$('#tabs').tabs('close', t);
		});
		return false;
	});

	// 退出
	$("#tabsMenu-exit").click(function() {
		$('#tabsMenu').menu('hide');
	});
}

// 弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
	$.messager.alert(title, msgString, msgType);
}

function initGlobalWaitingDialog() {
	$('#globalWaitingDialog').dialog({
		collapsible: false,
		modal: true,
		closed: true,
		closable: false,
		width: 350,  
	    height: 72,
	    title : "正在处理中，请稍后..."
	});
}


function showGlobalWaitingDialog(message) {
	if(message && message !=null && message.length > 0) {
		$('#globalWaitingDialog').dialog({
		    title : message
		});
	}
	$('#globalWaitingDialog').show().dialog('open');
}

function closeGlobalWaitingDialog() {
	$('#globalWaitingDialog').dialog({
	    title : inProcessMsg
	});
	$('#globalWaitingDialog').show().dialog('close');
}