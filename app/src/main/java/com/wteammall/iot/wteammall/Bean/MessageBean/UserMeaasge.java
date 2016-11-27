package com.wteammall.iot.wteammall.Bean.MessageBean;

/**
 * Created by I0T on 2016/11/24.
 */
public class UserMeaasge {

    private String Name;
    private String Context;
    private int Type;

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContext() {
        return Context;
    }

    public void setContext(String context) {
        Context = context;
    }
}
