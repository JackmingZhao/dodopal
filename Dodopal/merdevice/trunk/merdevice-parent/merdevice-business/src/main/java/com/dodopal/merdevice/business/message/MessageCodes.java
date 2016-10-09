package com.dodopal.merdevice.business.message;


/**
 * 
 * @author synico
 *
 */

public class MessageCodes {
    
    private String[] messageDescription = new String[] {
            //[0]消息长度
            "0000",
            //[1]同步信息
            "8888888888888888",
            //[2]中间件类型
            "00",
            //[3]数据类型
            "00",
            //[4]加密算法
            "02",
            //[5]MD5校验码
            "00000000000000000000000000000000"
    };
    
    
    private String[] checkCard = new String[] {
            //[0]消息长度
            "0000",
            //[1]消息类型
            "0000",
            //[2]版本号
            "00",
            //[3]发送时间
            "00000000000000",
            //[4]特殊域启用标志
            "00",
            //[5]特殊域长度
            "0000",
            //[6]应答码
            "000000",
            //[7]城市代码
            "0000",
            //[8]商户类型
            "00",
            //[9]商户号
            "00000000000000000000",
            //[10]银行编号
            "00000000",
            //[11]卡号
            "00000000000000000000",
            //[12]卡面号
            "00000000000000000000",
            //[13]卡物理类型
            "00",
            //[14]设备类型
            "00",
            //[15]设备编号
            "000000000000",
            //[16]设备流水号
            "000000000",
            //[17]操作员号
            "0000000000000000",
            //[18]系统订单号(上行全0)
            "00000000000000000000",
            //[19]用户编号
            "0000000000",
            //[20]验卡类型
            "0",
            //[21]锁卡标志(上行全0)
            "0",
            //[22]交易金额
            "00000000",
            //[23]卡余额
            "00000000",
            //[24]特殊域
            "",
            //[25]保留字段
            "0000000000"
    };
    
