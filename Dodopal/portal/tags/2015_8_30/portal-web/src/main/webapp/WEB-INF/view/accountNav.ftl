<h3 class="tit-h3">
	<@sec.accessControl permission="ddp.acct.base">
		<a href="${base}/ddp/showAccountSetInfo">基本信息</a><i class="i-line">|</i>
	</@sec.accessControl>
	<@sec.accessControl permission="ddp.acct.pay">
		<a href="${base}/ddp/showAccountPayway">支付方式</a><i class="i-line">|</i>
	</@sec.accessControl>
	<@sec.accessControl permission="ddp.acct.secure">
		<a href="${base}/secure/showAccountSecureMgr">安全设置</a><i class="i-line">|</i>
	</@sec.accessControl>	
	<@sec.accessControl permission="ddp.acct.personal">
		<a href="${base}/secure/showAccountPersonalMgr">个性化设置</a>
	</@sec.accessControl>
</h3>