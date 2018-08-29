package com.encrypt;

import static org.junit.Assert.*;
import com.encrypt.AES.AES;
import org.junit.Test;

public class TestAES {

	@Test
	public void test() {
		fail("Not yet implemented");
	}


	/**
	 * Step3：将请求的数据用AES加密，密钥为requestKey，加密结果：LuD5WoRRcHq1tuEWZQHLHxncudOg2i2+eMmzvZghHi0=
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣 
	 * @since JDK 1.8
	 * 2018年2月8日 下午3:36:46
	 */
	@Test
	public void testAESEncrypt(){
		String data = "abcdefghijklmnopqrstuvwxyz";
		String key = "1234567890abcdef";
		String result = AES.encryptToBase64(data, key);
		System.out.println("对数据：’" + data + ",使用密码：‘" + key + "',进行加密后的密文是：'" + result + "'.");
	}
	//对数据：’abcdefghijklmnopqrstuvwxyz,
	//使用密码：‘1234567890abcdef',
	//进行加密后的密文是：'LuD5WoRRcHq1tuEWZQHLHxncudOg2i2+eMmzvZghHi0='.

}
