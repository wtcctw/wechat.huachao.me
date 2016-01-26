package me.huachao.dto.message.output;

import java.util.Date;

/**
 * Created by huachao on 1/26/16.
 */
public class BaseOutputMessage {

    private String to;
    private String from;
    private Date createTime;
    private String type;

    public BaseOutputMessage(String to, String from, Date createTime, String type){
        this.to = to;
        this.from = from;
        this.createTime = createTime;
        this.type = type;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
