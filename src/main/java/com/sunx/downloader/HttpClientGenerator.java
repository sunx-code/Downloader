package com.sunx.downloader;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.CookieStore;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.util.Map;

public class HttpClientGenerator {
	private PoolingHttpClientConnectionManager manager = null;
	
	/**
	 * 创建对象
	 */
	public HttpClientGenerator(){
//		注册链接请求类型
		Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
//				.register("https", SSLConnectionSocketFactory.getSocketFactory())
				.register("https", SSLConnectionSocketFactory.getSystemSocketFactory())
				.build();
//		给链接池绑定注册类型
		manager = new PoolingHttpClientConnectionManager(reg);
		manager.setDefaultMaxPerRoute(100);
	}
	/**
	 * 设置连接池大小
	 * @param size
	 * @return
	 */
	public HttpClientGenerator setPoolSize(int size){
		manager.setMaxTotal(size);
		return this;
	}
	/**
	 * 给定一个站点获取执行下载的httpclient
	 * @param site
	 * @return
	 */
	public CloseableHttpClient getClient(Site site){
		return generateClient(site);
	}
	/**
	 * 通过给定的站点获取指定的下载器对象
	 * @param site
	 * @return
	 */
	private CloseableHttpClient generateClient(Site site){
		HttpClientBuilder httpClientBuilder = HttpClients.custom()
				.setConnectionManager(manager);
		if(site != null && site.getUserAgent() != null){
			httpClientBuilder.setUserAgent(site.getUserAgent());
		}else{
			httpClientBuilder.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
		}
		if(site == null || site.isUseGzip()){
			httpClientBuilder.addInterceptorFirst(new HttpRequestInterceptor() {
				public void process(HttpRequest request, HttpContext context)
						throws HttpException, IOException {
					if(!request.containsHeader("Accept-Encoding")){
						request.addHeader("Accept-Encoding","gzip");
					}
				}
			});
		}
//		这是socket参数-->保持连接，是否延长
		SocketConfig socketConfig = SocketConfig.custom().setSoKeepAlive(true)
				.setTcpNoDelay(true).build();
		httpClientBuilder.setDefaultSocketConfig(socketConfig);
		generateCookie(httpClientBuilder,site);
		return httpClientBuilder.build();
	}
	/**
	 * 设置默认添加的cookie参数
	 * @param httpClientBuilder
	 * @param site
	 */
	private void generateCookie(HttpClientBuilder httpClientBuilder, Site site){
		CookieStore cookieStore = new BasicCookieStore();
		if(site.getCookies() != null && site.getCookies().size() > 0){
			for(Map.Entry<String,String> entry : site.getCookies().entrySet()){
				BasicClientCookie cookie = new BasicClientCookie(entry.getKey(),entry.getValue());
				cookie.setDomain(site.getDomain());
				cookieStore.addCookie(cookie);
			}
		}
		httpClientBuilder.setDefaultCookieStore(cookieStore);
	}
}