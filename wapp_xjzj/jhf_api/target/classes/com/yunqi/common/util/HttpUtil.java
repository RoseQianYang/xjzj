package com.yunqi.common.util;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import com.yunqi.common.ServiceException;

/**
 * 用于 微信支付 HTTP工具类 
 * @author wangsong
 *
 */
public class HttpUtil {
	private static final Logger log = Logger.getLogger(HttpUtil.class);

	/**
	 * 提交HTTP POST方式
	 * @param url  请求地址
	 * @param paramPairs
	 * @return
	 * @throws ServiceException
	 */
	public static String doHttpPostXML(String url, Map<String, String> paramPairs) throws ServiceException {

		// 创建Httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;

		try {
			log.info("提交HTTP POST");

			StringBuffer sb = new StringBuffer();
			sb.append("<xml>");
			if (paramPairs != null) {
				for (Map.Entry<String, String> entry : paramPairs.entrySet()) {
					String name = entry.getKey();
					String text = entry.getValue();

					sb.append("<" + name + ">");
					sb.append(text);
					sb.append("</" + name + ">");
				}
			}
			sb.append("</xml>");
			log.info("HTTP POST body：" + sb.toString());
			// 创建http POST请求
			HttpPost httppost = new HttpPost(url);
			// 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
			httppost.setEntity(new StringEntity(sb.toString(), ContentType.create("text/html", "UTF-8")));
			// 执行请求
			response = httpclient.execute(httppost);
			int statusCode = response.getStatusLine().getStatusCode();
			HttpEntity resEntity = response.getEntity();
			String content = EntityUtils.toString(resEntity, "utf-8");
			EntityUtils.consume(resEntity);

			log.info("HTTP POST响应：" + content);
			// 判断返回状态是否为200
			if (statusCode != 200) {
				log.info("HTTP POST失败：statusCode=" + statusCode);
				throw new ServiceException("HTTP POST失败：statusCode=" + statusCode);
			}

			return content;
		} catch (Exception e) {
			log.error("HTTP POST出错", e);
			throw new ServiceException(e);
		} finally {
			try {
				if (response != null)
					response.close();
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String doHttpPost(
			String url, Map<String, String> paramPairs, 
			String jsonBody, File file, String fileName) throws ServiceException{
		
		if(file != null && StringUtils.isNotBlank(jsonBody)) {
			throw new ServiceException("jsonBody和file不能同时存在");
		}		
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		
		try {
			log.info("提交HTTP POST");
			
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			// 创建参数列表
			if(paramPairs != null) {
				for (Map.Entry<String, String> entry : paramPairs.entrySet()) {
					String name = entry.getKey();
					String text = entry.getValue();
					
					log.info("HTTP POST PARAM: " + name + "=" + text);
					
					formparams.add(new BasicNameValuePair(name, text));
				}
			}
			
			URI uri = new URIBuilder(URI.create(url))
					.setParameters(formparams)
			        .build();
			
			log.info("HTTP POST URL：" + uri.toString());

//			HttpPost httppost = new HttpPost(uri);
			
			HttpPost httppost = new HttpPost(url);
			
			HttpEntity reqEntity = null;
			if(file != null) {
				log.info("HTTP POST FILE BODY: " + file.getAbsolutePath());
				FileBody fileBody = new FileBody(file);
				
				reqEntity = MultipartEntityBuilder.create()
						.addPart(fileName, fileBody).build();
			}

			if(StringUtils.isNotBlank(jsonBody)) {
				log.info("HTTP POST JSON BODY: " + jsonBody);
				reqEntity = new StringEntity(jsonBody,
						ContentType.create("text/html", "UTF-8"));
			}
						
			if(reqEntity != null)	
			// 模拟表单
			httppost.setEntity(reqEntity);
			// 执行http请求
			response = httpclient.execute(httppost);
			int statusCode = response.getStatusLine().getStatusCode();
			HttpEntity resEntity = response.getEntity();
			String content = EntityUtils.toString(resEntity, "utf-8");
			EntityUtils.consume(resEntity);

			log.info("HTTP POST响应：" + content);

			if (statusCode != 200) {
				log.info("HTTP POST失败：statusCode=" + statusCode);
				throw new ServiceException("HTTP POST失败：statusCode=" + statusCode);
			}

			return content;
		} catch (Exception e) {
			log.error("HTTP POST出错", e);
			throw new ServiceException(e);
		} finally {
			try {
				if (response != null)
					response.close();
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 提交HTTP GET方式
	 * @param url 请求地址
	 * @param paramPairs
	 * @return
	 * @throws ServiceException
	 */
	public static String doHttpGet(String url, Map<String, String> paramPairs) throws ServiceException {
		// 创建Httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;

		try {
			log.info("提交HTTP GET");

			List<NameValuePair> formparams = new ArrayList<NameValuePair>();

			if (paramPairs != null) {
				for (Map.Entry<String, String> entry : paramPairs.entrySet()) {
					String name = entry.getKey();
					String text = entry.getValue();

					log.info("HTTP GET PARAM: " + name + "=" + text);

					formparams.add(new BasicNameValuePair(name, text));
				}
			}
			// 创建uri
			URI uri = new URIBuilder(URI.create(url)).setParameters(formparams).build();

			log.info("HTTP GET URL：" + uri.toString());
			// 创建http GET请求
			HttpGet httpget = new HttpGet(uri);
			// 执行请求
			response = httpclient.execute(httpget);
      
			int statusCode = response.getStatusLine().getStatusCode();
			HttpEntity resEntity = response.getEntity();
			String content = EntityUtils.toString(resEntity, "utf-8");
			EntityUtils.consume(resEntity);

			log.info("HTTP GET响应：" + content);
			// 判断返回状态是否为200
			if (statusCode != 200) {
				log.info("HTTP GET失败：statusCode=" + statusCode);
				throw new ServiceException("HTTP GET失败：statusCode=" + statusCode);
			}

			return content;
		} catch (Exception e) {
			log.error("HTTP GET出错", e);
			throw new ServiceException(e);
		} finally {
			try {
				if (response != null)
					response.close();
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static byte[] doHttpGetForBytes(String url, Map<String, String> paramPairs) throws ServiceException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;

		try {
			log.info("提交HTTP GET");

			List<NameValuePair> formparams = new ArrayList<NameValuePair>();

			if (paramPairs != null) {
				for (Map.Entry<String, String> entry : paramPairs.entrySet()) {
					String name = entry.getKey();
					String text = entry.getValue();

					log.info("HTTP GET PARAM: " + name + "=" + text);

					formparams.add(new BasicNameValuePair(name, text));
				}
			}

			URI uri = new URIBuilder(URI.create(url)).setParameters(formparams).build();

			log.info("HTTP GET URL：" + uri.toString());

			HttpGet httpget = new HttpGet(uri);

			response = httpclient.execute(httpget);

			int statusCode = response.getStatusLine().getStatusCode();
			HttpEntity resEntity = response.getEntity();

			byte[] bytes = EntityUtils.toByteArray(resEntity);

			EntityUtils.consume(resEntity);

			log.info("HTTP GET响应：bytes" + bytes.length);

			if (statusCode != 200) {
				log.info("HTTP GET失败：statusCode=" + statusCode);
				throw new ServiceException("HTTP GET失败：statusCode=" + statusCode);
			}
			return bytes;
		} catch (Exception e) {
			log.error("HTTP GET出错", e);
			throw new ServiceException(e);
		} finally {
			try {
				if (response != null)
					response.close();
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String getRequestUrl(HttpServletRequest request) {
		int port = request.getServerPort();
		String portStr = port == 80 ? "" : ":" + port;

		String queryString = request.getQueryString();
		queryString = StringUtils.isBlank(queryString) ? "" : "?" + queryString;

		String requestUrl = request.getScheme() + "://" + request.getServerName() + portStr + request.getContextPath()
				+ request.getServletPath() + queryString;

		return requestUrl;
	}

	public static String removeUrlParams(String url, Set<String> paramNameSet) {
		String[] paramNames = paramNameSet.stream().map(s -> s + "=").collect(Collectors.toList())
				.toArray(new String[0]);

		String anchor = StringUtils.substringAfterLast(url, "#");

		url = StringUtils.substringBeforeLast(url, "#");

		String[] fregments = StringUtils.split(url, "?&");
		List<String> fregmentList = Arrays.stream(fregments).filter(s -> !StringUtils.startsWithAny(s, paramNames))
				.collect(Collectors.toList());

		String newUrl;
		if (fregmentList.size() > 1) {
			newUrl = fregmentList.get(0) + "?" + StringUtils.join(fregmentList.subList(1, fregmentList.size()), "&");
		} else {
			newUrl = fregmentList.get(0);
		}

		if (StringUtils.isNotBlank(anchor)) {
			newUrl += "#" + anchor;
		}

		return newUrl;
	}

}
