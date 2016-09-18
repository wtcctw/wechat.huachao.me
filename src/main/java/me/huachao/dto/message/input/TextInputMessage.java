package me.huachao.dto.message.input;

import lombok.Data;

/**
 * Created by huachao on 1/26/16.
 */
@Data
public class TextInputMessage extends BaseInputMessage {

    private String content;

    public TextInputMessage(String to, String from, String createTime, String type, String msgId, String content) {
        super(to, from, createTime, type, msgId);
        this.content = content;
    }

}
