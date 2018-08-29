/** 
 * Project Name:springboot-jsp 
 * File Name:HttpClientBeInvokedController.java 
 * Package Name:common.webCommon.testHttpClient.beInvoked 
 * Date:2018年2月22日下午5:14:52 
 */ 
package common.webCommon.testHttpClient.beInvoked;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/** 
* <p>Title:HttpClientBeInvokedController </p>
* <p>Description: 被调用的接口提供类</p>
* @author xn042142 付品欣
* @date 2018年2月22日 下午5:14:52 
*/
@RestController
public class HttpClientBeInvokedController {

	/**
	 * httpClientTestGet:get方式请求被调用的接口
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param id
	 * @return 
	 * @since JDK 1.8
	 * 2018年2月22日 下午5:18:31
	 * http://127.0.0.1:8081/httpClientTestGet?id=id0001
	 */
	@RequestMapping(value = "/httpClientTestGet", method = RequestMethod.GET)
    @ResponseBody
    public String httpClientTestGet(String id) {

        System.out.println("收到httpclient请求:id=" + id);

        return "id为:" + id + "的名字是李杰";
    }
	
	
	// ******************************************* POST请求方式的接口  *********************************************
	
	/**
	 * httpClientTestPost:POST方式被调用的接口
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param username 用户名称
	 * @param password 用户密码
	 * @return 
	 * @throws UnsupportedEncodingException 
	 * @since JDK 1.8
	 * 2018年2月22日 下午5:17:21
	 * http://127.0.0.1:8081/httpClientTestPost
	 */
	@RequestMapping(value = "/httpClientTestPost", method = RequestMethod.POST)
    @ResponseBody //将Controller的方法返回的对象，通过适当的HttpMessageConverter转换为指定格式后，写入到Response对象的body数据区
    public String httpClientTestPost(String username, String password) throws UnsupportedEncodingException {
		String encodeType = "utf-8";
		username = URLDecoder.decode(username, encodeType);
		password = URLDecoder.decode(password, encodeType);
        System.out.println("name:" + username + " ,password:" + password);

        return URLEncoder.encode("登陆成功!", encodeType);
    }
}

/*@ResponseBody
@Responsebody 注解表示该方法的返回的结果直接写入 HTTP 响应正文（ResponseBody）中，一般在异步获取数据时使用，通常是在使用 @RequestMapping 后，返回值通常解析为跳转路径，加上 @Responsebody 后返回结果不会被解析为跳转路径，而是直接写入HTTP 响应正文中。
作用：
	该注解用于将Controller的方法返回的对象，通过适当的HttpMessageConverter转换为指定格式后，写入到Response对象的body数据区。
使用时机：
	返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；*/