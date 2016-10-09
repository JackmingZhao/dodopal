package com.dodopal.common.constant;

/**
 * 公共响应码
 */
public class ResponseCode {

    /***************************************** 通用响应码开始 *****************************************/
    // 表示成功
    public static final String SUCCESS = "000000";

    // 未知错误，需要联系管理员
    public static final String UNKNOWN_ERROR = "999999";

    // 系统错误
    public static final String SYSTEM_ERROR = "999998";

    // 数据库系统错误
    public static final String DATABASE_ERROR = "999997";

    // JSON 转换异常
    public static final String JSON_ERROR = "999996";

    // 启用标志错误
    public static final String ACTIVATE_ERROR = "999995";

    //创建人不能为空
    public static final String CREATE_USER_NULL = "999994";

    //更新人不能为空
    public static final String UPDATE_USER_NULL = "999993";

    //登录人为空
    public static final String LOGIN_USER_NULL = "999992";

    // Hessian链接异常
    public static final String HESSIAN_ERROR = "999991";

    // 手机发送验证码异常
    public static final String SEND_MOBILE_CHECKCODE_ERROR = "999990";

    // 手机发送验证码异常
    public static final String SESSION_TIME_OUT = "999989";
    
    // ID不能为空
    public static final String ID_NULL = "999988";

    //无数据导出
    public static final String EXCEL_EXPORT_NULL_DATA = "999987";
    
    /**
     * 登录超时，请重新登录
     */
    public static final String LOGIN_TIME_OUT = "999986";

    // 导出记录最大限制5000条，请重新选择导出条件
    public static final String EXCEL_EXPORT_OVER_MAX = "999985";
    
    // 输入的验证码不正确
    public static final String VERIFICATION_CODE_ERROR = "999984";

    // 导出模板不存在
    public static final String EXCEL_TEMPLATE_NOT_FOUND = "999983";
    
    // 4.2.2.4  手持pos机充值订单查询失败
    public static final String V71_POS_ERROR = "999982";

    /***************************************** 通用响应码结束 *****************************************/

    /***************************************** OSS响应码开始 10XXXX 开头 *****************************************/
    //  OSS 连接用户系统失败
    public static final String OSS_USER_CONNECTION_ERROR = "109991";

    // OSS 调用产品库出错
    public static final String OSS_PRD_CONNECT_ERROR = "109992"; //OSS 调用产品库出错

    public static final String OSS_MENU_VALIDATOR = "100001";

    public static final String OSS_ROLE_VALIDATOR = "100002";

    public static final String OSS_DDIC_VALIDATOR = "100003";

    public static final String OSS_DEPARTMENT_VALIDATOR = "100004";

    public static final String OSS_EXTERNAL_VALIDATOR = "100005";

    public static final String OSS_EXTERNAL_ID_EMPTY = "100006";

    public static final String OSS_USER_VALIDATOR = "100010";

    public static final String OSS_POS_COMPANY_EMPTY = "100020";

    public static final String OSS_MER_NULL = "100030";

    public static final String OSS_POS_OPERATE_EMPTY = "100031";

    public static final String OSS_POS_BATCH_UPDATE_EMPTY = "100032";

    public static final String OSS_BATCH_UPLOAD_OVER_MAXSIZE = "100033"; //文件大小超出限制

    public static final String OSS_BATCH_UPLOAD_INCORRECT_FILE = "100034"; //文件格式不正确

    public static final String OSS_BATCH_UPLOAD_FILE_EMPTY = "100035"; //文件为空

    public static final String OSS_BATCH_UPDATE_ERROR = "100036"; //文件批处理出错

    public static final String OSS_BATCH_UPLOAD_FILE_PART_SUCCESS = "100037"; //文件导入部分成功

    public static final String OSS_POS_DELETE_BIND = "100038"; //POS文件批处理出错

    //OSS查询自定义商户功能信息失败
    public static final String OSS_MER_FUNCTION_INFO = "100040";
    //OSS停启用商户信息失败
    public static final String OSS_START_AND_DISABLE = "100041";
    //OSS删除审核不通过商户信息失败
    public static final String OSS_DELETE_NOT_MERCHANT = "100042";
    //OSS产品库一卡通产品数据入库内容为空！
    public static final String OSS_PERSIS_UPDATE_NULL = "100043";
    //OSS产品库一卡通产品页面请求参数为空！
    public static final String OSS_REQUEST_PARAM_NULL = "100044";
    //开户费率输入的单笔返点金额(元)不合法
    public static final String OSS_MER_SINGLE_AMOUNT_ERROR = "100045";
    //开户费率输入的千分比(‰)不合法
    public static final String OSS_MER_PERMILLAGE_ERROR = "100046";
    //****************   OSS 处理异常交易流水；处理一卡通充值异常      相关异常响应码      START     ******************//
 
    public static final String OSS_PAY_TRATRANSACTION_NULL = "101000";//  交易流水信息不存在
    public static final String OSS_ACCOUNT_NULL = "101001";//  主账户信息不存在
    public static final String OSS_ACCOUNT_FUND_NULL = "101002";//  资金账户信息不存在
    public static final String OSS_TOTAL_AMOUNT_OUTNUMBER_ERROR = "101003";//  账户总余额超过DB最大可容纳量
    public static final String OSS_SUM_TOTAL_AMOUNT_OUTNUMBER_ERROR = "101004";//  账户累计总余额超过DB最大可容纳量
    public static final String OSS_ACCOUNT_CHANGE_RECORD_EXSIT = "101005";//  账户充值资金变动记录已存在
    
    public static final String OSS_RECHARGE_ORDER_NULL = "101006";//  一卡通充值订单不存在
    public static final String OSS_PAY_TRATRANSACTION_STATE_ERROR = "101007";//  交易流水状态不正
    public static final String OSS_AVAILABLE_BALANCE_OUTNUMBER_ERROR = "101008";//  账户可用金额超过DB最大可容纳量
    public static final String OSS_FROZEN_AMOUNT_NOT_ENOUGH_ERROR = "101009";//  账户冻结金额小于解冻金额
    public static final String OSS_ACCOUNT_CHANGE_UNFREEZE_RECORD_EXSIT = "101010";//  账户解冻资金变动记录已存在
    public static final String OSS_DEDUCT_FROZEN_AMOUNT_NOT_ENOUGH_ERROR = "101011";//  账户冻结金额小于扣款金额
    public static final String OSS_ACCOUNT_CHANGE_DEDUCT_RECORD_EXSIT = "101012";//  账户扣款资金变动记录已存在
    
    public static final String OSS_ACCOUNT_CHANGE_FREEZE_RECORD_NULL = "101013";//  账户冻结资金变动记录不存在
    public static final String OSS_ACCOUNT_AUTHORIZED_NULL = "101014";//  授信账户信息不存在
    
    //****************   OSS 处理异常交易流水；处理一卡通充值异常      相关异常响应码      END     ******************//

    /***************************************** OSS响应码结束 *****************************************/

    /***************************************** 门户响应码开始 11XXXX 开头 *****************************************/
    public static final String PORTAL_USER_LOGIN_ERROR = "119999"; //门户系统登录出错

    public static final String PORTAL_MENU = "110001";

    public static final String PORTAL_MOB_ERROR = "110002"; // 输入的手机号码格式错误

    public static final String PORTAL_REGISTER_USER_ERR = "110003";

    public static final String PORTAL_REGISTER_MER_ERR = "110004";
    //子商户信息初始化查询失败
    public static final String PORTAL_CHILD_MERCHANT_FIND_ERR = "110005";
    //子商户添加商户信息失败
    public static final String PORTAL_SAVE_CHILD_MERCHANT_ERR = "110006";
    //子商户编辑商户信息失败
    public static final String PORTAL_UP_CHILD_MERCHANT_ERR = "110007";
    //子商户停用/启用商户信息失败
    public static final String PORTAL_START_DIA_CHILD_MERCHANT_ERR = "110008";
    //子商户查看单条商户信息失败
    public static final String PORTAL_CHILD_MERCHANT_BY_CODE_ERR = "110009";
    //子商户查看单条商户信息失败
    public static final String PORTAL_CHILD_MERCHANT_CHECK = "110010";

    public static final String PORTAL_MERGPUSER_BATCH_UPDATE_VALIDATE_ERROR = "110031";

    public static final String PORTAL_MERGPUSER_BATCH_UPDATE_EMPTY = "110032";

    public static final String PORTAL_MERGPUSER_BATCH_UPLOAD_OVER_MAXSIZE = "110033"; //文件大小超出限制

    public static final String PORTAL_MERGPUSER_BATCH_UPLOAD_INCORRECT_FILE = "110034"; //文件格式不正确

    public static final String PORTAL_MERGPUSER_BATCH_UPLOAD_FILE_EMPTY = "110035"; //文件为空

    public static final String PORTAL_MERGPUSER_BATCH_UPDATE_ERROR = "110036"; //POS文件批处理出错

    public static final String PORTAL_CAPTCHA_ERROR = "110037"; //验证码错误

    public static final String PORTAL_MOBILE_DYPWD_ERROR = "110038"; //手机验证码错误

    public static final String PORTAL_MOBILE_DYPWD_EXPIRED = "110039"; //手机验证码过期
    //公交卡充值订单初始化查询失败
    public static final String PRODUCT_ORDER_FIND_ERR = "110040";
    //查看用户选择一条公交卡充值订单失败
    public static final String PRODUCT_ORDER_FIND_BY_CODE_ERR = "110041";
    //历史记录查询失败
    public static final String PRODUCT_HISTORY_RECORD_ERR = "110042";

