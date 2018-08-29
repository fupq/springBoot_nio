/** 
 * Project Name:springboot-jsp 
 * File Name:InvokeCommon.java 
 * Package Name:common.webCommon 
 * Date:2018年2月12日上午11:15:46 
 */
package common.webCommon;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * <p>
 * Title:InvokeCommon
 * </p>
 * <p>
 * Description:调用接口或API的公共方法
 * 
 * @author xn042142 付品欣
 * @date 2018年2月12日 上午11:15:46
 */
@Service
public class InvokeCommon {

	private static Logger logger = Logger.getLogger(InvokeCommon.class);

	/**
	 * doPost:Post方式调用外部接口
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param url
	 * @param map
	 * @param charset
	 * @return 
	 * @since JDK 1.8
	 * 2018年2月12日 上午11:44:47
	 */
	@SuppressWarnings({ "resource" })
	public String doPost(String url, Map<?, ?> map, String charset) {
		HttpPost httpPost = null;
		String result = null;
		try {
			String json = JSON.toJSONString(map, false);
			logger.debug("目标URL->{" + url + "},传输的Json->{" + json + "}");
			HttpClient httpClient = new SSLClient();
			// 设置超时时间及其数据传输时间
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);// 连接时间
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);// 数据传输时间
			httpPost = new HttpPost(url);
			StringEntity entity = new StringEntity(json, "UTF-8");
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				// 返回值不是200，进行错误处理
				if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode()) {
					Map<String, String> errorMap = new HashMap<String, String>();
					errorMap.put("code", "400");
					errorMap.put("msg", response.getStatusLine().toString());
					result = JSON.toJSONString(errorMap, false);
				} else {
					HttpEntity resEntity = response.getEntity();
					if (resEntity != null) {
						result = EntityUtils.toString(resEntity, charset);
					}
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Map<String, String> errorMap = new HashMap<String, String>();
			errorMap.put("code", "400");
			errorMap.put("msg", ex.getMessage());
			result = JSON.toJSONString(errorMap, false);
			logger.debug("发送请求发生异常,URL:{" + url + "},参数:{" + map + "},异常信息:{" + ex.getMessage() + "}");
		} finally {
			if (httpPost != null) {
				httpPost.abort();
			}
		}
		return result;
	}

}
