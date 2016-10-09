package com.dodopal.card.business.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DES3CardEncrypt {
    
    /**
     * 商户号，密文
     * @param merCode
     * @param src
     * @return
     */
    public static String decrypt(String merCode,byte[] src){
        return null;
    }
    
//    /**
//     * 加密方法
//     * @param src 源数据的字节数组
//     * @return 
//     */
//    public static byte[] encryptMode(byte[] src) {
//        try {
//             SecretKey deskey = new SecretKeySpec(build3DesKey(PASSWORD_CRYPT_KEY), Algorithm);    //生成密钥
//             Cipher c1 = Cipher.getInstance(Algorithm);    //实例化负责加密/解密的Cipher工具类
//             c1.init(Cipher.ENCRYPT_MODE, deskey);    //初始化为加密模式
//             return c1.doFinal(src);
//         } catch (java.security.NoSuchAlgorithmException e1) {
//             e1.printStackTrace();
//         } catch (javax.crypto.NoSuchPaddingException e2) {
//             e2.printStackTrace();
//         } catch (java.lang.Exception e3) {
//             e3.printStackTrace();
//         }
//         return null;
//     }
//    
//
//    //keybyte为加密密钥，长度为24字节
//    //src为被加密的数据缓冲区（源）
//    public static byte[] encryptMode(byte[] keybyte, byte[] src) {
//       try {
//            //生成密钥
//            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
//
//            //加密
//            Cipher c1 = Cipher.getInstance(Algorithm);
//            c1.init(Cipher.ENCRYPT_MODE, deskey);
//            return c1.doFinal(src);
//        } catch (java.security.NoSuchAlgorithmException e1) {
//            e1.printStackTrace();
//        } catch (javax.crypto.NoSuchPaddingException e2) {
//            e2.printStackTrace();
//        } catch (java.lang.Exception e3) {
//            e3.printStackTrace();
//        }
//        return null;
//    }
}
