<h3 class="tit-h3">
<@sec.accessControl permission="merchant.child.info"><a href="${base}/childMerchant/childMerchantTypeMgr">子商户信息</a><i class="i-line">|</i></@sec.accessControl>

<@sec.accessControl permission="merchant.child.act">
<a href="${base}/merchantChildAct/amt" >子商户账户信息</a><i class="i-line">|</i>
</@sec.accessControl>


<@sec.accessControl permission="merchant.child.pos"><a href="${base}/pos/toChildrenPosList">子商户POS管理</a><i class="i-line">|</i></@sec.accessControl>
<@sec.accessControl permission="merchant.child.tranlist">
<a href="${base}/merchantChildTranlist/childMerchantRecord">子商户交易记录</a><i class="i-line">|</i>
</@sec.accessControl>
<@sec.accessControl permission="merchant.child.order">
<a href="${base}/childMerProductOrder/childMerProductOrderMgr">业务订单管理</a><i class="i-line">|</i>
</@sec.accessControl>
<@sec.accessControl permission="merchant.child.ordersum">
<a href="${base}/childMerProductOrder/childMerProductOrderSum">业务订单汇总</a>
</@sec.accessControl>
</h3>
<!--class="cur"-->  