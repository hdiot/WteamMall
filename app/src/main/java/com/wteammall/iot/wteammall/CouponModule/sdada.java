package com.wteammall.iot.wteammall.CouponModule;

import java.util.List;

/**
 * Created by liupe on 2016/11/26.
 */

public class sdada {
    /**
     * beginTime : 2016-11-23T18:23:06
     * endTime : 2017-01-01T00:00:00
     * function : 1
     * id : 8a8865865890b5d5015890b5d7b90000
     * maxGetNums : 3
     * name : ?1999?500
     * nums : 999
     * range : []
     * remainNums : 998
     * remission : 500.0
     * satisfy : 1999.0
     * discount : 8.0
     */

    private String beginTime;
    private String endTime;
    private int function;
    private String id;
    private int maxGetNums;
    private String name;
    private int nums;
    private int remainNums;
    private double remission;
    private double satisfy;
    private double discount;
    private List<String> range;

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getFunction() {
        return function;
    }

    public void setFunction(int function) {
        this.function = function;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMaxGetNums() {
        return maxGetNums;
    }

    public void setMaxGetNums(int maxGetNums) {
        this.maxGetNums = maxGetNums;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public int getRemainNums() {
        return remainNums;
    }

    public void setRemainNums(int remainNums) {
        this.remainNums = remainNums;
    }

    public double getRemission() {
        return remission;
    }

    public void setRemission(double remission) {
        this.remission = remission;
    }

    public double getSatisfy() {
        return satisfy;
    }

    public void setSatisfy(double satisfy) {
        this.satisfy = satisfy;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public List<String> getRange() {
        return range;
    }

    public void setRange(List<String> range) {
        this.range = range;
    }
}
