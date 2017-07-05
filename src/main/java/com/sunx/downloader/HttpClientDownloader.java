package com.sunx.downloader;

import com.sunx.downloader.encoding.CheckEncoding;
import com.sunx.downloader.proxy.Host;
import org.apache.http.*;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EntityUtils;

import java.io.EOFException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpClientDownloader extends AbstractDownloader {
//	设置httpclient请求缓存池
	private final Map<String,CloseableHttpClient> httpClients = new HashMap<String,CloseableHttpClient>();
//	构建httpclient对象器
	private  HttpClientGenerator httpClientGenerator = new HttpClientGenerator();
//	编码检测程序
	private CheckEncoding checkEncoding = new CheckEncoding();
//	常用参数
	private static final String CHARSET_REGEX = "(?:<meta.*)(?:charset=)(?:['\\\"]?)(\\w*-?\\d*)";
	private static final String CHARSET_ZHCN = "zh-cn";
	private static final int STATUS_CODE_ACCEPT = 200;
//	默认超时时间
	private static final int DEFAUTL_TIME_OUT = 6000;

	/**
	 * 通过给定的站点domain获取相应的对这个站点进行操作的下载器对象
	 * 如果站点为空,则重新获取
	 * 如果站点不为空,则冲缓存集合中获取下载器
	 * @param site
	 * @return
	 */
	private CloseableHttpClient getHttpClient(Site site){
		if(site == null){
			return httpClientGenerator.getClient(null);
		}
		String domain = site.getDomain();
		CloseableHttpClient httpClient = httpClients.get(domain);
		if(httpClient == null){
			httpClient = httpClients.get(domain);
			synchronized (this) {
				if(httpClient == null){
					httpClient = httpClientGenerator.getClient(site);
					httpClients.put(domain, httpClient);
				}
			}
		}
		return httpClient;
	}

	/**
	 * 将系统返回的网页封装为一个对象
	 * @param request
	 * @param charset
	 * @param httpResponse
	 * @return
	 */
	public String handlerResponse(Site site,Request request, String charset, CloseableHttpResponse httpResponse){
		String content = getContent(charset,httpResponse);

		//设置cookie
		if(site.isSave()){
			save(site,httpResponse);
			site.setIsSave(false);
		}
		System.out.println(request.getUrl() + " -> " + request.getStatusCode());
		return content;
	}
	/**
	 * 获取网页源码
	 * @param nCharset
	 * @param httpResponse
	 * @return
	 */
	public String getContent(String nCharset, CloseableHttpResponse httpResponse){
		HttpEntity entity = null;
		try {
			entity = httpResponse.getEntity();
			//获得响应流
			InputStream is = entity.getContent();
			
			ByteArrayBuffer buffer = new ByteArrayBuffer(4096);
			byte[] tmp = new byte[4096];
			int count;
			try {
				while ((count = is.read(tmp)) != -1) {
					buffer.append(tmp, 0, count);
				}
			} catch (EOFException e) {
			}
//			使用CheckEncoding来识别编码
			String charset = null;
			if(nCharset != null){
				charset = nCharset;
			}else{
				charset = checkEncoding.getEncoding(is);
				//			如果上一步没有获得字符编码。那直接从网页源码中的meta标签中更具正则匹配
				if(!charset.toLowerCase().contains("utf") && !charset.toLowerCase().contains("gb")){
					charset = getCharsetFromMetaTag(buffer);
					if(charset == null || "".equals(charset.trim())){
						charset = getCharsetFromResponeTitle(entity);
						if(charset == null || "".equals(charset.trim())){
							//强制编码
							charset = "UTF-8";
						}
					}
				}
			}
			return new String(buffer.toByteArray(), charset);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(entity != null){
				try{
					EntityUtils.consume(entity);
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	/**
	 * 从相应头中获取网页编码格式
	 * @param entity
	 * @return
	 */
	private String getCharsetFromResponeTitle(HttpEntity entity){
		String charset = null;
		ContentType contentType = null;
		try {
			contentType = ContentType.getOrDefault(entity);
			Charset charsets = contentType.getCharset();
			if (charsets != null) {
				charset = charsets.toString();
			}
		} catch (Exception e1) {
			if (CHARSET_ZHCN.equals(e1.getMessage())) {
				charset = CHARSET_ZHCN;
			}
		}
		return charset;
	}
	/**
	 * 通meta标签中获取网页编码格式
	 * @param buffer
	 * @return
	 */
	private String getCharsetFromMetaTag(ByteArrayBuffer buffer){
		String charset = null;
//		如果上一步没有获得字符编码。那直接从网页源码中的meta标签中更具正则匹配
		Pattern p = Pattern.compile(CHARSET_REGEX,
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(new String(buffer.toByteArray()));
		boolean result = m.find();
		if (result) {
			if (m.groupCount() == 1) {
				charset = m.group(1);
			} 
		}
		return charset;
	}
	/**
	 * 获取请求方法对象
	 * @param request
	 * @param site
	 * @param headers
	 * @return
	 */
	public HttpUriRequest getHttpUriRequest(Request request, Site site, Map<String,String> headers, String host, int port){
//		构建方法类对象
		RequestBuilder builder = selectRequestMethod(request).setUri(request.getUrl());

//		添加请求头数据
		if(headers != null){
			for(Map.Entry<String,String> headerEntry : headers.entrySet()){
				builder.setHeader(headerEntry.getKey(), headerEntry.getValue());
			}
		}
		int timeout = 0;
		if(site == null || site.getTimeOut() <= 0){
			timeout = DEFAUTL_TIME_OUT;
		}else{
			timeout = site.getTimeOut();
		}
//		设置请求参数
		RequestConfig.Builder configBuilder = RequestConfig.custom()
				.setConnectionRequestTimeout(timeout)
				.setConnectTimeout(timeout)
				.setSocketTimeout(timeout)
				.setRedirectsEnabled(true)
				.setCookieSpec(CookieSpecs.STANDARD);
		if (host != null){
			configBuilder.setProxy(new HttpHost(host,port));
		}

//		给方法构建器绑定请求参数
		builder.setConfig(configBuilder.build());
		return builder.build();
	}
	/**
	 * 构建一个请求方法类型,并填充相应的参数
	 * @param request
	 * @return
	 */
	public RequestBuilder selectRequestMethod(Request request){
		Method method = request.getMethod();
        if (method == null || method == Method.GET) {
            //default get
            return RequestBuilder.get();
        } else if (method == Method.POST) {
            RequestBuilder requestBuilder = RequestBuilder.post();
            if(request.getPostData() != null){
				for(Map.Entry<String,String> entry : request.getPostData().entrySet()){
					requestBuilder.addParameter(new BasicNameValuePair(entry.getKey(),entry.getValue()));
				}
			}
            return requestBuilder;
        }else if(method == Method.JSON){
			RequestBuilder requestBuilder = RequestBuilder.post();
        	//处理post提价json数据的形式
			if(request.getSrt() != null){
				StringEntity entity = new StringEntity(request.getSrt(),ContentType.APPLICATION_JSON);
				requestBuilder.setEntity(entity);
			}
			return requestBuilder;
		}
        throw new IllegalArgumentException("Illegal HTTP Method " + method);
	}

	public void setThread(int threadNum) {
		httpClientGenerator.setPoolSize(threadNum);
	}

	public String downloader(Request request,Site site,boolean flag){
		return downloader(request,site,new Host(null,-1),flag);
	}

	public String downloader(Request request,Site site,Host host,boolean flag){
		//		创建请求头以及编码格式参数
		String charset = null;
		Map<String,String> headers = null;
//		初始化参数
		if(site != null){
			charset = site.getCharset();
			headers = site.getHeader();
		}
//		开始执行操作
		CloseableHttpResponse httpResponse = null;
		int statusCode = 0;
		try {
//			获取请求对象
			HttpUriRequest httpUriRequest = getHttpUriRequest(request, site, headers,host.getHost(),host.getPort());
//			执行请求获取相应状态码
			httpResponse = getHttpClient(site).execute(httpUriRequest);
			statusCode = httpResponse.getStatusLine().getStatusCode();
//			将相应状态码放入到请求对象集合中
			request.setStatusCode(statusCode);
			if(statusCode == STATUS_CODE_ACCEPT || statusCode == 521){
				return handlerResponse(site,request,charset,httpResponse);
			}else if(flag && (statusCode == HttpStatus.SC_MOVED_TEMPORARILY) ||
					(statusCode == HttpStatus.SC_MOVED_PERMANENTLY) ||
					(statusCode == HttpStatus.SC_SEE_OTHER) ||
					(statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)){
				//处理跳转
				Header header = httpResponse.getFirstHeader("location");
				if(header != null){
					String newUrl = header.getValue();
					return downloader(new Request(newUrl),site,host);
				}
				return null;
			}else{
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(Thread.currentThread().getName() + ",下载网页源码失败：" + request.getUrl());
		}finally{
			request.setStatusCode(statusCode);
			try {
				if(httpResponse != null){
					httpResponse.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public String downloader(Request request, Site site, Host host) {
		return downloader(request,site,host,true);
	}

	public boolean isDownloader(CloseableHttpResponse httpResponse){
		boolean flag = true;

		Header[] headers = httpResponse.getAllHeaders();
		for(Header header : headers){
			String key = header.getName();
			String value = header.getValue();

			//如果是响应内容提示
			if("Content-Type".equals(key)){
				if(value.toLowerCase().contains("text/") || value.toLowerCase().contains("application/json")){
					flag = false;
				}
			}
		}
		return flag;
	}

	/**
	 * 保存cookie到站点集合中
	 * @param site
	 * @param response
	 */
	public void save(Site site,CloseableHttpResponse response){
		if(response == null)return;
		if(!response.containsHeader("Set-Cookie"))return;
		Header[] headers = response.getHeaders("Set-Cookie");
		if(headers == null || headers.length <= 0)return;
		//开始遍历cookie,并设置cookie
		for(Header header : headers){
			HeaderElement[] eles = header.getElements();
			if(eles == null || eles.length <= 0)continue;
			for(HeaderElement ele : eles){
				site.addCookies(ele.getName(),ele.getValue());

				System.out.println(ele.getName() + "\t" + ele.getValue());
			}
		}
	}
}
