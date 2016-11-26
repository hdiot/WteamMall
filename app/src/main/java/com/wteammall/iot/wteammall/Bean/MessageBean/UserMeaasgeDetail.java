package com.wteammall.iot.wteammall.Bean.MessageBean;

/**
 * Created by I0T on 2016/11/24.
 */
public class UserMeaasgeDetail {

    String AcceptTime;
    String ID;
    UserMeaasge mMessage;
    int Status;

    public String getAcceptTime() {
        return AcceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        AcceptTime = acceptTime;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public UserMeaasge getmMessage() {
        return mMessage;
    }

    public void setmMessage(UserMeaasge mMessage) {
        this.mMessage = mMessage;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
