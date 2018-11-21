package com.qf.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author LJX
 * @version 1.0
 * @date 2018/11/21  15:12
 */
public class HttpClientUtil {

    public static String sendJson(String url,String json) throws IOException {
        //构建httpclient对象
        CloseableHttpClient client = HttpClientBuilder.create().build();

        //构建post请求
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "application/json");
        //设置请求体的格式类型
        StringEntity stringEntity = new StringEntity(json, "utf-8");
        //设置请求体的内容
        post.setEntity(stringEntity);
        //发送post请求
        CloseableHttpResponse execute = client.execute(post);
        HttpEntity entity = execute.getEntity();
        String result = EntityUtils.toString(entity);
        return result;
    }

    /*public static void main(String[] args) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        CloseableHttpResponse execute = client.execute(httpGet);
        HttpEntity entity = execute.getEntity();
        String result = EntityUtils.toString(entity);
        System.out.println(result);
    }*/


}
