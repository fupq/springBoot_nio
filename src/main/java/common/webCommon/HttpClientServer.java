package common.webCommon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;

@Service
public class HttpClientServer {

	private static PoolingHttpClientConnectionManager connectionManager = null;
	private static HttpClientBuilder httpBuilder = null;
	private static RequestConfig requestConfig = null;

	private static int MAXCONNECTION = 50; // 客户端总并行链接最大数

	private static int DEFAULTMAXCONNECTION = 30; // 每个主机的最大并行链接数

	private static String IP = "127.0.0.1";
	private static int PORT = 8081;

	static {
		// 设置http的状态参数
		requestConfig = RequestConfig.custom()
				// .setSocketTimeout(20000)
				.setConnectTimeout(20000).setConnectionRequestTimeout(20000).build();

		HttpHost target = new HttpHost(IP, PORT);
		connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(MAXCONNECTION);// 客户端总并行链接最大数
		connectionManager.setDefaultMaxPerRoute(DEFAULTMAXCONNECTION);// 每个主机的最大并行链接数
		connectionManager.setMaxPerRoute(new HttpRoute(target), 20);
		httpBuilder = HttpClients.custom();
		httpBuilder.setConnectionManager(connectionManager);
	}

	/**
	 * getConnection:获取httpclient TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 * 
	 * @author xn042142 付品欣
	 * @return
	 * @since JDK 1.8 2018年2月12日 下午4:32:23
	 */
	public static CloseableHttpClient getConnection() {
		CloseableHttpClient httpClient = httpBuilder.build();
		return httpClient;
	}

	/**
	 * getRequestMethod:获取method方式的httpUriRequest.<br/>
	 * 
	 * @author xn042142 付品欣
	 * @param map 输入参数的map
	 * @param url 请求url
	 * @param method 请求方式
	 * @return HttpUriRequest
	 * @since JDK 1.8 2018年2月12日 下午4:35:00
	 */
	public static HttpUriRequest getRequestMethod(Map<String, String> map, String url, String method) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Set<Map.Entry<String, String>> entrySet = map.entrySet();
		for (Map.Entry<String, String> e : entrySet) {
			String name = e.getKey();
			String value = e.getValue();
			NameValuePair pair = new BasicNameValuePair(name, value);
			params.add(pair);
		}
		HttpUriRequest reqMethod = null;
		if ("post".equals(method)) {
			reqMethod = RequestBuilder.post().setUri(url)
					.addParameters(params.toArray(new BasicNameValuePair[params.size()])).setConfig(requestConfig)
					.build();
		} else if ("get".equals(method)) {
			reqMethod = RequestBuilder.get().setUri(url)
					.addParameters(params.toArray(new BasicNameValuePair[params.size()])).setConfig(requestConfig)
					.build();
		}
		return reqMethod;
	}

	/**
	 * doRequestPostOrGet:(发起HttpClient的mehtod方式的请求). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param map 输入参数的map
	 * @param url 请求url
	 * @param method 请求方式
	 * @return httpclient进行请求的响应数据
	 * @throws IOException
	 * @throws Exception 
	 * @since JDK 1.8
	 * 2018年2月27日 下午4:54:23
	 */
	public static String doRequestPostOrGet(Map<String, String> map, String url, String method)
			throws IOException, Exception {
		HttpUriRequest httpUriRequest = getRequestMethod(map, url, method);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String resposeString = null;
		try {
			CloseableHttpResponse response = httpclient.execute(httpUriRequest);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				System.out.println("--------------------------------------");
				// 打印响应状态
				System.out.println(response.getStatusLine());
				if (entity != null) {
					// 打印响应内容长度
					System.out.println("Response content length: " + entity.getContentLength());
					// 打印响应内容
					// resposeString = "Response content: " +
					// EntityUtils.toString(entity);
					resposeString = EntityUtils.toString(entity);
					System.out.println(resposeString);
				}
				System.out.println("------------------------------------");
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resposeString;
	}

	/**
	 * get:HttpClient get请求 TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 * 
	 * @author xn042142 付品欣
	 * @param urlStr
	 *            请求的url
	 * @since JDK 1.8 2018年2月12日 下午5:33:07
	 */
	public String get(String urlStr) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String resposeString = null;
		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet(urlStr);
			System.out.println("executing request " + httpget.getURI());
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				System.out.println("--------------------------------------");
				// 打印响应状态
				System.out.println(response.getStatusLine());
				if (entity != null) {
					// 打印响应内容长度
					System.out.println("Response content length: " + entity.getContentLength());
					// 打印响应内容
					// resposeString = "Response content: " +
					// EntityUtils.toString(entity);
					resposeString = EntityUtils.toString(entity);
					System.out.println(resposeString);
				}
				System.out.println("------------------------------------");
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resposeString;
	}

	public String httpClientPostSimple(String urlStr) throws ClientProtocolException, IOException {
		// List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		// formparams.add(new BasicNameValuePair("content", "log4j测试"));
		// HttpEntity reqEntity = new UrlEncodedFormEntity(formparams, "utf-8");
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000)// 一、连接超时：connectionTimeout-->指的是连接一个url的连接等待时间
				.setSocketTimeout(5000)// 二、读取数据超时：SocketTimeout-->指的是连接上一个url，获取response的返回等待时间
				.setConnectionRequestTimeout(5000).build();
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(urlStr);
		// post.setEntity(reqEntity);
		post.setConfig(requestConfig);
		HttpResponse response = client.execute(post);

		String message;
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity resEntity = response.getEntity();
			message = EntityUtils.toString(resEntity, "utf-8");
			System.out.println(message);
		} else {
			message = "请求失败";
			System.out.println(message);
		}
		return message;
	}
}
