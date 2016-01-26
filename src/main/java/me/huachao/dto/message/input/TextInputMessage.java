package me.huachao.dto.message.input;

/**
 * Created by huachao on 1/26/16.
 */
public class TextInputMessage extends BaseInputMessage {

    private String content;

    public TextInputMessage(String to, String from, String createTime, String type, String msgId, String content) {
        super(to, from, createTime, type, msgId);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
