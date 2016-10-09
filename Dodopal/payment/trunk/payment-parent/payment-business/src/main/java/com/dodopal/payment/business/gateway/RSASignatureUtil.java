package com.dodopal.payment.business.gateway;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;

public class RSASignatureUtil {

    //public static final String TEST_PUBLICK_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDS92pDVyWNT7dzG9zH0opH44z9FayCZTX5iqGUxUjPi667IkyaqrsmDPqKsJp47lJ29lzs+Qv8zjPPdmnxjFteMrfpc4ui24gL1iZnchwX87Ox/+Xrm8HFmKlhmUO9n/QgTT+Nz1RGMEN1+HijvsoAhS0TS8XjSfzRkrwvK2pJQIDAQAB";
    //public static final String TEST_PUBLICK_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDS92pDVyWNT7dzG9zH0opH44z9FayCZTX5iqGUxUjPi667IkyaqrsmDPqKsJp47lJ29lzs+Qv8zjPPdmnxjFteMrfpc4ui24gL1iZnchwX87Ox/+Xrm8HFmKlhmUO9n/QgTT+Nz1RGMEN1+HijvsoAhS0TS8XjSfzRkrwvK2pJQIDAQAB";
//	public static final String TEST_PUBLICK_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCi51JRFgYfEnzUOfC+EV3Q8lL5/I/h8e9dE8LAYZNkFGN6m0zIdyarHaYhQPMby7gX8B8IzU70xc7c2sE774LKbX2ZQzdXoQtBGTiBNxFU15hFgzJvyLa4H90gb5nzlDI4JtxMXQ9HaCbwWzoRQXPMlCL//9URWR8ba8SDElAquwIDAQAB";
//    public static final String TEST_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKLnUlEWBh8SfNQ58L4RXdDyUvn8j+Hx710TwsBhk2QUY3qbTMh3JqsdpiFA8xvLuBfwHwjNTvTFztzawTvvgsptfZlDN1ehC0EZOIE3EVTXmEWDMm/Itrgf3SBvmfOUMjgm3ExdD0doJvBbOhFBc8yUIv//1RFZHxtrxIMSUCq7AgMBAAECgYEAkEkw+M74Bwd59wOGvuJk5Q/s/Kx9yFNgfmFlSr5iLO9+GPd6r8gRQNiW77c2XLU4zkMJSeOG/nhBvA4pvutqPHV53j4YnfWuSb4eVcflPnVs+oQaCEtQreS5LabXd5Z5GzPlBz3hyawvUn1Rgu0P7xpLvAJEfyRFqJfuiIlgD8kCQQDTfpm4yzrjNLXfjoxzwtbcfjRh1QgC8MYNzQvKGI9C5f7vgBUUOyZgJzRhZXY5S0y7PO7rI600O2dwxJM+ng/fAkEAxS8VhXqkyWRJU3IYwhN39T0cW7/UtTVkEqzuz6dD0sY5jksfEF+PYSSLHLzEbHS+F5Ky8ddVvKehzahg8M4QpQJAB1KBxfkbT9R6W4QW7scXI0knTR/QCDIH2WB5AQU6qIlH+0jG8g1eNcQFtz2JC1DFFlJ0vyVcEMvht/DGXEuipwJANsJUn7TnfmYzVtEgvwTZE2ndLj2fvJMPL4CG8XPqCeaypSmHfuyKzNdxKpHhmpzxDWrX9wFt9e43qP1Rro4LYQJAU5IvszYPwIP53dZF2gSA4PWJU4WFBBVnEdYX4LOE5ih4saTHu5D71E4RiCrbUemio/MXms4534ZIm7q8IUNlFg==";

    public static final String TEST_PUBLICK_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
    public static final String TEST_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL6UJgSlHQGOYNej"
													+"QfEfedT1BPihRyZZeB8fUln5uPwhccUyfUAhyrGGdXxyF91AD++9mg70TUSfo9Oh"
													+"1xOgWSHD5NZTC7e9ND7n6xnJ4Htqmch03VktxVYAHIQ5FLMzPp5QdwNEo4BFQO3T"
													+"+3V5m57ICAeVzdZSp8Ce6RvKqHLtAgMBAAECgYEAtpE5sm1yUhFRGIJfI1xh2+41"
													+"KvFlNYm0FyxsOaGO4oovcRejARLeGB6U4fhygBNksOfTc8v5UWoVCSIa5cRQXO7z"
													+"A3QSrlHe9DEz+8AOHY/Aqxnb8JWuUFxhxg4cQi2xFStnN1LDIl+Fze/u6HDIF1qi"
													+"XrMyWFbM1qxLRNxLRcECQQDivRDas0VjCmt/pFfHV+Y13eesmFPDyBD8IY+eNrV6"
													+"9lpvEwM8qy/3rM3PQji1XxcA8gHwPRbPwj85SQBPVNdJAkEA1yxxUf2zvxyIVonB"
													+"0nm3q6CvZGm+AjPaKcmKlGHqqU3fV1JKzmifXXTBE56kSCUK960Es9dGtB/qSNqm"
													+"Gw3KhQJAStVXwLa2Se/gdupIxlQueMzab85+pvlbEbwJ6I/p6cFxK4O858MgO8Be"
													+"1O7oRIQz1W4KUb33z24u67s6TPSVcQJAegL/dwivZg0d2kRfHaRbW/zPt9amIcWZ"
													+"Tdy0shMb50j6/h7xwD1fMLVFzoREj4xBvdPkOL43n2gBh7JEQk79qQJBAITYOwbE"
													+"hQYa78xgU4SWBgOb3F1zn6/AxFhaH/KC8XAzXYK7cphQY7wE0GwLlv2iINUmLwCa"
													+"g8bjhXi+bdnY99s=";
    
