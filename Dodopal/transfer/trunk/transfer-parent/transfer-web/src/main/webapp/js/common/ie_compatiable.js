	
//fix IE 8 console.log is not defined issue when develop tool is not open 
if (typeof console === "undefined" || typeof console.log === "undefined") {
	console = {};
	console.log = function() {};
}

//fix IE 8 array indexOf method
if (!Array.prototype.indexOf) { 
    Array.prototype.indexOf = function(obj, start) {
         for (var i = (start || 0), j = this.length; i < j; i++) {
             if (this[i] === obj) { return i; }
         }
         return -1;
    }
}

Array.prototype.distinct = function(){
	var arr = [],
	     len = this.length;

	for ( var i = 0; i < len; i++ ){
		for( var j = i+1; j < len; j++ ){
			if( this[i] === this[j] ){
				j = ++i;
			}
		}
		arr.push( this[i] );
	}
	return arr;
};

if (!('lastIndexOf' in Array.prototype)) {
	Array.prototype.lastIndexOf= function(find, i /*opt*/) {
    if (i===undefined) i= this.length-1;
    if (i<0) i+= this.length;
    if (i>this.length-1) i= this.length-1;
    for (i++; i-->0;) /* i++ because from-argument is sadly inclusive */
        if (i in this && this[i]===find)
            return i;
    return -1;
    };
}

//这是为了解决 MSIE checkbox onchange 事件只有在失去焦点的时候才能触发。 
/*if ($.browser.msie) {  
  $(function() {  
    $('input:radio, input:checkbox').click(function() {  
      this.blur();  
      this.focus();  
    });  
  });  
} */