    //子商户业务订单初始化查询失败
    public static final String PORTAL_CHILD_MERCHANT_PRODUCT_ORDER_ERR = "110043";

    //一卡通消费 订单详情 查询失败
    public static final String PORTAL_PRODUCT_CONSUME_ORDER_FIND_ERR = "110044";

    /****************************************************************** add by xiong **/

    //商户的该折扣已经存在
    public static final String PORTAL_USER_DISCOUNT_EXIST = "110045";

    //新增商户折扣失败
    public static final String PORTAL_USER_ADD_DISCOUNT_FAIL = "110046";

    //新增商户直营网点的折扣失败
    public static final String PORTAL_USER_CHILD_MER_ADD_DISCOUNT_FAIL = "110047";

    //编辑商户直营网点的折扣失败
    public static final String PORTAL_USER_CHILD_MER_Edit_DISCOUNT_FAIL = "110048";
    
    //商户未开通此业务
    public static final String PORTAL_APP_NOT_OPPEN_ERR = "110049";
    
    //查询公交卡充值订单失败，请重新登录
    public static final String PRODUCT_RECHARGE_ORDER_FIND_ERR = "110050";
    
    //查询公交卡消费订单失败，请重新登录
    public static final String PRODUCT_CONSUME_ORDER_FIND_ERR = "110051";
    
    //门户访问产品库异常
    public static final String PORTAL_PRODUCT_HESSIAN_ERR = "110060";

    // 联合登录传入的失败返回地址为空
    public static final String PORTAL_UNION_LOGIN_BACKURL_NULL = "110061";


    //查询时间段过长，不能超过三天
    public static final String PORTAL_TIME_ERR = "110062";
    /***************************************** 门户响应码结束 *****************************************/

    /***************************************** 用户响应码开始 12XXXX 开头 *****************************************/

    public static final String USERS_MENU = "120001";

    //手机号格式不正确
    public static final String USERS_MOB_TEL_ERR = "120002";

    //短信发送失败
    public static final String USERS_SEND_MOB_CODE_ERR = "120003";

    //手机号码已注册
    public static final String USERS_MOB_EXIST = "120004";

    //验证码发送失败，此手机号未注册过，请检查
    public static final String USERS_MOB_NO_EXIST = "120005";

    //验证码过期或输入错误，请检查或重新获取。
    public static final String USERS_MOB_CODE_ERR = "120006";

    //手机号码尚未注册(忘记密码时)
    public static final String USERS_MOB_NOT_EXIST = "120007";

    //商户用户为管理员用户，无法停启用
    public static final String USERS_USER_ADMIN_ERR = "120019";
    //用户信息没找到
    public static final String USERS_FIND_USER_ERR = "120020";
    //商户管理员信息没找到
    public static final String USERS_FIND_USER_ADMIN_ERR = "120021";
    //商户信息没找到
    public static final String USERS_FIND_MERCHANT_ERR = "120022";
    //上级商户信息没找到
    public static final String USERS_FIND_PARENT_MERCHANT_ERR = "120023";
    //用户与商户不符
    public static final String USERS_USER_MER_ERR = "120024";

    //用户查询来源错误，0：门户；1：OSS
    public static final String USERS_FIND_FLG_ERR = "120027";
    //用户类型不能为空
    public static final String USERS_TYPE_NULL = "120028";
    //用户标志
    public static final String USERS_FLAG_NULL = "120029";
    //用户密码不能为空
    public static final String USERS_PWD_NULL = "120030";
    //手机号不能为空
    public static final String USERS_MOB_TEL_NULL = "120031";

    //用户名错误，必须首位为字母
    public static final String USERS_USER_NAME_ERR = "120032";
    //用户商户类型或测试用户标记不能为空
    public static final String USERS_MER_TYPE_NULL = "120033";

    //POS操作类型不能为空
    public static final String USERS_POS_OPER_NULL = "120034";

    //POS号不能为空
    public static final String USERS_POS_CODE_NULL = "120035";

    //POS绑定，商户号不能为空
    public static final String USERS_POS_BUND_MER_CODE_NULL = "120036";

    //POS操作，操作人信息不能为空
    public static final String USERS_POS_OPE_USER_NULL = "120037";

    //POS没有注册或POS非启用状态
    public static final String USERS_POS_NO_USE = "120038";

    //POS已绑定其他商户,不能再绑定
    public static final String USERS_POS_BUND_MER_CODE_EXIST = "120039";

    //公交卡号重复，请核对。
    public static final String USERS_CARD_CODE_EXIST = "120040";

    //pos无法重复绑定
    public static final String USERS_POS_BUND_MER_CODE_AGAIN_EXIST = "120041";

    //posId不能为空
    public static final String USERS_POS_ID_NULL = "120042";

    //统一提示：用户名或密码错误
    public static final String USERS_USER_NAME_OR_PWD_ERR = "120100";
    //用户名不存在
    public static final String USERS_USER_NAME_NO_EXIST = "120101";
    //密码错误
    public static final String USERS_PASSWORD_ERR = "120102";
    //用户已停用
    public static final String USERS_USER_DISABLE = "120103";
    //商户已停用
    public static final String USERS_MERCHANT_DISABLE = "120104";
    //未找到用户对应的商户信息
    public static final String USERS_MER_NOT_FOUND_DISABLE = "120105";
    //用户对应的商户号为空
    public static final String USERS_MER_CODE_NULL = "120106";
    //您没有权限，请联系管理员
    public static final String USERS_USER_AUTHORITY_NULL = "120107";
    //用户处于非审核通过状态
    public static final String USERS_USER_STATE_NO_THROUGH = "120108";
    //商户处于非审核通过状态
    public static final String USERS_MER_STATE_NO_THROUGH = "120109";

    //注册或审核时商户信息或用户信息不能为空
    public static final String USERS_MER_INFO_NULL = "120120";
    //商户名称已存在
    public static final String USERS_MER_NAME_EXIST = "120121";
    //商户类型错误
    public static final String USERS_MER_TYPE_ERR = "120122";
    //商户分类错误
    public static final String USERS_MER_CLASSIFY_ERR = "120123";
    //商户属性错误
    public static final String USERS_MER_PROPERTY_ERR = "120124";
    //用户注册来源错误
    public static final String USERS_MER_USER_SOURCE_ERROR = "120125";
    //子级商户费率数值超过父级商户
    public static final String USERS_MER_RATE_OVER_PARENT = "120126";
    //父级商户无此费率
    public static final String USERS_PARENT_RATE_NOT_FOUND = "120127";
    //商户审核状态错误
    public static final String USERS_MER_STATE_ERR = "120128";
    //商户名称不能为空
    public static final String USERS_MER_NAME_NULL = "120129";
    //用户名已存在
    public static final String USERS_MER_USER_NAME_EXIST = "120130";
    //联系人不能为空
    public static final String USERS_MER_LINK_USER_NULL = "120131";
    //审核人不能为空
    public static final String USERS_MER_STATE_USER_NULL = "120132";
    //省份不能为空
    public static final String USERS_MER_PRO_NULL = "120133";
    //城市不能为空
    public static final String USERS_MER_CITY_NULL = "120134";
    //地址不能为空
    public static final String USERS_MER_ADDS_NULL = "120135";
    //上级商户号不能为空
    public static final String USERS_MER_PARENT_CODE_NULL = "120136";
    //子商户与上级商户不一致
    public static final String USERS_CHILD_PARENT_INCONFORMITY = "120137";
    //当前状态不允许修改商户信息
    public static final String USERS_STATE_NOT_ALLOWED_UPDATE = "120138";
    //此商户不能创建下级商户
    public static final String USERS_MERCHANT_NOT_ALLOWED_CREATE = "120139";
    //上级商户已停用
    public static final String USERS_PARENT_MERCHANT_DISABLE = "120140";
    //供应商编码不能为空
    public static final String USERS_PROVIDER_CODE_NULL = "120141";
    //业务编码不能为空
    public static final String USERS_RATE_CODE_NULL = "120142";
    //费率类型不能为空
    public static final String USERS_RATE_TYPE_NULL = "120143";
    //用户名已注册
    public static final String USERS_MER_USER_NAME_REG = "120144";
    //商户名称已注册
    public static final String USERS_MER_NAME_REG = "120145";
    //一卡通充值业务必须勾选
    public static final String USERS_YKT_RECHARGE_NULL = "120146";
    //不能小于下级商户费率
    public static final String USERS_BELOW_CHILD_RATE = "120147";
    //该商户有关联的上级或者下级商户，如果要修改通卡公司/费率类型，请将相关连商户信息一并进行修改。是否要继续？
    public static final String USERS_RELATION_MERCHANT_NOT_SAME = "120148";
    //创建账户失败
    public static final String USERS_CREATE_ACCOUNT_FAIL = "120149";
    //账户类型不能为空
    public static final String USERS_FUND_TYPE_NULL = "120150";
    //用户审核状态错误
    public static final String USERS_MER_USER_STATE_ERR = "120151";
    //供应商商户或用户信息更新失败
    public static final String USERS_MER_PROVIDER_UPDATE_ERROR = "120152";

    //用户ID，不能为空。
    public static final String USERS_FIND_USER_ID_NULL = "120201";

    //商户编码不能为空。
    public static final String USERS_FIND_MER_CODE_NULL = "120202";

