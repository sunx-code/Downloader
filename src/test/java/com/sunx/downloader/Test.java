package com.sunx.downloader;

/**
 * 1 测试代码
 *   主要测试下载http数据,下载https网页源码的能力
 */
public class Test {
    private Downloader downloader = new HttpClientDownloader();

    @org.junit.Test
    public void testHttp(){
        String url = "http://mvnrepository.com/artifact/junit/junit/4.12";

        String src = page(url);

        System.out.println(src);
    }

    private String page(String url){
        return downloader.downloader(url);
    }
}