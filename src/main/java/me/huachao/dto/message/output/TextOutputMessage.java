package me.huachao.dto.message.output;

import java.util.Date;

/**
 * Created by huachao on 1/26/16.
 */
public class TextOutputMessage extends BaseOutputMessage{

    private static final String template = "<xml>\n" +
            "<ToUserName><![CDATA[%s]]></ToUserName>\n" +
            "<FromUserName><![CDATA[%s]]></FromUserName>\n" +
            "<CreateTime>%d</CreateTime>\n" +
            "<MsgType><![CDATA[text]]></MsgType>\n" +
            "<Content><![CDATA[%s]]></Content>\n" +
            "</xml>";

    private String content;

    public TextOutputMessage(String to, String from, Date createTime, String type, String content) {
        super(to, from, createTime, type);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toString() {
        return String.format(template,
                      this.getTo(),
                      this.getFrom(),
                      this.getCreateTime().getTime(),
                      this.getContent());
    }
}
