package com.dodopal.common.md5;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

public class Md5Encrypt {

    private static Logger logger = Logger.getLogger(Md5Encrypt.class);

    public static String cryptWithMD5(String pwd) {
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("MD5");
            byte[] passBytes;
            //try {
            passBytes = pwd.getBytes("UTF-8");
            //} 
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digested.length; i++) {
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            return sb.toString();
        }
        catch (NoSuchAlgorithmException ex) {
            logger.info("NoSuchAlgorithmException: " + ex);
        }
        catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
