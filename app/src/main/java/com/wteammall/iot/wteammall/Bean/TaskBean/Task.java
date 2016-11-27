package com.wteammall.iot.wteammall.Bean.TaskBean;

/**
 * Created by I0T on 2016/11/24.
 */
public class Task {

    int ID;
    String Name;
    int Type;
    String Content;
    double Points;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public double getPoints() {
        return Points;
    }

    public void setPoints(double points) {
        Points = points;
    }
}