    //用户信息不能为空。
    public static final String USERS_MER_USER_NULL = "120203";

    //请检查以下字段是否为空：用户姓名、公交卡号、部门ID、商户号、充值金额
    public static final String USERS_MER_USER_INFO_NULL = "120204";

    //密码不能为空
    public static final String USERS_FIND_USER_PWD_NULL = "120205";
    //用户名不能为空
    public static final String USERS_FIND_USER_NAME_NULL = "120206";
    //启用标志不能为空
    public static final String USERS_FIND_USER_ACT_NULL = "120207";

    //支付方式不能为空
    public static final String USERS_FIND_PAYINFO_NULL = "120208";
    //用户编号不能为空
    public static final String USERS_USER_CODE_NULL = "120209";
    //尚未开通该业务城市
    public static final String USERS_USER_CITY_NOT_FOUND = "120210";
    //城市信息不存在
    public static final String USERS_CITY_INFO_NOT_FOUND = "120211";

    //验签数据未找到
    public static final String KEY_TYPE_NOT_FIND = "120300";

    //密码未找到
    public static final String KEY_TYPE_PWD_NOT_FIND = "120301";

    //秘钥缺少id
    public static final String KEY_TYPE_PWD_ID_NULL = "120302";

    //更新签名密钥失败
    public static final String UP_MER_MD5_PAY_PWD_ERR = "120303";

    //更新验签密钥失败
    public static final String UP_MER_MD5_BACK_PAY_PWD_ERR = "120304";

    //公交卡id不能为空
    public static final String MER_USER_CARD_BD_ID_NULL = "120401";

    //公交卡用户名不能为空
    public static final String MER_USER_CARD_BD_NAME_NULL = "120402";

    //绑卡信息未找到
    public static final String MER_USER_CARD_BD_INFO_NULL = "120403";

    //部门信息未找到
    public static final String MER_GROUP_DEP_INFO_NULL = "120501";

    // 重复的部门名称
    public static final String MER_GROUP_DEP_NAME_REPEAT = "120502";

    //部门下存在人员
    public static final String MER_GROUP_DEP_HAVE_PERSON = "120503";

    //部门id不能为空
    public static final String MER_GROUP_DEP_ID_NULL = "120504";

    // 部门名称不能为空
    public static final String MER_GROUP_DEP_NAME_NULL = "120505";

    //角色编码不能为空
    public static final String USERS_MER_ROLE_CODE_NULL = "120601";

    //角色名称不能为空
    public static final String USERS_MER_ROLE_NAME_NULL = "120602";

    //角色名称已存在
    public static final String USERS_MER_ROLE_NAME_EXIST = "120603";

    // 批次单充值项不能为空
    public static final String USERS_BATCH_RCG_ITEM_NULL = "120701";
    // 批次单充值项集团人员ID不能为空
    public static final String USERS_BATCH_RCG_GROUP_USER_ID_NULL = "120702";
    // 批次单充值项集团人员不存在6
    public static final String USERS_BATCH_RCG_GROUP_USER_NULL = "120703";
    // 批次单充值项集团人员卡号不能为空
    public static final String USERS_BATCH_RCG_GROUP_USER_CARD_NULL = "120704";
    // 批次单充值项集团人员充值金额必须大于0
    public static final String USERS_BATCH_RCG_GROUP_USER_RCG_AMT_NULL = "120705";
    // 批次单充值项集团人员必须为在职状态
    public static final String USERS_BATCH_RCG_GROUP_USER_EMP = "120706";

    //该商户尚未启用，请联系客服人员
    public static final String USERS_YKT_MERCHANT_DISABLE = "125001";

    //该商户尚未开通公交卡充值业务，请联系客服人员
    public static final String USERS_YKT_RECHARGE_DISABLE = "125002";

    //该操作员尚未启用，请联系商户管理员
    public static final String USERS_YKT_MER_USER_DISABLE = "125003";

    //POS设备尚未启用，请联系客服人员
    public static final String USERS_YKT_POS_DISABLE = "125004";

    //POS设备尚未绑定，请先绑定POS设备
    public static final String USERS_YKT_POS_UNBIND = "125005";

    // 该商户尚未开通一卡通圈存业务，请联系客服人员
    public static final String USERS_YKT_LOAD_DISABLE = "125096";
    // 您今日充值次数已达上限
    public static final String USERS_PERSONAL_RECHARGE_OVER_MAX = "125097";
    // 该商户尚未开通一卡通消费业务，请联系客服人员
    public static final String USERS_YKT_PAYMENT_DISABLE = "125098";
    // 校验商户合法性，传入商户号为空，但用户非个人用户，请检查参数
    public static final String USERS_YKT_USER_NOT_PERSONAL = "125099";

    // 卡号不能为空
    public static final String USERS_CARD_CODE_NOT_EMPTY = "125100";
    /**** add by xiong ****/

    //卡片已绑定
    public static final String USERS_CARD_CODE_BIND_ENABLE = "125101";

    //用户绑定卡片数超出限制
    public static final String USERS_CARD_CODE_BIND_OVER = "125102";

    //用户绑定卡失败
    public static final String USERS_CARD_CODE_BIND_FAILURE = "125103";

    //操作人名称不能为空
    public static final String USERS_OPER_NAME_NOT_EMPTY = "125104";

    //来源不能为空
    public static final String USERS_SOURCE_NOT_EMPTY = "125105";

    // 卡片解绑失败
    public static final String USERS_CARD_UNBIND_FAIL = "125106";
    // 商户不是连锁直营网点
    public static final String MER_CHAIN_STORE_MER_FAIL = "125107";
    // 商户不是自动分配额度
    public static final String MER_IS_AUTO_DISTRIBUTE_FAIL = "125108";
    
    //POS尚未绑定城市，请联系客服
    public static final String USERS_POS_NO_BIND_CITY = "125111";
    
    //POS所属城市与您当前开通的业务城市不符，请联系客服
    public static final String USERS_POS_CITY_IS_ERROR = "125112";
    
    //商户扩展表信息未找到
    public static final String MER_EXTEND_INFO_NULL = "125113";
    /***************************************** 用户响应码结束 *****************************************/

    /***************************************** 交易支付错误码开始 13XXXX 开头 *****************************************/
    //支付方式配置信息为空
    public static final String PAY_CONFIG_NULL = "130001";

    //支付类型编码不能为空
    public static final String PAY_TYPE_NULL = "130002";

    //支付类型名称不能为空
    public static final String PAY_TYPE_NAME_NULL = "130003";

    //支付方式名称不能为空
    public static final String PAY_WAY_NAME_NULL = "130004";

    //提供给外接商户的网关号不能为空
    public static final String PAY_GATEWAY_NUMBER_NULL = "130005";

    //第三方账户号不能为空
    public static final String PAY_ANOTHER_ACCOUNT_CODE_NULL = "130006";

    //城市CODE不合法
    public static final String PAY_ICDC_CITYCODE_NULL = "130007";

    //城市名称不合法
    public static final String PAY_ICDC_CITYNAME_NULL = "130008";

    //费率不合法
    public static final String PAY_RATE_NULL = "130009";

    //财付通支付宝未选择
    public static final String PAY_TO_GW_ALI_ID_NULL = "130010";

    //通用支付方式配置信息不能为空
    public static final String PAY_GENERAL_NULL = "130011";

    //支付方式名称重复
    public static final String PAY_WAY_NAME_REPEAT = "130012";

    //商户号不能为空
    public static final String PAY_MER_CODE_NULL = "130013";

    //用户号不能为空
    public static final String PAY_USER_CODE_NULL = "130014";

    //商户号或用户号不合法
    public static final String PAY_MER_OR_USER_NULL = "130015";

    //用户类型不合法
    public static final String PAY_CUSTOMER_TYPE_ERROR = "130016";

    //订单号不合法
    public static final String PAY_ORDER_CODE_NULL = "130017";

    //业务类型不合法
    public static final String PAY_BUSINESS_TYPE_ERROR = "130018";

    //商品名称不合法
    public static final String PAY_GOODS_NAME_NULL = "130019";

    //来源不合法
    public static final String PAY_SOURCE_ERROR = "130020";

    //订单时间不合法
    public static final String PAY_ORDER_DATE_ERROR = "130021";

    //非法交易
    public static final String PAY_ILLEGAL_TRADE = "130022";

    //请联系管理员，配置账户支付类型的支付方式
    public static final String PAY_PAYTYPE_NULL_ERROR = "130023";

    //交易金额非法
    public static final String PAY_AMOUNT_ERROR = "130024";

    //操作人不能为空
    public static final String PAY_OPERATOR_ID_NULL = "130025";

    //交易类型不合法
    public static final String PAY_TRAN_TYPE_ERROR = "130026";

    //账户hessian异常
    public static final String PAY_ACCOUNT_HESSIAN_ERROR = "130030";
    //用户hessian异常
    public static final String PAY_USER_HESSIAN_ERROR = "130031";
    /************************************************************ add by xiong ***/
    //支付方式ID不能为空
    public static final String PAY_CONFIG_ID_NULL = "130032";

    //查询到支付方式服务费率为空
    public static final String PAY_SERVICE_RATE_EMPTY = "130033";
    
    //支付方式未找到
    public static final String PAY_WAY_NOT_FIND = "130034";
    
