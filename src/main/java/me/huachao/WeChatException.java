package me.huachao;

/**
 * Created by huachao on 1/28/16.
 */
public class WeChatException extends RuntimeException {

    public static final int ERR_MSG = 5001;
    public static final int ERR_HTTP = 5002;

    private int errCode;
    private String errMsg;

    public WeChatException(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String toString() {
        return String.format("WeChatException:{errCode:%d, errMsg:%s}", this.errCode, this.errMsg);
    }
}
