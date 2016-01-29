package me.huachao.service;

import me.huachao.util.http.HttpUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by huachao on 1/27/16.
 */
public class SimsimiService {

    private static final String urlTemplate = "http://sandbox.api.simsimi.com/request.p?key=%s&lc=ch&text=%s";

    private String apiKey;
    @Resource
    private HttpUtils httpUtils;

    public String reply(String words) {
        String response = httpUtils.getResponse(String.format(urlTemplate, apiKey, words),
                new ResponseHandler<String>() {
                    public String handleResponse(HttpResponse response) throws IOException {
                        int status = response.getStatusLine().getStatusCode();
                        if (status >= 200 && status < 300) {
                            HttpEntity entity = response.getEntity();
                            return entity != null ? EntityUtils.toString(entity) : null;
                        } else {
                            throw new ClientProtocolException("Unexpected response status: " + status);
                        }
                    }
                });

        JSONObject respJson = new JSONObject(response);
        int sc = respJson.getInt("result");
        if (sc == 100) {
            return respJson.getString("response");
        }
        return "小黄鸡暂时不能陪你聊天啦~~";
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
