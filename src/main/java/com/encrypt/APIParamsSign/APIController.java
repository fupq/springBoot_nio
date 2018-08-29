/** 
 * Project Name:springboot-jsp 
 * File Name:APIController.java 
 * Package Name:com.encrypt.APIParamsSign 
 * Date:2018年2月9日上午9:49:30 
 */ 
package com.encrypt.APIParamsSign;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.encrypt.AES.AES;
import com.encrypt.RSA.GoYouLoanEncryptConfig;

import common.webCommon.QueryString;
import net.sf.json.JSONObject;

/** 
* <p>Title:APIController </p>
* <p>Description: 使用TreeMap对接口请假参数排序，请用RSA非对称算法签名</p>
* @author xn042142 付品欣
* @date 2018年2月9日 上午9:49:30 
*/
@RestController //表示修饰该Controller所有的方法返回JSON格式,直接可以编写Restful接口
public class APIController {

	private static Logger logger = Logger.getLogger(APIController.class);
	
	@Autowired
	private APIServer apiServer;
	
	/**
	 * credit:授信申请接口
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * http://127.0.0.1:8081/creditEncode?merchantCode=GOLoanM001&data=kkdkdkd&sign=98ujydje=&encodeType=UTF-8
	 * @author xn042142 付品欣
	 * @param merchantCode 商户号,由小牛普惠分配
	 * @param data 对含有签名的基本业务数据JSON串加密后形成的密文，每个接口的请求业务参数请见具体的接口说明
	 * @param sign 签名，对data、merchantCode做RSA签名，请求方需要先申请公钥
	 * @param encodeType 对url进行URLEncoder.encode()编码是输入的编码类型参数String enc的值
	 * @return 
	 * @throws Exception 
	 * @since JDK 1.8
	 * 2018年2月11日 上午10:34:42
	 */
	@RequestMapping("/creditEncode")
	public String creditEncode(String data,String merchantCode,String requestKey,String sign) throws Exception{
		String encodeType = "utf-8";
		logger.info("**********************merchantCode="+merchantCode+",data="+data+",requestKey="+requestKey+",sign="+sign);
		/*merchantCode = URLDecoder.decode(merchantCode, encodeType);
		data = URLDecoder.decode(data, encodeType);
		sign = URLDecoder.decode(sign, encodeType);
		requestKey = URLDecoder.decode(requestKey, encodeType);
		//若对requestKey进行了RES加密，则需要进行解密
		logger.info("使用URLDecoder.decode()解码后：merchantCode="+merchantCode+",data="+data+",requestKey="+requestKey+",sign="+sign);*/
		String signResult="";
		String merchantPublickey = GoYouLoanEncryptConfig.getValue("neoPuHui.merchantPublickey");
		//对访问进行签名校验
		boolean checkResult = apiServer.checkParams(merchantCode,data,requestKey,sign,merchantPublickey);
		if(false == checkResult){
			signResult = "请求参数签名验证失败！";
			logger.info(signResult);
			return URLEncoder.encode(signResult, encodeType);
		}else{
			signResult = "请求参数签名验证通过！输入参数如下：";
			logger.info("请求参数签名验证通过！输入参数如下：");
			logger.info("**********************输入参数解密前data:"+data);
			//对requestKey使用普惠私钥采用RSA算法解密
			logger.info("**********************解密前的requestKey:"+data);
			String puHuiPrivateKey = GoYouLoanEncryptConfig.getValue("neoPuHui.puHuiPrivateKey");
			requestKey = apiServer.rsaDecrypt(requestKey, puHuiPrivateKey);
			logger.info("**********************使用普惠私钥采用RSA算法解密后的requestKey:"+data);
			//对请求参数使用AES算法解密
			//String puHuiPrivateKey = GoYouLoanEncryptConfig.getValue("neoPuHui.puHuiPrivateKey");
			String aesPassword = requestKey;
			logger.info("aes解密密码："+aesPassword);
			data = apiServer.aesDecrypt(data, aesPassword);//rsaDecrypt(data, puHuiPrivateKey);
			logger.info("**********************输入参数解密后data:"+data);
		}
		JSONObject inputJson = JSONObject.fromObject(data);
		logger.info("**********************inputJson.toString():"+inputJson.toString());
		/*Iterator<?> iterator = inputJson.keys();
		String key = null;
		String value = null;
		while(iterator.hasNext()){
        	key = (String) iterator.next();
        	value = inputJson.getString(key);
        	logger.info(key + ":" + value);
        }*/
		MyResposeData resposeData = new MyResposeData();
		//resposeData.setData(inputJson.toString());
		resposeData.setErrorCode("000000");
		resposeData.setErrorMsg("处理成功！");
		
		String responseKey = AES.getRandom(16);
		logger.info("对响应参数加密的密码，responseKey="+responseKey);
		logger.info("加密前的业务参数："+inputJson.toString());
		//使用AES加密算法采用密码responseKey对请求业务参数加密
		resposeData.setData(apiServer.aesEncrypt(inputJson.toString(),responseKey));
		
		logger.info("加密后的业务参数："+resposeData.getData());
		//对responseKey根据RSA非对称加密算法使用商户公钥进行加密
		//String merchantPublickey = GoYouLoanEncryptConfig.getValue("neoPuHui.merchantPublickey"); 
		responseKey = apiServer.RESEncrypt(responseKey,merchantPublickey);
		logger.info("对请求参数加密的密码使用商户公钥merchantPublickey='"+merchantPublickey+"',根据RSA非对称加密算法进行加密后的responseKey='"+responseKey+"'.");
		resposeData.setResponseKey(responseKey);
		resposeData.setSign();
		logger.info("**********************处理结果返回数据resposeData:"+resposeData.toString());
		//创建QueryString类，并对一个对属性进行编码
		QueryString queryString = new QueryString("data", resposeData.getData().toString(),encodeType);
		queryString.add("errorCode", resposeData.getErrorCode(), encodeType);
		queryString.add("errorMsg", resposeData.getErrorMsg(), encodeType);
		queryString.add("responseKey", resposeData.getResponseKey(), encodeType);
		queryString.add("sign", resposeData.getSign(), encodeType);
		logger.info(queryString.getQuery());
		logger.info(URLEncoder.encode(resposeData.toString(), encodeType));
		return URLEncoder.encode(resposeData.toString(), encodeType);
	}
	
