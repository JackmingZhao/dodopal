<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>转账</title>
<!-- InstanceEndEditable -->
<#include "../include.ftl"/>
<script src="${base}/js/portalUtil.js"></script>
<script src="${base}/js/common/select.js" type="text/javascript"></script>
<script src="${base}/js/ddp/transfer.js"></script>
<script type="text/javascript">
  var transferMerAr = new Array();
  var extractionMerAr = new Array();
</script>
<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->
</head>
<body>
<#include "../header.ftl"/>
<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
	<table cellpadding="0" cellspacing="0" class="com-table02 mb20">
		<col width="75%" />
		<tr>
			<td>
				<div class="balance-div clearfix">
					<div class="fl">
						<p class="ttl01">账户可用余额：</p>
						<div class="price-div clearfix"><span>&yen;<i> ${availableMoney}</i></span><@sec.accessControl permission="ddp.recharge">
			             <a href="${base}/ddp/recharge" id="rechargeA" class="mr20 orange-btn-h26">充值</a>
						</@sec.accessControl></div>
					</div>
					<div class="fr frozen-div">
						<p><span class="span-txt01">冻结</span><i>&yen; ${frozenMoney}</i></p>
						<div class="link-div"><a href="${base}/tran/record" class="blue">交易记录</a><span class="span-border">|</span><a href="${base}/ddp/showAccountChange" class="blue">资金变更记录</a></div>
					</div>
					
				</div>
			
			</td>
			<td class="bor-td-left"><dl>
					<dt>我的资产</dt>
					<dd>资金可用余额：<i class="blue">${accountMoney}</i> 元<br />
						资金冻结金额：<i class="blue">${accountFrozenAmount}</i> 元<br />
			<#if sessionUser.merType =='99'>
						已绑卡数：<i class="blue">${bindCardCount!0}</i> 张<br />
			<#else>
				<#if fundType?? >
			           <#if fundType=='1' >
						授信可用余额：<i class="blue">${accountFuntMoney}</i> 元<br />
						授信冻结金额：<i class="blue">${accountFundFrozenAmount}</i> 元</dd>
				       <#elseif fundType=='0'>
				         &nbsp;
				       </#if>
				<#else>
				        &nbsp;
				</#if>
			</#if>
				</dl></td>
		</tr>
	</table>
	
	<h3 class="tit-h3 tit-h3-02"><span>转账</span></h3>
	<div class="com-bor-box mb20">
		<div class="com-ttl-box com-ttl-box-02">
			<ul class="pay-navi-ul02 clearfix">
				<li class="on"><a href="javascript:void(0);" >分配额度</a></li>
				<li><a href="javascript:void(0);">提取额度</a></li>
			</ul>
		</div>
		<div class="tab-com">
			<div class="transfer-accounts clearfix">
				<table cellpadding="0" cellspacing="0" summary="">
					<col width="30%" />
					<tr>
						<th>收款方：</th>
						<td><div class="input-box"><input type="text" disabled="true" readOnly="true" id="collections" value="" name="" class="com-input-txt w250" title=""/><a href="javascript:;" class="link-btn-01"></a></div></td>
					</tr>
					<tr>
						<th>分配金额：</th>
						<td><input type="text" id="collectionsMoney" value="0.00" style="text-align:right;"  value="" name="" class="com-input-txt w190 mr10" />元</td>
					</tr>
					<tr>
						<th>备注：</th>
						<td><input type="text" id="collectionsComments" maxlength="200" value="" name="" placeholder="" class="com-input-txt w250" /></td>
					</tr>
					<tr>
						<th></th>
						<td><button class="orange-btn-h32 ok-btn-01">转账</button></td>
					</tr>
				</table>
				<dl class="safe-explain">
					<dt>操作说明：</dt>
					<dd>
						<ul>
							<li>1.分配额度时，可以选择多个网点进行操作。</li>
                            <li>2.点击收款方按钮，弹出处于启动状态的连锁直营网点信息。</li>
							<li>3.分配金额栏中只能输入正数。</li>
						</ul>
					</dd>
				</dl>				
			</div>
			<div class="transfer-accounts clearfix" style="display:none;">
				<table cellpadding="0" cellspacing="0" summary="">
					<col width="30%" />
					<tr>
						<th>付款方：</th>
						<td><div class="input-box"><input type="text" disabled="true" readOnly="true" id="payment" value="" name="" class="com-input-txt w250" /><a href="javascript:;" class="link-btn-02"></a></div><p class="notes-txt"><i></i>请选择网点名称</p></td>
					</tr>
					<tr>
						<th>提取金额：</th>
						<td><input type="text" id="paymentMoney" value="0.00" style="text-align:right;"  value="" name="" class="com-input-txt w190 mr10" />元</td>
					</tr>
					<tr>
						<th>备注：</th>
						<td><input type="text" id="paymentComments" maxlength="200" value="" name="" placeholder="" class="com-input-txt w250" /></td>
					</tr>
					<tr>
						<th></th>
						<td><button class="orange-btn-h32 ok-btn-02">转账</button></td>
					</tr>
				</table>
				<dl class="safe-explain">
					<dt>操作说明：</dt>
					<dd>
						<ul>
							<li>1.提取额度时，只能选择单个网点进行操作。</li>
                            <li>2.点击付款方按钮，弹出处于启动状态的连锁直营网点信息。</li>
							<li>3.提取金额栏中只能输入正数。</li>
						</ul>
					</dd>
				</dl>				
			</div>
			
		</div>
	</div>
