package com.wteammall.iot.wteammall.CouponModule;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * Created by liupe on 2016/11/24.
 */

public class CouponData implements Serializable {


    private String id;
    private String name;//名字
    private String detail;//优惠券详情
    private int function;//作用，1表示满减，2表示打折
    private BigDecimal discount;//折扣
    private BigDecimal satisfy; //满多少
    private BigDecimal remission;//减多少
    private Date beginTime;//最早使用时间
    private Date endTime;//最晚使用时间
    private int nums; //优惠券数量
    private int remainNums;//剩余数量
    private int maxGetNums;//每人限制领取数量
    private String range;

    public CouponData(int function, int maxGetNums, int remainNums, String range, int nums, Date endTime, Date beginTime, BigDecimal remission, BigDecimal discount) {
        this.function = function;
        this.maxGetNums = maxGetNums;
        this.remainNums = remainNums;
        this.range = range;
        this.nums = nums;
        this.endTime = endTime;
        this.beginTime = beginTime;
        this.remission = remission;
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public Date getEndTime() {
        return endTime;
    }

    public int getFunction() {
        return function;
    }

    public String getRange() {
        return range;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public int getRemainNums() {
        return remainNums;
    }

    public int getMaxGetNums() {
        return maxGetNums;
    }

    public int getNums() {
        return nums;
    }

    public String getRemission() {
        return remission.toString();
    }

    public String getId() {
        return id;
    }

    public BigDecimal getSatisfy() {
        return satisfy;
    }
}
