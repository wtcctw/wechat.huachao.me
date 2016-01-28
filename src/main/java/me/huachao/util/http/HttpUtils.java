package me.huachao.util.http;

import me.huachao.WeChatException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by huachao on 1/28/16.
 */
public class HttpUtils {

    private static final Logger Logger = LoggerFactory.getLogger(HttpUtils.class);

    @Resource
    private CloseableHttpClient httpClient;

    public <T> T getResponse(String url, ResponseHandler<T> responseHandler){
        T responseBody = null;
        try {
            HttpGet httpget = new HttpGet(url);
            responseBody = httpClient.execute(httpget, responseHandler);
        } catch (IOException ex) {
            Logger.warn("Exception, {}", ex);
            throw new WeChatException(WeChatException.ERR_HTTP, "Http Error"+url);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                ;
            }
        }
        return responseBody;
    }
}
