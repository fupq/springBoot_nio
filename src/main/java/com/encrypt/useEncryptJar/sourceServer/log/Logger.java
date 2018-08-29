/** 
 * Project Name:rsaSignAesEncrypt 
 * File Name:Logger.java 
 * Package Name:log 
 * Date:2018年2月26日下午5:58:27 
 */ 
package com.encrypt.useEncryptJar.sourceServer.log;

/** 
* <p>Title:Logger </p>
* <p>Description: </p>
* @author xn042142 付品欣
* @date 2018年2月26日 下午5:58:27 
*/
public class Logger {
	
	//打印开关，当isPrint为true打印日志，否则不打印日志
	private static final boolean isPrint = true;

	public static void println(String log){
		if(isPrint){
			System.out.println(log+"\n");
		}
	}
}
