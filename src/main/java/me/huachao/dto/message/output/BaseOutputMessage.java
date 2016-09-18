package me.huachao.dto.message.output;

import lombok.Data;

import java.util.Date;

/**
 * Created by huachao on 1/26/16.
 */
@Data
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

}
