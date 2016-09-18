package me.huachao.conf;

import me.huachao.service.crypt.AesException;
import me.huachao.service.crypt.WXBizMsgCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by huachao on 9/18/16.
 */

@Configuration
public class WXBizMsgCryptConfiguration {

    @Value("${wechat.token}")
    private String token;

    @Value("${wechat.encodingAesKey}")
    private String encodingAesKey;

    @Value("${wechat.appId}")
    private String appId;

    @Bean
    public WXBizMsgCrypt wxBizMsgCrypt() throws AesException {
        return new WXBizMsgCrypt(token, encodingAesKey, appId);
    }
}
