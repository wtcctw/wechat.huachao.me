package me.huachao.dto.message;

import lombok.Data;

import java.util.List;

/**
 * Created by huachao on 9/18/16.
 */
@Data
public class BaiduTuringResponse {

    private int code;
    private String text;
    private int app_id;
    private String user_reqid;
    private int faqAnswerId;
    private String tableName;
    private int task_pos;
    private String text_after;
    private String showtext;
    private String show_text_after;
    private List<TextItem> text_array;

    @Data
    public static class TextItem {
        private String text;
        private String text_speed;
        private String text_pitch;
        private String text_sound;

    }
}
