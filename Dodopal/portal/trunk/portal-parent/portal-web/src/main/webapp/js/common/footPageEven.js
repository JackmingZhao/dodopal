$(function(){
	var docH = $('.foot').offset().top;
	var winH = $(window).height();
	if(winH>=docH){
		$('.foot').css('position','static');
	}else{
		$('.foot').css({'position':'fixed','bottom':-$('.footer-copy').height()+'px','width':'100%'});
			var docH= $('body').get(0).scrollHeight;
			$(window).scroll(function()
			{
				var winH = $(window).height();
				var scrollH = $(document).scrollTop();
				var divHeight= $('.footer-navi').height();
				var l = docH - winH - scrollH;
				if(l < divHeight){
					$('.foot').css('position','static');
				}else{
					$('.foot').css({'position':'fixed','bottom':-$('.footer-copy').height()+'px','width':'100%'});
				}
			})
	}
})
