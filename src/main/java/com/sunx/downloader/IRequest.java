package com.sunx.downloader;

public interface IRequest {
	/**
	 * 设置下载方式
	 * @param type
	 */
	public IRequest addMethodType(String type);
	/**
	 * 向请求头中添加数据
	 * @param key
	 * @param value
	 */
	public IRequest addHeader(String key, String value);
	/**
	 * 向请求头中添加cookie信息
	 * @param key
	 * @param cookie
	 * @return
	 */
	public IRequest addCookie(String key, String cookie);
	/**
	 * 添加data
	 * @param key
	 * @param value
	 * @return
	 */
	public IRequest addPostData(String key, String value);
}