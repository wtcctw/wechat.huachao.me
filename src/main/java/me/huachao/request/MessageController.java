package me.huachao.request;

/**
 * Created by huachao on 1/25/16.
 */

import me.huachao.service.AccessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.charset.Charset;

/**
 * Used to receive messages from user
 */

@Controller
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    private static final String appId = "wxcbba150534e590d1";
    private static final String token = "huachao_wechat";
    private static final String aesKey = "Ly2xPGHMoMUfzNCxqIN3Z2NopsnKzGGEwvRo56j0pWT";

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String dispatchMsg(@RequestParam(value = "echostr", required = false) String echostr) {
        if (StringUtils.isEmpty(echostr)) { //接受普通消息
            return "forward:/msg";
        } else { //接入
            return "forward:/sign";
        }
    }

    @RequestMapping(value = "/sign", method = RequestMethod.GET)
    public @ResponseBody String sign(@RequestParam("signature") String signature,
                @RequestParam("timestamp") String timestamp,
                @RequestParam("nonce") String nonce,
                @RequestParam("echostr") String echostr) {
        logger.info(String.format("signature:%s; timestamp:%s; nonce:%s; echostr:%s;",
                signature, timestamp, nonce, echostr));
        boolean pass = AccessService.checkSignature(signature, timestamp, nonce, token);
        logger.warn(String.format("接入:%b", pass));
        if (pass)
            return echostr;
        else
            return null;
    }

    @RequestMapping(value = "/msg", method = {RequestMethod.POST})
    public String msg(@RequestParam("timestamp") String timestamp,
                      @RequestParam("nonce") String nonce,
                      @RequestParam("msg_signature") String msg_signature,
                      @RequestParam(value = "encrypt_type", required = false) String encrypt_type,
                      HttpEntity<byte[]> requestEntity) {
        String postBody = new String(requestEntity.getBody(), Charset.forName("utf-8"));
        logger.info("header:%s, postBody:%s", requestEntity.getHeaders().toString(), postBody);
        return null;
    }



}
