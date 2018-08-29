/** 
 * Project Name:springboot-jsp 
 * File Name:HttpClientInvokeController.java 
 * Package Name:common.webCommon.testHttpClient.invoke 
 * Date:2018年2月22日下午5:14:18 
 */ 
package common.webCommon.testHttpClient.invoke;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import common.webCommon.HttpClientServer;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;


/** 
* <p>Title:HttpClientInvokeController </p>
* <p>Description: </p>
* @author xn042142 付品欣
* @date 2018年2月22日 下午5:14:18 
*/
@RestController
public class HttpClientInvokeController {

	
	/**
	 * testGet: httpClient的get请求方式调用测试
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @throws Exception 
	 * @since JDK 1.8
	 * 2018年2月22日 下午5:22:19
	 * http://127.0.0.1:8081/httpClientGetTest
	 */
	@RequestMapping(value = "/httpClientGetTest", method = RequestMethod.GET)
	public String httpClientGetTest() throws Exception {
		
		String result = null;
        //创建一个httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建URIBuilder
        URIBuilder uri = new URIBuilder("http://127.0.0.1:8081/httpClientTestGet");
        //设置参数
        uri.addParameter("id", "中国001");
        //创建httpGet对象
        HttpGet httpGet = new HttpGet(uri.build());
        //设置请求的报文头部的编码
        httpGet.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
        //设置期望服务端返回的编码
        httpGet.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
        //请求服务
        CloseableHttpResponse response = client.execute(httpGet);
        //获取响应码
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            //获取返回实例entity
            HttpEntity entity = response.getEntity();
            //通过EntityUtils的一个工具方法获取返回内容
            String resStr = EntityUtils.toString(entity, "utf-8");
            result = "请求成功,请求返回内容为: " + resStr;
        } else {
        	result = "请求失败,错误码为: " + statusCode;
        }
      //输出
        System.out.println(result);
        //关闭response和client
        response.close();
        client.close();
        return result;
    }
	
	/**
	 * invokehttpClientPost:测试HttpClientServer.getRequestMethod(map, url, method)的post请求。
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException 
	 * @since JDK 1.8
	 * 2018年2月23日 上午10:27:33
	 * http://127.0.0.1:8081/invokehttpClientPost
	 */
	@RequestMapping("/invokehttpClientPost")
	public String invokehttpClientPost() throws ClientProtocolException, IOException{
		Map<String, String> map = new HashMap<String,String>();
		String encodeType = "utf-8";
		String username = URLEncoder.encode( "黎杰", encodeType);
		String password = URLEncoder.encode("10086", encodeType);
		map.put("username",username);
		map.put("password", password);
		String url = "http://127.0.0.1:8081/httpClientTestPost";
		String method = "post";
		HttpUriRequest reqMethod = HttpClientServer.getRequestMethod(map, url, method);
		HttpClient httpClient = HttpClientServer.getConnection();
		HttpResponse httpResponse = httpClient.execute(reqMethod);
		String message = null;
		if(httpResponse.getStatusLine().getStatusCode() == 200){
			HttpEntity httpEntity = httpResponse.getEntity();
			message = URLDecoder.decode(EntityUtils.toString(httpEntity, "utf-8"),encodeType);;
		}else{
			message = "请求失败！";
		}
		return message;
	}
	
	/**
	 * httpClientPostTest: httpClient的post方式请求测试
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @throws Exception 
	 * @since JDK 1.8
	 * 2018年2月22日 下午5:29:51
	 * http://127.0.0.1:8081/httpClientPostTest
	 */
	@RequestMapping(value = "/httpClientPostTest", method = RequestMethod.GET)
	public String httpClientPostTest() throws Exception {
		String codeType = "utf-8";
		String result = null;
        //创建一个httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建一个post对象   
        HttpPost httpPost = new HttpPost("http://127.0.0.1:8081/httpClientTestPost");
        //创建一个Entity，模拟表单数据
        List<NameValuePair> formList = new ArrayList<>();
        //添加表单数据
        formList.add(new BasicNameValuePair("username", "黎杰"));
        formList.add(new BasicNameValuePair("password", "10086"));
        //包装成一个Entity对象
        StringEntity entity = new UrlEncodedFormEntity(formList, codeType);
        //设置请求的内容
        httpPost.setEntity(entity);
        //设置请求的报文头部的编码
        httpPost.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
        //设置期望服务端返回的编码
        httpPost.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
        //执行post请求
        CloseableHttpResponse response = client.execute(httpPost);
        //获取响应码
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            //获取数据
            String resStr = EntityUtils.toString(response.getEntity());
            result = "请求成功,请求返回内容为: " + URLDecoder.decode(resStr,codeType);
            
        } else {
        	result = "请求失败,错误码为: " + statusCode;
        }
        System.out.println(result);
        //关闭response和client
        response.close();
        client.close();
        return result;
    }
}
