package me.huachao.dto.message.input;

/**
 * Created by huachao on 1/26/16.
 */
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

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getMsgId() {
        return msgId;
    }

    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }
}
