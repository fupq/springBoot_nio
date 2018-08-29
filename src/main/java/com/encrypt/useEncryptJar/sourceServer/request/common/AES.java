package com.encrypt.useEncryptJar.sourceServer.request.common;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

//import request.common.Base64;
//import request.common.ConfigureEncryptAndDecrypt;

public class AES {
	
	public static Random random = new Random();

	/**
	 * getRandom:获取长度为length的字符串，由大小写字符和0-9的数字组成
	 * TODO(杩欓噷鎻忚堪杩欎釜鏂规硶鐨勬敞鎰忎簨椤� 鈥� 鍙��).<br/> 
	 * 
	 * @author xn042142 浠樺搧娆�
	 * @param length 
	 * @return 
	 * @since JDK 1.8
	 * 2018骞�2鏈�24鏃� 涓嬪崍3:28:00
	 */
	public static String getRandom(int length) {
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < length; i++) {
			boolean isChar = (random.nextInt(2) % 2 == 0);// 杈撳嚭瀛楁瘝杩樻槸鏁板瓧
			if (isChar) { // 瀛楃
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 鍙栧緱澶у啓瀛楁瘝杩樻槸灏忓啓瀛楁瘝
				ret.append((char) (choice + random.nextInt(26)));
			} else { // 鏁板瓧
				ret.append(Integer.toString(random.nextInt(10)));
			}
		}
		return ret.toString();
	}
	
//	/**
//	 * 鍔犲瘑
//	 * 
//	 * @param data
//	 *            闇�瑕佸姞瀵嗙殑鍐呭
//	 * @param key
//	 *            鍔犲瘑瀵嗙爜
//	 * @return
//	 */
	/**
	 * encrypt:(瀵筨yte鏁扮粍浣跨敤AES绠楁硶鐢ㄥ瘑鐮乲ey杩涜鍔犲瘑). <br/> 
	 * TODO().<br/> 
	 * 
	 * @author xn042142 浠樺搧娆�
	 * @param data 琚姞瀵嗙殑瀛楄妭鏁扮粍byte[]
	 * @param key byte[]鏍煎紡鐨勫姞瀵嗗瘑鐮�
	 * @return byte[]鏍煎紡鐨勫瘑鏂�
	 * @since JDK 1.8
	 * 2018骞�2鏈�24鏃� 涓嬪崍3:49:08
	 */
	public static byte[] encrypt(byte[] data, byte[] key) {
		if(key.length!=16){
			throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
		}
		try {
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES"); 
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec seckey = new SecretKeySpec(enCodeFormat,"AES");
			Cipher cipher = Cipher.getInstance(ConfigureEncryptAndDecrypt.AES_ALGORITHM);// 鍒涘缓瀵嗙爜鍣�
			cipher.init(Cipher.ENCRYPT_MODE, seckey);// 鍒濆鍖�
			byte[] result = cipher.doFinal(data);
			return result; // 鍔犲瘑
		} catch (Exception e){
			throw new RuntimeException("encrypt fail!", e);
		}
	}

