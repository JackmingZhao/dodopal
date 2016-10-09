<link rel="shortcut icon" type="image/x-icon" href="${base}/favicon.ico"  media="screen"/>

<!-- Jquery Js-->
<script type='text/javascript' src='${scriptUrl}/common/jquery-easyui-1.3.1/jquery-1.8.0.min.js'></script>
<script type='text/javascript' src='${scriptUrl}/common/jquery.ui.widget.min.js'></script>
<script>
$.base='${base}';
function clickLabels3(){

}
</script>
<script type='text/javascript' src='${scriptUrl}/util/util.js'> </script>
<script type='text/javascript' src='${scriptUrl}/util/ddpValidationBox.js'> </script>
<script type='text/javascript' src='${scriptUrl}/util/constant.js'> </script>
<script type='text/javascript' src='${scriptUrl}/util/md5.js'> </script>
<script type='text/javascript' src='${scriptUrl}/util/date.js'> </script>

<script type='text/javascript' src='${base}/js/portalUtil.js'></script>
<script type="text/javascript" src="${base}/js/portalValidationHandler.js"></script>
<script type="text/javascript" src="${base}/js/common/permission.js"></script>
<script type="text/javascript" src="${base}/js/common/warnMessageCom.js"></script>
<script type="text/javascript" src="${base}/js/common/select.js"></script>
<script type="text/javascript" src="${base}/js/common/ddpDdic.js"></script>
<script type="text/javascript" src="${base}/js/common/area.js"></script>
<script type="text/javascript" src="${scriptUrl}/component/ddpMessager.js"></script>
<script type='text/javascript' src='${scriptUrl}/util/textareaMaxlength.js'> </script>


<link href="${styleUrl}/portal/css/base.css" rel="stylesheet" type="text/css">
<link href="${styleUrl}/portal/css/index.css" rel="stylesheet" type="text/css">
<link href="${styleUrl}/portal/css/portal.css" rel="stylesheet" type="text/css">


<!--[if IE]>
<script type='text/javascript' src='${base}/js/json2.js'> </script>
<script type='text/javascript' src='${base}/js/common/ie_compatiable.js'> </script>
<![endif]-->


<style>
	.oddRow{
		
	}
	.evenRow{
		background-color: rgb(246, 250, 254);
	}
</style>


<body>
	<div class="pop-win" js="cuowuBox" style="display: none;">
		<div class="bg-win"></div>
		<div class="pop-bor"></div>
		<div class="pop-box">
			<h3>信息提示</h3>
			<div class="innerBox">
				<div class="tips">
	            <div class="alert-icons alert-icons3"></div>
	            <span>错误操作！</span></div>
				<ul class="ul-btn ul-btn1">
					<li><a href="javascript:;" class="pop-borange" onclick="popclo(this)">确认</a></li>
					
				</ul>
			</div>
		</div>
	</div>
	<div class="pop-win" js="jinggaoBox" style="display: none;">
		<div class="bg-win"></div>
		<div class="pop-bor"></div>
		<div class="pop-box">
			<h3>信息提示</h3>
			<div class="innerBox">
				<div class="tips">
	            <div class="alert-icons alert-icons2"></div>
	            <span>将删除整条信息！</span></div>
				<ul class="ul-btn ul-btn1">
					<li><a href="javascript:;" class="pop-borange" onclick="popclo(this)">确认</a></li>
					
				</ul>
			</div>
		</div>
	</div>
	<div class="pop-win" js="confirmBox" style="display: none;">
		<div class="bg-win"></div>
		<div class="pop-bor"></div>
		<div class="pop-box">
			<h3>信息提示</h3>
			<div class="innerBox">
				<div class="tips">
	            <div class="alert-icons alert-icons1"></div>
	            <span>确认启用？</span></div>
				<ul class="ul-btn">
					<li><a href="javascript:;" class="pop-borange" onclick="popclo(this)">确认</a></li>
					<li><a href="javascript:;" js="clopop" class="pop-bgrange">取消</a></li>
				</ul>
			</div>
		</div>
	</div>
</body>