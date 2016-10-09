$.fn.province_city_county = function(v_pro,v_city){
    var _self = this;
       _self.html("<select id='province' name='province' style='width: 100px'></select>" +
            "<select id='city' name='city' style='width: 100px'></select>");
    var sel1 = _self.find("select").eq(0);
    var sel2 = _self.find("select").eq(1);
 
     
    _self.data("province",["请选择", ""]);
    _self.data("city",["请选择", ""]);
   
    //默认省级下拉
    if(_self.data("province")){
        sel1.append("<option value='"+_self.data("province")[1]+"'>"+_self.data("province")[0]+"</option>");
    }
    //默认城市下拉
    if(_self.data("city")){
        sel2.append("<option value='"+_self.data("city")[1]+"'>"+_self.data("city")[0]+"</option>");
    }
   
    $.get('xml/province_city.xml', function(data){
        var arrList = [];
        $(data).find('province').each(function(){
            var $province = $(this);
            sel1.append("<option value='"+$province.attr('value')+"'>"+$province.attr('value')+"</option>");
        });
        if(typeof v_province != 'undefined'){
            sel1.val(v_province);
            sel1.change();
        }
    });
     
    //省级联动控制
    var index1 = "" ;
    var provinceValue = "";
    var cityValue = "";
    sel1.change(function(){
        //清空其它2个下拉框
        sel2[0].options.length=0;
        index1 = this.selectedIndex;
        if(index1 == 0){    //当选择的为 "请选择" 时
            if(_self.data("city")){
                sel2.append("<option value='"+_self.data("city")[1]+"'>"+_self.data("city")[0]+"</option>");
            }
        } else{
            provinceValue = $('#province').val();
            $.get('xml/province_city.xml', function(data){
                $(data).find("province[value='"+provinceValue+"'] > city").each(function(){
                    var $city = $(this);
                    sel2.append("<option value='"+$city.attr('value')+"'>"+$city.attr('value')+"</option>");
                });
                cityValue = $("#city").val();
                if(typeof v_city != 'undefined'){
                    sel2.val(v_city);
                    sel2.change();
                }
            });
        }
    }).change();
    return _self;
};