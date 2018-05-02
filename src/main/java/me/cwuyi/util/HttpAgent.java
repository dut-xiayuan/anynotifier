package me.cwuyi.util;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

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
}
