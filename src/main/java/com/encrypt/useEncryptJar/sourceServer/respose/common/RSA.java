package com.encrypt.useEncryptJar.sourceServer.respose.common;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
//import java.net.URLEncoder;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;

public class RSA {

	/** *//** 
     * 加密算法RSA 
     */  
    public static final String KEY_ALGORITHM = "RSA"; 
    
	/**
     * RSA最大加密明文大小 
     */  
    private static final int MAX_ENCRYPT_BLOCK = 117;  
      
    /**
     * RSA最大解密密文大小 
     */  
    //private static final int MAX_DECRYPT_BLOCK = 128;  
    
	/**
     * 指定key的大
     */
    private static int KEYSIZE = 1024;
    
    /**
     * generateKeyPair:(使用RSA算法随机生成公钥和私钥). <br/> 
     * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
     * 
     * @author xn042142 付品欣
     * @return
     * @throws Exception 
     * @since JDK 1.8
     * 2018年2月7日 下午4:20:26
     */
    public static Map<String, String> generateKeyPair() throws Exception {
        /** RSA算法要求有一个可信任的随机数 */
        SecureRandom sr = new SecureRandom();
        /** 为RSA算法创建个KeyPairGenerator对象 */
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        /** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
        kpg.initialize(KEYSIZE, sr);
        /** 生成密匙 */
        KeyPair kp = kpg.generateKeyPair();
        /** 得到公钥 */
        Key publicKey = kp.getPublic();
        byte[] publicKeyBytes = publicKey.getEncoded();
        String pub = new String(Base64.encodeBase64(publicKeyBytes), ConfigureEncryptAndDecrypt.CHAR_ENCODING);
        /** 得到私钥 */
        Key privateKey = kp.getPrivate();
        byte[] privateKeyBytes = privateKey.getEncoded();
        String pri = new String(Base64.encodeBase64(privateKeyBytes), ConfigureEncryptAndDecrypt.CHAR_ENCODING);

        Map<String, String> map = new HashMap<String, String>();
        map.put("publicKey", pub);
        map.put("privateKey", pri);
        RSAPublicKey rsp = (RSAPublicKey) kp.getPublic();
        BigInteger bint = rsp.getModulus();
        byte[] b = bint.toByteArray();
        byte[] deBase64Value = Base64.encodeBase64(b);
        String retValue = new String(deBase64Value);
        map.put("modulus", retValue);
        return map;
    }
    
    /**
     * getPublicKey:获取公钥
     * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
     * 
     * @author xn042142 付品欣
     * @param key 公钥字符串（经过base64编码)
     * @return
     * @throws Exception 
     * @since JDK 1.8
     * 2018年2月8日 上午9:50:23
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(key.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }
    
    /**
     * encrypt:使用RSA算法对源数据进行加密
     * TODO(被加密的字符串长度不超过117).<br/> 
     * 
     * @author xn042142 付品欣
     * @param source 被加密的源字符串
     * @param publicKey 加密公钥字符串
     * @return RSA加密后的字符串
     * @throws Exception 
     * @since JDK 1.8
     * 2018年2月8日 上午9:52:22
     */
    public static String encrypt(String source, String publicKey) throws Exception {
        Key key = getPublicKey(publicKey);
        /** 得到Cipher对象来实现对源数据的RSA加密 */
        Cipher cipher = Cipher.getInstance(ConfigureEncryptAndDecrypt.RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] b = source.getBytes();
        /** 执行加密操作 */
        byte[] b1 = cipher.doFinal(b);
        return new String(Base64.encodeBase64(b1), ConfigureEncryptAndDecrypt.CHAR_ENCODING);
    }
    
    /**
     * encryptMore:公钥加密(分段加密--被加密的字符串超过117bytes时采用). <br/> 
     * TODO(分段加密).<br/> 
     * 
     * @author xn042142 付品欣
     * @param source 被加密字符串
     * @param publicKey 加密公钥
     * @return 加密后的字符串
     * @throws Exception 
     * @since JDK 1.8
     * 2018年2月24日 下午2:06:27
     */
    public static String encryptMore(String source, String publicKey) throws Exception{
    	/*Key key = getPublicKey(publicKey);
    	//得到Cipher对象来实现对源数据的RSA加密 
        Cipher cipher = Cipher.getInstance(ConfigureEncryptAndDecrypt.RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);*/
    	byte[] publicKeyByte = publicKey.getBytes();
    	X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyByte);
    	KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
        Key publicK = keyFactory.generatePublic(x509KeySpec); 
        // 对数据加密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.ENCRYPT_MODE, publicK);  
    	