	/**
	 * credit:授信申请接口
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * http://127.0.0.1:8081/credit?merchantCode=GOLoanM001&data=kkdkdkd&sign=98ujydje=
	 * @author xn042142 付品欣
	 * @param merchantCode 商户号,由小牛普惠分配
	 * @param data 对含有签名的基本业务数据JSON串加密后形成的密文，每个接口的请求业务参数请见具体的接口说明
	 * @param sign 签名，对data、merchantCode做RSA签名，请求方需要先申请公钥
	 * @return 
	 * @since JDK 1.8
	 * 2018年2月11日 上午10:34:42
	 */
	/*@RequestMapping("/credit")
	public String credit(String merchantCode,String data,String sign){
		logger.info("merchantCode="+merchantCode+",data="+data+",sign"+sign);
		String signResult="";
		String publicKey = GoYouLoanEncryptConfig.getValue("neoPuHui.merchantPublickey");
		//对访问进行签名校验
		boolean checkResult = apiServer.checkParams(merchantCode,data,sign,publicKey);
		if(false == checkResult){
			logger.info("请求参数签名验证失败！");
			signResult = "请求参数签名验证失败！";
		}else{
			logger.info("请求参数签名验证通过！输入参数如下：");
			signResult = "请求参数签名验证通过！输入参数如下：";
		}
		JSONObject inputJson = JSONObject.fromObject(data);
		Iterator<?> iterator = inputJson.keys();
		String key = null;
		String value = null;
		while(iterator.hasNext()){
        	key = (String) iterator.next();
        	value = inputJson.getString(key);
        	logger.info(key + ":" + value);
        }
		return signResult + new Date();
	}*/
}
