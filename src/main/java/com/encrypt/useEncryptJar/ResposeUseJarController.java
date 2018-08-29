/** 
 * Project Name:springboot-jsp 
 * File Name:ResposeUseJar.java 
 * Package Name:com.encrypt.useEncryptJar 
 * Date:2018年2月26日下午8:17:21 
 */ 
package com.encrypt.useEncryptJar;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.encrypt.APIParamsSign.MyResposeData;
import com.encrypt.useEncryptJar.sourceServer.request.CheckSignEntity;
import com.encrypt.useEncryptJar.sourceServer.respose.ResposeCheckSign;
import com.encrypt.useEncryptJar.sourceServer.respose.ResposeData;

//import request.CheckSignEntity;
//import respose.ResposeCheckSign;
//import respose.ResposeData;

/** 
* <p>Title:ResposeUseJar </p>
* <p>Description: 测试encryptSignRequest1.0.jar包</p>
* @author xn042142 付品欣
* @date 2018年2月26日 下午8:17:21 
*/
@RestController
public class ResposeUseJarController {

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
	 * http://127.0.0.1:8081/testJarCreditEncode?merchantCode=GOLoanM001&data=kkdkdkd&sign=98ujydje=&encodeType=UTF-8
	 */
	@RequestMapping("/testJarCreditEncode")
	public String creditEncode(String data,String merchantCode,String requestKey,String sign) throws Exception{
		ResposeCheckSign ResposeCheckSign = new ResposeCheckSign();
		
		/*String data="R7wNj9hFzFKqsM1bPPYPYJ5UvmY+q1OTjzcNlE8V6fVxHLn/rANB7NRR6HxIGaxKWjFngxhfmI5O/ghGajnaeT/uN48OROtLBdGgDMqjtvHWj5zORiDqSBZtbUGJFNbnffHSh574LA5p57zu9JIvJdSKztxyhixcAEolfhlPslL2FESqcnlguxUJ9mWyNMnmZEJ2SdteTMSBy5twHLndVyScWOz6UUAtnZX1Gnt3ArConluAmRH6klI7f3B8aZZKZj4pnZ4hvaiIx9b8fKP97JRT+5c19Lety4s3JcWp85GinqY8RQh4lWD7QOQK2o9himJpvrOZnQcpZ+BesIpC3w7/T7TA6qgUEEsOYaDhOYu70HopLdozwkPZ/k4/bQ7BcTruNYKyg1Dp2tFHyqCR9UfMutV0m/P11Sxp9IY2/kSQpGsaC0JTG/F4EmewB9xuYFkMPWtyucippuVn1kNlt23XsbSLQtuxCZ4uTM/FwHtWy+FJPOGKP0OuuvNIXLVpR80ZceLiNlAgyaPF7PFRdc8K7eDmpb62XndQqJVaVGUDszfv+nZ3PX/a1jvD5f301slnZ/r3oc5mo8S2hNzocsc2N5U5t1WqKqQ/I/nVShQaEDDPGPHhFrJ7iV9T90MGPFbmZWd/QCQUZAZtxVlpA8jLNwW8ia/dGQlZ71EB2rz79G49+Tu1YdTSdPAi9QJSuuaEQdE0Vg5DJoXRt+rp9NE5xQV2vhwBP387BPeEaFbFDNNg/UtuKnsu7PUElo2tlmNHokfun1jeZECDF5BQKu/I7RFr7lOndsQ08u3fwTZcP0f+8tqsMphTLBtBScl0Wr21M9F6oLGqxy4L/YfryF+q9WVnz8++qWFPBC+JPfq30pqs9FsJlRgcOEfl7tAmqoH+ghY3OVKQxRa0iqG6tAFLoUvrUWG+5Oy94PStrVMWQ3POtxCH/9yuARhiWsJQR1l1wxz8CzX4gN1MeKOe8nqFFka2d9hnGg74Jf8LfDg3U8y9ZkOcW3qBRwAq/+v33YzYHsIhyV9DJ8af7etGm+F7+X2xR378t+TX2mKGvjdEDn/AuIvC7jvgRd1gHOynzrr0Myl/NErzAM8KywpYoSpnmais3+8JjuZZcpNpyHjpbLyKMbsNr6jg+FaL0WI7";
		String merchantCode="GOLoanMerchant001";
		String requestKey="F30cnhGizsJPomY5m04gtnXxWJgh7WcaPKGGxWpikGx2EDSWSBw6ESSJpWrVMDlKK9dKxPVG/Ekmwa9SJi6JqTfurE7Wf1Kkr8NwnooH5zHAeqwZFEKb0ZF6vGuiZZq9rvupOwSUjyJMR6ugcS5NrEhhGNQtjCEazycJq8ZkFck=";
		String sign="YbOLrF28w4goe5lg+54XqZ0umGZWN5YNal+pEBY6lWJwm8hDCqHUwuf4IFyo6i1Ad8HdPJ24lgvE0RCqeFrFJqAjDwW5Fd/3OSAJgnDNGOHSS/9yg39WZYr+j8BuOcSzDwXvNH6P0FqlvctyONg8ExeC9BJMpd74Kgx6SiaIXL4=";
*/
		
		String privateKey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAITmcrRZ/p7vI7kkTrWonA1SZxG38Y6TVIVUFZBgZARdifu3PdmIMiA1qw85hnqDqluJz2RHYUHBOPrjoTy8ogLftktSBNoiwssg63HEdrdiszvGphpRbPlRrjK20zoPtt1zuE5qotdKyVi81PU0OhKSmG0gs9C1fZpQ6ZPd6gwdAgMBAAECgYAZPzYXMOmAA+oDZ/RT6j4LAdZ2tTz8Wty5n2mhTc5yTdrCqOLlCkyLdeaTM9hqOc0JKrrtT+oX9b7/WnLs+ODF5essKMnIwBoipSSedRWEgjYP3t23Ro96K1ytp1FnxyB1dfVp0eOdcd0FS0aZ8N6/dPizn8juMTQDbIIu3RkZwQJBAPbLE9oFMpLUUBQtCypjchAo4E7L4J4iz1cHqylpOmYOxZgj7FwTAlI1XIQWZHsne7PvwoUo7rYBJwPr9nUebrkCQQCJ26uF29g3AI9hT5QXduH4oW0setBK5naTqEbnnpMKvybmlUBO2d6lX6atNgxQG9XrXPGpJLBbpFQJD6Pr+7aFAkEA5/bCxvaBrY6PYhdgWkw0Zsn04zsv+ZLgbX3QvFCiylByGukQ/Q4E7X4oYiKl+TeIRv1BSWXK0RlOMZp1AWpESQJANp4mlbElN51sMQSjSqyaGLR0GZRK4/Hs9tFLzkZgQXi8Q8zMHrFo6aI82hE4zaBJn6dCQ1461QQFG1Xr/vnKNQJAeR03SnpV7Hc8XbHefV9qAba0aT1wfW3D21jQIObvkg3111q3l52Hei70bWIKPAC8COeP95lQPdTBA75ZDrPP0g==";
		String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDBXmCsvWWTe2yOptt29t+uRqQxYbisUt1qcikv7Uwf0SVNEUqoqMDxAWRxrBphIbnvQIzcLaHI7nORfh6wINuPB4QVWOMGlmT4AzC3XZqRz59iu0pnb/Z8Ms6NzGgGsg5rsuAP7wduMmosX1KB3Ysf+ymoMONK4wpzRtQsXC/WEQIDAQAB";

		CheckSignEntity checkSignEntity = ResposeCheckSign.toDecryptCheckSign(data, merchantCode, requestKey, sign, publicKey, privateKey);
		System.out.println("验证签名的结果："+checkSignEntity.isCheckSignStatus());
		System.out.println("解密后的业务数据data=："+checkSignEntity.getData());
		
		String errorCode = "000000";
		String errorMsg = "处理成功！";
		String resposedata = checkSignEntity.getData();
		ResposeData resposeData = new ResposeData(errorCode, errorMsg, resposedata);
		
		return ResposeCheckSign.encryptSignDealwith(resposeData, publicKey, privateKey);
		
	}
}