    //交易失败
    public static final String PAY_ERROR = "139999";
    //参数格式错误
    public static final String PARAMETER_ERROR="138888";
    //支付失败
    public static final String PAY_FAILURE="137777";
    //支付网关 交易金额为空
    public static final String TRAN_MONEY_NULL="130040";
    //支付网关 商品名称为空
    public static final String COMMODITYNAME_NULL="130041";
    //支付网关 客户类型为空
    public static final String CUSTOMERTYPE_NULL="130042";
    //支付网关 客户号为空
    public static final String CUSTOMERNO_NULL="130043";
    //支付网关 业务类型为空
    public static final String BUSINESSTYPE_NULL="130044";
    //支付网关 来源为空
    public static final String SOURCE_NULL="130045";
    //支付网关 商户类型为空
    public static final String MERCHANTTYPE_NULL="130046";
    //支付网关 支付方式为空
    public static final String PAYWAYID_NULL="130047";
    //支付网关 实际交易金额为空
    public static final String REALTRANMONEY_NULL="130048";
    //支付网关 所传签名信息不符
    public static final String SIGNE_EROR="130049";
    //支付网关 获取余额为空
    public static final String ACCOUNTBALANCE_NULL="130050";
    //支付网关 没有对应的支付方式
    public static final String PAYWAY_NULL="130051";
    //支付网关 支付方式被停用
    public static final String PAYWAY_DISABLED="130052";
    //支付网关 支付方式不正确
    public static final String PAYWAY_ERROR="130053";
    //支付网关 无法获取支付服务费率
    public static final String NO_FEE="130054";
     //支付网关 超出账户风控范围
    public static final String PAY_CONTROL_OVER="130055";
    //支付网关 支付提交失败
    public static final String PAY_SUBMIT_ERROR="130056";
    //支付网关 账户加值失败
    public static final String ACCOUNT_VALUE_ADDED_FAIL="130057";
    //支付网关 返回验签处理失败 
    public static final String CHECKSIGN_ERROR="130058";
    //支付网关 支付处理失败
    public static final String PAY_HANDLE_ERROR="130059";

    /**重复扣款 */
    public static final String PAY_DEDUCT_AGAIN_ERROR="130062";
    
    /************************************************************ add by shenxiang  start   ***/
    
    // 退款接口入参：原交易流水号为空
    public static final String PAYRETUND_PARA_TRADCODE_ERROR="131001";
    
    // 退款接口入参：来源不正确
    public static final String PAYRETUND_PARA_SOURCE_ERROR="131002";
    
    // 退款接口入参：操作员为空
    public static final String PAYRETUND_PARA_OPERATOEID_ERROR="131003";
    
    // 退款接口验证原交易流水状态不正确（外部状态非已支付状态不能退款）:交易流水状态非法
    public static final String PAYRETUND_TRADSTATUS__ERROR="131004";
    
    // 退款接口：退款失败
    public static final String PAYRETUND_FAILED="131005";
    
    /************************************************************ add by shenxiang  end   ***/
    
    /***************************************** 交易支付错误码结束 *****************************************/

    // 签名失败
    public static final String SIGN_ERROE = "140001";

    /***************************************** 产品库错误码开始 15XXXX 开头 *****************************************/
    //一卡通代码不能为空。
    public static final String PRODUCT_FIND_YKT_CODE_NULL = "150001";

    //通卡公司停启用失败
    public static final String PRODUCT_YKT_START_AND_DISABLE = "150002";

    //通卡公司名称不能为空。
    public static final String PRODUCT_FIND_YKT_NAME_NULL = "150003";

    //通卡公司所在省份不能为空。
    public static final String PRODUCT_FIND_YKT_PROVINCE_NULL = "150004";

    //通卡公司所在城市不能为空。
    public static final String PRODUCT_FIND_YKT_CITY_NULL = "150005";

    //通卡公司详细地址不能为空。
    public static final String PRODUCT_FIND_YKT_ADDRESS_NULL = "150006";

    //通卡公司付款方式不能为空。
    public static final String PRODUCT_FIND_YKT_PATTYPE_NULL = "150007";

    //通卡公司启用标识不能为空。
    public static final String PRODUCT_FIND_YKT_ACTIVATE_NULL = "150008";

    //通卡公司是否开通一卡通充值业务标识不能为空。
    public static final String PRODUCT_FIND_YKT_IS_RECHARGE_NULL = "150009";

    //通卡公司开通一卡通充值业务时一卡通充值费率不能为空。
    public static final String PRODUCT_FIND_YKT_RECHARGE_RATE_NULL = "150010";

    //通卡公司开通一卡通充值业务时充值业务结算类型不能为空。
    public static final String PRODUCT_FIND_YKT_RECHARGE_SETTYPE_NULL = "150011";

    //通卡公司开通一卡通充值业务时充值业务结算类型值不能为空。
    public static final String PRODUCT_FIND_YKT_RECHARGE_SETPARA_NULL = "150012";

    //通卡公司是否开通一卡通支付业务标识不能为空。
    public static final String PRODUCT_FIND_YKT_IS_PAY_NULL = "150013";

    //通卡公司开通一卡通支付业务时一卡通支付费率不能为空。
    public static final String PRODUCT_FIND_YKT_PAY_RATE_NULL = "150014";

    //通卡公司开通一卡通支付业务时支付业务结算类型不能为空。
    public static final String PRODUCT_FIND_YKT_PAY_SETTYPE_NULL = "150015";

    //通卡公司开通一卡通支付业务时支付业务结算类型值不能为空。
    public static final String PRODUCT_FIND_YKT_PAY_SETPARA_NULL = "150016";

    //通卡公司业务城市不能为空。
    public static final String PRODUCT_FIND_YKT_BUSINESSCITY_NULL = "150017";

    //一卡通代码已存在。
    public static final String PRODUCT_FIND_YKT_CODE_EXSIT = "150018";

    //通卡公司名称已存在。
    public static final String PRODUCT_FIND_YKT_NAME_EXSIT = "150019";

    //业务城市已被占用。
    public static final String PRODUCT_FIND_YKT_BUSINESSCITY_EXSIT = "150020";

    // 购买额度后累加的总额度超过数据库可容纳的最大位数。
    public static final String PRODUCT_FIND_ADD_LIMIT_OUTNUMBER = "150021";

    // 对冲额度超过剩余额度。
    public static final String PRODUCT_FIND_SUB_LIMIT_OUTNUMBER = "150022";

    // 申请额度的位数超过数据库最大位数
    public static final String PRODUCT_FIND_APPLY_ADD_LIMIT_OUTNUMBER = "150024";
    
    // 财务打款额度的位数超过数据库最大位数
    public static final String PRODUCT_FIND_FINANCIAL_PAY_AMT_OUTNUMBER = "150033"; //
    
    // 只能复核审核通过的申请单
    public static final String PRODUCT_FIND_CHECK_AUDIT_PASS = "150034"; //
    
    // 该申请单已经复核完了
    public static final String PRODUCT_FIND_CHECK_DONE = "150035"; //
    
    // 通卡本次追加额度与已加额度之和不得超过财务打款额度
    public static final String PRODUCT_FIND_CHECK_YKTADDLIMIT = "150036"; // 
    
    // 通卡增加额度的位数超过数据库最大位数
    public static final String PRODUCT_FIND_YKT_ADD_LIMIT_OUTNUMBER = "150025";
    
    // 打款手续费的位数超过数据库最大位数
    public static final String PRODUCT_FIND_PLAY_FEE__OUTNUMBER = "150026";
    
    // 只能删除未审核的申请单
    public static final String PRODUCT_DELETE_APPLY_ORDER_ERROR = "150027";
    
    // 只能编辑未审核的申请单
    public static final String PRODUCT_UPDATE_APPLY_ORDER_ERROR = "150028";
    
    // 只能审核未审核的申请单
    public static final String PRODUCT_AUDIT_APPLY_ORDER_ERROR = "150029";
    
    // 申请单不存在
    public static final String PRODUCT_APPLY_ORDER_NOT_EXSIT = "150030";
    
    // 额度提取记录不存在
    public static final String MER_LIMIT_EXTRACT_ERROR = "150031";
    // 额度提取状态更新失败
    public static final String UP_MER_LIMIT_EXTRACT_ERROR = "150032";
    //一卡通编号为不能为空
    public static final String PRODUCT_YKT_CODE_NULL = "150101";
    //一卡通业务城市ID不能为空
    public static final String PRODUCT_CITYID_NULL = "150102";
    //一卡通产品面价不能为0
    public static final String PRODUCT_PRO_PAYPRICE_NULL = "150103";
    //一卡通产品已存在
    public static final String PRODUCT_YKT_EXIST = "150104";
    //一卡通产品信息没找到
    public static final String PRODUCT_YKT_ERR = "150105";
    //上/下架标志错误
    public static final String PRODUCT_YKT_STATUS_ERROR = "150106";
    //一卡通产品编号不能为空
    public static final String PRODUCT_YKT_PRO_CODE_NULL = "150107";
    //一卡通业务城市名称不能为空
    public static final String PRODUCT_CITYNAME_NULL = "150108";
    //一卡 通产品面价必须为10的整数倍
    public static final String PRODUCT_PRO_PAYPRICE_ERROR = "150109";
    //商户编号不能为空
    public static final String PRODUCT_PRO_MERCHANTNUM_NULL = "150110";

