package me.huachao.dto.message.input;

import lombok.Data;

/**
 * Created by huachao on 1/26/16.
 */
@Data
public class BaseInputMessage {

    private String to;
    private String from;
    private int createTime;
    private String type;
    private long msgId;

    public BaseInputMessage(String to, String from, String createTime, String type, String msgId){
        this.to = to;
        this.from = from;
        this.createTime = Integer.parseInt(createTime);
        this.type = type;
        this.msgId = Long.parseLong(msgId);
    }

}