        byte[] sourceByte = source.getBytes();
        int inputLen = sourceByte.length;  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        int index = 0;
        // 对数据分段加密 
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {  
            	//执行块加密
                cache = cipher.doFinal(sourceByte, offSet, MAX_ENCRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(sourceByte, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            index += cache.length;
            offSet = i * MAX_ENCRYPT_BLOCK;  
        }  
        byte[] encryptedData = out.toByteArray();  
        out.close();  
        return encryptedData.toString();
    }
    
    /**
     * getPrivateKey:获取私钥
     * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
     * 
     * @author xn042142 付品欣
     * @param key 私钥字符串，经过base64编码
     * @return 私钥
     * @throws Exception 
     * @since JDK 1.8
     * 2018年2月7日 下午5:05:26
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(key.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }
    
    /**
     * decrypt:用私钥解密请求参数
     * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
     * 
     * @author xn042142 付品欣
     * @param cryptograph 被解密的字符串
     * @param privateKey 解密私钥
     * @return 解密后的字符串
     * @throws Exception 
     * @since JDK 1.8
     * 2018年2月7日 下午5:09:37
     */
    public static String decrypt(String cryptograph, String privateKey) throws Exception {
        Key key = getPrivateKey(privateKey);
        /** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
        Cipher cipher = Cipher.getInstance(ConfigureEncryptAndDecrypt.RSA_ALGORITHM); //使用RSA算法，RSA/ECB/PKCS1Padding
        //设置操作类型为解密
        cipher.init(Cipher.DECRYPT_MODE, key); 
        //对被解密的字符串转换为字节数据并进行base64解码
        byte[] b1 = Base64.decodeBase64(cryptograph.getBytes()); 
        /** 执行解密操作 */
        byte[] b = cipher.doFinal(b1);
        return new String(b);
    }
    
    
    /**
     * checkSign:验证签名是否通过
     * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
     * 
     * @author xn042142 付品欣
     * @param content 被签名的字符串
     * @param sign 签名
     * @param publicKey 签名用的公钥
     * @return 验证签名是否通过，true:通过，false:不通过（参数被篡改）
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeySpecException 
     * @throws InvalidKeyException 
     * @throws UnsupportedEncodingException 
     * @throws SignatureException 
     * @since JDK 1.8
     * 2018年2月7日 下午4:21:56
     */
    public static boolean checkSign(String content, String sign, String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
        //try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            //对公钥进行base64解码成字节数据
            byte[] encodedKey = Base64.decode2(publicKey);
            //根据公钥生成PublicKey公钥对象
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            //SHA1WithRSA:进行SHA1算法进行签名后，要求对签名后的数据进行处理，而不是直接进行RSA算法(采用RSA_PKCS1_PADDING)进行加密
            Signature signature = Signature.getInstance("SHA1WithRSA");

            signature.initVerify(pubKey);
            //使用指定的字节数组更新要签名或验证的数据。
            signature.update(content.getBytes("utf-8"));
            //将sign进行base64解码；验证传入的签名。如果签名属实则返回true，否则返回false。
            System.out.println("sign:"+sign);
            byte[] signByte = Base64.decode2(sign);
            System.out.println("signByte:"+signByte.toString());
            boolean result = signature.verify(signByte);
            return result;

        /*} catch (Exception e) {
            // logger.error("校验RSA签名错误:[content:{},publicKey:{}***]", content, publicKey.substring(0, 10), e);
        }*/
        //return false;
    }
    
    /**
     * sign:对字符串采用RSA算法使用私钥privateKey进行签名
     * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
     * 
     * @author xn042142 付品欣
     * @param content 被签名的字符串
     * @param privateKey 私钥字符串
     * @return 签名后的字符串
     * @since JDK 1.8
     * 2018年2月8日 上午11:42:44
     */
    public static String sign(String content, String privateKey) {
        String charset = ConfigureEncryptAndDecrypt.CHAR_ENCODING;
        String sign;
        //String signUTS_8;
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey.getBytes()));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            
            //SHA1WithRSA:进行SHA1算法进行签名后，要求对签名后的数据进行处理，而不是直接进行RSA算法(采用RSA_PKCS1_PADDING)进行加密
            Signature signature = Signature.getInstance("SHA1WithRSA");

            signature.initSign(priKey);
            //使用指定的字节数组更新要签名或验证的数据。
            signature.update(content.getBytes(charset));
            //返回所有更新的数据的签名字节。签名的格式取决于基础签名方案。
            //对该方法的调用将此签名对象重置为先前初始化时通过调用initSign(PrivateKey)进行签名的状态。也就是说，该对象被重置，如果需要，可以通过新的调用来生成另一个签名(如果需要的话)，通过新的调用来更新和签名。
            byte[] signed = signature.sign();
            //对签名进行base64进行编码
            sign = new String(Base64.encodeBase64(signed));
            return sign;
        } catch (Exception e) {
            // logger.error("生成RSA签名错误:[content:{},privateKey:{}***]", content, privateKey.substring(0, 10), e);
        }
        return null;
    }
}





