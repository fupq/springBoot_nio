/** 
 * Project Name:springboot-jsp 
 * File Name:InvokeAPIServer.java 
 * Package Name:com.encrypt.invokeAPI 
 * Date:2018年2月9日下午4:10:25 
 */ 
package com.encrypt.invokeAPI;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.encrypt.AES.AES;
import com.encrypt.RSA.GoYouLoanEncryptConfig;
import com.encrypt.RSA.RSA;
//import com.encrypt.invokeAPI.TreeMapOrder;

import net.sf.json.JSONObject;

/** 
* <p>Title:InvokeAPIServer </p>
* <p>Description: 调用API接口前对参数进行处理</p>
* @author xn042142 付品欣
* @date 2018年2月9日 下午4:10:25 
*/
@Service
public class InvokeAPIServer {

	private static Logger logger = Logger.getLogger(InvokeAPIServer.class);
	
	/**
	 * getJson:(授信申请接口所有输入参数的json,key值进行了TreeMap排序). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @return 包含授信申请接口所有输入参数的json
	 * @since JDK 1.8
	 * 2018年2月9日 下午3:07:35
	 */
	public JSONObject getOrderJson(){
		//TreeMapOrder treeMapOrder = new TreeMapOrder();
		TreeMap<String,String> myMap=new TreeMap<String,String>();//(treeMapOrder);
		myMap.put("degree","2");
	    myMap.put("family_member_type","010");
	    myMap.put("family_member_name","张XX");
	    myMap.put("family_member_phone","15139465468");
	    myMap.put("cfca_serial_no","1234567789");
	    myMap.put("company_name","XXXX有限公司");
	    myMap.put("company_province","广东省");
	    myMap.put("company_city","深圳市");
	    myMap.put("company_county","罗湖区");
	    myMap.put("company_address","彩田路1001号9楼401室");
	    myMap.put("br_cell", "");
	    myMap.put("br_name", "");
	    myMap.put("br_linkman_cell", "");
	    myMap.put("br_mail", "");
	    myMap.put("br_bank_id", "");
	    myMap.put("br_qq", "");
	    myMap.put("br_device_type", "");
	    myMap.put("customer_photo","");
	    myMap.put("apply_type","010");
	    myMap.put("gps_longitude","116.403694");
		myMap.put("user_id","13800138000");
	    myMap.put("apply_serial_no","1234567890");
	    myMap.put("customer_name","张三");
	    myMap.put("cert_id","420111198911096733");
	    myMap.put("cert_adress","上海市黄浦区南京东路1号8楼401");
	    myMap.put("gender","M");
	    myMap.put("mobile_phone","15139465468");
	    myMap.put("br_device_id", "");
	    myMap.put("br_tel_biz", "");
	    myMap.put("br_tel_home", "");
	    myMap.put("br_home_addr", "");
	    myMap.put("br_biz_addr", "");
	    myMap.put("br_apply_addr", "");
	    myMap.put("br_oth_addr", "");
	    myMap.put("gps_latitude","39.924896");
	    myMap.put("br_id", "");
	    JSONObject json = new JSONObject();
	    json= JSONObject.fromObject(myMap);
	    return json;
	}
	
