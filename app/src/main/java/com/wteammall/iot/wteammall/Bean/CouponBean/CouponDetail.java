package com.wteammall.iot.wteammall.Bean.CouponBean;

/**
 * Created by I0T on 2016/11/24.
 */
public class CouponDetail {


    String ID;
    String ObtainTime;
    int Status;
    Coupon mCoupon;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getObtainTime() {
        return ObtainTime;
    }

    public void setObtainTime(String obtainTime) {
        ObtainTime = obtainTime;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public Coupon getmCoupon() {
        return mCoupon;
    }

    public void setmCoupon(Coupon mCoupon) {
        this.mCoupon = mCoupon;
    }


}
