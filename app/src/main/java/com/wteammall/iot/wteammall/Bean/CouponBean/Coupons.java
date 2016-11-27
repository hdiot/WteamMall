package com.wteammall.iot.wteammall.Bean.CouponBean;

import java.util.List;

/**
 * Created by I0T on 2016/11/23.
 */
public class Coupons {
    List<CouponDetail> couponlist;
    public List<CouponDetail> getCouponlist() {
        return couponlist;
    }
    public void setCouponlist(List<CouponDetail> couponlist) {
        this.couponlist = couponlist;
    }
}