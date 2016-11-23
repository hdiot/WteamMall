package com.wteammall.iot.wteammall.UserModule.PersionalCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.wteammall.iot.wteammall.MainActivity;
import com.wteammall.iot.wteammall.R;

public class PersionIntegralActivity extends AppCompatActivity {

    TextView TV_Back;
    TextView TV_ToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persion_integral);

        initView();

        setListener();

        getMyIntegralInfo();
    }


    public void initView(){
        TV_Back = (TextView) findViewById(R.id.tv_per_integra_title_back);
        TV_ToMain = (TextView) findViewById(R.id.tv_per_integra_title_mainpage);
    }

    public void setListener(){
        TV_ToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersionIntegralActivity.this, MainActivity.class));
            }
        });

        TV_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersionIntegralActivity.this, PersonalCenterActivity.class));
            }
        });
    }

    public void getMyIntegralInfo(){

    }

}
