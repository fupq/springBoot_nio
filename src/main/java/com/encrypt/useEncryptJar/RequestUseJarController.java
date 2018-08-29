/** 
 * Project Name:springboot-jsp 
 * File Name:RequestUseJarController.java 
 * Package Name:com.encrypt.useEncryptJar 
 * Date:2018年2月26日下午9:57:51 
 */ 
package com.encrypt.useEncryptJar;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.encrypt.RSA.GoYouLoanEncryptConfig;
import com.encrypt.useEncryptJar.sourceServer.request.CheckSignEntity;
import com.encrypt.useEncryptJar.sourceServer.request.EncryptSign;
import com.encrypt.useEncryptJar.sourceServer.request.RequestEntity;

import common.webCommon.HttpClientServer;
import net.sf.json.JSONObject;
//import request.CheckSignEntity;
//import request.EncryptSign;
//import request.RequestEntity;

/** 
* <p>Title:RequestUseJarController </p>
* <p>Description: </p>
* @author xn042142 付品欣
* @date 2018年2月26日 下午9:57:51 
*/
@RestController
public class RequestUseJarController {

	private static Logger logger = Logger.getLogger(RequestUseJarController.class);
	
	/**
	 * invokeCreditJarGet:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @return 
	 * @since JDK 1.8
	 * 2018年2月26日 下午10:03:26
	 * http://127.0.0.1:8081/invokeCreditJarGet
	 */
	@RequestMapping("/invokeCreditJarGet")
	public String invokeCreditJarGet(){
		logger.info("使用encryptSignRequest1.0.jar包对请求参数进行加密和签名********************************** get请求方式， begin");
		String invokeCredt = null;
		try {
			String merchantCode="GOLoanMerchant001";
			JSONObject incomeJson = getInputParas();
			String data = incomeJson.toString();
			EncryptSign encryptSign = new EncryptSign();
			String publicKey = GoYouLoanEncryptConfig.getValue("neoPuHui.puHuiPublicKey"); 
			String privateKey = GoYouLoanEncryptConfig.getValue("neoPuHui.merchantPrivateKey"); 
			
			RequestEntity requestEntity = encryptSign.encryptSignDealwith(merchantCode,data,publicKey,privateKey);
			logger.info("对原始请求参数进行加密和签名后的结果：\ndata:"+requestEntity.getData()+"\nmerchantCode:"+requestEntity.getMerchantCode()+"\nrequestKey:"+requestEntity.getRequestKey()+"\nsign:"+requestEntity.getSign());
			String requestUrl = "http://127.0.0.1:8081/testJarCreditEncode?" + "data="+requestEntity.getData()+"&merchantCode="+requestEntity.getMerchantCode()+"&requestKey="+requestEntity.getRequestKey()+"&sign="+requestEntity.getSign();
			logger.info("*********进行get请求的url:"+requestUrl);
			//使用get方式调用接口
			HttpClientServer httpClientServer = new HttpClientServer();
			String resposeStr = httpClientServer.get(requestUrl);
			logger.info("编码后的返回结果："+resposeStr);
			CheckSignEntity checkSignEntity = encryptSign.toDecryptCheckSign(resposeStr,publicKey,privateKey);
			if(checkSignEntity.isCheckSignStatus() == true){
				logger.info("响应参数验证签名通过，解密后的业务数据是：data="+checkSignEntity.getData());
				invokeCredt = "响应参数验证签名通过，解密后的业务数据是：data="+checkSignEntity.getData();
			}else{
				invokeCredt = "响应参数验证签名不通过";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		logger.info("使用encryptSignRequest1.0.jar包对请求参数进行加密和签名********************************** get请求方式，end");
		return invokeCredt;
	}

	
	/**
	 * invokeCreditJarPost:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param method
	 * @return 
	 * @since JDK 1.8
	 * 2018年2月27日 下午5:00:16
	 * http://127.0.0.1:8081/invokeCreditJarPost?method="post"
	 */
	@RequestMapping("/invokeCreditJarPost")
	public String invokeCreditJarPost(String method){
		logger.info("使用encryptSignRequest1.0.jar包对请求参数进行加密和签名********************************** Post请求方式， begin");
		String requestMehtod = method.toLowerCase();
		logger.info("将method:"+method+"转换为小写后："+requestMehtod);
		if(method.equals("post") == false){
			if(method.equals("get") == false){
				logger.info("输入参数mehtod"+method+"不合法，请重新输入！");
				return "输入参数mehtod"+method+"不合法，请重新输入！";
			}
		}
		String invokeCredt = null;
		try {
			String merchantCode="GOLoanMerchant001";
			JSONObject incomeJson = getInputParas();
			String data = incomeJson.toString();
			EncryptSign encryptSign = new EncryptSign();
			String publicKey = GoYouLoanEncryptConfig.getValue("neoPuHui.puHuiPublicKey"); 
			String privateKey = GoYouLoanEncryptConfig.getValue("neoPuHui.merchantPrivateKey"); 
			
			RequestEntity requestEntity = encryptSign.encryptSignDealwith(merchantCode,data,publicKey,privateKey);
			logger.info("对原始请求参数进行加密和签名后的结果：\ndata:"+requestEntity.getData()+"\nmerchantCode:"+requestEntity.getMerchantCode()+"\nrequestKey:"+requestEntity.getRequestKey()+"\nsign:"+requestEntity.getSign());
			//********************************************************************* 开始进行请求 ************************************************************************************************************************************
			
			Map<String, String> map = new HashMap<String,String>();
			map.put("data", requestEntity.getData());
			map.put("merchantCode", requestEntity.getMerchantCode());
			map.put("requestKey", requestEntity.getRequestKey());
			map.put("sign", requestEntity.getSign());
			String requestUrl = "http://127.0.0.1:8081/testJarCreditEncode";
			logger.info("*********进行get请求的url:"+requestUrl);
			String resposeStr = HttpClientServer.doRequestPostOrGet(map,requestUrl,requestMehtod);

			//************************************************************ 请求的接口已经返回响应数据resposeStr ***********************************************************************************************************************
			logger.info("编码后的返回结果："+resposeStr);
			CheckSignEntity checkSignEntity = encryptSign.toDecryptCheckSign(resposeStr,publicKey,privateKey);
			if(checkSignEntity.isCheckSignStatus() == true){
				logger.info("响应参数验证签名通过，解密后的业务数据是：data="+checkSignEntity.getData());
				invokeCredt = "响应参数验证签名通过，解密后的业务数据是：data="+checkSignEntity.getData();
			}else{
				invokeCredt = "响应参数验证签名不通过";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		logger.info("使用encryptSignRequest1.0.jar包对请求参数进行加密和签名********************************** Post请求方式，end");
		return invokeCredt;
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
	
}
