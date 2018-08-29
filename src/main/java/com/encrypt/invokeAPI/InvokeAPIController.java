/** 
 * Project Name:springboot-jsp 
 * File Name:InvokeAPI.java 
 * Package Name:com.encrypt.invokeAPI 
 * Date:2018年2月9日下午4:03:47 
 */ 
package com.encrypt.invokeAPI;

import java.io.IOException;
//import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
//import java.net.URLEncoder;
//import java.util.ArrayList;
import java.util.HashMap;
//import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.encrypt.AES.AES;
import com.encrypt.APIParamsSign.APIController;
//import com.encrypt.APIParamsSign.APIServer;
import com.encrypt.RSA.GoYouLoanEncryptConfig;
//import com.encrypt.RSA.RSA;
import com.google.common.net.UrlEscapers;

import common.webCommon.HttpClientServer;
import common.webCommon.InvokeCommon;
import common.webCommon.QueryString;
import net.sf.json.JSONObject;

/** 
* <p>Title:InvokeAPI </p>
* <p>Description: 调用API接口</p>
* @author xn042142 付品欣
* @date 2018年2月9日 下午4:03:47 
*/
@RestController //表示修饰该Controller所有的方法返回JSON格式,直接可以编写Restful接口
public class InvokeAPIController {

	private static Logger logger = Logger.getLogger(InvokeAPIController.class);
	
	@Autowired
	private APIController apiController;
	
	@Autowired
	private InvokeAPIServer invokeAPIServer;
	
	
	@Autowired //在注入之前，对象已经实例化，是在这个接口注解的时候实例化的,new只是实例化一个对象，而且new的对象不能调用注入的其他类
	private InvokeCommon invokeCommon;// = new InvokeCommon();
	
	@Autowired
	private HttpClientServer httpClientServer;
	
