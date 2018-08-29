/** 
 * Project Name:rsaSignAesEncrypt 
 * File Name:EncryptSign.java 
 * Package Name:request 
 * Date:2018年2月26日下午4:43:15 
 */ 
package com.encrypt.useEncryptJar.sourceServer.request;


import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import com.encrypt.useEncryptJar.sourceServer.log.Logger;
import com.encrypt.useEncryptJar.sourceServer.request.common.AES;
import com.encrypt.useEncryptJar.sourceServer.request.common.ConfigureEncryptAndDecrypt;
import com.encrypt.useEncryptJar.sourceServer.request.common.RSA;

import net.sf.json.JSONObject;
//import request.common.AES;
//import request.common.ConfigureEncryptAndDecrypt;
//import request.common.RSA;

/** 
* <p>Title:EncryptSign </p>
* <p>Description:对数据进行加密和签名 </p>
* @author xn042142 付品欣
* @date 2018年2月26日 下午4:43:15 
*/
public class EncryptSign {
	

	/**
	 * encryptSignDealwith:(对请求参数进行加密，并对请求数据进行签名生成sign). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param merchantCode 商户编码
	 * @param data 原始的JSONObject格式的请求字符串
	 * @param publicKey 普惠给商户merchantCode分配的公钥
	 * @param privateKey 商户merchantCode自己生成的私钥
	 * @return 请求数据组成的实体类RequestEntity，包含
	 * @throws Exception 
	 * @since JDK 1.8
	 * 2018年2月26日 下午8:20:55
	 */
	public RequestEntity encryptSignDealwith(String merchantCode,String data,String publicKey,String privateKey) throws Exception{
		String requestKey = AES.getRandom(16);
		Logger.println("对请求参数加密的密码，requestKey="+requestKey);
		Logger.println("加密前的业务参数："+data);
		//使用AES加密算法采用密码requestKey对请求业务参数加密
		data = AES.encryptToBase64(data,requestKey);
		Logger.println("加密后的业务参数："+data);
		//对requestKey使用普惠公钥采用RSA算法加密
		requestKey = RSA.encrypt(requestKey,publicKey);
		Logger.println("对请求参数加密的密码使用普惠公钥publicKey='"+publicKey+"',\n采用RSA算法加密后requestKey='"+requestKey+"'.");
		String sign = generateSign(data,merchantCode,requestKey,privateKey);
		String encodeType = ConfigureEncryptAndDecrypt.CHAR_ENCODING;
		RequestEntity requestEntity = new RequestEntity(URLEncoder.encode(data, encodeType),URLEncoder.encode(merchantCode, encodeType),URLEncoder.encode(requestKey, encodeType),URLEncoder.encode(sign, encodeType));
		return requestEntity;
	}
	
	
	/**
	 * toDecryptCheckSign:(对返回的响应业务参数进行签名验证，并解密出响应的业务数据data). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param resposeStr
	 * @param publicKey
	 * @param privateKey
	 * @return
	 * @throws Exception 
	 * @since JDK 1.8
	 * 2018年2月27日 下午1:45:19
	 */
	public CheckSignEntity toDecryptCheckSign(String resposeStr,String publicKey,String privateKey) throws Exception{
		boolean checkStatus = false;
		String responseData = null;
		Logger.println("请求响应的数据（未经过URLDecoder.decode）resposeStr："+resposeStr);
		String encodeType = ConfigureEncryptAndDecrypt.CHAR_ENCODING;
		resposeStr = URLDecoder.decode(resposeStr, encodeType);
		Logger.println("解码后的响应数据（经过URLDecoder.decode）resposeStr："+resposeStr);
		Logger.println("resposeStr.substring(0, 1):"+resposeStr.substring(0, 1));
		if(resposeStr.substring(0, 1).equals("\"")){
			resposeStr = resposeStr.substring(1, resposeStr.length());
			Logger.println("发现resposeStr的第一个字符是\",因此去掉第一个字符得到j："+resposeStr);
		}
		if(resposeStr.substring(resposeStr.length()-1, resposeStr.length()).equals("\"")){
			resposeStr = resposeStr.substring(0, resposeStr.length()-1);
			Logger.println("发现resposeStr的最后一个字符是\",因此去掉最后一个字符得到json格式串："+resposeStr);
		}
		//编码后的返回结果：
		boolean checkResult = checkSign(resposeStr,publicKey);
		if(checkResult){
			Logger.println("返回结果签名验证成功，进件成功！");
			checkStatus = true;
			JSONObject responseJson = JSONObject.fromObject(resposeStr);
			String responseKey = responseJson.get("responseKey").toString();
			responseData = responseJson.get("data").toString();
			Logger.println("**********************响应参数解密前data:"+responseData);
			Logger.println("**********************解密前的responseKey:"+responseKey);
			//对responseKey使用商户私钥采用RSA算法解密
			responseKey = RSA.decrypt(responseKey, privateKey);
			Logger.println("**********************使用商户私钥采用RSA算法解密后的responseKey:"+responseKey);
			//对响应参数使用AES算法解密
			Logger.println("解密后的密码，responseKey="+responseKey);
			responseData = AES.decryptFromBase64(responseData, responseKey);//rsaDecrypt(data, puHuiPrivateKey);
			Logger.println("**********************响应参数解密后data:"+responseData);
			Logger.println("调用进件接口完毕，开始进行业务逻辑处理！");
			
		}else{
			Logger.println("返回结果签名验证失败！");
			checkStatus = false;
		}
		CheckSignEntity checkSignEntity = new CheckSignEntity(checkStatus,responseData);
		return checkSignEntity;
	}
	