    // 验签未通过
    public static final String PRODUCT_REQ_SIGN_ERROR = "150200";
    // 请求编码字符集不能为空
    public static final String PRODUCT_REQ_PARAM_INPUT_CHARSET_NULL = "150201";
    // 请求签名方式为空或格式错误
    public static final String PRODUCT_REQ_PARAM_SIGN_TYPE_ERROR = "150202";
    // 请求签名不能为空
    public static final String PRODUCT_REQ_PARAM_SIGN_NULL = "150203";
    // 请求时间为空或格式错误
    public static final String PRODUCT_REQ_PARAM_REQDATE_ERROR = "150204";
    // 手机号不能为空
    public static final String PRODUCT_REQ_PARAM_MER_USER_MOBILE_NULL = "150205";
    // 手机号格式错误
    public static final String PRODUCT_REQ_PARAM_MER_USER_MOBILE_ERROR = "150206";
    // 验证码不能为空
    public static final String PRODUCT_REQ_PARAM_DYPWD_NULL = "150207";
    // 序号不能为空
    public static final String PRODUCT_REQ_PARAM_PWDSEQ_NULL = "150208";
    // 用户名不能为空
    public static final String PRODUCT_REQ_PARAM_USER_NAME_NULL = "150209";
    // 用户名格式错误，数字、字母、下划线，首位必须为字母，长度4-20
    public static final String PRODUCT_REQ_PARAM_USER_NAME_ERROR = "150210";
    // 注册来源错误
    public static final String PRODUCT_REQ_PARAM_SOURCE_ERROR = "150211";
    // 密码为空或格式错误
    public static final String PRODUCT_REQ_PARAM_PASSWORD_ERROR = "150212";
    // DLL交易日志产品库主订单号不能为空
    public static final String PRODUCT_REQ_PARAM_PRD_ORDER_NUM_NULL = "150213";
    // 验证码类型错误
    public static final String PRODUCT_REQ_PARAM_CODE_TYPE_ERROR = "150214";
    // 城市信息不存在
    public static final String PRODUCT_REQ_PARAM_CITY_NOT_FOUND = "150215";
    // 老系统userid不能为空
    public static final String PRODUCT_REQ_PARAM_OLD_USER_ID_NULL = "150216";
    // 老系统usertype不能为空
    public static final String PRODUCT_REQ_PARAM_OLD_USER_TYPE_NULL = "150217";

    // 创建圈存订单
    public static final String PRODUCT_LOADORDER_REQUEST_ERROR = "155005"; //参数不合法
    public static final String PRODUCT_LOADORDER_BOOK_ERROR = "155006"; //下单失败

    public static final String PRODUCT_LOADORDER_QUERY_ERROR = "155007"; //查询可用于一卡通充值的圈存订单失败

    // 6.3  根据外接商户的订单号查询圈存订单状态
    public static final String PRODUCT_LOADORDER_QUERYSTATUS_INCORRECT = "155018"; //参数不合法
    public static final String PRODUCT_LOADORDER_QUERYSTATUS_ERROR = "155019"; //查询失败

    public static final String PRODUCT_LOADORDER_FINDBYCARDNUM_ERROR = "155004"; //参数不合法

    // 7.3.2    公交卡充值流程中检验圈存订单合法性
    public static final String PRODUCT_LOADORDER_NOT_EXIST = "155008"; // 您没有可以进行充值的订单
    public static final String PRODUCT_LOADORDER_INCORRECT = "155009"; // 您无法使用该充值订单进行一卡通充值
    public static final String PRODUCT_LOADORDER_PARAM_INVALID = "155010"; // 公交卡充值流程中检验圈存订单合法性参数不合法

    // 155500 - 155599 号段 为产品库第5章节所有响应码
    // 5.2.1    订单查询
    public static final String PRODUCT_ORDER_QUERY_ERROR = "155500"; // 订单查询过程中发生的错误

    public static final String PRODUCT_ORDER_NUMBER_EMPTY = "155501"; // 查询条件订单号不能为空
    public static final String PRODUCT_ORDER_DETAIL_QUERY_ERROR = "155502"; // 订单查询过程中发生的错误
    public static final String PRODUCT_ORDER_DETAIL_USER_CODE_ERROR = "155503"; // 订单查询用户号不能为空
    // --------------------------------------公交卡充值订单错误码 start-------------------------------------//
    // 7.3.3    检验公交卡充值产品合法性
    public static final String PRODUCT_CHECK_YKT_STATUS_DISABLE = "155001"; //城市尚未启用，请联系客服人员
    public static final String PRODUCT_CHECK_PRODUCT_STATUS_DISABLE = "155002"; //该产品已下架，请重新选择产品
    public static final String PRODUCT_CHECK_YKT_SERVICE_REPAIRING = "155058"; //通卡公司系统维护中
    

    // 公交卡充值各个流程环节中参数出错统一报错：参数不合法
    public static final String PRODUCT_CARD_RECHARGE_PARAMETER_ERROR = "155003"; //参数不合法
    public static final String PRODUCT_CARD_RECHARGE_LIMIT_ERROR = "155044";//充值金额与卡内余额之和超过卡内限额

    // 7.4.1    充值验卡环节中验证产品库订单 响应码
    public static final String PRODUCT_CHECK_CARD_ORDER_NOT_EXIST = "155011"; //非法订单
    public static final String PRODUCT_CHECK_CARD_ORDER_STATUS_ERROR = "155012"; //非法订单状态
    public static final String PRODUCT_CHECK_CARD_ORDER_PRODUCT_ERROR = "155013"; //非法充值产品   
    public static final String PRODUCT_CHECK_CARD_ORDER_TXNAMT_ERROR = "155014"; //充值金额异常
    public static final String PRODUCT_CHECK_CARD_ORDER_CARD_ERROR = "155015"; //非法的卡号
    public static final String PRODUCT_CHECK_CARD_ORDER_TWICE_ERROR = "155016"; //重复下单
    // 7.5.1    更新公交卡充值订单充值密钥状态 响应码
    public static final String PRODUCT_UPDATE_RECHARGEKEY_STATUS_ORDER_NOT_EXIST = "155036"; //非法订单
    public static final String PRODUCT_UPDATE_RECHARGEKEY_STATUS_ORDER_ERROR = "155037"; //非法订单状态
    // 7.6.1    更新公交卡充值订单结果状态
    public static final String PRODUCT_UPDATE_RESULT_STATUS_ORDER_NOT_EXIST = "155020"; //一卡通充值订单不存在
    public static final String PRODUCT_UPDATE_RESULT_STATUS_ORDER_STATUS_ERROR = "155021"; //一卡通订单状态错误
    // 7.7.1    更新公交卡充值订单前端判断失败结果状态
    public static final String PRODUCT_FRONT_FAIL_ORDER_STATUS_ORDER_NOT_EXIST = "155032"; //一卡通充值订单不存在
    // 7.8.1    资金冻结状态
    public static final String PRODUCT_BLOCK_FUND_RONT_ORDER_NOT_EXIST = "155017"; //非法订单
    public static final String PRODUCT_BLOCK_FUND_RONT_ORDER_STATUS_ERROR = "155022"; //非法订单状态
    public static final String PRODUCT_BLOCK_FUND_FAIL = "155027"; //更新产品库订单上的资金冻结状态失败
    // 7.8.2    资金解冻状态
    public static final String PRODUCT_UNBLOCK_FUND_RONT_ORDER_NOT_EXIST = "155028"; //非法订单
    public static final String PRODUCT_UNBLOCK_FUND_RONT_ORDER_STATUS_ERROR = "155034"; //非法订单状态
    public static final String PRODUCT_UNBLOCK_FUND_FAIL = "155029"; //更新产品库订单上的资金解冻状态失败
    // 7.8.3    资金扣款状态
    public static final String PRODUCT_DEDUCK_FUND_ORDER_NOT_EXIST = "155030"; //非法订单
    public static final String PRODUCT_DEDUCK_FUND_ORDER_STATUS_ERROR = "155035"; //非法订单状态
    public static final String PRODUCT_DEDUCK_FUND_FAIL = "155031"; //更新产品库订单上的资金扣款状态失败

    public static final String PRODUCT_UNBLOCK_SUCC = "155039"; //资金解冻成功
    public static final String PRODUCT_UNBLOCK_UPDATE_ORDERSTATUS_SUCC = "155040"; //资金解冻后修改产品库订单状态成功
    public static final String PRODUCT_MAC2_UPDATE_ORDERSTATUS_SUCC = "155041"; //申请充值密钥失败后修改产品库订单状态成功
    public static final String PRODUCT_DEDUCK_SUCC = "155042"; //资金扣款成功
    public static final String PRODUCT_DEDUCK_UPDATE_ORDERSTATUS_SUCC = "155043"; //资金扣款后修改产品库订单状态成功

    //  更新网银支付失败状态
    public static final String PRODUCT_ONLINE_PAY_ORDER_NOT_EXIST = "155045"; //非法订单
    public static final String PRODUCT_ONLINE_PAY_ORDER_STATUS_ERROR = "155046"; //非法订单状态

    //  更新账户充值结果状态
    public static final String PRODUCT_ACCOUNT_RECHARGE_ORDER_NOT_EXIST = "155047"; //非法订单
    public static final String PRODUCT_ACCOUNT_RECHARGE_ORDER_STATUS_ERROR = "155048"; //非法订单状态

