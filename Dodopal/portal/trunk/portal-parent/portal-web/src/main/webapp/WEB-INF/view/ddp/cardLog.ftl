<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>账户管理 | 卡片管理</title>
<!-- InstanceEndEditable -->
<#include "../include.ftl"/>
<script src="${base}/js/ddp/cardLog.js"></script>
<script src="${base}/js/portalUtil.js"></script>
<script type="text/javascript" src="${base}/js/common/ddpPaginator.js"></script>
<script src="${base}/js/common/select.js" type="text/javascript"></script>
<!-- InstanceEndEditable -->
</head>
<body>
<#include "../header.ftl"/>
<div class="con-main" id="cardLogTableMain"> <!-- InstanceBeginEditable name="main" -->
	<h3 class="tit-h3"><@sec.accessControl permission="ddp.card.card"><a href="${base}/ddp/card">卡片管理</a><i class="i-line">|</i></@sec.accessControl><@sec.accessControl permission="ddp.card.log"><a  href="${base}/ddp/log" class="cur">卡片操作日志</a></@sec.accessControl></h3>
	<div class="com-bor-box top-border">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="com-table01" id="cardLogTableTb">
			<col width="2%" />
			<col width="6%" />
			<col width="13%" />
			<col width="13%" />
			<col width="13%" />
            <col width="13%" />
            <col width="18%" />
            <col width="10%" />
            <col width="10%" />
			<col width="2%" />
			<thead>
			<tr>
				<th>&nbsp;</th>
             
				<th>序号</th>
				<th>卡号</th>
				<th>卡片名称</th>
				<th>用户姓名</th>
                <th>操作类型</th>
                <th>操作时间</th>
                <th>来源</th>
				<th>操作人姓名</th>
				<th>&nbsp;</th>
			</tr>
			</thead>
			<tbody></tbody>
		</table>
		<div class="null-box"></div>
        <p class="page-navi" id="cardLogTablePaginator"></p>
	</div>
	<!-- InstanceEndEditable --> </div>
<#include "../footer.ftl"/>
<!-- InstanceBeginEditable name="pop" --> 
<script type="text/javascript">
$(document).ready(function(e) {

	$('[js="del"]').click(function(){
		$('[js="delBox"]').show();
	});
	$('[js="edit"]').click(function(){
		$('[js="editBox"]').show();
	});
	
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="卡片管理"){
			$(this).addClass('cur');
		}
	});
	if($('.com-table01 :checked').length>0){
		$('.navi-ul [js="del"]').attr('class','orange-btn-h22');
	};
	$('.com-table01 [type="radio"]').click(function(){
		if($('.com-table01 :checked').length>0){
			$('.navi-ul [js="del"]').attr('class','orange-btn-h22');
			
		}else{
			$('.navi-ul [js="del"]').attr('class','gray-btn-h22');
		};
	});
});

</script>
<div class="pop-win" js="delBox" style="display: none;">
	<div class="bg-win"></div>
	<div class="pop-bor"></div>
	<div class="pop-box">
		<h3>解绑卡片提示</h3>
		<div class="innerBox">
			<div class="tips">
            <div class="alert-icons alert-icons1"></div>
            <span>确认要解绑此卡片XXXXXXXXXXXX吗？</span>
            </div>
			<ul class="ul-btn">
				<li><a href="javascript:;" onclick="popclo(this)" class="pop-borange">确认</a></li>
				<li><a href="javascript:;" js="clopop" class="pop-bgrange">取消</a></li>
			</ul>
		</div>
	</div>
</div>

<div class="pop-win" js="editBox" style="display: none;">
	<div class="bg-win"></div>
	<div class="pop-bor"  style="width: 530px; margin:-90px 0 0 -265px; height:270px;"></div>
	<div class="pop-box" style="width: 520px; margin:-85px 0 0 -260px;height:260px;">
		<h3>编辑卡片信息</h3>
		<div class="innerBox">
			<form action="/">
				<table  class="base-table">
					<col width="105" />
					<tr>
						<th class="w128">卡片名称：</th>
						<td><input type=file name="j" style="display: none;" onchange="ye.value=value">
							<input name=ye class="com-input-txt w260">
							</td>
					</tr>
                    <tr>
						<th>备注：</th>
						<td><textarea class="area-w268"></textarea>
							</td>
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
<!-- InstanceEndEditable --> 
<script type="text/javascript">
$(document).ready(function(e) {
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

</script>
</body>
<!-- InstanceEnd --></html>
