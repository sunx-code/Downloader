package com.sunx.downloader;

import java.util.HashMap;
import java.util.Map;

public class Request {
    //	请求url
    private String url;
    //	请求方法
    private Method method;
    //	站点名称
    private String domain;
    //	这个请求执行那个解析函数
    private String srciptMethod;
    //	post请求参数
    private Map<String,String> postData;
    //	存储其他临时参数
    private Map<String,Object> data;
    //	请求相应状态码
    private int statusCode;
    //post请求其他的参数
    private String srt;

    public Request(){}

    public Request(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public Request setUrl(String url) {
        this.url = url;
        return this;
    }

    public Method getMethod() {
        return method;
    }

    public Request setMethod(Method method) {
        this.method = method;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }
    public Object getData(String key){
        return this.data.get(key);
    }
    /**
     * 向data缓存集合中插入数据
     * @param key
     * @param value
     * @return
     */
    public Request addData(String key, Object value){
        if(this.data == null){
            this.data = new HashMap<String,Object>();
        }
        this.data.put(key, value);
        return this;
    }
    /**
     * 添加data集合
     * @param data
     * @return
     */
    public Request setData(Map<String, Object> data) {
        this.data = data;
        return this;
    }
    /**
     * 获取站点名称
     * @return
     */
    public String getDomain() {
        return domain;
    }
    /**
     * 设置站点名称
     * @param domain
     * @return
     */
    public Request setDomain(String domain) {
        this.domain = domain;
        return this;
    }
    public int getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
    /**
     * 更新可变参数
     * @param key
     * @param value
     * @return
     */
    public Request update(String key, String value){
        this.url = this.url.replaceAll(key,value);
        return this;
    }

    public String getSrciptMethod() {
        return srciptMethod;
    }

    public Request setSrciptMethod(String srciptMethod) {
        this.srciptMethod = srciptMethod;
        return this;
    }

    public Request setPostData(Map<String,String> postData){
        this.postData = postData;
        return this;
    }
    public Request addPostData(String key, String value){
        if(this.postData == null){
            this.postData = new HashMap<String,String>();
        }
        this.postData.put(key, value);
        return this;
    }

    public Map<String,String> getPostData(){
        return this.postData;
    }

    public String getSrt() {
        return srt;
    }

    public Request setSrt(String srt) {
        this.srt = srt;
        return this;
    }
}
