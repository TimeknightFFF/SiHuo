package com.sunshine.sihuo.chatpackage;

/**
 * Created by Administrator on 2015/5/5.
 */
public class ChatMessage {

    // 接收消息的类型
    public static final int SOURCE_TYPE_RECEIVED=1;

    // 发送的消息类型
    public static final int SOURCE_TYPE_SEND=0;

    private String from;
    private String to;
    private String body;

    private long time;
    private int sourceType;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }
}
