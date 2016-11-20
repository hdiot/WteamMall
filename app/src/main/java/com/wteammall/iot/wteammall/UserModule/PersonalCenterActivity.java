package com.wteammall.iot.wteammall.UserModule;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wteammall.iot.wteammall.R;

public class PersonalCenterActivity extends AppCompatActivity {

    LinearLayout LY_Persion_Info;
    LinearLayout LY_Persion_Msg;
    LinearLayout LY_Persion_Order;
    LinearLayout LY_Persion_Coupon;
    LinearLayout LY_Persion_Integal;

    ImageView IV_HeaderView;
    TextView TV_HeaderText;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);

        initView();
        setListener();

        IV_HeaderView.setImageIcon(null);
        TV_HeaderText.setText("");
    }

    public void initView(){
        LY_Persion_Coupon = (LinearLayout) findViewById(R.id.ly_persion_persioncoupon);
        LY_Persion_Order = (LinearLayout) findViewById(R.id.ly_persion_persionoder);
        LY_Persion_Info = (LinearLayout) findViewById(R.id.ly_persion_persioninfo);
        LY_Persion_Msg = (LinearLayout) findViewById(R.id.ly_persion_persionmsg);
        LY_Persion_Integal = (LinearLayout) findViewById(R.id.ly_persion_persionintegral);

        IV_HeaderView = (ImageView)findViewById(R.id.iv_persion_head);
        TV_HeaderText = (TextView)findViewById(R.id.tv_persion_header);

    }

    public void setListener(){

        LY_Persion_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        LY_Persion_Msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        LY_Persion_Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        LY_Persion_Coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        LY_Persion_Integal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
