<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<#include "../include.ftl"/>
<meta http-equiv="Content-Type" content="text/html; cha rset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>个性化设置</title>
<!-- InstanceEndEditable -->
<link href="${base}/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/css/index.css" rel="stylesheet" type="text/css" />
<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->
<script src="../js/ddp/accountSecure.js" type="text/javascript"></script>
</head>
<body>
<#include "../header.ftl"/>
<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
	<div class="account-setup mb30">
		<#include "../accountNav.ftl"/>
		<div class="account-box">
			<div class="ehs-box">
				<div class="boxl">
					<p>设置账户交易提示<a href="javascript:;" class="img03" js="editImg" id="img032"></a></p>
				</div>
				<div class="boxr"><a href="javascript:;" js="editOne" class="gray-btn-h26" id="payInfoFlgViewButton" onclick="payInfoFlgView()">设置</a>
					<form action="/">
						<table class="base-table" style="display: none;" id="payInfoFlgTable"> 
							<colgroup>
							<col width="120">
							</colgroup>
							<tbody>
								<tr>
									<th>弹出提示信息：</th>
									<td>
					                    <label><input type="radio" name="payinfoFlg" value="0" checked="checked"/>是</label>
					                    <label><input type="radio" name="payinfoFlg" value="1"/>否</label>
									</td>
								</tr>
								<tr>
									<th></th>
									<td><div class="btn-box">
											<input type="button" value="确认" class="pop-borange mr20" onclick="upPayInfoFlg()">
										</div></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- InstanceEndEditable --> </div>
<#include "../footer.ftl"/>
<script type="text/javascript">
$(document).ready(function(e) {
		$('[js="editOne"]').hover(function(){
			$(this).attr('class','orange-btn-h26');
			$(this).closest('.ehs-box').addClass('ehs-box-hover');
		},function(){
			$(this).attr('class','gray-btn-h26');
			$(this).closest('.ehs-box').removeClass('ehs-box-hover')
		});
		
			$('[js="editOne"]').click(function(){
			$(this).hide();	
			$(this).closest('.ehs-box').addClass('ehs-box-click');
		});
});
</script> 
<script type="text/javascript">
$(document).ready(function(e) {
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
