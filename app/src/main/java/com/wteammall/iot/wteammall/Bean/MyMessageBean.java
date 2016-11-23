package com.wteammall.iot.wteammall.Bean;

/**
 * Created by I0T on 2016/11/23.
 */
public class MyMessageBean {
    private String Sender;
    private String Context;
    private String Time;

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getContext() {
        return Context;
    }

    public void setContext(String context) {
        Context = context;
    }
}
