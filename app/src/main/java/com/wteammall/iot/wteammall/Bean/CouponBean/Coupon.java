package com.wteammall.iot.wteammall.Bean.CouponBean;

import java.util.ArrayList;

/**
 * Created by I0T on 2016/11/24.
 */
public class Coupon {

    String Begintime;
    String EndTime;
    int Funtion;
    String Name;
    int MaxGetNum;
    int Nums;
    int RemainNums;
    ArrayList<String> rang;
    double Remssion;
    double Satify;




    public ArrayList<String> getRang() {
        return rang;
    }

    public void setRang(ArrayList<String> rang) {
        this.rang = rang;
    }
    public int getMaxGetNum() {
        return MaxGetNum;
    }

    public void setMaxGetNum(int maxGetNum) {
        MaxGetNum = maxGetNum;
    }

    public int getNums() {
        return Nums;
    }

    public void setNums(int nums) {
        Nums = nums;
    }

    public int getRemainNums() {
        return RemainNums;
    }

    public void setRemainNums(int remainNums) {
        RemainNums = remainNums;
    }

    public double getRemssion() {
        return Remssion;
    }

    public void setRemssion(double remssion) {
        Remssion = remssion;
    }

    public double getSatify() {
        return Satify;
    }

    public void setSatify(double satify) {
        Satify = satify;
    }

    public String getBegintime() {
        return Begintime;
    }

    public void setBegintime(String begintime) {
        Begintime = begintime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public int getFuntion() {
        return Funtion;
    }

    public void setFuntion(int funtion) {
        Funtion = funtion;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}
