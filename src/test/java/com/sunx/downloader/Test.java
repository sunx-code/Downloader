package com.sunx.downloader;

import java.util.HashMap;
import java.util.Map;

/**
 * 1 测试代码
 *   主要测试下载http数据,下载https网页源码的能力
 */
public class Test {
    private Downloader downloader = new HttpClientDownloader();

    @org.junit.Test
    public void testHttp(){
        String url = "http://jjsb.cet.com.cn/szb_12410_A01.html";

        String html = downloader.downloader(new Request(url).setMethod(Method.GET),new Site().setCharset("UTF-8"));

//        String src = page(url);

        System.out.println(html);
    }

    private String page(String url){
        return downloader.downloader(url);
    }

    @org.junit.Test
    public void test(){
        String url = "http://www.lagou.com/resume/downloadResume?key=aed0842a9b63a8c541da0f7a85da530a&type=3";

        Map<String,String> header = new HashMap<String,String>();
        header.put("Cookie","JSESSIONID=ABAAABAACDBAAIA17FA1E0CEFE6319AD6DFC0CECD9064A9;"); //+
//                "PRE_UTM=; PRE_HOST=www.baidu.com; PRE_SITE=https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3DH5MfMIFakyWPPLUbfz8mSVtafoLDn5YFe01ZDfDyYFe%26wd%3D%26eqid%3D9b68e65d0000051d00000004591fe553; PRE_LAND=https%3A%2F%2Fwww.lagou.com%2F; _putrc=3D2FF6B5F29D6F81; login=true; unick=%E5%AD%99%E6%98%9F; showExpriedIndex=1; showExpriedCompanyHome=1; showExpriedMyPublish=1; hasDeliver=58; index_location_city=%E6%9D%AD%E5%B7%9E; _gid=GA1.2.800925464.1495262577; _ga=GA1.2.264126753.1495189521; LGSID=20170520144232-801b4d28-3d27-11e7-86ac-5254005c3644; LGRID=20170520144258-8fbdccf8-3d27-11e7-86ac-5254005c3644");

        HttpClientDownloader downloader = new HttpClientDownloader();
        String str = downloader.downloader(new Request().setUrl(url),new Site().setHeader(header));
        System.out.println(str);
    }
    @org.junit.Test
    public void testDown(){
        String url = "https://xueqiu.com/statuses/search.json?page=1&q=%E5%92%8C%E7%9D%A6%E5%AE%B6";

        Site site = new Site();
        site.addHeader("Cookie","xq_a_token=445b4b15f59fa37c8bd8133949f910e7297a52ef;");
//        site.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//        site.addHeader("Accept-Encoding","gzip, deflate, sdch");
//        site.addHeader("Accept-Language","zh-CN,zh;q=0.8");
//        site.addHeader("Referer","http://dianzibao.cb.com.cn/html/2017-05/22/node_1.htm");
//        site.addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");


        //https://xueqiu.com/

        String src = downloader.downloader(new Request(url).setMethod(Method.GET),site);
        System.out.println(src);
    }
}