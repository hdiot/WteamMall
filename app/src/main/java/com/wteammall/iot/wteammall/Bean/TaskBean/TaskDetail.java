package com.wteammall.iot.wteammall.Bean.TaskBean;

/**
 * Created by I0T on 2016/11/24.
 */
public class TaskDetail {

    Task mTask;
    String ID;
    String AcceptTime;
    int Status;

    public Task getmTask() {
        return mTask;
    }

    public void setmTask(Task mTask) {
        this.mTask = mTask;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAcceptTime() {
        return AcceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        AcceptTime = acceptTime;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
