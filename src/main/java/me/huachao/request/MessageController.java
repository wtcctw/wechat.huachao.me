package me.huachao.request;

/**
 * Created by huachao on 1/25/16.
 */

import me.huachao.dto.message.input.BaseInputMessage;
import me.huachao.dto.message.input.TextInputMessage;
import me.huachao.dto.message.output.BaseOutputMessage;
import me.huachao.dto.message.output.TextOutputMessage;
import me.huachao.service.AccessService;
import me.huachao.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * Used to receive messages from user
 */

@Controller
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Resource
    private MessageService messageService;

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
        boolean pass = AccessService.checkSignature(signature, timestamp, nonce, "huachao_wechat");
        logger.warn(String.format("接入:%b", pass));
        return (pass)? echostr : null;
    }

    @RequestMapping(value = "/msg", method = RequestMethod.POST)
    public @ResponseBody String msg(@RequestParam("timestamp") String timestamp,
                      @RequestParam("nonce") String nonce,
                      @RequestParam("msg_signature") String msgSignature,
                      @RequestParam(value = "encrypt_type", required = false) String encrypt_type,
                      HttpEntity<byte[]> requestEntity) {
        String postBody = new String(requestEntity.getBody(), Charset.forName("utf-8"));
        logger.info(String.format("header:%s, postBody:%s", requestEntity.getHeaders().toString(), postBody));
        String decryptMsg = messageService.decryptMsg(msgSignature, timestamp, nonce, postBody);
        logger.info("decryptedMessage:"+decryptMsg);
        BaseInputMessage inputMessage = messageService.parseInputMsg(decryptMsg);
        if (inputMessage != null && inputMessage.getType().equals("text")) {
            BaseOutputMessage outputMessage = handleTextMsg((TextInputMessage) inputMessage);
            return messageService.encryptMsg(outputMessage.toString(), timestamp, nonce);
        }
        return "success";
    }

    private BaseOutputMessage handleTextMsg(TextInputMessage textInputMessage) {
        TextOutputMessage outputMessage = new TextOutputMessage(textInputMessage.getFrom(),
                textInputMessage.getTo(), new Date(), "text", "reply:"+textInputMessage.getContent());
        return outputMessage;
    }
}