//	/**
//	 * 瑙ｅ瘑
//	 * 
//	 * @param data
//	 *            寰呰В瀵嗗唴瀹�
//	 * @param key
//	 *            瑙ｅ瘑瀵嗛挜
//	 * @return
//	 */
	/**
	 * decrypt:(瀵笰ES鍔犲瘑鍚庣殑byte[]鏍煎紡鐨勫瘑鏂囪繘琛岃В瀵�). <br/> 
	 * TODO(杩欓噷鎻忚堪杩欎釜鏂规硶鐨勬敞鎰忎簨椤� 鈥� 鍙��).<br/> 
	 * 
	 * @author xn042142 浠樺搧娆�
	 * @param data byte[]绫诲瀷鐨勫瘑鏂�
	 * @param key byte[]绫诲瀷鐨勮В瀵嗗瘑鐮�
	 * @return byte[]绫诲瀷鐨勬槑鏂�
	 * @since JDK 1.8
	 * 2018骞�2鏈�24鏃� 涓嬪崍4:04:47
	 */
	public static byte[] decrypt(byte[] data, byte[] key) {
		if(key.length!=16){
			throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
		}
		try {
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES"); 
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance(ConfigureEncryptAndDecrypt.AES_ALGORITHM);// 鍒涘缓瀵嗙爜鍣�
			cipher.init(Cipher.DECRYPT_MODE, seckey);// 鍒濆鍖�
			byte[] result = cipher.doFinal(data);
			return result; // 鍔犲瘑
		} catch (Exception e){
			throw new RuntimeException("decrypt fail!", e);
		}
	}
	
	/**
	 * encryptToBase64:浣跨敤AES绠楁硶鐢ㄥ瘑鐮乲ey瀵筪ata杩涜鍔犲瘑,骞跺瀵嗘枃杩涜base64缂栫爜
	 * TODO(AES鏄绉板姞瀵嗙畻娉曪紝鍔犲瘑瀵嗙爜鍜岃В瀵嗗瘑鐮佹槸鍚屼竴涓�).<br/> 
	 * 
	 * @author xn042142 浠樺搧娆�
	 * @param data 琚姞瀵嗙殑鏁版嵁
	 * @param key 鍔犲瘑瀵嗙爜
	 * @return 琚玝ase64缂栫爜鐨勫瘑鏂�
	 * @since JDK 1.8
	 * 2018骞�2鏈�24鏃� 涓嬪崍3:44:39
	 */
	public static String encryptToBase64(String data, String key){
		try {
			byte[] valueByte = encrypt(data.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING), key.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING));
			return new String(Base64.encode(valueByte));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("加密失败!", e);
		}
		
	}
	
	/**
	 * decryptFromBase64:(瀵笰ES鍔犲瘑鍚庡苟缁忚繃base64缂栫爜鐨勫瘑鏂囪В瀵�). <br/> 
	 * TODO(杩欓噷鎻忚堪杩欎釜鏂规硶鐨勬敞鎰忎簨椤� 鈥� 鍙��).<br/> 
	 * 
	 * @author xn042142 浠樺搧娆�
	 * @param data AES鍔犲瘑鍚庡苟缁忚繃base64缂栫爜鐨勫瘑鏂�
	 * @param key 瑙ｅ瘑瀵嗙爜
	 * @return 瑙ｅ瘑鍚庣殑鏄庢枃
	 * @since JDK 1.8
	 * 2018骞�2鏈�24鏃� 涓嬪崍4:03:24
	 */
	public static String decryptFromBase64(String data, String key){
		try {
			byte[] originalData = Base64.decode(data.getBytes());
			byte[] valueByte = decrypt(originalData, key.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING));
			return new String(valueByte, ConfigureEncryptAndDecrypt.CHAR_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("解密失败!", e);
		}
	}
	
	/**
	 * encryptToBase64:浣跨敤AES绠楁硶鐢ㄥ瘑鐮乲ey瀵筪ata杩涜鍔犲瘑,骞跺瀵嗘枃杩涜base64缂栫爜
	 * TODO(AES鏄绉板姞瀵嗙畻娉曪紝鍔犲瘑瀵嗙爜鍜岃В瀵嗗瘑鐮佹槸鍚屼竴涓�).<br/> 
	 * 
	 * @author xn042142 浠樺搧娆�
	 * @param data 琚姞瀵嗙殑鏁版嵁
	 * @param key 琚玝ase64缂栫爜鍚庣殑鍔犲瘑瀵嗙爜锛屽湪鍔犲瘑鍓嶉渶瑕佸姝ゅ瘑鐮佽繘琛宐ase64瑙ｅ瘑
	 * @return 琚玝ase64缂栫爜鐨勫瘑鏂�
	 * @since JDK 1.8
	 * 2018骞�2鏈�24鏃� 涓嬪崍3:44:39
	 */
	public static String encryptWithKeyBase64(String data, String key){
		try {
			byte[] valueByte = encrypt(data.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING), Base64.decode(key.getBytes()));
			return new String(Base64.encode(valueByte));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("加密失败!", e);
		}
	}
	
}