    public static final String PRODUCT_GET_DSIGNKEY_ERROR = "155100"; //获取产品库验签密钥失败
    public static final String PRODUCT_GET_BACKDSIGNKEY_ERROR = "155101"; //获取产品库签名密钥失败
    public static final String PRODUCT_GET_MSIGNKEY_ERROR = "155102"; //获取客户签名密钥错误
    public static final String PRODUCT_GET_BACKMSIGNKEY_ERROR = "155103"; //获取客户验签密钥错误
    public static final String PRODUCT_DSIGN_CHEKC_ERROR = "155104"; //签名验签未通过(d_sign验证不通过)
    public static final String PRODUCT_MSIGN_CHECK_ERROR = "155105"; //签名验签未通过(m_sign验证不通过)

    public static final String PRODUCT_CREATE_ORDER_RATE_ERROR = "155051"; //生单时商户费率不合法   

    public static final String PRODUCT_UPLOAD_RECHARGE_RESULT_ORDER_STATE_DIFFERENT = "155052"; //重发上传充值结果时，订单状态与DLL充值结果参数不匹配   

    //商户费率未找到  
    public static final String PRODUCT_MER_RATE_NULL = "155053";
    
    /**
     * 支付方式未找到
     */
    public static final String PRODUCT_PAY_WAY_ID_NULL = "155054";
    
    /**
     * 用户信息未找到
     */
    public static final String PRODUCT_USER_INFO_NULL = "155055";
    
    public static final String PRODUCT_RECHARGE_EXTERNAL_ORDERNUM_IS_NULL = "155056"; // 外部订单号不能为空
    
    public static final String PRODUCT_RECHARGE_EXTERNAL_ORDERNUM_OVER_LENGTH = "155057"; // 外部订单号长度不能超过64位
    
    //您暂无可进行交易的城市，请联系管理员
    public static final String PRODUCT_RECHARGE_CITY_YKT_ACTIVATE_ERROR = "155059"; 
    
    //7自助终端 取交易流水信息和圈存订单
    //交易流水状态不合法
    public static final String PRODUCT_PAYTRATRANSACTION_INFO_STATUS = "155070"; 
    
    
    
    // --------------------------------------公交卡充值订单错误码 end-------------------------------------//

    //-------------------------公交卡充值 手机端错误码 响应码 START -----------------------------------//
    // 城市code不能为空
    public static final String PRODUCT_MOBILE_CITY_CODE_NULL = "156001";
    // 产品版本号不能为空
    public static final String PRODUCT_MOBILE_PROVERSION_CODE_NULL = "156002";

    public static final String PRODUCT_MOBILE_CUSTTYPE_ERROR = "156003"; // 客户类型不合法
    public static final String PRODUCT_MOBILE_CUSTCODE_NULL = "156004"; // 客户号不能为空
    public static final String PRODUCT_MOBILE_USERID_NULL = "156005"; // 用户ID不能为空
    public static final String PRODUCT_MOBILE_ORDERNUM_NULL = "156006"; // 订单号不能为空

    //-------------------------公交卡充值 手机端错误码 响应码 END -----------------------------------//

    //-------------------------公交卡消费收单  响应码 START -----------------------------------//

    public static final String PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR = "157001"; // 创建收单记录失败，参数不合法

    public static final String PRODUCT_PURCHASE_ORDER_ERROR = "157002"; // 非法订单

    // 一卡通收单各个流程环节中参数出错统一报错：参数不合法
    public static final String PRODUCT_PURCHASE_PARAMETER_ERROR = "157003"; //参数不合法

    public static final String PRODUCT_UPLOAD_PURCHASE_RESULT_ORDER_STATE_SUCCESS = "157004"; //重发收单结果时，产品库响应成功

    public static final String PRODUCT_UPLOAD_PURCHASE_RESULT_ORDER_STATE_DIFFERENT = "157005"; //重发收单结果时，订单状态与DLL充值结果参数不匹配   
    
    public static final String PRODUCT_PURCHASE_YKT_DISABLE = "157006"; // 通卡公司尚未启用，请联系管理员
    
    public static final String PRODUCT_PURCHASE_YKT_ISPAY_DISABLE = "157007"; // 通卡公司尚未开启一卡通支付业务，请联系管理员
    
    //-------------------------公交卡消费收单  响应码  END -----------------------------------//

    // 产品库调用账户系统API异常
    public static final String PRODUCT_CALL_ACCOUNT_ERROR = "159996";
    // 产品库调用支付交易API异常
    public static final String PRODUCT_CALL_PAYMENT_ERROR = "159997";
    // 产品库调用用户系统API异常
    public static final String PRODUCT_CALL_USERS_ERROR = "159998";
    // 产品库调用卡服务API异常
    public static final String PRODUCT_CALL_CARD_ERROR = "159999";
    
    // 充值失败
    public static final String PRODUCT_RESCHARGE_ERROR = "159990";
    // 支付失败
    public static final String PRODUCT_PURCHASE_ERROR = "159991";    
    // 暂时无法进行交易,请联系客服
    public static final String PRODUCT_ERROR = "159992";
    
    //psam卡已经存在
    public static final String PRODUCT_PSAM_ERROR = "159993";
    
    //自助终端 7 取交易流水信息和圈存订单
    //可以退款
    public static final String PRODUCT_XXXXXX = "XXXXXX";
    //不可以退款
    public static final String PRODUCT_YYYYYY = "YYYYYY";
    //交易流水号不能为空
    public static final String PRODUCT_TRANNO_NULL = "150300";
    
    /************************************************************ add by shenxiang  start   ***/
    //自助终端退款：交易流水信息不存在
    public static final String PRODUCT_TRANINFO_NULL = "158001";
    //自助终端退款：圈存订单号不存在
    public static final String PRODUCT_LOADORDERNUM_NULL = "158002";
    //自助终端退款：圈存订单不存在
    public static final String PRODUCT_LOADORDER_NULL = "158003";
    //自助终端退款：圈存订单状态不正确
    public static final String PRODUCT_LOADORDER_STATE_ERROR = "158004";
    //自助终端退款：充值订单状态不正确
    public static final String PRODUCT_ORDER_STATE_ERROR = "158005";
    //活动参数不合法
    public static final String PRODUCT_PARKEY_ERROR = "158006";
    //解密键不合法
    public static final String PRODUCT_THIRDKEY_ERROR = "158007";
    //查询日期不合法
    public static final String PRODUCT_QUEDATE_ERROR = "158008";
    
    
    /************************************************************ add by shenxiang  end   ***/

    /***************************************** 产品库错误码结束 *****************************************/

    /***************************************** 卡服务错误码开头16XXXX 开头 *****************************************/

