/** 
 * Project Name:springboot-jsp 
 * File Name:RequestUseJar.java 
 * Package Name:com.encrypt.useEncryptJar 
 * Date:2018年2月26日下午6:46:34 
 */ 
package com.encrypt.useEncryptJar;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.encrypt.RSA.GoYouLoanEncryptConfig;
import com.encrypt.invokeAPI.InvokeAPIServer;
import com.encrypt.useEncryptJar.sourceServer.request.CheckSignEntity;
import com.encrypt.useEncryptJar.sourceServer.request.EncryptSign;
import com.encrypt.useEncryptJar.sourceServer.request.RequestEntity;

import common.webCommon.HttpClientServer;
import common.webCommon.InvokeCommon;
import net.sf.json.JSONObject;
//import request.EncryptSign;
//import request.RequestEntity;
//import request.CheckSignEntity;
/** 
* <p>Title:RequestUseJar </p>
* <p>Description: </p>
* @author xn042142 付品欣
* @date 2018年2月26日 下午6:46:34 
*/
public class RequestUseJar {
	
	private static Logger logger = Logger.getLogger(RequestUseJar.class);

	
	
	/*@Autowired //在注入之前，对象已经实例化，是在这个接口注解的时候实例化的,new只是实例化一个对象，而且new的对象不能调用注入的其他类
	private InvokeCommon invokeCommon;// = new InvokeCommon();
	
	@Autowired
	private HttpClientServer httpClientServer;*/
	
	/**main:(使用encryptSignRequest1.0.jar包对请求参数进行加密和签名). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param args 
	 * @throws Exception 
	 * @since JDK 1.8
	 * 2018年2月26日 下午6:46:34
	 */
	public static void main(String[] args)  {
		logger.info("使用encryptSignRequest1.0.jar包对请求参数进行加密和签名********************************** begin");
		try {
			String merchantCode="GOLoanMerchant001";
			JSONObject incomeJson = getInputParas();
			String data = incomeJson.toString();
			EncryptSign encryptSign = new EncryptSign();
			String publicKey = GoYouLoanEncryptConfig.getValue("neoPuHui.puHuiPublicKey"); 
			String privateKey = GoYouLoanEncryptConfig.getValue("neoPuHui.merchantPrivateKey"); 
			
			RequestEntity requestEntity = encryptSign.encryptSignDealwith(merchantCode,data,publicKey,privateKey);
			logger.info("对原始请求参数进行加密和签名后的结果：\ndata:"+requestEntity.getData()+"\nmerchantCode:"+requestEntity.getMerchantCode()+"\nrequestKey:"+requestEntity.getRequestKey()+"\nsign:"+requestEntity.getSign());
			String requestUrl = "http://127.0.0.1:8081/creditEncode?" + "data="+requestEntity.getData()+"&merchantCode="+requestEntity.getMerchantCode()+"&requestKey="+requestEntity.getRequestKey()+"&sign="+requestEntity.getSign();
			logger.info("*********进行get请求的url:"+requestUrl);
			//使用get方式调用接口
			HttpClientServer httpClientServer = new HttpClientServer();
			String resposeStr = httpClientServer.get(requestUrl);
			logger.info("编码后的返回结果："+resposeStr);
			CheckSignEntity checkSignEntity = encryptSign.toDecryptCheckSign(resposeStr,publicKey,privateKey);
			if(checkSignEntity.isCheckSignStatus() == true){
				logger.info("响应参数验证签名通过，解密后的业务数据是：data="+checkSignEntity.getData());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		logger.info("使用encryptSignRequest1.0.jar包对请求参数进行加密和签名********************************** end");
	}

	



	/**
	 * getInputParas:(模拟授信进件的业务参数). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @return 
	 * @since JDK 1.8
	 * 2018年2月26日 下午6:17:32
	 */
	public static JSONObject getInputParas(){
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
	
}
