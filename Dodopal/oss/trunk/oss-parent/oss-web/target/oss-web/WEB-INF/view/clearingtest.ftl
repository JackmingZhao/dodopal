<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <#include "include.ftl"/>

</head>
<title>Clearing</title>
</head>
<body>
    <div>
        <form name="YKTRechargeClearingData" action="YKTRechargeClearingData" method=post>
           <span class="new-btn-login-sp">
            <button class="new-btn-login" type="submit" style="text-align:center;">一卡通充值</button>
           </span>
        </form>
        <br/>
        <form name="YKTPurchaseClearingData" action="YKTPurchaseClearingData" method=post>
           <span class="new-btn-login-sp">
            <button class="new-btn-login" type="submit" style="text-align:center;">一卡通消费</button>
           </span>
        </form>
        <br/>
        <form name="YKTRechargeClearingData" action="accountRechargeClearingData" method=post>
           <span class="new-btn-login-sp">
            <button class="new-btn-login" type="submit" style="text-align:center;">账户充值</button>
           </span>
        </form>
        <br/>
    	<form name="settement" action="settement" method=post>
           <span class="new-btn-login-sp">
            <button class="new-btn-login" type="submit" style="text-align:center;">清算</button>
           </span>
        </form>
        <br/>
        <form name="profit" action="profit" method=post>
           <span class="new-btn-login-sp">
            <button class="new-btn-login" type="submit" style="text-align:center;">分润</button>
           </span>
        </form>
        <form name="profit" action="profitCollect" method=post>
           <span class="new-btn-login-sp">
            <button class="new-btn-login" type="submit" style="text-align:center;">分润汇总</button>
           </span>
        </form>
        <form name="repair" action="repairOrder" method=post>
           <span class="new-btn-login-sp">
            <button class="new-btn-login" type="submit" style="text-align:center;">补单</button>
           </span>
        </form>
    </div>
</body>
</html>