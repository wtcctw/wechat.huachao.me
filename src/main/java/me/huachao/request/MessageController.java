package me.huachao.request;

/**
 * Created by huachao on 1/25/16.
 */

import me.huachao.WeChatException;
import me.huachao.dto.message.input.BaseInputMessage;
import me.huachao.dto.message.input.TextInputMessage;
import me.huachao.dto.message.input.VoiceInputMessage;
import me.huachao.dto.message.output.BaseOutputMessage;
import me.huachao.dto.message.output.TextOutputMessage;
import me.huachao.service.AccessService;
import me.huachao.service.MessageService;
import me.huachao.service.SimsimiService;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    @Resource
    private SimsimiService simsimiService;

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String dispatchMsg(@RequestParam(value = "echostr", required = false) String echostr) {
        ShareContext.initContext();
        ShareContext.setContext("requestid", RandomUtils.nextInt(1, 100));
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
        logger.info("signature:{}; timestamp:{}; nonce:{}; echostr:{};",
                signature, timestamp, nonce, echostr);
        boolean pass = AccessService.checkSignature(signature, timestamp, nonce, "huachao_wechat");
        logger.warn("接入:{}", pass);
        return (pass)? echostr : null;
    }

    @RequestMapping(value = "/msg", method = RequestMethod.POST)
    public @ResponseBody String msg(@RequestParam("timestamp") String timestamp,
                      @RequestParam("nonce") String nonce,
                      @RequestParam("msg_signature") String msgSignature,
                      @RequestParam(value = "encrypt_type", required = false) String encrypt_type,
                      HttpEntity<byte[]> requestEntity) {
        ShareContext.setContext("timestamp", timestamp);
        ShareContext.setContext("nonce", nonce);
        String postBody = new String(requestEntity.getBody(), Charset.forName("utf-8"));
        logger.info("header:{}, postBody:{}", requestEntity.getHeaders().toString(), postBody);
        String decryptMsg = messageService.decryptMsg(msgSignature, timestamp, nonce, postBody);
        logger.info("decryptedMessage:{}", decryptMsg);
        BaseInputMessage inputMessage = messageService.parseInputMsg(decryptMsg);

        //根据message的type进行分类处理
        if (inputMessage != null && inputMessage.getType().equals("text")) {
            BaseOutputMessage outputMessage = handleTextMsg((TextInputMessage) inputMessage);
            return messageService.encryptMsg(outputMessage.toString(), timestamp, nonce);
        } else if (inputMessage != null && inputMessage.getType().equals("voice")) {

        }
        return messageService.encryptMsg("success", timestamp, nonce);
    }

    @ExceptionHandler(WeChatException.class)
    public @ResponseBody String handleWeChatException(WeChatException weChatException) {
        logger.warn("WeChatException Handling, {}", weChatException.toString());
        return messageService.encryptMsg("success", (String)ShareContext.getContext("timestamp"), (String)ShareContext.getContext("nonce"));
    }

    @ExceptionHandler(Exception.class)
    public @ResponseBody String handleAllException(Exception ex) {
        logger.warn("Exception Handling, {}", ex);
        return messageService.encryptMsg("success", (String)ShareContext.getContext("timestamp"), (String)ShareContext.getContext("nonce"));
    }


    private BaseOutputMessage handleTextMsg(TextInputMessage textInputMessage) {
        String words = textInputMessage.getContent();
        String replyWords = simsimiService.reply(words);
        TextOutputMessage outputMessage = new TextOutputMessage(textInputMessage.getFrom(),
                textInputMessage.getTo(), new Date(), "text", replyWords);
        return outputMessage;
    }

    private BaseOutputMessage handleVoiceMsg(VoiceInputMessage voiceInputMessage) {
        String words = voiceInputMessage.getRecongnition();
        String replyWords = simsimiService.reply(words);
        TextOutputMessage outputMessage = new TextOutputMessage(voiceInputMessage.getFrom(),
                voiceInputMessage.getTo(), new Date(), "text", replyWords);
        return outputMessage;
    }
}
