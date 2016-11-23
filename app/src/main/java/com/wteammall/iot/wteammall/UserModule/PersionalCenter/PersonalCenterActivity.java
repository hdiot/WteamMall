package com.wteammall.iot.wteammall.UserModule.PersionalCenter;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wteammall.iot.wteammall.MainActivity;
import com.wteammall.iot.wteammall.R;

public class PersonalCenterActivity extends AppCompatActivity {

    LinearLayout LY_Persion_Info;
    LinearLayout LY_Persion_Msg;
    LinearLayout LY_Persion_Order;
    LinearLayout LY_Persion_Coupon;
    LinearLayout LY_Persion_Integal;

    TextView TV_BACK;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);

        initView();
        setListener();

    }

    public void initView(){
        LY_Persion_Coupon = (LinearLayout) findViewById(R.id.ly_persion_persioncoupon);
        LY_Persion_Order = (LinearLayout) findViewById(R.id.ly_persion_persionoder);
        LY_Persion_Info = (LinearLayout) findViewById(R.id.ly_persion_persioninfo);
        LY_Persion_Msg = (LinearLayout) findViewById(R.id.ly_persion_persionmsg);
        LY_Persion_Integal = (LinearLayout) findViewById(R.id.ly_persion_persionintegral);

        TV_BACK = (TextView) findViewById(R.id.tv_per_center_title_back);
    }

    public void setListener(){

        LY_Persion_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PersonalCenterActivity.this,"进入订单页面",Toast.LENGTH_LONG).show();
                startActivity(new Intent(PersonalCenterActivity.this,PersionOrderActivity.class));
            }
        });
        LY_Persion_Msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PersonalCenterActivity.this,"进入信消息页面",Toast.LENGTH_LONG).show();
                startActivity(new Intent(PersonalCenterActivity.this,PersionMessageActivity.class));
            }
        });
        LY_Persion_Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PersonalCenterActivity.this,"进入个人信息页面",Toast.LENGTH_LONG).show();
                startActivity(new Intent(PersonalCenterActivity.this,PersionInformationActivity.class));
            }
        });
        LY_Persion_Coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PersonalCenterActivity.this,"进入优惠券页面",Toast.LENGTH_LONG).show();
                startActivity(new Intent(PersonalCenterActivity.this,PersionCouponActivity.class));
            }
        });
        LY_Persion_Integal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PersonalCenterActivity.this,"进入积分页面",Toast.LENGTH_LONG).show();
                startActivity(new Intent(PersonalCenterActivity.this,PersionIntegralActivity.class));
            }
        });

        TV_BACK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersonalCenterActivity.this, MainActivity.class));
            }
        });
    }
}