	/**
	 * getInputParas:授信申请接口所有输入参数的json
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @return 
	 * @since JDK 1.8
	 * 2018年2月11日 下午2:34:11
	 */
	public JSONObject getInputParas(){
		JSONObject json = new JSONObject();
		Map<String,String> myMap = new HashMap<String,String>();
		myMap.put("degree","2");
	    myMap.put("family_member_type","010");
	    myMap.put("family_member_name","张XX");
	    myMap.put("family_member_phone","15139465468");
	    myMap.put("cfca_serial_no","1234567789");
	    myMap.put("company_name","XXXX有限公司");
	    myMap.put("company_province","广东省");
	    myMap.put("company_city","深圳市");
	    myMap.put("company_county","罗湖区");
	    myMap.put("company_address","彩田路1001号9楼401室");
	    myMap.put("br_cell", "");
	    myMap.put("br_name", "");
	    myMap.put("br_linkman_cell", "");
	    myMap.put("br_mail", "");
	    myMap.put("br_bank_id", "");
	    myMap.put("br_qq", "");
	    myMap.put("br_device_type", "");
	    myMap.put("customer_photo","");
	    myMap.put("apply_type","010");
	    myMap.put("gps_longitude","116.403694");
		myMap.put("user_id","13800138000");
	    myMap.put("apply_serial_no","1234567890");
	    myMap.put("customer_name","张三");
	    myMap.put("cert_id","420111198911096733");
	    myMap.put("cert_adress","上海市黄浦区南京东路1号8楼401");
	    myMap.put("gender","M");
	    myMap.put("mobile_phone","15139465468");
	    myMap.put("br_device_id", "");
	    myMap.put("br_tel_biz", "");
	    myMap.put("br_tel_home", "");
	    myMap.put("br_home_addr", "");
	    myMap.put("br_biz_addr", "");
	    myMap.put("br_apply_addr", "");
	    myMap.put("br_oth_addr", "");
	    myMap.put("gps_latitude","39.924896");
	    myMap.put("br_id", "");
	    json= JSONObject.fromObject(myMap);
		return json;
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
		//TreeMapOrder treeMapOrder = new TreeMapOrder();
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
	    logger.info("*************排序后："+orderedJson.toString());
	    logger.info("*************排序后："+myOrderedJson.toString());
	    System.out.println("*************排序后："+orderedJson.toString());
	    System.out.println("*************排序后："+myOrderedJson.toString());
		return myOrderedJson.toString();
	}

	/**
	 * generateSign:对访问参数生产签名sign
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param incomeJson 输入参数组成的字符串
	 * @param merchantCode 商户编号
	 * @param privateKey 私钥
	 * @return 生成的签名sign
	 * @since JDK 1.8
	 * 2018年2月11日 下午3:06:37
	 */
	public String generateSign(String data,String merchantCode,String requestKey,String privateKey){
		String sign="";
		//1.对json字符串进行排序:请求参数串 = 请求参数按参数名称的字典顺序(例如：treeMap)从小到大串联排列（数组不参与排序，参数形式为key=value，各参数串联；eg:age=18&name=zhangshan）；
		String signedStr = "data="+data+"&merchantCode="+merchantCode + "&requestKey="+requestKey;//"data="+orderJson(incomeJson)+"&merchantCode="+merchantCode;
		logger.info("被签名的字符串：" + signedStr);
		
		//2.使用RSA算法对请求参数串和密钥进行签名生成sign
		sign = RSA.sign(signedStr, privateKey);
		logger.info("使用私钥：‘" + privateKey + "',对内容：’" + signedStr + "'进行签名后的结果是：‘" + sign + "'.");
		return sign;
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
	public boolean checkParams(String resposeStr){
		boolean checkResult = false;
		logger.info("******返回参数："+resposeStr);
		JSONObject json = JSONObject.fromObject(resposeStr);
		/*JSONObject dataJson = (JSONObject)json.get("data");
		String orderDataJson = orderJson(dataJson).toString();
		json.remove("data");
		json.put("data", orderDataJson);*/
		String sign = json.get("sign").toString();
		json.remove("sign");
		logger.info("返回参数排序前的内容:"+json.toString());
		String orderJsonStr = orderJson(json).toString();
		//String content = orderJson.toString();
		String publicKey = GoYouLoanEncryptConfig.getValue("neoPuHui.puHuiPublicKey");
		logger.info("被验证的内容content:"+orderJsonStr);
		logger.info("签名sign:"+sign);
		logger.info("验证的公钥publicKey:" + publicKey);
		checkResult = RSA.checkSign(orderJsonStr, sign, publicKey);
		return checkResult;
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
	
}