	/**
	 * 自助充值（北京）的钥(RSA)用于验签(测试)
	 * */
	public static final String public_zizhu_key="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
   
	public static final String PRIVATE_zizhu_key ="MIICXAIBAAKBgQDPgu/wdQewa1i0eRX/Rzef2kMfKwFimuau5T486mtQMjs8attW"
													+"LYM9ibYf77U1e3/IK/77QczIEucTWYtRHpQC5LDutzECfOHGCNThg2hHyMufybCP"
													+"x68JaCklrRcxvuPrJXVA+YFP/dAFg7ki7ijKBjL6cMNL18HIqCMqet0ZDwIDAQAB"
													+"AoGAerhu+f/cKkNx1Fl/3tbZWJoV9ArU6OG+51P/uLrR+D2ctqBjUtXbtbsJkF2N"
													+"BzIgYdtTEnjZiMFVEyZzs+enk/XYbmsLXwPobULNlfHgMq+RFPCUcgX8VPWyMJt6"
													+"WjMhsz5nG7m8tzdKUxNrA+JrKYjqT/mfcygGugH+M2cX/aECQQDn2PFZ3htipfSQ"
													+"KCTN7ApCjYJG4E3zbnm8RrOnNvTb4ij/VXoMKbRY52t4slqlY3LHBQQzW6nwzpSm"
													+"r3Nz14y3AkEA5SD8xgdyIZGp2I/UDguer9EMeNtqbzEm41wVK7reTie/leFAoHuF"
													+"KRD01pugQtvnctUT8zGfw/vDW5VrCLCuaQJBAJNdWUr92fpB8ckPOxWISp0yxPO3"
													+"7tQkDEyXw+ktDUYV4mkpM4I3n+JCdhUpiGRHadUGYz0hrkrZg7LPIlsnTtECQHWF"
													+"O+EeRI2vNpxa+PrTPusSoAEFGUw6u6V1v24UhJ01ifCH/ivR7vSnO80vOT3RqJ6f"
													+"Z4Ie+8D7yrGYq7NjW7ECQFETM24dFVChPc+qtpuVFletlmlR5RbBl+kmIejJvwjM"
													+"y0w0kJZbwajmqyai2EpVUTbHviMAXNRC5I/UHFjgV44=";
														
	
	
	/**
     * Generate ALIPAY RSA signature.
     * 
     * @return signature
     * @throws Exception if error occurred
     * @see #generatePrivateKey(String)
     * @see Signature#getInstance(String)
     * @see Signature#sign()
     * @see Base64#encodeBase64(byte[])
     */
    public String generate(String key, String payload, String charset) throws Exception {
        PrivateKey privateKey = generatePrivateKey(key);

        Signature signature = Signature.getInstance("SHA1WithRSA");
        signature.initSign(privateKey);
        signature.update(payload.getBytes(charset));

        // generate signature
        byte[] rawSignature = signature.sign();

        // Base64 Encode
        return new String(Base64.encodeBase64(rawSignature));
    }

    /**
     * Generate private key from encoded key.
     * 
     * @param encodedKey encoded key, should not be null
     * @return private key
     * @throws Exception if failed to generate
     * @see PKCS8EncodedKeySpec
     * @see Base64#decodeBase64(byte[])
     * @see KeyFactory#getInstance(String)
     */
    private PrivateKey generatePrivateKey(String encodedKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        // Base64 Decode
        byte[] decodedKey = Base64.decodeBase64(encodedKey.getBytes());
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodedKey));
    }

    /**
     * Verify signature.
     * 
     * @param key key
     * @param signature signature
     * @param payload payload
     * @param charset charset
     * @return true if passed, otherwise false
     * @throws Exception if error occurred
     * @see #generatePublicKey(String)
     * @see Signature#getInstance(String)
     * @see Signature#verify(byte[])
     * @see Base64#decodeBase64(byte[])
     */
    public static boolean verify(String key, String signature, String payload, String charset)
                                                                                       throws Exception {
        PublicKey publicKey = generatePublicKey(key);
        Signature signatureVerifier = Signature.getInstance("SHA1WithRSA");

        signatureVerifier.initVerify(publicKey);
        signatureVerifier.update(payload.getBytes(charset));

        // Base64 Decode
        return signatureVerifier.verify(Base64.decodeBase64(signature.getBytes()));
    }

    /**
     * Generate public key.
     * 
     * @param encodedKey encoded key, should not be null
     * @return public key
     * @throws Exception if failed to generate
     * @see KeyFactory#getInstance(String)
     * @see Base64#decodeBase64(byte[])
     * @see X509EncodedKeySpec
     */
    private static PublicKey generatePublicKey(String encodedKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        // Base64 Decode
        byte[] decodedKey = Base64.decodeBase64(encodedKey.getBytes());
        return keyFactory.generatePublic(new X509EncodedKeySpec(decodedKey));
    }
    
    
    

    /**
     * Test cases.
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        RSASignatureUtil example = new RSASignatureUtil();
        String payload = "11111";
        String charset = "UTF-8";

        String sign = example.generate(PRIVATE_zizhu_key, payload, charset);
        System.out.println(sign);
        System.out.println(example.verify(public_zizhu_key, sign, payload, charset));
    }

}
