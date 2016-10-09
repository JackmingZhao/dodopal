//效验补充信息
function checkVerValue() {

    //当创建的是连锁直营网点 且 是自动转账 额度和阀值是必填的
    var bitian = false;
    var isAutoEditVer = '';
    $('input[name=isAutoEditVer]').each(function (i, v) {
        if ($(v).is(':checked')) {
            isAutoEditVer = $(v).val();
        }
    });

    if (isAutoEditVer == '0') {
        //额度阀值
        var isThresholdEditVer = validateThresholdEditVer(true);
        //额度
        var isLimitEditVer = validateLimitEditVer(true);
        if (isThresholdEditVer && isLimitEditVer) {
            bitian = true;
        }
    } else {
        bitian = true;
    }

    var isMerEmailVer = validateMerEmailVer(true);
    var isMerTelephoneVer = validateMerTelephoneVer(true);
    var isMerFaxVer = validateMerFaxVer(true);
    var isMerUserIdentityNumberVer = checkIdentityNumber(true, 'merUserIdentityTypeVer', 'merUserIdentityNumberVer', 'merUserIdentityNumberVerERR');
    var isMerZipVer = validateMerZipVer(true);
    var isMerBankAccountVer = checkMerBankAccount(true, 'merBankNameVer', 'merBankAccountVer', 'merBankAccountVerERR');
    var isMerBankUserNameVer = validateMerBankUserNameVer(true);
    return isMerEmailVer
        && isMerTelephoneVer
        && isMerFaxVer
        && isMerZipVer
        && isMerBankAccountVer
        && isMerBankUserNameVer
        && isMerUserIdentityNumberVer
        && bitian;
}


//清空审核编辑界面
function clearChildMerchantVerView() {
    $('#yktinfoVerViewSpan').html('');
    //清空查看子商户详情界面上的通卡公司费率
    $('#yktTableVer tbody').empty();
}

//审核通过 编辑页面 连锁商户直营网点和 加盟网点  自动转账的额度和阀值- 切换
function isAutoEditClick(flag) {
    if (flag == 'true') {
        $('#thresholdEditShow').show();
    } else {
        $('#thresholdEditShow').hide();
    }

}

