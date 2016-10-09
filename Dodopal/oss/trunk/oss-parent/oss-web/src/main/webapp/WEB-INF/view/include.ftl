<link rel="shortcut icon" type="image/x-icon" href="${base}/favicon.ico"  media="screen"/>
<!-- JQGrid Css-->
<link rel="stylesheet" type="text/css"  href="${scriptUrl}/common/jqGrid-4.3.2/css/cupertino/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="${scriptUrl}/common/jqGrid-4.3.2/css/ui.jqgrid.css" />

<!-- EasyUI Css-->
<link id="easyui_css" rel='stylesheet' type='text/css' href='${scriptUrl}/common/jquery-easyui-1.3.1/themes/cupertino/easyui.css' />
<link rel='stylesheet' type='text/css' href='${scriptUrl}/common/jquery-easyui-1.3.1/themes/icon.css' />

<!-- Mydate 97 css-->
<link rel='stylesheet' type='text/css' href='${scriptUrl}/common/datepicker/skin/WdatePicker.css' />
<link rel='stylesheet' type='text/css' href='${scriptUrl}/common/datepicker/skin/default/datepicker.css' />


<!-- app css -->
<link href="${base}/css/appIcons.css" rel="stylesheet" type="text/css">
<link href="${base}/css/oss.css" rel="stylesheet" type="text/css">

<!-- EasyUI Js-->
<script type='text/javascript' src='${scriptUrl}/common/jquery-easyui-1.3.1/jquery-1.8.0.min.js'></script>
<script type='text/javascript' src='${scriptUrl}/common/jquery-easyui-1.3.1/jquery.easyui.min.js'></script>
<script type="text/javascript" src="${scriptUrl}/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>

<!-- JQGrid Js-->
<script type="text/javascript" src="${scriptUrl}/common/jqGrid-4.3.2/js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script type="text/javascript" src="${scriptUrl}/common/jqGrid-4.3.2/js/jquery.jqGrid.min.js"></script>
<!--<script type='text/javascript' src='${scriptUrl}/common/jLinq/jLinq-2.2.1.js'> </script> -->
<script type='text/javascript' src='${scriptUrl}/util/jqgrid_util.js'> </script>

<!-- datepicker-->
<script type="text/javascript" src="${scriptUrl}/common/datepicker/WdatePicker.js"></script>

<!--Util JS -->
<script type='text/javascript' src='${scriptUrl}/util/uiAction.js'> </script>
<script type='text/javascript' src='${scriptUrl}/util/date.js'> </script>
<script type='text/javascript' src='${scriptUrl}/util/formatter.js'> </script>
<script type='text/javascript' src='${scriptUrl}/util/util.js'> </script>
<script type='text/javascript' src='${scriptUrl}/util/uiFramework.js'> </script>
<script type='text/javascript' src='${scriptUrl}/util/constant.js'> </script>
<script type='text/javascript' src='${scriptUrl}/util/md5.js'> </script>
<script type='text/javascript' src='${scriptUrl}/util/ddpValidationBox.js'> </script>
<script type='text/javascript' src='${scriptUrl}/util/textareaMaxlength.js'> </script>
<script type='text/javascript' src='${base}/js/common/ossValidationHandler.js'> </script>


<script>
	$.jgrid.no_legacy_api = true;
	$.jgrid.useJSON = true;
    $.base ='${base}';
	var systemWarnLabel = '警告';
	var systemInfoLabel = '信息';
	var systemConfirmLabel = '确认';
	var attachLabel = '附件';
	var yesLabel = '是';
	var noLabel = '否';
	var submitLabel = '提交';
	var cancelLabel = '取消';
	var saveLabel = '保存';
	var deleteLabel = '删除';
	var commnetsLabel = '备注';
	var deleteConfirmLabel = '确定要删除所选择的记录?';
	var unSelectLabel = '请先选择需要操作的记录!';
	var onlyAllowOneSelectLabel = '只能选择一个需要操作的记录!';
	var inProcessMsg = '正在处理中，请稍后...';
	var resetPasswdSuccessLabel =  '重置密码成功';
	var globalAreaComboboxWidth = 100;
	var globalComboboxWidth = 133;
		
	//fix IE 8 console.log is not defined issue when develop tool is not open 
	if (typeof console === "undefined" || typeof console.log === "undefined") {
    	console = {};
    	console.log = function() {};
   	}
	
	//fix IE 8 array indexOf method
	if (!Array.prototype.indexOf) { 
	    Array.prototype.indexOf = function(obj, start) {
	         for (var i = (start || 0), j = this.length; i < j; i++) {
	             if (this[i] === obj) { return i; }
	         }
	         return -1;
	    }
	}
	
	Array.prototype.distinct = function(){
		var arr = [],
		     len = this.length;

		for ( var i = 0; i < len; i++ ){
			for( var j = i+1; j < len; j++ ){
				if( this[i] === this[j] ){
					j = ++i;
				}
			}
			arr.push( this[i] );
		}
		return arr;
	};

	if (!('lastIndexOf' in Array.prototype)) {
    	Array.prototype.lastIndexOf= function(find, i /*opt*/) {
        if (i===undefined) i= this.length-1;
        if (i<0) i+= this.length;
        if (i>this.length-1) i= this.length-1;
        for (i++; i-->0;) /* i++ because from-argument is sadly inclusive */
            if (i in this && this[i]===find)
                return i;
        return -1;
	    };
	}
	
	//这是为了解决 MSIE checkbox onchange 事件只有在失去焦点的时候才能触发。 
	if ($.browser.msie) {  
	  $(function() {  
	    $('input:radio, input:checkbox').click(function() {  
	      this.blur();  
	      this.focus();  
	    });  
	  });  
	} 
	
	$.extend($.jgrid.defaults, { viewrecords : true, rownumbers:true , gridview : true});
	
</script>