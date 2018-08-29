package common.webCommon;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 
* <p>Title:QueryString </p>
* <p>Description:对url总的属性和属性值使用URLEncoder.encode（）进行编码
* @author xn042142 付品欣
* @date 2018年2月13日 下午4:08:39
 */
public class QueryString {

	private StringBuffer query = new StringBuffer();

	/**
	 * 构造函数：创建QueryString类，并对一个对属性进行编码
	 * Creates a new instance of QueryString. 
	 * 
	 * @param name 第一个属性的名称
	 * @param value 第一个属性值的值
	 * @param encodeType encode的编码类型
	 */
	public QueryString(String name, String value,String encodeType) { 
	   encode(name, value,encodeType);
	}

	/**
	 * add:向QueryString对象中添加属性对进行编码
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param name 属性的名称
	 * @param value 属性值的值
	 * @param encodeType encode的编码类型
	 * @since JDK 1.8
	 * 2018年2月13日 下午4:12:07
	 */
	public synchronized void add(String name, String value,String encodeType) {
	   query.append('&');
	   encode(name, value,encodeType);
	}

	/**
	 * encode:对url中的属性对进行编码
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param name 属性的名称
	 * @param value 属性值的值
	 * @param encodeType encode的编码类型
	 * @since JDK 1.8
	 * 2018年2月13日 下午4:16:06
	 */
	private synchronized void encode(String name, String value,String encodeType) {
	   try {
	     query.append(URLEncoder.encode(name, encodeType));
	     query.append('=');
	     query.append(URLEncoder.encode(value, encodeType));
	} catch (UnsupportedEncodingException ex) {
	      throw new RuntimeException("Broken VM does not support UTF-8");
	}
	}

	/**
	 * getQuery:获取所有属性对编码后的url格式eg:pg=abc&name=xiaowang&password=kdisies 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @return 编码后的url格式
	 * @since JDK 1.8
	 * 2018年2月13日 下午4:16:52
	 */
	public String getQuery() {
	   return query.toString();
	}

	/**
	 * 返回所有属性对编码后的url格式的String类型
	 */
	public String toString() {
	   return getQuery();
	}
}
