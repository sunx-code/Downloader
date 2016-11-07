package com.sunx.downloader;

import com.sunx.downloader.proxy.Host;

import java.util.Map;

public abstract class AbstractDownloader implements Downloader{
	public String downloader(String url) {
		return downloader(url,Method.GET);
	}

	public String downloader(String url, Method method) {
		return downloader(url,method,null,-1);
	}

	public String downloader(String url, Method method, String host, int port) {
		Request request = new Request(url);
		Site site = new Site();
		return downloader(request,site,host,port);
	}

	public String downloader(String url, Map<String, String> header) {
		return downloader(url,Method.GET,header);
	}

	public String downloader(String url, Method method, Map<String, String> header) {
		Request request = new Request(url).setMethod(method);
		Site site = new Site().setHeader(header);
		return downloader(request,site);
	}

	public String downloader(String url, Map<String, String> header, String host, int port) {
		Request request = new Request(url);
		Site site = new Site().setHeader(header);
		return downloader(request,site,host,port);
	}

	public String downloader(Request request, Site site) {
		return downloader(request,site,null,-1);
	}

	public String downloader(Request request, Site site, String host, int port) {
		Host proxy = new Host(host,port);
		return downloader(request,site,proxy);
	}
}