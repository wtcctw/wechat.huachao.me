package me.huachao.request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huachao on 1/28/16.
 */
public class ShareContext {

    public static ThreadLocal<Map<String, Object>> SHARE_CONTEXT = new ThreadLocal<Map<String, Object>>();

    public static void initContext() {
        Map<String, Object> context = SHARE_CONTEXT.get();
        if (context == null) {
            SHARE_CONTEXT.set(new HashMap<String, Object>());
        }
    }

    public static Map<String, Object> getAllContext(){
        return SHARE_CONTEXT.get();
    }

    public static void setContext(String key, Object value) {
        Map<String, Object> context = SHARE_CONTEXT.get();
        if (context == null) {
            SHARE_CONTEXT.set(new HashMap<String, Object>());
        }
        context = SHARE_CONTEXT.get();
        context.put(key, value);
    }

    public static Object getContext(String key) {
        Map<String, Object> context = SHARE_CONTEXT.get();
        if (context != null) {
            return context.get(key);
        }
        return null;
    }

}