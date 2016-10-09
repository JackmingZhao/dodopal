<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>商户信息-POS信息</title>
<!-- InstanceEndEditable -->
<#include "../../include.ftl"/>
<script type="text/javascript" src="../js/common/ddpPaginator.js"></script>
<script type="text/javascript" src="../js/calendar/calendar.js"></script>
<link href="${styleUrl}/portal/css/m-base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/index.css" rel="stylesheet" type="text/css" />
<script src="${base}/js/common/select.js"></script>
<script src="${base}/js/common/area.js"></script>
<script type="text/javascript" src="../js/prvd/merchant/merchantPosList.js"></script>
<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
<script type="text/javascript">
var baseUrl = "${base}";
</script>
<script type="text/javascript">
var c = new Calendar("c");
document.write(c);
</script>

<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->
</head>
<body>

<#include "../../header.ftl"/>
<div class="con-main" id="merchantPosMain"> <!-- InstanceBeginEditable name="main" -->
	<h3 class="tit-h3">POS信息</h3>
	<div class="com-bor-box com-bor-box01">
		<div class="deal">
		<div class="seach-top-inner">
			<form action="/" id="merchantPosForm">
			<input type="hidden" name="merName" id="merName" value="${merName!}"></input>
			<input type="hidden" name="merCode" id="merCode" value="${merCode!}"></input>
				<ul class="clearfix">
					<li>
						<label><span class="w70">商户名称：</span>
							<span class="a-left" style="width:300px;">${merName!}</span>
						</label>
					</li>
				</ul>
				<ul class="clearfix">
					<li class="w300">
						<label><span class="w70">POS编号：</span>
							<input type="text" id="posCode" name="posCode" class="com-input-txt w88" />
						</label>
					</li>
					<li class="btn-list">
					    <input type="button" value="查询" class="orange-btn-h26" onclick="findMerchantPos();"/>
						<input type="button" value="清空" class="orange-btn-text26" onclick="clearQueryForm('merchantPosForm');"/>
					</li>
				</ul>
			</form>
		</div>
	</div>
		<div class="w890 mb0">
			<ul class="navi-ul clearfix pd00 mb20">
				<li><a href="javascript:void(0);" onclick="startOrStopUser(true);" id="qiyong"  disabled="disabled"  class="orange-btn-h22 w66">启用</a></li>
				<li><a href="javascript:void(0);" onclick="startOrStopUser(false);" id="tingyong"  disabled="disabled" class="gray-btn-h22 w66">停用</a></li>
			</ul>
			<table id="merchantPosTb" cellspacing="0" cellpadding="0" border="0" class="com-table01">
			<colgroup>
			<col width="1%">
			<col width="5%">
			<col width="13%">
			<col width="13%">
			<col width="10%">
			<col width="1%">
			</colgroup>
			<thead>
			<tr>
			    <th>&nbsp;</th>
				<th class="a-center"><input type="checkbox" onclick="toggle(this,'merPos');toggleActivateBtn('merPos');"  id="merPosCheck"/></th>
				<th>POS编号</th>
				<th>POS状态</th>
				<th>POS绑定时间</th>
				<th>&nbsp;</th>
			</tr>
			</thead>
			<tbody>
			
		</tbody>
		</table>
		<div class="null-box"></div>
		    <p class="page-navi" id="merPosPaginator"><span class="fl">信息下载：<a href="#"  onclick="exportExcel('exportmerchantPos','merchantPosForm')">导出Excel</a></span>
		    </p>
			<div class="a-center btn-box">
				<a href="javascript:void(0)" onclick="jumpPage()" class="orange-btn-h32">返回</a>
				<!--<a href="${base}/prvd/merchant?merName=${mmerName}&merUserName=${merUserName}&merUserMobile=${merUserMobile}&_pageSize=${_pageSize}&_pageNo=${_pageNo}&flag=1"" class="orange-btn-h32">返回</a>-->
			</div>
		</div>
	</div>
</div>

<form action="${base}/prvd/merchant" method="post" id="_form">
  <input type="hidden" name="merName" value="${mmerName}"/>
  <input type="hidden" name="merUserName" value="${merUserName}"/>
  <input type="hidden" name="merUserMobile" value="${merUserMobile}"/>
  <input type="hidden" name="_pageSize" value="${_pageSize}"/>
  <input type="hidden" name="_pageNo" value="${_pageNo}"/>
  <input type="hidden" name="flag" value="1"/>