	/**
	 * invokeCredit:调用授信进件的接口，测试签名
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * http://127.0.0.1:8081/invokeCredit
	 * @author xn042142 付品欣
	 * @return 
	 * @since JDK 1.8
	 * 2018年2月11日 上午11:07:54
	 * 注：用私钥签名，公钥验证签名（公钥和私钥必须成对）
	 * 
	 * 
	 */
	/*@RequestMapping("/invokeCredit")
	public String invokeCredit(){
		String invokeCredt = null;
		try{
			String merchantCode="GOLoanMerchant001";
			JSONObject incomeJson = invokeAPIServer.getInputParas();
			String data = incomeJson.toString();
			String privateKey = GoYouLoanEncryptConfig.getValue("neoPuHui.merchantPrivateKey"); 
			String sign = invokeAPIServer.generateSign(incomeJson,merchantCode,privateKey);
			//3.调用授信API：apiController.credit(incomeJson.toString());
			//invokeCredt = apiController.credit(merchantCode,data,sign);
			String urlStr = "http://127.0.0.1:8081/credit?merchantCode=" + merchantCode + "&data=" + data + "&sign=" + sign;
			logger.info(urlStr);
			String resposeStr = httpClientServer.get(urlStr);
			Map map = new HashMap();
			map.put("merchantCode", merchantCode);
			map.put("data", data);
			map.put("sign", sign);
			invokeCommon.doPost(urlStr, map, "UTF-8");
		}catch(Exception e){
			e.printStackTrace();
		}
		return invokeCredt;
	}*/
	
	
	/**
	 * invokeCreditEncode:Get方式调用授信进件的接口，测试签名,对url进行encode
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * http://127.0.0.1:8081/invokeCreditEncodeGet
	 * @author xn042142 付品欣
	 * @return 
	 * @since JDK 1.8
	 * 2018年2月11日 上午11:07:54
	 * 注：用私钥签名，公钥验证签名（公钥和私钥必须成对）
	 * http://127.0.0.1:8081/invokeCreditEncodeGet
	 * 
	 */
	@RequestMapping("/invokeCreditEncodeGet")
	public String invokeCreditEncodeGet(){
		String invokeCredt = null;
		try{
			String merchantCode="GOLoanMerchant001";
			JSONObject incomeJson = invokeAPIServer.getInputParas();
			String data = incomeJson.toString();
			String requestKey = AES.getRandom(16);
			logger.info("对请求参数加密的密码，requestKey="+requestKey);
			logger.info("加密前的业务参数："+data);
			//使用AES加密算法采用密码requestKey对请求业务参数加密
			data = invokeAPIServer.aesEncrypt(data,requestKey);
			logger.info("加密后的业务参数："+data);
			//对requestKey使用普惠公钥采用RSA算法加密
			String puHuiPublicKey = GoYouLoanEncryptConfig.getValue("neoPuHui.puHuiPublicKey"); 
			requestKey = invokeAPIServer.RESEncrypt(requestKey,puHuiPublicKey);
			logger.info("对请求参数加密的密码使用普惠公钥puHuiPublicKey='"+puHuiPublicKey+"',采用RSA算法加密，requestKey='"+requestKey+"'.");
			String merchantPrivateKey = GoYouLoanEncryptConfig.getValue("neoPuHui.merchantPrivateKey"); 
			String sign = invokeAPIServer.generateSign(data,merchantCode,requestKey,merchantPrivateKey);
			String encodeType = GoYouLoanEncryptConfig.getValue("neoPuhui.urlEncodType");
			//String urlStr = "http://127.0.0.1:8081/creditEncode?merchantCode=" + merchantCode + "&data=" + data + "&sign=" + sign + "requestKey=" + requestKey;
			//对业务参数进行URLEncoder.encode编码
			QueryString queryString = new QueryString("data", data, encodeType);
			queryString.add("merchantCode", merchantCode,encodeType);
			queryString.add("requestKey", requestKey, encodeType);
			queryString.add("sign", sign, encodeType);
			String urlEncode = "http://127.0.0.1:8081/creditEncode?" + queryString.getQuery();
			//logger.info("使用URLEncoder.encode（）方法对urlStr:"+urlStr);
			logger.info("进行URLEncoder.encode编码的结果是：" + urlEncode);
			
			//使用get方式调用接口
			String resposeStr = httpClientServer.get(urlEncode);
			logger.info("编码后的返回结果："+resposeStr);
			resposeStr = URLDecoder.decode(resposeStr, encodeType);
			logger.info("解码后的返回结果："+resposeStr);
			resposeStr = resposeStr.substring(1, resposeStr.length()-1);
			logger.info("截取后的json串："+resposeStr);
			//编码后的返回结果：
			boolean checkResult = invokeAPIServer.checkParams(resposeStr);
			if(checkResult){
				invokeCredt = "返回结果签名验证成功，进件成功！";
				logger.info(invokeCredt);
				
				JSONObject responseJson = JSONObject.fromObject(resposeStr);
				String responseKey = responseJson.get("responseKey").toString();
				String responseData = responseJson.get("data").toString();
				logger.info("**********************响应参数解密前data:"+responseData);
				//对responseKey使用商户私钥采用RSA算法解密
				logger.info("**********************解密前的responseKey:"+responseKey);
				//String neoPuHui.merchantPrivateKey = GoYouLoanEncryptConfig.getValue("neoPuHui.puHuiPrivateKey");
				responseKey = invokeAPIServer.rsaDecrypt(responseKey, merchantPrivateKey);
				logger.info("**********************使用商户私钥采用RSA算法解密后的responseKey:"+responseKey);
				//对响应参数使用AES算法解密
				//String puHuiPrivateKey = GoYouLoanEncryptConfig.getValue("neoPuHui.puHuiPrivateKey");
				//String aesPassword = requestKey;
				logger.info("aes解密密码："+responseKey);
				responseData = invokeAPIServer.aesDecrypt(responseData, responseKey);//rsaDecrypt(data, puHuiPrivateKey);
				logger.info("**********************响应参数解密后data:"+responseData);
				logger.info("调用进件接口完毕，开始进行业务逻辑处理！");
				
			}else{
				invokeCredt = "返回结果签名验证失败！";
				logger.info(invokeCredt);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return invokeCredt;
	}
	
	
	/**
	 * invokeCreditEncodePost:Post方式调用授信进件的接口，测试签名,对url进行encode
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @return 
	 * @since JDK 1.8
	 * 2018年2月23日 下午12:00:46
	 * http://127.0.0.1:8081/invokeCreditEncodePost
	 */
	@RequestMapping("/invokeCreditEncodePost")
	public String invokeCreditEncodePost(){
		String resposeStr = null;
		String invokeCredt = null;
		try{
			String merchantCode="GOLoanMerchant001";
			JSONObject incomeJson = invokeAPIServer.getInputParas();
			String data = incomeJson.toString();
			logger.info("请求业务参数Data:"+data);
			String privateKey = GoYouLoanEncryptConfig.getValue("neoPuHui.merchantPrivateKey"); 
			String sign = invokeAPIServer.generateSign(data,merchantCode,"123456789qwertyu",privateKey);
			logger.info("sign:"+sign);
			String encodeType = GoYouLoanEncryptConfig.getValue("neoPuhui.urlEncodType");

			//String urlStr = "http://127.0.0.1:8081/creditEncode?merchantCode=" + merchantCode + "&data=" + data + "&sign=" + sign + "encodeType=" + encodeType;
			
			/*QueryString queryString = new QueryString("merchantCode", merchantCode,encodeType);
			queryString.add("data", data, encodeType);
			queryString.add("sign", sign, encodeType);
			queryString.add("encodeType", encodeType, encodeType);*/
			String urlEncode = "http://127.0.0.1:8081/creditEncode";// + queryString.getQuery();
			/*logger.info("使用URLEncoder.encode（）方法对urlStr:"+urlStr);
			logger.info("进行编码的结果是：" + urlEncode);*/
			Map<String,String> map = new HashMap<String,String>();
			map.put("merchantCode", URLEncoder.encode(merchantCode, encodeType));
			map.put("data", URLEncoder.encode(data, encodeType));
			map.put("sign", URLEncoder.encode(sign, encodeType));
			map.put("encodeType", URLEncoder.encode(encodeType, encodeType));
			logger.info("post调用接口输入的map:"+map.toString());
			//使用Post方式调用接口
			HttpUriRequest HttpUriRequest = HttpClientServer.getRequestMethod(map, urlEncode, "post");
			HttpClient client = HttpClientServer.getConnection(); 
			HttpResponse httpResponse  = client.execute(HttpUriRequest);
			
			if(httpResponse.getStatusLine().getStatusCode() == 200){
				HttpEntity entity = httpResponse.getEntity();  
				resposeStr = EntityUtils.toString(entity, "utf-8");  
	            System.out.println("********返回结果："+resposeStr); 
			}else{
				resposeStr = "请求失败！"; 
	            System.out.println("*********返回结果："+resposeStr); 
			}
			
			logger.info("编码后的返回结果："+resposeStr);
			resposeStr = URLDecoder.decode(resposeStr, encodeType);
			logger.info("解码后的返回结果："+resposeStr);
			resposeStr = resposeStr.substring(1, resposeStr.length()-1);
			logger.info("截取后的json串："+resposeStr);
			//编码后的返回结果：
			boolean checkResult = invokeAPIServer.checkParams(resposeStr);
			if(checkResult){
				invokeCredt = "返回结果签名验证成功，进件成功！";
				logger.info(invokeCredt);
			}else{
				invokeCredt = "返回结果签名验证失败！";
				logger.info(invokeCredt);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return invokeCredt;
	}
	
	/**
	 * invokeCreditEncode:调用授信进件的接口，测试签名,对url进行encode
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * http://127.0.0.1:8081/invokeCredit
	 * @author xn042142 付品欣
	 * @return 
	 * @since JDK 1.8
	 * 2018年2月11日 上午11:07:54
	 * 注：用私钥签名，公钥验证签名（公钥和私钥必须成对）
	 * 
	 * 
	 */
	@RequestMapping("/invokeCreditEscape")
	public String invokeCreditEscape(){
		String invokeCredt = null;
		try{
			String merchantCode="GOLoanMerchant001";
			JSONObject incomeJson = invokeAPIServer.getInputParas();
			String data = incomeJson.toString();
			String privateKey = GoYouLoanEncryptConfig.getValue("neoPuHui.merchantPrivateKey"); 
			String sign = invokeAPIServer.generateSign(data,merchantCode,"123456789qwertyu",privateKey);
			//3.调用授信API：apiController.credit(incomeJson.toString());
			//invokeCredt = apiController.credit(merchantCode,data,sign);
			String urlStr = "http://127.0.0.1:8081/credit?merchantCode=" + merchantCode + "&data=" + data + "&sign=" + sign;
			//对url中的特殊字符转义
        	String urlStrSp = UrlEscapers.urlFragmentEscaper().escape(urlStr);
        	System.out.println("UrlEscapers.urlFragmentEscaper().escape()对"+urlStr+"中特殊字符转义后的结果是："+urlStrSp);
			String resposeStr = httpClientServer.get(urlStr);
			/*Map map = new HashMap();
			map.put("merchantCode", merchantCode);
			map.put("data", data);
			map.put("sign", sign);
			invokeCommon.doPost(urlStr, map, "UTF-8");*/
		}catch(Exception e){
			e.printStackTrace();
		}
		return invokeCredt;
	}
	
	/**
	 * httpClient:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * http://127.0.0.1:8081/httpClientSimplePost
	 * @author xn042142 付品欣
	 * @throws ClientProtocolException
	 * @throws IOException 
	 * @since JDK 1.8
	 * 2018年2月12日 下午2:31:32
	 */
	@RequestMapping("/httpClientSimplePost")
	public String httpClientSimplePost() throws ClientProtocolException, IOException{
		String urlStr = "http://127.0.0.1:8081/useLog4jPrintLog";
		String result = httpClientServer.httpClientPostSimple(urlStr);
		return result;
	}
	
	/**注意debug时socket的超时问题
	 * httpClientPost:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * http://127.0.0.1:8081/httpClientPostTest
	 * @author xn042142 付品欣
	 * @return 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @since JDK 1.8
	 * 2018年2月12日 下午4:44:15
	 */
	@RequestMapping("/httpClientPostTest")
	public String httpClientPostTest() throws ClientProtocolException, IOException{
		String message;
		Map<String, String> map = new HashMap<String, String>();  
//        map.put("account", "");  
//        map.put("password", "");  
    
        HttpClient client = HttpClientServer.getConnection();  
        HttpUriRequest postRequest = HttpClientServer.getRequestMethod(map, "http://127.0.0.1:8081/useLog4jPrintLog", "post");  
        HttpResponse response = client.execute(postRequest);  
    
        if (response.getStatusLine().getStatusCode() == 200) {  
            HttpEntity entity = response.getEntity();  
            message = EntityUtils.toString(entity, "utf-8");  
            System.out.println(message);  
        } else {  
        	message = "请求失败";
            System.out.println(message);  
        }  
		return message;
	}
	
	/**
	 * httpClientGetTest:httpClient get请求测试
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * http://127.0.0.1:8081/httpClientGetTest
	 * @author xn042142 付品欣
	 * @return 
	 * @since JDK 1.8
	 * 2018年2月12日 下午5:39:23
	 */
	@RequestMapping("/httpClientGetTest")
	public String httpClientGetTest(){
		//http://127.0.0.1:8081/credit?merchantCode=GOLoanMerchant001&data={"br_bank_id":"","apply_serial_no":"1234567890","br_name":"","family_member_phone":"15139465468","gender":"M","gps_longitude":"116.403694","cfca_serial_no":"1234567789","cert_id":"420111198911096733","family_member_name":"张XX","company_address":"彩田路1001号9楼401室","company_province":"广东省","mobile_phone":"15139465468","company_city":"深圳市","br_mail":"","br_apply_addr":"","br_device_type":"","br_cell":"","family_member_type":"010","br_oth_addr":"","degree":"2","cert_adress":"上海市黄浦区南京东路1号8楼401","br_tel_home":"","company_county":"罗湖区","br_device_id":"","br_home_addr":"","br_id":"","br_tel_biz":"","br_biz_addr":"","br_linkman_cell":"","customer_photo":"","br_qq":"","user_id":"13800138000","company_name":"XXXX有限公司","gps_latitude":"39.924896","customer_name":"张三","apply_type":"010"}&sign=a4CjnjbLi8pOmDbAQacBicMHJU9jiJ41GsI7uuSHZVR%2BTQE9vWcIUj1Gq14S44FX%2BQkvxFAzPr85FSEN4CIIPvwM1tzLALSTDbsXvLO72iD87mAFTuS6uJZd9%2FpK0MrzZH87JLp%2BNG2fi9Pzf7z9KBYWRBQzEk3%2F9GkLp2Abae8%3D
		String urlStr = "http://www.baidu.com/";
		String resposeStr = httpClientServer.get(urlStr);
		return resposeStr;
	}
}