</div>
	
<#include "../footer.ftl"/>
<!-- InstanceBeginEditable name="pop" --> 
<script type="text/javascript">
$(document).ready(function(e) {
	$('.ok-btn-01').click(function(){
	    createTransferDetail();
	   var collectionsMoney = $('#collectionsMoney').val();
	   var directMer= transferMerAr;
	   if(isBlank(directMer)){
		  $.messagerBox({type: 'warn', title:"信息提示", msg: "请选择直营网点"});
		   return;
	    }
	   if(isBlank(collectionsMoney)){
		  $.messagerBox({type: 'warn', title:"信息提示", msg: "请输入转账金额"});
		  return;
	    }
	   if(collectionsMoney=='0.00'|| collectionsMoney=='0' || collectionsMoney=='0.0'){
	    $.messagerBox({type: 'warn', title:"信息提示", msg: "请输入转账金额"});
	      return;
	    }
		$('[js="pop-01"]').show();
	});
	$('.ok-btn-02').click(function(){
	    createExtractionDetail();
	  	var paymentMoney = $('#paymentMoney').val();
	    var directMer= extractionMerAr;
	    if(isBlank(directMer)){
		  $.messagerBox({type: 'warn', title:"信息提示", msg: "请选择直营网点"});
		   return;
	    }
	   if(isBlank(paymentMoney)){
		  $.messagerBox({type: 'warn', title:"信息提示", msg: "请输入转账金额"});
		  return;
	    }
	   if(paymentMoney=='0.00'|| paymentMoney=='0' || paymentMoney=='0.0'){
	    $.messagerBox({type: 'warn', title:"信息提示", msg: "请输入转账金额"});
	      return;
	    }
		$('[js="pop-02"]').show();
	});
	
	$('.link-btn-01').click(function(){
		$("#transDiv").html("直营网点");
		$('[js="pop-03"]').show();
		findDirectTransfer("","13");
	});
	
	$('.link-btn-02').click(function(){
		$("#extractionDiv").html("直营网点");
		$('[js="pop-04"]').show();
		findDirectExtraction("","13");
		
	});
	
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="转账"){
			$(this).addClass('cur');
		}
	});
});

$().ready(function(){
  $(".pay-navi-ul02 li").each(function(i){
  $(".pay-navi-ul02 li").eq(i).click(function(){
	  $(this).addClass("on").siblings("li").removeClass("on");
	  $(".tab-com .transfer-accounts").eq(i).show().siblings().hide();
  });
 });
});
</script>
<div class="pop-win pop-bill" js="pop-01" style="display:none;">
	<div class="bg-win" ></div>
	<div class="pop-bor h360"></div>
	<div class="pop-box h350">
		<h3>分配额度提示信息</h3>
		<div class="innerBox">
			<p class="posr">请确认以下分配额度信息是否正确！</p>
			<div class="innerBox-div shop">
			<table cellpadding="0" cellspacing="0" class="pop-table table-title02">
            	<col width="50%" />
            	<col width="50%" />
				<tr>
					<th>网点名称</th>
					<th>额度（元）</th>
				</tr>
            </table>
			<table cellpadding="0" cellspacing="0" class="pop-table" id="transferDetail">
				<col width="50%" />
				<col width="50%" />
				<thead>
				<tr>
					<th>网点名称</th>
					<th>额度（元）</th>
				</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			</div>
			<ul class="ul-btn" style="padding:15px 0 ; margin-left:95px;">
				<li><a href="javascript:;" class="pop-borange" id="accountTransferTrue"  onclick="accountTransfer(this);">确认</a></li>
				<li><a href="javascript:;" js="clopop" class="pop-bgrange">取消</a></li>
			</ul>
		</div>
	</div>