//审核通过编辑加载数据
function loadChildMerchantVerEdit(data) {
    var merchantUserBeans = data.merchantUserBean;
    var merBusRateBeanList = data.merBusRateBeanList;
    var merRateSpmtList = data.merRateSpmtList;
    var merDdpInfoBean = data.merDdpInfoBean;
    var merAutoAmtBean = data.merAutoAmtBean;
    clearChildMerchantVerView();
    //加载完毕打开界面
    openChildMerchantView(2);
    //---------------------不可编辑的审核条件--------------
    //商户编码
    $('#merCodeVer').val(data.merCode);
    //商户类型
    $('#merTypeVer').html(data.merTypeView);
    //审核状态
    $('#merStateVer').html(data.merStateView);
    //商户名称
    $('#merNameVer').html(data.merName);
    //用户名
    $('#merUserNameVer').html(merchantUserBeans.merUserName);
    //联系人
    $('#merLinkUserVer').html(data.merLinkUser);
    //手机号码
    $('#merLinkUserMobileVer').html(data.merLinkUserMobile);
    //地址
    $('#merAddsVer').html(data.merProName + data.merCityName + data.merAdds);
    //业务类型
    var rateCodeView = '';
    $(merRateSpmtList).each(function (i, v) {
        if (rateCodeView != '') {
            rateCodeView += ',';
        }
        rateCodeView += v.rateCodeView;
    });
    $('#rateCodeView').html(rateCodeView);
    //通卡公司费率信息
    if (merBusRateBeanList != null && merBusRateBeanList != '') {
        loadYktVerView(merBusRateBeanList);
        $('#yktDivView').show();
        $("#yktTableVer").show();
    } else {
        $('#yktDivView').hide();
        $("#yktTableVer").hide();
    }
    //---------------------可编辑的审核条件--------------
    //经营范围
    $('#merBusinessScopeIdVer').val(data.merBusinessScopeId);
    //邮箱
    $('#merEmailVer').val(data.merEmail);
    //固定电话
    $('#merTelephoneVer').val(data.merTelephone);
    //传真
    $('#merFaxVer').val(data.merFax);
    //证件类型
    $('#merUserIdentityTypeVer').val(merchantUserBeans.merUserIdentityType);
    //证件号码
    $('#merUserIdentityNumberVer').val(merchantUserBeans.merUserIdentityNumber);
    //邮编
    $('#merZipVer').val(data.merZip);
    //开户银行
    $('#merBankNameVer').val(data.merBankName);
    //开户行账号
    $('#merBankAccountVer').val(data.merBankAccount);
    //开户名称
    $('#merBankUserNameVer').val(data.merBankUserName);


    if (merchantUserBeans.income == null || merchantUserBeans.income == '') {
        merchantUserBeans.income == '';
    } else {
        merchantUserBeans.income = Number(merchantUserBeans.income / 100).toFixed(2);
    }

    //学历
    $('#merUserEducationVer').val(merchantUserBeans.education);
    //生日
    $('#merUserBirthdayVer').val(merchantUserBeans.birthday);
    //收入
    $('#merUserIncomeVer').val(merchantUserBeans.income);
    //婚姻状况
    $('#merUserIsMarriedVer').val(merchantUserBeans.isMarried);


    //备注
    $('#merUserRemarkVer').val(merchantUserBeans.merUserRemark);
    selectUiInit();


    //---------------------新增 连锁商户直营网点 和连锁加盟网点 自动转账的额度和阀值--------
    if (merDdpInfoBean == null) {
        $('#isAutoEditShow').hide();
        $('#thresholdEditShow').hide();
        //$('#isAutoJoinEditShow').hide();

    } else {
        // 13 连锁商户直营网点
        //if(data.merType=='13'){
        if (merDdpInfoBean.isAutoDistribute == '0') {
            $('#isAutoEditShow').show();
            $("input[name='isAutoEditVer'][value=0]").prop('checked', true);
            $('#thresholdEditShow').show();
            //$('#isAutoJoinEditShow').hide();

            if (merAutoAmtBean == null) {
                $('#thresholdEditVer').val('');
                $('#limitEditVer').val('');
            } else {
                $('#thresholdEditVer').val(merAutoAmtBean.limitThreshold);
                $('#limitEditVer').val(merAutoAmtBean.autoLimitThreshold);
            }

        }
        if (merDdpInfoBean.isAutoDistribute == '1') {
            $('#isAutoEditShow').show();
            $("input[name='isAutoEditVer'][value=1]").prop('checked', true);
            $('#thresholdEditShow').hide();
            //$('#isAutoJoinEditShow').hide();
        }
        if (merDdpInfoBean.isAutoDistribute == '2') {
            $('#isAutoEditShow').show();
            $("input[name='isAutoEditVer'][value=2]").prop('checked', true);
            $('#thresholdEditShow').hide();
            //$('#isAutoJoinEditShow').hide();
        }

        //}
        //连锁商户加盟网点
//			if(data.merType=='14'){
        //	$('#isAutoEditShow').hide();
        //	$('#thresholdEditShow').hide();
        //$('#isAutoJoinEditShow').show();
//				if(merDdpInfoBean.limitSource=='0'){
//					$("input[name='isAutoJoinEditVer'][value=0]").prop('checked',true);
//				}else{
//					$("input[name='isAutoJoinEditVer'][value=1]").prop("checked",true);
//				}

//			}
    }

    //---------------------新增 连锁商户直营网点 自动转账的额度和阀值 --------
    //显示审核通过编辑界面
    $('#childMerchantEdit').show();
}
//-----------------------------------审核通过编辑业务费率加载开始------------------------------------------
//加载通卡公司费率
function loadYktVerView(merBusRateBeanList) {
    var selected = '';
    if (merBusRateBeanList && merBusRateBeanList.length > 0) {
        $(merBusRateBeanList).each(function (i, v) {
            if (selected != '') {
                selected += ',';
            }
            selected += v.proName;

            $('.null-box').hide();
            html = '<tr>';
            html += '<td class="nobor">&nbsp;</td>';

            html += '<td>'
            html += v.proName;
            html += '</td>';

            html += '<td>'
            html += v.rateName;
            html += '</td>';

            html += '<td>'
            html += v.rateTypeView;
            html += '</td>';

            html += '<td>'
            html += v.rate;
            html += '</td>';

            html += '<td class="nobor">&nbsp;</td>';
            html += '</tr>';
            //业务费率表 tbody
            $('#yktinfoVerView').append(html);
        });
        //城卡公司名称
        $('#yktinfoVerViewSpan').html(selected);
    }
}
//-----------------------------------审核通过编辑业务费率加载结束------------------------------------------
//---------------------------------------------已审核编辑保存开始--------------------------------------------
//保存子商户信息
function saveChildMerchantVer() {
    if (!checkVerValue()) {
        return false;
    }

    var isAutoEditVer = '';
    $('input[name=isAutoEditVer]').each(function (i, v) {
        if ($(v).is(':checked')) {
            isAutoEditVer = $(v).val();
        }
    });

//	var isAutoJoinEditVer ='';  
//	$('input[name=isAutoJoinEditVer]').each(function(i,v){
//		if($(v).is(':checked')){
//			isAutoJoinEditVer=$(v).val();
//		}
//	});

    //商户补充信息
    var merDdpInfoBean = {
        isAutoDistribute: isAutoEditVer
        //limitSource : isAutoJoinEditVer
    };

    var merAutoAmtBean = null;

    if (isAutoEditVer == '0') {
        //自动转账额度和阀值
        merAutoAmtBean = {
            limitThreshold: $('#thresholdEditVer').val(),
            autoLimitThreshold: $('#limitEditVer').val()
        };
    }


    var merchantUserBean = {
        merUserName: $('#merUserNameVer').html(),
        merUserTelephone: $('#merTelephoneVer').val(),
        merUserIdentityType: $('#merUserIdentityTypeVer').val(),
        merUserIdentityNumber: $('#merUserIdentityNumberVer').val(),
        merUserEmail: $('#merEmailVer').val(),
        education: $('#merUserEducationVer').val(),
        income: $('#merUserIncomeVer').val() == '' ? '' : Number($('#merUserIncomeVer').val()) * 100,
        birthday: $('#merUserBirthdayVer').val(),
        isMarried: $('#merUserIsMarriedVer').val(),
        merUserRemark: $('#merUserRemarkVer').val()
    };
    var merchantBeans = {
        merCode: $('#merCodeVer').val(),
        merBusinessScopeId: $('#merBusinessScopeIdVer').val(),
        merFax: $('#merFaxVer').val(),
        merZip: $('#merZipVer').val(),
        merEmail: $('#merEmailVer').val(),
        merTelephone: $('#merTelephoneVer').val(),
        merBankName: $('#merBankNameVer').val(),
        merBankAccount: $('#merBankAccountVer').val(),
        merDdpInfoBean: merDdpInfoBean,
        merAutoAmtBean: merAutoAmtBean,
        merBankUserName: $('#merBankUserNameVer').val(),
        merchantUserBean: merchantUserBean
    };
    ddpAjaxCall({
        url: "saveAndUpdataChildMerchant",
        data: merchantBeans,
        successFn: function (data) {
            if (data.code == "000000") {
                openChildMerchantView(0);
                $('#childMerchantMain').show();
                findChildMerchant($('.page-navi').paginator('getPage'));
            } else {
                //TODO error handler
                $.messagerBox({type: 'warn', title: "信息提示", msg: data.message});
            }
        }
    });

    return false;
}
//---------------------------------------------已审核编辑保存结束--------------------------------------------
