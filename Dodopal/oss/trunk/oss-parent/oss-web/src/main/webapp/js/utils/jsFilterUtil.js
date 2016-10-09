function stripscript(id) { 
	var s = $('#'+id).val();
	 var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）&;—|{}【】‘；：”“'。，、？]")
    return pattern.test(s); 
} 
function testValue(s) { 
	 var pattern = new RegExp("[`~!#$^&*()=|{}':;',\\[\\].<>/?~！#￥……&*（）&;—|{}【】‘；：”“'。，、？]")
    return pattern.test(s); 
}
function nameTest(s) { 
	 var pattern = new RegExp("[`~!#$^&*%()=|{}':;',\\[\\]<>/?~！￥……&*（）&;—|{}【】‘；：”“'。，、？]")
   return pattern.test(s); 
}
/**
/^1[3458]\d{9}$/
/^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/
 * 
 */
function testModelValue(mobile) { 
	  var myreg = /^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/;
	  return myreg.test(mobile);
}

function testFormNameValue(methed,formName) { 
	if(checkAllTextValid(formName)){
		msgShow(systemWarnLabel, "输入的文字包含非法文字", 'warning');
	}else{
		methed();
	}
} 
function onkey(id)  
{  
    if (window.event.keyCode==13)  
    {  
        document.getElementById(id).focus();  
        return false;  
    }  
}  


function checkAllTextValid(form) {
   //记录不含引号的文本框数量
   var resultTag = 0;
   //记录所有text文本框数量
//   var flag = 0;
   for (var i = 0; i < form.elements.length; i++) {
       if (form.elements[i].type == "text") {//||form.elements[i].type == "textarea"
//           flag = flag + 1;
           //用户名被检测出含有非法
//           var pattern = new RegExp("[`~!#$^&*%()=|{}':;'\\[\\]<>/?~！￥……&*（）&;—|{}【】‘；：”“'、？]")
//           if (pattern.test(form.elements[i].value))
//        	   return true;
    	   //检测关键字 
           if(form.elements[i].value.toLowerCase().indexOf('alert')!=-1||form.elements[i].value.toLowerCase().indexOf('document')!=-1||form.elements[i].value.toLowerCase().indexOf('script')!=-1){
        	   return true;
           }
       }
   }
}