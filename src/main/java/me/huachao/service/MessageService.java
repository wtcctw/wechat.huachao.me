package me.huachao.service;

import me.huachao.dto.message.input.BaseInputMessage;
import me.huachao.dto.message.input.TextInputMessage;
import me.huachao.service.crypt.AesException;
import me.huachao.service.crypt.WXBizMsgCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

/**
 * Created by huachao on 1/26/16.
 */


public class MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Resource
    private WXBizMsgCrypt crypt;

    public String decryptMsg(String msgSignature, String timestamp, String nonce, String postBody) {
        String decryptedMsg = null;
        try {
            decryptedMsg = crypt.decryptMsg(msgSignature, timestamp, nonce, postBody);
            logger.info(String.format("decrypted message:%s", decryptedMsg));
        } catch (Exception ex) {
            decryptedMsg = "";
            logger.warn("decrypt message failed!", ex);
        }
        return decryptedMsg;
    }

    public String encryptMsg(String replyMsg, String timestamp, String nonce) {
        String encryptedMsg = "success";
        try {
            encryptedMsg = crypt.encryptMsg(replyMsg, timestamp, nonce);
            logger.info(String.format("encrypted message:%s", encryptedMsg));
        } catch (AesException e) {
            logger.warn("encrypt message failed!", e);
        }
        return encryptedMsg;
    }

    public BaseInputMessage parseInputMsg(String xmltext) {
        BaseInputMessage inputMessage = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            StringReader sr = new StringReader(xmltext);
            InputSource is = new InputSource(sr);
            Document document = db.parse(is);
            Element root = document.getDocumentElement();
            String to = root.getElementsByTagName("ToUserName").item(0).getTextContent();
            String from = root.getElementsByTagName("FromUserName").item(0).getTextContent();
            String createTime = root.getElementsByTagName("CreateTime").item(0).getTextContent();
            String msgId = root.getElementsByTagName("MsgId").item(0).getTextContent();
            String msgType = root.getElementsByTagName("MsgType").item(0).getTextContent();
            String content = root.getElementsByTagName("Content").item(0).getTextContent();
            if (!StringUtils.isEmpty(msgType) && msgType.equals("text")) {
                inputMessage = new TextInputMessage(to, from, createTime, msgType, msgId, content);
            }
        } catch (Exception e) {
            logger.warn("parseInputMsg failed!", e);
        }
        return inputMessage;
    }
}