	/**
	 * checkParams:校验返回参数的签名参数是否通过
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param resposeStr 返回参数字符串
	 * @return 校验结果，ture:通过，false：不通过
	 * @since JDK 1.8
	 * 2018年2月14日 下午1:48:26
	 */
	private boolean checkSign(String resposeStr,String publicKey){
		boolean checkResult = false;
		Logger.println("******返回参数："+resposeStr);
		JSONObject json = JSONObject.fromObject(resposeStr);
		String sign = json.get("sign").toString();
		json.remove("sign");
		Logger.println("返回参数排序前的内容:"+json.toString());
		//对响应参数errorCode、errorMsg、responseKey、data、sign进行排序（请求参数按参数名称的字典顺序(例如：treeMap)从小到大串联排列）
		String orderJsonStr = orderJson(json).toString();
		Logger.println("被验证的内容content:"+orderJsonStr+"\n签名sign:"+sign+"\n验证的公钥publicKey:" + publicKey);
		checkResult = RSA.checkSign(orderJsonStr, sign, publicKey);
		return checkResult;
	}
	
	
	
	/**
	 * OrderJson:对json字符串进行排序:请求参数串 = 请求参数按参数名称的字典顺序(例如：treeMap)从小到大串联排列（数组不参与排序，参数形式为key=value，各参数串联；eg:age=18&name=zhangshan）；
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param json
	 * @return 
	 * @since JDK 1.8
	 * 2018年2月9日 下午4:47:19
	 */
	private String orderJson(JSONObject json){
		//定义TreeMap,进行排序
		TreeMap<String,String> treeMap=new TreeMap<String,String>();//(treeMapOrder);
		//1.1将json字符串中的参数对（key-value）放入TreeMap中进行排序:请求参数按参数名称的字典顺序(例如：treeMap)从小到大串联排列（数组不参与排序)
		Iterator<?> iterator = json.keys();
		String key = null;
		String value = null;
		while(iterator.hasNext()){
        	key = (String) iterator.next();
        	value = json.getString(key);
        	treeMap.put(key, value);
		}
		JSONObject orderedJson = JSONObject.fromObject(treeMap);
		JSONObject myOrderedJson = new JSONObject();
		Set<String> keys = treeMap.keySet();
	    for (String myKey : keys){
	    	myOrderedJson.element(myKey,treeMap.get(myKey));
	    }
	    Logger.println("*************排序后："+orderedJson.toString()+"\n*************排序后："+myOrderedJson.toString());
		return myOrderedJson.toString();
	}
	
	/**
	 * generateSign:(对请求参数生成签名sign). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param data 请求参数的json格式的字符串
	 * @param merchantCode 商户编号
	 * @param requestKey 根据商户私钥使用 RSA 加密AESKey后生成的密钥密文
	 * @param privateKey （自己的）私钥
	 * @return 生产后的签名sign
	 * @since JDK 1.8
	 * 2018年2月26日 下午5:23:29
	 */
	private String generateSign(String data,String merchantCode,String requestKey,String privateKey){
		String sign="";
		//1.对json字符串进行排序:请求参数串 = 请求参数按参数名称的字典顺序(例如：treeMap)从小到大串联排列（数组不参与排序，参数形式为key=value，各参数串联；eg:age=18&name=zhangshan）；
		String signedStr = "data="+data+"&merchantCode="+merchantCode + "&requestKey="+requestKey;//"data="+orderJson(incomeJson)+"&merchantCode="+merchantCode;
		Logger.println("被签名的字符串：" + signedStr);
		
		//2.使用RSA算法对请求参数串和密钥进行签名生成sign
		sign = RSA.sign(signedStr, privateKey);
		Logger.println("\n使用私钥：‘" + privateKey + "',\n对内容：’" + signedStr + "'\n进行签名后的结果是：‘" + sign + "'.\n");
		return sign;
	}
	
}
