package com.encrypt;


import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.encrypt.RSA.GoYouLoanEncryptConfig;
import com.encrypt.RSA.RSA;
import com.encrypt.invokeAPI.InvokeAPIServer;

import net.sf.json.JSONObject;

public class TestRSA {

	@Autowired
	private InvokeAPIServer invokeAPIServer;
	
	@Test
	public void test() {
		//fail("Not yet implemented");
		System.out.println("test!");
	}

	/**
	 * testGenerateKeyPair:使用RSA算法随机生成公钥和私钥
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣 
	 * @since JDK 1.8
	 * 2018年2月8日 上午9:29:27
	 */
	@Test
	public void testGenerateKeyPair(){
		Map<String, String> map;
		try {
			map = RSA.generateKeyPair();
			System.out.println("使用RSA算法生成的公钥是：" + map.get("publicKey"));
			System.out.println("使用RSA算法生成的私钥是：" + map.get("privateKey"));
			System.out.println("使用RSA算法生成的modulus是：" + map.get("modulus"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String publicKey=GoYouLoanEncryptConfig.getValue("neoPuHui.puHuiPublicKey");
        String privateKey=GoYouLoanEncryptConfig.getValue("neoPuHui.merchantPrivateKey");
        System.out.println("从配置文件中读取的公钥是：" + publicKey);
        System.out.println("从配置文件中读取的私钥是：" + privateKey);
  }
	
	
	/**Step4：将requestKey使用在线公钥加密
	 * testRSAEncrypt:测试RSA加密功能encrypt(String source, String publicKey)
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣 
	 * @throws Exception 
	 * @since JDK 1.8
	 * 2018年2月8日 上午9:55:53
	 */
	@Test
	public void testRSAEncrypt() throws Exception{
		//String source = "1234567890abcdef";
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
	    try{
			String source = json.toString();
			System.out.println(source);
			String publicKey = GoYouLoanEncryptConfig.getValue("neoPuHui.puHuiPublicKey");
			String result = RSA.encrypt(source, publicKey);
			System.out.println("字符串：‘" + source + "',被公钥’" + publicKey + "‘，加密后的密文是：’" + result + "'.");
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		//字符串：‘1234567890abcdef',
		//被公钥’MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCE5nK0Wf6e7yO5JE61qJwNUmcRt/GOk1SFVBWQYGQEXYn7tz3ZiDIgNasPOYZ6g6pbic9kR2FBwTj646E8vKIC37ZLUgTaIsLLIOtxxHa3YrM7xqYaUWz5Ua4yttM6D7bdc7hOaqLXSslYvNT1NDoSkphtILPQtX2aUOmT3eoMHQIDAQAB‘，
		//加密后的密文是：’KYW73OBk8fRrFqQV5mCAnkMiPe+FCszr3muqzEgYbMmk0llM6o+ZDcDTa4KOx0Y3w5g3l8q5+2bTD1y5CaYlTci4QR26OkYyDsCbRPTgs4G0pCNJ/56da8oc65z+d2XN+11M3sFZiX7jbZ6AyXGE897FSdcrnwN7DXIDJPDQGgI='.
	}
	
	/**Step11：用RSA私钥将接口返回的response解密，得到返回的aes key明文信息
	 * 用私钥解密请求参数--------------------------------正确的密码才正确？？？？
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @throws Exception 
	 * @since JDK 1.8
	 * 2018年2月7日 下午5:31:34
	 */
	@Test
	public void testRSADecrypt() {
		String decrypt = "";
		String cryptograph = "dq7sbpmmAfLYEbrLDZArWJAj4Xn+lb2QPCSiNHooJyMb38K6NW4R8HxafWkwl9loJAiCGfSedcbWQBvuP35YqFX9wbR6IQ5sN8y2hMgyT87QEDxRMG3HJiW6CA7YFbBe+Pr1rFOmywuCMMVN5MUgpFMmh0/RtSOpoBdZ6RjSRok=";
		String privateKey =GoYouLoanEncryptConfig.getValue("neoPuHui.merchantPrivateKey"); 
		try {
			decrypt = RSA.decrypt(cryptograph, privateKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("被解密的字符串："+ cryptograph +",解密私钥：" + privateKey + "解密后的字符串是：" + decrypt);
		//被解密的字符串：dq7sbpmmAfLYEbrLDZArWJAj4Xn+lb2QPCSiNHooJyMb38K6NW4R8HxafWkwl9loJAiCGfSedcbWQBvuP35YqFX9wbR6IQ5sN8y2hMgyT87QEDxRMG3HJiW6CA7YFbBe+Pr1rFOmywuCMMVN5MUgpFMmh0/RtSOpoBdZ6RjSRok=,解密私钥：MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMFeYKy9ZZN7bI6m23b2365GpDFhuKxS3WpyKS/tTB/RJU0RSqiowPEBZHGsGmEhue9AjNwtocjuc5F+HrAg248HhBVY4waWZPgDMLddmpHPn2K7Smdv9nwyzo3MaAayDmuy4A/vB24yaixfUoHdix/7Kagw40rjCnNG1CxcL9YRAgMBAAECgYBrxVwAKnboYcu5HcoHXcSA7yIn25z/fhelcgb+jTT2LqypbU+8/IC8UdhXemIhbJiifcmEFXKr+Co1FqOn6kgzURxFn5im2KMWitnCEEERAEa+GGWLM0lRXydI+K65S4Dc2cVDksEyW1YhtzZsocPnyMuzWLcBY9HN0V8v5HhseQJBAOOrgdlyM5khcF2vdl/nmxqCqLPBPby7zUpAmevurVWv5diOYhbc4CbRdJuSrYFqy8cu9K2NRaRvJCxbtM/j2s8CQQDZbjFgeW2CG3W3+k1FKns7kavdJlCe4ubOej5IEJjbrJyDt/uVt+J363D0CdCEjHn2BqSfphK8z6N3SW4BK/kfAkBiXESbR0WXkOTU9Ot1f8B48Z4lGwWrNo/41nQphFKKxJXOu6URL5f/7VotpG8ljJhBk73OBUzjP8knCO/TKSPtAkEAkT28mhdDAYBaWHVJHITOIPKj/WxUum4Tg6XA6N69XTCmtI437sEQ9M4/e6T6tzAnYCL74PFM3vdM2KgiZYH8PQJAQvGrb+DGy8rHLt/VE2ufLqDoNoQq/wAOFL3BvsoHh/grhKAF5JimuWKyCOGDKIIdu/g6p9WHZ1tjAQ6O8VFYwQ==解密后的字符串是：61js7LW98tpL91Oi
		
		String encrypt1 = "GSLcAxT1bTcIGuj2KOAal/qkV/cIvCi8w2snOxqQMCuukHB0VrJYJpC4i79mhiSDLfs06N85id+7qANeYiHNRzPuqyTiFinsH5s9bpHblulMBgHpyEsaSGGRqzJ1dMOTut7c+pS/GyU9Hry0cBkRDIlGbByuTfgZhombXpo1kHE=";
				//"KYW73OBk8fRrFqQV5mCAnkMiPe+FCszr3muqzEgYbMmk0llM6o+ZDcDTa4KOx0Y3w5g3l8q5+2bTD1y5CaYlTci4QR26OkYyDsCbRPTgs4G0pCNJ/56da8oc65z+d2XN+11M3sFZiX7jbZ6AyXGE897FSdcrnwN7DXIDJPDQGgI=";
		privateKey =GoYouLoanEncryptConfig.getValue("neoPuHui.puHuiPrivateKey"); 
		try {
			decrypt = RSA.decrypt(encrypt1, privateKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("被解密的字符串："+ encrypt1 +",解密私钥：" + privateKey + "解密后的字符串是：" + decrypt);
		
	}
	
	/**
	 * testRSAEncryptDecrypt:加解密测试
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 结论：一个字符串用RSA的公钥加密再用对应的RSA的私钥解密后和原来的字符串相同。注：公钥和私钥要是一对，否则解密报错。
	 * @author xn042142 付品欣
	 * @throws Exception 
	 * @since JDK 1.8
	 * 2018年2月8日 下午4:56:54
	 */
	@Test
	public void testRSAEncryptDecrypt() throws Exception{
		String source = "1234567890abcdef";
		String onLinePublicKey = GoYouLoanEncryptConfig.getValue("neoPuHui.puHuiPublicKey");
		String coded = RSA.encrypt(source, onLinePublicKey);
		System.out.println("字符串：‘" + source + "',被公钥’" + onLinePublicKey + "‘，加密后的密文是：’" + coded + "'.");
		String onLinePrivateKey = GoYouLoanEncryptConfig.getValue("neoPuHui.puHuiPrivateKey");
		String decode = RSA.decrypt(coded, onLinePrivateKey);
		System.out.println("被解密的字符串："+ coded +",解密私钥：" + onLinePrivateKey + "解密后的字符串是：" + decode);
		if(source != null && decode != null && source.equals(decode)){
			System.out.println("true:源码：’" + source + "',被neoOnline的公钥：‘" + onLinePublicKey + "',加密后是：’" + coded + "',再被neoOnline的私钥：‘" + onLinePrivateKey + "',解密后和源码相同。");
		}else{
			System.out.println("false:源码：’" + source + "',被neoOnline的公钥：‘" + onLinePublicKey + "',加密后是：’" + coded + "',再被neoOnline的私钥：‘" + onLinePrivateKey + "',解密后是：’" + decode + "'");
		}
		
		source = "erfdrewddsseswdsd";
		String merchantPublicKey = GoYouLoanEncryptConfig.getValue("neoPuHui.merchantPublickey");
		coded = RSA.encrypt(source, merchantPublicKey);
		System.out.println("字符串：‘" + source + "',被公钥’" + merchantPublicKey + "‘，加密后的密文是：’" + coded + "'.");
		String merchantPrivateKey = GoYouLoanEncryptConfig.getValue("neoPuHui.merchantPrivateKey");
		decode = RSA.decrypt(coded, merchantPrivateKey);
		System.out.println("被解密的字符串："+ coded +",解密私钥：" + merchantPrivateKey + "解密后的字符串是：" + decode);
		if(source != null && decode != null && source.equals(decode)){
			System.out.println("true:源码：’" + source + "',被neoOnline的公钥：‘" + merchantPublicKey + "',加密后是：’" + coded + "',再被neoOnline的私钥：‘" + merchantPrivateKey + "',解密后和源码相同。");
		}else{
			System.out.println("false:源码：’" + source + "',被neoOnline的公钥：‘" + merchantPublicKey + "',加密后是：’" + coded + "',再被neoOnline的私钥：‘" + merchantPrivateKey + "',解密后是：’" + decode + "'");
		}
	}
	
	/**Step6：根据 RAS商户私有密钥签名业务请求参数值字符串（paramValue），生成基于SHA1的RSA数字签名
	 * testSign:测试RSA签名函数RSA.sign(content, privateKey)
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣 
	 * @since JDK 1.8
	 * 2018年2月8日 下午2:29:32
	 */
	@Test
	public void testSign(){
		String content = "data=LuD5WoRRcHq1tuEWZQHLHxncudOg2i2+eMmzvZghHi0=&merchantCode=NEO20000&requestKey=GSLcAxT1bTcIGuj2KOAal/qkV/cIvCi8w2snOxqQMCuukHB0VrJYJpC4i79mhiSDLfs06N85id+7qANeYiHNRzPuqyTiFinsH5s9bpHblulMBgHpyEsaSGGRqzJ1dMOTut7c+pS/GyU9Hry0cBkRDIlGbByuTfgZhombXpo1kHE=";
		String privateKey = GoYouLoanEncryptConfig.getValue("neoPuHui.merchantPublickey");//("neoOnline.merchantPrivateKey"); 
		String signed = null;
		try{
			signed = RSA.sign(content, privateKey);
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("使用私钥：‘" + privateKey + "',对内容：’" + content + "'进行签名后的结果是：‘" + signed + "'.");
	}
	//使用私钥：‘MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMFeYKy9ZZN7bI6m23b2365GpDFhuKxS3WpyKS/tTB/RJU0RSqiowPEBZHGsGmEhue9AjNwtocjuc5F+HrAg248HhBVY4waWZPgDMLddmpHPn2K7Smdv9nwyzo3MaAayDmuy4A/vB24yaixfUoHdix/7Kagw40rjCnNG1CxcL9YRAgMBAAECgYBrxVwAKnboYcu5HcoHXcSA7yIn25z/fhelcgb+jTT2LqypbU+8/IC8UdhXemIhbJiifcmEFXKr+Co1FqOn6kgzURxFn5im2KMWitnCEEERAEa+GGWLM0lRXydI+K65S4Dc2cVDksEyW1YhtzZsocPnyMuzWLcBY9HN0V8v5HhseQJBAOOrgdlyM5khcF2vdl/nmxqCqLPBPby7zUpAmevurVWv5diOYhbc4CbRdJuSrYFqy8cu9K2NRaRvJCxbtM/j2s8CQQDZbjFgeW2CG3W3+k1FKns7kavdJlCe4ubOej5IEJjbrJyDt/uVt+J363D0CdCEjHn2BqSfphK8z6N3SW4BK/kfAkBiXESbR0WXkOTU9Ot1f8B48Z4lGwWrNo/41nQphFKKxJXOu6URL5f/7VotpG8ljJhBk73OBUzjP8knCO/TKSPtAkEAkT28mhdDAYBaWHVJHITOIPKj/WxUum4Tg6XA6N69XTCmtI437sEQ9M4/e6T6tzAnYCL74PFM3vdM2KgiZYH8PQJAQvGrb+DGy8rHLt/VE2ufLqDoNoQq/wAOFL3BvsoHh/grhKAF5JimuWKyCOGDKIIdu/g6p9WHZ1tjAQ6O8VFYwQ==',
	//对内容：’data=LuD5WoRRcHq1tuEWZQHLHxncudOg2i2+eMmzvZghHi0=&merchantCode=NEO20000&requestKey=GSLcAxT1bTcIGuj2KOAal/qkV/cIvCi8w2snOxqQMCuukHB0VrJYJpC4i79mhiSDLfs06N85id+7qANeYiHNRzPuqyTiFinsH5s9bpHblulMBgHpyEsaSGGRqzJ1dMOTut7c+pS/GyU9Hry0cBkRDIlGbByuTfgZhombXpo1kHE='
	//进行签名后的结果是：‘tBBzqOQ5hB919dY502IO+RzDXK1OhkytChw9HWwYalSjjH/tgih+YC5H4P9crlEvedWU8N5ZHlwZW3gsQIYTdBBCH7fRCHMH2zgyAludERcB8d/gbWHlgqwZDLOk4nk4wR/CsMPOJf8Y4pxbpWaHH+nNDK13vU+Ib/4TTQ2aRRU='.

	/**Step10：用RSA和RSA公钥验证签名是否和sign一样
	 * testCheckSign:验证签名是否通过
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣 
	 * @since JDK 1.8
	 * 2018年2月8日 上午9:28:47
	 */
	@Test
	public void testCheckSign(){
		String content = "data=LuD5WoRRcHq1tuEWZQHLHxncudOg2i2+eMmzvZghHi0=&merchantCode=NEO20000&requestKey=GSLcAxT1bTcIGuj2KOAal/qkV/cIvCi8w2snOxqQMCuukHB0VrJYJpC4i79mhiSDLfs06N85id+7qANeYiHNRzPuqyTiFinsH5s9bpHblulMBgHpyEsaSGGRqzJ1dMOTut7c+pS/GyU9Hry0cBkRDIlGbByuTfgZhombXpo1kHE=";
		String sign = "tBBzqOQ5hB919dY502IO+RzDXK1OhkytChw9HWwYalSjjH/tgih+YC5H4P9crlEvedWU8N5ZHlwZW3gsQIYTdBBCH7fRCHMH2zgyAludERcB8d/gbWHlgqwZDLOk4nk4wR/CsMPOJf8Y4pxbpWaHH+nNDK13vU+Ib/4TTQ2aRRU=";
		String publicKey = GoYouLoanEncryptConfig.getValue("neoPuHui.merchantPublickey");
		boolean signResult = RSA.checkSign(content, sign, publicKey);
		System.out.println("对被签名的字符串：’" + content + "',使用签名：‘" + sign +"'，进行验证的结果是：" + signResult);
	}
	//对被签名的字符串：’OnBT6ogCXaBaWyn7joItbjUBCvj7+6413IfHUUhFis97ItguXimhWdKWXJzMhAvNwnR0EEPlRaFXA+0mpPhq/zgEClE68HMoIozU3nGH8mTkklpZic/M+QmNqmQ1a2wEtBIS3wSiwY1yBEfOtK1JLOOinglz1W88f11GLi3Ev0M=',使用签名：‘ZB63X2rax/TlNcTsQGX77KumeFv9ggeOtGC/zS/Obpbxe3MdWrmMcwwkP90dtPwky82UQQgf0WmdypjjxVEFBVCJWxyAqVzbZkQG3me4z76Z0dPnQBIXY34yXL2kjri1XIQUT2uWinumg8JoVwoibDwVOKv2q1BllcpXY/LvOIM='，进行验证的结果是：false

	/**
	 * testRSASign:字符串进行商户的私钥签名，然后在用商户的公钥验证签名结果是：true;注：签名的私钥和签名验证的公钥必须是一对，否则报错。
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣 
	 * @since JDK 1.8
	 * 2018年2月8日 下午6:01:47
	 */
	@Test
	public void testRSASign(){
		String content = "data=apply_serial_no=1234567890&apply_type=010&br_apply_addr=&br_bank_id=&br_biz_addr=&br_cell=&br_device_id=&br_device_type=&br_home_addr=&br_id=&br_linkman_cell=&br_mail=&br_name=&br_oth_addr=&br_qq=&br_tel_biz=&br_tel_home=&cert_adress=上海市黄浦区南京东路1号8楼401&cert_id=420111198911096733&cfca_serial_no=1234567789&company_address=彩田路1001号9楼401室&company_city=深圳市&company_county=罗湖区&company_name=XXXX有限公司&company_province=广东省&customer_name=张三&customer_photo=&degree=2&family_member_name=张XX&family_member_phone=15139465468&family_member_type=010&gender=M&gps_latitude=39.924896&gps_longitude=116.403694&mobile_phone=15139465468&user_id=13800138000&merchantCode=GOLoanMerchant001";
				//"data=LuD5WoRRcHq1tuEWZQHLHxncudOg2i2+eMmzvZghHi0=&merchantCode=NEO20000&requestKey=GSLcAxT1bTcIGuj2KOAal/qkV/cIvCi8w2snOxqQMCuukHB0VrJYJpC4i79mhiSDLfs06N85id+7qANeYiHNRzPuqyTiFinsH5s9bpHblulMBgHpyEsaSGGRqzJ1dMOTut7c+pS/GyU9Hry0cBkRDIlGbByuTfgZhombXpo1kHE=";
		String privateKey = GoYouLoanEncryptConfig.getValue("neoPuHui.merchantPublickey");//("neoOnline.merchantPrivateKey"); 
		String signed = RSA.sign(content, privateKey);
		System.out.println("使用私钥：‘" + privateKey + "',对内容：’" + content + "'进行签名后的结果是：‘" + signed + "'.");
		
		//String content = "data=LuD5WoRRcHq1tuEWZQHLHxncudOg2i2+eMmzvZghHi0=&merchantCode=NEO20000&requestKey=GSLcAxT1bTcIGuj2KOAal/qkV/cIvCi8w2snOxqQMCuukHB0VrJYJpC4i79mhiSDLfs06N85id+7qANeYiHNRzPuqyTiFinsH5s9bpHblulMBgHpyEsaSGGRqzJ1dMOTut7c+pS/GyU9Hry0cBkRDIlGbByuTfgZhombXpo1kHE=";
		//String sign = "tBBzqOQ5hB919dY502IO+RzDXK1OhkytChw9HWwYalSjjH/tgih+YC5H4P9crlEvedWU8N5ZHlwZW3gsQIYTdBBCH7fRCHMH2zgyAludERcB8d/gbWHlgqwZDLOk4nk4wR/CsMPOJf8Y4pxbpWaHH+nNDK13vU+Ib/4TTQ2aRRU=";
		String publicKey = GoYouLoanEncryptConfig.getValue("neoPuHui.merchantPrivateKey");//("neoOnline.merchantPublickey");
		boolean signResult = RSA.checkSign(content, signed, publicKey);
		System.out.println("对被签名的字符串：’" + content + "',使用签名：‘" + signed +"'，进行验证的结果是：" + signResult);
	}
	
}
/*	//在所有测试方法前执行一次，一般在其中写上整体初始化的代码 
@BeforeClass
 
//在所有测试方法后执行一次，一般在其中写上销毁和释放资源的代码 
@AfterClass
 
//在每个测试方法前执行，一般用来初始化方法（比如我们在测试别的方法时，类中与其他测试方法共享的值已经被改变，为了保证测试结果的有效性，我们会在@Before注解的方法中重置数据） 
@Before
 
//在每个测试方法后执行，在方法执行完成后要做的事情 
@After
 
// 测试方法执行超过1000毫秒后算超时，测试将失败 
@Test(timeout = 1000)
 
// 测试方法期望得到的异常类，如果方法执行没有抛出指定的异常，则测试失败 
@Test(expected = Exception.class)
 
// 执行测试时将忽略掉此方法，如果用于修饰类，则忽略整个类 
@Ignore(“not ready yet”) 
@Test*/