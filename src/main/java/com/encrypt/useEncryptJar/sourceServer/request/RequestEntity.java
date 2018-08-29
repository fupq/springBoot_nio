/** 
 * Project Name:rsaSignAesEncrypt 
 * File Name:RequestEntity.java 
 * Package Name:request 
 * Date:2018年2月26日下午6:23:08 
 */ 
package com.encrypt.useEncryptJar.sourceServer.request;

/** 
* <p>Title:RequestEntity </p>
* <p>Description:请求参数实体类 </p>
* @author xn042142 付品欣
* @date 2018年2月26日 下午6:23:08 
*/
public class RequestEntity {

	/**
	 * 对含有签名的基本业务数据JSON串加密后形成的密文，每个接口的请求业务参数请见具体的接口说明
	 */
	private String data;
	
	/**
	 * 由小牛普惠分配的商户编号
	 */
	private String merchantCode;
	
	/**
	 * 根据商户私钥使用 加密Key后生成的密钥密文
	 */
	private String requestKey;
	
	/**
	 * 对data、requestKey和merchantCode做RSA签名，请求方需要先申请公钥
	 */
	private String sign;

	/**
	 * 构造函数
	 * Creates a new instance of RequestEntity. 
	 * 
	 * @param data 加密后的业务请求数据
	 * @param merchantCode 商户编码
	 * @param requestKey 加密后的key
	 * @param sign 对data,merchantCode,requestKey签名生成的sign
	 */
	public RequestEntity(String data, String merchantCode, String requestKey, String sign) {
		super();
		this.data = data;
		this.merchantCode = merchantCode;
		this.requestKey = requestKey;
		this.sign = sign;
	}

	/**
	 * getData:(获取加密后并经过URLEncoder.encode()编码的请求业务参数). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @return 加密后并经过URLEncoder.encode()编码的请求业务参数
	 * @since JDK 1.8
	 * 2018年2月26日 下午8:27:47
	 */
	public String getData() {
		return data;
	}

	/**
	 * getMerchantCode:(获取经过URLEncoder.encode()编码的商户编码，). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @return 经过URLEncoder.encode()编码的商户编码
	 * @since JDK 1.8
	 * 2018年2月26日 下午8:28:32
	 */
	public String getMerchantCode() {
		return merchantCode;
	}

	/**
	 * getRequestKey:(获取加密后的并经过URLEncoder.encode()编码的随机加密密码key). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @return 加密后的并经过URLEncoder.encode()编码的随机加密密码key
	 * @since JDK 1.8
	 * 2018年2月26日 下午8:30:16
	 */
	public String getRequestKey() {
		return requestKey;
	}

	/**
	 * getSign:(获取对data、requestKey和merchantCode进行签名生成的sign并对其进行URLEncoder.encode()编码). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @return 对data、requestKey和merchantCode进行签名生成的sign并对其进行URLEncoder.encode()编码
	 * @since JDK 1.8
	 * 2018年2月26日 下午8:33:16
	 */
	public String getSign() {
		return sign;
	}
	
	
}
