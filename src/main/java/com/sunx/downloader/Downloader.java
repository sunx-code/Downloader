package com.sunx.downloader;

import com.sunx.downloader.proxy.Host;

import java.util.Map;

public interface Downloader {
    /**
     * 给定请求连接,下载网页源码
     * @param url
     * @return
     */
    public String downloader(String url);

    /**
     * 给定请求连接和请求模式,进行下载网页源码
     * @param url
     * @param method
     * @return
     */
    public String downloader(String url,Method method);

    /**
     * 给定请求连接,请求方式,代理ip和端口
     * @param url
     * @param method
     * @param host
     * @param port
     * @return
     */
    public String downloader(String url,Method method,String host,int port);

    /**
     * 给定连接和请求头,进行下载数据
     * @param url
     * @param header
     * @return
     */
    public String downloader(String url,Map<String,String> header);

    /**
     * 给定连接,请求头,请求方式进行下载数据
     * @param url
     * @param method
     * @param header
     * @return
     */
    public String downloader(String url,Method method,Map<String,String> header);
    /**
     * 给定连接，请求头,代理ip和端口进行下载网页
     * @param url
     * @param header
     * @param host
     * @param port
     * @return
     */
    public String downloader(String url,Map<String,String> header,String host,int port);

    /**
     * 给定请求和站点的封装对象,进行下载数据
     * @param request
     * @param site
     * @return
     */
    public String downloader(Request request,Site site);

    /**
     * 给定请求和站点，以及代理进行下载数据
     * @param request
     * @param site
     * @param host
     * @param port
     * @return
     */
    public String downloader(Request request,Site site,String host,int port);

    /**
     * 绑定下载工具类
     * @param request
     * @param site
     * @param proxy
     * @return
     */
    public String downloader(Request request,Site site,Host proxy);

    /**
     * 绑定下载缓存池中有多少下载器
     * @param threadNum
     */
    public void setThread(int threadNum);
}