</form>
<div class="foot">

    <div class="footer-copy">
        <div class="w1030 footer-p">
            <p><a href="http://www.dodopal.com/">关于都都宝</a>&nbsp;|&nbsp;官方微博 <a href="#">http://weibo.com/dodopal</a></p>
            <p>都都宝版权所有&nbsp;COPYRIGHT (C) 2014 ICDC(Beijing) ALL RIGHTS RESERVED 京ICP证100323号</p>
            <p class="orange">使用完毕请安全退出，请勿泄露自己的登录名和密码</p>
        </div>
    </div>
</div>
<!-- InstanceBeginEditable name="pop" -->
<div class="pop-win" js="mobanBox" style="display: none;">
	<div class="bg-win"></div>
	<div class="pop-bor"  style="width: 530px; margin:-90px 0 0 -265px; height:180px;"></div>
	<div class="pop-box" style="width: 520px; margin:-85px 0 0 -260px;height:170px;">
		<h3>导出（Excel格式）</h3>
		<div class="innerBox">
			<form action="/">
				<table  class="base-table">
					<col width="105" />
					<tr>
						<th>下载到：</th>
						<td><input type=file name="j" style="display: none;" onchange="ye.value=value">
							<input name=ye class="com-input-txt w260">
							<input type=button value="浏览" onclick="j.click()" class="btn-orange"></td>
					</tr>
					<tr>
						<td colspan="2" class="a-center"><input type="submit" class="pop-borange mr20" value="确认">
							<a href="javascript:;" js="clopop" class="pop-bgrange">取消</a></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
<div class="pop-win" js="tijiaoBox" style="display: none;">
	<div class="bg-win"></div>
	<div class="pop-bor"></div>
	<div class="pop-box">
		<h3>信息提示</h3>
		<div class="innerBox">
			<div class="tips">
            <div class="alert-icons alert-icons1"></div>
            <span>您确认停用？</span></div>
			<ul class="ul-btn">
				<li><a href="javascript:;" class="pop-borange" onclick="popclo(this)">确认</a></li>
				<li><a href="javascript:;" js="clopop" class="pop-bgrange">取消</a></li>
			</ul>
		</div>
	</div>
</div> 
<script type="text/javascript">
$(document).ready(function(e) {
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="商户信息"){
			$(this).addClass('cur');
		}
	});
});
</script> 
<!-- InstanceEndEditable --> 
<script type="text/javascript">
$(document).ready(function(e) {
	$('[js="tijiao"]').click(function(){
		$('[js="tijiaoBox"]').show();
	});
	//顶部提示信息
	var timer= setInterval(function(){
		$('.warning span').animate({'left':-230+'px'},10000,"linear").animate({'left':$('.warning').width()+'px'},0);
		},500);
	
	var setcity=0;
	$('[js="setCity"]').click(function(event){
		  event.stopPropagation();
		$('.set-city').show();
		setcity=1;
	});
	
	$('body').click(function(){
		if(setcity=1){
			$('.set-city').hide();
		}
	});
	
	$('[js="qurren"]').click(function(){
		$('[js="qurrenjiner"]').show();
		
	});
	$('[js="moban"]').click(function(){
		$('[js="mobanBox"]').show();
	});

	$('.set-city-list li').click(function(event){
		event.stopPropagation();
		var i=$(this).index();
		$('.set-city-list li').find('a').removeClass('on');
		$(this).find('a').addClass('on');
		$('.set-city-dl').eq(i).show().siblings('.set-city-dl').hide();
	});
	$('.set-city-dl li a').click(function(event){
		event.stopPropagation();
	});
	
	
	$('.com-table01 tr:even').find('td').css("background-color",'#f6fafe');
	$('.com-table02 tr:even').find('td').css("background-color",'#f6fafe');
	$('.bg-win,[js="clopop"]').click(function(){
		$(this).closest('.pop-win').hide();
	});
	$('.header-nav ul li').click(function(){
		var i=$(this).index();
		$('.header-nav ul li a').removeClass('on');
		$('.header-inr-nav ul').hide();
		$('.header-inr-nav ul').eq(i).show();
		$(this).find('a').addClass('on');
	});
	if($('.header-inr-nav ul li a').hasClass('cur')){
		var i=$('.cur').closest('ul').index();
		$('.header-nav ul li a').removeClass('on').eq(i).addClass('on');
		$('.header-inr-nav ul').hide();
		$('.header-inr-nav ul').eq(i).show()
	};
});

function popclo(obj){
	$(obj).closest('.pop-win').hide();
}

$('.footer-navi .more a').click(function(){
	if($(this).hasClass('open')){
		$(this).removeClass('open');
		$('.footer-navi ul').height(60);
	}else{
		$(this).addClass('open');
		$('.footer-navi ul').removeAttr('style');
	};

});

function jumpPage(){
	$("#_form").submit();
}
</script>
</body>
<!-- InstanceEnd --></html>