</div>
<!-- 额度提取确认弹出框 -->
<div class="pop-win pop-bill" js="pop-02" style="display: none;">
	<div class="bg-win"></div>
	<div class="pop-bor"></div>
	<div class="pop-box">
		<h3>提取额度提示信息</h3>
		<div class="innerBox">
			<p>请确认以下提取额度信息是否正确！</p>
			<dl class="pop-dl clearfix" id="extractionDetail">
				
			</dl>
			<ul class="ul-btn ul-btn02">
				<li><a href="javascript:;" class="pop-borange" id="transferTrue" onclick="ExtractionTransfer(this)">确认</a></li>
				<li><a href="javascript:;" js="clopop" class="pop-bgrange">取消</a></li>
			</ul>
		</div>
	</div>
</div>

<!-- 分配额度 -->
<div class="pop-win pop-bill" js="pop-03" style="display:none;">
	<div class="bg-win"></div>
	<div class="pop-bor h380"></div>
	<div class="pop-box h370">
		<h3>网点信息</h3>
		<div class="innerBox">
		<div class="sel">
            	<div class="select-r">
                    <div class="selected-r pdf" onclick="showUl()" id="transDiv">直营网店</div>
                    <ul id='transUl'>
                        <li onclick="queryTransferPage(13,'直营网点','0')">直营网点</li>
                        <li onclick="queryTransferPage(14,'加盟网点','1')">加盟网点</li>
                    </ul>
            	</div>
			<div class="pop-search posr mar pdf80"><input type="text" id="transName" value="" name="" placeholder="网点名称"><button class="pop-search-btn" value="" onclick="queryTransfer()"></button></div>
         </div>
			<div class="shop">
			 <table cellpadding="0" cellspacing="0" class="pop-table table-title01 bd0">
            	<col width="6%" />
				<col width="32%" />
				<col width="38%" />
				<tr>
				    <th class="a-left"><input type="checkbox" id="checkAll"    onclick="checkAll();"/></th>
					<th  class="a-left">网点名称</th>
					<th>额度（元）</th>
				</tr>
            </table>
			<table cellpadding="0" cellspacing="0" class="pop-table" id="transferPage">
				<col width="47%" />
				<col width="47%" />
				<thead>
				<tr>
					<th>网点名称</th>
					<th>额度（元）</th>
				</tr>
			    </thead>
			    <tbody>
			    
			    </tbody>
			</table>
			</div>
			<ul class="ul-btn" style="padding:15px 0 ; margin-left:95px;">
				<li><a href="javascript:;" class="pop-borange"  onclick="transferPopclo(this)">确认</a></li>
				<li><a href="javascript:;" js="clopop" class="pop-bgrange">取消</a></li>
			</ul>
		</div>
	</div>
</div>


<!-- 额度提取 -->
<div class="pop-win pop-bill" js="pop-04" style="display:none;">
	<div class="bg-win"></div>
	<div class="pop-bor h380"></div>
	<div class="pop-box h370">
		<h3>网点信息</h3>
		<div class="innerBox">
		<div class="sel">
            	<div class="select-r">
                    <div class="selected-r pdf" onclick="showUl()" id="extractionDiv">直营网店</div>
                    <ul>
                        <li onclick="queryExtractionPage(13,'直营网点')">直营网点</li>
                        <li onclick="queryExtractionPage(14,'加盟网点')">加盟网点</li>
                    </ul>
            	</div>
			<div class="pop-search posr mar pdf80"><input type="text" value="" id="extractionName" name="" placeholder="网点名称"><button class="pop-search-btn" value="" onclick="queryExtraction()"></button></div>
         </div>
             <div class="shop">
              <table cellpadding="0" cellspacing="0" class="pop-table table-title01">
            	<col width="6%" />
				<col width="32%" />
				<col width="38%" />
				<tr>
				    <th></th>
					<th class="a-left">网点名称</th>
					<th>额度（元）</th>
				</tr>
              </table>
              <table cellpadding="0" cellspacing="0" class="pop-table bd0" id="extractionPage">
                <col width="47%" />
				<col width="47%" />
				<thead>
				<tr>
					<th>网点名称</th>
					<th>额度（元）</th>
				</tr>
			    </thead>
			    <tbody>
			    
			    </tbody>
			</table>
			</div>
			<ul class="ul-btn"  style="padding:15px 0 ; margin-left:95px;">
				<li><a href="javascript:;" class="pop-borange"  onclick="extractionPopclo(this)">确认</a></li>
				<li><a href="javascript:;" js="clopop" class="pop-bgrange">取消</a></li>
			</ul>
		</div>
	</div>
</div>
 <input type="hidden" id="transferType" value="13"/>
 <input type="hidden" id="extractionType" value="13"/>
 <input type="hidden" id="flag" value='0'>
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
		//clearInput();
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

function showUl(){
	$('.select-r ul').show();
}

</script>
</body>
<!-- InstanceEnd --></html>
