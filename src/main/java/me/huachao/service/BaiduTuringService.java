package me.huachao.service;

import com.alibaba.fastjson.JSON;
import me.huachao.WeChatException;
import me.huachao.dto.message.BaiduTuringResponse;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by huachao on 9/18/16.
 */

@Service
public class BaiduTuringService {

    private static final Logger logger = LoggerFactory.getLogger(BaiduTuringService.class);

    @Autowired
    private OkHttpClient okHttpClient;

    @Value("${baidu.key}")
    private String key;

    @Value("${baidu.userid}")
    private String userid;

    @Value("${baidu.apikey}")
    private String apikey;

    public String ask(String question) {
        BaiduTuringResponse baiduTuringResponse = null;
        String url = HttpUrl.parse("http://apis.baidu.com/turing/turing/turing")
                .newBuilder()
                .addQueryParameter("key", key)
                .addQueryParameter("info", question)
                .addQueryParameter("userid", userid)
                .build().toString();

        Request request = new Request.Builder()
                .url(url)
                .header("apikey", apikey)
                .build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) throw new WeChatException(1, "baidu turing robot is not responsible ok");
            String body = response.body().string();
            baiduTuringResponse = JSON.parseObject(body, BaiduTuringResponse.class);
        } catch (Exception e) {
            logger.error("baidu turing robot is not responsible ok", e);
            throw new WeChatException(1, "baidu turing robot is not responsible ok. "+e.getMessage());
        } finally {
            response.body().close();
        }

        return baiduTuringResponse!=null? baiduTuringResponse.getShowtext() : "不懂";
    }

}
