package com.wteammall.iot.wteammall.CouponModule;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wteammall.iot.wteammall.R;

import java.util.List;

import static android.R.attr.data;


/**
 * Created by liupe on 2016/11/24.
 */

public class CouponListAdapter extends RecyclerView.Adapter<CouponListAdapter.CouponViewHolder> {
    private List<CouponData> Coupones;
    private Context context;

    public CouponListAdapter(List<CouponData> Coupones, Context context) {
        this.Coupones = Coupones;
        this.context = context;
    }

    @Override
    public CouponListAdapter.CouponViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.coupon_item, viewGroup, false);
        CouponViewHolder nvh = new CouponViewHolder(v);
        return nvh;
    }

    @Override
    public void onBindViewHolder(final CouponListAdapter.CouponViewHolder personViewHolder, int i) {
        final int j = i;
        String type;
        String date;
        String amount;
        String maxGetNums;
        String satisfy;
        String remission;

        //对信息进行处理

        //类型
        if (Coupones.get(i).getFunction() == 1) type = "满减券";
        else type = "打折券";

        //日期
       /* date = Coupones.get(i).getBeginTime().getYear() + "." + Coupones.get(i).getBeginTime().getMonth() + "." + Coupones.get(i).getBeginTime().getDate()
                + "-" + Coupones.get(i).getEndTime().getYear() + "." + Coupones.get(i).getBeginTime().getMonth() + "." + Coupones.get(i).getBeginTime().getDate();*/
        String beginTime = Coupones.get(i).getBeginTime();
        String endTIme = Coupones.get(i).getEndTime();

        date = beginTime.substring(0, 10) + "至" + endTIme.substring(0, 10);

        //数量
        amount = Coupones.get(i).getRemainNums() + "/" + Coupones.get(i).getNums();

        //限制
        maxGetNums = "每人限领" + Coupones.get(i).getMaxGetNums() + "张";
        //满减限制
        if (Coupones.get(i).getSatisfy() == 0) satisfy = "";
        else satisfy = "满" + Coupones.get(i).getSatisfy() + "积分";


        //减多少,折扣
        if (Coupones.get(i).getFunction() == 1) remission = Coupones.get(i).getRemission() + "积分";
        else remission = Coupones.get(i).getDiscount() + "折";
        //对每一个优惠券项目进行赋值


        personViewHolder.coupon_date.setText(date);
        personViewHolder.coupon_range.setText("null");
        personViewHolder.coupon_amount.setText(amount);
        personViewHolder.coupon_type.setText(type);
        personViewHolder.coupon_discount.setText(remission);
        personViewHolder.coupon_limit.setText(maxGetNums);
        personViewHolder.coupon_satisfy.setText(satisfy);


        //cardView设置点击事件
        personViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent=new Intent(context,NewsActivity.class);
                // intent.putExtra("News",newses.get(j));
                //context.startActivity(intent);

            }
        });

        //coupon_button设置点击事件
        personViewHolder.getCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016/11/25 点击后访问网络返回是否成功 若成功则检测获得张数是不是达到最大限制,是就将显示已达到最大领取量并变灰


                personViewHolder.getCoupon.setBackgroundColor(Color.GRAY);







               /*
               Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                intent.putExtra(Intent.EXTRA_TEXT, newses.get(j).getDesc());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(Intent.createChooser(intent, newses.get(j).getTitle()));
                */
            }
        });


    }

    @Override
    public int getItemCount() {
        return Coupones.size();
    }

    //自定义ViewHolder类
    static class CouponViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView coupon_date;
        TextView coupon_range;
        TextView coupon_amount;
        TextView coupon_type;
        TextView coupon_discount;
        TextView coupon_limit;
        TextView coupon_satisfy;
        Button getCoupon;

        public CouponViewHolder(final View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.coupon_view);
            coupon_date = (TextView) itemView.findViewById(R.id.coupon_date);
            coupon_range = (TextView) itemView.findViewById(R.id.coupon_range);
            coupon_amount = (TextView) itemView.findViewById(R.id.coupon_amount);
            coupon_type = (TextView) itemView.findViewById(R.id.coupon_type);
            coupon_discount = (TextView) itemView.findViewById(R.id.coupon_discount);
            coupon_limit = (TextView) itemView.findViewById(R.id.coupon_limit);
            coupon_satisfy = (TextView) itemView.findViewById(R.id.coupon_satisfy);
            getCoupon = (Button) itemView.findViewById(R.id.coupon_button);


            //设置TextView背景为半透明
            // news_title.setBackgroundColor(Color.argb(20, 0, 0, 0));

        }


    }

}
