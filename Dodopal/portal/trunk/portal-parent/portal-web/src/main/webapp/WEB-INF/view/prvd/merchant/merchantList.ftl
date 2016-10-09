<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>供应商商户管理-商户信息</title>
<!-- InstanceEndEditable -->
<#include "../../include.ftl"/>
<script type='text/javascript' src='${scriptUrl}/util/util.js'> </script>
<script type="text/javascript" src="${base}/js/common/ddpPaginator.js"></script>
<link href="${styleUrl}/portal/css/m-base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/js/prvd/merchant/merchantList.js"></script>
<script src="${base}/js/common/select.js"></script>
<script src="${base}/js/common/area.js"></script>
<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
<script type="text/javascript">
var baseUrl = "${base}";
</script>
</head>
<body>

<#include "../../header.ftl"/>
<div class="con-main" id="cityMerchantPosMain"> <!-- InstanceBeginEditable name="main" -->
	<div class="seach-top-box">
		<div class="seach-top-inner">
			<form action="exportCityMerchantPos" id="cityMerchantPosForm">
				<ul class="clearfix">
					<li>
						<label><span class="w100">商户名称：</span>
							<input type="text" id="merName" name="merName" class="com-input-txt w88" value="${merName}"/>
						</label>
					</li>
					<li>
						<label><span class="w100">商户管理员：</span>
							<input type="text" id="merUserName" name="merUserName" class="com-input-txt w88" value="${merUserName}"/>
						</label>
					</li>
					<li  class="w300">
						<label><span>手机号码：</span>
							<input type="text" id="merUserMobile" name="merUserMobile" class="com-input-txt w88" value="${merUserMobile}"/>
						</label>
					</li>
					<li class="btn-list">
					<@sec.accessControl permission="prvd.merchant.find">
						<input type="button" value="查询" class="orange-btn-h26" onclick="findCityMerchantPos();"/>
					</@sec.accessControl>
						<input type="button" value="清空" class="orange-btn-text26" onclick="clearForm();"/>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="com-bor-box">
		<table width="100%" id="cityMerchantPosTb" border="0" cellspacing="0" cellpadding="0" class="com-table01 bdt0">
		  <colgroup>
			<col width="2%" />
			<col width="3%" />
			<col width="15%" />
			<col width="10%" />
			<col width="10%" />
			<col width="8%" />
			<col width="15%" />
			<col width="6%" />
			<col width="6%" />
			<col width="2%" />
		  </colgroup>
		  <thead>
			<tr>
				<th>&nbsp;</th>
				<th class="a-center">序号</th>
				<th>商户名称</th>
				<th>商户管理员</th>
				<th>手机号码</th>
				<th>拥有POS数量</th>
				<th>店面地址</th>
				<th>启用标识</th>
				<th class="a-center">操作</th>
				<th>&nbsp;</th>
			</tr>
		  </thead>
		  <tbody></tbody>
		</table>
		
		<div class="null-box"></div>
		    <p class="page-navi">
		     <@sec.accessControl permission="prvd.merchant.export">
		     <span class="fl">信息下载：<a href="#"  onclick="exportExcel('${base}/prvd/exportCityMerchantPos','cityMerchantPosForm')">导出Excel</a></span>
		     </@sec.accessControl>
		    </p>
	</div>
  </div>


<input type='hidden' id="_pageSize" value="${_pageSize}">
<input type='hidden' id="_pageNo" value="${_pageNo}">
<input type='hidden' id="flag" value="${flag}">


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
<script type="text/javascript">
$(document).ready(function(e) {
	$('[js="tijiao"]').click(function(){
		$('[js="tijiaoBox"]').show();
	});

	$('[js="zhixing"]').click(function(){
		$('[js="zhixingBox"]').show();
	});
	$('[js="del"]').click(function(){
		$('[js="delBox"]').show();
	});
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="商户信息"){
			$(this).addClass('cur');
		}
	});
	$('[js="moban"]').click(function(){
		$('[js="mobanBox"]').show();
	});
	if($('.com-table01 :checked').length>0){
		$('[js="tijiao"]').attr('class','orange-btn-h22 w66');
		$('[js="zhixing"]').attr('class','orange-btn-h22');
		$('[js="del"]').attr('class','orange-btn-h22');
	};
	$('.com-table01 [type="checkbox"]').click(function(){
		if($('.com-table01 :checked').length>0){
			$('[js="tijiao"]').attr('class','orange-btn-h22');
			$('[js="zhixing"]').attr('class','orange-btn-h22');
			$('[js="del"]').attr('class','orange-btn-h22');
		}else{
			$('[js="tijiao"]').attr('class','gray-btn-h22');
			$('[js="zhixing"]').attr('class','gray-btn-h22');
			$('[js="del"]').attr('class','gray-btn-h22');
		};
	});

});
</script>
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
<div class="pop-win" js="mobanBox" style="display: none;">
	<div class="bg-win"></div>
	<div class="pop-bor"  style="width: 530px; margin:-90px 0 0 -265px; height:180px;"></div>
	<div class="pop-box" style="width: 520px; margin:-85px 0 0 -260px;height:170px;">
		<h3>记录下载（Excel格式）</h3>
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
<div class="pop-win" js="zhixingBox" style="display: none;">
	<div class="bg-win"></div>
	<div class="pop-bor"></div>
	<div class="pop-box">
		<h3>信息提示</h3>
		<div class="innerBox">
			<div class="tips">
            <div class="alert-icons alert-icons2"></div>
            <span>请先插入POS机，如已插入请检查是否正确安装驱动！</span></div>
			<ul class="ul-btn">
				<li><a href="javascript:;" class="pop-borange" onclick="popclo(this)">确认</a></li>
				<li><a href="javascript:;" js="clopop" class="pop-bgrange">取消</a></li>
			</ul>
		</div>
	</div>
</div>
<div class="pop-win" js="delBox" style="display: none;">
	<div class="bg-win"></div>
	<div class="pop-bor"></div>
	<div class="pop-box">
		<h3>删除提示</h3>
		<div class="innerBox">
			<div class="tips">
            <div class="alert-icons alert-icons1"></div>
            <span>您确认删除批次单吗？</span>
            </div>
			<ul class="ul-btn">
				<li><a href="javascript:;" class="pop-borange" onclick="popclo(this)">确认</a></li>
				<li><a href="javascript:;" js="clopop" class="pop-bgrange">取消</a></li>
			</ul>
		</div>
	</div>
</div>
<!-- InstanceEndEditable --> 
<script type="text/javascript">
$(document).ready(function(e) {
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


</script>
</body>


<!-- InstanceEnd --></html>

