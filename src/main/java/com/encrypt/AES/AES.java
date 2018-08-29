package com.encrypt.AES;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.encrypt.RSA.Base64;
import com.encrypt.RSA.ConfigureEncryptAndDecrypt;

public class AES {
	
	public static Random random = new Random();

	/**
	 * getRandom:获取指定长多的由（有字母（大小写）和0到9的数字组成随机字符串
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param length 
	 * @return 
	 * @since JDK 1.8
	 * 2018年2月24日 下午3:28:00
	 */
	public static String getRandom(int length) {
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < length; i++) {
			boolean isChar = (random.nextInt(2) % 2 == 0);// 输出字母还是数字
			if (isChar) { // 字符
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
				ret.append((char) (choice + random.nextInt(26)));
			} else { // 数字
				ret.append(Integer.toString(random.nextInt(10)));
			}
		}
		return ret.toString();
	}
	
//	/**
//	 * 加密
//	 * 
//	 * @param data
//	 *            需要加密的内容
//	 * @param key
//	 *            加密密码
//	 * @return
//	 */
	/**
	 * encrypt:(对byte数组使用AES算法用密码key进行加密). <br/> 
	 * TODO().<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param data 被加密的字节数组byte[]
	 * @param key byte[]格式的加密密码
	 * @return byte[]格式的密文
	 * @since JDK 1.8
	 * 2018年2月24日 下午3:49:08
	 */
	public static byte[] encrypt(byte[] data, byte[] key) {
		if(key.length!=16){
			throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
		}
		try {
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES"); 
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec seckey = new SecretKeySpec(enCodeFormat,"AES");
			Cipher cipher = Cipher.getInstance(ConfigureEncryptAndDecrypt.AES_ALGORITHM);// 创建密码器
			cipher.init(Cipher.ENCRYPT_MODE, seckey);// 初始化
			byte[] result = cipher.doFinal(data);
			return result; // 加密
		} catch (Exception e){
			throw new RuntimeException("encrypt fail!", e);
		}
	}

//	/**
//	 * 解密
//	 * 
//	 * @param data
//	 *            待解密内容
//	 * @param key
//	 *            解密密钥
//	 * @return
//	 */
	/**
	 * decrypt:(对AES加密后的byte[]格式的密文进行解密). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param data byte[]类型的密文
	 * @param key byte[]类型的解密密码
	 * @return byte[]类型的明文
	 * @since JDK 1.8
	 * 2018年2月24日 下午4:04:47
	 */
	public static byte[] decrypt(byte[] data, byte[] key) {
		if(key.length!=16){
			throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
		}
		try {
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES"); 
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance(ConfigureEncryptAndDecrypt.AES_ALGORITHM);// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, seckey);// 初始化
			byte[] result = cipher.doFinal(data);
			return result; // 加密
		} catch (Exception e){
			throw new RuntimeException("decrypt fail!", e);
		}
	}
	
	/**
	 * encryptToBase64:使用AES算法用密码key对data进行加密,并对密文进行base64编码
	 * TODO(AES是对称加密算法，加密密码和解密密码是同一个).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param data 被加密的数据
	 * @param key 加密密码
	 * @return 被base64编码的密文
	 * @since JDK 1.8
	 * 2018年2月24日 下午3:44:39
	 */
	public static String encryptToBase64(String data, String key){
		try {
			byte[] valueByte = encrypt(data.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING), key.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING));
			return new String(Base64.encode(valueByte));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("encrypt fail!", e);
		}
		
	}
	
	/**
	 * decryptFromBase64:(对AES加密后并经过base64编码的密文解密). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param data AES加密后并经过base64编码的密文
	 * @param key 解密密码
	 * @return 解密后的明文
	 * @since JDK 1.8
	 * 2018年2月24日 下午4:03:24
	 */
	public static String decryptFromBase64(String data, String key){
		try {
			byte[] originalData = Base64.decode(data.getBytes());
			byte[] valueByte = decrypt(originalData, key.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING));
			return new String(valueByte, ConfigureEncryptAndDecrypt.CHAR_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("decrypt fail!", e);
		}
	}
	
	/**
	 * encryptToBase64:使用AES算法用密码key对data进行加密,并对密文进行base64编码
	 * TODO(AES是对称加密算法，加密密码和解密密码是同一个).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param data 被加密的数据
	 * @param key 被base64编码后的加密密码，在加密前需要对此密码进行base64解密
	 * @return 被base64编码的密文
	 * @since JDK 1.8
	 * 2018年2月24日 下午3:44:39
	 */
	public static String encryptWithKeyBase64(String data, String key){
		try {
			byte[] valueByte = encrypt(data.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING), Base64.decode(key.getBytes()));
			return new String(Base64.encode(valueByte));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("encrypt fail!", e);
		}
	}
	
}
