package com.wteammall.iot.wteammall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

/**
 * 启动界面（*闪屏页面）
 * 可以在启动页面添加版本更新等活动
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        //进入首页
        startActivity(new Intent(SplashActivity.this,MainActivity.class));
    }
}
