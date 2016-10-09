<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<title>应用中心</title>
<#include "../include.ftl"/>
<script src="${base}/js/common/select.js"></script>
<script src="${base}/js/common/area.js"></script>
<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
<script type="text/javascript" src="../js/common/ddpPaginator.js"></script>
<script type="text/javascript" src="../js/applicationCenter/productManage.js"></script>
</head>
<body>
<#include "../header.ftl"/>
<div class="con-main">
<div class="application-center clearfix">
 	 <div class="app-left">
		  <ul class="clearfix">
			  <li class="snavi01"><a href="${base}/product/toProductMge" class="on"><span><i></i>一卡通充值</span></a></li>
			  <li class="snavi03"><a href="#"><span><i></i>手机话费</span></a></li>
			  <li class="snavi04"><a href="#"><span><i></i>水费缴纳</span></a></li>
			  <li class="snavi05"><a href="#"><span><i></i>电费缴纳</span></a></li>
			  <li class="snavi06"><a href="#"><span><i></i>天然气缴纳</span></a></li>
			  <li class="snavi07"><a href="#"><span><i></i>有线电视费</span></a></li>
		  </ul>
	  </div>
	  <div class="app-right">
		  <div class="seach-top-inner">
			  <form action="productMgeExport" id="productForm">
				  <ul class="clearfix">
					  <li class="w210"><span>业务城市：</span>
						  <select name="cityName" id="cityName">
						  </select>
					  </li>
					  <li class="btn-list">
						  <input type="button" value="查询" class="orange-btn-h26" onclick="fidnProduct();"/>
						  <input type="hidden" value="清空" class="orange-btn-text26" onclick="clearProduct('productForm')"/>
					  </li>
				  </ul>
			  </form>
		  </div>
		  <div class="com-bor-box02">
			  <ul class="navi-ul01 clearfix">
				  <li class="fr">金额单位(元)</li>
			  </ul>
		  </div>
		  <table id="productManageTb" width="100%" border="0" cellspacing="0" cellpadding="0" class="com-table01">
			  <col width="2%" />
			  <col width="2%" />
			  <col width="14%" />
			  <col width="26%" />
			  <col width="22%" />
			  <col width="20%" />
			  <col width="12%" />
			  <col width="2%" />
			  <thead>
			  <tr>
				  <th>&nbsp;</th>
				  <th></th>
				  <th>城市名称</th>
				  <th>产品编号</th>
				  <th>产品名称</th>
				  <th>产品面价</th>
				  <th>商户成本价</th>
				  <th>&nbsp;</th>
			  </tr>
			  </thead>
			  <tbody></tbody>
		  </table>
		  <div class="null-box" style="display:none;"></div>
		  <p class="page-navi">
			  <@sec.accessControl permission="app.prdmgmt.recharge.export">
			      <span class="fl">产品下载：<a href="#" class="easyui-linkbutton" iconCls="icon-import" plain="true" id="exportProduct"
			       onclick="exportExcel('productMgeExport','productForm')">导出Excel</a>
			       </span>
			  </@sec.accessControl>
		  </p>
	  </div>
	</div>
	</div>
	<div class="footer-copy">
	<div class="w1030 footer-p">
		<p><a href="http://www.dodopal.com/" target="_blank">关于都都宝</a>&nbsp;|&nbsp;官方微博 <a href="http://weibo.com/dodopal" target="_blank">http://weibo.com/dodopal</a></p>
		<p>都都宝版权所有&nbsp;COPYRIGHT (C) 2014 ICDC(Beijing) ALL RIGHTS RESERVED 京ICP证100323号</p>
		<p class="orange">使用完毕请安全退出，请勿泄露自己的登录名和密码</p>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function(e) {
		
		$('[js="chanpin"]').click(function(){
			$('[js="chanpinBox"]').show();
		});
		
		$('.header-inr-nav ul li a').each(function(){
			if($.trim($(this).text())=="产品管理"){
				$(this).addClass('cur');
			}
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
</html>
