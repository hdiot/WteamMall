package com.wteammall.iot.wteammall.Bean;

/**
 * Created by I0T on 2016/11/22.
 */
public class MyUserInfoBean {
    private String UserName;
    private boolean SignStatus;
    private String Email;
    private int AccountStatus;
    private String RegisterTime;
    private String HeaderImgUrl;

    public int getPoints() {
        return Points;
    }

    public void setPoints(int points) {
        Points = points;
    }

    private int Points;

    public String getHeaderImgUrl() {
        return HeaderImgUrl;
    }

    public void setHeaderImgUrl(String headerImgUrl) {
        HeaderImgUrl = headerImgUrl;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public boolean getSignStatus() {
        return SignStatus;
    }

    public void setSignStatus(boolean signStatus) {
        SignStatus = signStatus;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getAccountStatus() {
        return AccountStatus;
    }

    public void setAccountStatus(int accountStatus) {
        AccountStatus = accountStatus;
    }

    public String getRegisterTime() {
        return RegisterTime;
    }

    public void setRegisterTime(String registerTime) {
        RegisterTime = registerTime;
    }

}
