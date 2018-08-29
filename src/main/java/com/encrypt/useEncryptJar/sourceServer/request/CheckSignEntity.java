/** 
 * Project Name:rsaSignAesEncrypt 
 * File Name:CheckSignEntity.java 
 * Package Name:request 
 * Date:2018年2月26日下午7:39:07 
 */ 
package com.encrypt.useEncryptJar.sourceServer.request;

/** 
* <p>Title:CheckSignEntity </p>
* <p>Description: </p>
* @author xn042142 付品欣
* @date 2018年2月26日 下午7:39:07 
*/
public class CheckSignEntity {

	/**
	 * 对响应数据进行验证签名的结果，true:验证通过，false:验证不通过，数据在传输时被修改
	 */
	private boolean checkSignStatus;
	
	/**
	 * 解密后的业务数据
	 */
	private String data;

	/**
	 * 构造函数
	 * Creates a new instance of CheckSignEntity. 
	 * 
	 * @param checkSignStatus 验证签名的结果
	 * @param data 解密后的业务数据
	 */
	public CheckSignEntity(boolean checkSignStatus, String data) {
		super();
		this.checkSignStatus = checkSignStatus;
		this.data = data;
	}

	/**
	 * isCheckSignStatus:(判断响应参数是否验证签名通过). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @return boolean类型的判断结果值，true:验证通过，false:验证不通过，数据在传输时被修改
	 * @since JDK 1.8
	 * 2018年2月26日 下午7:43:58
	 */
	public boolean isCheckSignStatus() {
		return checkSignStatus;
	}


	/**
	 * getData:(获取解密后的业务数据). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @return 解密后的业务数据
	 * @since JDK 1.8
	 * 2018年2月26日 下午7:43:00
	 */
	public String getData() {
		return data;
	}

	
	
}
