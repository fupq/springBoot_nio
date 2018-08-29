/** 
 * Project Name:springboot-jsp 
 * File Name:ResposeData.java 
 * Package Name:com.encrypt.APIParamsSign 
 * Date:2018年2月14日上午9:27:06 
 */ 
package com.encrypt.APIParamsSign;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import com.encrypt.RSA.GoYouLoanEncryptConfig;
import com.encrypt.RSA.RSA;
//import com.encrypt.invokeAPI.TreeMapOrder;

import net.sf.json.JSONObject;

/** 
* <p>Title:ResposeData </p>
* <p>调用API接口的返回值
* @author xn042142 付品欣
* @date 2018年2月14日 上午9:27:06 
*/
public class MyResposeData {
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
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getResponseKey() {
		return responseKey;
	}

	public void setResponseKey(String responseKey) {
		this.responseKey = responseKey;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getSign() {
		return sign;
	}

	public void setSign() {
		String returnSign=null;
		//返回json格式的返回参数
		JSONObject json = getJson();
		/*JSONObject dataJson = (JSONObject) json.get("data");// JSONObject.fromObject(json);
		System.out.println("**********************dataJson:"+dataJson.toString());
		//对json字符串进行排序:请求参数串 = 请求参数按参数名称的字典顺序(例如：treeMap)从小到大串联排列（数组不参与排序，参数形式为key=value，各参数串联；eg:age=18&name=zhangshan）；
		String orderedData = orderJson(dataJson);
		System.out.println("**********************返回数据排序后orderedData："+orderedData.toString());
		json.remove("data");
		json.put("data", orderedData);*/
		json.remove("sign");
		System.out.println("***********Json中返回的数据Data:" + json.getString("data"));
		String toBeSign = orderJson(json);
		System.out.println("**********被签名的返回字符串："+toBeSign);
		String privateKey = GoYouLoanEncryptConfig.getValue("neoPuHui.puHuiPrivateKey");
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
	
	public String sign(){
		String sign=null;
		/*TreeMap<String, String> treeMap = new TreeMap<String, String>();
		JSONObject json = getJson();
		Iterator<?> iterator = json.keys();
		String key = null;
		String value = null;
		while(iterator.hasNext()){
        	key = (String) iterator.next();
        	value = json.getString(key);
        	if(key != "sign"){
        		treeMap.put(key, value);
        	}
        }*/
		JSONObject json = getJson();
		json.remove("sign");
		String toBeSign = orderJson(json);
		System.out.println(toBeSign);
		String privateKey = GoYouLoanEncryptConfig.getValue("neoPuHui.puHuiPrivateKey");
		sign = RSA.sign(toBeSign, privateKey);
		return sign;
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
	public String orderJson(JSONObject json){
		//定义TreeMap,进行排序
		TreeMapOrder treeMapOrder = new TreeMapOrder();
		TreeMap<String,String> treeMap=new TreeMap<String,String>(treeMapOrder);
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
		//logger.info("treeMap中数据量：" + treeMap.size());
		/*Set<String> keys = treeMap.keySet();
		StringBuffer signedBuffer = new StringBuffer();
	    for (String myKey : keys){
	        //logger.info("将"+myKey + "=" + treeMap.get(myKey)+"拼接到字符串中！");
	        if(signedBuffer.length()>0){
	        	signedBuffer.append("&");
	        }
	        signedBuffer.append(myKey + "=" + treeMap.get(myKey));
	    }*/
		System.out.println("treeMap中data的数据："+treeMap.get("data"));
		JSONObject orderedJson = JSONObject.fromObject(treeMap);  //问题出在这个treempa转换为json后“进行了转移。？？？？？？？？？？？？？？？？？？？？？？？
	    //logger.info(treeMap);
	    //logger.info(signedBuffer.toString());
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
