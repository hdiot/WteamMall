package com.wteammall.iot.wteammall.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wteammall.iot.wteammall.Bean.CouponBean.Coupons;
import com.wteammall.iot.wteammall.R;

/**
 * Created by I0T on 2016/11/23.
 */
public class CouponAdapter extends BaseAdapter {

    Coupons mCoupons;
    Context mContext;
    LayoutInflater mInflater;
    private ViewHolder mViewHolder;

    public CouponAdapter(Context context, Coupons coupons) {
        Log.d("haha", "GGG--1");
        this.mContext = context;
        this.mCoupons = coupons;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mCoupons.getCouponlist().size();
    }

    @Override
    public Object getItem(int i) {
        return mCoupons.getCouponlist().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int I = i;

        mViewHolder = new ViewHolder();

        Log.d("haha", "GGG---");
        view = mInflater.inflate(R.layout.listview_item_per_coupon, null);
        mViewHolder.CouponName = (TextView) view.findViewById(R.id.coupon_name);
        mViewHolder.CouponBeginTime = (TextView) view.findViewById(R.id.coupon_begintime);
        mViewHolder.CouponEndTIme = (TextView) view.findViewById(R.id.coupon_endtime);
        mViewHolder.CouponRange = (TextView) view.findViewById(R.id.tv_coupon_range);
        mViewHolder.CouponButton = (Button) view.findViewById(R.id.coupom_button);


        mViewHolder.CouponName.setText(mCoupons.getCouponlist().get(i).getmCoupon().getName());
        mViewHolder.CouponBeginTime.setText(mCoupons.getCouponlist().get(i).getmCoupon().getBegintime());
        mViewHolder.CouponEndTIme.setText(mCoupons.getCouponlist().get(i).getmCoupon().getEndTime());
        if(mCoupons.getCouponlist().get(i).getmCoupon().getRang().size() == 0){
            mViewHolder.CouponRange.setText("全品类");
        }else {
            String Range = "";
            for (int n = 0; i<mCoupons.getCouponlist().get(i).getmCoupon().getRang().size(); n++){
                Range += mCoupons.getCouponlist().get(i).getmCoupon().getRang().get(n);
            }
            mViewHolder.CouponRange.setText(Range);
        }

        mViewHolder.CouponButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,mCoupons.getCouponlist().get(I).getmCoupon().getName(),Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


    public final class ViewHolder {
        public TextView CouponName;
        public TextView CouponBeginTime;
        public TextView CouponEndTIme;
        public TextView CouponRange;
        public Button CouponButton;
    }
}
