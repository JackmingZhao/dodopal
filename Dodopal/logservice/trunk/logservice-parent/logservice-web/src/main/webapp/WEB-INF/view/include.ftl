<link rel="shortcut icon" type="image/x-icon" href="${base}/favicon.ico"  media="screen"/>

<!-- Jquery Js-->
<script type='text/javascript' src='${scriptUrl}/common/jquery1.9.1/jquery-1.9.1.min.js'></script>
<script type='text/javascript' src='${scriptUrl}/common/jquery.ui.widget.min.js'></script>
<script type="text/javascript" src="${scriptUrl}/component/ddpMessager.js"></script>


<script type='text/javascript' src='${scriptUrl}/util/util.js'> </script>
<script type='text/javascript' src='${scriptUrl}/util/ddpValidationBox.js'> </script>
<script type='text/javascript' src='${scriptUrl}/util/constant.js'> </script>
<script type='text/javascript' src='${scriptUrl}/util/md5.js'> </script>
<script type='text/javascript' src='${scriptUrl}/util/date.js'> </script>

<script type='text/javascript' src='${base}/js/logserviceUtil.js'></script>
<script type="text/javascript" src="${base}/common/permission.js"></script>
<script type="text/javascript" src="${base}/common/warnMessageCom.js"></script>
<script type="text/javascript" src="${base}/common/select.js"></script>
<script type="text/javascript" src="${base}/common/ddpDdic.js"></script>
<script type="text/javascript" src="${base}/common/area.js"></script>
<script type='text/javascript' src='${scriptUrl}/util/textareaMaxlength.js'> </script>



<script>
$.base ='${base}';
$.scriptUrl = '${scriptUrl}';
$.styleUrl = '${styleUrl}';
if (typeof console === "undefined" || typeof console.log === "undefined") {
	console = {};
	console.log = function(data) {
	alert(data);
	};
}

</script>