    //-----------------------------------------卡服务充值错误码start-----------------------------------------//
    //与城市前置交互失败
    public static final String CARD_SOCKET_CONNECT_ERROR = "160001";
    //产品库订单号已存在
    public static final String CARD_PRDORDERNUM_EXIST = "160002";
    //产品库订单号不能为空
    public static final String CARD_PRDORDERNUM_NULL = "160003";
    //产品编号不能为空
    public static final String CARD_PRODUCTCODE_NULL = "160004";
    //商户编号不能为空
    public static final String CARD_MERCODE_NULL = "160005";
    //商户订单号不能为空
    public static final String CARD_MERORDERCODE_NULL = "160006";
    //用户编号不能为空
    public static final String CARD_USERCODE_NULL = "160007";
    //城市代码不能为空
    public static final String CARD_CITYCODE_NULL = "160008";
    //一卡通编号不能为空
    public static final String CARD_YKTCODE_NULL = "160009";
    //卡内号不能为空
    public static final String CARD_CARDINNERNO_NULL = "160010";
    //卡面号不能为空
    public static final String CARD_CARDFACENO_NULL = "160011";
    //验卡卡号不能为空
    public static final String CARD_TRADECARD_NULL = "160012";
    //卡物理类型不能为空
    public static final String CARD_CARDTYPE_NULL = "160013";
    //卡物理号不能为空
    public static final String CARD_UID_NULL = "160014";
    //设备类型不能为空
    public static final String CARD_POSTYPE_NULL = "160015";
    //设备编号不能为空
    public static final String CARD_POSID_NULL = "160016";
    //交易前卡余额不能为空
    public static final String CARD_BEFBAL_NULL = "160017";
    //交易金额不能为空
    public static final String CARD_TXNAMT_NULL = "160018";
    //数据来源不能为空
    public static final String CARD_SOURCE_NULL = "160019";
    //交易流水号不能为空
    public static final String CARD_TXNSEQ_NULL = "160020";
    //交易日期不能为空
    public static final String CARD_TXNDATE_NULL = "160021";
    //交易时间不能为空
    public static final String CARD_TXNTIME_NULL = "160022";
    //APDU指令集不能为空
    public static final String CARD_APDU_NULL = "160023";
    //充值类型不能为空
    public static final String CARD_CHARGETYPE_NULL = "160024";
    //交易步骤不能为空
    public static final String CARD_TRADESTEP_NULL = "160025";
    //交易步骤版本不能为空
    public static final String CARD_TRADESTEPVER_NULL = "160026";
    //交易结束标志不能为空
    public static final String CARD_TRADEENDFLAG_NULL = "160027";
    //特殊域不能为空
    public static final String CARD_SPECDATA_NULL = "160028";
    //交易应答码不能为空
    public static final String CARD_RESPCODE_NULL = "160029";
    //卡服务无此订单信息
    public static final String CARD_PRDORDERNUM_NOT_EXIST = "160030";
    //操作员ID不能为空
    public static final String CARD_USERID_NULL = "160031";
    //卡服务订单状态不正确
    public static final String CARD_ORDER_STATES_ERROR = "160032";
    //卡号(tradecard)与卡服务订单不匹配
    public static final String CARD_TRADECARD_ERROR = "160033";
    //一卡通编号 (yktcode)与卡服务订单不匹配
    public static final String CARD_YKTCODE_ERROR = "160034";
    //城市代码 (citycode)与卡服务订单不匹配
    public static final String CARD_CITYCODE_ERROR = "160035";
    //设备编号 (posid)与卡服务订单不匹配
    public static final String CARD_POSID_ERROR = "160036";
    //交易前卡余额 (befbal)与卡服务订单不匹配
    public static final String CARD_BEFBAL_ERROR = "160037";
    //交易金额 (txnamt)与卡服务订单不匹配
    public static final String CARD_TXNAMT_ERROR = "160038";
    //开始标志不能为空
    public static final String CARD_TRADESTARTFLAG_NULL = "160039";
    //卡服务申请读卡密钥失败
    public static final String CARD_APPLAY_KEY_FAILED = "160040";
    //卡服务订单号不能为空
    public static final String CARD_CARDORDERNUM_NULL = "160041";
    //结果上传交易状态不能为空
    public static final String CARD_TXNSTAT_NULL = "160042";
    //卡服务卡片执行APDU指令后返回值不能为空
    public static final String CARD_APDUDATA_NULL = "160043";
    //卡服务申请读卡密钥失败
    public static final String CARD_APPLAY_RECHARGE_FAILED = "160044";
    //充值金额与卡内余额只和超过卡内限额
    public static final String CARD_LIMIT_ERROR = "160046";
    //卡内限额不能为空
    public static final String CARD_LIMIT_NULL = "160047";
    //圈存标识不能为空
    public static final String CARD_LOADFLAG_NULL = "160048";
    //前置返回响应码为空
    public static final String CARD_FRONTRESP_NULL = "160049";
    //卡服务订单特殊域不能为空
    public static final String CARD_SPECIAL_NULL = "160050";
    //卡服务订单TAC值不能为空
    public static final String CARD_TAC_NULL = "160051";
    //卡服务APDU值异常
    public static final String CARD_APDU_ERROR = "160052";
    //北京 start
    //SAM卡签到签退数据不合法
    public static final String CARD_SAM_SIGN_IN_OFF_ERROR = "160053";
    //samno与posid不对应
    public static final String CARD_SAM_ERROR = "160054";
    //设备未签到
    public static final String CARD_POS_SIGN_ERROR = "160055";
    //sam编号不能为空
    public static final String CARD_SAM_NULL = "160056";
    //黑名单标志不能为空
    public static final String CARD_BLACKFLAG_NULL = "160057";
    //通讯流水号不能为空
    public static final String CARD_POSTRANSEQ_NULL = "160058";
    //pos菜单数据不合法
    public static final String CARD_POSMENU_ERROR = "160059";
    //卡物理类型不能为空
    public static final String CARD_PHTYPE_NULL = "160060";
    //脱机标识不合法
    public static final String CARD_OFFLINETYPE_ERROR = "160061";
    //IC流水号不能为空
    public static final String CARD_POSSEQ_NULL = "160063";
    //交易状态不合法
    public static final String CARD_TXNSTAT_ERROR = "160062";
    //加解密不能为空
    public static final String CARD_ENCRYPT_NULL = "160064";
    //批量脱机上传记录为空
    public static final String CARD_OFFLINE_RECORDS_NULL = "160065";
    //脱机消费结果上传订单重复
    public static final String CARD_OFFLINE_RECORDS_ERROR = "160066";
    //北京end
    

    //-----------------------------------------卡服务充值错误码end-----------------------------------------//

    //-----------------------------------------卡服务消费错误码start---------------------------------------//
    //消费金额大于卡内余额
    public static final String CARD_TXNAMT_BEFBAL_ERROR = "161001";
    //产品库订单号已存在
    public static final String CARD_CONSUME_PRDORDERNUM_EXIST = "161002";
    //产品库订单号不能为空
    public static final String CARD_CONSUME_PRDORDERNUM_NULL = "161003";
    //城市代码不能为空
    public static final String CARD_CONSUME_CITYCODE_NULL = "161004";
    //一卡通编号不能为空
    public static final String CARD_CONSUME_YKTCODE_NULL = "161005";
    //交易前卡余额不能为空
    public static final String CARD_CONSUME_BEFBAL_NULL = "161006";
    //交易金额不能为空
    public static final String CARD_CONSUME_TXNAMT_NULL = "161007";
    //商户编号不能为空
    public static final String CARD_CONSUME_MERCODE_NULL = "161008";
    //用户编号不能为空
    public static final String CARD_CONSUME_USERCODE_NULL = "161009";
    //操作员ID不能为空
    public static final String CARD_CONSUME_USERID_NULL = "161010";
    //验卡卡号不能为空
    public static final String CARD_CONSUME_TRADECARD_NULL = "161013";
    //卡物理类型不能为空
    public static final String CARD_CONSUME_CARDTYPE_NULL = "161014";
    //卡物理号不能为空
    public static final String CARD_CONSUME_UID_NULL = "161015";
    //设备类型不能为空
    public static final String CARD_CONSUME_POSTYPE_NULL = "161016";
    //设备编号不能为空
    public static final String CARD_CONSUME_POSID_NULL = "161017";
    //数据来源不能为空
    public static final String CARD_CONSUME_SOURCE_NULL = "161018";
    //充值类型不能为空
    public static final String CARD_CONSUME_CHARGETYPE_NULL = "161019";
    //消息类型不能为空
    public static final String CARD_CONSUME_TRADESTEP_NULL = "161020";
    //版本号不能为空
    public static final String CARD_CONSUME_VER_NULL = "161021";
    //卡面号城市前置返回为空
    public static final String CARD_CONSUME_CITYBACK_CARDINNERNO_NULL = "161011";
    //卡内号城市前置返回为空
    public static final String CARD_CONSUME_CITYBACK_CARDFACENO_NULL = "161012";
    //交易流水号城市前置返回为空
    public static final String CARD_CONSUME_CITYBACK_TXNSEQ_NULL = "161022";
    //交易日期城市前置返回为空
    public static final String CARD_CONSUME_CITYBACK_TXNDATE_NULL = "161023";
    //交易时间城市前置返回为空
    public static final String CARD_CONSUME_CITYBACK_TXNTIME_NULL = "161024";
    //APDU指令集城市前置返回为空
    public static final String CARD_CONSUME_CITYBACK_APDU_NULL = "161025";
    //交易结束标志城市前置返回为空
    public static final String CARD_CONSUME_CITYBACK_TRADEENDFLAG_NULL = "161026";
    //交易结束标志城市前置返回值异常
    public static final String CARD_CONSUME_CITYBACK_TRADEENDFLAG_ERROR = "161027";
    //创建卡服务订单失败
    public static final String CARD_CONSUME_CREATE_ORDER_ERROR = "161028";
    //获取城市前置IP/PORT失败
    public static final String CARD_CONSUME_YKTINFO_ERROR = "161029";
    //开始标志不能为空
    public static final String CARD_CONSUME_TRADESTARTFLAG_NULL = "161030";
    //未找到对应的产品库订单号
    public static final String CARD_CONSUME_PRDNUM_COUNT_ERROR = "161031";
    //卡号(tradecard)与卡服务订单不匹配
    public static final String CARD_CONSUME_TRADECARD_ERROR = "161032";
    //一卡通编号 (yktcode)与卡服务订单不匹配
    public static final String CARD_CONSUME_YKTCODE_ERROR = "161033";
    //城市代码 (citycode)与卡服务订单不匹配
    public static final String CARD_CONSUME_CITYCODE_ERROR = "161034";
    //设备编号 (posid)与卡服务订单不匹配
    public static final String CARD_CONSUME_POSID_ERROR = "161035";
    //交易前卡余额 (befbal)与卡服务订单不匹配
    public static final String CARD_CONSUME_BEFBAL_ERROR = "161036";
    //交易金额 (txnamt)与卡服务订单不匹配
    public static final String CARD_CONSUME_TXNAMT_ERROR = "161037";
    //卡服务订单状态不正确
    public static final String CARD_CONSUME_ORDER_STATES_ERROR = "161038";
    //重新获取APDU
    public static final String CARD_CONSUME_RECONSUME_WARN = "161039";
    //交易状态不能为空
    public static final String CARD_CONSUME_TXNSTAT_NULL = "161040";
    //特殊域不能为空
    public static final String CARD_CONSUME_SPECDATA_NULL = "161041";
    //APDUDATA不能为空
    public static final String CARD_CONSUME_APDUDATA_NULL = "161042";
    //交易状态与APDUDATA状态错误
    public static final String CARD_CONSUME_TXNSTAT_APDUDATA_ERROR = "161043";
    //交易应答码不能为空
    public static final String CARD_CONSUME_RESPCODE_NULL = "161044";
    //创建订单数据不合法
    public static final String CARD_CONSUME_CREATE_ORDER_DATA_ERROR = "161045";
    //北京 start
    //北京通卡卡物理类型为空
    public static final String CARD_CONSUME_BJTKCARDPHYTYPE_NULL = "161046";
    //卡逻辑类型为空
    public static final String CARD_CONSUME_CARDLOGICTYPE_NULL = "161047";
    //查询脱机金额异常
    public static final String CARD_CONSUME_CARD_OFFLINE_ERROR = "161048";
    //北京end
    
