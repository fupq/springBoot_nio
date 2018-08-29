/** 
 * Project Name:rsaSignAesEncrypt 
 * File Name:ResposeCheckSign.java 
 * Package Name:respose 
 * Date:2018年2月26日下午8:04:07 
 */ 
package com.encrypt.useEncryptJar.sourceServer.respose;

import java.net.URLEncoder;

import com.encrypt.useEncryptJar.sourceServer.log.Logger;
//import request.CheckSignEntity;
//import respose.common.AES;
//import respose.common.ConfigureEncryptAndDecrypt;
//import respose.common.RSA;
import com.encrypt.useEncryptJar.sourceServer.request.CheckSignEntity;
import com.encrypt.useEncryptJar.sourceServer.respose.common.AES;
import com.encrypt.useEncryptJar.sourceServer.respose.common.ConfigureEncryptAndDecrypt;
import com.encrypt.useEncryptJar.sourceServer.respose.common.RSA;

/** 
* <p>Title:ResposeCheckSign </p>
* <p>Description: </p>
* @author xn042142 付品欣
* @date 2018年2月26日 下午8:04:07 
*/
public class ResposeCheckSign {

	/**
	 * toDecryptCheckSign:(服务提供端使用该方法验证请求参数的签名是否通过，并解密出请求业务参数). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param resposeStr
	 * @param publicKey
	 * @param privateKey
	 * @return
	 * @throws Exception 
	 * @since JDK 1.8
	 * 2018年2月26日 下午8:15:02
	 */
	public CheckSignEntity toDecryptCheckSign(String data,String merchantCode,String requestKey,String sign,String publicKey,String privateKey) throws Exception{
		boolean checkStatus = false;
		String responseData = null;
		Logger.println("授信接口接受到的请求参数：\n**********************merchantCode="+merchantCode+",\ndata="+data+",\nrequestKey="+requestKey+",\nsign="+sign);
		String signResult="";
		//对访问进行签名校验
		boolean checkResult = checkParams(merchantCode,data,requestKey,sign,publicKey);
		if(false == checkResult){
			signResult = "请求参数签名验证失败！";
			checkStatus = false;
		}else{
			signResult = "请求参数签名验证通过！";
			checkStatus = true;
			
			Logger.println("**********************输入参数解密前data:"+data);
			//对requestKey使用普惠私钥采用RSA算法解密
			Logger.println("**********************解密前的requestKey:"+data);
			requestKey = RSA.decrypt(requestKey, privateKey);
			Logger.println("**********************使用普惠私钥采用RSA算法解密后的requestKey:"+requestKey);
			//对请求参数使用AES算法解密
			data = AES.decryptFromBase64(data, requestKey);//rsaDecrypt(data, puHuiPrivateKey);
			Logger.println("**********************输入参数解密后data:"+data);
			responseData = data;
		}
		Logger.println(signResult);
		CheckSignEntity checkSignEntity = new CheckSignEntity(checkStatus,responseData);
		return checkSignEntity;
	}
	
	/**
	 * encryptSignDealwith:(服务提供端处理完请求生成响应的业务数据后调用该方法对响应的业务数据进行加密并生成签名sign). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param resposeData 响应的数据对象
	 * @param publicKey 商户公钥
	 * @param privateKey 普惠私钥
	 * @return 加密和签名后的响应数据组装成json后转换成的字符串
	 * @throws Exception 
	 * @since JDK 1.8
	 * 2018年2月27日 上午10:03:01
	 */
	public String encryptSignDealwith(ResposeData resposeData,String publicKey,String privateKey) throws Exception{
		String encodeType = ConfigureEncryptAndDecrypt.CHAR_ENCODING;		
		String responseKey = AES.getRandom(16);
		Logger.println("对响应参数加密的密码，responseKey="+responseKey);
		Logger.println("加密前的业务参数："+resposeData.getData());
		//使用AES加密算法采用密码responseKey对请求业务参数加密
		resposeData.setData(AES.encryptToBase64(resposeData.getData(),responseKey));
		
		Logger.println("加密后的业务参数："+resposeData.getData());
		//对responseKey根据RSA非对称加密算法使用商户公钥进行加密
		responseKey = RSA.encrypt(responseKey,publicKey);
		Logger.println("对请求参数加密的密码使用商户公钥merchantPublickey='"+publicKey+"',根据RSA非对称加密算法进行加密后的responseKey='"+responseKey+"'.");
		resposeData.setResponseKey(responseKey);
		resposeData.setSign(privateKey);
		String returnStr = resposeData.toString();
		Logger.println("**********************json格式的响应参数resposeData在URLEncoder.encode编码前:"+returnStr);
		String returnStrEncode = URLEncoder.encode(returnStr,encodeType);
		Logger.println("json格式的响应参数resposeData经过URLEncoder.encode后："+returnStrEncode);
		return returnStrEncode;
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
	private boolean checkParams(String merchantCode,String data,String requestKey,String sign,String publicKey){
		boolean signResult = false;
		try{
			//拼接被签名的字符串
			String signedStr = "data=" + data + "&merchantCode=" + merchantCode + "&requestKey="+requestKey;
			//验证签名是否通过
			Logger.println("被验证的字符串signedStr="+signedStr+"\n,sign="+sign+"\n,publicKey="+publicKey);
			signResult = RSA.checkSign(signedStr, sign, publicKey);
		}catch(Exception e){
			e.printStackTrace();
			Logger.println(e.getMessage());
		}
		return signResult;
	}
}
