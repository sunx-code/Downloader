package com.sunx.downloader;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Site {
    //	站点域名
    private String domain = null;
    //	站点是否抓取完成
    private boolean isfinish = false;
    //	用于判断请求是否还在请求中
    private Set<String> cache = new HashSet<String>();
    //	请求队列
    private ConcurrentLinkedQueue<String> reqs = new ConcurrentLinkedQueue<String>();
    //	cookies
    private Map<String,String> cookies;
    //	请求头
    private Map<String,String> header;
    //	站点抓取间隔
    private long sleepTime = 10;
    //	最近一次站点的请求时间
    private long lastExcuteTime = 0;
    //	网站默认使用编码格式
    private String charset = "utf-8";
    //	请求代理
    private String userAgent = null;
    //	是否使用压缩方式
    private boolean useGzip;
    //	时间超时
    private int timeOut = 1000 * 5;
    // 是否获取返回的cookie
    private boolean isSave = false;

    public static Site me(){
        return new Site();
    }

    public synchronized String poll(){
        try {
//			如果请求队列为空,则进行返回null
            if(reqs == null){
                System.err.println("程序异常...");
                System.exit(0);
                return null;
            }
            if(reqs.size() <= 0){
                System.err.println("站点下没有请求数据,站点完成...");
                isfinish = true;
                return null;
            }
//			判断这次请求和上次请求时间间隔是否达到时间限定
            if(System.currentTimeMillis() - lastExcuteTime <= sleepTime){
                System.err.println("请求需要等待...");
                return null;
            }
            lastExcuteTime = System.currentTimeMillis();
            String req = reqs.poll();
            return req;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 移除数据
     * @param req
     */
    public void remove(String req){
        try {
            cache.remove(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 站点缓存中请求数据量
     * @return
     */
    public int size(){
        return this.reqs.size();
    }
    /**
     * 向站点中添加一批请求
     * @param reqs
     * @return
     */
    public Site setReqs(ConcurrentLinkedQueue<String> reqs) {
        this.reqs = reqs;
        return this;
    }
    /**
     * 向站点中添加一个请求
     * @param req
     * @return
     */
    public Site addRequest(String req){
        if(this.reqs == null){
            this.reqs = new ConcurrentLinkedQueue<String>();
        }
        if(this.cache.add(req)){
            this.reqs.offer(req);
        }
        return this;
    }
    /**
     * 获取站点名称
     * @return
     */
    public String getDomain(){
        return this.domain;
    }
    /**
     * 设置站点域名
     * @param domain
     * @return
     */
    public Site setDomain(String domain) {
        this.domain = domain;
        return this;
    }
    /**
     * 判断站点是否已经抓取完成
     * @return
     */
    public boolean isIsfinish() {
        return isfinish;
    }
    /**
     * 设置站点抓取状态
     * @param isfinish
     * @return
     */
    public Site setIsfinish(boolean isfinish) {
        this.isfinish = isfinish;
        return this;
    }
    /**
     * 获取站点的超时时间
     * @return
     */
    public long getSleepTime() {
        return sleepTime;
    }
    /**
     * 设置站点的超时时间
     * @param sleepTime
     * @return
     */
    public Site setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
        return this;
    }
    /**
     * 获取站点最近一次请求发生时间
     * @return
     */
    public long getLastExcuteTime() {
        return lastExcuteTime;
    }
    /**
     * 设置站点最近一次请求发生时间
     * @param lastExcuteTime
     * @return
     */
    public Site setLastExcuteTime(long lastExcuteTime) {
        this.lastExcuteTime = lastExcuteTime;
        return this;
    }
    /**
     * 获取编码格式
     * @return
     */
    public String getCharset() {
        return charset;
    }
    /**
     * 设置编码格式
     * @param charset
     * @return
     */
    public Site setCharset(String charset) {
        this.charset = charset;
        return this;
    }
    /**
     * 获取user-agent
     * @return
     */
    public String getUserAgent() {
        return userAgent;
    }
    /**
     * 设置user-agent
     * @param userAgent
     * @return
     */
    public Site setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public boolean isUseGzip() {
        return useGzip;
    }

    public Site setUseGzip(boolean useGzip) {
        this.useGzip = useGzip;
        return this;
    }
    public Map<String, String> getHeader() {
        return header;
    }

    public Site setHeader(Map<String, String> header) {
        if(this.header == null){
            this.header = new HashMap<String,String>();
        }
        this.header = header;
        return this;
    }
    public Site addHeader(String key, String value){
        if(this.header == null){
            this.header = new HashMap<String,String>();
        }
        this.header.put(key, value);
        return this;
    }
    /**
     * 获取cookie
     * @return
     */
    public Map<String, String> getCookies() {
        return cookies;
    }
    /**
     * 向cookie中添加一条数据
     * @param cookies
     * @return
     */
    public Site setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
        return this;
    }
    /**
     * 向cookie中添加一条数据
     * @param value
     * @param key
     * @return
     */
    public Site addCookies(String key, String value){
        if(this.cookies == null){
            this.cookies = new HashMap<String,String>();
        }
        this.cookies.put(key, value);
        return this;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public Site setTimeOut(int timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    public boolean isSave(){
        return this.isSave;
    }

    public Site setIsSave(boolean flag){
        this.isSave = flag;
        return this;
    }

    /**
     * 返回cookie
     * @param key
     * @return
     */
    public String getCookie(String key){
        if(cookies.containsKey(key)) return cookies.get(key);
        return null;
    }
}