    //-----------------------------------------卡服务消费错误码end-----------------------------------------//
    //卡服务解析报文失败
    public static final String CARD_PARSE_MSG_FAILED = "160100";
    //重发超时等待
    public static final String CARD_RESEND_WARN = "169999";
    //卡服务上传异常终止(北京结果上传)
    public static final String CARD_UPLOAD_ERROR_END = "169997";
    //卡服务上传正常终止(北京结果上传)
    public static final String CARD_UPLOAD_NORMAL_END = "169998";
    
    //nfc充值失败
    public static final String CARD_RECHARGEAPPLY_END = "190021";
    
    //下载参数编号为空
    public static final String CARD_DOWNLOAD_PARNO_NULL = "160500";
    //一次下载的记录条数为空
    public static final String CARD_DOWNLOAD_REQINDEX_NULL = "160501";
    //记录下载开始索引为空
    public static final String CARD_DOWNLOAD_REQTOTAL_NULL = "160502";
    //特殊域为空
    public static final String CARD_DOWNLOAD_SPECIALMODEL_NULL = "160503";
    //V71参数下载没有查询到参数数据
    public static final String RECORD_NULL = "020041";

    /***************************************** 卡服务错误码结束 *****************************************/

    /***************************************** 账户系统错误码开头17XXXX 开头 *****************************************/
    //账户系统异常
    public static final String ACC_SYSTEM_ERROR = "177777";

    //客户类型不合法
    public static final String ACC_CUSTOMER_TYPE_ERROR = "170001";
    //客户号不合法
    public static final String ACC_CUSTOMER_CODE_ERROR = "170002";
    //资金类别不合法
    public static final String ACC_BALANCE_TYPE_ERROR = "170003";
    //创建账户错误
    public static final String ACC_ADD_ERROR = "170004";
    //交易流水号不合法
    public static final String ACC_TRAN_NUM_ERROR = "170005";
    //金额不合法
    public static final String ACC_AMOUNT_ERROR = "170006";
    //非法充值，没有资金账户
    public static final String ACC_BLAN_ERROR = "170007";
    //资金账户已经禁用，不能充值
    public static final String ACC_BLAN_DISABLE = "170008";
    //超过日充值交易单笔限额
    public static final String ACC_RECHARGE_DAY_ONE_ERROR = "170009";
    //超过日充值交易累计限额
    public static final String ACC_RECHARGE_DAY_SUM_ERROR = "170010";
    //账户充值失败
    public static final String ACC_RECHARGE_ERROR = "170011";
    //资金授信账户信息不存在
    public static final String ACC_FUNDAUTHACCOUNT_NONEXISTENT_RROR = "170012";
    //资金授信账户信息异常
    public static final String ACC_FUNDAUTHACCOUNT_ERROR = "170013";
    //资金授信账户已经禁用，不能进行消费
    public static final String ACC_FUNDAUTHACCOUNT_DISABLE_ERROR = "170014";
    //超过日消费交易单笔限额
    public static final String ACC_CONSUM_DAY_ONE_ERROR = "170015";
    //超过日消费交易累计限额
    public static final String ACC_CONSUM_DAY_SUM_ERROR = "170016";
    //账户冻结失败
    public static final String ACC_FREEZE_ERROR = "170017";
    //非法资金解冻操作
    public static final String ACC_ILLEGAL_UNFREEZE_ERROR = "170018";
    //账户解冻失败
    public static final String ACC_UNFREEZE_ERROR = "170019";
    //资金授信账户信息不存在
    public static final String ACC_ILLEGAL_DEDUCT_ERROR = "170020";
    //资金扣款失败
    public static final String ACC_ACCOUNT_DEDUCT_ERROR = "170021";
    //主账户信息不存在
    public static final String ACC_ACCOUNT_NOTEXIT = "170022";
    //转出账户的客户类型不合法
    public static final String ACC_SOURCECUST_TYPE_ERROR = "170023";
    //转出账户的客户号不合法
    public static final String ACC_SOURCECUST_NUM_ERROR = "170024";
    //交易流水号不合法
    public static final String ACC_TRADE_NUM_ERROR = "170025";
    //转账的金额不合法
    public static final String ACC_AMMOUNT_ERROR = "170026";
    //转入账户的客户类型不合法
    public static final String ACC_TARGETCUST_TYPE_ERROR = "170027";
    //转入账户的客户号不合法
    public static final String ACC_TARGETCUST_NUM_ERROR = "170028";
    //转入方的资金账户已禁用
    public static final String ACC_TARGETCUST_IN_STATE_ERROR = "170029";
    //转出账户必须相同
    public static final String ACC_SOURCEACCOUNT_NOT_SAME = "170030";
    //非法的资金转账操作
    public static final String ACC_TRAN_BALANCE_ILLEGAL = "170031";
    //资金账户可用余额不够
    public static final String ACC_ACC_BALANCE_NOT_ENOUGH = "170032";
    //授信账户可用余额不够
    public static final String ACC_AUT_BALANCE_NOT_ENOUGH = "170033";
    //超过日转账交易单笔限额
    public static final String ACC_TRAN_DAYSINGLELIMIT_EXCEED = "170034";
    //超过日转账交易累计限额
    public static final String ACC_TRAN_DAYSUMLIMIT_EXCEED = "170035";
    //参数为空
    public static final String ACC_TRAN_PARAM_EMPTY = "170036";
    //资金账户风控记录信息不存在
    public static final String ACC_TRAN_CONTROL_EMPTY = "170037";
    //转账参数不正确
    public static final String ACC_TRAN_PARAM_ERROR = "170038";
    //不存在的商户号
    public static final String ACC_MERCODE_ERROR = "170039";
    //存在相应的资金变动记录
    public static final String ACCOUNT_CHANGE_EXIT = "170040";
    //超过日转账交易累计最大次数
    public static final String ACC_TRAN_DAYSUMTIMES_EXCEED = "170041";
    //入账方可用金额过大，不能超过9999999999
    public static final String ACC_TRAN_AVALIBLEFUND_ERROR = "170042";
    //入账方总金额过大，不能超过9999999999
    public static final String ACC_TRAN_TOTALFUND_ERROR = "170043";
    //出账方日累计转出金额过大，不能超过9999999999
    public static final String ACC_TRAN_DAYSUMLIMIT_ERROR = "170044";
    //入账方累计充值金额过大，不能超过999999999999
    public static final String ACC_TRAN_SUMTOTALFACCOUNT_ERROR = "170045";
    //转出方的资金账户已禁用
    public static final String ACC_TARGETCUST_OUT_STATE_ERROR = "170046";
    //资金累计信息表不存在
    public static final String ACC_ACCOUNTCHANGESUM_ERROR = "170047";
    //充值风控不通过
    public static final String ACC_CHECKRECHARGE_ERROR = "170048";
    //账户加密字段不一致
    public static final String  SIGN_ERROR = "170049";
    //-------------------------9 账户调帐 响应码 START add by shenxiang-----------------------------------//
    public static final String ACC_ACCOUNT_ADJUSTMENT_PARAMETER_ERROR = "171001";// 调账参数不正确
    public static final String ACC_ACCOUNT_ADJUSTMENT_STATE_DISABLE = "171002";// 调账申请对应的调账账户异常
    public static final String ACC_ACCOUNT_ADJUSTMENT_AMOUNT_ERROR = "171003";// 反调账金额超过资金授信账户可用余额
    public static final String ACC_ACCOUNT_ADJUSTMENT_CHANGE_RECORD_ERROR = "171010";// 调账申请重复调账（资金变动记录已经存在）
    public static final String ACC_ACCOUNT_ADJUSTMENT_TOTAL_AMOUNT_OUTNUMBER_ERROR = "171011";// 调账申请对应账户总余额超过数据库可容纳的最大限额
    public static final String ACC_ACCOUNT_ADJUSTMENT_SUM_TOTAL_AMOUNT_OUTNUMBER_ERROR = "171012";// 调账申请对应账户累计总金额超过数据库可容纳的最大限额
    //-------------------------9 账户调帐 响应码  END  add by shenxiang-----------------------------------//

    public static final String ACC_ADJUSTMENT_UPDATE_ERROR = "171004";// 调账单修改出错
    public static final String ACC_ADJUSTMENT_DELETE_ERROR = "171005";// 调账单删除出错
    public static final String ACC_ADJUSTMENT_EMPTY = "171006";// 调账单为空
    public static final String ACC_ADJUSTMENT_INVALIDATE_STATE_APPROVE = "171007";// 此状态不能进行审核

    public static final String ACC_ADJUSTMENT_CUSTOMER_TYPE_ERROR = "171008";// 客户类型不对

    public static final String ACC_ADJUSTMENT_CUSTOMER_PARAM_ERROR = "171009";// 客户类型或客户号为空

    public static final String ACC_RiSK_DEFAULT_PARAM_ERROR = "171010";// 资金账户风控默认值更新参数错误

    public static final String ACC_RiSK_DEFAULT_SEARCH_PARAM_ERROR = "171011";// 资金账户风控默认值查询参数错误

    public static final String ACC_RiSK_SEARCH_PARAM_ERROR = "171012";// 资金账户风控参数错误

    /***************************************** 账户系统错误结束 *****************************************/

    /***************************************** 占用错误码开头18XXXX 开头 *****************************************/

    /***************************************** 占用错误码结束 *****************************************/

}
