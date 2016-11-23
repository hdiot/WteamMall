package com.wteammall.iot.wteammall.UserModule.PersionalCenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.wteammall.iot.wteammall.MainActivity;
import com.wteammall.iot.wteammall.R;

public class PersionOrderActivity extends AppCompatActivity {


    TextView TV_Back;
    TextView TV_ToMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persion_order);

        initView();
        setListener();
        getMyOrderInfo();

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public void initView(){
        TV_Back = (TextView) findViewById(R.id.tv_persion_ordertitle_back);
        TV_ToMain = (TextView) findViewById(R.id.tv_persion_order_title_mainpage);
    }

    public void setListener(){
        TV_ToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersionOrderActivity.this, MainActivity.class));
            }
        });

        TV_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersionOrderActivity.this, PersonalCenterActivity.class));
            }
        });
    }

    public void getMyOrderInfo(){

    }

}
