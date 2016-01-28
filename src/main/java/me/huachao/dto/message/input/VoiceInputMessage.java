package me.huachao.dto.message.input;

/**
 * Created by huachao on 1/26/16.
 */
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

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getRecongnition() {
        return recongnition;
    }

    public void setRecongnition(String recongnition) {
        this.recongnition = recongnition;
    }
}
