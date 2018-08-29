/** 
 * Project Name:springboot-jsp 
 * File Name:APIServer.java 
 * Package Name:com.encrypt.APIParamsSign 
 * Date:2018年2月9日上午9:50:56 
 */ 
package com.encrypt.APIParamsSign;

//import java.util.HashMap;
//import java.util.Map;
//import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.encrypt.AES.AES;
//import com.alibaba.fastjson.JSON;
//import com.encrypt.RSA.GoYouLoanEncryptConfig;
import com.encrypt.RSA.RSA;
import com.encrypt.invokeAPI.InvokeAPIServer;

import net.sf.json.JSONObject;

/** 
* <p>Title:APIServer </p>
* <p>Description:进行API相关参数的排序，参数签名 </p>
* @author xn042142 付品欣
* @date 2018年2月9日 上午9:50:56 
*/
@Service
public class APIServer {

	private static Logger logger = Logger.getLogger(APIServer.class);
	
	@Autowired
	private InvokeAPIServer invokeAPIServer = new InvokeAPIServer();
	
	/**
	 * aesEncrypt:(采用AES算法进行加密，并对密文进行base64编码). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param toBoEncrypt 被加密的字符串
	 * @param password 加密秘密，16位数字或字母组成的字符串
	 * @return 加密后的字符串
	 * @throws Exception 
	 * @since JDK 1.8
	 * 2018年2月24日 下午3:52:55
	 */
	public String aesEncrypt(String toBoEncrypt,String password) throws Exception{
		String encryptedStr = AES.encryptToBase64(toBoEncrypt, password);
		return encryptedStr;
	}
	
	/**
	 * RESEncrypt:对字符串采用RSA算法进行加密
	 * TODO(被加密的字符串长度不能唱过117).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param toBoEncrypt 被加密的字符串
	 * @param privateKey 加密私钥
	 * @return 加密后的字符串
	 * @throws Exception 
	 * @since JDK 1.8
	 * 2018年2月24日 上午10:23:16
	 */
	public String RESEncrypt(String toBoEncrypt,String publicKey) throws Exception{
		String encryptedStr = RSA.encrypt(toBoEncrypt, publicKey);
		return encryptedStr;
	}
	
	/**
	 * checkParams:(对访问进行签名校验). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param merchantCode 商户号
	 * @param data 请求数据json字符串
	 * @param sign 待验证的签名
	 * @param publicKey 商户公钥
	 * @return 验证签名是否通过，true:通过，false:不通过（参数被篡改）
	 * @since JDK 1.8
	 * 2018年2月11日 下午12:38:36
	 */
	public boolean checkParams(String merchantCode,String data,String requestKey,String sign,String publicKey){
		boolean signResult = false;
		try{
			logger.info("data:"+data);
			/*JSONObject json = JSONObject.fromObject(data);
			logger.info("json:"+json.toString());
			//对json字符串进行排序后的字符串
			String orderStr = invokeAPIServer.orderJson(json);
			logger.info("orderStr:"+orderStr);
			//拼接被签名的字符串
			String signedStr = "data=" + orderStr + "&merchantCode=" + merchantCode;*/
			String signedStr = "data=" + data + "&merchantCode=" + merchantCode + "&requestKey="+requestKey;
			//验证签名是否通过
			logger.info("signedStr="+signedStr+",sign="+sign+",publicKey="+publicKey);
			signResult = RSA.checkSign(signedStr, sign, publicKey);
			//logger.info("对被签名的字符串：’" + signedStr + "',使用签名：‘" + sign +"'，进行验证的结果是：" + signResult);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(),e);
		}
		return signResult;
	}
	
	
	/**
	 * rsaDecrypt:对请求参数使用RSA算法运用私钥解密。
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param toBeDecrypt
	 * @param privateKey
	 * @return
	 * @throws Exception 
	 * @since JDK 1.8
	 * 2018年2月24日 上午11:23:17
	 */
	public String rsaDecrypt(String toBeDecrypt,String privateKey) throws Exception{
		String decryptStr = RSA.decrypt(toBeDecrypt, privateKey);
		return decryptStr;
	}
	
	/**
	 * aesDecrypt:(使用AES算法进行解密). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param toBeDecypted 被解密的字符串
	 * @param password 解密密码
	 * @return 解密后的字符串
	 * @since JDK 1.8
	 * 2018年2月24日 下午3:58:48
	 */
	public String aesDecrypt(String toBeDecypted,String password){
		String decryptStr = AES.decryptFromBase64(toBeDecypted, password);
		return decryptStr;
	}
	
}
