package me.huachao.request;

/**
 * Created by huachao on 1/25/16.
 */

import me.huachao.service.AccessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Used to receive messages from user
 */

@RestController
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    private static final String token = "huachao_wechat";
    private static final String aesKey = "Ly2xPGHMoMUfzNCxqIN3Z2NopsnKzGGEwvRo56j0pWT";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String sign(@RequestParam("signature") String signature,
                     @RequestParam("timestamp") String timestamp,
                     @RequestParam("nonce") String nonce,
                     @RequestParam("echostr") String echostr) {
        logger.info(String.format("signature:%s; timestamp:%s; nonce:%s; echostr:%s",
                signature, timestamp, nonce, echostr));
        boolean pass = AccessService.checkSignature(signature, timestamp, nonce, token);
        logger.warn(String.format("接入:%b", pass));
        if (pass)
            return echostr;
        else
            return null;
    }



}
