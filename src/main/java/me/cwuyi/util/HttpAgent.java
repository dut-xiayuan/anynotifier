package me.cwuyi.util;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpAgent {

    public static String getResult(String url) {
        HttpGet get = new HttpGet(url);
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String ret = "";
        try {
            httpClient = HttpClients.createDefault();
            response = httpClient.execute(get);
            ret = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }
}