    private String[] preProcessCharge = new String[] {
            //[0]消息长度
            "0000",
            //[1]消息类型
//          "2011/2012"
            "0000",
            //[2]版本号
            "01",
            //[3]发送时间
            "00000000000000",
            //[4]特殊域启用标志
            "10",
            //[5]特殊域长度
            "0000",
            //[6]应答码
            "000000",
            //[7]城市代码
//          "1123",
            "0000",
            //[8]商户类型
            "00",
            //[9]商户号
//          "411101101000036     ",
            "00000000000000000000",
            //[10]商户签名算法
            "00",
            //[11]待签数据长度
            "0000",
            //[12]待签名数据
            "",
            //[13]签名值长度
            "0000",
            //[14]签名值
            "",
            //[15]银行编号
//          "00007778",
            "00000000",
            //[16]用户编号
//          "0000003670",
            "0000000000",
            //[17]设备类型
            "00",
            //[18]设备编号
//          "974572714037",
            "000000000000",
            //[19]设备流水号
            "000000000",
            //[20]操作员号
            "0000000000000000",
            //[21]系统订单号
            "00000000000000000000",
            //[22]支付订单号
            "0000000000000000000000000000000000000000000000000000000000000000",
            //[23]卡物理类型
            "01",
            //[24]卡号
            "00000000000000000000",
            //[25]卡面号
            "00000000000000000000",
            //[26]交易日期
            "00000000",
            //[27]交易时间
            "000000",
            //[28]交易金额
            "00000000",
            //[29]交易前余额/卡余额
            "00000000",
            //[30]特殊域
//          "EF6FA98B1686880167810001EF6FA98B60847540000000FFFF010040004000000310002912201407080003C0A80AA6192014070800000000000000000000000000400001400020140708201512310000000000000000000000000000000000000000EF28FB68",
            "",
            //[31]保留字段
            "0000000000"
    };
    
    
    private String[] applyRechargeKeyt = new String[] {
            //[0]消息长度
            "0000",
            //[1]消息类型
//          "2101/2102",
            "0000",
            //[2]版本号
            "01",
            //[3]发送时间
            "00000000000000",
            //[4]特殊域启用标志
            "10",
            //[5]特殊域长度
            "0204",
            //[6]应答码
            "000000",
            //[7]城市代码
//          "1123",
            "0000",
            //[8]商户类型
            "00",
            //[9]商户号
//          "411101101000036",
            "00000000000000000000",
            //[10]银行编号
//          "00007778",
            "00000000",
            //[11]用户编号
//          "0000003670",
            "0000000000",
            //[12]设备类型
            "00",
            //[13]设备编号
//          "974572714037",
            "000000000000",
            //[14]设备流水号
            "000000000",
            //[15]操作员号
            "0000000000000000",
            //[16]系统订单号
//          "10000000000000092298",
            "00000000000000000000",
            //[17]支付订单号
//          "10000000000000092298",
            "0000000000000000000000000000000000000000000000000000000000000000",
            //[18]卡物理类型
            "01",
            //[19]卡号
//          "4000000310002912",
            "00000000000000000000",
            //[20]卡面号
//          "4000000310002912",
            "00000000000000000000",
            //[21]交易日期
//          "20140902",
            "00000000",
            //[22]交易时间
//          "142653",
            "000000",
            //[23]交易金额
            "00000000",
//          //[24]交易前余额
            "00000000",
//          //[25]卡计数器
            "0000",
//          //[26]密钥版本
            "00",
//          //[27]应用标识
            "00",
//          //[28]卡随机数
            "00000000",
//          //[29]MAC1
            "00000000",
            //[30]特殊域EF6FA98B1686880167810001EF6FA98B60847540000000FFFF010040004000000310002912201407080003C0A80AA6192014070800000000000000000000000000400001400020140708201512310000000000000000000000000000000000000000EF28FB68
            "",
            //[31]保留字段
            "0000000000"
    };
    
    
    private String[] uploadRechargeResult = new String[] {
            //[0]消息长度
            "0000",
            //[1]消息类型
//          "2201/2202",
            "0000",
            //[2]版本号
            "01",
            //[3]发送时间
//          "20140902142708",
            "00000000000000",
            //[4]特殊域启用标志
            "10",
            //[5]特殊域长度
            "0204",
            //[6]应答码
            "000000",
            //[7]城市代码
//          "1123",
            "0000",
            //[8]交易状态
            "0",
            //[9]商户类型
            "00",
            //[10]商户号
//          "411101101000036",
            "00000000000000000000",
            //[11]银行编号
//          "00007778",
            "00000000",
            //[12]用户编号
//          "0000003670",
            "0000000000",
            //[13]设备类型
            "00",
            //[14]设备编号
//          "974572714037",
            "000000000000",
            //[15]设备流水号
            "000000000",
            //[16]操作员号
            "0000000000000000",
            //[17]系统订单号
//          "10000000000000092298",
            "00000000000000000000",
            //[18]支付订单号
//          "10000000000000092298",
            "0000000000000000000000000000000000000000000000000000000000000000",
            //[19]卡物理类型
            "01",
            //[20]卡号
//          "4000000310002912",
            "00000000000000000000",
            //[21]卡面号
//          "4000000310002912",
            "00000000000000000000",
            //[22]交易日期
//          "20140902",
            "00000000",
            //[23]交易时间
//          "142653",
            "000000",
            //[24]交易金额
//          "00000001",
            "00000000",
            //[25]交易前余额
//          "00090000",
            "00000000",
            //[26]交易后金额
//          "00090001",
            "00000000",
            //[27]卡计数器
            "00000001",
            //[28]TAC(失败FFFFFFFF)
//          "C0686FEF",
            "00000000",
            //[29]特殊域
//          "EF6FA98B1686880167810001EF6FA98B60847540000000FFFF010040004000000310002912201407080003C0A80AA6192014070800000000000000000000000000400001400020140708201512310000000000000000000000000000000000000000EF28FB68",
            "",
            //[30]保留字段
            "0000000000"
    };
    
    
    private String[] preProcessPay = new String[] {
            //[0]消息长度
            "0000",
            //[1]消息类型
            "0000",
            //[2]版本号
            "01",
            //[3]发送时间
            "00000000000000",
            //[4]特殊域启用标志
            "10",
            //[5]特殊域长度
            "0000",
            //[6]应答码
            "000000",
            //[7]城市代码
            "0000",
            //[8]商户类型
            "00",
            //[9]商户号
            "00000000000000000000",
            //[10]用户编号
            "0000000000",
            //[11]设备类型
            "00",
            //[12]设备编号
            "000000000000",
            //[13]设备流水号
            "000000000",
            //[14]操作员号
            "0000000000000000",
            //[15]卡物理类型
            "00",
            //[16]系统订单号
            "00000000000000000000",
            //[17]支付订单号
            "0000000000000000000000000000000000000000000000000000000000000000",
            //[18]卡号
            "00000000000000000000",
            //[19]卡面号
            "00000000000000000000",
            //[20]交易日期
            "00000000",
            //[21]交易时间
            "000000",
            //[22]交易金额
            "00000000",
            //[23]卡余额
            "00000000",
            //[24]特殊域
            "",
            //[25]保留字段
            "0000000000"
    };
    
    
    private String[] applyPayKeyt = new String[] {
            //[0]消息长度
            "0000",
            //[1]消息类型
            "0000",
            //[2]版本号
            "01",
            //[3]发送时间
            "00000000000000",
            //[4]特殊域启用标志
            "00",
            //[5]特殊域长度
            "0000",
            //[6]应答码
            "000000",
            //[7]城市代码
            "0000",
            //[8]商户类型
            "00",
            //[9]商户号
            "00000000000000000000",
            //[10]用户编号
            "0000000000",
            //[11]设备类型
            "00",
            //[12]设备编号
            "000000000000",
            //[13]设备流水号
            "000000000",
            //[14]操作员号
            "0000000000000000",
            //[15]卡物理类型
            "00",
            //[16]系统订单号
            "00000000000000000000",
            //[17]支付订单号
            "0000000000000000000000000000000000000000000000000000000000000000",
            //[18]卡号
            "00000000000000000000",
            //[19]卡面号
            "00000000000000000000",
            //[20]交易日期
            "00000000",
            //[21]交易时间
            "000000",
            //[22]交易金额
            "00000000",
            //[23]安全认证码
            "000000000000000000",
            //[24]交易前余额
            "00000000",
            //[25]卡计数器
            "0000",
            //[26]透支限额
            "000000",
            //[27]密钥版本
            "00",
            //[28]应用标识
            "00",
            //[29]卡随机数
            "00000000",
            //[30]特殊域
            "",
            //[31]保留字段
            "0000000000"
    };
    
    
    private String[] uploadPayResult = new String[] {
            //[0]消息长度
            "0000",
            //[1]消息类型
            "0000",
            //[2]版本号
            "01",
            //[3]发送时间
            "00000000000000",
            //[4]特殊域启用标志
            "00",
            //[5]特殊域长度
            "0000",
            //[6]应答码
            "000000",
            //[7]城市代码
            "0000",
            //[8]交易状态
            "0",
            //[9]商户类型
            "00",
            //[10]商户号
            "00000000000000000000",
            //[11]银行编号
            "00000000",
            //[12]用户编号
            "0000000000",
            //[13]设备类型
            "00",
            //[14]设备编号
            "000000000000",
            //[15]设备流水号
            "000000000",
            //[16]操作员号
            "0000000000000000",
            //[17]卡物理类型
            "01",
            //[18]系统订单号
            "00000000000000000000",
            //[19]支付订单号
            "0000000000000000000000000000000000000000000000000000000000000000",
            //[20]卡号
            "00000000000000000000",
            //[21]卡面号
            "00000000000000000000",
            //[22]交易日期
            "00000000",
            //[23]交易时间
            "000000",
            //[24]交易金额
            "00000000",
            //[25]交易前余额
            "00000000",
            //[26]交易后金额
            "00000000",
            //[27]卡计数器
            "00000000",
            //[28]TAC
            "00000000",
            //[29]MAC2
            "00000000",
            //[30]特殊域
            "",
            //[31]保留字段
            "0000000000"
            
    };
    
    
    private String[] checkResponse = new String[] {
            //消息长度
            "0000",
            //消息类型
            "0000",
            //版本号
            "00",
            //发送时间
            "00000000000000",
            //特殊域启用标志
            "00",
            //特殊域长度
            "0000",
            //应答码
            "000000"
    };
    
    
    private String[] crdMData = new String[] {
            //Uid
            "00000000",
            //AtsLen
            "00",
            //Ats
            "",
            //RootFileLen
            "00",
            //RootFile
            "",
            //AppFileLen
            "00",
            //AppFile
            "",
            //SpecDataLen
            "0000",
            //SpecData
            ""
    };
    
    
    private String[] specData = new String[] {
            //File06Len
            "00",
            //File06
            "",
            //Record1
            "0000000000000000",
            //Record2
            "0000000000000000",
            //WalletBal
            "00000000"
    };
    
    
    private String[] apduHeader = new String[] {
            //ApduNum
            "00",
            //ApduData
            ""
    };
    
    
    private String[] apduData = new String[] {
            //ApduFlag
            "00",
            //ApduLen
            "00",
            //Apdu
            ""
    };


