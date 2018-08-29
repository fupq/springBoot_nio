/** 
 * Project Name:springboot-jsp 
 * File Name:ResposeData.java 
 * Package Name:com.encrypt.APIParamsSign 
 * Date:2018年2月14日上午9:27:06 
 */ 
package com.encrypt.useEncryptJar.sourceServer.respose;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import com.encrypt.useEncryptJar.sourceServer.respose.common.RSA;

import net.sf.json.JSONObject;

/** 
* <p>Title:ResposeData </p>
* <p>调用API接口的返回值
* @author xn042142 付品欣
* @date 2018年2月14日 上午9:27:06 
*/
public class ResposeData {
	/**
	 * 错误代码,当业务失败或出错时该值不为空
	 */
	String errorCode;
	
	/**
	 * 错误描述,当业务失败或出错时该值不为空
	 */
	String errorMsg;
	
	/**
	 * 密钥密文:根据在线私钥使用 RSA 加密AESKey后生成的密钥密文
	 */
	String responseKey;
	
	/**
	 * 返回信息:对含有签名的基本业务数据JSON串加密后形成的密文
	 */
	String data;
	
	/**
	 * 签名:errorCode、errorMsg、responseKey和data生成的RSA签名
	 */
	String sign;
	
	
	
	/**
	 * 构造函数
	 * Creates a new instance of ResposeData. 
	 * 
	 * @param errorCode 响应错误码
	 * @param errorMsg 响应错误信息
	 * @param data 响应的业务数据
	 */
	public ResposeData(String errorCode, String errorMsg, String data) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.data = data;
	}

	/**
	 * getErrorCode:(获取响应的错误码). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @return 
	 * @since JDK 1.8
	 * 2018年2月27日 上午10:24:08
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * setErrorCode:(设置响应的错误码). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param errorCode 
	 * @since JDK 1.8
	 * 2018年2月27日 上午10:23:40
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * getErrorMsg:(获取响应的错误信息). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @return 
	 * @since JDK 1.8
	 * 2018年2月27日 上午10:23:18
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * setErrorMsg:(设置响应的错误信息). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param errorMsg 
	 * @since JDK 1.8
	 * 2018年2月27日 上午10:22:57
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * getResponseKey:(获取响应的随机密码). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @return 
	 * @since JDK 1.8
	 * 2018年2月27日 上午10:22:40
	 */
	public String getResponseKey() {
		return responseKey;
	}

	/**
	 * setResponseKey:(设置响应的随机密码). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param responseKey 
	 * @since JDK 1.8
	 * 2018年2月27日 上午10:22:23
	 */
	public void setResponseKey(String responseKey) {
		this.responseKey = responseKey;
	}

	/**
	 * getData:(获取响应的业务数据). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @return 
	 * @since JDK 1.8
	 * 2018年2月26日 下午9:49:42
	 */
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String privateKey) {
		String returnSign=null;
		//返回json格式的返回参数
		JSONObject json = getJson();
		json.remove("sign");
		System.out.println("***********Json中返回的数据Data:" + json.getString("data"));
		String toBeSign = orderJson(json);
		System.out.println("**********被签名的返回字符串："+toBeSign);
		returnSign = RSA.sign(toBeSign, privateKey);
		System.out.println("*******返回字符串的sign："+returnSign);
		System.out.println("*******签名的私钥："+privateKey);
		this.sign = returnSign;
	}

	
	
	/**
	 * getJson:返回json格式的返回参数
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @return 
	 * @since JDK 1.8
	 * 2018年2月14日 上午10:06:38
	 */
	public JSONObject getJson(){
		JSONObject returnJson = new JSONObject();
		returnJson.put("sign", sign);
		returnJson.put("responseKey", responseKey);
		returnJson.put("data", data);
		returnJson.put("errorCode", errorCode);
		returnJson.put("errorMsg", errorMsg);
		return returnJson;
	}
	
	/**
	 * 返回字符串格式的返回参数
	 */
	public String toString(){
		return getJson().toString();
	}
	
	/**
	 * sign:(使用RSA算法对errorCode、errorMsg、responseKey和data按照参数名称从小到大进行签名). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param privateKey 私钥
	 * @return 生成的签名
	 * @since JDK 1.8
	 * 2018年2月27日 上午10:24:40
	 */
	public String sign(String privateKey){
		String sign=null;
		JSONObject json = getJson();
		json.remove("sign");
		String toBeSign = orderJson(json);
		System.out.println(toBeSign);
		sign = RSA.sign(toBeSign, privateKey);
		return sign;
	}
	
	/**
	 * OrderJson:对json字符串进行排序:请求参数串 = 请求参数按参数名称的字典顺序(例如：treeMap)从小到大串联排列（数组不参与排序，参数形式为key=value，各参数串联；eg:age=18&name=zhangshan）；
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param json 需要排序的json格式的数据
	 * @return  排序后的字符串
	 * @since JDK 1.8
	 * 2018年2月9日 下午4:47:19
	 */
	public String orderJson(JSONObject json){
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
        	System.out.println("orderJson(JSONObject json),key:'" + key + "',value:'" + value + "'添加到treeMap，安装key的名称的字典顺序从小到大串联排列；");
		}
		//1.2按照要求的顺序拼接字符串:参数形式为key=value，各参数串联；eg:age=18&name=zhangshan）；
		System.out.println("treeMap中data的数据："+treeMap.get("data"));
		JSONObject orderedJson = JSONObject.fromObject(treeMap);  
		JSONObject myJson = new JSONObject();
		System.out.println("json排序后的结果JSONObject.fromObject(treeMap)："+orderedJson.toString());
		Set<String> keys = treeMap.keySet();
		for(String mykey : keys){
			myJson.element(mykey, treeMap.get(mykey));
		}
		System.out.println("json排序后的结果："+myJson.toString());
		return myJson.toString();
	}
}
