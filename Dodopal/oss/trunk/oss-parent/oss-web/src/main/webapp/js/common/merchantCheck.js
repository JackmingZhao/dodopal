$.extend($.fn.validatebox.defaults.rules, {
	merName: {
        validator: function (value, param) {
            return /^[a-zA-Z\u4e00-\u9fa5][a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value);
        },
        message: '请输入商户名称,可以由中文、字母或数字组成'
    },
    merUserName: {
        validator: function (value, param) {
            return /^[a-zA-Z0-9\x7f\xff_]{3,16}$/.test(value);
        },
        message: '请输入用户名,支持中文、字母、数字及“_”,第一位必须以字母开头'
    },
    merLinkUser: {
        validator: function (value, param) {
            return /^[a-zA-Z\u4e00-\u9fa5]+$/.test(value);
        },
        message: '可由2-20字符，可由中文或英文组成。'
    },
    mobile: {
        validator: function (value, param) {
            return /^13\d{9}|14[57]\d{8}|15[012356789]\d{8}|18[01256789]\d{8}|170\d{8}$/.test(value);
        },
        message: '手机号码不正确'
    },
    merUserIdentityNumber: {
        validator: function (value, param) {
            return value.length >= param[18];
        },
        message: '邮箱格式不正确'
    },
    merBankAccount: {
        validator: function (value, param) {
            return  /^(998801|998802|622525|622526|435744|435745|483536|528020|526855|622156|622155|356869|531659|622157|627066|627067|627068|627069)\d{10}$/.test(value);
        },
        message: '邮箱格式不正确'
    },
    email: {
        validator: function (value, param) {
            return /^[_a-z\d\-\./]+@[_a-z\d\-]+(\.[_a-z\d\-]+)*(\.(info|biz|com|edu|gov|net|am|bz|cn|cx|hk|jp|tw|vc|vn))$/.test(value);
        },
        message: '邮箱格式不正确'
    },
    merfax: {
        validator: function (value, param) {
            return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/.test(value);
        },
        message: '传真格式不正确'
    },
    telephone: {
        validator: function (value, param) {
            return   /^0\d{2,3}-\d{5,9}|0\d{2,3}-\d{5,9}$/.test(value);
        },
        message: '固定电话格式不正确'
    },
    merZip: {
            validator: function (value, param) {
                return /^[1-9]d{5}(?!d)$/.test(value);
            },
            message: '邮编格式不正确'
   }
});

$.fn.validatebox.defaults.missingMessage = '请输入此项';
