package com.wteammall.iot.wteammall.UserModule.PersionalCenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.wteammall.iot.wteammall.Adapter.CouponAdapter;
import com.wteammall.iot.wteammall.Bean.MyCouponBean;
import com.wteammall.iot.wteammall.R;

import java.util.ArrayList;
import java.util.List;

public class PersionCouponActivity extends AppCompatActivity {

    ListView CouponListView;
    TextView TV_Back;
    TextView TV_GetCoupon;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persion_coupon);
        Log.d("haha","GGG---main");
        initView();
        setListener();
        getMyCouponInfo();
    }

    public void initView(){
        Log.d("haha","GGG---view");
        CouponListView = (ListView) findViewById(R.id.lv_per_coupon);
        TV_Back = (TextView) findViewById(R.id.tv_per_coupon_title_back);
        TV_GetCoupon = (TextView) findViewById(R.id.tv_per_coupon_title_togetcoupon);
    }

    public void setListener(){
        TV_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersionCouponActivity.this,PersonalCenterActivity.class));
            }
        });

        TV_GetCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(PersionCouponActivity.this,PersonalCenterActivity.class));

            }
        });
    }

    public void getMyCouponInfo(){
        List<MyCouponBean>list = new ArrayList<MyCouponBean>();
        MyCouponBean couponBean ;
        for (int i=0 ; i<10 ; i++){
            couponBean = new MyCouponBean();
            couponBean.setAmount(i);
            couponBean.setDate("2016-11-"+i);
            couponBean.setPrize(""+i);
            couponBean.setType(""+i+"类");
            list.add(couponBean);
            Log.d("haha","GGG---"+i);
        }
        CouponAdapter adapter = new CouponAdapter(PersionCouponActivity.this,list);
        CouponListView.setAdapter(adapter);
    }


}
