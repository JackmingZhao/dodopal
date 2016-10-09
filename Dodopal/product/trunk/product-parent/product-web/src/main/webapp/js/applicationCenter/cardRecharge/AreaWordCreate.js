function  loadFirstWordCity(bean){
	$(".set-city-dl dt").hide();
	$(".set-city-dl dd").hide();
	$("#hotCitydd").show();
	if(bean.allArea!=null){
			$.each(bean.allArea,function (i, value){
				var city = "";
				$.each(value,function (iv, param){
					city += "<li><a onclick='toChangeCity(\""+param.cityName+"\",\""+param.cityCode+"\");'>"+param.cityName+"</a></li>";
				});
				$("#"+i.toLocaleLowerCase()+"Span").append(city);
				$("#"+i.toLocaleLowerCase()+"Span").parent().show();
				$("#"+i.toLocaleLowerCase()+"Span").parent().prev().show();
			});
	}
	
}


function areaLoad(bean){
	if(bean.areaList!=null&&bean.areaList.length>1){
		$("#choiceCity").show();
		var hotCity = "";
		$.each(bean.areaList,function (i, value){
			hotCity += "<li><a href='javascript:toChangeCity(\""+value.cityName+"\",\""+value.cityCode+"\");'>"+value.cityName+"</a></li>";
		});
		$("#hotCity").append(hotCity);
	}else{
		$("#choiceCity").hide();
	}
}