<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <#include "include.ftl"/>

</head>
<title>Payment</title>
</head>
<body>
    <div>
        <form name="alipayment" action="pay" method=post>
            <div id="body" style="clear:left">
                <dl class="content">
                    <dt>交易流水号：</dt>
                    <dd>
                        <span class="null-star">*</span>
                        <input size="30" name="tranCode" />
                    </dd>
                    <dt>交易名称：</dt>
                    <dd>
                        <span class="null-star">*</span>
                        <input size="30" name="tranName" />
                    </dd>
                    <dt>金额：</dt>
                    <dd>
                        <span class="null-star">*</span>
                        <input size="30" name="tranMoney" />
                    </dd>
                    <dt>充值类型 ：</dt>
                    <dd>
                        <span class="null-star">*</span>
                        <select name="tranName" style="width: 220px;">
                            <option value="1">账户充值</option>
                            <option value="3">账户消费</option>
                            <option value="5">退款</option>
                            <option value="7">转出</option>
                            <option value="9">转入</option>
                            <option value="11">正调账</option>
                            <option value="13">反调账</option>
                        </select>
                        <span></span>
                    </dd>
                    <dt>用户号 ：</dt>
                    <dd>
                        <span class="null-star">*</span>
                        <input size="30" name="customerNo" />
                        <span>个人：个人在平台唯一编号 商户：商户号</span>
                    </dd>
                    <dt>订单号 ：</dt>
                    <dd>
                        <span class="null-star">*</span>
                        <input size="30" name="orderNumber" />
                        <span>同商户ID</span>
                    </dd>
                    <dt>业务类型代码 ：</dt>
                    <dd>
                        <span class="null-star">*</span>
                        <input size="30" name="businessType" />
                        <span></span>
                    </dd>
                    <dt>来源 ：</dt>
                    <dd>
                        <span class="null-star">*</span>
                        <input size="30" name="source" />
                        <span></span>
                    </dd>
                    <dt>实际交易金额 ：</dt>
                    <dd>
                        <span class="null-star">*</span>
                        <input size="30" name="realTranMoney" />
                        <span></span>
                    </dd>
                    <dt>清算标志 ：</dt>
                    <dd>
                        <span class="null-star">*</span>
                        <select name="clearFlag" style="width: 220px;">
                            <option value="0">未清算</option>
                            <option value="1">已清算</option>
                        </select>
                        <span></span>
                    </dd>
                    <dt>是否外接 ：</dt>
                    <dd>
                        <span class="null-star">*</span>
                        <select name="merchantType" style="width: 220px;">
                            <option value="true">是</option>
                            <option value="false">不是</option>
                        </select>
                        <span></span>
                    </dd>
                    <dt>客户类型 ：</dt>
                    <dd>
                        <span class="null-star">*</span>
                        <select name="customerType" style="width: 220px;">
                            <option value="0">是</option>
                        </select>
                        <span></span>
                    </dd>
                    <dt>支付方式Id ：</dt>
                    <dd>
                        <span class="null-star">*</span>
                        <input size="30" name="payWayId" />
                        <span></span>
                    </dd>
                    <dt>商品名称 ：</dt>
                    <dd>
                        <span class="null-star">*</span>
                        <input size="30" name="commodityName" />
                        <span></span>
                    </dd>
                    commodityName
                    <dt></dt>
                    <dt>操作Id ：</dt>
                    <dd>
                        <span class="null-star">*</span>
                        <input size="30" name="operateId" />
                        <span></span>
                    </dd>
                    <dt></dt>
                    <dd>
                        <span class="new-btn-login-sp">
                            <button class="new-btn-login" type="submit" style="text-align:center;">确 认</button>
                        </span>
                    </dd>
                </dl>
            </div>
        </form>
    </div>
</body>
</html>