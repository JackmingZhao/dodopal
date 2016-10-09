<td >
<input type="text" value="" disabled  class="com-input-txt w260" id="cityName" maxlength="20"/>
<input type="hidden" id="cityCode"/>
<span id="city"></span> 
<span id="choiceCity">[<a href="#" class="blue" js="setCity">切换城市</a>]</span>
					<div class="set-city" style=" display: none;">
					<#--
						<ul class="set-city-list">
							<li><a href="javascript:;" class="on">热门</a></li>
							<li><a href="javascript:;">ABCD</a></li>
							<li><a href="javascript:;">EFGH</a></li>
							<li><a href="javascript:;">JKLM</a></li>
							<li><a href="javascript:;">NOPQRS</a></li>
							<li><a href="javascript:;">TUVWX</a></li>
							<li><a href="javascript:;">YZ</a></li>
						</ul>
						-->
						<dl class="set-city-dl clearfix">
							<dd id="hotCitydd">
								<ul class="clearfix" id="hotCity">
								</ul>
							</dd>
						</dl>
						<#--
						<dl class="set-city-dl clearfix" style="display: none">
							<dt >A</dt>
							<dd >
								<ul class="clearfix" id="aSpan">
								</ul>
							</dd>
							<dt>B</dt>
							<dd>
								<ul class="clearfix" id="bSpan">
								</ul>
							</dd>
							<dt>C</dt>
							<dd>
								<ul class="clearfix" id="cSpan">
								</ul>
							</dd>
							<dt>D</dt>
							<dd>
								<ul class="clearfix" id="dSpan">
								</ul>
							</dd>
						</dl>
						<dl class="set-city-dl clearfix" style="display: none">
							<dt>E</dt>
							<dd>
								<ul class="clearfix" id="eSpan">
								</ul>
							</dd>
							<dt>F</dt>
							<dd>
								<ul class="clearfix" id="fSpan">
								</ul>
							</dd>
							<dt>G</dt>
							<dd>
								<ul class="clearfix" id="gSpan">
								</ul>
							</dd>
							<dt>H</dt>
							<dd>
								<ul class="clearfix" id="hSpan">
								</ul>
							</dd>
						</dl>
						<dl class="set-city-dl clearfix" style="display: none">
							<dt>J</dt>
							<dd>
								<ul class="clearfix" id="jSpan">
								</ul>
							</dd>
							<dt>K</dt>
							<dd>
								<ul class="clearfix" id="kSpan">
								</ul>
							</dd>
							<dt>L</dt>
							<dd>
								<ul class="clearfix" id="lSpan">
								</ul>
							</dd>
							<dt>M</dt>
							<dd>
								<ul class="clearfix" id="mSpan">
								</ul>
							</dd>
						</dl>
						<dl class="set-city-dl clearfix" style="display: none">
							<dt>N</dt>
							<dd>
								<ul class="clearfix" id="nSpan">
								</ul>
							</dd>
							<dt>O</dt>
							<dd>
								<ul class="clearfix" id="oSpan">
								</ul>
							</dd>
							<dt>P</dt>
							<dd>
								<ul class="clearfix" id="pSpan">
								</ul>
							</dd>
							<dt>Q</dt>
							<dd>
								<ul class="clearfix" id="qSpan">
								</ul>
							</dd>
							<dt>R</dt>
							<dd>
								<ul class="clearfix" id="rSpan">
								</ul>
							</dd>
							<dt>S</dt>
							<dd>
								<ul class="clearfix" id="sSpan">
								</ul>
							</dd>
						</dl>
						<dl class="set-city-dl clearfix" style="display: none">
							<dt>T</dt>
							<dd>
								<ul class="clearfix" id="tSpan">
								</ul>
							</dd>
							<dt>U</dt>
							<dd>
								<ul class="clearfix" id="uSpan">
								</ul>
							</dd>
							<dt>V</dt>
							<dd>
								<ul class="clearfix" id="vSpan">
								</ul>
							</dd>
							<dt>W</dt>
							<dd>
								<ul class="clearfix" id="wSpan">
								</ul>
							</dd>
							<dt>X</dt>
							<dd>
								<ul class="clearfix" id="xSpan">
								</ul>
							</dd>
						</dl>
						<dl class="set-city-dl clearfix" style="display: none">
							<dt>Y</dt>
							<dd>
								<ul class="clearfix" id="ySpan">
								</ul>
							</dd>
							<dt>Z</dt>
							<dd>
								<ul class="clearfix" id="zSpan">
								</ul>
							</dd>
						</dl>
						-->
					</div>
				</td>
				
				
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
	var timer=null;


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

	
	$('[js="pay-list"] li:gt(4)').hide();
	$('[js="pay-list"] li.only').show();
	var a=0;
	$('[js="pay-list"] li.only').click(function(){
		if(a==0){
			$('[js="pay-list"] li').show();
			a=1;
		}else{
			$('[js="pay-list"] li:gt(4)').hide();
			$('[js="pay-list"] li.only').show();
			a=0;
		}		
	});
$('.recharge-amount a').click(function(){
	$(this).addClass('a-click').siblings("a").removeClass("a-click");
});
$('.payway-ul input').click(function(){
	if(!$(this).attr('checked')){
		$(this).closest('.payway-ul').addClass('payway-ul-click');
	}else{
		$(this).closest('.payway-ul').removeClass('payway-ul-click');
	}
	
})
  $(".pay-navi-ul li").each(function(i){
  $(".pay-navi-ul li").eq(i).click(function(){
  $(this).addClass("on").siblings("li").removeClass("on");
  $(".pay-way-box ul").eq(i).show().siblings().hide();
  });
 });
 
 
});



</script>