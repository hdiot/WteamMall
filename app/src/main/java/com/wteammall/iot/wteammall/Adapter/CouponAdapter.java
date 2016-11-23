package com.wteammall.iot.wteammall.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.wteammall.iot.wteammall.Bean.MyCouponBean;
import com.wteammall.iot.wteammall.R;

import java.util.List;

/**
 * Created by I0T on 2016/11/23.
 */
public class CouponAdapter extends BaseAdapter {

    List<MyCouponBean> mCouponList;
    Context mContext;
    LayoutInflater mInflater;
    private ViewHolder mViewHolder;

    public CouponAdapter(Context context, List<MyCouponBean> couponBeenlist) {
        Log.d("haha", "GGG--1");
        this.mContext = context;
        this.mCouponList = couponBeenlist;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mCouponList.size();
    }

    @Override
    public Object getItem(int i) {
        return mCouponList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        mViewHolder = new ViewHolder();

        Log.d("haha", "GGG---");
        view = mInflater.inflate(R.layout.listview_item_per_coupon, null);
        mViewHolder.CouponPrize = (TextView) view.findViewById(R.id.coupon_prize);
        mViewHolder.CouponAmount = (TextView) view.findViewById(R.id.coupon_amount);
        mViewHolder.CouponDate = (TextView) view.findViewById(R.id.coupon_date);
        mViewHolder.CouponType = (TextView) view.findViewById(R.id.coupon_type);
        mViewHolder.CouponButton = (Button) view.findViewById(R.id.coupom_button);

        mViewHolder.CouponPrize.setText(mCouponList.get(i).getPrize());
        mViewHolder.CouponType.setText(mCouponList.get(i).getType());
        mViewHolder.CouponDate.setText(mCouponList.get(i).getDate());
        mViewHolder.CouponAmount.setText(mCouponList.get(i).getAmount() + " ");

        return view;
    }


    public final class ViewHolder {
        public TextView CouponPrize;
        public TextView CouponDate;
        public TextView CouponType;
        public TextView CouponAmount;
        public Button CouponButton;
    }
}