    /**
     * @return the messageDescription
     */
    public String[] getMessageDescription() {
        return messageDescription;
    }


    /**
     * @param messageDescription the messageDescription to set
     */
    public void setMessageDescription(String[] messageDescription) {
        this.messageDescription = messageDescription;
    }


    /**
     * @return the preProcessCharge
     */
    public String[] getPreProcessCharge() {
        return preProcessCharge;
    }


    /**
     * @param preProcessCharge the preProcessCharge to set
     */
    public void setPreProcessCharge(String[] preProcessCharge) {
        this.preProcessCharge = preProcessCharge;
    }


    /**
     * @return the applyRechargeKeyt
     */
    public String[] getApplyRechargeKeyt() {
        return applyRechargeKeyt;
    }


    /**
     * @param applyRechargeKeyt the applyRechargeKeyt to set
     */
    public void setApplyRechargeKeyt(String[] applyRechargeKeyt) {
        this.applyRechargeKeyt = applyRechargeKeyt;
    }


    /**
     * @return the uploadRechargeResult
     */
    public String[] getUploadRechargeResult() {
        return uploadRechargeResult;
    }


    /**
     * @param uploadRechargeResult the uploadRechargeResult to set
     */
    public void setUploadRechargeResult(String[] uploadRechargeResult) {
        this.uploadRechargeResult = uploadRechargeResult;
    }


