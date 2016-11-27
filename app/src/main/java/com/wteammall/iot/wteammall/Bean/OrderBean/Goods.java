package com.wteammall.iot.wteammall.Bean.OrderBean;

/**
 * Created by I0T on 2016/11/27.
 */
public class Goods {
    String Freight;
    String ID;
    String LimgUrl;
    String Name;
    String SimgUrl;

    public String getFreight() {
        return Freight;
    }

    public void setFreight(String freight) {
        Freight = freight;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getLimgUrl() {
        return LimgUrl;
    }

    public void setLimgUrl(String limgUrl) {
        LimgUrl = limgUrl;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSimgUrl() {
        return SimgUrl;
    }

    public void setSimgUrl(String simgUrl) {
        SimgUrl = simgUrl;
    }
}
