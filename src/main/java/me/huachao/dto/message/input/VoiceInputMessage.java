package me.huachao.dto.message.input;

import lombok.Data;

/**
 * Created by huachao on 1/26/16.
 */

@Data
public class VoiceInputMessage extends BaseInputMessage {

    private String mediaId;
    private String format;
    private String recongnition;

    public VoiceInputMessage(String to, String from, String createTime, String type, String msgId,
                             String mediaId, String format, String recongnition) {
        super(to, from, createTime, type, msgId);
        this.mediaId = mediaId;
        this.format = format;
        this.recongnition = recongnition;
    }

}