    /**
     * @return the checkCard
     */
    public String[] getCheckCard() {
        return checkCard;
    }


    /**
     * @param checkCard the checkCard to set
     */
    public void setCheckCard(String[] checkCard) {
        this.checkCard = checkCard;
    }


    /**
     * @return the preProcessPay
     */
    public String[] getPreProcessPay() {
        return preProcessPay;
    }


    /**
     * @param preProcessPay the preProcessPay to set
     */
    public void setPreProcessPay(String[] preProcessPay) {
        this.preProcessPay = preProcessPay;
    }


    /**
     * @return the applyPayKeyt
     */
    public String[] getApplyPayKeyt() {
        return applyPayKeyt;
    }


    /**
     * @param applyPayKeyt the applyPayKeyt to set
     */
    public void setApplyPayKeyt(String[] applyPayKeyt) {
        this.applyPayKeyt = applyPayKeyt;
    }


    /**
     * @return the uploadPayResult
     */
    public String[] getUploadPayResult() {
        return uploadPayResult;
    }


    /**
     * @param uploadPayResult the uploadPayResult to set
     */
    public void setUploadPayResult(String[] uploadPayResult) {
        this.uploadPayResult = uploadPayResult;
    }


    /**
     * @return the checkResponse
     */
    public String[] getCheckResponse() {
        return checkResponse;
    }


    /**
     * @param checkResponse the checkResponse to set
     */
    public void setCheckResponse(String[] checkResponse) {
        this.checkResponse = checkResponse;
    }


    /**
     * @return the crdMData
     */
    public String[] getCrdMData() {
        return crdMData;
    }


    /**
     * @param crdMData the crdMData to set
     */
    public void setCrdMData(String[] crdMData) {
        this.crdMData = crdMData;
    }


    /**
     * @return the specData
     */
    public String[] getSpecData() {
        return specData;
    }


    /**
     * @param specData the specData to set
     */
    public void setSpecData(String[] specData) {
        this.specData = specData;
    }
    
    
    /**
     * @return the apduHeader
     */
    public String[] getApduHeader() {
        return apduHeader;
    }


    /**
     * @param apduHeader the apduHeader to set
     */
    public void setApduHeader(String[] apduHeader) {
        this.apduHeader = apduHeader;
    }


    /**
     * @return the apduData
     */
    public String[] getApduData() {
        return apduData;
    }


    /**
     * @param apduData the apduData to set
     */
    public void setApduData(String[] apduData) {
        this.apduData = apduData;
    }

}
