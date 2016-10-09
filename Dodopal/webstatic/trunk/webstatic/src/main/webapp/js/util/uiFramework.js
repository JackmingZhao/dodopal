var easyuiPanelOnMove = function(left, top) {// 防止超出浏览器边界 
	if (left < 0) {
		$(this).panel('move', {
			left : 1
		});  
	}
	if (top < 0) {
		$(this).panel('move', {
			top : 1
		});
	}
};
$.fn.panel.defaults.onMove = easyuiPanelOnMove;
$.fn.window.defaults.onMove = easyuiPanelOnMove;
$.fn.dialog.defaults.onMove = easyuiPanelOnMove;


/**
 * 改变窗口大小的时候自动根据iframe大小设置jqGrid列表宽度和高度 参数说明：{ enableAutoResize :
 * 是否开启自动高度和宽度调整开关 dataGrid : jqGrid数据列表的ID callback : 计算完dataGrid需要的高度和宽度后的回调函数
 * width : 默认为iframe的宽度，如果指定则设置为指定的宽度 height : 默认为iframe的高度，如果指定则设置为指定的高度
 * beforeAutoResize : 窗口大小调整时自动设置之前 afterAutoResize : 窗口大小调整时自动设置之后 }
 */
// need to do more test and remove unused code later.

//-- 浏览器工具 --//
function isIE() {
	return $.browser.msie;
}

function isChrome() {
	return $.browser.webkit;
}

function isMozila() {
	return $.browser.mozilla;
}

function autoResize (options) {
	var defaults = {
		enableAutoResize : true,
		beforeAutoResize : null,
		afterAutoResize : null,
		dialogs : null,
		otherHeight:null
	};
	options = $.extend({}, defaults, options);

	// 第一次调用
	var size = getWidthAndHeigh();
	if ($.isFunction(options.callback)) {
		options.callback(size);
		//setToolbarHeight();
	}

	// 窗口大小改变的时候
	if (options.enableAutoResize === true) {
		if ($.isFunction(options.beforeAutoResize)) {
			options.beforeAutoResize();
		}
		window.onresize = function() {
			var size = getWidthAndHeigh(true);
			
			if($.isArray(options.dataGrid)){
				$(options.dataGrid).each(function(i,v){
					if(typeof v== 'string'){
						$(v).jqGrid('setGridHeight', size.height).jqGrid('setGridWidth', size.width);
					}
					if(typeof v== 'object'){
						$(v.selector).jqGrid('setGridHeight', (size.height - v.offsetHeight)).jqGrid('setGridWidth', (size.width - v.offsetWidth));
					}
				});
			}else{
				if(typeof options.dataGrid== 'string'){
					$(options.dataGrid).jqGrid('setGridHeight', size.height).jqGrid('setGridWidth', size.width);
				}
				if(typeof options.dataGrid== 'object'){
					$(options.dataGrid.selector).jqGrid('setGridHeight', (size.height - options.dataGrid.offsetHeight)).jqGrid('setGridWidth', (size.width - options.dataGrid.offsetWidth)).trigger('reloadGrid'); ;
				}
			};
			
			//setToolbarHeight();
			if ($.isFunction(options.afterAutoResize)) {
				options.afterAutoResize(size);
			}
			resetDialog(options.dialogs);
		};
	}

	function resetDialog(dialogs) {
		if(dialogs) {
			$.each(dialogs, function(index, element) {
				if( $('#'+ element).dialog('options').closed != true) {
					$('#'+ element).show().dialog('open', {
						maximized:true
			    	});
				}
			});
		}
	}
	
	// 根据浏览器不同设置工具栏的高度
	/*
	function setToolbarHeight() {
		// 根据浏览器不同设置工具栏的高度
		if (isIE() && options.toolbarHeight) {
			if (options.toolbarHeight.top && options.toolbarHeight.top.ie) {
				$('#t_list').height(options.toolbarHeight.top.ie);
			}
			if (options.toolbarHeight.bottom && options.toolbarHeight.bottom.ie) {
				$('#tb_list').height(options.toolbarHeight.bottom.ie);
			}
		}
	}
	*/

	// 获取iframe大小
	function getWidthAndHeigh(resize) {
		var hasToolbar = !options.toolbar ? false : options.toolbar[0];
		var toolbarType = [];
		if (hasToolbar) {
			toolbarType = options.toolbar[1];
			if (!toolbarType) {
				alert('请设置工具栏的属性，toolbar ： [true, [top, both]]');
			}
		}
		var iframeHeight = !options.height ? document.documentElement.clientHeight : options.height;
		var iframeWidth = !options.width ? document.documentElement.clientWidth : options.width;
		// chrome
		if (isChrome()) {
			if (hasToolbar) {
				if (toolbarType == 'top') {
					iframeWidth -= 8;
					iframeHeight -= 128;
				} else if (toolbarType == 'both') {
					iframeWidth -= 14;
					iframeHeight -= 140;
				}
			} else {
				iframeWidth -= 13;
				iframeHeight -= 87;
			}
		}
		// firefox
		else if (isMozila()) {
			if (hasToolbar) {
				if (toolbarType == 'top') {
					iframeWidth -= 10;
					iframeHeight -= 122;
				} else if (toolbarType == 'both') {
					iframeWidth -= 12;
					iframeHeight -= 145;
				}
			} else {
				iframeWidth -= 10;
				iframeHeight -= 89;
			}
		}
		// IE
		else {
			if (hasToolbar) {
				if (toolbarType == 'top') {
					iframeHeight -= 98;
					iframeWidth -= 4;
				} else if (toolbarType == 'both') {
					iframeWidth -= 14;
					iframeHeight -= 151;
				}
			} else {
				iframeWidth -= 5;
				iframeHeight -= 77;
			}
		}
		if(options.otherHeight) {
			iframeHeight -= options.otherHeight;
		}
		if(options.otherWidth) {
			iframeWidth -= options.otherWidth;
		}
		return {
			width : iframeWidth,
			height : iframeHeight
		};
	}
}



Overload = function(fn_objs){
    var is_match = function(x,y){
        if(x==y)return true;
        if(x.indexOf("*")==-1)return false;
     
        var x_arr = x.split(","),y_arr = y.split(",");
        if(x_arr.length != y_arr.length)return false;
     
        while(x_arr.length){
            var x_first =  x_arr.shift(),y_first = y_arr.shift();
            if(x_first!="*" && x_first!=y_first)return false;
        }
        return true;
    };
    var ret = function(){
        var args = arguments
        ,args_len = args.length
        ,args_types=[]
        ,args_type
        ,fn_objs = args.callee._fn_objs
        ,match_fn = function(){};
         
        for(var i=0;i<args_len;i++){
            var type = typeof args[i];
            type=="object" && (args[i].length>-1) && (type="array");
            args_types.push(type);
        }
        args_type = args_types.join(",");
        for(var k in fn_objs){
            if(is_match(k,args_type)){
                match_fn = fn_objs[k];
                break;
            }
        }
        return match_fn.apply(this,args);
    };
    ret._fn_objs = fn_objs;
    return ret;
};
String.prototype.format = Overload({
    "array" : function(params){
        var reg = /{(\d+)}/gm;
        return this.replace(reg,function(match,name){
            return params[~~name];
        });
    },
    "object" : function(param){
        var reg = /{([^{}]+)}/gm;
        return this.replace(reg,function(match,name){
            return param[name];
        });
    }
});