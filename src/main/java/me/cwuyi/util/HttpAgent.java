package me.cwuyi.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

public class HttpAgent {

    private static CloseableHttpClient httpClient;

    static {
        httpClient = HttpClients.createDefault();
    }

    public static String getResult(String url) {
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response = null;
        String ret = "";
        try {
            response = httpClient.execute(get);
            ret = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            try {
                httpClient.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static void postData(String url, String message) throws Exception {
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-type", "application/json; charset=utf-8");

        // 消息实体
        StringEntity entity = new StringEntity(message, Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        post.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(post);
        response.close();
    }


}
